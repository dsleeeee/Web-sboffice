/****************************************************************
 *
 * 파일명 : mobileStoreSale.js
 * 설  명 : (모바일) 매장매출 > 매장종합 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.27     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  승인현황 그리드 생성
 */
app.controller('mobileStoreSaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileStoreSaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
                var item = s.rows[e.row].dataItem;
                if (col.binding === "storeNm") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);

            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "storeNm") { // 매장명 클릭
                    var params    = {};
                    params.srchStoreCd = selectedRow.storeCd;
                    params.srchStoreNm = selectedRow.storeNm;
                    var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
                    var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
                    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

                    // 시작일자가 종료일자보다 빠른지 확인
                    if(startDt.getTime() > endDt.getTime()){
                        s_alert.pop(messages['mobile.cmm.dateChk.error']);
                        return false;
                    }
                    // 조회일자 최대 한달(31일) 제한
                    if (diffDay > 31) {
                        s_alert.pop(messages['mobile.cmm.dateOver.1month.error']);
                        return false;
                    }

                    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
                    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간

                    $scope.wjStoreSaleDtlLayer.show(true);
                    $scope._broadcast('storeSaleDtlCtrl', params);
                }

            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileStoreSaleCtrl", function(event, data) {

        // 접힌 그리드와 차트 영역 오픈
        gridOpen("mobileStoreSale");

        // 조회
        $scope.searchMobileStoreSale();
        event.preventDefault();
    });

    $scope.searchMobileStoreSale = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            s_alert.pop(messages['mobile.cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 한달(31일) 제한
        if (diffDay > 31) {
            s_alert.pop(messages['mobile.cmm.dateOver.1month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.srchStoreCd = $("#mobileStoreSaleStoreCd").val();

        var diffTime = parseInt($scope.endTime) - parseInt($scope.startTime) + 1;
        params.diffTime = diffTime; // 조회시간 차이(차트 높이 때문에)

        $scope._inquirySub("/mobile/sale/status/storeSale/storeSale/getMobileStoreSaleList.sb", params, function() {
            
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileStoreSale", $scope.flexMobileStoreSale, "Y");
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileStoreSaleStoreShow = function () {
        $scope._broadcast('mobileStoreSaleStoreCtrl');
    };


}]);
