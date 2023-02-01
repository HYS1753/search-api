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
public class CommonRes {

	@Schema(description = "성공유무", example = "True")
	private Boolean result;
	
	@Schema(description = "실행결과", example = "True")
	private String message;
}
