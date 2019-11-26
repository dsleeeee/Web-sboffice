/****************************************************************
 *
 * 파일명 : agencyList.js
 * 설  명 : 설치관리 업체현황탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.10.15     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 DropBoxDataMap
var useYnFgData = [
    {"name":"전체","value":""},
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];
// 업체구분 DropBoxDataMap
var agencyFgData = [
    {"name":"전체","value":""},
    {"name":"자사","value":"1"},
    {"name":"대리점","value":"2"}
];

/**
 *  업체현황 그리드 생성
 */
app.controller('agencyListCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('agencyListCtrl', $scope, $http, true));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYn", useYnFgData); //사용여부
    $scope._setComboData("agencyFg", agencyFgData); //업체구분

    // 검색조건에 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#startDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#endDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); //사용여부

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "restCnt") {
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }

                if (col.binding === "saleStoreCnt") {
                    var item = s.rows[e.row].dataItem;
                    if (item.saleStoreCnt != "0") {
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

                // 잔여건수 클릭시 상세정보 조회
                if ( col.binding === "restCnt") {
                    $scope.setSelectedStore(s.rows[ht.row].dataItem);
                    $scope.agencyDtlLayer.show(true);
                    event.preventDefault();
                }

                // 매출매장수 클릭시 상세정보 조회
                if ( col.binding === "saleStoreCnt") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.agencyCd = selectedRow.agencyCd;
                    params.agencyNm  = selectedRow.agencyNm;
                    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
                    params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

                    if (selectedRow.saleStoreCnt != "0") {
                        $scope._broadcast('agencyPurchsCtrl', params);
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("agencyListCtrl", function(event, data) {
        $scope.searchAgencyList();
        event.preventDefault();
    });

    // 업체현황 그리드 조회
    $scope.searchAgencyList = function() {
        var params = {};
        params.agencyCd = $("#srchAgencyCd").val();
        params.agencyNm = $("#srchAgencyNm").val();
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.orgnCd = orgnCd;
        params.orgnFg = orgnFg;
        params.listScale = $scope.listScaleAgency;

        $scope._inquiryMain("/pos/license/instlManage/instlManage/getAgencyList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedStore;
    $scope.setSelectedStore = function(store) {
        $scope.selectedStore = store;
    };
    $scope.getSelectedStore = function(){
        return $scope.selectedStore;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 잔여건수 상세정보 팝업 핸들러 추가
        $scope.agencyDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('agencyDtlCtrl', $scope.getSelectedStore());
            }, 50)
        });
    });

}]);