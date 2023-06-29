package l2.projet.pointagepersonnel.controller;

import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import java.io.File;


public class FicheDePaieController {
    @FXML
    private Button btnPdf;

    @FXML
    private TableColumn<?, ?> colNom;

    @FXML
    private TableColumn<?, ?> colNum;

    @FXML
    private TableColumn<?, ?> colPoste;

    @FXML
    private TableColumn<?, ?> colPrenom;

    @FXML
    private TableColumn<?, ?> colSalaire;

    @FXML
    private BorderPane contentArea;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TextField textFieldSearch;

    @FXML
    void handleButtonClickPdf(ActionEvent event) {
        try {
            Document document = new Document();
            Page page = document.getPages().add();
            String ligne1 = "Date : 30/04/2023";
            String ligne2 = "NOM : RAKOTO";
            String ligne3 = "PRENOM : Bernard";
            String ligne4 = "Poste : Secrétaire";
            String ligne5 = "Nombre d'absence : 0";
            String ligne6 = "montatnt : 1 000 000 Ar";

            TextFragment textFragment1 = new TextFragment(ligne1);
            TextFragment textFragment2 = new TextFragment(ligne2);
            TextFragment textFragment3 = new TextFragment(ligne3);
            TextFragment textFragment4 = new TextFragment(ligne4);
            TextFragment textFragment5 = new TextFragment(ligne5);
            TextFragment textFragment6 = new TextFragment(ligne6);

            textFragment1.getTextState().setFontSize(14);
            textFragment2.getTextState().setFontSize(13);
            textFragment3.getTextState().setFontSize(13);
            textFragment4.getTextState().setFontSize(12);

            page.getParagraphs().add(textFragment1);
            page.getParagraphs().add(textFragment2);
            page.getParagraphs().add(textFragment3);
            page.getParagraphs().add(textFragment4);

            document.save("C:\\Users\\PC58\\Desktop\\FicheDePaie.pdf");

            File file = new File("C:\\Users\\PC58\\Desktop\\FicheDePaie.pdf");

        } catch (Exception e) {
            System.out.println("Erreur lors de la génération du PDF : " + e.getMessage());
        }
    }

    @FXML
    void searchEmployee(KeyEvent event) {

    }
}
