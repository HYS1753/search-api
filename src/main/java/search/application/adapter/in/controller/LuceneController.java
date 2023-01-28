package search.application.adapter.in.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import search.application.domain.common.ResponseMessage;
import search.common.Constants;

@Slf4j
@Tag(name = "Lucene API", description = "Lucene 관련 API")
@RequestMapping("/search/lucene")
@RestController
public class LuceneController {

	@Operation(summary = "Test API", description = "<b style='color: red;'>TEST</b> API.")
	@Parameters(value = {
	    @Parameter(name="id", description="id(식별코드)", required=true, in=ParameterIn.QUERY, example = "1234")
	})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200"
					, description = Constants.MESSAGE_200_PREFIX + "ResponseMessage" + Constants.MESSAGE_200_POSTFIX
					, content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "An error occurred")
	})
	@GetMapping("/test")
	public ResponseMessage test(String id) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			log.debug("========== LuceneController :: test");
			responseMessage = ResponseMessage.builder()
					.resultData(id)
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
