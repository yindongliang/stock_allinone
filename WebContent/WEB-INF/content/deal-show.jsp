<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<s:iterator value="mapdata" id="column">
	<tr>
		<th><s:property value="key" /></th>
		<td><s:property value="value.ften.stock_name" /></td>
		<td><s:property value="value.ften.jingzcbenifitrate" /></td>
		<td><s:property value="value.fullup_cnt" /></td>
		<td><s:property value="value.ften.liutonggu" /></td>
		<td><s:property value="value.ften.liutonggudongchigubili" /></td>
		<td><s:property value="value.record_date" /></td>
	</tr>
</s:iterator>
<s:if test="datasize>50">
提示：查询结果共<s:property value="datasize" />,显示前50条。
</s:if>


