<%@ page contentType="text/html; charset=utf-8"%>

<div data-role="panel" id="editParamspanel" data-position="left"
	data-display="overlay" data-theme="a">
	<div>
		<h3>选择自定义参数</h3>
	</div>
	<div class="ui-grid-a ui-responsive">
		<div class="ui-block-a ui-alt-icon">

			<select id="customprams" data-shadow="false"
				onchange="setValuesForeditor(this.value)">
				<option value="0">自定义参数列表</option>

			</select>
		</div>
		<div class="ui-block-b ui-alt-icon">
			<a href="#" data-rel="popup"
				data-position-to="window" data-transition="pop"
				onclick="openpopupforDeletePram()"
				class="ui-shadow ui-btn">删除参数</a>

		</div>

	</div>
	<div>
		<h3>参数明细</h3>
	</div>
	<form id="paramEditor_form">

		
		<div class="ui-grid-a ui-responsive">
			<div class="ui-block-a ui-alt-icon">
				<div class="ui-body ui-body-d">日均参:x日均线连续x日，x%，均线向上或向下</div>

			</div>
			<div class="ui-block-b ui-alt-icon">
				<label for="text-9" class="ui-hidden-accessible"></label> <input
					type="text" data-clear-btn="true" name="dayk_editor_text"
					id="dayk_editor" value="">
			</div>
		</div>
		<div class="ui-grid-a ui-responsive">
			<div class="ui-block-a ui-alt-icon">
				<div class="ui-body ui-body-d">周均参:x周均线连续x周，x%，均线向上或向下</div>

			</div>
			<div class="ui-block-b ui-alt-icon">
				<label for="weekk_editor" class="ui-hidden-accessible"></label> <input
					type="text" data-clear-btn="true" name="weekk_editor_text"
					id="weekk_editor" value="">
			</div>
		</div>
		<div class="ui-grid-a ui-responsive">
			<div class="ui-block-a ui-alt-icon">
				<div class="ui-body ui-body-d">日K形态:x日前开盘价格x日前收盘价格组成K线，向上或向下穿过x,x,x日均线</div>

			</div>
			<div class="ui-block-b ui-alt-icon">
				<label for="weekk_editor" class="ui-hidden-accessible"></label> <input
					type="text" data-clear-btn="true" name="onlykd_editor_text"
					id="onlykd_editor" value="">
			</div>
		</div>
		<div class="ui-grid-a ui-responsive">
			<div class="ui-block-a ui-alt-icon">
				<div class="ui-body ui-body-d">周K形态:x周前开盘价格x周前收盘价格组成K线，向上或向下穿过x,x,x周均线</div>

			</div>
			<div class="ui-block-b ui-alt-icon">
				<label for="text-9" class="ui-hidden-accessible"></label> <input
					type="text" data-clear-btn="true" name="onlykw_editor_text"
					id="onlykw_editor" value="">
			</div>
		</div>
		
		<div class="ui-grid-a ui-responsive">
			<div class="ui-block-a ui-alt-icon">
				<div class="ui-body ui-body-d">涨停个数:x日内，涨停个数大于y个</div>

			</div>
			<div class="ui-block-b ui-alt-icon">
				<label for="text-9" class="ui-hidden-accessible"></label> <input
					type="text" data-clear-btn="true" name="zt_editor_text"
					id="zt_editor" value="">
			</div>
		</div>
		
		<div class="ui-grid-a ui-responsive">
			<div class="ui-block-a ui-alt-icon">
				<div class="ui-body ui-body-d">当日K线:收阴或阳,不破x日均线,涨幅大于x%,小于x%</div>

			</div>
			<div class="ui-block-b ui-alt-icon">
				<label for="text-9" class="ui-hidden-accessible"></label> <input
					type="text" data-clear-btn="true" name="currentk_editor_text"
					id="currentk_editor" value="">
			</div>
		</div>
		
		<div class="ui-grid-a ui-responsive">
			<div class="ui-block-a ui-alt-icon">
				<div class="ui-body ui-body-d">期间K线:x天前起,x天内,最高最低相差x%以上</div>

			</div>
			<div class="ui-block-b ui-alt-icon">
				<label for="text-9" class="ui-hidden-accessible"></label> <input
					type="text" data-clear-btn="true" name="duringk_editor_text"
					id="duringk_editor" value="">
			</div>
		</div>
		
		<div class="ui-grid-a ui-responsive">
			<div class="ui-block-a ui-alt-icon">
				<div class="ui-body ui-body-d">板块:选择特定开头的,不填代表全查询</div>

			</div>
			<div class="ui-block-b ui-alt-icon">
				<label for="text-9" class="ui-hidden-accessible"></label> <input
					type="text" data-clear-btn="true" name="bankuai_editor_text"
					id="bankuai_editor" value="">
			</div>
		</div>
		
	</form>

	<div class="ui-grid-b ui-responsive">
		<div class="ui-block-a">
			<a href="#" data-rel="popup"
				data-position-to="window" data-transition="pop"
				onclick="openpopupforSavePram()"
				class="ui-shadow ui-btn">修改</a>

		</div>
		<div class="ui-block-b"></div>
		<div class="ui-block-c">
			<a href="#" data-rel="close" class="ui-btn ui-shadow ui-corner-all ">关闭</a>
		</div>

	</div>
</div>

