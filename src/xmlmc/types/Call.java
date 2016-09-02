package xmlmc.types;

import xmlmc.ComplexParam;
import xmlmc.XmlMethodCall;

import javax.xml.parsers.ParserConfigurationException;

// TODO: 9/2/16 Lots of work still to do. Implement database features so that we can make logical decisions

public class Call implements SwType {
    private final XmlMethodCall xmlmc;
    private final ComplexParam param;
    private final String callClass;
    private String summary;
    private String customer;
    private String priority;

    public Call(XmlMethodCall xmlmc, String callClass) throws ParserConfigurationException {
        param = new ComplexParam("params");
        this.xmlmc = xmlmc;
        this.callClass = callClass;
    }

    @Override
    public ComplexParam buildXml() {
        return null;
    }
}
