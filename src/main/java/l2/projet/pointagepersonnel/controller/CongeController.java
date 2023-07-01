package l2.projet.pointagepersonnel.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import l2.projet.pointagepersonnel.Application;
import l2.projet.pointagepersonnel.controller.modals.UpdateModalCongeController;
import l2.projet.pointagepersonnel.controller.modals.VoirCongeController;
import l2.projet.pointagepersonnel.dao.CongeDAO;
import l2.projet.pointagepersonnel.model.Conge;

import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CongeController implements Initializable {

    @FXML
    private TableColumn<Conge, String> colNom;

    @FXML
    private TableColumn<Conge, Integer> colNum;

    @FXML
    private TableColumn<Conge, String> colMotif;

    @FXML
    private TableColumn<Conge, String> colPrenom;

    @FXML
    private TableColumn<Conge, Integer> colNbjr;

    @FXML
    private TableView<Conge> tableView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableConge();
    }

    @FXML
    void searchConge(KeyEvent event) {
        String search = ((TextField) event.getSource()).getText();
        tableView.setItems(CongeDAO.searchConge(search));
    }

    private void setTableConge() {
        colNum.setCellValueFactory(cellData -> cellData.getValue().numCongeProperty().asObject());
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colPrenom.setCellValueFactory(cellData -> cellData.getValue().prenomsProperty());
        colNbjr.setCellValueFactory(cellData -> cellData.getValue().nbjrProperty().asObject());
        colMotif.setCellValueFactory(cellData -> cellData.getValue().motifProperty());

        try {
            tableView.setItems(CongeDAO.getConge());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onClickDeleteConge() {
        Conge conge = tableView.getSelectionModel().getSelectedItem();
        if (conge == null) {
            MainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner le congé à supprimer.");
            return;
        }
        try {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText("Suppression du congé");
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer le congé sélectionné ?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                CongeDAO.deleteConge(String.valueOf(conge.getNumConge()));
                setTableConge();
            }else {
                confirmationAlert.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onClickNewConge() {
        showAsDialog("addModalConge");
    }

    @FXML
    void onClickUpdateConge() {
        Conge conge = tableView.getSelectionModel().getSelectedItem();
        if (conge == null) {
            MainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun congé sélectionné", "Veuillez sélectionner un employé.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(Application.class.getResource("modals/editModalConge.fxml")));
            Parent root = loader.load();

            UpdateModalCongeController tmpController = loader.getController();
            tmpController.setConge(conge);

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Congé d'un employé");
            modalStage.setScene(new Scene(root));
            modalStage.showAndWait();

            setTableConge();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onClickVoirConge() throws IOException {
        Conge conge = tableView.getSelectionModel().getSelectedItem();
        if (conge == null) {
            MainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun congé sélectionné", "Veuillez sélectionner un employé.");
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(Application.class.getResource("modals/voirConge.fxml")));
        Parent root = loader.load();

        VoirCongeController tmpController = loader.getController();
        tmpController.setVoirConge(conge);

        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
//            modalStage.initStyle(StageStyle.UNDECORATED);
        modalStage.setTitle("Congé d'un employé");
        modalStage.setScene(new Scene(root));
        modalStage.showAndWait();

        setTableConge();
    }

    private void showAsDialog(String url) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("modals/"+url+".fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
//            stage.initStyle(StageStyle.UNDECORATED);
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
            setTableConge();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
