/****************************************************************
 *
 * 파일명 : runStoreList.js
 * 설  명 : 런닝매장현황 런닝매장현황탭 JavaScript
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

// 조회일자 일/월구분
var dayGubunComboData = [
    {"name":"일","value":"D"},
    {"name":"월","value":"M"}
];

/**
 *  매출매장현황 그리드 생성
 */
app.controller('runStoreListCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('runStoreListCtrl', $scope, $http, true));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
    // 조회일자
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });


    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    $scope._setComboData("srchShopMigFg", shopMigFgComboMap);       // 신규/전환
    $scope._setComboData("includeFgCombo", includeFgTypeComboData); // 하위대리점 포함여부
    $scope._setComboData("dayGubunCombo", dayGubunComboData);      // 조회일자 일/월구분


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태구분

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 일/월 구분 콤보박스 선택 이벤트
    $scope.setDayGubunCombo = function (s) {
        if(s.selectedValue === "D") {
            $("#spanDay").css("display" , "");
            $("#spanMonth").css("display" , "none");
        } else if(s.selectedValue === "M") {
            $("#spanDay").css("display" , "none");
            $("#spanMonth").css("display" , "");
        }
    };

    // <-- 검색 호출 -->
    $scope.$on("runStoreListCtrl", function(event, data) {
        $scope.searchRunStoreList();
        event.preventDefault();
    });

    // 매출매장현황 그리드 조회
    $scope.searchRunStoreList = function() {
        if (!$scope.valueCheck()) {
            return false;
        }
        
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.dayGubun = $scope.srchDayGubunCombo.selectedValue;
        params.hqOfficeCd = $scope.hqOfficeCd;
        params.hqOfficeNm = $scope.hqOfficeNm
        params.storeCd = $scope.storeCd
        params.storeNm = $scope.storeNm
        params.bizNo = $("#bizNo").val();
        params.srchShopMigFg = $scope.shopMigFg;
        params.chkDt = $scope.isChecked;
        params.orgnFg = orgnFg;
        params.pAgencyCd = pAgencyCd;
        params.manageVanCd = $("#ssl_srchManageVanCdRun").val();
        params.includeFg = $scope.srchIncludeFgCombo.selectedValue;
        $scope.srchDayGubun = $scope.dayGubun;

        if(orgnFg === "MASTER"){
            params.agencyCd = $("#ssl_srchAgencyCdRun").val();
        }else{
            params.agencyCd = orgnCd;
        }

        $scope._inquiryMain("/pos/license/runStore/runStore/getRunStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

// 체크
    $scope.valueCheck = function () {

        var date1 = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var date2 = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));

        var diffDay = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);

        var month1 = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var month2 = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (month2.getTime() - month1.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월
        
        if($scope.dayGubun === 'M' && $scope.dayGubun !==null ) {
            // 시작일자가 종료일자보다 빠른지 확인
            if (month1.getTime() > month2.getTime()) {
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }
            // 조회일자 최대 1년(12개월) 제한
            if (diffMonth > 1) {
                $scope._popMsg(messages['cmm.dateOver.1month.error']);
                return false;
            }
        }else if($scope.dayGubun === 'D' && $scope.dayGubun !==null) {
            // 시작일자가 종료일자보다 빠른지 확인
            if (date1.getTime() > date2.getTime()) {
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }

            if (diffDay > 31) {
                $scope._popMsg(messages['cmm.dateOver.1month.error']);
                return false;
            }
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
        var startDt ='';
        var endDt = ''
        if($scope.srchDayGubun === "D"){
            startDt = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd');
            endDt   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd');
        }else if($scope.srchDayGubun === "M"){
            startDt = wijmo.Globalize.format(startMonth.value, 'yyyy-MM');
            endDt   = wijmo.Globalize.format(endMonth.value, 'yyyy-MM');
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,  //20220427 false 엑섹속도 cell 스타일 확인중
                includeColumns: function (column) {
                    return column.visible;
                }
            }, '런닝매장현황_런닝매장현황(' + startDt +'_' + endDt + ')'+'_'+ getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);