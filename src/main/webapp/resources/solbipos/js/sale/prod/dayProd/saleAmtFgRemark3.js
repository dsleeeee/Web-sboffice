/****************************************************************
 *
 * 파일명 : saleAmtFgRemark3.js
 * 설  명 : 설명팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.18     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('saleAmtFgRemarkPopupCtrl3', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleAmtFgRemarkPopupCtrl3', $scope, $http, true));

    $scope._setComboData("prodHqBrandCdCombo", momsHqBrandCdComboList); // 상품브랜드

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("saleAmtFgRemarkPopupCtrl3", function(event, data) {
        $scope.getSaleAmtFgRemarkList3();
        event.preventDefault();
    });

    // 조회
    $scope.getSaleAmtFgRemarkList3 = function(){
        // 파라미터
        var params = {};
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.sdselProdCd = $scope.sdselProdCd;
        params.sdselProdNm = $scope.sdselProdNm;
        params.prodClassCd = $scope.prodClassCd;
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        // '전체' 일때
        if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.gubun = 'N';
        $scope._inquirySub("/sale/prod/dayProd/dayProd/getSaleAmtFgRemarkList3.sb", params, function (){
            var scope = agrid.getScope('sdselMomsModPopupCtrl');
            scope.getSdselMomsMod();
        });
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer2;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl2');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

    $scope.add = function (){
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sale/prod/dayProd/dayProd/getSdselMomsModSave.sb", params, function(){
            $scope.getSaleAmtFgRemarkList3();
        });
    }
}]);

app.controller('sdselMomsModPopupCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sdselMomsModPopupCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("sdselMomsModPopupCtrl", function(event, data) {
    });

    // 프린터그룹 조회
    $scope.getSdselMomsMod = function(){
        // 파라미터
        var params = {};
        params.prodCd = '';
        params.prodNm = '';
        params.sdselProdCd = '';
        params.sdselProdNm = '';
        params.prodClassCd = '';
        params.prodHqBrandCd = '';
        var momsHqBrandCd = "";
        for(var i=0; i < momsHqBrandCdComboList.length; i++){
            if(momsHqBrandCdComboList[i].value !== null) {
                momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
            }
        }
        params.userBrands = momsHqBrandCd;
        params.gubun = 'Y';
        $scope._inquirySub("/sale/prod/dayProd/dayProd/getSaleAmtFgRemarkList3.sb", params);
    };

    $scope.delete = function (){
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sale/prod/dayProd/dayProd/getSdselMomsModDelete.sb", params, function(){
            var scope = agrid.getScope('saleAmtFgRemarkPopupCtrl3');
            scope.getSaleAmtFgRemarkList3();
        });
    }
}]);