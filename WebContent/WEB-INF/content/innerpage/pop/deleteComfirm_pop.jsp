<%@ page contentType="text/html; charset=utf-8"%>
<div data-role="popup" id="popupDialog" data-overlay-theme="a" data-theme="a" data-dismissible="false" style="max-width:400px;">
    <div data-role="header" data-theme="a">
    <h1>警告</h1>
    </div>
    <div role="main" class="ui-content">
        <h3 class="ui-title">确定要删除该条件吗？</h3>
    	<p>提示:可以通过【添加条件】找回</p>
        <a href="#" onclick="deletecondtion();" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-a" data-rel="back" data-transition="flow">确认</a>
        <a href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-inline ui-btn-a" data-rel="back">取消</a>
    </div>
</div>