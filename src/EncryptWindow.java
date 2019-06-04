
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;
import java.io.File;


import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.text.Font;

public class EncryptWindow {

     private Stage window;
     private Scene scene;
     private GridPane grid;

    public void display(String title){
        window = new Stage();
        window.setResizable(false);
        window.setTitle(title);



        Font.loadFont(Main.class.getResource("./resources/Chewy-Regular.ttf").toExternalForm(),34);

        //Defines dropdown for encryption modes
        DirectoryChooser saveDest = new DirectoryChooser();
        ChoiceBox<String> modes = new ChoiceBox<>(FXCollections.observableArrayList(
                "ECB",
                "CBC",
                "CTR"

        ));
        modes.setValue("CBC");

        GridPane.setConstraints(modes,15,6);

        //Defines window heading and position
        Label h1 = new Label("Encrypt");
        h1.getStyleClass().add("h1");
        GridPane.setConstraints(h1,15,0);

        //defines textfield and button for filepath
        TextField fileDest = new TextField();
        fileDest.setPromptText("File destination");
        fileDest.setMaxWidth(170);
        GridPane.setConstraints(fileDest,15,5);
        Button chooseBtn = new Button("Choose File");
        chooseBtn.setMaxWidth(100);
        GridPane.setConstraints(chooseBtn,16,5);

        //Defines textfield for output destination
        TextField outputDest = new TextField();
        outputDest.setPromptText("File output destination");
        outputDest.setMaxWidth(170);
        GridPane.setConstraints(outputDest,15,7);

        TextField fileName = new TextField();
        fileName.setPromptText("File name with extension");
        fileName.setMaxWidth(170);
        GridPane.setConstraints(fileName,15,9);

        Button outputBtn = new Button("Destination");
        outputBtn.setMaxWidth(100);
        GridPane.setConstraints(outputBtn,16,7);

        FileChooser fileChoose = new FileChooser();



        /*Opens file choosing window when choose file button is clicked.
         *Pastes path of chosen file in file destination input field.
         */
        chooseBtn.setOnAction(e->{
            File chosenFile = fileChoose.showOpenDialog(window);
            fileDest.setText(chosenFile.getPath());
            fileName.setText(chosenFile.getName());
        });



        /*Opens directory choosing window when output destination button is clicked.
         *Pastes path of chosen file in output destination input field.
         */

        outputBtn.setOnAction(e->{
            File chosenFile = saveDest.showDialog(window);
            outputDest.setText(chosenFile.getPath());
        });

        //Defines encrypt button and position in grid layout.
        Button encryptBtn = new Button("Encrypt");
        encryptBtn.setMaxWidth(180);
        GridPane.setConstraints(encryptBtn,15,14);

        /*
         *encrypts file when clicked and saves to directory chosen
         */
        encryptBtn.setOnAction(e->{
            try {
                Enkript encrypt = new Enkript(modes.getValue());
                encrypt.encryptFile(encrypt.grabFile(fileDest.getText()),outputDest.getText(),fileName.getText());


            }
            catch (Exception err){

            }

        });

        grid = new GridPane();
        grid.setPadding(new Insets(5,5,5,5));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.getChildren().addAll(h1,fileDest,modes,outputDest,chooseBtn,outputBtn,encryptBtn,fileName);

        scene = new Scene(grid,480,380);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.show();

    }

}