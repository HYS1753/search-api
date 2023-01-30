package search.application.domain.lucene;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @Project : search-api
* @FileName : KoreanRestaurantRes.java
* @Date : 2023. 1. 31.
* @author : HYS
* @description : 한국 음식점 정보 검색 API Response Domain 입니다.
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class KoreanRestaurantRes {

	@Schema(description = "음식점 명", example = "새마을식당")
	private String restaurantName; 

	@Schema(description = "카테고리1", example = "음식")
	private String category1; 

	@Schema(description = "검색 키워드", example = "음식점")
	private String category2; 

	@Schema(description = "검색 키워드", example = "한식")
	private String category3; 

	@Schema(description = "지역명", example = "서울")
	private String region; 
	
	@Schema(description = "시군구명", example = "종로구")
	private String city; 
	
	@Schema(description = "개요", example = "새마을식당은 돼지고기를 즉석에서 썰어내어 특제고추장 ...")
	private String description; 

}
