<%@ page contentType="text/html; charset=utf-8"%>
<style>
<!--
label.error {
	color: red;
	font-size: 16px;
	font-weight: normal;
	line-height: 1.4;
	margin-top: 0.5em;
	width: 100%;
	float: none;
}
-->
</style>
<script type="text/javascript">
	
</script>
<form id="conditonForm">


	<div id="dayparam_con" name="dayparam" class="ui-grid-c ui-responsive">

		<div class="ui-block-a ui-alt-icon">
			<a href="#popupDialog" onclick="setObjectbeforedelete(this)"
				data-rel="popup" data-position-to="window" data-transition="pop"
				class="ui-shadow ui-btn ui-corner-all ui-btn-icon-left ui-icon-delete">
				删除条件 </a>
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">日均参:x日均线连续?日，?%，均线向上或向下</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:5日均线连续8日，60%，均线向上(5,8,60,1)向下用-1表示如(5,8,60,-1)</div>
		</div>
		<div class="ui-block-d">

			<label for="dayk" class="ui-hidden-accessible"></label> <input
				type="text" name="dayk_condition_text" id="dayk"
				data-clear-btn="true" value="">
		</div>
	</div>
	<div id="weekparam_con" name="weekparam"
		class="ui-grid-c ui-responsive">
		<div class="ui-block-a ui-alt-icon">
			<a href="#popupDialog" onclick="setObjectbeforedelete(this)"
				data-rel="popup" data-position-to="window" data-transition="pop"
				class="ui-shadow ui-btn ui-corner-all ui-btn-icon-left ui-icon-delete">
				删除条件 </a>
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">周均参:x周均线连续?周，?%，均线向上或向下</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:5周均线连续8周，60%，均线向上(5,8,60,1)向下用-1表示如(5,8,60,-1)</div>
		</div>
		<div class="ui-block-d">

			<label for="weekk" class="ui-hidden-accessible"></label> <input
				type="text" data-clear-btn="true" name="weekk_condition_text"
				id="weekk" value="">

		</div>
	</div>

	<div id="kxdparam_con" name="kxdparam" class="ui-grid-c ui-responsive">

		<div class="ui-block-a ui-alt-icon">
			<a href="#popupDialog" onclick="setObjectbeforedelete(this)"
				data-rel="popup" data-position-to="window" data-transition="pop"
				class="ui-shadow ui-btn ui-corner-all ui-btn-icon-left ui-icon-delete">
				删除条件 </a>
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">日K形态:x1日前开盘价格x2日前收盘价格组成K线，向上或向下穿过?,?,?日均线</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:当日K线,向上穿过5,10,20日均线:(0,0,1,5,10,20)</div>
		</div>
		<div class="ui-block-d">

			<label for="onlykd" class="ui-hidden-accessible"></label> <input
				type="text" name="onlykd_condition_text" id="onlykd"
				data-clear-btn="true" value="">

		</div>


	</div>

	<div id="kxwparam_con" name="kxwparam" class="ui-grid-c ui-responsive">

		<div class="ui-block-a ui-alt-icon">
			<a href="#popupDialog" onclick="setObjectbeforedelete(this)"
				data-rel="popup" data-position-to="window" data-transition="pop"
				class="ui-shadow ui-btn ui-corner-all ui-btn-icon-left ui-icon-delete">
				删除条件 </a>
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">周K形态:x1周前开盘价格x2周前收盘价格组成K线，向上或向下穿过?,?,?周均线</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:当周K线,向下穿过5,10,20周均线:(0,0,-1,5,10,20)</div>
		</div>
		<div class="ui-block-d">

			<label for="onlykw" class="ui-hidden-accessible"></label> <input
				type="text" name="onlykw_condition_text" id="onlykw"
				data-clear-btn="true" value="">

		</div>




	</div>
	<div id="ztparam_con" name="ztparam" class="ui-grid-c ui-responsive">

		<div class="ui-block-a ui-alt-icon">
			<a href="#popupDialog" onclick="setObjectbeforedelete(this)"
				data-rel="popup" data-position-to="window" data-transition="pop"
				class="ui-shadow ui-btn ui-corner-all ui-btn-icon-left ui-icon-delete">
				删除条件 </a>
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">涨停个数:x日内，涨停个数大于y个</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:100日内涨停个数大于10个:(100,10)</div>
		</div>
		<div class="ui-block-d">

			<label for="zt" class="ui-hidden-accessible"></label> <input
				type="text" name="zt_condition_text" id="zt" data-clear-btn="true"
				value="">

		</div>
	</div>

	<div id="currentkparam_con" name="currentkparam" class="ui-grid-c ui-responsive">

		<div class="ui-block-a ui-alt-icon">
			<a href="#popupDialog" onclick="setObjectbeforedelete(this)"
				data-rel="popup" data-position-to="window" data-transition="pop"
				class="ui-shadow ui-btn ui-corner-all ui-btn-icon-left ui-icon-delete">
				删除条件 </a>
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">当日K线:收阳或阴,收盘价不低于x日均线,涨幅大于x%,小于x%</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:只查收阳,收盘价不低于5日均线,涨幅大于-5%,小于5%:(1,-1,5,-5,5)</div>
		</div>
		<div class="ui-block-d">

			<label for="currentk" class="ui-hidden-accessible"></label> <input
				type="text" name="currentk_condition_text" id="currentk" data-clear-btn="true"
				value="">

		</div>
	</div>
	<div id="duringkparam_con" name="duringkparam" class="ui-grid-c ui-responsive">

		<div class="ui-block-a ui-alt-icon">
			<a href="#popupDialog" onclick="setObjectbeforedelete(this)"
				data-rel="popup" data-position-to="window" data-transition="pop"
				class="ui-shadow ui-btn ui-corner-all ui-btn-icon-left ui-icon-delete">
				删除条件 </a>
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">期间K线:x天前起,x天内,最高最低相差x%以上</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:当天算起,30天内,最高最低相差15%以上:(0,30,15)</div>
		</div>
		<div class="ui-block-d">

			<label for="duringk" class="ui-hidden-accessible"></label> <input
				type="text" name="duringk_condition_text" id="duringk" data-clear-btn="true"
				value="">

		</div>
	</div>

	<div id="bankuaiparam_con" name="bankuaiparam" class="ui-grid-c ui-responsive">

		<div class="ui-block-a ui-alt-icon">
			<a href="#popupDialog" onclick="setObjectbeforedelete(this)"
				data-rel="popup" data-position-to="window" data-transition="pop"
				class="ui-shadow ui-btn ui-corner-all ui-btn-icon-left ui-icon-delete">
				删除条件 </a>
		</div>
		<div class="ui-block-b">
			<div class="ui-body ui-body-d">板块:选择特定开头的,不填代表全查询</div>
		</div>
		<div class="ui-block-c">
			<div class="ui-body ui-body-d">例:选择00,30开头的股票:(00,30)</div>
		</div>
		<div class="ui-block-d">

			<label for="bankuai" class="ui-hidden-accessible"></label> 
			<input data-options='{"type":"horizontal"}'
				type="text" name="bankuai_condition_text" id="bankuai" data-role="spinbox" data-clear-btn="true"
				value=""> 

		</div>
	</div>
</form>