package tv.programmes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import tv.programmes.model.ListWindowModel;

import java.lang.management.MemoryUsage;
import java.util.ArrayList;

public class ListWindowController extends Controller {
	ListWindowModel model;

    @FXML
    private ListView<String> list;
	@FXML
	private MenuItem channelsButton;
	@FXML
	private MenuItem calendarButton;
	@FXML
	private MenuItem actorButton;
	@FXML
	private MenuItem categorieButton;
	@FXML
	private MenuItem ratingButton;
	@FXML
	private MenuItem orderedChannelButton;



    @FXML
    public void initialize(){
        channelsButton.setOnAction(event -> switchToChannelList());
        calendarButton.setOnAction(event -> switchToCalendar());


    }

	@Override
    public void updateScene(){
	    ObservableList<String> data = FXCollections.observableArrayList();
	    data.addAll(model.getDataList());
	    list.setItems(data);
    }

	public ListWindowModel getModel() {
		return model;
	}

	public void setModel(ListWindowModel model) {
		this.model = model;
	}
}
