/****************************************************************
 *
 * 파일명 : depositDdcReg.js
 * 설  명 : 입금/공제관리 - 매장별집계 - 입금/기타공제 등록팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.20     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('depositDdcRegCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('depositDdcRegCtrl', $scope, $http, false));

    // 일자 셋팅
    $scope.moneyDate = wcombo.genDateVal("#moneyDate", gvStartDate);

    $scope.$on("depositDdcRegCtrl", function (event, data) {

        $scope.setCombo();

        if(data.seqNo !== undefined && data.seqNo !== null && data.seqNo !== ""){
            
            // hidden 키값 셋팅
            $("#hdMoneyDate").val(data.moneyDate);
            $("#hdStoreCd").val(data.storeCd);
            $("#hdSeqNo").val(data.seqNo);
            $scope.moneyDate.value = data.moneyDt;

            // title 셋팅
            $("#lblTitle").text(messages["depositDdc.depositDdcMod"]);

            // 기존 임금/기타공제 정보 셋팅
            $scope.setDepositDdc();

            //  키값 정보 수정 불가
            $("#moneyDate").attr("disabled", true);
            $("#moneyDate").css('background-color', '#F0F0F0');
            $("#moneyStoreNm").attr("disabled", true);
            $("#moneyStoreNm").css('background-color', '#F0F0F0');
            $("#moneyStoreSelectCancelBtn").css("display", "none");
            $("#btnDel").css("display", "");

        }else{

            // title 셋팅
            $("#lblTitle").text(messages["depositDdc.depositDdcReg"]);

            // 키값 정보 등록가능하도록
            $("#moneyDate").attr("disabled", false);
            $("#moneyDate").css('background-color', '#FFFFFF');
            $("#moneyStoreNm").attr("disabled", false);
            $("#moneyStoreNm").css('background-color', '#FFFFFF');
            $("#moneyStoreSelectCancelBtn").css("display", "");
            $("#btnDel").css("display", "none");
        }

    });

    // 기존 임금/기타공제 정보 셋팅
    $scope.setDepositDdc = function(){

        var params = {};
        params.moneyDate = $("#hdMoneyDate").val();
        params.storeCd = $("#hdStoreCd").val();
        params.seqNo = $("#hdSeqNo").val();

        $scope._postJSONQuery.withOutPopUp( "/excclc/excclc/depositDdc/getDepositDdc.sb", params, function(result) {

            if(result.data.data !== null && result.data.data !== ""){

                $("#moneyStoreNm").val("[" + $("#hdStoreCd").val() + "] " + result.data.data.storeNm);
                $("#moneyStoreCd").val($("#hdStoreCd").val());
                $scope.moneyFgCombo.selectedValue = result.data.data.moneyFg;
                $("#moneyAmt").val(result.data.data.moneyAmt);
                $("#moneyRemark").val(result.data.data.remark);
            }

        });
    };

    // 임금/기타공제 저장(등록,수정)
    $scope.saveDepositDdc = function(){

        if($("#moneyStoreCd").val() === ""){
            $scope._popMsg(messages["cmm.require.selectStore"] ); // 매장을 선택해주세요.
            return false;
        }

        if($scope.moneyFgCombo.selectedValue === "" || $scope.moneyFgCombo.selectedValue === null || $scope.moneyFgCombo.selectedValue === undefined){
            $scope._popMsg(messages["depositDdc.moneyFg"] + messages["cmm.require.select"]); // 계정(을)를 선택하세요.
            return false;
        }

        if($("#moneyAmt").val() === ""){
            $scope._popMsg(messages["depositDdc.moneyAmt"] + messages["cmm.require.text"]); // 금액(을)를 입력하세요.
            return false;
        }

        var params = {};

        if($("#hdSeqNo").val() === ""){ // 등록
            params.moneyDate = wijmo.Globalize.format($scope.moneyDate.value, 'yyyyMMdd');
            params.storeCd = $("#moneyStoreCd").val();
            params.moneyFg = $scope.moneyFgCombo.selectedValue;
            params.moneyAmt = $("#moneyAmt").val();
            params.remark = $("#moneyRemark").val();
            params.status = "I";
        }else{ // 수정
            params.moneyDate = $("#hdMoneyDate").val();
            params.storeCd = $("#hdStoreCd").val();
            params.seqNo = $("#hdSeqNo").val();
            params.moneyFg = $scope.moneyFgCombo.selectedValue;
            params.moneyAmt = $("#moneyAmt").val();
            params.remark = $("#moneyRemark").val();
            params.status = "U";
        }


        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/excclc/excclc/depositDdc/saveDepositDdc.sb", params, function () {

            // 매장별집계 리스트 재조회
            var scope = agrid.getScope('storeTotalCtrl');
            scope.searchStoreTotal();

            $scope.closeDepositDdc();

        });

    };

    // 입금/기타공제 삭제
    $scope.delDepositDdc = function(){

        // 삭제하시겠습니까?
        $scope._popConfirm(messages["depositDdc.confirm.del"], function() {

            var params = {};
            params.moneyDate = $("#hdMoneyDate").val();
            params.storeCd = $("#hdStoreCd").val();
            params.seqNo = $("#hdSeqNo").val();
            params.status = "D";

            $scope._postJSONSave.withPopUp("/excclc/excclc/depositDdc/saveDepositDdc.sb", params, function () {

                // 매장별집계 리스트 재조회
                var scope = agrid.getScope('storeTotalCtrl');
                scope.searchStoreTotal();

                $scope.closeDepositDdc();

            });
        });
    };

    // 계정 콤보박스 셋팅
    $scope.setCombo = function () {

        var comboParams = {};
        comboParams.nmcodeGrpCd = "133";
        comboParams.hqOfficeCd = hqOfficeCd;

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : "/excclc/excclc/depositDdc/getMoneyFgCombo.sb", /* 통신할 URL */
            params : comboParams, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list       = response.data.data.list;
                    var comboArray = [];
                    var comboData  = {};

                    comboData.name  = messages["cmm.select"];
                    comboData.value = "";
                    comboArray.push(comboData);

                    // 계정 콤보박스 셋팅
                    for (var i = 0; i < list.length; i++) {
                        comboData       = {};
                        comboData.name  = list[i].nmcodeNm;
                        comboData.value = list[i].nmcodeCd;
                        comboArray.push(comboData);
                    }
                    $scope._setComboData("moneyFg", comboArray);
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {
            if (typeof callback === 'function') {
                $timeout(function () {
                    callback();
                }, 10);
            }
        });
    };

    // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.moneyStoreShow = function () {
        $scope._broadcast('moneyStoreCtrl');
    };

    // 계정 등록 팝업
    $scope.moneyFgReg = function(){
        $scope.moneyFgRegLayer.show(true);
        $scope._broadcast('moneyFgRegCtrl');
    };

    // 닫기
    $scope.closeDepositDdc = function () {

        // 초기화
        $scope.moneyDate.value = getCurDate('-');
        $("#moneyStoreCd").val("");
        $("#moneyStoreNm").val("");
        $scope.moneyFgCombo.selectedIndex = 0;
        $("#moneyAmt").val("");
        $("#moneyRemark").val("");

        $("#hdMoneyDate").val("");
        $("#hdStoreCd").val("");
        $("#hdSeqNo").val("");

        // 키값 정보 등록가능하도록
        $("#moneyDate").attr("disabled", false);
        $("#moneyDate").css('background-color', '#FFFFFF');
        $("#moneyStoreNm").attr("disabled", false);
        $("#moneyStoreNm").css('background-color', '#FFFFFF');
        $("#moneyStoreSelectCancelBtn").css("display", "");
        $("#btnDel").css("display", "none");

        $scope.depositDdcRegLayer.hide();
    };

}]);