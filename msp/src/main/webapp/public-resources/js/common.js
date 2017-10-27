function toHome() {
	var wlh = window.location.href;
	var wlh2 = wlh.substring(0, wlh.indexOf('#') + 2) + 'menu';
	window.location.href = wlh2;
	return wlh2 == wlh;
}

function toProductManage() {
	var wlh = window.location.href;
	var wlh2 = wlh.substring(0, wlh.indexOf('#') + 2) + 'product/grid';
	window.location.href = wlh2;
	return wlh2 == wlh;
}

function toLogin() {
	var wlh = window.location.href;
	var wlh2 = wlh.substring(0, wlh.indexOf('#') + 2);
	window.location.href = wlh2;
	return wlh2 == wlh;
}

function parsseAccessResult(result){
	if(result.success){
		toHome();
	}else{
		toLogin();
	}
}

function historyBack(){
	window.history.back();
}

function parseFailedResult(result){
	if(result.success == false && result.code != null){
		console.log("权限不足！");
	}else{
		toLogin();
	}
}
/*function imgError(e){
	console.log(e);
	var target = $(e.target);
	target.attr("src","resources/images/default.jpg");
	e.target.onerror=null;
}*/