/****************************************************************
 *
 * 파일명 : smsUserRegist.js
 * 설  명 : SMS 사용 등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.31     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 본인인증 팝업(KCP 콜백)에서 window.opener로 직접 호출하는 브릿지 함수
// (테스트 버튼처럼 Angular 컨텍스트 안에서 호출되는 경우도 있어서 $$phase 체크 후 $apply)
// result: success, fail, duplicate, error(저장오류) / data: 상태별 부가정보
window.smsUserRegistVerifyCallback = function (result, data) {
    var scope = angular.element(document.getElementById('smsUserRegistView')).scope();

    var applyFn = function () {
        if (result === 'success') {
            scope.verifyState = 'success';
            scope.verifiedInfo = {
                userNm: data.userNm || '',
                telNo: data.telNo || '',
                verifyDt: data.verifyDt || getCurDateTime()
            };
            scope.goStep(3);
        } else if (result === 'duplicate') {
            scope.verifyState = 'duplicate';
            scope.dupUserId = data.dupUserId || '';
        } else {
            // fail, error 공통 - 본인인증 실패 화면으로
            scope.verifyState = 'fail';
            scope.verifyFailReason = data.reason || '';
        }
    };

    if (scope.$root.$$phase) {
        applyFn();
    } else {
        scope.$apply(applyFn);
    }
};

app.controller('smsUserRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsUserRegistCtrl', $scope, $http, $timeout, false));

    // 화면(단계) 상태 : 1-동의, 2-본인인증/등록, 3-완료
    $scope.step = 1;

    // 계정 정보
    $scope.userId = suUserId;
    $scope.userNm = suUserNm;

    // 약관 동의 상태
    $scope.consent = { c1: false, c2: false };
    $scope.detailOpen = { c1: false, c2: false };

    // 본인인증 상태 : before, loading, success, fail, duplicate
    $scope.verifyState = 'before';
    $scope.verifyFailReason = "";
    $scope.dupUserId = "";
    $scope.verifiedInfo = { userNm: "", telNo: "", verifyDt: "" };

    // 약관 동의 토글
    $scope.toggleConsent = function (key) {
        $scope.consent[key] = !$scope.consent[key];
    };

    // 전체 동의 토글
    $scope.toggleAllConsent = function () {
        var next = !$scope.isAllAgreed();
        $scope.consent.c1 = next;
        $scope.consent.c2 = next;
    };

    // 필수 약관 모두 동의했는지
    $scope.isAllAgreed = function () {
        return $scope.consent.c1 && $scope.consent.c2;
    };

    // 약관 상세 펼치기/접기
    $scope.toggleDetail = function (key) {
        $scope.detailOpen[key] = !$scope.detailOpen[key];
    };

    // 단계 이동
    $scope.goStep = function (n) {
        $scope.step = n;
    };

    // 본인인증 시작 (KCP 인증창 오픈)
    // 결과는 팝업(updateVerify.sb)이 window.smsUserRegistVerifyCallback을 직접 호출해서 반영됨
    $scope.runVerify = function () {
        $scope.verifyState = 'loading';

        if(!$scope.consent.c1 || !$scope.consent.c2){
            $scope._popMsg(messages["smsUserRegist.chkAgreeYn"]); // 약관 동의여부를 다시 확인해 주십시오.
            return false;
        }

        var params = {};
        params.agree1Yn = $scope.consent.c1 ? 'Y' : 'N';
        params.agree2Yn = $scope.consent.c2 ? 'Y' : 'N';

        $scope._postJSONQuery.withOutPopUp('/adi/sms/smsUserRegist/smsUserRegist/getVerifyVal.sb', params, function (response) {
            var data = response.data.data;

            var width = 410;
            var height = 500;
            var leftpos = screen.width / 2 - (width / 2);
            var toppos = screen.height / 2 - (height / 2);
            var winopts = "width=" + width + ", height=" + height + ", toolbar=no,status=no,statusbar=no,menubar=no,scrollbars=no,resizable=no";
            var position = ",left=" + leftpos + ", top=" + toppos;

            var url = data.gwUrl + '?' +                    // KCP 인증창
                'site_cd=' + data.siteCd + '&' +            // 상점코드
                'ordr_idxx=' + data.ordrIdxx + '&' +         // 상점관리요청번호
                'req_tx=cert' + '&' +                                // 요청의 종류를 구분하는 변수
                'cert_method=01' + '&' +                             // 01-휴대폰인증 02-공인인증(추후제공)
                'up_hash=' + data.upHash + '&' +             // 요청 hash data
                'Ret_URL=' + data.retUrl + '?sid=' + data.sessionId + '&' +  // 본인인증 결과 리턴페이지
                'cert_otp_use=Y' + '&' +                             // 인증요청시 OTP승인 여부
                'cert_enc_use_ext=Y'
            ;

            var authPopup = window.open(url, 'auth_popup', winopts + position);

            // 팝업을 콜백 없이 그냥 닫아버린 경우 감지해서 원상복구
            var popupChkTimer = setInterval(function () {
                if (authPopup && authPopup.closed) {
                    clearInterval(popupChkTimer);
                    if ($scope.verifyState === 'loading') {
                        $scope.$apply(function () {
                            $scope.verifyState = 'before';
                        });
                    }
                }
            }, 500);
        });
    };

    // 본인인증 다시 시도
    $scope.resetVerify = function () {
        $scope.verifyState = 'before';
    };

    // SMS 사용 등록 삭제
    $scope.deleteRegist = function () {

        var params = {};

        $scope._popConfirm("SMS 사용 등록을 삭제할까요?\n삭제하시면 현재 등록되어 있는 발신번호도 함께 모두 삭제됩니다.", function () {
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/smsUserRegist/smsUserRegist/deleteUserRegist.sb", params, function(){
                $scope._popMsg("삭제되었습니다.");
                $scope.verifyState = 'before';
                $scope.verifiedInfo = { userNm: "", telNo: "", verifyDt: "" };
                $scope.goStep(1);
            });
        });
    };

    // 진입시 체크 - 등록정보 있으면 바로 완료화면
    $scope._postJSONQuery.withOutPopUp('/adi/sms/smsUserRegist/smsUserRegist/getUserRegistInfo.sb', {}, function (response) {
        var data = response.data.data;

        if (data && data.userId) {
            $scope.verifyState = 'success';
            $scope.verifiedInfo = {
                userNm: $scope.userNm,
                telNo: data.telNo || '',
                verifyDt: data.regDt || ''
            };
            $scope.goStep(3);
        }
    });

}]);
