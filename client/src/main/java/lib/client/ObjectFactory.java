
package lib.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the lib.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _LogEntry_QNAME = new QName("http://service.soap.project.com/", "logEntry");
    private final static QName _GetLogsResponse_QNAME = new QName("http://service.soap.project.com/", "getLogsResponse");
    private final static QName _ClearLogsResponse_QNAME = new QName("http://service.soap.project.com/", "clearLogsResponse");
    private final static QName _ClearLogs_QNAME = new QName("http://service.soap.project.com/", "clearLogs");
    private final static QName _RepException_QNAME = new QName("http://service.soap.project.com/", "RepException");
    private final static QName _GetLogs_QNAME = new QName("http://service.soap.project.com/", "getLogs");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: lib.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ClearLogs }
     * 
     */
    public ClearLogs createClearLogs() {
        return new ClearLogs();
    }

    /**
     * Create an instance of {@link RepException }
     * 
     */
    public RepException createRepException() {
        return new RepException();
    }

    /**
     * Create an instance of {@link GetLogs }
     * 
     */
    public GetLogs createGetLogs() {
        return new GetLogs();
    }

    /**
     * Create an instance of {@link LogEntry }
     * 
     */
    public LogEntry createLogEntry() {
        return new LogEntry();
    }

    /**
     * Create an instance of {@link ClearLogsResponse }
     * 
     */
    public ClearLogsResponse createClearLogsResponse() {
        return new ClearLogsResponse();
    }

    /**
     * Create an instance of {@link GetLogsResponse }
     * 
     */
    public GetLogsResponse createGetLogsResponse() {
        return new GetLogsResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogEntry }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.project.com/", name = "logEntry")
    public JAXBElement<LogEntry> createLogEntry(LogEntry value) {
        return new JAXBElement<LogEntry>(_LogEntry_QNAME, LogEntry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLogsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.project.com/", name = "getLogsResponse")
    public JAXBElement<GetLogsResponse> createGetLogsResponse(GetLogsResponse value) {
        return new JAXBElement<GetLogsResponse>(_GetLogsResponse_QNAME, GetLogsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearLogsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.project.com/", name = "clearLogsResponse")
    public JAXBElement<ClearLogsResponse> createClearLogsResponse(ClearLogsResponse value) {
        return new JAXBElement<ClearLogsResponse>(_ClearLogsResponse_QNAME, ClearLogsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearLogs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.project.com/", name = "clearLogs")
    public JAXBElement<ClearLogs> createClearLogs(ClearLogs value) {
        return new JAXBElement<ClearLogs>(_ClearLogs_QNAME, ClearLogs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RepException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.project.com/", name = "RepException")
    public JAXBElement<RepException> createRepException(RepException value) {
        return new JAXBElement<RepException>(_RepException_QNAME, RepException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLogs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.project.com/", name = "getLogs")
    public JAXBElement<GetLogs> createGetLogs(GetLogs value) {
        return new JAXBElement<GetLogs>(_GetLogs_QNAME, GetLogs.class, null, value);
    }

}
