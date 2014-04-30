package stock.logic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import common.answer.bean.dto.Datastorage;
import common.answer.bean.dto.WeekData;
import common.answer.helper.LogicHelper;
@Service(value = "Weekksearchlogic")
public class Weekksearchlogic {

	public String search(List<String> stocklist, int[] paramsint,
			List<String> resultlstAfterSearch, Map<?, ?> localmemory) {
		double[] wkaves;

		for (String stock_cd : stocklist) {

			if (!stock_cd.startsWith("60") && !stock_cd.startsWith("30")
					&& !stock_cd.startsWith("00")) {
				continue;
			}

			Datastorage ds = (Datastorage) localmemory.get(stock_cd);
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
			resultlstAfterSearch.add(stock_cd);

		}


		return "done";
	}
	
}
