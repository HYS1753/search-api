package search.application.adapter.out.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;

import lombok.extern.slf4j.Slf4j;
import search.application.biz.lucene.port.out.LuceneOutPort;
import search.application.domain.common.CommonRes;
import search.application.domain.lucene.KoreanRestaurantReq;
import search.application.domain.lucene.KoreanRestaurantRes;
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

	/**
	* @Date : 2023. 2. 05.
	* @author : HYS
	* @description : koreanRestaurentIndexing
	* @param 
	* @return CommonRes
	*/
	@Override
	public CommonRes koreanRestaurentIndexing() throws Exception {
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: Start");
		CommonRes result = new CommonRes();
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: indexing properies setting start");
		// 색인 할 디렉터리 오픈
		Directory indexDirectory = FSDirectory.open(Paths.get(koreanRestaurantIndexPath));
		// 기존 색인 결과 있으면 삭제 
		File prevIndexDirectory = new File(koreanRestaurantIndexPath);
		File[] prevIndexfiles = prevIndexDirectory.listFiles();
		for (File file : prevIndexfiles) {
			file.delete();
		}
		// 한국어 분석을 위한 koreanAnalyzer 선언
		KoreanAnalyzer analyzer = new KoreanAnalyzer();
		// 색인 생성을 위한 Wirter설정 정보 구성, 분석기는 한국어 분석기 사용.
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		// 색인 디렉터리 및 설정정보를 통해 wirter 생성한다.
		IndexWriter writer = new IndexWriter(indexDirectory, config);
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: indexing properies setting success");
		
		// read csv file 
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: read korean Restaurant data from csv file start");
		List<KoreanRestaurantVo> docs = new ArrayList<KoreanRestaurantVo>();
		ClassPathResource csvFilePath = new ClassPathResource(koreanRestaurantCsvFilePath);
		try {
			File csvFile = new File(csvFilePath.getURI());
			docs= new CsvToBeanBuilder<KoreanRestaurantVo>(new FileReader(csvFile))
		            .withType(KoreanRestaurantVo.class)
		            .build()
		            .parse();
		} catch (Exception e) {
			String resultMsg = "read korean Restaurant data from csv file fail. Please Check logs.";
			log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: " + resultMsg);
			log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: Fail");
			result.setResult(false);
			result.setMessage(resultMsg);
			return result;
			//e.printStackTrace();
		}
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: parsed from csv " + docs.size() + " data");
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: read korean Restaurant data from csv file success");
		
		// indexing start
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: indexing korean restaurant data start");
		long startIndexingTime = System.currentTimeMillis();
		docs.stream().forEach(i -> this.koreanRestaurentAddDoc(i, writer));
		long endIndexingTime = System.currentTimeMillis();
		long elapseTime = (long) ((endIndexingTime - startIndexingTime)/1000.0);
		String ResultMsg = "indexing korean restaurant data end. parsed " + docs.size() + " documents, elapse " + elapseTime + " sec";
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: " + ResultMsg);
		
		// 색인 작업 완료 후 writer 닫음.
		writer.close();
		
		log.debug("========== LuceneAdapter :: koreanRestaurentIndexing :: Success");
		result.setResult(true);
		result.setMessage(ResultMsg);
		return result;
	}
	
	/**
	* @Date : 2023. 2. 05.
	* @author : HYS
	* @description : koreanRestaurentSearching
	* @param KoreanRestaurantReq
	* @return KoreanRestaurantRes
	*/
	@Override
	public KoreanRestaurantRes koreanRestaurentSearching(KoreanRestaurantReq koreanRestaurantRes) throws IOException {
		KoreanRestaurantRes result = new KoreanRestaurantRes();
		// 색인 결과 디렉터리 내 색인 결과 유무 판단
		File indexDirectory = new File(koreanRestaurantIndexPath);
		
		// 색인 결과가 있을 경우 
		if (indexDirectory.list().length > 0) {
			log.debug("========== LuceneAdapter :: koreanRestaurentSearching :: Indexing file is exists. Search start.");
			log.debug("========== LuceneAdapter :: koreanRestaurentSearching :: Create IndexReader, Searcher start");
			// 색인 결과를 읽을 indexReader 설정
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(koreanRestaurantIndexPath)));
			// 읽은 색인 결과를 검색하는 IndexSearcher 생성
			IndexSearcher searcher = new IndexSearcher(reader);
			log.debug("========== LuceneAdapter :: koreanRestaurentSearching :: Create IndexReader, Searcher Success");
			
			log.debug("========== LuceneAdapter :: koreanRestaurentSearching :: Create Search Query Start");
			// 검색 대상 설정
			TermQuery termQry = new TermQuery(new Term("restaurantName", koreanRestaurantRes.getKeywords()));
			TermQuery termQry2 = new TermQuery(new Term("description", koreanRestaurantRes.getKeywords()));
			
			// 검색식(쿼리) 생성
			BooleanQuery qry = new BooleanQuery
					.Builder()
					.add(new BooleanClause(termQry, BooleanClause.Occur.SHOULD))
					.add(new BooleanClause(termQry2, BooleanClause.Occur.SHOULD))
					.build();
			
			// 검색 결과 제한 개수
			int hitsPerPage = 10;
			
			log.debug("========== LuceneAdapter :: koreanRestaurentSearching :: Create Search Query Success");
			
			log.debug("========== LuceneAdapter :: koreanRestaurentSearching :: Start Searching with Indexing file and Search Query Start");
			// IndexSearch에 쿼리와 가져올 결과를 설정하고 검색 시작
			TopDocs docs = searcher.search(qry, hitsPerPage);
			// 검색 결과는 ScoreDoc 배열로 가져온다.
		    ScoreDoc[] hits = docs.scoreDocs;
		    
		    // 검색 결과를 KoreanRestaurantVO에 담는다.
		    ArrayList<KoreanRestaurantVo> resultDoc = new ArrayList<KoreanRestaurantVo>();
		    for (int i = 0; i < hits.length; i++) {
		    	KoreanRestaurantVo document = new KoreanRestaurantVo();
		    	Document tempDoc = searcher.doc(hits[i].doc);
		    	document.setRestaurantName(tempDoc.get("restaurantName"));
		    	document.setCategory1(tempDoc.get("category1"));
		    	document.setCategory2(tempDoc.get("category2"));
		    	document.setCategory3(tempDoc.get("category3"));
		    	document.setRegion(tempDoc.get("region"));
		    	document.setCity(tempDoc.get("city"));
		    	document.setDescription(tempDoc.get("description"));
		    	resultDoc.add(document);
		    }
		    log.debug("========== LuceneAdapter :: koreanRestaurentSearching :: Searching with Indexing file and Search Query Success");
		    result.setMatchCount(docs.totalHits.value);
		    result.setSearchResult(resultDoc);
		} else {
			log.debug("========== LuceneAdapter :: koreanRestaurentSearching :: indexing file is not exists. Search Fail.");
			result.setMatchCount(0L);
		}
		return result;
	}
	
	/**
	* @Date : 2023. 2. 05.
	* @author : HYS
	* @description : koreanRestaurentAddDoc
	* @param KoreanRestaurantVo, IndexWriter
	* @return 
	*/
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
			//log.debug(doc.getRestaurantName() + doc.getCategory1() + doc.getCategory2() + doc.getCategory3() + doc.getRegion() + doc.getCity() + doc.getDescription());
			//log.debug(e.toString());
			e.printStackTrace();
		}
	}
}
