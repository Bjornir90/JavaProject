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

	private void init(){
		validate.setOnMouseClicked(event -> passInformation());
	}

	private void passInformation(){
		String data = input.getText();
		if(data.isEmpty()){
			return;
		}
		controller.switchToFindByCredit(data);
		popupStage.close();
	}
}
