/**
 * 
 */
/*----------------------------------------------------------------------------------------------------*/
function priceFormatter(value) {
	return addCommas(value.toFixed(3));
}
/*----------------------------------------------------------------------------------------------------*/
function addCommas(nStr) {
	nStr += '';
	x = nStr.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}
var changeDir = function (){
	var formControl = (document.body.getElementsByClassName("form-control"));
	for (i = 0; i < formControl.length; i++){
		formControl[i].style.direction = dir;
	}
};
if(window.attachEvent) {
    window.attachEvent('onload', changeDir);
} else {
    if(window.onload) {
        var curronload = window.onload;
        var newonload = function(evt) {
            curronload(evt);
            changeDir(evt);
        };
        window.onload = newonload;
    } else {
        window.onload = changeDir;
    }
}
