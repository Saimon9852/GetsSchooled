package objects;

import java.util.Date;

public class ChatMessage {

    private String messageText;
    private String messageFrom;
    private String messageTo;
    private long messageTime;

    public ChatMessage(String messageText, String messageFrom, String messageTo) {
        this.messageText = messageText;
        this.messageFrom = messageFrom;
        this.messageTo = messageTo;
        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getmessageFrom() {
        return messageFrom;
    }

    public void setmessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }
    public String getmessageTo() {
        return messageTo;
    }
    public void setmessageTo(String messageTo) {
        this.messageTo = messageTo;
    }
    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}