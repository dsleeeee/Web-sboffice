/****************************************************************
 *
 * 파일명 : branchMoms.js
 * 설  명 : 본사-그룹 관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.09.23     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var useYnFg = [
  {"name":"전체","value":""},
  {"name":"사용","value":"Y"},
  {"name":"사용안함","value":"N"}
];

// 사용여부 DropBoxDataMap
var useYnFgDataMap = new wijmo.grid.DataMap([
  {id: "", name: "전체"},
  {id: "Y", name: "사용"},
  {id: "N", name: "사용안함"}
], 'id', 'name');

app.controller('branchMomsCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('branchMomsCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("srchUseYnFg", useYnFg);


  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

    //그리드 링크설정
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if( col.binding == "branchCd" || col.binding == "branchNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      var row = ht.row;
      if( ht.cellType == wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if( col.binding == "branchCd" || col.binding == "branchNm") {

          $scope.wjBranchMomsDtlLayer.show(true);
          var scope = agrid.getScope("branchMomsDtlCtrl");
          scope._broadcast('branchMomsDtlCtrl', selectedRow);
          event.preventDefault();
        }
      }
    });
  };

  $scope.$on("branchMomsCtrl", function(event, data) {
    $scope.searchBranchMomsList();
    event.preventDefault();
  });

  // 그리드 조회
  $scope.searchBranchMomsList = function(){
    var params = {};
    params.branchCd = $scope.branchCd;
    params.branchNm = $scope.branchNm;
    params.branchOwnerNm = $scope.branchOwnerNm;
    params.phoneNo = $scope.phoneNo;
    params.useYn = $scope.useYnCombo.selectedValue;

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
    $.postJSON("/store/hq/branchMoms/branchMoms/getBranchMomsList.sb", params, function (response){
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

  $scope.newAdd = function (){
    $scope.wjBranchMomsDtlLayer.show(true);
    var scope = agrid.getScope("branchMomsDtlCtrl");
    scope._broadcast('branchMomsDtlCtrl');
    event.preventDefault();
  };
}]);
