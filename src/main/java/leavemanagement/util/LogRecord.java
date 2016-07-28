package leavemanagement.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LogRecord {
	public static Logger logger = Logger.getLogger(LogRecord.class);
	static{
		Logger.getRootLogger().setLevel(Level.DEBUG);
	}
}
