package common.answer.bean.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Datastoragec extends Datastorage implements Serializable{

   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map<String,Integer> mpfullupmp=new HashMap<String,Integer>();
    public Map<String, Integer> getMpfullupmp() {
		return mpfullupmp;
	}

	public void setMpfullupmp(Map<String, Integer> mpfullupmp) {
		this.mpfullupmp = mpfullupmp;
	}

	
}
