package l2.projet.pointagepersonnel.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import l2.projet.pointagepersonnel.model.Pointage;
import l2.projet.pointagepersonnel.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PointageDAO {
    private static Pointage getPointageFromResultSet(ResultSet resultSet) throws SQLException {
        Pointage pointage = null;
        if (resultSet.next()) {
            pointage = new Pointage();
            pointage.setNumPointage(resultSet.getInt("num_pointage"));
            pointage.setNumEmpl(resultSet.getInt("num_empl"));
            pointage.setPointage(resultSet.getString("pointage"));
            pointage.setDatePointage(resultSet.getDate("date_pointage"));
        }
        return pointage;
    }

    private static ObservableList<Pointage> getPointageList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Pointage> pointageList = FXCollections.observableArrayList();
        while (rs.next()) {
            Pointage pointage = new Pointage();
            pointage.setNumPointage(rs.getInt("num_pointage"));
            pointage.setNumEmpl(rs.getInt("num_empl"));
            pointage.setPointage(rs.getString("pointage"));
            pointage.setDatePointage(rs.getDate("date_pointage"));
            pointage.setNom(rs.getString("nom"));
            pointage.setPrenoms(rs.getString("prenom"));
            pointage.setSalaire(rs.getInt("salaire"));

            pointageList.add(pointage);
        }
        return pointageList;
    }

    public static ObservableList<Pointage> getPointage(LocalDate datePointage) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT employe.num_empl, employe.nom, employe.prenom,  employe.salaire, " +
                "pointage.num_pointage, pointage.pointage, pointage.date_pointage\n" +
                "FROM employe\n" +
                "LEFT JOIN pointage ON employe.num_empl = pointage.num_empl " +
                "AND pointage.date_pointage = '"+ datePointage +"' ORDER BY employe.num_empl ASC;";
        try {
            ResultSet rsPointage = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Pointage> pointageList = getPointageList(rsPointage);
            return pointageList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }


    public static void insertPointage(int numEmpl, String pointage, LocalDate datePointage) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "INSERT INTO pointage(\n" +
                        "\tnum_empl, pointage, date_pointage)\n" +
                        "\tVALUES (" + numEmpl +  ", '" + pointage +  "', '" + datePointage +  "');";
        System.out.println(updateStmt);
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
    }

    public static void updatePointage(int numPointage, String pointage) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "UPDATE pointage\n" +
                        "\tSET pointage='" + pointage + "' "+
                        "\tWHERE num_pointage = " + numPointage + ";";
        System.out.println(updateStmt);
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    public static void deletePointageWithId(int numPointage) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "DELETE FROM pointage WHERE num_pointage =" + numPointage + ";";
        System.out.println(updateStmt);
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static ObservableList<Pointage> searchPointagesIsAbsent(String pointage, LocalDate datePointage){
        String selectStmt = "SELECT employe.num_empl, employe.nom, employe.prenom, employe.salaire, " +
                "pointage.num_pointage, pointage.pointage, pointage.date_pointage\n" +
                "FROM employe\n" +
                "LEFT JOIN pointage ON employe.num_empl = pointage.num_empl " +
                "AND pointage.date_pointage = '"+ datePointage + "' " +
                "WHERE pointage.pointage = '" + pointage + "';";
        try {
            ResultSet rsPointages = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Pointage> pointageList = getPointageList(rsPointages);
            return pointageList;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("SQL select operation has been failed: " + e);
            return null;
        }
    }


    public static ObservableList<Pointage> searchPointage(String search, LocalDate datePointage){
        String selectStmt = "SELECT employe.num_empl, employe.nom, employe.prenom, employe.poste, employe.salaire, " +
                "pointage.num_pointage, pointage.pointage, pointage.date_pointage\n" +
                "FROM employe\n" +
                "LEFT JOIN pointage ON employe.num_empl = pointage.num_empl " +
                "AND pointage.date_pointage = '"+ datePointage + "' " +
                "WHERE LOWER(employe.nom) LIKE LOWER('%" + search + "%') " +
                "OR LOWER(employe.prenom) LIKE LOWER('%" + search + "%') " +
                "OR LOWER(employe.poste) LIKE LOWER('%" + search + "%') " +
                "ORDER BY employe.num_empl ASC ;";
        try {
            ResultSet rsPointage = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Pointage> pointageList = getPointageList(rsPointage);
            return pointageList;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("SQL select operation has been failed: " + e);
            return null;
        }
    }

    public static int getNbrAbsence(int numEmpl) {
        String selectStmt = "SELECT COUNT(*) FROM pointage WHERE num_empl = " + numEmpl + " AND pointage = 'non';";
        try {
            ResultSet rsPointage = DBUtil.dbExecuteQuery(selectStmt);
            if (rsPointage.next()) {
                return rsPointage.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return 0;
    }
}
