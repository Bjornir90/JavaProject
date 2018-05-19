package tv.programmes;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import tv.programmes.utils.Parser;


public class ApplicationMain extends Application {
	private static final String viewFolder = "/tv/programmes/controller/";
	public static ArrayList<Channel> channels;
	public static ArrayList<Programmation> programmations;
	public static ArrayList<Emission> emissions;
	private static HashMap<String, Parent> scenes;

	static{
		channels = new ArrayList<>();
		programmations = new ArrayList<>();
		emissions = new ArrayList<>();
		scenes = new HashMap<>();
	}

	public static void main(String[] args){
		XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
		XMLStreamReader xmlReader = null;
		try {
			xmlReader = xmlFactory.createXMLStreamReader(new FileReader("tvguide.xml"));
		} catch(XMLStreamException e){
			System.err.println("Error while opening xml file : ");
			e.printStackTrace(System.err);
			System.exit(-1);
		} catch(FileNotFoundException e){
			//TODO prompt for new file name
			System.err.println("Error file not found : ");
			e.printStackTrace(System.err);
			System.exit(-1);
		}


		int event;
		try {
			while(xmlReader.hasNext()) {

				event = xmlReader.next();

				switch(event) {
					case XMLEvent.START_ELEMENT:
						String elementName = xmlReader.getLocalName();

						switch(elementName) {
							case "programme":
								Programmation p = null;
								try {
									p = Parser.parseEmission(xmlReader);
									if(!emissions.contains(p.getEmission())) {
										emissions.add(p.getEmission());
									}
								} catch (InstantiationException e){
									e.printStackTrace();
									break;
								}
								programmations.add(p);
								break;
							case "channel":
								Channel c = null;
								try {
									c = Parser.parseChannel(xmlReader);
								} catch(XMLParsingException e){
									e.printStackTrace();
									break;
								}
								if(c != null) {
									channels.add(c);
								}
								break;
							default:
								break;
						}

						break;
					default:
						break;
				}
			}
		} catch(XMLStreamException e) {
			System.err.println("Error reading xml file : ");
			e.printStackTrace(System.err);
			System.exit(-1);
		}
		System.out.println("channelsSize = " + channels.size());
		System.out.println("programmationsSize = " + programmations.size());
		System.out.println("emissionsSize = " + emissions.size());
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		String fxmlResource = "/tv/programmes/controller/MainWindow.fxml";
		Parent panel;

//		panel = FXMLLoader.load(getClass().getResource(viewFolder+"ListWindow.fxml"));
////		scenes.put("List", panel);
////
////		panel = FXMLLoader.load(getClass().getResource(viewFolder+"SingleItemWindow.fxml"));
////		scenes.put("SingleItem", panel);

		panel = FXMLLoader.load(getClass().getResource(fxmlResource));
		scenes.put("MainWindow", panel);


		Scene scene = new Scene(panel);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Switch the active stage to the channel information display
	 * @param channel The channel to display
	 */
	public void switchToChannelScene(Channel channel) throws Exception{

	}
}
