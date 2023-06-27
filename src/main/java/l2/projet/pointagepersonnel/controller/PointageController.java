package l2.projet.pointagepersonnel.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import l2.projet.pointagepersonnel.dao.PointageDAO;
import l2.projet.pointagepersonnel.model.Employe;
import l2.projet.pointagepersonnel.model.Pointage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PointageController implements Initializable {

    @FXML
    private Button btnDeletePointage;
    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdatePointage;

    @FXML
    private TableColumn<Employe, Integer> colNum;

    @FXML
    private TableColumn<Employe, String> colNom;


    @FXML
    private TableColumn<Pointage, String> colPointage;

    @FXML
    private TableColumn<Employe, String> colPrenom;

    @FXML
    private BorderPane contentArea;

    @FXML
    private DatePicker datePointagePicker;

    @FXML
    private RadioButton radioAbsent;

    @FXML
    private RadioButton radioPresent;


    @FXML
    private Button checkPresent;

    @FXML
    private TableView<Pointage> tableView;

    @FXML
    private TextField textFieldSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePointagePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setTablePointage();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            // Set default date to today
            if(datePointagePicker.getValue() == null)
                datePointagePicker.setValue(java.time.LocalDate.now());

            setTablePointage();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void searchPointage(KeyEvent event) {
        String search = ((TextField) event.getSource()).getText();
        tableView.setItems(PointageDAO.searchPointage(search, datePointagePicker.getValue()));
    }


    private void setTablePointage() throws SQLException, ClassNotFoundException {
        colNum.setCellValueFactory(cellData -> cellData.getValue().numEmplProperty().asObject());
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colPrenom.setCellValueFactory(cellData -> cellData.getValue().prenomsProperty());
        colPointage.setCellValueFactory(cellData -> cellData.getValue().pointageProperty());

        tableView.setItems(PointageDAO.getPointage(datePointagePicker.getValue()));
    }

    @FXML
    void handlePointage(MouseEvent event) {
        Pointage pointage = tableView.getSelectionModel().getSelectedItem();
        if (pointage == null) {
            MainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner un employé à pointer.");
            return;
        }
        if(datePointagePicker.getValue() == null) {
            MainController.showAlert(Alert.AlertType.WARNING, "Aucune Date", "Aucune date sélectionnée", "Veuillez sélectionner une date.");
            return;
        }
        try {
            PointageDAO.insertPointage(pointage.getNumEmpl(), "oui" , datePointagePicker.getValue());
            setTablePointage();
        } catch (SQLException | ClassNotFoundException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'insertion", "Une erreur est survenue lors de l'insertion du pointage.");
        }
    }

    @FXML
    void handleDeletePointage(MouseEvent event) {
        Pointage pointage = tableView.getSelectionModel().getSelectedItem();
        if (pointage == null) {
            MainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner le pointage à supprimer.");
            return;
        }
        try {
            PointageDAO.deletePointageWithId(pointage.getNumPointage());
            setTablePointage();
        } catch (SQLException | ClassNotFoundException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression", "Une erreur est survenue lors de la suppression du pointage.");
        }
    }

    @FXML
    void handleUpdatePointage(MouseEvent event) {

    }

    @FXML
    void onClickPresent(MouseEvent event) {

    }

    @FXML
    void onClickAbsent(MouseEvent event) {

    }

}
