/****************************************************************
 *
 * 파일명 : dclzRegist.js
 * 설  명 : 근태관리 신규등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.02.10     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 입력구분 VALUE
var inFg = [
    {"name":"전체","value":""},
    {"name":"POS","value":"010"},
    {"name":"WEB","value":"020"}
];

// 시 VALUE
var Hh = [24];
for(i =0 ; i < 24; i++){
    var timeVal = i.toString();
    if(i>=0 && i<=9){
        timeVal = "0" + timeVal;
    }
    Hh[i] = {"name":timeVal,"value":timeVal}
}

// 분, 초 VALUE
var MmSs = [60];
for(i =0 ; i < 60; i++){
    var timeVal = i.toString();
    if(i>=0 && i<=9){
        timeVal = "0" + timeVal;
    }
    MmSs[i] = {"name":timeVal,"value":timeVal}
}

/**
 *  근태관리 신규등록 팝업 생성
 */
app.controller('dclzRegistCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dclzRegistCtrl', $scope, $http, true));

    // 영업일자 콤보박스
    var saleDate = wcombo.genDateVal("#saleDate", gvStartDate);

    // 사원명
    $scope._setComboData("empNoCombo", empList);

    // 출근일시
    var commuteInDt = wcombo.genDateVal("#commuteInDt", gvStartDate);
    $scope._setComboData("commuteInDtHhCombo", Hh);
    $scope._setComboData("commuteInDtMmCombo", MmSs);
    $scope._setComboData("commuteInDtSsCombo", MmSs);

    // 퇴근일시
    var commuteOutDt = wcombo.genDateVal("#commuteOutDt", gvStartDate);
    $scope._setComboData("commuteOutDtHhCombo", Hh);
    $scope._setComboData("commuteOutDtMmCombo", MmSs);
    $scope._setComboData("commuteOutDtSsCombo", MmSs);

    $scope.$on("dclzRegistCtrl", function (event, data) {

        // 초기화
        $scope.resetInput();

        // 수정모드 시, 기존 정보 조회
        if(!isEmptyObject(data)){

            // 수정모드
            $("#saveType").val("mod");

            // 수정모드에서는 영업일자, 사원명 수정 불가
            $("#saleDate").attr("disabled", true);
            $("#saleDate").css('background-color', '#F0F0F0');
            $("#empNo").attr("disabled", true);
            $("#empNo").css('background-color', '#F0F0F0');
            
            // 수정모드에서는 삭제 가능
            $("#btnDel").css("display", "");

            // 기존 정보 불러오기
            $scope.getInfo(data);   

        }else{

            // 등록모드
            $("#saveType").val("reg");
            
            // 등록모드에서는 영업일자, 사원명 등록 가능
            $("#saleDate").attr("disabled", false);
            $("#saleDate").css('background-color', '#FFFFFF');
            $("#empNo").attr("disabled", false);
            $("#empNo").css('background-color', '#FFFFFF');
            
            // 등록모드에서는 삭제 불가
            $("#btnDel").css("display", "none");
        }

        event.preventDefault();
    });

    // 수정 모드 시, 기존 정보 조회
    $scope.getInfo = function(data){

        var params = {};
        params.storeCd = data.storeCd;
        params.empNo = data.empNo;
        params.saleDate = data.saleDate;
        params.inFg = data.inFg;

        $scope._postJSONQuery.withOutPopUp( "/adi/dclz/dclzmanage/dclzmanage/detail.sb", params, function(response){

            var result = response.data.data;

            saleDate.value =  stringToDate(result.saleDate); // 영업일자
            $scope.empNoCombo.selectedValue = result.empNo; // 사원명

            commuteInDt.value = stringToDate((result.commuteInDt).substr(0, 8));  // 출근일시
            $scope.commuteInDtHhCombo.selectedValue = (result.commuteInDt).substr(8, 2);
            $scope.commuteInDtMmCombo.selectedValue = (result.commuteInDt).substr(10, 2);
            $scope.commuteInDtSsCombo.selectedValue = (result.commuteInDt).substr(12, 2);

            commuteOutDt.value = stringToDate((result.commuteOutDt).substr(0, 8)); // 퇴근일시
            $scope.commuteOutDtHhCombo.selectedValue = (result.commuteOutDt).substr(8, 2);
            $scope.commuteOutDtMmCombo.selectedValue = (result.commuteOutDt).substr(10, 2);
            $scope.commuteOutDtSsCombo.selectedValue = (result.commuteOutDt).substr(12, 2);

            $scope.remark = result.remark; // 비고

            // 필요한 키값 hidden에 가지고 있기(수정, 삭제시 사용)
            $("#hdStoreCd").val(result.storeCd);
            $("#hdSaleDate").val(result.saleDate);
            $("#hdInFg").val(result.inFg);

        });
    };
    
    // 등록 및 수정
    $scope.regist = function () {

        var params = {};
        var url = "";

        if($("#saveType").val() === "reg"){

            params.saleDate = wijmo.Globalize.format(saleDate.value, 'yyyyMMdd');
            params.empNo = $scope.empNo;
            params.saleDate = wijmo.Globalize.format(saleDate.value, 'yyyyMMdd'); // 출근일자 - 현재는 영업일자와 동일하게 사용(추후 변경 가능성 유)
            params.commuteInDt = wijmo.Globalize.format(commuteInDt.value, 'yyyyMMdd') + $scope.commuteInDtHh + $scope.commuteInDtMm + $scope.commuteInDtSs;
            params.commuteOutDt = wijmo.Globalize.format(commuteOutDt.value, 'yyyyMMdd') + $scope.commuteOutDtHh + $scope.commuteOutDtMm + $scope.commuteOutDtSs;
            params.remark = $scope.remark;

            url = "/adi/dclz/dclzmanage/dclzmanage/regist.sb";

        }else{

            params.storeCd = $("#hdStoreCd").val();
            params.empNo = $scope.empNo;
            params.saleDate = $("#hdSaleDate").val();
            params.inFg = $("#hdInFg").val();
            params.commuteInDt = wijmo.Globalize.format(commuteInDt.value, 'yyyyMMdd') + $scope.commuteInDtHh + $scope.commuteInDtMm + $scope.commuteInDtSs;
            params.commuteOutDt = wijmo.Globalize.format(commuteOutDt.value, 'yyyyMMdd') + $scope.commuteOutDtHh + $scope.commuteOutDtMm + $scope.commuteOutDtSs;
            params.remark = $scope.remark;

            url = "/adi/dclz/dclzmanage/dclzmanage/modify.sb";
        }


        /** 퇴근시간이 출근시간보다 빠르거나 같습니다. */
        var msg = messages["dclzManage.dtChkMsg"];
        if(Number(params.commuteInDt) >= Number(params.commuteOutDt)){
            $scope._popMsg(msg);
            return;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp(url, params, function (response) {

            if (response.data.status === "OK") {
                // 성공 메시지
                $scope._popMsg(messages["cmm.saveSucc"]);

                // 리스트 부모창 재조회
                var dclzManageScope = agrid.getScope("dclzManageCtrl");
                dclzManageScope.dclzSearch();

                // 팝업 닫기
                $scope.close();
            }
        });
    }
    
    // 삭제
    $scope.delete = function () {

        if($("#saveType").val() === "mod"){ // 수정모드시에만 삭제가능

            /** 해당 근태정보를 삭제하시겠습니까? */
            var msg = messages["dclzManage.delMsg"];
            $scope._popConfirm(msg, function () {
                var params = {};
                params.storeCd = $("#hdStoreCd").val();
                params.empNo = $scope.empNo;
                params.saleDate = $("#hdSaleDate").val();
                params.inFg = $("#hdInFg").val();

                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._postJSONSave.withPopUp("/adi/dclz/dclzmanage/dclzmanage/remove.sb", params, function (response) {

                    if (response.data.status === "OK") {
                        // 성공 메시지
                        $scope._popMsg(messages["cmm.delSucc"]);

                        // 리스트 부모창 재조회
                        var dclzManageScope = agrid.getScope("dclzManageCtrl");
                        dclzManageScope.dclzSearch();

                        // 팝업 닫기
                        $scope.close();
                    }
                });
            });
        }
    };
    
    // 닫기
    $scope.close = function () {
        // 초기화
        $scope.resetInput();

        $scope.wjDclzDetailLayer.hide();
        $scope.wjDclzRegistLayer.hide();
    }

    // 초기화
    $scope.resetInput = function(){

        // 영업일자
        saleDate.value = getCurDate('-');

        // 사원명
        $scope.empNoCombo.selectedIndex = 0;

        // 출근일시
        commuteInDt.value = getCurDate('-');
        $scope.commuteInDtHhCombo.selectedIndex = 9;
        $scope.commuteInDtMmCombo.selectedIndex = 0;
        $scope.commuteInDtSsCombo.selectedIndex = 0;

        // 퇴근일시
        commuteOutDt.value = getCurDate('-');
        $scope.commuteOutDtHhCombo.selectedIndex = 18;
        $scope.commuteOutDtMmCombo.selectedIndex = 0;
        $scope.commuteOutDtSsCombo.selectedIndex = 0;

        // 비고
        $scope.remark = "";

        // 등록인지 수정인지 구분자
        $("#saveType").val("");

        // hidden에 갖고 있던 키값
        $("#hdStoreCd").val("");
        $("#hdSaleDate").val("");
        $("#hdInFg").val("");
    }

}]);