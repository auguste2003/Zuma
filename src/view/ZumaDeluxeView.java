package view;

import controller.*;
import model.Ball;
import model.Player;
import model.XYTupel;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.LinkedList;

public class ZumaDeluxeView extends PApplet implements IFZumaDeluxeView {

    final private int WIDTH;
    final private int HEIGHT;
    private IZumaDeluxeController controller;
    private PImage playerImage;
    private PImage[] images;
    int numTypesOfPokemon = 4;
    PImage imageObject;
private float frame = frameRate;
    public ZumaDeluxeView(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        setSize(width,height);
    }

    public float getFrame() {
        return frame;
    }

    public void setController(IZumaDeluxeController controller) {
        this.controller = controller;
    }

    public void draw() {
        controller.nextFrame();
    }

    public void setup(){

        // ControlP5


        playerImage = loadImage("images/zuma1.png");

        images = new PImage[numTypesOfPokemon];
        images[0]= loadImage("images/electrode3.png");
        images[1]= loadImage("images/100.png");
        images[2]= loadImage("images/electrode2.png");
        images[3]=  loadImage("images/electrode.png");


    }

    public void initDisplay(){
        background(0);
        //image(playerImage, 300,400, 50, 50);
    }

    /**
     * Methode pour dessiner les balles en deplacement 
     * @param movingBalls
     */
    public void drawMovingBalls(LinkedList<Ball> movingBalls) {
        for (int i = movingBalls.size() - 1; i >= 0; i--) {
            Ball ball = movingBalls.get(i);

       //     imageObject =loadImage(ball.getUrlImage());
            imageObject = images[ball.getType()];
            imageMode(CENTER);
            image(imageObject, ball.getPosition().getX(), ball.getPosition().getY(), ball.getSize(), ball.getSize());
         //   System.out.println("Draw ball");
        }
    }

    public void drawPlayer(Player player){
        pushMatrix();
        //background(255);
        translate(player.getPosition().getX(), player.getPosition().getY());
       // rotate(atan2(player.moveY(mouseY), mouseX - player.moveX(mouseX)));
        rotate(atan2(mouseY - player.getPosition().getY(), mouseX -  player.getPosition().getX()));
        imageMode(CENTER);
        image(playerImage, 0,0, 80, 80);

        PImage imageBall=images[controller.bekommtPreviewType()];
        image(imageBall, 30, 0, 47, 47);
        popMatrix();
    }
    public void drawProjectiles(LinkedList<Ball> projectiles , Player player) {

        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Ball p2 = projectiles.get(i);
          // Ball p= projectiles.get(i+1);

                imageObject =images[p2.getType()];
        //  PImage  imageObject2 =loadImage(p.getUrlImage());
                imageMode(CENTER);
                image(imageObject, p2.getPosition().getX(), p2.getPosition().getY(), p2.getSize(), p2.getSize());
        //   image(imageObject2, player.getPosition().getX(), player.getPosition().getY(), p2.getSize(), p2.getSize());
         //   System.out.println("Drow the projectil");
        }
    }


    public void mousePressed() {
//System.out.println("Mouse is pressed");
     //   controller.newProjektil( mouseX,mouseY);

        controller.newProjektil( mouseX,mouseY);

    }

    @Override
    public void keyReleased() {
        controller.userInput(key);
    }
}
