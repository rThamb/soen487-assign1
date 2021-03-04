package com.project.soap.service;

import impl.dao.db.AlbumDAO;
import lib.logs.LogEntry;

import javax.jws.WebService;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@WebService(endpointInterface = "com.project.soap.service.LogService")
public class LogServiceImpl implements LogService{

    @Override
    public List<LogEntry> getLogs(Date startDate, Date endDate, String changeType) throws Exception{
        InputStream input = new FileInputStream("config/config.properties");
        Properties prop = new Properties();
        prop.load(input);
        AlbumDAO dao = new AlbumDAO(prop);
        List<LogEntry> logs = dao.readLogs();

        if(!changeType.equals("") && changeType != null){
            logs.removeIf(log -> !log.getType().equals(changeType));
        }
        if(startDate != null){
            logs.removeIf(log -> log.getTimestamp().before(startDate));
        }
        if(endDate != null){
            logs.removeIf(log -> log.getTimestamp().after(endDate));
        }
        return logs;
    }

}
