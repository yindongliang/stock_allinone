function createUser(){
	this.ss;
	 var obj=new Object();
	
    obj.data={};
	obj.counter=2;
	obj.status="unlogin";
	obj.userlistname={};
	obj.telno="00000000";
	obj.getData=function(){
		
		return this.data;
	}
	obj.setData=function(data){
		
		return this.data=data;
	}
	return obj;
}
var u= createUser();

