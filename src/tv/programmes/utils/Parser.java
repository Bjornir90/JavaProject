package tv.programmes.utils;

import tv.programmes.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;

public class Parser {

	public static Programmation parseEmission(XMLStreamReader reader, ArrayList<Channel> channels) throws XMLStreamException, InstantiationException{
		String category = null;
		String title = null;
		String subtitle = null;
		ArrayList<Role> credits = new ArrayList<>();//keys are role and values are name of person
		String description = null;
		int length = 0, dateOfRelease = 0;
		String rating = null;
		boolean isSerie = false;
		String elementName, episodeNumberString = null;
		int event;
		String dateStartString = reader.getAttributeValue(null, "start");
		String dateEndString = reader.getAttributeValue(null, "stop");
		String channel = reader.getAttributeValue(null, "channel");
		Date startDate = new Date(dateStartString);
		Date endDate = new Date(dateEndString);

		while(reader.hasNext()){
			event = reader.next();
			if(event == XMLEvent.START_ELEMENT) {
				elementName = reader.getLocalName();
				reader.next();
				switch(elementName) {
					case "category":
						category = reader.getText();
//						System.out.println("category = " + category);
						break;
					case "title":
						title = reader.getText();
//						System.out.println("title = " + title);
						break;
					case "sub-title":
						subtitle = reader.getText();
//						System.out.println("subtitle = " + subtitle);
						break;
					case "desc":
						description = reader.getText();
//						System.out.println("description = " + description);
						break;
					case "date":
						String dateString = reader.getText();//Year of release
//						System.out.println(dateString);
						dateOfRelease = Integer.parseInt(dateString);
						break;
					case "length":
						length = Integer.parseInt(reader.getText());
//						System.out.println("length = " + length);
						break;
					case "episode-num":
						isSerie = true;
						episodeNumberString = reader.getText();
						break;
					case "credits":
						event = reader.next();
						while(!reader.getLocalName().equals("credits")){
							String job = null, name = null;
							job = reader.getLocalName();
							event = reader.next();
							name = reader.getText();
							if(name.contains("("))	name = name.substring(0, name.indexOf("("));
							event = reader.next();//end element
							event = reader.next();//freaking characters for some reason ??
							event = reader.next();//start element

							System.out.println("job = " + job);
							System.out.println("name = " + name);
							credits.add(new Role(name, job));
						}
						/*while(reader.hasNext()){
							if(event == XMLEvent.START_ELEMENT){
								System.out.println("Start reader.getLocalName() = " + reader.getLocalName());
							} else if(event == XMLEvent.CHARACTERS){
								System.out.println("reader.getText() = " + reader.getText());
							} else if(event == XMLEvent.END_ELEMENT){
								if(reader.getLocalName().equals("credits")) break;
								System.out.println("End reader.getLocalName() = " + reader.getLocalName());
							}
							event = reader.next();
						}*/
						break;
					default:
						break;
				}
			} else if(event == XMLEvent.END_ELEMENT){
				if(reader.getLocalName().equals("programme")){
					break;//we finished for this programmation
				}
			}
		}
		Channel channelInstance = null; //The instance of the channel for this programmation
		for (Channel c : channels){
			if(c.getId().equals(channel)){
				channelInstance = c;
				break;
			}
		}
		if(isSerie){
			Episode episode =  new Episode(episodeNumberString, category, title, subtitle, credits, description, length, dateOfRelease, rating);
			return new Programmation(startDate, endDate, episode, channelInstance);
		}
		Emission emission =  new Emission(category, title, subtitle, credits, description, length, dateOfRelease, rating);
		return new Programmation(startDate, endDate, emission, channelInstance);
	}

	public static Channel parseChannel(XMLStreamReader reader) throws XMLParsingException, XMLStreamException {
		int event = reader.getEventType();
		String id;
		String name = null;

		id = reader.getAttributeValue(null, "id");
		if(id == null) {
			System.err.println("Error parsing xml : missing id attribute");
			throw new XMLParsingException("XML parsing error : missing critical parameter");
		}

		while(!(event == XMLEvent.END_ELEMENT && reader.getLocalName().equals("channel"))){//while we are not out of the channel balise
			event = reader.next();
			if(event == XMLEvent.START_ELEMENT && reader.getLocalName().equals("display-name")){//find the display name balise
				reader.next();
				name = reader.getText();
			}
		}

		if(name == null){
			System.err.println("Error parsing xml : name balise");
			throw new XMLParsingException("XML parsing error : missing critical parameter");
		}
		Channel c = new Channel(id, name);
		if(c == null){
			System.err.println("Error, channel is null");
		}
		return c;
	}

}
