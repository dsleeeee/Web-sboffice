/****************************************************************
 *
 * 파일명 : inOutStockReport.js
 * 설  명 : 국민대 > 매입처관리 > 매입전표 등록 > 입고신규등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.11.21     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */

/** 입고/반출 팝업 controller */
app.controller('inOutStockRegistCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('inOutStockRegistCtrl', $scope, $http, true));

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("inOutStockRegistCtrl", function (event, data) {
        $scope.slipNo = data.slipNo;
        $scope.slipFg = data.slipFg;
        $scope.vendrCd = (nvl(data.vendrCd, '') === '' ? '' : data.vendrCd);

        // 신규등록인 경우 입고/반출정보 탭만 활성화
        if($scope.slipNo === '') {
            $scope.inOutStockDtlShowFg = true;
            $scope.inOutStockProdShowFg = false;
            $scope.inOutStockReportShowFg = false;
        }
        // 신규등록이 아닌 경우 모든 탭 활성화
        else {
            $scope.inOutStockDtlShowFg = true;
            $scope.inOutStockProdShowFg = true;
            if($scope.slipFg === -1) {
                $scope.inOutStockReportShowFg = true;
            }else{
                $scope.inOutStockReportShowFg = false;
            }
        }

        $scope.wjInOutStockRegistPopLayer.show(true);

        // 입고
        if($scope.slipFg === 1) {
            $("#popTitle").html(messages["acquireSlipRegist.pop.inTitle"]); // 현재 팝업이 같은 scope 가 아니라서 jquery 형태로 text 부여
            $scope.inOutStockDtlTab = messages["acquireSlipRegist.pop.inStockDtl"];
            $scope.inOutStockProdTab = messages["acquireSlipRegist.pop.inStockProd"];
        }
        //반출
        else if($scope.slipFg === -1) {
            $("#popTitle").html(messages["acquireSlipRegist.pop.rtnTitle"]); // 현재 팝업이 같은 scope 가 아니라서 jquery 형태로 text 부여
            $scope.inOutStockDtlTab = messages["acquireSlipRegist.pop.outStockDtl"];
            $scope.inOutStockProdTab = messages["acquireSlipRegist.pop.outStockProd"];
            $scope.inOutStockReportTab = messages["acquireSlipRegist.pop.outStockReport"];
        }

        // 입고/반출정보 탭 show
        $scope.inOutStockDtlShow();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 입고/반출정보 탭 보이기
    $scope.inOutStockDtlShow        = function () {
        $("#inOutStockDtlTab").addClass("on");
        $("#inOutStockProdTab").removeClass("on");
        $("#inOutStockReportTab").removeClass("on");

        $("#inOutStockDtlView").show();
        $("#inOutStockProdView").hide();
        $("#inOutStockReportView").hide();

        var params = {};
        params.slipNo = $scope.slipNo;
        params.slipFg = $scope.slipFg;
        $scope._broadcast('inOutStockDtlCtrl', params);
    };


    // 입고/반출상품 탭 보이기
    $scope.inOutStockProdShow    = function () {
        if($scope.slipNo === null) {
            $scope._popMsg(messages["acquireSlipRegist.pop.not.slip"]);
            return false;
        }

        $("#inOutStockDtlTab").removeClass("on");
        $("#inOutStockProdTab").addClass("on");
        $("#inOutStockReportTab").removeClass("on");

        $("#inOutStockDtlView").hide();
        $("#inOutStockProdView").show();
        $("#inOutStockReportView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("inOutStockProdCtrl");
        scope.flex.refresh();

        var params = {};
        params.slipNo = $scope.slipNo;
        params.slipFg = $scope.slipFg;
        params.vendrCd = $scope.vendrCd;
        $scope._broadcast('inOutStockProdCtrl', params);
    };


    // 반출서 탭 보이기
    $scope.inOutStockReportShow = function () {
        if($scope.slipNo === null) {
            $scope._popMsg(messages["acquireSlipRegist.pop.not.slip"]);
            return false;
        }

        $("#inOutStockDtlTab").removeClass("on");
        $("#inOutStockProdTab").removeClass("on");
        $("#inOutStockReportTab").addClass("on");

        $("#inOutStockDtlView").hide();
        $("#inOutStockProdView").hide();
        $("#inOutStockReportView").show();

        var params = {};
        params.slipNo = $scope.slipNo;
        params.slipFg = $scope.slipFg;
        params.vendrCd = $scope.vendrCd;
        $scope._broadcast('inOutStockReportCtrl', params);
    };


}]);
