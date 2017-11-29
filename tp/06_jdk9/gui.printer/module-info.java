module gui.printer {
    exports printer;
    exports printer.impl to javafx.graphics;
    requires javafx.controls;
    requires javafx.graphics;    
}

