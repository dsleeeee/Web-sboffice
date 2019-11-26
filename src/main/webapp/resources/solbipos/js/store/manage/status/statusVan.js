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

/**
 *  VAN사 조회 그리드 생성
 */
app.controller('statusVanCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusVanCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("clsFg", clsFgData); //용도
    $scope._setComboData("sysStatFg", sysStatFgData); //상태

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
                    $scope.setSelectedStore(s.rows[ht.row].dataItem);
                    var storeScope = agrid.getScope('statusVanDetailCtrl');
                    storeScope._broadcast('statusVanDetailCtrl', $scope.getSelectedStore());
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
            });
        }, false);
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
            $scope.setSelectedStoreDetail(data);
        }
        $scope.searchStatusVanDetail();
        event.preventDefault();
    });

    // VAN사 상세 그리드 조회
    $scope.searchStatusVanDetail = function() {
        var params = {};
        params.vanCd = $scope.selectedStoreDetail.vanCd;
        params.clsFg = $scope.selectedStoreDetail.clsFg;
        params.sysStatFg = $scope.selectedStoreDetail.sysStatFg;
        params.orgnFg = orgnFg;
        params.pAgencyCd = pAgencyCd;
        if(orgnFg != null && orgnFg == 'AGENCY') {
            params.agencyCd = orgnCd;
        }
        params.listScale = 30;

        $scope._inquiryMain("/store/manage/status/van/getStatusVanDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedStoreDetail;
    $scope.setSelectedStoreDetail = function(store) {
        $scope.selectedStoreDetail = store;
    };
    $scope.getSelectedStoreDetail = function(){
        return $scope.selectedStoreDetail;
    };
}]);