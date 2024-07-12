/****************************************************************
 *
 * 파일명 : dayClose.js
 * 설  명 : 광운대일마감 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.09.07     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var vSortFg = [
//	{"name":"매장형태","value":"1"}
    {"name":"직영구분","value":"1"}
    ,{"name":"매장용도","value":"2"}
];

var closeFgData = [
    {"name":"전체","value":""},
    {"name":"미마감","value":"0"},
    {"name":"마감","value":"1"}
];

/** 일별종합 controller */
app.controller('dayCloseCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayCloseCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#closeMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    $scope._setComboData("closeFg", closeFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 DataMap 설정
        $scope.closeFgDataMap = new wijmo.grid.DataMap(closeFgData, 'value', 'name'); // 구분

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayCloseCtrl");

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 마감일자
                if (col.binding === "closeDate") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }

                // [출납현황] / [계좌현황]
                if (col.binding === "statusCashInAmt" || col.binding === "statusCashOutAmt" || col.binding === "statusCashTotalAmt"
                            || col.binding === "statusCardInAmt" || col.binding === "statusCardOutAmt" || col.binding === "statusCardTotalAmt"
                            || col.binding === "accountStatusMainHanaInAmt" || col.binding === "accountStatusMainHanaOutAmt" || col.binding === "accountStatusMainHanaTotalAmt"
                            || col.binding === "accountStatusCardHanaInAmt" || col.binding === "accountStatusCardHanaOutAmt" || col.binding === "accountStatusCardHanaTotalAmt"
                            || col.binding === "accountStatusCardKbInAmt" || col.binding === "accountStatusCardKbOutAmt" || col.binding === "accountStatusCardKbTotalAmt"
                            || col.binding === "accountStatusSpHanaInAmt" || col.binding === "accountStatusSpHanaOutAmt" || col.binding === "accountStatusSpHanaTotalAmt") {
                    var item = s.rows[e.row].dataItem;
                    if (item.closeFg == "0" || item.closeFg == "1") {
                        wijmo.removeClass(e.cell, 'wj-custom-readonly');
                        s.rows[e.row].isReadOnly = false;
                    } else {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        s.rows[e.row].isReadOnly = true;
                    }
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                //  수량합계 클릭시 상세정보 조회
                if ( col.binding === "closeDate") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.storeCd      = selectedRow.storeCd;
                    params.storeNm      = selectedRow.storeNm;
                    params.closeDate    = selectedRow.closeDate;
                    params.openDate     = selectedRow.openDate;
                    params.closeFg      = selectedRow.closeFg;
                    params.interestAmt  = selectedRow.interestAmt;
                    params.inAmt        = selectedRow.inAmt;
                    params.outAmt       = selectedRow.outAmt;
                    params.groupAmt     = selectedRow.groupAmt;
                    params.hockeyAmt    = selectedRow.hockeyAmt;
                    params.etcAmt       = selectedRow.etcAmt;
                    params.inDayAmt     = selectedRow.inDayAmt;
                    params.inSum        = selectedRow.inSum;
                    params.inMonthSum   = selectedRow.inMonthSum;
                    params.inBMonthSum  = selectedRow.inBMonthSum;
                    params.inTotalSum   = selectedRow.inTotalSum;
                    params.outSum       = selectedRow.outSum;
                    params.outMonthSum  = selectedRow.outMonthSum;
                    params.outBMonthSum = selectedRow.outBMonthSum;
                    params.outTotalSum  = selectedRow.outTotalSum;

                    params.statusCashInAmt = selectedRow.statusCashInAmt;
                    params.statusCashOutAmt = selectedRow.statusCashOutAmt;
                    params.statusCashTotalAmt = selectedRow.statusCashTotalAmt;
                    params.statusCardInAmt = selectedRow.statusCardInAmt;
                    params.statusCardOutAmt = selectedRow.statusCardOutAmt;
                    params.statusCardTotalAmt = selectedRow.statusCardTotalAmt;
                    params.accountStatusMainHanaInAmt = selectedRow.accountStatusMainHanaInAmt;
                    params.accountStatusMainHanaOutAmt = selectedRow.accountStatusMainHanaOutAmt;
                    params.accountStatusMainHanaTotalAmt = selectedRow.accountStatusMainHanaTotalAmt;
                    params.accountStatusCardHanaInAmt = selectedRow.accountStatusCardHanaInAmt;
                    params.accountStatusCardHanaOutAmt = selectedRow.accountStatusCardHanaOutAmt;
                    params.accountStatusCardHanaTotalAmt = selectedRow.accountStatusCardHanaTotalAmt;
                    params.accountStatusCardKbInAmt = selectedRow.accountStatusCardKbInAmt;
                    params.accountStatusCardKbOutAmt = selectedRow.accountStatusCardKbOutAmt;
                    params.accountStatusCardKbTotalAmt = selectedRow.accountStatusCardKbTotalAmt;
                    params.accountStatusSpHanaInAmt = selectedRow.accountStatusSpHanaInAmt;
                    params.accountStatusSpHanaOutAmt = selectedRow.accountStatusSpHanaOutAmt;
                    params.accountStatusSpHanaTotalAmt = selectedRow.accountStatusSpHanaTotalAmt;

                    params.remark1      = selectedRow.remark1;
                    params.remark2      = selectedRow.remark2;
                    params.remark3      = selectedRow.remark3;
                    params.remark4      = selectedRow.remark4;
                    params.remark5      = selectedRow.remark5;
                    params.remark6      = selectedRow.remark6;

                    $scope.dayCloseDtlLayer.show(true);
                    $scope._broadcast('dayCloseDtlCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dayCloseCtrl", function (event, data) {
        $scope.searchDayClose();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.searchDayClose = function () {
        // 파라미터
        var params = {};
        params.closeDate = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.storeCd = $("#dayCloseStoreCd").val();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/excclc/excclc/dayClose/dayClose/getDayCloseList.sb", params, function (){});
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
                includeCellStyles: true,  //20220427 false 엑섹속도 cell 스타일 확인중
                includeColumns: function (column) {
                  return column.visible;
                }
            }, messages["dayClose.dayCloseKwu"] + '_'+ getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    // <-- 그리드 저장 -->
    $scope.save = function() {
        // [출납현황] / [계좌현황] 만 저장됩니다. 저장하시겠습니까?
        $scope._popConfirm(messages["dayClose.statusSaveAlert"] + " " + messages["cmm.choo.save"], function () {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                if($scope.flex.collectionView.itemsEdited[i].closeFg == "0" || $scope.flex.collectionView.itemsEdited[i].closeFg == "1") {
                    $scope.flex.collectionView.itemsEdited[i].status = "U";
                    $scope.flex.collectionView.itemsEdited[i].closeDate = $scope.flex.collectionView.itemsEdited[i].closeDate.replaceAll("-", "");
                    params.push($scope.flex.collectionView.itemsEdited[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/excclc/excclc/dayClose/dayClose/getDayCloseSave.sb", params, function(){
                // 조회
                $scope.searchDayClose();
            });
        });
    };
    // <-- //그리드 저장 -->

}]);