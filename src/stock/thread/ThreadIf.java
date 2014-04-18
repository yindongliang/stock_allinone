package stock.thread;

import java.util.List;
import java.util.Map;

public interface ThreadIf {
	
	public void setData(List<String> stocklist, int[] paramsint,
			List<String> resultlstAfterSearch, Map<?, ?> cache, Map<Integer, String> mpcnt, int i);
	
	public void start();
}
