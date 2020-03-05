/****************************************************************
 *
 * 파일명 : cornerAdd.js
 * 설  명 : 매장정보관리 > 매장터미널관리 > 코너추가 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.03.02     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('cornerAddCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cornerAddCtrl', $scope, $http, false));

    $scope.$on("cornerAddCtrl", function(event, data) {

       $("#cnr_storeCd").text(data.storeCd);
       $("#cnr_storeNm").text(data.storeNm);
       
       // input 값 초기화
       $scope.resetData();

    });

    // input 값 초기화
    $scope.resetData = function(){
        $scope.cornrNm = "";
        $scope.ownerNm = "";
        $scope.bizNo = "";
        $scope.telNo = "";
        $scope.vanCd = "";
        $scope.vanTermnlNo = "";
        $scope.vanSerNo = "";
    }

    // 코너 추가
    $scope.saveCorner = function(){

        var params = {};
        params.storeCd =  $("#cnr_storeCd").text();
        params.cornrNm = $scope.cornrNm;
        params.ownerNm =  $scope.ownerNm;
        params.bizNo = $scope.bizNo;
        params.telNo = $scope.telNo;
        params.vanCd = $scope.vanCd;
        params.vanTermnlNo = $scope.vanTermnlNo;
        params.vanSerNo = $scope.vanSerNo;

        /*$scope._postJSONSave.withPopUp( baseUrl + "corner/insertCorner.sb", params, function () {
            $scope._popMsg(messages["cmm.saveSucc"]);
            $scope.cornerAddLayer.hide();

            // 재조회
            $scope._broadcast('terminalCtrl', $scope.showCorner());
        });*/

        $.postJSON( baseUrl + "corner/insertCorner.sb", params, function(response) {

                if(response.status === 'OK') {
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    $scope.cornerAddLayer.hide();

                    // 코너 SelectBox 재조회
                    var terminalScope = agrid.getScope('terminalCtrl');
                    terminalScope.setCorner();
                }
            },
            function (result) {
                s_alert.pop(result.message);
            }
        );
    };

    // 닫기
    $scope.close = function(){
        $scope.cornerAddLayer.hide();
    };

}]);