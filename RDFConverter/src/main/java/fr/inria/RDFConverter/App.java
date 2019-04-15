package fr.inria.RDFConverter;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {
	private Stage primaryStage;

    public static void main( String[] args )
    {
		launch(args);
    }

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Convert");
		
		shoxForm();
	}
	
	public void stop() {
		this.primaryStage.close();
	}
	
	public void shoxForm() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("FormLayout.fxml"));
			Scene sceneBaseForm = new Scene(loader.load());

			FormController controller = loader.getController();
			controller.setMaster(this);	        

			primaryStage.setScene(sceneBaseForm);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File createFileChooser(String title, FileChooser.ExtensionFilter... extensions) {
		FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle(title);
		fileChooser.getExtensionFilters().addAll(extensions);
		return fileChooser.showOpenDialog(this.primaryStage);		
	}
	
	public File createFileChooserSave(String title, FileChooser.ExtensionFilter... extensions) {
		FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle(title);
		fileChooser.getExtensionFilters().addAll(extensions);
		return fileChooser.showSaveDialog(this.primaryStage);
	}
	
	public void createAlertError(String title, String header,String message) {
		Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle(title);
    	alert.setHeaderText(header);
    	alert.setContentText(message);
    	alert.showAndWait();
	}
	
	public void createAlertSuccess(String title, String header,String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle(title);
    	alert.setHeaderText(header);
    	alert.setContentText(message);
    	alert.showAndWait();
	}
}
