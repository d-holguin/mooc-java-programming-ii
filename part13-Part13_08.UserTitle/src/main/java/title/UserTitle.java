package title;

import javafx.application.Application;
import javafx.stage.Stage;

public class UserTitle extends Application {

    @Override
  public void start(Stage window) {
        Parameters params = getParameters();
        String userTitle = params.getNamed().get("userTitle");
       

        window.setTitle(userTitle);
        window.show();
    }
}