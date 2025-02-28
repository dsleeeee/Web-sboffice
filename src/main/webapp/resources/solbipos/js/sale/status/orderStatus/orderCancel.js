/****************************************************************
 *
 * 파일명 : orderCancel.js
 * 설  명 : 주문취소 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.05.15     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 배달주문구분
var cancelFgData = [
    {"name": "전체", "value": ""},
    {"name": "주문취소", "value": "1"},
    {"name": "상세일부취소", "value": "2"}
];

var cancelFg;

/**
 * 기간내 그리드 생성
 */
app.controller('orderCancelSrch1Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orderCancelSrch1Ctrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.ocStartDate = wcombo.genDateVal("#ocStartDate", gvStartDate);
    $scope.ocEndDate   = wcombo.genDateVal("#ocEndDate", gvEndDate);

    // 주문구분 콤보박스 데이터 set
    $scope._setComboData("cancelFgCombo", cancelFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
          if (e.panel == s.cells) {
              var col = s.columns[e.col];
              if (col.binding === "cancelCnt") {
                  wijmo.addClass(e.cell, 'wijLink');
              }
          }
        });

        // 그리드 선택 이벤트
        s.hostElement.addEventListener('mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                //var selectedRow = s.rows[ht.row].dataItem;
                var col = ht.panel.columns[ht.col];
                if( col.binding === "cancelCnt") {
                    var params = {};
                    params.startDate    = wijmo.Globalize.format($scope.ocStartDate.value, 'yyyyMMdd');
                    params.endDate      = wijmo.Globalize.format($scope.ocEndDate.value, 'yyyyMMdd');
                    params.storeCds     = $("#orderCancelSelectStoreCd").val();
                    params.cancelFg     = cancelFg;
                    $scope._broadcast('orderCancelCtrl', params);
                }
            }
        });
    };

    // 조회
    $scope.$on("orderCancelSrch1Ctrl", function(event, data) {
        // 기간내 전체취소건수 조회
        $scope.orderCancelPeriod();
        event.preventDefault();
    });
    
    // 기간내 전체취소건수 조회
    $scope.orderCancelPeriod = function () {

        if($("#orderCancelSelectStoreCd").val() === ""){
          $scope._popMsg(messages["orderStatus.storeCd"] + messages["cmm.require.select"]); // 매장코드(을)를 선택해주세요.
          return false;
        }

        var params = {};
        params.startDate    = wijmo.Globalize.format($scope.ocStartDate.value, 'yyyyMMdd');
        params.endDate      = wijmo.Globalize.format($scope.ocEndDate.value, 'yyyyMMdd');
        params.storeCds     = $("#orderCancelSelectStoreCd").val();
        params.cancelFg     = $scope.cancelFgCombo.selectedValue;

        cancelFg            = params.cancelFg;

        // 주문취소 grid 초기화
        var wjGridOrderCancel = wijmo.Control.getControl("#wjGridOrderCancel");
        while(wjGridOrderCancel.rows.length > 0){
            wjGridOrderCancel.rows.removeAt(wjGridOrderCancel.rows.length-1);
        }

        $scope._inquiryMain('/sale/orderStatus/orderStatus/orderStatus/getOrderCancelPeriod.sb', params, function()  {
            // 일자별 취소건수 조회
            var scope2 = agrid.getScope('orderCancelSrch2Ctrl');
            scope2.orderCancelByDate(params);

            // 캐셔별 취소건수 조회
            var scope3 = agrid.getScope('orderCancelSrch3Ctrl');
            scope3.orderCancelByCashier(params);
        });
    };

}]);

/**
 * 일자별 그리드 생성
 */
app.controller('orderCancelSrch2Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orderCancelSrch2Ctrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
          if (e.panel == s.cells) {
              var col = s.columns[e.col];
              if (col.binding === "cancelCnt") {
                  wijmo.addClass(e.cell, 'wijLink');
              }
          }
        });

        // 그리드 선택 이벤트
        s.hostElement.addEventListener('mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var selectedRow = s.rows[ht.row].dataItem;
                var col = ht.panel.columns[ht.col];
                if( col.binding === "cancelCnt") {
                    var params = {};
                    params.startDate    = wijmo.Globalize.format($scope.ocStartDate.value, 'yyyyMMdd');
                    params.endDate      = wijmo.Globalize.format($scope.ocEndDate.value, 'yyyyMMdd');
                    params.saleDate     = selectedRow.saleDate.replaceAll("-","");
                    params.storeCds     = $("#orderCancelSelectStoreCd").val();
                    params.cancelFg     = cancelFg;
                    $scope._broadcast('orderCancelCtrl', params);
                }
            }
        });
    };

    $scope.$on("orderCancelSrch2Ctrl", function(event, data) {
        event.preventDefault();
    });

    // 일자별 취소건수 조회
    $scope.orderCancelByDate = function (data) {
        $scope._inquirySub('/sale/orderStatus/orderStatus/orderStatus/getOrderCancelByDate.sb', data, function() {});
    };

}]);

/**
 * 캐셔별 그리드 생성
 */
app.controller('orderCancelSrch3Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orderCancelSrch3Ctrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
          if (e.panel == s.cells) {
              var col = s.columns[e.col];
              if (col.binding === "cancelCnt") {
                  wijmo.addClass(e.cell, 'wijLink');
              }
          }
        });

        // 그리드 선택 이벤트
        s.hostElement.addEventListener('mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var selectedRow = s.rows[ht.row].dataItem;
                var col = ht.panel.columns[ht.col];
                if( col.binding === "cancelCnt") {
                    var params = {};
                    params.startDate    = wijmo.Globalize.format($scope.ocStartDate.value, 'yyyyMMdd');
                    params.endDate      = wijmo.Globalize.format($scope.ocEndDate.value, 'yyyyMMdd');
                    params.empNo        = selectedRow.empNo;
                    params.storeCds     = $("#orderCancelSelectStoreCd").val();
                    params.cancelFg     = cancelFg;
                    $scope._broadcast('orderCancelCtrl', params);
                }
            }
        });
    };

    $scope.$on("orderCancelSrch3Ctrl", function(event, data) {
        event.preventDefault();
    });

    // 캐셔별 취소건수 조회
    $scope.orderCancelByCashier = function (data) {
        $scope._inquirySub('/sale/orderStatus/orderStatus/orderStatus/getOrderCancelByCashier.sb', data, function() {});
    };
}]);

/**
 * 주문취소 그리드 생성
 */
app.controller('orderCancelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orderCancelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.orderFgDataMap = new wijmo.grid.DataMap(orderFgData, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];

                // 판매데이터(SL_SALE) 데이터가 있는경우 영수증 상세 조회 가능
                if (col.binding === "billNo") {
                    var item = s.rows[e.row].dataItem;
                    if (item.saleYn !== "" && item.saleYn !== null && item.saleYn !== undefined){
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
                
                // 취소건은 빨간색으로 표시
                if (col.binding === "orderRegDt" || col.binding === "orderSeq" || col.binding === "prodNm" ||
                    col.binding === "orderDtlFg" || col.binding === "saleQty" || col.binding === "dcAmt" ||
                    col.binding === "realSaleAmt" || col.binding === "empNm" || col.binding === "moveTblNo" ||
                    col.binding === "moveTblCd") {
                    var item = s.rows[e.row].dataItem;
                    if (item.orderDtlFg === "2") {
                        wijmo.addClass(e.cell, 'red');
                    }
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                var params      = {};
                params.storeCd  = selectedRow.storeCd;
                params.saleDate = selectedRow.saleDate.replaceAll("-","");
                params.posNo    = selectedRow.posNo;
                params.billNo   = selectedRow.billNo;
                params.saleYn   = selectedRow.saleYn;
                params.webReg   = selectedRow.webReg;

                if (col.binding === "billNo") { // 영수증번호 클릭
                    // 판매데이터(SL_SALE) 데이터가 있는경우 영수증 상세 조회 가능
                    if(selectedRow.saleYn !== "" && selectedRow.saleYn !== null && selectedRow.saleYn !== undefined) {
                        $scope._broadcast('billInfoCtrl', params);
                    }
                }
            }
        });
    };

    $scope.$on("orderCancelCtrl", function(event, data) {
        // 주문취소내역 조회
        $scope.getOrderCancelList(data);
        event.preventDefault();
    });

    // 주문취소내역 조회
    $scope.getOrderCancelList = function (data) {
        $scope._inquirySub('/sale/orderStatus/orderStatus/orderStatus/getOrderCancelList.sb', data, function() {});
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
          $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
          return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
          wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
            includeColumnHeaders: true,
            includeCellStyles: true,
            includeColumns: function (column) {
              return column.visible;
            }
          }, '주문취소_' + getToday() + '.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
        }, 10);
    };
}]);