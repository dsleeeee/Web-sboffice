/****************************************************************
 *
 * 파일명 : copyTerminalInfo.js
 * 설  명 : 터미널정보복사 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.02.03     권지현      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('copyTerminalInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('copyTerminalInfoCtrl', $scope, $http, false));

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("copyTerminalInfoCtrl", function(event, data) {

        $("#copyTerminalInfo_storeCd").text(data.storeCd);
        event.preventDefault();
    });

    // 적용
    $scope.btnCopy = function () {
        if ($scope.copyPosNoCombo.selectedIndex === $scope.pastePosNoCombo.selectedIndex) {
            s_alert.pop(messages["terminalManage.copy.msg"]);// 포스번호가 동일합니다.
            return false;
        } else {
            $scope._popConfirm( '이전터미널정보는 삭제됩니다.<br/>' + $scope.copyPosNoCombo.text + "를 " +
                                $scope.pastePosNoCombo.text + "에 복사하시겠습니까?", function() {

                var params = {};
                params.storeCd = $("#copyTerminalInfo_storeCd").text();
                params.copyPosNo = $scope.copyPosNoCombo.selectedValue;
                params.pastePosNo = $scope.pastePosNoCombo.selectedValue;

                console.log(params);

                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._save("/store/manage/terminalManage/copyTerminalInfo/copyTerminalInfo.sb", params, function () {

                    $scope.copyPosNoCombo.selectedIndex = 0;
                    $scope.pastePosNoCombo.selectedIndex = 0;

                    $scope.copyTerminalInfoLayer.hide();

                });
            });
        }
    };

    // 닫기
    $scope.close = function () {
        $scope.copyPosNoCombo.selectedIndex = 0;
        $scope.pastePosNoCombo.selectedIndex = 0;
    };

}]);