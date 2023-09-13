package hernando.com.customlog;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
	private static final String OPEN_BRACKETS = "[";
	private static final String CLOSE_BRACKETS = "] ";
	private static final String SPACE = " ";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
     * Formats a log record into a string with a timestamp, log level, and message.
     *
     * @param record The log record to format.
     * @return A formatted log message as a string.
     */
    @Override
	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder();
		Instant instant = Instant.ofEpochMilli(record.getMillis());
		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
		builder.append(OPEN_BRACKETS).append(DATE_FORMATTER.format(zonedDateTime)).append(CLOSE_BRACKETS)
				.append(OPEN_BRACKETS).append(record.getLevel()).append(CLOSE_BRACKETS);
		setLogParameters(builder, record);
		return builder.append("\n").toString();
	}
    
	/**
     * Appends log parameters (variables) to the log message if present.
     *
     * @param builder The StringBuilder to which the log message is appended.
     * @param record  The log record containing the message and parameters.
     */
	private void setLogParameters(StringBuilder builder, LogRecord record) {
		// Check if there are parameters (variables) to include
		Object[] parameters = record.getParameters();
		if (parameters != null && parameters.length > 0) {
			String message = record.getMessage();
			String[] parts = message.split(Constants.SPLIT);
			for (int i = 0; i < parts.length; i++) {
				builder.append(parts[i]);
				if (i < parameters.length) {
					builder.append(parameters[i]);
				}
			}
		} else {
			builder.append(SPACE).append(record.getMessage());
		}
	}
}
