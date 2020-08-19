var app = agrid.getApp();

/**
 * 매장 사원 정보관리
 */
app.controller('itemItemCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('itemItemCtrl', $scope, $http, $timeout, true));

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		//$scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
	};

	// _broadcast
	$scope.$on("itemItemCtrl", function(event, data) {
		$scope.getItemItemList();
		event.preventDefault();
	});

	// 매출항목표시그리드 조회
	$scope.getItemItemList = function(){
		var params = {};
		params.orgnFg = $("#itemSelectOrgnFg").val();
		params.storeCd = $("#itemSelectStoreCd").val();
		params.busiFg = "SL";
		params.listScale = 100; //-페이지 스케일 갯수

		$scope._inquiryMain("/sale/status/item/item/list.sb", params, function() {});
	};

	// 화면 ready 된 후 설정
	angular.element(document).ready(function () {

	});

	// 저장
	$scope.save = function() {
		// 파라미터 설정
		var params = new Array();

		for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
			params.push($scope.flex.collectionView.itemsEdited[i]);
	    }

	    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
		$scope._save("/sale/status/item/item/save.sb", params, function(){});
	}

}]);

