/****************************************************************
 *
 * 파일명 : mobileTodaySale.js
 * 설  명 : (모바일) 매출현황 > 당일매출현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.02     김설아      1.0
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
 *  당일매출종합 그리드 생성
 */
app.controller('mobileTodaySaleTotalCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTodaySaleTotalCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTodaySaleTotalCtrl", function(event, data) {
        $scope.searchMobileTodaySaleTotal(data);
        event.preventDefault();
    });

    $scope.searchMobileTodaySaleTotal = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._postJSONQuery.withOutPopUp( "/mobile/sale/status/todaySale/todaySale/getMobileTodaySaleTotalList.sb", params, function(response){
            var mobileTodaySale = response.data.data.result;
            $scope.mobileTodaySale = mobileTodaySale;

            if(response.data.data.result != null) {
                $("#lblSaleCnt").text($scope.mobileTodaySale.saleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblReturnSaleCnt").text($scope.mobileTodaySale.returnSaleCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblBillCnt").text($scope.mobileTodaySale.billCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotSaleAmt").text($scope.mobileTodaySale.totSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotDcAmt").text($scope.mobileTodaySale.totDcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblRealSaleAmt").text($scope.mobileTodaySale.realSaleAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotGuestCnt").text($scope.mobileTodaySale.totGuestCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblGuestUprc").text($scope.mobileTodaySale.guestUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblBillUprc").text($scope.mobileTodaySale.billUprc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblCardAmt").text($scope.mobileTodaySale.cardAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblCashAmt").text($scope.mobileTodaySale.cashAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblEtcAmt").text($scope.mobileTodaySale.etcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblDcAmt").text($scope.mobileTodaySale.dcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblCoupnDcAmt").text($scope.mobileTodaySale.coupnDcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                $("#lblTotalDcAmt").text($scope.mobileTodaySale.totalDcAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
            }
        });
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.mobileTodaySale;
    $scope.setMobileTodaySale = function(store) {
        $scope.mobileTodaySale = store;
    };
    $scope.getMobileTodaySale = function(){
        return $scope.mobileTodaySale;
    };
}]);


/**
 *  결제수단 그리드 생성
 */
app.controller('mobileTodaySaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTodaySaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", getToday());

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 조회
        $scope.searchMobileTodaySalePay();

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 실매출액
                if (col.binding === "realSaleAmt") {
                    var item = s.rows[e.row].dataItem;

                    // 값이 있으면 링크 효과
                    if (nvl(item[("payCd")], '') !== '' && nvl(item[("payCd")], '') != "0") {
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

                // 실매출액 클릭시 상세정보 조회
                if ( col.binding === "realSaleAmt") {
                    var selectedRow = s.rows[ht.row].dataItem;

                    var params = {};
                    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
                    params.endDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
                    params.srchStoreCd = $("#mobileTodaySaleStoreCd").val();

                    var mobileCallCtrl = "";

                    // 값이 있으면 링크
                    if (nvl(selectedRow[("payCd")], '') !== '' && nvl(selectedRow[("payCd")], '') != "0") {
                        mobileCallCtrl = 'mobile' + (selectedRow[("payMethod")].substr(0, 1).toUpperCase() + selectedRow[("payMethod")].substr(1).toLowerCase()).replaceAll("_", "") + 'Ctrl';

                        $scope._broadcast(mobileCallCtrl, params);
                        event.preventDefault();
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTodaySaleCtrl", function(event, data) {
        gridOpen("mobileTodaySaleTotal");
        gridOpen("mobileTodaySalePay");
        gridOpen("mobileTodaySaleDc");
        gridOpen("mobileTodaySaleDlvr");
        gridOpen("mobileTodaySaleTime");

        $scope.searchMobileTodaySalePay();
        event.preventDefault();
    });

    $scope.searchMobileTodaySalePay = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.srchStoreCd = $("#mobileTodaySaleStoreCd").val();

        $scope._inquirySub("/mobile/sale/status/todaySale/todaySale/getMobileTodaySalePayList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileTodaySalePay", $scope.flexMobileTodaySalePay, "Y");

            // 매출종합현황
            $scope._broadcast("mobileTodaySaleTotalCtrl", params);
            // 할인내역
            $scope._broadcast("mobileTodaySaleDcCtrl", params);
            // 내점/배달/포장
            $scope._broadcast("mobileTodaySaleDlvrCtrl", params);
            // 시간대별
            $scope._broadcast("mobileTodaySaleTimeCtrl", params);
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileTodaySaleStoreShow = function () {
        $scope._broadcast('mobileTodaySaleStoreCtrl');
    };

    // 결제수단 엑셀다운로드
    $("#btnExcelMobileTodaySalePay").on("click", function(event) {

        if ($scope.flexMobileTodaySalePay.rows.length <= 0) {
          $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
          return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
          wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flexMobileTodaySalePay, {
            includeColumnHeaders: true,
            includeCellStyles: true,
            includeColumns: function (column) {
              return column.visible;
            }
          },  messages["mobile.todaySale"] + '_' + messages["mobile.todaySale.todaySalePay"] + '_' + getToday() + '.xlsx', function () {
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
app.controller('mobileTodaySaleDcCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTodaySaleDcCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTodaySaleDcCtrl", function(event, data) {
        $scope.searchMobileTodaySaleDc(data);
        event.preventDefault();
    });

    $scope.searchMobileTodaySaleDc = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/todaySale/todaySale/getMobileTodaySaleDcList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileTodaySaleDc", $scope.flexMobileTodaySaleDc, "Y");
        }, false);
    };
    // <-- //검색 호출 -->

    // 할인내역 엑셀다운로드
    $("#btnExcelMobileTodaySaleDc").on("click", function(event) {

        if ($scope.flexMobileTodaySaleDc.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flexMobileTodaySaleDc, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            }, messages["mobile.todaySale"] + '_' + messages["mobile.todaySale.todaySaleDc"] + '_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
        event.stopPropagation();
    });

}]);


/**
 *  내점/배달/포장 그리드 생성
 */
app.controller('mobileTodaySaleDlvrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTodaySaleDlvrCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTodaySaleDlvrCtrl", function(event, data) {
        $scope.searchMobileTodaySaleDlvr(data);
        event.preventDefault();
    });

    $scope.searchMobileTodaySaleDlvr = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/todaySale/todaySale/getMobileTodaySaleDlvrList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 내점/포장/배달 엑셀다운로드
    $("#btnExcelMobileTodaySaleDlvr").on("click", function(event) {

        if ($scope.flexMobileTodaySaleDlvr.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flexMobileTodaySaleDlvr, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            }, messages["mobile.todaySale"] + '_' + messages["mobile.todaySale.todaySaleDlvr"] + '_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
        event.stopPropagation();
    });

}]);


/**
 *  시간대별 그리드 생성
 */
app.controller('mobileTodaySaleTimeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileTodaySaleTimeCtrl', $scope, $http, false));

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

    $scope._setComboData("startTimeCombo", Hh);
    $scope._setComboData("endTimeCombo", Hh);
    $scope.startTime     = "0";
    $scope.endTime       = "23";

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

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileTodaySaleTimeCtrl", function(event, data) {
        $scope.searchMobileTodaySaleTime(data);
        event.preventDefault();
    });

    $scope.searchMobileTodaySaleTime = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.srchStoreCd = data.srchStoreCd;
        params.startTime = $scope.startTime;
        params.endTime = $scope.endTime;
        params.optionFg = $("input[name=optionFg]:checked").val();
        params.timeSlot = $scope.timeSlot;

        $scope._inquirySub("/mobile/sale/status/todaySale/todaySale/getMobileTodaySaleTimeList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 시간대별 엑셀다운로드
    $("#btnExcelMobileTodaySaleTime").on("click", function(event) {

        if ($scope.flexMobileTodaySaleTime.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flexMobileTodaySaleTime, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            }, messages["mobile.todaySale"] + '_' + messages["mobile.todaySale.todaySaleTime"] + '_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
        event.stopPropagation();
    });

}]);