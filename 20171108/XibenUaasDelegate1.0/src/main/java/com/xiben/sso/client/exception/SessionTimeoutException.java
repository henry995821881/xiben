package com.xiben.sso.client.exception;

public class SessionTimeoutException extends Exception{
	public SessionTimeoutException(String errorMsg)
	{
		super(errorMsg);
	}
}
