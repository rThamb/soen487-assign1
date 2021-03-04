package com.project.rest.responses;

import lib.web.JSONifiable;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class WebError implements JSONifiable, Serializable {

    private String type;
    private String message;

    public WebError(){ }
    public WebError(String type, String message){
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
