package objects;

import java.io.Serializable;

public class Review implements Serializable {

    private String message;
    private String name;
    private float stars;
    private String reviewerUID;

    public Review(){
        this.message = "";
        this.stars = 0;
        this.name = "";
        this.reviewerUID = "";
    }
    public Review(String message, String name, int stars){
        this.message = message;
        this.name = name;
        this.stars = stars;
    }
    public String getReviewerUID() {
        return reviewerUID;
    }

    public void setReviewerUID(String reviewerUID) {
        this.reviewerUID = reviewerUID;
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

    public float getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    /**
     * checks if a message is null
     * @return
     */
    public Boolean isNull(){
        return this.name.isEmpty() && this.message.isEmpty();
    }
}
