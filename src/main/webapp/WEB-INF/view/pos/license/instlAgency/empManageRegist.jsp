<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 본사 사원 등록/수정 팝업 --%>
<div id="empManageRegistLayerDim" class="fullDimmed" style="display: none;"></div>
<div id="empManageRegistLayer" class="layer" style="display: none" ng-controller="empManageRegistCtrl">
    <div class="layer_inner">
        <div class="title w870px">
            <!--layerContent-->
            <p class="tit"><label id="emr_title"></label></p>
            <a href="#" class="btn_close"></a>
            <form name="empForm">
                <div class="con">
                    <table class="tblType01" style="border-top: 1px solid #CCCCCC;">
                        <colgroup>
                            <col class="w15" />
                            <col class="w35" />
                            <col class="w15" />
                            <col class="w35" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <%--사원명--%>
                            <th><s:message code="instlAgency.empNm" /><em class="imp">*</em></th>
                            <td>
                                <input type="text" id="emr_empNm" name="emr_empNm" maxlength="50"/>
                            </td>
                            <%-- 사용여부 --%>
                            <th><s:message code="instlAgency.useYn" /><em class="imp">*</em></th>
                            <td>
                                <select class="sb-select" id="emr_useYn">
                                    <option value="N"><s:message code="instlAgency.ynN"/></option>
                                    <option value="Y"><s:message code="instlAgency.ynY"/></option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <%--실제사원번호--%>
                            <th><s:message code="instlAgency.realEmpNo" /></th>
                            <td>
                                <input type="text" id="emr_empNo" name="emr_empNo" maxlength="50" readonly/>
                            </td>
                            <%-- 재직여부 --%>
                            <th><s:message code="instlAgency.serviceFg" /><em class="imp">*</em></th>
                            <td>
                                <select class="sb-select" id="emr_serviceFg">
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <%--웹사용여부--%>
                            <th><s:message code="instlAgency.webUseYn" /></th>
                            <td colspan="3">
                                <select class="sb-select" id="emr_webUseYn" style="width:20%;" onchange="hideWebInfo();">
                                    <option value="N"><s:message code="instlAgency.ynN"/></option>
                                    <option value="Y"><s:message code="instlAgency.ynY"/></option>
                                </select>
                            </td>
                        </tr>
                        <tr id="trUserId">
                            <%--웹사용자ID--%>
                            <th><s:message code="instlAgency.userId" /><em class="imp">*</em></th>
                            <td colspan="3">
                                <input type="text" id="emr_userId" name="emr_userId" maxlength="50" style="width: 20%"/>
                                <input type="hidden" id="duplicationChkFg"/>
                                <%-- 중복체크 --%>
                                <span class="txtIn" id="spanCheckDuplicate">
                                    <a href="#" class="btn_grayS" onClick="checkDuplicate();"><s:message code="systemEmp.chk.duplicate" /></a>
                                </span>
                            </td>
                        </tr>
                        <tr id="trUserPwd">
                            <th><s:message code="instlAgency.userPwd" /><em class="imp">*</em></th>
                            <td colspan="3">
                                <input type="password" id="emr_userPwd" name="emr_userPwd" style="width: 20%">
                                <input type="password" id="emr_userPwdCfm" name="emr_userPwdCfm" style="width: 20%"/>
                            </td>
                        </tr>
                        <tr>
                            <th><s:message code="instlAgency.mapEmpNo" /></th>
                            <td><input type="text" id="emr_mapEmpNo" name="emr_mapEmpNo"></td>
                            <th><s:message code="instlAgency.adminFg" /><em class="imp">*</em></th>
                            <td>
                                <select class="sb-select" id="emr_adminFg">
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <%--휴대폰번호--%>
                            <th><s:message code="instlAgency.mpNo" /><em class="imp">*</em></th>
                            <td>
                                <input type="text" id="emr_mpNo" name="emr_mpNo" maxlength="50"/>
                            </td>
                            <%-- SMS수신여부 --%>
                            <th><s:message code="instlAgency.smsRecvYn" /></th>
                            <td>
                                <select class="sb-select" id="emr_smsRecvYn">
                                    <option value="N"><s:message code="instlAgency.smsN"/></option>
                                    <option value="Y"><s:message code="instlAgency.smsY"/></option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <%-- 비고 --%>
                            <th><s:message code="instlAgency.remark" /></th>
                            <td colspan="3">
                                <input type="text" id="emr_remark" name="emr_remark" maxlength="50"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btnSet">
                    <span><a href="#" class="btn_blue" id="btnSave" onClick="saveEmp()"><s:message code="cmm.save" /></a></span>
                    <span><a href="#" class="btn_blue" id="btnCancel"><s:message code="cmm.cancel" /></a></span>
                    <input type="hidden" id="saveType"/>
                    <input type="hidden" id="emr_agencyCd"/>
                    <input type="hidden" id="emr_pAgencyCd"/>
                    <input type="hidden" id="emr_userPwdHd"/>
                </div>
            </form>
        </div>
    </div>
</div>

<script>

    // 재직여부 SelectBox 데이터 바인딩
    var serviceFg = ${ccu.getCommCodeSelect("007")};
    var ele = document.getElementById('emr_serviceFg');
    for (var i = 0; i < serviceFg.length; i++) {
        ele.innerHTML = ele.innerHTML +'<option value="' + serviceFg[i]['value'] + '">' + serviceFg[i]['name'] + '</option>';
    }

    // 관리자 여부 SelectBox 데이터 바인딩
    var adminFg = ${ccu.getCommCodeSelect("097")};
    ele = document.getElementById('emr_adminFg');
    for (var i = 0; i < adminFg.length; i++) {
        ele.innerHTML = ele.innerHTML +'<option value="' + adminFg[i]['value'] + '">' + adminFg[i]['name'] + '</option>';
    }

    // 기존 정보 조회
    function empManageRegist(saveType, agencyCd, empNo, pAgencyCd){

        // 사원정보 등록/수정 화면 레이어 보이기
        $("#empManageRegistLayerDim").show();
        $("#empManageRegistLayer").show();

        // 현재 등록/수정 모드 값과 agencyCd, pAgencyCd 값 갖고있기
        $("#saveType").val(saveType); // 1: 신규등록, 2: 수정(WEB 사용), 3: 수정(WEB 미사용)
        $("#emr_agencyCd").val(agencyCd);
        $("#emr_pAgencyCd").val(pAgencyCd);

        // 수정모드 시
        if(saveType === "2" || saveType === "3"){

            // 화면명
            $("#emr_title").text("<s:message  code="instlAgency.empModify"/>");

            // 기존정보조회
            var params = {};
            params.agencyCd = agencyCd;
            params.empNo = empNo;

            $.postJSON("/pos/license/instlAgency/getAgencyEmpDtl.sb", params, function(result) {
                    var dtlData = result.data;

                    $("#emr_empNm").val(dtlData.empNm);
                    $("#emr_empNo").val(dtlData.empNo);
                    $("#emr_serviceFg").val(dtlData.serviceFg);
                    $("#emr_userId").val(dtlData.userId);
                    $("#duplicationChkFg").val(dtlData.userId);
                    $("#emr_userPwdHd").val(dtlData.empPwd);
                    $("#emr_webUseYn").val(dtlData.webUseYn);
                    $("#emr_mapEmpNo").val(dtlData.mapEmpNo);
                    $("#emr_mpNo").val(dtlData.mpNo);
                    $("#emr_useYn").val(dtlData.useYn);
                    $("#emr_smsRecvYn").val(dtlData.smsRecvYn);
                    $("#emr_remark").val(dtlData.remark);

                    // 웹 사용여부에 따른 ID, 비밀번호 입력창 보이기/숨기기
                    hideWebInfo();
                },
                function (result) {
                    s_alert.pop(result.message);
                }
            );

            // 총판/대리점 권한인 경우 관리자 구분 고정
            if($("#ai_agencyType").val() === "dist"){
                $("#emr_adminFg").val("P"); // 관리자 구분 : 총판 권한
            }else if($("#ai_agencyType").val() === "agency"){
                $("#emr_adminFg").val("C"); // 관리자 구분 : 대리점 권한
            }

            $("#emr_adminFg").css('background-color', '#F0F0F0');
            $("#emr_adminFg").attr("disabled", true);

        }else{ // 등록 모드 시

            // 화면명
            $("#emr_title").text("<s:message  code="instlAgency.empRegist"/>");

            //등록모드 시 웹 정보 입력 관련 보이게 처리
            $("#emr_empNo").attr("placeholder", " 사원번호는 자동으로 생성됩니다.");
            $("#trUserId").css("display", "");
            $("#emr_userId").removeAttr("readonly");
            $("#spanCheckDuplicate").css("display", "");
            $("#trUserPwd").css("display", "");

            // 입력 모드시 초기화
            $("#emr_empNm").val("");
            $("#emr_empNo").val("");
            $("#emr_serviceFg").val("");
            $("#emr_userId").val("");
            $("#duplicationChkFg").val("");
            $("#emr_userPwd").val("");
            $("#emr_userPwdCfm").val("");
            $("#emr_userPwdHd").val("");
            $("#emr_webUseYn").val("Y");
            $("#emr_mapEmpNo").val("");
            $("#emr_adminFg").val("");
            $("#emr_mpNo").val("");
            $("#emr_useYn").val("Y");
            $("#emr_smsRecvYn").val("N");
            $("#emr_remark").val("");

            // 총판/대리점 권한인 경우 관리자 구분 고정
            if($("#ai_agencyType").val() === "dist"){
                $("#emr_adminFg").val("P"); // 관리자 구분 : 총판 권한
            }else if($("#ai_agencyType").val() === "agency"){
                $("#emr_adminFg").val("C"); // 관리자 구분 : 대리점 권한
            }

            $("#emr_adminFg").css('background-color', '#F0F0F0');
            $("#emr_adminFg").attr("disabled", true);
        }

    }

    // 아이디 정책 및 중복 체크
    function checkDuplicate(){

        if( isEmptyObject($("#emr_userId").val())) {
            s_alert.pop(messages["systemEmp.userId"] + messages["cmm.require.text"]);
            return false;
        }

        var params    = {};
        params.userId = $("#emr_userId").val();

        // console.log('params ', params);

        $.postJSON("/base/store/emp/system/chkSystemUserId.sb", params, function(result) {
                var rResult = result.data;

                if(rResult == "SUCCESS"){
                    $("#duplicationChkFg").val($("#emr_userId").val());
                    s_alert.pop(messages["storeEmp.notDuplicate.msg"]);
                } else if(rResult === "USER_ID_REGEXP"){
                    s_alert.pop(messages["storeEmp.userIdRegexp.msg"]);
                } else if(rResult === "USER_ID_LENGHTH_REGEXP"){
                    s_alert.pop(messages["storeEmp.userIdLengthRegexp.msg"]);
                } else if(rResult === "USER_ID_CANNOT_USE_HANGEUL"){
                    s_alert.pop(messages["storeEmp.userIdNotUseHangeul.msg"]);
                } else if(rResult === "USER_ID_MUST_CONTAIN_ENG_CAHR"){
                    s_alert.pop(messages["storeEmp.userIdContainEngChar.msg"]);
                } else if(rResult === "USER_ID_ONLY_ENG_NUM_CHAR"){
                    s_alert.pop(messages["storeEmp.userIdOnlyEnvNumChar.msg"]);
                } else if(rResult === "USER_ID_DUPLICATE"){
                    s_alert.pop(messages["storeEmp.userId.duplicate.msg"]);
                } else {
                    s_alert.pop(messages["storeEmp.userId.notDuplicate.msg"]);
                }
            },
            function (result) {
                s_alert.pop(result.message);
            }
        );
    };

    // 저장
    function saveEmp(){

        // valid check
        if(chkValid()){

            var params = {};
            params.saveType = $("#saveType").val() === "1" ? "REG" : "MOD"; // 기존 서비스에서 사용하던 방식이 있어서 변환해준다.
            params.agencyCd = $("#emr_agencyCd").val();
            params.empNo = $("#emr_empNo").val();
            params.empNm = $("#emr_empNm").val();
            params.useYn = $("#emr_useYn").val();
            params.serviceFg = $("#emr_serviceFg").val();
            params.webUseYn = $("#emr_webUseYn").val();

            if($("#saveType").val() === "2"){
                params.userId = $("#emr_userId").val();
                params.userPwd = $("#emr_userPwdHd").val();
            }else{
                if($("#emr_webUseYn").val() === "Y"){
                    params.userId = $("#emr_userId").val();
                    params.userPwd = $("#emr_userPwd").val();
                }else{
                    params.userId = "";
                    params.userPwd = "";
                }
            }

            params.mapEmpNo = $("#emr_mapEmpNo").val();
            params.adminFg = $("#emr_adminFg").val();
            params.mpNo = $("#emr_mpNo").val();
            params.smsRecvYn = $("#emr_smsRecvYn").val();
            params.remark = $("#emr_remark").val();


            $.postJSON("/pos/license/instlAgency/saveAgencyEmp.sb", params, function(response) {

                    if(response.data === 'SUCCESS') {
                        s_alert.pop(messages["cmm.saveSucc"]);

                        // 사원정보 등록/수정 화면 레이어 숨기기
                        $("#empManageRegistLayerDim").hide();
                        $("#empManageRegistLayer").hide();

                        // 부모창 리스트 Refresh
                        getEmpManageList($("#emr_agencyCd").val(), $("#emr_pAgencyCd").val());

                        // 수정 모드 시 상세 화면 Refresh
                        if($("#saveType").val() === "2" || $("#saveType").val() === "3"){
                            getEmpManageDtl($("#emr_agencyCd").val(),$("#emr_empNo").val(), $("#emr_pAgencyCd").val());
                        }

                    } else if(response.data === 'USER_ID_REGEXP') {
                        s_alert.pop(messages["systemEmp.userIdRegexp.msg"]);
                        return false;
                    } else if(response.data === 'PASSWORD_REGEXP') {
                        // 확인 버튼만 팝업
                        var id = s_alert.randomString(5);
                        var pop = $("#_layerOk").clone(false).attr("id", id).appendTo(document.body);
                        pop.find("p").html(messages["login.pw.cannot"]);

                        // 팝업 띄우기
                        $("#_alertTent").show();
                        pop.show();

                        // 확인 클릭
                        pop.find("a").bind("click", function () {
                            $("#_alertTent").hide();
                            pop.remove();
                        });

                        return false;
                    } else {
                        s_alert.pop(messages["cmm.registFail"]);
                        return false;
                    }
                },
                function (result) {
                    s_alert.pop(result.message);
                }
            );
        };
    }

    // ValidDation Check
    function chkValid(){

        /*사원명*/
        if($("#emr_empNm").val() === ""){
            s_alert.pop("<s:message code='instlAgency.empNm' /><s:message code='cmm.require.text' />");
            return false;
        }

        /*재직여부*/
        if($("#emr_serviceFg").val() === ""){
            s_alert.pop("<s:message code='instlAgency.serviceFg' /><s:message code='cmm.require.select' />");
            return false;
        }

        /*웹 사용여부가 Y인 경우만 체크*/
        if($("#emr_webUseYn").val() === "Y") {

            /*웹사용자ID*/
            if ($("#emr_userId").val() === "") {
                s_alert.pop("<s:message code='instlAgency.userId' /><s:message code='cmm.require.text' />");
                return false;
            }

            /*웹사용자ID 중복체크*/
            if ($("#duplicationChkFg").val() === "") {
                s_alert.pop("<s:message code='instlAgency.userId' /><s:message code='cmm.require.duplicate' />");
                return false;
            }

            /*웹사용자ID 중복체크2*/
            if ($("#emr_userId").val() !== $("#duplicationChkFg").val()) {
                s_alert.pop("<s:message code='instlAgency.userId' /><s:message code='cmm.require.duplicate' />");
                return false;
            }

            /*입력 모드 시에만 비밀번호 입력 체크*/
            if ($("#saveType").val() === "1" || $("#saveType").val() === "3") {

                /*비밀번호 입력*/
                if ($("#emr_userPwd").val() === "") {
                    s_alert.pop("<s:message code='instlAgency.userPwd' /><s:message code='cmm.require.text' />");
                    return false;
                }

                /*비밀번호확인 입력*/
                if ($("#emr_userPwdCfm").val() === "") {
                    s_alert.pop("<s:message code='instlAgency.userPwdCfm' /><s:message code='cmm.require.text' />");
                    return false;
                }

                /*비밀번호 & 비밀번호 확인 동일여부 체크*/
                if ($("#emr_userPwd").val() !== $("#emr_userPwdCfm").val()) {
                    s_alert.pop("<s:message code='instlAgency.userPwd' /><s:message code='cmm.require.check' />");
                    return false;
                }
            }
        }

        /*관리자구분*/
        if($("#emr_adminFg").val() === ""){
            s_alert.pop("<s:message code='instlAgency.adminFg' /><s:message code='cmm.require.text' />");
            return false;
        }

        /*휴대폰번호*/
        if($("#emr_mpNo").val() === ""){
            s_alert.pop("<s:message code='instlAgency.mpNo' /><s:message code='cmm.require.text' />");
            return false;
        }

        return true;
    }

    /* 웹 사용여부에 따른 ID, 비밀번호 입력창 보이기/숨기기 */
    function hideWebInfo(){

        if($("#emr_webUseYn").val() === "Y"){ // 웹사용여부 '사용' 시

            $("#trUserId").css('display', '');
            if($("#saveType").val() === "2") {
                $("#emr_userId").attr("readonly", true);
                $("#spanCheckDuplicate").css("display", "none");
                $("#trUserPwd").css('display', 'none');
            }else{
                $("#emr_userId").removeAttr("readonly");
                $("#spanCheckDuplicate").css("display", "");
                $("#trUserPwd").css('display', '');
            }

        }else{ // 웹사용여부 '미사용' 시
            $("#trUserId").css('display', 'none');
            $("#trUserPwd").css('display', 'none');
        }
    }

    /*레이어팝업 닫기*/
    $("#empManageRegistLayer .btn_close, #empManageRegistLayer #btnCancel").click(function(){
        $("#empManageRegistLayerDim").hide();
        $("#empManageRegistLayer").hide();
    });

</script>
