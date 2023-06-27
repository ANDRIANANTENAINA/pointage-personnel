package l2.projet.pointagepersonnel.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import l2.projet.pointagepersonnel.model.Conge;
import l2.projet.pointagepersonnel.model.Employe;
import l2.projet.pointagepersonnel.model.Pointage;
import l2.projet.pointagepersonnel.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CongeDAO {

    private static Conge getCongeFromResultSet(ResultSet rs) throws SQLException {
        Conge conge = null;
        if (rs.next()) {
            conge = new Conge();
            conge.setNumConge(rs.getInt("num_conge"));
            conge.setNumEmpl(rs.getInt("num_empl"));
            conge.setMotif(rs.getString("motif"));
            conge.setNbjr(Integer.parseInt(rs.getString("nbjr")));
            conge.setDateDemande(rs.getDate("date_demande").toLocalDate());
            conge.setDateRetour(rs.getDate("date_retour").toLocalDate());
            conge.setNom(rs.getString("nom"));
            conge.setPrenoms(rs.getString("prenom"));
        }
        return conge;
    }

    private static ObservableList<Conge> getCongeList(ResultSet rs) throws SQLException {
        ObservableList<Conge> pointageList = FXCollections.observableArrayList();
        while (rs.next()) {
            Conge conge = new Conge();
            conge.setNumConge(rs.getInt("num_conge"));
            conge.setNumEmpl(rs.getInt("num_empl"));
            conge.setMotif(rs.getString("motif"));
            conge.setNbjr(Integer.parseInt(rs.getString("nbjr")));
            conge.setDateDemande(rs.getDate("date_demande").toLocalDate());
            conge.setDateRetour(rs.getDate("date_retour").toLocalDate());
            conge.setNom(rs.getString("nom"));
            conge.setPrenoms(rs.getString("prenom"));
            conge.setPoste(rs.getString("poste"));

            pointageList.add(conge);
        }
        return pointageList;
    }

    public static ObservableList<Conge> searchConge(String search) {
        String selectStmt = "SELECT employe.num_empl, employe.nom, employe.prenom, " +
                "conge.num_conge, conge.motif, conge.nbjr, conge.date_demande, conge.date_retour " +
                "FROM employe " +
                "JOIN conge ON employe.num_empl = conge.num_empl " +
                "WHERE LOWER(employe.nom) LIKE LOWER('%" + search + "%') " +
                "ORDER BY employe.num_empl ASC ;";
        try {
            ResultSet resultSet = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Conge> congeList = getCongeList(resultSet);
            return congeList;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("SQL select operation has been failed: " + e);
            return null;
        }
    }

    public static void insertConge(int numEmpl, int nbjr ,String motif, LocalDate dateDemande, LocalDate dateRetour) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "INSERT INTO public.conge(\n" +
                        "\tnum_empl, motif, nbjr, date_demande, date_retour)\n" +
                        "\tVALUES (" + numEmpl +  ", '" + motif +  "', '" + nbjr + "', " +
                        "'" + dateDemande +  "','"+ dateRetour +"');";
        System.out.println(updateStmt);
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
    }

    public static ObservableList<Conge> getConge() throws SQLException, ClassNotFoundException  {
        String selectStmt = "SELECT employe.num_empl, employe.nom, employe.prenom, employe.poste,  " +
                "conge.num_conge, conge.motif, conge.nbjr, conge.date_demande, conge.date_retour " +
                "FROM employe " +
                "JOIN conge ON employe.num_empl = conge.num_empl;";
        try {
            ResultSet rsPointage = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Conge> congeList = getCongeList(rsPointage);
            return congeList;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    public static void deleteConge(String numConge)  throws SQLException, ClassNotFoundException {
        String updateStmt =
                "DELETE FROM conge WHERE num_conge = " + numConge + ";";
        System.out.println(updateStmt);
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static void updateConge(Integer numConge, int nombreJr, String motifText, LocalDate dateDemande, LocalDate dateRetour) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "UPDATE public.conge\n" +
                        "\tSET motif='" + motifText + "', " +
                        "nbjr='" + nombreJr + "', " +
                        "date_demande='" + dateDemande + "', " +
                        "date_retour='" + dateRetour + "' WHERE num_conge = " + numConge + ";";
        System.out.println(updateStmt);
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;

        }
    }
}
