/****************************************************************
 *
 * 파일명 : alimtalkManual.js
 * 설  명 : 알림톡 계정생성 가이드 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  알림톡 계정생성 가이드 조회 그리드 생성
 */
app.controller('alimtalkManualCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkManualCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkManualCtrl", function(event, data) {
        // 페이지변경
        $scope.pageChange(1);

        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 페이지변경
    $scope.pageChange = function(page) {
        $("#divManual1").css("display", "none");
        $("#divManual2").css("display", "none");
        $("#divManual3").css("display", "none");
        $("#divManual4").css("display", "none");
        $("#divManual5").css("display", "none");
        $("#divManual6").css("display", "none");
        $("#divManual7").css("display", "none");
        $("#divManual8").css("display", "none");
        $("#divManual9").css("display", "none");
        $("#divManual10").css("display", "none");

        if(page == 1) {
            $("#divManual1").css("display", "");
        }
        else if(page == 2) {
            $("#divManual2").css("display", "");
        }
        else if(page == 3) {
            $("#divManual3").css("display", "");
        }
        else if(page == 4) {
            $("#divManual4").css("display", "");
        }
        else if(page == 5) {
            $("#divManual5").css("display", "");
        }
        else if(page == 6) {
            $("#divManual6").css("display", "");
        }
        else if(page == 7) {
            $("#divManual7").css("display", "");
        }
        else if(page == 8) {
            $("#divManual8").css("display", "");
        }
        else if(page == 9) {
            $("#divManual9").css("display", "");
        }
        else if(page == 10) {
            $("#divManual10").css("display", "");
        }
    };

    // 팝업 닫기
    $scope.close = function(){
        // 페이지변경
        $scope.pageChange(1);

        $scope.wjAlimtalkManualLayer.hide();
    };
}]);