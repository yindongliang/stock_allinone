package stock.mmgridcommunicator.thread;

import java.util.List;
import java.util.Map;

public interface ThreadIf {
	
	public void setData(List<String> stocklist, int[] paramsint,
			Map<String,String> resultMpAfterSearch, Map<?, ?> distributedMap, Map<Integer, String> mpcnt, int i,Map<?,?> otherinfo);
	
	public void setData(List<String> stocklist, int[] paramsint,
			List<String> resultlstAfterSearch, Map<?, ?> distributedMap, Map<Integer, String> mpcnt, int i,Map<?,?> otherinfo);

	
	public void start();
}
