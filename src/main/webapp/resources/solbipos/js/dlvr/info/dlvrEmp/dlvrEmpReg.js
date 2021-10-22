/****************************************************************
 *
 * 파일명 : dlvrEmpReg.js
 * 설  명 : 배달사원정보관리 신규사원등록 팝업 JavaScript
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

app.controller('dlvrEmpRegCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrEmpRegCtrl', $scope, $http, false));

    // SMS수신여부
    $scope._getComboDataQuery('072', 'smsRecvYn');
    // 사용여부
    $scope._getComboDataQuery('067', 'useYn');

    $scope.$on("dlvrEmpRegCtrl", function (event, data) {

        if(!isEmptyObject(data)){
            $("#btnRegist").css("display", "none");
            $("#btnSave").css("display", "");

            // 상세 조회
            $scope.setDtl(data);

        }else{
            $("#btnRegist").css("display", "");
            $("#btnSave").css("display", "none");

            // 입력폼 초기화
            $scope.resetForm();
        }
    });

    // 상세 조회
    $scope.setDtl = function(data){

        var params = {};
        params.dlvrEmpNo = data;

        $scope._postJSONQuery.withOutPopUp( "/dlvr/manage/info/dlvrEmp/getDlvrEmpDtl.sb", params, function(result) {

            $scope.dlvrEmpInfo = result.data.data;

        });
    };

    // 배달사원 신규등록
    $scope.regist = function(){

        var params = {};
        params.dlvrEmpNm = $("#dlvrEmpNm").val();
        params.hpNo = $("#hpNo").val();
        params.smsRecvYn = $scope.smsRecvYnCombo.selectedValue;
        params.useYn =  $scope.useYnCombo.selectedValue;
        params.remark = $("#remark").val();

        $scope._postJSONSave.withOutPopUp( "/dlvr/manage/info/dlvrEmp/insertDlvrEmp.sb", params, function(response){

            var result = response.data.data;

            if(result < 1) {
                $scope._popMsg(messages["cmm.registFail"]);
                return false;
            }else{
                $scope._popMsg(messages["cmm.registSucc"]);
                $scope.wjDlvrEmpRegPopLayer.hide();

                // 리스트 재조회
                $scope._pageView('dlvrEmpCtrl', 1);
            }
        });
    };

    // 배달사원 정보수정
    $scope.save = function(){

        var params = {};
        params.dlvrEmpNo = $("#dlvrEmpNo").val();
        params.dlvrEmpNm = $("#dlvrEmpNm").val();
        params.hpNo = $("#hpNo").val();
        params.smsRecvYn = $scope.smsRecvYnCombo.selectedValue;
        params.useYn =  $scope.useYnCombo.selectedValue;
        params.remark = $("#remark").val();

        $scope._postJSONSave.withOutPopUp( "/dlvr/manage/info/dlvrEmp/updateDlvrEmp.sb", params, function(response){

            var result = response.data.data;

            if(result < 1) {
                $scope._popMsg(messages["cmm.registFail"]);
                return false;
            }else{
                $scope._popMsg(messages["cmm.registSucc"]);
                $scope.wjDlvrEmpRegPopLayer.hide();

                // 리스트 재조회
                $scope._pageView('dlvrEmpCtrl', 1);
            }
        });
    };

    // 입력폼 초기화
    $scope.resetForm = function(){
        $scope.dlvrEmpInfo.dlvrEmpNo = "";
        $scope.dlvrEmpInfo.dlvrEmpNm = "";
        $scope.dlvrEmpInfo.hpNo = "";
        $scope.smsRecvYnCombo.selectedIndex = 1;
        $scope.useYnCombo.selectedIndex = 1;
        $scope.dlvrEmpInfo.remark = "";
    };

    // 닫기
    $scope.closeReg = function () {
        $scope.wjDlvrEmpRegPopLayer.hide();
    };

}]);