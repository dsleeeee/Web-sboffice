<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div id="dimVendr" class="fullDimmed" style="display:none;"></div>
<div id="layerVendr" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w800">
      <%-- 타이틀 --%>
      <p id="popTitle" class="tit"></p>
      <a href="#" class="btn_close"></a>
      <div class="con">
        <%-- 거래처등록, 취급상품 탭 --%>
        <div class="tabType1">
          <ul>
            <%-- 거래처등록 탭 --%>
            <li><a id="vendrTab" href="#" class="on"><s:message code="vendr.regst" /></a></li>
            <%-- 취급상품 탭 --%>
            <li><a id="trtMntTab" href="#"><s:message code="vendr.trtMnt" /></a></li>
          </ul>
        </div>

        <div id="viewArea" class="mt20 sc" style=" height:302px;">
          <table class="tblType01">
            <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
            </colgroup>
            <tbody>
              <tr>
                <%-- 거래처코드 --%>
                <th>
                  <div class="impWrap"><s:message code="vendr.vendrCd" /> <em class="imp">*</em></div>
                </th>
                <td id="vVendrCd"></td>
                <%-- 거래처명 --%>
                <th>
                  <div class="impWrap"><s:message code="vendr.vendrNm" /> <em class="imp">*</em></div>
                </th>
                <td id="vVendrNm"></td>
              </tr>
              <tr>
                <%-- 대표자명 --%>
                <th>
                  <div class="impWrap"><s:message code="vendr.ownerNm" /> <em class="imp">*</em></div>
                </th>
                <td id="vOwnerNm"></td>
                <%-- 거래처구분 --%>
                <th>
                  <div class="impWrap"><s:message code="vendr.vendorFg" /> <em class="imp">*</em></div>
                </th>
                <td id="vVendorFg"></td>
              </tr>
              <tr>
                <%-- 부가세 포함여부 --%>
                <th>
                  <div class="impWrap"><s:message code="vendr.vatIncldYn" /> <em class="imp">*</em></div>
                </th>
                <td id="vVatIncldYn"></td>
                <%-- 사용여부 --%>
                <th>
                  <div class="impWrap"><s:message code="vendr.useYn" /></div>
                </th>
                <td id="vUseYn"></td>
              </tr>
              <tr>
                <%-- 사업자번호 --%>
                <th>
                  <div class="impWrap"><s:message code="vendr.bizNo" /></div>
                </th>
                <td id="vBizNo"></td>
                <th>
                  <div class="impWrap"><s:message code="vendr.telNo" /></div>
                </th>
                <td id="vTelNo"></td>
              </tr>
              <tr>
                <th>
                  <div class="impWrap"><s:message code="vendr.emailAddr" /></div>
                </th>
                <td id="vEmailAddr"></td>
                <th>
                  <div class="impWrap"><s:message code="vendr.faxNo" /></div>
                </th>
                <td id="vFaxNo"></td>
              </tr>
              <tr>
                <th>
                  <div class="impWrap"><s:message code="vendr.addr" /></div>
                </th>
                <td colspan="3" id="vAddr"></td>
              </tr>
              <tr>
                <th>
                  <div class="impWrap"><s:message code="vendr.remark" /></div>
                </th>
                <td colspan="3" id="vRemark"></td>
              </tr>
            </tbody>
          </table>
        </div>

        <div id="regArea" class="mt20 sc" style="height:372px; display:none;">
          <table class="tblType01">
            <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
            </colgroup>
            <tbody>
              <tr class="brt">
                <%-- 거래처코드 --%>
                <th><s:message code="vendr.vendrCd" /> <em class="imp">*</em></th>
                <td>
                  <div class="sb-select">
                    <div id="rVendrCd"></div>
                  </div>
                </td>
                <%-- 거래처명 --%>
                <th><s:message code="vendr.vendrNm" /> <em class="imp">*</em></th>
                <td>
                  <div class="sb-select">
                    <div id="rVendrNm"></div>
                  </div>
                </td>
              </tr>
              <tr>
                <%-- 대표자명 --%>
                <th><s:message code="vendr.ownerNm" /> <em class="imp">*</em></th>
                <td>
                  <div class="sb-select">
                    <div id="rOwnerNm"></div>
                  </div>
                </td>
                <%-- 거래처구분 --%>
                <th><s:message code="vendr.vendorFg" /> <em class="imp">*</em></th>
                <td>
                  <div class="sb-select">
                    <div id="rVendorFg"></div>
                  </div>
                </td>
              </tr>
              <tr>
                <%-- 부가세 포함여부 --%>
                <th><s:message code="vendr.vatIncldYn" /> <em class="imp">*</em></th>
                <td>
                  <div class="sb-select">
                    <div id=rVatIncldYn></div>
                  </div>
                </td>
                <%-- 사용여부 --%>
                <th><s:message code="vendr.useYn" /></th>
                <td>
                  <div class="sb-select">
                    <div id=rUseYn></div>
                  </div>
                </td>
              </tr>
              <tr>
                <%-- 사업자번호 --%>
                <th><s:message code="vendr.bizNo" /></th>
                <td>
                  <span class="w25 txtIn pdr5" >
                    <div class="sb-select">
                      <div id="rBizNo1"></div>
                    </div>
                  </span>
                  <span class="w20 txtIn pdr5">
                    <div class="sb-select">
                      <div id="rBizNo2"></div>
                    </div>
                  </span>
                  <span class="w45 txtIn">
                    <div class="sb-select w70">
                      <div id="rBizNo3"></div>
                    </div>
                  </span>
                </td>
                <th><s:message code="vendr.telNo" /></th>
                <td>
                    <div class="sb-select">
                      <div id="rTelNo"></div>
                    </div>
                </td>
              </tr>
              <tr>
                <th><s:message code="vendr.emailAddr" /></th>
                <td>
                  <div class="sb-select">
                    <div id="rEmailAddr"></div>
                  </div>
                </td>
                <th><s:message code="vendr.faxNo" /></th>
                <td>
                    <div class="sb-select">
                      <div id="rFaxNo"></div>
                    </div>
                </td>
              </tr>
              <tr>
                <th><s:message code="vendr.addr" /></th>
                <td colspan="3">
                  <span class="w20 txtIn pdr5">
                    <div class="sb-select w100">
                      <div id="rPostNo"></div>
                    </div>
                  </span>
                  <%-- 주소찾기 버튼 --%>
                  <span class="w20 pdb10">
                    <a href="#" class="btn_grayS" id="btnFindAddr"><s:message code="hqManage.findAddr" /></a>
                  </span>
                </td>
              </tr>
              <tr>
                <th>&nbsp;</th>
                <td colspan="3">
                  <span class="w40 txtIn pdr5">
                    <div class="sb-select">
                      <div id="rAddr"></div>
                    </div>
                  </span>
                  <span class="w55 txtIn">
                    <div class="sb-select">
                      <div id="rAddrDtl"></div>
                    </div>
                  </span>
                </td>
              </tr>
              <tr>
                <th><s:message code="vendr.remark" /></th>
                <td colspan="3">
                  <div class="sb-select">
                    <div id="rRemark"></div>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <%-- 공통 버튼 영역 --%>
      <div class="btnSet">
        <%-- 등록 --%>
        <span><a href="#" class="btn_blue" id="btnReg" style="display:none;"><s:message code="cmm.new.add" /></a></span>
        <%-- 저장 --%>
        <span><a href="#" class="btn_blue" id="btnSave" style="display:none;"><s:message code="cmm.save" /></a></span>
        <%-- 수정 --%>
        <span><a href="#" class="btn_blue" id="btnEdit"><s:message code="cmm.edit" /></a></span>
        <%-- 닫기 --%>
        <span><a href="#" class="btn_gray" id="btnClose"><s:message code="cmm.close" /></a></span>
      </div>

    </div>
  </div>
</div>
<script>

  var vendorFgData     = ${ccu.getCommCode("011")};
  var vatIncldYnData   = ${ccu.getCommCodeExcpAll("067")};
  var useYnData        = ${ccu.getCommCodeExcpAll("067")};

  var rVendorFg    = wcombo.genCommonBox("#rVendorFg",vendorFgData);
  var rVatIncldYn  = wcombo.genCommonBox("#rVatIncldYn",vatIncldYnData);
  var rUseYn       = wcombo.genCommonBox("#rUseYn",useYnData);

  var rVendrCd     = genInputText("#rVendrCd","10");
  var rVendrNm     = genInputText("#rVendrNm","50");
  var rOwnerNm     = genInputText("#rOwnerNm","50");
  var rBizNo1      = genInputText("#rBizNo1","3");
  var rBizNo2      = genInputText("#rBizNo2","2");
  var rBizNo3      = genInputText("#rBizNo3","5");
  var rTelNo       = genInputText("#rTelNo","15");
  var rFaxNo       = genInputText("#rFaxNo","15");
  var rEmailAddr   = genInputText("#rEmailAddr","200");

  var rPostNo      = genInputText("#rPostNo","5");
  var rAddr        = genInputText("#rAddr","200");
  var rAddrDtl     = genInputText("#rAddrDtl","200");
  var rRemark      = genInputText("#rRemark","500");

  function genInputText(div, length) {
    return wcombo.genInputText(div, length, "", null);
  }

  <%-- 거래처등록 레이어 --%>
  function openRegistLayer(val) {

    if( val=="reg" || val == "" )
    {
        infoInit();

        $("#layerVendr #popTitle").text("<s:message code='vendr.layer.regist.title' />");

        $("#layerVendr").show();
        $("#dimVendr").show();

        $("#viewArea").hide();
        $("#regArea").show();

        $("#btnSave").hide();
        $("#btnEdit").hide();
        $("#btnReg").show();
    }else
    {
        $("#layerVendr #popTitle").text("["+ val +"] " + "<s:message code='vendr.layer.modify.title' />");

        $("#layerVendr").show();
        $("#dimVendr").show();

        $("#viewArea").show();
        $("#regArea").hide();

        $("#btnSave").hide();
        $("#btnEdit").show();
        $("#btnReg").hide();

    }
  }
  <%-- 거래처 상세정보 레이어 --%>
  function openDtlLayer(items) {

    getDtlData(items);

    $("#popTitle").text("["+ items.vendrCd +"] " + items.vendrNm);

    $("#layerVendr").show();
    $("#dimVendr").show();

    $("#viewArea").show();
    $("#regArea").hide();


    $("#btnSave").hide();
    $("#btnReg").hide();
    $("#btnEdit").show();
  }

  function getDtlData(items) {
      var param = items;

      $.postJSON("/base/prod/vendr/regist/view.sb", param, function(result) {
        var data = result.data;

        <%-- 상세정보 --%>
        $("#vVendrCd").text(data.vendrCd);
        $("#vVendrNm").text(data.vendrNm);
        $("#vOwnerNm").text(data.ownerNm);
        $("#vVendorFg").text(data.vendorFgNm);
        $("#vVatIncldYn").text(data.vatIncldYnNm);
        $("#vUseYn").text(data.useYnNm);

        if(data.bizNo1 != null && data.bizNo2 != null && data.bizNo3 != null)
        $("#vBizNo").text(data.bizNo1 + "-" + data.bizNo2 + "-" + data.bizNo3);

        if(data.telNo != null)
        $("#vTelNo").text(data.telNo);

        if(data.emailAddr != null)
        $("#vEmailAddr").text(data.emailAddr);

        if(data.faxNo != null)
        $("#vFaxNo").text(data.faxNo);

        if(data.postNo != null && data.addr != null && data.addrDtl != null )
        $("#vAddr").text("("+data.postNo+") "+data.addr + " "+data.addrDtl);

        if(data.remark != null)
        $("#vRemark").text(data.remark);


        <%-- 상세정보 수정 --%>
        rVendrCd.value              = data.vendrCd;
        rVendrNm.value              = data.vendrNm;
        rOwnerNm.value              = data.ownerNm;
        rVendorFg.selectedValue     = data.vendorFg;
        rVatIncldYn.selectedValue   = data.vatIncldYn;
        rUseYn.selectedValue        = data.useYn;
        rBizNo1.value               = data.bizNo1;
        rBizNo2.value               = data.bizNo2;
        rBizNo3.value               = data.bizNo3;
        rTelNo.value                = data.telNo;
        rFaxNo.value                = data.faxNo;
        rPostNo.value               = data.postNo;
        rAddr.value                 = data.addr;
        rAddrDtl.value              = data.addrDtl;
        rEmailAddr.value            = data.emailAddr;
        rRemark.value               = data.remark

        rVendrCd.isReadOnly = true;
      },
        function (result) {
          s_alert.pop(result.message);
          return false;
        }
      );
    }

  function saveHqOffice(sendUrl) {

    var param = {};
    param.vendrCd    = rVendrCd.value;
    param.vendrNm    = rVendrNm.value;
    param.ownerNm    = rOwnerNm.value;
    param.vendorFg   = "1";
    param.vatIncldYn = rVatIncldYn.selectedValue;
    param.useYn      = rUseYn.selectedValue;
    param.bizNo      = rBizNo1.value + rBizNo2.value + rBizNo3.value;
    param.telNo      = rTelNo.value;
    param.emailAddr  = rEmailAddr.value;
    param.faxNo      = rFaxNo.value;
    param.postNo     = rPostNo.value;
    param.addr       = rAddr.value;
    param.addrDtl    = rAddrDtl.value;
    param.remark     = rRemark.value;

    $.postJSONSave(sendUrl, param, function(result) {
      s_alert.pop("<s:message code='cmm.saveSucc'/>");
      $(".btn_close").click();
      search(1);
    },
    function (result) {
        s_alert.pop(result.message);
        return false;
      }
    );
  }

  <%-- 신규등록 버튼 클릭 --%>
  $("#btnReg").click(function(e){
    chkVal("/base/prod/vendr/regist/regist.sb");
  });

  <%-- 닫기 버튼 클릭 --%>
  $("#layerVendr .btn_close, #layerVendr #btnClose").click(function(e){
     infoInit();

    $("#viewArea").show();
    $("#btnEdit").show();

    $("#regArea").hide();
    $("#btnSave").hide();

    $("#dimVendr").hide();
    $("#layerVendr").hide();
  });

  <%-- 수정 버튼 클릭 --%>
  $("#layerVendr #btnEdit").click(function(e){
    $("#viewArea").hide();
    $("#regArea").show();

    $("#btnSave").show();
    $("#btnEdit").hide();
  });

  <%-- 저장 버튼 클릭 --%>
  $("#btnSave").click(function(e){
    chkVal("/base/prod/vendr/regist/modify.sb");
  });

  <%-- validation --%>
  function chkVal(sendUrl) {

    <%-- 거래처코드을 입력해주세요. --%>
    var msg = "<s:message code='vendr.vendrCd'/> <s:message code='cmm.require.text'/>";
    if(rVendrCd.value === "") {
      s_alert.pop(msg);
      return false;
    }

    <%-- 거래처명을 입력해주세요. --%>
    var msg = "<s:message code='vendr.vendrNm'/> <s:message code='cmm.require.text'/>";
    if(rVendrNm.value === "") {
      s_alert.pop(msg);
      return false;
    }

    <%-- 대표자명을 입력해주세요. --%>
    var msg = "<s:message code='vendr.ownerNm'/> <s:message code='cmm.require.text'/>";
    if(rOwnerNm.value === "") {
      s_alert.pop(msg);
      return false;
    }

    <%-- 거래처 구분 여부를 선택 해주세요. --%>
    var msg = "<s:message code='vendr.vendorFg'/> <s:message code='cmm.require.select'/>";
    if(rVendorFg.value === "") {
      s_alert.pop(msg);
      return false;
    }

    <%-- 부가세 포함여부를 선택 해주세요. --%>
    var msg = "<s:message code='vendr.vatIncldYn'/> <s:message code='cmm.require.select'/>";
    if(rVatIncldYn.value === "") {
      s_alert.pop(msg);
      return false;
    }

    <%-- 사용여부를 선택 해주세요. --%>
    var msg = "<s:message code='vendr.useYn'/> <s:message code='cmm.require.select'/>";
    if(rUseYn.value === "") {
      s_alert.pop(msg);
      return false;
    }

    saveHqOffice(sendUrl);
  }

  <%--신규등록 탭 초기화--%>
  function infoInit() {

    $("#noDataArea").hide();
    $("#membrCardInfo").hide();
    $("#basicInfrm").show();

    var inputArr = [
      rVendrCd, rVendrNm, rOwnerNm, rBizNo1, rBizNo2, rBizNo3, rTelNo, rEmailAddr, rFaxNo, rPostNo, rAddr, rAddrDtl, rRemark
    ].forEach(function(element){element.value="";});
    var selectArr = [
        rVendorFg, rVatIncldYn, rUseYn
    ].forEach(function(element){element.selectedIndex=0;});
    rVendrCd.isReadOnly=false;
  }

  <%-- 취급상품 탭 클릭 --%>
  $("#layerVendr #trtMntTab").click(function(e){
    if(vendr === undefined || vendr === null || vendr === "") {
      s_alert.pop("<s:message code='vendr.request.regist.vendr'/>");
      return false;
    }
    showTrtMntSet($('#vVendrCd').text());
  });
</script>
