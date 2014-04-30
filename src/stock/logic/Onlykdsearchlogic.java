package stock.logic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import common.answer.bean.dto.Alldata;
import common.answer.bean.dto.Datastorage;
import common.answer.helper.Analysis;
import common.answer.helper.LogicHelper;
@Service(value = "Onlykdsearchlogic")
public class Onlykdsearchlogic {

	public String search(List<String> stocklist, int[] paramsint,
			List<String> resultlstAfterSearch, Map<?, ?> localmemory) {
		for (String stock_cd : stocklist) {
			Datastorage ds = (Datastorage) localmemory.get(stock_cd);
			List<Alldata> listalldata = ds.getAlldatalist();
			int[] jx = new int[paramsint.length - 3];
			for (int i = 3; i < paramsint.length; i++) {
				jx[i - 3] = paramsint[i];
			}

			double kp1v = 0;

			double minprice = 0;
			double maxprice = 0;
			Map<String, Object> mp = null;

			// kp1日前收盘价格
			kp1v = listalldata.get(paramsint[0]).getPresent_price()
					.doubleValue();

			mp = Analysis.mmValue(
					listalldata.subList(paramsint[0], paramsint[1] + 1),
					paramsint[1] - paramsint[0] + 1);
			// kp2日内最低收盘价格
			minprice = listalldata.get((int) mp.get("min") + paramsint[0])
					.getPresent_price().doubleValue();
			// kp2日内最高收盘价格
			maxprice = listalldata.get((int) mp.get("max") + paramsint[0])
					.getPresent_price().doubleValue();

			double[] arrjx1 = new double[jx.length];
			double[] arrjx2 = new double[jx.length];
			boolean satisfyflg=true;
			for (int i = 0; i < jx.length; i++) {

				// 计算kp1日前?日均线
				arrjx1[i] = LogicHelper.caculateAveNDay(listalldata, jx[i],
						paramsint[0]);
				if (paramsint[2] > 0) {
					// 计算最低日前?日均线
					arrjx2[i] = LogicHelper.caculateAveNDay(listalldata, jx[i],
							(int) mp.get("min") + paramsint[0]);
				} else {
					// 计算最高日前?日均线
					arrjx2[i] = LogicHelper.caculateAveNDay(listalldata, jx[i],
							(int) mp.get("max") + paramsint[0]);
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
			resultlstAfterSearch.add(stock_cd);

		}


		return "done";
	}
	
}
