package group_6_model_sequential;

import android.text.format.DateFormat;

/**
 * Created by Group-6 on 06.11.17.
 * Content holder class
 */
public class Content {

    private Integer id;
    private String message;
    private long timestamp;

    public Content(Integer id,
                   String message,
                   long timestamp) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return DateFormat.format(
                "dd-MM-yyyy (HH:mm:ss)",
                timestamp).toString();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
