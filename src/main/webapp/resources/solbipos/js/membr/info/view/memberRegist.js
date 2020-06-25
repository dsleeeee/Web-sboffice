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
        console.log($scope.saveMode);
        if ($scope.insertMembrNo === undefined || $scope.saveMode !== "MOD") {
            console.log("111111");
            $scope._popMsg(messages["cmm.base.info.save"]);
        } else {
            $("#basicView").hide();
            $("#cardView").show();
            $("#deliView").hide();

            $("#basicTab").removeClass("on");
            $("#cardTab").addClass("on");
            $("#deliTab").removeClass("on");
            $scope.$broadcast("getCardList");
        }
    };

    // 배달 탭 보이기
    $scope.deliShow = function () {
        $("#basicView").hide();
        $("#cardView").hide();
        $("#deliView").show();

        $("#basicTab").removeClass("on");
        $("#cardTab").removeClass("on");
        $("#deliTab").addClass("on");
    };

    $scope.$on("responseGet", function (event, params) {
        $scope.insertMembrNo = params
        $scope.$broadcast("paramsCard", params);
    });

    $scope.$on("modMember", function (event, data) {
        $scope.saveMode = data;
        // event.preventDefault();
    });

    $scope.$on("memberRegistInfo", function (event, data) {
        $scope.$broadcast("memberBasicCtrl", data);
        // event.preventDefault();
    });
}]);