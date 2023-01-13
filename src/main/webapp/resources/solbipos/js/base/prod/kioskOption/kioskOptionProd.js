/****************************************************************
 *
 * 파일명 : kioskOptionProd.js
 * 설  명 : 키오스크옵션 상품등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.08.19     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품조회 그리드 생성
 */
app.controller('kioskOptionProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskOptionProdCtrl', $scope, $http, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("kioskOptionProdCtrl", function(event, data) {
        // 페이징하면 에러나서
        if(!$.isEmptyObject(data) ) {
            // label에 담은 이유 : 분류조회 후 검색시, 데이터가 날라가버림 / 검색에 1을 빼자니 저장클릭 안됨
            $("#lblProdCd").text(data.prodCd);
        }
        $scope.searchKioskOptionProd();
        event.preventDefault();
    });

    $scope.searchKioskOptionProd = function(){
        var params = {};
        params.prodCd = $("#lblProdCd").text();

        if(brandUseFg === "1" && orgnFg === "HQ"){

          // 선택한 상품브랜드가 있을 때
          params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

          // 선택한 상품브랜드가 없을 때('전체' 일때)
          if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
              var userHqBrandCd = "";
              for(var i=0; i < userHqBrandCdComboList.length; i++){
                  if(userHqBrandCdComboList[i].value !== null) {
                      userHqBrandCd += userHqBrandCdComboList[i].value + ","
                  }
              }
              params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
          }
        }

        $scope._inquiryMain("/base/prod/kioskOption/kioskOptionProd/getKioskOptionProdList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 상품분류정보 팝업
    $scope.popUpKioskOptionProdProdClass = function() {
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
    $scope.delKioskOptionProdProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    // 저장
    $scope.save = function(optionFg){
        var params = {};
        params.prodCd = $("#lblProdCd").text();
        params.optionFg = optionFg;

        // 표기순번 조회
        $scope._postJSONQuery.withPopUp( "/base/prod/kioskOption/kioskOptionProd/getKioskOptionProdDispSeq.sb", params, function(response){

            var kioskOptionProd = response.data.data.result;
            $scope.kioskOptionProd = kioskOptionProd;

            params.dispSeq = $scope.kioskOptionProd.dispSeq;

            // 저장
            $scope.saveKioskOptionProd(params);
        });
    };

    // 저장
    $scope.saveKioskOptionProd = function(data){
        var j = 0;

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].prodCd = $("#lblProdCd").text();
                $scope.flex.collectionView.items[i].optionFg = data.optionFg;
                $scope.flex.collectionView.items[i].dispSeq = data.dispSeq+j;
                params.push($scope.flex.collectionView.items[i]);
                j++;
            }
        }

        // if(params.length <= 0) {
        //     s_alert.pop(messages["cmm.not.select"]);
        //     return;
        // }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/kioskOption/kioskOptionProd/getKioskOptionProdSave.sb", params, function(){ $scope.close() });
    };

    // 팝업 닫기
    $scope.close = function(){
        $scope.wjKioskOptionProdLayer.hide();

        $scope.optionProdCd ="";
        $scope.optionProdNm ="";
        $scope.barCd ="";
        $scope.prodClassCdNm ="";
        $scope.prodClassCd ="";

        var params = {};
        params.prodCd = $("#lblProdCd").text();

        // 저장기능 수행후 재조회
        $scope._broadcast('kioskOptionDetailCtrl', params);
    };
}]);