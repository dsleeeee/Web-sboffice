<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 본사 사원 상세 팝업 --%>
<div id="empManageDtlLayerDim" class="fullDimmed" style="display: none;"></div>
    <div id="empManageDtlLayer" class="layer" style="display: none">
        <div class="layer_inner">
            <div class="title w870px">
                <!--layerContent-->
                <p class="tit"><s:message  code="instlAgency.empDtl"/> </p>
                <a href="#" class="btn_close"></a>
                    <div class="con">
                        <table class="tblType01 mt10" style="border-top: 1px solid #CCCCCC;">
                            <colgroup>
                                <col class="w15" />
                                <col class="w35" />
                                <col class="w15" />
                                <col class="w35" />
                            </colgroup>
                            <tbody>
                            <tr>
                                <%--사원명--%>
                                <th><s:message code="instlAgency.empNm" /></th>
                                <td>
                                    <label id="emd_empNm"></label>
                                </td>
                                <%-- 사용여부 --%>
                               <th><s:message code="instlAgency.useYn" /></th>
                               <td>
                                   <label id="emd_useYn"></label>
                               </td>
                            </tr>
                           <%-- <tr>
                                &lt;%&ndash;직급&ndash;%&gt;
                                <th><s:message code="instlAgency.rank" /></th>
                                <td>
                                </td>
                                &lt;%&ndash; 직책 &ndash;%&gt;
                                <th><s:message code="instlAgency.position" /></th>
                                <td>
                                </td>
                            </tr>--%>
                            <tr>
                                <%--실제사원번호--%>
                                <th><s:message code="instlAgency.realEmpNo" /></th>
                                <td>
                                    <label id="emd_empNo"></label>
                                </td>
                                <%-- 재직여부 --%>
                                <th><s:message code="instlAgency.serviceFg" /></th>
                                <td>
                                    <label id="emd_serviceFg"></label>
                                </td>
                            </tr>
                            <tr>
                                <%--웹사용여부--%>
                                <th><s:message code="instlAgency.webUseYn" /></th>
                                <td>
                                    <label id="emd_webUseYn"></label>
                                </td>
                                <%--웹사용자ID--%>
                                <th><s:message code="instlAgency.userId" /></th>
                                <td colspan="3">
                                    <label id="emd_userId"></label>
                                </td>
                            </tr>
                            <tr>
                                <%--매핑사원코드--%>
                                <th><s:message code="instlAgency.mapEmpNo" /></th>
                                <td>
                                    <label id="emd_mapEmpNo"></label>
                                </td>
                                <%--관리자구분--%>
                                <th><s:message code="instlAgency.adminFg" /></th>
                                <td colspan="3">
                                    <label id="emd_adminFg"></label>
                                </td>
                            </tr>
                            <tr>
                                <%-- 비밀번호 --%>
                                <%--<th><s:message code="instlAgency.empPwd" /></th>
                                <td>
                                    <label id="emd_empPwd"></label>
                                </td>--%>
                                <%-- 전화번호 --%>
                                <%--<th><s:message code="instlAgency.telNo" /></th>
                                <td>
                                    <label id="emd_telNo"></label>
                                </td>--%>
                            </tr>
                            <tr>
                                <%--휴대폰번호--%>
                                <th><s:message code="instlAgency.mpNo" /></th>
                                <td>
                                    <label id="emd_mpNo"></label>
                                </td>
                                <%-- SMS수신여부 --%>
                                <th><s:message code="instlAgency.smsRecvYn" /></th>
                                <td>
                                    <label id="emd_smsRecvYn"></label>
                                </td>
                            </tr>
                            <%--<tr>
                                &lt;%&ndash; 주소 &ndash;%&gt;
                                <th><s:message code="instlAgency.addr" /></th>
                                <td colspan="3">
                                    <label id="emd_addr"></label>
                                </td>
                            </tr>--%>
                            <tr>
                                <%-- 비고 --%>
                                <th><s:message code="instlAgency.remark" /></th>
                                <td colspan="3">
                                    <label id="emd_remark"></label>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <input type="hidden" id="emd_agencyCd" namae="agencyCd"/>
                    <div class="btnSet">
                        <span><a href="#" class="btn_blue" id="emd_btnEdit"><s:message code="cmm.edit" /></a></span>
                        <span><a href="#" class="btn_blue" id="emd_btnCancel"><s:message code="cmm.cancel" /></a></span>
                    </div>
            </div>
        </div>
    </div>

<script>
    function getEmpManageDtl(agencyCd, empNo){
        $("#empManageDtlLayerDim").show();
        $("#empManageDtlLayer").show();

        // 수정화면 호출 시 사용
        $("#emd_agencyCd").val(agencyCd);

        var params = {};
        params.agencyCd = agencyCd;
        params.empNo = empNo;

        $.postJSON("/pos/license/instlAgency/getAgencyEmpDtl.sb", params, function(result) {
                var dtlData = result.data;

                $("#emd_empNm").text(dtlData.empNm);
                if(dtlData.useYn === "N"){$("#emd_useYn").text("미사용");}else{ $("#emd_useYn").text("사용");}
                $("#emd_empNo").text(dtlData.empNo);
                $("#emd_serviceFg").text(dtlData.serviceFgNm);
                $("#emd_userId").text(dtlData.userId);
                $("#emd_empPwd").text(dtlData.empPwd);
                if(dtlData.webUseYn === "N"){$("#emd_webUseYn").text("미사용");}else{ $("#emd_webUseYn").text("사용");}
                $("#emd_mapEmpNo").text(dtlData.mapEmpNo);
                $("#emd_adminFg").text(dtlData.adminFgNm);
                $("#emd_mpNo").text(dtlData.mpNo);
                if(dtlData.smsRecvYn === "N"){$("#emd_smsRecvYn").text("미수신");}else{ $("#emd_smsRecvYn").text("수신");}
                $("#emd_remark").text(dtlData.remark);
            },
            function (result) {
                s_alert.pop(result.message);
            }
        );
    }

    <%-- 수정 --%>
    $("#empManageDtlLayer #emd_btnEdit").click(function(){
        empManageRegist("MOD", $("#emd_agencyCd").val(), $("#emd_empNo").text());
    });

    <%-- 레이어팝업 닫기 --%>
    $("#empManageDtlLayer .btn_close, #empManageDtlLayer #emd_btnCancel").click(function(){
        $("#empManageDtlLayerDim").hide();
        $("#empManageDtlLayer").hide();
    });

</script>

