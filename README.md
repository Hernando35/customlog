# customlog
This is an sample project which extends the java.util.logging library. Since the Log4j vulnerabilities in 2021, IBM Content Navigator doesn't allow to Custom ICN plugins to load the Logger classes from this framework. This means it is not possible to write logs on a specific place in case of debugging of tracing the custom plugins activities. 

# Usage
Just add to you Custom class this line: private static final CustomLogger log = LoggerImplementation.getInstance();

Then use what you want to log:
                                log.info("initializing service")
