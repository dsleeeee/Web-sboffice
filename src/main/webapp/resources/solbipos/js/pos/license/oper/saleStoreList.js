/****************************************************************
 *
 * 파일명 : saleStoreList.js
 * 설  명 : 운영현황 매출매장현황탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.10.28     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부
var shopMigFgComboMap = [
    {"name": "전체", "value": ""},
    {"name": "신규", "value": "0"},
    {"name": "전환", "value": "1"}
];

/**
 *  매출매장현황 그리드 생성
 */
app.controller('saleStoreListCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleStoreListCtrl', $scope, $http, true));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 신규/전환
    $scope._setComboData("srchShopMigFg", shopMigFgComboMap);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태구분

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 'AllHeaders';
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};

        dataItem.hqOfficeCd     = messages["oper.storeInfo"];
        dataItem.hqOfficeNm     = messages["oper.storeInfo"];
        dataItem.storeCd        = messages["oper.storeInfo"];
        dataItem.storeNm        = messages["oper.storeInfo"];
        dataItem.bizNo          = messages["oper.storeInfo"];
        dataItem.vendorTermnlNo = messages["oper.storeInfo"];
        dataItem.sysStatNm      = messages["oper.storeInfo"];
        dataItem.vanNm          = messages["oper.storeInfo"];
        dataItem.shopMigFg      = messages["oper.storeInfo"];
        dataItem.regDate        = messages["oper.storeInfo"];
        dataItem.sysOpenDate    = messages["oper.storeInfo"];
        dataItem.agencyCd       = messages["oper.storeInfo"];
        dataItem.agencyNm       = messages["oper.storeInfo"];
        dataItem.posCnt         = messages["oper.storeInfo"];
        dataItem.billCnt        = messages["oper.dateTot"];
        dataItem.saleCnt        = messages["oper.dateTot"];
        dataItem.rtnSaleCnt     = messages["oper.dateTot"];
        dataItem.realSaleCnt    = messages["oper.dateTot"];
        dataItem.pBillCnt       = messages["oper.date"];
        dataItem.pSaleCnt       = messages["oper.date"];
        dataItem.pRtnSaleCnt    = messages["oper.date"];
        dataItem.pRealSaleCnt   = messages["oper.date"];
        dataItem.pCardCnt       = messages["oper.date"];
        dataItem.pCashCnt       = messages["oper.date"];
        dataItem.pCashApprCnt   = messages["oper.date"];
        dataItem.totGuestCnt    = messages["oper.etcInfo"];
        dataItem.totDlvrCnt     = messages["oper.etcInfo"];
        dataItem.totResveCnt    = messages["oper.etcInfo"];
        dataItem.totRefundCnt   = messages["oper.etcInfo"];
        dataItem.minSaleDate    = messages["oper.etcInfo"];
        dataItem.maxSaleDate    = messages["oper.etcInfo"];
        dataItem.ownerNm        = messages["oper.etcInfo"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display: 'table',
                    tableLayout: 'fixed'
                })
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display: 'table-cell',
                    verticalAlign: 'middle',
                    textAlign: 'center'
                });
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            // readOnly 배경색 표시
            else if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }

        s.refresh();
    };

    // <-- 검색 호출 -->
    $scope.$on("saleStoreListCtrl", function(event, data) {
        $scope.searchSaleStoreList();
        event.preventDefault();
    });

    // 매출매장현황 그리드 조회
    $scope.searchSaleStoreList = function() {
        if (!$scope.valueCheck()) {
            return false;
        }
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
        params.srchAgencyCd = $("#ssl_srchAgencyCd").val();
        params.hqOfficeCd = $("#hqOfficeCd").val();
        params.hqOfficeNm = $("#hqOfficeNm").val();
        params.storeCd = $("#storeCd").val();
        params.storeNm = $("#storeNm").val();
        params.bizNo = $("#bizNo").val();
        params.srchShopMigFg = $scope.shopMigFg;
        params.chkDt = $scope.isChecked;
        params.orgnFg = orgnFg;
        params.pAgencyCd = pAgencyCd;
        params.agencyCd = orgnCd;
        params.listScale = $scope.listScaleSale;
        params.manageVanCd = $("#ssl_srchManageVanCd").val();

        var srchStartToEnd = "(" + wijmo.Globalize.format($scope.startDate, 'yyyy-MM-dd') + " ~ " + wijmo.Globalize.format($scope.endDate, 'yyyy-MM-dd') + ")";
        var grid = wijmo.Control.getControl("#saleStoreListGrid").columnHeaders.rows[0].dataItem;

        grid.pBillCnt     = messages["oper.date"] + srchStartToEnd;
        grid.pSaleCnt     = messages["oper.date"] + srchStartToEnd;
        grid.pRtnSaleCnt  = messages["oper.date"] + srchStartToEnd;
        grid.pRealSaleCnt = messages["oper.date"] + srchStartToEnd;
        grid.pCardCnt     = messages["oper.date"] + srchStartToEnd;
        grid.pCashCnt     = messages["oper.date"] + srchStartToEnd;
        grid.pCashApprCnt = messages["oper.date"] + srchStartToEnd;

        $scope._inquiryMain("/pos/license/oper/oper/getSaleStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

// 체크
    $scope.valueCheck = function () {
        var msg = messages['kds.date.error'];
        var date1 = new Date(wijmo.Globalize.format($scope.startDate, 'yyyy-MM-dd'));
        var date2 = new Date(wijmo.Globalize.format($scope.endDate, 'yyyy-MM-dd'));

        var diffDay = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
        if (diffDay > 31) {
            $scope._popMsg(msg);
            return false;
        }
        return true;
    };

    /*********************************************************
     * 관리밴사 조회
     * *******************************************************/
    $scope.searchManageVan = function(){
        var popup = $scope.vanLayer;

        // 팝업 닫을때
        popup.show(true, function (s) {
            var vanScope = agrid.getScope('searchVanCtrl');
            vanScope.$apply(function () {
                vanScope._gridDataInit();
                if (!$.isEmptyObject(vanScope.getVan())) {
                    $("#ssl_srchManageVanCd").val(vanScope.getVan().vanCd);
                    $("#manageVanNm").val(vanScope.getVan().vanNm);
                }
            });
        });
    };

    // 관리밴사 선택취소
    $scope.delManageVanCd = function(){
        $("#ssl_srchManageVanCd").val("");
        $("#manageVanNm").val(messages["cmm.all"]);
    }

    /*********************************************************
     * 대리점(관리업체) 조회
     * *******************************************************/
    $scope.searchAgency = function(){
        if(orgnFg === "MASTER") {
            var popup = $scope.agencyLayer;

            // 팝업 닫을때
            popup.show(true, function (s) {
                var agencyScope = agrid.getScope('searchAgencyCtrl');
                agencyScope.$apply(function () {
                    agencyScope._gridDataInit();
                    if (!$.isEmptyObject(agencyScope.getAgency())) {
                        $("#ssl_srchAgencyCd").val(agencyScope.getAgency().agencyCd);
                        $("#agencyNm").val(agencyScope.getAgency().agencyNm);
                    }
                });
            });
        }
    };

    // 대리점(관리업체) 선택취소
    $scope.delAgencyCd = function(){
        $("#ssl_srchAgencyCd").val("");
        $("#agencyNm").val(messages["cmm.all"]);
    }

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,  //20220427 false 엑섹속도 cell 스타일 확인중
                includeColumns: function (column) {
                    return column.visible;
                }
            }, '매출매장현황_매출일자(' + wijmo.Globalize.format($scope.startDate, 'yyyy-MM-dd') +'_' + wijmo.Globalize.format($scope.endDate, 'yyyy-MM-dd') + ')'+'_'+ getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);