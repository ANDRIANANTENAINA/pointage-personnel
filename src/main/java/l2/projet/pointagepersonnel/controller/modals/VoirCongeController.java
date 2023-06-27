package l2.projet.pointagepersonnel.controller.modals;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import l2.projet.pointagepersonnel.model.Conge;

import java.time.LocalDate;

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

    public void setVoirConge(Conge conge) {
        LocalDate dateNow = LocalDate.now();
        int nbjrRestant = conge.getDateRetour().getDayOfYear() - dateNow.getDayOfYear();

        voirNumEmpl.setText(String.valueOf(conge.getNumEmpl()));
        voirNom.setText(conge.getNom());
        voirPrenoms.setText(conge.getPrenoms());
        voirNbjr.setText(String.valueOf(nbjrRestant));
        voirMotif.setText(conge.getMotif());
        voirPoste.setText(conge.getPoste());
        voirDateDemande.setText(String.valueOf(conge.getDateDemande()));
        voirDateRetour.setText(String.valueOf(conge.getDateRetour()));
    }
}
