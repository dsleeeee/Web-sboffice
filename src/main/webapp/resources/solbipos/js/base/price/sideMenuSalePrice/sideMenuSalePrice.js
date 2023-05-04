/****************************************************************
 *
 * 파일명 : sideMenu.js
 * 설  명 : 사이드-메뉴 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.23     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */

var app = agrid.getApp();

/** 그리드 생성 */
app.controller('sideMenuSalePriceCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sideMenuSalePriceCtrl', $scope, $http, false));

    $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "saleUprc") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });
    };

    //판매가 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    }

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("sideMenuSalePriceCtrl", function(event, data) {
        $scope.searchSideMenuList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 선택메뉴 목록 조회
    $scope.searchSideMenuList = function(){
        if($("#sideMenuSalePriceStoreCd").val() === null || $("#sideMenuSalePriceStoreCd").val() === "" || $("#sideMenuSalePriceStoreCd").val() === undefined){
            $scope._popMsg(messages["sideMenuSalePrice.store.chk"]);
            return false;
        }

        var params = {};
        params.storeCd =  $("#sideMenuSalePriceStoreCd").val();

        if(brandUseFg === "1") {
            // 선택한 상품브랜드가 있을 때
            params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

            // 선택한 상품브랜드가 없을 때('전체' 일때)
            if (params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var userHqBrandCd = "";
                for (var i = 0; i < userHqBrandCdComboList.length; i++) {
                    if (userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }
        params.listScale = 500;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain("/base/price/sideMenuSalePrice/sideMenuSalePrice/getSideMenuSalePriceList.sb", params);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.sideMenuSalePriceStoreShow = function () {
        $scope._broadcast('sideMenuSalePriceStoreCtrl');
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer.show(true, function (s) {
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

    // 가격저장
    $scope.saveAddProdUprc = function (){

        // 파라미터 설정
        var params = new Array();

        var numchkexp = /[^0-9]/g; // 숫자가 아닌 값 체크
        var numchkexp2 = /^-[0-9]/g;

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {
                // 기존판매가와 변경판매가의 금액이 다른 경우
                if($scope.flex.collectionView.items[i].addProdUprc !== $scope.flex.collectionView.items[i].saleUprc){

                    // 변경판매가 - 마이너스(-)외에 다른 문자 입력 불가
                    if (numchkexp.test($scope.flex.collectionView.items[i].saleUprc)) {
                        if((numchkexp2.test($scope.flex.collectionView.items[i].saleUprc) == false)){
                            $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        }
                    }

                    // 변경판매가 - 1000000000 이상 입력 불가
                    if($scope.flex.collectionView.items[i].saleUprc >= 1000000000){
                        $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/price/sideMenuSalePrice/sideMenuSalePrice/saveSideMenuSalePrice.sb', params, function(){
            $scope.searchSideMenuList();
        });
    };
}]);
