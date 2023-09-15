# customlog
This is an sample project which extends the java.util.logging library. Since the Log4j vulnerabilities in 2021, IBM Content Navigator doesn't allow to Custom ICN plugins to load the Logger classes from this framework. This means it is not possible to write logs on a specific place in case of debugging of tracing the custom plugins activities. 
This framework provides a custom logger implementation for logging messages. It utilizes Java's built-in logging framework to handle log messages at various log levels (INFO, DEBUG, WARNING, ERROR) with customizable log formatting and log file configuration.                                
                               \
## Usage

      1. Create an instance of CustomLogger using the `getInstance` method, providing a logger name as an argument.
	  2. Optionally, configure the logger's log file output path and custom log formatter using the `setLogFileOutput` and `setCustomLogFormatter` methods.
	  3. Use the provided methods (`info`, `debug`, `warn`, `error`) to log messages at different log levels.

### Example

	  ```java
	  AppLogger logger = CustomLogger.getInstance();
      logger.setLogFileOutput("C:\\custom\\logs.log");
      logger.info("This is an information message.");
      logger.error("An error occurred", exception);

