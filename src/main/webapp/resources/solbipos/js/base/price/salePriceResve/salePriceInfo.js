/****************************************************************
 *
 * 파일명 : salePriceInfo.js
 * 설  명 : 가격예약(판매가관리) 상품가격정보 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.25     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품가격정보 팝업생성
 */
app.controller('salePriceInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('salePriceInfoCtrl', $scope, $http, true));

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

    $scope.$on("salePriceInfoCtrl", function(event, data) {

        // 매장명, 상품명 셋팅
        $("#lblProd").text("[" + data.prodCd + "] " + data.prodNm);
        $("#hdProdCd").val(data.prodCd);

        // 상품가격정보 조회
        $scope.salePriceInfo();
        event.preventDefault();

    });

    // 상품가격정보 조회
    $scope.salePriceInfo = function () {

        var params = {};
        params.prodCd = $("#hdProdCd").val();

        $scope._inquirySub('/base/price/salePriceResve/salePriceResve/getSalePriceInfo.sb', params, function() {}, false);
    }

}]);