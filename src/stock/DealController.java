package stock;

import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import stock.helper.HttpHelper;
import stock.mmgridcommunicator.HazelCommunicator;
import stock.service.Stock2DB;

import com.hazelcast.core.IMap;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;

import common.answer.bean.dto.Datastorage;
import common.answer.bean.dto.Datastoragec;
import common.answer.bean.dto.Keyvalue;
import common.answer.bean.dto.SearchRecord;

@Controller(value = "DealController")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "deal" }) })
public class DealController extends ValidationAwareSupport implements
		ModelDriven<Object>, Validateable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String status;

	private static String mmaddress;

	private static String applogicname;

	private static String appserverinfo;

	private static int threadcnt = 5;

	private Collection<SearchRecord> list;

	private Map<String, Datastorage> mapdata = new HashMap<String, Datastorage>();

	public int getDatasize() {
		return datasize;
	}

	public void setDatasize(int datasize) {
		this.datasize = datasize;
	}

	private int datasize = -1;

	private String ztkey = "";

	@Autowired
	HttpHelper httpHelper = null;
	@Autowired
	protected Stock2DB stock2DB = null;

	private static final Log Logger = LogFactory.getLog(DealController.class);

	// GET /orders/1
	@SuppressWarnings("unchecked")
	public HttpHeaders show() {
		try {
			if (mmaddress == null) {
				List<Keyvalue> kvlst = stock2DB.getKeyvalue();
				HttpServletRequest request = ServletActionContext.getRequest();
				String path = request.getRequestURI();
				String[] arr = path.split("/");
				for (Keyvalue kv : kvlst) {

					if ("mmaddress".equals(kv.getKeyee())) {
						/*
						 * set hazelcast ip and port 127.0.0.1:5401
						 */
						setMmaddress(kv.getValuee());
					} else if ("applogicname".equals(kv.getKeyee())) {
						// avaliable logic app e.g. dayk weekk
						setApplogicname(kv.getValuee());
					} else if (arr[1].equals(kv.getKeyee())) {

						/*
						 * ulr for invoke logic app e.g.
						 * http://127.0.0.1:6182/dayk
						 */
						setAppserverinfo(kv.getValuee());
					} else if ("threadcnt".equals(kv.getKeyee())) {
						// avaliable logic app e.g. dayk weekk
						threadcnt = Integer.parseInt(kv.getValuee());

					}
				}
			}
			/*
			 * http://localhost:8082/stock_dispatch/deal/{tel:1870131245,dayk:
			 * '5,8,80,1'} id={tel:1870131245,dayk:'5,8,80,1'}
			 */
			JSONObject paramsInfo = JSONObject.fromObject(id);

			if (HazelCommunicator.datacopy.get("stocklist") == null) {
				HazelCommunicator.sychdataFromHazel(mmaddress, threadcnt);
			}
			Logger.info("original data size is "
					+ HazelCommunicator.datacopy.size());
			IMap<?, ?> map_remote = HazelCommunicator.map_remote;

			// get user's cell phone NO
			String tel = paramsInfo.getString("tel");
			// get user's cell phone NO
			if(paramsInfo.has("zt")){
				String ztparam = paramsInfo.getString("zt");

				String[] params = ztparam.split(",");

				if (ztparam != null && !"".equals(ztparam)) {
					for (int i = 0; i < params.length; i++) {
						ztkey = ztkey + Integer.parseInt(params[i]);
					}
				}
			}
			
			/* ready the data which will be passed to logic app by URL */
			Map<String, Object> mapfromjsonobj = (Map<String, Object>) JSONObject
					.toBean(paramsInfo, Map.class);
			// replace restrict word in httpclient
			mapfromjsonobj.put("mmaddress", mmaddress.replace(":", "&")
					.replace(".", "&&"));

			mapfromjsonobj.put("threadcnt", threadcnt);

			JSONObject tempjson = JSONObject.fromObject(mapfromjsonobj);

			String forwardid = URLEncoder.encode(tempjson.toString(), "UTF-8");
			System.out.println(forwardid);
			Set<?> s = paramsInfo.keySet();
			Iterator<?> it = s.iterator();

			String[] appserverinfoarr = appserverinfo.split(",");
			while (it.hasNext()) {
				String key = (String) it.next();
				// if deal name is available and match the user who can do that.
				// for now ,just make all of the deal can be run.

				if (applogicname.contains(key)) {
					status = httpHelper.sendRequest(
							"/" + key + "/" + forwardid, appserverinfoarr);
					if (!"done".equals(status)) {
						break;
					}
				}

			}
			if ("error".equals(status)) {
				map_remote.remove(tel);
				throw new Exception();
			}
			/*
			 * get user searched data's stock cd from hazelcast and get real
			 * data from memory copy by key.
			 */
			List<String> listAfterresearch = null;
			if (!"nodata".equals(status)) {
				listAfterresearch = (List<String>) map_remote.get(tel);
			}
			if (listAfterresearch != null && listAfterresearch.size() != 0) {
				int cnt = 50;
				if (listAfterresearch.size() <= 50) {
					cnt = listAfterresearch.size();
				}
				for (int i = 0; i < cnt; i++) {
					String stock_cd = listAfterresearch.get(i);
					Datastoragec ds = (Datastoragec) HazelCommunicator.datacopy
							.get(stock_cd);
					mapdata.put(stock_cd, ds);
				}

				datasize = listAfterresearch.size();
				Logger.info("searched data size is " + listAfterresearch.size());
			} else {
				datasize = 0;
				Logger.info("searched data size is 0");
			}

			map_remote.remove(tel);

			return new DefaultHttpHeaders("show");
		} catch (Exception e) {

			e.printStackTrace();
			return new DefaultHttpHeaders("error");
		}
	}

	public String getZtkey() {
		return ztkey;
	}

	public void setZtkey(String ztkey) {
		this.ztkey = ztkey;
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

	public String getMmaddress() {
		return mmaddress;
	}

	public synchronized void setMmaddress(String ulraddress) {
		DealController.mmaddress = ulraddress;
	}

	public static String getAppserverinfo() {
		return appserverinfo;
	}

	public synchronized static void setAppserverinfo(String appserverinfo) {
		DealController.appserverinfo = appserverinfo;
	}

	public Collection<SearchRecord> getList() {
		return list;
	}

	public void setList(Collection<SearchRecord> list) {
		this.list = list;
	}

	public Map<String, Datastorage> getMapdata() {
		return mapdata;
	}

	public void setMapdata(Map<String, Datastorage> mapdata) {
		this.mapdata = mapdata;

	}

	public static String getApplogicname() {
		return applogicname;
	}

	public static void setApplogicname(String applogicname) {
		DealController.applogicname = applogicname;
	}

}
