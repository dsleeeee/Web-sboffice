/****************************************************************
 *
 * 파일명 : statusVan.js
 * 설  명 : 매장현황 VAN사탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.09.27     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 업체구분 DropBoxDataMap
var agencyFgData = [
    {"name": "전체", "value": "0"},
    {"name": "자사", "value": "1"},
    {"name": "대리점", "value": "2"}
];

/**
 *  VAN사 조회 그리드 생성
 */
app.controller('statusVanCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusVanCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("clsFg", clsFgData); //용도
    $scope._setComboData("sysStatFg", sysStatFgData); //상태
    $scope._setComboData("agencyFg", agencyFgData); //업체구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "storeCnt") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 업체명 클릭시 상세정보 조회
                if ( col.binding === "storeCnt") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var storeScope = agrid.getScope('statusVanDetailCtrl');
                    storeScope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
                    storeScope._broadcast('statusVanDetailCtrl', selectedRow);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("statusVanCtrl", function(event, data) {
        $scope.searchStatusVan();
        event.preventDefault();
    });

    // VAN사 그리드 조회
    $scope.searchStatusVan = function() {
        var params = {};
        params.orgnFg = orgnFg;
        params.pAgencyCd = pAgencyCd;
        if(orgnFg != null && orgnFg == 'AGENCY') {
            params.agencyCd = orgnCd;
        }

        $scope._inquiryMain("/store/manage/status/van/getStatusVanList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('statusVanDetailCtrl');
                storeScope._gridDataInit();
                // 매장 grid 보이도록
                var statusVanDetailCtrlPager = document.getElementById('statusVanDetailCtrlPager');
                statusVanDetailCtrlPager.style.visibility='hidden'
            });
        }, false);
    };
    // <-- //검색 호출 -->

}]);


/**
 *  VAN사 상세조회 그리드 생성
 */
app.controller('statusVanDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusVanDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFgData, 'value', 'name'); //용도구분
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태구분
    };

    // <-- 검색 호출 -->
    $scope.$on("statusVanDetailCtrl", function(event, data) {
        if( !isEmptyObject(data) ) {
            $scope.setSelectedStoreVanDetail(data);
        }
        $scope.searchStatusVanDetail();
        event.preventDefault();
    });

    // VAN사 상세 그리드 조회
    $scope.searchStatusVanDetail = function() {
        var params = {};
        params.vanCd = $scope.selectedStoreVanDetail.vanCd;
        params.clsFg = $scope.selectedStoreVanDetail.clsFg;
        params.sysStatFg = $scope.selectedStoreVanDetail.sysStatFg;
        params.orgnFg = orgnFg;
        params.pAgencyCd = pAgencyCd;
        if(orgnFg != null && orgnFg == 'AGENCY') {
            params.agencyCd = orgnCd;
        }
        params.listScale = 30;

        $scope._inquiryMain("/store/manage/status/van/getStatusVanDetailList.sb", params, function() {
            var statusVanDetailCtrlPager = document.getElementById('statusVanDetailCtrlPager');
            statusVanDetailCtrlPager.style.visibility='visible'
        }, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedStoreVanDetail;
    $scope.setSelectedStoreVanDetail = function(store) {
        $scope.selectedStoreVanDetail = store;
    };
    $scope.getSelectedStoreVanDetail = function(){
        return $scope.selectedStoreVanDetail;
    };
}]);