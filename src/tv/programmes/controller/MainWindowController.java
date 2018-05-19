package tv.programmes.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import tv.programmes.ApplicationMain;
import tv.programmes.Channel;

import java.util.Iterator;

public class MainWindowController {

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

				for(Channel c : ApplicationMain.channels){
					if(c.getName().equals(buttonText)){

					}
				}

			}
		};


	}

	@FXML
	private void initialize(){
		initializeHandlers();
		int rowIndex = 0;
		for(Channel c : ApplicationMain.channels){
			Button channelButton = new Button(c.getName());
			gridChannel.add(channelButton, 0, rowIndex);
			rowIndex++;
		}
	}
}
