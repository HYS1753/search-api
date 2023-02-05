package search.application.biz.lucene.port.out;

import search.application.domain.common.CommonRes;
import search.application.domain.lucene.KoreanRestaurantReq;
import search.application.domain.lucene.KoreanRestaurantRes;

/**
* @Project : search-api
* @FileName : LuceneOutPort.java
* @Date : 2023. 1. 31.
* @author : HYS
* @description :  Lucene 검색 API Out Bound Port
*/

public interface LuceneOutPort {

	/**
	* @Date : 2023. 1. 31.
	* @author : HYS
	* @description : 한국 음식점 정보 색인 실행 API Out Bound Port
	* @param 
	* @return
	* @throws Exception 
	*/
	public CommonRes koreanRestaurentIndexing() throws Exception;
	
	/**
	* @Date : 2023. 2. 05.
	* @author : HYS
	* @description : 한국 음식점 정보 검색 실행 API Out Bound Port
	* @param 
	* @return
	* @throws Exception 
	*/
	public KoreanRestaurantRes koreanRestaurentSearching(KoreanRestaurantReq koreanRestaurantReq) throws Exception;

}
