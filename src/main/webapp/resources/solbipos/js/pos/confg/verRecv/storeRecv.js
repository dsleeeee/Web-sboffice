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
  } else if(val === 'V') {
    location.href = '/pos/confg/verRecv/verStore/list.sb';
  }
}

function getStoreList(){
  var scope = agrid.getScope('storeRecvCtrl');
  scope.getStoreVersionList();
}

/**********************************************************************
 *  버전목록 그리드
 **********************************************************************/
app.controller('storeRecvCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeRecvCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 선택 매장
  $scope.selectStore;
  $scope.setSelectStore = function(store){
    $scope.selectStore = store;
  };
  $scope.getSelectStore = function(){
    return $scope.selectStore;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.mainYnDatMap = new wijmo.grid.DataMap(mainYn, 'value', 'name');
    $scope.posFgDatMap = new wijmo.grid.DataMap(posFg, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "storeNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 매장 선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        if ( col.binding === "storeNm") {
          var selectedData = s.rows[ht.row].dataItem;
          $scope.setSelectStore(selectedData);
          $scope.storeRecvDtlLayer.show(true);
          event.preventDefault();
        }
      }
    });
  };

  // 조회 버튼 클릭
  $scope.$on("storeRecvCtrl", function(event, data) {
    $scope.getStoreVersionList();
    event.preventDefault();
  });

  // 버전 목록 조회
  $scope.getStoreVersionList = function(){
    var params = {};
    params.listScale = 30;
    params.curr = $scope._getPagingInfo('curr');
    if (orgnFg == 'HQ'){
      params.hqOfficeCd = hqOfficeCd;
    } else {
      params.hqOfficeCd = $("#hqOfficeCd").val();
    }
    params.hqOfficeNm = $("#hqOfficeNm").val();
    params.storeCd = $("#storeCd").val();
    params.storeNm = $("#storeNm").val();
    params.lastVer = $("#lastVer").val();

    // console.log('params', params);

    $scope._inquiryMain("/pos/confg/verRecv/storeRecv/list.sb", params, function() {
    });
  };


  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 매장별 수신현황 상세 팝업 핸들러 추가
    $scope.storeRecvDtlLayer.shown.addHandler(function (s) {
      $scope._broadcast("storeRecvDtlCtrl", $scope.getSelectStore());
    });
  });

}]);
