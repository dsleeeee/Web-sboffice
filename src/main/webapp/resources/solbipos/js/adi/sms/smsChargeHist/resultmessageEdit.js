/****************************************************************
 *
 * 파일명 : resultmessageEdit.js
 * 설  명 : 비고(결제메시지) 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.09.22     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  비고(결제메시지) 팝업
 */
app.controller('resultmessageEditCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('resultmessageEditCtrl', $scope, $http, false));

    // <-- 검색 호출 -->
    $scope.$on("resultmessageEditCtrl", function(event, data) {
        event.preventDefault();
    });

    // 비고(결제메시지) 저장
    $scope.resultmessageSave = function(){
        var params = {};
        params.orgnCd           = $("#orgnCd").val();
        params.chargeDate       = $("#chargeDate").val();
        params.chargeTime       = $("#chargeTime").val();
        params.resultmessage    = $("#resultmessage").val();

        $scope._postJSONSave.withPopUp("/adi/sms/smsChargeHist/smsChargeHist/getResultmessageSave.sb", params, function (){
            $scope.wjResultmessageEditLayer.hide();
            var scope = agrid.getScope('smsChargeHistCtrl');
            scope.searchSmsChargeHist();
        });
    };


}]);