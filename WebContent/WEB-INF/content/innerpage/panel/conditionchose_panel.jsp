<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8"%>
<div data-role="panel" id="condtionpanel" data-position="left"
	data-display="overlay" data-theme="a">
	<div>
		<h3>选择条件</h3>
	</div>
	<!-- panel content goes here -->

	<div id="dayparam__choose" name="dayparam"
		class="ui-grid-b ui-responsive">
		<div class="ui-block-a ui-alt-icon">
			<label for="grid-checkbox-1">选择</label> <input id="grid-checkbox-1"
				name="grid-checkbox" type="checkbox">
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">日均参:x日均线连续x日，x%，均线向上或向下</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:5日均线连续8日，60%，均线向上(5,8,60,1)向下用-1表示如(5,8,60,-1)</div>
		</div>

	</div>

	<div id="weekparam_choose" name="weekparam"
		class="ui-grid-b ui-responsive">
		<div class="ui-block-a ui-alt-icon">
			<label for="grid-checkbox-2">选择</label> <input id="grid-checkbox-2"
				name="grid-checkbox" type="checkbox">
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">周均参:x周均线连续x周，x%，均线向上或向下</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:5周均线连续8周，60%，均线向上(5,8,60,1)向下用-1表示如(5,8,60,-1)</div>
		</div>

	</div>


	<div id="kxdparam__choose" name="kxdparam"
		class="ui-grid-b ui-responsive">
		<div class="ui-block-a ui-alt-icon">
			<label for="grid-checkbox-3">选择</label> <input id="grid-checkbox-3"
				name="grid-checkbox" type="checkbox">
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">日K形态:x日前开盘价格y日前收盘价格组成K线，向上或向下穿过?,?,?日均线</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:当日K线,向上穿过5,10,20日均线:(0,0,5,10,20)</div>
		</div>

	</div>
	
	<div id="kxwparam__choose" name="kxwparam"
		class="ui-grid-b ui-responsive">
		<div class="ui-block-a ui-alt-icon">
			<label for="grid-checkbox-4">选择</label> <input id="grid-checkbox-4"
				name="grid-checkbox" type="checkbox">
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">周K形态:x周前开盘价格y周前收盘价格组成K线，向上或向下穿过?,?,?周均线</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:当周K线,向上穿过5,10,20周均线:(0,0,5,10,20)</div>
		</div>

	</div>
	
	<div id="ztparam__choose" name="ztparam"
		class="ui-grid-b ui-responsive">
		<div class="ui-block-a ui-alt-icon">
			<label for="grid-checkbox-5">选择</label> <input id="grid-checkbox-5"
				name="grid-checkbox" type="checkbox">
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">涨停个数:x日内，涨停个数大于y个</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:100日内涨停个数大于10个:(100,10)</div>
		</div>

	</div>
	
	<div id="currentkparam__choose" name="currentkparam"
		class="ui-grid-b ui-responsive">
		<div class="ui-block-a ui-alt-icon">
			<label for="grid-checkbox-6">选择</label> <input id="grid-checkbox-6"
				name="grid-checkbox" type="checkbox">
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">当日K线:收阴或阳,不破x日均线,涨幅大于x%,小于x%</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:收阳,不破5日均线,涨幅大于-5%,小于5%:(1,5,-5,5)</div>
		</div>

	</div>
	
	<div id="duringkparam__choose" name="duringkparam"
		class="ui-grid-b ui-responsive">
		<div class="ui-block-a ui-alt-icon">
			<label for="grid-checkbox-7">选择</label> <input id="grid-checkbox-7"
				name="grid-checkbox" type="checkbox">
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">期间K线:x天前起,x天内,最高最低相差x%以上</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:当天算起,30天内,最高最低相差15%以上:(0,30,15)</div>
		</div>

	</div>
	<div id="bankuaiparam__choose" name="bankuaiparam"
		class="ui-grid-b ui-responsive">
		<div class="ui-block-a ui-alt-icon">
			<label for="grid-checkbox-8">选择</label> <input id="grid-checkbox-8"
				name="grid-checkbox" type="checkbox">
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">板块:选择特定开头的,不填代表全查询</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:选择00,30开头的股票:(00,30)</div>
		</div>

	</div>

	<div class="ui-grid-b ui-responsive">
		<div class="ui-block-a">
			<input type="button" value="确定" onclick="addcondtion();">
		</div>
		<div class="ui-block-b"></div>

		<div class="ui-block-c">
			<a href="#" data-rel="close" class="ui-btn ui-shadow ui-corner-all ">关闭</a>

		</div>
	</div>
</div>