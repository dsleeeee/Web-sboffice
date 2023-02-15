/****************************************************************
 *
 * 파일명 : prodOptionAddWithProd.js
 * 설  명 : 옵션관리 상품추가 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.02.10    이다솜      1.0
 *
 * **************************************************************/


/**
 *  옵션관리 상품선택 그리드 생성
 */
app.controller('prodOptionAddWithProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodOptionAddWithProdCtrl', $scope, $http, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드

    // 상품 선택여부
    $scope.itemChecked = false;

    // 선택한 옵션그룹코드
    $scope.selOptioGrpCd = "";

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                // 체크박스 클릭시
                if (col.binding === 'gChk' && s.rows[ht.row].dataItem.gChk ) {
                    $scope.itemChecked = true;
                } else {
                    $scope.itemChecked = false;
                }
            }
        });
    };

    // 사이드메뉴 상품선택 그리드 조회
    $scope.$on("prodOptionAddWithProdCtrl", function(event, data) {

        // 선택한 옵션그룹코드 셋팅
        if(data !== undefined && !isEmptyObject(data)) {
            $scope.selOptioGrpCd = data;
        }

        // 상품브랜드 검색조건 show/hidden
        if(brandUseFg === "1" && orgnFg === "HQ"){
            $("#trProdHqBrand").css("display", "");
        }else{
            $("#trProdHqBrand").css("display", "none");
        }

        // 상품목록조회
        $scope.srchProdList();
    });

    // 상품목록조회
    $scope.srchProdList = function(){
       var params = {};

       params.optionGrpCd = $scope.selOptioGrpCd;
       params.prodCd = $("#srchProdCd").val();
       params.prodNm = $("#srchProdNm").val();
       params.prodClassCd = $scope.prodClassCd;
       params.storeProdUseFg = storeProdUseFg; // 매장상품제한구분 여부 (0: 미사용 / 1: 사용)

        if(brandUseFg === "1" && orgnFg === "HQ"){
            // 선택한 상품브랜드가 있을 때
            params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

            // 선택한 상품브랜드가 없을 때('전체' 일때)
            if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null){
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                      userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }
        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain('/base/prod/prodOption/prodOption/getProdList.sb', params);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    };

    // 상품선택버튼 클릭
    $scope.selProdConfirm = function () {

        var idx = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                idx++;
            }
        }

        if ( idx < 1 ) {
            $scope.itemChecked = false;
            $scope._popMsg('상품을 선택해주세요.');
            return false;
        }else{
            $scope.itemChecked = true;
        }
    };

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

}]);