/****************************************************************
 *
 * 파일명 : mobileTimeDaySale.js
 * 설  명 : (모바일) 매출현황 > 시간대별(일자별)매출현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.07     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 시 VALUE
var Hh = [24];
for(i =0 ; i < 24; i++){
    var timeVal = i.toString();
    if(i>=0 && i<=9){
        timeVal = "0" + timeVal;
    }
    Hh[i] = {"name":timeVal,"value":timeVal}
}

/**
 *  시간대별 그리드 생성
 */
app.controller('mobileTimeDaySaleCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTimeDaySaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 검색조건에 시간대
    $scope._setComboData("startTimeCombo", Hh);
    $scope._setComboData("endTimeCombo", Hh);
    $scope.startTime     = "0";
    $scope.endTime       = "23";

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 조회
        // $scope.searchMobileTimeDaySale();
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTimeDaySaleCtrl", function(event, data) {
        gridOpen("mobileTimeDaySaleTime");

        $scope.searchMobileTimeDaySale();
        event.preventDefault();
    });

    $scope.searchMobileTimeDaySale = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['mobile.cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 한달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['mobile.cmm.dateOver.1month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.srchStoreCd = $("#mobileTimeDaySaleStoreCd").val();
        params.startTime = $scope.startTime;
        params.endTime = $scope.endTime;

        $scope._inquirySub("/mobile/sale/status/timeDaySale/timeDaySale/getMobileTimeDaySaleTimeList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileTimeDaySaleTime", $scope.flexMobileTimeDaySaleTime, "Y");
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileTimeDaySaleStoreShow = function () {
        $scope._broadcast('mobileTimeDaySaleStoreCtrl');
    };
}]);