
<% 
String url=request.getRequestURL().toString();
int idx=url.lastIndexOf("/");
int firstslashidx= url.indexOf("/", 10);

if(idx>8){
	
	response.sendRedirect(url.substring(0, firstslashidx)+"/stock_allinone/deal");
}else{
	response.sendRedirect(url+"/stock_allinone/deal");
}

%>

