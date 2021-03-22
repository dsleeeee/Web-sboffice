/****************************************************************
 *
 * 파일명 : foodAllergyProd.js
 * 설  명 : 알레르기-상품 등록 팝업 JavaScript
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

/**
 *  상품조회 그리드 생성
 */
app.controller('foodAllergyProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('foodAllergyProdCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("foodAllergyProdCtrl", function(event, data) {
        // label에 담은 이유 : 분류조회 후 검색시, 데이터가 날라가버림 / 검색에 1을 빼자니 저장클릭 안됨
        $("#lblFoodAllergyProdRecipesCd").text(data.recipesCd);
        $("#lblFoodAllergyProRecipesNm").text(data.recipesNm);
        $("#lblFoodAllergyProdAllergieNm").text(data.allergieNm);
        $("#lblFoodAllergyProdhqBrandCd").text(data.hqBrandCd);
        $scope.searchFoodAllergyProd();
        event.preventDefault();
    });

    $scope.searchFoodAllergyProd = function(){
        var params = {};
        params.recipesCd = $("#lblFoodAllergyProdRecipesCd").text();
        params.hqBrandCd = $("#lblFoodAllergyProdhqBrandCd").text();
        if (params.hqBrandCd == '0') {
            params.hqBrandCd = null;
        }

        $scope._inquiryMain("/base/prod/foodAllergy/foodAllergy/getFoodAllergyProdList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    // 저장
    $("#funcSave").click(function(e){
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].status = "I";
                $scope.flex.collectionView.items[i].recipesCd = $("#lblFoodAllergyProdRecipesCd").text();
                if($("#lblFoodAllergyProdhqBrandCd").text() != '0'){
                    $scope.flex.collectionView.items[i].hqBrandCd = $("#lblFoodAllergyProdhqBrandCd").text();
                }
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/foodAllergy/foodAllergy/getFoodAllergyDetailSave.sb", params, function(){ $scope.close() });
    });

    // 팝업 닫기
    $scope.close = function(){
        $scope.wjFoodAllergyProdLayer.hide();

        var params = {};
        params.recipesCd = $("#lblFoodAllergyProdRecipesCd").text();
        params.recipesNm = $("#lblFoodAllergyProRecipesNm").text();
        params.allergieNm = $("#lblFoodAllergyProdAllergieNm").text();

        // 저장기능 수행후 재조회
        $scope._broadcast('foodAllergyDetailCtrl', params);
    };
}]);