package l2.projet.pointagepersonnel.controller.modals;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import l2.projet.pointagepersonnel.controller.MainController;
import l2.projet.pointagepersonnel.dao.CongeDAO;
import l2.projet.pointagepersonnel.dao.EmployeeDAO;
import l2.projet.pointagepersonnel.model.Conge;
import l2.projet.pointagepersonnel.model.Employe;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;



public class AddModalCongeController implements Initializable {
    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuller;

    @FXML
    private ComboBox<String> combiBoxConge;

    @FXML
    private DatePicker datePickerDemande;

    @FXML
    private DatePicker datePickerRetour;

    @FXML
    private TextArea textAreaMotif;

    private Integer numEmpl = null;

    @FXML
    private Text textNotif;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // onChage date, set text notif to empty
        datePickerDemande.valueProperty().addListener((observable, oldValue, newValue) -> {
            textNotif.setText("");
        });

        datePickerRetour.valueProperty().addListener((observable, oldValue, newValue) -> {
            textNotif.setText("");
        });
        loadEmployeeComboBox();
        datePickerDemande.setValue(LocalDate.now());
        combiBoxConge.valueProperty().addListener((observable, oldValue, newValue) -> {
            numEmpl = Integer.valueOf(combiBoxConge.getSelectionModel().getSelectedItem().split("-")[0]);
            textNotif.setText("");
        });
    }

    @FXML
    void handleClickCancel(ActionEvent event) {
        Stage stage = (Stage) btnAnnuller.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleClickValided(ActionEvent event) {
        if(numEmpl == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Employee vide", "Veuillez selectionner un employeé!");
        } else {
            try {
                LocalDate dateDemande = datePickerDemande.getValue();
                LocalDate dateRetour = datePickerRetour.getValue();

                int nombreJr = dateRetour.getDayOfYear() - dateDemande.getDayOfYear();

                ObservableList<Conge> listConge = CongeDAO.getCongeByEmpl(numEmpl);
                int totalConge = 0;
                for(Conge conge: listConge){
                    totalConge = totalConge + conge.getNbjr();
                }
                int total =  nombreJr + totalConge ;

                if ( total >= 30){
                    textNotif.setText("Le nombre de jour de conge depasse 30 jours, vous avez déjà "+ totalConge +" jours de congé");
                    return;
                }


                CongeDAO.insertConge(numEmpl, nombreJr, textAreaMotif.getText(), dateDemande, dateRetour);
                Stage stage = (Stage) btnAjouter.getScene().getWindow();
                stage.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            };
        }
    }

    void loadEmployeeComboBox(){
        ObservableList<Employe> employee;
        List<String> employeObservableList = new ArrayList<>();
        try {
            employee = EmployeeDAO.getEmployees();
            for(Employe empl : employee){
                String element = empl.getNumEmpl() +"-"+ empl.getNom() +" "+ empl.getPrenoms();
                employeObservableList.add(element);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        combiBoxConge.getItems().addAll(employeObservableList);

    }

}
