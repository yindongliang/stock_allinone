
<% 
/*String url=request.getRequestURL().toString();
int idx=url.lastIndexOf("/");
int firstslashidx= url.indexOf("/", 10);

if(idx>8){
	
	response.sendRedirect(url.substring(0, firstslashidx)+"/stockfinder/deal");
}else{
	response.sendRedirect(url+"/stockfinder/deal");
}*/
response.sendRedirect("http://kksogu.com/stockfinder/deal");
%>

