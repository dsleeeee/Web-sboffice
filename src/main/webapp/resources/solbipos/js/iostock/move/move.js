/**
 * get application
 */
var app = agrid.getApp();

app.controller('moveCtrl', ['$scope', function ($scope) {
	if(gvOrgnFg == 'H' || gvOrgnFg == 'M'){ 
		$("#hqStoreMoveView").show();
        $("#storeMoveView").hide();
        $("#standMoveView").hide();
	}
	 if(gvOrgnFg == 'S'){
		$("#hqStoreMoveView").hide();
        $("#storeMoveView").show();
        $("#standMoveView").show();
	}
}]);