/****************************************************************
 *
 * 파일명 : storeStoreSalePriceInfo.js
 * 설  명 : 가격예약(매장판매가) [매장별 판매가관리] 상품가격정보 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.15     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품가격정보 팝업생성
 */
app.controller('storeStoreSalePriceInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeStoreSalePriceInfoCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });
    };

    $scope.$on("storeStoreSalePriceInfoCtrl", function(event, data) {

        // 매장명, 상품명 셋팅
        $("#lblProd2").text("[" + data.prodCd + "] " + data.prodNm);
        $("#lblStore2").text( data.storeNm);
        $("#hdProdCd2").val(data.prodCd);
        $("#hdStoreCd2").val(data.storeCd);

        // 상품가격정보 조회
        $scope.searchStoreSalePriceInfo();
        event.preventDefault();
    });

    // 상품가격정보 조회
    $scope.searchStoreSalePriceInfo = function () {

        var params = {};
        params.prodCd = $("#hdProdCd2").val();
        params.storeCd = $("#hdStoreCd2").val();

        $scope._inquirySub('/base/price/salePriceResve/storeSalePriceResve/getStoreSalePriceInfo.sb', params, function() {}, false);
    }
}]);