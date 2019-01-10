/****************************************************************
 *
 * 파일명 : verRecv.js
 * 설  명 : 포스버전수신현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.01.07     김지은      1.0           Angular방식으로 변경
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

function changeTab(val){

  if(val === 'R'){
    location.href = '/pos/confg/verRecv/verRecv/list.sb';
  } else if(val === 'S') {
    location.href = '/pos/confg/verRecv/storeRecv/list.sb';
  }
}

function getVersionList(){
  var scope = agrid.getScope('verInfoCtrl');
  scope.getVersionList();
}

/**********************************************************************
 *  버전목록 그리드
 **********************************************************************/
app.controller('verInfoCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verInfoCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 선택 버전
  $scope.selectVersion;
  $scope.setSelectVersion = function(ver){
    $scope.selectVersion = ver;
  };
  $scope.getSelectVersion = function(){
    return $scope.selectVersion;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.posFgDatMap = new wijmo.grid.DataMap(posFg, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "verSerNo") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 매장 선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        if ( col.binding === "verSerNo") {
          var selectedData = s.rows[ht.row].dataItem;
          $scope.setSelectVersion(selectedData);
          // $scope.storeRecvDtlLayer.show(true);

          var scope = agrid.getScope('verInfoDtlCtrl');
          scope._pageView('verInfoDtlCtrl',1)

          event.preventDefault();
        }
      }
    });
  };

  // 조회 버튼 클릭
  $scope.$on("verInfoCtrl", function(event, data) {
    $scope.getVersionList();
    event.preventDefault();
  });

  // 버전 목록 조회
  $scope.getVersionList = function(){
    var params = {};
    params.listScale = 10;
    params.curr = $scope._getPagingInfo('curr');
    params.verSerNo  = $("#verSerNo").val();
    params.verSerNm  = $("#verSerNm").val();

    // console.log('params', params);

    $scope._inquiryMain("/pos/confg/verRecv/verStore/list.sb", params, function() {

      var dtlScope = agrid.getScope('verInfoDtlCtrl');
      dtlScope._gridDataInit();

    });
  };

}]);

/**********************************************************************
 *  버전별 매장 목록 그리드
 **********************************************************************/
app.controller('verInfoDtlCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verInfoDtlCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 선택 버전
  $scope.selectVersion;
  $scope.setSelectStore = function(ver){
    $scope.selectVersion = ver;
  };
  $scope.getSelectVersion = function(){
    return $scope.selectVersion;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.verRecvFgDatMap = new wijmo.grid.DataMap(verRecvFg, 'value', 'name');
  };

  // 조회 버튼 클릭
  $scope.$on("verInfoDtlCtrl", function(event, data) {

    if(!isEmptyObject(data)) {
      $scope.setSelectStore(data);
    }

    $scope.getStoreVersionList();
    event.preventDefault();
  });

  // 버전 목록 조회
  $scope.getStoreVersionList = function(){
    var params = {};

    var scope = agrid.getScope("verInfoCtrl");
    scope.getSelectVersion();

    params.listScale = 10;
    params.curr = $scope._getPagingInfo('curr');
    params.verSerNo  = scope.getSelectVersion().verSerNo;
    // params.verSerNm  = $("#verSerNm").val();

    // console.log('params', params);

    $scope._inquiryMain("/pos/confg/verRecv/verStore/storeList.sb", params, function() {
    });
  };


}]);
