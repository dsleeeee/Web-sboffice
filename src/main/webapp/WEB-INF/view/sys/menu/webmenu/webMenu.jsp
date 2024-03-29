<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<c:set var="menuData" value="${webMenuList}" />

<div class="subCon">
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
  </div>

  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
      <tr>
        <%-- 메뉴명 --%>
        <th><s:message code="webMenu.nm" /></th>
        <td colspan="3">
          <div class="sb-select w40">
            <div id="searchResrceNm"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <div class="wj-TblWrap mt10">

    <%-- 왼쪽 메뉴 트리 --%>
    <div class="w30 fl">
      <div class="wj-TblWrapBr mr10 pd10" style="height: 500px;">

        <div class="sb-select dkbr mb10 oh">
          <div id="theComboBox3" class="w130px fl"></div>
          <div class="fr">
            <%-- 전체펼치기 버튼 --%>
            <button id="expandBtn" class="btn_skyblue">
              <s:message code="cmm.all.expand" />
            </button>
            <%-- 전체접기 버튼 --%>
            <button id="foldBtn" class="btn_skyblue">
              <s:message code="cmm.all.fold" />
            </button>
          </div>
        </div>

        <div class="theTreeAll_cls" id="theGrid"></div>
      </div>
    </div>

    <%-- 오른쪽 메뉴 정보 --%>
    <div class="w70 fr">
      <div class="wj-TblWrapBr pd10" style="height: 500px;">

        <div class="tr mb10">
          <%-- 삭제 버튼 --%>
          <button id="deleteBtn" class="btn_skyblue">
            <s:message code="cmm.del" />
          </button>
          <%-- 신규 등록 버튼 --%>
          <button id="regBtn" class="btn_skyblue">
            <s:message code="webMenu.new" />
          </button>
        </div>

        <%-- 메뉴정보 --%>
        <h3 class="h3_tbl2 pdt5 lh25 mt20">
          <s:message code="webMenu.info" />
        </h3>

        <table class="searchTbl">
          <colgroup>
            <col class="w30" />
            <col class="w70" />
          </colgroup>
          <tbody>
            <%-- 메뉴코드 --%>
            <tr>
              <th><s:message code="webMenu.code" /></th>
              <td>
                <div class="sb-select">
                  <div id="resrceCd"></div>
                </div>
              </td>
            </tr>
            <%-- 메뉴명 --%>
            <tr>
              <th><s:message code="webMenu.nm" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <div id="resrceNm"></div>
                </div>
              </td>
            </tr>
            <%-- 사용권한구굽ㄴ --%>
            <tr>
              <th><s:message code="webMenu.authOrgn" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <div id="dispIdx"></div>
                </div>
              </td>
            </tr>
            <%-- URL --%>
            <tr>
              <th>URL</th>
              <td>
                <div class="sb-select">
                  <div id="url"></div>
                </div>
              </td>
            </tr>
            <%-- 특수 권한 --%>
            <tr>
              <th><s:message code="webMenu.spclAuthor" /></th>
              <td>
                <div class="sb-select">
                  <div id=spclAuthor></div>
                </div>
              </td>
            </tr>
            <%-- 사용권한구분 --%>
            <tr>
              <th><s:message code="webMenu.authOrgn" /></th>
              <td>
                <div class="sb-select pdt5">
                  <div id=authOrgn>
                    <span class="chk ml10">
                      <label for="chkAdmin">
                        <s:message code="webMenu.admin" />
                      </label>
                      <input type="checkbox" id="chkAdmin" ng-model="isChecked1" />
                    </span>
                    <span class="chk ml10">
                      <label for="chkDist">
                        <s:message code="webMenu.dist" />
                      </label>
                      <input type="checkbox" id="chkDist" ng-model="isChecked2" />
                    </span>
                    <span class="chk ml10">
                      <label for="chkAgency">
                        <s:message code="webMenu.agency" />
                      </label>
                      <input type="checkbox" id="chkAgency" ng-model="isChecked3" />
                    </span>
                    <span class="chk ml10">
                      <label for="chkHq">
                        <s:message code="webMenu.hq" />
                      </label>
                      <input type="checkbox" id="chkHq" ng-model="isChecked4" />
                    </span>
                    <span class="chk ml10">
                      <label for="chkStore">
                        <s:message code="webMenu.store" />
                      </label>
                      <input type="checkbox" id="chkStore" ng-model="isChecked5" />
                    </span>
                    <span class="chk ml10">
                      <label for="chkExStore">
                        <s:message code="webMenu.exStore" />
                      </label>
                      <input type="checkbox" id="chkExStore" ng-model="isChecked6" />
                    </span>
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>

        <%-- 기능정보 --%>
        <h3 class="h3_tbl2 pdt5 lh25 mt20">
          <s:message code="webMenu.fun.info" />
          <span class="fr">
            <%-- 추가버튼 --%>
            <a id="funcReg" href="#" class="btn_grayS">
              <s:message code="cmm.add" />
            </a>
            <%-- 삭제버튼 --%>
            <a id="funcDel" href="#" class="btn_grayS">
              <s:message code="cmm.del" />
            </a>
          </span>
        </h3>

        <div id="theGrid2"></div>

        <%-- 저장 버튼 --%>
        <div class="tc mt20">
          <button id="funcSave" class="btn_blue">
            <s:message code="cmm.save" />
          </button>
        </div>
      </div>
    </div>
  </div>
</div>


<script>
  $(document).ready(function() {

    var resrceCd            = wcombo.genInput("#resrceCd");
    var resrceNm            = wcombo.genInput("#resrceNm");
    var url                 = wcombo.genInput("#url");
    var dispIdx             = wcombo.genInput("#dispIdx");
    var sdata               = ${ccu.getCommCodeNoSelect("905")};
    var spclAuthor          = wcombo.genCommonBox("#spclAuthor", sdata);
    resrceCd.isDisabled = true;

    var grid = new wijmo.nav.TreeView("#theGrid", {
      itemsSource: ${menuData},
      displayMemberPath: "header",
      childItemsPath: "items",
      isAnimated: false,
      loadedItems: function(s, e) {
        s.collapseToLevel(0);
      },
      itemClicked: function(s, e) {
        if(s.selectedItem === grid.selectedItem) {
          selectItem(s.selectedItem);
        }
      }
    });

    var gdata = ${ccu.getCommCodeExcpAll("998")};
    var mFuncFg = new wijmo.grid.DataMap(gdata, "value", "name");

    var rdata2 =
      [
        {binding:"funcFg", header:"<s:message code='webMenu.fun.class' />", dataMap:mFuncFg},
        {binding:"resrceNm", header:"<s:message code='webMenu.fun.nm' />"},
        {binding:"url", header:"URL"}
      ];
    var grid2         = wgrid.genGrid("#theGrid2", rdata2);

    grid2.rowHeaders.columns.splice(0, 1);
    grid2.isReadOnly      = false;
    grid2.imeEnabled = true;

    <%-- 리소스 트리 선택 이벤트 --%>
    function selectItem(selectedItem) {
      chkRightInit();
      $.postJSON("/sys/menu/webMenu/webMenu/view.sb", selectedItem, function(result) {
        var w = result.data.list;
        var f = [];

        for(var i=0; i<w.length; i++) {
          var item = w[i];
          if(item.resrceFg === "M") {
            resrceCd.text = item.resrceCd;
            resrceNm.text = item.resrceNm;
            url.text = item.url;
            spclAuthor.selectedValue = item.spclAuthor === undefined ? "N" : item.spclAuthor;
            dispIdx.text = item.dispIdx === undefined ? "" : item.dispIdx.toString();

            if(item.authOrgn !== "" && item.authOrgn !== null) {
                if (item.authOrgn.indexOf("A") !== -1) {
                    $("#chkAdmin").prop("checked", true);
                }
                if (item.authOrgn.indexOf("P") !== -1) {
                    $("#chkDist").prop("checked", true);
                }
                if (item.authOrgn.indexOf("C") !== -1) {
                    $("#chkAgency").prop("checked", true);
                }
                if (item.authOrgn.indexOf("H") !== -1) {
                    $("#chkHq").prop("checked", true);
                }
                if (item.authOrgn.indexOf("S") !== -1) {
                    $("#chkStore").prop("checked", true);
                }
                if (item.authOrgn.indexOf("I") !== -1) {
                    $("#chkExStore").prop("checked", true);
                }
            }else{
                $("#chkAdmin").prop("checked", false);
                $("#chkDist").prop("checked", false);
                $("#chkAgency").prop("checked", false);
                $("#chkHq").prop("checked", false);
                $("#chkStore").prop("checked", false);
                $("#chkExStore").prop("checked", false);
            }

          }
          else if(item.resrceFg === "F") {
            f.push(item);
          }
        }
        grid2.itemsSource = new wijmo.collections.CollectionView(f);
        grid2.collectionView.trackChanges = true;
      },
      function(result){
        s_alert.pop(result.message);
      });
    }

    <%-- 리소스 트리 검색 콤보 박스 생성 --%>
    var searchResrceNm = new wijmo.input.AutoComplete("#searchResrceNm", {
        itemsSource: getSearchList(grid.itemsSource),
        selectedIndex: -1,
        displayMemberPath: "path",
        searchMemberPath: "keywords",
        selectedIndexChanged: function(s, e) {
          if(s.selectedItem) {
            grid.selectedItem = s.selectedItem.item;
            selectItem(grid.selectedItem);
          }
        }
    });

    <%-- 리소스 트리 검색 콤보 박스 데이터 가져오기 --%>
    function getSearchList(items, searchList, path) {
      if (searchList == null) {
        searchList = [];
      }
      if (path == null) {
        path = "";
      }
      for (var i = 0; i < items.length; i++) {
        var item = items[i];
        searchList.push({
          item: item,
          path: path + item.header,
          keywords: item.url
        });
        if (item.items) {
          getSearchList(item.items, searchList, path + item.header + " / ");
        }
      }
      return searchList;
    }

    <%-- 메뉴 삭제 버튼 --%>
    $("#deleteBtn").click(function(e) {
      <%-- 메뉴 삭제시 해당 메뉴 기능도 같이 삭제 됩니다. 삭제 하시겠습니까? --%>
      var msg = "<s:message code='webMenu.delete.msg'/>";
      s_alert.popConf(msg, function(){
        var param = {};

        param.resrceCd = grid.selectedItem.resrceCd;

        $.postJSON("/sys/menu/webMenu/webMenu/remove.sb", param, function(result) {
          refreshMenu();
        },
        function(result) {
          s_alert.pop(result.message);
        });
      });
    });

    <%-- 신규등록 버튼 --%>
    $("#regBtn").click(function(e){
      var item = grid.selectedItem;
      <%-- 현재 메뉴 레벨에서는 하위 메뉴를 등록 할 수 없습니다. --%>
      var msg = "<s:message code='webMenu.level.limit'/>";
      if(item != undefined && item.level === 3) {
        s_alert.pop(msg);
        return;
      }

      <%-- 초기화 --%>
      rightInit();
      chkRightInit();
    });

    <%-- 추가 버튼 --%>
    $("#funcReg").click(function(e){
      grid2.collectionView.addNew();
    });

    <%-- 기능 삭제 버튼 --%>
    $("#funcDel").click(function(e){
      grid2.collectionView.removeAt(grid2.selection.row);
    });

    <%-- 저장 버튼 --%>
    $("#funcSave").click(function(e){
      <%-- 메뉴명을 입력해주세요. --%>
      var msg = "<s:message code='webMenu.nm'/> <s:message code='cmm.require.text'/>";
      if(resrceNm.text === "") {
        s_alert.pop(msg);
        return;
      }
      <%-- 메뉴명을 입력해주세요. --%>
      var msg1 = "<s:message code='webMenu.dispIndx'/> <s:message code='cmm.require.text'/>";
      if(dispIdx.text === "") {
        s_alert.pop(msg1);
        return;
      }

      var arr = setGridCRUD(grid2);
      var item = grid.selectedItem;
      var param = {};
      param.resrceCd = resrceCd.text;
      param.resrceNm = resrceNm.text;
      param.url = url.text;
      param.pResrce = item === null ? "000000" : item.resrceCd;
      param.dispLevel = item === null ? 0 : item.level+1;
      param.resrceInfoArr = arr;
      param.dispIdx = parseInt(dispIdx.text);
      param.spclAuthor = spclAuthor.selectedValue === "N" ? null : spclAuthor.selectedValue;

      param.authOrgn = "";
      if($("#chkAdmin").is(":checked")){
        param.authOrgn += "A";
      }
      if($("#chkDist").is(":checked")){
        param.authOrgn += "P";
      }
      if($("#chkAgency").is(":checked")){
        param.authOrgn += "C";
      }
      if($("#chkHq").is(":checked")){
        param.authOrgn += "H";
      }
      if($("#chkStore").is(":checked")){
        param.authOrgn += "S";
      }
      if($("#chkExStore").is(":checked")){
        param.authOrgn += "I";
      }

      $.postJSONArray("/sys/menu/webMenu/webMenu/save.sb", param, function(result) {
        refreshMenu();
        grid.selectedItem = item;
        selectItem(grid.selectedItem);
      },
      function(result) {
        if(!isEmpty(result.message)) {
          s_alert.pop(result.message);
        }
        else if(result.data.resrceNm != undefined) {
          s_alert.pop(result.data.resrceNm);
        }
        else if(result.data.url != undefined) {
          s_alert.pop(result.data.url);
        }
      });

    });

    <%-- 전체펼치기 --%>
    $("#expandBtn").click(function(e){
      grid.collapseToLevel(100000);
    });

    <%-- 전체접기 --%>
    $("#foldBtn").click(function(e){
      grid.collapseToLevel(0);
    });

    <%-- 메뉴 등록 및 삭제 후 리소스 트리 데이터 업데이트 --%>
    function refreshMenu() {
      $.postJSONArray("/sys/menu/webMenu/webMenu/list.sb", {}, function(result) {
        grid.itemsSource = result.data.list;
        searchResrceNm.itemsSource = getSearchList(grid.itemsSource);
        rightInit();
        chkRightInit();
      },
      function(result) {
        s_alert.pop(result.message);
      });
    }

    <%-- 오른쪽 리소스 입력 화면 초기화 --%>
    function rightInit() {
      url.text = "";
      resrceCd.text = "";
      resrceNm.text = "";
      dispIdx.text = "";
      spclAuthor.selectedValue = "N";
      $("#chkAdmin").prop("checked", false);
      $("#chkDist").prop("checked", false);
      $("#chkAgency").prop("checked", false);
      $("#chkHq").prop("checked", false);
      $("#chkStore").prop("checked", false);
      $("#chkExStore").prop("checked", false);

      grid2.itemsSource = new wijmo.collections.CollectionView();
      grid2.collectionView.trackChanges = true;
    }

    <%-- 오른쪽 리소스 입력 화면 체크박스 초기화 --%>
    function chkRightInit() {
      $("#chkAdmin").prop("checked", false);
      $("#chkDist").prop("checked", false);
      $("#chkAgency").prop("checked", false);
      $("#chkHq").prop("checked", false);
      $("#chkStore").prop("checked", false);
      $("#chkExStore").prop("checked", false);
    }

  });
</script>
















