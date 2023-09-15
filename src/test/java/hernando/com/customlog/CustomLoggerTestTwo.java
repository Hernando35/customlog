package hernando.com.customlog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

 class CustomLoggerTestTwo {
	@TempDir
	static Path tempDir;

	private CustomLogger logger;

	@BeforeEach
	void setUp() {
		// Initialize your CustomLogger instance here
		logger = CustomLogger.getInstance();
		logger.setLogFileOutPut("C:\\config\\application.log");
	}

	@Test
	void testInfoLogging() {
		logger.info("This is an info message two.");
		
	}

	@Test
	void testDebugLogging() {
		logger.debug("This is a debug message two.");
		// Add assertions here to verify the log content or file contents
	}

	@Test
	void testWarnLogging() {
		logger.warn("This is a warning message.");
		// Add assertions here to verify the log content or file contents
	}

	@Test
	void testErrorLogging() {
		logger.error("This is an error message.", new Exception("Test exception"));
		// Add assertions here to verify the log content or file contents
	}

	@Test
	void testLoadProperties() {
		// Test loading properties from a test properties file
		logger.setLogFileOutPut("C:\\config\\application.log");
		Properties properties = logger.loadProperties("C:\\config\\logging.properties");
		properties.setProperty("logLevel", "ALL");
		String level = properties.getProperty("logLevel");

		System.out.println(level);
		// }
		assertNotNull(properties);
		assertEquals("ALL", level);
	}

	@Test
	void testSetLogFilePath() throws IOException {
		String newLogFilePath = tempDir.resolve("C:\\config\\application.log").toString();

		// Set a new log file path
		logger.setLogFileOutPut(newLogFilePath);

		// Verify that the log file path has been updated and a new log file has been
		// created
		// assertEquals(newLogFilePath, logger.getLogFilePath(newLogFilePath));
		assertTrue(tempDir.resolve("C:\\config\\application.log").toFile().exists());
	}
}

