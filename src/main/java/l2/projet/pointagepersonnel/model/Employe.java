package l2.projet.pointagepersonnel.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employe {
    private SimpleIntegerProperty numEmpl = new SimpleIntegerProperty();
    private SimpleStringProperty nom = new SimpleStringProperty();
    private SimpleStringProperty prenoms = new SimpleStringProperty();
    private SimpleStringProperty poste = new SimpleStringProperty();
    private SimpleIntegerProperty salaire = new SimpleIntegerProperty();

    public Employe() {
    }


    public Employe(SimpleIntegerProperty numEmpl, SimpleStringProperty nom, SimpleStringProperty prenoms, SimpleStringProperty poste, SimpleIntegerProperty salaire) {
        this.numEmpl = numEmpl;
        this.nom = nom;
        this.prenoms = prenoms;
        this.poste = poste;
        this.salaire = salaire;
    }

    public int getNumEmpl() {
        return numEmpl.get();
    }

    public SimpleIntegerProperty numEmplProperty() {
        return numEmpl;
    }

    public void setNumEmpl(int numEmpl) {
        this.numEmpl.set(numEmpl);
    }

    public String getNom() {
        return nom.get();
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getPrenoms() {
        return prenoms.get();
    }

    public SimpleStringProperty prenomsProperty() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms.set(prenoms);
    }

    public String getPoste() {
        return poste.get();
    }

    public SimpleStringProperty posteProperty() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste.set(poste);
    }

    public int getSalaire() {
        return salaire.get();
    }

    public SimpleIntegerProperty salaireProperty() {
        return salaire;
    }

    public void setSalaire(int salaire) {
        this.salaire.set(salaire);
    }
}
