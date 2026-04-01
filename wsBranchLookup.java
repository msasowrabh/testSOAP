package com.cdw.cvp.clm.element.ws;

import com.audium.server.AudiumException;
import com.audium.server.session.DecisionElementData;
import com.audium.server.voiceElement.AudiumElement;
import com.audium.server.voiceElement.DecisionElementBase;
import com.audium.server.voiceElement.ElementData;
import com.audium.server.voiceElement.ElementException;
import com.audium.server.voiceElement.ExitState;
import com.audium.server.voiceElement.Setting;
import com.audium.server.xml.DecisionElementConfig;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
//import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Date;

//import java.util.HashMap;

import java.util.Properties;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;



//
//* This custom Decision Element retrieves the branch tied
//* to a caller entered zip code.
//*
public class wsBranchLookup extends DecisionElementBase implements AudiumElement
{
	private final String SUCCESS_RETURN_CODE = "Success";
	private final String FAILED_RETURN_CODE = "Failed";
	private final String NO_DATA_RETURN_CODE = "No Data";
	private final String BRANCH_ID_ELEMENT_DATA_NAME = "BranchId";
	private String wsUsername = null;
	
//	 This method returns the name of the action element that appears in Studio's Element view
   public String getElementName()
   {
       return "wsBranchLookup";
   }
	// This method returns a description of the element that will display in Studio
	// when the cursor hovers over the element in the Element view
   public String getDescription()
   {
       return "Claims Branch Lookup Web Service";
   }

	// This method returns the name of a folder that will contain the action element 
	// in Studio's Element view. Use return null if you don't want it in a folder.
   public String getDisplayFolderName()
   {
       return "Custom\\ws";
   }

   public Setting[] getSettings()
       throws ElementException
   {
       Setting settingArray[] = new Setting[4];
           
       settingArray[0] = new Setting("postal_code", "Postal Code", "The US Postal Code entered by the caller.",
       		true,   // It is required
       		true,   // It appears only once
       		true,  // Allows substitution
       		Setting.STRING);
       
       settingArray[1] = new Setting("ws_username", "Username", "Web Service Username.",
          		true,   // It is required
          		true,   // It appears only once
          		true,  // Allows substitution
          		Setting.STRING);
       
       settingArray[2] = new Setting("ws_endpoint", "Web Service Endpoint", "The URL of the web service endpoint.",
          		true,   // It is required
          		true,   // It appears only once
          		true,  // Allows substitution
          		Setting.STRING);
       
       settingArray[3] = new Setting("ws_timeout", "Web Service Timeout", "The max time (secs) to wait for a response.",
         		true,   // It is required
         		true,   // It appears only once
         		true,  // Allows substitution
         		Setting.STRING);
       
       return settingArray;
   }

   public ExitState[] getExitStates()
       throws ElementException
   {
       ExitState exitstateArray[] = new ExitState[3];
       exitstateArray[0] = new ExitState(this.SUCCESS_RETURN_CODE, this.SUCCESS_RETURN_CODE, "Success");
       exitstateArray[1] = new ExitState(this.FAILED_RETURN_CODE,this.FAILED_RETURN_CODE, "Failed");
       exitstateArray[2] = new ExitState(this.NO_DATA_RETURN_CODE, this.NO_DATA_RETURN_CODE, "No Data");
       return exitstateArray;
   }

   public ElementData[] getElementData() throws ElementException 
   {
   	ElementData[] elementDataArray = new ElementData[1];
       elementDataArray[0] = new ElementData(this.BRANCH_ID_ELEMENT_DATA_NAME, this.BRANCH_ID_ELEMENT_DATA_NAME);
       
       return elementDataArray;
   }
   /**
	 * Calls a SOAP service with dynamic parameters and returns the full XML
	 * response if HTTP 200. Logs success or failure and time elapsed.
	 *
	 * @param endpointUrl SOAP endpoint URL
	 * @param username    WS-Security username
	 * @param password    WS-Security password
	 * @param zipCode     Zip code for request
 * @param ded 
	 * @return Full XML response on HTTP 200 success, null otherwise
	 */
	private  String callSoapServiceAndReturnXml(String endpointUrl, String wsUsername, String wsPassword,
			String zipCode, int timeoutMillis, DecisionElementData ded) {
		long startTime = System.currentTimeMillis();
		String requestId = UUID.randomUUID().toString(); // generate unique requestId
		HttpURLConnection conn = null;

		try {
			//Build dynamic SOAP XML
			String soapXml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
					+ " <SOAP-ENV:Header xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
					+ " <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" soap:mustUnderstand=\"1\">\r\n"
					+ " <wsse:UsernameToken wsu:Id=\"UsernameToken-e925075c-4e15-496d-8703-1f41f84d4cd0\">\r\n"
					+ " <wsse:Username>{{wsUsername}}</wsse:Username>\r\n"
					+ " <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">{{wsPassword}}</wsse:Password>\r\n"
					+ " </wsse:UsernameToken>\r\n" + " </wsse:Security>\r\n" + " </SOAP-ENV:Header>\r\n"
					+ " <soap:Body>\r\n"
					+ " <AIP270AIOperation xmlns=\"http://www.CAIP234LI.com/schemas/CAIP234LIInterface\" xmlns:ns2=\"http://www.CAIP234LO.com/schemas/CAIP234LOInterface\">\r\n"
					+ " <requestMessage>\r\n" + " <requestHeader>\r\n" + " <requestId>{{requestId}}</requestId>\r\n"
					+ " <requestCommandType>request</requestCommandType>\r\n"
					+ " <requestCommandMode>alwaysrespond</requestCommandMode>\r\n" + " <echoBack>false</echoBack>\r\n"
					+ " <serviceContext>\r\n" + " <keepAlive>false</keepAlive>\r\n"
					+ " <channelName>abc</channelName>\r\n" + " </serviceContext>\r\n" + " </requestHeader>\r\n"
					+ " <requestBody>\r\n" + " <ca_zip_code>{{zipCode}}</ca_zip_code>\r\n" + " </requestBody>\r\n"
					+ " </requestMessage>\r\n" + " </AIP270AIOperation>\r\n" + " </soap:Body>\r\n" + "</soap:Envelope>";

			// Replace placeholders
	        soapXml = soapXml.replace("{{wsUsername}}", wsUsername)
	                         .replace("{{wsPassword}}", wsPassword)
	                         .replace("{{requestId}}", requestId)
	                         .replace("{{zipCode}}", zipCode);

			//Open connection
			URL url = new URL(endpointUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			//conn.setRequestProperty("SOAPAction", "AIP270AIOperation");
			conn.setConnectTimeout(timeoutMillis);
	        conn.setReadTimeout(timeoutMillis);
	        conn.setDoOutput(true);
			//ded.addToLog(soapXml);

			//Send request
			try (OutputStream os = conn.getOutputStream()) {
				os.write(soapXml.getBytes("UTF-8"));
			}

			//Get HTTP response code
			int httpCode = conn.getResponseCode();
			InputStream is = httpCode == 200 ? conn.getInputStream() : conn.getErrorStream();

			//Read response
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

			long duration = (System.currentTimeMillis() - startTime) / 1000;
			ded.addToLog("INFO"," Time elapsed: " + duration + " secs");

			if (httpCode == 200) {
				ded.addToLog("INFO","INFO: SOAP request succeeded with HTTP 200");
				return response.toString();
			} else {
				ded.addToLog("ERROR"," SOAP request failed with HTTP code " + httpCode);
				ded.addToLog("ERROR"," Response: " + response.toString());
				return null;
			}

		} catch (Exception e) {
			long duration = (System.currentTimeMillis() - startTime) / 1000;
			ded.addToLog("ERROR"," Exception during SOAP call after " + duration + " secs");
			e.printStackTrace();
			return null;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
   
   public String doDecision(String name, DecisionElementData ded)
       throws AudiumException
   {
       DecisionElementConfig dec = ded.getDecisionElementConfig();
       String zipCode = dec.getSettingValue("postal_code", ded);
       wsUsername = dec.getSettingValue("ws_username", ded);
       String wsEndpoint = dec.getSettingValue("ws_endpoint", ded);
       String wsTimeout = dec.getSettingValue("ws_timeout", ded);
       String return_code = this.FAILED_RETURN_CODE;
       Date start_time = new Date();
       
       try
       {
    	   if (zipCode == null) {
    		   ded.addToLog("ERROR", "Postal Code is null!");
    		   return this.FAILED_RETURN_CODE;
    	   }
    		   
    	
    	   
    	   
    	
    	   
    	  
     	   long to = 0;
     	  int timeout=30000;

     	 if (!wsTimeout.equals("0")) {

     	     timeout = Integer.parseInt(wsTimeout) * 1000;
     	     to = timeout;   // <-- ADD THIS

     	     ded.addToLog("INFO", "Setting timeout to " + timeout + " ms");

     	     
     	 }

     		else {
     		    ded.addToLog("INFO", "Using default web service timeout 30000");
     		}

     	   String wsPassword=getWSPassword();
     	  if (wsPassword == null) {
     		    ded.addToLog("ERROR", "Web service password is null");
     		    return this.FAILED_RETURN_CODE;
     		}
     	 

           
           
           // Invoke web service
           ded.addToLog("INFO", "Calling Web Service with zip " + zipCode);
          String resultXml=callSoapServiceAndReturnXml(wsEndpoint,wsUsername, wsPassword,
			zipCode,timeout,ded);
          ded.addToLog("INFO", resultXml);
          
          
          if(resultXml==null)
          {
        	  ded.addToLog("ERROR", "could not get data");
        	  return this.NO_DATA_RETURN_CODE;
          }
          String officeCode=null;
          String postalCodeResult=null;
          String status =null;
          
          
          //processing the result
          try {

        	// Parse the XML

        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        	factory.setNamespaceAware(true); // important for SOAP

        	DocumentBuilder builder = factory.newDocumentBuilder();

        	Document doc = (Document) builder.parse(new ByteArrayInputStream(resultXml.getBytes()));
        

        	// XPath setup

        	XPath xPath = XPathFactory.newInstance().newXPath();


        	// Extract response status

        	status = xPath.evaluate("//*[local-name()='responseCommandStatus']", doc).trim();

        	ded.addToLog("INFO","Response Status: " + status);

        	if ("Success".equalsIgnoreCase(status)) {

        		// Extract officeCode and postalCode if success

        		officeCode = xPath.evaluate("//*[local-name()='officeCode']", doc).trim();

        		postalCodeResult = xPath.evaluate("//*[local-name()='postalCode']", doc).trim();

        		ded.addToLog("Info","Office Code: " + officeCode);

        		ded.addToLog("Info","Postal Code: " + postalCodeResult);

        	}

        } catch (Exception e) 
          {

        				ded.addToLog("ERROR", e.getMessage());
        				return this.FAILED_RETURN_CODE;

        }
           
           
           ded.addToLog("INFO", "Response Status->" + status);
           
           if ("Success".equalsIgnoreCase(status)) {
        	   //respBody = resp.getResponseBody();
        	   String branchId =  officeCode;
        	   ded.addToLog("INFO", "Returned Branch Id is " + branchId);
        	   
        	   // Replace leading 0 and append 1 to the branch id to stay
        	   // Consistent with naming convention in ICM, CM, and Unity
        	   if ((branchId.length() == 3) && (branchId.substring(0, 1).equals("0"))) {
        		   branchId = "1" + branchId.substring(1,3);
        		   ded.addToLog("INFO", "Modified Branch Id is " + branchId);
        	   }
        	   else {
        		   if (branchId.length() == 2) {
        			   branchId = "1" + branchId;
        			   ded.addToLog("INFO", "Modified Branch Id is " + branchId);
        		   }
        	   }
        	   ded.setElementData(this.BRANCH_ID_ELEMENT_DATA_NAME, branchId);
        	   return_code = this.SUCCESS_RETURN_CODE;
           }
           else {
        	   ded.addToLog("INFO", "No Data!");
        	   ded.setElementData(this.BRANCH_ID_ELEMENT_DATA_NAME, "0");
        	   return_code = this.NO_DATA_RETURN_CODE;
           }    		 
           
    	   // Get the web service time elapsed (in seconds)
    	   Date end_time = new Date();
    	   //String time_elapsed = "z";
    	   long durationMs = end_time.getTime() - start_time.getTime();

    	   ded.addToLog("INFO", "Time Elapsed " + (durationMs / 1000) + " secs");

    	   if (durationMs > timeout) {
    	       ded.addToLog("ERROR", "Web service timeout exceeded (" + (timeout / 1000) + " seconds)");
    	   }
    	   return return_code;
       }
       
          
       catch(Exception ex)
       {
    	   ded.addToLog("ERROR_default", ex.getMessage());
    	   
    	   StringWriter errors = new StringWriter();
    	   ex.printStackTrace(new PrintWriter(errors));
    	   ded.addToLog("ERROR", errors.toString());
    	   ex.printStackTrace(System.out);
    	   return this.FAILED_RETURN_CODE;
       }
   } 
   
   private String getWSPassword() {
try {
	File config = new File("c:/clm/clm.properties");
	
	if (config.exists()) {
   	FileInputStream propertyFile = null;  
   	Properties properties = new Properties();
		// Create property file input stream
		propertyFile = new FileInputStream(config);
		properties.load(propertyFile);
		String wsUsername = properties.getProperty("wsUsername");
		String wsPassword = properties.getProperty("wsPassword");
		propertyFile.close();
		
		// Verify the user and password
		wsPassword = this.decryptBase64(wsPassword);
		return wsPassword;
		
		
	}
	return null;
	
}
catch (Exception ex) {
	return null;
}
}
private String decryptBase64 (String encryptedPW)  {
	Cipher decryptCipher = null;
  try {
	   // Initialize decrypter
      DESKeySpec key = new DESKeySpec("GOBROWNS".getBytes());
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	   decryptCipher = Cipher.getInstance("DES");
      decryptCipher.init(Cipher.DECRYPT_MODE, keyFactory.generateSecret(key));
      
      // Encode bytes to base64 to get a string
      byte [] decodedBytes = Base64.decodeBase64(encryptedPW.getBytes());

      // Decrypt
      byte[] unencryptedByteArray = decryptCipher.doFinal(decodedBytes);

      // Decode using utf-8
      return new String(unencryptedByteArray, "UTF8");
  }
  catch (Exception ex) {
	  
	   return null;
  }
}
   
}
