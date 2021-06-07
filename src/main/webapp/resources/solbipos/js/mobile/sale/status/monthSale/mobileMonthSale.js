/****************************************************************
 *
 * 파일명 : mobileMonthSale.js
 * 설  명 : (모바일) 매출현황 > 월별매출현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매출종합 그리드 생성
 */
app.controller('mobileMonthSaleTotalCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMonthSaleTotalCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileMonthSaleTotalCtrl", function(event, data) {
        $scope.searchMobileMonthSaleTotal(data);
        event.preventDefault();
    });

    $scope.searchMobileMonthSaleTotal = function(data){
        var params = {};
        params.startMonth = data.startMonth;
        params.endMonth = data.endMonth;
        params.srchStoreCd = data.srchStoreCd;

        $scope._postJSONQuery.withOutPopUp( "/mobile/sale/status/monthSale/monthSale/getMobileMonthSaleTotalList.sb", params, function(response){
            var mobileMonthSale = response.data.data.result;
            $scope.mobileMonthSale = mobileMonthSale;

            if(response.data.data.result != null) {
                $("#lblRealSaleCnt").text($scope.mobileMonthSale.realSaleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblRtnSaleCnt").text($scope.mobileMonthSale.rtnSaleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblBillCnt").text($scope.mobileMonthSale.billCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblSaleCnt").text($scope.mobileMonthSale.saleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotSaleAmt").text($scope.mobileMonthSale.totSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotDcAmt").text($scope.mobileMonthSale.totDcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblRealSaleAmt").text($scope.mobileMonthSale.realSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblMonthAvrSale").text($scope.mobileMonthSale.monthAvrSale.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblCardAmt").text($scope.mobileMonthSale.cardAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblCashAmt").text($scope.mobileMonthSale.cashAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblEtcAmt").text($scope.mobileMonthSale.etcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblBillUprc").text($scope.mobileMonthSale.billUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
            }
        });
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.mobileMonthSale;
    $scope.setMobileMonthSale = function(store) {
        $scope.mobileMonthSale = store;
    };
    $scope.setMobileMonthSale = function(){
        return $scope.mobileMonthSale;
    };
}]);


/**
 *  결제수단 그리드 생성
 */
app.controller('mobileMonthSaleCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMonthSaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 차트 초기값 셋팅
        $scope.searchMobileMonthSaleChartSet();

        // 조회
        // $scope.searchMobileMonthSalePay();
    };

    // 차트 초기값 셋팅
    $scope.searchMobileMonthSaleChartSet = function(){
        var date = new Date();
        var year = new String(date.getFullYear());
        var month = new String(date.getMonth()+1);
        month = month.length <= 1 ? "0"+month : month;

        var params = {};
        params.startMonth = year + month; // 조회기간
        params.endMonth = year + month; // 조회기간
        params.srchStoreCd = $("#mobileMonthSaleStoreCd").val();
        params.diffMonth = 1; // 조회기간 차이(차트 높이 때문에)

        // 바 차트
        $scope._broadcast("mobileMonthSaleDlvrChartCtrl", params);
        $scope._broadcast("mobileMonthSaleDlvrChart2Ctrl", params);
        $scope._broadcast("mobileMonthSaleDlvrChart3Ctrl", params);
        $scope._broadcast("mobileMonthSaleDlvrChart4Ctrl", params);
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileMonthSaleCtrl", function(event, data) {
        gridOpen("mobileMonthSaleTotal");
        gridOpen("mobileMonthSalePay");
        gridOpen("mobileMonthSaleDc");
        gridOpen("mobileMonthSaleShop");
        gridOpen("mobileMonthSaleDlvr");
        gridOpen("mobileMonthSaleDtl");

        $scope.searchMobileMonthSalePay();
        event.preventDefault();
    });

    $scope.searchMobileMonthSalePay = function(){
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
        params.srchStoreCd = $("#mobileMonthSaleStoreCd").val();

        // 소수점 입력안됨
        var numchkexp1 = /^\d*[.]\d*$/;
        if(numchkexp1.test(diffMonth) == true) {
            params.diffMonth = parseInt( diffMonth.toString().substring(0, diffMonth.toString().indexOf(".")) ); // 조회기간 차이(차트 높이 때문에)
        } else {
            params.diffMonth = diffMonth; // 조회기간 차이(차트 높이 때문에)
        }

        $scope._inquirySub("/mobile/sale/status/monthSale/monthSale/getMobileMonthSalePayList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileMonthSalePay", $scope.flexMobileMonthSalePay, "Y");

            // 매출종합
            $scope._broadcast("mobileMonthSaleTotalCtrl", params);
            // 할인내역
            $scope._broadcast("mobileMonthSaleDcCtrl", params);
            // 내점현황
            $scope._broadcast("mobileMonthSaleShopCtrl", params);
            // 내점/배달/포장
            $scope._broadcast("mobileMonthSaleDlvrCtrl", params);
            // 바 차트
            $scope._broadcast("mobileMonthSaleDlvrChartCtrl", params);
            $scope._broadcast("mobileMonthSaleDlvrChart2Ctrl", params);
            $scope._broadcast("mobileMonthSaleDlvrChart3Ctrl", params);
            $scope._broadcast("mobileMonthSaleDlvrChart4Ctrl", params);
            // 월자별 매출현황
            $scope._broadcast("mobileMonthSaleDtlCtrl", params);
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileMonthSaleStoreShow = function () {
        $scope._broadcast('mobileMonthSaleStoreCtrl');
    };
}]);


/**
 *  할인내역 그리드 생성
 */
app.controller('mobileMonthSaleDcCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMonthSaleDcCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileMonthSaleDcCtrl", function(event, data) {
        $scope.searchMobileMonthSaleDc(data);
        event.preventDefault();
    });

    $scope.searchMobileMonthSaleDc = function(data){
        var params = {};
        params.startMonth = data.startMonth;
        params.endMonth = data.endMonth;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/monthSale/monthSale/getMobileMonthSaleDcList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileMonthSaleDc", $scope.flexMobileMonthSaleDc, "Y");
        }, false);
    };
    // <-- //검색 호출 -->
}]);


/**
 *  내점현황 그리드 생성
 */
app.controller('mobileMonthSaleShopCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMonthSaleShopCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileMonthSaleShopCtrl", function(event, data) {
        $scope.searchMobileMonthSaleShop(data);
        event.preventDefault();
    });

    $scope.searchMobileMonthSaleShop = function(data){
        var params = {};
        params.startMonth = data.startMonth;
        params.endMonth = data.endMonth;
        params.srchStoreCd = data.srchStoreCd;

        $scope._postJSONQuery.withOutPopUp( "/mobile/sale/status/monthSale/monthSale/getMobileMonthSaleShopList.sb", params, function(response){
            var mobileMonthSaleShop = response.data.data.result;
            $scope.mobileMonthSaleShop = mobileMonthSaleShop;

            if(response.data.data.result != null) {
                $("#lblShopRealSaleCnt").text($scope.mobileMonthSaleShop.realSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblShopMonthAvrSale").text($scope.mobileMonthSaleShop.monthAvrSale.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblShopTotGuestCnt").text($scope.mobileMonthSaleShop.totGuestCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblShopGuestUprc").text($scope.mobileMonthSaleShop.guestUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
            }
        });
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.mobileMonthSaleShop;
    $scope.setMobileMonthSaleShop = function(store) {
        $scope.mobileMonthSaleShop = store;
    };
    $scope.setMobileMonthSaleShop = function(){
        return $scope.mobileMonthSaleShop;
    };
}]);


/**
 *  내점/배달/포장 그리드 생성
 */
app.controller('mobileMonthSaleDlvrCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMonthSaleDlvrCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileMonthSaleDlvrCtrl", function(event, data) {
        $scope.searchMobileMonthSaleDlvr(data);
        event.preventDefault();
    });

    $scope.searchMobileMonthSaleDlvr = function(data){
        var params = {};
        params.startMonth = data.startMonth;
        params.endMonth = data.endMonth;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/monthSale/monthSale/getMobileMonthSaleDlvrList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);
/**
 *  내점/배달/포장 차트 생성
 */
app.controller('mobileMonthSaleDlvrChartCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMonthSaleDlvrChartCtrl', $scope, $http, false));

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

    // 조회기간 차이(차트 높이 때문에)
    var diffMonthCol = 1;

    // <-- 검색 호출 -->
    $scope.$on("mobileMonthSaleDlvrChartCtrl", function(event, data) {
        diffMonthCol = data.diffMonth; // 조회기간 차이(차트 높이 때문에)

        // 차트 높이 선택한 날짜에 따라
        var col = diffMonthCol;
        // 최소값 + (15 * 날짜수) + px
        var chartHeight = 115 + (15 * col) + "px";
        $("#mobileMonthSaleDlvrBarChart").css("height", chartHeight);

        $scope.searchMobileMonthSaleDlvrChart(data);
    });

    $scope.searchMobileMonthSaleDlvrChart = function(data){
        var params = {};
        params.startMonth = data.startMonth;
        params.endMonth = data.endMonth;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "1";

        $scope._inquirySub("/mobile/sale/status/monthSale/monthSale/getMobileMonthSaleDlvrChartList.sb", params);
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
app.controller('mobileMonthSaleDlvrChart2Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMonthSaleDlvrChart2Ctrl', $scope, $http, false));

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

    // 조회기간 차이(차트 높이 때문에)
    var diffMonthCol = 1;

    // <-- 검색 호출 -->
    $scope.$on("mobileMonthSaleDlvrChart2Ctrl", function(event, data) {
        diffMonthCol = data.diffMonth; // 조회기간 차이(차트 높이 때문에)

        // 차트 높이 선택한 날짜에 따라
        var col = diffMonthCol;
        // 최소값 + (15 * 날짜수) + px
        var chartHeight = 115 + (15 * col) + "px";
        $("#mobileMonthSaleDlvrBarChart2").css("height", chartHeight);

        $scope.searchMobileMonthSaleDlvrChart2(data);
    });

    $scope.searchMobileMonthSaleDlvrChart2 = function(data){
        var params = {};
        params.startMonth = data.startMonth;
        params.endMonth = data.endMonth;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "2";

        $scope._inquirySub("/mobile/sale/status/monthSale/monthSale/getMobileMonthSaleDlvrChartList.sb", params);
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
app.controller('mobileMonthSaleDlvrChart3Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMonthSaleDlvrChart3Ctrl', $scope, $http, false));

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

    // 조회기간 차이(차트 높이 때문에)
    var diffMonthCol = 1;

    // <-- 검색 호출 -->
    $scope.$on("mobileMonthSaleDlvrChart3Ctrl", function(event, data) {
        diffMonthCol = data.diffMonth; // 조회기간 차이(차트 높이 때문에)

        // 차트 높이 선택한 날짜에 따라
        var col = diffMonthCol;
        // 최소값 + (15 * 날짜수) + px
        var chartHeight = 115 + (15 * col) + "px";
        $("#mobileMonthSaleDlvrBarChart3").css("height", chartHeight);

        $scope.searchMobileMonthSaleDlvrChart3(data);
    });

    $scope.searchMobileMonthSaleDlvrChart3 = function(data){
        var params = {};
        params.startMonth = data.startMonth;
        params.endMonth = data.endMonth;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "3";

        $scope._inquirySub("/mobile/sale/status/monthSale/monthSale/getMobileMonthSaleDlvrChartList.sb", params);
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
app.controller('mobileMonthSaleDlvrChart4Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMonthSaleDlvrChart4Ctrl', $scope, $http, false));

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

    // 조회기간 차이(차트 높이 때문에)
    var diffMonthCol = 1;

    // <-- 검색 호출 -->
    $scope.$on("mobileMonthSaleDlvrChart4Ctrl", function(event, data) {
        diffMonthCol = data.diffMonth; // 조회기간 차이(차트 높이 때문에)

        // 차트 높이 선택한 날짜에 따라
        var col = diffMonthCol;
        // 최소값 + (15 * 날짜수) + px
        var chartHeight = 115 + (15 * col) + "px";
        $("#mobileMonthSaleDlvrBarChart4").css("height", chartHeight);

        $scope.searchMobileMonthSaleDlvrChart4(data);
    });

    $scope.searchMobileMonthSaleDlvrChart4 = function(data){
        var params = {};
        params.startMonth = data.startMonth;
        params.endMonth = data.endMonth;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "4";

        $scope._inquirySub("/mobile/sale/status/monthSale/monthSale/getMobileMonthSaleDlvrChartList.sb", params);
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
 *  월자별 매출현황 그리드 생성
 */
app.controller('mobileMonthSaleDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMonthSaleDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
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
        dataItem.saleYm = messages["mobile.monthSale.saleYm"];
        dataItem.totSaleAmt = messages["mobile.monthSale.totSaleAmt"];
        dataItem.totDcAmt = messages["mobile.monthSale.totDcAmt"];
        dataItem.totRealSaleAmt = messages["mobile.monthSale.totRealSaleAmt"];
        dataItem.shopRealSaleAmt = messages["mobile.monthSale.shop"];
        dataItem.shopBillCnt = messages["mobile.monthSale.shop"];
        dataItem.shopBillUprc = messages["mobile.monthSale.shop"];
        dataItem.dlvrRealSaleAmt = messages["mobile.monthSale.dlvr"];
        dataItem.dlvrBillCnt = messages["mobile.monthSale.dlvr"];
        dataItem.dlvrBillUprc = messages["mobile.monthSale.dlvr"];
        dataItem.packRealSaleAmt = messages["mobile.monthSale.pack"];
        dataItem.packBillCnt = messages["mobile.monthSale.pack"];
        dataItem.packBillUprc = messages["mobile.monthSale.pack"];
        dataItem.totGuestCnt = messages["mobile.monthSale.totGuestCnt"];
        dataItem.guestUprc = messages["mobile.monthSale.guestUprc"];
        dataItem.cardAmt = messages["mobile.monthSale.pay"];
        dataItem.cashAmt = messages["mobile.monthSale.pay"];
        dataItem.etcAmt = messages["mobile.monthSale.pay"];
        dataItem.dcAmt = messages["mobile.monthSale.dc"];
        dataItem.coupnDcAmt = messages["mobile.monthSale.dc"];
        dataItem.etcDcAmt = messages["mobile.monthSale.dc"];

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
    $scope.$on("mobileMonthSaleDtlCtrl", function(event, data) {
        $scope.searchMobileMonthSaleDtl(data);
        event.preventDefault();
    });

    $scope.searchMobileMonthSaleDtl = function(data){
        var params = {};
        params.startMonth = data.startMonth;
        params.endMonth = data.endMonth;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/monthSale/monthSale/getMobileMonthSaleDtlList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileMonthSaleDtl", $scope.flexMobileMonthSaleDtl, "Y");
        }, false);
    };
    // <-- //검색 호출 -->
}]);