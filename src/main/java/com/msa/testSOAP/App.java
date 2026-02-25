package com.msa.testSOAP;

import com.example.soapclient.*;
import com.example.soapclient.ProgramInterface.RequestMessage;
import com.example.soapclient.ProgramInterface.RequestMessage.RequestBody;
import com.example.soapclient.ProgramInterface.RequestMessage.RequestHeader;
import com.example.soapclient.ProgramInterface.RequestMessage.RequestHeader.ServiceContext;
import com.example.soapclient.ProgramInterface2.ResponseMessage;
import com.example.soapclient.ProgramInterface2.ResponseMessage.ResponseBody;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class App {

    private String wsUsername;
    private String wsEndpoint;
    private int wsTimeout; // in seconds

    public App(String wsUsername, String wsEndpoint, int wsTimeout) {
        this.wsUsername = wsUsername;
        this.wsEndpoint = wsEndpoint;
        this.wsTimeout = wsTimeout;
    }

    public String lookupBranch(String postalCode) {
        if (postalCode == null || postalCode.isEmpty()) {
            System.err.println("ERROR: Postal code is null or empty!");
            return null;
        }

        Date startTime = new Date();

        try {
            System.out.println("INFO: Initiating Web Service");
            AIP270AIService service = new AIP270AIService();
            AIP270AIPort port = service.getAIP270AIPort();

            // Set up endpoint, headers, and timeout
            BindingProvider bp = (BindingProvider) port;
            Map<String, Object> context = bp.getRequestContext();

            Map<String, List<String>> headers = new HashMap<>();
            headers.put("SOAPAction", Collections.singletonList("AIP270AIOperation"));
            context.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

            context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsEndpoint);
            System.out.println("INFO: Endpoint address set to " + wsEndpoint);

            if (wsTimeout > 0) {
                int timeoutMs = wsTimeout * 1000;
                context.put("com.sun.xml.ws.connect.timeout", timeoutMs);
                context.put("com.sun.xml.ws.request.timeout", timeoutMs);
                System.out.println("INFO: Timeout set to " + wsTimeout + " seconds");
            }

            // Set Basic Authentication
            String wsPassword = "Fv36xe$T97jgP_A"; // Or load via getWSPassword()
            context.put(BindingProvider.USERNAME_PROPERTY, wsUsername);
            context.put(BindingProvider.PASSWORD_PROPERTY, wsPassword);

            // Build request
            RequestMessage reqMessage = new RequestMessage();
            RequestHeader reqHeader = new RequestHeader();
            RequestBody reqBody = new RequestBody();

            reqHeader.setRequestId("a1234");
            reqHeader.setRequestCommandType("request");
            reqHeader.setRequestCommandMode("alwaysrespond");
            reqHeader.setEchoBack("false");

            ServiceContext sc = new ServiceContext();
            sc.setKeepAlive("false");
            sc.setChannelName("abc");
            reqHeader.setServiceContext(sc);

            reqBody.setCaZipCode(postalCode);
            reqMessage.setRequestHeader(reqHeader);
            reqMessage.setRequestBody(reqBody);

            // Call web service
            System.out.println("INFO: Calling Web Service with zip " + postalCode);
            ResponseMessage resp = port.aip270AIOperation(reqMessage);

            String respStatus = resp.getResponseHeader().getResponseCommandStatus().trim();
            System.out.println("INFO: Response Status -> " + respStatus);

            if ("Success".equalsIgnoreCase(respStatus)) {
                ResponseBody respBody = resp.getResponseBody();
                String branchId = respBody.getOfficeCode();
                System.out.println("INFO: Returned Branch Id is " + branchId);

                // Adjust branch ID for convention
                if (branchId.length() == 3 && branchId.startsWith("0")) {
                    branchId = "1" + branchId.substring(1);
                } else if (branchId.length() == 2) {
                    branchId = "1" + branchId;
                }
                System.out.println("INFO: Modified Branch Id is " + branchId);

                // Log elapsed time
                Date endTime = new Date();
                long durationSec = (endTime.getTime() - startTime.getTime()) / 1000;
                System.out.println("INFO: Time elapsed: " + durationSec + " sec");

                return branchId;
            } else {
                System.out.println("INFO: No Data returned for postal code " + postalCode);
                return "0";
            }

        } catch (Exception ex) {
            System.err.println("ERROR: " + ex.getMessage());
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            System.err.println(errors.toString());
            return null;
        }
    }

    private String getWSPassword() {
        try {
            File config = new File("c:/clm/clm.properties");
            if (!config.exists()) return null;

            Properties properties = new Properties();
            FileInputStream propertyFile = new FileInputStream(config);
            properties.load(propertyFile);
            propertyFile.close();

            String wsPassword = properties.getProperty("wsPassword");
            return decryptBase64(wsPassword);
        } catch (Exception ex) {
            return null;
        }
    }

    private String decryptBase64(String encryptedPW) {
        try {
            DESKeySpec key = new DESKeySpec("GOBROWNS".getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Cipher decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, keyFactory.generateSecret(key));

            byte[] decodedBytes = Base64.decodeBase64(encryptedPW.getBytes());
            byte[] unencryptedByteArray = decryptCipher.doFinal(decodedBytes);

            return new String(unencryptedByteArray, "UTF8");
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) {
        String username = "CCESVCSNP";
        String endpoint = "https://testsecservices.erieinsurance.com:7443/Claims800BranchLookup";
        String postalCode = "16428";
        int timeout = 5;

        // Enable Metro SOAP message dumping
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");

        App client = new App(username, endpoint, timeout);
        String branchId = client.lookupBranch(postalCode);
        System.out.println("Branch ID for postal code " + postalCode + ": " + branchId);
    }
}