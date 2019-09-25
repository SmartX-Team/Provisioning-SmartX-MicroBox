/**
 * @author Muhammad Ahmad
 * @version 0.1
 */

package smartx.multiview.collectors.CollectorsMain;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

import smartx.multiview.collectors.flow.*;
import smartx.multiview.collectors.resource.*;
import smartx.multiview.collectors.microbox.IOVisor_Data_DailyreportConsumer;
import smartx.multiview.collectors.microbox.JsonWrite;
import smartx.multiview.collectors.microbox.SNAP_Data_DailyreportConsumer;
import smartx.multiview.collectors.microbox.agentBoxAppStatus_DailyreportConsumer;
import smartx.multiview.collectors.microbox.agentCenterAppStatus_DailyreportConsumer;
import smartx.multiview.collectors.microbox.agentStatus_DailyreportConsumer;
//import smartx.multiview.collectors.microbox.Snap_influxdb;
import smartx.multiview.collectors.microbox.collectd_DailyreportConsumer;
//import smartx.multiview.collectors.microbox.collectd_DailyreportConsumer2;
//import smartx.multiview.collectors.microbox.collectd_DailyreportConsumer3;
import smartx.multiview.collectors.microbox.jsonRead;
import smartx.multiview.collectors.microbox.latency_DailyreportConsumer;
//import smartx.multiview.collectors.microbox.latency_DailyreportConsumer_extended_sites;
import smartx.multiview.collectors.microbox.ping_DailyreportConsumer;
//import smartx.multiview.collectors.topology.ping_latency_DailyreportConsumer;
import smartx.multiview.collectors.microbox.tcpDailyreportConsumer;

import smartx.multiview.collectors.microbox.udpDailyreportConsumer;

import smartx.multiview.collectors.microbox.vcenter_DailyreportConsumer1;
import smartx.multiview.DataLake.*;

public class CustomCollectorsMain 
{
	public static void main( String[] args )
    {
		/*PlaygroundConfigurationLoader configLoader = new PlaygroundConfigurationLoader();
		configLoader.getProperties();
		*/
		MongoDB_Connector MongoConnector = new MongoDB_Connector();
		//MongoConnector.setDbConnection(configLoader.getMONGO_DB_HOST(), configLoader.getMONGO_DB_PORT(), configLoader.getMONGO_DB_DATABASE());
		MongoConnector.setDbConnection("103.22.221.56", 27017, "multiviewdb");
		
		//Elasticsearch_Connector ESConnector = new Elasticsearch_Connector();
		//ESConnector.setClient(configLoader.getES_HOST(), configLoader.getES_PORT());

    	//Start Visibility Data Collection for Ping Data from SmartX Boxes
    	
		/*PingStatusCollectClass pingStatusCollect = new PingStatusCollectClass(configLoader.getVISIBILITY_CENTER(), MongoConnector, configLoader.getpboxMongoCollection(), configLoader.getpboxstatusMongoCollection(), configLoader. getpboxstatusMongoCollectionRT(), configLoader.getBoxType());
    	pingStatusCollect.start();
    	try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//Update Instant Visibility Collection for Box status using Ping Data
    	PingStatusUpdateClass pingStatusUpdate = new PingStatusUpdateClass(configLoader.getSmartXBox_USER(), configLoader.getSmartXBox_PASSWORD(), MongoConnector, configLoader.getpboxMongoCollection(), configLoader.getpboxstatusMongoCollectionRT(), configLoader.getBoxType(), configLoader.getOVS_VM_USER(), configLoader.getOVS_VM_PASSWORD());
    	pingStatusUpdate.start(); 
        try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
/*        //Start Visibility Collection for VM's Data
        OpenStackInstances instancelist = new OpenStackInstances(configLoader.getCTRL_Box_IP(), configLoader.getCTRL_Box_USER(), configLoader.getCTRL_Box_PASSWORD(), configLoader.getMONGO_DB_HOST(), configLoader.getMONGO_DB_PORT(), configLoader.getMONGO_DB_DATABASE(), configLoader.getvboxMongoCollection(), configLoader.getvboxMongoCollectionRT());
        instancelist.start();
                
        //Start Instant Visibility Collection for OVS Data
        ovsBridgeStatusClass bridgeStatus  = new ovsBridgeStatusClass(configLoader.getSmartXBox_USER(), configLoader.getSmartXBox_PASSWORD(), configLoader.getMONGO_DB_HOST(), configLoader.getMONGO_DB_PORT(), configLoader.getMONGO_DB_DATABASE(), configLoader.getpboxMongoCollection(), configLoader.getovsListMongoCollection(), configLoader.getovsstatusMongoCollection(), configLoader.getBoxType(), configLoader.getOVS_VM_USER(), configLoader.getOVS_VM_PASSWORD());
        bridgeStatus.start();
        
        //Start Instant Visibility Collection for Tenant-VLAN Mappings
        TenantVLANMapping tenantvlanMapping  = new TenantVLANMapping(configLoader.getCTRL_Box_IP(), configLoader.getCTRL_Box_USER(), configLoader.getCTRL_Box_PASSWORD(), configLoader.getMONGO_DB_HOST(), configLoader.getMONGO_DB_PORT(), configLoader.getMONGO_DB_DATABASE(), configLoader.gettenantVLANMongoCollection());
        tenantvlanMapping.start();
        
        //Start Visibility Collection for OpenStack Bridges Data
        OpenStackBridgesStatus osBridgeStatus  = new OpenStackBridgesStatus(configLoader.getSmartXBox_USER(), configLoader.getSmartXBox_PASSWORD(), configLoader.getMONGO_DB_HOST(), configLoader.getMONGO_DB_PORT(), configLoader.getMONGO_DB_DATABASE(), configLoader.getpboxMongoCollection(), configLoader.getflowConfigOpenStackMongoCollection(), configLoader.getflowConfigOpenStackMongoCollectionRT(), configLoader.getBoxType());
        osBridgeStatus.start();
        
        //Start Instant Visibility Collection for Vlan Mappings
        BridgesVLANMapping vlanMapping  = new BridgesVLANMapping(configLoader.getSmartXBox_USER(), configLoader.getSmartXBox_PASSWORD(), configLoader.getOVS_VM_USER(), configLoader.getOVS_VM_PASSWORD(), MongoConnector, configLoader.getpboxMongoCollection(), configLoader.getbridgevlanmapMongoCollection(), configLoader.getbridgevlanmapMongoCollectionRT(), configLoader.getVLAN_START(), configLoader.getVLAN_END(), configLoader.getBoxType());
        vlanMapping.start();
        
        //Start Visibility Collection for sFlow Flow Collection
    	String topic = "sFlow";
    	Timer timer = new Timer();
    	timer.schedule(new sFlowKafkaProducer(configLoader.getVISIBILITY_CENTER(), topic),0,10000);
    	try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //Start sFlow Kafka Consumer
        sFlowKafkaConsumer sFlowconsumer  = new sFlowKafkaConsumer(configLoader.getVISIBILITY_CENTER()+":9092", MongoConnector, ESConnector, configLoader.getsflowMongoCollection(), configLoader.getBoxType(), configLoader.gettenantVLANMongoCollection());
        sFlowconsumer.Consume();
        
        //Start IO Visor Kafka Consumer
        IOVisorKafkaConsumer iovisorconsumer = new IOVisorKafkaConsumer(configLoader.getVISIBILITY_CENTER()+":9092", MongoConnector);
        iovisorconsumer.Consume();
        
        //Start Visibility Collection for ODL Flow Rules Data
        SDNControllerStatus sdnStatus = new SDNControllerStatus(configLoader.getMONGO_DB_HOST(), configLoader.getMONGO_DB_PORT(), configLoader.getMONGO_DB_DATABASE(), configLoader.getflowConfigMongoCollection(), configLoader.getflowConfigMongoCollectionRT(), configLoader.getdevopscontrollers(), configLoader.getControllerUser(), configLoader.getControllerPassword());
        sdnStatus.start();
        
        //Start Visibility Collection for ODL Statistics Data
        SDNControllerStats sdnStats = new SDNControllerStats(configLoader.getMONGO_DB_HOST(), configLoader.getMONGO_DB_PORT(), configLoader.getMONGO_DB_DATABASE(), configLoader.getflowStatsMongoCollection(), configLoader.getflowStatsMongoCollectionRT(), configLoader.getdevopscontrollers(), configLoader.getControllerUser(), configLoader.getControllerPassword());
        sdnStats.start();*/
        
        //Start Visibility Collection for TCP Topology Data to be used for score
//        tcpTopologyKafkaConsumer tcptopology = new tcpTopologyKafkaConsumer(configLoader.getVISIBILITY_CENTER()+":9092", MongoConnector);
		
		
		
	
		
		// 	Add/Edit the list of micro-Box to be measured 
		JsonWrite jw= new JsonWrite();
		jw.main(args);
		jsonRead jr=new jsonRead();
		jr.main(args);
		
		
		Elasticsearch_Connector ESConnector = new Elasticsearch_Connector();
		ESConnector.setClient("103.22.221.56", 9300);

		
		
		ping_DailyreportConsumer ping_dailyreport = new ping_DailyreportConsumer("103.22.221.56:9092", MongoConnector,ESConnector);
		ping_dailyreport.start();
		
		latency_DailyreportConsumer latency_dailyreport = new latency_DailyreportConsumer("103.22.221.56:9092", MongoConnector,ESConnector);
		latency_dailyreport.start();
		
		vcenter_DailyreportConsumer1 vcenter_Data_Dailyreport = new vcenter_DailyreportConsumer1("103.22.221.56:9092", MongoConnector,ESConnector);
		vcenter_Data_Dailyreport.start();
		
		IOVisor_Data_DailyreportConsumer IOVisor_Data_dailyreport = new IOVisor_Data_DailyreportConsumer("103.22.221.56:9092", MongoConnector);
		IOVisor_Data_dailyreport.start();	
		
		tcpDailyreportConsumer tcpdailyreport = new tcpDailyreportConsumer("103.22.221.56:9092", MongoConnector);
		tcpdailyreport.start();
		
		udpDailyreportConsumer udpdailyreport = new udpDailyreportConsumer("103.22.221.56:9092", MongoConnector);
		udpdailyreport.start();
		
		
		
		///////////////////////////////////////Collectd////////////////////////////////////////////////////////////////////////////////
		/*
		collectd_DailyreportConsumer collect_Data_Dailyreport1 = new collectd_DailyreportConsumer("103.22.221.56:9092",MongoConnector, ESConnector);
		collect_Data_Dailyreport1.start();
			*/
		
		//Agent monitoring
		
		/*agentStatus_DailyreportConsumer agentStatus = new agentStatus_DailyreportConsumer("103.22.221.56:9092", MongoConnector,ESConnector);
		agentStatus.start();
		
		agentBoxAppStatus_DailyreportConsumer BoxagentAppStatus = new agentBoxAppStatus_DailyreportConsumer("103.22.221.56:9092", MongoConnector,ESConnector);
		BoxagentAppStatus.start();
		
		agentCenterAppStatus_DailyreportConsumer  CenteragentAppStatus= new agentCenterAppStatus_DailyreportConsumer("103.22.221.56:9092", MongoConnector,ESConnector);
		CenteragentAppStatus.start();*/

		
		
		
		
		
		
		
		
		/*SNAP_Data_DailyreportConsumer SNAP_Data_Dailyreport= new SNAP_Data_DailyreportConsumer("103.22.221.56:9092", MongoConnector);
		SNAP_Data_Dailyreport.start();*/
		//Start Visibility Collection for daily_Report
		
		

		
		
    }
}
