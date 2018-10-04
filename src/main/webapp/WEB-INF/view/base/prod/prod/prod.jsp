<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon">
  <%--searchTbl--%>
  <div class="searchBar flddUnfld">
    <a href="javascript:;" class="open">${menuNm}</a>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
      <%-- 조회 일자 --%>
      <tr>
        <th><s:message code="cmm.search.date" /></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"> <input id="startDt" name="startDt" class="w200" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="endDt" name="endDt" class="w200" /></span>
            <span class="chk ml10"> <input type="checkbox" id="chkDt" />
            <label for="chkDt"><s:message code="cmm.all.day" /></label>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th><s:message code="prod.prodCd" /></th>
        <td>
          <div class="sb-select">
            <div id="prodCd"></div>
          </div>
        </td>
        <%-- 상품명 --%>
        <th><s:message code="prod.prodNm" /></th>
        <td>
          <div class="sb-select">
            <div id="prodNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 바코드 --%>
        <th><s:message code="prod.barCd" /></th>
        <td colspan="3">
          <div class="sb-select">
            <div id="barCd"></div>
          </div>
        </td>
        <%-- 분류조회 --%>
          <%--
        <th><s:message code="prod.class.search" /></th>
        <td>
          <div class="sb-select">
            <div id="prodClassCd"></div>
          </div>
        </td>
        --%>
      </tr>
    </tbody>
  </table>
  <%--//searchTbl--%>

  <div class="mt10 pdb20 oh bb">
    <button class="btn_blue fr" id="searchBtn">
      <s:message code="cmm.search" />
    </button>
  </div>

  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <div id="listScaleBox" class="w150 fl"></div>

    <%-- 엑셀 다운로드 //TODO --%>
    <%--
    <button class="btn_skyblue fr" id="excelBtn">
      <s:message code="cmm.excel.down" />
    </button>
    --%>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height: 400px;">
    <%-- 개발시 높이 조절해서 사용--%>
    <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
    <div id="theGrid" style="height:393px;"></div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="page1" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>


<%--//상품상세 정보--%>
<div id="prodFullDimmed" class="fullDimmed" style="display: none;"></div>
<div id="prodDetailLayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="title w600">
      <p class="tit"><s:message code="prod.info" /></p>
      <a href="javascript:;" class="btn_close"></a>
      <div class="con sc2" style="height: 500px;">
        <p class="s14 bk mb5"><s:message code="prod.basicInfo" /></p>
        <table class="tblType01">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
            <tr>
              <%-- 상품이미지 //TODO --%>
              <th rowspan="3"><s:message code="prod" /><br /><s:message code="image" />
              </th>
              <td rowspan="3">
                <%--등록한 상품이 없는 경우--%>
                <span class="goodsNo">IMAGE</span>
                <!--
                <span class="goodsYes"><img src="img/sample.jpg" alt="" /></span> <%--등록한 상품이 있는 경우--%>
                -->
              </td>
              <%--단가구분 //TODO --%>
              <th>
                <div class="impWrap"><s:message code="prod.uprcFg" /></div>
              </th>
              <td id="_uprcFg"></td>
            </tr>
            <tr>
              <%--상품코드--%>
              <th>
                <div class="impWrap"><s:message code="prod.prodCd" /><em class="imp">*</em></div>
              </th>
              <td id="_prodCd"></td>
            </tr>
            <tr>
              <%--상품명--%>
              <th>
                <div class="impWrap"><s:message code="prod.prodNm" /></div>
              </th>
              <td id="_prodNm"></td>
            </tr>
            <tr>
              <%--상품분류--%>
              <th><s:message code="prod.class" /></th>
              <td id="_prodClassNm"></td>
              <%--거래처 //TODO --%>
              <th>
                <div class="impWrap"><s:message code="prod.vendr" /></div>
              </th>
              <td id="_vendr"></td>
            </tr>
            <tr>
              <%--판매상품여부--%>
              <th><s:message code="prod.saleProdYn" /></th>
              <td id="_saleProdYn"></td>
              <%--원산지--%>
              <th>
                <div class="impWrap"><s:message code="prod.orgplceCd" /></div>
              </th>
              <td id="_orgplceCd"></td>
            </tr>
            <tr>
              <%--최초판매단가--%>
              <th>
                <div class="impWrap"><s:message code="prod.firstSaleUprc" /></div>
              </th>
              <td id="_saleUprc"></td>
              <%--원가단가--%>
              <th>
                <div class="impWrap"><s:message code="prod.costUprc" /></div>
              </th>
              <td id="_costUprc"></td>
            </tr>
            <tr>
              <%--주문상품구분--%>
              <th>
                <div class="impWrap"><s:message code="prod.poProdFg" /></div>
              </th>
              <td id="_poProdFg"></td>
              <%--주문단위--%>
              <th>
                <div class="impWrap"><s:message code="prod.poUnitFg" /></div>
              </th>
              <td id="_poUnitFg"></td>
            </tr>
            <tr>
              <%--최소주문--%>
              <th>
                <div class="impWrap"><s:message code="prod.poMinQty" /></div>
              </th>
              <td id="_poMinQty"></td>
              <th></th>
              <td></td>
            </tr>
          </tbody>
        </table>
        <p class="s14 bk mt20 mb5"><s:message code="prod.addInfo" /></p>
        <table class="tblType01">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
            <tr>
              <%--과세여부--%>
              <th>
                <div class="impWrap"><s:message code="prod.vatFg" /><em class="imp">*</em></div>
              </th>
              <td id="_vatFg"></td>
              <%--재고관리여부--%>
              <th>
                <div class="impWrap"><s:message code="prod.stockProdYn" /><em class="imp">*</em></div>
              </th>
              <td id="_stockProdYn"></td>
            </tr>
            <tr>
              <%--사용여부--%>
              <th>
                <div class="impWrap"><s:message code="useYn" /><em class="imp">*</em></div>
              </th>
              <td id="_useYn"></td>
              <%--안전재고--%>
              <th>
                <div class="impWrap"><s:message code="prod.safeStockQty" /><em class="imp">*</em></div>
              </th>
              <td id="_safeStockQty"></td>
            </tr>
            <tr>
              <%--품절여부 //TODO --%>
              <th>
                <div class="impWrap"><s:message code="prod.soldOutYn" /><em class="imp">*</em></div>
              </th>
              <td id="_soldOutYn"></td>
              <%--초기재고 //TODO --%>
              <th>
                <div class="impWrap"><s:message code="prod.defaultStock" /><em class="imp">*</em></div>
              </th>
              <td id="_defaultStock"></td>
            </tr>
            <tr>
              <%--저장품코드 //TODO --%>
              <th>
                <div class="impWrap"><s:message code="prod.saveProdCd" /><em class="imp">*</em></div>
              </th>
              <td><a href="#" class="link" id="_saveProdCd"></a></td>
              <%--세트상품구분//TODO --%>
              <th>
                <div class="impWrap"><s:message code="prod.setProdFg" /><em class="imp">*</em></div>
              </th>
              <td id="_setProdFg"></td>
            </tr>
            <tr>
              <%--환급적용여부--%>
              <th>
                <div class="impWrap"><s:message code="prod.refApplyYn" /><em class="imp">*</em></div>
              </th>
              <td id="_refApplyYn"></td>
              <th></th>
              <td></td>
            </tr>
          </tbody>
        </table>
        <%--비고--%>
        <p class="s14 bk mt20 mb5"><s:message code="remark" /></p>
        <table class="tblType01">
          <colgroup>
            <col class="w100" />
          </colgroup>
          <tbody>
            <tr>
              <th class="gr lh20" id="_remark">
              </th>
            </tr>
          </tbody>
        </table>

        <%-- 할인 / 적립 --%>
        <p class="s14 bk mt20 mb5"><s:message code="prod.dcAndSaveInfo" /></p>
        <table class="tblType01">
          <colgroup>
            <col width="15%" />
            <col width="85%" />
          </colgroup>
          <tbody>
            <tr>
              <%--할인 //TODO --%>
              <th><s:message code="prod.dc" /></th>
              <td id="_dc"></td>
            </tr>
            <tr>
              <%--적립 //TODO --%>
              <th><s:message code="prod.save" /></th>
              <td id="_save"></td>
            </tr>
          </tbody>
        </table>
        <p class="s14 bk mt20 mb5"><s:message code="prod.unitstInfo" /></p>
        <table class="tblType01 mb20">
          <colgroup>
            <col class="w15" />
            <col class="w85" />
          </colgroup>
          <tbody id="_unitstData">
          </tbody>
        </table>
      </div>
      <div class="btnSet">
        <span><a href="javascript:;" id="btnClose" class="btn_blue">닫기</a></span>
      </div>
    </div>
    <!--//layerContent-->
  </div>
</div>
<!--//layer:For Center screen-->


<script>
$(document).ready(function(){
  var rdata =
    [
      <%--{"binding":"lProdClass","header":"<s:message code='prod.lClass' />","width":"*"},--%>
      <%--{"binding":"mProdClass","header":"<s:message code='prod.mClass' />","width":"*"},--%>
      <%--{"binding":"sProdClass","header":"<s:message code='prod.sClass' />","width":"*"},--%>
      {"binding":"prodClassNm","header":"<s:message code='prod.prodClassNm' />","width":"*"},
      {"binding":"prodCd","header":"<s:message code='prod.prodCd' />","width":"*"},
      {"binding":"prodNm","header":"<s:message code='prod.prodNm' />","width":"*"},
      {"binding":"costUprc","header":"<s:message code='prod.costUprc' />","width":"*"},
      {"binding":"splyUprc","header":"<s:message code='prod.splyUprc' />","width":"*"},
      {"binding":"saleUprc","header":"<s:message code='prod.saleUprc' />","width":"*"},
      {"binding":"orgplceCd","header":"<s:message code='prod.orgplceCd' />","width":"*"},
      {"binding":"poUnitFg","header":"<s:message code='prod.poUnitFg' />","width":"*"}
    ];

  var grid         = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var prodCd       = wcombo.genInput("#prodCd");
  var prodNm       = wcombo.genInput("#prodNm");
  var barCd        = wcombo.genInput("#barCd");
  // var prodClassCd  = wcombo.genInput("#prodClassCd");
  var startDt      = wcombo.genDateVal("#startDt", "${sessionScope.sessionInfo.startDt}");
  var endDt        = wcombo.genDateVal("#endDt", "${sessionScope.sessionInfo.endDt}");
  var ldata        = ${ccu.getListScale()};
  var listScaleBox = wcombo.genCommonBox("#listScaleBox", ldata);

  function search(index) {
    var param = {};

    param.startDt = getDate(startDt);
    param.endDt = getDate(endDt);
    param.chkDt = $('#chkDt').is(":checked");
    param.prodCd = prodCd.text;
    param.prodNm = prodNm.text;
    param.barCd = barCd.text;
    // param.prodClassCd = prodClassCd.text;
    param.listScale = listScaleBox.selectedValue;
    param.curr = index;

    $.postJSON("/base/prod/prod/prod/list.sb", param, function(result) {
      var list = result.data.list;

      if(list.length === undefined || list.length == 0) {
        s_alert.pop(result.message);
      }

      grid.itemsSource = list;
      page.make("#page1", result.data.page.curr, result.data.page.totalPage);
    },
    function(result) {
      s_alert.pop(result.message);
    });
  }

  <%-- 그리드 포맷 --%>
  grid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "prodCd") {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  <%-- 그리드 선택 이벤트 --%>
  grid.addEventListener(grid.hostElement, 'mousedown', function(e) {
    var ht = grid.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "prodCd") {
        searchProdDetail(grid.rows[ht.row].dataItem);
      }
    }
  });

  function searchProdDetail(item) {
    var param = {};
    param.prodCd = item.prodCd;

    $.postJSON("/base/prod/prod/prod/view.sb", param, function(result) {
      showProdLayer(result.data);
    }
    ,function(result){
      s_alert.pop(result.message);
    });
  }

  <%-- 레이어 팝업 오픈 --%>
  function showProdLayer(item) {

    var vatFg        = ${ccu.getCommCodeExcpAll("039")};
    var poProdFg     = ${ccu.getCommCodeExcpAll("064")}; //TODO 발주, 주문상품 구분 => 코드 추가 필요
    var setProdFg    = ${ccu.getCommCodeExcpAll("009")};
    var useYn        = ${ccu.getCommCodeExcpAll("067")};
    var vatFgDataMap = new wijmo.grid.DataMap(vatFg, 'value', 'name');
    var poProdFgDataMap  = new wijmo.grid.DataMap(poProdFg, 'value', 'name');
    var setProdFgDataMap = new wijmo.grid.DataMap(setProdFg, 'value', 'name');
    var useYnDataMap     = new wijmo.grid.DataMap(useYn, 'value', 'name');

    $("#_prodCd").text(item.prodCd);
    $("#_prodNm").text(item.prodNm);
    $("#_prodClassNm").text(item.prodClassNm);
    $("#_orgplceCd").text(item.orgplceCd);
    $("#_saleProdYn").text(item.saleProdYn);
    $("#_costUprc").text(comma(item.costUprc));
    $("#_saleUprc").text(comma(item.saleUprc));
    $("#_poProdFg").text(poProdFgDataMap.getDisplayValue(item.poProdFg));
    $("#_poUnitFg").text(item.poUnitFg);
    $("#_poMinQty").text(item.poMinQty);

    $("#_vatFg").text(vatFgDataMap.getDisplayValue(item.vatFg));
    $("#_stockProdYn").text(item.stockProdYn);
    $("#_useYn").text(useYnDataMap.getDisplayValue(item.useYn));
    $("#_safeStockQty").text(item.safeStockQty);
    $("#_setProdFg").text(setProdFgDataMap.getDisplayValue(item.setProdFg));
    $("#_remark").text(nvl(item.remark, ''));

    var list = item.list;
    var unitstData = "";

    if( list.length > 0 ) {
      $.each(list, function() {
        unitstData += "<tr><th><div class='impWrap'><s:message code='prod.prodCd' /><em class='imp'>*</em></div></th>"
                    + "<td><a href='#' class='link'>" + this.unitProdCd + "</a></td></tr>";
      });
    }
    else {
      unitstData += "<tr><th colspan='2'><s:message code='cmm.empty.data' /></th></tr>";
    }

    $("#_unitstData").html(unitstData);

    $("#prodFullDimmed").show();
    $("#prodDetailLayer").show();
  }

  <%-- 레이어 팝업 닫기 --%>
  $(".btn_close, #btnClose").click(function() {
    $("#prodFullDimmed").hide();
    $("#prodDetailLayer").hide();
  });

  <%-- 리스트 조회 --%>
  $("#searchBtn").click(function( e ) {
    search(1);
  });

  <%-- 엑셀 다운로드 --%>
  $("#excelBtn").click(function( e ) {
    var name = "${menuNm}";
    wexcel.down(grid, name, name + ".xlsx");
  });

  <%-- 페이징 --%>
  $(document).on("click", ".page1", function() {
    search($(this).data("value"));
  });

  <%-- 전체기간 체크박스 --%>
  $(document).on("click", "#chkDt", function() {
    var chkDt = $('#chkDt').is(":checked");
    startDt.isDisabled = chkDt;
    endDt.isDisabled = chkDt;
  });

});

</script>
