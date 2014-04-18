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
				<div class="ui-body ui-body-d">周参:x周均线连续x周，x%，均线向上或向下</div>

			</div>
			<div class="ui-block-b ui-alt-icon">
				<label for="weekk_editor" class="ui-hidden-accessible"></label> <input
					type="text" data-clear-btn="true" name="weekk_editor_text"
					id="weekk_editor" value="">
			</div>
		</div>
		<div class="ui-grid-a ui-responsive">
			<div class="ui-block-a ui-alt-icon">
				<div class="ui-body ui-body-d">日参:x日均线连续x日，x%，均线向上或向下</div>

			</div>
			<div class="ui-block-b ui-alt-icon">
				<label for="text-9" class="ui-hidden-accessible"></label> <input
					type="text" data-clear-btn="true" name="dayk_editor_text"
					id="dayk_editor" value="">
			</div>
		</div>
		<div class="ui-grid-a ui-responsive">
			<div class="ui-block-a ui-alt-icon">
				<div class="ui-body ui-body-d">2222</div>

			</div>
			<div class="ui-block-b ui-alt-icon">
				<label for="weekk_editor" class="ui-hidden-accessible"></label> <input
					type="text" data-clear-btn="true" name="kxd_editor_text"
					id="kxd_editor" value="">
			</div>
		</div>
		<div class="ui-grid-a ui-responsive">
			<div class="ui-block-a ui-alt-icon">
				<div class="ui-body ui-body-d">333</div>

			</div>
			<div class="ui-block-b ui-alt-icon">
				<label for="text-9" class="ui-hidden-accessible"></label> <input
					type="text" data-clear-btn="true" name="kxw_editor_text"
					id="kxw_editor" value="">
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

