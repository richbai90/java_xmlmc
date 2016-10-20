package com.bittercreektech.xmlmc;

/*****************************************
 * Author : Rich Baird
 * Date : 10/12/16
 * Assignment: XmlmcException
 * Class xmlmc
 ******************************************/
public class XmlmcException extends RuntimeException {

    private String sessionId;

    public XmlmcException(String msg, XmlMethodCall xmlmc){
        super(msg);

        sessionId = xmlmc.getSessionId();
    }


    public XmlmcException(String msg, Exception cause, XmlMethodCall xmlmc){
        super(msg, cause);

        sessionId = xmlmc.getSessionId();
    }

    public XmlmcException(String msg, Throwable cause, XmlMethodCall xmlmc){
        super(msg, cause);

        sessionId = xmlmc.getSessionId();
    }

    public String getSessionId() {
        return sessionId;
    }
}
