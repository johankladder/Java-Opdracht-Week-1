package nl.hanze.web.t41.http;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public final class HTTPSettings {
	// ZET HIER DE JUISTE DIRECTORY IN WAAR JE BESTANDEN STAAN.
	
	static public String DOC_ROOT = System.getProperty("user.dir");
	static final String FILE_NOT_FOUND = "";
	
	static final int BUFFER_SIZE = 2048;	
	static final int PORT_MIN=0;
	static final int PORT_MAX=65535;
	
	static public int PORT_NUM = 4444;
	static final HashMap<String, String> dataTypes = new HashMap<String, String>();	

	static final String[] DAYS = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
	public static void setPort(String[] args) {
		if(args.length > 0) {
			PORT_NUM = Integer.parseInt(args[0]);
		}
		// Else use the default.
	}

	public static String getDate() {
		GregorianCalendar cal = new GregorianCalendar();
		String rv = "";
		rv += DAYS[cal.get(Calendar.DAY_OF_WEEK) - 1] + ", ";
		rv += cal.get(Calendar.DAY_OF_MONTH) + " " + MONTHS[cal.get(Calendar.MONTH)];
		rv += " " + cal.get(Calendar.YEAR) + "\r\n";

		return rv;
	}

	public static void setDocumentRoot(String[] args) {
		if(args.length > 1) {
			DOC_ROOT = args[1];
		}
		// Else use the default.
	}
}
