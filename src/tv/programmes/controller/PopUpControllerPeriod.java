package tv.programmes.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PopUpControllerPeriod {
	private TextField inputDay, inputMonth;
	private Button validate;
	private Controller controller;
	private Stage popupStage;

	public PopUpControllerPeriod(TextField inputDay, TextField inputMonth, Button validate, Controller controller, Stage popupStage) {
		this.inputDay = inputDay;
		this.inputMonth = inputMonth;
		this.validate = validate;
		this.controller = controller;
		this.popupStage = popupStage;
		init();
	}

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
	}

	private void passInformation(){
		String day = inputDay.getText(), month = inputMonth.getText();
		if(day.isEmpty() || month.isEmpty()){
			return;
		}
		controller.switchToFindByPeriod(Integer.parseInt(day), Integer.parseInt(month));
		popupStage.close();
	}
}
