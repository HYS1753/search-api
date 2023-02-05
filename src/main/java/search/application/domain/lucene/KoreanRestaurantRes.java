package search.application.domain.lucene;

import java.util.ArrayList;

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

	@Schema(description = "검색 된 결과 개수", example = "10")
	private Long matchCount;
	
	@Schema(description = "검색결과", example = "KoreanRestaurantVO")
	private ArrayList<KoreanRestaurantVo> searchResult;

}
