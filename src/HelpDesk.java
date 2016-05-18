import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/*****************************************
 * Author : rich
 * Date : 3/31/16
 * Assignment: HelpDesk
 ******************************************/
public class HelpDesk {

    private final ApiSession session;
    private final String service = "helpdesk";

    public HelpDesk(ApiSession session) {
        this.session = session;
    }

    public ResponseHandler acceptCalls (String... callref) throws ParserConfigurationException, TransformerException, IOException {
        Request request = new Request(service,"acceptCalls");
        for(String ref : callref) {
            request.setParam("callref",ref);
        }

        return session.sendRequest(request);
    }


}
