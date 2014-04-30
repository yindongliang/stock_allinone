package stock;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import stock.helper.HttpHelper;
import stock.logic.Bankuaisearchlogic;
import stock.logic.Currentksearch;
import stock.logic.Dayksearchlogic;
import stock.logic.Duringksearchlogic;
import stock.logic.Onlykdsearchlogic;
import stock.logic.Onlykwsearchlogic;
import stock.logic.Weekksearchlogic;
import stock.logic.Ztsearchlogic;
import stock.mmgridcommunicator.HazelCommunicator;
import stock.service.Stock2DB;

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

	@Autowired
	Dayksearchlogic dayksearchlogic=null;
	@Autowired
	Bankuaisearchlogic bankuaisearchlogic=null;
	@Autowired
	Currentksearch currentksearch=null;
	@Autowired
	Weekksearchlogic weekksearchlogic=null;
	@Autowired
	Onlykdsearchlogic onlykdsearchlogic=null;
	@Autowired
	Onlykwsearchlogic onlykwsearchlogic=null;
	@Autowired
	Duringksearchlogic duringksearchlogic=null;
	@Autowired
	Ztsearchlogic ztsearchlogic=null;
	
	private static final Log Logger = LogFactory.getLog(DealController.class);

	
	@SuppressWarnings("unchecked")
	public HttpHeaders show() {
		try {
			if (mmaddress == null) {
				List<Keyvalue> kvlst = stock2DB.getKeyvalue();
				
				
				for (Keyvalue kv : kvlst) {

					if ("mmaddress".equals(kv.getKeyee())) {
						/*
						 * set hazelcast ip and port 127.0.0.1:5401
						 */
						setMmaddress(kv.getValuee());
					} else if ("applogicname".equals(kv.getKeyee())) {
						// avaliable logic app e.g. dayk weekk
						setApplogicname(kv.getValuee());
					} else if ("threadcnt".equals(kv.getKeyee())) {
						// avaliable logic app e.g. dayk weekk
						threadcnt = Integer.parseInt(kv.getValuee());

					}
				}
			}
			
			if (!HazelCommunicator.sychcompleteflg) {
				HazelCommunicator.sychdataFromHazel(mmaddress, threadcnt);
			}else{
				if(HazelCommunicator.datacopy.get("stockdate")==null){
					return new DefaultHttpHeaders("nodata");
				}
			}
			Logger.info("original data size is "
					+ HazelCommunicator.datacopy.size());
			Logger.info("the id is :"+id);
			JSONObject paramsInfo = JSONObject.fromObject(id);
			
			// get user's cell phone NO
			String tel = paramsInfo.getString("tel");
			Logger.info("the tel is :"+tel);
			Logger.info("url is :"+URLEncoder.encode(paramsInfo.toString(), "UTF-8"));
			
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
			
			Set<?> s = paramsInfo.keySet();
			Iterator<?> it = s.iterator();
			List<String> resultlstAfterSearch = null;
			List<String> stocklist=(List<String>)HazelCommunicator.datacopy.get("stocklist");
			if(stocklist==null){
				return new DefaultHttpHeaders("nodata");
			}
			while (it.hasNext()) {
				String key = (String) it.next();
				if(!applogicname.contains(key)){
					continue;
				}
				
				String[] params = ((String) paramsInfo.get(key)).split(",");
				
				int[] paramsint = new int[params.length];
				for (int i = 0; i < params.length; i++) {
					paramsint[i] = Integer.parseInt(params[i]);
				}
				if("dayk".equals(key)){
					
					resultlstAfterSearch = new ArrayList<String>();
					status = dayksearchlogic.search(stocklist, paramsint, resultlstAfterSearch, HazelCommunicator.datacopy);
					if (!"done".equals(status)) {
						break;
					}
					stocklist=resultlstAfterSearch;
				}else if("weekk".equals(key)){
					resultlstAfterSearch = new ArrayList<String>();
					status = weekksearchlogic.search(stocklist, paramsint, resultlstAfterSearch, HazelCommunicator.datacopy);
					if (!"done".equals(status)) {
						break;
					}
					stocklist=resultlstAfterSearch;
				}else if("bankuai".equals(key)){
					resultlstAfterSearch = new ArrayList<String>();
					status = bankuaisearchlogic.search(stocklist, paramsint, resultlstAfterSearch, HazelCommunicator.datacopy);
					if (!"done".equals(status)) {
						break;
					}
					stocklist=resultlstAfterSearch;
				}else if("currentk".equals(key)){
					resultlstAfterSearch = new ArrayList<String>();
					status = currentksearch.search(stocklist, paramsint, resultlstAfterSearch, HazelCommunicator.datacopy);
					if (!"done".equals(status)) {
						break;
					}
					stocklist=resultlstAfterSearch;
				}else if("duringk".equals(key)){
					resultlstAfterSearch = new ArrayList<String>();
					status = duringksearchlogic.search(stocklist, paramsint, resultlstAfterSearch, HazelCommunicator.datacopy);
					if (!"done".equals(status)) {
						break;
					}
					stocklist=resultlstAfterSearch;
				}else if("onlykd".equals(key)){
					resultlstAfterSearch = new ArrayList<String>();
					status = onlykdsearchlogic.search(stocklist, paramsint, resultlstAfterSearch, HazelCommunicator.datacopy);
					if (!"done".equals(status)) {
						break;
					}
					stocklist=resultlstAfterSearch;
				}else if("onlykw".equals(key)){
					resultlstAfterSearch = new ArrayList<String>();
					status = onlykwsearchlogic.search(stocklist, paramsint, resultlstAfterSearch, HazelCommunicator.datacopy);
					if (!"done".equals(status)) {
						break;
					}
					stocklist=resultlstAfterSearch;
				}else if("zt".equals(key)){
					resultlstAfterSearch = new ArrayList<String>();
					status = ztsearchlogic.search(stocklist, paramsint, resultlstAfterSearch, HazelCommunicator.datacopy);
					if (!"done".equals(status)) {
						break;
					}
					stocklist=resultlstAfterSearch;
				}
				
			}
			if ("error".equals(status)) {
				//map_remote.remove(tel);
				throw new Exception();
			}
			/*
			 * get user searched data's stock cd from hazelcast and get real
			 * data from memory copy by key.
			 */
			List<String> listAfterresearch = resultlstAfterSearch;

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

	

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(String id) {

		this.id = id;
	}

	

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

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

}
