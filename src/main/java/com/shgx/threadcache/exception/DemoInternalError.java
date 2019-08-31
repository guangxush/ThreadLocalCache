package com.shgx.threadcache.exception;

/**
 * @Description
 * @auther guangxush
 * @create 2019/1/25
 */
public class DemoInternalError extends RuntimeException{
    public DemoInternalError(String message, String s) {

    }

    public DemoInternalError(String message) {
        super(message);
    }

    public DemoInternalError(String message, Throwable e){
        super(message, e);
    }
}
