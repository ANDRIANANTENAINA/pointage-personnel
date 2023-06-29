module l2.projet.pointagepersonnel {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.postgresql.jdbc;
    requires java.sql;
    requires aspose.pdf;
    requires converter;

    opens l2.projet.pointagepersonnel to javafx.fxml;
    exports l2.projet.pointagepersonnel;
    exports l2.projet.pointagepersonnel.controller;
    opens l2.projet.pointagepersonnel.controller to javafx.fxml;
    exports l2.projet.pointagepersonnel.controller.modals;
    opens l2.projet.pointagepersonnel.controller.modals to javafx.fxml;
}