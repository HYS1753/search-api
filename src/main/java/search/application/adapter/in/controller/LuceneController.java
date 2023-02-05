package search.application.adapter.in.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import search.application.biz.lucene.port.in.LucenePort;
import search.application.domain.common.CommonRes;
import search.application.domain.common.ResponseMessage;
import search.application.domain.lucene.KoreanRestaurantReq;
import search.application.domain.lucene.KoreanRestaurantRes;
import search.common.Constants;

@Slf4j
@Tag(name = "Lucene API", description = "Lucene 관련 API")
@RequestMapping("/search/lucene")
@RestController
public class LuceneController {
	
	@Autowired
	private LucenePort LuceneService;
	
	/**
	 * @description : 한국 음식점 정보 색인 실행  API Controller
	 * @param None
	 * @return ResponseMessage.class
	 */
	@Operation(summary = "Korea Restaurant Indexing API", description = "<b style='color: red;'>한국 음식점 정보</b> 색인 실행 API.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200"
					, description = Constants.MESSAGE_200_PREFIX + "CommonRes" + Constants.MESSAGE_200_POSTFIX
					, content = @Content(schema = @Schema(implementation = CommonRes.class))),
			@ApiResponse(responseCode = "404"
				, description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "An error occurred")
	})
	@GetMapping("/koreanRestaurant/indexing")
	public ResponseMessage koreanRestaurentIndexing() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			log.debug("========== LuceneController :: koreanRestaurentIndexing");
			responseMessage = ResponseMessage.builder()
					.resultData(LuceneService.koreanRestaurentIndexing())
					.resultCode(HttpStatus.OK.value())
					.resultMsg(Constants.MESSAGE_200)
					.build();
		} catch (Exception e) {
			log.debug("Exception occured.");
			responseMessage.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseMessage.setResultMsg(Constants.MESSAGE_500);
			//throw new BizRuntimeException(e);
		}
		return responseMessage;
	}
	
	/**
	 * @description : 한국 음식점 정보 검색 실행  API Controller
	 * @param None
	 * @return ResponseMessage.class
	 */
	@Operation(summary = "Korea Restaurant Search API", description = "<b style='color: red;'>한국 음식점 정보</b> 검색 API.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200"
					, description = Constants.MESSAGE_200_PREFIX + "KoreanRestaurantRes" + Constants.MESSAGE_200_POSTFIX
					, content = @Content(schema = @Schema(implementation = KoreanRestaurantRes.class))),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "An error occurred")
	})
	@GetMapping("/koreanRestaurant/searching")
	public ResponseMessage koreanRestaurentSearching(@Parameter(name="keywords", description="keywords(키워드)", required=true, in=ParameterIn.QUERY, example = "중식") String keywords) {
		KoreanRestaurantReq params = new KoreanRestaurantReq();
		params.setKeywords(keywords);
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			log.debug("========== LuceneController :: koreanRestaurentSearch");
			responseMessage = ResponseMessage.builder()
					.resultData(LuceneService.koreanRestaurentSearching(params))
					.resultCode(HttpStatus.OK.value())
					.resultMsg(Constants.MESSAGE_200)
					.build();
		} catch (Exception e) {
			log.debug("Exception occured.");
			responseMessage.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseMessage.setResultMsg(Constants.MESSAGE_500);
			//throw new BizRuntimeException(e);
		}
		return responseMessage;
	}
}
