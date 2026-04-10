/****************************************************************
 *
 * 파일명 : hqEmp.js
 * 설  명 : 본사사원정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.20     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var mainSaleFg = [
  {"name": "전체", "value": ""},
  {"name": "사용", "value": "0"},
  {"name": "미사용", "value": "1"}
];

var useYn = [
  {"name":"전체","value":""},
  {"name":"사용","value":"Y"},
  {"name":"미사용","value":"N"}
];
/**
 * 본사사원정보관리 - 그리드
 */
app.controller('hqEmpCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('hqEmpCtrl', $scope, $http, false));

  // 전체기간 체크박스
  $scope.isChecked = true;
  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  // 재직여부
  $scope._getComboDataQuery('007', 'serviceFgComboData', 'A');
  // 웹사용여부
  $scope._getComboDataQuery('067', 'webUseYnComboData', 'A');
  // SMS 수신여부
  $scope._getComboDataQuery('072', 'smsRecvYnComboData', 'A');
  // 사용여부
  $scope._getComboDataQuery('067', 'useYnComboData', 'A');
  // 메인화면매출표시
  $scope._setComboData("srchMainSaleFg", mainSaleFg);

  $scope.selectedHqEmp;

  // 등록일자 셋팅
  $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드에서 사용하는 dataMap 초기화
    $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFg, 'value', 'name');
    $scope.webUseYnDataMap  = new wijmo.grid.DataMap(webUseYn, 'value', 'name');
    $scope.smsRecvYnDataMap = new wijmo.grid.DataMap(smsRecvYn, 'value', 'name');
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    $scope.mainSaleFgDataMap = new wijmo.grid.DataMap(mainSaleFg, 'value', 'name');

    // 그리드 포맷
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if( col.binding === "empNo" || col.binding === "empNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        if( col.binding === "empNo" || col.binding === "empNm") {
          // 상세정보 팝업
          //$scope.hqEmpDetailLayer.show(true);
          $scope.hqEmpDetailLayer.show(true, function(){
            var scope = agrid.getScope('hqEmpDetailCtrl');
            $scope.getHqEmpList();
          });
        }
      }
    });
    // 전체기간 체크박스 선택에 따른 날짜선택 초기화
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };

  // _broadcast
  $scope.$on("hqEmpCtrl", function(event, data) {

    $scope.getHqEmpList();
    event.preventDefault();
  });

  // 본사사원정보관리 그리드 조회
  $scope.getHqEmpList = function(){
    var params = {};
    params.listScale = $scope.listScale;

    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }

    params.empNo      = $scope.empNo;
    params.empNm      = $scope.empNm;
    params.userId     = $scope.userId;
    params.serviceFg  = $scope.serviceFg;
    params.mpNo       = $scope.mpNo;
    params.webUseYn   = $scope.webUseYn;
    params.smsRecvYn  = $scope.smsRecvYn;
    params.useYn      = $scope.useYn;
    params.mainSaleFg = $scope.mainSaleFg;

    console.log(params);

    $scope._inquiryMain("/base/store/emp/hq/list.sb", params, function() {});
    // 페이징 처리
    if ($scope._getPagingInfo('curr') > 0) {
      params['curr'] = $scope._getPagingInfo('curr');
    } else {
      params['curr'] = 1;
    }
    // 가상로그인 대응한 session id 설정
    if (document.getElementsByName('sessionId')[0]) {
      params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $.postJSON("/base/store/emp/hq/list.sb", params, function (response){
      var grid = $scope.flex;
      grid.itemsSource = response.data.list;
      grid.itemsSource.trackChanges = true;

      var list = response.data.list;
      if (list.length === undefined || list.length === 0) {
        $scope.data = new wijmo.collections.CollectionView([]);
        if (true && response.message) {

          // 페이징 처리
          $scope._setPagingInfo('ctrlName', $scope.name);
          $scope._setPagingInfo('pageScale', 10);
          $scope._setPagingInfo('curr', 1);
          $scope._setPagingInfo('totCnt', 1);
          $scope._setPagingInfo('totalPage', 1);

          $scope._broadcast('drawPager');

          $scope._popMsg(response.message);
        }
        return false;
      }
      var data = new wijmo.collections.CollectionView(list);
      data.trackChanges = true;
      $scope.data = data;

      // 페이징 처리
      if (response.data.page && response.data.page.curr) {
        var pagingInfo = response.data.page;
        $scope._setPagingInfo('ctrlName', $scope.name);
        $scope._setPagingInfo('pageScale', pagingInfo.pageScale);
        $scope._setPagingInfo('curr', pagingInfo.curr);
        $scope._setPagingInfo('totCnt', pagingInfo.totCnt);
        $scope._setPagingInfo('totalPage', pagingInfo.totalPage);
        $scope._broadcast('drawPager');
      }

    }, function (response){
      s_alert.pop(response.message);
      var grid = $scope.flex;
      grid.itemsSource = new wijmo.collections.CollectionView([]);
    });
  };

  // 신규등록 버튼 클릭
  $scope.registHqEmp = function(){
    $scope.selectedHqEmp = {};
    $scope.hqEmpRegistLayer.show(true, function(s){
      $scope.getHqEmpList();
    });
  };

  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 사원상세정보 팝업 핸들러 추가
    $scope.hqEmpDetailLayer.shown.addHandler(function (s) {
      $scope.selectedHqEmp = $scope.flex.selectedRows[0]._data;
      setTimeout(function() {
        var params = $scope.selectedHqEmp;
        $scope._broadcast('hqEmpDetailCtrl', params);
      }, 50);
    });
    // 사원 등록 팝업 핸들러 추가
    $scope.hqEmpRegistLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        var params = $scope.selectedHqEmp;
        $scope._broadcast('hqEmpRegistCtrl', params);
      }, 50);
    });
  });

}]);
