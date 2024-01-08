package model;






public class Ball {
   private  float height;
   private  float width;
   private  float ballStepDown ;
  private   int speedIncrement ;
  private   int   timesTouchedEdge;
  private   XYTupel position;
  private XYTupel velocity;
  private int type;
  private boolean isProjectile;
  private float size;
    private XYTupel savedVelocity;
    private  boolean moving = true;

    // Constructeur modifié pour prendre un type de Pokémon , type de balles
   public  Ball(float x, float y, XYTupel velocity, int type, float size, int   timesTouchedEdge,boolean isProjectile, float ballStepDown, int speedIncrement, float height, float width) {
        //super(x, y,velocity, type,size,isProjectile);
        position = new XYTupel(x, y);
        this.velocity = velocity; // Direction et vitesse du projectile
        this.type = type;
        this.isProjectile = isProjectile; // C'est un projectile
        this.size = size; // Taille de la balle
        this.ballStepDown =   ballStepDown;
        this.speedIncrement =speedIncrement;
        this.timesTouchedEdge =timesTouchedEdge;
        this.height =height;
        this.width=width;
   }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void saveVelocity() {
        this.savedVelocity = this.velocity;
    }

    public void restoreVelocity() {
        if (this.savedVelocity != null) {
            this.velocity = this.savedVelocity;
        }
    }


    public int getTimesTouchedEdge() {
        return timesTouchedEdge;
    }

    public float getSize() {
        return size;
    }

    public int getType() {
        return type;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public XYTupel getPosition(){
       return position;
    }

    /**
     * Methode pour deplacer la balle en fonction d'une vitesse donnee
     */
    void move() {
        if((!this.isProjectile) && this.moving) {
            position.add(this.velocity);
            // Pour les balles en mouvement (pas les projectiles) (For moving balls, not projectiles)
            if (this.velocity.getY() == 0) {// on verifie encore que la balle se deplace verticalement
                // Changer de direction et descendre lorsqu'on touche un bord (Change direction and move down when touching an edge)
          //   if ((position.getX() > width-size && velocity.getX() > 0) || (position.getX() < 0 && velocity.getX() < 0)) {
                if (position.getX() > width-size && velocity.getX() > 0){
                    velocity.changeOrienX();
               //     this.timesTouchedEdge++; // augmente le nombre de fois que ca touche les bords
                }
              //  System.out.println(timesTouchedEdge);
                if (position.getX() < size && velocity.getX() < 0 ){
                    velocity.turnY();
                //    this.timesTouchedEdge++; // augmente le nombre de fois que ca touche les bords
                }


             /*   ///  position.x = constrain(position.x, 0, width); // setzt position.x zwischen 0 und width
                    //    position.y += this.ballStepDown;
                    //     velocity.x *= -1; // Inverser la direction (Reverse the direction)
               //     position.moveY(this.ballStepDown);
                 if(timesTouchedEdge ==1 || timesTouchedEdge ==3) {
                     velocity.changeOrienX();
                     timesTouchedEdge++; // augmente le nombre de fois que ca touche les bords
                 }
                } else if (timesTouchedEdge ==2 || timesTouchedEdge ==4) {
                 velocity.changeOrienY();
                 timesTouchedEdge++; // augmente le nombre de fois que ca touche les bords
             } */
             } else if (velocity.getX()==0) {
                if (position.getY() > height-size && velocity.getY() > 0) {
                    velocity.turnX();
                //    this.timesTouchedEdge++; // augmente le nombre de fois que ca touche les bords
                }
            }
        }else if (this.isProjectile){
          //  System.out.println("Ist not projectil");

            this.position.add(this.velocity);
        }
    }

    public boolean getIsProjectile() {
        return isProjectile;
    }

    public void setProjectile(boolean projectile) {
        isProjectile = projectile;
    }

    public void setVelocity(XYTupel velocity) {
        this.velocity = velocity;
    }

    public void setPosition(XYTupel position) {
        this.position = position;
    }

    public XYTupel getVelocity() {
        return velocity;
    }

    public void setIprojectile(boolean projectile) {
        isProjectile = projectile;
    }


    /**
     * La balle doit disparaitre lors qu'elle touche le bord de la fenetre 3 fois
     * @return
     */
    boolean shouldDisappear() {
        // Disparaître après avoir touché les bords trois fois (Disappear after touching the edges three times)
        return timesTouchedEdge >= 5;
    }
    boolean isOffScreen() {
        // Disparaître si la balle est hors de l'écran (pour les projectiles) (Disappear if the ball is off-screen, for projectiles)
        return position.getY() < 0 || position.getY() > height || position.getX() < 0 || position.getX() > width;
    }

    /**
     * verifie si la balle est en colision avec une autre
     * @param ball
     * @return
     */
    boolean collidesWith(Ball ball) {

            float dist = this.position.dist(ball.position);


        return dist < (ball.size / 2 + this.size / 2);

    }



    public String toString() {
        return this.type+" ";
    }

    public void setTimesTouchedEdge(int timesTouchedEdge) {
        this.timesTouchedEdge = timesTouchedEdge;
    }
}