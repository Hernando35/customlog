package hernando.com.customlog;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public final class LogHandler extends Handler {
	private FileHandler fileHandler;

	public LogHandler() {
		try {
			// Set the file path for log files and other configuration options
			fileHandler = new FileHandler("C:/logs/application.log", true);
			fileHandler.setFormatter(new LogFormatter());
			// setting custom filter for FileHandler
			fileHandler.setFilter(new LogFilter());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(LogRecord record) {
		// Implement your custom logging behavior here
		// For example, you can log the record to a database, send it to an external
		// service, etc.

		// Also, log the record to the file using the FileHandler
		if (fileHandler != null) {
			fileHandler.publish(record);
		}
	}

	@Override
	public void flush() {
		// Optionally, implement any flushing behavior if required
		if (fileHandler != null) {
			fileHandler.flush();
		}
	}

	@Override
	public void close() throws SecurityException {
		// Optionally, implement any cleanup logic if required
		if (fileHandler != null) {
			fileHandler.close();
		}
	}
}