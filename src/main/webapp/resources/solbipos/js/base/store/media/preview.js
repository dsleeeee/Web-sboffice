/****************************************************************
 *
 * 파일명 : preview.js
 * 설  명 : 듀얼모니터영상관리 - 미리보기 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.08.09     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  듀얼모니터영상관리 - 미리보기
 */
app.controller('mediaPreviewCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mediaPreviewCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("mediaPreviewCtrl", function(event, data) {

        if(data !== undefined && !isEmptyObject(data)) {

            var params = {};
            params.verSerNo = data;

            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._postJSONQuery.withOutPopUp("/base/store/media/verInfo/dtlInfo.sb", params, function (response) {

                var fileInfo = response.data.data;
                
                // 이미지 셋팅
                $("#imgPreview").attr("src", "http://" + window.location.host + "/Media/" + fileInfo.fileNm);
            });
        }

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();

    });

}]);
