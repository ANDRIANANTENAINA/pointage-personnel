package l2.projet.pointagepersonnel.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import l2.projet.pointagepersonnel.dao.EmployeeDAO;
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
    private Button checkPresent;

    @FXML
    private TableView<Pointage> tableView;

    @FXML
    private TextField textFieldSearch;

    private boolean isAbsent;

    private ObservableList<Pointage> pointages;

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
        pointages = PointageDAO.getPointage(datePointagePicker.getValue());

        tableView.setItems(pointages);
    }

    @FXML
    void handlePointage(MouseEvent event) {
        Pointage pointageSelected = tableView.getSelectionModel().getSelectedItem();
        if (pointageSelected == null) {
            MainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner un employé à pointer.");
            return;
        }
        if(datePointagePicker.getValue() == null) {
            MainController.showAlert(Alert.AlertType.WARNING, "Aucune Date", "Aucune date sélectionnée", "Veuillez sélectionner une date.");
            return;
        }
        try {
            // set all employee to "non" except the selected employee
            // and - 10 000
            for(Pointage pointage: this.pointages){
                if(pointageSelected.getNumEmpl() == pointage.getNumEmpl()) {
                    if(pointage.getDatePointage() == null)
                        PointageDAO.insertPointage(pointageSelected.getNumEmpl(), "oui" , datePointagePicker.getValue());
                    else if (pointage.getPointage() != null) {
                        if(pointage.getPointage().equals("non")){
                            PointageDAO.updatePointage(pointageSelected.getNumPointage(), "oui");
                            EmployeeDAO.updateEmployeeP(pointage.getNumEmpl(), pointage.getSalaire() + 10000);
                        }
                    }
                }else{
                    if(pointage.getDatePointage() == null){
                        PointageDAO.insertPointage(pointage.getNumEmpl(), "non" , datePointagePicker.getValue());
                        EmployeeDAO.updateEmployeeP(pointage.getNumEmpl(), pointage.getSalaire() - 10000);
                    }
                }
            }
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
            EmployeeDAO.updateEmployeeP(pointage.getNumEmpl(), pointage.getSalaire() + 10000);
            setTablePointage();
        } catch (SQLException | ClassNotFoundException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression", "Une erreur est survenue lors de la modification du pointage.");
        }
    }

//   "oui" to "non"
    @FXML
    void handleUpdatePointage(MouseEvent event) {
        Pointage pointage = tableView.getSelectionModel().getSelectedItem();
        if (pointage == null) {
            MainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner le pointage à modifier.");
            return;
        }
        try {
            if(pointage.getPointage().equals("oui")){
                PointageDAO.updatePointage(pointage.getNumPointage(), "non");
                EmployeeDAO.updateEmployeeP(pointage.getNumEmpl(), pointage.getSalaire() - 10000);
            }
            setTablePointage();
        } catch (SQLException | ClassNotFoundException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la maodification", "Une erreur est survenue lors de la suppression du pointage.");
        }
    }


    @FXML
    void onClickAbsent(MouseEvent event) {
        isAbsent = !isAbsent;

        if(isAbsent)
            tableView.setItems(PointageDAO.searchPointagesIsAbsent("non", datePointagePicker.getValue()));
        else {
            try {
                setTablePointage();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
