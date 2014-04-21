package stock.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stock.mmgridcommunicator.HazelCommunicator;

public class ThreadListener {
	
	
	public static void listenThreads(int[] paramsint,List<String> resultlstAfterSearch,List<String> stocklist,Class<?> clazz,int threadcount,Map<?,?> otherinfo) throws InstantiationException, IllegalAccessException{
		
		Map<Integer, String> mpcnt = new HashMap<Integer, String>();
		int t = stocklist.size() / threadcount;
		List<ThreadIf> ls = new ArrayList<ThreadIf>();
		for (int i = 0; i < threadcount; i++) {
			ThreadIf threadif=(ThreadIf) clazz.newInstance();
			if (i == threadcount - 1) {
				
				threadif.setData(stocklist.subList(i * t,
						stocklist.size()), paramsint, resultlstAfterSearch,
						HazelCommunicator.datacopy, mpcnt, i,otherinfo);
				
				ls.add(threadif);
			} else {
				threadif.setData(stocklist.subList(i * t, (i + 1) * t), paramsint, resultlstAfterSearch,
						HazelCommunicator.datacopy, mpcnt, i,otherinfo);
				
				ls.add(threadif);

			}
			threadif.start();
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
