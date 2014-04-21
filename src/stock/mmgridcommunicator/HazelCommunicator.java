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
	@SuppressWarnings({ "deprecation", "unchecked" })
	public synchronized static void  sychdataFromHazel(String mmaddress,int threadcount) throws InstantiationException, IllegalAccessException {
		ClientConfig clientConfig = new ClientConfig();

		clientConfig.addAddress(mmaddress);
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(clientConfig);
		map_remote = client.getMap("stockers");
		List<String> stockcdls = null;
		while(true){
			stockcdls =((List<String>) map_remote.get("stocklist"));
			if(stockcdls==null){
				continue;
			}else{
				break;
			}
			
		}
		datacopy.put("stocklist", stockcdls);
		
		if(stockcdls==null){
			Logger.info("no data in memory grid");
		}
		
		ThreadListener.listenThreads(null, null, stockcdls, HazelcastThr.class, threadcount,null);
	}
	

	public static String setStockListForfutherDeal(JSONObject paramsInfo,String tel,List<String> stocklist) throws InstantiationException, IllegalAccessException{
		
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

	static class Fastcopy extends Thread {
		private List<String> stocklist;

		Map<?, ?> map;
		boolean complete = false;
		Map<Integer, String> mpcnt;
		int i;

		public Fastcopy(List<String> stocklist, Map<?, ?> map, Map<Integer, String> mpcnt, int i) {
			this.stocklist = stocklist;
			this.mpcnt = mpcnt;
			this.i = i;
			this.map = map;
			this.start();
		}

		public void run() {

			

			for (String stock_cd : stocklist) {

				datacopy.put(stock_cd, map.get(stock_cd));
				
			}

			mpcnt.put(i, "");
		}

	}
}
