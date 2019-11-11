/****************************************************************
 *
 * 파일명 : agencyInfo.js
 * 설  명 : 업체정보 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.10.21     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('agencyInfoCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('agencyInfoCtrl', $scope, $http, true));

    //
    $scope.ai_saveType = "REG";

    $scope.$on("agencyInfoCtrl", function(event, data) {
        $scope.getAgencyInfo(data);
        event.preventDefault();
    });

    // 업체정보 조회
    $scope.getAgencyInfo = function(data){
        var params = {};
        params.agencyCd = data.agencyCd;

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/pos/license/instlAgency/getInstlAgencyDtl.sb', /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {

                    $scope.ai_agencyCd = response.data.data.agencyCd;
                    $scope.ai_agencyNm = response.data.data.agencyNm;
                    $scope.ai_ownerNm = response.data.data.ownerNm;
                    $scope.ai_bizNo = response.data.data.bizNo;
                    $scope.ai_bizStoreNm = response.data.data.bizStoreNm;
                    /*$scope.ai_bizType = response.data.data.bizType;
                    $scope.ai_bizItem = response.data.data.bizItem;*/
                    $scope.ai_telNo = response.data.data.telNo;
                    $scope.ai_faxNo = response.data.data.faxNo;
                    $scope.ai_emailAddr = response.data.data.emailAddr;
                    $scope.ai_hmpgAddr = response.data.data.hmpgAddr;
                    $scope.ai_postNo = response.data.data.postNo;
                    $scope.ai_addr = response.data.data.addr;
                    $scope.ai_addrDtl = response.data.data.addrDtl;
                    $scope.ai_remark = response.data.data.remark;
                    
                    // 저장타입 지정
                    $scope.ai_saveType = "MOD";

                }
            }
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {
            // "complete" code here
        });
    };

    // 신규버전 등록
    $scope.newRegAgency = function(){
        $scope.ai_agencyCd = "";
        $scope.ai_agencyNm = "";
        $scope.ai_ownerNm = "";
        $scope.ai_bizNo = "";
        $scope.ai_bizStoreNm = "";
        /*$scope.ai_bizType = "";
        $scope.ai_bizItem = "";*/
        $scope.ai_telNo = "";
        $scope.ai_faxNo = "";
        $scope.ai_emailAddr = "";
        $scope.ai_hmpgAddr = "";
        $scope.ai_postNo = "";
        $scope.ai_addr = "";
        $scope.ai_addrDtl = "";
        $scope.ai_remark = "";

        // 저장타입 지정
        $scope.ai_saveType = "REG";
    };

    // 업체정보저장
    $scope.saveAgency = function(){

        var params = {};
        params.agencyCd = $scope.ai_agencyCd;
        params.agencyNm = $scope.ai_agencyNm;
        params.ownerNm = $scope.ai_ownerNm;
        params.bizNo = $scope.ai_bizNo;
        params.bizStoreNm = $scope.ai_bizStoreNm;
        params.telNo = $scope.ai_telNo;
        params.faxNo = $scope.ai_faxNo;
        params.emailAddr = $scope.ai_emailAddr;
        params.hmpgAddr = $scope.ai_hmpgAddr;
        params.postNo = $scope.ai_postNo;
        params.addr = $scope.ai_addr;
        params.addrDtl = $scope.ai_addrDtl;
        params.remark = $scope.ai_remark;
        params.saveType = $scope.ai_saveType;

        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: '/pos/license/instlAgency/saveAgency.sb', /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if(JSON.stringify(response.data.data) === "1"){ // 성공 시
                    // 성공 alert
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    // 입력 폼 초기화
                    $scope.newRegAgency();
                    // 업체 리스트 조회
                    $scope.getInstlAgency();
                }
            }
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            $scope._popMsg(messages["cmm.saveFail"]);
            return false;
        }).then(function () {
            // "complete" code here
        });

    };

}]);