package view;

import model.Ball;
import model.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public interface IFZumaDeluxeView {
    void drawProjectiles(LinkedList<Ball> projectiles,Player player);
    void initDisplay();
    void drawPlayer(Player player);
    void drawMovingBalls(LinkedList<Ball> movingBalls);
    float getFrame();
}
