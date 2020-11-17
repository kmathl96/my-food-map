package com.web.curation.exception;

public class MyException extends RuntimeException {
	public MyException() {
		super("로그인 관련 에러");
		System.out.println("로그인 관련 에러");
	}
	public MyException(String msg) {
		super(msg);
		System.out.println(msg);
	}
}
