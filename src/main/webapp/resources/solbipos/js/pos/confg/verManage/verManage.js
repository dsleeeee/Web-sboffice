/****************************************************************
 *
 * 파일명 : verManage.js
 * 설  명 : 포스버전관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.31     김지은      1.0           Angular방식으로 변경
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  포스버전관리 그리드
 **********************************************************************/
app.controller('verManageCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verManageCtrl', $scope, $http, true));

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

    $scope.progFgDataMap = new wijmo.grid.DataMap(progFg, 'value', 'name');
    $scope.containYnDataMap = new wijmo.grid.DataMap(containYn, 'value', 'name');
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

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
          $scope.versionInfoDetailLayer.show(true);
          event.preventDefault();
        }
      }
    });
  };

  // 조회 버튼 클릭
  $scope.$on("verManageCtrl", function(event, data) {
    $scope.getVersionList();
    event.preventDefault();
  });

  // 버전 목록 조회
  $scope.getVersionList = function(){
    var params = {};
    params.listScale = $scope.listScaleVer;
    params.curr = $scope._getPagingInfo('curr');
    params.hqOfficeCd = hqOfficeCd;

    $scope._inquiryMain("/pos/confg/verManage/verInfo/list.sb", params, function() {
    });
  };

  // 신규버전 등록
  $scope.registVersion = function(){
    $scope.setSelectVersion(null);
    $scope.versionRegistLayer.show(true, function(){
      var scope = agrid.getScope('verRegistCtrl');
      scope.version = null;
      scope.progFg = '1';
      scope.useYn = 'Y';

      $('#file').val(null);
      $scope._pageView('verManageCtrl', 1);
    });
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 버전상세보기 팝업 핸들러 추가
    $scope.versionInfoDetailLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('verDetailCtrl');
      }, 50)
    });

    // 매장등록 팝업 핸들러 추가
    $scope.storeAddLayer.shown.addHandler(function (s) {
      // setTimeout(function() {
      //   $scope._broadcast('storeAddCtrl');
      // }, 50)
    });
    //
    // 버전신규등록 팝업 핸들러 추가
    $scope.versionRegistLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('verRegistCtrl', $scope.getSelectVersion() );
      }, 20)
    });
  });

}]);

