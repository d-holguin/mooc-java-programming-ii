package textstatistics;

import java.util.Arrays;
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
        TextArea TArea = new TextArea();
        pane.setCenter(TArea);

        HBox labelBar = new HBox();
        labelBar.setSpacing(10);

        Label lettersLabel = new Label("Letters: 0");
        Label wordsLabel = new Label("Words: 0");
        Label longestWordLabel = new Label("The longest word is: ");

        labelBar.getChildren().addAll(lettersLabel, wordsLabel, longestWordLabel);
        pane.setBottom(labelBar);

        Scene view = new Scene(pane, 500, 500);

        window.setScene(view);
        window.show();
//-------------------------Action Lisener--Below------------------------------------------------------------------------

        TArea.textProperty().addListener((change, oldValue, newValue) -> { //params are supposed to be like thiat i guess as ppart of the interface https://stackoverflow.com/questions/30160899/value-change-listener-for-javafxs-textfield
            int characters = newValue.length();
            String[] parts = newValue.split(" ");
            int words = parts.length;
            String longest = Arrays.stream(parts)
                    .sorted((s1, s2) -> s2.length() - s1.length())
                    .findFirst()
                    .get();

            // set values of text elements
            lettersLabel.setText("Letters: " + String.valueOf(characters));
            wordsLabel.setText("Words: " + String.valueOf(words));
            longestWordLabel.setText("The longest word is: " + longest);

        });

    }

    public static void main(String[] args) {
        launch(TextStatisticsApplication.class);
    }

}
