/****************************************************************
 *
 * 파일명 : postpaid.js
 * 설  명 : 외상입금/후불 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.20     김지은      1.0
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
 *  후불회원 그리드 생성
 */
app.controller('postpaidCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('postpaidCtrl', $scope, $http, true));

  $scope.orgnFg = gvOrgnFg;

  if($scope.orgnFg === 'S') {
    $scope.storeCds = gvStoreCd;
  }

 // todo 검색조건에 입금구분넣고 회원번호, 회원명 like검색

  // comboBox 초기화
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("useYn", useYnFgData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.postpaidFgDataMap = new wijmo.grid.DataMap(postpaidFgData, 'value', 'name');
    $scope.postpaidPayFgDataMap = new wijmo.grid.DataMap(postpaidPayFgData, 'value', 'name');

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        // 가격 변경시 체크박스 체크
        if (col.binding === "postpaidAmt2") {
          $scope.checked(item);
        }
      }
      s.collectionView.commitEdit();
    });
  };

  // 금액 수정 시 체크박스 체크
  $scope.checked = function (item){
    item.gChk = true;
  };

  $scope.batchChange = function (){
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].postpaidAmt2 = $scope.postpaidAmt;
      }
    }
    $scope.flex.refresh();
  };

  $scope.$on("postpaidCtrl", function(event, data) {
    $scope.searchPostpaid();
    event.preventDefault();
  });

  // 후불회원 그리드 조회
  $scope.searchPostpaid = function(){
    var params      = {};
    params.storeCds = $("#storeCd").val();
    $scope._inquiryMain("/membr/anals/postpaid/postpaid/getPostpaidMemberList.sb", params, function() {}, false);
  };

  // 외상입금 팝업 > 저장
  $scope.deposit = function(){
    // $scope.depositLayer.show(true, function(){
    //   $scope._broadcast("postpaidCtrl");
    // });

    var params = new Array();
    for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
      $scope.flex.collectionView.itemsEdited[u].status = "U";
      $scope.flex.collectionView.itemsEdited[u].postpaidAmt = $scope.flex.collectionView.itemsEdited[u].postpaidAmt2;
      params.push($scope.flex.collectionView.itemsEdited[u]);
    }

    $scope._save("/membr/anals/postpaid/postpaid/saveDeposit.sb",params, function (){
      $scope.searchPostpaid();
    });
  };

  // 세금계산서 발행완료처리 팝업
  $scope.taxBillDeposit = function(){
    $scope.taxBillDepositLayer.show(true, function(){
      $scope._broadcast("postpaidCtrl");
    });
  };

  // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeShow = function () {
    $scope._broadcast('storeCtrl');
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 외상입금 팝업 핸들러 추가
    // $scope.depositLayer.shown.addHandler(function (s) {
    //   setTimeout(function() {
    //     $scope._broadcast('depositCtrl');
    //   }, 50)
    // });
    // 세금계산서 발행완료처리 팝업 핸들러 추가
    $scope.depositLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('taxBillDepositCtrl');
      }, 50)
    });
  });


  //엑셀 다운로드
  $scope.excelDownload = function () {

    var params = {};
    params.storeCds = $("#storeCd").val();
    params.membrNo = $("#srcgMembrNo").val();
    params.membrNm = $("#srchMembrNm").val();

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope._broadcast('postpaidExcelCtrl', params);

  };
}]);


app.controller('postpaidExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('postpaidExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) { };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("postpaidExcelCtrl", function (event, data) {
    $scope.getExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 리스트 조회
  $scope.getExcelList = function (data) {
    // 파라미터
    var params = data;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/membr/anals/postpaid/postpaid/getPostpaidMemberListExcel.sb", params, function() {

      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
      $timeout(function () {
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
          includeColumnHeaders: true,
          includeCellStyles   : true,
          includeColumns      : function (column) {
            return column.visible;
          }
        }, '외상발생_입금내역' + getCurDateTime() +'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };
}]);
