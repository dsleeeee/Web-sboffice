/****************************************************************
 *
 * 파일명 : foodAllergy.js
 * 설  명 : 식품 알레르기 정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.10.06     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 알레르기-상품 등록에 추가버튼, '재료코드' 미선택시 막을려고 ("V":선택, "N":미선택)
var addSelected = "N";

/**
 *  식품 알레르기 정보관리 그리드 생성
 */
app.controller('foodAllergyCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('foodAllergyCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        var url                = '/base/prod/foodAllergy/foodAllergy/getBrandComboList.sb';
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
                    params.allergieNm = selectedRow.allergieNm;
                    params.hqBrandCd = selectedRow.hqBrandCd;
                    var storeScope = agrid.getScope('foodAllergyDetailCtrl');
                    storeScope._broadcast('foodAllergyDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });

        // 조회
        $scope.searchFoodAllergy();
    };

    // <-- 검색 호출 -->
    $scope.$on("foodAllergyCtrl", function(event, data) {
        $scope.searchFoodAllergy();
        event.preventDefault();
    });

    $scope.searchFoodAllergy = function(){
        var params = {};

        $scope._inquiryMain("/base/prod/foodAllergy/foodAllergy/getFoodAllergyList.sb", params, function() {
            addSelected = "N";
            $scope.$apply(function() {
                var storeScope = agrid.getScope('foodAllergyDetailCtrl');
                storeScope._gridDataInit();
                storeScope._broadcast('foodAllergyDetailCtrl', null);
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
        params.allergieNm = "";
        params.hqBrandCd = "선택";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };
    // <-- //그리드 행 추가 -->

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
        $scope._popConfirm(messages["foodAllergy.delConfirm"], function() {
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
                $scope._popMsg(messages["foodAllergy.recipesNmBlank"]);
                return false;
            }
            if($scope.flex.collectionView.items[i].allergieNm === "") {
                $scope._popMsg(messages["foodAllergy.allergieNmBlank"]);
                return false;
            }
            if($scope.flex.collectionView.items[i].recipesNm.length > 100) {
                $scope._popMsg(messages["foodAllergy.recipesNmMax"]);
                return false;
            }
            if($scope.flex.collectionView.items[i].allergieNm.length > 133) {
                $scope._popMsg(messages["foodAllergy.allergieNmMax"]);
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
        $scope._save("/base/prod/foodAllergy/foodAllergy/getFoodAllergySave.sb", params, function(){ $scope.allSearch() });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchFoodAllergy();
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
 *  알레르기-상품 등록 그리드 생성
 */
app.controller('foodAllergyDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('foodAllergyDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("foodAllergyDetailCtrl", function(event, data) {
        $scope.setSelectedStore(data);

        if(!$.isEmptyObject($scope.selectedStore) ) {
            addSelected = "Y";
        }
        if(addSelected === "Y") {
            $("#lblRecipesCd").text(" ( [ " + $scope.selectedStore.recipesCd + " ]");
            $("#lblRecipesNm").text($scope.selectedStore.recipesNm + " / ");
            $("#lblAllergieNm").text($scope.selectedStore.allergieNm + " )");
        } else if(addSelected === "N") {
            $("#lblRecipesCd").text("");
            $("#lblRecipesNm").text("");
            $("#lblAllergieNm").text("");
        }

        $scope.searchFoodAllergyDetail();
        event.preventDefault();
    });

    $scope.searchFoodAllergyDetail = function(){
        var params = {};
        params.recipesCd = $scope.selectedStore.recipesCd;

        $scope._inquiryMain("/base/prod/foodAllergy/foodAllergy/getFoodAllergyDetailList.sb", params, function() {}, false);
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
            $scope.wjFoodAllergyProdLayer.show(true);
            event.preventDefault();
        } else if(addSelected === "N" ) {
            $scope._popMsg(messages["foodAllergy.recipesCdBlank"]);
            return false;
        }
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 알레르기-상품 등록 팝업 핸들러 추가
        $scope.wjFoodAllergyProdLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('foodAllergyProdCtrl', $scope.getSelectedStore());
            }, 50)
        });
    });
    // <-- //추가 -->

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
        $scope._popConfirm(messages["foodAllergy.delConfirm"], function() {
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

    $scope.save = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            $scope.flex.collectionView.itemsRemoved[i].recipesCd = $scope.selectedStore.recipesCd;
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/foodAllergy/foodAllergy/getFoodAllergyDetailSave.sb", params, function(){});
    };
}]);