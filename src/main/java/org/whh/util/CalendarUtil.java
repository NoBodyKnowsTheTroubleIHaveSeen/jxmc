package org.whh.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	/**
	 * 设置一天开始
	 * 
	 * @param cal
	 * @return
	 */
	private static Calendar setDayTimeStart(Calendar cal) {
		if (cal != null) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			return cal;
		}
		return null;
	}

	/**
	 * 设置一天结束
	 * 
	 * @param cal
	 * @return
	 */
	private static Calendar setDayTimeEnd(Calendar cal) {
		if (cal != null) {
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			return cal;
		}
		return null;
	}

	/**
	 * 获得一天开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayTimeStart(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			setDayTimeStart(cal);
			return cal.getTime();
		}
		return null;
	}

	/**
	 * 获得一天结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayTimeEnd(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			setDayTimeEnd(cal);
			return cal.getTime();
		}
		return null;
	}

	/**
	 * 获得月初
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			setDayTimeStart(cal);
			return cal.getTime();
		}
		return null;
	}

	/**
	 * 获得月末
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			setDayTimeEnd(cal);
			return cal.getTime();
		}
		return null;
	}

	/**
	 * 获得周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFristDayOfWeek(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
			cal.add(Calendar.DATE, 1);
			setDayTimeStart(cal);
			return cal.getTime();
		}
		return null;
	}

	/**
	 * 获得周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
			cal.add(Calendar.DATE, 1);
			setDayTimeEnd(cal);
			return cal.getTime();
		}
		return null;
	}

	public static String formateDate(Date date) {
		return formateDate(date, null);
	}

	public static String formateDate(Date date, String formate) {
		if (date == null) {
			return null;
		}
		if (formate == null) {
			formate = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat format = new SimpleDateFormat(formate);
		String result = format.format(date);
		return result;
	}
}
