package stock.logic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import common.answer.bean.dto.Alldata;
import common.answer.bean.dto.Datastorage;
@Service(value = "Duringksearchlogic")
public class Duringksearchlogic {

	public String search(List<String> stocklist, int[] paramsint,
			List<String> resultlstAfterSearch, Map<?, ?> localmemory) {
			// 天前算起，日内 最高最低差计算
				if (paramsint[1] + paramsint[0] > 70 || paramsint[1] < 0) {
					return "done";
				}
				for (String stock_cd : stocklist) {
					

					Datastorage ds = (Datastorage) localmemory.get(stock_cd);
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
			resultlstAfterSearch.add(stock_cd);

		}


		return "done";
	}
	
}
