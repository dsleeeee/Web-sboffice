/****************************************************************
 *
 * 파일명 : migDataMappingTab.js
 * 설  명 : 기초관리 > 매장정보관리 > 데이터이관요청내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.11.05     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('migDataMappingTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {
        $("#okMigDataMappingView").show();
        $("#nxMigDataMappingView").hide();
        $("#zeusDataMappingView").hide();
    };

    // OKPOS 탭 보이기
    $scope.okMigDataMapping = function () {
        $("#okMigDataMappingTab").addClass("on");
        $("#nxMigDataMappingTab").removeClass("on");
        $("#zeusDataMappingTab").removeClass("on");

        $("#okMigDataMappingView").show();
        $("#nxMigDataMappingView").hide();
        $("#zeusDataMappingView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope = agrid.getScope("migDataMappingCtrl");
        scope.flex.refresh();
    };

    // NXPOS 탭 보이기
    $scope.nxMigDataMapping = function () {
        $("#okMigDataMappingTab").removeClass("on");
        $("#nxMigDataMappingTab").addClass("on");
        $("#zeusDataMappingTab").removeClass("on");

        $("#okMigDataMappingView").hide();
        $("#nxMigDataMappingView").show();
        $("#zeusDataMappingView").hide();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope2 = agrid.getScope("nxMigDataMappingCtrl");
        scope2.flex.refresh();
    };

    // NXPOS 탭 보이기
    $scope.zeusDataMapping = function () {
        $("#okMigDataMappingTab").removeClass("on");
        $("#nxMigDataMappingTab").removeClass("on");
        $("#zeusDataMappingTab").addClass("on");

        $("#okMigDataMappingView").hide();
        $("#nxMigDataMappingView").hide();
        $("#zeusDataMappingView").show();

        // angular 그리드 hide 시 깨지므로 refresh()
        var scope2 = agrid.getScope("zeusDataMappingCtrl");
        scope2.flex.refresh();
    };

}]);