/****************************************************************
 *
 * 파일명 : virtualAccountRegister.js
 * 설  명 : 가상계좌 입금 생성 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.07.24     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  가상계좌 입금 생성 조회 그리드 생성
 */
app.controller('virtualAccountRegisterCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('virtualAccountRegisterCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("vaBankcodeCombo", vaBankcodeComboData); // 발급할 계좌의 은행코드

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("virtualAccountRegisterCtrl", function(event, data) {
        // $("#va_mny").val(data.va_mny); // 가상계좌 발급금액

        $scope.searchVirtualAccountRegister();
        event.preventDefault();
    });

    $scope.searchVirtualAccountRegister = function() {
    };
    // <-- //검색 호출 -->

    // 가상계좌 발급
    $scope.virtualAccountRegisterSave = function() {
        // if ($("#ordr_idxx").val() == "") {
        //     $scope._popMsg(messages["virtualAccountRegister.ordrIdxxBlankAlert"]); // 주문번호를 입력해주세요.
        //     return;
        // }
        // if ($("#va_name").val() == "") {
        //     $scope._popMsg(messages["virtualAccountRegister.vaNameBlankAlert"]); // 입금자명을 입력해주세요.
        //     return;
        // }
        // if ($("#va_mny").val() == "") {
        //     $scope._popMsg(messages["virtualAccountRegister.vaMnyBlankAlert"]); // 가상계좌 발급금액을 입력해주세요.
        //     return;
        // }
        // if ($scope.srchVaBankcodeCombo.selectedValue == "") {
        //     $scope._popMsg(messages["virtualAccountRegister.vaBankcodeBlankAlert"]); // 입금은행을 입력해주세요.
        //     return;
        // }
        // if ($("#vaDateBlankAlert").val() == "") {
        //     $scope._popMsg(messages["virtualAccountRegister.vaDateBlankAlert"]); // 입금마감 시각을 입력해주세요.
        //     return;
        // }

        var params = {};
        params.orgnCd = orgnCd;
        params.hqOfficeCd = hqOfficeCd;
        params.storeCd = storeCd;
        params.userId = userId;
        params.site_cd = site_cd;
        params.kcp_cert_info = kcp_cert_info;
        params.amount = $("#va_mny").val(); // 총 금액
        params.va_mny = $("#va_mny").val(); // 가상계좌 발급금액
        params.ordr_idxx = $("#ordr_idxx").val(); // 상점관리 주문번호
        params.good_name = $("#good_name").val(); // 상품명
        params.buyr_name = $("#buyr_name").val(); // 주문자명
        params.buyr_tel2 = $("#buyr_tel2").val(); // 주문자 E-Mail
        params.buyr_mail = $("#buyr_mail").val(); // 주문자 휴대폰번호
        params.va_bankcode = $scope.srchVaBankcodeCombo.selectedValue; // 발급할 계좌의 은행코드
        params.va_name = $("#va_name").val(); // 입금자명
        params.va_date = $("#va_date").val(); // 입금 마감시각
        var receipt = $('input[name=receipt]:checked').val();
        if(receipt === "Y") {
            params.va_receipt_gubn = $('input[name=vaReceiptGubn]:checked').val(); // 현금영수증 발행용도
            params.va_taxno = $("#va_taxno").val(); // 현금영수증 식별번호
        } else {
            params.va_receipt_gubn = ""; // 현금영수증 발행용도
            params.va_taxno = ""; // 현금영수증 식별번호
        }


        // params.amount = 1004; // 총 금액
        // params.va_mny = 1004; // 가상계좌 발급금액
        // params.ordr_idxx = "TEST202407161721116753952"; // 상점관리 주문번호
        // params.good_name = "운동화"; // 상품명
        // params.buyr_name = "홍길동"; // 주문자명
        // params.buyr_tel2 = "01012345678"; // 주문자 E-Mail
        // params.buyr_mail = "test@test.co.kr"; // 주문자 휴대폰번호
        // params.va_bankcode = "BK04"; // 발급할 계좌의 은행코드
        // params.va_name = "홍길동"; // 입금자명
        // params.va_date = "20240731093030"; // 입금 마감시각
        // params.va_receipt_gubn = ""; // 현금영수증 발행용도
        // params.va_taxno = ""; // 현금영수증 식별번호


        // 로딩바 show
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

        $.ajax({
            type: "POST",
            url: "/iostock/loan/virtualAccount/virtualAccountRegister/getVirtualAccountRegisterApiSave.sb",
            data: JSON.stringify(params),
            cache: false,
            dataType: "json",
            contentType : 'application/json',
            success: function(result){
                // alert(result.status);
                // alert(result.data);
                // alert(result.data.resultCode);
                // alert(result.data.resultMessage);
                if (result.data.resultCode.toString() === "0000") {
                    $scope._popMsg("정상처리 되었습니다.");
                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');
                }
                else if (result.data.resultCode.toString() !== "0000") {
                    $scope._popMsg(result.data.resultMessage.toString());
                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');
                }
                // if (result.status === "OK") {
                //     $scope._popMsg("저장되었습니다.");
                //     $scope.close();
                // }
                else if (result.status === "FAIL") {
                    $scope._popMsg('Ajax Fail By HTTP Request');
                    $scope.$broadcast('loadingPopupInactive');
                }
                else if (result.status === "SERVER_ERROR") {
                    $scope._popMsg(result.message);
                    $scope.$broadcast('loadingPopupInactive');
                }
                /*else if(result.status === undefined) {
                    location.href = "/";
                }*/
                else {
                    var msg = result.status + " : " + result.message;
                    $scope._popMsg(msg);
                    $scope.$broadcast('loadingPopupInactive');
                }
            }
        });
    };

    // 현금영수증 발급 여부
    $scope.radioReceiptChange = function() {
        var receipt = $('input[name=receipt]:checked').val();
        if(receipt == "Y") {
            $("#trReceipt").css("display", "");
            $("#trReceipt2").css("display", "");
        } else {
            $("#trReceipt").css("display", "none");
            $("#trReceipt2").css("display", "none");
        }
    };

    // 발행용도
    $scope.radioVaReceiptGubnChange = function() {
        var vaReceiptGubn = $('input[name=vaReceiptGubn]:checked').val();
        if(vaReceiptGubn == "0") {
            $("#thVaReceiptGubn0").css("display", "");
            $("#thVaReceiptGubn1").css("display", "none");
        } else {
            $("#thVaReceiptGubn0").css("display", "none");
            $("#thVaReceiptGubn1").css("display", "");
        }
    };

    // 팝업 닫기
    $scope.close = function(){
        $("#va_mny").val(""); // 가상계좌 발급금액
        $("#ordr_idxx").val(""); // 상점관리 주문번호
        $("#good_name").val(""); // 상품명
        $("#buyr_name").val(""); // 주문자명
        $("#buyr_tel2").val(""); // 주문자 E-Mail
        $("#buyr_mail").val(""); // 주문자 휴대폰번호
        $scope.srchVaBankcodeCombo.selectedIndex = 0; // 발급할 계좌의 은행코드
        $("#va_name").val(""); // 입금자명
        $("#va_date").val(""); // 입금 마감시각

        $("#va_taxno").val(""); // 현금영수증 식별번호

        $scope.wjVirtualAccountRegisterLayer.hide();
    };

}]);