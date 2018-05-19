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

}
