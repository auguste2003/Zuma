package model;

import java.util.LinkedList;

public interface IZumaDeluxeModel  {

    Player getPlayer1();

    LinkedList<Ball> getMovingBalls();
    void startNewGame(  );
    GameState getGameState();
    void setGameState(GameState state);
}
