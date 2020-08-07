/*
 * Copyright (C) 2020 Dantes
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
/**
 *
 * @author Dantes
 */
public class VocabularyPracticeApplication extends Application {

    private Dictionary dictionary;

    @Override
    public void init() throws Exception {
        // 1. Create the dictionary that the application uses
        this.dictionary = new Dictionary();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // 2. Create the views ("subviews")
        PracticeView practiceView = new PracticeView(dictionary);
        InputView inputView = new InputView(dictionary);

        // 3. Create the higher level layout
        BorderPane layout = new BorderPane();

        // 3.1. Create the menu for the general layout
        HBox menu = new HBox();
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setSpacing(10);

        // 3.2. Create the menu buttons
        Button enterButton = new Button("Enter new words");
        Button practiceButton = new Button("Practice");

        // 3.3. Add the buttons to the menu
        menu.getChildren().addAll(enterButton, practiceButton);
        layout.setTop(menu);


        // 4. Connect the subviews with the buttons. Clicking menu buttons changes the subview.
        enterButton.setOnAction((event) -> layout.setCenter(inputView.getView()));
        practiceButton.setOnAction((event) -> layout.setCenter(practiceView.getView()));

        // 5. First show the input view
        layout.setCenter(inputView.getView());

        // 6. Create the main view and add the high level layout
        Scene view = new Scene(layout, 400, 300);

        // 7. Show the application
        stage.setScene(view);
        stage.show();
    }

    public static void main(String[] args) {
        launch(VocabularyPracticeApplication.class);
    }
}