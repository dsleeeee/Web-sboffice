/****************************************************************
 *
 * 파일명 : stockPeriod.js
 * 설  명 : 재고현황(매장-기간별) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.22     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 일자표시옵션
var dayOptionComboData = [
    {"name":"일자별","value":"1"},
    {"name":"기간합","value":"2"}
];

/**
 *  재고현황(매장-기간별) 조회 그리드 생성
 */
app.controller('stockPeriodCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('stockPeriodCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 단위구분
    $scope._setComboData("srchUnitFg", [
        {"name": messages["storeCurr.unitStockFg"], "value": "0"},
        {"name": messages["storeCurr.unitOrderFg"], "value": "1"}
    ]);

    // 콤보박스 셋팅
    $scope._setComboData("dayOptionCombo", dayOptionComboData); // 일자표시옵션

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 상품분류 항목표시
        $scope.ChkProdClassDisplay = false;

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("stockPeriodCtrl");

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("stockPeriodCtrl", function(event, data) {
        $scope.searchStockPeriod();
        event.preventDefault();
    });

    $scope.searchStockPeriod = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCd = $("#stockPeriodSelectStoreCd").val();
        params.dayOption = $scope.dayOption;

        $scope._inquiryMain("/stock/product/stockPeriod/stockPeriod/getStockPeriodList.sb", params, function() {
            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridList");
            var columns = grid.columns;

            // 컬럼 총갯수
            var columnsCnt = 19;

            for (var i = 0; i < columnsCnt; i++) {
                columns[i].visible = true;
            }

            // 합계가 0이면 해당 컬럼 숨기기
            for (var j = 0; j < columnsCnt; j++) {
                // 일자표시옵션
                if(params.dayOption === "1"){  // 일자별
                    if(columns[j].binding == "dayFrom" || columns[j].binding == "dayTo" || columns[j].binding == "currQty") {
                        columns[j].visible = false;
                    }
                } else if(params.dayOption === "2"){  // 기간합
                    if(columns[j].binding == "saleDate" || columns[j].binding == "yoil" || columns[j].binding == "currQty2") {
                        columns[j].visible = false;
                    }
                }

                // 상품분류 항목표시 체크
                if($("input:checkbox[id='chkDt']").is(":checked")){
                } else {
                    if(columns[j].binding == "lv1Nm" || columns[j].binding == "lv2Nm" || columns[j].binding == "lv3Nm") {
                        columns[j].visible = false;
                    }
                }
            }
            // <-- //그리드 visible -->
        }, false);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.stockPeriodSelectStoreShow = function () {
        $scope._broadcast('stockPeriodSelectStoreCtrl');
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    //상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
    $scope.isChkProdClassDisplay = function(){
        var columns = $scope.flex.columns;

        for(var i=0; i<columns.length; i++){
            if(columns[i].binding === 'lv1Nm' || columns[i].binding === 'lv2Nm' || columns[i].binding === 'lv3Nm'){
                $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
            }
        }
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {
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
            }, '재고현황(매장-기간별)_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);