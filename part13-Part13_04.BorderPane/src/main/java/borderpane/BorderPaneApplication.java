package borderpane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class BorderPaneApplication extends Application {

    @Override
    public void start(Stage window) {
        BorderPane borderLayout = new BorderPane();

        borderLayout.setTop(new Label("NORTH"));
        borderLayout.setRight(new Label("EAST"));
        borderLayout.setBottom(new Label("SOUTH"));
        //borderLayout.setLeft(new Label("WEST"));
        // borderLayout.setCenter(new Label(""));

        Scene view = new Scene(borderLayout,300,500);

        window.setScene(view);
        window.show();

    }

    public static void main(String[] args) {
        launch(BorderPaneApplication.class);
    }

}
