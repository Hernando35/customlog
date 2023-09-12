package hernando.com.customlog;

/**
 * This interface defines methods for logging messages with different log levels.
 */
interface AppLogger {
    /**
     * Logs an informational message.
     *
     * @param message The message to be logged.
     * @param args    Optional arguments to include in the log message.
     */
    public void info(String message, Object... args);

    /**
     * Logs a debug message.
     *
     * @param message The message to be logged.
     * @param args    Optional arguments to include in the log message.
     */
    public void debug(String message, Object... args);

    /**
     * Logs a warning message.
     *
     * @param message The message to be logged.
     * @param args    Optional arguments to include in the log message.
     */
    public void warn(String message, Object... args);

    /**
     * Logs an error message along with an exception.
     *
     * @param message   The error message to be logged.
     * @param exception The exception to be logged.
     * @param args      Optional arguments to include in the log message.
     */
    public void error(String message, Throwable exception, Object... args);

    /**
     * Sets the path to the log file.
     *
     * @param path The path to the log file.
     */
    public void setLogFilePath(final String path);
}

