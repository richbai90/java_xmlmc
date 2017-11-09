/**
 * An end to end test for the java xmlmc api demonstrating how to import and use the compiled jar.
 * You'll need to make sure that the xmlmc jar file is in your classpath. Working without an IDE
 * the easiest way to do that is by running java -classpath .;<path to xmlmc.jar>
 * in this repository, that can be found at xmlmc/out/artifacts/xmlmc_jar
 */

import com.bittercreektech.xmlmc.Response;
import com.bittercreektech.xmlmc.XmlMethodCall;
import com.bittercreektech.xmlmc.XmlMethodCallOverNat;
import com.bittercreektech.xmlmc.types.Call;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class EndToEnd {
    public static void main(String[] args) {

        try {

            // Tell the api to use https
            XmlMethodCall methodCall = new XmlMethodCall("richvmserver.local", 443);
            //Perform a logon attempt
            Response establishSession = methodCall.session().analystLogon("admin", "");

            if (establishSession.isSuccessful()) {

                //Create a new call object

                Call call = new Call(methodCall, "Service Request");
                call.setDescription("This is a test call from the api");
                call.setCustomer("AlanC");


                //Sla Name, Impact, Urgency
                call.setImpactAndUrgency("Silver Standard SLA", "Low", "High");
                Response response = methodCall.helpdesk().logAndAcceptNewCall(call);

                //Print the xml response
                System.out.println(response);

                //Print the callref value
                System.out.println(response.getParameter("callref"));

                //logoff
                methodCall.session().analystLogoff();
            } else {
                System.out.println(establishSession.getLastError());
                methodCall.session().analystLogoff();
            }

        } catch (IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

}
