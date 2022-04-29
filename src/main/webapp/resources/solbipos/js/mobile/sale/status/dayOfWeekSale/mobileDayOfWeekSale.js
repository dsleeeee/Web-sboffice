/****************************************************************
 *
 * 파일명 : mobileDayOfWeekSale.js
 * 설  명 : (모바일) 매출현황 > 요일별 JavaScript
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
app.controller('mobileDayOfWeekSaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDayOfWeekSaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');


    };

    // <-- 검색 호출 -->
    $scope.$on("mobileDayOfWeekSaleCtrl", function(event, data) {

        // 접힌 그리드와 차트 영역 오픈
        gridOpen("mobileDayOfWeekSale");

        // 조회
        $scope.searchMobileDayOfWeekSale();
        event.preventDefault();
    });

    $scope.searchMobileDayOfWeekSale = function(){
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
        params.srchStoreCd = $("#mobileDayOfWeekSaleStoreCd").val();

        var diffTime = parseInt($scope.endTime) - parseInt($scope.startTime) + 1;
        params.diffTime = diffTime; // 조회시간 차이(차트 높이 때문에)

        $scope._inquirySub("/mobile/sale/status/dayOfWeekSale/dayOfWeekSale/getMobileDayOfWeekSaleList.sb", params, function() {
            
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileDayOfWeekSale", $scope.flexMobileDayOfWeekSale, "Y");

            // 차트 보이게하고 조회
            $("#divDayOfWeekSaleBarChart").css("display", "");
            $scope._broadcast("mobileDayOfWeekSaleChartCtrl", params);

        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileDayOfWeekSaleStoreShow = function () {
        $scope._broadcast('mobileDayOfWeekSaleStoreCtrl');
    };

}]);

/**
 * 일자별 차트
 */
app.controller('mobileDayOfWeekSaleChartCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDayOfWeekSaleChartCtrl', $scope, $http, false));

    // 차트
    $scope.initChart = function(s, args){
        // s.plotMargin = 'auto auto 50 auto'; // top, right, bottom, left
        s.plotMargin = 'auto auto auto auto'; // top, right, bottom, left
        s.axisX.labelAngle = 0; // x축 명칭 기울기
        // s.axisX.overlappingLabels = wijmo.chart.OverlappingLabels.Show;
        // s.header = "다중매장";
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
    $scope.$on("mobileDayOfWeekSaleChartCtrl", function(event, data) {

        diffTimeCol = data.diffTime + 1; // 조회시간 차이(차트 높이 때문에)

        // 차트 높이 선택한 날짜에 따라
        var col = diffTimeCol;
        // 최소값 + (15 * 날짜수) + px
        var chartHeight = 115 + (15 * col) + "px"; // 최소값 : 31일땐 580 / 30일땐 550 / 1일떈 130(더 작게도 가능함)
        $("#mobileOrderChannelPayBarChart").css("height", chartHeight);

        $scope.searchMobileDayOfWeekChart(data);
    });

    $scope.searchMobileDayOfWeekChart = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;
        params.orderType = "realSaleAmt";

        $scope._inquirySub("/mobile/sale/status/dayOfWeekSale/dayOfWeekSale/getMobileDayOfWeekSaleChartList.sb", params);
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
