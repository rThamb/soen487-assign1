package lib.logs;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;

@XmlRootElement
public class LogEntry {

    private Time timestamp;
    private String type;

    public LogEntry(){
    }

    public Time getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Time timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
