/****************************************************************
 *
 * 파일명 : dayPeriodTime.js
 * 설  명 : 기간별매출 > 설정기간별탭 > 시간대별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.01.30     김설아      1.0
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
 *  시간대별 매출 조회 그리드 생성
 */
app.controller('dayPeriodTimeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayPeriodTimeCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayPeriodTime", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayPeriodTime", gvEndDate);

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

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayPeriodTimeCtrl");

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "realSaleAmt") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 실매출 클릭시 상세정보 조회
                if ( col.binding === "realSaleAmt") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.saleTime  = selectedRow.saleTime;
                    params.storeCds = $("#dayPeriodTimeStoreCd").val();
                    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
                    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
                    params.optionFg = $("input[name=optionFg]:checked").val();

                    var storeScope = agrid.getScope('dayPeriodTimeDetailCtrl');
                    storeScope._broadcast('dayPeriodTimeDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("dayPeriodTimeCtrl", function(event, data) {
        $scope.searchDayPeriodTime();
        event.preventDefault();
    });

    $scope.searchDayPeriodTime = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.storeCds = $("#dayPeriodTimeStoreCd").val();
        params.startTime = $scope.startTime;
        params.endTime = $scope.endTime;
        params.optionFg = $("input[name=optionFg]:checked").val();
        params.timeSlot = $scope.timeSlot;

        $scope._inquiryMain("/sale/day/dayPeriod/dayPeriod/getDayPeriodTimeList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('dayPeriodTimeDetailCtrl');
                storeScope._gridDataInit();   // 그리드 초기화
            });
        }, false);
    };
    // <-- //검색 호출 -->

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

    // 시간대별 엑셀 다운로드
    $scope.excelDownloadPeriodSaleTime = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                messages["dayperiod.dayPeriod"] + '(' + messages["dayPeriod.time"] + ')_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);

/**
 *  시간대별 매출 상세조회 그리드 생성
 */
app.controller('dayPeriodTimeDetailCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayPeriodTimeDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayPeriodTimeDetailCtrl");

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("dayPeriodTimeDetailCtrl", function(event, data) {
        $scope.searchDayPeriodTimeDetail(data);
        event.preventDefault();
    });

    $scope.searchDayPeriodTimeDetail = function(data) {
        var params = {};
        params.saleTime = data.saleTime;
        params.storeCds = data.storeCds;
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.optionFg = data.optionFg;
        params.timeSlot = data.timeSlot;

                $scope._inquiryMain("/sale/day/dayPeriod/dayPeriod/getDayPeriodTimeDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매출상세 엑셀 다운로드
    $scope.excelDownloadPeriodSaleDtl = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                messages["dayperiod.dayPeriod"] + '(' + messages["dayPeriod.time"] + '_' + messages["dayPeriod.saleDtl"] + ')_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);