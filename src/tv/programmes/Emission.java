package tv.programmes;

import java.util.ArrayList;

public class Emission {

	private String category;
	private String title;
	private String subtitle;
	private ArrayList<Role> credits;
	private String description;
	private int length;
	private int yearOfRelease;
	private String rating;


	public Emission(String category, String title, String subtitle, ArrayList<Role> credits, String description, int length, int yearOfRelease, String rating) {
		this.category = category;
		this.title = title;
		this.subtitle = subtitle;
		this.credits = credits;
		this.description = description;
		this.length = length;
		this.yearOfRelease = yearOfRelease;
		this.rating = rating;
	}

	public String getCategory() {
		return category;
	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public ArrayList<Role> getCredits() {
		return credits;
	}

	public String getDescription() {
		return description;
	}

	public int getLength() {
		return length;
	}

	public int getYearOfRelease() {
		return yearOfRelease;
	}

	public String getRating() {
		return rating;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Emission)) return false;

		Emission emission = (Emission) o;

		if(length != emission.length) return false;
		if(yearOfRelease != emission.yearOfRelease) return false;
		if(category != null ? !category.equals(emission.category) : emission.category != null) return false;
		if(title != null ? !title.equals(emission.title) : emission.title != null) return false;
		if(subtitle != null ? !subtitle.equals(emission.subtitle) : emission.subtitle != null) return false;
		if(credits != null ? !credits.equals(emission.credits) : emission.credits != null) return false;
		if(description != null ? !description.equals(emission.description) : emission.description != null) return false;
		return rating != null ? rating.equals(emission.rating) : emission.rating == null;
	}

	@Override
	public int hashCode() {
		int result = category != null ? category.hashCode() : 0;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (subtitle != null ? subtitle.hashCode() : 0);
		result = 31 * result + (credits != null ? credits.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + length;
		result = 31 * result + yearOfRelease;
		result = 31 * result + (rating != null ? rating.hashCode() : 0);
		return result;
	}

	/**
	 * Creates a short description of the emission, for easy displaying.
	 * @return the description
	 */
	public String shortDescription(){
		return title + " Duration : " + length;
	}
}
