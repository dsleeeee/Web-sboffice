/****************************************************************
 *
 * 파일명 : TouchKey.js
 * 설  명 : 판매터치키등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.13    노현수      1.0
 * 2018.11.20    노현수      1.1
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 버튼사용 필터 DropBoxDataMap
var touchKeyFilterData = [
  {"name":"전체","value":"A"},
  {"name":"사용","value":"T"},
  {"name":"미사용","value":"F"}
];

var touchKeyStyleCd, touchKeyStyleCdList, touchKeyStyles;
var viewStyleCd;

// angular 그리드 생성
app.controller('touchKeyCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('touchKeyCtrl', $scope, $http, false));

  // 접속 사용자의 권한
  $scope.userOrgnFg = gvOrgnFg;

  // 상품분류정보
  $scope.prodClassInfo = {};
  $scope.setProdClassInfo = function(data){
    $scope.prodClassInfo = data;
  };
  $scope.getProdClassInfo = function(){
    return $scope.prodClassInfo;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.areAllRowsSelected = function(flex) {
      for (var i = 0; i < flex.rows.length; i++) {
        if (!flex.rows[i].isSelected) {
          return false;
        }
      }
      return true;
    };
    // ReadOnly 효과설정 : checkbox disabled
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "touchKeyUsed") {
          e.cell.children[0].disabled = true;
        }
      }
    });
    // mouse click
    s.hostElement.addEventListener('mousedown', function (e) {
      var ht = s.hitTest(e);
      // allow sorting/resizing/dragging
      if (ht.cellType === wijmo.grid.CellType.ColumnHeader) {
        return;
      }
      // row 클릭시 선택되도록 설정 : 자연스러운 드래그를 위함
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        for (var i = 0; i < s.rows.length; i++) {
          s.rows[i].isSelected = false;
        }
        s.rows[ht.row].isSelected = true;
      }
      // cancel default handling
      e.preventDefault();
      e.stopPropagation();
      e.stopImmediatePropagation();
    }, true);

    $scope.filter.showFilterIcons = false;
    $scope.touchKeyFilter = 'F';
    $scope.touchKeyGrp = null;
  };
  // 필터선언
  $scope.filter = {
    touchKeyUsed: ''
  };
  // 필터 적용
  var toFilter = null;
  $scope.updateFilter = function(part, value) {
    if ( part === 'touchKeyUsed' ) {
      if (value === "A") {
        value = "";
      } else {
        value = value === "T";
      }
    }
    // update filter
    $scope.filter[part] = value;
    // reschedule update
    if (toFilter) clearTimeout(toFilter);
    toFilter = setTimeout(function () {
      if ($scope.data) {
        $scope.data.refresh();
      }
    }, 10);
  };
  //터치키 그룹코드 콤보
  $scope._setComboData("touchKeyGrpCombo", touchKeyGrpData);
  //터치키 그룹코드 콤보(터치키 매장적용 팝업)
  $scope._setComboData("applyTouchKeyGrpCombo", touchKeyGrpData);
  //터치키 그룹코드 콤보(터치키 복사 팝업)
  $scope._setComboData("copyTouchKeyGrpCombo", touchKeyGrpData);
  // 버튼사용여부 필터 콤보
  $scope._setComboData("touchKeyFilterCombo", touchKeyFilterData);
  // 브랜드 콤보박스 셋팅
  $scope._setComboData("srchBrandCombo", brandList);
  $scope.setTouchKeyFilter = function(s) {
    $scope.updateFilter('touchKeyUsed', s.selectedValue);
  };
  $scope.selectedBrand = function(s) {
    $scope._broadcast('touchKeyCtrl');
  };
  // 상품목록 그리드 조회
  $scope.$on("touchKeyCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.prodClassCd = $scope.getProdClassInfo().prodClassCd;
    params.prodNm = $scope.getProdClassInfo().prodNm;
    params.hqBrandCd = $scope.hqBrandCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/base/prod/touchKey/touchKey/list.sb", params, function() {
      // 조회내용 없을 경우 팝업메시지 별도 처리
      if ($scope.flex.collectionView.items.length < 1) {
        $scope._popMsg(messages["touchKey.noProdData"]);
      } else {
        if (touchKeyGraph) {
          touchKeyGraph.sidebar.initUsed();
        }
      }
      // row 선택 클리어
      $scope.flex.select(-1, -1);
      // 필터 수행
      $scope.data.filter = function(item) {
        var touchKeyUsed = $scope.filter.touchKeyUsed;
        if ( !isEmpty(touchKeyUsed) && item.touchKeyUsed !== touchKeyUsed ) {
          return false;
        }
        return true;
      };
      $scope.filteredData = $scope.data.items;

    }, false);

    if($scope.touchKeyGrp != null && $scope.touchKeyGrp != "" && $("#touchKeyView").is(":visible")){
      var params = {};
      params.tukeyGrpCd = $scope.touchKeyGrp;
      params.hqBrandCd = $scope.hqBrandCd;

      $.postJSON("/base/prod/touchKey/touchKey/noTouchKey.sb", params, function(result) {
            if(result.data.list.length != 0){ // 터치키미적용상품이 없으면 팝업창을 안띄움
              $scope._broadcast('showPopUpNoTouchKey', $scope.touchKeyGrp);
            }
          },
          function(result){
            s_alert.pop(result.message);
          });
    }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품분류정보 팝업
  $scope.popUpProdClass = function() {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd = scope.getSelectedClass();
        var params = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
            function(response){
              var prodClassInfo = {};
              prodClassInfo.prodClassCd = prodClassCd;
              prodClassInfo.prodClassCdNm = response.data.data;
              $scope.setProdClassInfo(prodClassInfo);
              $scope._broadcast('touchKeyCtrl');
            }
        );
      }
    });
  };

  // 터치키 적용매장 목록 팝업
  $scope.$on("showPopUp", function(event, data) {
    var popup = $scope.popUpApplyStoreLayer;
    popup.shown.addHandler(function (s) {
      // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
      setTimeout(function() {
        $scope._broadcast('popUpApplyStoreCtrl');
      }, 50)
    });
    // 팝업 닫을때
    popup.show(true, function (s) {
      // 적용 버튼 눌렀을때만
      if (popup.dialogResult === "wj-hide-apply") {
        // 팝업 컨트롤러 Get
        var scopeLayer = agrid.getScope("popUpApplyStoreCtrl");
        // 저장 파라미터 설정
        var paramArr = [];

        for (var i = 0; i < scopeLayer.flexLayer.itemsSource.items.length; i++) {
          if ( scopeLayer.flexLayer.itemsSource.items[i].gChk === true ) {
            scopeLayer.flexLayer.itemsSource.items[i].tukeyGrpCd = scopeLayer.applyTouchKeyGrp;
            paramArr.push(scopeLayer.flexLayer.itemsSource.items[i]);
          }
        }
        if (paramArr.length <= 0) {
          s_alert.pop(messages["touchKey.msg.select"]);
        }
        // 저장수행
        $scope._postJSONSave.withPopUp("/base/prod/touchKey/touchKey/applyStore.sb", paramArr, function () {
          $scope._popMsg(messages["cmm.saveSucc"]);
        });
      }
    });
  });

  // 매장 판매터치키복사 팝업
  $scope.copyStoreTouchKey = function(){
    $scope.copyStoreTouchKeyLayer.show(true);
    $scope._broadcast('copyStoreTouchKeyCtrl');
  };

  // 스타일미리보기
  $scope.viewStyle = function() {
    $scope.touchKeyStyleLayer.show();
    $scope._broadcast('touchKeyStyleCtrl', viewStyleCd);
    event.preventDefault();
  };

  // 터치키 복사 팝업
  $scope.$on("showPopUpCopy", function(event, data) {
    var popup = $scope.popUpCopyTouchKeyLayer;
    popup.shown.addHandler(function (s) {
      // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
      setTimeout(function() {
        $scope._broadcast('popUpCopyTouchKeyCtrl');
      }, 50)
    });
    // 팝업 닫을때
    popup.show(true, function (s) {
      // 적용 버튼 눌렀을때만
      if (popup.dialogResult === "wj-hide-apply") {
        // 팝업 컨트롤러 Get
        var scopeLayer = agrid.getScope("popUpCopyTouchKeyCtrl");
        scopeLayer.$apply(function(){
          //scopeLayer._popConfirm("신규 그룹으로 터치키를 생성하시겟습니까?", function() {

          // 저장 파라미터 설정
          var params = {};
          params.copyTukeyGrpCd = scopeLayer.copyTouchKeyGrp;
          // 가상로그인 대응
          if (document.getElementsByName('sessionId')[0]) {
            params.sid = document.getElementsByName('sessionId')[0].value;
          }

          // 저장수행
          $scope._postJSONSave.withPopUp("/base/prod/touchKey/touchKey/copyTouchKeyGrp.sb", params, function (response) {

            // 메시지
            $scope._popMsg(messages["cmm.saveSucc"]);

            var scope = agrid.getScope("touchKeyCtrl");

            // 새로 저장한 그룹코드 값으로 selectBox 셋팅
            setTimeout(function () {
              scope.touchKeyGrp = response.data.data;
              scope.touchKeyGrpCombo.selectedValue = response.data.data;

              // 터치키 재조회
              document.getElementById('btnSrchTouchKey').click();

            }, 50);

            // 터치키 그룹 코드 재조회
            $.ajax({
              type: 'POST',
              async: false,
              cache: false,
              dataType: 'json',
              data: params,
              url: '/base/prod/touchKey/touchKey/getTouchKeyGrp.sb',
              success: function(data){

                var list = data.data.list;
                var comboArray = [];
                var comboArrayAll = [];
                var comboData  = {};

                comboArrayAll.unshift({name: "전체", value: ""});

                for (var i = 0; i < list.length; i++) {
                  comboData       = {};
                  comboData.name  = list[i].name;
                  comboData.value = list[i].value;
                  comboArray.push(comboData);
                  comboArrayAll.push(comboData);
                }

                touchKeyGrpData = comboArray;
                tukeyGrpData = comboArrayAll;
                scope._setComboData("touchKeyGrpCombo", touchKeyGrpData);
                scope._setComboData("applyTouchKeyGrpCombo", touchKeyGrpData);
                scope._setComboData("copyTouchKeyGrpCombo", touchKeyGrpData);
              }
            });

            // 터치키 그룹이 있으므로, 보여주기
            $("#btnSrchTouchKey").css("display", "");
            $("#btnNewGrp").css("display", "");
            //$("#btnCancleNewGrp").css("display", "");
            $("#touchKeyGrpCombo").attr("disabled", false);
            $("#touchKeyGrpCombo").css('background-color', '#FFFFFF');
            $("#btnApplyStore").css("display", "");
            $("#trTouchKeyGrp").css("display", "");
            $("#trApplyStore").css("display", "");

            // 터치키 저장 기본 수정 Flag로 셋팅
            $("#hdNewGrp").val("N");

            // 매장은 터치키 매장적용, 터치키복사 기능 사용할 수 없음.
            if(orgnFg === "STORE") {
                $("#trApplyStore").css("display", "none");
            }else{
                $("#trApplyStore").css("display", "");
            }

          });
          //});
        });
      }
    });
  });

  // 터치키미적용상품 팝업
  $scope.$on("showPopUpNoTouchKey", function(event, data) {
    var scope = agrid.getScope('touchKeyCtrl');
    var tukeyGrpCd = scope.touchKeyGrp;
    $scope.popUpNoTouchKeyLayer.show();
    $scope._broadcast('popUpNoTouchKeyCtrl', tukeyGrpCd);
    event.preventDefault();
  });

  // 매장수정허용분류 팝업
  $scope.$on("showPopUpStoreModGrp", function(event, data) {

    $scope.popUpStoreModGrpLayer.show();
    $scope._broadcast('popUpStoreModGrpCtrl');
    event.preventDefault();

  });

  // 매장수정허용분류 팝업
  $scope.$on("showGrpNm", function(event, data) {

    $scope.popUpGrpNmLayer.show();
    $scope._broadcast('popUpGrpNmCtrl');
    event.preventDefault();

  });

}]);


/**
 * 터치키영역은 화면이 로드된 후 그린다.
 */
var touchKeyGraph;
$(document).ready(function() {
  (function () {
    var params = {};
    if (document.getElementsByName('sessionId')[0]) {
      params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // 터치키그룹 추가
    var scope = agrid.getScope("touchKeyCtrl");
    params['tukeyGrpCd'] = scope.touchKeyGrp;

    if(scope.touchKeyGrp !== null && scope.touchKeyGrp !== "") { // 기존에 사용중인 터치키그룹이 있는 경우에만 조회(없으면 기본 스타일 '01'로 셋팅)
      // 스타일 코드 조회
      $.ajax({
        type: 'POST',
        async: false,
        cache: false,
        dataType: 'json',
        url: '/base/prod/touchKey/touchKey/getTouchKeyStyleCd.sb',
        data: params,
        success: function (data) {
          touchKeyStyleCd = data.data;
        }
      });
    }else{ touchKeyStyleCd = '01' }

    // 스타일 코드목록 조회
    $.ajax({
      type: 'POST',
      async: false,
      cache: false,
      dataType: 'json',
      url: '/base/prod/touchKey/touchKey/getTouchKeyStyleCdList.sb',
      data: params,
      success: function(data){
        touchKeyStyleCdList = JSON.parse(data.data);
      }
    });
    // 스타일 목록 조회
    $.ajax({
      type: 'POST',
      async: false,
      cache: false,
      dataType: 'json',
      url: '/base/prod/touchKey/touchKey/getTouchKeyStyleList.sb',
      data: params,
      success: function (data) {
        touchKeyStyles = data.data.list;
      }
    });

    var touchkeyInit = Touchkey.prototype.init;
    Touchkey.prototype.init = function () {
      touchkeyInit.apply(this, arguments);
    };

    if (!mxClient.isBrowserSupported()) {
      // Displays an error message if the browser is not supported.
      mxUtils.error('Browser is not supported!', 200, false);
    }
    else {
      // Adds required resources (disables loading of fallback properties, this can only
      // be used if we know that all keys are defined in the language specific file)
      mxResources.loadDefaultBundle = false;
      var bundle = mxResources.getDefaultBundle(RESOURCE_BASE, mxLanguage)
          || mxResources.getSpecialBundle(RESOURCE_BASE, mxLanguage);

      // Fixes possible asynchronous requests
      mxUtils.getAll(
          [bundle, STYLE_PATH + '/touchKey.xml'],
          function (xhr) {
            // Adds bundle text to resources
            mxResources.parse(xhr[0].getText());

            // Configures the default graph theme
            var themes = {};
            themes[Graph.prototype.defaultThemeName] = xhr[1].getDocumentElement();

            // Main
            var touchKey = new Touchkey(themes);
            touchKeyGraph = touchKey;
          },
          function () {
            document.body.innerHTML = '<center style="margin-top:10%;">Error loading resource files. Please check browser console.</center>';
          });
    }
  })();

});

//지정된 영역에만 터치키를 넣을 수 있도록 처리
mxGraph.prototype.allowNegativeCoordinates = false;

// Overrides check for valid roots
mxGraph.prototype.isValidRoot = function() {
  return false;
};

/**
 * 메인 Class
 */
Touchkey = function (themes) {
  mxEventSource.call(this);
  //터치키분류 컨테이너
  this.classContainer = document.getElementById("classArea");
  //상품 컨테이너
  this.prodContainer = document.getElementById("prodArea");
  //터치키분류 생성
  this.classArea = new Graph(this.classContainer, themes);
  //상품분류 생성
  this.prodArea = new Graph(this.prodContainer, themes);
  //왼쪽 Wijmo 그리드 생성
  this.sidebar = new Sidebar(this.prodArea);
  //오른쪽 설정 영역 생성
  this.format = new Format(this);
  this.init();

  var cGraph = this.classArea;
  var pGraph = this.prodArea;
  // 영역 외부 클릭시 이벤트
  $(document).click(function (event) {
    // 터치키분류, 상품영역, 우측 기능키영역등 특정 영역 제외
    if(!$(event.target).closest('#classWrap').length && !$(event.target).closest('#prodWrap').length
        && !$(event.target).closest('#fontStyleWrap') && !$(event.target).closest('#colorStyleWrap') ) {
      // 그래프영역 선택 초기화
      cGraph.getSelectionModel().clear();
      pGraph.getSelectionModel().clear();
      // 터치키분류 영역 에디팅 판단하여 에디팅 취소 처리
      if (cGraph.cellEditor.getEditingCell() != null) {
        cGraph.cellEditor.stopEditing(true);
      }
    }
  });
};

//Extends mxEditor
mxUtils.extend(Touchkey, mxEventSource);

//변수 생성
Touchkey.prototype.classArea = null;
Touchkey.prototype.prodArea = null;
Touchkey.prototype.sidebar = null;

/**
 * 메인 - 초기화
 */
Touchkey.prototype.init = function () {
  //터치키분류 영역 고유 특성 정의
  this.classArea.initClassArea(this.prodArea);
  //상품 영역 고유 특성 정의
  this.prodArea.initProdArea(this.classArea, this.sidebar);
  //레이어가 보일 때(switchLayer..) 이벤트 추가 처리
  var sidebar = this.sidebar;
  var mxGraphModelSetVisible = mxGraphModel.prototype.setVisible;
  this.prodArea.model.setVisible = function (cell, visible) {
    //visible = true 인 레이어의 상품을 그리드에서 체크
    if (sidebar != null && visible) {
      sidebar.initUsed(cell);
    }
    mxGraphModelSetVisible.apply(this, arguments);
  };
  var format = this.format;
  var classArea = this.classArea;
  //터치키분류 영역에서 셀 클릭 시 설정 패널 초기화
  classArea.addListener(mxEvent.CLICK, function (sender, evt) {
    format.update(classArea);
  });
  //상품 영역에서 셀 클릭 시 설정 패널 초기화
  var prodArea = this.prodArea;
  prodArea.addListener(mxEvent.CLICK, function (sender, evt) {
    format.update(prodArea);
  });

  //마우스 오른쪽 클릭 - 컨텍스트 메뉴
  mxEvent.disableContextMenu(this.classContainer);
  mxEvent.disableContextMenu(this.prodContainer);

  format.selectStyle.selectedValue = touchKeyStyleCd.replaceAll('\"','');
  format.setBtnStyle();

  //서버의 초기 설정 로드
  this.format.open(true);

};

/**
 * 메모리 삭제
 */
Touchkey.prototype.destroy = function () {
  if (this.classArea != null) {
    this.classArea.destroy();
    this.classArea = null;
  }
  if (this.classArea != null) {
    this.prodArea.destroy();
    this.prodArea = null;
  }
  if (this.keyHandler != null) {
    this.keyHandler.destroy();
    this.keyHandler = null;
  }

  var c = [this.prodContainer, this.classContainer];
  for (var i = 0; i < c.length; i++) {
    if (c[i] != null && c[i].parentNode != null) {
      c[i].parentNode.removeChild(c[i]);
    }
  }
};

/**
 * DOM element 클릭 이벤트 추가
 */
function addClickHandler(elt, funct) {
  if (funct) {
    mxEvent.addListener(elt, 'click', function (evt) {
      funct(evt);
      mxEvent.consume(evt);
    });

    if (document.documentMode !== null && document.documentMode >= 9) {
      // Prevents focus
      mxEvent.addListener(elt, 'mousedown', function (evt) {
        evt.preventDefault();
      });
    }
  }
}

/**
 * 상품 조회 그리드 처리
 */
function Sidebar(prodArea) {
  this.graph = prodArea;
  this.init();
}

/**
 * 그리드 영역 초기화
 */
Sidebar.prototype.init = function () {
  this.grid = this.makeGrid();
  this.makeDragSource();
};

/**
 * 사용여부 초기화
 */
Sidebar.prototype.initUsed = function (layer) {
  var graph = this.graph;
  var theGrid = this.grid;
  var scope = agrid.getScope("touchKeyCtrl");

  var layer = layer || graph.getDefaultParent();

  //그리드의 모든 항목 사용여부 false로 셋팅
  for (i = 0; i < theGrid.rows.length; i++) {
    theGrid.setCellData(theGrid.rows[i].index, 'touchKeyUsed', false);
  }

  //상품코드로 wijmo 그리드에서 해당 인덱스 추출
  var getIdByProdCd = function (prodCd) {
    var id = -1;
    for (var g = 0; g < theGrid.rows.length; g++) {
      if (theGrid.rows[g].dataItem.prodCd === prodCd) {
        id = theGrid.rows[g].index;
        break;
      }
    }
    return id;
  };

  //각 상품의 상품코드로 그리드에서 체크 표시
  var model = graph.getModel();
  var match, regex, prodCd, id;
  for ( var i in model.cells) {
    regex = /tukeyFg=([^=]*.(?=;))/gm;
    match = regex.exec(model.cells[i].getStyle());
    // 버튼일때만 수행하도록
    if (match && match[1] === "01") {
      // 정규식 이용하여 prodCd 추출 : 상품코드 길이 가변 대응
      regex = /prodCd=([^=]*.(?=;))/gm;
      match = regex.exec(model.cells[i].getStyle());
      if (match) {
        prodCd = match[1];
      }
      id = getIdByProdCd(prodCd);
      if (id >= 0) {
        theGrid.setCellData(id, 'touchKeyUsed', true);
      }
    }
  }

  // 필터 업데이트
  scope.updateFilter('touchKeyUsed', scope.touchKeyFilter);

};

/**
 * 생성된 angularJS 그리드를 가져온다.
 */
Sidebar.prototype.makeGrid = function () {
  var grid = agrid.getScope("touchKeyCtrl").flex;

  //선택 Clear
  grid.select(-1, -1);

  return grid;
};

/**
 * Cell을 드래그 할수 있도록 처리
 */
Sidebar.prototype.makeDragSource = function () {

  var graph = this.graph;
  var sidebar = this;
  var grid = this.grid;

  //드래그 이벤트 생성
  var dropEvent = function (graph, evt, cell, x, y) {

    var parent = graph.getDefaultParent();
    var model = graph.getModel();

    // Drop 할 때 오브젝트 생성
    var pt = graph.getPointForEvent(evt);

    // Grid에서 선택된 데이터 대상
    for (var selected = 0; selected < grid.selectedItems.length; selected++) {

      // 마우스 포인터 위치를 기준으로 Drop 가능한 위치 찾기
      var pos = graph.findPosition(pt);
      if (pos == null) {
        mxEvent.consume(evt);
        return;
      }
      // Drop 된 포지션과 다음 포지션에 터치키 생성
      var rows = grid.selectedRows[selected];
      var item = rows.dataItem;
      // 업데이트 시작
      model.beginUpdate();
      // 스타일코드
      var styleCd = graph.selectStyle.selectedValue;

      try {
        // 버튼
        var btn = graph.insertVertex(parent, null,
            null,
            pos.x, pos.y,
            graph.touchKeyInfo.width, graph.touchKeyInfo.height,
            "prodCd=" + item.prodCd + ";styleCd=" + styleCd + ";tukeyFg=01;rounded=0;"
        );

        if($("#hdTouchKeyStyleApply").val() === "Y" && $("#hdCopyFillColor").val() !== "") {
          graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, $("#hdCopyFillColor").val(), new Array(btn));
          graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles["01"].off, new Array(btn));
          graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.fontStyles["01"].size, new Array(btn));
        }else{
          graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles["01"].off, new Array(btn));
          graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles["01"].off, new Array(btn));
          graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.fontStyles["01"].size, new Array(btn));
        }

        // 버튼에 품목명 추가
        var prodTag = graph.insertVertex(btn, null,
            item.prodNm,
            5, 5,
            0, 0,
            "prodCd=" + item.prodCd + ";styleCd=" + styleCd + ";tukeyFg=02;rounded=0;strokeColor=none;resizable=0;selectable=1;movable=0;align=left;verticalAlign=top;whiteSpace=wrap;overflow=hidden;"
        );

        if($("#hdTouchKeyStyleApply").val() === "Y" && $("#hdCopyFillColor").val() !== "") {
          graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, $("#hdCopyFillColor").val(), new Array(prodTag));
          graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, $("#hdCopyFont02").val().split("/")[0], new Array(prodTag));
          graph.setCellStyles(mxConstants.STYLE_FONTSIZE, $("#hdCopyFont02").val().split("/")[1], new Array(prodTag));
        }else{
          graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles["02"].off, new Array(prodTag));
          graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles["02"].off, new Array(prodTag));
          graph.setCellStyles(mxConstants.STYLE_FONTSIZE, fontSizeEnvstVal, new Array(prodTag));
        }

        // 버튼에 금액 추가
        var priceTag = graph.insertVertex(btn, null,
            addComma(item.saleUprc),
            5, graph.touchKeyInfo.y / 2 + 10,
            0, 0,
            "prodCd=" + item.prodCd + ";styleCd=" + styleCd + ";tukeyFg=03;rounded=0;strokeColor=none;resizable=0;selectable=1;movable=0;align:right;"
        );

        if($("#hdTouchKeyStyleApply").val() === "Y" && $("#hdCopyFillColor").val() !== "") {
          graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, $("#hdCopyFillColor").val(), new Array(priceTag));
          graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, $("#hdCopyFont03").val().split("/")[0], new Array(priceTag));
          graph.setCellStyles(mxConstants.STYLE_FONTSIZE, $("#hdCopyFont03").val().split("/")[1], new Array(priceTag));
        }else{
          graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles["03"].off, new Array(priceTag));
          graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles["03"].off, new Array(priceTag));
          graph.setCellStyles(mxConstants.STYLE_FONTSIZE, fontSizeEnvstVal, new Array(priceTag));
        }

        // 하위 셀의 사이즈 자동조정
        graph.updateCellSize(prodTag, false);
        graph.updateCellSize(priceTag, false);

        // 버튼 길이 조정
        if ( btn.geometry.width > 99 ) {
          btn.geometry.width = 99;
        }
        // 상품태그 크기조정
        var tagWidth = graph.touchKeyInfo.width - 10;
        var tagHeight = graph.touchKeyInfo.height - 10;
        prodTag.geometry.width = tagWidth;
        prodTag.geometry.height = tagHeight;

        // 금액태그 위치 조정
        var movedX = graph.touchKeyInfo.width - priceTag.geometry.width  - 5;
        var movedY = graph.touchKeyInfo.height - priceTag.geometry.height - 5;
        priceTag.geometry.x = movedX;
        priceTag.geometry.y = movedY;

      }
      finally {
        model.endUpdate();
        sidebar.initUsed();
        // 좌측 그리드 선택 Clear
        rows.isSelected = false;
      }
    }
  };
  //--dropEvent

  //드래그할 항목 생성
  var previewElt = document.createElement('div');
  previewElt.style.border = 'dashed black 1px';
  previewElt.style.width = graph.touchKeyInfo.x + 'px';
  previewElt.style.height = graph.touchKeyInfo.y + 'px';

  //DnD 처리
  var ds = mxUtils.makeDraggable(grid.cells.hostElement, graph, dropEvent, previewElt, -(graph.touchKeyInfo.x / 2), -(graph.touchKeyInfo.y / 2));
  ds.highlightDropTargets = true;

};


/**
 * 그래픽 영역 - 터치키분류, 상품영역
 */
function Graph(container, themes) {

  mxGraph.call(this, container);
  this.themes = themes || this.defaultThemes;

  var loadStylesheet = function (graph) {
    var node = (graph.themes != null) ? graph.themes[graph.defaultThemeName] :
        (!mxStyleRegistry.dynamicLoading) ? null :
            mxUtils.load(STYLE_PATH + '/touchKey.xml').getDocumentElement();

    if (node != null) {
      var dec = new mxCodec(node.ownerDocument);
      dec.decode(node, graph.getStylesheet());
    }
  };
  this.currentVertexStyle = mxUtils.clone(this.defaultVertexStyle);
  loadStylesheet(this);
}

/**
 * Graph inherits from mxGraph.
 */
mxUtils.extend(Graph, mxGraph);

Graph.prototype.defaultThemes = {};
Graph.prototype.defaultVertexStyle = {};
//키 사이 간격 두께 : px (Custom 변수)
Graph.prototype.btnBorder = 1;
//터치키 정보 (Custom 변수)
Graph.prototype.touchKeyInfo = { width: 99, height: 74, x: 100, y: 75 };
//현재 선택한 하위속성 정보 (Custom 변수)
Graph.prototype.orgChildren = { id: '', parent: new mxCell(), cell: [] };
//최대 페이지 갯수
Graph.prototype.MAX_PAGE = 5;
//한페이지에 컬럼 갯수
Graph.prototype.COL_PER_PAGE = 5;
// 페이지당 줄 수
Graph.prototype.ROW_PER_PAGE = window.MAX_CLASS_ROW;
// 텍스트 에디팅 방지
Graph.prototype.textEditing = false;
Graph.prototype.defaultThemeName = 'touchKey';
//터치키분류 영역 셀의 prefix
Graph.prototype.classPrefix = 'G';
//상품 영역 셀의 prefix
Graph.prototype.prodPrefix = 'T';
//터치키분류 영역에 index 변수
//터치키분류, 상품영역의 셀과 레이어의 아이디를 맞추기 위해 사용
Graph.prototype.nextGrpId = 1;
//현재 페이지 번호
Graph.prototype.pageNo = 1;
//스타일색상
Graph.prototype.buttonStyles = {};
Graph.prototype.fontStyles = {};
//스타일코드
Graph.prototype.styleCd = touchKeyStyleCd;
//스타일코드 콤보
Graph.prototype.selectStyle = null;
//태그구분 콤보
Graph.prototype.cellTypeCombo = null;


/**
 * 터치키분류 / 상품 영역 초기화
 */
Graph.prototype.init = function () {

  mxGraph.prototype.init.apply(this, arguments);

  var graph = this;
  //마우스를 영역 밖으로 드래그 했을 때 패닝이 되지 않도록 처리
  graph.setPanning(false);

  //Enables HTML labels > true 하는 경우 vertex는 strictHTML 형태로 삽입된다.
  //strictHTML 일때는 shape redraw 가 안먹는다.
  //로직으로 우겨넣으려면 cell.getState()의 dialect 값 조정(DIALECT_SVG) 해서 사용하여야 한다.
  graph.setHtmlLabels(true);

  //셀을 이동했을 때 스크롤 금지
  graph.graphHandler.scrollOnMove = false;
  graph.autoScroll = false;

  graph.constrainChildren = false;
  graph.extendParents = false;
  graph.extendParentsOnAdd = false;

  //터치키분류 / 상품 이동 시 처리
  //대상 셀에 이미 상품이 있을 경우 이동 금지
  var mxGraphHandlerMoveCells = mxGraphHandler.prototype.moveCells;
  graph.graphHandler.moveCells = function (cells, dx, dy, clone, target, evt) {
    // style 에서 tukeyFg 값 추출
    var style = graph.getCellStyle(cells[0]);
    if (style['tukeyFg']) {
      var tukeyFg = style['tukeyFg'].toString().leftPad("0", 2);
      // 하위 셀 ( 상품명/금액 ) 인 경우 무시
      if (tukeyFg === "02" || tukeyFg === "03") {
        mxGraphHandlerMoveCells.apply(this, arguments);
      }
    }

    var pt = this.graph.getPointForEvent(evt);
    //박스 크기에 맞게 사이즈 조정
    var newPoint = this.graph.adjustCellPoint(dx, dy);
    dx = newPoint.x;
    dy = newPoint.y;

    //vertex 이동 시 이동될 위치에 vertex가 있는 경우 이동 금지
    var checkCollision = function (bounds, dx, dy, selectedCells) {
      var startX = bounds.x + dx + 1;
      var startY = bounds.y + dy + 1;

      //이동될 위치에 있는 Object가 자신인지 체크
      //자신일 경우에는 이동 가능
      var isMyself = function (cell) {
        var isMy = false;
        for (var i = 0; i < selectedCells.length; i++) {
          if (selectedCells[i] === cell) {
            isMy = true;
            break;
          }
        }
        return isMy;
      };

      //한칸씩 이동 하면서 해당 위치에 셀이 있는지 체크
      var isColl = false;
      for (var x = 0; x < bounds.width; (x += graph.touchKeyInfo.x)) {
        for (var y = 0; y < bounds.height; (y += graph.touchKeyInfo.y)) {
          var cell = graph.getCellAt(startX + x, startY + y);
          if (cell != null && !isMyself(cell)) {
            // style 에서 tukeyFg 값 추출
            style = graph.getCellStyle(cell);
            tukeyFg = style['tukeyFg'].toString().leftPad("0", 2);
            if (tukeyFg === "01") {
              isColl = true;
              break;
            }
          }
        }
        if (isColl) {
          break;
        }
      }
      return isColl;
    };

    //이동될 위치에 셀이 있는 경우 이동 취소
    if (checkCollision(this.bounds, dx, dy, cells)) {
      mxEvent.consume(evt);
      return;
    }

    //이동될 위치의 좌표 계산
    var dstX1 = this.bounds.x + dx;
    var dstY1 = this.bounds.y + dy;
    var dstX2 = dstX1 + this.bounds.width;
    var dstY2 = dstY1 + this.bounds.height;

    //페이지 범위를 넘는 경우 이동 취소
    var maxBounds = this.graph.getMaximumGraphBounds();
    var startX = (maxBounds.width / this.graph.MAX_PAGE) * (this.graph.pageNo - 1);
    var endX = (maxBounds.width / this.graph.MAX_PAGE) * this.graph.pageNo;
    if (dstX1 < startX || dstX2 > endX) {
      mxEvent.consume(evt);
      return;
    }

    //right-bottom은 페이지 이동 객체 위치이므로 vertex를 넣을 수 없다.
    if (checkPagingArea(dstX2, dstY2)) {
      mxEvent.consume(evt);
      return;
    }

    mxGraphHandlerMoveCells.apply(this, arguments);
  };

  //페이징 객체가 있는 영역인지 체크
  var checkPagingArea = function (x, y) {
    var isPagingArea = false;
    var lastX = graph.touchKeyInfo.x * graph.COL_PER_PAGE * graph.pageNo - graph.touchKeyInfo.x;
    var lastY = graph.touchKeyInfo.y * graph.ROW_PER_PAGE - graph.touchKeyInfo.y;
    if (x > lastX && y > lastY) {
      isPagingArea = true;
    }
    return isPagingArea;
  };

  //셀의 사이즈가 변경되었을 때 배경 크기에 맞게 보정
  graph.resizeCell = function (cell, bounds, recurse) {
    // 위치 재계산
    var newPoint = this.adjustCellPoint(bounds.x, bounds.y);
    // 사이즈 재계산
    var newSize = this.adjustCellSize(bounds.width, bounds.height);
    // 셀크기 설정
    bounds = new mxRectangle(newPoint.x, newPoint.y, newSize.x, newSize.y);
    //vertex 리사이즈 시 다른 vertex를 덮는 경우 리턴
    var checkCollision = function () {
      var isColl = false, cellAt, style, tukeyFg;
      for (var x = 0; x < bounds.width; (x += graph.touchKeyInfo.x)) {
        for (var y = 0; y < bounds.height; (y += graph.touchKeyInfo.y)) {
          cellAt = graph.getCellAt(bounds.x + x, bounds.y + y);
          if (cellAt !== null && cell !== cellAt) {
            // style 에서 tukeyFg 값 추출
            style = graph.getCellStyle(cellAt);
            tukeyFg = style['tukeyFg'].toString().leftPad("0", 2);
            if (tukeyFg === "01") {
              isColl = true;
              break;
            }
          }
        }
        if (isColl) {
          break;
        }
      }
      return isColl;
    };
    if (checkCollision()) {
      return;
    }
    //right-bottom은 페이지 이동 객체 위치이므로 vertex를 넣을 수 없다.
    var dstX2 = bounds.x + bounds.width;
    var dstY2 = bounds.y + bounds.height;
    if (checkPagingArea(dstX2, dstY2)) {
      return;
    }
    // 하위속성 리사이징
    var resizeChild = function (child) {
      var style, tukeyFg, movedX, movedY;
      for (var r = 0; r < child.length; r++) {
        var cell = child[r];
        // 하위셀 크기 자동 조정
        graph.updateCellSize(cell, false);
        // style 에서 tukeyFg 값 추출
        style = graph.getCellStyle(cell);
        tukeyFg = style['tukeyFg'].toString().leftPad("0", 2);
        // 상품명 태그 조정 : 상품태그의 영역은 여백을 제외한 버튼크기
        if (tukeyFg === "02") {
          cell.geometry.x = 5;
          cell.geometry.y = 5;
          cell.geometry.width = bounds.width - 11;
          cell.geometry.height = bounds.height - 11;
          // 금액태그 위치 조정
        } else if (tukeyFg === "03") {
          movedX = bounds.width - cell.geometry.width - 5;
          movedY = bounds.height - cell.geometry.height - 5;
          cell.geometry.x = movedX;
          cell.geometry.y = movedY;
        }
      }
    };
    // 하위속성 존재시 금액표기부 Y좌표 조정
    if (cell.children) {
      resizeChild(cell.children);
    }
    mxGraph.prototype.resizeCell.apply(this, arguments);
  };

  //마우스 클릭 할 때 focus 처리
  //https://jgraph.github.io/mxgraph/docs/known-issues.html
  /*if (mxClient.IS_NS) {
    mxEvent.addListener(graph.container, 'mousedown', function () {
      if (!graph.isEditing()) {
        graph.container.setAttribute('tabindex', '-1');
        graph.container.focus();
      }
    });
  }*/

};


/**
 * 터치키의 사이즈에 따라 위치이동 보정
 */
Graph.prototype.adjustCellPoint = function (w, h) {
  var kw = this.touchKeyInfo.x;
  var kh = this.touchKeyInfo.y;

  var mw = w % kw;
  var mh = h % kh;

  // 선택된 영역 count ( 가로/세로 )
  var dw = Math.round(w / kw);
  var dh = Math.round(h / kh);

  // 터치키 크기(가로/세로) 절반을 넘으면 터치키(가로/세로) 만큼으로 크기 보정
  var dx = Math.abs(mw) <= (kw / 2) ? (kw * dw) : (kw * dw);
  var dy = Math.abs(mh) <= (kh / 2) ? (kh * dh) : (kh * dh);

  return new mxPoint(dx, dy);
};

/**
 * 터치키의 셀크기 변경 시 사이즈 보정
 */
Graph.prototype.adjustCellSize = function (w, h) {
  var kw = this.touchKeyInfo.width;
  var kh = this.touchKeyInfo.height;

  var mw = w % kw;
  var mh = h % kh;
  // 선택된 영역 count ( 가로/세로 )
  var dw = Math.round(w / kw) <= 0 ? 1 : Math.round(w / kw);
  var dh = Math.round(h / kh) <= 0 ? 1 : Math.round(h / kh);
  // 터치키 크기(가로/세로) 절반을 넘으면 터치키(가로/세로) 만큼으로 크기 보정
  // 터치키 사이의 1px 간격 유지를 위해 해당 사이즈만큼 추가 계산 (btnBorder)
  var dx = Math.abs(mw) <= (kw / 2) ? (kw * dw) : (kw * dw);
  var dy = Math.abs(mh) <= (kh / 2) ? (kh * dh) : (kh * dh);
  dx += ( dw - 1 ) * this.btnBorder;
  dy += ( dh - 1 ) * this.btnBorder;

  return new mxPoint(dx, dy);
};


/**
 * Sanitizes the given HTML markup.
 */
Graph.prototype.sanitizeHtml = function (value, editing) {
  // Uses https://code.google.com/p/google-caja/wiki/JsHtmlSanitizer
  // NOTE: Original minimized sanitizer was modified to support
  // data URIs for images, and mailto and special data:-links.
  // LATER: Add MathML to whitelisted tags
  function urlX(link) {
    if (link != null && link.toString().toLowerCase().substring(0, 11) !== 'javascript:') {
      return link;
    }
    return null;
  }

  function idX(id) {
    return id
  }
  return html_sanitize(value, urlX, idX);
};


/**
 * 터치키분류 변경 시 상품영역 스크롤 초기화
 */
Graph.prototype.initProdPaging = function () {
  var graph = this;
  //좌우 이동 버튼 위치 초기화
  graph.container.parentElement.scrollLeft = 0;
  graph.pageNo = 1;
  var pageText = "PAGE : " + graph.pageNo;
  if (graph.isClassArea) {
    document.getElementById('classPageNoText').textContent = pageText;
  } else {
    document.getElementById('prodPageNoText').textContent = pageText;
  }
};

/**
 * 엘리먼트 선택 표시
 */
Graph.prototype.setSelected = function (elt, name, selected) {

  if (selected) {
    var arr = elt.className.split(' ');
    if (arr.indexOf('on') === -1) {
      elt.className += ' ' + name;
    }
  }
  else {
    elt.className = elt.className.replace(new RegExp(name, 'g'), '').trim();
  }
};

/**
 * 셀 이동 금지
 * cell 추가시 style 에 movable=0; 추가하여 사용
 */
Graph.prototype.isCellMovable = function(cell) {
  var state = this.view.getState(cell);
  var style = (state != null) ? state.style : this.getCellStyle(cell);

  return this.isCellsMovable() && !this.isCellLocked(cell) && style['movable'] !== 0;
};

/**
 * 셀 선택표시 감추기
 * cell 추가시 style 에 selectable=0; 추가하여 사용
 */
Graph.prototype.isCellSelectable = function(cell) {
  var state = this.view.getState(cell);
  var style = (state != null) ? state.style : this.getCellStyle(cell);

  return this.isCellsSelectable() && !this.isCellLocked(cell) && style['selectable'] !== 0;
};

/**
 * 초기 데이터 조회 시 변수 초기화
 */
Graph.prototype.initValue = function (rowPerPage) {
  var graph = this;

  //새로운 셀 생성을 위한 다음 index 값 초기값 계산
  //첫번째 상품 선택을 위한
  var model = graph.getModel();
  var parent = graph.getDefaultParent();

  //로드 후 생성되는 셀의 인덱스 초기화
  var childCount = model.getChildCount(parent);
  graph.nextGrpId = (childCount + 1);

  //xml에 설정했던 페이지당 줄 수 적용
  graph.ROW_PER_PAGE = parseInt(rowPerPage);
  // graph 영역 크기 설정
  var graphSizeX = graph.touchKeyInfo.width * graph.COL_PER_PAGE;
  var graphSizeY = graph.touchKeyInfo.height * graph.ROW_PER_PAGE;
  // 버튼사이의 간격 1px 적용
  graphSizeX += (graph.COL_PER_PAGE - 1) * this.btnBorder;
  if (graph.ROW_PER_PAGE === 1) {
    graphSizeY += 1 * this.btnBorder;
  } else {
    graphSizeY += (graph.ROW_PER_PAGE - 1) * this.btnBorder;
  }
  // x축 길이 설정
  graphSizeX = 2499;

  // graph 에 길이 설정
  graph.minimumGraphSize = new mxRectangle(0, 0, graphSizeX, graphSizeY);
  graph.maximumGraphBounds = new mxRectangle(0, 0, graphSizeX, graphSizeY);

  // 객체의 class 제거
  var removeClass = function (elt, name) {
    elt.className = elt.className.replace(new RegExp(name, 'g'), '').trim();
  };
  // 객체에 class 추가
  var addClass = function (elt, name) {
    elt.className += ' ' + name;
  };
  //터치키분류 영역이 1줄 or 2줄 or 3줄 인지에 따라 영역 크기 지정
  if (graph.isClassArea) {
    var classWrap = document.getElementById('classWrap');
    var classArea = document.getElementById('classArea');
    var classNavWrap = document.getElementById('divClassNavWrap');
    removeClass(classWrap, 'hClassLine1');
    removeClass(classWrap, 'hClassLine2');
    removeClass(classWrap, 'hClassLine3');
    removeClass(classNavWrap, 'hClassLine1');
    removeClass(classNavWrap, 'hClassLine2');
    removeClass(classNavWrap, 'hClassLine3');
    removeClass(classArea, 'touchClassLine1');
    removeClass(classArea, 'touchClassLine2');
    removeClass(classArea, 'touchClassLine3');
    if (parseInt(rowPerPage) === 1) {
      addClass(classWrap, 'hClassLine1');
      addClass(classNavWrap, 'hClassLine1');
      addClass(classArea, 'touchClassLine1');
    } else if (parseInt(rowPerPage) === 2) {
      addClass(classWrap, 'hClassLine2');
      addClass(classNavWrap, 'hClassLine2');
      addClass(classArea, 'touchClassLine2');
    } else if (parseInt(rowPerPage) === 3) {
      addClass(classWrap, 'hClassLine3');
      addClass(classNavWrap, 'hClassLine3');
      addClass(classArea, 'touchClassLine3');
    }
    // 페이지번호 표시
    document.getElementById('classPageNoText').textContent = "PAGE : " + graph.pageNo;
  } else {
    var prodWrap = document.getElementById('prodWrap');
    var prodArea = document.getElementById('prodArea');
    var prodNavWrap = document.getElementById('divProdNavWrap');
    removeClass(prodWrap, 'hProdsLine5');
    removeClass(prodWrap, 'hProdsLine6');
    removeClass(prodWrap, 'hProdsLine7');
    removeClass(prodNavWrap, 'hProdsLine5');
    removeClass(prodNavWrap, 'hProdsLine6');
    removeClass(prodNavWrap, 'hProdsLine7');
    removeClass(prodArea, 'touchProdsLine5');
    removeClass(prodArea, 'touchProdsLine6');
    removeClass(prodArea, 'touchProdsLine7');
    if (parseInt(rowPerPage) === 5) {
      addClass(prodWrap, 'hProdsLine5');
      addClass(prodNavWrap, 'hProdsLine5');
      addClass(prodArea, 'touchProdsLine5');
    } else if (parseInt(rowPerPage) === 6) {
      addClass(prodWrap, 'hProdsLine6');
      addClass(prodNavWrap, 'hProdsLine6');
      addClass(prodArea, 'touchProdsLine6');
    } else if (parseInt(rowPerPage) === 7) {
      addClass(prodWrap, 'hProdsLine7');
      addClass(prodNavWrap, 'hProdsLine7');
      addClass(prodArea, 'touchProdsLine7');
    }
    // 페이지번호 표시
    document.getElementById('prodPageNoText').textContent = "PAGE : " + graph.pageNo;
  }

  //좌우 이동 버튼 위치 초기화
  graph.container.scrollLeft = 0;
  graph.pageNo = 1;

  graph.refresh();
};

/**
 * UNDO/REDO 이벤트 생성 - 상품영역만 적용
 * - 터치키분류 생성 시 여러 동작을 하여 불필요한 history가 생성되었음
 */
Graph.prototype.createUndoManager = function (graph) {

  var undoManager = new mxUndoManager();
  var listener = function (sender, evt) {
    undoManager.undoableEditHappened(evt.getProperty('edit'));
  };
  graph.getModel().addListener(mxEvent.UNDO, listener);
  graph.getView().addListener(mxEvent.UNDO, listener);

  return undoManager;
};

/**
 * 키보드 이벤트 생성
 * - 키보드 이벤트를 각각 graph에 적용하는 것이 기능 상 불가능 한것으로 확인.
 */
Graph.prototype.createKeyHandler = function (graph) {
  var keyHandler = new mxKeyHandler(graph);
  // Delete
  keyHandler.bindKey(46, function (evt) {
    // 최초클릭으로 item 선택시에는 delete 가 바로 되지 않는 현상으로 주석처리
//    graph.escape();
    var cells = graph.getDeletableCells(graph.getSelectionCells());
    // 선택된 셀이 있는경우에만.
    if (cells != null && cells.length > 0) {
      var style, tukeyFg, parents, select = [];
      for (var c = 0; c < cells.length; c++) {
        // style 에서 tukeyFg 값 추출
        style = graph.getCellStyle(cells[c]);
        tukeyFg = style['tukeyFg'].toString().leftPad("0", 2);
        // 하위 셀 ( 상품명/금액 ) 인 경우 무시
        if (tukeyFg !== "02" && tukeyFg !== "03") {
          parents = graph.model.getParents(cells);
          graph.removeCells(cells);
          // Selects parents for easier editing of classes
          if (parents != null) {
            select = [];
            for (var i = 0; i < parents.length; i++) {
              if (graph.model.contains(parents[i]) && graph.model.isVertex(parents[i])) {
                select.push(parents[i]);
              }
            }
            graph.setSelectionCells(select);
          }
        }
      }
    }
  });

  //TODO 터치키분류에서 상품레이어 처리 시 2개의 트랜잭션 처리되는 문제. 일단 주석 처리
  //Ctrl + z
  keyHandler.bindControlKey(90, function (evt) {
    graph.undoManager.undo()
  });
  //Ctrl + Shift + z
  keyHandler.bindControlShiftKey(90, function (evt) {
    graph.undoManager.redo()
  });

  return keyHandler;
};

/**
 * 그리드 사이즈 만큼 이동하면서 셀의 위치 찾기
 */
Graph.prototype.findPosition = function (pt) {
  var graph = this;
  var cntFind = 0;
  var cx = (graph.touchKeyInfo.x * graph.COL_PER_PAGE * graph.pageNo);
  var cy = (graph.touchKeyInfo.y * graph.ROW_PER_PAGE);
  var px = parseInt(pt.x / graph.touchKeyInfo.x) * graph.touchKeyInfo.x;
  var py = parseInt(pt.y / graph.touchKeyInfo.y) * graph.touchKeyInfo.y;
  var lastX = cx - graph.touchKeyInfo.x;
  var lastY = cy - graph.touchKeyInfo.y;

  for (var posY = py; posY < cy; posY += graph.touchKeyInfo.y) {
    for (var posX = px; posX < cx; posX += graph.touchKeyInfo.x) {
      //마지막 셀은 페이지 이동을 위해 미사용 처리 필요
      if (posX >= lastX && posY >= lastY) {
        //TODO 다음 페이지 이동하여 첫번째 셀에 위치하도록 수정
        return null;
      }
      if (graph.getCellAt((posX + 1), (posY + 1)) == null) {
        return new mxPoint(posX, posY);
      }
      //긴급탈출(만약의 경우..)
      if (cntFind++ > 30) return null;
    }
    //width 끝까지 갔을 때 다음 줄은 0부터 시작
    px = 0;
  }
};

/**
 * 그리드 색상스타일 값 초기화
 */
Graph.prototype.initStyle = function() {
  // 선택된 스타일
  var styleCd = this.selectStyle.selectedValue;
  for (var i = 0; i < touchKeyStyles.length; i++) {
    for (var key in touchKeyStyles[i]) {
      if (key === "styleCd" && styleCd === touchKeyStyles[i][key]) {
        var buttonStyles = {};
        var fontStyles = {};
        if (this.isClassArea && touchKeyStyles[i].buttonFg === 'G') {
          buttonStyles.on = touchKeyStyles[i].buttonOnColor;
          buttonStyles.off = touchKeyStyles[i].buttonOffColor;
          fontStyles.on = touchKeyStyles[i].fontOnColor;
          fontStyles.off = touchKeyStyles[i].fontOffColor;
          fontStyles.size = touchKeyStyles[i].fontSize;
          // 개별 영역의 변수에 할당
          this.buttonStyles = buttonStyles;
          this.fontStyles = fontStyles;
        } else {
          var buttonTagFg = touchKeyStyles[i].buttonTagFg;
          buttonStyles.on = touchKeyStyles[i].buttonOnColor;
          buttonStyles.off = touchKeyStyles[i].buttonOffColor;
          fontStyles.on = touchKeyStyles[i].fontOnColor;
          fontStyles.off = touchKeyStyles[i].fontOffColor;
          fontStyles.size = touchKeyStyles[i].fontSize;
          // 개별 영역의 변수에 할당
          this.buttonStyles[buttonTagFg] = buttonStyles;
          this.fontStyles[buttonTagFg] = fontStyles;
        }
      }
    }
  }
};

/**
 * 터치키분류영역 셀 삭제 전 매장수정허용분류 체크
 * @param format
 */
function deleteClassChk(format) {
  var graph = format.graph;
  var cells = graph.getSelectionCells();
  // 터치키분류영역에 버튼없을때 삭제버튼을 누르는 경우 오류 방지
  if (cells.length > 0) {
    var classCd = cells[0].style.substr(8,5);

    var params = {};
    var scope = agrid.getScope("touchKeyCtrl");
    params.tukeyGrpCd = scope.touchKeyGrp;
    params.tukeyClassCd = classCd;

    // 가상로그인 대응
    if (document.getElementsByName('sessionId')[0]) {
      params.sid = document.getElementsByName('sessionId')[0].value;
    }

    $.postJSON("/base/prod/touchKey/touchKey/getDeleteClassChk.sb", params, function (result) {
      // 228 N or null
      if(orgnFg === "HQ"){
        deleteClassCell(format);
      } else if(orgnFg === "STORE") {
        scope._popMsg("분류는 삭제 할 수 없습니다.");
        return false;
      }
    }, function (){
      // 228에 Y
      if(orgnFg === "HQ") {
        scope._popMsg("[매장수정허용분류] 에 등록된 터치키분류는 삭제 할 수 없습니다.");
        return false;
      } else if(orgnFg === "STORE") {
        scope._popMsg("분류는 삭제 할 수 없습니다.");
        return false;
      }
    });
  }
}

/**
 * 터치키분류영역 셀 삭제
 * @param format
 */
function deleteClassCell(format) {
  var graph = format.graph;
  var cells = graph.getSelectionCells();
  var gModel = graph.getModel();
  // 터치키분류영역에 버튼없을때 삭제버튼을 누르는 경우 오류 방지
  if (cells.length > 0) {
    // 상품영역
    var prodArea = format.touchkey.prodArea;
    var pModel = prodArea.getModel();
    // 상품영역 셀 삭제
    prodArea.removeCells([pModel.getCell(cells[0].id)]);
    // 터치키분류영역 셀 삭제
    graph.removeCells([cells[0]]);
    var parent = graph.getDefaultParent();
    // 삭제후 터치키분류영역의 첫번째 셀 선택
    var firstCell = gModel.getChildAt(parent, 0);
    // 첫번째 셀 존재시에만 선택
    if ( firstCell != null ) {
      // 해당 셀의 위치로 스크롤링효과주기
      document.getElementById('classWrap').scrollLeft = 0;
      // 페이지번호 계산
      var scrollWidth = graph.touchKeyInfo.x * graph.COL_PER_PAGE;
      var intx = firstCell.geometry.x*1+1;
      var pageNo = Math.ceil(intx/scrollWidth);
      // 페이지번호 설정
      graph.pageNo = pageNo;
      // 스크롤
      document.getElementById('classWrap').scrollLeft = scrollWidth * ( pageNo - 1 );
      document.getElementById('classPageNoText').textContent = "PAGE : " + pageNo;
      // 분류영역의 첫번째 셀 선택
      graph.selectCellForEvent(firstCell);
      var layer = prodArea.model.getCell(firstCell.getId());
      // 상품영역 레이어 변경
      prodArea.switchLayer(layer);
    }
    // 셀 속성지정 감추기
    document.getElementById('keyStyle').classList.add("hideNav");
    document.getElementById('keyStyleAd').classList.add("hideNav");
  }
}

/**
 * 상품영역 셀 삭제
 * @param cell
 */
function deleteProdCell(format) {
  var graph = format.touchkey.prodArea;
  var dCells = graph.getDeletableCells(graph.getSelectionCells());
  // 선택된 셀이 있는 경우에만...
  if (dCells != null && dCells.length > 0) {
    for (var c = 0; c < dCells.length; c++) {
      // style 에서 tukeyFg 값 추출 : 하위셀인지 판단
      var style = graph.getCellStyle(dCells[c]);
      var tukeyFg = style['tukeyFg'].toString().leftPad("0", 2);
      // 하위 셀 ( 상품명/금액 ) 인 경우 무시
      if (tukeyFg !== "02" && tukeyFg !== "03") {
        var parents = graph.model.getParents(dCells);
        graph.removeCells(dCells);
        graph.getSelectionModel().clear();
        // Selects parents for easier editing of classes
        if (parents != null) {
          var select = [];
          for (var i = 0; i < parents.length; i++) {
            if (graph.model.contains(parents[i]) && graph.model.isVertex(parents[i])) {
              select.push(parents[i]);
            }
          }
          if (select.length > 0) {
            graph.setSelectionCells(select);
            document.getElementById('keyStyle').classList.remove("hideNav");
            document.getElementById('keyStyleAd').classList.remove("hideNav");
            document.getElementById('colorStyleWrap').classList.remove("hideNav");
          } else {
            document.getElementById('keyStyle').classList.add("hideNav");
            document.getElementById('keyStyleAd').classList.add("hideNav");
          }
        }
      }
    }
  }
}

/**
 * 오른쪽 설정 기능 패널
 */
function Format(touchkey) {

  this.touchkey = touchkey;
  this.container = document.getElementById('format');
  this.graph = touchkey.classArea;
  this.scope = agrid.getScope("touchKeyCtrl");

  this.init();

}

/**
 * 기능패널의 변수 선언
 */
Format.prototype.cellTypeCombo = null;
Format.prototype.fontColor = null;
Format.prototype.fontSize = null;
Format.prototype.fillColor = null;

/**
 * 기능 패널 초기화
 */
Format.prototype.init = function () {

  //터치키분류, 상품 선택 변경 시 설정 패널 초기화
  this.update = mxUtils.bind(this, function (graph) {
    this.graph = graph;
    this.refresh();
  });

  //모든 구성 요소 생성
  this.initElements();
};


/**
 * 화면 새로 그리기
 */
Format.prototype.refresh = function () {

  var graph = this.graph;
  //선택된 셀이 있을 때만 활성화 되는 부분
  var cells = graph.getSelectionCells();
  if (cells.length > 0) {
    //설정 값 초기화
    this.setElementsValue();
  }
};

/**
 * 폰트/정렬 설정 초기화
 */
Format.prototype.initElements = function () {

  var graph = this.graph;
  var format = this;

  // 상품목록 조회 버튼
  // addClickHandler(document.getElementById('btnSearch'), function () {
  //   //format.open(false);
  //   var scope = agrid.getScope("touchKeyCtrl");
  //   scope._broadcast('touchKeyCtrl');
  // });

  // 추가터치키생성 버튼
  addClickHandler(document.getElementById('btnNewGrp'), function () {

    var scope = agrid.getScope("touchKeyCtrl");
    var classArea = format.touchkey.classArea;
    var prodArea = format.touchkey.prodArea;

    scope.$apply(function(){
      scope._popConfirm("신규그룹으로 터치키를 생성하시겠습니까?", function() {

        // 터치키 저장 시 새 그룹으로 생성해 저장하겠다는 Flag
        $("#hdNewGrp").val("Y");

        // 터치키 그룹코드와 저장 버튼 막기
        $("#touchKeyGrpCombo").attr("disabled", true);
        $("#touchKeyGrpCombo").css('background-color', '#F0F0F0');

        format.selectStyle.selectedValue = '01';
        format.setBtnStyle();

        // 아무것도 없는 빈 XML 터치키 셋팅
        format.setGraphXml(classArea, null);
        format.setGraphXml(prodArea, null);
        scope._broadcast('touchKeyCtrl');

      });
    });
  });

  // 초기화 버튼
  addClickHandler(document.getElementById('btnInti'), function () {

    var sid = "";

    var scope = agrid.getScope("touchKeyCtrl");
    var classArea = format.touchkey.classArea;
    var prodArea = format.touchkey.prodArea;

    if(document.getElementsByName('sessionId')[0]){
      sid = document.getElementsByName('sessionId')[0].value;
    }

    scope.$apply(function(){
      scope._popConfirm("터치키를 초기화 하시겠습니까?", function() {

        // 터치키 저장 시 새 그룹으로 생성해 저장하겠다는 Flag
        $("#hdNewGrp").val("N");

        format.selectStyle.selectedValue = '01';
        format.setBtnStyle();

        // 아무것도 없는 빈 XML 터치키 셋팅
        // format.setGraphXml(classArea, null);
        // format.setGraphXml(prodArea, null);
        // scope._broadcast('touchKeyCtrl');

        $.ajax({
          type: "POST",
          url: "/base/prod/touchKey/touchKey/deleteTouchKey.sb?sid=" + sid,
          data: params,
          dataType: "json",
          processData: false,
          contentType: false,
          cache: false,
          success: function(result){
            if (result.status === "OK") {
              alert("초기화 되었습니다.");

              location.reload(true);
            } else {
              alert("초기화하는데 실패하였습니다.");
            }
          }
        });

      });
    });
  });

  // 조회버튼
  addClickHandler(document.getElementById('btnSrchTouchKey'), function () {

    var scope = agrid.getScope("touchKeyCtrl");
    var classArea = format.touchkey.classArea;
    var prodArea = format.touchkey.prodArea;

    var params = {};
    if (document.getElementsByName('sessionId')[0]) {
      params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // 터치키그룹 추가
    params['tukeyGrpCd'] = scope.touchKeyGrp;

    // 스타일 코드 조회
    $.ajax({
      type: 'POST',
      async: false,
      cache: false,
      dataType: 'json',
      url: '/base/prod/touchKey/touchKey/getTouchKeyStyleCd.sb',
      data: params,
      success: function(data){
        touchKeyStyleCd = (data['data']).replaceAll('\"','');
        format.selectStyle.selectedValue = touchKeyStyleCd;
        format.setBtnStyle();
      }
    });

    // 터치키 XML과 사용중인 상품 조회
    $.ajax({
      type: 'POST',
      async: false,
      cache: false,
      dataType: 'json',
      url:  TOUCHKEY_OPEN_URL,
      data: params,
      success: function(data){

        if (data.status === 'OK') {
          try {
            //var jsonStr = JSON.parse(data);
            var xmlStr = data.data;

            if (xmlStr != null) {
              var xmlArr = xmlStr.split("|");

              //터치키분류 영역 추가
              var classXml = mxUtils.parseXml(xmlArr[0]);
              format.setGraphXml(classArea, classXml.documentElement);

              //상품 영역 추가
              var prodXml = mxUtils.parseXml(xmlArr[1]);
              format.setGraphXml(prodArea, prodXml.documentElement);

              var model = classArea.getModel();
              var parent = classArea.getDefaultParent();

              // 터치키분류 영역에서 첫번째(무엇이될지는모름) 셀을 선택하고 상품영역에서도 해당 레이어 활성화
              var firstCell = model.getChildAt(parent, 0);
              // 셀이 존재하는 경우에만 선택 후 이동처리
              if ( firstCell != null ) {
                classArea.selectCellForEvent(firstCell);
                var layer = prodArea.model.getCell(firstCell.getId());
                // 상품영역 레이어 변경
                prodArea.switchLayer(layer);
              }

              // 터치키분류 영역 스크롤 초기화
              document.getElementById('classWrap').scrollLeft = 0;
              // 터치키분류 영역 페이지번호 초기화
              document.getElementById('classPageNoText').textContent = "PAGE : 1";

            } else {
              // xml이 없는경우 초기화
              format.setGraphXml(classArea, null);
              format.setGraphXml(prodArea, null);
            }
            scope._broadcast('touchKeyCtrl');

          }
          catch (e) {
            scope.$apply(function(){
              alert(e.message);
              scope.$broadcast('loadingPopupInactive');
              scope._popMsg(mxResources.get('errorOpeningFile'));
            });
          }
        }
        else {
          scope.$apply(function(){
            alert(data.status);
            scope.$broadcast('loadingPopupInactive');
            scope._popMsg(mxResources.get('errorOpeningFile'));
          });
        }
      }
    });

    // 터치키 그룹코드와 새 그룹으로 저장 버튼 보이기
    $("#touchKeyGrpCombo").attr("disabled", false);
    $("#touchKeyGrpCombo").css('background-color', '#FFFFFF');

    // 터치키 저장 기본 수정 Flag로 셋팅
    $("#hdNewGrp").val("N");

  });

  // 스타일적용 버튼
  addClickHandler(document.getElementById('btnApplyStyle'), function () {

    var classArea = format.touchkey.classArea;
    var prodArea = format.touchkey.prodArea;

    var item = format.selectStyle.selectedItem;
    var styleNm = item.styleNm;

    var cell = classArea.getSelectionCells()[0];
    var scope = agrid.getScope("touchKeyCtrl");
    if (cell) {
      scope.$apply(function(){
        scope._popConfirm("선택하신 분류키 [ " + cell.value + " ] 의 스타일을<br>[ " + styleNm + " ] (으)로 변경하시겠습니까?<br><br>스타일은 선택된 분류의 모든 하위터치키에 적용됩니다.", function() {
          // 버튼 색상 스타일 적용
          format.setBtnStyle();
          // 색상 스타일 적용 : 터치키분류/상품 영역
          format.setGraphStyle(prodArea, true);
          format.setGraphStyle(classArea, true);
        });
      });
    } else {
      scope.$apply(function(){
        scope._popMsg('스타일을 적용할 분류키를 선택해주세요.');
      });
    }

  });

  // 저장 버튼
  addClickHandler(document.getElementById('btnSave'), function () {

    var scope = agrid.getScope("touchKeyCtrl");
    scope.$apply(function(){
      scope._popConfirm("해당 그룹의 터치키를 저장하시겠습니까?", function() {

        // 저장
        format.save(format.touchkey.classArea, format.touchkey.prodArea);
      });
    });
  });

  // 버튼초기화 버튼
  addClickHandler(document.getElementById('btnReset'), function (s) {
    var item = format.selectStyle.selectedItem;
    var styleNm = item.styleNm;

    var scope = agrid.getScope("touchKeyCtrl");
    scope.$apply(function(){
      scope._popConfirm("버튼 초기화시 현재 선택되어있는 [ "+ styleNm +" ]<br>(으)로 초기화 됩니다. 계속하시겠습니까?", function() {
        // 선택된 스타일로 초기화
        format.setGraphStyle(format.graph, false);
        // 그래프 새로고침 하여 변경내용 화면에 적용
        format.graph.refresh();
      });
    });
  });

  // 버튼삭제 버튼
  addClickHandler(document.getElementById('btnDelete'), function () {
    var scope = agrid.getScope("touchKeyCtrl");
    scope.$apply(function(){
      if (format.graph.isClassArea) {
        scope._popConfirm("해당 분류키를 삭제하시겠습니까?<br>분류키에 포함된 하위 모든 터치키가 삭제됩니다.", function() {
          if(orgnFg === "HQ" || (orgnFg === "STORE" && touchKeyEnvstVal2 === "2")){
            deleteClassChk(format);
          } else {
            deleteClassCell(format);
          }
        });
      } else {
        scope._popConfirm("해당 터치키를 삭제하시겠습니까?", function() {
          deleteProdCell(format);
        });
      }
    });
  });

  // 터치키스타일 복사 버튼
  addClickHandler(document.getElementById('btnTouchKeyStyleCopy'), function () {
    var prodArea = format.touchkey.prodArea;
    var cell = prodArea.getSelectionCells()[0];
    if (cell) {
      var initFillColor; // 채우기
      var style02, initFontColor02, initFontSize02; // 폰트(터치키) - 상품명 사이즈와 컬러
      var style03, initFontColor03, initFontSize03; // 폰트(터치키) - 금액 사이즈와 컬러

      style02 = graph.getCellStyle(cell.children[0]);
      initFillColor = style02['fillColor'];
      initFontColor02 = style02['fontColor'];
      initFontSize02 = style02['fontSize'];

      style03 = graph.getCellStyle(cell.children[1]);
      initFontColor03 = style03['fontColor'];
      initFontSize03 = style03['fontSize'];

      // 복사한 터치키스타일 보여주고 hidden에 Setting
      document.getElementById("pickCopyFillColor").value = initFillColor;
      document.getElementById("pickCopyFont02").value = initFontColor02;
      document.getElementById("pickCopyFont03").value = initFontColor03;
      $("#lblCopyFont02").text(" " + initFontSize02 + "pt");
      $("#lblCopyFont03").text(" " + initFontSize03 + "pt");

      $("#hdCopyFillColor").val(initFillColor);
      $("#hdCopyFont02").val(initFontColor02 + "/" + initFontSize02);
      $("#hdCopyFont03").val(initFontColor03 + "/" + initFontSize03);

      $("#divTouchKeyStyleCopy").css("display", "");
    }
  });

  // 터치키스타일 적용 버튼
  addClickHandler(document.getElementById('btnTouchKeyStyleApply'), function () {

    // 적용 버튼 toggle
    if($("#hdTouchKeyStyleApply").val() === "N"){
      $("#hdTouchKeyStyleApply").val("Y"); // 적용 버튼 활성화
      $("#btnTouchKeyStyleApply").css('background-color', '#1e88e5');
      $("#btnTouchKeyStyleApply").css('color', '#ffffff');
    }else{
      $("#hdTouchKeyStyleApply").val("N"); // 적용 버튼 비활성화
      $("#btnTouchKeyStyleApply").css('background-color', '#ebf5ff');
      $("#btnTouchKeyStyleApply").css('color', '#337dde');
    }
  });

  // 버튼구분 콤보 DropBoxDataMap
  var cellTypeComboData = [
    {"name":"상품명","value":"02"},
    {"name":"금액","value":"03"}
  ];

  /**
   * 태그구분 콤보
   */
  this.cellTypeCombo = new wijmo.input.ComboBox('#cellTypeCombo', {
    itemsSource: cellTypeComboData,
    displayMemberPath: 'name',
    selectedValuePath: 'value',
    isEditable: false,
    selectedValue: "",
    selectedIndexChanged: function (s, e) {
      var prodArea = format.touchkey.prodArea;
      var cell = prodArea.getSelectionCells()[0];
      if (cell) {
        var style, initFontSize, initFontColor, initFillColor;
        if ( s.selectedValue === "02" ) {
          style = graph.getCellStyle(cell.children[0]);
          initFontSize = style['fontSize'];
          initFontColor = style['fontColor'];
          initFillColor = style['fillColor'];
        } else if ( s.selectedValue === "03" ) {
          style = graph.getCellStyle(cell.children[1]);
          initFontSize = style['fontSize'];
          initFontColor = style['fontColor'];
          initFillColor = style['fillColor'];
        }
        format.fontSize.value = initFontSize;
        format.fontColor.value = initFontColor;
        format.fillColor.value = initFillColor;
      }
    }
  });

  /**
   * 셀 채우기 색상 설정 시작
   */
  this.fillColor = new wijmo.input.InputColor('#fillColor', {
    placeholder: 'Select the color',
    value: '#000000',
    valueChanged: function (s, e) {
      // cell 영역 선택시에만
      if (s.graph) {
        if(s.value.length > 7) {
          var rgb = s.value;
          rgb = rgb.match(/^rgba?[\s+]?\([\s+]?(\d+)[\s+]?,[\s+]?(\d+)[\s+]?,[\s+]?(\d+)[\s+]?/i);
          s.value = (rgb && rgb.length === 4) ? "#" +
              ("0" + parseInt(rgb[1],10).toString(16)).slice(-2) +
              ("0" + parseInt(rgb[2],10).toString(16)).slice(-2) +
              ("0" + parseInt(rgb[3],10).toString(16)).slice(-2) : '';
        }
        var cell = s.graph.getSelectionCells()[0];
        // 하위속성 존재시 하위속성 색상도 같이 변경
        if (cell.children) {
          s.graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, s.value, cell.children);
        }
        s.graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, s.value, null);
      }
    }
  });

  /**
   * 폰트 색상 설정 시작
   */
  this.fontColor = new wijmo.input.InputColor('#fontColor', {
    placeholder: 'Select the color',
    value: '#000000',
    valueChanged: function (s, e) {
      // cell 영역 선택시에만
      if (s.graph) {
        if(s.value.length > 7) {
          var rgb = s.value;
          rgb = rgb.match(/^rgba?[\s+]?\([\s+]?(\d+)[\s+]?,[\s+]?(\d+)[\s+]?,[\s+]?(\d+)[\s+]?/i);
          s.value = (rgb && rgb.length === 4) ? "#" +
              ("0" + parseInt(rgb[1],10).toString(16)).slice(-2) +
              ("0" + parseInt(rgb[2],10).toString(16)).slice(-2) +
              ("0" + parseInt(rgb[3],10).toString(16)).slice(-2) : '';
        }
        var cellType = graph.cellTypeCombo.selectedValue;
        var cell = s.graph.getSelectionCells()[0];
        if (cell.children) {
          if ( cellType === "02" ) {
            s.graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, s.value, new Array(cell.children[0]));
          } else if ( cellType === "03" ) {
            s.graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, s.value, new Array(cell.children[1]));
          }
        }
        s.graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, s.value, null);
      }
    }
  });

  /**
   * 폰트 크기 설정 시작
   */
  this.fontSize = new wijmo.input.InputNumber('#fontSize', {
    format: 'n0',
    step: 1,
    min: 8,
    max: 20,
    value: 10,
    valueChanged: function (s, e) {
      // cell 영역 선택시에만
      if (s.graph) {
        var cellType = graph.cellTypeCombo.selectedValue;
        var cell = s.graph.getSelectionCells()[0];
        if (cell.children) {
          if ( cellType === "02" ) {
            s.graph.setCellStyles(mxConstants.STYLE_FONTSIZE, s.value, new Array(cell.children[0]));
          } else if ( cellType === "03" ) {
            s.graph.setCellStyles(mxConstants.STYLE_FONTSIZE, s.value, new Array(cell.children[1]));
          }
        }
        s.graph.setCellStyles(mxConstants.STYLE_FONTSIZE, s.value, null);
      }
    }
  });

  // 터치키 스타일코드 콤보박스 생성
  this.selectStyle = new wijmo.input.ComboBox('#selectStyle', {
    displayMemberPath: 'styleNm',
    selectedValuePath: 'styleCd',
    itemsSource: touchKeyStyleCdList,
    isEditable: false,
    selectedValue: touchKeyStyleCd,
    selectedIndexChanged: function(s, e) {
      var classArea = format.touchkey.classArea;
      var prodArea = format.touchkey.prodArea;
      var styleCd = s.selectedValue;
      viewStyleCd = s.selectedValue;

      for (var i = 0; i < touchKeyStyles.length; i++) {
        for (var key in touchKeyStyles[i]) {
          if (key === "styleCd" && styleCd === touchKeyStyles[i][key]) {
            var buttonStyles = {};
            var fontStyles = {};
            if (touchKeyStyles[i].buttonFg === 'G') {
              buttonStyles.on = touchKeyStyles[i].buttonOnColor;
              buttonStyles.off = touchKeyStyles[i].buttonOffColor;
              fontStyles.on = touchKeyStyles[i].fontOnColor;
              fontStyles.off = touchKeyStyles[i].fontOffColor;
              fontStyles.size = touchKeyStyles[i].fontSize;
              // 개별 영역의 변수에 할당
              classArea.buttonStyles = buttonStyles;
              classArea.fontStyles = fontStyles;
            } else {
              var buttonTagFg = touchKeyStyles[i].buttonTagFg;
              buttonStyles.on = touchKeyStyles[i].buttonOnColor;
              buttonStyles.off = touchKeyStyles[i].buttonOffColor;
              fontStyles.on = touchKeyStyles[i].fontOnColor;
              fontStyles.off = touchKeyStyles[i].fontOffColor;
              fontStyles.size = touchKeyStyles[i].fontSize;
              // 개별 영역의 변수에 할당
              prodArea.buttonStyles[buttonTagFg] = buttonStyles;
              prodArea.fontStyles[buttonTagFg] = fontStyles;
            }
          }
        }
      }
    }
  });

  // 초기 버튼 색상 스타일 적용
  format.setBtnStyle();
  // 그래프에서 접근하도록 설정
  format.touchkey.classArea.selectStyle = this.selectStyle;
  format.touchkey.prodArea.selectStyle = this.selectStyle;
  format.touchkey.classArea.cellTypeCombo = this.cellTypeCombo;
  format.touchkey.prodArea.cellTypeCombo = this.cellTypeCombo;

};

/**
 * 페이지 버튼 스타일 테마 적용
 */
Format.prototype.setBtnStyle = function() {

  // 현재 선택된 스타일코드
  var styleCd = this.selectStyle.selectedValue;
  var path = "/resource/solbipos/css/img/touchKey/";
  var ext = ".png";
  var styleBtnFile = styleCd + ext;

  var grpNavPrev = document.getElementById("grpNavPrev");
  grpNavPrev.style.backgroundImage = "url('" + path + "/touchKey_class_arrL_color" + styleBtnFile + "')";
  var grpNavNext = document.getElementById("grpNavNext");
  grpNavNext.style.backgroundImage = "url('" + path + "/touchKey_class_arrR_color" + styleBtnFile + "')";
  var prodNavPrev = document.getElementById("prodNavPrev");
  prodNavPrev.style.backgroundImage = "url('" + path + "/touchKey_prod_arrL_color" + styleBtnFile + "')";
  var prodNavNext = document.getElementById("prodNavNext");
  prodNavNext.style.backgroundImage = "url('" + path + "/touchKey_prod_arrR_color" + styleBtnFile + "')";
};

/**
 * 색상 스타일 테마 적용 (StyleCd 변경도 함께 수행)
 */
Format.prototype.setGraphStyle = function (graph, type) {

  var styleCdRegex = /styleCd=([^=]*.(?=;))/gm;
  // 현재 선택된 스타일코드
  var styleCd = this.selectStyle.selectedValue;
  // 해당영역의 전체 셀 또는 선택된 셀
  var cells;
  if (type) {
    cells = graph.getChildCells(graph.getDefaultParent(), true, true);
  } else {
    cells = graph.getSelectionCells();
  }
  // 터치키분류 영역/상품영역 색상 별도 지정
  if (graph.isClassArea) {
    // 터치키분류 영역은 선택된 터치키분류 만 변경
    var gCells = graph.getSelectionCells();
    graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.buttonStyles.size, gCells);
    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles.off, gCells);
    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles.off, gCells);
    // 선택된 터치키분류 의 StyleCd 변경
    var gnStyles = "", gCell;
    for (var g = 0; g < gCells.length; g++) {
      gCell = gCells[g];
      // 정규식으로 styleCd 변경
      gnStyles = gCell.getStyle().replace(styleCdRegex, "styleCd="+styleCd);
      gCell.setStyle(gnStyles);
    }
    this.fontSize.value = graph.fontStyles.size;
    this.fontColor.value = graph.fontStyles.off;
    this.fillColor.value = graph.buttonStyles.off;
  } else {
    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles["01"].off, cells);
    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles["01"].off, cells);
    graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.fontStyles["01"].size, cells);
    // 상품영역 셀 styleCd 변경
    var pnStyles, pCell, cnStyles, childCell;
    for (var p = 0; p < cells.length; p++) {
      pCell = cells[p];
      // 정규식으로 styleCd 변경
      pnStyles = pCell.getStyle().replace(styleCdRegex, "styleCd="+styleCd);
      pCell.setStyle(pnStyles);
      // 자식속성 존재시 같이 변경
      if (pCell.children) {
        var style, tukeyFg;
        for (var c = 0; c < pCell.children.length; c++) {
          childCell = pCell.children[c];
          // style 에서 tukeyFg 값 추출 : 하위셀인지 판단
          style = graph.getCellStyle(childCell);
          tukeyFg = style['tukeyFg'].toString().leftPad("0", 2);
          // 상품명 태그 색상 조정
          if (tukeyFg === "02" || tukeyFg === "03") {
            graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.fontStyles[tukeyFg].size, new Array(childCell));
            graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles[tukeyFg].off, new Array(childCell));
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles[tukeyFg].off, new Array(childCell));
          }
          // 정규식으로 styleCd 변경
          cnStyles = childCell.getStyle().replace(styleCdRegex, "styleCd="+styleCd);
          childCell.setStyle(cnStyles);
        }
      }
    }
    var cellType = this.cellTypeCombo.selectedValue;
    this.fontSize.value = graph.fontStyles[cellType].size;
    this.fontColor.value = graph.fontStyles[cellType].off;
    this.fillColor.value = graph.buttonStyles[cellType].off;
  }

};

/**
 * 폰트/정렬 설정값 Set
 */
Format.prototype.setElementsValue = function () {
  var graph = this.graph;
  var format = this;

  //선택된 셀에서 스타일 정보 읽기
  var cells = graph.getSelectionCells();
  var cellType = this.cellTypeCombo.selectedValue;
  var style, initFontSize, initFontColor, initFillColor;
  for (var i = 0; i < cells.length; i++) {
    var cell = cells[i];
    if (graph.isClassArea) {
      // 터치키분류 영역
      style = graph.getCellStyle(cell);
      initFontSize = style['fontSize'];
      initFontColor = style['fontColor'];
      initFillColor = style['fillColor'];
      // 스타일코드 Set
      var styleCd = style['styleCd'].toString().leftPad("0", 2);
      this.selectStyle.selectedValue = styleCd;
      // 페이지버튼 색상변경
      this.setBtnStyle();
      // '태그구분' 콤보박스 사용못하게 막기(분류는 태그구분 필요 없음)
      $("#cellTypeCombo").css('background-color', '#F0F0F0');
      $("#cellTypeCombo").attr("disabled", true);
    } else {
      var cellTypeComboValueChk = graph.cellTypeCombo.selectedValue;
      var childrenNode = 0;
      if(cellTypeComboValueChk == '03') childrenNode = 1;
      else                              childrenNode = 0;
      style = graph.getCellStyle(cell.children[childrenNode]);
      initFontSize = style['fontSize'];
      initFontColor = style['fontColor'];
      initFillColor = style['fillColor'];
      // 자식속성
      if (cell.children) {
        // 다른버튼 선택시에만 변경되도록
        if (graph.orgChildren.id !== cell.getId()) {
          graph.orgChildren.id = cell.getId();
          graph.orgChildren.parent = cell;
          var childCell = [];
          for(var c = 0; c < cell.children.length; c++) {
            childCell.push(cell.children[c]);
          }
          graph.orgChildren.cell = childCell;
        }
      }
      // '태그구분' 콤보박스 사용
      $("#cellTypeCombo").css('background-color', '#FFFFFF');
      $("#cellTypeCombo").attr("disabled", false);
    }
  }

  format.fontSize.graph = graph;
  format.fontColor.graph = graph;
  format.fillColor.graph = graph;

  format.fontSize.value = initFontSize;
  format.fontColor.value = initFontColor;
  format.fillColor.value = initFillColor;

};

/**
 * 기존 구성 조회
 */
Format.prototype.open = function (isLoad) {
  var classArea = this.touchkey.classArea;
  var prodArea = this.touchkey.prodArea;
  var scope = this.scope;

  scope.$apply(function() {
    scope.setProdClassInfo({});
    scope.$broadcast('loadingPopupActive');
  });

  if (document.getElementsByName('sessionId')[0]) {
    TOUCHKEY_OPEN_URL = TOUCHKEY_OPEN_URL + "?sid=" + document.getElementsByName('sessionId')[0].value;
  }

  // 터치키그룹 추가
  var scope = agrid.getScope("touchKeyCtrl");
  var params = 'tukeyGrpCd=' + scope.touchKeyGrp;

  if(scope.touchKeyGrp !== null && scope.touchKeyGrp !== "") {

    //open
    var reqArea = mxUtils.post(TOUCHKEY_OPEN_URL, params,
        mxUtils.bind(this, function (req) {
          //var enabled = req.getStatus() != 404;
          if (req.getStatus() === 200) {
            try {
              var jsonStr = JSON.parse(req.getText());
              var xmlStr = jsonStr.data;

              if (xmlStr != null) {
                var xmlArr = xmlStr.split("|");

                //터치키분류 영역 추가
                var classXml = mxUtils.parseXml(xmlArr[0]);
                this.setGraphXml(classArea, classXml.documentElement);

                //상품 영역 추가
                var prodXml = mxUtils.parseXml(xmlArr[1]);
                this.setGraphXml(prodArea, prodXml.documentElement);

                var model = classArea.getModel();
                var parent = classArea.getDefaultParent();

                // 터치키분류 영역에서 첫번째(무엇이될지는모름) 셀을 선택하고 상품영역에서도 해당 레이어 활성화
                var firstCell = model.getChildAt(parent, 0);
                // 셀이 존재하는 경우에만 선택 후 이동처리
                if ( firstCell != null ) {
                  classArea.selectCellForEvent(firstCell);
                  var layer = prodArea.model.getCell(firstCell.getId());
                  // 상품영역 레이어 변경
                  prodArea.switchLayer(layer);
                }

                // 터치키분류 영역 스크롤 초기화
                document.getElementById('classWrap').scrollLeft = 0;
                // 터치키분류 영역 페이지번호 초기화
                document.getElementById('classPageNoText').textContent = "PAGE : 1";

              } else {
                // xml이 없는경우 초기화
                this.setGraphXml(classArea, null);
                this.setGraphXml(prodArea, null);
              }
              scope._broadcast('touchKeyCtrl');

            }
            catch (e) {
              scope.$apply(function(){
                scope.$broadcast('loadingPopupInactive');
                scope._popMsg(mxResources.get('errorOpeningFile'));
              });
            }
            if (!isLoad) {
              scope.$apply(function(){
                scope.$broadcast('loadingPopupInactive');
                scope._popMsg(mxResources.get('opened'));
              });
            }
          }
          else {
            scope.$apply(function(){
              scope.$broadcast('loadingPopupInactive');
              scope._popMsg(mxResources.get('errorOpeningFile'));
            });
          }
        })
    );
  }else{
    // xml이 없는경우 초기화
    this.setGraphXml(classArea, null);
    this.setGraphXml(prodArea, null);

    scope._broadcast('touchKeyCtrl');
  }

};

/**
 * Sets the XML node for the current diagram.
 */
Format.prototype.setGraphXml = function (graph, node) {

  if (node != null) {
    var dec = new mxCodec(node.ownerDocument);
    if (node.nodeName === 'mxGraphModel') {
      graph.model.beginUpdate();
      try {
        graph.model.clear();
        dec.decode(node, graph.getModel());
      }
      finally {
        graph.model.endUpdate();
        //로드 후 변수 초기화
        graph.initValue(graph.ROW_PER_PAGE);
      }
    } else {
      throw {
        message: mxResources.get('cannotOpenFile'),
        node: node,
        toString: function () {
          return this.message;
        }
      };
    }
  }
  else {
    graph.model.clear();
  }
};

/**
 * 화면구성 XML을 서버에 저장
 */
Format.prototype.save = function () {

  var classArea = this.touchkey.classArea;
  var prodArea = this.touchkey.prodArea;
  var scope = agrid.getScope("touchKeyCtrl");

  if (classArea.isEditing()) {
    classArea.stopEditing();
  }
  if (prodArea.isEditing()) {
    prodArea.stopEditing();
  }

  var node = null;
  // 상품분류 영역
  var enc = new mxCodec(mxUtils.createXmlDocument());
  node = enc.encode(classArea.getModel());
  var xmlClass = mxUtils.getXml(node);

  // 상품 영역
  enc = new mxCodec(mxUtils.createXmlDocument());
  node = enc.encode(prodArea.getModel());
  var xmlProd = mxUtils.getXml(node);

  var xml = encodeURIComponent(xmlClass) + '|' + encodeURIComponent(xmlProd);

  try {
    if (xml.length < MAX_REQUEST_SIZE) {
      var onload = function (req) {
        scope.$apply(function(){
          scope.$broadcast('loadingPopupInactive');
          scope._popMsg(mxResources.get('saved'));

          // 새로 저장한 그룹코드 값으로 selectBox 셋팅
          setTimeout(function () {
            var jsonStr = JSON.parse(req.getText());
            var xmlStr = JSON.stringify(jsonStr.data).replaceAll('\"','');
            scope.touchKeyGrp = xmlStr;
            scope.touchKeyGrpCombo.selectedValue = xmlStr;

            // 터치키 재조회
            document.getElementById('btnSrchTouchKey').click();

          }, 50);

          // 터치키 그룹 코드 재조회
          $.ajax({
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            data: params,
            url: '/base/prod/touchKey/touchKey/getTouchKeyGrp.sb',
            success: function(data){

              var list = data.data.list;
              var comboArray = [];
              var comboArrayAll = [];
              var comboData  = {};

              comboArrayAll.unshift({name: "전체", value: ""});

              for (var i = 0; i < list.length; i++) {
                comboData       = {};
                comboData.name  = list[i].name;
                comboData.value = list[i].value;
                comboArray.push(comboData);
                comboArrayAll.push(comboData);
              }

              touchKeyGrpData = comboArray;
              tukeyGrpData = comboArrayAll;
              scope._setComboData("touchKeyGrpCombo", touchKeyGrpData);
              scope._setComboData("applyTouchKeyGrpCombo", touchKeyGrpData);
              scope._setComboData("copyTouchKeyGrpCombo", touchKeyGrpData);
            }
          });

          // 터치키 그룹이 있으므로, 보여주기
          $("#btnSrchTouchKey").css("display", "");
          $("#btnNewGrp").css("display", "");
          //$("#btnCancleNewGrp").css("display", "");
          $("#touchKeyGrpCombo").attr("disabled", false);
          $("#touchKeyGrpCombo").css('background-color', '#FFFFFF');
          $("#btnApplyStore").css("display", "");
          $("#trTouchKeyGrp").css("display", "");
          $("#trApplyStore").css("display", "");

          // 터치키 저장 기본 수정 Flag로 셋팅
          $("#hdNewGrp").val("N");

          // 매장은 터치키 매장적용, 터치키복사 기능 사용할 수 없음.
          if(orgnFg === "STORE") {
              $("#trApplyStore").css("display", "none");
          }else{
              $("#trApplyStore").css("display", "");
          }

        });
      };
      var onerror = function (req) {
        scope.$apply(function(){
          scope.$broadcast('loadingPopupInactive');
          scope._popMsg("저장 중 오류가 발생하였습니다.");
        });
      };
      scope.$apply(function() {
        scope.$broadcast('loadingPopupActive', messages["cmm.saving"]);
      });

      var params = 'xml=' + xml;

      // 수정인지 신규저장인지 파악하기 위해
      if($("#hdNewGrp").val() === 'Y'){
        var grpNm = prompt('그룹명을 입력하세요','그룹명');
        params += '&tukeyGrpCd=';
        params += '&tukeyGrpNm=' + grpNm;
      }else{
        params += '&tukeyGrpCd=' + scope.touchKeyGrp;
      }

      // 가상로그인 대응
      if (document.getElementsByName('sessionId')[0]) {
        params += "&sid=" + document.getElementsByName('sessionId')[0].value;
      }
      new mxXmlRequest(TOUCHKEY_SAVE_URL, params).send(onload, onerror);
    }
    else {
      scope.$apply(function(){
        scope.$broadcast('loadingPopupInactive');
        scope._popMsg(mxResources.get('drawingTooLarge'));
      });
      return false;
    }
  }
  catch (e) {
    scope.$apply(function(){
      scope.$broadcast('loadingPopupInactive');
      scope._popMsg(mxResources.get('errorSavingFile'));
    });
  }

};


/**
 * 상품 영역 레이어 활성화 처리
 */
Graph.prototype.switchLayer = function (layer) {
  var prodArea = this;
  var model = prodArea.getModel();
  //선택된 영역이 셀인 경우 해당 셀에 해당하는 상품 레이어 활성화
  var layerCount = model.getChildCount(model.root);
  //모든 레이어 visible false
  for (var layerIdx = 0; layerIdx < layerCount; layerIdx++) {
    (mxUtils.bind(this, function (layerCell) {
      model.setVisible(layerCell, false);
    }))(model.getChildAt(model.root, layerIdx));
  }
  prodArea.setDefaultParent(layer);
  //클릭이벤트 - 선택된 레이어 visible true
  model.setVisible(layer, true);
  // 상품영역 좌우 이동 버튼 위치 초기화
  prodArea.initProdPaging();
};

// /**
//  * 터치키분류 영역에 Overlay 삭제버튼 생성
//  */
// Graph.prototype.createOverlay = function (prodArea, cell) {
//
//   var graph = this;
//
//   //삭제 오버레이 아이콘 생성
//   var overlay = new mxCellOverlay(
//     new mxImage(IMAGE_PATH + '/delete.gif', 16, 16),
//     'Overlay tooltip',
//     mxConstants.ALIGN_RIGHT,
//     mxConstants.ALIGN_TOP,
//     new mxPoint(-10, 10)
//   );
//
//   // 삭제 오버레이 아이콘 클릭 시 처리
//   overlay.addListener(mxEvent.CLICK, function (sender, evt2) {
//     // 상품영역 레이어 삭제
//     var model = prodArea.getModel();
//     prodArea.removeCells([model.getCell(cell.id)]);
//     // 터치키분류영역 셀 삭제
//     graph.removeCells([cell]);
//   });
//   return overlay;
// };


/**
 * 터치키분류 영역 초기화
 */
Graph.prototype.initClassArea = function (prodArea) {

  // Fixes ignored clipping if foreignObject used in Webkit
  mxClient.NO_FO = mxClient.NO_FO || mxClient.IS_SF || mxClient.IS_GC;

  //영역변수 설정
  var graph = this;
  //스타일코드콤보 변수 설정
  var styleCombo = this.selectStyle;
  //태그구분콤보 변수 설정
  var cellTypeCombo = this.cellTypeCombo;
  //터치키분류 영역지정
  graph.isClassArea = true;
  //터치키분류 키 정보 (Custom 변수)
  graph.touchKeyInfo = {width: 99, height: 60, x: 100, y: 61};
  //현재 선택한 하위속성 정보 (Custom 변수)
  graph.orgChildren = {id: '', parent: new mxCell(), cell: []};
  //터치키분류 영역 라인수 초기화
  graph.initValue(window.MAX_CLASS_ROW);
  //색상스타일값 초기화
  graph.initStyle();
  //멀티선택방지
  graph.getSelectionModel().setSingleSelection(true);

  //터치키분류 영역에 새로운 분류 생성
  var createClassArea = function (x, y) {
    var parent = graph.getDefaultParent();
    // 터치키분류 ID 중복방지를 위해 체크
    var grpId = graph.classPrefix + graph.nextGrpId;
    for(var i=0; i<9999; i++){
      if( graph.model.getCell(grpId) )
      {
        graph.nextGrpId++;
        grpId = graph.classPrefix + graph.nextGrpId;
      }
      else
      {
        break;
      }
    }

    // 상품영역과 id 공유를 위해 classCd 커스텀태그로 별도 관리 : 20180920 노현수
    var classCd = "000" + graph.nextGrpId;
    classCd = graph.classPrefix + classCd.slice(-4);
    //스타일코드
    var styleCd = styleCombo.selectedValue;

    if (graph.isEnabled()) {

      graph.model.beginUpdate();

      try {

        var btn = graph.insertVertex(parent,
            grpId,
            mxResources.get('className'),
            x, y,
            graph.touchKeyInfo.width, graph.touchKeyInfo.height,
            "classCd=" + classCd + ";rounded=0;styleCd=" + styleCd + ";"
        );
        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles.off, new Array(btn));
        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles.off, new Array(btn));
        graph.setCellStyles(mxConstants.STYLE_FONTSIZE, fontSizeEnvstVal, new Array(btn));

      } finally {
        graph.model.endUpdate();
        graph.setSelectionCells(new Array(btn));
      }
      // Sets the overlay for the cell in the graph
      // graph.addCellOverlay(cell, graph.createOverlay(prodArea, cell));
    }

    graph.nextGrpId++;
    return grpId;
  };

  //상품 영역에 새로운 레이어 생성
  var createLayer = function (grpId) {
    var layer = null;
    var cell = new mxCell(grpId);
    cell.id = grpId;
    layer = prodArea.addCell(cell, prodArea.model.root, grpId);
    return layer;
  };

  //override 마우스 이벤트 - 터치키분류 영역
  graph.addMouseListener({
    //터치키분류 영역 마우스 클릭 시 해당 상품 레이어 보이기
    //상품 레이어가 없을 경우 새로 생성
    //터치키분류와 상품영역은 id로 연결
    mouseDown: function (sender, me) {
      var layer;
      if (me.state == null) {
        // 클릭 할 때 오브젝트 생성
        var pt = graph.getPointForEvent(me.evt);
        // 마우스 포인터 위치를 기준으로 생성 가능한 위치 찾기
        var pos = graph.findPosition(pt);
        // 가능한 위치가 아닌경우...
        if (pos == null) {
          mxEvent.consume(me);
          return false;
        }
        if (orgnFg === "STORE" && touchKeyEnvstVal2 === "2"){
          mxEvent.consume(me);
          return false;
        }
        //선택된 터치키분류 영역이 셀이 아닌 경우에는 해당 영역에 새로운 분류생성
        var x = parseInt(me.getGraphX() / graph.touchKeyInfo.x) * graph.touchKeyInfo.x;
        var y = parseInt(me.getGraphY() / graph.touchKeyInfo.y) * graph.touchKeyInfo.y;
        var currId = createClassArea(x, y);
        layer = createLayer(currId);
        prodArea.switchLayer(layer);
      } else {
        //선택된 레이어를 기본값으로 설정
        var cell = me.state.cell;
        //터치키분류 터치키 클릭 시 처리
        var model = prodArea.getModel();
        layer = model.getCell(cell.id);
        prodArea.switchLayer(layer);
        //상품영역 스크롤했던 것 초기화
        prodArea.initProdPaging();
      }
      document.getElementById('keyStyle').classList.remove("hideNav");
      document.getElementById('keyStyleAd').classList.remove("hideNav");
    },
    mouseMove: function (sender, me) {
    },
    mouseUp: function (sender, me) {
    },
    dragEnter: function (evt, state) {
    },
    dragLeave: function (evt, state) {
    }
  });

  //기본 터치키분류 생성
  if (prodArea.isEnabled() && graph.isEnabled()) {
    prodArea.model.beginUpdate();
    graph.model.beginUpdate();
    try {
      var currId = createClassArea(0, 0);
      var model = prodArea.getModel();
      //그래프가 생성될 때 id=1은 이미 생성되어 있으므로 해당 레이어 삭제 후 재 생성
      prodArea.removeCells([model.getCell(1)]);
      var layer = createLayer(currId);

      prodArea.switchLayer(layer);
    }
    finally {
      graph.model.endUpdate();
      prodArea.model.endUpdate();
    }
  }

  /**
   * 페이징 버튼
   */
      //페이지 이동 위치 셀의크기 * 페이지에 컬럼 수
  var scrollWidth = graph.touchKeyInfo.x * graph.COL_PER_PAGE;
  var pageNoWrap = document.getElementById('classPageNoText');
  var classWrap = document.getElementById('classWrap');
  addClickHandler(document.getElementById('grpNavPrev'), function () {
    if (graph.pageNo > 1) {
      graph.pageNo -= 1;
      classWrap.scrollLeft -= scrollWidth;
      pageNoWrap.textContent = "PAGE : " + graph.pageNo;
      // 페이지의 첫 버튼 찾아 선택처리
      findCell(graph.pageNo);
    }
  });
  addClickHandler(document.getElementById('grpNavNext'), function () {
    if (graph.pageNo < graph.MAX_PAGE) {
      graph.pageNo += 1;
      classWrap.scrollLeft += scrollWidth;
      pageNoWrap.textContent = "PAGE : " + graph.pageNo;
      // 페이지의 첫 버튼 찾아 선택처리
      findCell(graph.pageNo);
    }
  });

  // 터치키분류 버튼 찾기
  var findCell = function(pageNo) {
    var x, y, parent, firstCell, layer;
    var model = graph.getModel();
    // 1페이지에서는 무조건 첫번째 셀로만 선택.
    if (pageNo === 1) {
      parent = graph.getDefaultParent();
      firstCell = model.getChildAt(parent, 0);
      graph.selectCellForEvent(firstCell);
      layer = prodArea.model.getCell(firstCell.getId());
      prodArea.switchLayer(layer);
      // 그외 페이지에서는 x,y 좌표 계산하여 찾기
    } else {
      // 터치키분류 Row 수 만큼 검색
      for (var r = 0; r < window.MAX_CLASS_ROW; r++) {
        // 셀 존재시 수행안함
        if (firstCell) {
          break;
        }
        // y포지션
        y = r * 61;
        // 페이지당 컬럼 수 만큼 검색
        for (var c = 0; c < graph.COL_PER_PAGE; c++) {
          // x포지션
          x = ( pageNo - 1 ) * 500 + c * 100 ;
          firstCell = graph.getCellAt(x, y);
          // 셀 존재시 선택이벤트 처리
          if (firstCell) {
            graph.selectCellForEvent(firstCell);
            layer = prodArea.model.getCell(firstCell.getId());
            prodArea.switchLayer(layer);
            break;
          }
        }
      }
    }
  }

};


/**
 * 상품 영역 고유 특성 처리
 */
Graph.prototype.initProdArea = function (classArea, sidebar) {

  var graph = this;
  this.classArea = classArea;
  this.sidebar = sidebar;
  // 상품키 정보 (Custom 변수)
  graph.touchKeyInfo = {width: 99, height: 74, x: 100, y: 75};
  //현재 선택한 하위속성 정보 (Custom 변수)
  graph.orgChildren = {id: '', parent: new mxCell(), cell: []};
  var theGrid = this.sidebar.grid;
  // 상품영역은 터치키분류 영역이 아님.
  graph.isClassArea = false;
  // vertex에 child 삽입시 접는 기능 제거 : 20180906 노현수
  graph.foldingEnabled = false;
  // cell 에디팅 불가 : 20180906 노현수
  graph.setCellsEditable(false);
  // vertex에 삽입된 child 밖으로 드래그 불가 설정 : 20180906 노현수
  graph.graphHandler.setRemoveCellsFromParent(false);
  //색상스타일값 초기화
  graph.initStyle();
  //상품영역은 8-터치키분류 Row 줄수 로 초기화
  var prodsLine = 8 - window.MAX_CLASS_ROW;
  graph.initValue(prodsLine);
  //멀티선택방지
  graph.getSelectionModel().setSingleSelection(true);
  //상품 영역에만 UNDO, 키 핸들러 추가
  graph.undoManager = graph.createUndoManager(graph);
  var rubberband = new mxRubberband(graph);
  graph.keyHandler = graph.createKeyHandler(graph);


  //셀 삭제 시 그리드에 반영
  var cellsRemoved = graph.cellsRemoved;
  graph.cellsRemoved = function (cells) {
    cellsRemoved.apply(this, arguments);
    sidebar.initUsed();
  };

  //셀 추가 시 그리드에 반영
  var cellsAdded = graph.cellsAdded;
  graph.cellsAdded = function (cells) {
    cellsAdded.apply(this, arguments);
    sidebar.initUsed();
  };

  //UNDO 를 했을 때 그리드의 사용여부 업데이트
  var undoHandler = function (sender, evt) {
    var model = graph.getModel();
    var cells = [];

    //추가된 경우 사용여부 true & 선택
    var cand = graph.getSelectionCellsForChanges(evt.getProperty('edit').changes);
    for (var i = 0; i < cand.length; i++) {
      if ((model.isVertex(cand[i]) || model.isEdge(cand[i])) && graph.view.getState(cand[i]) != null) {
        cells.push(cand[i]);
      }
    }
    graph.setSelectionCells(cells);

    //삭제된 경우 사용여부 false
    sidebar.initUsed();
  };
  graph.undoManager.addListener(mxEvent.UNDO, undoHandler);
  graph.undoManager.addListener(mxEvent.REDO, undoHandler);

  // 셀 선택변경시 이전 셀 가져오기
  // graph.getSelectionModel().addListener(mxEvent.CHANGE, function(sender, evt) {
  //   var sCell = sender.cells[0];
  //   if ( sCell ) {
  //     console.log("selected Cell ", sCell.children[0].value);
  //   }
  //   var cell = evt.getProperty('added')[0];
  //   if ( cell ) {
  //     console.log("before cell? ", cell.children[0].value);
  //   }
  // });

  //override 마우스 이벤트 - 상품영역
  graph.addMouseListener({
    // 상품버튼 클릭시 버튼/상품명/금액 구분하여 속성설정 예외처리
    mouseDown: function (sender, me) {
      // 터치키분류 영역 선택 초기화 : 제거 - 문제발생시 다시 원복 할것 : 20190102 노현수
      // graph.classArea.getSelectionModel().clear();
      // 터치키분류 영역 에디팅 판단하여 에디팅 취소 처리
      if (graph.classArea.cellEditor.getEditingCell() != null) {
        graph.classArea.cellEditor.stopEditing(true);
      }
      // 클릭 영역에 셀이 있는 경우에만...
      if (me.state !== undefined) {
        // 버튼속성 뷰 활성화
        document.getElementById('keyStyle').classList.remove("hideNav");
        document.getElementById('keyStyleAd').classList.remove("hideNav");

        // 선택한 셀 값 파악
        var cells = graph.getSelectionCells();
        var cell = graph.getSelectionCells()[0];

        // 선택한 셀이 있으면 스타일 적용
        if (cell.children) {
          // '적용'버튼 활성화시 복사한 스타일로 적용
          if($("#hdTouchKeyStyleApply").val() === "Y" && $("#hdCopyFillColor").val() !== "") {
            // 배경
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, $("#hdCopyFillColor").val(), cells);
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, $("#hdCopyFillColor").val(), cell.children);
            // 상품명, 금액
            graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, $("#hdCopyFont02").val().split("/")[0], new Array(cell.children[0]));
            graph.setCellStyles(mxConstants.STYLE_FONTSIZE, $("#hdCopyFont02").val().split("/")[1], new Array(cell.children[0]));
            graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, $("#hdCopyFont03").val().split("/")[0], new Array(cell.children[1]));
            graph.setCellStyles(mxConstants.STYLE_FONTSIZE, $("#hdCopyFont03").val().split("/")[1], new Array(cell.children[1]));
          }
        }
      } else {
        graph.getSelectionModel().clear();
        document.getElementById('keyStyle').classList.add("hideNav");
        document.getElementById('keyStyleAd').classList.add("hideNav");
      }
    },
    mouseMove: function (sender, me) {
    },
    mouseUp: function (sender, me) {
    },
    dragEnter: function (evt, state) {
    },
    dragLeave: function (evt, state) {
    }
  });

  /**
   * 페이징 버튼
   *
   * 페이지 이동 위치 셀의크기 * 페이지에 컬럼 수
   */
  var scrollWidth = graph.touchKeyInfo.x * graph.COL_PER_PAGE;
  var prodWrap = document.getElementById('prodWrap');
  var pageNoWrap = document.getElementById('prodPageNoText');
  addClickHandler(document.getElementById('prodNavPrev'), function () {
    if (graph.pageNo > 1) {
      graph.pageNo -= 1;
      prodWrap.scrollLeft -= scrollWidth;
      pageNoWrap.textContent = "PAGE : " + graph.pageNo;
    }
  });
  addClickHandler(document.getElementById('prodNavNext'), function () {
    if (graph.pageNo < graph.MAX_PAGE) {
      graph.pageNo += 1;
      prodWrap.scrollLeft += scrollWidth;
      pageNoWrap.textContent = "PAGE : " + graph.pageNo;
    }
  });

  // 셀 커서 속성
  graph.getCursorForCell = function(cell) {
    var sCell = graph.getSelectionCell();
    if (cell != null && ( cell === sCell || cell.parent === sCell ) ) {
      return "all-scroll";
    } else {
      return "default";
    }
  };

  // 자식속성 선택시에도 이동 가능하게 하기 위하여...
  var graphHandlerGetInitialCellForEvent = mxGraphHandler.prototype.getInitialCellForEvent;
  graph.graphHandler.getInitialCellForEvent = function (me) {
    this.graph.getSelectionModel().clear();
    var model = this.graph.getModel();
    var psel = model.getParent(this.graph.getSelectionCell());
    var cell = graphHandlerGetInitialCellForEvent.apply(this, arguments);
    var parent = model.getParent(cell);

    if (psel == null || (psel !== cell && psel !== parent)) {
      while (!this.graph.isCellSelected(cell) && !this.graph.isCellSelected(parent) &&
      model.isVertex(parent) && !this.graph.isValidRoot(parent)) {
        cell = parent;
        parent = this.graph.getModel().getParent(cell);
      }
    }

    return cell;
  };

};


/**
 * 로딩 시점에 초기화
 * @returns
 */
(function () {
  //Sets colors for handles
  mxConstants.HANDLE_FILLCOLOR = '#99ccff';
  mxConstants.HANDLE_STROKECOLOR = '#0088cf';
  mxConstants.VERTEX_SELECTION_COLOR = '#00a8ff';
  mxConstants.OUTLINE_COLOR = '#00a8ff';
  mxConstants.OUTLINE_HANDLE_FILLCOLOR = '#99ccff';
  mxConstants.OUTLINE_HANDLE_STROKECOLOR = '#00a8ff';
  mxConstants.CONNECT_HANDLE_FILLCOLOR = '#cee7ff';
  mxConstants.EDGE_SELECTION_COLOR = '#00a8ff';
  mxConstants.DEFAULT_VALID_COLOR = '#00a8ff';
  mxConstants.LABEL_HANDLE_FILLCOLOR = '#cee7ff';
  mxConstants.GUIDE_COLOR = '#0088cf';
  mxConstants.HIGHLIGHT_OPACITY = 30;
  mxConstants.HIGHLIGHT_SIZE = 8;
})();
