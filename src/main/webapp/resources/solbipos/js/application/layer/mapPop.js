/****************************************************************
 *
 * 파일명 : mapPop.js
 * 설  명 : 지도보기 Popup JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.09.23     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

/**
 *
 */
app.controller('mapPopCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mapPopCtrl', $scope, $http, false));

    $scope.$on("mapPopCtrl", function(event, data) {

        // title setting
        $("#lblTitle").text(data.title);

        // 지도조회
        searchMap(data.markerNm === "" ? data.title : data.markerNm, data.addr, data.latitude, data.longitude);

        // 좌표클릭으로 주소 및 위도,경도 조회
        //clickMap(data.markerNm === "" ? data.title : data.markerNm, data.latitude, data.longitude);
    });

}]);