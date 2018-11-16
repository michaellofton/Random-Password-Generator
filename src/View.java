//Extends Parent to be able to be used in a scene object
import javafx.scene.Parent;

// Widgets
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

//Layout
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/*
 The view is a configurable class used in creating what the user sees when they run the program.
 The view of the program can be easily modified due to the use of modularity (separating components).
 There are 2 main sections, 1 for widgets, and another for the layout. Within those sections, a future
 programmer could add or remove certain widgets/layouts. There is also a section for creating the
 layout to have various characteristics, and that can also be modified.
 
 Below is a basic comment system used to show the name of the person who made changes to the function and when
 they made those changes. This helps keep code organized and helps let future programmers know who made changes
 and when for each function. If the employee is still with the company, and the programmer has a question about
 how a function works, they would know which programmer to contact.
 */

public class View extends Parent
{
    private boolean debug;
    private GridPane gridLayout;

    private BorderPane menuLayoutBorderPane;
    private MenuBar menuBar;
    private Menu fileMenu;
    private MenuItem savePassMenuItem;

    private TextArea passwordTextArea;
    private Button copyButton;

    private Text passwordLengthText;

    private ToggleGroup radButtonToggleGroup;
    private RadioButton numOfCharRadioButton;
    private ChoiceBox<Integer> passLengthChoiceBox;

    private VBox VBoxChoiceBoxCharLength;
    private VBox VBoxTextFieldCharLength;

    private RadioButton moreCharRadioButton;
    private TextField moreCharTextField;

    private Text passwordContentText;
    private CheckBox lowercaseCheckBox;
    private CheckBox specialCharCheckBox;
    private CheckBox uppercaseCheckBox;
    private CheckBox numberCheckBox;

    private Button makePasswordButton;
    private Label messageLabel;


    private void createWidgets()
    /*---------------------------------------
    * Author(s): 	Michael Lofton, Other Name, Third Name, etc
    * Date:			3-05-2018
    *
    * This function creates widgets that will be later placed into
    * the layout(s) for user interaction. If a programmer needs to
    * remove a widget or add more widgets this is the function to modify.
    */
    {
        gridLayout = new GridPane();

        // ------------- Menu -------------
        menuLayoutBorderPane = new BorderPane();
        menuBar = new MenuBar();
        fileMenu = new Menu("File");

        //Menu Items to be put in each Menu
        savePassMenuItem = new MenuItem("Save Password");

        // ------------- Text Area -------------
        passwordTextArea = new TextArea();
        passwordTextArea.setPrefColumnCount(3);
        passwordTextArea.setPrefRowCount(3);
        passwordTextArea.setEditable(false);

        //------------- Password Length with ChoiceBox -------------
        passwordLengthText = new Text("Select Password Length");

        radButtonToggleGroup = new ToggleGroup();
        numOfCharRadioButton = new RadioButton("Number of characters");
        numOfCharRadioButton.setToggleGroup(radButtonToggleGroup);
        numOfCharRadioButton.setSelected(true);

        //Give the ChoiceBox an observable list
        ObservableList<Integer> passwordLengths = FXCollections.observableArrayList
                (8,9,10,11,12,13,14,15);
        passLengthChoiceBox = new ChoiceBox<>(passwordLengths);
        passLengthChoiceBox.setValue(8);

        //---------------- Password Length with TextField ---------------
        moreCharRadioButton = new RadioButton("Need a different amount?");
        moreCharRadioButton.setToggleGroup(radButtonToggleGroup);
        moreCharTextField = new TextField();
        moreCharTextField.setPromptText("Type a number"); //Default grey text

        // ---------------- Password Content with CheckBoxes----------------
        passwordContentText = new Text("Choose Password Content");
        lowercaseCheckBox = new CheckBox("Use lowercase letters");
        specialCharCheckBox = new CheckBox("Use special characters");
        uppercaseCheckBox = new CheckBox("Use uppercase letters");
        numberCheckBox = new CheckBox("Use numbers");

        // ---------------- Make Buttons ----------------
        makePasswordButton = new Button("Make Password");
        copyButton = new Button("Copy password");

        //---------------- Message Label ----------------
        messageLabel = new Label("");
    }
    //---------------------------------------



    private void setupLayout()
    /*---------------------------------------
    * Author(s): 	Michael Lofton
    * Date:			3-27-2018
    * Creates the needed layouts for the View
    * object by creating a grid layout to put
    * all the widgets on, and 2 VBoxes, one
    * for each radio button representing password
    * length choices.
    */
    {
        gridLayout.setAlignment(Pos.CENTER);
        gridLayout.setHgap(10.0);
        gridLayout.setVgap(10.0);
        gridLayout.setPadding(new Insets(15.0, 15.0, 15.0, 15.0));

        VBoxChoiceBoxCharLength = new VBox(5.0); //spacing = 5.0
        VBoxChoiceBoxCharLength.setAlignment(Pos.CENTER);
        VBoxChoiceBoxCharLength.getChildren().addAll(numOfCharRadioButton, passLengthChoiceBox);

        VBoxTextFieldCharLength = new VBox(5.0); //spacing = 5.0
        VBoxTextFieldCharLength.getChildren().addAll(moreCharRadioButton, moreCharTextField);
    }
    //---------------------------------------



    private void addWidgetsToLayout()
    /*---------------------------------------
    * Author(s): 	Michael Lofton
    * Date:			3-25-2018
    * Expects the programmer to call this function after
    * first calling createWidgets and then calling
    * setupLayout
    */
    {
        /************ Menu ************/
        //Adding menu items to menus
        fileMenu.getItems().add(savePassMenuItem);

        //Adding menus to menu bar, and then to the border pane
        menuBar.getMenus().addAll(fileMenu);
        menuLayoutBorderPane.setTop(menuBar);

        /************ Text Area ************/
        gridLayout.setRowIndex(passwordTextArea, 1);
        gridLayout.setColumnIndex(passwordTextArea, 0);
        gridLayout.setRowSpan(passwordTextArea, 1);
        gridLayout.setColumnSpan(passwordTextArea, 2);

        /******* Text - Password Length *********/
        gridLayout.setRowIndex(passwordLengthText, 2);
        gridLayout.setColumnIndex(passwordLengthText, 0);
        gridLayout.setColumnSpan(passwordLengthText, 2);

        /******* VBOX - ChoiceBox Characters ***********
            * Number of Characters Radio Button
            * Character Length Choice box*/
        gridLayout.setRowIndex(VBoxChoiceBoxCharLength, 3);
        gridLayout.setColumnIndex(VBoxChoiceBoxCharLength, 0);

        /******* VBOX - TextField Characters ************
            * Need more characters
            * Radio Button Textfield */
        gridLayout.setRowIndex(VBoxTextFieldCharLength, 3);
        gridLayout.setColumnIndex(VBoxTextFieldCharLength, 1);

        /*********** Text - Choose Password Content ************/
        gridLayout.setRowIndex(passwordContentText, 4);
        gridLayout.setColumnIndex(passwordContentText, 0);

        /*********** Checkbox - Lowercase Letters ***********/
        gridLayout.setRowIndex(lowercaseCheckBox, 5);
        gridLayout.setColumnIndex(lowercaseCheckBox, 0);

        /*********** Checkbox - Special Characters ***********/
        gridLayout.setRowIndex(specialCharCheckBox, 6);
        gridLayout.setColumnIndex(specialCharCheckBox, 0);

        /*********** Checkbox - Uppercase Letters ***********/
        gridLayout.setRowIndex(uppercaseCheckBox, 5);
        gridLayout.setColumnIndex(uppercaseCheckBox, 1);

        /*********** Checkbox - Numbers ***********/
        gridLayout.setRowIndex(numberCheckBox, 6);
        gridLayout.setColumnIndex(numberCheckBox, 1);

        /*********** Button - Make Password ***********/
        gridLayout.setRowIndex(makePasswordButton, 7);
        gridLayout.setColumnIndex(makePasswordButton, 0);

        /*********** Button - Copy Password ***********/
        //copies password to user's computer's clipboard
        gridLayout.setRowIndex(copyButton, 7);
        gridLayout.setColumnIndex(copyButton, 1);

        /*********** Label - Message label ***********/
        //when there are errors or things user should know
        gridLayout.setRowIndex(messageLabel, 8);
        gridLayout.setColumnIndex(messageLabel, 0);
        gridLayout.setColumnSpan(messageLabel, 3);


        //Add all desired widgets/layouts to the grid layout here
        gridLayout.getChildren().addAll(passwordTextArea, passwordLengthText, VBoxChoiceBoxCharLength,
                                        VBoxTextFieldCharLength, passwordContentText, lowercaseCheckBox,
                                        specialCharCheckBox, uppercaseCheckBox, numberCheckBox, makePasswordButton,
                                        copyButton, messageLabel);

        //Add widgets to the borderpane layout
        menuLayoutBorderPane.setCenter(gridLayout);
        menuLayoutBorderPane.setTop(menuBar);

        /* add the layout that has all added widgets to the
        * View object using the public method inherited
        * from Parent */
        this.getChildren().add(menuLayoutBorderPane);
    }
    //---------------------------------------

    private void createView()
    /*------------------------------------------
    * Author(s): 	Michael Lofton
    * Date:			4-01-2018
    * Used to call all the necessary functions in the
    * right order, preventing the programmer from
    * calling them in a different order and messing up
    * the creation process. This function calls private
    * functions to create the widgets, setup the layout,
    * and then add the widgets to the layout.
    */
    {
        //Recreates view.fxml in this file as a modifiable class
        createWidgets();
        setupLayout();
        addWidgetsToLayout();
    }


    public View()
    /*------------------------------------------
    * Author(s): 	Michael Lofton
    * Date:			3-04-2018
    * The constructor is called when new object
    * of this class is made.
    */
    {
        createView();
    }


    /*--------------------------------------
    G E T T E R Functions
    Allow the controller to get the data from the view
    ---------------------------------------*/
    public TextArea getPasswordTextArea()
    {
        return passwordTextArea;
    }

    public Text getPasswordLengthText()
    {
        return passwordLengthText;
    }

    public Button getCopyButton()
    {
        return copyButton;
    }

    public RadioButton getNumOfCharRadioButton()
    {
        return numOfCharRadioButton;
    }

    public ChoiceBox<Integer> getPassLengthChoiceBox()
    {
        return passLengthChoiceBox;
    }

    public RadioButton getMoreCharRadioButton()
    {
        return moreCharRadioButton;
    }

    public TextField getMoreCharTextField()
    {
        return moreCharTextField;
    }

    public Text getPasswordContentText()
    {
        return passwordContentText;
    }

    public CheckBox getLowercaseCheckBox()
    {
        return lowercaseCheckBox;
    }

    public CheckBox getSpecialCharCheckBox()
    {
        return specialCharCheckBox;
    }

    public CheckBox getUppercaseCheckBox()
    {
        return uppercaseCheckBox;
    }

    public CheckBox getNumberCheckBox()
    {
        return numberCheckBox;
    }

    public Button getMakePasswordButton()
    {
        return makePasswordButton;
    }

    public Label getMessageLabel()
    {
        return messageLabel;
    }

    public MenuItem getSavePassMenuItem()
    {
        return savePassMenuItem;
    }

    /*--------------------------------------
    S E T T E R Functions
    Allow the controller to change the widget data in the view
    ---------------------------------------*/
    public void setPasswordTextArea(TextArea givenTextArea)
    {
        passwordTextArea = givenTextArea;
    }

    public void setPasswordLengthText(Text givenText)
    {
        passwordLengthText = givenText;
    }

    public void setCopyButton(Button givenButton)
    {
        copyButton = givenButton;
    }

    public void setNumOfCharRadioButton(RadioButton givenRadioButton)
    {
        numOfCharRadioButton = givenRadioButton;
    }

    public void setPassLengthChoiceBox(ChoiceBox<Integer> givenChoiceBox)
    {
        passLengthChoiceBox = givenChoiceBox;
    }

    public void setMoreCharRadButton(RadioButton givenRadioButton)
    {
        moreCharRadioButton = givenRadioButton;
    }

    public void getMoreCharTextField(TextField givenTextField)
    {
        moreCharTextField = givenTextField;
    }

    public void setPasswordContentText(Text givenText)
    {
        passwordContentText = givenText;
    }

    public void setLowercaseCheckBox(CheckBox givenCheckBox)
    {
        lowercaseCheckBox = givenCheckBox;
    }

    public void setSpecialCharCheckBox(CheckBox givenCheckBox)
    {
        specialCharCheckBox = givenCheckBox;
    }

    public void setUppercaseCheckBox(CheckBox givenCheckBox)
    {
        uppercaseCheckBox = givenCheckBox;
    }

    public void setNumberCheckBox(CheckBox givenCheckBox)
    {
        numberCheckBox = givenCheckBox;
    }

    public void setMakePasswordButton(Button givenButton)
    {
        makePasswordButton = givenButton;
    }

    public void setMessageLabel(Label givenLabel)
    {
        messageLabel = givenLabel;
    }


    public void enableDebug(boolean value)
    {
        debug = value;
    }

    public void showChildren()
    {
        if (debug == true)
        {
            System.out.println("Showing children");
            System.out.println(this.getChildrenUnmodifiable());
        }
        else
        {
            System.out.println("Enable debug to show children");
        }
    }

}
