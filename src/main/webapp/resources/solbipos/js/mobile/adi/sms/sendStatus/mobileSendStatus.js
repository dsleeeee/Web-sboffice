/****************************************************************
 *
 * 파일명 : mobileSendStatus.js
 * 설  명 : (모바일) 부가서비스 > 문자전송현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.01.04     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 메세지타입
var msgTypeDataMapData = [
    {"name":"SMS","value":"1"},
    {"name":"LMS","value":"2"},
    {"name":"MMS","value":"3"}
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

/**
 *  문자전송현황 조회 그리드 생성
 */
app.controller('mobileSendStatusCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileSendStatusCtrl', $scope, $http, false));

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
        $scope.msgTypeDataMap = new wijmo.grid.DataMap(msgTypeDataMapData, 'value', 'name'); // 메세지타입
        $scope.sendStatusFgDataMap = new wijmo.grid.DataMap(sendStatusFgData, 'value', 'name'); // 결과

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
                    $scope.setSelectedSendStatus(selectedRow);
                    $scope.wjMobileMessageDtlLayer.show(true);
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
        dataItem.gChk = messages["mobile.cmm.chk"];
        dataItem.regDt = messages["mobile.sendStatus.regDt"];
        dataItem.sOgnNm = messages["mobile.sendStatus.send"];
        dataItem.sUserNm = messages["mobile.sendStatus.send"];
        dataItem.sPhoneNumber = messages["mobile.sendStatus.send"];
        dataItem.rOgnNm = messages["mobile.sendStatus.receive"];
        dataItem.rPhoneNumber = messages["mobile.sendStatus.receive"];
        dataItem.msgType = messages["mobile.sendStatus.msgType"];
        dataItem.sendDate = messages["mobile.sendStatus.sendDate"];
        dataItem.readDate = messages["mobile.sendStatus.readDate"];
        dataItem.sendStatus = messages["mobile.sendStatus.sendStatus"];
        dataItem.resultNm = messages["mobile.sendStatus.resultNm"];
        dataItem.company = messages["mobile.sendStatus.company"];
        dataItem.msgContent = messages["mobile.sendStatus.msgContent"];

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
    $scope.$on("mobileSendStatusCtrl", function(event, data) {
        $scope.searchMobileSendStatus();
        event.preventDefault();
    });

    $scope.searchMobileSendStatus = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.listScale = $scope.listScale;
        params.reserveYn = $scope.reserveYnCombo.selectedValue;

        $scope._inquirySub("/adi/sms/sendStatus/sendStatus/getSendStatusList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileSendStatus", $scope.flexMobileSendStatus, "N");
        }, false);
    };
    // <-- //검색 호출 -->

    // 예약취소
    $scope.reserveCancel = function() {
        // 파라미터 설정
        var paramsChk = new Array();
        for (var i = 0; i < $scope.flexMobileSendStatus.collectionView.items.length; i++) {
            if($scope.flexMobileSendStatus.collectionView.items[i].gChk) {
                paramsChk.push($scope.flexMobileSendStatus.collectionView.items[i]);
            }
        }

        if(paramsChk.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flexMobileSendStatus.collectionView.items.length; i++) {
            if($scope.flexMobileSendStatus.collectionView.items[i].gChk) {
                if($scope.flexMobileSendStatus.collectionView.items[i].reserveYn != "1"
                    || $scope.flexMobileSendStatus.collectionView.items[i].sendStatus == "3"
                    || ($scope.flexMobileSendStatus.collectionView.items[i].sendDate != "" && parseInt($scope.flexMobileSendStatus.collectionView.items[i].sendDate.substring(0, 8)) >= parseInt(getCurDateTime())) ) {

                    $scope._popMsg(messages["mobile.sendStatus.reserveCancelAlert"]); // 예약 문자가 아니거나 이미 전송된 문자입니다.
                    return false;
                }

                params.push($scope.flexMobileSendStatus.collectionView.items[i]);
            }
        }

        // 예약 문자를 취소하시겠습니까?
        if (confirm(messages["mobile.sendStatus.reserveCancelConfirm"])) {
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/sendStatus/sendStatus/getSendStatusReserveCancelSave.sb", params, function(){ $scope.searchSendStatus() });
        }
    };

    // SMS전송
    // $scope.smsSendPop = function () {
    //     $scope.wjSmsSendLayer.show(true);
    //     event.preventDefault();
    // };

    // 선택
    $scope.selectedSendStatus;
    $scope.setSelectedSendStatus = function(store) {
        $scope.selectedSendStatus = store;
    };
    $scope.getSelectedSendStatus = function() {
        return $scope.selectedSendStatus;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 메세지 팝업 핸들러 추가
        $scope.wjMobileMessageDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('mobileMessageDtlCtrl', $scope.getSelectedSendStatus());
            }, 50)
        });

        // SMS전송 팝업 핸들러 추가
        // $scope.wjSmsSendLayer.shown.addHandler(function (s) {
        //     setTimeout(function() {
        //         $scope._broadcast('smsSendCtrl', null);
        //     }, 50)
        // });
    });
}]);