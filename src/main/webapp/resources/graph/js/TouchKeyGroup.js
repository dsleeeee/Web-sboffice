/**
 * 상품 영역 레이어 활성화 처리
 */
Graph.prototype.switchLayer = function (layer) {
  var prod = this;
  var model = prod.getModel();
  //선택된 영역이 셀인 경우 해당 셀에 해당하는 상품 레이어 활성화
  var layerCount = model.getChildCount(model.root);
  //모든 레이어 visible false
  for (var layerIdx = 0; layerIdx < layerCount; layerIdx++) {
    (mxUtils.bind(this, function (layerCell) {
      model.setVisible(layerCell, false);
    }))(model.getChildAt(model.root, layerIdx));
  }
  prod.setDefaultParent(layer);
  //클릭이벤트 - 선택된 레이어 visible true
  model.setVisible(layer, true);
  // 상품영역 좌우 이동 버튼 위치 초기화
  prod.initProdPaging();
};

// /**
//  * 상품 분류 영역에 Overlay 삭제버튼 생성
//  */
// Graph.prototype.createOverlay = function (prod, cell) {
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
//     var model = prod.getModel();
//     prod.removeCells([model.getCell(cell.id)]);
//     // 분류영역 셀 삭제
//     graph.removeCells([cell]);
//   });
//   return overlay;
// };


/**
 * 상품 분류 영역 초기화
 */
Graph.prototype.initGroupArea = function (prod) {

  // Fixes ignored clipping if foreignObject used in Webkit
  mxClient.NO_FO = mxClient.NO_FO || mxClient.IS_SF || mxClient.IS_GC;

  //영역변수 설정
  var graph = this;
  //스타일코드콤보 변수 설정
  var styleCombo = this.selectStyle;
  //분류영역지정
  graph.isGroup = true;
  //분류키 정보 (Custom 변수)
  graph.touchKeyInfo = {width: 99, height: 60, x: 100, y: 61};
  //현재 선택한 버튼 정보 (Custom 변수)
  graph.touchKeyInit = {fontSize: 0, fontColor: '', fillColor: ''};
  //현재 선택한 하위속성 정보 (Custom 변수)
  graph.orgChildren = {id: '', parent: new mxCell(), cell: []};
  //분류영역은 2줄로 초기화
  graph.initValue(window.MAX_GROUP_ROW);
  //색상스타일값 초기화
  graph.initStyle();
  //멀티선택방지
  graph.getSelectionModel().setSingleSelection(true);

  //상품 분류 영역에 새로운 분류 생성
  var createGroup = function (x, y) {
    var parent = graph.getDefaultParent();
    var grpId = graph.groupPrefix + graph.nextGrpId;
    // 상품영역과 id 공유를 위해 classCd 커스텀태그로 별도 관리 : 20180920 노현수
    var classCd = "000" + graph.nextGrpId;
    classCd = graph.groupPrefix + classCd.slice(-4);
    //스타일코드
    var styleCd = styleCombo.selectedValue;

    if (graph.isEnabled()) {

      graph.model.beginUpdate();

      try {

        var btn = graph.insertVertex(parent,
          grpId,
          mxResources.get('groupName'),
          x, y,
          graph.touchKeyInfo.width, graph.touchKeyInfo.height,
          "classCd=" + classCd + ";rounded=0;styleCd=" + styleCd + ";"
        );
        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles.off, new Array(btn));
        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles.off, new Array(btn));
        graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.fontStyles.size, new Array(btn));

      } finally {
        graph.model.endUpdate();
      }
      // Sets the overlay for the cell in the graph
      // graph.addCellOverlay(cell, graph.createOverlay(prod, cell));
    }

    graph.nextGrpId++;
    return grpId;
  };

  //상품 영역에 새로운 레이어 생성
  var createLayer = function (grpId) {
    var layer = null;
    var cell = new mxCell(grpId);
    cell.id = grpId;
    layer = prod.addCell(cell, prod.model.root, grpId);
    return layer;
  };

  //override 마우스 이벤트 - 분류영역
  graph.addMouseListener({
    //상품 분류 영역 마우스 클릭 시 해당 상품 레이어 보이기
    //상품 레이어가 없을 경우 새로 생성
    //분류과 상품영역은 id로 연결
    currentState: null,
    previousStyle: null,
    mouseDown: function (sender, me) {
      // 마우스오버를 위해 초기화
      if (this.currentState != null) {
        this.dragLeave(me.getEvent(), this.currentState);
        this.currentState = null;
      }
      var layer;
      if (me.state == null) {
        //선택된 상품분류 영역이 셀이 아닌 경우에는 해당 영역에 새로운 분류생성
        var x = parseInt(me.getGraphX() / graph.touchKeyInfo.x) * graph.touchKeyInfo.x;
        var y = parseInt(me.getGraphY() / graph.touchKeyInfo.y) * graph.touchKeyInfo.y;
        var currId = createGroup(x, y);
        layer = createLayer(currId);
        prod.switchLayer(layer);
      } else {
        //선택된 레이어를 기본값으로 설정
        var cell = me.state.cell;
        //상품분류 터치키 클릭 시 처리
        var model = prod.getModel();
        layer = model.getCell(cell.id);
        prod.switchLayer(layer);
        //상품영역 스크롤했던 것 초기화
        prod.initProdPaging();
      }
      document.getElementById('keyStyle').classList.remove("hideNav");
    },
    mouseMove: function (sender, me) {
      // 에디팅모드(더블클릭) 이후 마우스커서 이동시 라벨에 잔상남는 현상으로
      // graph.cellEditor.getEditingCell() 으로 에디팅 셀 있는지 판단해서 이벤트 적용 설정 : 20180929 노현수
      if (graph.cellEditor.getEditingCell() == null) {
        if (this.currentState != null && me.getState() === this.currentState) {
          return;
        }
        var tmp = graph.view.getState(me.getCell());
        // Ignores everything but vertices
        if (graph.isMouseDown || (tmp !== null && !graph.getModel().isVertex(tmp.cell))) {
          tmp = null;
        }
        if (tmp !== this.currentState) {
          if (this.currentState != null  ) {
            this.dragLeave(me.getEvent(), this.currentState);
          }
          this.currentState = tmp;
          if (this.currentState != null) {
            this.dragEnter(me.getEvent(), this.currentState);
          }
        }
      }
    },
    mouseUp: function (sender, me) {
    },
    dragEnter: function (evt, state) {
      if (state != null) {
        this.previousStyle = state.style;
        state.style = mxUtils.clone(state.style);
        graph.updateHoverStyle(state, true);
      }
    },
    dragLeave: function (evt, state) {
      if (state != null && !state.invalid) {
        state.style = this.previousStyle;
        graph.updateHoverStyle(state, false);
      }
    }
  });

  //기본 상품 분류 생성
  if (prod.isEnabled() && graph.isEnabled()) {
    prod.model.beginUpdate();
    graph.model.beginUpdate();
    try {
      var currId = createGroup(0, 0);
      var model = prod.getModel();
      //그래프가 생성될 때 id=1은 이미 생성되어 있으므로 해당 레이어 삭제 후 재 생성
      prod.removeCells([model.getCell(1)]);
      layer = createLayer(currId);

      prod.switchLayer(layer);
    }
    finally {
      graph.model.endUpdate();
      prod.model.endUpdate();
    }
  }

  /**
   * 페이징 버튼
   */
    //페이지 이동 위치 셀의크기 * 페이지에 컬럼 수
  var scrollWidth = graph.touchKeyInfo.x * graph.COL_PER_PAGE;
  var pageNoWrap = document.getElementById('groupPageNoText')
  var groupWrap = document.getElementById('groupWrap');
  addClickHandler(document.getElementById('grpNavPrev'), function () {
    if (graph.pageNo > 1) {
      graph.pageNo -= 1;
      groupWrap.scrollLeft -= scrollWidth;
      pageNoWrap.textContent = "PAGE : " + graph.pageNo;
    }
  });
  addClickHandler(document.getElementById('grpNavNext'), function () {
    if (graph.pageNo < graph.MAX_PAGE) {
      graph.pageNo += 1;
      groupWrap.scrollLeft += scrollWidth;
      pageNoWrap.textContent = "PAGE : " + graph.pageNo;
    }
  });

};
