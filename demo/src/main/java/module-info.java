module cs.cvut.fel.pjv.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;

    opens cs.cvut.fel.pjv.demo to javafx.fxml;
    exports cs.cvut.fel.pjv.demo;
}