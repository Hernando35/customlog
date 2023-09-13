package hernando.com.customlog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoggerTest {

	private CustomLogger customLogger;

	@BeforeEach
	public void setup() {
		customLogger = createCustomLoggerInstance("TestLogger");
	}

	private CustomLogger createCustomLoggerInstance(String loggerName) {
		try {
			Constructor<CustomLogger> constructor = CustomLogger.class.getDeclaredConstructor(String.class);
			constructor.setAccessible(true);
			return constructor.newInstance(loggerName);
		} catch (Exception e) {
			fail("Exception occurred while creating CustomLogger instance: " + e.getMessage());
			return null;
		}
	}

	@Test
	public void testConstructorWithValidPropertiesFile() {
		// Create a temporary properties file for testing
		String tempFilePath = "C:\\config\\application.log";
		Properties testProperties = new Properties();
		testProperties.setProperty("logLevel", "INFO");
		testProperties.setProperty("customLogFormatter", "com.log.hernando.LogFormatter");

		try (OutputStream output = new FileOutputStream(tempFilePath)) {
			testProperties.store(output, null);

			// Set the logFilePath to the temporary file path
			customLogger.setLogFileOutPut(tempFilePath);

			// Access and verify the properties of the customLogger
			assertNotNull(customLogger);

			// Cleanup the temporary properties file
			File tempFile = new File(tempFilePath);
			assertFalse(tempFile.delete());
		} catch (IOException e) {
			fail("Exception occurred while testing the constructor: " + e.getMessage());
		}
	}

}
