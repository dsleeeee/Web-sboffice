/****************************************************************
 *
 * 파일명 : envRemarkPop.js
 * 설  명 : 매장정보관리 > 매장환경 > 환경설정코드 설명 팝업
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.01.06     이다솜      1.0
 *
 * **************************************************************/
app.controller('envRemarkPopCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('envRemarkPopCtrl', $scope, $http, true));

    $scope.$on("envRemarkPopCtrl", function(event, data) {
        
        // 선택한 환경설정코드 및 명칭 셋팅
        $("#lblEnvTitle").text("[" + data.envstCd + "] " + data.envstNm);

        // 환경설정 설명
        $("#divEnvRemark").html(data.remark.replace(/\n/g, '<br/>'));
    });

}]);