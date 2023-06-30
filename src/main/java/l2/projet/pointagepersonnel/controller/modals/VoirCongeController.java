package l2.projet.pointagepersonnel.controller.modals;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import l2.projet.pointagepersonnel.dao.CongeDAO;
import l2.projet.pointagepersonnel.model.Conge;

import java.sql.SQLException;

public class VoirCongeController {
    @FXML
    private Button btnFermer;

    @FXML
    private Text voirDateDemande;

    @FXML
    private Text voirDateRetour;

    @FXML
    private Text voirMotif;

    @FXML
    private Text voirNbjr;

    @FXML
    private Text voirNom;

    @FXML
    private Text voirNumEmpl;

    @FXML
    private Text voirPoste;

    @FXML
    private Text voirPrenoms;


    @FXML
    void handleClickCancel(ActionEvent event) {
        Stage stage = (Stage) btnFermer.getScene().getWindow();
        stage.close();
    }

    public void setVoirConge(Conge conges) {
        ObservableList<Conge> listConge = null;
        try {
            listConge = CongeDAO.getCongeByEmpl(conges.getNumEmpl());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        int totalConge = 0;
        for(Conge conge: listConge){
            totalConge = totalConge + conge.getNbjr();
        }
        int nbjrRestant = 30 - totalConge;

        voirNumEmpl.setText(String.valueOf(conges.getNumEmpl()));
        voirNom.setText(conges.getNom());
        voirPrenoms.setText(conges.getPrenoms());
        voirNbjr.setText(String.valueOf(nbjrRestant));
        voirMotif.setText(conges.getMotif());
        voirPoste.setText(conges.getPoste());
        voirDateDemande.setText(String.valueOf(conges.getDateDemande()));
        voirDateRetour.setText(String.valueOf(conges.getDateRetour()));
    }
}
