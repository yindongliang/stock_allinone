package stock.logic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import common.answer.bean.dto.Alldata;
import common.answer.bean.dto.Datastorage;
import common.answer.util.Caculator;

@Service(value = "Currentksearch")
public class Currentksearch {

	public String search(List<String> stocklist, int[] paramsint,
			List<String> resultlstAfterSearch, Map<?, ?> localmemory) {
		for (String stock_cd : stocklist) {

			Datastorage ds = (Datastorage) localmemory.get(stock_cd);
			if (ds == null) {
				continue;
			}
			// 当日数据
			List<Alldata> listalldata = ds.getAlldatalist();
			double todayprice = listalldata.get(0).getPresent_price()
					.doubleValue();
			double yestodayprice = listalldata.get(0).getYt_close_price()
					.doubleValue();
			double ev = todayprice
					- Double.parseDouble(listalldata.get(0).getTd_open_price());

			if (!(paramsint[0] > 0 && paramsint[1] > 0)) {
				if (paramsint[0] > 0) {// 查阳线
					if (ev < 0) {
						continue;
					}
				} else if (paramsint[1] > 0) {// 查阴线
					if (ev >= 0) {
						continue;
					}
				}
				if (paramsint[0] < 0 && paramsint[1] < 0) {
					continue;

				}
			}

			double todayav = common.answer.helper.LogicHelper.caculateAveNDay(
					listalldata, paramsint[2], 0);
			if (listalldata.get(0).getPresent_price().doubleValue() < todayav) {
				continue;
			}
			double incr = Caculator.keepRound((todayprice - yestodayprice)
					/ yestodayprice * 100, 2);

			if (incr < paramsint[3] || incr > paramsint[4]) {
				continue;
			}
			resultlstAfterSearch.add(stock_cd);

		}

		return "done";
	}

}
