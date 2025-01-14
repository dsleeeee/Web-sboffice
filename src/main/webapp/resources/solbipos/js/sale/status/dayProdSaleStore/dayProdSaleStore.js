/****************************************************************
 *
 * 파일명 : dayProdSaleStore.js
 * 설  명 : 매출관리 > 매출현황2 > 일별상품매출현황(매장별) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.01.09    이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 상품 구분
var selTypeFgData = [
    {"name":"일반판매상품","value":"N"},
    {"name":"사이드모상품","value":"P"},
    {"name":"사이드상품","value":"S"},
];

/**
 *  일별상품매출현황(매장별) 그리드 생성
 */
app.controller('dayProdSaleStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayProdSaleStoreCtrl', $scope, $http, false));

    // 조회일자
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 상품 구분
        $scope.selTypeFgMap = new wijmo.grid.DataMap(selTypeFgData, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 상품코드, 사이드상품코드
                if (col.binding === "prodCd" || col.binding === "sideProdCd") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                var params      = {};

                // 상품코드 클릭시 상세정보 조회
                if (col.binding === "prodCd" && selectedRow.prodCd !== "") {
                    params.hqOfficeCd = selectedRow.hqOfficeCd;
                    params.storeCd = selectedRow.storeCd;
                    params.saleDate = selectedRow.saleDate.replaceAll("-","");
                    params.prodCd = selectedRow.prodCd;
                    params.selTypeFg = selectedRow.selTypeFg == 'S' ? 'P' : selectedRow.selTypeFg; // 사이드상품의 모상품이기 때문에 변경 'N'으로 변경
                    params.sidePProdCd = '';
                    $scope._broadcast('dayProdSaleStoreDtlCtrl', params);
                    $scope.dayProdSaleStoreDtlLayer.show(true);
                    event.preventDefault();
                }

                // 사이드 상품코드 클릭시 상세정보 조회
                if (col.binding === "sideProdCd" && selectedRow.sideProdCd !== null) {
                    params.hqOfficeCd = selectedRow.hqOfficeCd;
                    params.storeCd = selectedRow.storeCd;
                    params.saleDate = selectedRow.saleDate.replaceAll("-", "");
                    params.prodCd = selectedRow.sideProdCd;
                    params.selTypeFg = selectedRow.selTypeFg;
                    params.sidePProdCd = selectedRow.prodCd;
                    $scope._broadcast('dayProdSaleStoreDtlCtrl', params);
                    $scope.dayProdSaleStoreDtlLayer.show(true);
                    event.preventDefault();
                }

            }
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dayProdSaleStoreCtrl", function (event, data) {
        // 조회
        $scope.searchList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.searchList = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 3달(93일) 제한
        if (diffDay > 93) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#dayProdSaleStoreStoreCd").val();
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.sideProdClassCd = $scope.sideProdClassCd;
        params.sideProdCd = $scope.sideProdCd;
        params.sideProdNm = $scope.sideProdNm;
        params.listScale = 500;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/sale/status/dayProdSaleStore/getDayProdSaleStoreList.sb", params);
    };

    // 엑셀다운로드
    $scope.excelDownload = function () {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 3달(93일) 제한
        if (diffDay > 93) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#dayProdSaleStoreStoreCd").val();
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.sideProdClassCd = $scope.sideProdClassCd;
        params.sideProdCd = $scope.sideProdCd;
        params.sideProdNm = $scope.sideProdNm;
        params.listScale = 500;

        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('dayProdSaleStoreExcelCtrl', params);
        });
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function () {
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    // 사이드상품분류정보 팝업
    $scope.popUpSideProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.sideProdClassCd = prodClassCd;
                        $scope.sideProdClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 사이드상품분류정보 선택취소
    $scope.delSideProdClass = function () {
        $scope.sideProdClassCd = "";
        $scope.sideProdClassCdNm = "";
    };

}]);

/**
 * 일별상품매출현황(매장별) 엑셀다운로드 그리드 생성
 */
app.controller('dayProdSaleStoreExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayProdSaleStoreExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dayProdSaleStoreExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/dayProdSaleStore/getDayProdSaleStoreExcelList.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, messages["dayProdSaleStore.dayProdSaleStore"]+ '_'+ getToday()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);
