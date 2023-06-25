package l2.projet.pointagepersonnel.controller.modals;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import l2.projet.pointagepersonnel.controller.MainController;
import l2.projet.pointagepersonnel.dao.EmployeeDAO;
import l2.projet.pointagepersonnel.model.Employe;

import java.sql.SQLException;


public class AddModalEmployeeController {
    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuller;

    @FXML
    private TextField textNom;

    @FXML
    private TextField textPrenom;

    @FXML
    private TextField textPoste;

    @FXML
    private TextField textSalaire;

    @FXML
    void handleClickCancel(ActionEvent event) {
        Stage stage = (Stage) btnAnnuller.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleClickValided(ActionEvent event) {

        if(textNom.getText().isEmpty() || textNom.getText().isEmpty() || textNom.getText().isEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Erreur", "Champ vide", "Veuillez remplir les champs vide !");
        } else {
            try {
                String nom = textNom.getText();
                String prenom = textPrenom.getText();
                String poste = textPoste.getText();
                int salaire = Integer.parseInt(textSalaire.getText());

                EmployeeDAO.insertEmployee(nom, prenom, poste, salaire);
                Stage stage = (Stage) btnAjouter.getScene().getWindow();
                stage.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            };
        }
    }


}
