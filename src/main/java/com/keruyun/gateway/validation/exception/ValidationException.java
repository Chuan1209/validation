package com.keruyun.gateway.validation.exception;

import com.keruyun.gateway.validation.type.ErrorType;

/**
 * 校验异常
 * Created by youngtan99@163.com on 15/10/23.
 */
public class ValidationException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = -3768385243351564836L;

    private ErrorType errorType;

    public ValidationException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    public ValidationException(ErrorType errorType, Throwable cause) {
        super(cause);
        this.errorType = errorType;
    }

    public ValidationException(ErrorType errorType) {
        super();
        this.errorType = errorType;
    }

    public ValidationException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ValidationException() {
        super();
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    /**
     * @return the errorType
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * @param errorType the errorType to set
     */
    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
