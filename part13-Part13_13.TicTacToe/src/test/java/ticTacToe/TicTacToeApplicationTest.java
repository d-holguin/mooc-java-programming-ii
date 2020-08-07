package ticTacToe;

import ticTacToe.TicTacToeApplication;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.testfx.framework.junit.ApplicationTest;

public class TicTacToeApplicationTest extends ApplicationTest {

    private Stage stage;

    static {
        if (Boolean.getBoolean("SERVER")) {
            System.setProperty("java.awt.headless", "true");
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("glass.platform", "Monocle");
            System.setProperty("monocle.platform", "Headless");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        TicTacToeApplication application = new TicTacToeApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure that TicTacToeApplication extends the class Application.");
        }

        try {
            Reflex.reflect(TicTacToeApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure that the class TicTacToeApplication has the method start that receives a Stage object as a parameter. If this is the case, make sure that the method works as inteded. The error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    @Points("13-13.1")
    public void componentsInPlace() {
        Scene scene = stage.getScene();
        assertNotNull("Stage object should have a Scene object. Now after executing the start method, the getScene call of the stage returned the null reference.", scene);
        Parent rootElement = scene.getRoot();
        assertNotNull("Scene object should receive an object meant for the layout of the components (BorderPane, in this case). Now the Scene object had no objects that hold components.", rootElement);

        BorderPane layout = null;
        try {
            layout = BorderPane.class.cast(rootElement);
        } catch (Throwable t) {
            fail("Make sure you are using the BorderPane class as the layout for the UI components.");
        }

        assertNotNull("Scene obect must receive as a parameter a BorderPane object meant for the layout of the UI components.", layout);
        assertTrue("The top of the BorderPane should contain a Label object. Now it contained: " + layout.getTop(), layout.getTop().getClass().isAssignableFrom(Label.class));
        assertTrue("The center of the Borderpane should contain a GridPane object. Now it contained: " + layout.getCenter(), layout.getCenter().getClass().isAssignableFrom(GridPane.class));

        GridPane grid = null;
        try {
            grid = GridPane.class.cast(layout.getCenter());
        } catch (Throwable t) {
            fail("Make sure you use the GridPane class at the center of the BorderPane.");
        }

        assertNotNull("Make sure you use the GridPane class at the center of the BorderPane. Now the center contained " + grid, grid);

        assertEquals("Expected the GridPane to contain nine buttons. Now the number of elements was: " + grid.getChildren().size(), 9, grid.getChildren().size());

        for (Node node : grid.getChildren()) {
            try {
                Button button = Button.class.cast(node);
            } catch (Throwable t) {
                fail("Expected every object of the grid to be a Button. This was not the case. Error: " + t.getMessage());
            }
        }

    }

    @Test
    @Points("13-13.2")
    public void turnChangesWhenGameIsPlayed() {
        checkTurnChange(0, 1);
    }

    @Test
    @Points("13-13.2")
    public void turnChangesWhenGameIsPlayed2() {
        checkTurnChange(5, 2);
    }

    @Test
    @Points("13-13.2")
    public void turnChangesWhenGameIsPlayed3() {
        checkTurnChange(4, 7);
    }

    private void checkTurnChange(int first, int second) {
        Label textLabel = textLabel();
        assertEquals("At the beginning of the game the text label should display \"Turn: X\". Now it contained the text: \"" + textLabel.getText() + "\".", "Turn: X", textLabel.getText());
        List<Button> buttons = buttons();
        assertTrue("There should be 9 buttons in the game. Now the number of buttons was " + buttons.size(), buttons.size() == 9);

        assertTrue("At the beginning of the game the buttons should contain no text. Now the following text could be found: " + buttons.get(first).getText(), buttons.get(first).getText().trim().isEmpty());
        clickOn(buttons.get(first));
        assertTrue("When the button is clicked and it's the X turn, the text in the button should be X. Not the text in the button was: " + buttons.get(first).getText(), buttons.get(first).getText().trim().equals("X"));

        textLabel = textLabel();
        assertEquals("After X's turn it should then be O's turn. The text label should read \"Turn: O\". Now it contained the text: \"" + textLabel.getText() + "\".", "Turn: O", textLabel.getText());
        clickOn(buttons.get(second));
        assertTrue("When the button is clicked and it's O's turn, the text contained by the button should becode 0. Now the text became: " + buttons.get(second).getText(), buttons.get(second).getText().trim().equals("O"));
        textLabel = textLabel();
        assertEquals("After O's turn has been finished, it should then be X's turn. The text label should read \"Turn: X\". Now it contained the text: \"" + textLabel.getText() + "\".", "Turn: X", textLabel.getText());
    }

    @Test
    @Points("13-13.2")
    public void samePositionCannotBePlayedTwice() {
        checkThatSamePositionCannotBePlayedTwice(0);
    }

    @Test
    @Points("13-13.2")
    public void samePositionCannotBePlayedTwice2() {
        checkThatSamePositionCannotBePlayedTwice(5);
    }

    @Test
    @Points("13-13.2")
    public void samePositionCannotBePlacedTwice3() {
        checkThatSamePositionCannotBePlayedTwice(8);
    }

    @Test
    @Points("13-13.3")
    public void gameCanBeWonOrLost() {
        Label textLabel = textLabel();
        assertEquals("At the beginning of the game the text label should display \"Turn: X\". Now it contained the text: \"" + textLabel.getText() + "\".", "Turn: X", textLabel.getText());
        List<Button> buttons = buttons();
        assertTrue("There should be 9 buttons in the game. Now the number of buttons was " + buttons.size(), buttons.size() == 9);
        Collections.shuffle(buttons);

        String turn = "X";
        for (int i = 0; i < buttons.size(); i++) {
            clickOn(buttons.get(i));
            assertTrue("When the button is clicked and it's " + turn + "'s turn, the text contained by the button should become " + turn + ". Now the button contained the text: " + buttons.get(i).getText(), buttons.get(i).getText().trim().equals(turn));

            String previousTurn = turn;
            turn = turn.equals("X") ? "O" : "X";
            textLabel = textLabel();
            if (textLabel.getText().equals("The end!")) {
                return;
            }

            if (i == 8) {
                fail("When the game has been played to completion, the text label should read \"The end!\". Now the text was \"" + textLabel.getText() + "\".");
            }

            if (!textLabel.getText().toLowerCase().contains("turn")) {
                assertEquals("Make sure that the end of the game is signalled with the text \"The end!\". Now the text was \"" + textLabel.getText() + "\".", "The end!", textLabel.getText());
            }

            assertEquals("After playing " + previousTurn + "'s turn, it should then be" + turn + "'s turn. Now the text label said: \"" + textLabel.getText() + "\".", "Turn: " + turn, textLabel.getText());
        }

        textLabel = textLabel();
        if (!textLabel.getText().equals("The end!")) {
            fail("After the game ends, the text label should contain the text \"The end!\". Now it contained \"" + textLabel.getText() + "\"");
        }
    }

    private void checkThatSamePositionCannotBePlayedTwice(int place) {
        Label textLabel = textLabel();
        assertEquals("At the beginning of the game the text label should display \"Turn: X\". Now it contained the text: \"" + textLabel.getText() + "\".", "Turn: X", textLabel.getText());
        List<Button> buttons = buttons();
        assertTrue("There should be 9 buttons in the game. Now the number of buttons was " + buttons.size(), buttons.size() == 9);

        assertTrue("At the beginning of the game the buttons should contain no text. Now the following text could be found: "  + buttons.get(place).getText(), buttons.get(place).getText().trim().isEmpty());
        clickOn(buttons.get(place));
        textLabel = textLabel();
        assertTrue("When the button is clicked and it's the X turn, the text in the button should be X. Not the text in the button was: "  + buttons.get(place).getText(), buttons.get(place).getText().trim().equals("X"));

        assertEquals("After X's turn it should then be O's turn. The text label should read \"Turn: O\". Now it contained the text: \"" + textLabel.getText() + "\".", "Turn: O", textLabel.getText());
        clickOn(buttons.get(place));
        textLabel = textLabel();
        assertTrue("When an already chosen button is clicked and it's O's turn, the text in the button should not change, and the turn should still be O's. Now the text became: " + buttons.get(place).getText(), buttons.get(place).getText().trim().equals("X"));
        assertEquals("When O clicks on an already chosen position, the turn must not change. The text label should still contain the text \"Turn: O\". Now the text it contained was: \"" + textLabel.getText() + "\".", "Turn: O", textLabel.getText());

    }

    private Label textLabel() {
        Scene scene = stage.getScene();
        if (scene == null) {
            fail("A Scene object couldn't be found in the Stage object.");
        }

        Parent rootElement = scene.getRoot();
        if (rootElement == null) {
            fail("The parameter passed in the contructor of the Scene object was null.");
        }

        BorderPane layout = null;
        try {
            layout = BorderPane.class.cast(rootElement);
        } catch (Throwable t) {
            fail("The layout of the UI components didn't use the BorderPane class.");
        }

        if (layout.getTop() == null || !layout.getTop().getClass().isAssignableFrom(Label.class)) {
            fail("There was no Label object at the top of the BorderPane in the UI.");
        }

        return (Label) layout.getTop();
    }

    private List<Button> buttons() {
        Scene scene = stage.getScene();
        if (scene == null) {
            fail("A Scene object couldn't be found in the Stage object.");
        }

        Parent rootElement = scene.getRoot();
        if (rootElement == null) {
            fail("The parameter passed in the contructor of the Scene object was null.");
        }

        BorderPane layout = null;
        try {
            layout = BorderPane.class.cast(rootElement);
        } catch (Throwable t) {
            fail("The layout of the UI components didn't use the BorderPane class.");
        }

        if (layout.getTop() == null || !layout.getCenter().getClass().isAssignableFrom(GridPane.class)) {
            fail("There wasn't a GridPane object at the center of the BorderPane in the UI.");
        }

        GridPane grid = (GridPane) layout.getCenter();
        assertEquals("Expected the gridPane to contain nine buttons. Now the number of the elements was: " + grid.getChildren().size(), 9, grid.getChildren().size());
        List<Button> buttons = new ArrayList<>();

        for (Node node : grid.getChildren()) {
            try {
                Button button = Button.class.cast(node);
                buttons.add(button);
            } catch (Throwable t) {
                fail("Expected every object in the grid to be a Button. Now this was not the case. Error: " + t.getMessage());
            }
        }

        return buttons;
    }
}
