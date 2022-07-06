/****************************************************************
 *
 * 파일명 : prepaid.js
 * 설  명 : 선불 입금/사용 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.01     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var useYnFgData = [
  {"name":"전체","value":""},
  {"name":"유","value":"Y"},
  {"name":"무","value":"N"}
];
/**
 *  선불회원 그리드 생성
 */
app.controller('prepaidCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prepaidCtrl', $scope, $http, true));

  $scope.orgnFg = gvOrgnFg;

  if($scope.orgnFg === 'S') {
    $scope.storeCds = gvStoreCd;
  }

  // comboBox 초기화
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("useYn", useYnFgData);
  // $scope._setComboData("srchArrayCombo", arrayData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.prepaidInFgDataMap = new wijmo.grid.DataMap(prepaidInFgData, 'value', 'name');
    $scope.prepaidPayFgDataMap = new wijmo.grid.DataMap(prepaidPayFgData, 'value', 'name');

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        // 가격 변경시 체크박스 체크
        if (col.binding === "prepaidAmt2") {
          $scope.checked(item);
        }
      }
      s.collectionView.commitEdit();
    });
  };

  // 충전금액 수정 시 체크박스 체크
  $scope.checked = function (item){
    item.gChk = true;
  };

  $scope.batchChange = function (){
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].prepaidAmt2 = $scope.prepaidAmt;
      }
    }
    $scope.flex.refresh();
  };

  $scope.$on("prepaidCtrl", function(event, data) {
    $scope.searchPostpaid();
    event.preventDefault();
  });

  // 선불회원 그리드 조회
  $scope.searchPostpaid = function(){

    // 파라미터
    var params = {};
    params.listScale  = $scope.listScale;
    params.storeCds = $("#prepaidChargeStoreCd").val();
    // params.array = srchArrayCombo.selectedValue;

    // if($scope.orgnFg === 'H' && params.storeCds  === '') {
    //   $scope._popMsg(messages["prepaid.require.selectStore"]);
    //   return false;
    // }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain(baseUrl + "prepaid/getPrepaidMemberList.sb", params, function() {}, false);
  };

  // 선불금 충전 팝업 > 저장버튼으로 수정
  $scope.charge = function(){
    // var popup = $scope.chargeLayer;
    // popup.show(true, function (s) {
    // });
    var params = new Array();
    for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
      console.log($scope.flex.collectionView.itemsEdited[u].prepaidAmt2);
      if(($scope.flex.collectionView.itemsEdited[u].prepaidAmt2 !== undefined || $scope.flex.collectionView.itemsEdited[u].prepaidAmt2 !== '') && $scope.flex.collectionView.itemsEdited[u].prepaidAmt2 > 0) {
        $scope.flex.collectionView.itemsEdited[u].status = "U";
        $scope.flex.collectionView.itemsEdited[u].prepaidStoreCd = $scope.flex.collectionView.itemsEdited[u].storeCd;
        $scope.flex.collectionView.itemsEdited[u].prepaidAmt = $scope.flex.collectionView.itemsEdited[u].prepaidAmt2;
        params.push($scope.flex.collectionView.itemsEdited[u]);
      }
    }

    $scope._save(baseUrl + "prepaid/saveChargeAmt.sb",params, function (){
      $scope.searchPostpaid();
    });
  };

  // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.prepaidChargeStoreShow = function () {
    $scope._broadcast('prepaidChargeStoreCtrl');
  };

  //엑셀 다운로드
  $scope.excelDownload = function () {

    var params = {};
    params.storeCds = $("#prepaidChargeStoreCd").val();
    params.membrNo = $("#srcgMembrNo").val();
    params.membrNm = $("#srchMembrNm").val();

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope._broadcast('prepaidExcelCtrl', params);

  };
}]);

app.controller('prepaidExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prepaidExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) { };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prepaidExcelCtrl", function (event, data) {
    $scope.getExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품매출순위 리스트 조회
  $scope.getExcelList = function (data) {
    // 파라미터
    var params = data;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain(baseUrl + "prepaid/getPrepaidMemberListExcel.sb", params, function() {

      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
      $timeout(function () {
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
          includeColumnHeaders: true,
          includeCellStyles   : true,
          includeColumns      : function (column) {
            return column.visible;
          }
        }, '선불금충전_사용내역' + getCurDateTime() +'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };
}]);