package stock.thread;

import java.util.List;
import java.util.Map;

import common.answer.bean.dto.Alldata;
import common.answer.bean.dto.Datastorage;
import common.answer.helper.Analysis;
import common.answer.helper.LogicHelper;

public class OnlykdsearchThr extends Thread implements ThreadIf {

	private List<String> stocklist;
	private int[] paramsint;

	private List<String> resultlstAfterSearch;

	Map<?, ?> distributedMap;
	Map<Integer, String> mpcnt;
	int i;

	public void run() {

		for (String stock_cd : stocklist) {
			Datastorage ds = (Datastorage) distributedMap.get(stock_cd);
			List<Alldata> listalldata = ds.getAlldatalist();
			int[] jx = new int[paramsint.length - 3];
			for (int i = 3; i < paramsint.length; i++) {
				jx[i - 3] = paramsint[i];
			}

			double kp1v = 0;

			double minprice = 0;
			double maxprice = 0;
			Map<String, Object> mp = null;

			// kp1��ǰ���̼۸�
			kp1v = listalldata.get(paramsint[0]).getPresent_price()
					.doubleValue();

			mp = Analysis.mmValue(
					listalldata.subList(paramsint[0], paramsint[1] + 1),
					paramsint[1] - paramsint[0] + 1);
			// kp2����������̼۸�
			minprice = listalldata.get((int) mp.get("min") + paramsint[0])
					.getPresent_price().doubleValue();
			// kp2����������̼۸�
			maxprice = listalldata.get((int) mp.get("max") + paramsint[0])
					.getPresent_price().doubleValue();

			double[] arrjx1 = new double[jx.length];
			double[] arrjx2 = new double[jx.length];
			boolean satisfyflg=true;
			for (int i = 0; i < jx.length; i++) {

				// ����kp1��ǰ?�վ���
				arrjx1[i] = LogicHelper.caculateAveNDay(listalldata, jx[i],
						paramsint[0]);
				if (paramsint[2] > 0) {
					// ���������ǰ?�վ���
					arrjx2[i] = LogicHelper.caculateAveNDay(listalldata, jx[i],
							(int) mp.get("min") + paramsint[0]);
				} else {
					// ���������ǰ?�վ���
					arrjx2[i] = LogicHelper.caculateAveNDay(listalldata, jx[i],
							(int) mp.get("max") + paramsint[0]);
				}

				if (paramsint[2] > 0) {// �ϴ�����
					if (kp1v < arrjx1[i] || minprice > arrjx2[i]) {
						satisfyflg=false;
						break;
					}
				} else {// �´�����
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
