package tv.programmes.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

public class PopUpControllerPeriod {
	private TextField inputDay, inputMonth;
	private Button validate;
	private Controller controller;
	private Stage popupStage;

	/**
	 * Creates a controller for the popup that asks the period to the user.
	 * Calls the init method.
	 * @param inputDay the input for the day
	 * @param inputMonth the input for the month
	 * @param validate the button that validates the inputs
	 * @param controller the controller that called this
	 * @param popupStage the stage for the popup
	 */
	public PopUpControllerPeriod(TextField inputDay, TextField inputMonth, Button validate, Controller controller, Stage popupStage) {
		this.inputDay = inputDay;
		this.inputMonth = inputMonth;
		this.validate = validate;
		this.controller = controller;
		this.popupStage = popupStage;
		init();
	}

	/**
	 * Set event handler for the validate button, and set textformatter for both textField, so they only accept numbers.
	 */
	private void init(){
		validate.setOnMouseClicked(event -> passInformation());
		inputDay.setTextFormatter(new TextFormatter<>(c-> {
			if(c.getControlNewText().isEmpty()) return c;
			if(c.getControlNewText().matches("\\d*")){
				return c;
			} else {
				return null;
			}
		}));
		inputMonth.setTextFormatter(new TextFormatter<>(c-> {
			if (c.getControlNewText().isEmpty()) return c;
			if (c.getControlNewText().matches("\\d*")) {
				return c;
			} else {
				return null;
			}
		}));
	}

	/**
	 * Called when the user has validated it's input.
	 * It pass the inputs to the controller, so it can switch scene.
	 * It closes the popup.
	 */
	private void passInformation(){
		String day = inputDay.getText(), month = inputMonth.getText();
		day = day.substring(0, 2);
		month = month.substring(0, 2);
		if(day.isEmpty() || month.isEmpty()){
			return;
		}
		controller.switchToFindByPeriod(Integer.parseInt(day), Integer.parseInt(month));
		popupStage.close();
	}
}
