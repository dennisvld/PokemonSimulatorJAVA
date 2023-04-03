package Battle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Logger {
	private BufferedWriter buffWr;


	public Logger(String logType) {
		if (logType != null) {
			try {
				FileWriter fr = new FileWriter(logType, true);
				this.buffWr = new BufferedWriter(fr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			buffWr = new BufferedWriter(new OutputStreamWriter(System.out));
	}

	public void log(String toLog) {
		try {
			buffWr.write(toLog);
			buffWr.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
