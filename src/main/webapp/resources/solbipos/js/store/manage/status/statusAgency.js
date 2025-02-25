/****************************************************************
 *
 * 파일명 : statusAgency.js
 * 설  명 : 매장현황 관리업체탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.09.24     김설아      1.0
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
    {"name": "전체", "value": "0"},
    {"name": "자사", "value": "1"},
    {"name": "대리점", "value": "2"}
];

/**
 *  관리업체 조회 그리드 생성
 */
app.controller('statusAgencyCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusAgencyCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._getComboDataQuery('001', 'clsFg', 'A');
    $scope._getComboDataQuery('005', 'sysStatFg', 'A');
    $scope._setComboData("agencyFg", agencyFgData); //업체구분
    $scope._setComboData("useYn", useYnFgData); //사용여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "agencyNm") {
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
                if ( col.binding === "agencyNm") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var storeScope = agrid.getScope('statusAgencyDetailCtrl');
                    storeScope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
                    storeScope._broadcast('statusAgencyDetailCtrl', selectedRow);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("statusAgencyCtrl", function(event, data) {
        $scope.searchStatusAgency();
        event.preventDefault();
    });

    // 관리업체 그리드 조회
    $scope.searchStatusAgency = function() {
        var params = {};
        params.orgnFg = orgnFg;
        params.pAgencyCd = pAgencyCd;
        params.agencyCd = orgnCd;
        params.listScale = 10;
        params.srchAgencyCd = $("#srchAgencyCd").val();
        params.srchAgencyNm = $("#srchAgencyNm").val();

        $scope._inquiryMain("/store/manage/status/agency/getStatusAgencyList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('statusAgencyDetailCtrl');
                storeScope._gridDataInit();

                // 매장 grid paging 초기화(숨기기.. 아예 없애는거 모름..)
                var statusAgencyDetailCtrlPager = document.getElementById('statusAgencyDetailCtrlPager');
                statusAgencyDetailCtrlPager.style.visibility='hidden'
            });
        }, false);
    };
    // <-- //검색 호출 -->

}]);


/**
 *  관리업체 상세조회 그리드 생성
 */
app.controller('statusAgencyDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusAgencyDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFgData, 'value', 'name'); //용도구분
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태구분
    };

    // <-- 검색 호출 -->
    $scope.$on("statusAgencyDetailCtrl", function(event, data) {
        if( !isEmptyObject(data) ) {
            $scope.setSelectedStoreDetail(data);
        }
        $scope.searchStatusAgencyDetail();
        event.preventDefault();
    });

    // 관리업체 상세 그리드 조회
    $scope.searchStatusAgencyDetail = function() {
        var params = {};
        params.agencyCd = $scope.selectedStoreDetail.agencyCd;
        params.clsFg = $scope.selectedStoreDetail.clsFg;
        params.sysStatFg = $scope.selectedStoreDetail.sysStatFg;
        params.listScale = 30;

        $scope._inquiryMain("/store/manage/status/agency/getStatusAgencyDetailList.sb", params, function() {
            // 매장 grid 보이도록
            var statusAgencyDetailCtrlPager = document.getElementById('statusAgencyDetailCtrlPager');
            statusAgencyDetailCtrlPager.style.visibility='visible'
        }, false);
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