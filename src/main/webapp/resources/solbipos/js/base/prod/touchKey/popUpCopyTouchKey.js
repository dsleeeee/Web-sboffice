/****************************************************************
 *
 * 파일명 : popUpCopyTouchKey.js
 * 설  명 : 터치키복사 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.04.28     이다솜      1.0
 *
 * **************************************************************/

// 팝업 생성
app.controller('popUpCopyTouchKeyCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('popUpCopyTouchKeyCtrl', $scope, $http, false));

    $scope.$on("popUpCopyTouchKeyCtrl", function(event, data) {

    });

}]);