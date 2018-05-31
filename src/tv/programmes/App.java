package tv.programmes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tv.programmes.controller.Controller;
import tv.programmes.controller.ListWindowController;
import tv.programmes.model.ListWindowModel;
import tv.programmes.utils.Parser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class App {

    private static final String viewFolder = "/tv/programmes/controller/";
    private ArrayList<Channel> channels;
    private ArrayList<Programmation> programmations;
    private ArrayList<Emission> emissions;
    private HashMap<String, Scene> scenes;
    private HashMap<String, Controller> controllers;
    private Controller currentController;
    private Stage root;


    public App (){
        channels = new ArrayList<>();
        programmations = new ArrayList<>();
        emissions = new ArrayList<>();
        scenes = new HashMap<>();
        controllers = new HashMap<>();

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
                                    p = Parser.parseEmission(xmlReader, channels);
                                    if(!emissions.contains(p.getEmission())) {
                                        emissions.add(p.getEmission());
                                    }
                                    p.getChannel().addProgrammation(p);
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
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent parent = loader.load(getClass().getResource(viewFolder+"ListWindow.fxml").openStream());
            ListWindowController listController = loader.getController();
            listController.setApp(this);
            listController.setModel(new ListWindowModel(listController));
            Scene listScene = new Scene(parent);
            controllers.put("List", listController);
            scenes.put("List", listScene);
        } catch (IOException e) {
            System.err.println("App loading error : could not find fxml file");
            e.printStackTrace();
            System.exit(1);//It is a fatal error, better to shutdown now than have null references later
        }
    }

    public static String getViewFolder() {
        return viewFolder;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public ArrayList<Programmation> getProgrammations() {
        return programmations;
    }

    public ArrayList<Emission> getEmissions() {
        return emissions;
    }

    public HashMap<String, Scene> getScenes() {
        return scenes;
    }

	public HashMap<String, Controller> getControllers() { return controllers; }

	public Controller getCurrentController() {
        return currentController;
    }

    public void setCurrentController(Controller currentController) {
        this.currentController = currentController;
    }

	public Stage getRoot() {
		return root;
	}

	public void setRoot(Stage root) {
		this.root = root;
	}
}
