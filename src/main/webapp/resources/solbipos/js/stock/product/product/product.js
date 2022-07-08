/****************************************************************
 *
 * 파일명 : product.js
 * 설  명 : 생산관리 - 생산등록 리스트 화면 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.06.21     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 생산등록 그리드 controller */
app.controller('productCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('productCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
    $scope.productDate   = wcombo.genDate("#productDate");

    // 생산구분
    $scope.productFgMap = new wijmo.grid.DataMap([
        {id: "0", name: messages["product.productFg0"]},
        {id: "1", name: messages["product.productFg1"]}
    ], 'id', 'name');

    // 진행
    $scope.procFgMap = new wijmo.grid.DataMap([
        {id: "0", name: messages["product.procFg0"]},
        {id: "1", name: messages["product.procFg1"]}
      ], 'id', 'name');

    // 생산구분 콤보박스
    $scope._setComboData("srchProductFg", [
        {"name": messages["cmm.all"], "value": ""},
        {"name": messages["product.productFg0"], "value": "0"},
        {"name": messages["product.productFg1"], "value": "1"}
    ]);

    // 진행 콤보박스
    $scope._setComboData("srchProcFg", [
        {"name": messages["cmm.all"], "value": ""},
        {"name": messages["product.procFg0"], "value": "0"},
        {"name": messages["product.procFg1"], "value": "1"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
          if (e.panel === s.cells) {
            var col = s.columns[e.col];
            var item = s.rows[e.row].dataItem;

            if (col.format === "date") {
              e.cell.innerHTML = getFormatDate(e.cell.innerText);
            } else if (col.format === "dateTime") {
              e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
            } else if (col.format === "time") {
              e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
            }

            if (col.binding === 'productTitle') {
                wijmo.addClass(e.cell, 'wijLink');
            }
          }
        });

        // 제목 클릭 시, 상세화면 조회
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;

                if (col.binding === "productTitle") {
                    $scope._broadcast('productDtlCtrl', selectedRow);
                    $scope.wjProductDtlLayer.show(true);
                    event.preventDefault();
                }
            }
        });


        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("productCtrl");
    };

    $scope.$on("productCtrl", function (event, data) {
        
        // 조회
        $scope.searchProductList();
        
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });
    
    // 조회
    $scope.searchProductList = function () {

        // 파라미터
        var params       = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.productFg = $scope.productFg;
        params.procFg    = $scope.procFg;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/stock/product/product/product/list.sb", params);
    };

    // 삭제
    $scope.deleteProduct = function () {

        /** 선택하신 자료를 삭제하시겠습니까? */
        var msg = messages["product.del.msg"];
        s_alert.popConf(msg, function () {

            var params = [];
            if ($scope.flex.collectionView.itemsEdited.length <= 0) {
                $scope._popMsg(messages["cmm.not.modify"]);
                return false;
            }

            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {

                var item = $scope.flex.collectionView.itemsEdited[i];

                if (item.gChk === true && item.procFg !== '0') {
                  $scope._popMsg(messages["product.confm.notDel.msg"]); // 확정된 자료는 삭제할 수 없습니다.
                  return false;
                }

                if(item.gChk === true) {
                  item.status = "U";
                  params.push(item);
                }
            }

            $scope._save("/stock/product/product/product/delete.sb", params, function () {

                // 재조회
                $scope.searchProductList();
            });
        });
    };

    // 생산등록, 폐기등록
    $scope.regProduct = function (productFg) {

        var params = {};
        params.productDate = wijmo.Globalize.format($scope.productDate.value, 'yyyyMMdd');
        params.productFg = productFg;
        params.seqNo = "";

        $scope.wjProductRegistLayer.show(true);
        $scope._broadcast('productRegistCtrl', params);
    };
    

}]);