package nl.hanze.web.t41.http;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public final class HTTPSettings {
	// ZET HIER DE JUISTE DIRECTORY IN WAAR JE BESTANDEN STAAN.
	
	static final String DOC_ROOT = "";
	static final String FILE_NOT_FOUND = "";
	
	static final int BUFFER_SIZE = 2048;	
	static final int PORT_MIN=0;
	static final int PORT_MAX=65535;
	
	static final public int PORT_NUM = 4444;
	static final HashMap<String, String> dataTypes = new HashMap<String, String>();	

	static final String[] DAYS = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
	
	
	public static String getDate() {
		GregorianCalendar cal = new GregorianCalendar();
		String rv = "";
		rv += DAYS[cal.get(Calendar.DAY_OF_WEEK) - 1] + ", ";
		rv += cal.get(Calendar.DAY_OF_MONTH) + " " + MONTHS[cal.get(Calendar.MONTH)];
		rv += " " + cal.get(Calendar.YEAR) + "\r\n";
		
		return rv;
	}
}
