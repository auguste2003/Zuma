

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.LinkedList;
import java.util.Random;
public class Beispiel extends PApplet {
    /**
     * Das ist noch kein MVC Model und alle Attribute sind noch nicht private
     */
    PVector playerPosition; // Position von meinem Player
    PImage playerImage;
    PImage imageObject; // Für alle Objekte im Spiel

    LinkedList<Projectil> projectiles; // Objekte , die der Player projektiert
    int pokemonSize = 47; // Size von den Objekten

    float projectileSpeed = 20;
    int ballSpeed =2;

    int numTypes = 4; // Anzahl von Objektarten

    Random random = new Random();




    //  LinkedList<Ball> movingBalls; // Balles en mouvement (Moving balls)

    int ballSize = 47;
    int ballStepDown = 80; // Abstand
    int numMovingBalls = 8; // Nombre total de balles (Total number of balls)
    int ballSpacing = 47; // Espacement entre les balles (Spacing between balls)
    int previewType; // Type de projectile de prévisualisation

    PImage[] objectImage = new PImage[4]; // Tableau pour stocker les images des Pokémon
    int numTypesOfPokemon = 4; // Nombre de types de Pokémon
    String[] images;

    LinkedList<spielObjekt> objects;
    public static void main(String[] args) {
        PApplet.main("Beispiel");
    }

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {


        playerPosition = new PVector(width / 2, height -50); // Mettre le joueur a l'a podition basse de l'ecran

        projectiles = new LinkedList<Projectil>();


        previewType = random.nextInt(numTypes);

/*    pokemonImages[0] = loadImage("images/electrode3.png");
        pokemonImages[1] = loadImage("images/100.png");
        pokemonImages[2] = loadImage("images/electrode2.png");
        pokemonImages[3] = loadImage("images/electrode.png"); // Remplacez par une autre image de Pokémon
*/
        playerImage = loadImage("zuma1.png");
        // Charger les images des Pokémon
        images = new String[numTypesOfPokemon];
        images[0]="images/electrode3.png";
        images[1]= "images/100.png" ;
        images[2]= "images/electrode2.png" ;
        images[3]=  "images/electrode.png";

        for(Projectil p:projectiles){
            p.setUrlImage(images[previewType]);
        }

        objects = new LinkedList<spielObjekt>();

        // Ajouter des Pokémon en mouvement à la liste
        for (int i = 0; i < numMovingBalls; i++) {

            int pokemonType = (int) random(numTypesOfPokemon); // Choisir un type de Pokémon au hasard
            objects.add(new Ball(i * -ballSpacing, 40,new PVector(5,0), pokemonType,ballSize,false,ballStepDown,2,600,800));// On repousse chaque fois le x de la balle vers l'ariere
            objects.get(i).setUrlImage(images[pokemonType]); // Ein URL für das Laden setzen
        }
    }


    public void draw() {
        background(0);


        handleCollisions();
        drawPlayer();
        drawProjectiles();
        drawMovingBalls();
    }
 private void handleCollisions() {
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectil projectile = projectiles.get(i); // Projektile
            for (int j = 0; j < objects.size(); j++) {
                var ball =  objects.get(j); // ich neheme ein Objekt
                if(!ball.isProjectile) { // überprüfe ob es einen Ball ist
// J'implemente directement pour verifier si c'est une balle ou un projectile
                    float dist = PVector.dist(projectile.position, ball.position); // distance zwischen den beiden
                    if (dist < (ball.size / 2 + projectile.size / 2)) { // Kollison



                            // Trouver la position d'insertion correcte
                            System.out.println("Colision " + ball.type + ": (x,y) =" + "(" + ball.position.x + "," + ball.position.y + ")" + " \n" + " mit Pokemon "
                                    + projectile.type + ": (x,y) =" + "(" + projectile.position.x + "," + projectile.position.y + ")");

// hier zähle ich wieviele Objekte ähnlich zu dem Projektil an direser Stelle vorkommen
                            int sameTypeStart = j;
                            int sameTypeEnd = j;
                            int type = objects.get(j).type;
                            if (projectile.type == type) {
                                objects.add(j, projectile); // Da füge ich zuerst das Projektil
                                while (sameTypeStart > 0 && objects.get(sameTypeStart - 1).type == type) {
                                    sameTypeStart--; // nach links zählen
                                    System.out.println("nombre de balles similaires a gauche " + sameTypeStart);
                                }

                                while (sameTypeEnd < objects.size() - 1 && objects.get(sameTypeEnd + 1).type == type) {
                                    sameTypeEnd++; // nach recht  zählen
                                    System.out.println("nombre de balles similaires a droite " + sameTypeEnd);
                                }

                                if (sameTypeEnd - sameTypeStart >= 2) { // Wenn es mehr als drei insgesamt sind , entferne ich sie
                                    //        float gapDistance = (sameTypeEnd - sameTypeStart + 1) * pokemonSize + 25;
                                    for (int a = sameTypeEnd; a >= sameTypeStart; a--) {
                                        objects.remove(a);
                                    }
                                }
                            } else if (projectile.type != ball.type) {

                                // Créez une nouvelle balle avec les propriétés du projectile
                                //   Ball newBall = new Ball(projectile.position.x, projectile.position.y, projectile.type,ballSize,ball.velocity);


                                // Ajoutez la nouvelle balle à la liste
                                objects.add(j+1, projectile);

                            }
                            projectiles.remove(i); // Retirer apres la collision
                            break; // Sortir de la boucle pour éviter de vérifier les collisions avec d'autres balles

                        }

                }
            }
        }
    }

    // Methode aktualisiert, um die movingBalls zu zeichnen.
    private void drawMovingBalls() {
        for (int i = objects.size() - 1; i >= 0; i--) {
            spielObjekt ball = objects.get(i);
            ball.move();
            imageObject =loadImage(ball.getUrlImage());
            imageMode(CENTER);
            image(imageObject, ball.position.x, ball.position.y, ball.size, ball.size);
            if (ball.shouldDisappear()) {
                objects.remove(i);
            }
        }
    }

    private void drawProjectiles() {
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectil p = projectiles.get(i);
            p.setUrlImage(images[previewType]);
            p.move();
            if (!p.isOffScreen()) {
                imageObject =loadImage(p.getUrlImage());
                imageMode(CENTER);
                image(imageObject, p.position.x, p.position.y, p.size, p.size);
                System.out.println(projectiles);
            } else {
                projectiles.remove(i);
                System.out.println(projectiles);
            }
        }
    }

    private void drawPlayer() {
        pushMatrix();
        translate(playerPosition.x, playerPosition.y);
        rotate(atan2(mouseY - playerPosition.y, mouseX - playerPosition.x));
        imageMode(CENTER);
        image(playerImage, 0, 0, 80, 80); // Dessine l'image du joueur

        // Dessiner le projectile de prévisualisation sur le joueur
        // Ajustez les valeurs 0, -50 pour changer la position relative du projectile par rapport au joueur
     //   objectImage =
               // projectiles.get(0).setUrlImage(images[previewType]);
     //   imageObject =loadImage(projectiles.get(0).getUrlImage());
         //image(imageObject, 30, 0, pokemonSize, pokemonSize);
        PImage image = loadImage(images[previewType]);
        image(image, 30, 0, pokemonSize, pokemonSize);
        popMatrix();
    }

    public void mousePressed() {
        // Utilisez previewType pour le projectile qui sera lancé
        PVector dir = PVector.sub(new PVector(mouseX, mouseY), playerPosition); // Vitesse de propagation
        dir.normalize();
        dir.mult(projectileSpeed);
        projectiles.add(new Projectil(playerPosition.x, playerPosition.y, dir, previewType,pokemonSize,true,800,600)); // Utilisation du second constructeur pour les projectiles
System.out.println("proje");
        // Choisir un nouveau type aléatoire pour la prochaine prévisualisation
        previewType = random.nextInt(numTypes);

        System.out.println(previewType);
    }





    abstract class spielObjekt{
        int type ;
        PVector position;
        PVector velocity;


        int types;
        int timesTouchedEdge = 0; // Compter le nombre de rebonds
        boolean isProjectile; // Indique si le Pokémon est un projectile

        float size; // autres propriétés...
       private  String urlImage;

        spielObjekt(float x, float y, PVector velocity, int type, float size,  boolean isProjectile ) {
            position = new PVector(x, y);
            this.velocity = velocity; // Direction et vitesse du projectile
            this.type = type;
            this.isProjectile = isProjectile; // C'est un projectile
            this.size = size; // Taille de la balle
        }
public void setUrlImage(String urlImage){
      this.urlImage=urlImage;
}
public String getUrlImage(){
            return urlImage;
}
        abstract   void move();

        abstract   boolean isOffScreen();

        abstract  boolean shouldDisappear();
    }


    class Projectil  extends spielObjekt {

        float height;
        float width;
        // Constructeur pour les projectiles
        Projectil(float x, float y, PVector velocity, int type, float size,  boolean isProjectile ,float height,float width ) {
            super(x, y,velocity, type,size,isProjectile);
            position = new PVector(x, y);
            this.velocity = velocity; // Direction et vitesse du projectile
            this.type = type;
            this.isProjectile = isProjectile; // C'est un projectile
            this.size = size; // Taille de la balle
            this.height =height;
            this.width=width;
        }


        // Diese Methode prüft, ob es eine Kollision mit einer anderen Kugel gibt.
        boolean collidesWith(Ball ball) {
            float dist = PVector.dist(this.position, ball.position);

            return dist < (ball.size / 2 + this.size / 2);
        }


        void move() {
            if (isProjectile) {
                position.add(this.velocity);
            }
        }

        // Méthode pour vérifier si le projectile est hors de l'écran
        boolean isOffScreen() {
            return position.y < 0 || position.y > height || position.x < 0 || position.x > width;
        }
        boolean shouldDisappear() {
            // Disparaître après avoir touché les bords trois fois (Disappear after touching the edges three times)
            return false;
        }
        @Override
        public String toString() {
            return "Projectile : Position :"+"(" + this.position.x + "," + this.position.y + ")"+ "Velocyty :"+this.velocity+
                    "type :"+this.type+" size :"+this.size+"isProjectile "+this.isProjectile;
        }
    }
    class Ball extends spielObjekt {
        float height;
        float width;
        float ballStepDown ;
        int speedIncrement ;
        int   timesTouchedEdge;
        // Constructeur modifié pour prendre un type de Pokémon
        Ball(float x, float y, PVector velocity, int type, float size,  boolean isProjectile,  float ballStepDown,int speedIncrement,float height,float width) {
            super(x, y,velocity, type,size,isProjectile);
            position = new PVector(x, y);
            this.velocity = velocity; // Direction et vitesse du projectile
            this.type = type;
            this.isProjectile = isProjectile; // C'est un projectile
            this.size = size; // Taille de la balle
            this.ballStepDown =   ballStepDown;
            this.speedIncrement =speedIncrement;
            timesTouchedEdge =0;
            this.height =height;
            this.width=width;
        }



        void move() {
            position.add(this.velocity);
            // Pour les balles en mouvement (pas les projectiles) (For moving balls, not projectiles)
            if (this.velocity.y == 0) {// on verifie encore que la balle se deplace verticalement
                // Changer de direction et descendre lorsqu'on touche un bord (Change direction and move down when touching an edge)
                if ((position.x > width && velocity.x > 0) || (position.x < 0 && velocity.x < 0)) {
                  ///  position.x = constrain(position.x, 0, width); // setzt position.x zwischen 0 und width
                    position.y += this.ballStepDown;
                    velocity.x *= -1; // Inverser la direction (Reverse the direction)
                    timesTouchedEdge++; // augmente le nombre de fois que ca touche les bords
                }
            }

        }

        boolean shouldDisappear() {
            // Disparaître après avoir touché les bords trois fois (Disappear after touching the edges three times)
            return timesTouchedEdge >= 3;
        }

        boolean isOffScreen() {
            // Disparaître si la balle est hors de l'écran (pour les projectiles) (Disappear if the ball is off-screen, for projectiles)
            return position.x < 0 || position.x > width || position.y < 0 || position.y > height;
        }
        public String toString() {
            return "Ball : Position :"+"(" + this.position.x + "," + this.position.y + ") "+ "Velocyty :"+this.velocity+
                    "type :"+this.type+" size :"+this.size+"isProjectile :"+this.isProjectile;
        }

    }

}
