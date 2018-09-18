<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
    /*.cusWrap {min-height: 768px; height: 100%;}*/
    /*.content-middle {transform: translateY(50%);}*/

    .cusWrap {width: 100%; min-height: 768px; height: 100%; display: table;}
    .content-middle {width: 100%; height: 100%; display: table-cell; vertical-align: middle;}
    .cusTitle {display:block; width:100%; height:100%; line-height:45px; color:#337dde;  padding:0 15px; font-size:0.875em; position:relative;}

</style>

<div class="cusWrap">
    <div class="content-middle">
        <div class="subCon">
            <div class="searchBar">
                <span class="cusTitle"><s:message code="application.pos.simpleMemberJoin.title"/></span>
            </div>
            <table class="searchTbl">
                <colgroup>
                    <col class="w30" />
                    <col class="w70" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 연락처 --%>
                    <th><s:message code="application.pos.simpleMemberJoin.telNo"/></th>
                    <td><input type="text" id="telNo" name="telNo" class="sb-input w100" maxlength="13" placeholder="<s:message code='application.pos.simpleMemberJoin.numberOnly'/> <s:message code='application.pos.simpleMemberJoin.telNo.ex'/>"/></td>
                </tr>
                <tr>
                    <%-- 회원명 --%>
                    <th><s:message code="application.pos.simpleMemberJoin.membrNm"/></th>
                    <td><input type="text" id="membrNm" name="membrNm" class="sb-input w100" maxlength="25" /></td>
                </tr>
                <tr>
                    <%-- 생년월일 --%>
                    <th><s:message code="application.pos.simpleMemberJoin.birthday"/></th>
                    <td><input type="text" id="birthday" name="birthday" class="sb-input w100" maxlength="8" placeholder="<s:message code='application.pos.simpleMemberJoin.numberOnly'/> <s:message code='application.pos.simpleMemberJoin.birthday.ex'/>"/></td>
                </tr>
                <tr>
                    <%-- 성별 --%>
                    <th><s:message code="application.pos.simpleMemberJoin.gendrFg"/></th>
                    <td>
                        <span class="sb-radio"><input type="radio" id="gendrFgM" name="gendrFg" value="M" checked /><label for="gendrFgM">남</label></span>
                        <span class="sb-radio"><input type="radio" id="gendrFgF" name="gendrFg" value="F" /><label for="gendrFgF">여</label></span>
                    </td>
                </tr>
                <%--<tr>--%>
                <%--&lt;%&ndash; 회원분류 &ndash;%&gt;--%>
                <%--<th><s:message code="application.pos.simpleMemberJoin.membrClass"/></th>--%>
                <%--<td>--%>
                <%--<div class="sb-select">--%>
                <%--<div id="membrClass"></div>--%>
                <%--</div>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <tr>
                    <%-- 비고 --%>
                    <th><s:message code="application.pos.simpleMemberJoin.remark"/></th>
                    <td><input type="text" id="remark" name="remark" class="sb-input w100" maxlength="100" /></td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 pdb20 oh">
                <button class="btn_blue fr" id="btnSave"><s:message code="cmm.save" /></button>
            </div>
        </div>
    </div>
</div>

<script>

<%-- 조회 버튼 클릭 --%>
$("#btnSave").click(function(){
    valueCheck("/application/pos/simpleMemberJoin/save.sb");
});

function valueCheck(sendUrl) {
    <%-- 연락처를 입력해주세요. --%>
    var msg = "<s:message code='application.pos.simpleMemberJoin.telNo'/> <s:message code='cmm.require.text'/>";
    if($("#telNo").val() === "") {
        s_alert.popOk(msg, function(){ $("#telNo").focus(); });
        return;
    }

    <%-- 연락처는 숫자만 입력할 수 있습니다. --%>
    var msg = "<s:message code='application.pos.simpleMemberJoin.telNo'/> <s:message code='cmm.require.number'/>";
    var numChkregexp = /[^0-9]/g;
    if(numChkregexp.test($("#telNo").val())) {
        s_alert.popOk(msg, function(){ $("#telNo").select(); });
        return;
    }

    <%-- 연락처를 정확히 입력해주세요. --%>
    var msg = "<s:message code='application.pos.simpleMemberJoin.telNo'/> <s:message code='application.pos.simpleMemberJoin.validCheck'/>";
    if($("#telNo").val().length < 10) {
        s_alert.popOk(msg, function(){ $("#telNo").select(); });
        return;
    }

    <%-- 회원명을 입력해주세요. --%>
    var msg = "<s:message code='application.pos.simpleMemberJoin.membrNm'/> <s:message code='cmm.require.text'/>";
    if($("#membrNm").val() === "") {
        s_alert.popOk(msg, function(){ $("#membrNm").focus(); });
        return;
    }

    <%-- 회원명의 길이가 너무 깁니다. --%>
    var msg = "<s:message code='application.pos.simpleMemberJoin.membrNm'/> <s:message code='application.pos.simpleMemberJoin.textOver'/>";
    if($("#membrNm").val().getByteLengthForOracle() > 50) {
        s_alert.popOk(msg, function(){ $("#membrNm").select(); });
        return;
    }

    <%-- 생년월일을 입력해주세요. --%>
    var msg = "<s:message code='application.pos.simpleMemberJoin.birthday'/> <s:message code='cmm.require.text'/>";
    if($("#birthday").val() === "") {
        s_alert.popOk(msg, function(){ $("#birthday").focus(); });
        return;
    }

    <%-- 생년월일은 숫자만 입력할 수 있습니다. --%>
    var msg = "<s:message code='application.pos.simpleMemberJoin.birthday'/> <s:message code='cmm.require.number'/>";
    if(numChkregexp.test($("#birthday").val())) {
        s_alert.popOk(msg, function(){ $("#birthday").select(); });
        return;
    }

    <%-- 생년월일을 정확히 입력해주세요. --%>
    var msg = "<s:message code='application.pos.simpleMemberJoin.birthday'/> <s:message code='application.pos.simpleMemberJoin.validCheck'/>";
    if($("#birthday").val().length < 8) {
        s_alert.popOk(msg, function(){ $("#birthday").select(); });
        return;
    }
    save(sendUrl);
}

function save(sendUrl) {
    var param = {};
    param.telNo    = $("#telNo").val();
    param.membrNm  = $("#membrNm").val();
    param.birthday = $("#birthday").val();
    param.gendrFg  = $(':radio[name="gendrFg"]:checked').val();
    param.remark   = $("#remark").val();

    $.postJSONSave(sendUrl, param, function (result) {
        if (result.status === "FAIL") {
            s_alert.pop(result.message);
            return;
        }
        s_alert.pop("<s:message code='cmm.saveSucc'/>");
        resetVal();
    }
    , function (result) {
        console.log(result);
        s_alert.pop(result.message);
    });
}

//변수초기화
function resetVal() {
    $("#telNo").val("");
    $("#membrNm").val("");
    $("#birthday").val("");
    $("#gendrFgM").prop("checked", true);
    $("#remark").val("");
}
</script>
