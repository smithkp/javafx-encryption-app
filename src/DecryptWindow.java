import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.File;

public class DecryptWindow {

    private Stage window;
    private Scene scene;
    private GridPane grid;
    private String fileName;

    public void display(String title){
        window = new Stage();
        window.setTitle(title);
        window.setResizable(false);

        Font chewy = Font.loadFont(Main.class.getResource("./resources/Chewy-Regular.ttf").toExternalForm(),34);

        //Defines dropdown for encryption modes
        FileChooser fileChoose = new FileChooser();
        DirectoryChooser saveDest = new DirectoryChooser();
        ChoiceBox<String> modes = new ChoiceBox<>(FXCollections.observableArrayList(
                "ECB",
                "CBC",
                "CTR"

        ));
        modes.setValue("EBC");
        GridPane.setConstraints(modes,15,6);

        //defines input field for IV and its position in window
        TextField iv = new TextField();
        iv.setMaxWidth(50);
        iv.setPromptText("IV");
        GridPane.setConstraints(iv,16,6);

        GridPane.setConstraints(modes,15,6);


        //Defines window heading and position
        Label h1 = new Label("Decrypt");
        h1.getStyleClass().add("h1");
        GridPane.setConstraints(h1,15,0);

        //defines textfield and button for filepath
        TextField fileLocation = new TextField();
        fileLocation.setPromptText("File Location");
        fileLocation.setMaxWidth(170);
        GridPane.setConstraints(fileLocation,15,5);
        Button chooseBtn = new Button("Choose File");
        chooseBtn.setMaxWidth(100);
        GridPane.setConstraints(chooseBtn,16,5);

        //Defines textfield for output destination
        TextField fileDest = new TextField();
        fileDest.setPromptText("File output destination");
        fileDest.setMaxWidth(170);
        GridPane.setConstraints(fileDest,15,7);

        Button outputBtn = new Button("Destination");
        outputBtn.setMaxWidth(100);
        GridPane.setConstraints(outputBtn,16,7);

        /*Opens directory choosing window when output destination button is clicked.
         *Pastes path of chosen file in output destination input field.
         */

        outputBtn.setOnAction(e->{
            File chosenFile = saveDest.showDialog(window);
            fileDest.setText(chosenFile.getPath());
            fileName = chosenFile.getName();
        });

        /*Opens file choosing window when choose file button is clicked.
         *Pastes path of chosen file in file destination input field.
         */

         chooseBtn.setOnAction(e->{
             File chosenFile = fileChoose.showOpenDialog(window);
             fileLocation.setText(chosenFile.getPath());
         });

        TextField keyInput = new TextField();
        keyInput.setPromptText("128-bit key");
        keyInput.setMaxWidth(170);
        GridPane.setConstraints(keyInput,15,9);

        Button decryptBtn = new Button("Decrypt");
        decryptBtn.setMaxWidth(180);
        GridPane.setConstraints(decryptBtn,15,14);

        /*
         *decrypts file when clicked and saves to directory chosen
         */
        decryptBtn.setOnAction(e->{
            try {
                Enkript d = new Enkript(modes.getValue());
                d.decryptFile(d.grabFile(fileLocation.getText()), modes.getValue(),fileDest.getText(),fileName,keyInput.getText(),iv.getText());
            }
            catch (Exception err){

            }
        });

        grid = new GridPane();
        grid.setPadding(new Insets(5,5,5,5));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.getChildren().addAll(h1,fileDest,modes,fileLocation,chooseBtn,outputBtn,decryptBtn,keyInput,iv);

        scene = new Scene(grid,480,380);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.show();
    }

}
