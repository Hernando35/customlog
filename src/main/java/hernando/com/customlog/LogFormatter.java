package hernando.com.customlog;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
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
		builder.append(Constants.OPEN_BRACKETS).append(DATE_FORMATTER.format(zonedDateTime))
				.append(Constants.CLOSE_BRACKETS).append(Constants.OPEN_BRACKETS).append(record.getLevel())
				.append(Constants.CLOSE_BRACKETS);

		setLogParameters(builder, record);
		return builder.append(Constants.ENTER).toString();
	}

	/**
	 * Appends log parameters (variables) to the log message if present.
	 *
	 * @param builder The StringBuilder to which the log message is appended.
	 * @param record  The log record containing the message and parameters.
	 */
	private void setLogParameters(StringBuilder builder, LogRecord record) {
		Object[] parameters = record.getParameters();
		String message = record.getMessage();

		if (parameters != null && parameters.length > 0 && message.contains("{}")) {
			appendParametersWithPlaceholders(builder, message, parameters);
		} else if (parameters != null && parameters.length == 1 && !message.contains("{}")) {
			appendSingleParameter(builder, message, parameters[0]);
		} else {
			appendDefaultMessage(builder, message);
		}
	}

	/**
	 * Appends parameters to the log message with placeholders.
	 *
	 * @param builder    The StringBuilder to which the log message is appended.
	 * @param message    The log message.
	 * @param parameters The message parameters.
	 */
	private void appendParametersWithPlaceholders(StringBuilder builder, String message, Object[] parameters) {
		String[] parts = message.split(Constants.SPLIT);
		for (int i = 0; i < parts.length; i++) {
			builder.append(parts[i]);
			if (i < parameters.length) {
				builder.append(parameters[i]);
			}
		}
		if (parameters.length > parts.length) {
			builder.append(SPACE).append(Constants.OPEN_BRACKETS).append(parameters[parts.length])
					.append(Constants.CLOSE_BRACKETS);
		}
	}

	/**
	 * Appends a single parameter to the log message without placeholders.
	 *
	 * @param builder   The StringBuilder to which the log message is appended.
	 * @param message   The log message.
	 * @param parameter The single message parameter.
	 */
	private void appendSingleParameter(StringBuilder builder, String message, Object parameter) {
		builder.append(message).append(SPACE).append(Constants.OPEN_BRACKETS).append(parameter)
				.append(Constants.CLOSE_BRACKETS);
	}

	/**
	 * Appends the default log message.
	 *
	 * @param builder The StringBuilder to which the log message is appended.
	 * @param message The log message.
	 */
	private void appendDefaultMessage(StringBuilder builder, String message) {
		builder.append(SPACE).append(message);
	}
}