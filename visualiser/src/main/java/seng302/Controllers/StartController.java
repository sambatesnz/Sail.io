package seng302.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng302.RaceMode;
import seng302.RaceObjects.Race;
import seng302.UserInput.KeyBindingUtility;
import seng302.UserInput.PracticeMessage;

import javax.script.Bindings;
import java.io.IOException;

public class StartController {

    @FXML private Button connectBtn;
    @FXML private TextField ipField;
    @FXML private Label statusLbl;
    @FXML private Text ipLabel;
    @FXML private RadioButton RaceModeRadioButton;
    @FXML private RadioButton AgarModeRadioButton;
    @FXML private RadioButton PracticeModeRadioButton;


    private Stage primaryStage;
    private Scene rootScene;
    private ToggleGroup modeGroup;

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

        modeGroup = new ToggleGroup();

        modeGroup.getToggles().add(RaceModeRadioButton);
        modeGroup.getToggles().add(AgarModeRadioButton);
        modeGroup.getToggles().add(PracticeModeRadioButton);

        ipField.visibleProperty().bind(RaceModeRadioButton.selectedProperty());
        ipLabel.visibleProperty().bind(RaceModeRadioButton.selectedProperty());
    }


    /**
     * Called when the user selects the start race button.
     * Changes from the start page to the raceview.
     * @throws IOException socket cannot read/write
     * @throws InterruptedException one end of socket disconnected
     */
    @FXML
    public void connect() throws IOException, InterruptedException {
        RaceMode raceMode = getRaceMode();

        String ip = null;
        int port = -1;

        switch (raceMode){
            case AGAR: {
                ip = "http://132.181.16.12";
                port = raceMode.getPort();
                break;
            } case PRACTICE: {
                ip = "http://132.181.16.12";
                port = raceMode.getPort();
                break;
            } case RACE: {
                ip = getIp();
                port = getPort();
                break;
            }
        }

        System.out.println(ip);
        System.out.println(getPort());

        if (ip == null || port == -1) {
            ip = "127.0.0.1";
            port = RaceMode.RACE.getPort();
        }

        Platform.runLater(
            () -> statusLbl.setText("connecting...")
        );

        ClientController clientController = new ClientController(ip, port);
        clientController.startClient();
        Race race = clientController.getRace();

        race.connectedToServerProperty().addListener((observable, oldValue, isConnected) -> {
            if (isConnected.equals(0)) { // disconnected
                Platform.runLater(
                        () -> {
                            try {
                                exitToMenu();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
            } else if (isConnected.equals(1)) { // connected
                Platform.runLater(
                    () -> {
                        try {
                            newConnection(race);
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                );
            } else if (isConnected.equals(2)) { //failed to connect
                statusLbl.setText("Could not connect");
            }
        });
    }

    private void connectPractice() throws InterruptedException {
        Platform.runLater(
                () -> statusLbl.setText("connecting...")
        );
        ClientController clientController = new ClientController(getIp(), getPort(), primaryStage, rootScene);
        clientController.startClient();
        Race race = clientController.getRace();

        race.connectedToServerProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(1)) {
                Platform.runLater(
                        () -> {
                            try {
                                practiceView(race);
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

    private void practiceView(Race race) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/RaceView.fxml"));
        RaceController raceController = new RaceController(race);
        loader.setController(raceController);
        Parent root = loader.load();

        Scene rootScene = new Scene(root);
        primaryStage.setScene(rootScene);

        KeyBindingUtility.setKeyBindings(rootScene, race);
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

//    /**
//     * Called when the user clicks the practice race start button.
//     */
//    @FXML
//    private void practiceClick() throws IOException {
//        try {
//            connectPractice();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setStartScene(Scene rootScene) {
        this.rootScene = rootScene;
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

    public void exitToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/StartingPage.fxml"));

        Parent root = loader.load();
        Scene rootScene = new Scene(root);

        StartController startController = loader.getController();

        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMaximized(false);
        primaryStage.setScene(rootScene);
        primaryStage.setTitle("RaceView");
        primaryStage.show();

        startController.setPrimaryStage(primaryStage);
        startController.setStartScene(rootScene);
    }

    public RaceMode getRaceMode() {
        RadioButton a = (RadioButton) modeGroup.getSelectedToggle();
        String mode = a.getText().toLowerCase();
        return RaceMode.getRaceMode(mode);
    }
}
