package tv.programmes;

import tv.programmes.utils.Date;

public class Programmation {
	private Date startDate, endDate;
	private Emission emission;
	private Channel channel;

	public Programmation(Date startDate, Date endDate, Emission emission, Channel channel) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.emission = emission;
		this.channel = channel;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate(){
		return endDate;
	}

	public Emission getEmission() {
		return emission;
	}

	public Channel getChannel() {
		return channel;
	}

	/**
	 * Creates a short description of the programmation, for easy displaying.
	 * @return the description
	 */
	public String shortDescription(){
		String desc = emission.shortDescription();
		desc = "Start :" +startDate.toString() +" End : "+ endDate.toString() +" "+ desc;
		return desc;
	}
}
