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
 * CustomLogger is a class that provides a custom logger implementation for
 * logging messages. It utilizes Java's built-in logging framework to handle log
 * messages at various log levels (INFO, DEBUG, WARNING, ERROR) with
 * customizable log formatting and log file configuration.
 *
 * Usage: 1. Create an instance of CustomLogger using the getInstance method,
 * providing a logger name as an argument. 2. Optionally, configure the logger's
 * log file path and custom log formatter using the setLogFilePath and
 * setCustomLogFormatter methods. 3. Use the provided methods (info, debug,
 * warn, error) to log messages at different log levels.
 *
 * Example: ``` CustomLogger logger = CustomLogger.getInstance("MyLogger");
 * logger.setLogFilePath("C:\\custom\\logging.properties"); logger.info("This is
 * an information message."); logger.error("An error occurred", exception); ```
 *
 * This class has default values for log level, log file path, and log
 * formatter. You can customize these defaults by modifying the constants
 * DEFAULT_LOG_LEVEL, DEFAULT_LOG_FILE_PROPS, and DEFAULT_LOG_FORMATTER.
 *
 * CustomLogger is designed to be a singleton class, ensuring that there is only
 * one instance per logger name. Multiple calls to getInstance with the same
 * logger name will return the same instance.
 *
 * Note: The default log formatter is an instance of LogFormatter, which can be
 * overridden with a custom formatter by providing its fully qualified class
 * name in the "customLogFormatter" property in the logging.properties file.
 *
 * @author Hernando Garcia
 */

public class CustomLogger implements AppLogger {
	private static final Logger logger = Logger.getLogger(CustomLogger.class.getName());
	private static final String DEFAULT_LOG_LEVEL = "ALL";
	private static final String DEFAULT_LOG_FILE_PROPS = "C:\\config\\logging.properties";
	private static final Formatter DEFAULT_LOG_FORMATTER = new LogFormatter();
	private Formatter logFormatter = DEFAULT_LOG_FORMATTER;
	private String logFileOutPut;
	private static CustomLogger instance;
	private String loggerName;

	/**
	 * Constructs a new instance of CustomLogger with the specified logger name.
	 *
	 * @param loggerName The name of the logger.
	 */
	private CustomLogger(String loggerName) {
		this.loggerName = loggerName;
		configureLogger();
	}

	/**
	 * Gets the name of the logger.
	 *
	 * @return The name of the logger.
	 */
	public String getLoggerName() {
		return loggerName;
	}

	/**
	 * Gets the log file output path for the logger.
	 *
	 * @return The path to the log file where log messages are written.
	 */
	public String getLogFileOutPut() {
		return logFileOutPut;
	}

	/**
	 * Sets the name of the logger.
	 *
	 * @param loggerName The new name for the logger.
	 */
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	/**
	 * Configures the logger based on properties defined in the logging.properties
	 * file. This method sets the log level and custom log formatter.
	 */
	private void configureLogger() {
		Properties properties = new Properties();
		try (InputStream input = new FileInputStream(DEFAULT_LOG_FILE_PROPS)) {
			properties.load(input);
			String logLevel = properties.getProperty("logLevel", DEFAULT_LOG_LEVEL);
			logger.setLevel(Level.parse(logLevel));
			String customLogFormatterClassName = properties.getProperty("customLogFormatter");
			setCustomLogFormatter(customLogFormatterClassName);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error occurred while configuring the logger", e);
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
				logger.log(Level.SEVERE, "Error occurred while setting custom log formatter", e);
			}
		}
	}

	/**
	 * Gets an instance of CustomLogger with the specified logger name. This method
	 * ensures that there is only one instance per logger name.
	 *
	 * @param loggerName The name of the logger.
	 * @return An instance of CustomLogger.
	 */
	public static synchronized CustomLogger getInstance(String loggerName) {
		if (instance == null) {
			instance = new CustomLogger(loggerName);
		}
		instance.setLoggerName(loggerName);
		return instance;
	}

	/**
	 * Formats log messages with the logger name and logs an informational message.
	 *
	 * @param message The log message.
	 * @param args    The message arguments.
	 */
	public void info(String message, Object... args) {
		if (logger.isLoggable(Level.INFO)) {
			logger.log(Level.INFO, messages(loggerName, message), args);
		}
	}

	/**
	 * Formats log messages with the logger name and logs a debug message.
	 *
	 * @param message The log message.
	 * @param args    The message arguments.
	 */
	public void debug(String message, Object... args) {
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE, messages(loggerName, message), args);
		}
	}

	/**
	 * Formats log messages with the logger name and logs a warning message.
	 *
	 * @param message The log message.
	 * @param args    The message arguments.
	 */
	public void warn(String message, Object... args) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, messages(loggerName, message), args);
		}
	}

	/**
	 * Formats log messages with the logger name and logs an error message along
	 * with an exception.
	 *
	 * @param message   The log message.
	 * @param exception The exception to be logged.
	 * @param args      The message arguments.
	 */
	public void error(String message, Throwable exception, Object... args) {
		if (logger.isLoggable(Level.SEVERE)) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			StringBuilder sb = new StringBuilder();
			exception.printStackTrace(pw);
			String stackTrace = sw.toString();
			logger.log(Level.SEVERE,
					sb.append(messages(loggerName, message)).append(Constants.ENTER).append(stackTrace).toString(),
					args);
		}
	}

	/**
	 * Formats a log message with the logger name, if provided, and the specified
	 * message.
	 *
	 * @param loggerName The name of the logger, or null if no logger name is
	 *                   specified.
	 * @param message    The log message to be formatted.
	 * @return A formatted log message that includes the logger name (if provided)
	 *         and the message.
	 */
	private String messages(String loggerName, String message) {
		StringBuilder sb = new StringBuilder(Constants.OPEN_BRACKETS);
		String className = loggerName != null ? sb.append(loggerName).append(Constants.CLOSE_BRACKETS).toString() : "";
		return className.concat(message);
	}

	/**
	 * Sets the log file path for the logger. This method allows you to specify the
	 * path to the log file where log messages will be written.
	 *
	 * @param logFilePath The path to the log file.
	 */
	public void setLogFileOutPut(final String logFilePath) {
		this.logFileOutPut = logFilePath;
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
			logger.log(Level.SEVERE, Constants.LOG_EXCEPTION, e);
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
			logger.log(Level.SEVERE, Constants.LOG_EXCEPTION, e);
		}
		return properties;
	}

}
