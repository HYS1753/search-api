package search.application.biz.lucene.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import search.application.biz.lucene.port.in.LucenePort;
import search.application.biz.lucene.port.out.LuceneOutPort;
import search.application.domain.common.CommonRes;
import search.application.domain.lucene.KoreanRestaurantReq;
import search.application.domain.lucene.KoreanRestaurantRes;


/**
* @Project : search-api
* @FileName : LuceneService.java
* @Date : 2023. 1. 31.
* @author : HYS
* @description : 한국 음식점 정보 검색 API Service
*/
@Slf4j
@Service
public class LuceneService implements LucenePort {
	
	@Autowired
	private LuceneOutPort luceneEngineService;
	
	@Override
	public CommonRes koreanRestaurentIndexCheck() {
		// 색인 파일 유무 확인
		CommonRes res = new CommonRes();
		return res;
	}
	
	@Override
	public CommonRes koreanRestaurentIndexing() throws Exception {
		CommonRes res = new CommonRes();
		if (this.luceneEngineService.koreanRestaurentIndexing()) {
			res.setResult("Success");
		} else {
			res.setResult("Fail");
		}
		return res;
	}
	
	@Override
	public KoreanRestaurantRes koreanRestaurentSearch(KoreanRestaurantReq koreanRestaurantReq) {
		
		// CSV 파일을 통한 수집 색인 
		
		// return
		KoreanRestaurantRes res = new KoreanRestaurantRes();
		return res;
	}

}
