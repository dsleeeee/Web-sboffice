/****************************************************************
 *
 * 파일명 : branchKmuDtl.js
 * 설  명 : 그룹관리 신규 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.27     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('branchKmuDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('branchKmuDtlCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYn", useYn);

    $scope.$on("branchKmuDtlCtrl", function(event, data) {
        if ($.isEmptyObject(data)) {
            $scope.resetForm();
        } else {
            $scope.getBranchKmuInfo(data);
        }
        event.preventDefault();
    });

    // 그리드 조회
    $scope.resetForm = function(){
        $("#branchCd").val("자동채번");
        $("#branchNm").val("");
        $("#branchOwnerNm").val("");
        $("#telNo").val("0200000000");
        $("#phoneNo").val("01000000000");
        $("#email").val("");
        $("#postNo").val("");
        $("#addr").val("");
        $("#addrDtl").val("");
        $("#orgplceInfo").val("");
        $scope.useYnCombo.selectedIndex = 1;
    };

    $scope.getBranchKmuInfo = function (data) {
        $scope.resetForm();

        $("#branchCd").val(data.branchCd);
        $("#branchNm").val(data.branchNm);
        $("#branchOwnerNm").val(data.branchOwnerNm);
        $("#telNo").val(data.telNo);
        $("#phoneNo").val(data.phoneNo);
        $("#email").val(data.email);
        $("#postNo").val(data.postNo);
        $("#addr").val(data.addr);
        $("#addrDtl").val(data.addrDtl);
        $("#orgplceInfo").val(data.orgplceInfo);
        if(data.useYn === "Y"){
            $scope.useYnCombo.selectedIndex = 1;
        } else {
            $scope.useYnCombo.selectedIndex = 0;
        }
    };

    $scope.save = function (){

        if (!$scope.valueCheck()) return false;

        var params = {};
        params.branchCd      = $("#branchCd").val();
        params.branchNm      = $("#branchNm").val();
        params.branchOwnerNm = $("#branchOwnerNm").val();
        params.telNo         = $("#telNo").val();
        params.phoneNo       = $("#phoneNo").val();
        params.email         = $("#email").val();
        params.postNo        = $("#postNo").val();
        params.addr          = $("#addr").val();
        params.addrDtl       = $("#addrDtl").val();
        params.orgplceInfo   = $("#orgplceInfo").val();
        params.useYn         = $scope.useYnCombo.selectedValue;

        $scope._postJSONSave.withPopUp("/store/hq/branchKmu/branchKmu/saveBranchKmu.sb", params, function (result){
            $scope._popMsg(messages["cmm.saveSucc"]);
            // 팝업 닫기
            $scope.close();
        });
    };


    $scope.valueCheck = function () {

        // 그룹명 입력 체크
        if(nvl($("#branchNm").val(), '') === ''){
            msg = messages["branchKmu.branchNm"] + messages["branchKmu.noneChk"];
            $scope._popMsg(msg);
            return false;
        }

        // 그룹장명 입력 체크
        if(nvl($("#branchOwnerNm").val(), '') === ''){
            msg = messages["branchKmu.branchOwnerNm"] + messages["branchKmu.noneChk"];
            $scope._popMsg(msg);
            return false;
        }

        // 그룹명 최대길이 체크
        if (nvl($("#branchNm").val(), '') !== '' && nvl($("#branchNm").val() + '', -'').getByteLengthForOracle() > 50) {
            msg = messages["branchKmu.branchNm"] + messages["excelUpload.overLength"] + " 100 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 그룹장명 최대길이 체크
        if (nvl($("#branchOwnerNm").val(), '') !== '' && nvl($("#branchOwnerNm").val() + '', '').getByteLengthForOracle() > 50) {
            msg = messages["branchKmu.branchOwnerNm"] + messages["excelUpload.overLength"] + " 50 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 전화번호 정규식
        var msg = messages["branchKmu.telNo"] + messages["cmm.require.number"];
        var numChkregexp = /[^0-9]/g;
        if (numChkregexp.test($("#telNo").val())) {
            $scope._popMsg(msg);
            return false;
        }

        // 휴대번호 정규식
        msg = messages["branchKmu.phoneNo"] + messages["cmm.require.number"];
        var numChkregexp = /[^0-9]/g;
        if (numChkregexp.test($("#phoneNo").val())) {
            $scope._popMsg(msg);
            return false;
        }

        // 전화번호 최대길이 체크
        if (nvl($("#telNo").val(), '') !== '' && nvl($("#telNo").val() + '', '').getByteLengthForOracle() > 14) {
            msg = messages["branchKmu.telNo"] + messages["excelUpload.overLength"] + " 14 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 휴대번호 최대길이 체크
        if (nvl($("#phoneNo").val(), '') !== '' && nvl($("#phoneNo").val() + '', '').getByteLengthForOracle() > 14) {
            msg = messages["branchKmu.phoneNo"] + messages["excelUpload.overLength"] + " 14 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        //  사용여부를 선택하세요.
        var msg = messages["branchKmu.useYn"] + messages["cmm.require.select"];
        if (isNull($scope.useYnCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        // 이메일 최대길이 체크
        if (nvl($("#email").val(), '') !== '' && nvl($("#email").val() + '', '').getByteLengthForOracle() > 30) {
            msg = messages["branchKmu.email"] + messages["excelUpload.overLength"] + " 30 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 상세주소 최대길이 체크
        if (nvl($("#addrDtl").val(), '') !== '' && nvl($("#addrDtl").val() + '', '').getByteLengthForOracle() > 100) {
            msg = messages["branchKmu.addrDtl"] + messages["excelUpload.overLength"] + " 100 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 비고 최대길이 체크
        if (nvl($("#orgplceInfo").val(), '') !== '' && nvl($("#orgplceInfo").val() + '', '').getByteLengthForOracle() > 2000) {
            msg = messages["branchKmu.orgplceInfo"] + messages["excelUpload.overLength"] + " 2000 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        return true;
    };

    // 팝업 닫기
    $scope.close = function(){
        $scope.wjBranchKmuDtlLayer.hide();
        var scope = agrid.getScope('branchKmuCtrl');
        scope.searchBranchKmuList();
    };

}]);
