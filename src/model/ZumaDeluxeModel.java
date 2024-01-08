package model;

import java.util.LinkedList;
import java.util.Random;



/**
 * The logic of me game
 */
public class ZumaDeluxeModel implements IZumaDeluxeModel{
    /**
     * some parameter related to the logic
     */
    private final int HEIGTH ;
    private final int WIDTH;
    private Player player1;
    private GameState gameState;
    private LinkedList<Ball>   movingBalls = new LinkedList<Ball>(); // Balles en mouvement (Moving balls)
    LinkedList<Ball> projectiles = new LinkedList<>(); // Objekte , die der Player projektiert
  private   int pokemonSize = 47; // Size von den Objekten
  private   int numTypes = 4; // Anzahl von Objektarten
   private  Random random = new Random();
   private  Ball ball;

    private  int  previewType = random.nextInt(numTypes);; // Type de projectile de prévisualisation

   private  float NewBallx =0; // pour la ball qui seras ajoutee
   private  float NewBally =0;

  private   int ballSize = 47;
private  XYTupel velocity =new XYTupel(0,0);
   private  int numMovingBalls = 20; // Nombre total de balles (Total number of balls)
   private  int ballSpacing = 47; // Espacement entre les balles (Spacing between balls)

     private   XYTupel projectylVelocity;

    private int numTypesOfPokemon = 4; // Nombre de types de Pokémon
    private int insertionsstelle ;
private Ball projectil;
private  Ball  previewsBall;
int position;
float distance ;
    int endIndex;
    int startIndex;
int numBallsToRemove;
private int score ;
    private ScoreCalculator scoreCalculator;
    private Thread scoreThread;
private float frame ;
    /**
     * constructor to initialise the parameters
     * @param width
     * @param height
     */
    public ZumaDeluxeModel(int width, int height) {
        this.HEIGTH = height;
        this.WIDTH = width;
        startNewGame();
        this.gameState = GameState.START;

    }

    public LinkedList<Ball> getMovingBalls() {
        return movingBalls;
    }
    public LinkedList<Ball> getProjectiles(){
        return projectiles;
    }

    /**
     * start a new game
     */

    public void startNewGame( ){
        // Ball ausgeben
System.out.println("Game starts! \n Das Spiel dauert 7 Minuten ");

// thread aufrufen
        scoreCalculator = new ScoreCalculator();
        scoreThread = new Thread(scoreCalculator);
        scoreThread.start();

        player1 = new Player(this.WIDTH,this.HEIGTH);
        previewType = random.nextInt(numTypes);
        // Charger les images des Pokémon
        // Ajouter des Pokémon en mouvement à la liste
        for (int i = 0; i < numMovingBalls; i++) {
            int pokemonType = (int) (Math.random()*numTypesOfPokemon ); // Choisir un type de Pokémon au hasard
            ball = new Ball(i * -ballSpacing, 40,new XYTupel(1,0), pokemonType,ballSize,1,false,500,2,600,800);
            ball.toString();
            addBall(ball);// On repousse chaque fois le x de la balle vers l'ariere
        }


        System.out.println(movingBalls);
    }


    public int getPreviewType(){
        return previewType;
    }
    /**
     * La methode cree des balles en fonction des url des differents types de balles

     */
    public void moveBalls() {

        for (int i = movingBalls.size() - 1; i >= 0; i--) {
            if (!movingBalls.get(i).shouldDisappear()) {
                movingBalls.get(i).move();
            }else{
                movingBalls.remove(i);
            }
        }

    }

    public void setFrame(float frame) {
        this.frame = frame;
    }

    public void addBall(Ball ball ){
        movingBalls.add(ball);// On repousse chaque fois le x de la balle vers l'ariere
    }
public XYTupel calculateProhectilVelocity(float x, float y){
    XYTupel vektor = new XYTupel(x,y); // Vitesse de propagation
    XYTupel dir = vektor.sub(vektor,player1.getPosition());
    dir.normalize();
    dir.mult(20);
    return dir;
}

    public ScoreCalculator getScoreCalculator() {
        return scoreCalculator;
    }

    /**
     * la methode cree des projectiles a travers les cordonees de la souris

     */

    public void createProjectiles() {
        //   System.out.println("Projectil");
        // Utilisez previewType pour le projectile qui sera lancé
     /*   XYTupel vektor = new XYTupel(x,y); // Vitesse de propagation
        XYTupel dir = vektor.sub(vektor,player1.getPosition());
        dir.normalize();
        dir.mult(20);
*/
       projectil = new Ball(this.player1.getPosition().getX(), this.player1.getPosition().getY(), new XYTupel(1,0), previewType, pokemonSize, 1, true, 500, 2, 600, 800);


        System.out.println("erzeugter Projektil : " + projectil);
        projectiles.add(projectil); // ajoute aux projectiles
        previewType = random.nextInt(numTypes);
    }
public Ball getProjectil(){
        return projectil;
}
    /**
     * La methode deplace les projectiles en fonction du clic de la souris et les projecte dans le sens d'orientation du joueur

     */
    public void moveProjektil(int projectilTyp ,int stelle){

int Stelle = 0;
if(stelle < movingBalls.size()) {
Stelle = stelle;}else { Stelle =stelle-1;};
    if (projectil.getVelocity().getX() == movingBalls.get(Stelle).getVelocity().getX()) {
        if (projectilTyp != projectil.getType()) {
            System.out.println("falscher Projektil");
        }
        System.out.println(movingBalls);
        projectil.getPosition().setX(movingBalls.get(Stelle).getPosition().getX());
        projectil.getPosition().setY(40);
        System.out.println(projectil);
        this.handleCollisions(Stelle);
    }

    for (int i = projectiles.size() - 1; i >= 0; i--) {
        Ball p = projectiles.get(i);

        if (!p.isOffScreen() && p.getType() == projectilTyp && p.getVelocity().getX() != movingBalls.get(Stelle).getVelocity().getX()) {
            p.move();
        } else {
            projectiles.remove(i);
        }
    }

   this.handleCollisions(Stelle);

   gameOverMessage();
        }


    /**
     *
     * @return
     */

    public void handleCollisions( int stelle ) {

        for (int i = 0; i < projectiles.size(); i++) {
            Ball projectile = projectiles.get(i);
            int collisionIndex = CheckCollision(projectile);
            if (collisionIndex != -1 && collisionIndex == stelle) {
               createSpaceForPreviewsBall(projectile.getType(), collisionIndex);
               // CheckNumberAndRemoveBalls(projectile.getType(), collisionIndex);
                projectiles.remove(projectile); // Entfernen des Projektils nach der Verarbeitung
               i--; // Korrektur des Index nach dem Entfernen eines Elements
           }
        }
    }

    public int getInsertionsstelle() {
        return insertionsstelle;
    }
// Ajouter un parametre pour la position du projectile

    private int CheckCollision(Ball projectile) {

        for (int i = 0; i < movingBalls.size(); i++) {
            // verifie pour chaque balle s'il y'a collision
            if (projectile.collidesWith(movingBalls.get(i))) {
                insertionsstelle = i;
                System.out.println("Ball "+movingBalls.get(i).getType()+" an der Stelle "+i+" getroffen");
                return i; // Rückgabe des Index des kollidierten Balls
            }
        }
        return -1; // Keine Kollision erkannt
    }

    public void createSpaceForPreviewsBall(int projektiltyp, int collisionIndex) {
        // Bestimme die Position des Balls am Kollisionsindex
        Ball collisionBall = movingBalls.get(collisionIndex);
        float collisionX = collisionBall.getPosition().getX();
        float collisionY = collisionBall.getPosition().getY();


        if (collisionBall.getVelocity().getX() >0){
            velocity = new XYTupel(1,0);
        } else if (collisionBall.getVelocity().getX() <0) {
            velocity = new XYTupel(-1,0);
        } else if (collisionBall.getVelocity().getY() >0) {
            velocity =new XYTupel(0,1);
        } else if (collisionBall.getVelocity().getY() <0) {
            velocity =new XYTupel(0,-1);
        }

        // Erzeuge das neue Projektil
        Ball newProjectile = new Ball(collisionX, collisionY,velocity, projektiltyp,ballSize,movingBalls.get(collisionIndex).getTimesTouchedEdge(),movingBalls.get(collisionIndex).getIsProjectile(),500,2,600,800);

        // Füge das Projektil an der Kollisionsstelle ein
        movingBalls.add(collisionIndex, newProjectile);

        // Verschiebe alle nachfolgenden Bälle, um Platz für das neue Projektil zu schaffen
        shiftSubsequentBallsBasedOnVelocity(collisionIndex);

        // Überprüfen und Entfernen von übereinstimmenden Bällen
        CheckNumberAndRemoveBalls(projektiltyp, collisionIndex);
    }

    private void shiftSubsequentBallsBasedOnVelocity(int startIndex) {
        this.startIndex =startIndex;
        // Verschiebe alle nachfolgenden Bälle basierend auf ihrer Bewegungsrichtung
        for (int i = this.startIndex; i < movingBalls.size(); i++) {
            Ball ball = movingBalls.get(i);
            if(ball == movingBalls.get(startIndex)){

            }else {
                shiftBallBasedOnVelocity(ball);
            }
        }
    }

    private void shiftBallBasedOnVelocity(Ball ball) {
        float velocityX = ball.getVelocity().getX();
        float velocityY = ball.getVelocity().getY();

        // Berechne die neue Position basierend auf der Bewegungsrichtung des Balls
        float newX = ball.getPosition().getX();
        float newY = ball.getPosition().getY();
//if( (ball.getPosition().getX()<WIDTH-ballSize && ball.getPosition().getX()<ballSize && ball.getPosition().getY()<HEIGTH-ballSize)   ) {
    // Wenn sich der Ball horizontal bewegt, verschiebe ihn in die entgegengesetzte Richtung
    if (velocityY == 0 && ball.getPosition().getX()<WIDTH-2*ballSize) {
        newX += (velocityX > 0 ? -ballSize : ballSize);

    }
    // Wenn sich der Ball vertikal bewegt, verschiebe ihn in die entgegengesetzte Richtung
    if (velocityX == 0 && ball.getPosition().getY()>2*ballSize && ball.getPosition().getY()<HEIGTH-2*ballSize  ) {

        newY += (velocityY > 0 ? -ballSize : ballSize);
    }

        // Aktualisiere die Position des Balls
        updateBallPosition(ball, newX, newY);
    }

    private void updateBallPosition(Ball ball, float newX, float newY) {
        // Aktualisiere die Position des Balls
        ball.getPosition().setX(newX);
        ball.getPosition().setY(newY);
    }




    public void closeGaps() {


        // Durchlaufen Sie die Liste von hinten nach vorne
        for (int i = movingBalls.size() - 1; i > 0; i--) {
            // Berechne die Distanz zwischen dem aktuellen Ball und dem vorherigen Ball
            float distance = movingBalls.get(i).getPosition().dist(movingBalls.get(i - 1).getPosition());

            // Wenn die Distanz größer als die Ballgröße ist, bewege den aktuellen Ball näher
            if (distance > ballSize) {
                for (int j = 0; j < i; j++) {

                    movingBalls.get(j).setMoving(false);
                }
            }
            if (distance < ballSize) {
                for (int j = 0; j < i; j++) {
                    movingBalls.get(j).setMoving(true);
                }
            }
        }


/*if(this.startIndex>0 && this.startIndex<movingBalls.size()) {
    // Die tatsächliche Distanz zwischen den Bällen am Start- und Endindex ermitteln
    this.startIndex = startIndex;
    this.numBallsToRemove = numBallsToRemove;
    position = this.startIndex - this.numBallsToRemove + 1;
    System.out.println("StartIndex " + this.startIndex + " und  position " + position);


    distance = movingBalls.get(this.startIndex).getPosition().dist(movingBalls.get(this.startIndex - 1).getPosition());
    System.out.println("Distance: " + distance + " Ball Size: " + ballSize);

    // Wenn die Distanz größer als ballSize ist, stoppe die Bälle, um die Lücke zu schließen


    if (distance > ballSize) {
        for (int i = 0; i < this.startIndex; i++) {

            movingBalls.get(i).setMoving(false);
        }
    }
    if (distance < ballSize) {
        for (int i = 0; i < this.startIndex; i++) {
            movingBalls.get(i).setMoving(true);
        }
    }

}*/
}


    private void CheckNumberAndRemoveBalls(int type,int startIndex) {


      //  System.out.println("Projektil "+previewsBall.getType()+" an der Stelle "+startIndex+" hinzugefügt");
        System.out.println(movingBalls);
       this.startIndex =startIndex;
         endIndex = this.startIndex;
        int ballType = movingBalls.get(this.startIndex).getType();
        // Nach links suchen, um übereinstimmende Bälle zu finden
        while (this.startIndex > 0 && movingBalls.get(this.startIndex - 1).getType() == ballType) {
            this.startIndex--;
        }
        // Nach rechts suchen, um übereinstimmende Bälle zu finden
        while (endIndex < movingBalls.size() - 1 && movingBalls.get(endIndex + 1).getType() == ballType) {
            endIndex++;
        }
        this.numBallsToRemove = endIndex - this.startIndex + 1;
        // Überprüfen, ob genug Bälle zum Entfernen vorhanden sind (mindestens 3)
        if (endIndex - this.startIndex >= 2) {
            // Entfernen der übereinstimmenden Bälle
            System.out.print("Bälle entfernt: ");
            for (int i = endIndex; i >= this.startIndex; i--) {
                System.out.print(movingBalls.get(i) + ", ");
                movingBalls.remove(i);
                scoreCalculator.ballRemoved(); // Oder scoreCalculator.addPoints(10), wenn Sie die Methode so benannt haben
            }

            System.out.println();
            System.out.println(movingBalls);
            closeGaps();
        // Schließe die Lücke, die durch das Entfernen der Bälle entstanden ist
      //      closeGaps(this.startIndex,numBallsToRemove); // Wir rufen closeGaps mit dem startIndex auf, der jetzt auf den Ball nach der entfernten Sequenz zeigt
         //   gameOverMessage();
            //   closeGaps(endIndex,endIndex); // Wir rufen closeGaps mit dem startIndex auf, der jetzt auf den Ball nach der entfernten Sequenz zeigt
        }



      //  closeGaps(this.startIndex,numBallsToRemove); // Wir rufen closeGaps mit dem startIndex auf, der jetzt auf den Ball nach der entfernten Sequenz zeigt

        // Après la suppression, fermez les écarts
    }
    public Player getPlayer1() {
        return player1;
    }
    public GameState getGameState() {
        return gameState;
    }
    public void setGameState(GameState state) {
        this.gameState = state;
    }

    
    public void gameOverMessage(){




        if(movingBalls.isEmpty() && ball.getTimesTouchedEdge()!=5){
            System.out.println("Glückwunsch Sie habe gewonnen ");
            scoreCalculator.terminate();
            try {
                scoreThread.join(); // Warten Sie, bis der Thread sicher beendet ist
            } catch (InterruptedException e) {
                System.out.println("Error beim Beenden des Threads ");
            }
            this.gameState = GameState.GAME_OVER;
        } else if (scoreCalculator.getElapsedTime()>3 && !movingBalls.isEmpty()) {
            System.out.println("Zeit abgelaufen! Sie haben leider nicht rechtzeitig gewonnen.");
            System.out.println("Spielzeit: " + scoreCalculator.getElapsedTime() + " Sekunden");
            System.out.println("Ihr Score: " + scoreCalculator.getScore());
            scoreCalculator.terminate();
            try {
                scoreThread.join(); // Warten Sie, bis der Thread sicher beendet ist
            } catch (InterruptedException e) {
                System.out.println("Error beim Beenden des Threads ");
            }
            this.gameState = GameState.GAME_OVER;
        } else if (ball.getTimesTouchedEdge() ==5) {
            System.out.println("Leider haben Sie nicht alle Bälle rausnehmen können !");
            System.out.println("Spielzeit: " + scoreCalculator.getElapsedTime() + " Sekunden");
            System.out.println("Ihr Score: " + scoreCalculator.getScore());
            scoreCalculator.terminate();
            try {
                scoreThread.join(); // Warten Sie, bis der Thread sicher beendet ist
            } catch (InterruptedException e) {
                System.out.println("Error beim Beenden des Threads ");
            }
            this.gameState = GameState.GAME_OVER;
        }

    }
    // Methoden für den thread





}

