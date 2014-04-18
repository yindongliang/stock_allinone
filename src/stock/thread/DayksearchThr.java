package stock.thread;

import java.util.List;
import java.util.Map;

import common.answer.bean.dto.Alldata;
import common.answer.bean.dto.Datastorage;
import common.answer.helper.LogicHelper;

public class DayksearchThr extends Thread implements ThreadIf {

	private List<String> stocklist;
	private int[] paramsint;

	private List<String> resultlstAfterSearch;

	Map<?, ?> distributedMap;
	Map<Integer, String> mpcnt;
	int i;	
	
	public void run() {

		double[] daves;

		for (String stock_cd : stocklist) {

			if (!stock_cd.startsWith("60") && !stock_cd.startsWith("30")
					&& !stock_cd.startsWith("00")) {
				continue;
			}

			Datastorage ds = (Datastorage) distributedMap.get(stock_cd);
			if(ds==null){
				continue;
			}
			List<Alldata> listalldata = ds.getAlldatalist();
			daves = new double[paramsint[1] + 1];
			if (listalldata.size() < paramsint[0] + paramsint[1] + 1) {
				continue;
			}

			for (int i = 0; i < daves.length; i++) {

				daves[i] = LogicHelper.caculateAveNDay(listalldata,
						paramsint[0], i);

			}

			if (!LogicHelper.bfblx(daves, paramsint[3], paramsint[2])) {

				continue;
			}
			;

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
			Map<Integer, String> mpcnt, int i) {
	
		this.stocklist = stocklist;
		this.paramsint = paramsint;

		this.resultlstAfterSearch = resultlstAfterSearch;
		this.mpcnt = mpcnt;
		this.distributedMap = distributedMap;
		this.i = i;
	}
	
}
