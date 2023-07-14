/****************************************************************
 *
 * 파일명 : saleAmtFgRemarkPopup.js
 * 설  명 : 설명팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.13     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('saleAmtFgRemarkPopupCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleAmtFgRemarkPopupCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("saleAmtFgRemarkPopupCtrl", function(event, data) {
        $scope.getsaleAmtFgRemarkList();
    });

    // 프린터그룹 조회
    $scope.getsaleAmtFgRemarkList = function(){
        // 파라미터
        var params = {};
        $scope._inquirySub("/sale/prod/dayProd/dayProd/getSaleAmtFgRemarkList.sb", params);
    };

    // 닫기
    $scope.close = function(){
        $scope.saleAmtFgRemarkPopupLayer.hide();
    }
}]);