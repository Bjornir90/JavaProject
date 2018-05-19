package tv.programmes;

import java.awt.color.ICC_ProfileRGB;
import java.util.ArrayList;

public class Channel {
	private String id;
	private String name;
	private ArrayList<Programmation> programmations;


	public Channel(String id, String name){
		this.id = id;
		this.name = name;
		this.programmations = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Programmation> getProgrammations() {
		return programmations;
	}

	public void addProgrammation(Programmation prog){
		programmations.add(prog);
	}
}
