/****************************************************************
 *
 * 파일명 : mobileWeekSale.js
 * 설  명 : (모바일) 매출현황 > 주간매출현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.14     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매출종합 그리드 생성
 */
app.controller('mobileWeekSaleTotalCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileWeekSaleTotalCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileWeekSaleTotalCtrl", function(event, data) {
        $scope.searchMobileWeekSaleTotal(data);
        event.preventDefault();
    });

    $scope.searchMobileWeekSaleTotal = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._postJSONQuery.withOutPopUp( "/mobile/sale/status/weekSale/weekSale/getMobileWeekSaleTotalList.sb", params, function(response){
            var mobileWeekSale = response.data.data.result;
            $scope.mobileWeekSale = mobileWeekSale;

            if(response.data.data.result != null) {
                $("#lblRealSaleCnt").text($scope.mobileWeekSale.realSaleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblRtnSaleCnt").text($scope.mobileWeekSale.rtnSaleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblBillCnt").text($scope.mobileWeekSale.billCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblSaleCnt").text($scope.mobileWeekSale.saleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotSaleAmt").text($scope.mobileWeekSale.totSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotDcAmt").text($scope.mobileWeekSale.totDcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblRealSaleAmt").text($scope.mobileWeekSale.realSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblDayAvrSale").text($scope.mobileWeekSale.dayAvrSale.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblCardAmt").text($scope.mobileWeekSale.cardAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblCashAmt").text($scope.mobileWeekSale.cashAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblEtcAmt").text($scope.mobileWeekSale.etcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblBillUprc").text($scope.mobileWeekSale.billUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
            }
        });
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.mobileWeekSale;
    $scope.setMobileWeekSale = function(store) {
        $scope.mobileWeekSale = store;
    };
    $scope.setMobileWeekSale = function(){
        return $scope.mobileWeekSale;
    };
}]);


/**
 *  결제수단 그리드 생성
 */
app.controller('mobileWeekSaleCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileWeekSaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", getToday());

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 차트 초기값 셋팅
        $scope.searchMobileWeekSaleChartSet();

        // 조회
        // $scope.searchMobileWeekSalePay();
    };

    // 차트 초기값 셋팅
    $scope.searchMobileWeekSaleChartSet = function(){
        // 조회일자
        $("#lblDate").text("");

        var params = {};
        params.startDate =  wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.srchStoreCd = $("#mobileWeekSaleStoreCd").val();

        // 바 차트
        $scope._broadcast("mobileWeekSaleDlvrChartCtrl", params);
        $scope._broadcast("mobileWeekSaleDlvrChart2Ctrl", params);
        $scope._broadcast("mobileWeekSaleDlvrChart3Ctrl", params);
        $scope._broadcast("mobileWeekSaleDlvrChart4Ctrl", params);
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileWeekSaleCtrl", function(event, data) {
        gridOpen("mobileWeekSaleTotal");
        gridOpen("mobileWeekSalePay");
        gridOpen("mobileWeekSaleDc");
        gridOpen("mobileWeekSaleShop");
        gridOpen("mobileWeekSaleDlvr");
        gridOpen("mobileWeekSaleDtl");

        $scope.searchMobileWeekSalePay();
        event.preventDefault();
    });

    $scope.searchMobileWeekSalePay = function(){
        /** 조회일자 요일 구하기 **/
        var weekName = new Array('일','월','화','수','목','금','토');
        var searchDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        var searchYear = searchDate.substring(0,4);
        var searchMonth = searchDate.substring(4,6);
        var searchDay = searchDate.substring(6,8);
        var week = new Date(searchYear, searchMonth-1, searchDay, 0,0,0,0);
        var searchWeek = weekName[week.getDay()]; // 조회일자 요일

        var A = 0;
        var B = 0;
        if(searchWeek == "월") {
            A = 0;
            B = 6;
        } else if(searchWeek == "화") {
            A = 1;
            B = 5;
        } else if(searchWeek == "수") {
            A = 2;
            B = 4;
        } else if(searchWeek == "목") {
            A = 3;
            B = 3;
        } else if(searchWeek == "금") {
            A = 4;
            B = 2;
        } else if(searchWeek == "토") {
            A = 5;
            B = 1;
        } else if(searchWeek == "토") {
            A = 6;
            B = 0;
        }

        /** 시작일자 구하기 **/
        // A일 전 날짜 구하기
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var startDiffDay = startDt.getTime() - (A * 24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨
        var startNewDate = new Date();
        startNewDate.setTime(startDiffDay);
        // A일 전 날짜 만들기
        var beforeDt = wijmo.Globalize.format(startNewDate, 'yyyyMMdd');
        var beforeYear = beforeDt.substring(0,4);
        var beforeMonth = beforeDt.substring(4,6);
        var beforeDay = beforeDt.substring(6,8);
        beforeDt = beforeYear + beforeMonth + beforeDay; // 시작날짜

        /** 종료일자 구하기 **/
        // B일 전 날짜 구하기
        var endDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDiffDay = endDt.getTime() + (B * 24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨
        var endNewDate = new Date();
        endNewDate.setTime(endDiffDay);
        // B일 전 날짜 만들기
        var afterDt = wijmo.Globalize.format(endNewDate, 'yyyyMMdd');
        var afterYear = afterDt.substring(0,4);
        var afterMonth = afterDt.substring(4,6);
        var afterDay = afterDt.substring(6,8);
        afterDt = afterYear + afterMonth + afterDay; // 종료날짜

        // 조회일자
        $("#lblDate").text(beforeYear + "-" + beforeMonth + "-" + beforeDay + "(월)" + "~" + afterYear + "-" + afterMonth + "-" + afterDay + "(일)");

        var params = {};
        params.startDate = beforeDt; // 조회기간
        params.endDate = afterDt; // 조회기간
        params.srchStoreCd = $("#mobileWeekSaleStoreCd").val();

        $scope._inquirySub("/mobile/sale/status/weekSale/weekSale/getMobileWeekSalePayList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileWeekSalePay", $scope.flexMobileWeekSalePay, "Y");

            // 매출종합
            $scope._broadcast("mobileWeekSaleTotalCtrl", params);
            // 할인내역
            $scope._broadcast("mobileWeekSaleDcCtrl", params);
            // 내점현황
            $scope._broadcast("mobileWeekSaleShopCtrl", params);
            // 내점/배달/포장
            $scope._broadcast("mobileWeekSaleDlvrCtrl", params);
            // 바 차트
            $scope._broadcast("mobileWeekSaleDlvrChartCtrl", params);
            $scope._broadcast("mobileWeekSaleDlvrChart2Ctrl", params);
            $scope._broadcast("mobileWeekSaleDlvrChart3Ctrl", params);
            $scope._broadcast("mobileWeekSaleDlvrChart4Ctrl", params);
            // 일자별 매출현황
            $scope._broadcast("mobileWeekSaleDtlCtrl", params);
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileWeekSaleStoreShow = function () {
        $scope._broadcast('mobileWeekSaleStoreCtrl');
    };
}]);


/**
 *  할인내역 그리드 생성
 */
app.controller('mobileWeekSaleDcCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileWeekSaleDcCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileWeekSaleDcCtrl", function(event, data) {
        $scope.searchMobileWeekSaleDc(data);
        event.preventDefault();
    });

    $scope.searchMobileWeekSaleDc = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/weekSale/weekSale/getMobileWeekSaleDcList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileWeekSaleDc", $scope.flexMobileWeekSaleDc, "Y");
        }, false);
    };
    // <-- //검색 호출 -->
}]);


/**
 *  내점현황 그리드 생성
 */
app.controller('mobileWeekSaleShopCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileWeekSaleShopCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileWeekSaleShopCtrl", function(event, data) {
        $scope.searchMobileWeekSaleShop(data);
        event.preventDefault();
    });

    $scope.searchMobileWeekSaleShop = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._postJSONQuery.withOutPopUp( "/mobile/sale/status/weekSale/weekSale/getMobileWeekSaleShopList.sb", params, function(response){
            var mobileWeekSaleShop = response.data.data.result;
            $scope.mobileWeekSaleShop = mobileWeekSaleShop;

            if(response.data.data.result != null) {
                $("#lblShopRealSaleCnt").text($scope.mobileWeekSaleShop.realSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblShopDayAvrSale").text($scope.mobileWeekSaleShop.dayAvrSale.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblShopTotGuestCnt").text($scope.mobileWeekSaleShop.totGuestCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblShopGuestUprc").text($scope.mobileWeekSaleShop.guestUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
            }
        });
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.mobileWeekSaleShop;
    $scope.setMobileWeekSaleShop = function(store) {
        $scope.mobileWeekSaleShop = store;
    };
    $scope.setMobileWeekSaleShop = function(){
        return $scope.mobileWeekSaleShop;
    };
}]);


/**
 *  내점/배달/포장 그리드 생성
 */
app.controller('mobileWeekSaleDlvrCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileWeekSaleDlvrCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileWeekSaleDlvrCtrl", function(event, data) {
        $scope.searchMobileWeekSaleDlvr(data);
        event.preventDefault();
    });

    $scope.searchMobileWeekSaleDlvr = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/weekSale/weekSale/getMobileWeekSaleDlvrList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);
/**
 *  내점/배달/포장 차트 생성
 */
app.controller('mobileWeekSaleDlvrChartCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileWeekSaleDlvrChartCtrl', $scope, $http, false));

    // 차트
    $scope.initChart = function(s, args){
        // s.plotMargin = 'auto auto 50 auto'; // top, right, bottom, left
        s.plotMargin = 'auto auto auto auto'; // top, right, bottom, left
        s.axisX.labelAngle = 0; // x축 명칭 기울기
        // s.axisX.overlappingLabels = wijmo.chart.OverlappingLabels.Show;
        // s.header = "내점/배달/포장";
        s.legend.position = wijmo.chart.Position.Top; // 범례 위치

        var chartAnimation = new wijmo.chart.animation.ChartAnimation(s, {
            animationMode: wijmo.chart.animation.AnimationMode.All,
            easing: wijmo.chart.animation.Easing.Linear,
            duration: 400
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileWeekSaleDlvrChartCtrl", function(event, data) {
        $scope.searchMobileWeekSaleDlvrChart(data);
    });

    $scope.searchMobileWeekSaleDlvrChart = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "1";

        $scope._inquirySub("/mobile/sale/status/weekSale/weekSale/getMobileWeekSaleDlvrChartList.sb", params);
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
app.controller('mobileWeekSaleDlvrChart2Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileWeekSaleDlvrChart2Ctrl', $scope, $http, false));

    //메인그리드 조회후 상세그리드 조회.
    $scope.initChart = function(s, args){
        // s.plotMargin = 'auto auto 50 auto'; // top, right, bottom, left
        s.plotMargin = 'auto auto auto auto'; // top, right, bottom, left
        s.axisX.labelAngle = 0; // x축 명칭 기울기
        //s.axisX.overlappingLabels = wijmo.chart.OverlappingLabels.Show;
        // s.header = "내점";
        s.legend.position = wijmo.chart.Position.Top; // 범례 위치

        var chartAnimation = new wijmo.chart.animation.ChartAnimation(s, {
            animationMode: wijmo.chart.animation.AnimationMode.All,
            easing: wijmo.chart.animation.Easing.Linear,
            duration: 400
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileWeekSaleDlvrChart2Ctrl", function(event, data) {
        $scope.searchMobileWeekSaleDlvrChart2(data);
    });

    $scope.searchMobileWeekSaleDlvrChart2 = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "2";

        $scope._inquirySub("/mobile/sale/status/weekSale/weekSale/getMobileWeekSaleDlvrChartList.sb", params);
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
app.controller('mobileWeekSaleDlvrChart3Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileWeekSaleDlvrChart3Ctrl', $scope, $http, false));

    //메인그리드 조회후 상세그리드 조회.
    $scope.initChart = function(s, args){
        // s.plotMargin = 'auto auto 50 auto'; // top, right, bottom, left
        s.plotMargin = 'auto auto auto auto'; // top, right, bottom, left
        s.axisX.labelAngle = 0; // x축 명칭 기울기
        //s.axisX.overlappingLabels = wijmo.chart.OverlappingLabels.Show;
        // s.header = "배달";
        s.palette = ['#ff9d39']; // 그래프 색상
        s.legend.position = wijmo.chart.Position.Top; // 범례 위치

        var chartAnimation = new wijmo.chart.animation.ChartAnimation(s, {
            animationMode: wijmo.chart.animation.AnimationMode.All,
            easing: wijmo.chart.animation.Easing.Linear,
            duration: 400
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileWeekSaleDlvrChart3Ctrl", function(event, data) {
        $scope.searchMobileWeekSaleDlvrChart3(data);
    });

    $scope.searchMobileWeekSaleDlvrChart3 = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "3";

        $scope._inquirySub("/mobile/sale/status/weekSale/weekSale/getMobileWeekSaleDlvrChartList.sb", params);
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
app.controller('mobileWeekSaleDlvrChart4Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileWeekSaleDlvrChart4Ctrl', $scope, $http, false));

    //메인그리드 조회후 상세그리드 조회.
    $scope.initChart = function(s, args){
        // s.plotMargin = 'auto auto 50 auto'; // top, right, bottom, left
        s.plotMargin = 'auto auto auto auto'; // top, right, bottom, left
        s.axisX.labelAngle = 0; // x축 명칭 기울기
        //s.axisX.overlappingLabels = wijmo.chart.OverlappingLabels.Show;
        // s.header = "포장";
        s.palette = ['#71d195']; // 그래프 색상
        s.legend.position = wijmo.chart.Position.Top; // 범례 위치

        var chartAnimation = new wijmo.chart.animation.ChartAnimation(s, {
            animationMode: wijmo.chart.animation.AnimationMode.All,
            easing: wijmo.chart.animation.Easing.Linear,
            duration: 400
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileWeekSaleDlvrChart4Ctrl", function(event, data) {
        $scope.searchMobileWeekSaleDlvrChart4(data);
    });

    $scope.searchMobileWeekSaleDlvrChart4 = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "4";

        $scope._inquirySub("/mobile/sale/status/weekSale/weekSale/getMobileWeekSaleDlvrChartList.sb", params);
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


/**
 *  일자별 매출현황 그리드 생성
 */
app.controller('mobileWeekSaleDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileWeekSaleDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
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

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.saleDate = messages["mobile.weekSale.saleDate"];
        dataItem.totSaleAmt = messages["mobile.weekSale.totSaleAmt"];
        dataItem.totDcAmt = messages["mobile.weekSale.totDcAmt"];
        dataItem.totRealSaleAmt = messages["mobile.weekSale.totRealSaleAmt"];
        dataItem.shopRealSaleAmt = messages["mobile.weekSale.shop"];
        dataItem.shopBillCnt = messages["mobile.weekSale.shop"];
        dataItem.shopBillUprc = messages["mobile.weekSale.shop"];
        dataItem.dlvrRealSaleAmt = messages["mobile.weekSale.dlvr"];
        dataItem.dlvrBillCnt = messages["mobile.weekSale.dlvr"];
        dataItem.dlvrBillUprc = messages["mobile.weekSale.dlvr"];
        dataItem.packRealSaleAmt = messages["mobile.weekSale.pack"];
        dataItem.packBillCnt = messages["mobile.weekSale.pack"];
        dataItem.packBillUprc = messages["mobile.weekSale.pack"];
        dataItem.totGuestCnt = messages["mobile.weekSale.totGuestCnt"];
        dataItem.guestUprc = messages["mobile.weekSale.guestUprc"];
        dataItem.cardAmt = messages["mobile.weekSale.pay"];
        dataItem.cashAmt = messages["mobile.weekSale.pay"];
        dataItem.etcAmt = messages["mobile.weekSale.pay"];
        dataItem.dcAmt = messages["mobile.weekSale.dc"];
        dataItem.coupnDcAmt = messages["mobile.weekSale.dc"];
        dataItem.etcDcAmt = messages["mobile.weekSale.dc"];

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
    $scope.$on("mobileWeekSaleDtlCtrl", function(event, data) {
        $scope.searchMobileWeekSaleDtl(data);
        event.preventDefault();
    });

    $scope.searchMobileWeekSaleDtl = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/weekSale/weekSale/getMobileWeekSaleDtlList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileWeekSaleDtl", $scope.flexMobileWeekSaleDtl, "Y");
        }, false);
    };
    // <-- //검색 호출 -->
}]);