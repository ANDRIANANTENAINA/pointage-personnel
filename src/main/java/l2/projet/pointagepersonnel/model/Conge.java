package l2.projet.pointagepersonnel.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Date;

public class Conge extends Employe{
    private SimpleIntegerProperty numConge = new SimpleIntegerProperty();
    private SimpleIntegerProperty numEmpl = new SimpleIntegerProperty();
    private SimpleStringProperty motif = new SimpleStringProperty();
    private SimpleIntegerProperty nbjr = new SimpleIntegerProperty();
    private SimpleObjectProperty<LocalDate> dateDemande = new SimpleObjectProperty<>();
    private SimpleObjectProperty<LocalDate> dateRetour = new SimpleObjectProperty<>();

    public Conge(){

    }

    public int getNumConge() {
        return numConge.get();
    }

    public SimpleIntegerProperty numCongeProperty() {
        return numConge;
    }

    public void setNumConge(int numConge) {
        this.numConge.set(numConge);
    }

    @Override
    public int getNumEmpl() {
        return numEmpl.get();
    }

    @Override
    public SimpleIntegerProperty numEmplProperty() {
        return numEmpl;
    }

    public void setNumEmpl(int numEmpl) {
        this.numEmpl.set(numEmpl);
    }

    public String getMotif() {
        return motif.get();
    }

    public SimpleStringProperty motifProperty() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif.set(motif);
    }

    public int getNbjr() {
        return nbjr.get();
    }

    public SimpleIntegerProperty nbjrProperty() {
        return nbjr;
    }

    public void setNbjr(int nbjr) {
        this.nbjr.set(nbjr);
    }

    public LocalDate getDateDemande() {
        return dateDemande.get();
    }

    public SimpleObjectProperty<LocalDate> dateDemandeProperty() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande.set(dateDemande);
    }

    public LocalDate getDateRetour() {
        return dateRetour.get();
    }

    public SimpleObjectProperty<LocalDate> dateRetourProperty() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour.set(dateRetour);
    }

    @Override
    public String toString() {
        return "Conge{" +
                "numConge=" + numConge +
                ", numEmpl=" + numEmpl +
                ", motif=" + motif +
                ", nbjr=" + nbjr +
                ", dateDemande=" + dateDemande +
                ", dateRetour=" + dateRetour +
                '}';
    }
}
