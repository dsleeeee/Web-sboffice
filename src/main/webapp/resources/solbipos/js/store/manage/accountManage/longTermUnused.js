/****************************************************************
 *
 * 파일명 : longTermUnused.js
 * 설  명 : 게정관리 장기미사용탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.02.14     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 접속기간
var loginPeriodData = [
    {"name":"전체","value":""},
    {"name":"3개월이상","value":"93"},
    {"name":"6개월이상","value":"186"},
    {"name":"12개월이상","value":"365"}
];

// 계정사용여부
var webUseYnData = [
    {"name":"전체","value":""},
    {"name":"사용","value":"Y"},
    {"name":"중지","value":"N"}
];

// 사용자구분
var orgnFg2Data = [
    {"name":"관리자","value":"A"},
    {"name":"총판","value":"P"},
    {"name":"대리점","value":"C"},
    {"name":"본사","value":"H"},
    {"name":"매장","value":"S"},
    {"name":"단독매장","value":"I"},
    {"name":"본사+매장(단독포함)","value":"H,S,I"}
];

/**
 *  장기미사용탭 그리드 생성
 */
app.controller('longTermUnusedCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('longTermUnusedCtrl', $scope, $http, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("loginPeriod", loginPeriodData);           // 접속기간
    $scope._setComboData("webUseYn", webUseYnData);                 // 계정사용여부    
    $scope._setComboData("orgnFg2", orgnFg2Data);                   // 사용자구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        
        // 그리드 DataMap 설정
        $scope.orgnFg2DataMap = new wijmo.grid.DataMap(orgnFg2Data, 'value', 'name');         // 사용자구분
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');         // 본사, 매장상태구분
        $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFgData, 'value', 'name');     // 재직구분
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');                 // 계정사용여부, 사용여부, 웹사용자사용여부
    };

    // <-- 검색 호출 -->
    $scope.$on("longTermUnusedCtrl", function(event, data) {
        // 장기미사용 계정리스트 조회
        $scope.searchLongTermUnusedList();
        event.preventDefault();
    });

    // 장기미사용 계정리스트 조회
    $scope.searchLongTermUnusedList = function () {

        // 파라미터
        var params      = {};
        params.hqOfficeCd = $scope.hqOfficeCd;
        params.hqOfficeNm = $scope.hqOfficeNm;
        params.storeCd = $scope.storeCd;
        params.storeNm = $scope.storeNm;
        params.userId = $scope.userId;
        params.userNm = $scope.userNm;
        params.loginPeriod = $scope.loginPeriodCombo.selectedValue;
        params.webUseYn = $scope.webUseYnCombo.selectedValue;
        params.orgnFg2 = $scope.orgnFg2Combo.selectedValue;
        params.listScale = $scope.listScale;

        $scope._inquiryMain("/store/manage/acountManage/getLongTermUnusedList.sb", params);

    };

    // 계정 상태 변경
    $scope.accountStatChg = function(statChgFg){

        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }

        // 저장하시겠습니까?
        $scope._popConfirm(messages["cmm.choo.save"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].statChgFg = statChgFg; // 계정 상태 변경 구분
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            if(params.length <= 0) {
                // 선택된 데이터가 없습니다.
                $scope._popMsg(messages["cmm.not.select"]);
                return false;
            }

            // 계정 상태 변경
            $scope._postJSONSave.withPopUp("/store/manage/acountManage/saveAccountStatChg.sb", params, function(){

                //장기미사용 계정리스트 재조회
                $scope.searchLongTermUnusedList();
            });
        });
    };

    // 엑셀다운로드
    $scope.excelDownload = function(){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var vScope = agrid.getScope('longTermUnusedExcelCtrl');

        // 파라미터
        var params      = {};
        params.hqOfficeCd = $scope.hqOfficeCd;
        params.hqOfficeNm = $scope.hqOfficeNm;
        params.storeCd = $scope.storeCd;
        params.storeNm = $scope.storeNm;
        params.userId = $scope.userId;
        params.userNm = $scope.userNm;
        params.loginPeriod = $scope.loginPeriodCombo.selectedValue;
        params.webUseYn = $scope.webUseYnCombo.selectedValue;
        params.orgnFg2 = $scope.orgnFg2Combo.selectedValue;

        vScope.excelDownload(params);
    }

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('longTermUnusedExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('longTermUnusedExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.orgnFg2DataMap = new wijmo.grid.DataMap(orgnFg2Data, 'value', 'name');         // 사용자구분
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');         // 본사, 매장상태구분
        $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFgData, 'value', 'name');     // 재직구분
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');                 // 계정사용여부, 사용여부, 웹사용자사용여부
    };

    //
    $scope.$on("longTermUnusedExcelCtrl", function (event, data) {

    });

    // 엑셀다운로드
    $scope.excelDownload = function(params){

        $scope._inquiryMain("/store/manage/acountManage/getLongTermUnusedExcelList.sb", params, function (){

            if ($scope.flex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
            $timeout(function()	{
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelflex,
                    {
                        includeColumnHeaders: 	true,
                        includeCellStyles	: 	true,
                        includeColumns      :	function (column) {
                            return column.visible;
                        }
                    },
                    messages["accountManage.longTermUnused"]  + '_' +  getCurDateTime() + '.xlsx',
                    function () {
                        $timeout(function () {
                            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                        }, 10);
                    }
                );
            }, 10);
        });
    }

}]);