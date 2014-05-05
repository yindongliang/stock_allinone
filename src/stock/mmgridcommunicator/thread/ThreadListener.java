package stock.mmgridcommunicator.thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stock.mmgridcommunicator.HazelCommunicator;

public class ThreadListener {
	
	
	public static void listenThreads(List<String> stocklist,Map<?, ?> distributedMap,Map<String, Object> datacopy,int threadcount) throws InstantiationException, IllegalAccessException{
		
		Map<Integer, String> mpcnt = new HashMap<Integer, String>();
	
		
		
		if(threadcount==1){
			HazelcastThr hazelcastThr=new HazelcastThr();
			hazelcastThr.setData(stocklist, distributedMap,
					datacopy, mpcnt, 0);
			hazelcastThr.start();
		}else{
			int t = stocklist.size() / threadcount;
			
			for (int i = 0; i < threadcount; i++) {
				HazelcastThr hazelcastThr=new HazelcastThr();
				if (i == threadcount - 1) {
					
					hazelcastThr.setData(stocklist.subList(i * t,
							stocklist.size()), distributedMap,
							datacopy, mpcnt, i);
					
					
				} else {
					hazelcastThr.setData(stocklist.subList(i * t, (i + 1) * t),distributedMap,
							datacopy, mpcnt, i);
					
					

				}
				hazelcastThr.start();
			}
		}
		
		
		while (true) {
			boolean continueflg=false;
			for(int i=0;i<threadcount;i++){
				if(mpcnt.get(i)==null){
					continueflg=true;
					try {
						Thread.sleep(1);
						break;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(!continueflg){
				break;
			}
			
		}
	
	}
	

	
}
