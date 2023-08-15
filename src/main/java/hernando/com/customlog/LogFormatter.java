package hernando.com.customlog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class LogFormatter extends Formatter {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Override
	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder();
		builder.append(dateFormat.format(new Date(record.getMillis())));
		builder.append(" ");
		builder.append(record.getLevel().getName());
		builder.append(": ");
		builder.append(record.getMessage());
		builder.append("\n");
		return builder.toString();
	}
}
