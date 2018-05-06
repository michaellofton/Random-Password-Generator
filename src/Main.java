import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    /*
    The visual layout (the view, a fxml file) is loaded using the fxml loader.
    The title for the program is given as a string
    The window is then created using the loaded view (fxml file)
    The windows is then set to be visible and displayable for the user.
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {

        passwordGenerator passwordGenModel = new passwordGenerator();
        View guiView = new View(); //Extends Parent to make it workable with scene objects

        Controller controller = new Controller(passwordGenModel, guiView);

        primaryStage.setScene(new Scene(guiView, 340, 360));

        //Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        primaryStage.setTitle("Random Password Generator");
        //primaryStage.setScene(new Scene(root, 450, 340));
        primaryStage.show();
		


    }



}