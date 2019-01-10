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

  if(val === 'S'){
    location.href = '/pos/confg/verRecv/storeRecv/list.sb';
  } else if(val === 'V') {
    location.href = '/pos/confg/verRecv/verStore/list.sb';
  }
}

function searchPosVerList(){
  var scope = agrid.getScope('verRecvCtrl');
  scope.getVersionList();
}

/**********************************************************************
 *  버전목록 그리드
 **********************************************************************/
app.controller('verRecvCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verRecvCtrl', $scope, $http, true));

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

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "verSerNo") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 버전 선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        if ( col.binding === "verSerNo") {
          var selectedData = s.rows[ht.row].dataItem;
          $scope.setSelectVersion(selectedData);
          // $scope.versionInfoDetailLayer.show(true);

          var storeScope = agrid.getScope('verRecvStoreCtrl');
          storeScope._broadcast('verRecvStoreCtrl', $scope.getSelectVersion());

          event.preventDefault();
        }
      }
    });
  };

  // 조회 버튼 클릭
  $scope.$on("verRecvCtrl", function(event, data) {
    $scope.getVersionList();
    event.preventDefault();
  });

  // 버전 목록 조회
  $scope.getVersionList = function(){
    var params = {};
    params.listScale = $scope.listScaleVer;
    params.curr = $scope._getPagingInfo('curr');
    params.verSerNo = $("#verSerNo").val();
    params.verSerNm = $("#verSerNm").val();

    // console.log('params', params);

    $scope._inquiryMain("/pos/confg/verRecv/verRecv/list.sb", params, function() {
    });
  };


}]);


/**********************************************************************
 *  버전수신매장 그리드
 **********************************************************************/
app.controller('verRecvStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verRecvStoreCtrl', $scope, $http, true));

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
  };

  // 조회 버튼 클릭
  $scope.$on("verRecvStoreCtrl", function(event, data) {

    if( !isEmptyObject(data) ) {
      $scope.setSelectVersion(data);
    }

    $scope.getVersionStoreList();
    event.preventDefault();
  });

  // 버전수신정보 목록 조회
  $scope.getVersionStoreList = function(){
    var params = {};
    params.listScale = $scope.listScale;
    params.curr = $scope._getPagingInfo('curr');
    params.verSerNo = $scope.getSelectVersion().verSerNo;

    $scope._inquiryMain("/pos/confg/verRecv/verRecv/storeList.sb", params, function() {
    });
  };


}]);
