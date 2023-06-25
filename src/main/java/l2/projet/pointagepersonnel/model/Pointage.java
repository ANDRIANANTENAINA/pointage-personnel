package l2.projet.pointagepersonnel.model;

import javafx.beans.property.*;

import java.util.Date;

public class Pointage extends Employe{
    private SimpleIntegerProperty numPointage = new SimpleIntegerProperty();
    private SimpleIntegerProperty numEmpl = new SimpleIntegerProperty();
    private StringProperty pointage = new SimpleStringProperty();
    private SimpleObjectProperty<Date> datePointage = new SimpleObjectProperty<Date>();

    public Pointage() {
    }

    public Pointage(SimpleIntegerProperty numEmpl, StringProperty pointage, SimpleObjectProperty<Date> datePointage) {
        this.numEmpl = numEmpl;
        this.pointage = pointage;
        this.datePointage = datePointage;
    }

    public int getNumPointage() {
        return numPointage.get();
    }

    public SimpleIntegerProperty numPointageProperty() {
        return numPointage;
    }

    public void setNumPointage(int numPointage) {
        this.numPointage.set(numPointage);
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

    public String getPointage() {
        return pointage.get();
    }

    public StringProperty pointageProperty() {
        return pointage;
    }

    public void setPointage(String pointage) {
        this.pointage.set(pointage);
    }

    public Date getDatePointage() {
        return datePointage.get();
    }

    public SimpleObjectProperty<Date> datePointageProperty() {
        return datePointage;
    }

    public void setDatePointage(Date datePointage) {
        this.datePointage.set(datePointage);
    }
}
