package stock.thread;

import java.util.List;
import java.util.Map;

import common.answer.bean.dto.Datastorage;
import common.answer.bean.dto.WeekData;
import common.answer.helper.LogicHelper;

public class WeekksearchThr extends Thread implements ThreadIf {

	private List<String> stocklist;
	private int[] paramsint;

	private List<String> resultlstAfterSearch;

	Map<?, ?> distributedMap;
	Map<Integer, String> mpcnt;
	int i;	
	
	public void run() {

		double[] wkaves;

		for (String stock_cd : stocklist) {

			if (!stock_cd.startsWith("60") && !stock_cd.startsWith("30")
					&& !stock_cd.startsWith("00")) {
				continue;
			}

			Datastorage ds = (Datastorage) distributedMap.get(stock_cd);
			if(ds==null){
				continue;
			}
			
			List<WeekData> weekdatas = ds.getWeekdatalist();
	        wkaves = new double[paramsint[1] + 1];
	        if (weekdatas.size() < paramsint[0] + paramsint[1] + 1) {
	        	continue;
	        }
	        for (int i = 0; i < wkaves.length; i++) {
	           wkaves[i] = LogicHelper.caculateAveNWeek(weekdatas, paramsint[0], i);
	        }

	        if (!LogicHelper.bfblx(wkaves, paramsint[3], paramsint[2])) {
	        	continue;
	        };
			
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
