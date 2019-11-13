/****************************************************************
 *
 * 파일명 : statusStore.js
 * 설  명 : 매장현황 매장탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.09.23     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매장구분 DropBoxDataMap
var storeFgData = [
    {"name":"전체","value":"0"},
    {"name":"단독매장","value":"1"},
    {"name":"프랜차이저","value":"2"}
];

/**
 *  매장정보조회 그리드 생성
 */
app.controller('statusStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusStoreCtrl', $scope, $http, true));

    // 총판계정으로 접속한 경우, 해당 총판의 데이터만 조회되도록 함.
    if(orgnFg === "AGENCY"){
        $("#agencyCd").val(orgnCd);
        $("#agencyNm").val(orgnNm);
    }

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("clsFg", clsFgData); //용도
    $scope._setComboData("sysStatFg", sysStatFgData); //상태
    $scope._setComboData("storeFg", storeFgData); //매장구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFgData, 'value', 'name'); //용도구분
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태구분

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "envstValNm") {

                    var item = s.rows[e.row].dataItem;
                    if (item.envstValNm === "코너개별승인") {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 그리드 선택 이벤트 wijLink
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 코너사용구분 클릭시 상세정보 조회
                if ( col.binding === "envstValNm") {
                    $scope.setSelectedStore(s.rows[ht.row].dataItem);
                    if ( $scope.selectedStore.envstValNm === "코너개별승인") {
                        $scope.statusStoreCornerViewLayer.show(true);
                    }
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("statusStoreCtrl", function(event, data) {
        $scope.searchStatusStore();
        event.preventDefault();
    });

    // 매장정보 그리드 조회
    $scope.searchStatusStore = function() {
        var params = {};
        params.agencyCd = $("#agencyCd").val();
        params.agencyNm = $("#agencyNm").val();
        params.listScale = $scope.listScaleStore;

        $scope._inquiryMain("/store/manage/status/store/getStatusStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    /*********************************************************
     * 관리업체 조회
     * *******************************************************/
    $scope.searchAgency = function(){
        if(orgnFg === "MASTER" || pAgencyCd === "00000") {
            var popup = $scope.agencyLayer;

            // 팝업 닫을때
            popup.show(true, function (s) {
                var agencyScope = agrid.getScope('searchAgencyCtrl');
                agencyScope.$apply(function () {
                    agencyScope._gridDataInit();
                    if (!$.isEmptyObject(agencyScope.getAgency())) {
                        $("#agencyCd").val(agencyScope.getAgency().agencyCd);
                        $("#agencyNm").val(agencyScope.getAgency().agencyNm);
                    }
                });
            });
        }
    };

    /*********************************************************
     * 밴사 조회
     * *******************************************************/
    $scope.searchManageVan = function(){
        var popup = $scope.vanLayer;

        // 팝업 닫을때
        popup.show(true, function (s) {
            var vanScope = agrid.getScope('searchVanCtrl');
            vanScope.$apply(function(){
                vanScope._gridDataInit();
                if( !$.isEmptyObject(vanScope.getVan())  ){
                    $scope.vanCd = vanScope.getVan().vanCd;
                    $scope.vanNm = vanScope.getVan().vanNm;
                }
            });
        });
    };

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

        // 코너 상세정보 팝업 핸들러 추가
        $scope.statusStoreCornerViewLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('statusStoreCornerCtrl', $scope.getSelectedStore());
        }, 50)
        });
    });

}]);