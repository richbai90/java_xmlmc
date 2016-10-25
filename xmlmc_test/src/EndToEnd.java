/**
 * An end to end test for the java xmlmc api demonstrating how to import and use the compiled jar.
 * You'll need to make sure that the xmlmc jar file is in your classpath. Working without an IDE
 * the easiest way to do that is by running java -classpath .;<path to xmlmc.jar>
 * in this repository, that can be found at xmlmc/out/artifacts/xmlmc_jar
 */

import com.bittercreektech.xmlmc.Response;
import com.bittercreektech.xmlmc.XmlMethodCall;
import com.bittercreektech.xmlmc.types.Call;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class EndToEnd {
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();


        try {

            String jsonFilePath = System.getProperty("user.dir") + "/xmlmc_test/src/call.json";

            //Create a new schema from the json object
            CallSchema callSchema = mapper.readValue(new File(jsonFilePath), CallSchema.class);


            XmlMethodCall methodCall = new XmlMethodCall("192.168.1.138");
            //Perform a logon attempt
            Response sessionEstablished = methodCall.session().analystLogon("admin", "");

            if (sessionEstablished.isSuccessful()) {

                //Create a new call object

                Call call = new Call(methodCall, "incident");
                call.setDescription(callSchema.getDescription() + "\n " + mapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(callSchema));
                call.setCustomer(callSchema.getCustomer());


                //Sla Name, Impact, Urgency
                call.setImpactAndUrgency(callSchema.getSla(), callSchema.getImpact(), callSchema.getUrgency());

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
