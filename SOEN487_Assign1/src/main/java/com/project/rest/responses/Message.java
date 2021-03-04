package com.project.rest.responses;

import lib.web.JSONifiable;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Message implements JSONifiable, Serializable {
    private String message;

    public Message(){}
    public Message(String mes){
       message = mes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
