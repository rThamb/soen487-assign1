package lib.repos;

import lib.logs.LogEntry;

import java.util.List;

public interface LogRepo {

    List<LogEntry> readLogs() throws Exception;

    void clearLogs() throws Exception;
}
