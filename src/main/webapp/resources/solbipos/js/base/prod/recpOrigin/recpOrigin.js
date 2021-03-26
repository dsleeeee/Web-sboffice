/****************************************************************
 *
 * 파일명 : recpOrigin.js
 * 설  명 : 원산지관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.07.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 재료-상품 등록에 추가버튼, '재료코드' 미선택시 막을려고 ("V":선택, "N":미선택)
var addSelected = "N";

/**
 *  원산지관리 그리드 생성
 */
app.controller('recpOriginCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('recpOriginCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 재료코드
                if (col.binding === "recipesCd") {
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

                // 재료코드 클릭시 상세정보 조회
                if ( col.binding === "recipesCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.recipesCd = selectedRow.recipesCd;
                    params.recipesNm = selectedRow.recipesNm;
                    params.orgplceNm = selectedRow.orgplceNm;

                    var storeScope = agrid.getScope('recpOriginDetailCtrl');
                    storeScope._broadcast('recpOriginDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });

        // 조회
        $scope.searchRecpOrigin();
    };

    // <-- 검색 호출 -->
    $scope.$on("recpOriginCtrl", function(event, data) {
        $scope.searchRecpOrigin();
        event.preventDefault();
    });

    $scope.searchRecpOrigin = function(){
        var params = {};

        $scope._inquiryMain("/base/prod/recpOrigin/recpOrigin/getRecpOriginList.sb", params, function() {
            addSelected = "N";
            $scope.$apply(function() {
                var storeScope = agrid.getScope('recpOriginDetailCtrl');
                storeScope._gridDataInit();
                storeScope._broadcast('recpOriginDetailCtrl', null);
            });
        }, false);
    };
    // <-- //검색 호출 -->

    // <-- 그리드 행 추가 -->
    $scope.addRow = function() {
        // 파라미터 설정
        var params = {};
        params.status = "I";
        params.gChk = true;
        params.recipesCd="자동채번";
        params.recipesNm = "";
        params.orgplceNm = "";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };
    // <-- //그리드 행 추가 -->

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
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
    // <-- //그리드 행 삭제 -->

    // <-- 그리드 저장 -->
    $scope.save = function() {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].recipesNm === "") {
                $scope._popMsg(messages["recpOrigin.recipesNmBlank"]);
                return false;
            }
            if($scope.flex.collectionView.items[i].orgplceNm === "") {
                $scope._popMsg(messages["recpOrigin.orgplceNmBlank"]);
                return false;
            }
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/recpOrigin/recpOrigin/getRecpOriginSave.sb", params, function(){ $scope.allSearch() });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchRecpOrigin();
    };
    // <-- //그리드 저장 -->
}]);


/**
 *  재료-상품 등록 그리드 생성
 */
app.controller('recpOriginDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('recpOriginDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("recpOriginDetailCtrl", function(event, data) {
        $scope.setSelectedStore(data);

        if(!$.isEmptyObject($scope.selectedStore) ) {
            addSelected = "Y";
        }
        if(addSelected === "Y") {
            $("#lblRecipesCd").text(" ( [ " + $scope.selectedStore.recipesCd + " ]");
            $("#lblRecipesNm").text($scope.selectedStore.recipesNm + " / ");
            $("#lblOrgplceNm").text($scope.selectedStore.orgplceNm + " )");
            $scope.searchRecpOriginDetail();

        } else if(addSelected === "N") {
            $("#lblRecipesCd").text("");
            $("#lblRecipesNm").text("");
            $("#lblOrgplceNm").text("");
        }
        event.preventDefault();
    });

    $scope.searchRecpOriginDetail = function(){
        var params = {};
        params.recipesCd = $scope.selectedStore.recipesCd;

        $scope._inquiryMain("/base/prod/recpOrigin/recpOrigin/getRecpOriginDetailList.sb", params, function() {}, false);
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

    // <-- 추가 -->
    $scope.add = function() {
        if(!$.isEmptyObject($scope.selectedStore) ) {
            addSelected = "Y";
        }

        if(addSelected === "Y") {
            $scope.wjRecpProdLayer.show(true);
            event.preventDefault();
        } else if(addSelected === "N" ) {
                $scope._popMsg(messages["recpOrigin.recipesCdBlank"]); // 재료코드를 선택해주세요.
                return false;
        }
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 재료-상품 등록 팝업 핸들러 추가
        $scope.wjRecpProdLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('recpProdCtrl', $scope.getSelectedStore());
            }, 50)
        });
    });
    // <-- //추가 -->

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
        if(!$.isEmptyObject($scope.selectedStore) ) {
            addSelected = "Y";
        }

        if(addSelected === "Y") {
            $scope._popConfirm(messages["cmm.choo.delete"], function() {
                for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                    var item = $scope.flex.collectionView.items[i];

                    if(item.gChk) {
                        $scope.flex.collectionView.removeAt(i);
                    }
                }

                // 저장
                $scope.delSave();
            });

        } else if(addSelected === "N" ) {
            $scope._popMsg(messages["recpOrigin.recipesCdBlank"]); // 재료코드를 선택해주세요.
            return false;
        }
    };

    $scope.delSave = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            $scope.flex.collectionView.itemsRemoved[i].recipesCd = $scope.selectedStore.recipesCd;
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/recpOrigin/recpOrigin/getRecpOriginDetailSave.sb", params, function(){});
    };
    // <-- //그리드 행 삭제 -->

}]);