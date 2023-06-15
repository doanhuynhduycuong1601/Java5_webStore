package cuong.run.utils;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class GetString {
	static DecimalFormat decimalFormat = new DecimalFormat("###,###");
	
	public static String getVnd(Double price) {
		return decimalFormat.format(price) + " vnđ";
	}
	
	
	public static String getDate(LocalDateTime localDateTime) {
		return localDateTime.getYear()+"/"+localDateTime.getMonthValue()+"/"+localDateTime.getDayOfMonth();
	}
	
	public static String getDateTime(LocalDateTime localDateTime) {
		return localDateTime.getYear()+"/"+localDateTime.getMonthValue()+"/"+localDateTime.getDayOfMonth()
		+". Thời gian : "+localDateTime.getHour()+":"+localDateTime.getMinute()+"/"+localDateTime.getSecond();
	}
}
