package com.fastsale;

@SuppressWarnings("serial")
public class CouponException extends Exception
{
    public CouponException(String msg, Throwable e)
	{
		super(msg,e);
	}
	
	public CouponException(String msg)
	{
		super(msg);
	}
}
