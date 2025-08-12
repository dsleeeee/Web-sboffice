/****************************************************************
 *
 * 파일명 : menuSaleMrpizza.js
 * 설  명 : 미스터피자 > 마케팅조회 > 메뉴별매출 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.25     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 배달주문구분
/*var dlvrOrderFgData = [
    {"name": "일반", "value": "1"},
    {"name": "배달", "value": "2"},
    {"name": "포장", "value": "3"},
    {"name": "포장", "value": "4"}
];*/

/**
 *  메뉴별매출 그리드 생성
 */
app.controller('menuSaleMrpizzaCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('menuSaleMrpizzaCtrl', $scope, $http, false));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "yoil") {
                    if (item.yoil === "토") {
                        wijmo.addClass(e.cell, 'blue');
                    } else if (item.yoil === "일") {
                        wijmo.addClass(e.cell, 'red');
                    }
                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("menuSaleMrpizzaCtrl", function (event, data) {

        // 메뉴별매출 리스트 조회
        $scope.searchMenuSaleList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 메뉴별매출 리스트 조회
    $scope.searchMenuSaleList = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if (startDt.getTime() > endDt.getTime()) {
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        if ($("#menuSaleMrpizzaStoreCd").val().length > 0 && $("#menuSaleMrpizzaStoreCd").val().split(",").length - 1 === 0) {
            // 조회일자 최대 3달(93일) 제한
            if (diffDay > 93) {
                $scope._popMsg(messages['cmm.dateOver.3month.error']);
                return false;
            }
        } else {
            // 조회일자 최대 10일 제한
            if (diffDay >= 10) {
                $scope._popMsg(messages['cmm.dateOver.10day.error']);
                return false;
            }
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCds = $("#menuSaleMrpizzaStoreCd").val();
        params.prodCds = $("#menuSaleMrpizzaSelectCd").val();
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/mrpizza/menuSaleMrpizza/getMenuSaleMrpizzaList.sb", params, function () {

            // 조회날짜 기준 엑셀 다운로드 기간
            $scope.excelStartDate = params.startDate;
            $scope.excelEndDate   = params.endDate;
        });

    };

    // 상품선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.menuSaleMrpizzaSelectShow = function () {
        $scope._broadcast('menuSaleMrpizzaSelectCtrl');
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {

        // 선택취소 값 전달
        $scope._broadcast('prodClassCheckPopUpCtrl', {
            selectCancelFg: $("#_selectCancelFg").val()
        });
        var popUp = $scope.prodClassCheckPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassCheckPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd[0];
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/treePopup/getProdClassCdNmCheck.sb", params,
                    function (response) {
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = (isEmptyObject(response.data.data) ? "" : response.data.data) + (prodClassCd.length - 1 > 0 ? " 외 " + (prodClassCd.length - 1).toString() : "");
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function () {
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
        $("#_selectCancelFg").val("Y");
    };

    // 엑셀다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        // 엑셀다운로드 기간
        var startDt = $scope.excelStartDate;
        var endDt = $scope.excelEndDate;

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                "메뉴별판매" + '_' + startDt + '_' + endDt + '_' + getCurDateTime() + '.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    }

}]);