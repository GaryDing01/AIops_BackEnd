package com.aiops_web.std;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseStd<T> implements Serializable {

    private T data;
    private int code;
    private String message;
    private String description;

    public ResponseStd(T data, int code, String message, String description) {
        this.data = data;
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public ResponseStd(T data, int code, String message) {
        this(data, code, message, "");
    }

    public ResponseStd( T data, int code) {
        this(data, code, "", "");
    }

    public ResponseStd( T data) {
        this(data, ErrorCode.SUCCESS.getCode(), "", "");
    }

    public ResponseStd(ErrorCode errorCode) {
        this(null, errorCode.getCode(), errorCode.getMessage(), errorCode.getDescription());
    }

    public ResponseStd(ErrorCode errorCode, T data) {
        this(data, errorCode.getCode(), errorCode.getMessage(), errorCode.getDescription());
    }
}