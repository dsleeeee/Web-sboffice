/****************************************************************
 *
 * 파일명 : mobileDaySale.js
 * 설  명 : (모바일) 매출현황 > 일별매출현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.04     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매출종합 그리드 생성
 */
app.controller('mobileDaySaleTotalCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDaySaleTotalCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileDaySaleTotalCtrl", function(event, data) {
        $scope.searchMobileDaySaleTotal(data);
        event.preventDefault();
    });

    $scope.searchMobileDaySaleTotal = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._postJSONQuery.withOutPopUp( "/mobile/sale/status/daySale/daySale/getMobileDaySaleTotalList.sb", params, function(response){
            var mobileDaySale = response.data.data.result;
            $scope.mobileDaySale = mobileDaySale;

            if(response.data.data.result != null) {
                $("#lblRealSaleCnt").text($scope.mobileDaySale.realSaleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblRtnSaleCnt").text($scope.mobileDaySale.rtnSaleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblBillCnt").text($scope.mobileDaySale.billCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblSaleCnt").text($scope.mobileDaySale.saleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotSaleAmt").text($scope.mobileDaySale.totSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotDcAmt").text($scope.mobileDaySale.totDcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblRealSaleAmt").text($scope.mobileDaySale.realSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblDayAvrSale").text($scope.mobileDaySale.dayAvrSale.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblCardAmt").text($scope.mobileDaySale.cardAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblCashAmt").text($scope.mobileDaySale.cashAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblEtcAmt").text($scope.mobileDaySale.etcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblBillUprc").text($scope.mobileDaySale.billUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
            }
        });
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.mobileDaySale;
    $scope.setMobileDaySale = function(store) {
        $scope.mobileDaySale = store;
    };
    $scope.setMobileDaySale = function(){
        return $scope.mobileDaySale;
    };
}]);


/**
 *  결제수단 그리드 생성
 */
app.controller('mobileDaySaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDaySaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", getToday());
    var endDate = wcombo.genDateVal("#endDate", getToday());

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 차트 초기값 셋팅
        $scope.searchMobileDaySaleChartSet();

        // 조회
        // $scope.searchMobileDaySalePay();
    };

    // 차트 초기값 셋팅
    $scope.searchMobileDaySaleChartSet = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.srchStoreCd = $("#mobileDaySaleStoreCd").val();
        params.diffDay = 1; // 조회기간 차이(차트 높이 때문에)

        // 바 차트
        $scope._broadcast("mobileDaySaleDlvrChartCtrl", params);
        $scope._broadcast("mobileDaySaleDlvrChart2Ctrl", params);
        $scope._broadcast("mobileDaySaleDlvrChart3Ctrl", params);
        $scope._broadcast("mobileDaySaleDlvrChart4Ctrl", params);
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileDaySaleCtrl", function(event, data) {
        gridOpen("mobileDaySaleTotal");
        gridOpen("mobileDaySalePay");
        gridOpen("mobileDaySaleDc");
        gridOpen("mobileDaySaleShop");
        gridOpen("mobileDaySaleDlvr");
        gridOpen("mobileDaySaleDtl");

        $scope.searchMobileDaySalePay();
        event.preventDefault();
    });

    $scope.searchMobileDaySalePay = function(){
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
        params.srchStoreCd = $("#mobileDaySaleStoreCd").val();
        params.diffDay = diffDay; // 조회기간 차이(차트 높이 때문에)

        $scope._inquirySub("/mobile/sale/status/daySale/daySale/getMobileDaySalePayList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileDaySalePay", $scope.flexMobileDaySalePay, "Y");

            // 매출종합
            $scope._broadcast("mobileDaySaleTotalCtrl", params);
            // 할인내역
            $scope._broadcast("mobileDaySaleDcCtrl", params);
            // 내점현황
            $scope._broadcast("mobileDaySaleShopCtrl", params);
            // 내점/배달/포장
            $scope._broadcast("mobileDaySaleDlvrCtrl", params);
            // 바 차트
            $scope._broadcast("mobileDaySaleDlvrChartCtrl", params);
            $scope._broadcast("mobileDaySaleDlvrChart2Ctrl", params);
            $scope._broadcast("mobileDaySaleDlvrChart3Ctrl", params);
            $scope._broadcast("mobileDaySaleDlvrChart4Ctrl", params);
            // 일자별 매출현황
            $scope._broadcast("mobileDaySaleDtlCtrl", params);
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileDaySaleStoreShow = function () {
        $scope._broadcast('mobileDaySaleStoreCtrl');
    };

    // 결제수단 엑셀다운로드
    $("#btnExcelMobileDaySalePay").on("click", function(event) {

        if ($scope.flexMobileDaySalePay.rows.length <= 0) {
          $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
          return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
          wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flexMobileDaySalePay, {
            includeColumnHeaders: true,
            includeCellStyles: true,
            includeColumns: function (column) {
              return column.visible;
            }
          },  messages["mobile.daySale"] + '_' + messages["mobile.daySale.salePay"] + '_' + getToday() + '.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
        }, 10);
        event.stopPropagation();
    });

}]);


/**
 *  할인내역 그리드 생성
 */
app.controller('mobileDaySaleDcCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDaySaleDcCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileDaySaleDcCtrl", function(event, data) {
        $scope.searchMobileDaySaleDc(data);
        event.preventDefault();
    });

    $scope.searchMobileDaySaleDc = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/daySale/daySale/getMobileDaySaleDcList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileDaySaleDc", $scope.flexMobileDaySaleDc, "Y");
        }, false);
    };
    // <-- //검색 호출 -->

    // 할인내역 엑셀다운로드
    $("#btnExcelMobileDaySaleDc").on("click", function(event) {

        if ($scope.flexMobileDaySaleDc.rows.length <= 0) {
          $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
          return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
          wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flexMobileDaySaleDc, {
            includeColumnHeaders: true,
            includeCellStyles: true,
            includeColumns: function (column) {
              return column.visible;
            }
          },  messages["mobile.daySale"] + '_' + messages["mobile.daySale.saleDc"] + '_' + getToday() + '.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
        }, 10);
        event.stopPropagation();
    });

}]);


/**
 *  내점현황 그리드 생성
 */
app.controller('mobileDaySaleShopCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDaySaleShopCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileDaySaleShopCtrl", function(event, data) {
        $scope.searchMobileDaySaleShop(data);
        event.preventDefault();
    });

    $scope.searchMobileDaySaleShop = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._postJSONQuery.withOutPopUp( "/mobile/sale/status/daySale/daySale/getMobileDaySaleShopList.sb", params, function(response){
            var mobileDaySaleShop = response.data.data.result;
            $scope.mobileDaySaleShop = mobileDaySaleShop;

            if(response.data.data.result != null) {
                $("#lblShopRealSaleCnt").text($scope.mobileDaySaleShop.realSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblShopDayAvrSale").text($scope.mobileDaySaleShop.dayAvrSale.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblShopTotGuestCnt").text($scope.mobileDaySaleShop.totGuestCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblShopGuestUprc").text($scope.mobileDaySaleShop.guestUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
            }
        });
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.mobileDaySaleShop;
    $scope.setMobileDaySaleShop = function(store) {
        $scope.mobileDaySaleShop = store;
    };
    $scope.setMobileDaySaleShop = function(){
        return $scope.mobileDaySaleShop;
    };
}]);


/**
 *  내점/배달/포장 그리드 생성
 */
app.controller('mobileDaySaleDlvrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDaySaleDlvrCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileDaySaleDlvrCtrl", function(event, data) {
        $scope.searchMobileDaySaleDlvr(data);
        event.preventDefault();
    });

    $scope.searchMobileDaySaleDlvr = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/daySale/daySale/getMobileDaySaleDlvrList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 내점/배달/포장 엑셀다운로드
    $("#btnExcelMobileDaySaleDlvr").on("click", function(event) {

        if ($scope.flexMobileDaySaleDlvr.rows.length <= 0) {
          $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
          return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
          wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flexMobileDaySaleDlvr, {
            includeColumnHeaders: true,
            includeCellStyles: true,
            includeColumns: function (column) {
              return column.visible;
            }
          },  messages["mobile.daySale"] + '_' + messages["mobile.daySale.saleDlvr"] + '_' + getToday() + '.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
        }, 10);
        event.stopPropagation();
    });

}]);
/**
 *  내점/배달/포장 차트 생성
 */
app.controller('mobileDaySaleDlvrChartCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDaySaleDlvrChartCtrl', $scope, $http, false));

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
    var diffDayCol = 1;

    // <-- 검색 호출 -->
    $scope.$on("mobileDaySaleDlvrChartCtrl", function(event, data) {
        diffDayCol = data.diffDay + 1; // 조회기간 차이(차트 높이 때문에)

        // 차트 높이 선택한 날짜에 따라
        var col = diffDayCol;
        // 최소값 + (15 * 날짜수) + px
        var chartHeight = 115 + (15 * col) + "px"; // 최소값 : 31일땐 580 / 30일땐 550 / 1일떈 130(더 작게도 가능함)
        $("#mobileDaySaleDlvrBarChart").css("height", chartHeight);

        $scope.searchMobileDaySaleDlvrChart(data);
    });

    $scope.searchMobileDaySaleDlvrChart = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "1";

        $scope._inquirySub("/mobile/sale/status/daySale/daySale/getMobileDaySaleDlvrChartList.sb", params);
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
app.controller('mobileDaySaleDlvrChart2Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDaySaleDlvrChart2Ctrl', $scope, $http, false));

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
    var diffDayCol = 1;

    // <-- 검색 호출 -->
    $scope.$on("mobileDaySaleDlvrChart2Ctrl", function(event, data) {
        diffDayCol = data.diffDay + 1; // 조회기간 차이(차트 높이 때문에)

        // 차트 높이 선택한 날짜에 따라
        var col = diffDayCol;
        // 최소값 + (15 * 날짜수) + px
        var chartHeight = 115 + (15 * col) + "px"; // 최소값 : 31일땐 580 / 30일땐 550 / 1일떈 130(더 작게도 가능함)
        $("#mobileDaySaleDlvrBarChart2").css("height", chartHeight);

        $scope.searchMobileDaySaleDlvrChart2(data);
    });

    $scope.searchMobileDaySaleDlvrChart2 = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "2";

        $scope._inquirySub("/mobile/sale/status/daySale/daySale/getMobileDaySaleDlvrChartList.sb", params);
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
app.controller('mobileDaySaleDlvrChart3Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDaySaleDlvrChart3Ctrl', $scope, $http, false));

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
    var diffDayCol = 1;

    // <-- 검색 호출 -->
    $scope.$on("mobileDaySaleDlvrChart3Ctrl", function(event, data) {
        diffDayCol = data.diffDay + 1; // 조회기간 차이(차트 높이 때문에)

        // 차트 높이 선택한 날짜에 따라
        var col = diffDayCol;
        // 최소값 + (15 * 날짜수) + px
        var chartHeight = 115 + (15 * col) + "px"; // 최소값 : 31일땐 580 / 30일땐 550 / 1일떈 130(더 작게도 가능함)
        $("#mobileDaySaleDlvrBarChart3").css("height", chartHeight);

        $scope.searchMobileDaySaleDlvrChart3(data);
    });

    $scope.searchMobileDaySaleDlvrChart3 = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "3";

        $scope._inquirySub("/mobile/sale/status/daySale/daySale/getMobileDaySaleDlvrChartList.sb", params);
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
app.controller('mobileDaySaleDlvrChart4Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDaySaleDlvrChart4Ctrl', $scope, $http, false));

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
    var diffDayCol = 1;

    // <-- 검색 호출 -->
    $scope.$on("mobileDaySaleDlvrChart4Ctrl", function(event, data) {
        diffDayCol = data.diffDay + 1; // 조회기간 차이(차트 높이 때문에)

        // 차트 높이 선택한 날짜에 따라
        var col = diffDayCol;
        // 최소값 + (15 * 날짜수) + px
        var chartHeight = 115 + (15 * col) + "px"; // 최소값 : 31일땐 580 / 30일땐 550 / 1일떈 130(더 작게도 가능함)
        $("#mobileDaySaleDlvrBarChart4").css("height", chartHeight);

        $scope.searchMobileDaySaleDlvrChart4(data);
    });

    $scope.searchMobileDaySaleDlvrChart4 = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;
        params.gubun = "4";

        $scope._inquirySub("/mobile/sale/status/daySale/daySale/getMobileDaySaleDlvrChartList.sb", params);
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
app.controller('mobileDaySaleDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDaySaleDtlCtrl', $scope, $http, false));

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
        dataItem.saleDate = messages["mobile.daySale.saleDate"];
        dataItem.totSaleAmt = messages["mobile.daySale.totSaleAmt"];
        dataItem.totDcAmt = messages["mobile.daySale.totDcAmt"];
        dataItem.totRealSaleAmt = messages["mobile.daySale.totRealSaleAmt"];
        dataItem.shopRealSaleAmt = messages["mobile.daySale.shop"];
        dataItem.shopBillCnt = messages["mobile.daySale.shop"];
        dataItem.shopBillUprc = messages["mobile.daySale.shop"];
        dataItem.dlvrRealSaleAmt = messages["mobile.daySale.dlvr"];
        dataItem.dlvrBillCnt = messages["mobile.daySale.dlvr"];
        dataItem.dlvrBillUprc = messages["mobile.daySale.dlvr"];
        dataItem.packRealSaleAmt = messages["mobile.daySale.pack"];
        dataItem.packBillCnt = messages["mobile.daySale.pack"];
        dataItem.packBillUprc = messages["mobile.daySale.pack"];
        dataItem.totGuestCnt = messages["mobile.daySale.totGuestCnt"];
        dataItem.guestUprc = messages["mobile.daySale.guestUprc"];
        dataItem.cardAmt = messages["mobile.daySale.pay"];
        dataItem.cashAmt = messages["mobile.daySale.pay"];
        dataItem.etcAmt = messages["mobile.daySale.pay"];
        dataItem.dcAmt = messages["mobile.daySale.dc"];
        dataItem.coupnDcAmt = messages["mobile.daySale.dc"];
        dataItem.etcDcAmt = messages["mobile.daySale.dc"];

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
    $scope.$on("mobileDaySaleDtlCtrl", function(event, data) {
        $scope.searchMobileDaySaleDtl(data);
        event.preventDefault();
    });

    $scope.searchMobileDaySaleDtl = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/daySale/daySale/getMobileDaySaleDtlList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileDaySaleDtl", $scope.flexMobileDaySaleDtl, "Y");
        }, false);
    };
    // <-- //검색 호출 -->

    // 일자별 매출현황 엑셀다운로드
    $("#btnExcelMobileDaySaleDtl").on("click", function(event) {

        if ($scope.flexMobileDaySaleDtl.rows.length <= 0) {
          $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
          return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
          wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flexMobileDaySaleDtl, {
            includeColumnHeaders: true,
            includeCellStyles: true,
            includeColumns: function (column) {
              return column.visible;
            }
          },  messages["mobile.daySale"] + '_' + messages["mobile.daySale.saleDtl"] + '_' + getToday() + '.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
        }, 10);
        event.stopPropagation();
    });
    
}]);