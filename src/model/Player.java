package model;
public class Player {

    private XYTupel position;

    public Player(int width, int height) {
     //   this.position = new XYTupel((float)width/2, (float)height-50);
        this.position = new XYTupel((float)width/2, (float)height/2);
        System.out.println(position.getX());
        System.out.println(position.getY());
    }

    public XYTupel getPosition() {
        return position;
    }

}
