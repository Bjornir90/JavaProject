package tv.programmes.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import tv.programmes.Channel;
import tv.programmes.Programmation;

import java.util.ArrayList;


public class MainWindowController extends Controller{

	private EventHandler channelButtonHandler;

	@FXML
	private MenuItem channelsButton;
	@FXML
	private MenuItem calendarButton;
	@FXML
	private GridPane gridChannel;
	@FXML
	private MenuItem actorButton;
	@FXML
	private MenuItem categorieButton;
	@FXML
	private MenuItem ratingButton;
	@FXML
	private MenuItem orderedChannelButton;
	@FXML
	private MenuItem creditButton;
	@FXML
	private MenuItem periodButton;


	/**
	 * Set the event handler for every buttons in the interface.
	 */
	private void initializeHandlers(){

		channelButtonHandler = event -> {
			Button clickedOn = (Button) event.getSource();
			String buttonText = clickedOn.getText();
			for(Channel c : app.getChannels()){
				if(c.getName().equals(buttonText)){
					switchToChannelScene(c);
				}
			}

		};

		channelsButton.setOnAction(event -> switchToChannelList());
		calendarButton.setOnAction(event -> switchToCalendar());
		actorButton.setOnAction(event -> switchToActorList());
		ratingButton.setOnAction(event -> switchToRatingList());
		orderedChannelButton.setOnAction(event -> switchToSortedChannelList());
		creditButton.setOnAction(event -> createPopupCredit());
		periodButton.setOnAction(event -> createPopupPeriod());

	}

	@FXML
	/**
	 * Calls initializeHandlers();
	 * Populates the gridPane with a button for each channel.
	 */
	public void initialize(){
		if(app != null) {
			initializeHandlers();
			int rowIndex = 0;
			for (Channel c : app.getChannels()) {
				Button channelButton = new Button(c.getName());
				channelButton.setOnMouseClicked(channelButtonHandler);
				gridChannel.add(channelButton, 0, rowIndex);
				rowIndex++;
			}

		}
	}

    /**
     * Switch the active scene to the channel information display
     * @param channel The channel to display
     */
    public void switchToChannelScene(Channel channel){
        Stage stage = app.getRoot();
        Scene sceneToSwitch = app.getScenes().get("List");
        stage.setScene(sceneToSwitch);
        app.setCurrentController(app.getControllers().get("List"));

        ArrayList<String> dataToPass = new ArrayList<>();
        for(Programmation prog : channel.getProgrammations()){
        	dataToPass.add(prog.shortDescription());
	        System.out.println(prog.shortDescription());
        }
	    ((ListWindowController) app.getCurrentController()).getModel().setDataList(dataToPass);
        stage.show();
    }

	@Override
	public void updateScene() {

	}
}
