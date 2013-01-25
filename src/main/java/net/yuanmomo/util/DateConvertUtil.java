package net.yuanmomo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertUtil {
	private static String dateFormat="yyyy-MM-dd HH:mm:ss"; //2012-08-22 10:22:28
	private static SimpleDateFormat format=new SimpleDateFormat(dateFormat);
	
	public static String dateToString(Date date){
		return format.format(date);
	}
	public static Date stringToDate(String value) throws ParseException{
		return format.parse(value);
	}
}
