function controlloginIcon() {

	if (userdata_tel.status == "unlogin") {
		$("#userbtn").show();
		$("#userinfo").hide();

	} else {
		$("#userbtn").hide();
		$("#userinfo").html(current_telno);
		$("#userinfo").show();
	}
}

function islogin() {

	if (userdata_tel.status == "unlogin") {
		return false;
	}
	return true;
}