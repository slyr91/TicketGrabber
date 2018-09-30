package daryl.ticketgrabber.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //This project had 3 components. The data component in the data package to manage and control the flow of data in a
    //thread safe manner. The work component where the execution tasks would be and the thread management parts were.
    //The last component was purely aesthetic, add a gui to monitor at a glance the progress and status of each thread.
    //This project is on hold. Probably indefinitely. There is simply no need for it and the use case is limited. Good
    //practice tho.

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../../../main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
