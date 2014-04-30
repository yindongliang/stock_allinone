package stock.logic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import common.answer.bean.dto.Alldata;
import common.answer.bean.dto.Datastorage;
import common.answer.bean.dto.WeekData;
import common.answer.helper.Analysis;
import common.answer.helper.LogicHelper;
@Service(value = "Onlykwsearchlogic")
public class Onlykwsearchlogic {

	public String search(List<String> stocklist, int[] paramsint,
			List<String> resultlstAfterSearch, Map<?, ?> localmemory) {
		for (String stock_cd : stocklist) {
			Datastorage ds = (Datastorage) localmemory.get(stock_cd);
			List<WeekData> weekdatas=ds.getWeekdatalist();
			
			int[] jx = new int[paramsint.length - 3];
			for (int i = 3; i < paramsint.length; i++) {
				jx[i - 3] = paramsint[i];
			}

			double kp1v = 0;

			double minprice = 0;
			double maxprice = 0;
			Map<String, Object> mp = null;

			// kp1��ǰ���̼۸�
            kp1v = weekdatas.get(paramsint[0]).getClose_price().doubleValue();
            mp = Analysis.mmValueweek(weekdatas.subList(paramsint[0], paramsint[1] + 1), paramsint[1] - paramsint[0] + 1);
            // kp2����������̼۸�
            minprice = weekdatas.get((int) mp.get("min") + paramsint[0]).getClose_price().doubleValue();
            // kp2����������̼۸�
            maxprice = weekdatas.get((int) mp.get("max") + paramsint[0]).getClose_price().doubleValue();

			double[] arrjx1 = new double[jx.length];
			double[] arrjx2 = new double[jx.length];
			boolean satisfyflg=true;
			for (int i = 0; i < jx.length; i++) {

				// ����kp1��ǰ?�ܾ���
                arrjx1[i] = LogicHelper.caculateAveNWeek(weekdatas, (jx[i]), paramsint[0]);
                if (paramsint[2] > 0) {
                    // ���������ǰ?�ܾ���
                    arrjx2[i] = LogicHelper.caculateAveNWeek(weekdatas, (jx[i]), (int) mp.get("min") + paramsint[0]);
                } else {
                    // ���������ǰ?�ܾ���
                    arrjx2[i] = LogicHelper.caculateAveNWeek(weekdatas, (jx[i]), (int) mp.get("max") + paramsint[0]);
                }

				if (paramsint[2] > 0) {// �ϴ�����
					if (kp1v < arrjx1[i] || minprice > arrjx2[i]) {
						satisfyflg=false;
						break;
					}
				} else {// �´�����
					if (kp1v > arrjx1[i] || maxprice < arrjx2[i]) {
						satisfyflg=false;
						break;
					}
				}
			}
			if(!satisfyflg){
				continue;
			}
			resultlstAfterSearch.add(stock_cd);

		}


		return "done";
	}
	
}
