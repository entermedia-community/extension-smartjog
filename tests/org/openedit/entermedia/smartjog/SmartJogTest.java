package org.openedit.entermedia.smartjog;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openedit.entermedia.BaseEnterMediaTest;

import com.smartjog.webservices.Company;
import com.smartjog.webservices.Delivery;
import com.smartjog.webservices.Server;
import com.smartjog.webservices.ServerFile;

public class SmartJogTest extends BaseEnterMediaTest
{
	private static final Log log = LogFactory.getLog(SmartJogTest.class);

	public SmartJogTest(String inArg0)
	{
		super(inArg0);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		String rootPath = System.getProperty("oe.root.path");
		if (rootPath == null)
		{
			System.setProperty("oe.root.path", "base");
		}

	}

	public void testListCompanies() throws Exception
	{

		String base = getRoot().getParent().toString();
		SmartJog ssc = new SmartJog(base + "/etc/ssl/openssl/");
		
		log.info("SSL Configuration done.");
		try
		{
			log.info("Connecting to SmartJog WebServices server....");

			/*
			Company[] companyArray = ssc.getAllCompanies();
			if (companyArray != null && companyArray.length > 0)
			{
				log.info("Connection successful.");
				log.info("Displaying all companies :");
				for (Company company : companyArray)
				{
					if (company != null)
					{
						log.info("< companyId=" + company.getCompanyId()
								+ " - companyName=" + company.getCompanyName()
								+ " >");
					}
				}
			}
			*/
			
			/*
			Server[] serverArray = ssc.getAllServers();
			if (serverArray != null && serverArray.length > 0)
			{
				log.info("Displaying all servers :");
				for (Server server : serverArray)
				{
					if (server != null)
					{
						log.info("< serverId=" + server.getServerId()
								+ " - serverName=" + server.getServerName()
								+ " >");
					}
				}
			}
			
			*/
			
			ServerFile sf = ssc.getServerFile(null, "em-test.txt");
			
			log.info("< serverFileId="
					+ sf.getServerFileId() + " - fileName="
					+ sf.getFileName() + " - fileMd5="
					+ sf.getFileMd5() + " >");
			
			/*
			Delivery delivery = ssc.deliverFileToServer(14573, sf.getServerFileId());
			if (delivery != null)
			{
				log.info("< deliveryId="+delivery.getDeliveryId()
						+ " - trackingNumber="
						+ delivery.getTrackingNumber()+" - filename="
						+ delivery.getFilename() +" - md5="+delivery.getMd5()
						+ " - status="+delivery.getStatus()+" >");
			}
			*/
			
			/*
			ServerFile[] serverFileArray = ssc.getAllServerFiles(null);
			if (serverFileArray != null && serverFileArray.length > 0)
			{
				log.info("Displaying all available files on your servers or shared to employees of your company :");
				for (ServerFile serverFile : serverFileArray)
				{
					if (serverFile != null)
					{
						log.info("< serverFileId="
								+ serverFile.getServerFileId() + " - fileName="
								+ serverFile.getFileName() + " - fileMd5="
								+ serverFile.getFileMd5() + " >");
					}
				}
			}
			*/
			
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	public void xtestList() throws Exception
	{
		String base = getRoot().getParent().toString();
		SmartJog ssc = new SmartJog(base + "/etc/ssl/stores/");

		log.info("SSL Configuration done.");
		try
		{
			log.info("Connecting to SmartJog WebServices server....");

			/*
			 * Displays all companies
			 */
			Company[] companyArray = ssc.getAllCompanies();
			if (companyArray != null && companyArray.length > 0)
			{
				log.info("Connection successful.");
				log.info("Displaying all companies :");
				for (Company company : companyArray)
				{
					if (company != null)
					{
						log.info("< companyId=" + company.getCompanyId()
								+ " - companyName=" + company.getCompanyName()
								+ " >");
					}
				}
			}

			/*
			 * Displays all servers
			 */
			Server[] serverArray = ssc.getAllServers();
			if (serverArray != null && serverArray.length > 0)
			{
				log.info("Displaying all servers :");
				for (Server server : serverArray)
				{
					if (server != null)
					{
						log.info("< serverId=" + server.getServerId()
								+ " - serverName=" + server.getServerName()
								+ " >");
					}
				}
			}

			/*
			 * Displays all available files on your servers or shared to
			 * employees of your company
			 */
			ServerFile[] serverFileArray = ssc.getAllServerFiles(null);
			if (serverFileArray != null && serverFileArray.length > 0)
			{
				log.info("Displaying all available files on your servers or shared to employees of your company :");
				for (ServerFile serverFile : serverFileArray)
				{
					if (serverFile != null)
					{
						log.info("< serverFileId="
								+ serverFile.getServerFileId() + " - fileName="
								+ serverFile.getFileName() + " - fileMd5="
								+ serverFile.getFileMd5() + " >");
					}
				}
			}

			/*
			 * Creates 3 deliveries and displays their outgoing tracking
			 */
			/*
			 * int serverId = serverArray[0].getServerId(); int[]
			 * serverFileIdArray = {serverFileArray[0].getServerFileId(),
			 * serverFileArray[1].getServerFileId(),
			 * serverFileArray[2].getServerFileId()}; Delivery[] deliveryArray =
			 * ssc.deliverFilesToServer(serverId, serverFileIdArray); if
			 * (deliveryArray != null && deliveryArray.length > 0) { log.info(
			 * "3 deliveries has just been created. Displaying their outgoing tracking :"
			 * ); for (Delivery delivery : deliveryArray) { if (delivery !=
			 * null) { log.info("< deliveryId="+delivery.getDeliveryId()
			 * +" - trackingNumber="
			 * +delivery.getTrackingNumber()+" - filename="+
			 * delivery.getFilename(
			 * )+" - md5="+delivery.getMd5()+" - status="+delivery
			 * .getStatus()+" >"); } } }
			 */

			log.info("End of SmartJog WebServices Simple Client.");
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

}
