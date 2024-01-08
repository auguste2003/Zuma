package model;

/**
 * a xytupel implemantation for or the players and balls used in the model
 */
public class XYTupel {
    private float x = 0;
    private float y = 0;

    /**
     * creates a xytupel
     * @param x given x value to create the tupel
     * @param y given y value to create the tupel
     */
    XYTupel(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * sets the x value
     * @param x given x value to be set in the tupel
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * sets the y value
     * @param y given x value to be set in the tupel
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * gets the x value set in the tupel
     * @return returns the x value set in the tupel
     */
    public float getX() {
        return x;
    }

    /**
     * gets the y value set in the tupel
     * @return returns the y value set in the tupel
     */
    public float getY() {
        return y;
    }

    /**
     * move the x position
     * @param zahl
     */
    public void moveX(float zahl){
     //   System.out.println("Auf der x Achse bewegen ");
        this.x += zahl;
    }

    /**
     * move the y position
     * @param zahl
     */
    public void moveY(float zahl){
        this.y += zahl;
    }

    /**
     * change the direction of the ball
     */
    public void changeOrienX(){
      //  this.x *=-1;
        float z = this.x;
        this.x =0;
        this.y =z;
    }
    public void turnX(){
        //  this.x *=-1;
        float z = this.x;
        this.x =-y;
        this.y =0;
    }
    public void turnY(){
        //  this.x *=-1;
        float z = this.x;
        this.x =0;
        this.y =z;
    }
    public void changeOrienY(){
        //  this.x *=-1;
        float z = this.y;
        this.y =0;
        this.x =z;
    }

    // Methode zum Addieren eines anderen Vektors
    public void add(XYTupel v) {

        this.x += v.x;
        this.y += v.y;

    }

    // Methode zum Subtrahieren eines anderen Vektors
    public XYTupel sub(XYTupel v , XYTupel k) {
        float x = v.getX()-k.getX();
        float y= v.getY()-k.getY();
        return  new XYTupel(x,y);
    }

    public float dist(XYTupel v) {
        float dx = x - v.x;
        float dy = y - v.y;

        return (float) Math.sqrt(dx * dx + dy * dy );
    }

    public void normalize() {
        // Berechne die Länge des Vektors
        double length = Math.sqrt(x * x + y * y );

        // Überprüfe, ob die Länge nicht null ist, um eine Division durch null zu vermeiden
        if (length != 0) {
            // Teile jede Komponente des Vektors durch die Länge, um ihn zu normalisieren
            x /= length;
            y /= length;

        } else {
            System.out.println("Der Vektor hat eine Länge von Null und kann nicht normalisiert werden.");
        }
    }

    public void mult(double scalar) {
        // Multipliziere jede Komponente des Vektors mit dem Skalar
        x *= scalar;
        y *= scalar;

    }
    public String toString(){
        return "("+x+","+y+")";
    }
}