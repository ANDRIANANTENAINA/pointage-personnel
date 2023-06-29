package l2.projet.pointagepersonnel.controller;

import com.aspose.pdf.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import l2.projet.pointagepersonnel.dao.EmployeeDAO;
import l2.projet.pointagepersonnel.dao.PointageDAO;
import l2.projet.pointagepersonnel.model.Employe;
import com.github.royken.converter.FrenchNumberToWords;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class FicheDePaieController implements Initializable {
    @FXML
    private Button btnPdf;

    @FXML
    private TableView<Employe> tableView;

    @FXML
    private TableColumn<Employe, Integer> colNum;

    @FXML
    private TableColumn<Employe, String> colNom;

    @FXML
    private TableColumn<Employe, String> colPrenom;

    @FXML
    private TableColumn<Employe, String> colPoste;

    @FXML
    private TableColumn<Employe, Integer> colSalaire;

    @FXML
    private BorderPane contentArea;

    @FXML
    private TextField textFieldSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableEmployee();
    }

    private void setTableEmployee() {
        colNum.setCellValueFactory(cellData -> cellData.getValue().numEmplProperty().asObject());
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
    void handleButtonClickPdf(ActionEvent event) {
        Employe employe = tableView.getSelectionModel().getSelectedItem();
        if (employe == null) {
            MainController.showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun employé sélectionné", "Veuillez sélectionner un employé.");
            return;
        }
        try {
            int nbrAbsence = PointageDAO.getNbrAbsence(employe.getNumEmpl());

            Stage primaryStage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le fichier PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FichiersPDF", "*.pdf"));

            // Sélection du chemin de fichier par l'utilisateur
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                DecimalFormat format = new DecimalFormat("#,###");
                String filePath = selectedFile.getAbsolutePath();
                Document document = new Document();
                Page page = document.getPages().add();

                // titre
                TextFragment header = new TextFragment("FICHE DE PAIE");
                header.getTextState().setFont(FontRepository.findFont("Arial"));
                header.getTextState().setFontSize(24);
                header.setHorizontalAlignment(HorizontalAlignment.Center);
                header.getTextState().setUnderline(true);
                header.setPosition(new Position(130, 720));
                page.getParagraphs().add(header);

                TextFragment ligneVideFragment = new TextFragment("\n");
                page.getParagraphs().add(ligneVideFragment);

                String ligne1 = "Date : " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String ligne2 = "Nom : " + employe.getNom();
                String ligne3 = "Prénoms : " + employe.getPrenoms();
                String ligne4 = "Poste : " + employe.getPoste();
                String ligne5 = "Nombre d'absence : " + nbrAbsence + " jour(s)";
                String ligne6 = "Montant : "   + format.format(employe.getSalaire()) + " AR (" +  FrenchNumberToWords.convert(employe.getSalaire()) + "Ariary)";


                TextFragment textFragment1 = new TextFragment(ligne1);
                TextFragment textFragment2 = new TextFragment(ligne2);
                TextFragment textFragment3 = new TextFragment(ligne3);
                TextFragment textFragment4 = new TextFragment(ligne4);
                TextFragment textFragment5 = new TextFragment(ligne5);
                TextFragment textFragment6 = new TextFragment(ligne6);


                textFragment1.getTextState().setFontSize(13);
                textFragment2.getTextState().setFontSize(13);
                textFragment3.getTextState().setFontSize(13);
                textFragment4.getTextState().setFontSize(13);
                textFragment5.getTextState().setFontSize(13);
                textFragment6.getTextState().setFontSize(13);


                page.getParagraphs().add(textFragment1);
                page.getParagraphs().add(textFragment2);
                page.getParagraphs().add(textFragment3);
                page.getParagraphs().add(textFragment4);
                page.getParagraphs().add(textFragment5);
                page.getParagraphs().add(textFragment6);

                document.save(filePath);
                File file = new File(filePath);
                MainController.showAlert(Alert.AlertType.INFORMATION, "Succès", "Génération du PDF réussie", "Le fichier est enregistré dans " + file.getAbsolutePath());
            }

        } catch (Exception e) {
            System.out.println("Erreur lors de la génération du PDF : " + e.getMessage());
        }
    }

    @FXML
    void searchEmployee(KeyEvent event) {
        String search = ((TextField) event.getSource()).getText();
        tableView.setItems(EmployeeDAO.searchEmployees(search));
    }

}
