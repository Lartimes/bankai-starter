package bankai.shizuku.ichigo.common.exception;

import bankai.shizuku.ichigo.common.response.ErrorCode;
import bankai.shizuku.ichigo.common.response.ResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException implements Serializable {

    protected int code;

    public BusinessException() {
        super();
        this.code = ResponseCode.FAILURE.getCode();
    }

    public BusinessException(String msg) {
        super(msg);
        this.code = ResponseCode.FAILURE.getCode();
    }

    public BusinessException(String msg, Throwable throwable) {
        super(msg, throwable);
        this.code = ResponseCode.FAILURE.getCode();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }

    public BusinessException(ErrorCode errorCode, Throwable throwable) {
        this(errorCode.getCode(), errorCode.getMessage(), throwable);
    }

}