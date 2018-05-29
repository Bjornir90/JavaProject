package tv.programmes;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tv.programmes.controller.Controller;


public class ApplicationMain extends Application {


	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		String fxmlResource = "/tv/programmes/controller/MainWindow.fxml";
		Parent panel;
		App app = new App();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlResource));

		panel = loader.load();

		Controller controller = loader.getController();
		controller.setApp(app);
		app.setCurrentController(controller);

		primaryStage.setTitle("TV programmes");
		Scene scene = new Scene(panel);
		app.getScenes().put("MainWindow", scene);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

}
