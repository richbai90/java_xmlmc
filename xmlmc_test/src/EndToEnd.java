/**
 * An end to end test for the java xmlmc api demonstrating how to import and use the compiled jar.
 * You'll need to make sure that the xmlmc jar file is in your classpath. Working without an IDE
 * the easiest way to do that is by running java -classpath .;<path to xmlmc.jar>
 * in this repository, that can be found at xmlmc/out/artifacts/xmlmc_jar
 */

import xmlmc.Response;
import xmlmc.XmlMethodCall;
import xmlmc.types.Call;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class EndToEnd {
    public static void main(String[] args) {
        try {
            XmlMethodCall methodCall = new XmlMethodCall("192.168.1.138");
            //Perform a logon attempt
            Response sessionEstablished = methodCall.session().analystLogon("admin", "");

            if (sessionEstablished.isSuccessful()) {

                //Create a new call object

                Call call = new Call(methodCall, "incident");
                call.setDescription("this is a test call. Logged from the java api");
                call.setCustomer("alanc");

                //Sla Name, Impact, Urgency
                call.setImpactAndUrgency("Service Delivery - Silver Service (10 Days)", "High", "High");

                //System.out.println(call.buildXml());
                Response response = methodCall.helpdesk().logAndAcceptNewCall(call);

                //Print the xml response
                System.out.println(response);

                //Print the callref value
                System.out.println(response.getParameter("callref"));

                //logoff
                methodCall.session().analystLogoff();
            } else {
                System.out.println(sessionEstablished.getLastError());
                methodCall.session().analystLogoff();
            }

        } catch (IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

}
