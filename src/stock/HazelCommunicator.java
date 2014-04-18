package stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public static void sychdataFromHazel(String mmaddress,int threadcount) throws InstantiationException, IllegalAccessException {
		ClientConfig clientConfig = new ClientConfig();

		clientConfig.addAddress(mmaddress);
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(clientConfig);
		map_remote = client.getMap("stockers");
		List<String> stockcdls = ((List<String>) map_remote.get("stocklist"));
		datacopy.put("stocklist", stockcdls);
		
		if(stockcdls==null){
			Logger.info("no data in memory grid");
		}
//		Map<Integer, String> mpcnt = new HashMap<Integer, String>();
		
		ThreadListener.listenThreads(null, null, stockcdls, HazelcastThr.class, threadcount);
		
//		int t = stockcdls.size() / threadcount;
//		List<Fastcopy> ls = new ArrayList<Fastcopy>();
//		for (int i = 0; i < threadcount; i++) {
//
//			if (i == threadcount - 1) {
//
//				ls.add((new Fastcopy(stockcdls.subList(i * t,
//						stockcdls.size()), map_remote, mpcnt, i)));
//			} else {
//				ls.add((new Fastcopy(stockcdls.subList(i * t, (i + 1) * t),
//						map_remote, mpcnt, i)));
//
//			}
//		}
//		while (mpcnt.size() != threadcount) {
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

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
