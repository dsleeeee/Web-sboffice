/****************************************************************
 *
 * 파일명 : captionMsgGrpDtl.js
 * 설  명 : 다국어관리(기능키/메시지) - 화면구분 상세화면 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.11.03     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('captionMsgGrpDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('captionMsgGrpDtlCtrl', $scope, $http, true));

    // 입력한 화면구분 정보
    $scope.version = {};

    // 조회 버튼 클릭
    $scope.$on("captionMsgGrpDtlCtrl", function(event, data) {
        $scope.dtlInfo(data);
        event.preventDefault();
    });

    // 조회
    $scope.dtlInfo = function (data) {

        var params = data;

        $scope._postJSONQuery.withOutPopUp( "/base/store/multilingualCaptionMsg/dtlInfo.sb", params, function(response){

            $scope.version = response.data.data;

            // 수정 버튼 막기
            if(orgnFg === "STORE" && $scope.version.captionImgCd < 8000){
                $("#btnMod").hide();
            } else {
                $("#btnMod").show();
            }

            // 파일사이즈 변환하여 표기
            $scope.version.fileSize = getfileSize($scope.version.fileSize);
        });
    };

    // 수정
    $scope.modify = function(){

        // 팝업 닫힐 때
        $scope.captionMsgGrpRegLayer.show(true, function(){

            // 기존 입력값 초기화
            var scope = agrid.getScope('captionMsgGrpRegCtrl');
            scope.version = {};

            $('#file').val(null);
        });
    };

    // 닫기
    $scope.close = function(){
        $scope.captionMsgGrpDtlLayer.hide();
        $scope.version = {};
    };

}]);