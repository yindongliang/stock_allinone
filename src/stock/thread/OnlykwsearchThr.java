package stock.thread;

import java.util.List;
import java.util.Map;

import common.answer.bean.dto.Datastorage;
import common.answer.bean.dto.WeekData;
import common.answer.helper.Analysis;
import common.answer.helper.LogicHelper;

public class OnlykwsearchThr extends Thread implements ThreadIf {

	private List<String> stocklist;
	private int[] paramsint;

	private List<String> resultlstAfterSearch;

	Map<?, ?> distributedMap;
	Map<Integer, String> mpcnt;
	int i;

	public void run() {

		for (String stock_cd : stocklist) {
			Datastorage ds = (Datastorage) distributedMap.get(stock_cd);
			List<WeekData> weekdatas=ds.getWeekdatalist();
			
			int[] jx = new int[paramsint.length - 3];
			for (int i = 3; i < paramsint.length; i++) {
				jx[i - 3] = paramsint[i];
			}

			double kp1v = 0;

			double minprice = 0;
			double maxprice = 0;
			Map<String, Object> mp = null;

			// kp1周前收盘价格
            kp1v = weekdatas.get(paramsint[0]).getClose_price().doubleValue();
            mp = Analysis.mmValueweek(weekdatas.subList(paramsint[0], paramsint[1] + 1), paramsint[1] - paramsint[0] + 1);
            // kp2周内最低收盘价格
            minprice = weekdatas.get((int) mp.get("min") + paramsint[0]).getClose_price().doubleValue();
            // kp2周内最高收盘价格
            maxprice = weekdatas.get((int) mp.get("max") + paramsint[0]).getClose_price().doubleValue();

			double[] arrjx1 = new double[jx.length];
			double[] arrjx2 = new double[jx.length];
			boolean satisfyflg=true;
			for (int i = 0; i < jx.length; i++) {

				// 计算kp1周前?周均线
                arrjx1[i] = LogicHelper.caculateAveNWeek(weekdatas, (jx[i]), paramsint[0]);
                if (paramsint[2] > 0) {
                    // 计算最低周前?周均线
                    arrjx2[i] = LogicHelper.caculateAveNWeek(weekdatas, (jx[i]), (int) mp.get("min") + paramsint[0]);
                } else {
                    // 计算最低周前?周均线
                    arrjx2[i] = LogicHelper.caculateAveNWeek(weekdatas, (jx[i]), (int) mp.get("max") + paramsint[0]);
                }

				if (paramsint[2] > 0) {// 上穿均线
					if (kp1v < arrjx1[i] || minprice > arrjx2[i]) {
						satisfyflg=false;
						break;
					}
				} else {// 下穿均线
					if (kp1v > arrjx1[i] || maxprice < arrjx2[i]) {
						satisfyflg=false;
						break;
					}
				}
			}
			if(!satisfyflg){
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
