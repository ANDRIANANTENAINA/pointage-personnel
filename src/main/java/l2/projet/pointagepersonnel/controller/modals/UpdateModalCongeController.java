package l2.projet.pointagepersonnel.controller.modals;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import l2.projet.pointagepersonnel.controller.MainController;
import l2.projet.pointagepersonnel.dao.CongeDAO;
import l2.projet.pointagepersonnel.model.Conge;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateModalCongeController{
    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuller;

    @FXML
    private DatePicker datePickerDemande;

    @FXML
    private DatePicker datePickerRetour;

    @FXML
    private TextArea textAreaMotif;

    @FXML
    private TextField textNom;

    @FXML
    private TextField textNum;

    @FXML
    private TextField textPrenoms;

    private Integer numConge = null;


    public void setConge(Conge conge) {
        numConge = conge.getNumConge();
        textNum.setText(String.valueOf(conge.getNumConge()));
        textNom.setText(conge.getNom());
        textPrenoms.setText(conge.getPrenoms());
        textAreaMotif.setText(conge.getMotif());
        datePickerDemande.setValue(conge.getDateDemande());
        datePickerRetour.setValue(conge.getDateRetour());
    }

    @FXML
    void handleClickCancel(ActionEvent event) {
        Stage stage = (Stage) btnAnnuller.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleClickValided(ActionEvent event) {
        if(numConge == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Conge vide", "Veuillez selectionner un employeé!");
        } else {
            try {
                LocalDate dateDemande = datePickerDemande.getValue();
                LocalDate dateRetour = datePickerRetour.getValue();
                int nombreJr = dateRetour.getDayOfYear() - dateDemande.getDayOfYear();

                CongeDAO.updateConge(numConge, nombreJr, textAreaMotif.getText(), dateDemande, dateRetour);
                Stage stage = (Stage) btnAjouter.getScene().getWindow();
                stage.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            };
        }
    }
}
