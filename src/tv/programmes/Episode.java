package tv.programmes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Episode extends Emission{
	private int season, episode, part;

	/**
	 * Instantiates a new Episode from the String given in the xml (0.0.0/1)
	 * @param number The String in the format given in the xml
	 */
	public Episode(String number, String category, String title, String subtitle, ArrayList<Role> credits, String description, int length, int dateOfRelease, String rating) throws InstantiationException{
		super(category, title, subtitle, credits, description, length, dateOfRelease, rating);
		Pattern pattern = Pattern.compile("^(\\d+)*(?:/*\\d*\\.)(\\d+)*(?:/*\\d*\\.)(\\d+)*");//first capturing group is season number, second is episode number and third is part number
		Matcher matcher = pattern.matcher(number);
		if(matcher.find()){
			if(matcher.start(1) != -1){
				season = Integer.parseInt(matcher.group(1));
			} else {
				season = -1;
			}
			if(matcher.start(2) != -1){
				episode = Integer.parseInt(matcher.group(2));
			} else {
				episode = -1;
			}
			if(matcher.start(3) != -1){
				part = Integer.parseInt(matcher.group(3));
			} else {
				part = -1;
			}
			//System.out.println("season = "+season+" episode = "+episode+" part = "+part+" string = "+number);
		} else {
			throw new InstantiationException("Given String is not a episode number : "+number);
		}
	}


	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Episode)) return false;
		if(!super.equals(o)) return false;

		Episode episode1 = (Episode) o;

		if(season != episode1.season) return false;
		if(episode != episode1.episode) return false;
		return part == episode1.part;
	}


	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + season;
		result = 31 * result + episode;
		result = 31 * result + part;
		return result;
	}
}
