package hrms.human_resource_system.exception;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class DLException extends RuntimeException {

    private static final Logger logger = Logger.getLogger(DLException.class.getName());

    static {
        try {
            FileHandler fh = new FileHandler("logs/errors.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (Exception e) {
            System.err.println("Logger setup failed: " + e.getMessage());
        }
    }

    public DLException(String message) {
        super(message);
        logger.severe(message);
    }

    public DLException(String message, Throwable cause) {
        super(message, cause);
        logger.severe(message + " - " + cause.getMessage());
    }
}
