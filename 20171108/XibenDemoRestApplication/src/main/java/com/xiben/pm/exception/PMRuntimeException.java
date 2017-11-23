package com.xiben.pm.exception;

public class PMRuntimeException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PMRuntimeException(String msg)
	{
		super(msg);
	}
	public PMRuntimeException(String msg,Throwable t)
	{
		super(msg,t);
	}
}
