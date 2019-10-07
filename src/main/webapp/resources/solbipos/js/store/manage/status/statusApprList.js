/**
 * get application
 */
var app = agrid.getApp();

/** 관리매장 승인내역 controller */
app.controller('statusApprListCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusApprListCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchApprStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchApprEndDate", gvEndDate);
    $scope.orgnFg        = gvOrgnFg;

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("statusApprListCtrl");

        // 그리드 포맷
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if(col.binding === "cardApprCnt" || col.binding === "cashApprCnt"){
                    //wijmo.addClass(e.cell, 'wj-custom-readonly');
                    wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "cardApprCnt" || col.binding === "cashApprCnt") {
                    var params      = {};
                    params.hqOfficeCd = selectedRow.hqOfficeCd;
                    params.storeCd  = selectedRow.storeCd;
                    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
                    params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
                    params.payCol = payCol;

                    if (col.binding === "cardApprCnt") {
                        params.payApprType="CARD";
                    }else if (col.binding === "cashApprCnt"){
                        params.payApprType="CASH";
                    }

                    $scope._broadcast('statusApprDtlCtrl', params);
                }
            }
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("statusApprListCtrl", function (event, data) {
        $scope.searchStatusApprList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    $scope.searchStatusApprList = function () {

        // 파라미터
        var params       = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.agencyNm = $("#srchApprAgencyNm").val();
        params.storeNm = $("#srchApprStoreNm").val();
        params.bizNo = $("#srchApprBizNo").val();
        params.listScale = $scope.listScaleAppr;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/store/manage/status/storeAppr/list.sb", params);
    }


}]);