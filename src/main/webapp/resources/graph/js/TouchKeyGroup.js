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

};

/**
 * 상품 그룹 영역에 Overlay 삭제버튼 생성
 */
Graph.prototype.createOverlay = function (prod, cell) {

  var graph = this;

  //삭제 오버레이 아이콘 생성
  var overlay = new mxCellOverlay(
    new mxImage(IMAGE_PATH + '/delete.gif', 16, 16),
    'Overlay tooltip',
    mxConstants.ALIGN_RIGHT,
    mxConstants.ALIGN_TOP,
    new mxPoint(-10, 10)
  );

  // 삭제 오버레이 아이콘 클릭 시 처리
  overlay.addListener(mxEvent.CLICK, function (sender, evt2) {
    // 상품영역 레이어 삭제
    var model = prod.getModel();
    prod.removeCells([model.getCell(cell.id)]);
    // 그룹영역 셀 삭제
    graph.removeCells([cell]);
  });
  return overlay;
};


/**
 * 상품 그룹 영역 초기화
 */
Graph.prototype.initGroupArea = function (prod) {

  var graph = this;

  // 상품 그룹영역은 버튼 높이 다름
  graph.tKeySize.height = 60;

  //그룹영역은 2줄로 초기화
  graph.isGroup = true;
  graph.initValue(MAX_GROUP_ROW);

  //상품 그룹 영역에 새로운 그룹 생성
  var createGroup = function (x, y) {
    var parent = graph.getDefaultParent();
    var grpId = graph.groupPrefix + graph.nextGrpId;
    //console.log(grpId);
    if (graph.isEnabled()) {

      var cell;
      graph.model.beginUpdate();
      try {
        cell = graph.insertVertex(parent,
          grpId,
          mxResources.get('groupName'),
          x, y,
          graph.tKeySize.width, graph.tKeySize.height);
      }
      finally {
        graph.model.endUpdate();
      }
      // Sets the overlay for the cell in the graph
      graph.addCellOverlay(cell, graph.createOverlay(prod, cell));
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

  //override 마우스 이벤트 - 그룹영역
  graph.addMouseListener({
    //상품 그룹 영역 마우스 클릭 시 해당 상품 레이어 보이기
    //상품 레이어가 없을 경우 새로 생성
    //그룹과 상품영역은 id로 연결
    mouseDown: function (sender, me) {
      if (me.state == null) {
        //선택된 상품그룹 영역이 셀이 아닌 경우에는 해당 영역에 새로운 그룹생성
        var x = parseInt(me.getGraphX() / graph.tKeySize.width) * graph.tKeySize.width;
        var y = parseInt(me.getGraphY() / graph.tKeySize.height) * graph.tKeySize.height;
        var currId = createGroup(x, y);
        var layer = createLayer(currId);
        prod.switchLayer(layer);
      }
      else {
        //선택된 레이어를 기본값으로 설정
        var cell = me.state.cell;
        //상품그룹 터치키 클릭 시 처리
        var model = prod.getModel();
        var layer = model.getCell(cell.id);
        prod.switchLayer(layer);
        //상품영역 스크롤했던 것 초기화
        prod.initProdPaging();
      }
    },
    mouseMove: function () {
    },
    mouseUp: function () {
    }
  });

  //기본 상품 그룹 생성
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
  var scrollWidth = graph.tKeySize.width * graph.COL_PER_PAGE;
  var div = document.getElementById('groupNav');
  var wrap = document.getElementById('groupWrap');
  addClickHandler(document.getElementById('grp'), function () {
    if (graph.pageNo > 1) {
      graph.pageNo -= 1;
      wrap.scrollLeft -= scrollWidth;
    }
  });
  addClickHandler(document.getElementById('grn'), function () {
    if (graph.pageNo < graph.MAX_PAGE) {
      graph.pageNo += 1;
      wrap.scrollLeft += scrollWidth;
    }
  });

};
