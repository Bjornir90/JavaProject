package tv.programmes.controller;


import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PopUpControllerCredit {
	private TextField input;
	private Button validate;
	private Controller controller;
	private Stage popupStage;

	public PopUpControllerCredit(TextField input, Button validate, Controller controller, Stage stage) {
		this.input = input;
		this.validate = validate;
		this.controller = controller;
		this.popupStage = stage;
		init();
	}

	/**
	 * Simply sets an event handler for the validate button of the popup.
	 */
	private void init(){
		validate.setOnMouseClicked(event -> passInformation());
	}

	/**
	 * Called when the user has validated it's input.
	 * It pass the inputs to the controller, so it can switch scene.
	 * It closes the popup.
	 */
	private void passInformation(){
		String data = input.getText();
		if(data.isEmpty()){
			return;
		}
		controller.switchToFindByCredit(data);
		popupStage.close();
	}
}
