/****************************************************************
 *
 * 파일명 : prodRecpOriginAdd.js
 * 설  명 : 재료 및 원산지 등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.03.25     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품조회 그리드 생성
 */
app.controller('prodRecpOriginAddCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodRecpOriginAddCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("prodRecpOriginAddCtrl", function(event, data) {
        // label에 담은 이유 : 분류조회 후 검색시, 데이터가 날라가버림 / 검색에 1을 빼자니 저장클릭 안됨
        $("#lblProdRecpOriginAddProdCd").text(data.prodCd);
        $("#lblProdRecpOriginAddProdNm").text(data.prodNm);
        $("#lblProdRecpOriginAddHqBrandCd").text(data.hqBrandCd);
        $scope.searchProdRecpOriginAdd();
        event.preventDefault();
    });

    $scope.searchProdRecpOriginAdd = function(){
        var params = {};
        params.prodCd = $("#lblProdRecpOriginAddProdCd").text();
        params.hqBrandCd = $("#lblProdRecpOriginAddHqBrandCd").text();

        $scope._inquiryMain("/base/prod/recpOrigin/prodRecpOriginAdd/getProdRecpOriginAddList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $("#funcProdRecpOriginAddSave").click(function(e){
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].status = "I";
                $scope.flex.collectionView.items[i].prodCd = $("#lblProdRecpOriginAddProdCd").text();
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/recpOrigin/prodRecpOriginAdd/getProdRecpOriginAddSave.sb", params, function(){ $scope.close() });
    });

    // 팝업 닫기
    $scope.close = function(){
        $scope.wjProdRecpOriginAddLayer.hide();

        $scope.recipesNm = "";
        $scope.orgplceNm = "";

        var params = {};
        params.prodCd = $("#lblProdRecpOriginAddProdCd").text();
        params.prodNm = $("#lblProdRecpOriginAddProdNm").text();
        params.hqBrandCd = $("#lblProdRecpOriginAddHqBrandCd").text();

        // 저장기능 수행후 재조회
        $scope._broadcast('prodRecpOriginDetailCtrl', params);
        $scope._broadcast('prodRecpOriginPopupCtrl', params);
    };

}]);