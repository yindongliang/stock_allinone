package stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import stock.thread.DayksearchThr;
import stock.thread.ThreadListener;

import com.hazelcast.core.IMap;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;

@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "dayk" }) })
public class DaykController extends ValidationAwareSupport implements
		ModelDriven<Object>, Validateable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String status;

	private static final Log Logger = LogFactory.getLog(DaykController.class);


	// GET /orders/1
	@SuppressWarnings("unchecked")
	public HttpHeaders show() throws Exception {

		// get xml and client info
		Logger.info("in corelogic the id is " + id);

		JSONObject paramsInfo = JSONObject.fromObject(id);

		if (HazelCommunicator.datacopy.isEmpty()) {
			HazelCommunicator.sychdataFromHazel(paramsInfo.getString("mmaddress").replace("&&", ".").replace("&", ":"),5);
		}
		Logger.info("data size is " + HazelCommunicator.datacopy.size());
		IMap<String, List<String>> map_remote = (IMap<String, List<String>>) HazelCommunicator.map_remote;

		Logger.info("get cached data ");
		// data management
		List<String> stocklist = null;

		// parameters
		String tel = paramsInfo.getString("tel");

		// get data from memory,if it exists
		List<String> resultls = (List<String>) map_remote.get(tel);

		Logger.info("user tel is " + tel);
		// if searched result is empty 
		if (resultls == null || resultls.size() == 0) {
			resultls = new ArrayList<String>();
			stocklist = (List<String>) HazelCommunicator.datacopy.get("stocklist");
		} else {
			// if exist,so search the data from memory,small range
			stocklist = resultls;
		}
		if (stocklist == null || stocklist.size() == 0) {
			// MemcachedUtil.tearDownAfterClass();
			return new DefaultHttpHeaders("nodata");
		}
		
		String[] params = ((String) paramsInfo.get("dayk")).split(",");
		int threadcount =(int) paramsInfo.get("threadcnt");
		int[] paramsint = new int[params.length];
		for (int i = 0; i < params.length; i++) {
			paramsint[i] = Integer.parseInt(params[i]);
		}


		List<String> resultlstAfterSearch = Collections
				.synchronizedList(new ArrayList<String>());

		Logger.info("start");
		
		ThreadListener.listenThreads(paramsint, resultlstAfterSearch, stocklist, DayksearchThr.class, threadcount);
		

		Logger.info("end");

		Logger.info("caculating finished");

		if (resultlstAfterSearch.size() == 0) {
			return new DefaultHttpHeaders("nodata");
		} else {
			map_remote.put(tel, resultlstAfterSearch);
			return new DefaultHttpHeaders("done");
		}

	}

	

	// GET /orders
	public HttpHeaders index() throws Exception {

		return new DefaultHttpHeaders("index").disableCaching();

	}

	// GET /orders/1/edit
	public String edit() {
		return "edit";
	}

	// GET /orders/new
	public String editNew() {

		return "editNew";
	}

	// GET /orders/1/deleteConfirm
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	// DELETE /orders/1
	public String destroy() {

		addActionMessage("Order removed successfully");
		return "success";
	}

	// POST /orders
	public HttpHeaders create() {

		addActionMessage("New order created successfully");
		return new DefaultHttpHeaders("success").setLocationId("");
	}

	// PUT /orders/1
	public String update() {
		addActionMessage("Order updated successfully");
		return "success";
	}

	public void validate() {
		// if (model.getClientName() == null || model.getClientName().length()
		// ==0) {
		// addFieldError("clientName", "The client name is empty");
		// }
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(String id) {

		this.id = id;
	}

	//
	// public Object getModel() {
	// return (list != null ? list : model);
	// }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
