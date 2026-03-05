/****************************************************************
 *
 * 파일명 : posLogCollectMgmt.js
 * 설  명 : POS로그수집관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.03.04     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매장상태구분 DropBoxDataMap
var commandTypeComboData = [
    {"name": "전체", "value": ""},
    {"name": "DB_SELECT", "value": "DB_SELECT"},
    {"name": "DB_TABLE", "value": "DB_TABLE"},
    {"name": "LOG_FILE", "value": "LOG_FILE"}
];

// DB백업 포함여부 DropBoxDataMap
var dbBackupComboData = [
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

// 스마트오더 수집여부 DropBoxDataMap
var smartOrderComboData = [
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

// 스마트오더 수집여부 DropBoxDataMap
var sendYnComboData = [
    {"name": "전송", "value": "Y"},
    {"name": "미전송", "value": "N"}
];

/**
 * POS로그수집관리 조회 그리드 생성
 */
app.controller('posLogCollectMgmtCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posLogCollectMgmtCtrl', $scope, $http, false));

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 명령타입 콤보박스 셋팅
    $scope._setComboData("commandTypeComboData", commandTypeComboData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.dbBackupComboDataMap = new wijmo.grid.DataMap(dbBackupComboData, 'value', 'name'); // DB백업 포함여부
        $scope.smartOrderComboDataMap = new wijmo.grid.DataMap(smartOrderComboData, 'value', 'name'); // 스마트오더 수집여부
        $scope.sendYnComboDataMap = new wijmo.grid.DataMap(sendYnComboData, 'value', 'name'); // 열람구분

    };

    $scope.$on("posLogCollectMgmtCtrl", function (event, data) {
        // 조회
        $scope.getSearchPosLogList();
        event.preventDefault();
    });

    // 조회
    $scope.getSearchPosLogList = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1년(365일) 제한
        if (diffDay > 186) {
            $scope._popMsg(messages['cmm.dateOver.6month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd'); //조회기간
        params.commandType = $scope.commandTypeCombo.selectedValue;

        // POS로그수집관리 리스트 조회
        $scope._inquiryMain("/sys/admin/posLogCollectMgmt/posLogCollectMgmt/getSearchPosLogList.sb", params);

    };

    $scope.regPosLog =function () {
        $scope.posLogRegistLayer.show(true, function(){
            // 탭 닫을때 그리드 초기화
            var sScope = agrid.getScope("searchStoreCtrl");
            sScope._gridDataInit();
            // paging 영역 히든
            var vCtrlPager = document.getElementById('searchStoreCtrlPager');
            vCtrlPager.style.visibility='hidden'
            var nScope = agrid.getScope("regPosLogCtrl");
            nScope.reset();

            $("#regHqOfficeCd").val("");
            $("#regHqOfficeNm").val("");
            $("#regStoreCd").val("");
            $("#regStoreNm").val("");
        });
    }

    // 엑셀다운로드
    $scope.excelDownload = function(){

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
                    //  column.visible;
                    return column.binding != 'gChk';
                }
            }, messages["posLogCollectMgmt.posLogCollectMgmt"]  + '_' +  getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };


}]);