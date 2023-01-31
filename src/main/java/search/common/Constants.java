package search.common;

public class Constants {
	
	// ResponseMessage 및 Swagger 상수
	
	/** 200 메시지 클래스 반환 접두문자 */
    //public static final String MESSAGE_200_PREFIX = "<pre>Request han been successfully<br/>{<br/>  \"data\": {}, <font color=\"red\">data be referenced by";
	public static final String MESSAGE_200_PREFIX = "<pre>Request han been successfully<br/>{<br/> \"resultCode\": 200,<br/> \"resultMessage\": \"Success\", <br/> \"resultData\": {}, <font color=\"red\"> 하단에 정의된 ";
    /** 200 메시지 클래스 반환 접미문자 */
    //public static final String MESSAGE_200_POSTFIX = " class</font><br/>  \"statusCode\": 200,<br/>  \"resultMessage\": \"Success\",<br/>  \"detailMessage\": \"Error Message\"<br/>}</pre>";
	public static final String MESSAGE_200_POSTFIX = " Domain class가 data로 출력.</font><br/>}</pre>";
    /** 200 메시지 */
    public static final String MESSAGE_200 = "Success";
    /** 204 메시지 */
    public static final String MESSAGE_204 = "No data received";
    /** 404 메시지 */
    public static final String MESSAGE_404 = "Page Not Found";
    /** 500 메시지 */
    public static final String MESSAGE_500 = "An error occurred";
    
    // DB 파일 pre, post fix 설정
    public static final String DB_FILE_PREFIX = "database_";
    
    public static final String DB_FILE_POSTFIX = ".zip";
    
    // 연산용 반복 횟수
    public static final Integer ITERCOUNT = 2;
   
}
