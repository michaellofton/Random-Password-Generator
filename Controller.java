/*
 The controller is used to hookup the functionality of the program with the user interface so
 that the program responds to the user's interactions with it. This class implements
 an EventHandler which allows it to call the necessary functions under certain interactive
 situations (i.e. the user clicks a button to make a password, it calls a function to make the password).
 The controller also has access to both the view and the model, which are passed as parameters to its constructor.
 */

import javafx.event.EventHandler;

import javafx.scene.control.CheckBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.FileWriter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class Controller implements EventHandler<ActionEvent>
{
    private View guiView;
    private passwordGenerator passGenModel;
    private Label messageLabel;
    private int maxPassLength = 1024;

    public Controller(passwordGenerator givenPassGenModel, View givenView)
    /*-------------------------------------------------------------------
    * Author(s):    Michael Lofton
    * Date:         4-13-2018
    *
    * Takes in a model and a view as parameters and binds them to private
    * variables so the controller can use them in the future. The function
    * also sets the action event for each widget of the view that
    * requires an event to happen after it's been interacted with.
    */
    {
        guiView = givenView;
        passGenModel = givenPassGenModel;

        messageLabel = guiView.getMessageLabel();
        guiView.getMakePasswordButton().setOnAction(this);
        guiView.getCopyButton().setOnAction(this);
        guiView.getMoreCharTextField().setOnMouseClicked(
                event ->
                {
                    handleMoreCharTextFieldClick();
                });

        guiView.getPassLengthChoiceBox().setOnMouseClicked(
                event ->
                {
                    handlePassLengthChoiceBoxClick();
                });

        guiView.getSavePassMenuItem().setOnAction(this);
    }

    /* Need to create a handle(ActionEvent name) function when
    implementing EventHandler<ActionEvent> because it's an abstract class
     */
    public void handle(ActionEvent event)
    /*-----------------------------------------------------------------
    * Author(s):    Michael Lofton
    * Date:         4-07-2018
    *
    * Checks to see which button or widget was clicked on. Calls an appropriate
    * function to perform the necessary actions when certain event happens.
    * Allows easy modifiability if the GUI needs to use more or widgets that
    * create events.
    */
    {
        String eventString = event.toString();
        String savePassMenuItemStr = guiView.getSavePassMenuItem().toString();
        String makePassButtonStr = guiView.getMakePasswordButton().getText();
        String copyButtonStr = guiView.getCopyButton().getText();

        if (eventString.contains(makePassButtonStr))
        {
            handleMakePasswordButton();
        }
        else if (eventString.contains(copyButtonStr))
        {
            handleCopyButton();
        }
        else if (eventString.contains(savePassMenuItemStr))
        {
            handleSavePassMenuItem();
        }
    }
    //*******************************************************************

    public void handleMakePasswordButton()
    /*-----------------------------------------------------------------
    * Author(s):    Michael Lofton
    * Date:         4-10-2018
    *
    * If no checkbox was selected, throw a null pointer exception and changes the
    * label so the user knows. Otherwise, add the selected checkbox's characters to
    * the set of possible characters for a password, obtain the password length from
    * the user's selected choice, and make the password.
    */
    {
        /* Storing in object to prevent making another set of calls to get the object
        when using calling setCharacterSet method in the passwordGenerator model*/
        RadioButton NumOfCharRadioButton = guiView.getNumOfCharRadioButton();
        RadioButton moreCharRadioButton = guiView.getMoreCharRadioButton();

        CheckBox lowercaseCheckBox = guiView.getLowercaseCheckBox();
        CheckBox uppercaseCheckBox = guiView.getUppercaseCheckBox();
        CheckBox specialCheckBox = guiView.getSpecialCharCheckBox();
        CheckBox numberCheckBox = guiView.getNumberCheckBox();

        try
        {
            messageLabel.setText("");
            if (!lowercaseCheckBox.isSelected() && !uppercaseCheckBox.isSelected()
                && !specialCheckBox.isSelected() && !numberCheckBox.isSelected())
            {
                throw new NullPointerException("No checkboxes were selected");
            }

            passGenModel.setCharacterSet(
                    lowercaseCheckBox.isSelected(),
                    uppercaseCheckBox.isSelected(),
                    specialCheckBox.isSelected(),
                    numberCheckBox.isSelected());

            int passLength;
            if (NumOfCharRadioButton.isSelected())
            {
                passLength = (int)guiView.getPassLengthChoiceBox().getSelectionModel().getSelectedItem();
            }
            else
            {
                String moreCharactersString = guiView.getMoreCharTextField().getText();
                passLength = Integer.parseInt(moreCharactersString); //Convert number represented as string to integer
                if (passLength <= 0)
                {
                    messageLabel.setText("ERROR: Please type a number larger than 0 under" + "\n'" + moreCharRadioButton.getText() + "'");
                }
                else if (passLength > maxPassLength)
                {
                    messageLabel.setText("ERROR: Length of password can't be larger than 1024.");
                    passLength = 0;
                }
            }

            String password = passGenModel.makePassword(passLength);
            guiView.getPasswordTextArea().setText(password);
        }
        catch(NullPointerException nullException)
        {
            messageLabel.setText("ERROR: Please select at least 1 checkbox");
        }
        catch(NumberFormatException formatException)
        {
            messageLabel.setText("ERROR: Please type a whole number under" + "\n'" + moreCharRadioButton.getText() + "'");
        }
    }
    //*******************************************************************


    public void copyToClipboard(String textToCopy, ClipboardOwner user)
    /*-----------------------------------------------------------------
    * Author(s):    Michael Lofton
    * Date:         4-07-2018
    *
    * When calling this function, we can set clipboardOwner parameter to null,
    * and by default it will use the local machine's clipboard.
    */
    {
        //Create & get the clipboard from the computer
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        //Make the text selected
        Transferable selectedText = new StringSelection(textToCopy);

        //Copy & Write the selected text to the user's clipboard
        clipboard.setContents(selectedText, user);
    }


    public void handleCopyButton()
    /*-----------------------------
    *Author(s):    Michael Lofton
    * Date:         4-07-2018
    *
    * Copies the password to the user's
    * clipboard when they press the copy
    * button, but only if they already
    * generated a password.
    */
    {
        String textToCopy = guiView.getPasswordTextArea().getText();
        if (textToCopy.equals(""))
        {
            messageLabel.setText("You need to make a password before you try to copy it.");
        }
        else
        {
            copyToClipboard(textToCopy, null);
            messageLabel.setText("Password Copied!");
        }
    }

    public void handleMoreCharTextFieldClick()
    /*------------------------------------
    * Author(s):    Michael Lofton
    * Date:         4-8-2018
    *
    * Makes the radio button above the textfield
    * be selected when the user clicks on the
    * textfield (Clicking on the textfield is the same
    * as selecting the radio button).
    */
    {
        guiView.getMoreCharRadioButton().setSelected(true);
    }


    public void handlePassLengthChoiceBoxClick()
    /*----------------------------------------
    * Author(s):    Michael Lofton
    * Date:         4-8-2018
    *
    * Makes the radio button above the choicebox
    * be selected when the user clicks on the
    * choicebox (Clicking on the choicebox is the same
    * as selecting the radio button).
    */
    {
        guiView.getNumOfCharRadioButton().setSelected(true);
    }
    //*******************************************************************


    public String getCurrentWorkingDirectory()
    {
        //System.out.println("Getting current working directory");
        return System.getProperty("user.dir");
    }

    //Call this only if the file is brand new
    public String createHeader()
    /*--------------------------
    * Author(s):    Michael Lofton
    * Date:         4-11-2018
    *
    * Creates a header string showing the first line
    * to be written to the file of passwords if the file
    * hasn't been made yet.
    */
    {
        String header = "Randomly Generated Passwords:" +
                        System.lineSeparator() +
                        System.lineSeparator();
        return header;
    }

    public String createDetails(String password)
    /*------------------------------------------
    * Author(s):    Michael Lofton
    * Date:         4-11-2018
    *
    * Creates details for the generated password including
    * the time it was created, the day, the month, the year,
    * and the timezone. Returns a string with this information
    */
    {
        //Format the date according to a specific desire:
        //https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ofLocalizedDateTime-java.time.format.FormatStyle-

        ZonedDateTime timeCreated = ZonedDateTime.now();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("h:m a");
        timeCreated.format(dateFormat);

        String date = timeCreated.getMonth() + "/" + timeCreated.getDayOfMonth() + "/" + timeCreated.getYear();
        String time = timeCreated.format(dateFormat);//timeCreated.getHour() + ":" + timeCreated.getMinute() + timeCreated.format(dateFormat);
        String zone = "Time Zone: " + timeCreated.getZone().toString();
        String passFormatted = "Password: " + password;

        int longest = Integer.max(
                Integer.max(date.length(), time.length()),
                Integer.max(zone.length(), passFormatted.length()));

        /* START_Attribution: Credit to umidjons from GitHub for this technique:
        https://gist.github.com/umidjons/10859940
        */
        char borderChar = '=';
        String border = new String(new char[longest]).replace('\0', borderChar);
        /* END_Attribution */

        return date + System.lineSeparator()
                + time + System.lineSeparator()
                + zone + System.lineSeparator()
                + border + System.lineSeparator()
                + passFormatted + System.lineSeparator()
                + System.lineSeparator();
    }


    public void writePassToFile(String password, File selectedFile)
    /*--------------------------------------------------------------
    * Author(s):    Michael Lofton
    * Date:         4-13-2018
    *
    * If the given file exists, the function will append the details of
    * the given password to the file. If the file doesn't exist, it will
    * create a new file and write the header, then append the details of
    * the given password. If an error happens, the function changes the
    * message label to let the user know.
    */
    {
        try
        {
            if (selectedFile.exists())
            {
                RandomAccessFile fileCursor = new RandomAccessFile(selectedFile, "rw");
                long cursorPosition = fileCursor.length();
                fileCursor.seek(cursorPosition);
                fileCursor.write(createDetails(password).getBytes());
                fileCursor.close();
                messageLabel.setText("Saving to file was successful");
            }
            else
            {
                FileWriter outputStreamFileWriter = new FileWriter(selectedFile);
                BufferedWriter outputBuffer = new BufferedWriter(outputStreamFileWriter);
                outputBuffer.write(createHeader());
                outputBuffer.write(createDetails(password));
                outputBuffer.flush(); /* tell the buffer to write the data right now*/
                outputBuffer.close();
                messageLabel.setText("Saving to file was successful");
            }
        }
        catch (FileNotFoundException fileNotFoundEx)
        {
            messageLabel.setText("Couldn't find the The selected file: " + selectedFile.toString());
        }
        catch(IOException exception)
        {
            messageLabel.setText("Error saving to file: " + selectedFile.toString());
        }
    }

    public FileChooser.ExtensionFilter getFileExtensions()
    {
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Text files",  "*.txt");

        return extFilter;
    }

    public void handleSavePassMenuItem()
    /*------------------------------------------
    * Author(s):    Michael Lofton
    * Date:         4-29-2018
    *
    * If no password has been generated, then the user is given a message
    * telling them they should first generate a message before they try to save
    * it. If they already generated a password, the function gets the current
    * working directory, allows the user to choose where to save their file,
    * and then writes the password to the specified location.
    */
    {
        String password = guiView.getPasswordTextArea().getText();

        if (password.equals(""))
        {
            messageLabel.setText("You need to make a password before you can save it.");
        }
        else
        {
            String currentWorkingDirectory = getCurrentWorkingDirectory();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose where to save the password");
            fileChooser.setInitialDirectory(new File(currentWorkingDirectory));
            fileChooser.setInitialFileName("Random Passwords Generated");
            fileChooser.getExtensionFilters().add(getFileExtensions());

            //Shows the actual file chooser dialog window
            File selectedFile = fileChooser.showSaveDialog(null);
            System.out.println("Selected file is: " + selectedFile);

            //If the user chose a file they want to save to, save password to it.
            if (selectedFile != null)
            {
                writePassToFile(password, selectedFile);
            }
        }
    }
}
