/**
 * get application
 */
var app = agrid.getApp();

app.controller('moveCtrl', ['$scope', function ($scope) {
	if(gvOrgnFg == 'H' || gvOrgnFg == 'M'){ 
		$("#hqStoreMoveView").show();
        $("#storeMoveView").hide();
	}
	 if(gvOrgnFg == 'S'){
		$("#hqStoreMoveView").hide();
        $("#storeMoveView").show();
	}
}]);