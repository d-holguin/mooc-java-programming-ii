package application;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GreeterApplication extends Application {

     @Override
    public void start(Stage windows) {
    GridPane gPane = new GridPane();
    Label firstLabel = new Label("Enter your name and start");
    Button firstBtn = new Button("Start");
    TextField tField = new TextField();
    
    gPane.add(firstLabel, 0, 0);
    gPane.add(tField, 0, 1);
    gPane.add(firstBtn, 0, 2);
    
    
    Scene firstScene = new Scene(gPane, 300,180);
    
    
    //layout stuff 
      gPane.setPrefSize(300, 180);
      gPane.setAlignment(Pos.CENTER);
      gPane.setVgap(10);
      gPane.setHgap(10);
      gPane.setPadding(new Insets(20, 20, 20, 20));
      ////
      StackPane  secondPane = new StackPane();
      Label secondLabel = new Label();
      secondPane.getChildren().add(secondLabel);
      
      Scene seconScene = new Scene(secondPane,300,180);
      
      
         firstBtn.setOnAction((event) -> {

             secondLabel.setText("Welcome " + tField.getText() + "!");
            windows.setScene(seconScene);

        });
      
      
      
      
    
    
    
    
    
    
      windows.setScene(firstScene);
      windows.show();
    }

    public static void main(String[] args) {

        launch(GreeterApplication.class);
    }
}
