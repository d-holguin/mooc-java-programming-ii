package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JokeApplication extends Application {

    @Override
    public void start(Stage window) throws Exception {

        // 1. Create main layout
        BorderPane layout = new BorderPane();

        // 1.1. Create menu for main layout
        HBox menu = new HBox();
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setSpacing(10);

        // 1.2. Create buttons for menu
        Button firstBtn = new Button("Joke");
        Button secondBtn = new Button("Answer");
        Button thirdBtn = new Button("Explanation");

        // 1.3. Add buttons to menu
        menu.getChildren().addAll(firstBtn, secondBtn, thirdBtn);

        layout.setTop(menu);


        // 2. Add subviews and add them to the menu buttons
        // 2.1. Create subview layout 
        StackPane firstLayout = createView("What do you call a bear with no teeth?");
        StackPane secondLayout = createView("A gummy bear.");
        
        StackPane thirdLayout = createView("Gummy bear is candy and candy is funny because candy");
        
        
        
        

        // 2.2. Add subviews to button. Pressing the buttons will change the view
        firstBtn.setOnAction((event) -> layout.setCenter(firstLayout));
        secondBtn.setOnAction((event) -> layout.setCenter(secondLayout));
        thirdBtn.setOnAction((event) -> layout.setCenter(thirdLayout));
        
        

        // 2.3. Set initial view
        layout.setCenter(firstLayout);


        // 3. Create main scene with layout 
        Scene scene = new Scene(layout);


        // 4. Show the main scene
        window.setScene(scene);
        window.show();
    }

    private StackPane createView(String text) {

        StackPane layout = new StackPane();
        layout.setPrefSize(300, 180);
        layout.getChildren().add(new Label(text));
        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    public static void main(String[] args) {
        launch(JokeApplication.class);
    }
}