/****************************************************************
 *
 * 파일명 : promotionReportDtl.js
 * 설  명 : 프로모션정산 적용상품 상세 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.01.03    김유승       1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();
var promo = {};

// 적용상품 - 구매대상
var selectProdDsFgData = [
    {"name":"전체구매","value":"1"},
    {"name":"일부구매(종류+수량)","value":"2"},
    {"name":"일부구매(수량=교차선택)","value":"3"},
    {"name":"제외상품","value":"4"},
    {"name":"품목개별할인","value":"5"}
];

// 상품코드/분류 구분
var gubunDsFgData=[
    {"name":"상품","value":"1"},
    {"name":"분류","value":"2"}
];

// 할인구분
var applyDcDsData = [
    {"name":"정률할인","value":"1"},
    {"name":"정액할인","value":"2"}
];

/**
 * 프로모션관리 적용상품 그리드
 */
app.controller('promotionSelectProdGridCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('promotionSelectProdGridCtrl', $scope, $http, $timeout, false));

    $scope._setComboData("selectProdDs", selectProdDsFgData); // 적용상품 - 구매대상
    $scope._setComboData("applyDcDs", applyDcDsData); // 할인구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.gubunDsFgDataMap = new wijmo.grid.DataMap(gubunDsFgData, 'value', 'name'); //상품코드/분류 구분
        $scope.applyDcDsDataMap = new wijmo.grid.DataMap(applyDcDsData, 'value', 'name'); //할인구분
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("promotionSelectProdGridCtrl", function(event, data) {
        $("#lblPromoType").text(data.promoType);
        // 적용상품 목록 조회
        $scope.getPromotionProdList(data);
        event.preventDefault();
    });

    // 적용상품 목록 조회
    $scope.getPromotionProdList = function(data){

        var params = {};
        params.promotionCd = data.promotionCd;

        if (data.promoType === "001") { // 영수증전체할인 적용상품 사용안함
            $("#tdSelectPromoType").val(messages["promotionReport.billDc"]);
            $("#tdSelectUseYn").val(messages["promotionReport.useN"]);
            $("#trSelectProd").css("display", "none");
            $("#prodGrid").css("display", "none");
        } else if (data.promoType === "101") { // 적용품목할인
            $scope.selectProdDsCombo.itemsSource = new wijmo.collections.CollectionView(selectProdDsFgData.slice(0, 3)); // 적용상품 - 구매대상
            $("#tdSelectPromoType").val(messages["promotionReport.usdProdDc"]);
            $("#tdSelectUseYn").val(messages["promotionReport.useY"]);
            $("#trSelectProd").css("display", "");
            $("#prodGrid").css("display", "");
        } else if (data.promoType === "201") { // 1+1 할인
            $scope.selectProdDsCombo.itemsSource = new wijmo.collections.CollectionView(selectProdDsFgData.slice(0, 1).concat(selectProdDsFgData.slice(2, 3))); // 적용상품 - 구매대상
            $("#tdSelectPromoType").val(messages["promotionReport.onePlusDc"]);
            $("#tdSelectUseYn").val(messages["promotionReport.useY"]);
            $("#trSelectProd").css("display", "");
            $("#prodGrid").css("display", "");
        } else if (data.promoType === "301") { // 1+1 증정
            $scope.selectProdDsCombo.itemsSource = new wijmo.collections.CollectionView(selectProdDsFgData.slice(0, 1).concat(selectProdDsFgData.slice(2, 3))); // 적용상품 - 구매대상
            $("#tdSelectPromoType").val(messages["promotionReport.oneGiftDc"]);
            $("#tdSelectUseYn").val(messages["promotionReport.useY"]);
            $("#trSelectProd").css("display", "");
            $("#prodGrid").css("display", "");
        } else if (data.promoType === "401") { // 특별가
            $scope.selectProdDsCombo.itemsSource = new wijmo.collections.CollectionView(selectProdDsFgData.slice(0, 1)); // 적용상품 - 구매대상
            $("#tdSelectPromoType").val(messages["promotionReport.specialDc"]);
            $("#tdSelectUseYn").val(messages["promotionReport.useY"]);
            $("#trSelectProd").css("display", "");
            $("#prodGrid").css("display", "");
        } else if (data.promoType === "501") { // 품목개별할인
            $scope.selectProdDsCombo.itemsSource = new wijmo.collections.CollectionView(selectProdDsFgData.slice(4, 5)); // 적용상품 - 구매대상
            $("#tdSelectPromoType").val(messages["promotionReport.indivDc"]);
            $("#tdSelectUseYn").val(messages["promotionReport.useY"]);
            $("#trSelectProd").css("display", "");
            $("#prodGrid").css("display", "");
        }

        $scope._inquirySub("/base/promotion/promotion/getPromotionProdList.sb", params, function () {

            var grid = wijmo.Control.getControl("#wjGridSelectProd");
            var columns = grid.columns;
        });
    };

    // 적용상품 - 구매대상 선택에 따른 selectProdCnt 명칭(상품수, 수량) 변경
    $scope.setSelectProdCnt = function (s){

        var grid = wijmo.Control.getControl("#wjGridSelectProd");
        var columns = grid.columns;

        // 적용상품 구매대상이 '품목개별할인' 인 경우만 할인구분, 할인값 상품별 개별입력 가능.
        if($("#lblPromoType").text() === "501"){
            columns[3].width = 180;   // 할인구분, 할인값 컬럼추가로 인한 명칭 컬럼 width 값 조정
            columns[5].visible = true;
            columns[6].visible = true;
        }else {
            columns[3].width = 250;
            columns[5].visible = false;
            columns[6].visible = false;
        }

        // 구매대상 선택값에 따른 적용상품 그리드 조건수량입력 제어를 위해
        // 구매대상이 전체구매, 일부구매(종류+수량)인 경우 조건수량 입력
        // 구매대상이 일부구매(수량=교차선택), 제외상품인 경우 조건수량 미입력(숨김처리)
        // 전채구매, 일부구매(수량=교차선택)만 사용 중
        if(s.selectedValue === "1"){ // 전체구매
            //$("#lblSelectProdCnt").text(messages["promotion.selectProdCrossYCnt"]); // 전체구매는 수량 입력 안해서 숨김
            // $("#thSelectProdCnt").css("display", "none");
            // $("#tdSelectProdCnt").css("display", "none");
            columns[4].visible = true;
        }else if(s.selectedValue === "2"){ // 일부구매(종류+수량)
            // $("#lblSelectProdCnt").text(messages["promotion.selectProdCrossNCnt"]); // title : 상품수
            // $("#thSelectProdCnt").css("display", "");
            // $("#tdSelectProdCnt").css("display", "");
            columns[4].visible = true;
        }else if (s.selectedValue ==="3"){ // 일부구매(수량=교차선택)
            // $("#lblSelectProdCnt").text(messages["promotion.selectProdCrossYCnt"]); // title : 수량
            // $("#thSelectProdCnt").css("display", "");
            // $("#tdSelectProdCnt").css("display", "");
            columns[4].visible = false;
        }else if (s.selectedValue ==="4"){ // 제외상품
            // $("#lblSelectProdCnt").text(messages["promotion.selectProdCrossYCnt"]); // title : 수량
            // $("#thSelectProdCnt").css("display", "");
            // $("#tdSelectProdCnt").css("display", "");
            columns[4].visible = false;
        }else{ // 품목개별할인
            //$("#lblSelectProdCnt").text(messages["promotion.selectProdCrossYCnt"]); // 품목개별할인은 수량 입력 안해서 숨김
            // $("#thSelectProdCnt").css("display", "none");
            // $("#tdSelectProdCnt").css("display", "none");
            columns[4].visible = false;
        }
    };

    // 닫기
    $scope.close = function () {
        $("#lblPromoType").text("");
    };

}]);