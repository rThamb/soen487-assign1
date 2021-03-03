package lib.repos;

import lib.logs.LogEntry;

import java.util.List;

public interface LogRepo {

    List<LogEntry> readLogs();

    void clearLogs();
}
