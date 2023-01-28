package search.application.domain.common;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
	
	@Schema(description = "처리 상태 코드", required = true, example = "200")
	private Integer resultCode;
	
	@Schema(description = "처리 결과 메세지", required = false, example = "정상처리 되었습니다.")
	private String resultMsg;
	
	@Schema(description = "요청 처리 결과 Object", required = false)
	private Object resultData;

    public static ResponseMessage responseMessageBuilder(Integer resultCode, String resultMsg, Object resultData) {
        ResponseMessage message = new ResponseMessage();
        message.setResultCode(resultCode);
        message.setResultMsg(resultMsg);
        message.setResultData(resultData);
        return message;
    }
    public static ResponseMessage responseMessageBuilder(Integer resultCode, String resultMsg) {
        ResponseMessage message = new ResponseMessage();
        message.setResultCode(resultCode);
        message.setResultMsg(resultMsg);
        return message;
    }


}
