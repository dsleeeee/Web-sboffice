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
 *  일자-시간대별 그리드 생성
 */
app.controller('mobileTimeDaySaleDateTimeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTimeDaySaleDateTimeCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTimeDaySaleDateTimeCtrl", function(event, data) {
        $scope.searchMobileTimeDaySaleDateTime(data);
        event.preventDefault();
    });

    $scope.searchMobileTimeDaySaleDateTime = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;
        params.startTime = data.startTime;
        params.endTime = data.endTime;
        params.optionFg = $("input[name=optionFg]:checked").val();
        params.timeSlot = data.timeSlot;

        $scope._inquirySub("/mobile/sale/status/timeDaySale/timeDaySale/getMobileTimeDaySaleDateTimeList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileTimeDaySaleDateTime", $scope.flexMobileTimeDaySaleDateTime, "Y");

            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridMobileTimeDaySaleDateTimeList");
            var columns = grid.columns;
            var start = parseInt(data.startTime) + 1;
            var end = parseInt(data.endTime) + 1;

            if($("input[name=optionFg]:checked").val() == "time") { // 시간대
                // 컬럼 총갯수
                var columnsCnt = 25;
                for (var i = 1; i < columnsCnt; i++) {
                    if (i >= start && i <= end) {
                        columns[i].visible = true;
                    } else {
                        columns[i].visible = false;
                    }
                }
            } else if($("input[name=optionFg]:checked").val() == "timeSlot") {   // 시간대분류

                for (var i = start; i < columns.length; i++) {
                    columns[i].visible = false;
                    for(var j = 0; j < timeSlotColList.length; j++){
                        if($scope.timeSlot == timeSlotColList[j].value || $scope.timeSlot === ""){
                            if (columns[i].binding == 'realSaleAmtT'+timeSlotColList[j].value.replaceAll("~","")) {
                                columns[i].visible = true;
                            }
                        }
                    }
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
app.controller('mobileTimeDaySaleCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTimeDaySaleCtrl', $scope, $http, false));

    $scope.timeSlotData = [];
    var comboArray  = [{name:"전체", value:""}];
    for(var i = 0; i < timeSlotColList.length; i++){
        var comboData   = {};
        comboData.name = timeSlotColList[i].name + "(" + timeSlotColList[i].value + ")";
        comboData.value = timeSlotColList[i].value;
        comboArray.push(comboData);
    }

    timeSlotData = comboArray;
    $scope._setComboData("timeSlotCombo", timeSlotData);

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

        // 차트 초기값 셋팅
        $scope.searchMobileTimeDaySaleChartSet();

        // 조회
        // $scope.searchMobileTimeDaySale();
    };

    // 차트 초기값 셋팅
    $scope.searchMobileTimeDaySaleChartSet = function(){
        var params = {};
        params.startDate = getToday(); // 조회기간
        params.endDate = getToday(); // 조회기간
        params.srchStoreCd = $("#mobileTimeDaySaleStoreCd").val();
        params.startTime = 0;
        params.endTime = 23;
        params.diffTime = 1; // 조회시간 차이(차트 높이 때문에)
        params.optionFg = $("input[name=optionFg]:checked").val();
        params.timeSlot = $scope.timeSlot;

        // 바 차트
        $scope._broadcast("mobileTimeDaySaleTimeChartCtrl", params);
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTimeDaySaleCtrl", function(event, data) {
        gridOpen("mobileTimeDaySaleDateTime");
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
        params.optionFg = $("input[name=optionFg]:checked").val();
        params.timeSlot = $scope.timeSlot;
        var diffTime = parseInt($scope.endTime) - parseInt($scope.startTime) + 1;
        params.diffTime = diffTime; // 조회시간 차이(차트 높이 때문에)

        $scope._inquirySub("/mobile/sale/status/timeDaySale/timeDaySale/getMobileTimeDaySaleTimeList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileTimeDaySaleTime", $scope.flexMobileTimeDaySaleTime, "Y");

            // 일자-시간대별
            $scope._broadcast("mobileTimeDaySaleDateTimeCtrl", params);
            // 바 차트
            $scope._broadcast("mobileTimeDaySaleTimeChartCtrl", params);
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileTimeDaySaleStoreShow = function () {
        $scope._broadcast('mobileTimeDaySaleStoreCtrl');
    };

    // 라디오버튼 클릭시 이벤트 발생
    $("input:radio[name=optionFg]").click(function(){
        if($("input[name=optionFg]:checked").val() == "time"){              // 시간대
            $("#timeOption").show();
            $("#timeSlotOption").hide();
        }else {       // 시간대분류
            $("#timeOption").hide();
            $("#timeSlotOption").show();
        }
    });

}]);
/**
 *  시간대별 차트 생성
 */
app.controller('mobileTimeDaySaleTimeChartCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTimeDaySaleTimeChartCtrl', $scope, $http, false));

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
    $scope.$on("mobileTimeDaySaleTimeChartCtrl", function(event, data) {
        diffTimeCol = data.diffTime + 1; // 조회시간 차이(차트 높이 때문에)

        // 차트 높이 선택한 날짜에 따라
        var col = diffTimeCol;
        // 최소값 + (15 * 날짜수) + px
        var chartHeight = 115 + (15 * col) + "px"; // 최소값 : 31일땐 580 / 30일땐 550 / 1일떈 130(더 작게도 가능함)
        $("#mobileTimeDaySaleTimeBarChart").css("height", chartHeight);

        $scope.searchMobileTimeDaySaleTimeChart(data);
    });

    $scope.searchMobileTimeDaySaleTimeChart = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;
        params.startTime = data.startTime;
        params.endTime = data.endTime;
        params.optionFg = $("input[name=optionFg]:checked").val();
        params.timeSlot = data.timeSlot;

        $scope._inquirySub("/mobile/sale/status/timeDaySale/timeDaySale/getMobileTimeDaySaleTimeChartList.sb", params);
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