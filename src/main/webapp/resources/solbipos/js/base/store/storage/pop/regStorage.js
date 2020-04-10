/**
 * get application
 */
var app = agrid.getApp();

var regUseYn = [
	  {"name": "사용", "value": "Y"},
	  {"name": "미사용", "value": "N"},
	];

/** 창고 신규등록 controller */
app.controller('storageRegLayerCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	 // 상위 객체 상속 : T/F 는 picker
	 angular.extend(this, new RootController('storageRegLayerCtrl', $scope, $http, true));
	  
	 // 사용여부를 쓰는 콤보박스의 데이터
	 $scope._setComboData('useYnRegComboData', regUseYn);
	 
	// 다른 컨트롤러의 broadcast 받기
	 $scope.$on("storageRegLayerCtrl", function (event, data) {
	    
	 	$scope.wjStorageRegLayer.show(true);
	 	
	 	//창고코드 자동생성 표시, 인풋 리셋
	 	$('#regStorageCdAutoSet').val(messages["storageManage.storageCdAutoSet"]);
	 	$('#regStorageNm').val('');

	 	// 기능수행 종료 : 반드시 추가
	 	event.preventDefault();
	 });
	 
	  // 창고 신규 저장 - 빈값 체크
	 $scope.saveStorage = function() {
	    // 파라미터 설정
	    var params = {};
	    params.storageNm = $('#regStorageNm').val();
	    params.useYn 	 = $scope.regUseYn;
		
	    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수 
	    $scope._postJSONSave.withPopUp( "/base/store/storage/popup/storage/reg.sb", params, function(){
	       $scope._popMsg(messages["storageManage.saveStorage"]);
	       $scope.wjStorageRegLayer.hide();
	       
	       params = {};
	       $scope._broadcast('storageListCtrlSrch', params);
	    });
	 }
}]);