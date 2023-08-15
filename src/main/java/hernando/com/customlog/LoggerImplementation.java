package hernando.com.customlog;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerImplementation implements CustomLogger, Serializable {
	private static final long serialVersionUID = 1L;
	private static final LoggerImplementation INSTANCE = new LoggerImplementation("DefaultLogger");
	private static final String LOG_CONFIG_FILE = "C:/config/logging.properties";

	private final Logger logger;

	private LoggerImplementation(String name) {
		logger = Logger.getLogger(name);
	}

	public static LoggerImplementation getInstance() {
		return INSTANCE;
	}

	@Override
	public void info(String... messages) {
		if (messages != null) {
			String message = String.join(" ", messages);
			logger.log(Level.INFO, message);
		}
	}

	@Override
	public void warning(String message) {

		logger.log(Level.WARNING, message);
	}

	@Override
	public void error(String message, Throwable ex) {
		logger.log(Level.SEVERE, message, ex);
	}


	static {
		loadLoggingProperties();
	}

	private static void loadLoggingProperties() {
		// try (InputStream inputStream =
		// CustomLogger.class.getClassLoader().getResourceAsStream(LOG_CONFIG_FILE)) {
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream(LOG_CONFIG_FILE));
			// LogManager.getLogManager().readConfiguration(inputStream);
			// Set the custom formatter for ConsoleHandler
			ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(new LogFormatter());
			// Set the custom formatter for FileHandler and add it to the root logger
			LogHandler logHandler = new LogHandler();
			logHandler.setFormatter(new LogFormatter());
			LogManager.getLogManager().getLogger("").addHandler(logHandler);
			// Optionally, set the log level for both handlers
			Level logLevel = Level.INFO; // or any desired log level
			consoleHandler.setLevel(logLevel);
			logHandler.setLevel(logLevel);
		} catch (IOException | SecurityException e) {
			e.printStackTrace();
		}
	}
}
