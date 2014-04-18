<%@ page contentType="text/html; charset=utf-8"%>
<div data-role="popup" data-overlay-theme="a" data-dismissible="false"
	id="popupLogin" data-theme="a" class="ui-corner-all">

	<div style="padding: 10px 20px;">
		<form id="loginform">
			<h3>请输入手机号码</h3>
			<div class="ui-grid-solo ui-responsive">
				<label for="tel" class="ui-hidden-accessible">手机号码:</label> <input
					name="cellno" id="tel" value="" placeholder="手机号码" data-theme="a"
					type="text">
			</div>
			<!-- <div class="ui-grid-a ui-responsive ">
				<div class="ui-block-a">
					<label for="pw" class="ui-hidden-accessible">随机密码:</label> <input
						name="pass" id="pw" value="" placeholder="随机密码" data-theme="a"
						type="password">
				</div>
				<div class="ui-block-b">
					<a href="#"><br> 获取随机密码</a>
				</div>
			</div> -->
		</form>
		<div class="ui-grid-a ui-responsive">
			<div class="ui-block-a">
				<a href="#" id="login_okbtn"
					class="ui-shadow ui-btn ui-corner-all"
					>
					登陆</a>
			</div>
			
			<div class="ui-block-b">
				<a href="#"
					class="hide-page-loading-msg ui-shadow ui-btn ui-corner-all"
					data-rel="back"> 取消 </a>
			</div>
		</div>


	</div>

</div>