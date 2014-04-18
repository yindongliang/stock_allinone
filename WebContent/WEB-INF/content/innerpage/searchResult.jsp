<%@ page contentType="text/html; charset=utf-8"%>
<span id="msgzone"></span>
<form>
    <input id="filterTable-input" data-type="search">
</form>
				<table data-role="table" id="table-custom-2"
					data-mode="columntoggle"
					class="ui-body-d ui-shadow table-stripe ui-responsive"
					data-column-btn-theme="a"
					data-column-btn-text="选择显示列..."
					data-column-popup-theme="a"
					data-filter="true"  
					data-input="#filterTable-input"  >
					<thead >
						<tr class="ui-bar-d">
							<th data-priority="2">股票代码</th>
							<th>股票名称</th>
							<th class="sortable" data-priority="3"><abbr title="">最近净资产收益率</abbr></th>
							<th class="sortable" data-priority="1"><abbr title="">涨停个数</abbr></th>
							<th class="sortable" data-priority="5"><abbr title="">流通股本(万股)</abbr></th>
							<th class="sortable" data-priority="6"><abbr title="">流通股东持股比例</abbr></th>
							<th data-priority="7">数据更新日期</th>
						</tr>
					</thead>
					<tbody id="dataResult">
						
						
					</tbody>
				</table>