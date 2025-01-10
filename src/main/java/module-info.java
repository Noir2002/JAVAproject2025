module com.isep.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.isep.javafxdemo to javafx.fxml;
    exports com.isep.javafxdemo;
}