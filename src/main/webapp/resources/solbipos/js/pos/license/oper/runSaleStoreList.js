/****************************************************************
 *
 * 파일명 : runSaleStoreList.js
 * 설  명 : 운영현황 러닝매장현황탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.05.10     김중선      1.0
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
app.controller('runSaleStoreListCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('runSaleStoreListCtrl', $scope, $http, true));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 신규/전환
    $scope._setComboData("srchShopMigFg", shopMigFgComboMap);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태구분

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("runSaleStoreListCtrl", function(event, data) {
        $scope.searchRunSaleStoreList();
        event.preventDefault();
    });

    // 매출매장현황 그리드 조회
    $scope.searchRunSaleStoreList = function() {
        if (!$scope.valueCheck()) {
            return false;
        }
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
        params.srchAgencyCd = $("#ssl_srchAgencyCdRun").val();
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
        params.manageVanCd = $("#ssl_srchManageVanCdRun").val();

        $scope._inquiryMain("/pos/license/oper/oper/getRunSaleStoreList.sb", params, function() {}, false);
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
    $scope.searchManageVanRun = function(){
        var popup = $scope.vanLayer;

        // 팝업 닫을때
        popup.show(true, function (s) {
            var vanScope = agrid.getScope('searchVanCtrl');
            vanScope.$apply(function () {
                vanScope._gridDataInit();
                if (!$.isEmptyObject(vanScope.getVan())) {
                    $("#ssl_srchManageVanCdRun").val(vanScope.getVan().vanCd);
                    $("#manageVanNmRun").val(vanScope.getVan().vanNm);
                }
            });
        });
    };

    // 관리밴사 선택취소
    $scope.delManageVanCdRun = function(){
        $("#ssl_srchManageVanCdRun").val("");
        $("#manageVanNmRun").val(messages["cmm.all"]);
    }

    /*********************************************************
     * 대리점(관리업체) 조회
     * *******************************************************/
    $scope.searchAgencyRun = function(){
        if(orgnFg === "MASTER") {
            var popup = $scope.agencyLayer;

            // 팝업 닫을때
            popup.show(true, function (s) {
                var agencyScope = agrid.getScope('searchAgencyCtrl');
                agencyScope.$apply(function () {
                    agencyScope._gridDataInit();
                    if (!$.isEmptyObject(agencyScope.getAgency())) {
                        $("#ssl_srchAgencyCdRun").val(agencyScope.getAgency().agencyCd);
                        $("#agencyNmRun").val(agencyScope.getAgency().agencyNm);
                    }
                });
            });
        }
    };

    // 대리점(관리업체) 선택취소
    $scope.delAgencyCdRun = function(){
        $("#ssl_srchAgencyCdRun").val("");
        $("#agencyNmRun").val(messages["cmm.all"]);
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
            }, '러닝매장현황_매출일자(' + wijmo.Globalize.format($scope.startDate, 'yyyy-MM-dd') +'_' + wijmo.Globalize.format($scope.endDate, 'yyyy-MM-dd') + ')'+'_'+ getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);