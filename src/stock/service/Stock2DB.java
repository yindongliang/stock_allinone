package stock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.answer.bean.dto.Ften;
import common.answer.bean.dto.Keyvalue;

@Service(value = "Stock2DB")
public class Stock2DB {

   
    @Autowired
    protected UpdateDAO updateDAO = null;
    @Autowired
    protected QueryDAO queryDAO = null;
    
    private static Logger log = Logger.getLogger(Stock2DB.class);

    public List<Keyvalue> getKeyvalue() {
    	 
         List<Keyvalue> Keyvalue = queryDAO.executeForObjectList("keyvalue.selectKeyvalueAll", null);
    	
        return Keyvalue;
    }

   
}


