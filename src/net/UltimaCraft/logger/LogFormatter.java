package net.UltimaCraft.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
	public String format(LogRecord rec) {
		StringBuffer buf = new StringBuffer(1000);
		buf.append("[");
		buf.append(calcDate(rec.getMillis()));
		buf.append("][");
		buf.append(rec.getLevel() + "]: ");

		buf.append(formatMessage(rec) + "\n");

		return buf.toString();
	}

	private String calcDate(long millisecs) {
		SimpleDateFormat date_format = new SimpleDateFormat("HH:mm:ss");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}

	public String getHead(Handler h) {
		return "";
	}

	public String getTail(Handler h) {
		return "";
	}
}
