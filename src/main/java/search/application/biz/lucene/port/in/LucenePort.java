package search.application.biz.lucene.port.in;

import search.application.domain.lucene.KoreanRestaurantReq;
import search.application.domain.lucene.KoreanRestaurantRes;

/**
 * @Project     : search-api
 * @FileName    : LucenePort.java
 * @Date        : 2023. 1. 31.
 * @author      : HYS
 * @description : Lucene 검색 API In Bound Port
 */
public interface LucenePort {

	/**
	* @Date : 2023. 1. 31.
	* @author : HYS
	* @description : 한국 음식점 정보 검색 API Inbound Port
	* @param KoreanRestaurantReq
	* @return
	*/
	public KoreanRestaurantRes koreanRestaurentSearch(KoreanRestaurantReq koreanRestaurantReq);
}
