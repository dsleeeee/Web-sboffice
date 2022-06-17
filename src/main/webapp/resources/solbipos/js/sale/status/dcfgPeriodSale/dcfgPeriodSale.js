/****************************************************************
 *
 * 파일명 : dcfgPeriodSale.js
 * 설  명 : 할인구분기간상세 화면 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.06.15     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 할인구분기간상세 controller */
app.controller('dcfgPeriodSaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dcfgPeriodSaleCtrl', $scope, $http, $timeout, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", getToday());
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", getToday());

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                // 소계, 합계 row에 색상 넣기
                if (col.binding === "seq" || col.binding === "saleDate" || col.binding === "storeNm" || col.binding === "lv1Nm" || col.binding === "lv2Nm" ||
                    col.binding === "lv3Nm" || col.binding === "dcNm" || col.binding === "dcdtlDcNm" || col.binding === "prodNm" || col.binding === "saleQty" ||
                    col.binding === "saleAmt" ||col.binding === "dcAmt" || col.binding === "totDcAmt" || col.binding === "realSaleAmt") {

                    // 소계
                    if (item[("seq")] === '2') {
                        wijmo.addClass(e.cell, 'wij_gridBackground-pink-bold');
                    }

                    // 합계
                    if (item[("seq")] === '3') {
                        wijmo.addClass(e.cell, 'wij_gridBackground-yellow-bold');
                    }
                }
            }
        });

        s.allowMerging = 'Cells';
        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                var rows = panel.rows[r];

                // row가 소계 이거나 합계인 경우, row merging 가능
                if(rows.dataItem.seq == "2" || rows.dataItem.seq == "3") {

                    rows.allowMerging = true;

                    // 소계, 합계 Title 왼쪽정렬
                    if (col.binding === "seq" || col.binding === "saleDate" || col.binding === "storeNm" || col.binding === "lv1Nm" || col.binding === "lv2Nm" ||
                        col.binding === "lv3Nm" || col.binding === "dcNm" || col.binding === "dcdtlDcNm" || col.binding === "prodNm"){
                        wijmo.addClass(cell, 'itemAlignment');
                    }
                }
            }
        };

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
          var ht = s.hitTest(e);
          s.allowSorting = false;
        });

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dcfgPeriodSaleCtrl", function (event, data) {

        // 조회
        $scope.srchDcfgPeriodSaleList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.srchDcfgPeriodSaleList = function(){

        // 파라미터
        var params       = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $("#dcfgPeriodSaleStoreCd").val();
        params.dcCd = $("#dcfgPeriodSaleDcfgCd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/dcfgPeriodSale/list.sb", params, function() {

            var grid = wijmo.Control.getControl("#wjGridDcfgPeriodSale");
            var rows = grid.rows;
            var rowCount = 0;     // 소계에 표시될 항목 총갯수
            var rowCountAll = 0;  // 합계에 표시될 항목 총갯수

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];

                // 항목 총갯수 표시를 위해
                if(item.seq === "1") {
                    rowCount++;
                }else if(item.seq === "2") {

                    // 소계, 합계는 정보 없고 title 넣기
                    var rowCountMsg = "상품 : " + (item.dcdtlDcNm ? item.dcdtlDcNm : '')  + " (" + rowCount + " 항목)";

                    // cell 내용이 같으면 merge 됨(rows.allowMerging = true;)
                    item.prodNm = rowCountMsg;
                    item.saleDate = rowCountMsg;
                    item.storeNm = rowCountMsg;
                    item.lv1Nm = rowCountMsg;
                    item.lv2Nm = rowCountMsg;
                    item.lv3Nm = rowCountMsg;
                    item.dcNm = rowCountMsg;
                    item.dcdtlDcNm = rowCountMsg;

                    // 전체 항목 총갯수 표시를 위해
                    rowCountAll += rowCount;
                    rowCount = 0;

                }else{

                    // 소계, 합계는 정보 없고 title 넣기
                    var rowCountAllMsg = item.saleDate + " " + item.storeNm + " " + "전체 : (" + rowCountAll + " 항목)";

                    // cell 내용이 같으면 merge 됨(rows.allowMerging = true;)
                    item.prodNm = rowCountAllMsg;
                    item.saleDate = rowCountAllMsg;
                    item.storeNm = rowCountAllMsg;
                    item.lv1Nm = rowCountAllMsg;
                    item.lv2Nm = rowCountAllMsg;
                    item.lv3Nm = rowCountAllMsg;
                    item.dcNm = rowCountAllMsg;
                    item.dcdtlDcNm = rowCountAllMsg;

                    rowCountAll = 0;
                }
            }
        });

    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dcfgPeriodSaleStoreShow = function () {
        $scope._broadcast('dcfgPeriodSaleStoreCtrl');
    };

    // 할인유형선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dcfgPeriodSaleDcfgShow = function () {
        $scope._broadcast('dcfgPeriodSaleDcfgCtrl');
    };

    // 상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
    $scope.isChkProdClassDisplay = function(){
        var columns = $scope.flex.columns;

        for(var i=0; i<columns.length; i++){
            if(columns[i].binding === 'lv1Nm' || columns[i].binding === 'lv2Nm' || columns[i].binding === 'lv3Nm'){
                $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
            }
        }
    };
    
    // 엑셀다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
          $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
          return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    return column.visible;
                }
            },  messages["dcfgPeriodSale.dcfgPeriodSale"] + '_'+ getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    }
    
}]);
