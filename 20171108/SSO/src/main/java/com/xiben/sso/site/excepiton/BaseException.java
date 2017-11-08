package com.xiben.sso.site.excepiton;

public class BaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorMsg;
	private String errorCode;
	
	public BaseException(String errorCode,String errorMsg,Throwable e){
		
		super(errorMsg,e);
		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
		
	}
	
    public BaseException(String errorCode,String errorMsg){
		
		super(errorMsg);
		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
		
	}
    
	

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
	
	
	
}
