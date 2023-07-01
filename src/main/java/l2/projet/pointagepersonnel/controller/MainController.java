package l2.projet.pointagepersonnel.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import l2.projet.pointagepersonnel.Application;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController{

    @FXML
    private StackPane contentArea;


    @FXML
    public void initialize() {
        loadFxmlFile();
    }

    private void loadFxmlFile() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("components/employee.fxml")));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void afficheConge() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("components/conge.fxml")));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void afficheEmployee() {
        loadFxmlFile();
    }

    @FXML
    void affichePointage() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("components/pointage.fxml")));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void afficheFDP() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("components/ficheDepaie.fxml")));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void showAlert(Alert.AlertType alertType, String erreur, String champsVides, String s) {
        Alert alert = new Alert(alertType);
        alert.setTitle(erreur);
        alert.setHeaderText(champsVides);
        alert.setContentText(s);
        alert.showAndWait();
    }
}