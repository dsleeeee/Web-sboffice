/****************************************************************
 *
 * 파일명 : fnkeyCmNmcdTab.js
 * 설  명 : 다국어관리(기능키/공통코드) 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.12.12     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('fnkeyCmNmcdTabCtrl', ['$scope', function ($scope) {

    $scope.init = function () {

        if(orgnFg === "MASTER"){
            $("#cmPosFnkeyView").show();
            $("#storeFnkeyView").hide();
            $("#cmNmcdView").hide();
        }

        if(orgnFg === "HQ"){
            $("#cmPosFnkeyView").hide();
            $("#storeFnkeyView").show();
            $("#cmNmcdView").hide();
        }
    };
    
    // 기능키(공통) 탭 보이기
    $scope.cmPosFnkeyShow = function () {
        $("#cmPosFnkeyTab").addClass("on");
        $("#storeFnkeyTab").removeClass("on");
        $("#cmNmcdTab").removeClass("on");
    
        $("#cmPosFnkeyView").show();
        $("#storeFnkeyView").hide();
        $("#cmNmcdView").hide();
    
        //
        $scope._broadcast("cmPosFnkeyCtrl");
    };

    // 기능키(매장) 탭 보이기
    $scope.storeFnkeyShow = function (){
        $("#cmPosFnkeyTab").removeClass("on");
        $("#storeFnkeyTab").addClass("on");
        $("#cmNmcdTab").removeClass("on");

        $("#cmPosFnkeyView").hide();
        $("#storeFnkeyView").show();
        $("#cmNmcdView").hide();

        //
        $scope._broadcast("storeFnkeyCtrl");
    };

    // 공통코드 탭 보이기
    $scope.cmNmcdShow = function () {
        $("#cmPosFnkeyTab").removeClass("on");
        $("#storeFnkeyTab").removeClass("on");
        $("#cmNmcdTab").addClass("on");

        $("#cmPosFnkeyView").hide();
        $("#storeFnkeyView").hide();
        $("#cmNmcdView").show();

        //
        $scope._broadcast("cmNmcdCtrl");
    };


}]);