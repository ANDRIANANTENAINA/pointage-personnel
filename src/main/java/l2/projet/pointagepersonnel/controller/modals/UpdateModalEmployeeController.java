package l2.projet.pointagepersonnel.controller.modals;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import l2.projet.pointagepersonnel.controller.MainController;
import l2.projet.pointagepersonnel.dao.EmployeeDAO;
import l2.projet.pointagepersonnel.model.Employe;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class UpdateModalEmployeeController {

    private int numEmploye;

    @FXML
    private Button btnAnnuller;

    @FXML
    private Button btnValider;

    @FXML
    private TextField textNom;

    @FXML
    private TextField textPoste;

    @FXML
    private TextField textPrenom;

    @FXML
    private TextField textSalaire;

    public void setEmployee(Employe employe) {
        numEmploye = employe.getNumEmpl();
        textNom.setText(employe.getNom());
        textPrenom.setText(employe.getPrenoms());
        textPoste.setText(employe.getPoste());
        textSalaire.setText(String.valueOf(employe.getSalaire()));
    }

    @FXML
    void btnModalValid(MouseEvent event) {
        if(textNom.getText().isEmpty() || textPrenom.getText().isEmpty() || textPoste.getText().isEmpty() || textSalaire.getText().isEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Champ vide", "Veuillez remplir les champs vide !");
        } else {
            try {
                String nom = textNom.getText();
                String prenom = textPrenom.getText();
                String poste = textPoste.getText();
                int salaire = Integer.parseInt(textSalaire.getText());

                EmployeeDAO.updateEmployee(numEmploye, nom, prenom, poste, salaire);
                Stage stage = (Stage) btnValider.getScene().getWindow();
                stage.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            };
        }
    }

    @FXML
    void btnModalAnnuler(MouseEvent event) {
        Stage stage = (Stage) btnAnnuller.getScene().getWindow();
        stage.close();
    }

}
