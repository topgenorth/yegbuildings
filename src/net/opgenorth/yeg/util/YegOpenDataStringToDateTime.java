package net.opgenorth.yeg.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YegOpenDataStringToDateTime implements ITransmorgifier<String, Date> {
	// Format of the YEG timestamp string: 2010-01-12T02:21:58.2675623Z
	// Right now, we don't really care about the time, just the actual date.
	public static final String YEG_DATE_FORMAT = "yyyy-MM-dd";
	private static SimpleDateFormat _formatter = new SimpleDateFormat(YEG_DATE_FORMAT);
	@Override
	public Date transmorgify(String dateString) {
		if ((dateString == null) || (dateString.trim().length() == 0)) {
			return null;
		}
		try {
			Date date =  _formatter.parse(dateString);
			return date;
		}
		catch (ParseException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			return null;
		}
	}
}
