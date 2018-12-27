package objects;

import java.io.Serializable;

public class Review implements Serializable {

    private String message;
    private String name;
    private int stars;
    public Review(String name,String msg, int stars){
        this.message = msg;
        this.stars = stars;
        this.name = name;
    }
    public Review(){
        this.message = "";
        this.stars = 0;
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
    public Boolean isNull(){
        return this.name.isEmpty() && this.message.isEmpty();
    }
}
