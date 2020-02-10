<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="agencyInfoView" class="subCon" style="display:none;">

    <%--============================================= 탭 =============================================--%>
    <ul class="subTab">
        <%-- 설치업체관리 업체정보 --%>
        <li><a id="agencyInfoTab" href="#" class="on"><s:message code="instlAgency.agencyInfo" /></a></li>
        <%-- 설치업체관리 사원관리 --%>
        <li><a id="empManageTab"  href="#" onClick="changeTabInstlAgency('emp');"><s:message code="instlAgency.empManage" /></a></li>
        <%-- 설치업체관리 인증관리 --%>
        <li><a id="authManageTab"  href="#" onClick="changeTabInstlAgency('auth');"><s:message code="instlAgency.authManage" /></a></li>
    </ul>
    <div style="padding:10px; height:50px;">
        <%-- 신규등록 --%>
        <button class="btn_skyblue ml5 fr" id="btnNewRegAgency" onClick="newRegAgency()">
            <s:message code="instlAgency.newReg"/>
        </button>
    </div>

    <form name="agencyForm">
        <table class="tblType01 mt10" style="border-top: 1px solid #CCCCCC;">
            <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
            </colgroup>
            <tbody>
                <tr>
                    <%-- 총판구분 --%>
                    <th><s:message code="instlAgency.agencyType" /></th>
                    <td colspan="3">
                        <select id="ai_agencyType"  class='wj-content' style="width:150px;" onchange="hideAgency();">
                            <option value="dist"><s:message code="instlAgency.dist"/></option>
                            <option value="agency"><s:message code="instlAgency.agency"/></option>
                        </select>
                        <select id="ai_pAgencyCd" class='wj-content' style="width:150px;">
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><s:message code="instlAgency.agencyCd" />/<s:message code="instlAgency.agencyNm" /></th>
                    <td><input type="text" id="ai_agencyCd" readonly ng-model="ai_agencyCd" class="sb-input" style="width:90px;"> /
                        <input type="text" id="ai_agencyNm" name="ai_agencyNm" ng-model="ai_agencyNm" class="sb-input" style="width:167px;" maxlength="50"/>
                    </td>
                    <%-- 대표자명 --%>
                    <th><s:message code="instlAgency.ownerNm" /></th>
                    <td><input type="text" id="ai_ownerNm" name="ai_ownerNm" ng-model="ai_ownerNm" class="sb-input w100" maxlength="25"/>
                    </td>
                </tr>
                <tr>
                    <%-- 사업자번호 --%>
                    <th><s:message code="instlAgency.bizNo" /></th>
                    <td><input type="text" id="ai_bizNo" name="ai_bizNo" ng-model="ai_bizNo" class="sb-input w100" maxlength="10"/>
                    </td>
                    <%-- 상호명 --%>
                    <th><s:message code="instlAgency.bizStoreNm" /></th>
                    <td><input type="text" id="ai_bizStoreNm" name="ai_bizStoreNm" ng-model="ai_bizStoreNm" class="sb-input w100" maxlength="50"/>
                    </td>
                </tr>
                <%--<tr>
                    &lt;%&ndash; 업태 &ndash;%&gt;
                    <th><s:message code="instlAgency.bizItem" /></th>
                    <td><input type="text" id="bizItem" name="bizItem" ng-model="bizItem" class="sb-input w100" maxlength="15"/></td>
                    &lt;%&ndash; 업체구분 &ndash;%&gt;
                    <th><s:message code="instlAgency.bizType" /></th>
                    <td><input type="text" id="bizType" name="bizType" ng-model="bizType" class="sb-input w100" maxlength="5"/></td>
                </tr>--%>
                <tr>
                    <%-- 전화번호 --%>
                    <th><s:message code="instlAgency.telNo" /></th>
                    <td><input type="text" id="ai_telNo" name="ai_telNo" ng-model="ai_telNo" class="sb-input w100" maxlength="15"/>
                    </td>
                    <%-- 팩스번호 --%>
                    <th><s:message code="instlAgency.faxNo" /></th>
                    <td><input type="text" id="ai_faxno" name="ai_faxno" ng-model="ai_faxno" class="sb-input w100" maxlength="15" /></td>
                </tr>
                <tr>
                    <%-- 이메일주소 --%>
                    <th><s:message code="instlAgency.emailAddr" /></th>
                    <td><input type="text" id="ai_emailAddr" name="ai_emailAddr" ng-model="ai_emailAddr" class="sb-input w100" maxlength="100"/></td>
                    <%-- 홈페이지주소 --%>
                    <th><s:message code="instlAgency.hmpgAddr" /></th>
                    <td><input type="text" id="ai_hmpgAddr" name="ai_hmpgAddr" ng-model="ai_hmpgAddr" class="sb-input w100" maxlength="100"/></td>
                </tr>
                <tr>
                    <%-- 주소 --%>
                    <th rowspan="3"><s:message code="instlAgency.addr" /></th>
                    <td colspan="3"><input type="text" id="ai_postNo" name="ai_postNo" ng-model="ai_postNo" class="sb-input w20" maxlength="5" style="width:90px;"/>
                                      <a href="#" class="btn_grayS ml5"><s:message code="instlAgency.addrSearch" /></a>
                    </td>
                </tr>
                <tr>
                    <td colspan="3"><input type="text" id="ai_addr" name="ai_addr" ng-model="ai_addr" class="sb-input w100" maxlength="100"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="3"><input type="text" id="ai_addrDtl" name="ai_addrDtl" ng-model="ai_addrDtl" class="sb-input w100" maxlength="100"/>
                    </td>
                </tr>
                <tr>
                    <%-- 비고 --%>
                    <th><s:message code="instlAgency.remark" /></th>
                    <td colspan="3"><input type="text" id="ai_remark" name="ai_remark" ng-model="ai_remark" class="sb-input w100" maxlength="250" /></td>
                    <%-- 저장타입 지정 --%>
                    <input type="hidden" id="ai_saveType" name="ai_saveType" ng-model="ai_saveType">
                </tr>
            </tbody>
        </table>
        <%-- 저장 --%>
        <div class="btnSet" id="ai_btnSave" class="mt10 mb10" align="center" style="padding:20px;">
            <button class="btn btn_blue" onClick="saveAgency();"><s:message code="cmm.save"/></button>
        </div>
    </form>
</div>

<script>

    // 저장타입
    $("#ai_saveType").val("REG");

    // 로그인 권한 값
    var orgnFg      = "${orgnFg}";
    var orgnCd      = "${orgnCd}";

    $(document).ready(function(){
        // 대리점 목록 바인딩
        setAgencySel(agColList);
    });

    <%-- 업체정보 조회 --%>
    function getAgencyInfo(data) {

        var params = {};
        params.agencyCd = data.agencyCd;

        $.postJSON("/pos/license/instlAgency/getInstlAgencyDtl.sb", params,
            function(result) {

                var dtlData = result.data;

                if(dtlData.pAgencyCd === "00000"){
                    $("#ai_agencyType").val("dist");
                    $("#ai_pAgencyCd").val("");
                    $("#ai_pAgencyCd").css('display', 'none');

                    if(orgnFg === "MASTER"){
                        $("#ai_btnSave").css('display', '');
                    }else{
                        $("#ai_btnSave").css('display', 'none');
                    }

                }else{
                    $("#ai_agencyType").val("agency");
                    $("#ai_pAgencyCd").css('display', '');
                    $("#ai_pAgencyCd").val(dtlData.pAgencyCd);

                    if(orgnCd === dtlData.agencyCd){
                        $("#ai_btnSave").css('display', 'none');
                    }else{
                        $("#ai_btnSave").css('display', '');
                    }

                }

                $("#ai_agencyType").css('background-color', '#F0F0F0');
                $("#ai_agencyType").attr("disabled", true);
                $("#ai_pAgencyCd").css('background-color', '#F0F0F0');
                $("#ai_pAgencyCd").attr("disabled", true);

                $("#ai_agencyCd").val(dtlData.agencyCd);
                $("#ai_agencyNm").val(dtlData.agencyNm);
                $("#ai_ownerNm").val(dtlData.ownerNm);
                $("#ai_bizNo").val(dtlData.bizNo);
                $("#ai_bizStoreNm").val(dtlData.bizStoreNm);
                /*$("#ai_bizType").val(dtlData.bizType);
                $("#ai_bizItem").val(dtlData.bizItem);*/
                $("#ai_telNo").val(dtlData.telNo);
                $("#ai_faxNo").val(dtlData.faxNo);
                $("#ai_emailAddr").val(dtlData.emailAddr);
                $("#ai_hmpgAddr").val(dtlData.hmpgAddr);
                $("#ai_postNo").val(dtlData.postNo);
                $("#ai_addr").val(dtlData.addr);
                $("#ai_addrDtl").val(dtlData.addrDtl);
                $("#ai_remark").val(dtlData.remark);

                // 저장타입 지정
                $("#ai_saveType").val("MOD");
            },
            function (result) {
                s_alert.pop(result.message);
                return;
            }
        );
    }

    <%-- 신규버전 등록 --%>
    function newRegAgency(){

        if(orgnFg === "MASTER"){
            $("#ai_agencyType").val("dist");
            $("#ai_pAgencyCd").val("");
            $("#ai_pAgencyCd").css('display', 'none');
        }else{
            $("#ai_agencyType").val("agency");
            $("#ai_pAgencyCd").val(orgnCd);
            $("#ai_pAgencyCd").css('display', '');
        }

        $("#ai_agencyType").css('background-color', '#F0F0F0');
        $("#ai_agencyType").attr("disabled", true);
        $("#ai_pAgencyCd").css('background-color', '#F0F0F0');
        $("#ai_pAgencyCd").attr("disabled", true);

        $("#ai_btnSave").css('display', '');

        $("#ai_agencyCd").val("");
        $("#ai_agencyNm").val("");
        $("#ai_ownerNm").val("");
        $("#ai_bizNo").val("");
        $("#ai_bizStoreNm").val("");
        /*$("#ai_bizType").val("");
        $("#ai_bizItem").val("");*/
        $("#ai_telNo").val("");
        $("#ai_faxNo").val("");
        $("#ai_emailAddr").val("");
        $("#ai_hmpgAddr").val("");
        $("#ai_postNo").val("");
        $("#ai_addr").val("");
        $("#ai_addrDtl").val("");
        $("#ai_remark").val("");

        // 저장타입 지정
        $("#ai_saveType").val("REG");
    }

    <%-- 업체정보저장 --%>
    function saveAgency(){

        // valid check
        if(chkValidAgency()){

            var params = {};

            params.agencyType = $("#ai_agencyType").val();
            params.pAgencyCd = $("#ai_pAgencyCd").val();
            params.agencyCd = $("#ai_agencyCd").val();
            params.agencyNm = $("#ai_agencyNm").val();
            params.ownerNm = $("#ai_ownerNm").val();
            params.bizNo = $("#ai_bizNo").val();
            params.bizStoreNm = $("#ai_bizStoreNm").val();
            params.telNo = $("#ai_telNo").val();
            params.faxNo = $("#ai_faxNo").val();
            params.emailAddr = $("#ai_emailAddr").val();
            params.hmpgAddr = $("#ai_hmpgAddr").val();
            params.postNo = $("#ai_postNo").val();
            params.addr = $("#ai_addr").val();
            params.addrDtl = $("#ai_addrDtl").val();
            params.remark = $("#ai_remark").val();
            params.saveType = $("#ai_saveType").val();

            $.postJSON("/pos/license/instlAgency/saveAgency.sb", params, function(response) {

                    if(response.status === 'OK') {
                        s_alert.pop(messages["cmm.saveSucc"]);

                        // 업체 리스트 재조회
                        $("#btnSearch").click();

                    }
                },
                function (result) {
                    s_alert.pop(result.message);
                }
            );
        }
    };

    // ValidDation Check
    function chkValidAgency(){

        /*업체명*/
        if($("#ai_agencyNm").val() === ""){
            s_alert.pop("<s:message code='instlAgency.agencyNm' /><s:message code='cmm.require.text' />");
            return false;
        }

        /*대표자명*/
        if($("#ai_ownerNm").val() === ""){
            s_alert.pop("<s:message code='instlAgency.ownerNm' /><s:message code='cmm.require.select' />");
            return false;
        }

        /*사업자번호*/
        if($("#ai_bizNo").val() === ""){
            s_alert.pop("<s:message code='instlAgency.bizNo' /><s:message code='cmm.require.text' />");
            return false;
        }

        /*상호명*/
        if($("#ai_bizStoreNm").val() === ""){
            s_alert.pop("<s:message code='instlAgency.bizStoreNm' /><s:message code='cmm.require.text' />");
            return false;
        }

        /*전화번호*/
        if($("#ai_telNo").val() === ""){
            s_alert.pop("<s:message code='instlAgency.telNo' /><s:message code='cmm.require.text' />");
            return false;
        }

        /*주소_우편번호*/
        if($("#ai_postNo").val() === ""){
            s_alert.pop("<s:message code='instlAgency.postNo' /><s:message code='cmm.require.text' />");
            return false;
        }

        /*주소*/
        if($("#ai_addr").val() === ""){
            s_alert.pop("<s:message code='instlAgency.addr' /><s:message code='cmm.require.text' />");
            return false;
        }

        /*주소_상세*/
        if($("#ai_addrDtl").val() === ""){
            s_alert.pop("<s:message code='instlAgency.addrDtl' /><s:message code='cmm.require.text' />");
            return false;
        }

        return true;
    }

    <%-- 총판구분 대리점 SelectBox Setting --%>
    function setAgencySel (agencyList){

        // 옵션 초기화
        $("#ai_pAgencyCd").find("option").remove();

        var list = agencyList;
        var ele = document.getElementById('ai_pAgencyCd');

        for (var i = 0; i < list.length; i++) {
            ele.innerHTML = ele.innerHTML + '<option value="' + list[i]['agencyCd'] + '">' + list[i]['agencyNm'] + '</option>';
        }
    }

    <%-- 총판구분 선택에 따른 대리점 SelectBox Visible --%>
    function hideAgency(){
        if($("#ai_agencyType").val() === "dist"){
            $("#ai_pAgencyCd").css('display', 'none');
        }else{
            $("#ai_pAgencyCd").css('display', '');
            $("#ai_pAgencyCd option:eq(0)").attr("selected", "selected");
        }
    }

</script>
