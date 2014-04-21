package stock.thread;

import java.util.List;
import java.util.Map;

import common.answer.bean.dto.Alldata;
import common.answer.bean.dto.Datastorage;
import common.answer.util.Caculator;

public class CurrentksearchThr extends Thread implements ThreadIf {

	private List<String> stocklist;
	private int[] paramsint;

	private List<String> resultlstAfterSearch;

	Map<?, ?> distributedMap;
	Map<Integer, String> mpcnt;
	int i;	
	
	public void run() {

		for (String stock_cd : stocklist) {
			
			Datastorage ds = (Datastorage) distributedMap.get(stock_cd);
			if(ds==null){
				continue;
			}
			//当日数据
	        List<Alldata> listalldata = ds.getAlldatalist();
	        double todayprice = listalldata.get(0).getPresent_price().doubleValue();
	        double yestodayprice = listalldata.get(0).getYt_close_price().doubleValue();
	        double ev = todayprice - Double.parseDouble(listalldata.get(0).getTd_open_price());

	        if (!(paramsint[0] > 0 && paramsint[1] > 0)) {
	            if (paramsint[0] > 0) {//查阳线
	                if (ev < 0) {
	                    continue;
	                }
	            } else if (paramsint[1] > 0) {//查阴线
	                if (ev >= 0) {
	                	 continue;
	                }
	            }
	            if (paramsint[0] < 0 && paramsint[1] < 0) {
	            	 continue;

	            }
	        }

	        double todayav = common.answer.helper.LogicHelper.caculateAveNDay(listalldata, paramsint[2], 0);
	        if (listalldata.get(0).getPresent_price().doubleValue() < todayav) {
	            continue;
	        }
	        double incr = Caculator.keepRound((todayprice - yestodayprice)
	                / yestodayprice * 100, 2);

	        if (incr < paramsint[3] || incr > paramsint[4]) {
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
			Map<Integer, String> mpcnt, int i,Map<?,?> otherinfo) {
	
		this.stocklist = stocklist;
		this.paramsint = paramsint;

		this.resultlstAfterSearch = resultlstAfterSearch;
		this.mpcnt = mpcnt;
		this.distributedMap = distributedMap;
		this.i = i;
	}
	
}
