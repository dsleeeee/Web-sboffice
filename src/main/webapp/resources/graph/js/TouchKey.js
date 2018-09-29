//지정된 영역에만 터치키를 넣을 수 있도록 처리
mxGraph.prototype.allowNegativeCoordinates = false;

/**
 * 메인 Class
 */
Touchkey = function (themes) {
  mxEventSource.call(this);
  //분류 컨테이너
  this.groupContainer = document.getElementById("group");
  //상품 컨테이너
  this.prodContainer = document.getElementById("prod");
  //상품분류 생성
  this.group = new Graph(this.groupContainer, themes);
  //상품분류 생성
  this.prod = new Graph(this.prodContainer, themes);
  //왼쪽 Wijmo 그리드 생성
  this.sidebar = new Sidebar(this.prod);
  //오른쪽 설정 영역 생성
  this.format = new Format(this);
  this.init();
};

//Extends mxEditor
mxUtils.extend(Touchkey, mxEventSource);

//변수 생성
Touchkey.prototype.group = null;
Touchkey.prototype.prod = null;
Touchkey.prototype.sidebar = null;

/**
 * 메인 - 초기화
 */
Touchkey.prototype.init = function () {
  //상품 분류 영역 고유 특성 정의
  this.group.initGroupArea(this.prod);
  //상품 영역 고유 특성 정의
  this.prod.initProdArea(this.group, this.sidebar);
  //레이어가 보일 때(switchLayer..) 이벤트 추가 처리
  var sidebar = this.sidebar;
  var mxGraphModelSetVisible = mxGraphModel.prototype.setVisible;
  this.prod.model.setVisible = function (cell, visible) {
    //visible = true 인 레이어의 상품을 그리드에서 체크
    if (sidebar != null && visible) {
      sidebar.initUsed(cell);
    }
    mxGraphModelSetVisible.apply(this, arguments);
  };
  var format = this.format;
  var group = this.group;
  //분류 영역에서 셀 클릭 시 설정 패널 초기화
  group.addListener(mxEvent.CLICK, function (sender, evt) {
    format.update(group);
  });
  //상품 영역에서 셀 클릭 시 설정 패널 초기화
  var prod = this.prod;
  prod.addListener(mxEvent.CLICK, function (sender, evt) {
    format.update(prod);
  });

  //마우스 오른쪽 클릭 - 컨텍스트 메뉴
  mxEvent.disableContextMenu(this.groupContainer);
  mxEvent.disableContextMenu(this.prodContainer);

  //서버의 초기 설정 로드
  this.format.open(true);

};

/**
 * 메모리 삭제
 */
Touchkey.prototype.destroy = function () {
  if (this.group != null) {
    this.group.destroy();
    this.group = null;
  }
  if (this.group != null) {
    this.prod.destroy();
    this.prod = null;
  }
  if (this.keyHandler != null) {
    this.keyHandler.destroy();
    this.keyHandler = null;
  }

  var c = [this.prodContainer, this.groupContainer];
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
  if (funct != null) {
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
function Sidebar(prod) {
  this.graph = prod;
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

  var layer = layer || graph.getDefaultParent();

  //그리드의 모든 항목 사용여부 false로 셋팅
  for (i = 0; i < theGrid.rows.length; i++) {
    theGrid.setCellData(theGrid.rows[i].index, 'used', false);
  }

  //상품코드로 wijmo 그리드에서 해당 인덱스 추출
  var getIdByProdCd = function (prodCd) {
    var id = -1;
    for (g = 0; g < theGrid.rows.length; g++) {
      if (theGrid.rows[g].dataItem.prodCd === prodCd) {
        id = theGrid.rows[g].index;
        break;
      }
    }
    return id;
  };

  //각 상품의 상품코드로 그리드에서 체크 표시
  var model = graph.getModel();
  var childCount = model.getChildCount(layer);
  var cell, match, regex, prodCd;
  for (var i = 0; i < childCount; i++) {
    cell = model.getChildAt(layer, i);
    regex = /prodCd=([^=]*.(?=;))/gm;
    match = regex.exec(cell.getStyle());
    if (match) {
      prodCd = match[1];
    }
    var id = getIdByProdCd(prodCd);
    if (id >= 0) {
      theGrid.setCellData(id, 'used', true);
    }
  }

};

/**
 * 그리드 생성
 */
Sidebar.prototype.makeGrid = function () {

  var graph = this.graph;

  //조회된 상품 정보로 데이터 생성
  function getData() {
    var data = [];
    for (i = 0; i < PRODS.length; i++) {
      data.push({
        prodClassNm: PRODS[i].prodClassNm,
        prodCd: PRODS[i].prodCd,
        prodNm: PRODS[i].prodNm,
        saleUprc: PRODS[i].saleUprc,
        used: false
      });
    }
    return data;
  }

  //그리드 생성
  var flex = new wijmo.grid.FlexGrid('#theGrid', {
    itemsSource: getData(),
    columns: [
      {binding: 'prodCd', header: mxResources.get('prodCd'), isReadOnly: true, visible: false},
      {binding: 'prodNm', header: mxResources.get('prodNm'), width: '*', isReadOnly: true},
      {binding: 'prodClassNm', header: mxResources.get('prodClassNm'), width: 60, isReadOnly: true},
      {binding: 'saleUprc', header: mxResources.get('saleUprc'), isReadOnly: true, visible: false},
      {binding: 'used', header: mxResources.get('alreadyUsed'), width: 60, isReadOnly: true}
    ],
    selectionMode: 'ListBox',
    allowDragging: 'None',
    isReadOnly: true
  });

  //TODO Drag & Drop 방식 선택에 따라 테이블속성, 판매터치키 소스 통일
  //이슈사항 검토 시 방식 결정 요청하였으나 응답 없음

  //선택 Clear
  flex.select(-1, -1);

  function areAllRowsSelected(flex) {
    for (var i = 0; i < flex.rows.length; i++) {
      if (!flex.rows[i].isSelected) return false;
    }
    return true;
  }

  //커스텀 이벤트 처리
  flex.hostElement.addEventListener('mousedown', function (e) {
    var ht = flex.hitTest(e);

    // allow sorting/resizing/dragging
    if (ht.cellType === wijmo.grid.CellType.ColumnHeader) {
      return;
    }
    // row 클릭시 선택되도록 설정 : 자연스러운 드래그를 위함
    if (ht.cellType === wijmo.grid.CellType.Cell) {
      for (var i = 0; i < flex.rows.length; i++) {
        flex.rows[i].isSelected = false;
      }
      flex.rows[ht.row].isSelected = true;
    }

    // toggle row selection when clicking row headers
    if (ht.cellType === wijmo.grid.CellType.RowHeader) {
      flex.rows[ht.row].isSelected = !flex.rows[ht.row].isSelected;
    }

    // toggle all rows selection when clicking top-left cell
    if (ht.cellType === wijmo.grid.CellType.TopLeft) {
      var select = !areAllRowsSelected(flex);
      for (var i = 0; i < flex.rows.length; i++) {
        flex.rows[i].isSelected = select;
      }
    }

    // cancel default handling
    e.preventDefault();
    e.stopPropagation();
    e.stopImmediatePropagation();
  }, true);

  // show checkboxes on row headers
  flex.formatItem.addHandler(function (s, e) {
    var sel = null;

    // apply selected state to row header cells
    if (e.panel === flex.rowHeaders) {
      sel = flex.rows[e.row].isSelected;
      wijmo.toggleClass(e.cell, 'wj-state-multi-selected', sel);
    }

    // apply selected state to top-left cell
    if (e.panel === flex.topLeftCells) {
      sel = areAllRowsSelected(flex);
    }

    // show checkboxes on row header and top-left cells
    if (sel != null && e.col === 0) {
      //css에 정의된 기어 아이콘 삭제
      if (e.row === 0) {
        e.cell.style.backgroundImage = 'none';
      }
      e.cell.innerHTML = '<span class="wj-glyph-check" style="opacity:' + (sel ? 1 : .25) + '"></span>';
    }
  });

  //상품분류 검색 콤보박스의 데이터 생성
  function getComboData() {
    var names = [];
    //전체 Item 생성
    names.push(mxResources.get('all'));
    //상품 데이터에서 분류
    for (i = 0; i < PRODS.length; i++) {
      names.push(PRODS[i].prodClassNm);
    }
    //중복을 제거하고 분류 데이터 return
    return names.slice()
      .sort(function (a, b) {
        return a - b;
      })
      .reduce(function (a, b) {
        if (a.slice(-1)[0] !== b) a.push(b);
        return a;
      }, []);
  }

  //상품분류 콤보박스 생성
  var selectClass = new wijmo.input.ComboBox('#selectClass', {
    itemsSource: getComboData(),
    isEditable: false,
    selectedIndexChanged: function (s, e) {
      var search = s.selectedValue;
      //전체 선택 시에는 필터 clear
      if (search === mxResources.get('all')) {
        flex.collectionView.filter = function (item) {
          return true;
        }
      }
      else {
        //선택된 분류 필터 적용
        var rx = new RegExp(search, 'i');
        flex.collectionView.filter = function (item) {
          return !search || JSON.stringify(item).match(rx);
        }
      }
    }
  });

  return flex;
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

    //Drop 할 때 오브젝트 생성
    var pt = graph.getPointForEvent(evt);

    //Grid에서 선택된 데이터 대상
    for (var selected = 0; selected < grid.selectedItems.length; selected++) {

      //마우스 포인터 위치를 기준으로 Drop 가능한 위치 찾기
      var pos = graph.findPosition(pt);
      if (pos == null) {
        mxEvent.consume(evt);
        return;
      }
      //Drop 된 포지션과 다음 포지션에 터치키 생성
      var rows = grid.selectedRows[selected];
      var item = rows.dataItem;
      model.beginUpdate();

      // 현재 graph 영역의 전체 버튼 갯수를 tukeyCd(키값)으로 활용
      var tukeyCd = graph.getChildCells(graph.getDefaultParent(), true, true).length + 1;
      tukeyCd = "00" + tukeyCd;
      tukeyCd = tukeyCd.slice(-3);
      // 스타일코드
      var styleCd = graph.selectStyle.selectedValue;

      try {
        // 버튼
        var btn = graph.insertVertex(parent, null,
          null,
          pos.x, pos.y,
          graph.touchKeyInfo.width, graph.touchKeyInfo.height,
          "tukeyCd=" + tukeyCd + ";tukeyFg=01;prodCd=" + item.prodCd + ";styleCd=" + styleCd + ";rounded=0;"
        );
        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles["01"].off, new Array(btn));
        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles["01"].off, new Array(btn));
        graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.fontStyles["01"].size, new Array(btn));

        // 버튼에 품목명 추가
        var prodTag = graph.insertVertex(btn, null,
          item.prodNm,
          5, 5,
          0, 0,
          "tukeyCd=" + tukeyCd + ";tukeyFg=02;prodCd=" + item.prodCd + ";styleCd=" + styleCd + ";strokeColor=none;rounded=0;resizable=0;"
        );
        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles["02"].off, new Array(prodTag));
        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles["02"].off, new Array(prodTag));
        graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.fontStyles["02"].size, new Array(prodTag));

        // 버튼에 금액 추가
        var priceTag = graph.insertVertex(btn, null,
          addComma(item.saleUprc),
          5, graph.touchKeyInfo.y / 2 + 10,
          0, 0,
          "tukeyCd=" + tukeyCd + ";tukeyFg=03;prodCd=" + item.prodCd + ";styleCd=" + styleCd + ";strokeColor=none;rounded=0;resizable=0;"
        );
        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles["03"].off, new Array(priceTag));
        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles["03"].off, new Array(priceTag));
        graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.fontStyles["03"].size, new Array(priceTag));

        // 하위 셀의 사이즈 자동조정
        graph.updateCellSize(prodTag, false);
        graph.updateCellSize(priceTag, false);
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
 * 그래픽 영역 - 상품분류, 상품영역
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
//현재 선택한 버튼 정보 (Custom 변수)
Graph.prototype.touchKeyInit = { fontSize: 0, fontColor: '', fillColor: ''};
//현재 선택한 하위속성 정보 (Custom 변수)
Graph.prototype.orgChildren = { id: '', parent: new mxCell(), cell: [] };
//최대 페이지 갯수
Graph.prototype.MAX_PAGE = 5;
//한페이지에 컬럼 갯수
Graph.prototype.COL_PER_PAGE = 5;
// 페이지당 줄 수
Graph.prototype.ROW_PER_PAGE = window.MAX_GROUP_ROW;
// 텍스트 에디팅 방지
Graph.prototype.textEditing = false;
Graph.prototype.defaultThemeName = 'touchKey';
//상품 분류 영역 셀의 prefix
Graph.prototype.groupPrefix = 'G';
//상품 영역 셀의 prefix
Graph.prototype.prodPrefix = 'T';
//상품분류 영역에 index 변수
//분류, 상품영역의 셀과 레이어의 아이디를 맞추기 위해 사용
Graph.prototype.nextGrpId = 1;
//현재 페이지 번호
Graph.prototype.pageNo = 1;
//스타일색상
Graph.prototype.buttonStyles = {};
Graph.prototype.fontStyles = {};
//스타일코드
Graph.prototype.styleCd = window.TOUCHKEY_STYLE_CD;
//스타일코드 콤보
Graph.prototype.selectStyle = null;

/**
 * 분류/상품 영역 초기화
 */
Graph.prototype.init = function () {

  mxGraph.prototype.init.apply(this, arguments);

  var graph = this;
  //마우스를 영역 밖으로 드래그 했을 때 패닝이 되지 않도록 처리
  graph.setPanning(false);

  //Enables HTML labels > true 하는 경우 vertex는 strictHTML 형태로 삽입된다.
  //strictHTML 일때는 shape redraw 가 안먹는다.
  //로직으로 우겨넣으려면 cell.getState()의 dialect 값 조정(DIALECT_SVG) 해서 사용하여야 한다.
  //그래서 일단은 주석처리 해둠 : 20180928 노현수
  // graph.setHtmlLabels(true);

  //셀을 이동했을 때 스크롤 금지
  graph.graphHandler.scrollOnMove = false;
  graph.autoScroll = false;

  //분류/상품 이동 시 처리
  //대상 셀에 이미 상품이 있을 경우 이동 금지
  var mxGraphHandlerMoveCells = mxGraphHandler.prototype.moveCells;
  var tukeyFg, match, regex;
  graph.graphHandler.moveCells = function (cells, dx, dy, clone, target, evt) {
    // 정규식으로 tukeyFg 찾기
    regex = /tuketFg=([^=]*.(?=;))/gm;
    match = regex.exec(cells[0].getStyle());
    if (match) {
      tukeyFg = match[1];
    }
    // 하위 셀 ( 상품명/금액 ) 인 경우 무시
    if (tukeyFg === "02" || tukeyFg === "03") {
      mxGraphHandlerMoveCells.apply(this, arguments);
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
            isColl = true;
            break;
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
      var isColl = false;
      for (var x = 0; x < bounds.width; (x += graph.touchKeyInfo.x)) {
        for (var y = 0; y < bounds.height; (y += graph.touchKeyInfo.y)) {
          var cellAt = graph.getCellAt(bounds.x + x, bounds.y + y);
          if (cellAt !== null && cell !== cellAt) {
            isColl = true;
            break;
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
      var tukeyFg, childRegex, movedX, movedY;
      for (var r = 0; r < child.length; r++) {
        var cell = child[r];
        // 하위셀 크기 자동 조정
        graph.updateCellSize(cell, true);
        // 정규식으로 tukeyFg 추출
        childRegex = /tukeyFg=([^=]*.(?=;))/gm;
        match = childRegex.exec(cell.getStyle());
        if (match) {
          tukeyFg = match[1];
        }
        // 상품명 태그 위치 조정
        if (tukeyFg === "02") {
          cell.geometry.x = 5;
          cell.geometry.y = 5;
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
  if (mxClient.IS_NS) {
    mxEvent.addListener(graph.container, 'mousedown', function () {
      if (!graph.isEditing()) {
        graph.container.setAttribute('tabindex', '-1');
        graph.container.focus();
      }
    });
  }

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
  //터치키 크기(가로/세로) 절반을 넘으면 터치키(가로/세로) 만큼으로 크기 보정
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
  var dw = Math.round(w / kw);
  var dh = Math.round(h / kh);
  // 터치키 크기(가로/세로) 절반을 넘으면 터치키(가로/세로) 만큼으로 크기 보정
  // 터치키 사이의 1px 간격 유지를 위해 해당 사이즈만큼 추가 계산 (btnBorder)
  var dx = ( Math.abs(mw) <= (kw / 2) ? (kw * dw) : (kw * dw) ) + ( ( dw - 1 ) * this.btnBorder );
  var dy = ( Math.abs(mh) <= (kh / 2) ? (kh * dh) : (kh * dh) ) + ( ( dh - 1 ) * this.btnBorder );

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
 * 분류 변경 시 상품영역 스크롤 초기화
 */
Graph.prototype.initProdPaging = function () {
  var graph = this;
  //좌우 이동 버튼 위치 초기화
  graph.container.parentElement.scrollLeft = 0;
  graph.pageNo = 1;
  var pageText = "PAGE : " + graph.pageNo;
  if (graph.isGroup) {
    document.getElementById('groupPageNoText').textContent = pageText;
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
  graph.ROW_PER_PAGE = parseInt(rowPerPage) || graph.ROW_PER_PAGE;
  // graph 영역 크기 설정
  var graphSizeX = graph.touchKeyInfo.width * graph.COL_PER_PAGE;
  var graphSizeY = graph.touchKeyInfo.height * graph.ROW_PER_PAGE;
  // 버튼사이의 간격 1px 적용
  graphSizeX += (graph.COL_PER_PAGE - 1) * this.btnBorder;
  graphSizeY += (graph.ROW_PER_PAGE - 1) * this.btnBorder;
  // x축 길이 설정
  graphSizeX = graphSizeX * graph.MAX_PAGE;

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
  //분류 영역이 2줄 or 3줄 인지에 따라 영역 크기 지정
  if (graph.isGroup) {
    var groupWrap = document.getElementById('groupWrap');
    var group = document.getElementById('group');
    var groupNavWrap = document.getElementById('divGroupNavWrap');
    removeClass(groupWrap, 'hGroupLine2');
    removeClass(groupWrap, 'hGroupLine3');
    removeClass(groupNavWrap, 'hGroupLine2');
    removeClass(groupNavWrap, 'hGroupLine3');
    removeClass(group, 'touchGroupLine2');
    removeClass(group, 'touchGroupLine3');
    if (parseInt(rowPerPage) === 2) {
      addClass(groupWrap, 'hGroupLine2');
      addClass(groupNavWrap, 'hGroupLine2');
      addClass(group, 'touchGroupLine2');
    } else if (parseInt(rowPerPage) === 3) {
      addClass(groupWrap, 'hGroupLine3');
      addClass(groupNavWrap, 'hGroupLine3');
      addClass(group, 'touchGroupLine3');
    }
    // 페이지번호 표시
    document.getElementById('groupPageNoText').textContent = "PAGE : " + graph.pageNo;
  } else {
    var prodWrap = document.getElementById('prodWrap');
    var prod = document.getElementById('prod');
    var prodNavWrap = document.getElementById('divProdNavWrap');
    removeClass(prodWrap, 'hProdsLine5');
    removeClass(prodWrap, 'hProdsLine6');
    removeClass(prodNavWrap, 'hProdsLine5');
    removeClass(prodNavWrap, 'hProdsLine6');
    removeClass(prod, 'touchProdsLine5');
    removeClass(prod, 'touchProdsLine6');
    if (parseInt(rowPerPage) === 6) {
      addClass(prodWrap, 'hProdsLine6');
      addClass(prodNavWrap, 'hProdsLine6');
      addClass(prod, 'touchProdsLine6');
    } else if (parseInt(rowPerPage) === 5) {
      addClass(prodWrap, 'hProdsLine5');
      addClass(prodNavWrap, 'hProdsLine5');
      addClass(prod, 'touchProdsLine5');
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
 * - 상품분류 생성 시 여러 동작을 하여 불필요한 history가 생성되었음
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

    if (cells != null && cells.length > 0) {
      var parents = graph.model.getParents(cells);
      graph.removeCells(cells);

      // Selects parents for easier editing of groups
      if (parents != null) {
        var select = [];
        for (var i = 0; i < parents.length; i++) {
          if (graph.model.contains(parents[i]) && graph.model.isVertex(parents[i])) {
            select.push(parents[i]);
          }
        }
        graph.setSelectionCells(select);
      }
    }
  });

  //TODO 상품분류에서 상품레이어 처리 시 2개의 트랜잭션 처리되는 문제. 일단 주석 처리
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

  for (var i = 0; i < TOUCHKEY_STYLES.length; i++) {
    for (var key in TOUCHKEY_STYLES[i]) {
      if (key === "styleCd" && styleCd === TOUCHKEY_STYLES[i][key]) {
        var buttonStyles = {};
        var fontStyles = {};
        if (this.isGroup && TOUCHKEY_STYLES[i].buttonFg === 'G') {
          buttonStyles.on = TOUCHKEY_STYLES[i].buttonOnColor;
          buttonStyles.off = TOUCHKEY_STYLES[i].buttonOffColor;
          fontStyles.on = TOUCHKEY_STYLES[i].fontOnColor;
          fontStyles.off = TOUCHKEY_STYLES[i].fontOffColor;
          fontStyles.size = TOUCHKEY_STYLES[i].fontSize;
          // 개별 영역의 변수에 할당
          this.buttonStyles = buttonStyles;
          this.fontStyles = fontStyles;
        } else {
          var buttonTagFg = TOUCHKEY_STYLES[i].buttonTagFg;
          buttonStyles.on = TOUCHKEY_STYLES[i].buttonOnColor;
          buttonStyles.off = TOUCHKEY_STYLES[i].buttonOffColor;
          fontStyles.on = TOUCHKEY_STYLES[i].fontOnColor;
          fontStyles.off = TOUCHKEY_STYLES[i].fontOffColor;
          fontStyles.size = TOUCHKEY_STYLES[i].fontSize;
          // 개별 영역의 변수에 할당
          this.buttonStyles[buttonTagFg] = buttonStyles;
          this.fontStyles[buttonTagFg] = fontStyles;
        }
      }
    }
  }
};

/**
 * 분류영역 셀 삭제
 * @param format
 */
function deleteGroupCell(format) {
  var graph = format.graph;
  var cells = graph.getSelectionCells();
  var gModel = graph.getModel();
  var parent = graph.getDefaultParent();
  // 상품영역
  var prod = format.touchkey.prod;
  var pModel = prod.getModel();
  // 분류영역에 버튼없을때 삭제버튼을 누르는 경우 오류 방지
  if (cells.length > 0) {
    // 상품영역 셀 삭제
    prod.removeCells([pModel.getCell(cells[0].id)]);
    // 분류영역 셀 삭제
    graph.removeCells([cells[0]]);
    // 삭제후 분류영역의 첫번째 셀 선택
    var firstCell = gModel.getChildAt(parent, 0);
    graph.selectCellForEvent(firstCell);
    var layer = prod.model.getCell(firstCell.getId());
    prod.switchLayer(layer);
    // 셀 속성지정 감추기
    document.getElementById('keyStyle').classList.add("hideNav");
  }
}

/**
 * 상품영역 셀 삭제
 * @param cell
 */
function deleteProdCell(format) {
  var graph = format.touchkey.prod;
  var dCells = graph.getDeletableCells(graph.getSelectionCells());
  if (dCells != null && dCells.length > 0) {
    var parents = graph.model.getParents(dCells);
    graph.removeCells(dCells);
    graph.getSelectionModel().clear();
    // Selects parents for easier editing of groups
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
        document.getElementById('colorStyleWrap').classList.remove("hideNav");
      } else {
        document.getElementById('keyStyle').classList.add("hideNav");
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
  this.graph = touchkey.group;

  this.init();

}

/**
 * 기능패널의 변수 선언
 */
Format.prototype.fontColor = null;
Format.prototype.fontSize = null;
Format.prototype.fillColor = null;

/**
 * 기능 패널 초기화
 */
Format.prototype.init = function () {

  //분류, 상품 선택 변경 시 설정 패널 초기화
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
 * 하위 셀 위치 초기화
 * @param cell
 */
Format.prototype.initChildCell = function(cell) {
  var childCell, tukeyFg, regex, match, movedX, movedY;
  for (var r = 0; r < cell.children.length; r++) {
    childCell = cell.children[r];
    // 정규식으로 tukeyFg 추출
    regex = /tukeyFg=([^=]*.(?=;))/gm;
    match = regex.exec(childCell.getStyle());
    if (match) {
      tukeyFg = match[1];
    }
    // 상품명 태그 위치 조정
    if ( tukeyFg === "02" ) {
      childCell.geometry.x = 5;
      childCell.geometry.y = 5;
    // 금액태그 위치 조정
    } else if( tukeyFg === "03" ) {
      movedX = cell.geometry.width - childCell.geometry.width - 5;
      movedY = cell.geometry.height - childCell.geometry.height - 5;
      childCell.geometry.x = movedX;
      childCell.geometry.y = movedY;
    }
  }
};

/**
 * 폰트/정렬 설정 초기화
 */
Format.prototype.initElements = function () {

  var graph = this.graph;
  var format = this;

  // 구성초기화 버튼
  addClickHandler(document.getElementById('btnInit'), function () {
    format.open(false);
  });

  // 버튼초기화 버튼
  addClickHandler(document.getElementById('btnReset'), function (s) {
    // 선택된 셀에서 스타일 정보 읽기
    var cells = format.graph.getSelectionCells();
    var cell = cells[0];
    // 버튼의 원 속성값 get
    format.fontColor.value = format.graph.touchKeyInit.fontColor;
    format.fontSize.value = format.graph.touchKeyInit.fontSize;
    format.fillColor.value = format.graph.touchKeyInit.fillColor;
    // 버튼 속성 리셋
    format.graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, format.fontColor.value, cell);
    format.graph.setCellStyles(mxConstants.STYLE_FONTSIZE, format.fontSize.value, cell);
    format.graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, format.fillColor.value, cell);

    // 하위속성 있는경우 위치 리셋 : 상품버튼
    if (cell.children) {
      if (cell.children.length === format.graph.orgChildren.cell.length) {
        format.initChildCell(cell);
      } else {
        var children = format.graph.orgChildren.cell;
        cell.children = children;
        for (var c = 0; c < children.length; c++) {
          if(children[c].parent === null) {
            children[c].parent = format.graph.orgChildren.parent;
          }
        }
        format.initChildCell(cell);
      }
    }

    // 상위속성 있는경우 위치리셋 : 상품버튼의 상품명/금액 태그
    if (!cell.parent.value) {
      format.initChildCell(cell.parent);
    }

    // 그래프 새로고침 하여 변경내용 화면에 적용
    format.graph.refresh();
  });

  // 삭제 버튼
  addClickHandler(document.getElementById('btnDelete'), function () {
    var graph = format.graph;
    if (graph.isGroup) {
      deleteGroupCell(format);
    } else {
      deleteProdCell(format);
    }
  });

  // 저장 버튼
  addClickHandler(document.getElementById('btnSave'), function () {
    format.save(format.touchkey.group, format.touchkey.prod);
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
        s.graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, s.value, null);
        var cell = s.graph.getSelectionCells()[0];
        // 하위속성 존재시 하위속성 색상도 같이 변경
        if (cell.children) {
          s.graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, s.value, cell.children);
        }
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
        s.graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, s.value, null);
        var cell = s.graph.getSelectionCells()[0];
        // 하위속성 존재시 하위속성 색상도 같이 변경
        if (cell.children) {
          s.graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, s.value, cell.children);
        }
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
        s.graph.setCellStyles(mxConstants.STYLE_FONTSIZE, s.value, null);
        var cell = s.graph.getSelectionCells()[0];
        // 하위속성 존재시 하위속성 크기도 같이 변경
        if (cell.children) {
          s.graph.setCellStyles(mxConstants.STYLE_FONTSIZE, s.value, cell.children);
        }
      }
    }
  });

  // 터치키 스타일코드 콤보박스 생성
  this.selectStyle = new wijmo.input.ComboBox('#selectStyle', {
    displayMemberPath: 'styleNm',
    selectedValuePath: 'styleCd',
    itemsSource: TOUCHKEY_STYLE_CDS,
    isEditable: false,
    selectedValue: TOUCHKEY_STYLE_CD,
    selectedIndexChanged: function (s, e) {
      var group = format.touchkey.group;
      var prod = format.touchkey.prod;
      var styleCd = s.selectedValue;
      for (var i = 0; i < TOUCHKEY_STYLES.length; i++) {
        for (var key in TOUCHKEY_STYLES[i]) {
          if (key === "styleCd" && styleCd === TOUCHKEY_STYLES[i][key]) {
            var buttonStyles = {};
            var fontStyles = {};
            if (TOUCHKEY_STYLES[i].buttonFg === 'G') {
              buttonStyles.on = TOUCHKEY_STYLES[i].buttonOnColor;
              buttonStyles.off = TOUCHKEY_STYLES[i].buttonOffColor;
              fontStyles.on = TOUCHKEY_STYLES[i].fontOnColor;
              fontStyles.off = TOUCHKEY_STYLES[i].fontOffColor;
              fontStyles.size = TOUCHKEY_STYLES[i].fontSize;
              // 개별 영역의 변수에 할당
              group.buttonStyles = buttonStyles;
              group.fontStyles = fontStyles;
            } else {
              var buttonTagFg = TOUCHKEY_STYLES[i].buttonTagFg;
              buttonStyles.on = TOUCHKEY_STYLES[i].buttonOnColor;
              buttonStyles.off = TOUCHKEY_STYLES[i].buttonOffColor;
              fontStyles.on = TOUCHKEY_STYLES[i].fontOnColor;
              fontStyles.off = TOUCHKEY_STYLES[i].fontOffColor;
              fontStyles.size = TOUCHKEY_STYLES[i].fontSize;
              // 개별 영역의 변수에 할당
              prod.buttonStyles[buttonTagFg] = buttonStyles;
              prod.fontStyles[buttonTagFg] = fontStyles;
            }
          }
        }
      }
      // 버튼 색상 스타일 적용
      format.setBtnStyle();
      // 색상 스타일 적용 : 분류/상품 영역
      format.setGraphStyle(group);
      format.setGraphStyle(prod);
    }
  });
  // 초기 버튼 색상 스타일 적용
  format.setBtnStyle();
  // 그래프에서 접근하도록 설정
  format.touchkey.group.selectStyle = this.selectStyle;
  format.touchkey.prod.selectStyle = this.selectStyle;

};

/**
 * 버튼 스타일 테마 적용
 */
Format.prototype.setBtnStyle = function() {
  // 현재 선택된 스타일코드
  var styleCd = this.selectStyle.selectedValue;
  var path = "/resource/solbipos/css/img/touchKey/";
  var ext = ".png";

  var grpNavPrev = document.getElementById("grpNavPrev");
  grpNavPrev.style.backgroundImage = "url('" + path + "/touchKey_class_arrL_color" + styleCd + ext + "')";
  var grpNavNext = document.getElementById("grpNavNext");
  grpNavNext.style.backgroundImage = "url('" + path + "/touchKey_class_arrR_color" + styleCd + ext + "')";
  var prodNavPrev = document.getElementById("prodNavPrev");
  prodNavPrev.style.backgroundImage = "url('" + path + "/touchKey_prod_arrL_color" + styleCd + ext + "')";
  var prodNavNext = document.getElementById("prodNavNext");
  prodNavNext.style.backgroundImage = "url('" + path + "/touchKey_prod_arrR_color" + styleCd + ext + "')";


};

/**
 * 색상 스타일 테마 적용 (StyleCd 변경도 함께 수행)
 */
Format.prototype.setGraphStyle = function (graph) {

  var styleCdRegex = /styleCd=([^=]*.(?=;))/gm;
  // 현재 선택된 스타일코드
  var styleCd = this.selectStyle.selectedValue;
  // 해당영역의 전체 셀
  var cells = graph.getChildCells(graph.getDefaultParent(), true, true);
  // 분류영역/상품영역 색상 별도 지정
  if (graph.isGroup) {
    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles.off, cells);
    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles.off, cells);
    graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.fontStyles.size, cells);
    // 선택된 분류의 StyleCd 변경
    // var gCells = graph.getSelectionCells();
    var gnStyles = "", gCell;
    for (var g = 0; g < cells.length; g++) {
      gCell = cells[g];
      // 정규식으로 styleCd 변경
      gnStyles = gCell.getStyle().replace(styleCdRegex, "styleCd="+styleCd);
      gCell.setStyle(gnStyles);
    }
  } else {
    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles["01"].off, cells);
    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles["01"].off, cells);
    graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.fontStyles["01"].size, cells);
    // 상품영역 전체 styleCd 변경
    var pnStyles, pCell, cnStyles, childCell;
    for (var p = 0; p < cells.length; p++) {
      pCell = cells[p];
      // 정규식으로 styleCd 변경
      pnStyles = pCell.getStyle().replace(styleCdRegex, "styleCd="+styleCd);
      pCell.setStyle(pnStyles);
      // 자식속성 존재시 같이 변경
      if (pCell.children) {
        var tukeyFg, tukeyFgRegex, match;
        for (var c = 0; c < pCell.children.length; c++) {
          childCell = pCell.children[c];
          // 정규식으로 tukeyFg 추출
          tukeyFgRegex = /tukeyFg=([^=]*.(?=;))/gm;
          match = tukeyFgRegex.exec(childCell.getStyle());
          if (match) {
            tukeyFg = match[1];
          }
          // 상품명 태그 색상 조정
          if (tukeyFg === "02" || tukeyFg === "03") {
            graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, graph.buttonStyles[tukeyFg].off, new Array(childCell));
            graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, graph.fontStyles[tukeyFg].off, new Array(childCell));
            graph.setCellStyles(mxConstants.STYLE_FONTSIZE, graph.fontStyles[tukeyFg].size, new Array(childCell));
          }
          // 정규식으로 styleCd 변경
          cnStyles = childCell.getStyle().replace(styleCdRegex, "styleCd="+styleCd);
          childCell.setStyle(cnStyles);
        }
      }
      // 하위속성 크기 조정
      graph.resizeCell(pCell, pCell.geometry, false);
    }
  }

};

/**
 * 마우스 오버시 색상 스타일 테마 적용
 */
Graph.prototype.updateHoverStyle = function(state, hover) {

  var parentState;
  if (hover) {
    if (this.isGroup) {
      // 분류영역에는 자식속성이 없음
      state.style[mxConstants.STYLE_FILLCOLOR] = this.buttonStyles.on;
      state.style[mxConstants.STYLE_FONTCOLOR] = this.fontStyles.on;
      state.style[mxConstants.STYLE_FONTSIZE] = this.fontStyles.size;
    } else {
      state.style[mxConstants.STYLE_FILLCOLOR] = this.buttonStyles["01"].on;
      state.style[mxConstants.STYLE_FONTCOLOR] = this.fontStyles["01"].on;
      state.style[mxConstants.STYLE_FONTSIZE] = 11;
      // 상품영역일때만 자식속성 존재 하므로 자식속성도 동일하게 변경
      if (state.cell.children) {
        this.updateStyleChildren(state, hover);
      }
      // 자식속성에 마우스 오버시
      if (state.cell.parent != null && isEmpty(state.cell.parent.value)) {
        parentState = this.view.getState(state.cell.parent);
        if(parentState != null) {
          parentState.style[mxConstants.STYLE_FILLCOLOR] = this.buttonStyles["01"].on;
          parentState.style[mxConstants.STYLE_FONTCOLOR] = this.fontStyles["01"].on;
          parentState.style[mxConstants.STYLE_FONTSIZE] = this.fontStyles["01"].size;
          parentState.shape.apply(parentState);
          parentState.shape.redraw();
          this.updateStyleChildren(parentState, hover);
        }
      }
    }
  } else {
    if (this.isGroup) {
      // 분류영역에는 자식속성이 없음
      state.style[mxConstants.STYLE_FILLCOLOR] = this.buttonStyles.off;
      state.style[mxConstants.STYLE_FONTCOLOR] = this.fontStyles.off;
      state.style[mxConstants.STYLE_FONTSIZE] = this.fontStyles.size;
    } else {
      state.style[mxConstants.STYLE_FILLCOLOR] = this.buttonStyles["01"].off;
      state.style[mxConstants.STYLE_FONTCOLOR] = this.fontStyles["01"].off;
      state.style[mxConstants.STYLE_FONTSIZE] = this.fontStyles["01"].size;
      // 상품영역일때만 자식속성 존재 하므로 자식속성도 동일하게 변경
      if (state.cell.children) {
        this.updateStyleChildren(state, hover);
      }
      // 자식속성에 마우스 오버 해제시
      if (state.cell.parent != null && isEmpty(state.cell.parent.value)) {
        parentState = this.view.getState(state.cell.parent);
        if(parentState != null) {
          parentState.style[mxConstants.STYLE_FILLCOLOR] = this.buttonStyles["01"].off;
          parentState.style[mxConstants.STYLE_FONTCOLOR] = this.fontStyles["01"].off;
          parentState.style[mxConstants.STYLE_FONTSIZE] = this.fontStyles["01"].size;
          parentState.shape.apply(parentState);
          parentState.shape.redraw();
          this.updateStyleChildren(parentState, hover);
        }
      }
    }
  }
  state.shape.apply(state);
  state.shape.redraw();

  if (state.text != null) {
    state.text.apply(state);
    state.text.redraw();
  }
};

/**
 * 셀의 하위속성 스타일 적용
 */
Graph.prototype.updateStyleChildren = function (state, hover) {

  var tukeyFg, match, regex;
  for(var i = 0; i < state.cell.children.length; i++) {
    var childCell = state.cell.children[i];
    var childState = this.view.getState(childCell);
    if (childState != null) {
      regex = /tukeyFg=([^=]*.(?=;))/gm;
      match = regex.exec(childCell.getStyle());
      if (match) {
        tukeyFg = match[1];
      }
      // 상품/금액 태그 색상 조정
      if (tukeyFg === "02" || tukeyFg === "03") {
        if (hover) {
          childState.style[mxConstants.STYLE_FILLCOLOR] = this.buttonStyles[tukeyFg].on;
          childState.style[mxConstants.STYLE_FONTCOLOR] = this.fontStyles[tukeyFg].on;
          childState.style[mxConstants.STYLE_FONTSIZE] = this.fontStyles[tukeyFg].size;
        } else {
          childState.style[mxConstants.STYLE_FILLCOLOR] = this.buttonStyles[tukeyFg].off;
          childState.style[mxConstants.STYLE_FONTCOLOR] = this.fontStyles[tukeyFg].off;
          childState.style[mxConstants.STYLE_FONTSIZE] = this.fontStyles[tukeyFg].size;
        }
      }
      childState.shape.apply(childState);
      childState.shape.redraw();
      if (childState.text != null) {
        childState.text.apply(childState);
        childState.text.redraw();
      }
    }
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
  var initFontSize = 11;
  var initFontColor;
  var initFillColor;
  for (var i = 0; i < cells.length; i++) {
    var cell = cells[i];
    var state = graph.view.getState(cell);
    if (state != null) {
      initFontSize = Math.max(0, parseInt(mxUtils.getValue(state.style, mxConstants.STYLE_FONTSIZE, null)));
      initFontColor = mxUtils.getValue(state.style, mxConstants.STYLE_FONTCOLOR, null);
      initFillColor = mxUtils.getValue(state.style, mxConstants.STYLE_FILLCOLOR, null);
    }
    // 분류영역선택시 스타일코드 설정
    if (graph.isGroup) {

    // 자식속성은 상품영역에만 존재한다.
    } else {
      // 자식속성
      if (cell.children) {
        // 다른버튼 선택시에만 변경되도록
        if (graph.orgChildren.id !== cell.getId()) {
          graph.orgChildren.id = cell.getId();
          graph.orgChildren.parent = cell;
          var childCell = new Array();
          for(var c = 0; c < cell.children.length; c++) {
            childCell.push(cell.children[c]);
          }
          graph.orgChildren.cell = childCell;
        }
      }
      // 부모속성 : 상품/금액태그
      if (!cell.parent.value) {
        var parent = cell.parent;
        // 다른속성 선택시에만 변경되도록
        if (graph.orgChildren.id !== parent.getId()) {
          graph.orgChildren.id = parent.getId();
          graph.orgChildren.parent = parent;
          var parentCell = new Array();
          for(var p = 0; p < parent.children.length; p++) {
            parentCell.push(parent.children[p]);
          }
          graph.orgChildren.cell = parentCell;
        }
      }
    }
  }

  format.fontSize.graph = graph;
  format.fontColor.graph = graph;
  format.fillColor.graph = graph;

  format.fontSize.value = initFontSize;
  format.fontColor.value = initFontColor;
  format.fillColor.value = initFillColor;

  graph.touchKeyInit.fontSize = initFontSize;
  graph.touchKeyInit.fontColor = initFontColor;
  graph.touchKeyInit.fillColor = initFillColor;

};

/**
 * 기존 구성 조회
 */
Format.prototype.open = function (isLoad) {
  var group = this.touchkey.group;
  var prod = this.touchkey.prod;

  //open
  var reqGroup = mxUtils.post(TOUCHKEY_OPEN_URL, '',
    mxUtils.bind(this, function (req) {
      //var enabled = req.getStatus() != 404;
      if (req.getStatus() === 200) {
        try {
          var jsonStr = JSON.parse(req.getText());
          var xmlStr = jsonStr.data;

          if (xmlStr != null) {
            var xmlArr = xmlStr.split("|");

            //분류 영역 추가
            var groupXml = mxUtils.parseXml(xmlArr[0]);
            this.setGraphXml(group, groupXml.documentElement);

            //상품 영역 추가
            var prodXml = mxUtils.parseXml(xmlArr[1]);
            this.setGraphXml(prod, prodXml.documentElement);

            var model = group.getModel();
            var parent = group.getDefaultParent();

            //분류 영역에서 첫번째(무엇이될지는모름) 셀을 선택하고 상품영역에서도 해당 레이어 활성화
            var firstCell = model.getChildAt(parent, 0);
            group.selectCellForEvent(firstCell);
            var layer = prod.model.getCell(firstCell.getId());
            prod.switchLayer(layer);
          }
        }
        catch (e) {
          mxUtils.alert(mxResources.get('errorOpeningFile'));
        }
        if (!isLoad) {
          mxUtils.alert(mxResources.get('opened'));
        }
      }
      else {
        mxUtils.alert(mxResources.get('errorOpeningFile'));

      }
    })
  );

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
    }
    else {
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
  var group = this.touchkey.group;
  var prod = this.touchkey.prod;

  if (group.isEditing()) {
    group.stopEditing();
  }
  if (prod.isEditing()) {
    prod.stopEditing();
  }

  /*
  var enc = new mxCodec(mxUtils.createXmlDocument());
  var node = enc.encode(group.getModel());
  var xml = mxUtils.getPrettyXml(node);
  mxLog.show();
  mxLog.write(xml);
  var enc = new mxCodec(mxUtils.createXmlDocument());
  var node = enc.encode(prod.getModel());
  var xml = mxUtils.getPrettyXml(node);
  mxLog.show();
  mxLog.write(xml);
  */

  var node = null;
  var enc = new mxCodec(mxUtils.createXmlDocument());
  node = enc.encode(group.getModel());
  var xmlGroup = mxUtils.getXml(node);

  var node = null;
  var enc = new mxCodec(mxUtils.createXmlDocument());
  node = enc.encode(prod.getModel());
  var xmlProd = mxUtils.getXml(node);

  var xml = encodeURIComponent(xmlGroup) + '|' + encodeURIComponent(xmlProd);

  try {
    if (xml.length < MAX_REQUEST_SIZE) {
      var onload = function (req) {
        mxUtils.alert(mxResources.get('saved'));
      }
      var onerror = function (req) {
        mxUtils.alert('Error');
      }
      new mxXmlRequest(TOUCHKEY_SAVE_URL, 'xml=' + xml).send(onload, onerror);
    }
    else {
      mxUtils.alert(mxResources.get('drawingTooLarge'));
      return;
    }
  }
  catch (e) {
    mxUtils.alert(mxResources.get('errorSavingFile'));
  }

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
