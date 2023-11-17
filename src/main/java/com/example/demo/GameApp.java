package com.example.demo;

// Aaron Pelto
// CST-183
// Fall 2023

/*
Write a Java application that acts as a "front-end" GUI to set preferences for a computer game. Be sure to
use JavaFX components for the interface.

Include the following components for user data entry:
    ● Text field to enter a username for the player. Include appropriate labels.
    ● Check box indicating whether or not the user wants emails or text messages from the game company.
    ● Radio button group for choice for game complexity level (Beginner, Experienced, Advanced,Expert).
    ● Slider bar to provide the means to select the game speed. Create a slider bar range from 0 ... 100.
    ● Drop-down list for identifying the user's choice for color of their game piece.
      Include at least five colors of your choice
    ● Text area that will display the current values for the complexity level (via radio buttons),
      game speed (via slider), and color (via drop-down list).
      This implies listeners to update the text in the text any time these components are updated.
    ● Button to "submit" or "send the information"

------------------------------------------------------------------------------------------------------------------------


You may choose any layout management scheme you would like for this program but work to define a
clean, intuitive, and organized interface. There is much room for creativity within these specifications, so
feel free to embellish as you wish. Additional features are required for behavior of the form:
    ● Add use of JavaFX .css for at least one formatting component of your form
    ● Add a feature that will capture and store the current (actual) date and time when the "submit" button is pressed to update preferences.
    ● Add at least one usage of JavaFX drawing.
     This could include a (simple) game logo or just a shape or two to embellish the form.
    ● Be sure your interface/class is set up to handle an immediate user button click.
      Have default values or settings included to avoid any runtime exceptions from this action.

------------------------------------------------------------------------------------------------------------------------
Build a GamePrefs class behind this application to manage the data and processing related to the user
choices.
Be sure your class includes at least one constructor, set/get methods, and a toString() method (that returns all collected info as one String object).
When the "submit" button is pressed, collect the input from the interface and "set" the data into one object of your GamePrefs class.

Then, pull the data from the object and display a summary of the entire submission.
Use the GamePrefs class toString() method to return this summary of the data.

Utilize a JavaFX Alert action (no JOptionPane, please) for the output using the information returned from the toString() method call.
 */

// Imports
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class GameApp extends Application {

    // Imports for the GUI
    // These two variables handle the difficulty passed to the GamePrefs class
    private String difficulty;
    private ToggleGroup difficultyToggleGroup;

    // These are the remaining uses for the GUI
    private TextField userNameTextField;
    private CheckBox emailCheckBox;
    private CheckBox textMessageCheckBox;
    private Slider speedSlider;
    private ComboBox<String> colorComboBox;
    private TextArea displayTextArea;


    // Main Method with the Launch Args for JavaFX Application
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        // Application Title
        primaryStage.setTitle("Start your Game");

        // Importing the Image for the Logo
        Image gameLogo = new Image(new FileInputStream("crazygamelogo.PNG"));
        ImageView imageView = new ImageView(gameLogo);
        imageView.setX(50);
        imageView.setY(25);
        imageView.setFitHeight(250);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);


        userNameTextField = createUserNameTextField();
        createNotificationComponents();
        emailCheckBox.setSelected(true);

        createDifficultyRadioButtons();

        speedSlider = createSpeedSlider();
        colorComboBox = createColorComboBox();
        displayTextArea = createDisplayTextArea();
        Label notificationLabel = createNotificationLabel();
        Button submitButton = createSubmitButton();

        // Create layout and add components
        GridPane gridPane = createGridPane(
                userNameTextField,
                notificationLabel,
                emailCheckBox,
                textMessageCheckBox,
                speedSlider,
                colorComboBox,
                submitButton,
                displayTextArea,
                imageView
        );

        // Set up the scene
        // This is 720p
        Scene scene = new Scene(gridPane, 780, 480);

        //What did we learn with Maven and JavaFX
        // You have to put the style.css file in the Resources, .com folder
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        primaryStage.setScene(scene);

        // Update Text Area at launch
        updateDisplayTextArea();

        // Show the stage
        primaryStage.show();

        // CSS Style Sheet Implementation
        gridPane.getStyleClass().add("main-grid");
        submitButton.getStyleClass().add("submit-button");
        displayTextArea.getStyleClass().add("display-area");
    }

    private GridPane createGridPane(
            TextField userNameTextField,
            Label notificationLabel,
            CheckBox emailCheckBox,
            CheckBox textMessageCheckBox,
            Slider speedSlider,
            ComboBox<String> colorComboBox,
            Button submitButton,
            TextArea displayTextArea,
            ImageView logoImageView
    ) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        // Adjust column constraints
        // This helps keep the UI clean-ish
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);

        gridPane.getColumnConstraints().addAll(col1, col2);

        gridPane.add(logoImageView, 1, 0);


        // Username GridPane Layout
        gridPane.add(new Label("User Name:"), 0, 1);
        gridPane.add(userNameTextField, 1, 1);

        // Notification for Email and Text Messages
        gridPane.add(notificationLabel, 0, 2, 2, 1);
        gridPane.add(new Label("Communication Methods:"), 0, 3);
        gridPane.add(emailCheckBox, 1, 3);
        gridPane.add(textMessageCheckBox, 1, 4);

        // Difficulty Radio Buttons with HBox
        gridPane.add(new Label("Difficulty:"), 0, 5);
        gridPane.add(createDifficultyRadioButtons(), 1, 5);

        // Game Speed Slider
        gridPane.add(new Label("Game Speed:"), 0, 6);
        gridPane.add(speedSlider, 1, 6);

        // Color Selection Combo Box
        gridPane.add(new Label("Color Selection:"), 0, 7);
        gridPane.add(colorComboBox, 1, 7);

        // Start Game Submit Button
        gridPane.add(submitButton, 1, 8);


        // Listening for the text fields in Text Area
        gridPane.add(displayTextArea, 0, 9, 2, 1);

        return gridPane;
    }

    private TextField createUserNameTextField() {
        userNameTextField = new TextField();
        userNameTextField.setPromptText("Enter your username");
        userNameTextField.setText("Default User");

        userNameTextField.textProperty().addListener((observable, oldValue, newValue) -> updateDisplayTextArea());

        return userNameTextField;
    }

    // This method creates the notification text boxes
    // Since this is an entire UI component related to how the user interacts with the program for notifications
    private void createNotificationComponents() {
        textMessageCheckBox = new CheckBox("Text Messages");
        emailCheckBox = new CheckBox("Emails");

        Label notificationLabel = new Label("Do you want to receive communications from GameCompany?");

        // Listener for the notification label
        textMessageCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                notificationLabel.setVisible(newValue));
        emailCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                notificationLabel.setVisible(newValue));

        notificationLabel.setVisible(textMessageCheckBox.isSelected() || emailCheckBox.isSelected());

        emailCheckBox.setOnAction(e -> updateDisplayTextArea());
        textMessageCheckBox.setOnAction(e -> updateDisplayTextArea());
    }

    // because I wanted to make sure the spacing was clean
    // This method creates the difficulty radio buttons
    // I had to convert the radio buttons to a node to be able to use them in the createGridPane method
    // This changes it from a ToggleGroup to a Node
    private Node createDifficultyRadioButtons() {
        difficultyToggleGroup = new ToggleGroup();

        RadioButton easyRadioButton = new RadioButton("Easy");
        RadioButton mediumRadioButton = new RadioButton("Medium");
        RadioButton hardRadioButton = new RadioButton("Hard");
        RadioButton impossibleRadioButton = new RadioButton("Impossible");

        // Set IDs
        easyRadioButton.setId("easyRadioButton");
        mediumRadioButton.setId("mediumRadioButton");
        hardRadioButton.setId("hardRadioButton");
        impossibleRadioButton.setId("impossibleRadioButton");

        difficultyToggleGroup .getToggles().addAll(easyRadioButton, mediumRadioButton, hardRadioButton, impossibleRadioButton);

        // Set default selection to "Medium"
        difficultyToggleGroup .selectToggle(mediumRadioButton);

        // The listener for the difficulty radio buttons
        difficultyToggleGroup .selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedRadioButton = (RadioButton) newValue;
                difficulty = selectedRadioButton.getText();
                updateDisplayTextArea();
            }
        });

        // I  had to find  some information on this
        // I wanted to make sure the radio buttons were in a horizontal box
        // https://stackoverflow.com/questions/54370226/switching-from-a-horizontal-to-a-vertical-layout-with-three-radio-buttons
        // similar stack overflow issue but gave me the idea to research
        HBox difficultyBox = new HBox(10);
        difficultyBox.getChildren().addAll(easyRadioButton, mediumRadioButton, hardRadioButton, impossibleRadioButton);

        return difficultyBox;
    }


    // Game Speed Slider
    // Pretty straight forward
    private Slider createSpeedSlider() {
        // Sets the slider properties
        Slider slider = new Slider(0, 100, 50);

        // We need the tick marks to show the various steps of the slider
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);

        // We will only move the game difficulty by increments of 10
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(0);

        // Snap to Ticks ensures its only 10% increments
        slider.setSnapToTicks(true);
        slider.setBlockIncrement(10);

        // Listener for the slider
        // Ensuring that it always returns 1 and not 0
        // Game speed cant be 0!
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                slider.setValue(1);
            }
            updateDisplayTextArea();
        });

        return slider;
    }

    // Combo Box for the color selection
    // I just used the Rainbow colors
    private ComboBox<String> createColorComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet");
        comboBox.setValue("Red"); // Set Red as the default color

        // Since this is just a combo box with a string listener, we can just use the lambda expression
        comboBox.setOnAction(e -> updateDisplayTextArea());

        colorComboBox = comboBox;

        return comboBox;
    }

    private TextArea createDisplayTextArea() {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        return textArea;
    }

    // This is the notification label for the GameCompany GUI
    private Label createNotificationLabel() {
        Label label = new Label("Do you want to receive communications from GameCompany?");
        label.setStyle("-fx-font-weight: bold");
        return label;
    }


    // This method creates the submit button
    // The button submits the GamePref object data
    // The alert at the end shows the details submitted
    private Button createSubmitButton() {
        Button submitButton = new Button("Start Game");
        submitButton.setOnAction(e -> {

            String userName = userNameTextField.getText().trim();

            // Continue with submission logic
            boolean receiveEmails = emailCheckBox.isSelected();
            boolean receiveTextMessages = textMessageCheckBox.isSelected();
            double speed = speedSlider.getValue();
            String color = colorComboBox.getValue();

            // Create GamePrefs object
            GamePrefs gamePrefs = new GamePrefs(userName, receiveEmails, receiveTextMessages, difficulty, speed, color);

            // Display user selections in the TextArea
            displayTextArea.setText(formatUserSelections(gamePrefs));

            showAlert("Game Preferences", formatUserSelections(gamePrefs));
        });
        return submitButton;
    }

    // Because I wanted to format the text submitted I used this method
    // I wanted to make sure the date and time were formatted correctly for American Users

    private String formatUserSelections(GamePrefs gamePrefs) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");


        // We  get the getters so we can ensure that we are returning something
        return "User Name: " + gamePrefs.getUserName() +
                "\n\nCommunication Methods" +
                "\nText: " + (gamePrefs.isReceiveTextMessages() ? "Yes" : "No") +
                "\nEmail: " + (gamePrefs.isReceiveEmails() ? "Yes" : "No") +
                "\n\nDifficulty: " + gamePrefs.getDifficulty() +
                "\n\nGame Speed: " + gamePrefs.getGameSpeed() +
                "\n\nColor Selection: " + gamePrefs.getColorSelection() +
                "\n\nDate: " + gamePrefs.getTimestamp().format(dateFormatter) +
                "\nTime: " + gamePrefs.getTimestamp().format(timeFormatter);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // This is how I will update the text areas
    // I used the listener for the text fields here as well
    private void updateDisplayTextArea() {
        boolean receiveEmails = emailCheckBox.isSelected();
        boolean receiveTextMessages = textMessageCheckBox.isSelected();

        String userName = userNameTextField.getText().trim();

        double speed = speedSlider.getValue();
        String color = colorComboBox.getValue();

        // Radio Buttons are incredibly frustrating
        RadioButton selectedRadioButton = (RadioButton) difficultyToggleGroup.getSelectedToggle();
        difficulty = selectedRadioButton.getText();

        // Create GamePrefs object
        GamePrefs gamePrefs = new GamePrefs(userName, receiveEmails, receiveTextMessages, difficulty, speed, color);

        // Display all the options in the area text.
        displayTextArea.setText(gamePrefs.toString());
    }
}