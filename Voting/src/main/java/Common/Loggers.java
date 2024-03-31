package Common;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Loggers {
	Logger logger;
	
	public <T> Loggers(Class<T> cls) {
		logger = Logger.getLogger(cls);
		PropertyConfigurator.configure("/home/ragavi-zstk352/log4.properties");
	}
	
	public Logger getLogger() {
		return logger;
	}

}