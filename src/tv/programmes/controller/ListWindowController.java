package tv.programmes.controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class ListWindowController extends Controller {

    @FXML
    private MenuItem channelsButton;
    @FXML
    private MenuItem calendarButton;
    @FXML
    private ListView<String> list;

    @FXML
    public void initialize(){
        ObservableList<String> data = FXCollections.observableArrayList();
        System.out.println("ListControllerLoaded");
    }
}
