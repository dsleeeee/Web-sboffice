/****************************************************************
 *
 * 파일명 : storeProdSalePriceInfo.js
 * 설  명 : 가격예약(매장판매가) [상품별 판매가관리] 상품가격정보 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.13     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품삭제 팝업생성
 */
app.controller('storeProdSalePriceInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeProdSalePriceInfoCtrl', $scope, $http, true));

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

    $scope.$on("storeProdSalePriceInfoCtrl", function(event, data) {

        // 매장명, 상품명 셋팅
        $("#lblProd").text(data.prodNm);
        $("#lblStore").text( "[" + data.storeCd + "] " + data.storeNm);
        $("#hdProdCd").val(data.prodCd);
        $("#hdStoreCd").val(data.storeCd);

        // 상품가격정보 조회
        $scope.searchStoreSalePriceInfo(data);
        event.preventDefault();
    });

    // 상품가격정보 조회
    $scope.searchStoreSalePriceInfo = function () {

        var params = {};
        params.prodCd = $("#hdProdCd").val();
        params.storeCd = $("#hdStoreCd").val();

        $scope._inquirySub('/base/price/salePriceResve/storeSalePriceResve/getStoreSalePriceInfo.sb', params, function() {}, false);
    }
}]);