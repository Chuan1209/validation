package com.keruyun.gateway.validation.exception;

import com.keruyun.gateway.validation.type.ErrorType;

/**
 * 业务异常 Created by tany@shishike.com on 15/10/23.
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -3008701086164361883L;
	
	/**
	 * 错误码
	 */
	private ErrorType errorType;

	public ServiceException(ErrorType errorType, String message, Throwable cause) {
		super(message, cause);
		this.errorType = errorType;
	}

	public ServiceException(ErrorType errorType, Throwable cause) {
		super(cause);
		this.errorType = errorType;
	}

	public ServiceException(ErrorType errorType) {
		super();
		this.errorType = errorType;
	}

	public ServiceException(ErrorType errorType, String message) {
		super(message);
		this.errorType = errorType;
	}

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * @return the errorType
	 */
	public ErrorType getErrorType() {
		return errorType;
	}

	/**
	 * @param errorType
	 *            the errorType to set
	 */
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	@Override
	public Throwable fillInStackTrace() {
		return null;
	}
}
