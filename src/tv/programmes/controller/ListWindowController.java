package tv.programmes.controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class ListWindowController extends Controller {

    @FXML
    private MenuItem channelsButton;
    @FXML
    private MenuItem calendarButton;

    @FXML
    protected void initialize(){
        System.out.printf("ListController loaded");
    }
}
