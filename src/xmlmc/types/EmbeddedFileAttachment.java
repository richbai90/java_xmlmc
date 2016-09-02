package xmlmc.types;

import org.apache.commons.codec.binary.Base64;
import xmlmc.ComplexParam;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Parses a file into an embeddedFileAttachment xml structure and returns the ComplexParam object
 * Suitable for placing into a {@link xmlmc.Request}
 */
public class EmbeddedFileAttachment implements SwType {
    private ComplexParam param;

    /**
     * Create a new FileAttachment given a complete file path and mimeType
     * @param fileName Complete file path to the file you wish to parse
     * @param mimeType the mime type of the file you wish to parse
     * @throws ParserConfigurationException
     */
    public EmbeddedFileAttachment(String fileName, String mimeType) throws ParserConfigurationException {
        param = new ComplexParam("embeddedFileAttachment");
        try {
            parseFile(fileName, mimeType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a new file attachment given a file name, file input stream, length, and mimeType
     * @param fileName Name of the file to attach
     * @param file A file input stream
     * @param length size of the file in bytes
     * @param mimeType mime type to use
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public EmbeddedFileAttachment(String fileName, FileInputStream file, int length, String mimeType) throws IOException, ParserConfigurationException {
        param = new ComplexParam("embeddedFileAttachment");
        parseFile(fileName, mimeType, file, length);
    }

    /**
     * Create a new file attachment given a path to the file. MimeType is auto detected.
     * @see EmbeddedFileAttachment#EmbeddedFileAttachment(String, String)
     */
    public EmbeddedFileAttachment(String fileName) throws ParserConfigurationException {
        param = new ComplexParam("embeddedFileAttachment");
        try {
            parseFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a new file attachment given a file input stream and size
     * @see EmbeddedFileAttachment#EmbeddedFileAttachment(String, FileInputStream, int, String)
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public EmbeddedFileAttachment(String fileName, FileInputStream file, int length) throws IOException, ParserConfigurationException {
        param = new ComplexParam("embeddedFileAttachment");
        parseFile(fileName,file,length);
    }







    /**
     * Encode a file into base64 by passing a file name
     *
     * @param fileName Full path to the file you wish to parse
     * @return {@link ComplexParam} with file name, and data
     * @throws Exception
     */
    private void parseFile(String fileName) throws Exception {
        byte[] base64EncodedData = Base64.encodeBase64(loadFileAsBytesArray(fileName), false);
        String fileData = new String(base64EncodedData, StandardCharsets.UTF_8);
        String nameArr[] = fileName.replaceAll("/\\/g", "/").split("/");
        String name = nameArr[nameArr.length - 1];
        param.addParameter("fileName", name);
        param.addParameter("fileData", fileData);
    }

    /**
     * Encode a file into base 64 by pasing a file name. Set the mimeType also.
     *
     * @param fileName Full path to the file you wish to parse
     * @param mimeType mimeType of the file you wish to parse
     * @return {@link ComplexParam} with file name, data, and mime type.
     * @throws Exception
     */
    private void parseFile(String fileName, String mimeType) throws Exception {
        parseFile(fileName);
        param.addParameter("mimeType", mimeType);
    }

    /**
     * Parse a file from a file input stream.
     *
     * @param fileName Name of the file, needen't be a full path
     * @param file     {@link FileInputStream} of the file you wish to parse
     * @param length   actual size in bytes of the file.
     * @return {@link ComplexParam} with file name, and data set.
     * @throws IOException
     */
    private void parseFile(String fileName, FileInputStream file, int length) throws IOException {
        byte[] base64EncodedData = Base64.encodeBase64(loadFileAsBytesArray(file, length), false);
        String fileData = new String(base64EncodedData, StandardCharsets.UTF_8);
        String nameArr[] = fileName.replaceAll("/\\/g", "/").split("/");
        String name = nameArr[nameArr.length - 1];
        param.addParameter("fileName", name);
        param.addParameter("fileData", fileData);
    }

    /**
     * Parses a file from a file input stream and sets the mime type as well
     *
     * @see EmbeddedFileAttachment#parseFile(String, FileInputStream, int)
     */
    private void parseFile(String fileName, String mimeType, FileInputStream file, int length) throws IOException {
        parseFile(fileName, file, length);
        param.addParameter("mimeType", mimeType);
    }

    private byte[] loadFileAsBytesArray(String fileName) throws Exception {

        File file = new File(fileName);
        int length = (int) file.length();
        long l = file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        int read = reader.read(bytes, 0, length);
        reader.close();
        return bytes;

    }

    private byte[] loadFileAsBytesArray(FileInputStream file, int length) throws IOException {

        byte[] bytes = new byte[length];
        BufferedInputStream reader = new BufferedInputStream(file);
        int read = reader.read(bytes, 0, length);
        reader.close();
        return bytes;
    }

    @Override
    public ComplexParam buildXml() {
        return param;
    }
}
