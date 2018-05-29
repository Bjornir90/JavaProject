package tv.programmes.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import tv.programmes.Channel;


public class MainWindowController extends Controller{

	private EventHandler channelButtonHandler;

	@FXML
	private MenuItem channelsButton;
	@FXML
	private MenuItem calendarButton;
	@FXML
	private GridPane gridChannel;

	@FXML
	private void testButton(ActionEvent event){
		System.out.println("Button was clicked : "+channelsButton.getText());
	}

	private void initializeHandlers(){

		channelButtonHandler = new EventHandler() {
			@Override
			public void handle(Event event) {
				Button clickedOn = (Button) event.getSource();
				String buttonText = clickedOn.getText();

				for(Channel c : app.getChannels()){
					if(c.getName().equals(buttonText)){
                        switchToChannelScene(c, clickedOn.getScene().getWindow());
					}
				}

			}
		};


	}

	@FXML
	protected void initialize(){
		initializeHandlers();
		int rowIndex = 0;
		for(Channel c : app.getChannels()){
			Button channelButton = new Button(c.getName());
			gridChannel.add(channelButton, 0, rowIndex);
			rowIndex++;
		}
	}

    /**
     * Switch the active scene to the channel information display
     * @param channel The channel to display
     * @param window The window of the application
     */
    public void switchToChannelScene(Channel channel, Window window){
        Stage stage = (Stage) window;
        stage.setScene(app.getScenes().get("List"));
    }
}
