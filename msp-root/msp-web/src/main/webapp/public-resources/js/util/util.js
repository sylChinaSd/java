//设置主题链接
function setThemeHref(theme){
	var href = window.location.href;
	if(href.indexOf('?')){
		href=href.split('?')[0];
	}
	href+='?theme='+theme;
	window.location.href = href;
}