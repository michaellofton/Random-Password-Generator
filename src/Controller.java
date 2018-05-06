import javafx.event.EventHandler;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
//import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.*;
import javafx.stage.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Date;


/*
 The controller is used to hookup the functionality of the program with the user interface so
 that the program responds to the user's interactions with it.

 */
public class Controller implements EventHandler<ActionEvent>
{
    private View guiView;
    private passwordGenerator passGenModel;
    private Label messageLabel;

    private int maxPassLength = 1024;

    String savePassMenuItemStr;
    String maxLengthMenuItemStr;
    String textFieldStr;
    String makePassButtonStr;
    String copyButtonStr;

    public Controller(passwordGenerator givenPassGenModel, View givenView)
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
        guiView.getMaxLengthMenuItem().setOnAction(this);

    }

    /* Need to create a handle(ActionEvent name) function when
    implementing EventHandler<ActionEvent> because it's an abstract class
     */
    public void handle(ActionEvent event)
    /*-----------------------------------------------------------------
    * Author(s): 	Michael Lofton
    * Date:			xx-xx-2018
    *
    * checks to see which button or widget was clicked on. Performs the
    * necessary actions when a certain button was clicked on. Allows easy
    * modifiability if the GUI needs to use more or fewer buttons.
    */
    {
        String savePassMenuItemStr = guiView.getSavePassMenuItem().toString();
        String maxLengthMenuItemStr = guiView.getMaxLengthMenuItem().toString();
        String makePassButtonStr = guiView.getMakePasswordButton().getText();
        String copyButtonStr = guiView.getCopyButton().getText();
        String eventString = event.toString();

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
        else if (eventString.contains(maxLengthMenuItemStr))
        {
            handleMaxLengthMenuItem();
        }
    }


    /*******************************************************************
    Takes in a button as a parameter, and
     */


    public void handleMakePasswordButton()
    {
        //Handle the "make password" button

        /* Storing in object to prevent making another set of calls to get the object
        when using calling setCharacterSet method */
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
	* Author(s): 	Michael Lofton
	* Date:			xx-xx-2018
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
	* Author(s): 	Michael Lofton
	* Date:			xx-xx-2018
	*
	* Copies the password to the user's
	* clipboard when they press the copy
	* button
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
    /*-----------------------------
	* Author(s): 	Michael Lofton
	* Date:			xx-xx-2018
	*
	* Makes the radio button above the textfield
	* be selected when the user clicks on the
	* textfield. Clicking on the textfield is the same
	* as selecting the radio button)
	*/
    {
        guiView.getMoreCharRadioButton().setSelected(true);
    }


    public void handlePassLengthChoiceBoxClick()
    /*-----------------------------
	* Author(s): 	Michael Lofton
	* Date:			xx-xx-2018
	*
	* Makes the radio button above the choicebox
	* be selected when the user clicks on the
	* choicebox. Clicking on the choicebox is the same
	* as selecting the radio button)
	*/
    {
        guiView.getNumOfCharRadioButton().setSelected(true);
    }



    public String getCurrentWorkingDirectory()
    {
        System.out.println("Getting current working directory");
        return System.getProperty("user.dir");
    }

    //Call this only if the file is brand new
    public String createHeader()
    {
        String header = "Randomly Generated Passwords:" +
                        System.lineSeparator() +
                        System.lineSeparator();
        return header;
    }

    public String createDetails(String password)
    {
        //Format the date according to a specific desire:
        //https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ofLocalizedDateTime-java.time.format.FormatStyle-

        ZonedDateTime timeCreated = ZonedDateTime.now();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("h:m a");
        timeCreated.format(dateFormat);

        String date = timeCreated.getMonth() + "/" + timeCreated.getDayOfMonth() + "/" + timeCreated.getYear();
        String time = timeCreated.format(dateFormat);//timeCreated.getHour() + ":" + timeCreated.getMinute() + timeCreated.format(dateFormat);
        String zone = "Time Zone: " + timeCreated.getZone().toString();
        String passFormatted = "Password " + password;

        int longest = Integer.max(
                Integer.max(date.length(), time.length()),
                Integer.max(zone.length(), passFormatted.length()));

        /*Attribution: Credit to umidjons from GitHub for this technique:
        https://gist.github.com/umidjons/10859940
        */
        char borderChar = '=';
        String border = new String(new char[longest]).replace('\0', borderChar);

        return date + System.lineSeparator()
                + time + System.lineSeparator()
                + zone + System.lineSeparator()
                + border + System.lineSeparator()
                + passFormatted + System.lineSeparator()
                + System.lineSeparator();
    }


    public void writePassToFile(String password, File selectedFile)
    {

        System.out.println("Writing " + password + " to " + selectedFile);
        System.out.println(createHeader() + createDetails(password));

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



        //http://www.baeldung.com/java-write-to-file

        /*
        if file doesnt exist,
            make a new file, write header on first line;
        else if file already exists
            push all data down by one space
            append header/data to top of file
            */

        //FileWriter is a custom class used to call simple methods that write data to files
        //When creating the custom class, use template for kind of data to write in files
        //I.E. writing strings, binary data,

        //FileWriter passFileWriter = new FileWriter();
    }

    public FileChooser.ExtensionFilter getFileExtensions()
    {
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Text files",  "*.txt");

        return extFilter;
    }

    public void handleSavePassMenuItem()
    {
        System.out.println("Handling Saving password");

        String currentWorkingDirectory = getCurrentWorkingDirectory();
        System.out.println("CWD: " + currentWorkingDirectory);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose where to save the password");
        fileChooser.setInitialDirectory(new File(currentWorkingDirectory));
        fileChooser.setInitialFileName("Random Passwords Generated");
        fileChooser.getExtensionFilters().add(getFileExtensions());

        //Shows the actual file chooser dialog window
        File selectedFile = fileChooser.showSaveDialog(null);
        System.out.println("Selected file is: " + selectedFile);

        String password = guiView.getPasswordTextArea().getText();

        //If the user chose a file they want to save to, save password to it.
        writePassToFile(password, selectedFile);

        //2nd param = path (string) or file
        //writePassToFile(pass, path);

    }

    public void handleMaxLengthMenuItem()
    {
        System.out.println("Handling changing the max length of pass");
    }

}
