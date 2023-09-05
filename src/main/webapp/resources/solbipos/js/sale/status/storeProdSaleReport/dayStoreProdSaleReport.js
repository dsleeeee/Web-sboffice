/****************************************************************
 *
 * 파일명 : dayStoreProdSaleReport.js
 * 설  명 : 일자별 매장-상품 매출 다운로드 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.09.05     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일자별 매장-상품 매출 다운로드 그리드 생성
 */
app.controller('dayStoreProdSaleReportCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayStoreProdSaleReportCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 자료생성
    var dataCreateDate = wcombo.genDateVal("#dataCreateDate", gvStartDate);

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
                        daySaleReport_download(selectedRow.fileName);
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("dayStoreProdSaleReportCtrl", function(event, data) {
        $scope.searchDayStoreProdSaleReport();
        event.preventDefault();
    });

    $scope.searchDayStoreProdSaleReport = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');

        $scope._inquiryMain("/sale/status/storeProdSaleReport/dayStoreProdSaleReport/getDayStoreProdSaleReportList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 자료생성 -->
    $scope.dataCreate = function(){
        var createDate = wijmo.Globalize.format(dataCreateDate.value, 'yyyyMMdd');

        // 자료생성 요청건 존재여부 확인
        var params = {};
        params.fromSaleDate = createDate;
        params.toSaleDate = createDate;

        $scope._postJSONQuery.withOutPopUp( "/sale/status/storeProdSaleReport/storeProdSaleReport/getStoreProdSaleReportChk.sb", params, function(response){
            var dayStoreProdSaleReport = response.data.data.result;
            $scope.dayStoreProdSaleReport = dayStoreProdSaleReport;

            if($scope.dayStoreProdSaleReport.cnt > 0) {
                var msg = createDate + " " + messages["storeProdSaleReport.saleMonthAlert"]; // 자료가 존재합니다. 삭제 후 진행해주세요.
                $scope._popMsg(msg);
                return;
            } else {
                $scope.save(params);
            }
        });
    };

    $scope.save = function(params){
        // 자료생성을 하시겠습니까?
        $scope._popConfirm(messages["storeProdSaleReport.dataCreateSaveConfirm"], function() {
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/sale/status/storeProdSaleReport/storeProdSaleReport/getStoreProdSaleReportSave.sb", params, function(){
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
            $scope._save("/sale/status/storeProdSaleReport/storeProdSaleReport/getStoreProdSaleReportDel.sb", params, function(){
                $scope.allSearch();
            });
        });
    };
    // <-- //삭제 -->

    // 재조회
    $scope.allSearch = function () {
        $scope.searchDayStoreProdSaleReport();
    };

}]);