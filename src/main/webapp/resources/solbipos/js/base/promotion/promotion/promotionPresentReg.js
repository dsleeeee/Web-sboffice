/****************************************************************
 *
 * 파일명 : promotionPresentReg.js
 * 설  명 : 프로모션 혜택상품 추가 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.26     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('promotionPresentRegCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('promotionPresentRegCtrl', $scope, $http, false));

    // 사용여부 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchPresentBrand", brandList);
    $scope._setComboData("srchPresentStoreGroup", storeGroupList);
    $scope._setComboData("presentUseYnAll", useYnAllFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.presentUseYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); //사용여부

        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "storeGroupNms") {

                    var item = s.rows[e.row].dataItem;
                    if (item.storeGroupNms === "()") {
                        e.cell.innerHTML = "";
                    }
                }
            }
        });
    };

    // 팝업 오픈 시, 혜택상품 리스트 조회
    $scope.$on("promotionPresentRegCtrl", function(event, data) {

        // 혜택상품-구분 선택값에 따라 조건수량 입력여부 결정
        $("#hdPresentDs1").val(data);

        // 구매대상 선택값이 전체구매, 일부구매(종류+수량)인 경우만 조건수량 입력가능
        var grid = wijmo.Control.getControl("#wjGridPromotionPresentReg");
        var columns = grid.columns;
        if($("#hdPresentDs1").val() === "1") {
            columns[6].visible = true;
        }else{
            columns[6].visible = false;
        }

        // 혜택상품 조회
        $scope.searchPresent();
        event.preventDefault();

    });

    // 혜택상품 조회
    $scope.searchPresent = function () {

        var params = {};
        params.promotionCd = $("#hdPromotionCd").val();
        params.hqBrandCd = $scope.srchPresentBrandCombo.selectedValue;
        params.storeGroupCd = $scope.srchPresentStoreGroupCombo.selectedValue;
        params.prodCd = $("#srchPresentCd").val();
        params.prodNm = $("#srchPresentNm").val();
        params.useYn = $scope.srchPresentUseYnCombo.selectedValue;

        $scope._inquirySub("/base/promotion/promotion/getPresentProdList.sb", params, function () {});
    };

    // 조회
    $scope.btnSearchPresent = function(){

        // 혜택상품 조회
        $scope.searchPresent();
    };

    // 추가
    $scope.btnInsertPresent = function () {

        // 파라미터 설정
        var params = new Array();

        // 혜택상품-구분 이 전체인 경우만 조건수량 체크
        if($("#hdPresentDs1").val() === "1") {
            // 조건수량이 수정된 내역이 있는지 체크
            if ($scope.flex.collectionView.itemsEdited.length <= 0) {
                $scope._popMsg(messages["cmm.not.modify"]);
                return false;
            }
        }

        // 선택한 상품이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            var item = $scope.flex.collectionView.itemsEdited[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("추가할 " + messages["promotion.product"] + "의 체크박스" + messages["promotion.chk.item"]); // 추가할 상품의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {

            var item = $scope.flex.collectionView.itemsEdited[i];

            // 혜택상품-구매 선택값이 전체인 경우만 조건수량 체크
            if($("#hdPresentDs1").val() === "1") {
                if (item.gChk === true && (item.prodQty === null || item.prodQty === "" || item.prodQty === "0" || item.prodQty === 0)) {
                    $scope._popMsg(messages["promotion.chk.prodQty"]); // 선택한 상품의 조건수량을 반드시 입력하세요.
                    return false;
                }
            }

            if(item.gChk === true) {
                var obj = {};
                obj.status = "I";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.prodCd = item.prodCd;

                // 혜택상품-구매 선택값이 전체인 경우만 조건수량 입력
                if($("#hdPresentDs1").val() === "1") {
                    obj.giftQty =  item.prodQty;
                }else{
                    obj.giftQty =  1;
                }

                params.push(obj);
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/promotion/promotion/savePromotionPresent.sb", params, function () {

            $scope.promotionPresentRegLayer.hide(true);

            // 혜택상품 목록 재조회
            $scope._pageView('promotionSelectPresentGridCtrl', 1);

        });
    };

}]);