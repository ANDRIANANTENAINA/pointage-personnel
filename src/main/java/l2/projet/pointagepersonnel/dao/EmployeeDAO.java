package l2.projet.pointagepersonnel.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import l2.projet.pointagepersonnel.model.Employe;
import l2.projet.pointagepersonnel.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {

    private static Employe getEmployeeFromResultSet(ResultSet rs) throws SQLException {
        Employe emp = null;
        if (rs.next()) {
            emp = new Employe();
            emp.setNumEmpl(rs.getInt("num_empl"));
            emp.setNom(rs.getString("nom"));
            emp.setPrenoms(rs.getString("prenom"));
            emp.setPoste(rs.getString("poste"));
            emp.setSalaire(rs.getInt("salaire"));
        }
        return emp;
    }

    private static ObservableList<Employe> getEmployeeList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Employe> empList = FXCollections.observableArrayList();

        while (rs.next()) {
            Employe emp = new Employe();
            emp.setNumEmpl(rs.getInt("num_empl"));
            emp.setNom(rs.getString("nom"));
            emp.setPrenoms(rs.getString("prenom"));
            emp.setPoste(rs.getString("poste"));
            emp.setSalaire(rs.getInt("salaire"));

            empList.add(emp);
        }
        return empList;
    }

    public static void insertEmployee(String nom, String prenom, String poste, Integer salaire) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "INSERT INTO public.employe(\n" +
                        "\tnom, prenom, poste, salaire)\n" +
                        "\tVALUES ('" + nom +  "', '" + prenom +  "', '" + poste +  "', " + salaire +  ");";
        System.out.println(updateStmt);
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
    }

    public static ObservableList<Employe> getEmployees() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM employe";
        try {
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Employe> employeeList = getEmployeeList(rsEmps);
            return employeeList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
    public static Employe getEmployeeById(String num_empl) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM employe WHERE num_empl="+num_empl;

        try{
            ResultSet resultSet = DBUtil.dbExecuteQuery(selectStmt);
            return getEmployeeFromResultSet(resultSet);
        }catch (SQLException e){
            System.out.println("While searching an employee with " + num_empl + " id, an error occurred: " + e);
            throw e;
        }
    }

    public static void updateEmployee(Integer id, String nom, String prenom, String poste, Integer salaire)
            throws SQLException, ClassNotFoundException {
        String updateStmt =
                "UPDATE public.employe\n" +
                        "\tSET nom='" + nom +  "', prenom='" + prenom +  "', poste='" + poste +  "', salaire='" + salaire + "'" +
                        "\tWHERE num_empl='" + id + "';";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    public static void deleteEmployee (String id) throws SQLException, ClassNotFoundException {
        String updateStmt = "DELETE FROM employe WHERE num_empl ="+ id +";";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static ObservableList<Employe> searchEmployees(String search) {
        String selectStmt = "SELECT * FROM employe WHERE LOWER(nom) LIKE LOWER('%" + search +
                "%') OR LOWER(prenom) LIKE LOWER('%" + search + "%') OR LOWER(poste) LIKE LOWER('%" + search + "%');";
        try {
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Employe> employeeList = getEmployeeList(rsEmps);
            return employeeList;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("SQL select operation has been failed: " + e);
        }
        return null;
    }

    public static void updateEmployeeP(int numEmpl, int salaire) throws SQLException {
        String updateStmt =
                "UPDATE public.employe\n" +
                        "\tSET salaire='" + salaire + "'" +
                        "\tWHERE num_empl='" + numEmpl + "';";
        System.out.println(updateStmt);
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
