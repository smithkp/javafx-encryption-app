import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;
    Scene mainScene;


    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage mainstage) {
        window = mainstage;
        window.setResizable(false);
        window.setTitle("AES File Encryption");

        Font chewy = Font.loadFont(Main.class.getResource("./resources/Chewy-Regular.ttf").toExternalForm(),34);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5,5,5,5));
        grid.setVgap(10);
        grid.setHgap(10);

        //Adds large title text
        Text title = new Text("Encryption");
        title.getStyleClass().add("title");
        title.setTextAlignment(TextAlignment.CENTER);
        GridPane.setConstraints(title,6,0);


        //Defines encryption button and position on grid
        Button encryptButton = new Button("Encrypt");
        encryptButton.setTextAlignment(TextAlignment.CENTER);
        encryptButton.getStyleClass().add("encrypt-btn");
        encryptButton.setMaxWidth(170);

        //Displays encryption window when encryption button is clicked on starting window
        encryptButton.setOnAction(e->{
            EncryptWindow ew = new EncryptWindow();
            ew.display("Encryption");
        });

        GridPane.setConstraints(encryptButton,6,10);

        //Defines decryption button and position on grid
        Button decryptButton = new Button("Decrypt");
        decryptButton.setTextAlignment(TextAlignment.CENTER);
        decryptButton.getStyleClass().add("decrypt-btn");
        decryptButton.setMaxWidth(170);
        GridPane.setConstraints(decryptButton,6,16);

        //Displays decryption window when decryption button is clicked on starting window
        decryptButton.setOnAction(e->{
            DecryptWindow dw = new DecryptWindow();
            dw.display("Decryption");
        });


        grid.getChildren().addAll(encryptButton,decryptButton,title);
        mainScene = new Scene(grid,310,400);
        mainScene.getStylesheets().add("style.css");
        window.setScene(mainScene);


        window.show();



    }


}
