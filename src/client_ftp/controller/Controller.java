package client_ftp.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class Controller implements Runnable {

    private static final String NEWLINE = "\n";

    @FXML
    TextArea area;
    @FXML
    TextField path, command;
    @FXML
    Button button, login;
    @FXML
    ComboBox<String> combo;

    Socket clientFTP;
    BufferedReader br;
    PrintStream ps;

    int id;
    String msg;

    @FXML
    public void initialize() {
        button.setDisable(true);
        area.setEditable(false);
        path.setEditable(false);
        login.setOnAction(this::connect);
        button.setOnAction(this::command);
        command.setOnKeyPressed(this::commmandKey);

        ArrayList<String> list = new ArrayList<>();
        list.add("Default");
        list.add("Black");
        combo.setItems(FXCollections.observableArrayList(list));
        combo.setValue(list.get(0));
        combo.setOnAction(e -> {
            for (String s : list) button.getParent().getStyleClass().remove(s.toLowerCase() + "back");
            button.getParent().getStyleClass().add(combo.getValue().toLowerCase() + "-back");

            for (Node n: button.getParent().getChildrenUnmodifiable()) {
                for (String s : list) n.getStyleClass().remove(s.toLowerCase());
                n.getStyleClass().add(combo.getValue().toLowerCase());
            }
        });
    }

    private void connect(ActionEvent e) {
        area.appendText("Le Client FTP" + NEWLINE);
        login.setText("Déconnexion");
        login.setOnAction(this::deconnect);
        button.setDisable(false);
        run();
    }

    private void deconnect(ActionEvent e) {
        login.setText("Connexion");
        login.setOnAction(this::connect);
        button.setDisable(true);

        ps.println("bye" + " " + id);
        try {
            clientFTP.close();
        } catch (IOException ignored) {
            area.appendText("Déconnexion" + NEWLINE);
        }
    }

    private void command(ActionEvent e) {
        ps.println(command.getText() + " " + id);
        area.appendText("-    " + command.getText() + NEWLINE);

        try {
            while ((msg = br.readLine()).charAt(0) != '2' && msg.charAt(0) != '0') area.appendText(msg + NEWLINE);
            area.appendText(msg + NEWLINE);

            if ((command.getText().startsWith("get") || command.getText().startsWith("stor")) && msg.startsWith("0 Sur le port")) { //get & stor
                String ligne = msg.substring(msg.length() - 4);
                int port = Integer.parseInt(ligne);

                try {
                    Socket clientGetStor = new Socket("localhost", port);

                    msg = br.readLine();
                    area.appendText(msg + NEWLINE);

                    clientGetStor.close();
                } catch (IOException ex) {
                    area.appendText("Erreur lors de la connexion" + NEWLINE);
                }
            } else if (command.getText().startsWith("ls") || command.getText().startsWith("cd")) {
                path.setText(msg.split(" ")[msg.split(" ").length - 1]);
            }
        } catch (IOException ignored) {
            area.appendText("Serveur déconnecté" + NEWLINE);
        }

        if (command.getText().startsWith("bye")) {
            login.setText("Connexion");
            login.setOnAction(this::connect);
            button.setDisable(true);
        }

        command.clear();
    }

    private void commmandKey(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) command(null);
    }

    @Override
    public void run() {
        try {
            clientFTP = new Socket("localhost", 2124);
            br = new BufferedReader(new InputStreamReader(clientFTP.getInputStream()));
            ps = new PrintStream(clientFTP.getOutputStream());

            while ((msg = br.readLine()).charAt(0) != '0') area.appendText(msg + NEWLINE);
            area.appendText(msg + NEWLINE);
            id = Integer.parseInt(br.readLine());
        } catch (IOException ex) {
            area.appendText("Erreur de connexion !" + NEWLINE);
        }
    }
}