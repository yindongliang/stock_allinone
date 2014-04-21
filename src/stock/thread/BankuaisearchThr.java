package stock.thread;

import java.util.List;
import java.util.Map;

public class BankuaisearchThr extends Thread implements ThreadIf {

	private List<String> stocklist;
	private int[] paramsint;

	private List<String> resultlstAfterSearch;

	Map<?, ?> distributedMap;
	Map<Integer, String> mpcnt;
	int i;	
	
	public void run() {

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
