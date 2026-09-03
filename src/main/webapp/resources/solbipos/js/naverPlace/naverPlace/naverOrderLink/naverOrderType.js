/****************************************************************
 *
 * 파일명 : naverOrderType.js
 * 설  명 : 네이버플레이스 > 네이버플레이스 > 네이버 주문연동 > 주문유형 설정 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.13     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('naverOrderTypeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('naverOrderTypeCtrl', $scope, $http, false));

    // 테이블 주문 channelServiceId
    //var tableChannelServiceId = null;
    // 픽업 주문 channelServiceId
    //var pickupChannelServiceId = null;

    $scope.$on("naverOrderTypeCtrl", function (event, data) {

        // 테이블/픽업 주문 channelServiceId 파악
        /*for (var i = 0; i < data.length; i++) {
            if (data[i].serviceType === "TABLE") {
                tableChannelServiceId = data[i].channelServiceId;
            } else if (data[i].serviceType === "PICKUP") {
                pickupChannelServiceId = data[i].channelServiceId;
            }
        }*/
    });

    // 서비스 설정 완료
    $scope.saveSetting = function () {

        // 테이블/픽업 주문 노출여부 선택 확인
        if (/*tableChannelServiceId && */$("input[name='tableOrder2']:checked").length === 0) {
            $scope._popConfirm(messages["naverOrderLink.serviceType.table.confirm"]);
            return false;
        }

        if (/*pickupChannelServiceId && */$("input[name='pickupOrder2']:checked").length === 0) {
            $scope._popConfirm(messages["naverOrderLink.serviceType.pickup.confirm"]);
            return false;
        }

        var params = {};
        params.channelType = "NAVER";
        params.posShopId = sessionStorage.getItem("storeCd");
        params.services = [];

        //if (tableChannelServiceId) {
            params.services.push({
                serviceType: "TABLE",
                channelServiceId: "",
                useFlag: $("input[name='tableOrder2']:checked").val() === "Y"
            });
        //}

        //if (pickupChannelServiceId) {
            params.services.push({
                serviceType: "PICKUP",
                channelServiceId: "",
                useFlag: $("input[name='pickupOrder2']:checked").val() === "Y"
            });
        //}

        $scope._postJSONSave.withOutPopUp("/naverPlace/naverPlace/naverOrderLink/regServiceActive.sb", params, function (response) {
            var data = response.data.data.list;
            if (data.status === 200) {
            } else {
                $scope._popMsg(data.message);
            }
            setTimeout(function () {
                // 재조회
                location.reload();
                $scope.wjNaverOrderTypeLayer.hide();
            }, 1000);
        });
    }

}]);