<%@ page contentType="text/html; charset=utf-8"%>
<div data-role="popup" id="popupforSavePram" data-overlay-theme="a" data-theme="a" data-dismissible="false" style="max-width:400px;">
    <div data-role="header" data-theme="a">
    <h1>警告</h1>
    </div>
    <div role="main" class="ui-content">
        <h3 class="ui-title">确定要修改该自定义参数吗？</h3>
    	<p>警告:修改后将覆盖原值</p>
        
        <a href="#" onclick="saveEditor();" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-a" data-rel="back" data-transition="flow">确认</a>
    	<a href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-a" data-rel="back">取消</a>
    </div>
</div>