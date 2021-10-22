/****************************************************************
 *
 * 파일명 : dlvrEmpDtl.js
 * 설  명 : 배달사원정보관리 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.10.20     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('dlvrEmpDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrEmpDtlCtrl', $scope, $http, false));

    // 배달사원정보
    $scope.dlvrEmpInfo;

    $scope.initGrid = function (s, e) {

    };

    $scope.$on("dlvrEmpDtlCtrl", function (event, data) {
        if(!isEmptyObject(data)){
            // 상세 조회
            $scope.srchDtl(data);
        }
    });
    
    // 상세 조회
    $scope.srchDtl = function (data) {

        var params = {};
        params.dlvrEmpNo = data.dlvrEmpNo;

        $scope._postJSONQuery.withOutPopUp( "/dlvr/manage/info/dlvrEmp/getDlvrEmpDtl.sb", params, function(result) {

            $scope.dlvrEmpInfo = result.data.data;

            //
            if($scope.dlvrEmpInfo.smsRecvYn === "Y") {
                $scope.dlvrEmpInfo.smsRecvYn = "수신"
            }else{
                $scope.dlvrEmpInfo.smsRecvYn = "미수신"
            }

            //
            if($scope.dlvrEmpInfo.useYn === "Y") {
                $scope.dlvrEmpInfo.useYn = "사용"
            }else{
                $scope.dlvrEmpInfo.useYn = "미사용"
            }

        });
    };
    // 수정
    $scope.modDtl = function(){

        $scope.wjDlvrEmpDtlPopLayer.hide();
        $scope.wjDlvrEmpRegPopLayer.show(true);
        $scope._broadcast('dlvrEmpRegCtrl', $scope.dlvrEmpInfo.dlvrEmpNo);
    };

    // 닫기
    $scope.closeDtl = function () {
        $scope.wjDlvrEmpDtlPopLayer.hide();
    };
}]);