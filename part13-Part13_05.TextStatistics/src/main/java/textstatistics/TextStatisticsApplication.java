package textstatistics;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TextStatisticsApplication extends Application {

    public void start(Stage window) {

        BorderPane pane = new BorderPane();
        pane.setCenter(new TextArea("This is a tfield"));

        HBox labelBar = new HBox();
        labelBar.setSpacing(10);

        Label firstLabel = new Label("Letters: 0");
        Label secondLabel = new Label("Words: 0");
        Label thirdLabel = new Label("The longest word is: ");

        labelBar.getChildren().add(firstLabel);
        labelBar.getChildren().add(secondLabel);
        labelBar.getChildren().add(thirdLabel);
        pane.setBottom(labelBar);

        Scene view = new Scene(pane, 500, 500);

        window.setScene(view);
        window.show();

    }

    public static void main(String[] args) {
        launch(TextStatisticsApplication.class);
    }

}
