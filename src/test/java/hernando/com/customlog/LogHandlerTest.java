package hernando.com.customlog;


import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.jupiter.api.Test;

public class LogHandlerTest {
	private static final CustomLogger logger = CustomLogger.getInstance();

	@Test
	public void testLogger() {
		logger.info("error messagges");
		LogFormatter formatter = new LogFormatter();
		LogRecord record = new LogRecord(Level.INFO, "Hello my friend");
		assertNotEquals("Test format", formatter.format(record));
	}
}
