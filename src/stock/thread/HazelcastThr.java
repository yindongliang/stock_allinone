package stock.thread;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import stock.mmgridcommunicator.HazelCommunicator;

import common.answer.bean.dto.Datastorage;
import common.answer.bean.dto.Datastoragec;

public class HazelcastThr extends Thread implements ThreadIf {

	private List<String> stocklist;

	Map<?, ?> distributedMap;
	Map<Integer, String> mpcnt;
	int i;
	Map<String, Object> datacopy;

	public void run() {
		for (String stock_cd : stocklist) {

			Datastoragec ds = new Datastoragec();
			BeanUtils.copyProperties((Datastorage) distributedMap.get(stock_cd), ds);
			
			datacopy.put(stock_cd, ds);

		}

		mpcnt.put(i, "");
	}

	public synchronized void setResultList(List<String> ls, String stock_cd) {
		ls.add(stock_cd);
	}

	@Override
	public void setData(List<String> stocklist, int[] paramsint,
			List<String> resultlstAfterSearch, Map<?, ?> distributedMap,
			Map<Integer, String> mpcnt, int i, Map<?, ?> otherinfo) {

		this.stocklist = stocklist;

		this.mpcnt = mpcnt;
		this.distributedMap = HazelCommunicator.map_remote;
		this.i = i;
		this.datacopy = HazelCommunicator.datacopy;
	}

}
