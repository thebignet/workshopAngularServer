package piouter.dto;

import java.io.Serializable;

public class ResponseDto implements Serializable {
    public static final ResponseDto OK = new ResponseDto(0,"");
    private final Integer code;
    private final String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ResponseDto(Integer code, String message) {

        this.code = code;
        this.message = message;
    }
}
