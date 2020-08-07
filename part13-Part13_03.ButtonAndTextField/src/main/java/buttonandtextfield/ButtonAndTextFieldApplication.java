package buttonandtextfield;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ButtonAndTextFieldApplication extends Application {

    public void start(Stage window) {

        Button btn = new Button("Button");

        TextField txtField = new TextField();
        Pane pane = new Pane();

        pane.getChildren().add(btn);

        pane.getChildren().add(txtField);
        btn.setLayoutX(100);
        btn.setLayoutY(100);
        txtField.setLayoutX(250);
        txtField.setLayoutY(250);
        Scene view = new Scene(pane, 500, 500);

        window.setScene(view);
        window.show();

    }

    public static void main(String[] args) {
        launch(ButtonAndTextFieldApplication.class);
    }

}
