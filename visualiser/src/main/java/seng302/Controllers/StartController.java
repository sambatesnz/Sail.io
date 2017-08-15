package seng302.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng302.RaceObjects.Race;
import seng302.UserInput.KeyBindingUtility;

import java.io.IOException;

public class StartController {

    @FXML private Button connectBtn;
    @FXML private Stage primaryStage;
    @FXML private TextField ipField;
    @FXML private Label statusLbl;

    public StartController() {
//        this.primaryStage = mainStage;

        Coordinate.setWindowWidthX(800);
        Coordinate.setWindowHeightY(600);
//
//        mainStage.widthProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowWidthX((newValue).doubleValue()));
//        mainStage.heightProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowHeightY(newValue.doubleValue()));
    }

    @FXML
    public void initialize(){
        ipField.setText("localhost:4941");
        statusLbl.setText("");
    }


    /**
     * Called when the user selects the start race button.
     *
     * Changes from the start page to the raceview.
     */
    @FXML
    public void connect() throws IOException, InterruptedException {
        Platform.runLater(
            () -> {
                statusLbl.setText("connecting...");
            }
        );
        ClientController clientController = new ClientController(getIp(), getPort());
        clientController.startClient();
            Race race = clientController.getRace();

            race.connectedToServerProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.equals(1)) {
                    Platform.runLater(
                        () -> {
                            try {
                                newConnection(race);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    );
                } else if (newValue.equals(2)) {
                    statusLbl.setText("Could not connect");
                }
            });
        }

    private void newConnection(Race race) throws InterruptedException, IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));

            LobbyController lobbyController = new LobbyController(race);
            lobbyController.setPrimaryStage(primaryStage);
            loader.setController(lobbyController);
            Parent root = loader.load();

            Scene rootScene = new Scene(root);
            primaryStage.setScene(rootScene);
            KeyBindingUtility.setKeyBindings(rootScene, race);

            lobbyController.initialiseTable();
            lobbyController.initialiseTime();

    }




    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Called when the user clicks the practice race start button.
     * Runs a server locally, which the client application automatically connects with using a message click.
     */
    public void practiceRace() {

    }

    private String getIp() {
        String ipInput = ipField.getText();
        String[] splitInput = ipInput.split(":");
        return  ("http://" + splitInput[0]);
    }

    private int getPort() {
        String ipInput = ipField.getText();
        String[] splitInput = ipInput.split(":");
        return (Integer.parseInt(splitInput[1]));
    }
}
