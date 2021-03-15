package lib.repos;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import lib.client.LogEntry;
import lib.client.LogServiceImplService;
import lib.client.RepException_Exception;
import lib.exception.RepException;
import lib.util.MyHttpClient;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class ClientLogRepo {

    private MyHttpClient client;
    private String url = "http://localhost:8090/log?wsdl";

    public ClientLogRepo(){
        this.client = new MyHttpClient();
    }

    public List<LogEntry> getLogs(String toStr, String fromStr, String option) throws Exception {

        XMLGregorianCalendar toXML = getDate(toStr);
        XMLGregorianCalendar fromXML = getDate(fromStr);

        LogServiceImplService logService = new LogServiceImplService();
        List<LogEntry> logs = logService.getLogServiceImplPort().getLogs(toXML, fromXML, option.trim());

        return logs;
    }

    public void clearLogs() throws RepException {
        LogServiceImplService logService = new LogServiceImplService();
        try {
            logService.getLogServiceImplPort().clearLogs();
        } catch (RepException_Exception e) {
            throw new RepException(e.getMessage());
        }
    }

    //YYYY-MM-DD
    private XMLGregorianCalendar getDate(String date){
        try {
            String[] num = date.split("-");
            GregorianCalendar dateObj = new GregorianCalendar(parse(num[0]), parse(num[1]), parse(num[2]), 0, 0, 0);
            XMLGregorianCalendar dateXML = new XMLGregorianCalendarImpl(dateObj);

            return dateXML;
        }catch(Exception e){
            return null;
        }
    }

    private int parse(String num){
        return Integer.parseInt(num);
    }

}
