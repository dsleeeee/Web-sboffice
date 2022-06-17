/**
 * get application
 */
var app = agrid.getApp();

// 배달구분
var dlvrFgData = [
	{"name": "CALL", "value": "1"},
	{"name": "조리", "value": "2"},
	{"name": "배달", "value": "3"},
	{"name": "완료", "value": "4"},
	{"name": "취소", "value": "5"}
];

/** 배달지정보관리 controller */
app.controller('deliveryTelNoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('deliveryTelNoCtrl', $scope, $http, true));

	// 조회일자 세팅
	$scope.srchStartDate = wcombo.genDateVal("#srchStartDate", getToday());
	$scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", getToday());

	// 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    $scope.isChecked = true;
    // 전체기간 체크박스 선택에 따른 날짜선택 초기화
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		$scope.dlvrFgDataMap = new wijmo.grid.DataMap(dlvrFgData, 'value', 'name'); // 배달구분

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("deliveryTelNoCtrl", function (event, data) {

     if( wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd') > wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd')){
    	 $scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
    	 return false;
     }

	 $scope.searchDeliveryTelNoList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("deliveryTelNoCtrlSrch", function (event, data) {

     if( wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd') > wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd')){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
     }

	 $scope.searchDeliveryTelNoList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 판매자일자별 리스트 조회
  $scope.searchDeliveryTelNoList = function () {

    // 파라미터
    var params       = {};
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }
    params.listScale = $scope.listScale;
      params.cidTelNo = $scope.cidTelNo;
      params.dlvrAddr = $scope.dlvrAddr;
      params.dlvrMemo = $scope.dlvrMemo;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/dlvr/manage/info/deliveryTelNo/getDeliveryTelNoList.sb", params, function() {});

  };

  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };

  // 저장
    $scope.save = function (){
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/dlvr/manage/info/deliveryTelNo/updateDeliveryTelNo.sb", params, function (){
                $scope.searchDeliveryTelNoList();
            });
        });
    }

    // 선택삭제
    $scope.del = function (){
        $scope._popConfirm(messages["cmm.chk"]+messages["cmm.choo.delete"], function() {
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];

                if(item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 삭제
            $scope.deleteSave();
        });
    }

    $scope.deleteSave = function() {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/dlvr/manage/info/deliveryTelNo/updateDeliveryTelNo.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
            $scope.searchDeliveryTelNoList();
        });
    }

  // 전체삭제
    $scope.delAll = function (){
        $scope._popConfirm(messages["cmm.all"]+messages["cmm.choo.delete"], function() {
            var params = [];
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withOutPopUp("/dlvr/manage/info/deliveryTelNo/deleteAllDeliveryTelNo.sb", params, function (){
                $scope.searchDeliveryTelNoList();
            });
        });
    }
}]);

