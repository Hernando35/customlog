package hernando.com.customlog;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * A custom file handler for logging, with an option for buffered or immediate
 * writing.
 */
public class CustomFileHandler extends FileHandler {
	private boolean buffered = true; // Default to buffered mode

	/**
	 * Creates a new instance of CustomFileHandler.
	 *
	 * @param logFilePath The path to the log file.
	 * @param formatter   The formatter to format log records.
	 * @throws IOException If an I/O error occurs while creating the log file.
	 */
	public CustomFileHandler(String logFilePath, Formatter formatter) throws IOException {
		super(logFilePath, 6000, 1, true);
		setFormatter(formatter);
	}

	/**
	 * Sets whether the handler should use buffered writing.
	 *
	 * @param buffered If true, log records are buffered for efficient writing; if
	 *                 false, records are written immediately.
	 */
	public void setBuffered(boolean buffered) {
		this.buffered = buffered;
	}

	/**
	 * Publishes a log record.
	 *
	 * @param record The log record to be published.
	 */
	@Override
	public void publish(LogRecord record) {
		if (buffered) {
			super.publish(record);
		} else {
			// If not buffered, write immediately and flush
			super.publish(record);
			flush();
		}
	}
}
