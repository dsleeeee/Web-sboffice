/****************************************************************
 *
 * 파일명 : mobileLastPwdChgDtChkPop.js
 * 설  명 : 6개월이상 비밀번호 미수정시 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.02.16     김설아      1.0
 *
 * **************************************************************/

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

$("#btn_close").click(function(){
    $("#fullDimmedMobileLastPwdChgDtChkPop").css('display', 'none');
    $("#layerMobileLastPwdChgDtChkPop").css('display', 'none');
});


