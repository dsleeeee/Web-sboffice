/****************************************************************
 *
 * 파일명 : smsSendHist.js
 * 설  명 : SMS전송이력 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.08.05     김설아      1.0
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
// 예약여부
var reserveYnDataMapData = [
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
 *  SMS전송이력 조회 그리드 생성
 */
app.controller('smsSendHistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsSendHistCtrl', $scope, $http, false));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

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
                if (col.binding === "smsSendCount") {
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
                    $scope.setSelectedSmsSendHist(selectedRow);
                    $scope.wjMessageDtlLayer.show(true);
                    event.preventDefault();
                }

                // 건수 클릭시 상세정보 조회
                if ( col.binding === "smsSendCount") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    $scope.setSelectedSmsSendHist(selectedRow);
                    $scope.wjAddresseeeDtlLayer.show(true);
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
        dataItem.regDt = messages["smsSendHist.regDt"];
        dataItem.smsSendOrgnCd = messages["smsSendHist.send"];
        dataItem.smsSendOrgnNm = messages["smsSendHist.send"];
        dataItem.smsSendSeq = messages["smsSendHist.smsSendSeq"];
        dataItem.smsSendCount = messages["smsSendHist.smsSendCount"];
        dataItem.msgType = messages["smsSendHist.msgType"];
        dataItem.subject = messages["smsSendHist.subject"];
        dataItem.msgContent = messages["smsSendHist.msgContent"];
        dataItem.sendDate = messages["smsSendHist.sendDate"];
        dataItem.readDate = messages["smsSendHist.readDate"];
        dataItem.reserveYn = messages["smsSendHist.reserveYn"];
        dataItem.sendQty = messages["smsSendHist.sendQty"];
        dataItem.waitQty = messages["smsSendHist.waitQty"];
        dataItem.successQty = messages["smsSendHist.successQty"];
        dataItem.failQty = messages["smsSendHist.failQty"];

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
    $scope.$on("smsSendHistCtrl", function(event, data) {
        $scope.searchSmsSendHist();
        event.preventDefault();
    });

    $scope.searchSmsSendHist = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.storeCds = $("#smsSendHistStoreCd").val();
        params.listScale = $scope.listScale;

        $scope._inquiryMain("/adi/sms/smsSendHist/smsSendHist/getSmsSendHistList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.smsSendHistStoreShow = function () {
        $scope._broadcast('smsSendHistStoreCtrl');
    };

    // 선택
    $scope.selectedSmsSendHist;
    $scope.setSelectedSmsSendHist = function(store) {
        $scope.selectedSmsSendHist = store;
    };
    $scope.getSelectedSmsSendHist = function() {
        return $scope.selectedSmsSendHist;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 메세지 팝업 핸들러 추가
        $scope.wjMessageDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('messageDtlCtrl', $scope.getSelectedSmsSendHist());
            }, 50)
        });

        // 수신자정보 팝업 핸들러 추가
        $scope.wjAddresseeeDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('addresseeDtlCtrl', $scope.getSelectedSmsSendHist());
            }, 50)
        });
    });

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	false,
                    includeColumns      :	function (column) {
                        return column.visible;
                    }
                },
                'SMS전송이력_'+getCurDate()+'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
    // <-- //엑셀다운로드 -->
}]);