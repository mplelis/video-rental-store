package com.videorentalstore.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoLogging {
	
    private static final Logger LOGGER = LoggerFactory.getLogger("app_logger");
    private static final Logger ERROR_LOGGER = LoggerFactory.getLogger("error_logger");

    private VideoLogging() {
    }
    
    public static void logMessage(String message) {
        try {
            LOGGER.info(message);
        } catch (Exception e) {
            ERROR_LOGGER.error("logMessage - Exception: {}", e);
            ERROR_LOGGER.error("logMessage: {}", message);
        }
    }
    
    public static void logMessage(String message, Object... arguments) {
        try {
            LOGGER.info(message, arguments);
        } catch (Exception e) {
            ERROR_LOGGER.error("logMessage - Exception: {}", e);
            ERROR_LOGGER.error("logMessage: {}", message);
        }
    }

	public static void logError(String message) {
		ERROR_LOGGER.error("VideoError - {}", message);
	}
}