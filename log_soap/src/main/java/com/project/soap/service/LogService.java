package com.project.soap.service;

import lib.logs.LogEntry;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

@WebService
@SOAPBinding
public interface LogService {

    @WebMethod
    List<LogEntry> getLogs(Date startDate, Date endDate, String changeType) throws Exception;

}
