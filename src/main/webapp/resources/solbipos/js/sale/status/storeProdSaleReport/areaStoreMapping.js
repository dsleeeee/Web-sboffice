/****************************************************************
 *
 * 파일명 : areaStoreMapping.js
 * 설  명 : 지역-매장관리 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.03     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매장관리 등록에 추가버튼, '지역코드' 미선택시 막을려고 ("V":선택, "N":미선택)
var addSelected = "N";

/**
 *  지역관리 그리드 생성
 */
app.controller('areaStoreMappingCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('areaStoreMappingCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 지역코드
                if (col.binding === "areaCd") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 지역코드 클릭시 상세정보 조회
                if ( col.binding === "areaCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params = {};
                    params.branchCd = selectedRow.branchCd;
                    params.areaCd = selectedRow.areaCd;
                    params.areaNm = selectedRow.areaNm;

                    var storeScope = agrid.getScope('areaStoreMappingDetailCtrl');
                    storeScope._broadcast('areaStoreMappingDetailCtrl', params);

                    var storeScope1 = agrid.getScope('areaStoreMappingStoreCtrl');
                    storeScope1._broadcast('areaStoreMappingStoreCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("areaStoreMappingCtrl", function(event, data) {
        $scope.searchAreaStoreMapping();
        event.preventDefault();
    });

    $scope.searchAreaStoreMapping = function(){
        var params = {};

        $scope._inquiryMain("/sale/status/storeProdSaleReport/areaStoreMapping/getAreaStoreMappingList.sb", params, function() {
            addSelected = "N";
            $scope.$apply(function() {
                var storeScope = agrid.getScope('areaStoreMappingDetailCtrl');
                storeScope._gridDataInit();
                storeScope._broadcast('areaStoreMappingDetailCtrl', null);

                var storeScope1 = agrid.getScope('areaStoreMappingStoreCtrl');
                storeScope1._gridDataInit();
                storeScope1._broadcast('areaStoreMappingStoreCtrl', null);
            });
        }, false);
    };
    // <-- //검색 호출 -->
}]);


/**
 *  지역-매장관리 그리드 생성
 */
app.controller('areaStoreMappingDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('areaStoreMappingDetailCtrl', $scope, $http, false));


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("areaStoreMappingDetailCtrl", function(event, data) {
        $scope.setSelectedAreaStoreMapping(data);

        if(!$.isEmptyObject($scope.selectedAreaStoreMapping) ) {
            addSelected = "Y";
        }
        if(addSelected === "Y") {
            $("#lblAreaCd").text(" ( [ " + $scope.selectedAreaStoreMapping.areaCd + " ] ");
            $("#lblAreaNm").text($scope.selectedAreaStoreMapping.areaNm + " )");
            $scope.searchAreaStoreMappingDetail();

        } else if(addSelected === "N") {
            $("#lblAreaCd").text("");
            $("#lblAreaNm").text("");
        }
        event.preventDefault();
    });

    $scope.searchAreaStoreMappingDetail = function(){
        var params = {};
        params.branchCd = $scope.selectedAreaStoreMapping.branchCd;
        params.areaCd = $scope.selectedAreaStoreMapping.areaCd;

        $scope._inquiryMain("/sale/status/storeProdSaleReport/areaStoreMapping/getAreaStoreMappingDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedAreaStoreMapping;
    $scope.setSelectedAreaStoreMapping = function(store) {
        $scope.selectedAreaStoreMapping = store;
    };
    $scope.getSelectedAreaStoreMapping = function(){
        return $scope.selectedAreaStoreMapping;
    };

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
        if(addSelected === "N") {
            $scope._popMsg(messages["areaStoreMapping.areaCdAlert"]); // 지역코드을 선택해주세요.
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];

                if(item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 저장
            $scope.save();
        });
    };

    $scope.save = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sale/status/storeProdSaleReport/areaStoreMapping/getAreaStoreMappingDetailSave.sb", params, function(){
            $scope.allSearch();
        });
    };
    // <-- //그리드 행 삭제 -->

    // 재조회
    $scope.allSearch = function () {
        $scope.searchAreaStoreMappingDetail();

        var storeScope = agrid.getScope('areaStoreMappingStoreCtrl');
        storeScope.searchAreaStoreMappingStore();
    };
}]);


/**
 *  매장관리 그리드 생성
 */
app.controller('areaStoreMappingStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('areaStoreMappingStoreCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("sysStatFgCombo", sysStatFgComboData); // 매장상태구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgComboData, 'value', 'name'); // 매장상태구분
    };

    // <-- 검색 호출 -->
    $scope.$on("areaStoreMappingStoreCtrl", function(event, data) {
        $scope.setSelectedAreaStoreMappingStore(data);

        if(!$.isEmptyObject($scope.selectedAreaStoreMappingStore) ) {
            addSelected = "Y";
        }
        if(addSelected === "Y") {
            $scope.searchAreaStoreMappingStore();
        }
        event.preventDefault();
    });

    $scope.searchAreaStoreMappingStore = function(){
        var params = {};
        params.branchCd = $scope.selectedAreaStoreMappingStore.branchCd;
        params.areaCd = $scope.selectedAreaStoreMappingStore.areaCd;

        $scope._inquiryMain("/sale/status/storeProdSaleReport/areaStoreMapping/getAreaStoreMappingStoreList.sb", params, function() {}, false);
    };

    $scope.search = function(){
        $scope.searchAreaStoreMappingStore();
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedAreaStoreMappingStore;
    $scope.setSelectedAreaStoreMappingStore = function(store) {
        $scope.selectedAreaStoreMappingStore = store;
    };
    $scope.getSelectedAreaStoreMappingStore = function(){
        return $scope.selectedAreaStoreMappingStore;
    };

    // <-- 그리드 저장 -->
    $scope.save = function() {
        if(addSelected === "N") {
            $scope._popMsg(messages["areaStoreMapping.areaCdAlert"]); // 지역코드을 선택해주세요.
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].branchCd = $scope.selectedAreaStoreMappingStore.branchCd;
                    $scope.flex.collectionView.items[i].areaCd = $scope.selectedAreaStoreMappingStore.areaCd;
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            if(params.length <= 0) {
                s_alert.pop(messages["cmm.not.select"]);
                return;
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/sale/status/storeProdSaleReport/areaStoreMapping/getAreaStoreMappingStoreSave.sb", params, function(){
                $scope.allSearch();
            });
        });
    };
    // <-- //그리드 저장 -->

    // 재조회
    $scope.allSearch = function () {
        $scope.searchAreaStoreMappingStore();

        var storeScope = agrid.getScope('areaStoreMappingDetailCtrl');
        storeScope.searchAreaStoreMappingDetail();
    };
}]);