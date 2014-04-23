package stock.mmgridcommunicator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import stock.thread.HazelcastThr;
import stock.thread.ThreadListener;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class HazelCommunicator {

	public static IMap<?, ?> map_remote;
	public static Map<String, Object> datacopy = new HashMap<String, Object>();
	private static final Log Logger = LogFactory.getLog(HazelCommunicator.class);
	public static String mmaddress;
	public static int threadcount;
	static{
		Thread tr = new Thread() {
	
	        @Override
	        public void run() {
	            while (true) {
	            	if(mmaddress==null){
	            		try {
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
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	}
	            	
	            	String stockdateinremote=((String) map_remote.get("stockdate"));
	            	String stockdateinlocalm=(String)datacopy.get("stockdate");
	            	if(!stockdateinremote.equals(stockdateinlocalm)){
	            		try {
							sychdataFromHazel(mmaddress,threadcount);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	}
	            }
	        }
	    };
	    tr.start();
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public synchronized static void  sychdataFromHazel(String mmaddress,int threadcount) throws Exception {
		ClientConfig clientConfig = new ClientConfig();
		HazelCommunicator.mmaddress=mmaddress;
		HazelCommunicator.threadcount=threadcount;
		clientConfig.addAddress(mmaddress);
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(clientConfig);
		map_remote = client.getMap("stockers");
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
		datacopy.put("stockdate", stockdate);
		if(stockcdls==null){
			Logger.info("no data in memory grid");
		}
		
		ThreadListener.listenThreads(null, null, stockcdls, HazelcastThr.class, threadcount,null);
	}
	

	public static String setStockListForfutherDeal(JSONObject paramsInfo,String tel,List<String> stocklist) throws Exception{
		
		if (HazelCommunicator.datacopy.get("stocklist")==null) {
			HazelCommunicator.sychdataFromHazel(paramsInfo.getString("mmaddress").replace("&&", ".").replace("&", ":"),5);
		}
		Logger.info("data size is " + HazelCommunicator.datacopy.size());
		IMap<String, List<String>> map_remote = (IMap<String, List<String>>) HazelCommunicator.map_remote;

		Logger.info("get cached data ");
		
		// get data from memory,if it exists
		List<String> resultls = (List<String>) map_remote.get(tel);

		Logger.info("user tel is " + tel);
		// if searched result is empty 
		if (resultls == null || resultls.size() == 0) {
			
			stocklist.addAll((List<String>) HazelCommunicator.datacopy.get("stocklist"));
			if (stocklist.size() == 0) {
				Logger.info("stock list is null need to check memory ");
			}
		} else {
			// if exist,so search the data from memory,small range
			Logger.info("search range data size is " + resultls.size());
			stocklist.addAll(resultls);
		}
		if (stocklist.size() == 0) {
			// MemcachedUtil.tearDownAfterClass();
			return "nodata";
		}
		return "listSetComplete";
	}

	
}
