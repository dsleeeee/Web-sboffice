/****************************************************************
 *
 * 파일명 : alimtalkSendStatus.js
 * 설  명 : 알림톡 전송결과 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.30     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 메세지타입
var alkMsgTypeDataMapData = [
    {"name":"SMS","value":"1"},
    {"name":"LMS","value":"2"},
    {"name":"MMS","value":"3"},
    {"name":"알림톡_SMS","value":"4"},
    {"name":"알림톡_LMS","value":"5"},
    {"name":"알림톡_MMS","value":"6"},
    {"name":"알림톡","value":"-4"},
    {"name":"알림톡_대체발송","value":"-5"}
];
// 결과
var sendStatusFgData = [
    {"name":"전체","value":""},
    {"name":"발송대기","value":"0"},
    {"name":"발송완료","value":"3"},
    {"name":"발송실패","value":"-1"}
];
// 예약여부
var reserveYnDataMapData = [
    {"name":"전체","value":""},
    {"name":"예약","value":"1"},
    {"name":"즉시","value":"0"}
];
// 대체발송 메세지타입
var rmTypeDataMapData = [
    {"name":"SMS","value":"1"},
    {"name":"LMS","value":"2"},
    {"name":"MMS","value":"3"}
];

/**
 *  알림톡 전송결과 조회 그리드 생성
 */
app.controller('alimtalkSendStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkSendStatusCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("reserveYnCombo", reserveYnDataMapData); // 예약여부
    $scope._setComboData("sendStatusCombo", sendStatusFgData); // 결과

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.msgTypeDataMap = new wijmo.grid.DataMap(alkMsgTypeDataMapData, 'value', 'name'); // 메세지타입
        $scope.sendStatusFgDataMap = new wijmo.grid.DataMap(sendStatusFgData, 'value', 'name'); // 결과
        $scope.rmTypeDataMap = new wijmo.grid.DataMap(rmTypeDataMapData, 'value', 'name'); // 대체발송 메세지타입
        $scope.rmSendYnDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); // 대체발송 사용여부
        $scope.reserveYnDataMap = new wijmo.grid.DataMap(reserveYnDataMapData, 'value', 'name'); // 예약여부

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 메세지
                if (col.binding === "msgContent") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }

                // 대체발송 메세지
                if (col.binding === "rmContent") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 메세지 클릭시 상세정보 조회
                if ( col.binding === "msgContent") {
                    var selectedRow = s.rows[ht.row].dataItem;

                    var storeScope = agrid.getScope('alimtalkSendHistCtrl');
                    storeScope.setSelectedAlimtalkSendHist(selectedRow);
                    storeScope.wjAlimtalkMessageDtlLayer.show(true);
                    event.preventDefault();
                }

                // 대체발송 메세지 클릭시 상세정보 조회
                if ( col.binding === "rmContent") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    selectedRow.subject = selectedRow.rmTitle;
                    selectedRow.msgContent = selectedRow.rmContent;

                    var storeScope = agrid.getScope('alimtalkSendHistCtrl');
                    storeScope.setSelectedAlimtalkSendHist(selectedRow);
                    storeScope.wjAlimtalkMessageDtlLayer.show(true);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkSendStatusCtrl", function(event, data) {
        $scope.searchAlimtalkSendStatus();
        event.preventDefault();
    });

    $scope.searchAlimtalkSendStatus = function() {
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 6달(186일) 제한
        if (diffDay > 186) {
            $scope._popMsg(messages['cmm.dateOver.6month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.ssOrgnCd = $scope.ssOrgnCd;
        params.ssOrgnNm = $scope.ssOrgnNm;
        params.rrOrgnCd = $scope.rrOrgnCd;
        params.rrOrgnNm = $scope.rrOrgnNm;
        params.reserveYn = $scope.reserveYn;
        params.sendStatus = $scope.sendStatus;
        params.listScale = 2000;

        $scope._inquiryMain("/adi/alimtalk/alimtalkSendStatus/alimtalkSendStatus/getAlimtalkSendStatusList.sb", params, function() {

            var grid = wijmo.Control.getControl("#wjGridAlimtalSendStatus");
            var rows = grid.rows;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                if(item.reserveYn != "1"
                    || item.sendStatus == "3"
                    || item.sendStatus == "-1"
                    || (item.sendDate != "" && parseInt(item.sendDate.substring(0, 16)) <= parseInt(getCurDateTime())) ) {

                    item.gChk = false;
                    rows[i].isReadOnly = true;
                }
            }

        }, false);
    };
    // <-- //검색 호출 -->

    // 예약취소
    $scope.reserveCancel = function() {
        // 파라미터 설정
        var paramsChk = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                paramsChk.push($scope.flex.collectionView.items[i]);
            }
        }

        if(paramsChk.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                if($scope.flex.collectionView.items[i].reserveYn != "1"
                    || $scope.flex.collectionView.items[i].sendStatus == "3"
                    || $scope.flex.collectionView.items[i].sendStatus == "-1"
                    || ($scope.flex.collectionView.items[i].sendDate != "" && parseInt($scope.flex.collectionView.items[i].sendDate.substring(0, 16)) <= parseInt(getCurDateTime())) ) {

                    $scope._popMsg(messages["alimtalkSendStatus.reserveCancelAlert"]); // 예약 문자가 아니거나 이미 전송된 문자입니다.
                    return false;
                }
                $scope.flex.collectionView.items[i].orgnCd = orgnCd;
                $scope.flex.collectionView.items[i].regId = userId;
                $scope.flex.collectionView.items[i].modId = userId;
                $scope.flex.collectionView.items[i].orgnFg = orgnFg;
                $scope.flex.collectionView.items[i].hqOfficeCd = hqOfficeCd;
                $scope.flex.collectionView.items[i].storeCd = storeCd;
                $scope.flex.collectionView.items[i].groupSenderKey = groupSenderKey;
                $scope.flex.collectionView.items[i].groupSenderKeyNm = groupSenderKeyNm;
                $scope.flex.collectionView.items[i].appKey = appKey;
                $scope.flex.collectionView.items[i].secretKey = secretKey;
                $scope.flex.collectionView.items[i].apiUrl = apiUrl;

                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 예약 문자를 취소하시겠습니까?
        if (confirm(messages["alimtalkSendStatus.reserveCancelConfirm"])) {
            // 예약취소 (메세지발송 취소 API 호출 및 저장)
            $scope.registerRequestApi(params);
        }
    };

    // 예약취소 (메세지발송 취소 API 호출 및 저장)
    $scope.registerRequestApi = function(params) {
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

        $.ajax({
            type: "POST",
            url: "/adi/alimtalk/alimtalkSendStatus/alimtalkSendStatus/getAlimtalkReserveCancelApiSave.sb",
            data: JSON.stringify(params),
            cache: false,
            dataType: "json",
            contentType : 'application/json',
            success: function(result){
                // alert(result.status);
                // alert(result.data);
                // alert(result.data.resultCode);
                // alert(result.data.resultMessage);
                if (result.data.resultCode.toString() === "0") {
                    $scope._popMsg("예약취소 되었습니다.");
                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');
                    // 조회
                    $scope.searchAlimtalkSendStatus();
                }
                else if (result.data.resultCode.toString() !== "0") {
                    $scope._popMsg(result.data.resultMessage.toString());
                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');
                }
                // if (result.status === "OK") {
                //     $scope._popMsg("저장되었습니다.");
                //     $scope.close();
                // }
                else if (result.status === "FAIL") {
                    $scope._popMsg('Ajax Fail By HTTP Request');
                    $scope.$broadcast('loadingPopupInactive');
                }
                else if (result.status === "SERVER_ERROR") {
                    $scope._popMsg(result.message);
                    $scope.$broadcast('loadingPopupInactive');
                }
                /*else if(result.status === undefined) {
                    location.href = "/";
                }*/
                else {
                    var msg = result.status + " : " + result.message;
                    $scope._popMsg(msg);
                    $scope.$broadcast('loadingPopupInactive');
                }
            }
        });
    };

    // 선택
    // $scope.selectedAlimtalkSendStatus;
    // $scope.setSelectedAlimtalkSendStatus = function(store) {
    //     $scope.selectedAlimtalkSendStatus = store;
    // };
    // $scope.getSelectedAlimtalkSendStatus = function() {
    //     return $scope.selectedAlimtalkSendStatus;
    // };

    // 화면 ready 된 후 설정
    // angular.element(document).ready(function () {
    //
    //     // 알림톡 메세지 팝업 핸들러 추가
    //     $scope.wjAlimtalkMessageDtlLayer.shown.addHandler(function (s) {
    //         setTimeout(function() {
    //             $scope._broadcast('alimtalkMessageDtlCtrl', $scope.getSelectedAlimtalkSendStatus());
    //         }, 50)
    //     });
    // });

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.ssPhoneNumber = $scope.ssPhoneNumber;
        params.ssOrgnCd = $scope.ssOrgnCd;
        params.ssOrgnNm = $scope.ssOrgnNm;
        params.rrOrgnCd = $scope.rrOrgnCd;
        params.rrOrgnNm = $scope.rrOrgnNm;
        params.reserveYn = $scope.reserveYn;
        params.sendStatus = $scope.sendStatus;

        $scope._broadcast('alimtalkSendStatusExcelCtrl', params);
    };
    // <-- //엑셀다운로드 -->
}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('alimtalkSendStatusExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkSendStatusExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.msgTypeDataMap = new wijmo.grid.DataMap(alkMsgTypeDataMapData, 'value', 'name'); // 메세지타입
        $scope.sendStatusFgDataMap = new wijmo.grid.DataMap(sendStatusFgData, 'value', 'name'); // 결과
        $scope.rmTypeDataMap = new wijmo.grid.DataMap(rmTypeDataMapData, 'value', 'name'); // 대체발송 메세지타입
        $scope.rmSendYnDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); // 대체발송 사용여부
        $scope.reserveYnDataMap = new wijmo.grid.DataMap(reserveYnDataMapData, 'value', 'name'); // 예약여부

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 메세지
                if (col.binding === "msgContent") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }

                // 대체발송 메세지
                if (col.binding === "rmContent") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkSendStatusExcelCtrl", function(event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/adi/alimtalk/alimtalkSendStatus/alimtalkSendStatus/getAlimtalkSendStatusExcelList.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "알림톡 전송결과_" + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
    // <-- //검색 호출 -->
}]);