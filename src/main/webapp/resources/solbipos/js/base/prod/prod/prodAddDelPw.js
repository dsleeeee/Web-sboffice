/****************************************************************
 *
 * 파일명 : prodAddDelPw.js
 * 설  명 : 상품등록 및 삭제 비밀번호 확인 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.09.11     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  비밀번호 확인 팝업
 */
app.controller('prodAddDelPwCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodAddDelPwCtrl', $scope, $http, true));

    // 선택상품
    $scope.selectedProd;
    $scope.setSelectedProd = function(prod) {
        $scope.selectedProd = prod;
    };
    $scope.getSelectedProd = function(){
        return $scope.selectedProd;
    };

    $scope.$on("prodAddDelPwCtrl", function(event, data) {

        if(data.addDelFg === 'add'){
            $scope.addDelFg = 'add';
        }else {
            $scope.addDelFg = 'del';
        }
        event.preventDefault();
    });

    // 확인
    $scope.confirm = function () {

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

        var params = {};
        params.password = $("#addDelPw").val();

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/prod/prod/chkAddDelPw.sb", params, function (response) {

            var result = response.data.data;
            $("#addDelPw").val('');
            if(result === "true"){
                if($scope.addDelFg === 'add') {
                    $scope.prodModifyLayer.show();
                }else{
                    $scope.prodDeleteLayer.show();
                    $scope.$broadcast('loadingPopupInactive');
                }
                $scope.prodAddDelPwLayer.hide(true);
            }else if(result === "false"){
                $scope.setSelectedProd();
                $scope.prodAddDelPwLayer.hide(true);
                $scope.$broadcast('loadingPopupInactive');
                return false;
            }

        });

    };

    // 전체상품삭제
    $scope.cancel = function () {
        $scope.setSelectedProd();
        $scope.prodAddDelPwLayer.hide(true);
        $("#addDelPw").val('');
    };

    // 팝업 close
    $scope.closeProdAddDelPw = function (){
        $scope.setSelectedProd();
        $scope.prodAddDelPwLayer.hide(true);
        $("#addDelPw").val('');
    }

}]);