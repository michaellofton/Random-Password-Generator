//Extends Parent to use in a scene object
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

// Widgets
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

//Layout
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


/*
 The view is a configurable class used in creating what the user sees when they run the program.
 The view of the program can be easily modified due to the use of modularity (separating components)
 There are 2 main sections, 1 for widgets, another for the layout. Within those sections, a future
 programmer could add or remove certain widgets/layouts
 
 Below is a comment system used to show the name of the person who made the function and when it was made.
 This helps keep code organized and helps let future programmers know who made what and when.
 */
public class View extends Parent
{
	boolean debug;
	private GridPane gridLayout;

	private BorderPane menuLayoutBorderPane;
	private MenuBar menuBar;
	private Menu fileMenu;
	private Menu settingsMenu;
	private MenuItem savePassMenuItem;
	private MenuItem maxLengthMenuItem;

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
	private Button savePassToPCButton;
	private Label messageLabel;


	public void createWidgets()
	/*---------------------------------------
	* Author(s): 	Michael Lofton, Other Name, Third Name, etc
	* Date:			xx-xx-2018
	* 
	*/
	{
		gridLayout = new GridPane();

		// ------------- Menu -------------
		menuLayoutBorderPane = new BorderPane();
		menuBar = new MenuBar();
		fileMenu = new Menu("File");
		settingsMenu = new Menu("Settings");

		//Menu Items to be put in each Menu
		savePassMenuItem = new MenuItem("Save Password");
		maxLengthMenuItem = new MenuItem("Change max password length");

		//Adding menu items to menus
		fileMenu.getItems().add(savePassMenuItem);
		settingsMenu.getItems().add(maxLengthMenuItem);

		//Adding menus to menu bar, and then to the border pane
		menuBar.getMenus().addAll(fileMenu, settingsMenu);
		menuLayoutBorderPane.setTop(menuBar);

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

		//Give the choicebox an observable list
        ObservableList<Integer> passwordLengths = FXCollections.observableArrayList(8,9,10,11,12,13,14,15);
        passLengthChoiceBox = new ChoiceBox<>(passwordLengths);
		passLengthChoiceBox.setValue(8);

		//---------------- Password Length with TextField ----------------
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
	

	
	public void setupLayout()
	/*---------------------------------------
	* Author(s): 	Michael Lofton
	* Date:			xx-xx-2018
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

	

	public void addWidgetsToLayout()
	/*---------------------------------------
	* Author(s): 	Michael Lofton
	* Date:			xx-xx-2018
	* 
	* 
	*/
	{
		//Text Area - Password Text Area
		gridLayout.setRowIndex(passwordTextArea, 1);
		gridLayout.setColumnIndex(passwordTextArea, 0);
		gridLayout.setRowSpan(passwordTextArea, 1);
		gridLayout.setColumnSpan(passwordTextArea, 2);


		/*Button - Copy Button
		gridLayout.setRowIndex(HBoxCopyButton, 2);
		gridLayout.setColumnIndex(HBoxCopyButton, 1);*/
		//gridLayout.setColumnSpan(copyButton, 2);

		//Text - Select Password Length
		gridLayout.setRowIndex(passwordLengthText, 2);
		gridLayout.setColumnIndex(passwordLengthText, 0);
		gridLayout.setColumnSpan(passwordLengthText, 2);

		/*VBOX - ChoiceBox Characters
			/Number of Characters Radio Button
			/Character Length Choice box*/
		gridLayout.setRowIndex(VBoxChoiceBoxCharLength, 3);
		gridLayout.setColumnIndex(VBoxChoiceBoxCharLength, 0);

		/*VBOX - TextField Charactersm
			/Need more characters Radio Button
			/Textfield */
		gridLayout.setRowIndex(VBoxTextFieldCharLength, 3);
		gridLayout.setColumnIndex(VBoxTextFieldCharLength, 1);

        //Text - Choose Password Content
        gridLayout.setRowIndex(passwordContentText, 4);
        gridLayout.setColumnIndex(passwordContentText, 0);

		//Checkbox - Lowercase Letters
		gridLayout.setRowIndex(lowercaseCheckBox, 5);
		gridLayout.setColumnIndex(lowercaseCheckBox, 0);

		//Checkbox - Special Characters
		gridLayout.setRowIndex(specialCharCheckBox, 6);
		gridLayout.setColumnIndex(specialCharCheckBox, 0);

		//Checkbox - Uppercase Letters
		gridLayout.setRowIndex(uppercaseCheckBox, 5);
		gridLayout.setColumnIndex(uppercaseCheckBox, 1);

		//Checkbox - Numbers
		gridLayout.setRowIndex(numberCheckBox, 6);
		gridLayout.setColumnIndex(numberCheckBox, 1);

		//Button - Make Password
		gridLayout.setRowIndex(makePasswordButton, 7);
		gridLayout.setColumnIndex(makePasswordButton, 0);

		//Button - Copy Password to Computer's Clipboard
		gridLayout.setRowIndex(copyButton, 7);
		gridLayout.setColumnIndex(copyButton, 1);

		//Label - Message label when there are errors or things user should know
		gridLayout.setRowIndex(messageLabel, 8);
		gridLayout.setColumnIndex(messageLabel, 0);
		gridLayout.setColumnSpan(messageLabel, 3);


		//Add all desired widwgets/layouts to the grid layout here
		gridLayout.getChildren().addAll(passwordTextArea, passwordLengthText, VBoxChoiceBoxCharLength,
										VBoxTextFieldCharLength, passwordContentText, lowercaseCheckBox,
										specialCharCheckBox, uppercaseCheckBox, numberCheckBox, makePasswordButton,
										copyButton, messageLabel);
		//Add widgets to the borderpane layout
		menuLayoutBorderPane.setCenter(gridLayout);
		menuLayoutBorderPane.setTop(menuBar);

		//add the layout that has all added widgets to the View object using the public method inherited from Parent
		this.getChildren().add(menuLayoutBorderPane);
	}
	//---------------------------------------


	public View()
	{
		createWidgets();
		setupLayout();
		addWidgetsToLayout();

		//Recreate view.fxml in this file
		//Create an instasnce of this object and call appropriate methods from main
		//Create widgets, create layouts, put widgets in layouts, etc
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

	public MenuItem getMaxLengthMenuItem()
	{
		return maxLengthMenuItem;
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

	public void setSavePassToPCButton(Button givenButton)
	{
		savePassToPCButton = givenButton;
	}

	public void setMessageLabel(Label givenLabel)
	{
		messageLabel = givenLabel;
	}



	public void showChildren()
	{
		if (debug == true)
		{
			System.out.println("Showing children");
			System.out.println(this.getChildrenUnmodifiable());
		}
	}
	
}
