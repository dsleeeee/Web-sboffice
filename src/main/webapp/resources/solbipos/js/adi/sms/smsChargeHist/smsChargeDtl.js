/****************************************************************
 *
 * 파일명 : smsChargeDtl.js
 * 설  명 : SMS결제상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.08.23     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// SMS결제취소 결과
function goCancelResult(res_cd, res_msg)
{
    var params = {};
    params.resultcode = res_cd;
    params.resultmessage = res_msg;

    var scope = agrid.getScope('smsChargeDtlCtrl');
    // 로딩바 hide
    scope.$broadcast('loadingPopupInactive');
    scope.cancelResultShow(params);
}

/**
 *  SMS결제상세 팝업 조회 그리드 생성
 */
app.controller('smsChargeDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsChargeDtlCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("successYnDtlCombo", successYnComboData); // 성공여부
    $scope._setComboData("pgresourceDtlCombo", gpgresourceComboData); // 결제수단

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("smsChargeDtlCtrl", function(event, data) {
        if(data != undefined) {
            $("#orgnNmDtl").val(data.orgnNm);
            $("#chargeIdNmDtl").val(data.chargeIdNm);
            $("#chargeDateDtl").val(data.chargeDate.substring(0,4) + "-" + data.chargeDate.substring(4,6) + "-" + data.chargeDate.substring(6,8));
            $("#chargeTimeDtl").val(data.chargeTime.substring(0,2) + ":" + data.chargeTime.substring(2,4) + ":" + data.chargeTime.substring(4,6));
            $("#chargeAmtDtl").val(data.chargeAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
            $scope.pgresourceDtlCombo.selectedValue = data.pgresource;
            $("#controlnoDtl").val(data.controlno);
            $scope.successYnDtlCombo.selectedValue = data.successYn;
            $("#resultmessageDtl").val(data.resultmessage);

            if(data.successYn == "Y") {
                $("#divCancel").css('display', '');
            } else {
                $("#divCancel").css('display', 'none');
            }
        } else {
            $("#orgnNmDtl").val("");
            $("#chargeIdNmDtl").val("");
            $("#chargeDateDtl").val("");
            $("#chargeTimeDtl").val("");
            $("#chargeAmtDtl").val("");
            $scope.pgresourceDtlCombo.selectedValue = "";
            $("#controlnoDtl").val("");
            $scope.successYnDtlCombo.selectedValue = "";
            $("#resultmessageDtl").val("");

            $("#divCancel").css('display', 'none');
        }
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function(){
        $("#orgnNmDtl").val("");
        $("#chargeIdNmDtl").val("");
        $("#chargeDateDtl").val("");
        $("#chargeTimeDtl").val("");
        $("#chargeAmtDtl").val("");
        $scope.pgresourceDtlCombo.selectedValue = "";
        $("#controlnoDtl").val("");
        $scope.successYnDtlCombo.selectedValue = "";
        $("#resultmessageDtl").val("");

        $("#divCancel").css('display', 'none');

        $scope.wjSmsChargeDtlLayer.hide();

        // 재조회
        var smsChargeHistScope = agrid.getScope('smsChargeHistCtrl');
        smsChargeHistScope.searchSmsChargeHist();
    };

    // SMS결제취소 결과
    $scope.cancelResultShow = function(data){
        $scope.setSelectedSmsChargeDtl(data);
        $scope.wjSmsCancelResultLayer.show(true);
    };

    // 선택
    $scope.selectedSmsChargeDtl;
    $scope.setSelectedSmsChargeDtl = function(store) {
        $scope.selectedSmsChargeDtl = store;
    };
    $scope.getSelectedSmsChargeDtl = function() {
        return $scope.selectedSmsChargeDtl;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // SMS결제취소 결과 팝업 핸들러 추가
        $scope.wjSmsCancelResultLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsCancelResultCtrl', $scope.getSelectedSmsChargeDtl());
            }, 50)
        });
    });
}]);