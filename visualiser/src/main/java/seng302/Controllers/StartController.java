package seng302.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng302.RaceMode;
import seng302.RaceObjects.Race;
import seng302.RaceObjects.ViewScreenType;
import seng302.UserInput.KeyBindingUtility;

import java.io.IOException;

public class StartController {

    @FXML private JFXButton connectBtn;
    @FXML private JFXTextField ipField;
    @FXML private JFXTextField portField;
    @FXML private ImageView logoImageView;
    @FXML private Label statusLbl;
    @FXML private Text fullMastText;
    @FXML private JFXToggleButton RaceModeButton;
    @FXML private JFXToggleButton AgarModeButton;
    @FXML private JFXToggleButton PracModeButton;
    @FXML private JFXToggleButton CustModeButton;

    private Stage primaryStage;
    private Scene rootScene;
    private Race race;
    private ToggleGroup modeGroup;
    private ClientController clientController;

    public StartController() {
        Coordinate.setWindowWidthX(800);
        Coordinate.setWindowHeightY(600);
    }

    @FXML
    public void initialize(){
        statusLbl.setText("");

        fullMastText.setFocusTraversable(true);

        modeGroup = new ToggleGroup();
//        AgarModeButton.getCssMetaData().stream().map(CssMetaData::getProperty).forEach(System.out::println);

        AgarModeButton.setSelected(true);

        modeGroup.getToggles().add(RaceModeButton);
        modeGroup.getToggles().add(AgarModeButton);
        modeGroup.getToggles().add(PracModeButton);
        modeGroup.getToggles().add(CustModeButton);

        ipField.visibleProperty().bind(CustModeButton.selectedProperty());
        portField.visibleProperty().bind(CustModeButton.selectedProperty());

        connectBtn.disableProperty().bind(modeGroup.selectedToggleProperty().isNull());
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

        switch (raceMode){
            case AGAR: {
//                String ip = "http://132.181.16.12"; //Turn me on for production
//                String ip = "http://127.0.0.1";
                String ip = "http://132.181.12.107";
//                String ip = "http://132.181.12.107";
                int port = raceMode.getPort();
                connectLobby(ip, port, raceMode);
                break;
            } case PRACTICE: {
                String ip = "http://127.0.0.1";
                int port = raceMode.getPort();
                connectPractice(ip, port, raceMode);
                break;
            } case RACE: {
//                String ip = getIp();
                String ip = "http://132.181.16.12";
//                String ip = "http://127.0.0.1";
                int port = raceMode.getPort();
                connectLobby(ip, port, raceMode);
                break;
            }case CUSTOM: {
                String ip = getIp();
                int port = getPort();
                connectLobby(ip, port, raceMode);
                break;
            } default: {
                String ip = "http://127.0.0.1";
                int port = RaceMode.RACE.getPort();
                connectLobby(ip, port, raceMode);
            }
        }
    }

    private void connectLobby(String ip, int port, RaceMode raceMode) throws InterruptedException {
        Platform.runLater(
                () -> statusLbl.setText("connecting...")
        );

        clientController = new ClientController(ip, port);
        clientController.startClient();
        race = clientController.getRace();

        race.setRaceMode(raceMode);

        race.connectedToServerProperty().addListener((Observable, OldValue, isConnected) -> {
            if (isConnected.equals(1)) { // connected
                Platform.runLater(
                        () -> {
                            try {
                                newConnection(race);
                            } catch (InterruptedException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
        });

        race.viewScreenProperty().addListener((observable, oldValue, view) -> {
            if (view.equals(ViewScreenType.MENU.getViewScreenType())) { // disconnected
                Platform.runLater(
                        () -> {
                            try {
                                exitToMenu("");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
            } else if (view.equals(ViewScreenType.MENU_ERROR.getViewScreenType())) {
                Platform.runLater(
                    () -> {
                        try {
                            newConnection(race);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                );

            } else if (view.equals(ViewScreenType.MENU_SERVER_CLOSED.getViewScreenType())) {
                Platform.runLater(
                        () -> {
                            try {
                                exitToMenu("Server Closed");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );

            } else if (view.equals(ViewScreenType.SCORE_SCREEN.getViewScreenType())) {
                Platform.runLater(
                        () -> {
                            try {
                                showScoreScreen();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
        });
    }

    private void connectPractice(String ip, int port, RaceMode raceMode) throws InterruptedException {
        Platform.runLater(
                () -> statusLbl.setText("connecting...")
        );

        PracticeClientController clientController = new PracticeClientController(ip, port, primaryStage, rootScene);
        clientController.startClient();
        race = clientController.getRace();

        race.setRaceMode(raceMode);

        race.connectedToServerProperty().addListener((observable, oldValue, isConnected) -> {
            if (isConnected.equals(0)) { // disconnectedRace
                Platform.runLater(
                        () -> {
                            try {
                                exitToMenu("Race finishing");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
            } else if (isConnected.equals(1)) { // connected
                Platform.runLater(
                        () -> {
                            try {
                                int i =0;
                                while (!race.isRaceReady()){
                                    Thread.sleep(2);
                                    //TODO This is a bad blocking call
                                    i +=1;
                                }
                                System.out.println("Ran a loop " + i + " times!");
                                practiceView(race);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                );
            } else if (isConnected.equals(2)) { //failed to connect
                Platform.runLater(
                        () -> {
                            try {
                                statusLbl.setText("Could not connect");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
        });
    }

    private void practiceView(Race race) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/RaceView.fxml"));
        RaceController raceController = new RaceController(race);
        raceController.setPrimaryStage(primaryStage);
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

    public void setStatus(String message){
        statusLbl.setText(message);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setStartScene(Scene rootScene) {
        this.rootScene = rootScene;
    }

    private String getIp() {
        String ipInput = ipField.getText();
        return  ("http://" + ipInput);
    }

    private int getPort() {
        String portInput = portField.getText();
        int port;
        try {
            port = Integer.parseInt(portInput);
        } catch (NumberFormatException badInput) {
            // using default port
            port = 4941;
        }
        return port;
    }

    public void exitToMenu(String message) throws IOException {
        clientController = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/StartingPage.fxml"));

        Parent root = loader.load();
        Scene rootScene = new Scene(root);

        StartController startController = loader.getController();
        startController.setStatus(message);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMaximized(false);
        primaryStage.setScene(rootScene);
        primaryStage.setTitle("Sail.io");
        primaryStage.show();

        startController.setPrimaryStage(primaryStage);
        startController.setStartScene(rootScene);
    }

    public void showScoreScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/ScoreScreen.fxml"));

        Parent root = loader.load();
        Scene rootScene = new Scene(root);

        ScoreScreenController scoreScreenController = loader.getController();
        scoreScreenController.setRace(race);
        scoreScreenController.initialiseTable();

        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.setMaximized(false);
        primaryStage.setScene(rootScene);
        primaryStage.setTitle("Sail.io");
        primaryStage.show();

        scoreScreenController.setPrimaryStage(primaryStage);
    }

    public RaceMode getRaceMode() {
        ToggleButton a = (ToggleButton) modeGroup.getSelectedToggle();
        String mode = a.getText().toLowerCase().trim();
        return RaceMode.getRaceMode(mode);
    }
}
