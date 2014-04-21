package stock.thread;

import java.util.List;
import java.util.Map;

import common.answer.bean.dto.Alldata;
import common.answer.bean.dto.Datastorage;

public class DuringksearchThr extends Thread implements ThreadIf {

	private List<String> stocklist;
	private int[] paramsint;

	private List<String> resultlstAfterSearch;

	Map<?, ?> distributedMap;
	Map<Integer, String> mpcnt;
	int i;

	public void run() {

		// 天前算起，日内 最高最低差计算
		if (paramsint[1] + paramsint[0] > 70 || paramsint[1] < 0) {
			return;
		}
		for (String stock_cd : stocklist) {
			

			Datastorage ds = (Datastorage) distributedMap.get(stock_cd);
			if (ds == null) {
				continue;
			}
			List<Alldata> listalldata = ds.getAlldatalist();

			Map<String, Object> mp2 = common.answer.helper.Analysis.mmValue(
					listalldata.subList(paramsint[0], paramsint[0]
							+ paramsint[1]), paramsint[1]);
			double minprice2 = 0;
			double maxprice2 = 0;
			minprice2 = listalldata.get((int) mp2.get("min") + paramsint[0])
					.getPresent_price().doubleValue();

			maxprice2 = listalldata.get((int) mp2.get("max") + paramsint[0])
					.getPresent_price().doubleValue();
			if ((maxprice2 - minprice2) * 100 / maxprice2 < paramsint[2]) {
				continue;
			} 
			setResultList(resultlstAfterSearch, stock_cd);

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
		this.paramsint = paramsint;

		this.resultlstAfterSearch = resultlstAfterSearch;
		this.mpcnt = mpcnt;
		this.distributedMap = distributedMap;
		this.i = i;
	}

}
