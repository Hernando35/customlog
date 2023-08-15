package hernando.com.customlog;

public interface CustomLogger {
	void info(String... messages);
	void warning(String message);
	void error(String message, Throwable e);
}
