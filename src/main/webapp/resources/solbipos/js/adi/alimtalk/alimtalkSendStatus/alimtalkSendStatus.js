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
app.controller('alimtalkSendStatusCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkSendStatusCtrl', $scope, $http, false));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("reserveYnCombo", reserveYnDataMapData); // 예약여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.msgTypeDataMap = new wijmo.grid.DataMap(alkMsgTypeDataMapData, 'value', 'name'); // 메세지타입
        $scope.sendStatusFgDataMap = new wijmo.grid.DataMap(sendStatusFgData, 'value', 'name'); // 결과
        $scope.rmTypeDataMap = new wijmo.grid.DataMap(rmTypeDataMapData, 'value', 'name'); // 대체발송 메세지타입
        $scope.rmSendYnDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); // 대체발송 사용여부

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 메세지
                if (col.binding === "msgContent") {
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
                    $scope.setSelectedAlimtalkSendStatus(selectedRow);
                    $scope.wjAlimtalkMessageDtlLayer.show(true);
                    event.preventDefault();
                }
            }
        });

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.gChk = messages["cmm.chk"];
        dataItem.regDt = messages["alimtalkSendStatus.regDt"];
        dataItem.sOgnNm = messages["alimtalkSendStatus.send"];
        dataItem.sUserNm = messages["alimtalkSendStatus.send"];
        dataItem.rOgnNm = messages["alimtalkSendStatus.receive"];
        dataItem.rPhoneNumber = messages["alimtalkSendStatus.receive"];
        dataItem.msgType = messages["alimtalkSendStatus.msgType"];
        dataItem.sendDate = messages["alimtalkSendStatus.sendDate"];
        dataItem.readDate = messages["alimtalkSendStatus.readDate"];
        dataItem.sendStatus = messages["alimtalkSendStatus.sendStatus"];
        dataItem.resultNm = messages["alimtalkSendStatus.resultNm"];
        dataItem.msgContent = messages["alimtalkSendStatus.msgContent"];
        dataItem.sendOrgnNm = messages["alimtalkSendStatus.sendOrgnNm"];
        dataItem.payOrgnNm = messages["alimtalkSendStatus.payOrgnNm"];
        dataItem.senderKeyOrgnNm = messages["alimtalkSendStatus.senderKeyOrgnNm"];
        dataItem.rmSendYn = messages["alimtalkSendStatus.rm"];
        dataItem.rmType = messages["alimtalkSendStatus.rm"];
        dataItem.rmTitle = messages["alimtalkSendStatus.rm"];
        dataItem.rmContent = messages["alimtalkSendStatus.rm"];
        dataItem.rmSendNo = messages["alimtalkSendStatus.rm"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
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
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }
        // <-- //그리드 헤더2줄 -->
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkSendStatusCtrl", function(event, data) {
        $scope.searchAlimtalkSendStatus();
        event.preventDefault();
    });

    $scope.searchAlimtalkSendStatus = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.listScale = $scope.listScale;

        $scope._inquiryMain("/adi/alimtalk/alimtalkSendStatus/alimtalkSendStatus/getAlimtalkSendStatusList.sb", params, function() {}, false);
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
                    || ($scope.flex.collectionView.items[i].sendDate != "" && parseInt($scope.flex.collectionView.items[i].sendDate.substring(0, 8)) >= parseInt(getCurDateTime())) ) {

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
    $scope.selectedAlimtalkSendStatus;
    $scope.setSelectedAlimtalkSendStatus = function(store) {
        $scope.selectedAlimtalkSendStatus = store;
    };
    $scope.getSelectedAlimtalkSendStatus = function() {
        return $scope.selectedAlimtalkSendStatus;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 알림톡 메세지 팝업 핸들러 추가
        $scope.wjAlimtalkMessageDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('alimtalkMessageDtlCtrl', $scope.getSelectedAlimtalkSendStatus());
            }, 50)
        });
    });
}]);