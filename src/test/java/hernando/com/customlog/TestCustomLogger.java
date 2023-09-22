package hernando.com.customlog;

import java.lang.reflect.Field;

public class TestCustomLogger {
	public static void configureCustomLoggerForTesting() {
	        // Use reflection to modify the static final properties for testing
	        try {
	            Field defaultLogFilePropsField = CustomLogger.class.getDeclaredField("DEFAULT_LOG_FILE_PROPS");
	            defaultLogFilePropsField.setAccessible(true);
	            defaultLogFilePropsField.set(null, "your-test-properties-file.properties");

	            Field defaultOutputLogFileField = CustomLogger.class.getDeclaredField("DEFAULT_OUTPUT_LOG_FILE");
	            defaultOutputLogFileField.setAccessible(true);
	            defaultOutputLogFileField.set(null, "your-test-output-file.log");
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to configure CustomLogger for testing.", e);
	        }
	    }
}