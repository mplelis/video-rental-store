package com.videorentalstore.exception;

/**
 * All application logic related exceptions should throw this.
 */
public class VideoApplicationException extends Exception {
    private static final long serialVersionUID = 1L;

    public VideoApplicationException(String message){
        super(message);
    }

    public VideoApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

}
