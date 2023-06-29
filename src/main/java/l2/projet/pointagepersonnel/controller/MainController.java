package l2.projet.pointagepersonnel.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import l2.projet.pointagepersonnel.Application;
import l2.projet.pointagepersonnel.controller.modals.UpdateModalEmployeeController;
import l2.projet.pointagepersonnel.dao.EmployeeDAO;
import l2.projet.pointagepersonnel.model.Employe;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController{

    @FXML
    private Button btnAddEmploye, btnDeleteEmploye, btnUpdateEmploye;

    @FXML
    private TableView<Employe> tableView;

    @FXML
    private TableColumn<Employe, String> colNom;

    @FXML
    private TableColumn<Employe, String> colNum;

    @FXML
    private TableColumn<Employe, String> colPoste;

    @FXML
    private TableColumn<Employe, String> colPrenom;

    @FXML
    private TableColumn<Employe, Integer> colSalaire;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button pageConge;

    @FXML
    private Button pageEmployee;

    @FXML
    private Button pagePointage;



    private ObservableList<Employe> employeeList;

    @FXML
    public void initialize() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("components/employee.fxml")));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void afficheConge(MouseEvent event) {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("components/conge.fxml")));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void afficheEmployee(MouseEvent event) {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("components/employee.fxml")));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void affichePointage(MouseEvent event) {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("components/pointage.fxml")));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void afficheFDP(MouseEvent event) {
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