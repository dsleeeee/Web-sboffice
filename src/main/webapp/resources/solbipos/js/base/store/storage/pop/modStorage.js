/**
 * get application
 */
var app = agrid.getApp();


var modUseYn = [
	  {"name": "사용", "value": "Y"},
	  {"name": "미사용", "value": "N"}
	];

/** 창고 정보 수정 controller */
app.controller('storageModLayerCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('storageModLayerCtrl', $scope, $http, true));
	
	// 사용여부를 쓰는 콤보박스의 데이터
	$scope._setComboData('modUseYnComboData', modUseYn);
	
	$scope.stock;
	
	//창고정보
	$scope.storageModInfo;
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("storageModLayerCtrl", function (event, data) {
		$scope.modStorageCd  = data.storageCd;
		$scope.modStorageNm  = data.storageNm;
		$scope.modUseYn      = data.useYn;
		
		//재고가 있는 경우 사용 불가 선택 금지
	    $scope._postJSONSave.withOutPopUp( "/base/store/storage/popup/storage/stockCnt.sb", data, function(result){
	    	if(result.data.data > 0) {
	    		$scope.stock = true;
	    		$('#modUseYn').css('background','#e8e8e8');
	    	} else {
	    		$scope.stock = false;
	    	}
		});
		
	    $scope.wjStorageModLayer.show(true);

	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	});
		  
	  // 창고 정보 수정
	 $scope.modStorage = function() {
		 // 파라미터 설정
		 var params = {};
		 params.storageCd = $scope.modStorageCd;
		 params.storageNm = $('#modStorageNm').val();
		 params.useYn 	 = $scope.modUseYn;
	    
	    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수 
	    $scope._postJSONSave.withOutPopUp( "/base/store/storage/popup/storage/modInfo.sb", params, function(){
			  $scope._popMsg(messages["storageManage.modStorage"], $scope._broadcast('storageListCtrl'));
			  $scope.wjStorageModLayer.hide();
		});
	 }

}]);