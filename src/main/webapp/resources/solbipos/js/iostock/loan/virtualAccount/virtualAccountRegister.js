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
        // 현재날짜 기준으로 3일 후
        var today = new Date();
        var date = new Date(today.setDate(today.getDate() + 3));
        var yyyy = date.getFullYear();
        var mm = date.getMonth()+1; //January is 0!
        var dd = date.getDate();
        var hours = date.getHours(); // 시
        var minutes = date.getMinutes();  // 분
        var seconds = date.getSeconds();  // 초
        if (dd < 10) { dd = '0' + dd; }
        if (mm < 10) { mm = '0' + mm; }
        if (hours < 10) { hours = '0' + hours; }
        if (minutes < 10) { minutes = '0' + minutes; }
        if (seconds < 10) { seconds = '0' + seconds; }
        date = String(yyyy) + String(mm) + dd + hours + minutes + seconds;
        var date2 = String(yyyy) + "-" + String(mm) + "-" + dd + " " + hours + ":" + minutes + ":" + seconds;

        $("#lblStoreCd").text(data.storeCd); // 매장코드
        $("#buyr_name").val(userNm); // 주문자명
        $("#va_name").val(userNm); // 입금자명
        $("#va_date").val(date); // 입금 마감시각
        $("#va_date2").val(date2); // 입금 마감시각 (YYYY-MM-DD HH24:MI:SS)

        // 가상계좌 기초정보 체크
        $scope.virtualAccountInfoChk();

        // 가상계좌 발급금액 체크
        $scope.vaMnyChk(data.va_mny);

        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 가상계좌 기초정보 체크
    $scope.virtualAccountInfoChk = function() {
        var params = {};
        params.storeCd = $("#lblStoreCd").text();

        $scope._postJSONQuery.withOutPopUp("/iostock/loan/virtualAccount/virtualAccountRegister/getVirtualAccountKeyColList.sb", params, function(response){
            var info = response.data.data.list[0];

            if(response.data.data.list.length > 0) {
                if(info.siteCd == "" || info.siteCd == null) {
                    // 팝업 닫기
                    $scope.close();
                    $scope._popMsg(messages["virtualAccountRegister.virtualAccountInfoChkAlert"]); // 가상계좌 기초정보가 없습니다.
                    return;
                } else {
                    $("#lblHqOfficeCd").text(info.hqOfficeCd);
                    $("#lblStoreNm").text(info.storeNm);
                    $("#lblSiteCd").text(info.siteCd);
                    $("#lblKcpCertInfo").text(info.kcpCertInfo);
                }
            }
        });
    };

    // 가상계좌 발급금액 체크
    $scope.vaMnyChk = function(va_mny) {
        if (va_mny == "" || va_mny == null) {
            // 팝업 닫기
            $scope.close();
            $scope._popMsg(messages["virtualAccountRegister.vaMnyBlankAlert"]); // 가상계좌 발급금액을 입력해주세요.
            return;
        } else {
            // 숫자만 입력
            var numChkexp = /[^0-9]/g;
            if (numChkexp.test(va_mny)) {
                // 팝업 닫기
                $scope.close();
                $scope._popMsg(messages["virtualAccountRegister.vaMnyNumberChkAlert"]); // 가상계좌 발급금액은 숫자만 입력해주세요.
                return;
            }

            if(parseInt(va_mny) < 1){
                // 팝업 닫기
                $scope.close();
                $scope._popMsg(messages["virtualAccountRegister.vaMnyInChkAlert"]); // 가상계좌 발급금액은 1원 이상 입력해주세요.
                return;
            }
        }

        $("#va_mny").val(va_mny.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")); // 가상계좌 발급금액
    };

    // 가상계좌 발급
    $scope.virtualAccountRegisterSave = function() {
        var params = {};
        if(orgnFg == "HQ") {
            params.orgnCd = $("#lblHqOfficeCd").text();
        } else {
            params.orgnCd = $("#lblStoreCd").text();
        }
        params.hqOfficeCd = $("#lblHqOfficeCd").text();
        params.storeCd = $("#lblStoreCd").text();
        params.storeNm = $("#lblStoreNm").text();
        params.userId = userId;
        params.site_cd = $("#lblSiteCd").text();
        params.kcp_cert_info = $("#lblKcpCertInfo").text();
        params.va_mny = $("#va_mny").val().replaceAll(",", ""); // 가상계좌 발급금액
        params.amount = params.va_mny; // 총 금액
        // params.ordr_idxx = $("#ordr_idxx").val(); // 상점관리 주문번호
        // params.good_name = $("#good_name").val(); // 상품명
        params.buyr_name = $("#buyr_name").val(); // 주문자명
        params.buyr_tel2 = $("#buyr_tel2").val(); // 주문자 E-Mail
        params.buyr_mail = $("#buyr_mail").val().replaceAll("-", ""); // 주문자 휴대폰번호
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

        if(params.va_bankcode == "" || params.va_bankcode == null) {
            $scope._popMsg(messages["virtualAccountRegister.vaBankcodeBlankAlert"]); // 입금은행을 입력해주세요.
            return;
        }

        if(receipt === "Y") {
            if(params.va_taxno === "" || params.va_taxno == null) {
                $scope._popMsg(messages["virtualAccountRegister.vaTaxnoBlankAlert"]); // 현금영수증 식별번호를 입력해주세요.
                return;
            }
        }

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
                    // 팝업 닫기
                    $scope.close();
                    // 재조회
                    $scope.allSearch();
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
    $scope.close = function() {
        $("#lblHqOfficeCd").text("");
        $("#lblStoreCd").text("");
        $("#lblStoreNm").text("");
        $("#lblSiteCd").text("");
        $("#lblKcpCertInfo").text("");

        $("#va_mny").val(""); // 가상계좌 발급금액
        $("#ordr_idxx").val(""); // 상점관리 주문번호
        $("#good_name").val(""); // 상품명
        $("#buyr_name").val(""); // 주문자명
        $("#buyr_tel2").val(""); // 주문자 E-Mail
        $("#buyr_mail").val(""); // 주문자 휴대폰번호
        $scope.srchVaBankcodeCombo.selectedIndex = 0; // 발급할 계좌의 은행코드
        $("#va_name").val(""); // 입금자명
        $("#va_date").val(""); // 입금 마감시각
        $("#va_date2").val(""); // 입금 마감시각 (YYYY-MM-DD HH24:MI:SS)
        $("#va_taxno").val(""); // 현금영수증 식별번호

        $scope.wjVirtualAccountRegisterLayer.hide();
    };

    // 재조회
    $scope.allSearch = function () {
        // 가상계좌내역
        var scope = agrid.getScope('virtualAccountCtrl');
        scope.searchVirtualAccount();
    };

}]);