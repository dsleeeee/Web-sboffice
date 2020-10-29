/**
 * get application
 */
var app = agrid.getApp();

app.controller('memberRegistCtrl', ['$scope', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberRegistCtrl', $scope, $http, false));

    $scope.init = function () {
        $("#basicView").show();
        $("#cardView").hide();
        $("#deliView").hide();

        $("#basicTab").addClass("on");
        $("#cardTab").removeClass("on");
        $("#deliTab").removeClass("on")
    };
    // 기본 탭 보이기
    $scope.basicShow = function () {
        $("#basicView").show();
        $("#cardView").hide();
        $("#deliView").hide();

        $("#basicTab").addClass("on");
        $("#cardTab").removeClass("on");
        $("#deliTab").removeClass("on");
    };

    // 카드 탭 보이기
    $scope.cardShow = function () {
        if ($scope.saveMode === 'REG' && $scope.insertMembrNo === undefined) {
            $scope._popMsg(messages["cmm.base.info.save"]);
        } else if ($scope.saveMode === 'REG' && $scope.insertMembrNo !== undefined) {
            $("#basicView").hide();
            $("#cardView").show();
            $("#deliView").hide();

            $("#basicTab").removeClass("on");
            $("#cardTab").addClass("on");
            $("#deliTab").removeClass("on");
            $scope.$broadcast("getCardList");
        } else if ($scope.saveMode === 'MOD') {
            $("#basicView").hide();
            $("#cardView").show();
            $("#deliView").hide();

            $("#basicTab").removeClass("on");
            $("#cardTab").addClass("on");
            $("#deliTab").removeClass("on");
            $scope.$broadcast("getCardList");
        } else {
            $scope._popMsg(messages["cmm.base.info.save"]);
        }
        //$scope.$broadcast("memberRegistCtrl");
    };

    // 배달 탭 보이기
    $scope.deliShow = function () {
        if ($scope.saveMode === 'REG' && $scope.insertMembrNo === undefined) {
            $scope._popMsg(messages["cmm.base.info.save"]);
        } else if ($scope.saveMode === 'REG' && $scope.insertMembrNo !== undefined) {
            $("#basicView").hide();
            $("#cardView").hide();
            $("#deliView").show();

            $("#basicTab").removeClass("on");
            $("#cardTab").removeClass("on");
            $("#deliTab").addClass("on");
            $scope.$broadcast('getMemberDlvr', $scope.insertMembrNo);
            $scope.$broadcast('getMemberDlvrTel', $scope.insertMembrNo);
        } else if ($scope.saveMode === 'MOD') {
            $("#basicView").hide();
            $("#cardView").hide();
            $("#deliView").show();

            $("#basicTab").removeClass("on");
            $("#cardTab").removeClass("on");
            $("#deliTab").addClass("on");
            $scope.$broadcast('getMemberDlvr', $scope.insertMembrNo);
            $scope.$broadcast('getMemberDlvrTel', $scope.insertMembrNo);
        } else {
            $scope._popMsg(messages["cmm.base.info.save"]);
        }
    };
    $scope.$on("responseGet", function (event, params, mode) {
        $scope.insertMembrNo = params;
        $scope.saveMode = mode;
        $scope.$broadcast("paramsCard", params);
    });

    $scope.$on("modMember", function (event, mode, data) {
        $scope.saveMode = mode;
        $scope.updateMembrNo = data;
        // event.preventDefault();
    });

    $scope.$on("memberRegistInfo", function (event, data) {
        $scope.init();
        $scope.$broadcast("memberBasicCtrl", data);
        // event.preventDefault();
    });
}]);