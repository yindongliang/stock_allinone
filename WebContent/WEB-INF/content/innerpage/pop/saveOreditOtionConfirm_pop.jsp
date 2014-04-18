<%@ page contentType="text/html; charset=utf-8"%>
<div data-role="popup" data-overlay-theme="a" data-dismissible="false" id="popupSaveoredit" data-theme="a" class="ui-corner-all">
    <form id="saveeditor_form">
        <div style="padding:10px 20px;">
            <h3>如保存请输入参数名</h3>
            <div>
            <label for="parametername" class="ui-hidden-accessible">参数名:</label>
            <input id="parametername" name="parametername" value="自定义参数1" placeholder="自定义参数名" data-theme="a" type="text"  >
            </div>
            
            <div class="ui-grid-b ui-responsive">
            	<div class="ui-block-a">
					<a href="#"
						onclick="insertSlectBoxOption('parameterSetting')"
						class="ui-shadow ui-btn ui-corner-all">
						保存</a>
				</div>
				<div class="ui-block-b">
					<a href="#" onclick="$('#popupSaveoredit').popup('close');$('#editParamspanel').panel('open');"
						class="ui-shadow ui-btn ui-corner-all">
						编辑</a>
				</div>
				<div class="ui-block-c">
					<a href="#"
						class="hide-page-loading-msg ui-shadow ui-btn ui-corner-all" data-rel="back"
						>
						取消 </a>
				</div>
            </div>
            
            
        </div>
    </form>
</div>