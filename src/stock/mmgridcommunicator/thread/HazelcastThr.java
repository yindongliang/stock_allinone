package stock.mmgridcommunicator.thread;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import common.answer.bean.dto.Datastorage;
import common.answer.bean.dto.Datastoragec;

public class HazelcastThr extends Thread {

	private List<String> stocklist;

	Map<?, ?> distributedMap;
	Map<Integer, String> mpcnt;
	int i;
	Map<String, Object> datacopy;

	public void run() {
		for (String stock_cd : stocklist) {

			Datastoragec ds = new Datastoragec();
			Datastorage dsremote;
			int i=0;
			while(true){
				
				dsremote=(Datastorage) distributedMap.get(stock_cd);
				if(dsremote==null){
					if(i>10){
						break;
					}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i++;
					continue;
				}else{
					break;
				}
				
			}
			
			
			BeanUtils.copyProperties((Datastorage) distributedMap.get(stock_cd), ds);
			
			datacopy.put(stock_cd, ds);

		}

		mpcnt.put(i, "");
	}
	
	public void setData(List<String> stocklist, Map<?, ?> distributedMap,Map<String, Object> datacopy,
			Map<Integer, String> mpcnt, int i) {

		this.stocklist = stocklist;

		this.mpcnt = mpcnt;
		this.distributedMap = distributedMap;
		this.i = i;
		this.datacopy = datacopy;
	}



}
