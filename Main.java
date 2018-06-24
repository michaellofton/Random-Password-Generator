/* Program Description:
* @author Michael Lofton
* @version 1.0.0
*
* This program allows any user to generate a random password up to
* 1024 characters long, using one or all of the following character sets:
* lowercase, uppercase, special characters, and numbers. The user can also
* copy the password, or save the password to a file with information
* detailing when it was generated.
*
* The user is given control over how large they want their password to be, and
* what they want their password to contain.
*
* The save feature is used when people decide to use the generated password
* for some website or account and forgot what their password was because they
* didn't write it down.
*/
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception
    {
        int sceneWidth = 340;
        int sceneHeight = 360;
        View guiView = new View(); //Extends Parent to make it workable with scene objects

        passwordGenerator passwordGenModel = new passwordGenerator();

        Controller controller = new Controller(passwordGenModel, guiView);
        window.setScene(new Scene(guiView, sceneWidth, sceneHeight));

        window.setTitle("Random Password Generator");
        window.setResizable(false);
        window.show();

    }
}