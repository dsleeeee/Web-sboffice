/****************************************************************
 *
 * 파일명 : mobileStoreTimeSale.js
 * 설  명 : (모바일) 매장매출 > 시간대별 JavaScript
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
 *  요일별 그리드 생성
 */
app.controller('mobileStoreTimeSaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileStoreTimeSaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);

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

                if(item[("storeCd")] == undefined && item[("storeNm")] == undefined && item[("saleHour")] == undefined) {
                    item[("saleHour")] = "총합";
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileStoreTimeSaleCtrl", function(event, data) {

        // 접힌 그리드와 차트 영역 오픈
        gridOpen("mobileStoreTimeSale");

        // 조회
        $scope.searchMobileStoreTimeSale();
        event.preventDefault();
    });

    $scope.searchMobileStoreTimeSale = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.srchStoreCd = $("#mobileStoreTimeSaleStoreCd").val();
        params.array = $(":input:radio[name=array]:checked").val();

        $scope._inquirySub("/mobile/sale/status/storeTimeSale/storeTimeSale/getMobileStoreTimeSaleList.sb", params, function() {
            
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileStoreTimeSale", $scope.flexMobileStoreTimeSale, "Y");
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileStoreTimeSaleStoreShow = function () {
        $scope._broadcast('mobileStoreTimeSaleStoreCtrl');
    };


}]);
