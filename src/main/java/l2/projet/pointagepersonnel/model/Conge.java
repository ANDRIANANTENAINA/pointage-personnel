package l2.projet.pointagepersonnel.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Conge {
    private IntegerProperty numConge;
    private IntegerProperty numEmpl;
    private StringProperty motif;
    private IntegerProperty nbjr;
    private SimpleObjectProperty<Date> dateDemande;
    private SimpleObjectProperty<Date> dateRetour;

    public Conge() {
    }

    public int getNumConge() {
        return numConge.get();
    }

    public IntegerProperty numCongeProperty() {
        return numConge;
    }

    public void setNumConge(int numConge) {
        this.numConge.set(numConge);
    }

    public int getNumEmpl() {
        return numEmpl.get();
    }

    public IntegerProperty numEmplProperty() {
        return numEmpl;
    }

    public void setNumEmpl(int numEmpl) {
        this.numEmpl.set(numEmpl);
    }

    public String getMotif() {
        return motif.get();
    }

    public StringProperty motifProperty() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif.set(motif);
    }

    public int getNbjr() {
        return nbjr.get();
    }

    public IntegerProperty nbjrProperty() {
        return nbjr;
    }

    public void setNbjr(int nbjr) {
        this.nbjr.set(nbjr);
    }

    public Date getDateDemande() {
        return dateDemande.get();
    }

    public SimpleObjectProperty<Date> dateDemandeProperty() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande.set(dateDemande);
    }

    public Date getDateRetour() {
        return dateRetour.get();
    }

    public SimpleObjectProperty<Date> dateRetourProperty() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
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
