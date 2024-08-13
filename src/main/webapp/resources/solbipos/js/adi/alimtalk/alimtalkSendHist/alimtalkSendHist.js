/****************************************************************
 *
 * 파일명 : alimtalkSendHist.js
 * 설  명 : 알림톡 전송이력 JavaScript
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
var msgTypeDataMapData = [
    {"name":"SMS","value":"1"},
    {"name":"LMS","value":"2"},
    {"name":"MMS","value":"3"},
    {"name":"알림톡_SMS","value":"4"},
    {"name":"알림톡_LMS","value":"5"},
    {"name":"알림톡_MMS","value":"6"}
];
// 예약여부
var reserveYnDataMapData = [
    {"name":"전체","value":""},
    {"name":"예약","value":"1"},
    {"name":"즉시","value":"0"}
];
// 조회구분
var gubunComboData = [
    {"name":"전체","value":""},
    {"name":"본사","value":"1"},
    {"name":"매장","value":"2"}
];

/**
 *  알림톡 전송이력 조회 그리드 생성
 */
app.controller('alimtalkSendHistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkSendHistCtrl', $scope, $http, false));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateAlimtalkSendHist", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateAlimtalkSendHist", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("gubunCombo", gubunComboData); // 조회구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.msgTypeDataMap = new wijmo.grid.DataMap(msgTypeDataMapData, 'value', 'name'); // 메세지타입
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

                // 건수
                if (col.binding === "alkSendCount") {
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
                    $scope.setSelectedAlimtalkSendHist(selectedRow);
                    $scope.wjAlimtalkMessageDtlLayer.show(true);
                    event.preventDefault();
                }

                // 건수 클릭시 상세정보 조회
                if ( col.binding === "alkSendCount") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    $scope.setSelectedAlimtalkSendHist(selectedRow);
                    $scope.wjAlimtalkAddresseeeDtlLayer.show(true);
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
        dataItem.regDt = messages["alimtalkSendHist.regDt"];
        dataItem.alkSendOrgnCd = messages["alimtalkSendHist.send"];
        dataItem.alkSendOrgnNm = messages["alimtalkSendHist.send"];
        dataItem.alkSendSeq = messages["alimtalkSendHist.alkSendSeq"];
        dataItem.alkSendCount = messages["alimtalkSendHist.alkSendCount"];
        dataItem.msgType = messages["alimtalkSendHist.msgType"];
        dataItem.subject = messages["alimtalkSendHist.subject"];
        dataItem.msgContent = messages["alimtalkSendHist.msgContent"];
        dataItem.sendDate = messages["alimtalkSendHist.sendDate"];
        dataItem.readDate = messages["alimtalkSendHist.readDate"];
        dataItem.reserveYn = messages["alimtalkSendHist.reserveYn"];
        dataItem.sendQty = messages["alimtalkSendHist.sendQty"];
        dataItem.waitQty = messages["alimtalkSendHist.waitQty"];
        dataItem.successQty = messages["alimtalkSendHist.successQty"];
        dataItem.failQty = messages["alimtalkSendHist.failQty"];

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
    $scope.$on("alimtalkSendHistCtrl", function(event, data) {
        $scope.searchAlimtalkSendHist();
        event.preventDefault();
    });

    $scope.searchAlimtalkSendHist = function() {
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
        params.subject = $scope.subject;
        params.msgContent = $scope.msgContent;
        if(orgnFg == "HQ") {
            params.gubunCombo = $scope.gubunCombo;
        }
        params.storeCds = $("#alimtalkSendHistStoreCd").val();
        params.listScale = $scope.listScaleAlimtalkSendHist;

        $scope._inquiryMain("/adi/alimtalk/alimtalkSendHist/alimtalkSendHist/getAlimtalkSendHistList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택
    $scope.selectedAlimtalkSendHist;
    $scope.setSelectedAlimtalkSendHist = function(store) {
        $scope.selectedAlimtalkSendHist = store;
    };
    $scope.getSelectedAlimtalkSendHist = function() {
        return $scope.selectedAlimtalkSendHist;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 알림톡 메세지 팝업 핸들러 추가
        $scope.wjAlimtalkMessageDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('alimtalkMessageDtlCtrl', $scope.getSelectedAlimtalkSendHist());
            }, 50)
        });

        // 알림톡 수신자정보 팝업 핸들러 추가
        $scope.wjAlimtalkAddresseeeDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('alimtalkAddresseeDtlCtrl', $scope.getSelectedAlimtalkSendHist());
            }, 50)
        });
    });

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.alimtalkSendHistFlex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.ssOrgnCd = $scope.ssOrgnCd;
        params.ssOrgnNm = $scope.ssOrgnNm;
        params.subject = $scope.subject;
        params.msgContent = $scope.msgContent;
        if(orgnFg == "HQ") {
            params.gubunCombo = $scope.gubunCombo;
        }
        params.storeCds = $("#alimtalkSendHistStoreCd").val();

        $scope._broadcast('alimtalkSendHistExcelCtrl', params);
    };
    // <-- //엑셀다운로드 -->

    // 조회구분 선택에 따른 매장선택 조회조건 show/hidden
    $scope.gubunComboChanged = function (s) {
        if (s.selectedValue === "" || s.selectedValue === "1") {
            $("#thSelStore").css("display", "none");
            $("#tdSelStore").css("display", "none");
        }else {
            $("#thSelStore").css("display", "");
            $("#tdSelStore").css("display", "");
        }
    };
}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('alimtalkSendHistExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkSendHistExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.msgTypeDataMap = new wijmo.grid.DataMap(msgTypeDataMapData, 'value', 'name'); // 메세지타입
        $scope.reserveYnDataMap = new wijmo.grid.DataMap(reserveYnDataMapData, 'value', 'name'); // 예약여부

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.regDt = messages["alimtalkSendHist.regDt"];
        dataItem.alkSendOrgnCd = messages["alimtalkSendHist.send"];
        dataItem.alkSendOrgnNm = messages["alimtalkSendHist.send"];
        dataItem.alkSendSeq = messages["alimtalkSendHist.alkSendSeq"];
        dataItem.alkSendCount = messages["alimtalkSendHist.alkSendCount"];
        dataItem.msgType = messages["alimtalkSendHist.msgType"];
        dataItem.subject = messages["alimtalkSendHist.subject"];
        dataItem.msgContent = messages["alimtalkSendHist.msgContent"];
        dataItem.sendDate = messages["alimtalkSendHist.sendDate"];
        dataItem.readDate = messages["alimtalkSendHist.readDate"];
        dataItem.reserveYn = messages["alimtalkSendHist.reserveYn"];
        dataItem.sendQty = messages["alimtalkSendHist.sendQty"];
        dataItem.waitQty = messages["alimtalkSendHist.waitQty"];
        dataItem.successQty = messages["alimtalkSendHist.successQty"];
        dataItem.failQty = messages["alimtalkSendHist.failQty"];

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
    $scope.$on("alimtalkSendHistExcelCtrl", function(event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/adi/alimtalk/alimtalkSendHist/alimtalkSendHist/getAlimtalkSendHistExcelList.sb", params, function() {
            if ($scope.alimtalkSendHistExcelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.alimtalkSendHistExcelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "알림톡 전송이력_" + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
    // <-- //검색 호출 -->
}]);