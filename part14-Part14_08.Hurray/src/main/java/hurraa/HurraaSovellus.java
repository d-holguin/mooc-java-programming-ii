package hurraa;


import javafx.scene.media.AudioClip;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class HurraaSovellus extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane pane = new BorderPane();

        Button nappi = new Button("Hurraa!");
        
        //final URL resource = getClass().getResource("Applause-Yannick_Lemieux.wav");
            
             
        //audioClip = new AudioClip(resource.toExternalForm());
     

        // Media sound = new Media("file:Applause-Yannick_Lemieux.wav") {};
        pane.setCenter(nappi);

        //String musicFile = "StayTheNight.mp3";     // For example
        //Media media = new Media("Applause-Yannick_Lemieux.wav"); 
        //String musicFile = "Applause-Yannick_Lemieux.wav";     // For example

       AudioClip musicFile = new AudioClip("file:Applause-Yannick_Lemieux.wav");

        nappi.setOnAction((event) -> {
            musicFile.play();
        });

        Scene scene = new Scene(pane, 600, 400);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
