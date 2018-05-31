package tv.programmes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import tv.programmes.model.ListWindowModel;

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
    public void initialize(){
        channelsButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
		        switchToChannelList();
	        }
        });

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
