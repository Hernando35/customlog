package hernando.com.customlog;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class LogFilter implements Filter {
	@Override
	public boolean isLoggable(LogRecord record) {
		// Implement custom filtering logic here
		return true; // Example: Always log everything } }
	}
}
