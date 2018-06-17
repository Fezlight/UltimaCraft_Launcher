package net.UltimaCraft.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.UltimaCraft.Util;

public class LoggerU {
	private final static Logger logger = Logger.getLogger(LoggerU.class
			.getName());
	private static FileHandler fh = null;

	public static void init() throws SecurityException, IOException {
		logger.setUseParentHandlers(false);
		Handler[] handlers = logger.getHandlers();
		for (Handler handler : handlers) {
			if (handler.getClass() == ConsoleHandler.class)
				logger.removeHandler(handler);
		}
		fh = new FileHandler(Util.getWorkingDirectory() + File.separator
				+ "launcherLogs.log", false);
		fh.setFormatter(new LogFormatter());
		logger.addHandler(fh);

		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new LogFormatter());
		logger.addHandler(handler);

		logger.setLevel(Level.INFO);
	}
}
