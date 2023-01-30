package search.application.domain.lucene;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @Project : search-api
* @FileName : KoreanRestaurantReq.java
* @Date : 2023. 1. 31.
* @author : HYS
* @description : 한국 음식점 정보 검색 API Request Domain 입니다.
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class KoreanRestaurantReq {
	
	@Schema(description = "검색 키워드", example = "중식")
	private String keywords; 

}
