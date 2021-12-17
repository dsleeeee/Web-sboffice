/****************************************************************
 *
 * 파일명 : daySaleReport.js
 * 설  명 : 일별매출내역 다운로드(제너시스올떡 분식대장) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.08     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 생성구분
var procFgData = [
    {"name":"생성요청","value":"0"},
    {"name":"생성중","value":"1"},
    {"name":"생성완료","value":"2"},
    {"name":"오류발생","value":"9"}
];

/**
 *  일별매출내역 다운로드 그리드 생성
 */
app.controller('daySaleReportCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('daySaleReportCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 자료생성
    var dataCreateMonth = new wijmo.input.InputDate('#dataCreateMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.procFgDataMap = new wijmo.grid.DataMap(procFgData, 'value', 'name'); // 생성구분

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

                // 다운로드
                if (col.binding === "download") {
                    var item = s.rows[e.row].dataItem;
                    // 값이 있으면 링크 효과
                    if (nvl(item[("procFg")], '') == '2') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 다운로드 클릭시 상세정보 조회
                if ( col.binding === "download") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    // 값이 있으면 링크
                    if (nvl(selectedRow[("procFg")], '') == '2') {
                        // 다운로드
                        saleReport_download(selectedRow.fileName);
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("daySaleReportCtrl", function(event, data) {
        $scope.searchDaySaleReport();
        event.preventDefault();
    });

    $scope.searchDaySaleReport = function(){
        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');

        $scope._inquiryMain("/sale/status/daySaleReport/daySaleReport/getDaySaleReportList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.daySaleReportStoreShow = function () {
        $scope._broadcast('daySaleReportStoreCtrl');
    };

    // <-- 자료생성 -->
    $scope.dataCreate = function(){
        if ($("#daySaleReportStoreCd").val() == "") {
            s_alert.pop(messages["cmm.require.selectStore"]);
            return;
        }

        // 자료생성 요청건 존재여부 확인
        var params = {};
        params.dataCreateMonth = wijmo.Globalize.format(dataCreateMonth.value, 'yyyyMM');
        params.storeCds = $("#daySaleReportStoreCd").val();

        $scope._postJSONQuery.withOutPopUp( "/sale/status/daySaleReport/daySaleReport/getDaySaleReportChk.sb", params, function(response){
            var daySaleReport = response.data.data.result;
            $scope.daySaleReport = daySaleReport;

            if($scope.daySaleReport.cnt > 0) {
                var month = wijmo.Globalize.format(dataCreateMonth.value, 'yyyyMM');
                var storeCds = $("#daySaleReportStoreCd").val();
                var msg = month + " " + messages["daySaleReport.saleMonthAlert"] + "<br/> (선택된 매장 : " + storeCds + ")"; // 자료가 존재합니다. 삭제 후 진행해주세요.
                $scope._popMsg(msg);
                return;
            } else {
                $scope.save();
            }
        });
    };

    $scope.save = function(){
        // 자료생성을 하시겠습니까?
        $scope._popConfirm(messages["daySaleReport.dataCreateSaveConfirm"], function() {
            // 선택한 매장
            var storeCds = $("#daySaleReportStoreCd").val();
            var arrStoreCol = storeCds.split(',');

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < arrStoreCol.length; i++) {
                var items = {};
                items.dataCreateMonth = wijmo.Globalize.format(dataCreateMonth.value, 'yyyyMM');
                items.storeCd = arrStoreCol[i];

                params.push(items);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/sale/status/daySaleReport/daySaleReport/getDaySaleReportSave.sb", params, function(){
                $scope.allSearch();
            });
        });
    };
    // <-- //자료생성 -->

    // <-- 삭제 -->
    $scope.del = function(){
        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            if(params.length <= 0) {
                s_alert.pop(messages["cmm.not.select"]);
                return;
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/sale/status/daySaleReport/daySaleReport/getDaySaleReportDel.sb", params, function(){
                $scope.allSearch();
            });
        });
    };
    // <-- //삭제 -->

    // 재조회
    $scope.allSearch = function () {
        $scope.searchDaySaleReport();
    };
}]);