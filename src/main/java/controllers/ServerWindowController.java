package controllers;

import Server.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ServerWindowController {

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private TextArea connectionsArea;

    @FXML
    void startButtonClicked(ActionEvent event) {
        Server server = new Server();
        connectionsArea.setText("Сервер запущен!");
    }

    @FXML
    void stopButtonClicked(ActionEvent event) {

    }

}
