/****************************************************************
 *
 * 파일명 : dayOfWeekProdClass.js
 * 설  명 : 기간별매출 > 요일별탭 > 상품분류별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.03.19     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 분류표시 DropBoxDataMap
var vLevel = [
    {"name":"1레벨","value":"1"},
    {"name":"2레벨","value":"2"},
    {"name":"3레벨","value":"3"}
];
var vLevelNo = [
    {"name":"분류없음","value":"0"}
];

/**
 *  상품분류별 매출 조회 그리드 생성
 */
app.controller('dayOfWeekProdClassCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayOfWeekProdClassCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayOfWeekProdClass", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayOfWeekProdClass", gvEndDate);

    if(maxLevel == 0) {
        // 조회조건 콤보박스 데이터 Set
        $scope._setComboData("srchDayOfWeekProdClassLevelCombo", vLevelNo);
    } else {
        // 본사/매장 별 사용하는 최대 분류레벨 값을 파악하여 분류표시 selectBox 선택 값 셋팅
        if(maxLevel == 1) {
            vLevel.splice(1,2);
        } else if (maxLevel == 2) {
            vLevel.splice(2,1);
        }
        // 조회조건 콤보박스 데이터 Set
        $scope._setComboData("srchDayOfWeekProdClassLevelCombo", vLevel);
    }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayOfWeekProdClassCtrl");

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

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
    $scope.$on("dayOfWeekProdClassCtrl", function(event, data) {
        $scope.searchDayOfWeekProdClass();
        event.preventDefault();
    });

    $scope.searchDayOfWeekProdClass = function() {

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1년(365일) 제한
        if (diffDay > 365) {
            $scope._popMsg(messages['cmm.dateOver.1year.error']);
            return false;
        }

        var grid = wijmo.Control.getControl("#wjDayOfWeekProdClassList");
        var columns = grid.columns;
        var arr = $("#hdDayOfWeekProdClassNm").val().split(",");

        // 기존에 조회된 컬럼 제거
        if(columns.length > 4) {
            var removeItem = [];
            for (var j = 4; j < columns.length; j++) {
                removeItem[j-4] = columns[j].binding;
            }
            for (var q = 0; q < removeItem.length; q++) {
                columns.remove(removeItem[q]);
            }
        }

        // 분류별 실매출, 수량 컬럼 생성
        if(arr.length > 0 && $("#hdDayOfWeekProdClassNm").val() !== "") {
            for (var i = 1; i <= arr.length; i++) {
                columns.push(new wijmo.grid.Column({ header: messages["dayofweek.realSaleAmt"], binding : 'pay' + i + 'RealSaleAmt', align: "right", isReadOnly: "true", aggregate: "Sum"}));
                columns.push(new wijmo.grid.Column({ header: messages["dayofweek.saleQty"], binding : 'pay' + i + 'SaleQty', align: "right", isReadOnly: "true", aggregate: "Sum"}));
            }

            // 분류별 실매출, 수량 컬럼 헤더머지
            var dataItem         = {};
            dataItem.yoil        = messages["dayofweek.yoil"];
            dataItem.storeCnt    = messages["dayofweek.storeCnt"];
            dataItem.totRealSaleAmt  = messages["dayofweek.totRealSaleAmt"];
            dataItem.totSaleQty    = messages["dayofweek.totSaleQty"];

            for (var i = 1; i <= arr.length; i++) {
                dataItem['pay' + i + "RealSaleAmt"] = arr[i-1];
                dataItem['pay' + i + "SaleQty"] = arr[i-1];
            }
            grid.columnHeaders.rows[0].dataItem = dataItem
        }

        // 상품분류별 매출 조회
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCds = $("#dayOfWeekProdClassStoreCd").val();
        params.strProdClassCd = $("#hdDayOfWeekProdClassCd").val();

        $scope._inquiryMain("/sale/day/dayOfWeek/dayOfWeek/getDayOfWeekProdClassList.sb", params, function() {});

    };
    // <-- //검색 호출 -->

    // 분류 표시 레젤 SelectBox 선택 시
    $scope.setDayOfWeekProdClass = function(s){

        // 파라미터
        var params= {};
        params.level = s.selectedValue;

        $.postJSON("/sale/day/day/dayProdClass/level.sb", params, function(result) {

            var data = result.data.list;
            var strCd = "";
            var strNm = "";

            for(var i=0; i<data.length; i++) {
                strCd += data[i].prodClassCd + ",";
                strNm += data[i].prodClassNm + ",";
            }

            $("#hdDayOfWeekProdClassCd").val(strCd.substr(0, strCd.length - 1));
            $("#hdDayOfWeekProdClassNm").val(strNm.substr(0, strNm.length - 1));
        },
        function(result){
            s_alert.pop(result.message);
        });
    };

    // 상품분류정보 팝업
    $scope.popUpDayOfWeekProdClass = function() {
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
    $scope.delDayOfWeekProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {

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
                messages["dayofweek.dayofweek"] + '(' + messages["dayofweek.prodClassSale"] + ')_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };
}]);