package kg.freesms.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String getCurrentDateTime() {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            Date date = new Date();
            return dateFormat.format(date);
	}


}
