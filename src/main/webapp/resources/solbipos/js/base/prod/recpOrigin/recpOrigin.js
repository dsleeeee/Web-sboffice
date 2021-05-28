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
        var url                = '/base/prod/recpOrigin/recpOrigin/getBrandComboList.sb';
        var comboParams        = {};
        comboParams.hqOfficeCd = hqOfficeCd;
        $scope._queryCombo("map", null, "hqBrandFgMap", url, comboParams, "S"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

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
                    params.hqBrandCd = selectedRow.hqBrandCd;

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
        params.hqBrandCd = "선택";

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
            if($scope.flex.collectionView.items[i].recipesNm.length > 100) {
                $scope._popMsg(messages["recpOrigin.recipesNmMax"]);
                return false;
            }
            if($scope.flex.collectionView.items[i].orgplceNm.length > 133) {
                $scope._popMsg(messages["recpOrigin.orgplceNmMax"]);
                return false;
            }
            if($scope.flex.collectionView.items[i].hqBrandCd == '0' || $scope.flex.collectionView.items[i].hqBrandCd == '선택') {
                $scope.flex.collectionView.items[i].hqBrandCd = null;
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
        $scope._save("/base/prod/recpOrigin/recpOrigin/getRecpOriginSave.sb", params, function(){

            $scope.allSearch();

            // 상품-원산지 관리 tab > 등록'/미등록 재료 리스트 초기화
            var scopeMid = agrid.getScope('prodRecpOriginDetailCtrl');
            scopeMid._gridDataInit();
            scopeMid._broadcast('prodRecpOriginDetailCtrl', null);

            var scopeRight = agrid.getScope('prodRecpOriginRegCtrl');
            scopeRight._gridDataInit();
            scopeRight._broadcast('prodRecpOriginRegCtrl', null);

            // 재료명, 원사지명 조회조건 textbox 초기화
            scopeRight.reset();

        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchRecpOrigin();
    };
    // <-- //그리드 저장 -->

    // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
    // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
    // comboId : combo 생성할 ID
    // gridMapId : grid 에서 사용할 Map ID
    // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
    // params : 데이터 조회할 url에 보낼 파라미터
    // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
    // callback : queryCombo 후 callback 할 함수
    $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
        var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
        if (url) {
            comboUrl = url;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : comboUrl, /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list       = response.data.data.list;
                    var comboArray = [];
                    var comboData  = {};

                    if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
                        comboArray = [];
                        if (option === "A") {
                            comboData.name  = messages["cmm.all"];
                            comboData.value = "";
                            comboArray.push(comboData);
                        } else if (option === "S") {
                            comboData.name  = messages["cmm.select"];
                            comboData.value = "";
                            comboData.id    = "0";
                            comboArray.push(comboData);
                        }

                        for (var i = 0; i < list.length; i++) {
                            comboData       = {};
                            comboData.name  = list[i].nmcodeNm;
                            comboData.value = list[i].nmcodeCd;
                            comboArray.push(comboData);
                        }
                        $scope._setComboData(comboId, comboArray);
                    }

                    if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
                        comboArray = [];
                        comboData      = {};
                        comboData.id   = "0";
                        comboData.name = "선택";
                        comboArray.push(comboData);

                        for (var i = 0; i < list.length; i++) {
                            comboData      = {};
                            comboData.id   = list[i].nmcodeCd;
                            comboData.name = list[i].nmcodeNm;
                            comboArray.push(comboData);
                        }
                        $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
                    }
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {
            if (typeof callback === 'function') {
                $timeout(function () {
                    callback();
                }, 10);
            }
        });
    };
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