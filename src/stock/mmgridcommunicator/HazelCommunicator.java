package stock.mmgridcommunicator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import stock.mmgridcommunicator.thread.ThreadListener;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class HazelCommunicator {

	//public static IMap<?, ?> map_remote;
	public static Map<String, Object> datacopy = new HashMap<String, Object>();
	private static final Log Logger = LogFactory.getLog(HazelCommunicator.class);
	public static String mmaddress;
	public static int threadcount;
	public static boolean sychcompleteflg=false;
	static{
		Thread tr = new Thread() {
	
	        @Override
	        public void run() {
	            while (true) {
	            	if(mmaddress==null){
	            		try {
	            			Logger.info("need to fire the first request to read the data from hazelcast");
							sleep(5*60*1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            		continue;
	            	}
	            	
	            	if(datacopy.isEmpty()){
	            		try {
							sychdataFromHazel(mmaddress,threadcount);
							continue;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	}
	            	HazelcastInstance client =getClient();
	            	IMap<?, ?> map_remote = client.getMap("stockers");
	            	String stockdateinremote=((String) map_remote.get("stockdate"));
	            	client.shutdown();
	            	
	            	String stockdateinlocalm=(String)datacopy.get("stockdate");
	            	if(stockdateinremote!=null&&stockdateinlocalm!=null){
	            		if(!stockdateinremote.equals(stockdateinlocalm)){
	            			try {
								sychdataFromHazel(mmaddress,threadcount);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	            		}
	            		
	            	}
	            	try {
						Thread.sleep(5*1000*60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	    };
	    tr.start();
	}
	
	public static HazelcastInstance getClient(){
		ClientConfig clientConfig = new ClientConfig();
		ClientNetworkConfig clientnetworkConfig = new ClientNetworkConfig();
		clientnetworkConfig.addAddress(mmaddress);
		
		clientConfig.setNetworkConfig(clientnetworkConfig);
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(clientConfig);
		
		return  client;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public synchronized static void  sychdataFromHazel(String mmaddress,int threadcount) throws Exception {
		
		sychcompleteflg=false;
		if(datacopy!=null&&datacopy.get("stockdate")!=null){
			return;
		}
		
		HazelCommunicator.mmaddress=mmaddress;
		HazelCommunicator.threadcount=threadcount;
		HazelcastInstance client =getClient();
		IMap<?, ?> map_remote = client.getMap("stockers");
		List<String> stockcdls = null;
		int i=0;
		while(true){
			
			stockcdls =((List<String>) map_remote.get("stocklist"));
			if(stockcdls==null){
				if(i>10){
					break;
				}
				Thread.sleep(1);
				i++;
				continue;
			}else{
				break;
			}
			
		}
		if(i>10){
			throw new Exception("maybe there is nodata in hazelcast");
		}
		String stockdate=((String) map_remote.get("stockdate"));
		datacopy.clear();
		datacopy.put("stocklist", stockcdls);
		
		Logger.info("data is synchronizing with memory");
		if(stockcdls==null){
			Logger.info("no data in memory grid");
		}
		ThreadListener.listenThreads(stockcdls, map_remote, datacopy, threadcount);
		
		datacopy.put("stockdate", stockdate);
		sychcompleteflg=true;
		Logger.info("data is synchronized with memory");
		client.shutdown();
	}
	

	

	
}
