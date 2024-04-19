/****************************************************************
 *
 * 파일명 : runStoreTrnsitn.js
 * 설  명 : 런닝매장현황 런닝매장추이 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.11     김유승      1.0
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

// 하위대리점 포함여부
var includeFgTypeComboData = [
    {"name":"포함","value":"Y"},
    {"name":"미포함","value":"N"}
];

/**
 *  매출매장현황 그리드 생성
 */
app.controller('runStoreTrnsitnCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('runStoreTrnsitnCtrl', $scope, $http, true));

    // 조회일자
    var startMonth = new wijmo.input.InputDate('#startMonth3', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 조회일자
    var endMonth = new wijmo.input.InputDate('#endMonth3', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    $scope._setComboData("includeFgCombo", includeFgTypeComboData); // 하위대리점 포함여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        // s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // // add a sigma to the header to show that this is a summary row
        // s.bottomLeftCells.setCellData(0, 0, '합계');

    };

    // <-- 검색 호출 -->
    $scope.$on("runStoreTrnsitnCtrl", function(event, data) {
        $scope.searchRunTrnsitnList();
        event.preventDefault();

    });

    // 매출매장현황 그리드 조회
    $scope.searchRunTrnsitnList = function() {
        if (!$scope.valueCheck()) {
            return false;
        }

        // 한달 전 날짜 구하기
        var endDt = new Date(startMonth.value);
        var endDiffDay = new Date(endDt.setMonth(endDt.getMonth()-1));
        var lastMonth = wijmo.Globalize.format(endDiffDay, 'yyyyMM');

        var endDt2 = new Date(endMonth.value);
        var endDiffDay2 = new Date(endDt2.setMonth(endDt2.getMonth()-1));
        var lastMonth2 = wijmo.Globalize.format(endDiffDay2, 'yyyyMM');

        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.hqOfficeCd = $scope.hqOfficeCd;
        params.hqOfficeNm = $scope.hqOfficeNm
        params.storeCd = $scope.storeCd
        params.storeNm = $scope.storeNm
        // params.orgnFg = orgnFg;
        // params.pAgencyCd = pAgencyCd;
        params.manageVanCd = $("#ssl_srchManageVanCdTrnsitn").val();
        params.includeFg = $scope.srchIncludeFgCombo.selectedValue;
        params.lastMonth = lastMonth;
        params.lastMonth2 = lastMonth2;


        if(orgnFg === "MASTER"){
            params.agencyCd = $("#ssl_srchAgencyCdTrnsitn").val();
        }else{
            params.agencyCd = orgnCd;
        }

        $scope._inquiryMain("/pos/license/runStore/runStore/getRunTrnsitnList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->


// 체크
    $scope.valueCheck = function () {

        var date1 = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var date2 = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(date1.getTime() > date2.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 1년(12개월) 제한
        if (diffMonth > 12) {
            $scope._popMsg(messages['cmm.dateOver.1year.error']);
            return false;
        }
        return true;
    };

    /*********************************************************
     * 관리밴사 조회
     * *******************************************************/
    $scope.searchManageVanTrnsitn = function(){
        var popup = $scope.vanLayer;

        // 팝업 닫을때
        popup.show(true, function (s) {
            var vanScope = agrid.getScope('searchVanCtrl');
            vanScope.$apply(function () {
                vanScope._gridDataInit();
                if (!$.isEmptyObject(vanScope.getVan())) {
                    $("#ssl_srchManageVanCdTrnsitn").val(vanScope.getVan().vanCd);
                    $("#manageVanNmTrnsitn").val(vanScope.getVan().vanNm);
                }
            });
        });
    };

    // 관리밴사 선택취소
    $scope.delManageVanCdTrnsitn = function(){
        $("#ssl_srchManageVanCdTrnsitn").val("");
        $("#manageVanNmTrnsitn").val(messages["cmm.all"]);
    }

    /*********************************************************
     * 대리점(관리업체) 조회
     * *******************************************************/
    $scope.searchAgencyTrnsitn = function(){
        if(orgnFg === "MASTER") {
            var popup = $scope.agencyLayer;

            // 팝업 닫을때
            popup.show(true, function (s) {
                var agencyScope = agrid.getScope('searchAgencyCtrl');
                agencyScope.$apply(function () {
                    agencyScope._gridDataInit();
                    if (!$.isEmptyObject(agencyScope.getAgency())) {
                        $("#ssl_srchAgencyCdTrnsitn").val(agencyScope.getAgency().agencyCd);
                        $("#agencyNmTrnsitn").val(agencyScope.getAgency().agencyNm);
                    }
                });
            });
        }
    };

    // 대리점(관리업체) 선택취소
    $scope.delAgencyCdTrnsitn = function(){
        $("#ssl_srchAgencyCdTrnsitn").val("");
        $("#agencyNmTrnsitn").val(messages["cmm.all"]);
    }

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var startDt = wijmo.Globalize.format(startMonth.value, 'yyyy-MM');
        var endDt   = wijmo.Globalize.format(endMonth.value, 'yyyy-MM');

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,  //20220427 false 엑섹속도 cell 스타일 확인중
                includeColumns: function (column) {
                    return column.visible;
                }
            }, '런닝매장현황_런닝매장추이(' + startDt +'_' + endDt + ')'+'_'+ getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);