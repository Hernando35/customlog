package hernando.com.customlog;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.text.DateFormat.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyLoggerTest {

	 private CustomLogger customLogger;

    @BeforeAll
    public static void configureLoggerForTesting() {
        // Use reflection to modify the static final properties for testing
        try {
            java.lang.reflect.Field defaultLogFilePropsField = CustomLogger.class.getDeclaredField("DEFAULT_LOG_FILE_PROPS");
            defaultLogFilePropsField.setAccessible(true);
            defaultLogFilePropsField.set(null, "src/test/resources/logging.properties");
        } catch (Exception e) {
           // throw new RuntimeException("Failed to configure CustomLogger for testing.", e);
        }
    }

    @BeforeEach
    public void setUp() {
        customLogger = CustomLogger.getInstance();
        System.out.println(customLogger.getLogFileOutPut());
    }

    @Test
    public void testInfoLog() {
        customLogger.info("This is an info message");

        // You can add assertions here to check if the logger behavior matches expectations.
        // For example, you can check if the log file contains the expected message.
        // You'll need to read the log file contents and perform assertions accordingly.
        // Here, we're just testing if the info message was logged without errors.
    }
 }









