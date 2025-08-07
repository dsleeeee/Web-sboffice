/****************************************************************
 *
 * 파일명 : dcDetailMrpizzaTab.js
 * 설  명 : 미스터피자 > 마케팅조회 > 할인세부내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.30    이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  할인세부내역 탭 화면
 */
app.controller('dcDetailMrpizzaTabCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dcDetailMrpizzaTabCtrl', $scope, $http, false));

    $scope.init = function () {
        $("#dcDetailMrpizzaAllStoreView").show();
        $("#dcDetailMrpizzaSelectStoreView").hide();
        $("#dcDetailMrpizzaDcTypeView").hide();
    };

    // 전체점포 탭 보이기
    $scope.dcDetailMrpizzaAllStoreShow = function () {
        $("#dcDetailMrpizzaAllStoreTab").addClass("on");
        $("#dcDetailMrpizzaSelectStoreTab").removeClass("on");
        $("#dcDetailMrpizzaDcTypeTab").removeClass("on");

        $("#dcDetailMrpizzaAllStoreView").show();
        $("#dcDetailMrpizzaSelectStoreView").hide();
        $("#dcDetailMrpizzaDcTypeView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dcDetailMrpizzaAllStoreCtrl");
        scope.flex.refresh();
    };

    // 선택점포 탭 보이기
    $scope.dcDetailMrpizzaSelectStoreShow = function () {
        $("#dcDetailMrpizzaAllStoreTab").removeClass("on");
        $("#dcDetailMrpizzaSelectStoreTab").addClass("on");
        $("#dcDetailMrpizzaDcTypeTab").removeClass("on");

        $("#dcDetailMrpizzaAllStoreView").hide();
        $("#dcDetailMrpizzaSelectStoreView").show();
        $("#dcDetailMrpizzaDcTypeView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dcDetailMrpizzaSelectStoreCtrl");
        scope.flex.refresh();
    };

    // 할인구분 탭 보이기
    $scope.dcDetailMrpizzaDcTypeShow = function () {
        $("#dcDetailMrpizzaAllStoreTab").removeClass("on");
        $("#dcDetailMrpizzaSelectStoreTab").removeClass("on");
        $("#dcDetailMrpizzaDcTypeTab").addClass("on");

        $("#dcDetailMrpizzaAllStoreView").hide();
        $("#dcDetailMrpizzaSelectStoreView").hide();
        $("#dcDetailMrpizzaDcTypeView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("dcDetailMrpizzaDcTypeCtrl");
        scope.flex.refresh();
    };
}]);