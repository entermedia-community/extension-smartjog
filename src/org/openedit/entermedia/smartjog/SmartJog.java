package org.openedit.entermedia.smartjog;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.openedit.OpenEditException;
import com.openedit.page.manage.PageManager;
import com.smartjog.webservices.Company;
import com.smartjog.webservices.DeliverRequest;
import com.smartjog.webservices.DeliverResponse;
import com.smartjog.webservices.Delivery;
import com.smartjog.webservices.GetCompaniesRequest;
import com.smartjog.webservices.GetCompaniesResponse;
import com.smartjog.webservices.GetOutgoingDeliveryRequest;
import com.smartjog.webservices.GetOutgoingDeliveryResponse;
import com.smartjog.webservices.GetServerFilesRequest;
import com.smartjog.webservices.GetServerFilesResponse;
import com.smartjog.webservices.GetServersRequest;
import com.smartjog.webservices.GetServersResponse;
import com.smartjog.webservices.Recipient;
import com.smartjog.webservices.Server;
import com.smartjog.webservices.ServerFile;
import com.smartjog.webservices.SmartjogWebservicesLocator;
import com.smartjog.webservices.SmartjogWebservicesPortType;

public class SmartJog
{
	protected SmartjogWebservicesPortType service = null;
	protected SmartjogWebservicesLocator locator = null;
	
	private static final Log log = LogFactory.getLog(SmartJog.class);

	/**
	 * Certificates configuration
	 */
	protected String trustStoreName = "truststore.jks";
	protected String trustStorePassword = "qasklp";
	protected String certificateName = "client-cert.p12";
	protected String certificatePassword = "qasklp";
	protected PageManager fieldPageManager;
   
    
	
	


	public PageManager getPageManager() {
		return fieldPageManager;
	}

	public void setPageManager(PageManager fieldPageManager) {
		this.fieldPageManager = fieldPageManager;

	}

	// example: https://webservices.demo.smartjog.tv/sjws2.0/
	protected String getWSAddress()
	{
		return "https://webservices.smartjog.com/sjws2.0/"; // this is the
															// production server
		
	}
public void init(){
	intializedSSLWithPKCS12();
}
	public SmartJog()
	{
		log.info("SMARTJOG CREATED");
	}

	public SmartjogWebservicesPortType getService() throws OpenEditException
	{
		if (service == null)
		{
			try
			{
				
				locator = new SmartjogWebservicesLocator();
				
				locator
						.setSmartjogWebservicesPortEndpointAddress(getWSAddress());
				locator.getEngine().refreshGlobalOptions();
				service = locator.getSmartjogWebservicesPort();	
			}
			catch (Exception e)
			{
				throw new OpenEditException(e);
			}

		}
		return service;
	}

	public void intializedSSLWithPKCS12()
	{
		/* WARNING - The server needs to be retarted to accept new values.
		 * Watch out for calling this code with first one catalogid, and then another,
		 * because it WON'T WORK.
		 */
		String sslDir = getPageManager().getPage("/WEB-INF/certs/").getContentItem().getAbsolutePath();
		if (!sslDir.endsWith("/"))
			sslDir += "/";
		
		log.info("Path of your certificate : " + sslDir + certificateName);
		//System.setProperty("javax.net.debug", "all");
		System.setProperty("javax.net.ssl.keyStore", sslDir + certificateName);
		System.setProperty("javax.net.ssl.keyStorePassword",
				certificatePassword);
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
	}

	public Company[] getAllCompanies() throws RemoteException
	{
		Company[] companyArray = null;

		GetCompaniesRequest input = new GetCompaniesRequest();

		GetCompaniesResponse output = getService().getCompanies(input);
		if (output != null)
		{
			companyArray = output.getCompanyArray();
		}

		return companyArray;
	}

	public Server[] getAllServers() throws RemoteException
	{
		Server[] serverArray = null;

		GetServersRequest input = new GetServersRequest();

		GetServersResponse output = getService().getServers(input);
		if (output != null)
		{
			serverArray = output.getServerArray();
		}

		return serverArray;
	}

	public ServerFile[] getAllServerFiles(Integer serverId)
			throws RemoteException
	{
		ServerFile[] serverFileArray = null;

		GetServerFilesRequest input = new GetServerFilesRequest();
		if (serverId != null)
		{
			input.setServerId(serverId);
		}

		GetServerFilesResponse output = getService().getServerFiles(input);
		if (output != null)
		{
			serverFileArray = output.getServerFileArray();
		}

		return serverFileArray;
	}

	public ServerFile getServerFile(Integer serverId, String inFileName)
			throws RemoteException
	{
		GetServerFilesRequest request = new GetServerFilesRequest();
		if (serverId != null)
		{
			request.setServerId(serverId);
		}
		
		request.setFileName(inFileName);

		GetServerFilesResponse response = getService().getServerFiles(request);
		if(response.getServerFileArray() != null){
			return  response.getServerFileArray(0);
		} else{
			return null;
		}
		
	}

	public Delivery deliverFileToServer(int serverId, int serverFileId)
			throws RemoteException
	{
		int[] serverFileIdArray =
		{ serverFileId };
		Delivery[] deliveryArray = deliverFilesToServer(serverId,
				serverFileIdArray);
		if (deliveryArray != null && deliveryArray.length > 0)
			return deliveryArray[0];
		return null;
	}

	public Delivery[] deliverFilesToServer(int serverId, int[] serverFileIdArray)
			throws RemoteException
	{
		Delivery[] deliveryArray = null;

		Recipient recipient = new Recipient();
		recipient.setServerId(serverId);
		recipient.setBillto("0000SJ00000");
		recipient.setBillref("Billing Reference");
		recipient.setEMailArray(new String[]
		{ "eservices@smartjog.com" });
		recipient.setComment("A simple comment");
		Recipient[] recipientArray =
		{ recipient };

		DeliverRequest input = new DeliverRequest();
		input.setServerFileIdArray(serverFileIdArray);
		input.setRecipientArray(recipientArray);

		DeliverResponse output = getService().deliver(input);
		if (output != null)
		{
			deliveryArray = output.getDeliveryArray();
		}

		return deliveryArray;
	}
	
	public Status updateStatus(String inTrackingNumber, int inServerId, int inDeliveryId)
	{
		GetOutgoingDeliveryRequest req = new GetOutgoingDeliveryRequest(); 
		req.setDeliveryIdArray(new int[]{inServerId});
		req.setServerIdArray(new int[]{inServerId});
		req.setTrackingNumberArray(new String[]{inTrackingNumber});
		Status status = new Status();
		try
		{
			GetOutgoingDeliveryResponse res = getService().getOutgoingDelivery(req);
			Delivery delivery = res.getDeliveryArray()[0];
			//Complete, Processing, Aborting, Aborted)
			String complete = delivery.getCompletion();
			if( complete != null)
			{
				status.setPercent(complete);
			}				
			status.setStatus(delivery.getStatus());
		}
		catch (Exception e)
		{
			throw new OpenEditException(e);
		}
		return status;
	}
}