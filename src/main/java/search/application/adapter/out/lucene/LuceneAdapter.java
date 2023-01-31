package search.application.adapter.out.lucene;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import lombok.extern.slf4j.Slf4j;
import search.application.biz.lucene.port.out.LuceneOutPort;
import search.application.domain.lucene.KoreanRestaurantVo;

/**
* @Project : search-api
* @FileName : LuceneAdapter.java
* @Date : 2023. 1. 31.
* @author : HYS
* @description : 지하철 API DB Adapter
*/
@Slf4j
@Service
public class LuceneAdapter implements LuceneOutPort {
	
	@Value("${lucene.koreanRestaurant.indexPath}")
	private String koreanRestaurantIndexPath;	// 색인 할 경로
	
	@Value("${lucene.koreanRestaurant.csvFilePath}")
	private String koreanRestaurantCsvFilePath;

	@Override
	public Boolean koreanRestaurentIndexing() throws Exception {
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: Start");
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: indexing properies setting start");
		// 색인 할 디렉터리 오픈
		Directory indexDirectory = FSDirectory.open(Paths.get(koreanRestaurantIndexPath));
		// 한국어 분석을 위한 koreanAnalyzer 선언
		KoreanAnalyzer analyzer = new KoreanAnalyzer();
		// 색인 생성을 위한 Wirter설정 정보 구성, 분석기는 한국어 분석기 사용.
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		// 색인 디렉터리 및 설정정보를 통해 wirter 생성한다.
		IndexWriter writer = new IndexWriter(indexDirectory, config);
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: indexing properies setting success");
		
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: read korean Restaurant data from csv file start");
		//csv file 읽어 드림
		List<KoreanRestaurantVo> docs = new ArrayList<KoreanRestaurantVo>();
		ClassPathResource csvFilePath = new ClassPathResource(koreanRestaurantCsvFilePath);
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath.getURI()))){
			// csv 라이브러리 사용
			CsvToBean csvToBean = new CsvToBeanBuilder(reader)
					.withType(KoreanRestaurantVo.class)
					.withIgnoreLeadingWhiteSpace(true)
					.build();
			docs = csvToBean.parse();
		} catch (Exception e) {
			log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: read korean Restaurant data from csv file fail");
			log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: Fail");
			return false;
			//e.printStackTrace();
		}
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: parsed from csv " + docs.size() + " data");
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: read korean Restaurant data from csv file success");
		
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: indexing korean restaurant data start");
		// indexing start
		docs.stream().forEach(i -> this.koreanRestaurentAddDoc(i, writer));
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: indexing korean restaurant data success");
		
		// 색인 작업 완료 후 writer 닫음.
		writer.close();
		
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: Success");
		return true;
	}
	
	private void koreanRestaurentAddDoc(KoreanRestaurantVo doc, IndexWriter writer) {
		// Create Document
		Document document = new Document();
		
		try {
			// 검색 필드 추가
			// TextField : 토큰으로 분할해 분석
			// StringField : 전체 전문 검색
			document.add(new TextField("restaurantName", doc.getRestaurantName(), Field.Store.YES));
			document.add(new StringField("category1", doc.getCategory1(), Field.Store.YES));
			document.add(new StringField("category2", doc.getCategory2(), Field.Store.YES));
			document.add(new StringField("category3", doc.getCategory3(), Field.Store.YES));
			document.add(new StringField("region", doc.getRegion(), Field.Store.YES));
			document.add(new StringField("city", doc.getCity(), Field.Store.YES));
			document.add(new TextField("description", doc.getDescription(), Field.Store.YES));
			
			// 색인에 추가 
			writer.addDocument(document);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
}
