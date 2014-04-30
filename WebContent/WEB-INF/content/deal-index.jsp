<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html charset=utf-8">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;"
	name="viewport" />
	<meta name="keywords" content="kk, KK, 搜股, 股票, 问股, 快快, 寻股, 找股, 技术, 形态, k线, K线, 推荐股票, 荐股">
	<link rel="shortcut icon" href="images/ajax-loader.gif">
<title>KK搜股</title>
<link
	href="${pageContext.request.contextPath}/css/mytheme.css"
	type="text/css" rel="stylesheet">
	<link
	href="${pageContext.request.contextPath}/css/jquery.mobile.icons-1.4.2.min.css"
	type="text/css" rel="stylesheet">
	<link
	href="${pageContext.request.contextPath}/css/jquery.mobile.structure-1.4.2c.min.css"
	type="text/css" rel="stylesheet">
	
<link href="${pageContext.request.contextPath}/css/custom.css"
	type="text/css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/jquery.mobile-1.4.2.min.js"></script>
<script src="${pageContext.request.contextPath}/js/myjs.min.js"></script>



</head>

<body>
	<div id="validator" data-role="page">
		<!-- /panel -->
		<jsp:include page="./innerpage/panel/conditionchose_panel.jsp"></jsp:include>
		<jsp:include page="./innerpage/panel/editordeleteparams_panel.jsp"></jsp:include>
		<!-- /panel -->
		<!-- /popup -->
		<jsp:include page="./innerpage/pop/login_pop.jsp"></jsp:include>
		<jsp:include page="./innerpage/pop/saveOreditOtionConfirm_pop.jsp"></jsp:include>
		<jsp:include page="./innerpage/pop/deleteComfirm_pop.jsp"></jsp:include>
		<jsp:include page="./innerpage/pop/deleteconfirmprameditor_pop.jsp"></jsp:include>
		<jsp:include page="./innerpage/pop/saveconfirmprameditor_pop.jsp"></jsp:include>
		<!-- /popup -->

		<div data-role="header">
			
			<h1></h1>

			<div data-role="controlgroup" data-type="horizontal"
				class=" ui-btn-right">
				<a href="#popupLogin" data-rel="popup" data-position-to="window" id="userbtn"
					data-transition="pop" class="ui-btn ui-btn-icon-left ui-icon-user">登陆</a>
				
				<a href="#" id="userinfo"
					 class="ui-btn"></a>
				<a href="#" class="ui-btn ui-btn-icon-left ui-icon-action" onclick="logout()">退出</a>
			</div>
		</div>
		<!-- /header -->
		<div role="main" class="ui-content">

			<div class="ui-grid-b ui-responsive">
				<div class="ui-block-a">
					
					<select id="parameterSetting"  data-shadow="false" onchange="setValues(this.value)">
						<option value="0">参数设定</option>
						<option value="1">强市参数(系统)</option>
						<option value="2">弱市参数(系统)</option>
					</select>
				</div>
				<div class="ui-block-b">
				<a  onclick="openeditsavePopup()"
					class="ui-shadow ui-btn ui-btn-icon-left ui-icon-star">编辑预定义参数</a>
				</div>
				<div class="ui-block-c">
					<a href="#"
						onclick="getSearchResult();"
						class="ui-shadow ui-btn ui-corner-all ui-btn-icon-left  ui-icon-search">
						查询 </a>
				</div>
			</div>

			<div>
				<h3>查询条件</h3>
			</div>
			
			<jsp:include page="./innerpage/conditiongrid.jsp"></jsp:include>
			<div id="addcondition" class="ui-grid-c ui-responsive">
				<div class="ui-block-a">
					<a href="#condtionpanel"
						class="ui-shadow ui-btn ui-corner-all ui-btn-icon-left ui-icon-plus">
						添加条件 </a>
				</div>
			</div>
			<div>
				<h3>查询结果</h3>
			</div>
			<div class="ui-grid-solo ui-responsive">
				<jsp:include page="./innerpage/searchResult.jsp"></jsp:include>
			</div>

		</div>
		<!-- /content -->
		<div data-role="footer">
			<h5>欢迎咨询:dl.yin@hotmail.com</h5>
		</div>
		<!-- /footer -->
	</div>
	<!-- /page -->

</body>
</html>