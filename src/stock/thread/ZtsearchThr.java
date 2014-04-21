package stock.thread;

import java.util.List;
import java.util.Map;

import common.answer.bean.dto.Datastorage;
import common.answer.bean.dto.Datastoragec;

public class ZtsearchThr extends Thread implements ThreadIf {

	private List<String> stocklist;
	private int[] paramsint;

	private List<String> resultlstAfterSearch;

	Map<?, ?> distributedMap;
	Map<Integer, String> mpcnt;
	int i;

	public void run() {

		for (String stock_cd : stocklist) {
			Datastoragec ds = (Datastoragec) distributedMap.get(stock_cd);
			Integer fullup = ds.getMpfullupmp().get(paramsint[0] + "" + paramsint[1]);
			int zt = 0;
			if (fullup == null) {

				
				for (int f = 0; f < paramsint[0]; f++) {
					if (ds.getAlldatalist().get(f).getPresent_price()
							.doubleValue() > 1.095 * ds.getAlldatalist().get(f)
							.getYt_close_price().doubleValue()) {
						zt++;

					}
				}
				if (zt < paramsint[1]) {
					continue;
				}
				ds.getMpfullupmp().put(paramsint[0] + "" + paramsint[1], zt);
				setResultList(resultlstAfterSearch, stock_cd);
			}else{
				zt = fullup;
				setResultList(resultlstAfterSearch, stock_cd);
			}
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
