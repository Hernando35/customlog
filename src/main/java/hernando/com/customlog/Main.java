package hernando.com.customlog;


public class Main {
	private static final CustomLogger log = LoggerImplementation.getInstance();

	public static void main(String[] args) {
		String value = "bbq";
		log.info("info message:", value);
		testen(null);
	}

	private static void testen(String value) {
		try {
			String vv = value.concat("doe");
			log.info(vv);
		} catch (Exception e) {
			log.error(value, e);
			throw e;
		}
	}
}
