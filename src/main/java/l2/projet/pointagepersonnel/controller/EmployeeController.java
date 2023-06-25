package l2.projet.pointagepersonnel.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import l2.projet.pointagepersonnel.Application;
import l2.projet.pointagepersonnel.controller.modals.UpdateModalEmployeeController;
import l2.projet.pointagepersonnel.dao.EmployeeDAO;
import l2.projet.pointagepersonnel.model.Employe;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class EmployeeController {
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
    public void initialize() {
        setTableEmployee();
    }

    @FXML
    void searchEmployee(KeyEvent event) throws SQLException, ClassNotFoundException {
        String search = ((TextField) event.getSource()).getText();
        tableView.setItems(EmployeeDAO.searchEmployees(search));
    }

    private void setTableEmployee() {
        colNum.setCellValueFactory(cellData -> cellData.getValue().numEmplProperty().asObject().asString());
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colPrenom.setCellValueFactory(cellData -> cellData.getValue().prenomsProperty());
        colPoste.setCellValueFactory(cellData -> cellData.getValue().posteProperty());
        colSalaire.setCellValueFactory(cellData -> cellData.getValue().salaireProperty().asObject());

        try {
            tableView.setItems(EmployeeDAO.getEmployees());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifyEmployee(MouseEvent event) {
        Employe employe = tableView.getSelectionModel().getSelectedItem();
        if (employe == null) {
            MainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner un employé à éditer.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(Application.class.getResource("modals/editModalEmployee.fxml")));
            Parent root = loader.load();

            UpdateModalEmployeeController tmpController = loader.getController();
            tmpController.setEmployee(employe);

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Affecter un employé");
            modalStage.setScene(new Scene(root));
            modalStage.showAndWait();
            initialize();

            initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void deleteEmployee(MouseEvent event) {
        Employe employe = tableView.getSelectionModel().getSelectedItem();
        if (employe == null) {
            MainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner un employé à supprimer.");
            return;
        }
        try {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText("Suppression de l'employé");
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer l'employé sélectionné ?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                EmployeeDAO.deleteEmployee(String.valueOf(employe.getNumEmpl()));
                initialize();;
            }else {
                confirmationAlert.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void handleButtonClick(ActionEvent event) {
        if (event.getSource() == btnAddEmploye) {
            showAsDialog();
        } else if (event.getSource() == btnDeleteEmploye) {
            System.out.println("Delete");
        } else if (event.getSource() == btnUpdateEmploye) {
            System.out.println("Update");
        }
    }

    private void showAsDialog() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("modals/addModalEmployee.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
//            stage.initStyle(StageStyle.UNDECORATED);
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
