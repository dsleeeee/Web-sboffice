/****************************************************************
 *
 * 파일명 : naverOrderInfo.js
 * 설  명 : 네이버플레이스 > 네이버플레이스 > 네이버 주문연동 > 정보 수정 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.13     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('naverOrderInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('naverOrderInfoCtrl', $scope, $http, false));

    $scope.$on("naverOrderInfoCtrl", function (event, data) {

        // 주문 유형 정보 셋팅
        $scope.getServiceType(data);

    });

    // 주문 유형 정보 셋팅
    $scope.getServiceType = function (serviceActiveYn) {

        $("input[name='tableOrder'][value='N']").prop("checked", true);
        $("input[name='pickupOrder'][value='N']").prop("checked", true);

        var serviceList = (serviceActiveYn && serviceActiveYn.data) ? serviceActiveYn.data : [];
        for (var i = 0; i < serviceList.length; i++) {
            var serviceItem = serviceList[i];

            if (serviceItem.service === "TABLE") {
                if (serviceItem.useFlag) {
                    $("input[name='tableOrder'][value='Y']").prop("checked", true);
                } else {
                    $("input[name='tableOrder'][value='N']").prop("checked", true);
                }
            } else if (serviceItem.service === "PICKUP") {
                if (serviceItem.useFlag) {
                    $("input[name='pickupOrder'][value='Y']").prop("checked", true);
                } else {
                    $("input[name='pickupOrder'][value='N']").prop("checked", true);
                }
            }
        }

        /*var params = {};
        $scope._postJSONQuery.withOutPopUp('/naverPlace/naverPlace/naverOrderLink/getServiceActive.sb', params, function (response) {
            var serviceActiveYn = response.data.data.list;

            $("input[name='tableOrder'][value='N']").prop("checked", true);
            $("input[name='pickupOrder'][value='N']").prop("checked", true);

            var serviceList = (serviceActiveYn && serviceActiveYn.data) ? serviceActiveYn.data : [];
            for (var i = 0; i < serviceList.length; i++) {
                var serviceItem = serviceList[i];

                if (serviceItem.service === "TABLE") {
                    if (serviceItem.useFlag) {
                        $("input[name='tableOrder'][value='Y']").prop("checked", true);
                    } else {
                        $("input[name='tableOrder'][value='N']").prop("checked", true);
                    }
                } else if (serviceItem.service === "PICKUP") {
                    if (serviceItem.useFlag) {
                        $("input[name='pickupOrder'][value='Y']").prop("checked", true);
                    } else {
                        $("input[name='pickupOrder'][value='N']").prop("checked", true);
                    }
                }
        });*/
    };
    
    // 서비스 활성화/비활성화 저장
    $scope.saveServiceType = function () {

        // 테이블/픽업 주문 노출여부 선택 확인
        if ($("input[name='tableOrder']:checked").length === 0) {
            $scope._popConfirm(messages["naverOrderLink.serviceType.table.confirm"]);
            return false;
        }

        if ($("input[name='pickupOrder']:checked").length === 0) {
            $scope._popConfirm(messages["naverOrderLink.serviceType.pickup.confirm"]);
            return false;
        }

        var params = {};
        params.channelType = "NAVER";
        params.posShopId = sessionStorage.getItem("storeCd");
        params.services = [];

        params.services.push({
            serviceType: "TABLE",
            channelServiceId: "",
            useFlag: $("input[name='tableOrder']:checked").val() === "Y"
        });

        params.services.push({
            serviceType: "PICKUP",
            channelServiceId: "",
            useFlag: $("input[name='pickupOrder']:checked").val() === "Y"
        });

        $scope._postJSONSave.withOutPopUp("/naverPlace/naverPlace/naverOrderLink/regServiceActive.sb", params, function (response) {
            var data = response.data.data.list;
            if (data.status === 200) {
                setTimeout(function () {
                    // 재조회
                    location.reload();
                    $scope.wjNaverOrderInfoLayer.hide();
                }, 1000);
            } else {
                $scope._popMsg(data.message);
                return false;
            }
        });
    };
    
    // 닫기
    $scope.closeServiceType = function () {
        $("input[name='tableOrder'][value='Y']").prop("checked", false);
        $("input[name='tableOrder'][value='N']").prop("checked", false);
        $("input[name='pickupOrder'][value='Y']").prop("checked", false);
        $("input[name='pickupOrder'][value='N']").prop("checked", false);
    }

}]);