<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

  <div id="posFuncManageArea" style="display:none;" ng-controller="funcKeyCtrl">
    <h2 class="h2_tit" id="posFuncManageTitle"></h2>

    <%--============================================= 탭 =============================================--%>
    <ul class="subTab">
      <%-- 포스기능 사용관리 --%>
      <li><a id="posUseManage" href="javascript:;" class="on"><s:message code="posFunc.manage.posFunc" /></a></li>
      <%-- 포스기능 인증관리 --%>
      <li><a id="posAuthManage"  href="javascript:;"><s:message code="posFunc.auth.posFunc" /></a></li>
    </ul>

    <%-- 왼쪽  --%>
    <div class="w40 fl mt20">
      <div class="wj-TblWrapBr oh mr10 pd20" style="height:560px;">
        <div class="updownSet oh mb10">
          <div class="sb-select w150 fl">
            <div id="posListBox"></div>
          </div>
          <div class="fr mb10">
            <%-- 기능복사버튼 --%>
            <button class="btn_skyblue" id="btnCopyFunc"><s:message code="posFunc.copy.func" /></button>
          </div>
        </div>
        <%-- 그리드 --%>
        <div id="posFuncGrid"></div>
      </div>
    </div>
    <%-- 오른쪽 --%>
    <div class="w60 fr mt20" style="overflow-x: auto;">
      <div class="wj-TblWrapBr oh ml10 pd20" style="height:560px;">
        <div class="updownSet oh mb10">
          <%-- up 버튼 --%>
          <button class="btn_up" id="btnUp"><s:message code="cmm.up" /></button>
          <%-- down 버튼 --%>
          <button class="btn_down" id="btnDown"><s:message code="cmm.down" /></button>
          <%-- 좌표자동세팅 버튼 --%>
          <button class="btn_skyblue" id="btnAutoPosition" style="display:none;"><s:message code="posFunc.autoSet.position" /></button>
          <%-- 저장버튼 --%>
          <button class="btn_skyblue" id="btnSave"><s:message code="cmm.save" /></button>
          <button class="btn_skyblue" id="btnFuncSave"><s:message code="cmm.save" /></button>
        </div>
        <%-- 그리드 --%>
        <div id="useFuncGrid"></div>
        <%-- 기능키영역 --%>
        <div id="funcKeyDiv" style="display:none">
          <div style="height: 330px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="ListBox"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    allow-dragging="None">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="posFunc.grid.fnkeyNo"/>" binding="fnkeyNo" width="100" isReadOnly="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="posFunc.grid.fnkeyNm"/>" binding="fnkeyNm" width="*" isReadOnly="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="posFunc.grid.imgFileNm"/>" binding="imgFileNm" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="posFunc.grid.useYn"/>" binding="fnkeyUsed" width="70" isReadOnly="true"></wj-flex-grid-column>

            </wj-flex-grid>
          </div>
          <div id="funcKeyGraph" class="funcKeyWrap funcKeyLine2 mt20"></div>
        </div>
      </div>
    </div>

  </div>
<script>

  var selectedRow;
  var posUsageEnv = ${cnv.getEnvCodeExcpAll("4019")};
  var posTypeEnv = ${cnv.getEnvCodeExcpAll("4020")};

  <%-- header --%>
  var posFuncHeader =
    [
      {binding: "fnkeyFg", header: "<s:message code='posFunc.fnkeyFg' />", visible: false, width: "*" },
      {binding: "fnkeyNm", header: "<s:message code='posFunc.fnkeyNm' />", width: "*"},
      {binding: "array", header: "<s:message code='posFunc.array' />", width: "*", visible: false},
      {binding: "totCnt", header: "<s:message code='posFunc.func.cnt' />", width: 60},
      {binding: "regCnt", header: "<s:message code='posFunc.regist.cnt' />", width: 60}
    ];

  var useFuncHeader =
    [
      {
        binding: "gChk",
        header: "<s:message code='cmm.chk' />",
        dataType: wijmo.DataType.Boolean,
        width: 40
      },
      {
        binding: "fnkeyNo",
        header: "<s:message code='posFunc.fnkeyNo' />",
        isReadOnly: true,
        width: "*"
      },
      {
        binding: "fnkeyNm",
        header: "<s:message code='posFunc.fnkeyNm' />",
        isReadOnly: true,
        width: "*"
      },
      {
        binding: "existFgBefore",
        header: "<s:message code='posFunc.existFgBefore' />",
        visible: false,
        width: "*"
      },
      {
        binding: "existFg",
        header: "<s:message code='posFunc.existFg' />",
        visible: false,
        width: "*"
      },
      {
        binding: "posiAdjYn",
        header: "<s:message code='posFunc.posiAdjYn' />",
        dataType: wijmo.DataType.Boolean,
        visible: false,
        width: "*"
      },
      {
        binding: "dispSeq",
        header: "<s:message code='posFunc.dispSeq' />",
        visible: false,
        width: "*"
      },
      {
        binding: "colPosi",
        header: "<s:message code='posFunc.colPosi' />",
        visible: false,
        width: "*"
      },
      {
        binding: "rowPosi",
        header: "<s:message code='posFunc.rowPosi' />",
        visible: false,
        width: "*"
      },
      {binding: "width", header: "<s:message code='posFunc.width' />", visible: false, width: "*"},
      {
        binding: "height",
        header: "<s:message code='posFunc.height' />",
        visible: false,
        width: "*"
      },
      {
        binding: "useYn",
        header: "<s:message code='posFunc.useYn' />",
        dataType: wijmo.DataType.Boolean,
        width: 60
      }
    ];

  <%-- 그리드 생성 --%>
  var posFuncGrid = wgrid.genGrid("#posFuncGrid", posFuncHeader);
  var useFuncGrid = wgrid.genGrid("#useFuncGrid", useFuncHeader);

  posFuncGrid.isReadOnly = true;
  useFuncGrid.isReadOnly = false;

  var posListBox = wcombo.genCommonBox("#posListBox", []);

  <%-- 그리드 포맷 --%>
  posFuncGrid.formatItem.addHandler(function (s, e) {
    if (e.panel === s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if (col.binding === "fnkeyNm") {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  <%-- 그리드 선택 이벤트 --%>
  posFuncGrid.addEventListener(posFuncGrid.hostElement, 'click', function (e) {
    var ht = posFuncGrid.hitTest(e);
    if (ht.cellType === wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if (col.binding === "fnkeyNm") {
        selectedRow = posFuncGrid.rows[ht.row].dataItem;
        <%-- 위치조정여부 'N'이면 버튼 보여주기 --%>
        if (selectedRow.posiAdjYn === "N") {
          $("#btnAutoPosition").show();
        } else {
          $("#btnAutoPosition").hide();
        }

        <%-- 결제메뉴(좌/우)의 경우에는 오른쪽 div에 기능키 영역이 들어감 --%>
        if (selectedRow.fnkeyFg === "6020" || selectedRow.fnkeyFg === "6021") {
          // 포스기능 키 목록 조회
          var scope = agrid.getScope("funcKeyCtrl");
          scope._broadcast('funcKeyCtrl');
          scope.setSaveInfo("storeCd", selectedRow.storeCd);
          scope.setSaveInfo("posNo", selectedRow.posNo);
          scope.setSaveInfo("fnkeyFg", selectedRow.fnkeyFg);

          $("#btnUp").hide();
          $("#btnDown").hide();
          $("#btnSave").hide();
          $("#useFuncGrid").hide();

          $("#btnFuncSave").show();
          $("#funcKeyDiv").show();

          // TODO : 그래프 데이터 포스기능키 좌/우 적용 변경..
          funckeyGraph.format.openByGrid(true);

        } else {

          $("#btnFuncSave").hide();
          $("#funcKeyDiv").hide();

          $("#btnUp").show();
          $("#btnDown").show();
          $("#btnSave").show();
          $("#useFuncGrid").show();

          // 포스기능 상세 조회
          getPosFuncDetail();
        }
      }
    }
  });

  <%-- 포스 선택시 기능목록 조회  --%>
  posListBox.selectedIndexChanged.addHandler(function (s, e) {
    getPosConfList();
  });

  <%-- 포스기능 사용관리 화면 보여주기 --%>
  function showPosFuncList() {
    $("#posFuncManageArea").show();
    getPosList();
    // 선택매장 표시
    $("#posFuncManageTitle").text("[" + selectedStore.storeCd + "] " + selectedStore.storeNm);
    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("funcKeyCtrl");
    scope.flex.refresh();

  }

  <%-- 포스 목록 조회 --%>
  function getPosList() {
    var param = {};
    param.hqOfficeCd = selectedStore.hqOfficeCd;
    param.storeCd = selectedStore.storeCd;

    $.postJSON("/base/store/posfunc/use/getPosList.sb", param,
      function (result) {
        posList = result.data.list;
        posListBox.itemsSource = posList;

        if (posList.length === 0) {
          s_alert.pop("<s:message code='posFunc.no.regist.pos' />");
          return;
        }

        getPosConfList();
      },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

  <%-- 포스 기능 목록 조회 --%>
  function getPosConfList() {

    var param = {};
    param.storeCd = selectedStore.storeCd;
    param.posNo = posListBox.selectedValue;

    $.postJSON("/base/store/posfunc/use/getPosFuncList.sb", param,
      function (result) {
        var list = result.data.list;
        posFuncGrid.itemsSource = new wijmo.collections.CollectionView(list);
        posFuncGrid.collectionView.trackChanges = true;
        useFuncGrid.itemsSource = new wijmo.collections.CollectionView([]);
      },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

  <%-- 컬럼 셋팅 변경 --%>
  function changeColSet(list) {
    for (var i = useFuncGrid.itemsSource.itemCount - 1; i >= 0; i--) {
      var item = useFuncGrid.collectionView.items[i];
      if (item.posiAdjYn) {
        item.colPosi = "";
        item.rowPosi = "";
        item.width = "";
        item.height = "";
        //TODO row단위 readonly 바꿔야함
        useFuncGrid.collectionView.commitEdit();
        useFuncGrid.collectionView.refresh();
      }
    }
  }

  function hidePosFuncList() {
    $("#posFuncManageArea").hide();
  }

  <%-- 기능복사 버튼 클릭 //TODO 작업중 --%>
  $("#posFuncManageArea #btnCopyFunc").click(function () {
    openCopyFuncLayer();
  });

  <%-- up 버튼 클릭 --%>
  $("#posFuncManageArea #btnUp").click(function (e) {
    for (var i = 0; i < useFuncGrid.collectionView.itemCount; i++) {
      if (i > 0 && (useFuncGrid.collectionView.items[i].gChk === true)) {
        if (useFuncGrid.collectionView.items[i - 1].gChk !== true) {
          var tmpItem = useFuncGrid.collectionView.items[i - 1];
          useFuncGrid.collectionView.items[i - 1] = useFuncGrid.collectionView.items[i];
          useFuncGrid.collectionView.items[i] = tmpItem;
          useFuncGrid.collectionView.commitEdit();
          useFuncGrid.collectionView.refresh();
        }
      }
    }
  });

  <%-- down 버튼 클릭 --%>
  $("#posFuncManageArea #btnDown").click(function (e) {
    for (var i = useFuncGrid.itemsSource.itemCount - 1; i >= 0; i--) {
      if ((i < useFuncGrid.itemsSource.itemCount - 1) && (useFuncGrid.collectionView.items[i].gChk === true)) {
        if (useFuncGrid.collectionView.items[i + 1].gChk !== true) {
          var tmpItem = useFuncGrid.collectionView.items[i + 1];
          useFuncGrid.collectionView.items[i + 1] = useFuncGrid.collectionView.items[i];
          useFuncGrid.collectionView.items[i] = tmpItem;
          useFuncGrid.collectionView.commitEdit();
          useFuncGrid.collectionView.refresh();
        }
      }
    }
  });

  <%--//TODO 좌표자동세팅 버튼 클릭 --%>
  $("#posFuncManageArea #btnAutoPosition").click(function () {
  });

  <%-- 포스기능 상세 조회 --%>

  function getPosFuncDetail() {
    var param = {};

    param.storeCd = selectedStore.storeCd;
    param.posNo = posListBox.selectedValue;
    param.fnkeyFg = selectedRow.fnkeyFg;

    $.postJSON("/base/store/posfunc/use/getPosConfDetail.sb", param,
      function (result) {
        var list = result.data.list;
        useFuncGrid.itemsSource = new wijmo.collections.CollectionView(list);
        useFuncGrid.collectionView.trackChanges = true;
      },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

  <%-- 저장버튼 클릭 --%>
  $("#posFuncManageArea #btnSave").click(function () {
    var paramArr = new Array();

    for (var i = 0; i < useFuncGrid.collectionView.itemCount; i++) {  // dispSeq 재설정
      useFuncGrid.collectionView.editItem(useFuncGrid.collectionView.items[i]);
      useFuncGrid.collectionView.items[i].status = 'U';
      useFuncGrid.collectionView.items[i].dispSeq = (i + 1);
      useFuncGrid.collectionView.items[i].storeCd = selectedStore.storeCd;
      useFuncGrid.collectionView.items[i].posNo = posListBox.selectedValue;
      useFuncGrid.collectionView.items[i].fnkeyFg = selectedRow.fnkeyFg;
      useFuncGrid.collectionView.commitEdit();
      paramArr.push(useFuncGrid.collectionView.items[i]);
    }

    $.postJSONArray("/base/store/posfunc/use/savePosConf.sb", paramArr,
      function (result) {
        s_alert.pop("<s:message code='cmm.saveSucc' />");
        useFuncGrid.collectionView.clearChanges();
        getPosFuncDetail();
      },
      function (result) {
        s_alert.pop(result.message);
      });
  });

  <%-- 탭 클릭 --%>
  $("#posFuncManageArea #posAuthManage").click(function () {
    hidePosFuncList();
    showPosFuncAuth();
  });

</script>
<script type="text/javascript">
  // Default resources are included in grapheditor resources
  mxLoadResources = false;

  // urlParams is null when used for embedding
  window.urlParams = window.urlParams || {};

  window.MAX_REQUEST_SIZE = window.MAX_REQUEST_SIZE  || 10485760;

  window.RESOURCES_PATH = window.RESOURCES_PATH || '/resource/graph/resources';
  window.RESOURCE_BASE = window.RESOURCE_BASE || window.RESOURCES_PATH + '/message';
  window.STYLE_PATH = window.STYLE_PATH || '/resource/graph/styles';
  window.CSS_PATH = window.CSS_PATH || '/resource/graph/styles';
  window.IMAGE_PATH = window.IMAGE_PATH || '/resource/graph/images';

  window.FUNCKEY_OPEN_URL = window.FUNCKEY_OPEN_URL || '/base/store/posfunc/use/getFuncKeyXml.sb';
  window.FUNCKEY_SAVE_URL = window.FUNCKEY_SAVE_URL || '/base/store/posfunc/use/saveFuncKey.sb';

  window.mxBasePath = window.mxBasePath || '/resource/vendor/mxgraph/src';
  window.mxLanguage = window.mxLanguage || urlParams['lang'];
  window.mxLanguages = window.mxLanguages || ['ko'];

</script>
<script type="text/javascript" src="/resource/vendor/mxgraph/mxClient.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/sanitizer/sanitizer.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/js/posFuncUseManage.js?ver=2018100201" charset="utf-8"></script>
