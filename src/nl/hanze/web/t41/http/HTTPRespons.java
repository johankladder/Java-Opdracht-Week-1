package nl.hanze.web.t41.http;

import com.sun.xml.internal.messaging.saaj.util.Base64;

import java.io.*;
import java.util.HashMap;

public class HTTPRespons {
    private OutputStream out;
    private HTTPRequest request;

    public HTTPRespons(OutputStream out) {
        this.out = out;
    }

    public void setRequest(HTTPRequest request) {
        this.request = request;
    }

    public void sendResponse() throws IOException {
        byte[] bytes = new byte[HTTPSettings.BUFFER_SIZE];

        FileInputStream fis = null;

        String fileName = request.getUri();

        try {
            File file = new File(HTTPSettings.DOC_ROOT, fileName);
            FileInputStream inputStream = getInputStream(file);

            if (file.exists()) out.write(getHTTPHeader(fileName));
            else out.write(getHTTPHeader(""));

            int ch = inputStream.read(bytes, 0, HTTPSettings.BUFFER_SIZE);

            String fileType = getFileType(fileName);
            if (fileType == "gif" || fileType == "png" || fileType == "jpeg" || fileType == "jpg" || fileType == "pdf")
                bytes = Base64.encode(bytes);

            while (ch != -1) {
                out.write(bytes, 0, ch);
                ch = inputStream.read(bytes, 0, HTTPSettings.BUFFER_SIZE);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                fis.close();
        }

    }

    private FileInputStream getInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            file = new File(HTTPSettings.DOC_ROOT, HTTPSettings.FILE_NOT_FOUND);
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException f) {
                f.printStackTrace();
            }
        }
        return null;

    }

    private HashMap<String, String> getStatusMessage(String fileName, String fileType) {
        try {
            HashMap<String, String> data = new HashMap<>();
            if ((new File(HTTPSettings.DOC_ROOT, fileName)).exists()) {
                if (HTTPSettings.dataTypes.containsKey(fileType)) {
                    data.put("filename", fileName);
                    data.put("status", "200 OK");
                } else {
                    fileName = HTTPSettings.FILE_NOT_FOUND;
                    data.put("filename", HTTPSettings.FILE_NOT_FOUND);
                    data.put("status", "500 Internal Server Error");

                }
            } else {
                fileName = HTTPSettings.FILE_NOT_FOUND;
                data.put("filename", HTTPSettings.FILE_NOT_FOUND);
                data.put("status", "404 File Not Found");
            }

            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getHTTPHeader(String fileName) {

        String fileType = getFileType(fileName);

        HashMap data = getStatusMessage(fileName, fileType);

        String statusMessage = (String) data.get("status");

        fileName = (String) data.get("filename");

        fileType = getFileType(fileName);

        String header = null;

        String contentType = "text/plain";
        if (HTTPSettings.dataTypes.containsKey(fileType)) {
            contentType = HTTPSettings.dataTypes.get(fileType);
        }

        int contentLength = (int) (new File(HTTPSettings.DOC_ROOT, fileName)).length();

        header += ""
                + "HTTP/1.1 " + statusMessage + "\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + contentLength + "\r\n"
                + "Date: " + HTTPSettings.getDate() + "\r\n"
                + "\r\n";


        byte[] rv = header.getBytes();
        return rv;
    }

    private String getFileType(String fileName) {
        int i = fileName.lastIndexOf(".");
        String ext = "";
        if (i > 0 && i < fileName.length() - 1) {
            ext = fileName.substring(i + 1);
        }

        return ext;
    }


    private void showResponse(byte[] respons) {
        StringBuffer buf = new StringBuffer(HTTPSettings.BUFFER_SIZE);

        for (int i = 0; i < respons.length; i++) {
            buf.append((char) respons[i]);
        }
        System.out.print(buf.toString());

    }

}
