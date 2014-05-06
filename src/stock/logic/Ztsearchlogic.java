package stock.logic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import common.answer.bean.dto.Datastoragec;
@Service(value = "Ztsearchlogic")
public class Ztsearchlogic {

	public String search(List<String> stocklist, int[] paramsint,
			List<String> resultlstAfterSearch, Map<?, ?> localmemory) {
		for (String stock_cd : stocklist) {
			Datastoragec ds = (Datastoragec) localmemory.get(stock_cd);
			Integer fullup = ds.getMpfullupmp().get(paramsint[0] + "" + paramsint[1]);
			int zt = 0;
			if (fullup == null) {
				int length=paramsint[0];
				if(ds.getAlldatalist().size()<paramsint[0]){
					length=ds.getAlldatalist().size();
				}
				for (int f = 0; f < length; f++) {
					if (ds.getAlldatalist().get(f).getPresent_price()
							.doubleValue() > 1.095 * ds.getAlldatalist().get(f)
							.getYt_close_price().doubleValue()) {
						zt++;

					}
				}
				if (zt < paramsint[1]) {
					continue;
				}
				ds.getMpfullupmp().put(paramsint[0] + "" + paramsint[1], zt);
				resultlstAfterSearch.add(stock_cd);
			}else{
				zt = fullup;
				resultlstAfterSearch.add(stock_cd);
			}
		

		}


		return "done";
	}
	
}
