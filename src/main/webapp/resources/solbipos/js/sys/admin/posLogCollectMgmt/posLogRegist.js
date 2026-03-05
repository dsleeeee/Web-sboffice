/****************************************************************
 *
 * 파일명 : posLogRegist.js
 * 설  명 : POS로그수집등록 팝업 JavaScript
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
var regCommandTypeComboData = [
    {"name": "DB_SELECT", "value": "DB_SELECT"},
    {"name": "DB_TABLE", "value": "DB_TABLE"},
    {"name": "LOG_FILE", "value": "LOG_FILE"}
];

var regLogTypeComboData = [
    {"name": "선택없음", "value": ""},
    {"name": "FULL", "value": "FULL"},
    {"name": "선택", "value": "SELECT"}
];

var regDbBackupComboData = [
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

var regSmartOrderComboData = [
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];
app.controller('searchStoreCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchStoreCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("searchStoreCtrl", function(event, data) {
        $scope.searchStoreList();
        event.preventDefault();
    });

    // 매장 정보 조회
    $scope.searchStoreList = function(){

        var params        = {};
        params.listScale = 500;
        $scope._inquiryMain("/sys/admin/posLogCollectMgmt/posLogCollectMgmt/getSearchStoreList.sb", params, function () {
            // paging 영역 보이도록
            var vCtrlPager = document.getElementById('searchStoreCtrlPager');
            vCtrlPager.style.visibility='visible'
        });
    };


}]);

app.controller('regPosLogCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('regPosLogCtrl', $scope, $http, true));

    // 콤보박스 셋팅
    $scope._setComboData("regCommandTypeComboData", regCommandTypeComboData); // 명령타입
    $scope._setComboData("regLogTypeComboData", regLogTypeComboData); // 로그타입
    $scope._setComboData("regDbBackupComboData", regDbBackupComboData); // DB백업 포함여부
    $scope._setComboData("regSmartOrderComboData", regSmartOrderComboData); // 스마트오더 사용여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("regPosLogCtrl", function(event, data) {
    });

    // 초기화
    $scope.reset = function () {
        $scope.sql = '';
        $scope.vcatPath = '';
        $scope.remark = '';
        $scope.regCommandTypeCombo.selectedIndex = 0;
        $scope.regLogTypeCombo.selectedIndex = 0;
        $scope.regDbBackupCombo.selectedIndex = 0;
        $scope.regSmartOrderCombo.selectedIndex = 0;
        var todayStr = getToday();
        $scope.dateFromCombo.value = todayStr.substring(0,4) + '-' + todayStr.substring(4,6) + '-' + todayStr.substring(6,8);
        $scope.dateToCombo.value = todayStr.substring(0,4) + '-' + todayStr.substring(4,6) + '-' + todayStr.substring(6,8);
        $('input[name="tableLogTypeFg"]').prop('checked', false);
        $('input[name="logFileTypeFg"]').prop('checked', false);
    };

    // 명령타입 변경 시
    $scope.changeCommandType = function(s) {
        var filteredData = [];

        if (s.selectedValue === "DB_SELECT") {
            // 선택없음만
            filteredData = regLogTypeComboData.filter(function (item) {
                return item.value === "";
            });

            $scope.showSqlDiv = true;
            $scope.showTableLogTypeFg = false;
            $scope.showLogFileTypeFg = false;

        } else if (s.selectedValue === "DB_TABLE") {
            // FULL + 선택
            filteredData = regLogTypeComboData.filter(function (item) {
                return item.value === "FULL" || item.value === "SELECT";
            });

            $scope.showSqlDiv = false;
            $scope.showTableLogTypeFg = false;
            $scope.showLogFileTypeFg = false;

        } else if (s.selectedValue === "LOG_FILE") {
            // 선택만
            filteredData = regLogTypeComboData.filter(function (item) {
                return item.value === "SELECT";
            });
            $scope.showSqlDiv = false;
            $scope.showTableLogTypeFg = false;
            $scope.showLogFileTypeFg = true;
        }

        // 콤보 데이터 다시 세팅
        $scope._setComboData("regLogTypeComboData", filteredData);

        // 선택값 초기화 (안전하게)
        $scope.regLogType = "";
        $scope.sql = '';
        $scope.vcat = '';
        $scope.remark = '';
    };

    // 로그타입 변경 시
    $scope.changeLogType = function(s) {

        if($scope.regCommandTypeCombo.selectedValue === "DB_TABLE" && s.selectedValue === "SELECT"){
                $scope.showTableLogTypeFg = true;
        } else{
            $scope.showTableLogTypeFg = false;
        }

    };

    // 등록
    $scope.savePosLog = function(){

        $scope.stepCnt = 50;    // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        var logType = '';

        // 명령타입이 DB_TABLE/LOG_FILE일 때 로그타입 체크여부 확인
        if($scope.regCommandTypeCombo.selectedValue === "DB_TABLE"){
            if($scope.regLogTypeCombo.selectedValue === "SELECT") {
                if ($('input[name="tableLogTypeFg"]:checked').length <= 0) {
                    $scope._popMsg(messages["posLogCollectMgmt.msg.tableLogTypeSelect"]); // OD,SL,RV 선택하여주십시오
                    return false;
                } else {
                    logType = $('input[name="tableLogTypeFg"]:checked').map(function () {
                        return this.value;
                    }).get().join(",");
                }
            }else{
                logType = $scope.regLogTypeCombo.selectedValue;
            }
        }else if($scope.regCommandTypeCombo.selectedValue === "LOG_FILE"){
            if ($('input[name="logFileTypeFg"]:checked').length <= 0) {
                $scope._popMsg(messages["posLogCollectMgmt.msg.logFileTypeFgSelect"]); // POS,VCAT,PaycoVCAT,PaycoVMEM,PaycoVORDER 선택하여주십시오
                return false;
            }else{
                logType = $('input[name="logFileTypeFg"]:checked').map(function () {return this.value;}).get().join(",");
            }
        }else if($scope.regCommandTypeCombo.selectedValue === "DB_SELECT"){
            if (!$scope.sql || !$scope.sql.trim()) {
                $scope._popMsg(messages["posLogCollectMgmt.msg.sql"]); //SQL을 작성하여 주십시오.
                return false;
            }
        }

        if(!$scope.remark || !$scope.remark.trim()){
            $scope._popMsg(messages["posLogCollectMgmt.msg.remark"]); // 사유 작성하여 주십시오.
            return false;
        }

        var scope = agrid.getScope('searchStoreCtrl');

        if (scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }

        var params = [];
        for (var i = 0; i < scope.flex.collectionView.items.length; i++) {
            var item = scope.flex.collectionView.items[i];
            if(item.gChk) {
                item.commandType        = $scope.regCommandTypeCombo.selectedValue;
                item.logType            = logType;
                item.dateFrom           = wijmo.Globalize.format($scope.dateFromCombo.value, 'yyyyMMdd');
                item.dateTo             = wijmo.Globalize.format($scope.dateToCombo.value, 'yyyyMMdd');
                item.sql                = $scope.sql;
                item.includeDbBackup    = $scope.regDbBackupCombo.selectedValue;
                item.smartOrderYn       = $scope.regSmartOrderCombo.selectedValue;
                item.vcatPath           = $scope.vcatPath;
                item.remark             = $scope.remark;
                params.push(item);
            }
        }

        if (params.length <= 0) {
            $scope._popMsg(messages["cmm.not.select"]);
            return false;
        }else {
            // 50개 이상 저장 시 SMS전송
            if(params.length > 50) {
                var smsParams = {};
                smsParams.subject = "[대량적용알림]POS로그수집등록";
                smsParams.smsMsg = "POS로그수집등록을 시작하였습니다. 사용자 : " + userId + ", 적용수 : " + params.length;
                smsParams.msgFg = "S01_0001"; // [전송구분] S01_0001:테스트용, S01_0002:터치키/키오스크 매장적용 시
                smsParams.nmcodeItem1 = "터치키/키오스크 매장적용";
                $scope._postJSONSave.withOutPopUp("/base/prod/touchKey/touchKey/notiSmsSend.sb", smsParams, function () {
                });
            }
            $scope.save2(params);
        }
    };

    $scope.save2 = function (orgParams) {

        $scope.totalRows = orgParams.length;    // 체크수

        var params = [];

        // 저장 시작이면 작업내역 로딩 팝업 오픈
        if ($scope.progressCnt === 0) {
            $timeout(function () {
                $scope.excelUploadingPopup(true);
                $("#progressCnt").html($scope.progressCnt);
                $("#totalRows").html($scope.totalRows);
            }, 10);
        }

        // stepCnt 만큼 데이터 DB에 저장
        var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
        for (var i = $scope.progressCnt; i < loopCnt; i++) {
            params.push(orgParams[i]);
        }

        console.log("총 갯수 :" + $scope.totalRows);
        console.log("진행 갯수 :" + $scope.progressCnt + "~" + (loopCnt - 1));
        console.log("---------------------------------------------------------------------");

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/sys/admin/posLogCollectMgmt/posLogRegist/savePosLog.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 작업내역 로딩 팝업 닫기
                    $scope.excelUploadingPopup(false);
                    $scope._gridDataInit();
                    // 저장되었습니다.
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    // 검증결과 조회
                    var scope = agrid.getScope('searchStoreCtrl');
                    scope.searchStoreList();
                    // 재조회
                    var scope2 = agrid.getScope('posLogCollectMgmtCtrl');
                    scope2.getSearchPosLogList();

                    // 입력 칸 초기화
                    $scope.reset();
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
            if (parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
                // 처리된 숫자 변경
                $scope.progressCnt = loopCnt;
                // 팝업의 progressCnt 값 변경
                $("#progressCnt").html($scope.progressCnt);
                $scope.save2(orgParams);
            }
        });
    };

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['cmm.progress'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 진행 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            // html 적용
            $scope._loadingPopup.content.innerHTML = innerHtml;
            // 팝업 show
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
    };

}]);