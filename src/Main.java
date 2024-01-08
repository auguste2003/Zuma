import processing.core.PApplet;
import model.*;
import controller.*;
import view.*;

public final class Main {
    public static void main(String[] args) {
        final int GAME_HEIGTH = 600;
        final int GAME_WIDTH = 800;
        var model = new ZumaDeluxeModel(GAME_WIDTH,GAME_HEIGTH);
        var controller = new ZumaDeluxeController();
        var view = new ZumaDeluxeView(GAME_WIDTH,GAME_HEIGTH);

        // Connect M, V and C
        controller.setModel(model);
        controller.setView(view);
        view.setController(controller);

        // Starts the processing application
        PApplet.runSketch(new String[]{"ZumaDeluxe"}, view);
    }
}