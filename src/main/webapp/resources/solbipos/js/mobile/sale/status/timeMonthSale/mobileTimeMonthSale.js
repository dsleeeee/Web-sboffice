/****************************************************************
 *
 * 파일명 : mobileTimeMonthSale.js
 * 설  명 : (모바일) 매출현황 > 시간대별(월별)매출현황 JavaScript
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
 *  월-시간대별 그리드 생성
 */
app.controller('mobileTimeMonthSaleDateTimeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTimeMonthSaleDateTimeCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTimeMonthSaleDateTimeCtrl", function(event, data) {
        $scope.searchMobileTimeMonthSaleDateTime(data);
        event.preventDefault();
    });

    $scope.searchMobileTimeMonthSaleDateTime = function(data){
        var params = {};
        params.startMonth = data.startMonth;
        params.endMonth = data.endMonth;
        params.srchStoreCd = data.srchStoreCd;
        params.startTime = data.startTime;
        params.endTime = data.endTime;

        $scope._inquirySub("/mobile/sale/status/timeMonthSale/timeMonthSale/getMobileTimeMonthSaleDateTimeList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileTimeMonthSaleDateTime", $scope.flexMobileTimeMonthSaleDateTime, "Y");

            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridMobileTimeMonthSaleDateTimeList");
            var columns = grid.columns;
            var start = parseInt(data.startTime) + 1;
            var end = parseInt(data.endTime) + 1;

            // 컬럼 총갯수
            var columnsCnt = 25;

            for (var i = 1; i < columnsCnt; i++) {
                if (i >= start && i <= end) {
                    columns[i].visible = true;
                } else {
                    columns[i].visible = false;
                }
            }
            // <-- //그리드 visible -->
        }, false);
    };
    // <-- //검색 호출 -->
}]);


/**
 *  시간대별 그리드 생성
 */
app.controller('mobileTimeMonthSaleCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTimeMonthSaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

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

        // 차트 초기값 셋팅
        $scope.searchMobileTimeMonthSaleChartSet();

        // 조회
        // $scope.searchMobileTimeMonthSale();
    };

    // 차트 초기값 셋팅
    $scope.searchMobileTimeMonthSaleChartSet = function(){
        var date = new Date();
        var year = new String(date.getFullYear());
        var month = new String(date.getMonth()+1);
        month = month.length <= 1 ? "0"+month : month;

        var params = {};
        params.startMonth = year + month; // 조회기간
        params.endMonth = year + month; // 조회기간
        params.srchStoreCd = $("#mobileTimeMonthSaleStoreCd").val();
        params.startTime = 0;
        params.endTime = 23;
        params.diffTime = 1; // 조회시간 차이(차트 높이 때문에)

        // 바 차트
        $scope._broadcast("mobileTimeMonthSaleTimeChartCtrl", params);
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTimeMonthSaleCtrl", function(event, data) {
        gridOpen("mobileTimeMonthSaleDateTime");
        gridOpen("mobileTimeMonthSaleTime");

        $scope.searchMobileTimeMonthSale();
        event.preventDefault();
    });

    $scope.searchMobileTimeMonthSale = function(){
        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['mobile.cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 1년(12개월) 제한
        if (diffMonth > 13) {
            $scope._popMsg(messages['mobile.cmm.dateOver.1year.error']);
            return false;
        }

        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.srchStoreCd = $("#mobileTimeMonthSaleStoreCd").val();
        params.startTime = $scope.startTime;
        params.endTime = $scope.endTime;
        var diffTime = parseInt($scope.endTime) - parseInt($scope.startTime) + 1;
        params.diffTime = diffTime; // 조회시간 차이(차트 높이 때문에)

        $scope._inquirySub("/mobile/sale/status/timeMonthSale/timeMonthSale/getMobileTimeMonthSaleTimeList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileTimeMonthSaleTime", $scope.flexMobileTimeMonthSaleTime, "Y");

            // 일자-시간대별
            $scope._broadcast("mobileTimeMonthSaleDateTimeCtrl", params);
            // 바 차트
            $scope._broadcast("mobileTimeMonthSaleTimeChartCtrl", params);
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileTimeMonthSaleStoreShow = function () {
        $scope._broadcast('mobileTimeMonthSaleStoreCtrl');
    };
}]);
/**
 *  시간대별 차트 생성
 */
app.controller('mobileTimeMonthSaleTimeChartCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTimeMonthSaleTimeChartCtrl', $scope, $http, false));

    // 차트
    $scope.initChart = function(s, args){
        // s.plotMargin = 'auto auto 50 auto'; // top, right, bottom, left
        s.plotMargin = 'auto auto auto auto'; // top, right, bottom, left
        s.axisX.labelAngle = 0; // x축 명칭 기울기
        // s.axisX.overlappingLabels = wijmo.chart.OverlappingLabels.Show;
        // s.header = "시간대별";
        s.legend.position = wijmo.chart.Position.Top; // 범례 위치

        var chartAnimation = new wijmo.chart.animation.ChartAnimation(s, {
            animationMode: wijmo.chart.animation.AnimationMode.All,
            easing: wijmo.chart.animation.Easing.Linear,
            duration: 400
        });
    };

    // 조회시간 차이(차트 높이 때문에)
    var diffTimeCol = 1;

    // <-- 검색 호출 -->
    $scope.$on("mobileTimeMonthSaleTimeChartCtrl", function(event, data) {
        diffTimeCol = data.diffTime + 1; // 조회시간 차이(차트 높이 때문에)

        // 차트 높이 선택한 날짜에 따라
        var col = diffTimeCol;
        // 최소값 + (15 * 날짜수) + px
        var chartHeight = 115 + (15 * col) + "px";
        $("#mobileTimeMonthSaleTimeBarChart").css("height", chartHeight);

        $scope.searchMobileTimeMonthSaleTimeChart(data);
    });

    $scope.searchMobileTimeMonthSaleTimeChart = function(data){
        var params = {};
        params.startMonth = data.startMonth;
        params.endMonth = data.endMonth;
        params.srchStoreCd = data.srchStoreCd;
        params.startTime = data.startTime;
        params.endTime = data.endTime;

        $scope._inquirySub("/mobile/sale/status/timeMonthSale/timeMonthSale/getMobileTimeMonthSaleTimeChartList.sb", params);
    };
    // <-- //검색 호출 -->

    $scope.rendered = function(s, e) {

        var pArea = s.hostElement.querySelector('.wj-plot-area > rect');
        var pAreaWidth = pArea.width.baseVal.value;
        var groupWidth = pAreaWidth / (s.collectionView.items.length || 1);

        var labels = document.querySelectorAll('.wj-axis-x .wj-label');
        var widthMax = new Array();

        labels.forEach(function(value, key, parent) {

            var x = +value.getAttribute('x');
            var y = +value.getAttribute('y');
            var text = value.innerHTML.split(' - ');
            value.innerHTML = '';

            widthMax[key] = new Array();

            text.forEach(function(item, index) {

                var e = document.createElementNS('http://www.w3.org/2000/svg', 'tspan');
                //e.setAttribute("x", (x + 0).toString());
                e.setAttribute('y', (y + (15 * index)).toString());
                e.innerHTML = item;
                value.appendChild(e);

                var bbox = e.getBoundingClientRect();
                var extent = e.getExtentOfChar(0);
                var boxWidth = e.getComputedTextLength();
                var gap = 0;

                gap = (groupWidth - boxWidth) / 2;
                widthMax[key][index] = gap;

                e.setAttribute('x', (x + gap).toString());
            });
        });

        labels.forEach(function(value, key, parent) {
            var children = value.childNodes;
            for (var i = 0; i < children.length; i++) {
                var e = value.childNodes[i];
                var extent = e.getExtentOfChar(0);

                e.setAttribute('x', extent.x - widthMax[key][0] + 30);
            }
        });

        s.tooltip.content = function (ht) {
            var title = ht.name;
            var nameArr = ht._xfmt.split(" - ");
            var value = ht.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            return "<b>" + title + "</b><br><br>" + nameArr[0] + "<br><br>" + value;
        }
    }
}]);