package stock.logic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import common.answer.bean.dto.Alldata;
import common.answer.bean.dto.Datastorage;
import common.answer.helper.LogicHelper;
@Service(value = "Dayksearchlogic")
public class Dayksearchlogic {

	public String search(List<String> stocklist, int[] paramsint,
			List<String> resultlstAfterSearch, Map<?, ?> localmemory) {
		// TODO Auto-generated method stub
		double[] daves;

		for (String stock_cd : stocklist) {

			if (!stock_cd.startsWith("60") && !stock_cd.startsWith("30")
					&& !stock_cd.startsWith("00")) {
				continue;
			}

			Datastorage ds = (Datastorage) localmemory.get(stock_cd);
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
			
			resultlstAfterSearch.add(stock_cd);
			

		}

		return "done";
	}
	
}
