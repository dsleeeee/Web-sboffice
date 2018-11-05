/****************************************************************
 *
 * 파일명 : checkBizNo.js
 * 설  명 : 사업자번호 사용현황 조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.01     김지은      1.0
 *
 * **************************************************************/

/**
 *  벤사 조회 그리드
 */
app.controller('checkBizNoCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('checkBizNoCtrl', $scope, $http, false));

  $scope.bizNo = "";
  $scope.setBizNo = function (bizNo){
    $scope.bizNo = bizNo;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.storeFgDataMap = new wijmo.grid.DataMap([{value:"H", name:"본사"},{value:"S", name:"매장"}], 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "storeCd") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 매장 선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === "storeCd") {
          $scope.getBizInfoDetail(selectedRow);
        }
      }
    });
  };

  $scope.$on("checkBizNoCtrl", function(event, data) {
    $scope.searchBizInfo();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  /*********************************************************
   * 사업자번호 조회
   * *******************************************************/
  $scope.searchBizInfo = function(){
    var params = {};
    params.bizNo = $scope.bizNo;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/store/hq/hqManage/master/bizUseList.sb", params, function() {
    }, false);
  };


  /*********************************************************
   * 사업자번호 상세정보 조회
   * *******************************************************/
  $scope.getBizInfoDetail = function(params){

    $scope._postJSONQuery.withOutPopUp( "/store/hq/hqManage/master/bizInfoDtl.sb", params,
      function(response){

        console.log(response);

        var bizDetail = response.data.data;

        $("#bStoreCd").text(bizDetail.storeCd);
        $("#bStoreNm").text(bizDetail.storeNm);
        $("#bStoreCd").text(bizDetail.storeCd);
        $("#bSysStatFg").text(bizDetail.sysStatFgNm);
        $("#bOwnerNm").text(bizDetail.ownerNm);
        $("#bBizNo").text(bizDetail.bizNo1 + "-" + bizDetail.bizNo2 + "-" + bizDetail.bizNo3);
        $("#bBizStoreNm").text(bizDetail.bizStoreNm);
        $("#bArea").text(bizDetail.areaNm);
        $("#bTelNo").text(bizDetail.telNo);
        $("#bFaxNo").text(bizDetail.faxNo);
        $("#bAgency").text(bizDetail.agencyNm);
        $("#bClsFg").text(bizDetail.clsFgNm);
        $("#bSysOpenDate").text(bizDetail.sysOpenDate);
        $("#bAddr").text("(" + bizDetail.postNo + ") " + bizDetail.addr + " " + bizDetail.addrDtl);

      }
    );
  };



}]);
