package controller;

import model.*;
import view.IFZumaDeluxeView;
import model.ScoreCalculator;

public class ZumaDeluxeController implements IZumaDeluxeController{

    private ZumaDeluxeModel model;
    private IFZumaDeluxeView view;
private boolean isMoving =false ;
    public void setModel(ZumaDeluxeModel model) {
        this.model = model;
    }

    public void setView(IFZumaDeluxeView view) {
        this.view = view;
    }

    public void newProjektil(float x, float y){

       // model.calculateProhectilVelocity(x,y);

        model.createProjectiles();
        model.getProjectil().setVelocity(model.calculateProhectilVelocity(x,y));
     //   model.moveProjektil(model.getProjectiles());
        isMoving =true;
      //  model.handleCollisions(model.getMovingBalls());
    }

    public int bekommtPreviewType(){
        return model.getPreviewType();
    }


    @Override
    public void nextFrame() {

        // Java fx
model.setFrame(view.getFrame());
        view.initDisplay();

        view.drawMovingBalls(model.getMovingBalls());
      model.moveBalls();
        view.drawPlayer(model.getPlayer1());
        view.drawProjectiles(model.getProjectiles(),model.getPlayer1());
//   System.out.println( "Spielzeit: " + model.getScoreCalculator().getElapsedTime());
if(isMoving){

            model.moveProjektil(model.getProjectil().getType(),model.getInsertionsstelle());
//model.handleCollisions(model.getInsertionsstelle());

            //model.handleCollisions(model.getInsertionsstelle());
   // model.handleCollisions(model.getMovingBalls(),model.getProjectil());
        }




//model.moveProjektil(model.getProjectiles());
      //  model.handleCollisions(model.getMovingBalls(),model.getProjectiles());
//model.handleCollisions(model.getMovingBalls());


     /*   switch (model.getGameState()){
            case START -> {
                view.initDisplay();
            }
            case PLAYING -> {
                view.drawPlayer(model.getPlayer1());
            }
        }
*/



    }

    public void userInput(char a){
        switch (model.getGameState()){
            case START -> {
                if (a == ' '){
                    model.setGameState(GameState.PLAYING);
                }
            }
        }
    }


}
