package tv.programmes.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Date implements Comparable{
	private int year, month, day, hour, minute, second;

	/**
	 * Instantiates a Date from the date in the format found in the xml
	 * @param date the date in the format of the xml
	 */
	public Date(String date) throws InstantiationException{
		Pattern pattern  = Pattern.compile("^(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})");
		Matcher matcher = pattern.matcher(date);
		if(matcher.find()){
			year = Integer.parseInt(matcher.group(1));
			month = Integer.parseInt(matcher.group(2));
			day = Integer.parseInt(matcher.group(3));
			hour = Integer.parseInt(matcher.group(4));
			minute = Integer.parseInt(matcher.group(5));
			second = Integer.parseInt(matcher.group(6));
		} else {
			throw new InstantiationException("Given String is not a date : "+date);
		}
	}

	public Date(){

	}


	@Override
	public int compareTo(Object o) {
		if(!this.getClass().isInstance(o)){//If o is not a Date
			throw new IllegalArgumentException("Argument is not a Date");
		}

		Date arg = (Date) o;

		if(arg.year<this.year){
			return 1;
		} else if(arg.year>this.year){
			return -1;
		} else {
			if(arg.month<this.month){
				return 1;
			} else if(arg.month>this.month){
				return -1;
			} else {
				if(arg.day<this.day){
					return 1;
				} else if(arg.day>this.day){
					return -1;
				} else {
					if(arg.hour<this.hour){
						return 1;
					} else if(arg.hour>this.hour){
						return -1;
					} else {
						if(arg.minute<this.minute){
							return 1;
						} else if(arg.minute>this.minute){
							return -1;
						} else {
							if(arg.second<this.second){
								return 1;
							} else if(arg.second>this.second){
								return -1;
							} else {
								return 0;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		return	year +
				"/" + month +
				"/" + day +
				" " + hour +
				":" + minute +
				":" + second;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Date date = (Date) o;

		if (year != date.year) return false;
		if (month != date.month) return false;
		if (day != date.day) return false;
		if (hour != date.hour) return false;
		if (minute != date.minute) return false;
		return second == date.second;
	}

	@Override
	public int hashCode() {
		int result = year;
		result = 31 * result + month;
		result = 31 * result + day;
		result = 31 * result + hour;
		result = 31 * result + minute;
		result = 31 * result + second;
		return result;
	}

	public String getPrintableDay(){
		return year + "/"+ month + "/" + day;
	}

	public boolean isSameDay(Date that){
		if(year != that.year) return false;
		if(month != that.month) return false;
		return day == that.day;
	}

	public Date copyDay(){
		Date date = new Date();
		date.year = year;
		date.month = month;
		date.day = day;
		date.hour = 0;
		date.minute = 0;
		date.second = 0;
		return date;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public void setSecond(int second) {
		this.second = second;
	}
}
