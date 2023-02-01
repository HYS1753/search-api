package search.application.domain.lucene;

import com.opencsv.bean.CsvBindByPosition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @Project : search-api
* @FileName : KoreanRestaurantVo.java
* @Date : 2023. 1. 31.
* @author : HYS
* @description : 한국 음식점 정보 검색 API VO Domain 입니다.
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class KoreanRestaurantVo {

	@CsvBindByPosition(position = 0)
	@Schema(description = "음식점 명", example = "새마을식당")
	private String restaurantName; 

	@CsvBindByPosition(position = 1)
	@Schema(description = "카테고리1", example = "음식")
	private String category1; 

	@CsvBindByPosition(position = 2)
	@Schema(description = "검색 키워드", example = "음식점")
	private String category2; 

	@CsvBindByPosition(position = 3)
	@Schema(description = "검색 키워드", example = "한식")
	private String category3; 

	@CsvBindByPosition(position = 4)
	@Schema(description = "지역명", example = "서울")
	private String region; 
	
	@CsvBindByPosition(position = 5)
	@Schema(description = "시군구명", example = "종로구")
	private String city; 
	
	@CsvBindByPosition(position = 6)
	@Schema(description = "개요", example = "새마을식당은 돼지고기를 즉석에서 썰어내어 특제고추장 ...")
	private String description; 
}
