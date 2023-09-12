package hernando.com.customlog;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Hernando Garcia
 * 
 *         This class represents a custom logger implementation for logging
 *         messages.
 */
public class CustomLogger implements AppLogger {
	private static CustomLogger instance;
	private Logger logger;
	private String logFilePath = "C:\\config\\logging.properties";
	private Formatter logFormatter = new LogFormatter(); // Default log format
	private Class<?> callingClass; // Instance variable to store the calling class
	// To avoid initializations

	protected CustomLogger() {
	}

	/**
	 * Private constructor to create a CustomLogger instance.
	 *
	 * @param loggerName The name of the logger.
	 */
	private CustomLogger(String loggerName) {
		logger = Logger.getLogger(loggerName); // Use the provided logger name
		try (InputStream input = new FileInputStream(logFilePath)) {
			Properties properties = loadProperties(logFilePath);
			if (properties != null) {
				properties.load(input);
				String logLevel = properties.getProperty("logLevel", "ALL");
				logger.setLevel(Level.parse(logLevel));
				String customLogFormatterClassName = properties.getProperty("customLogFormatter");
				setCustomLogFormatter(customLogFormatterClassName);
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "error ocurred", e);
		}
	}

	/**
	 * Sets a custom log formatter for the logger.
	 *
	 * @param customLogFormatterClassName The name of the custom log formatter
	 *                                    class.
	 */
	private void setCustomLogFormatter(String customLogFormatterClassName) {
		if (customLogFormatterClassName != null && !customLogFormatterClassName.isEmpty()) {
			try {
				logFormatter = (Formatter) Class.forName(customLogFormatterClassName).getDeclaredConstructor()
						.newInstance();
			} catch (Exception e) {
				logger.log(Level.SEVERE, "error ocurred", e);
			}
		}
	}



	/**
	 * Gets an instance of CustomLogger.
	 *
	 * @param callingClass The class that calls the logger.
	 * @return An instance of CustomLogger.
	 */
	public static synchronized CustomLogger getInstance(Class<?> callingClass) {
		if (instance == null) {
			String loggerName = callingClass.getSimpleName();
			instance = new CustomLogger(loggerName);
		}
		instance.setCallingClass(callingClass); // Set the calling class
		return instance;
	}

	/**
	 * Sets the calling class for the logger.
	 *
	 * @param callingClass The class that calls the logger.
	 */
	public void setCallingClass(Class<?> callingClass) {
		this.callingClass = callingClass;
	}

	/**
	 * Sets the log file path for the logger.
	 *
	 * @param logFilePath The path to the log file.
	 */
	public void setLogFilePath(final String logFilePath) {
		this.logFilePath = logFilePath;
		// Remove existing handlers
		Handler[] handlers = logger.getHandlers();
		for (Handler handler : handlers) {
			logger.removeHandler(handler);
			handler.close();
		}
		// add custom file handler with the updated log file path
		try {
			FileHandler fileHandler = new CustomFileHandler(logFilePath, logFormatter);
			// Add custom formatter
			fileHandler.setFormatter(logFormatter);
			// Add custom filter
			fileHandler.setFilter(new LogFilter());
			logger.addHandler(fileHandler);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "error ocurred", e);
		}
	}

	/**
	 * Loads properties from a properties file.
	 *
	 * @param propertiesFileName The name of the properties file.
	 * @return The loaded properties.
	 */
	protected Properties loadProperties(String propertiesFileName) {
		Properties properties = new Properties();
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
			if (inputStream != null) {
				properties.load(inputStream);
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "error ocurred", e);
		}
		return properties;
	}

	/**
	 * Creates log messages with class name prefix.
	 *
	 * @param callingClass The class that calls the logger.
	 * @param message      The log message.
	 * @return The formatted log message with a class name prefix.
	 */
	private final synchronized String messages(Class<?> callingClass, String message) {
		this.callingClass = callingClass;
		String className = callingClass != null ? "[" + callingClass.getSimpleName() + "] " : "";
		return className.concat(message);
	}

	/**
	 * Logs an informational message.
	 *
	 * @param message The log message.
	 * @param args    The message arguments.
	 */
	public void info(String message, Object... args) {
		if (logger.isLoggable(Level.INFO)) {
			logger.log(Level.INFO, messages(callingClass, message), args);
		}
	}

	/**
	 * Logs a debug message.
	 *
	 * @param message The log message.
	 * @param args    The message arguments.
	 */
	public void debug(String message, Object... args) {
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE, messages(callingClass, message), args);
		}
	}

	/**
	 * Logs a warning message.
	 *
	 * @param message The log message.
	 * @param args    The message arguments.
	 */
	public void warn(String message, Object... args) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, messages(callingClass, message), args);
		}
	}

	/**
	 * Logs an error message along with an exception.
	 *
	 * @param message   The log message.
	 * @param exception The exception to be logged.
	 * @param args      The message arguments.
	 */
	public void error(String message, Throwable exception, Object... args) {
		if (logger.isLoggable(Level.SEVERE)) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);
			String stackTrace = sw.toString();
			logger.log(Level.SEVERE, messages(callingClass, message) + "\n" + stackTrace, args);
		}
	}
}
