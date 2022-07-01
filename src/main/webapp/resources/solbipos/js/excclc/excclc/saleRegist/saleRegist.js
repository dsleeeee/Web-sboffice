/**
 * get application
 */
var app = agrid.getApp();

/** 일별종합 controller */
app.controller('saleRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('saleRegistCtrl', $scope, $http, true));

  var srchGubun = false;

  $scope.srchSaleDate = wcombo.genDateVal("#srchSaleDate", gvStartDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("saleRegistCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "billNo") {
            wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 기능구분 선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        // 기능구분 클릭시 해당 기능 목록 조회
        if ( col.binding === "billNo" ) {
            $scope.newRegistLayer.show();

            $("#saleDate").text(wijmo.Globalize.format($scope.srchSaleDate.value, 'yyyy-MM-dd'));

            if(orgnFg == "HQ") {
              $("#storeNm").text($("#saleRegistStoreNm").val());
              $("#storeCd").text($("#saleRegistStoreCd").val());
            } else {
              $("#storeNm").text("[" + storeCd + "]" + storeNm);
              $("#storeCd").text(storeCd);
            }
            $("#posNo").text(selectedRow.posNo);
            $("#billNo").text(selectedRow.billNo);

            if(selectedRow.modFg === "N"){
              $("#btnSave").css("display", "none");
            } else {
              $("#btnSave").css("display", "");
            }

            var newRegistScope = agrid.getScope('newRegistCtrl');
            newRegistScope.searchBillDtl();

          event.preventDefault();
        }
      }
    });
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 팝업 핸들러 추가
    $scope.newRegistLayer.hidden.addHandler(function (s) {
      setTimeout(function () {
        var selectProdScope = agrid.getScope('selectProdCtrl');
        selectProdScope._gridDataInit();   // 선택상품 그리드 초기화
        var newRegistScope = agrid.getScope('newRegistCtrl');
        newRegistScope._gridDataInit();   // 선택상품 그리드 초기화
        $("#cash").val("");
        $("#card").val("");
        $("#posNo").val("01");
        $("#billNo").val("");

      }, 50)
    });
  });

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("saleRegistCtrl", function (event, data) {
    if(orgnFg == "HQ") {
      if ($("#saleRegistStoreCd").val() == "" || $("#saleRegistStoreCd").val() == undefined) {
        $scope._popMsg(messages["cmm.require.selectStore"]);
        return false;
      }
    }
      $scope.searchSaleRegistList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.newRegist = function (){
    if(srchGubun){
      if(orgnFg == "HQ") {
        if ($("#saleRegistStoreCd").val() == "" || $("#saleRegistStoreCd").val() == undefined) {
          $scope._popMsg(messages["cmm.require.selectStore"]);
          return false;
        }
      }

      $scope.newRegistLayer.show(true);
      $("#saleDate").text(wijmo.Globalize.format($scope.srchSaleDate.value, 'yyyy-MM-dd'));
      if(orgnFg == "HQ") {
        $("#storeNm").text($("#saleRegistStoreNm").val());
        $("#storeCd").text($("#saleRegistStoreCd").val());
      } else {
        $("#storeNm").text("[" + storeCd + "]" + storeNm);
        $("#storeCd").text(storeCd);
      }
      $("#billNo").text("");
      $("#btnSave").css("display", "");

      var newRegistScope = agrid.getScope('newRegistCtrl');
      newRegistScope.saleFg = "1";

    } else {
      $scope._popMsg(messages["saleRegist.srchGubun.chk"]);
      return false;
    }
  };

  // 매출수기등록 리스트 조회
  $scope.searchSaleRegistList = function () {
    // 파라미터
    var params       = {};
    params.saleDate = wijmo.Globalize.format($scope.srchSaleDate.value, 'yyyyMMdd');
    params.storeCd = $("#saleRegistStoreCd").val();
    
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/excclc/excclc/saleRegist/saleRegist/getSaleRegistList.sb", params, function (){
      srchGubun = true;
    });
  };

  // 매장선택
  $scope.saleRegistStoreShow = function () {
    $scope._broadcast('saleRegistStoreCtrl');
  };
}]);
