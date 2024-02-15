/****************************************************************
 *
 * 파일명 : serviceTimeAvg.js
 * 설  명 : 서비스타임(평균시간) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.02.06     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 서비스구분
var serviceTypeComboData = [
    {"name":"전체","value":""},
    {"name":"DELIVERY","value":"DELIVERY"},
    {"name":"PICKUP","value":"PICKUP"},
    {"name":"RESERVATION","value":"RESERVATION"},
    {"name":"EAT_IN","value":"EAT_IN"}
];

/**
 *  서비스타임(평균시간) 그리드 생성
 */
app.controller('serviceTimeAvgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('serviceTimeAvgCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);

    // 콤보박스 셋팅
    $scope._setComboData("serviceTypeCombo", serviceTypeComboData); // 서비스구분

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("serviceTimeAvgCtrl", function(event, data) {
        $scope.searchServiceTimeAvg();
        event.preventDefault();
    });

    $scope.searchServiceTimeAvg = function(){
        if($("#serviceTimeAvgStoreCd").val() == "") {
            $scope._popMsg(messages["cmm.require.selectStore"]); // 매장을 선택해 주세요.
            return false;
        }

        if($("#serviceTimeAvgStoreCd").val().split(",").length > 5) {
            $scope._popMsg(messages["serviceTimeAvg.storeCntAlert"]); // 매장은 최대 5개 선택 가능합니다.
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.storeCds = $("#serviceTimeAvgStoreCd").val();
        params.serviceType = $scope.serviceType;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.momsStoreFg01 = $scope.momsStoreFg01;

        $scope._inquiryMain("/sale/status/serviceTimeAvg/serviceTimeAvg/getServiceTimeAvgList.sb", params, function() {
            // <-- 그리드 visible -->
            var grid = wijmo.Control.getControl("#wjGridList");

            // 동적 컬럼 초기화
            while (grid.columns.length > 1) {
                grid.columns.removeAt(grid.columns.length - 1);
            }

            // 매장코드
            var arrStoreCd = $("#serviceTimeAvgStoreCd").val().split(",");
            // [매장코드] 매장명
            var arrStoreCdNm = $("#serviceTimeAvgStoreCdNm").val().split(",");

            // 컬럼 생성
            for (var i = 0; i < arrStoreCd.length; i++) {
                // TC
                grid.columns.push(new wijmo.grid.Column({
                    header: messages["serviceTimeAvg.tc"],
                    binding: "store" + arrStoreCd[i] + "Tc",
                    width: 90,
                    align: "center",
                    isReadOnly: "true"
                }));

                // 서비스타임
                grid.columns.push(new wijmo.grid.Column({
                    header: messages["serviceTimeAvg.serviceTime"],
                    binding: "store" + arrStoreCd[i] + "ServiceTime",
                    width: 90,
                    align: "center",
                    isReadOnly: "true"
                }));
            }

            // grid merge 가능 영역
            // All:= 7 (Merge all areas), AllHeaders:= 6(Merge column and row headers), Cells:= 1(Merge scrollable cells),
            // ColumnHeaders:= 2(Merge column headers), None:= 0(No merging), RowHeaders:= 4(Merge row headers)
            grid.allowMerging = 'ColumnHeaders';
            // grid header 라인 기본 2줄 유지
            if(2 > grid.columnHeaders.rows.length){
                grid.columnHeaders.rows.push(new wijmo.grid.Row());
            }

            // 첫째줄 헤더 생성
            var dataItem = {};
            dataItem.saleTime = messages["serviceTimeAvg.saleTime"];

            for (var i = 0; i < arrStoreCd.length; i++) {
                eval('dataItem.store' + arrStoreCd[i] + 'Tc' + '="' + arrStoreCdNm[i] + '"');
                eval('dataItem.store' + arrStoreCd[i] + 'ServiceTime' + '="' + arrStoreCdNm[i] + '"');
            }

            grid.columnHeaders.rows[0].dataItem = dataItem;

            grid.itemFormatter = function (panel, r, c, cell) {

                if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                    //align in center horizontally and vertically
                    panel.rows[r].allowMerging    = true;
                    panel.columns[c].allowMerging = true;
                    wijmo.setCss(cell, {
                        display    : 'table',
                        tableLayout: 'fixed'
                    });
                    cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                    wijmo.setCss(cell.children[0], {
                        display      : 'table-cell',
                        verticalAlign: 'middle',
                        textAlign    : 'center'
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
                    var rows = panel.rows[r];

                    if (col.isReadOnly) {
                        wijmo.addClass(cell, 'wj-custom-readonly');
                    }
                }
            };
            // <-- //그리드 visible -->
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.serviceTimeAvgStoreShow = function () {
        $scope._broadcast('serviceTimeAvgStoreCtrl');
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

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
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },  '서비스타임(평균시간)' + '_' + wijmo.Globalize.format(startDate.value, 'yyyyMMdd') + '_' + getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);