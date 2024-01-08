import processing.core.PApplet;

public class Bahn extends  PApplet {

    public static void main(String[] args) {
        PApplet.main("Bahn");
    }

    // Erstellen von zwei Ball-Objekten
    Ball ball1 = new Ball(0, 40, 2); // Ball startet bei (0,40) mit Geschwindigkeit 2
    Ball ball2 = new Ball(0, 40, 3); // Ball startet bei (0,80) mit Geschwindigkeit 3
public void settings(){
    size(400, 120);
}
    public void setup() {

    }
    public void draw() {
        background(255);

        // Aktualisieren und Zeichnen von Ball 1
        ball1.update();
        ball1.display();

        // Aktualisieren und Zeichnen von Ball 2
        ball2.update();
        ball2.display();

        // Überprüfen, ob beide Bälle angehalten haben
        if (!ball1.moving && !ball2.moving) {
            // Beide Bälle nehmen ihre Bewegung wieder auf
            ball1.moving = true;
            ball2.moving = true;
        }
    }




    // Definition der Ball-Klasse
    // Definition der Ball-Klasse
    class Ball {
        float x, y;     // Position des Balls
        float speed;    // Geschwindigkeit des Balls
        boolean moving; // Bewegungsstatus des Balls

        // Konstruktor zum Initialisieren des Balls
        Ball(float x, float y, float speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.moving = true; // Der Ball startet in Bewegung
        }

        // Aktualisiert die Position des Balls
        void update() {
            if (moving) {
                x += speed; // Bewegt den Ball, wenn er sich bewegen soll
                if (x >= 200) { // Überprüft, ob der Ball die x-Koordinate 200 erreicht hat
                    moving = false; // Stoppt den Ball, wenn er die Position erreicht
                }
            }
        }

        // Zeichnet den Ball
        void display() {
            ellipse(x, y, 20, 20);
        }
    }



}
