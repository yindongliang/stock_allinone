package stock.logic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service(value = "Bankuaisearchlogic")
public class Bankuaisearchlogic {

	public String search(List<String> stocklist, int[] paramsint,
			List<String> resultlstAfterSearch, Map<?, ?> localmemory) {
		for (String stock_cd : stocklist) {
			boolean satisfyflg=false;
			for (int i =0;i<paramsint.length;i++){
				if(paramsint[i]==0){
					if(stock_cd.startsWith("00")){
						satisfyflg=true;
					}
				}else {
					if(stock_cd.startsWith(paramsint[i]+"")){
						satisfyflg=true;
					}
				}
			}
			if(satisfyflg==false){
				continue;
			}
			resultlstAfterSearch.add(stock_cd);

		}


		return "done";
	}
	
}
