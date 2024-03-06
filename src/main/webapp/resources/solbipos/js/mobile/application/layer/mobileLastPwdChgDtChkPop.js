/****************************************************************
 *
 * 파일명 : mobileLastPwdChgDtChkPop.js
 * 설  명 : 6개월이상 비밀번호 미수정시 알림 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.02.16     김설아      1.0
 *
 * **************************************************************/

// 변경하기
$("#btnPwdChg").click(function(){
    // 비밀번호 변경 레이어 팝업 가져오기 (pwChgPop.jsp)
    var id = userId;
    $("#labelUserId").text(id);
    $("#pwdUserId").val(id);
    $("#fullDimmedPw").show();
    $("#layerpw").show();

    $("#fullDimmedMobileLastPwdChgDtChkPop").css('display', 'none');
    $("#layerMobileLastPwdChgDtChkPop").css('display', 'none');
});

// 1. 쿠키 만들기
function setCookie(name, value, expiredays) {
    var today = new Date();
    today.setDate(today.getDate() + expiredays);
    document.cookie = name + '=' + escape(value) + '; path=/; expires=' + today.toGMTString() + ';'
}

// 2. 쿠키 가져오기
function getCookie(name){
    var cName = name + "=";
    var x = 0;
    while ( x <= document.cookie.length )
    {
        var y = (x+cName.length);
        if ( document.cookie.substring( x, y ) == cName )
        {
            if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                endOfCookie = document.cookie.length;
            return unescape( document.cookie.substring( y, endOfCookie ) );
        }
        x = document.cookie.indexOf( " ", x ) + 1;
        if ( x == 0 )
            break;
    }
    return "";
}

// alert(getCookie("notLastPwdChgDtChkPop" + userId));

// 쿠키체크 후 팝업 띄우기
if(getCookie("notLastPwdChgDtChkPop" + userId)!="Y") {
    // 메인화면 진입인지 체크
    if(mainYn == "Y") {
        // 6개월이상 비밀번호 미수정시 알림 팝업
        if (lastPwdChgDtChk === "Y") {
            // $("#fullDimmedMobileLastPwdChgDtChkPop").show();
            // $("#layerMobileLastPwdChgDtChkPop").show();
        }
    }
}

// 다음에 변경하기(90일)
$("#btn_close").click(function(){
    setCookie("notLastPwdChgDtChkPop" + userId,'Y', 90);
    $("#fullDimmedMobileLastPwdChgDtChkPop").css('display', 'none');
    $("#layerMobileLastPwdChgDtChkPop").css('display', 'none');
});