import xmlmc.ComplexParam;
import xmlmc.Request;
import xmlmc.types.EmbeddedFileAttachment;

import javax.xml.parsers.ParserConfigurationException;

/*****************************************
 * Author : rich
 * Date : 9/2/16
 * Assignment: FileAttachmentTest
 ******************************************/
public class FileAttachmentTest {
    public static void main(String[] args) throws Exception {
        Request myRequest = new Request("helpdesk","fileattach");
        EmbeddedFileAttachment fileAttachment = new EmbeddedFileAttachment();
        ComplexParam fa = fileAttachment.parseFile("/Users/rich/Documents/test.m");
        myRequest.addComplexParam(fa);
        System.out.println(myRequest);
    }
}
