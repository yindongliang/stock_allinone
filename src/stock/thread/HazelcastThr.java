package stock.thread;

import java.util.List;
import java.util.Map;

import stock.HazelCommunicator;

public class HazelcastThr extends Thread implements ThreadIf {

	private List<String> stocklist;

	Map<?, ?> distributedMap;
	Map<Integer, String> mpcnt;
	int i;
	Map<String, Object> datacopy;

	public void run() {
		for (String stock_cd : stocklist) {

			datacopy.put(stock_cd, distributedMap.get(stock_cd));

		}

		mpcnt.put(i, "");
	}

	public synchronized void setResultList(List<String> ls, String stock_cd) {
		ls.add(stock_cd);
	}

	@Override
	public void setData(List<String> stocklist, int[] paramsint,
			List<String> resultlstAfterSearch, Map<?, ?> distributedMap,
			Map<Integer, String> mpcnt, int i) {

		this.stocklist = stocklist;

		this.mpcnt = mpcnt;
		this.distributedMap = HazelCommunicator.map_remote;
		this.i = i;
		this.datacopy = HazelCommunicator.datacopy;
	}

}
