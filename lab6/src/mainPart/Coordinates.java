package mainPart;

import java.io.Serializable;

public class Coordinates implements Serializable {
    public Coordinates(double x, double y){
        if (x > 96){
            x = 96;
        }
        if (x < 0){
            x = 0;
        }
        this.xcord = x;

        if (y > 96){
            y = 96;
        }
        if (y < 0){
            y = 0;
        }
        this.ycord = y;

        this.cords = this.xcord + "   " + this.ycord;
    }
    private double xcord; //Максимальное значение поля: 96, Поле не может быть null
    private double ycord;
    private String cords;

    public double getX(){
        return this.xcord;
    }
    public double getY(){
        return this.ycord;
    }
    public String getCoords(){
        return cords;
    }
}
