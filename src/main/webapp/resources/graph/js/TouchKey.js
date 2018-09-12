//지정된 영역에만 터치키를 넣을 수 있도록 처리
mxGraph.prototype.allowNegativeCoordinates = false;


/**
 * 메인 Class
 */
Touchkey = function (themes) {
  mxEventSource.call(this);
  //그룹 컨테이너
  this.groupContainer = document.getElementById("group");
  //상품 컨테이너
  this.prodContainer = document.getElementById("prod");
  //상품그룹 생성
  this.group = new Graph(this.groupContainer, themes);
  //상품그룹 생성
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
  //상품 그룹 영역 고유 특성 정의
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
  //그룹 영역에서 셀 클릭 시 설정 패널 초기화
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

  //상품영역 셀의 스타일에서 상품코드 추출
  var getProdCd = function (style) {
    var prodCd = "";
    var styles = style.split(";");
    styles.forEach(function (style, index) {
      var keyValues = style.split("=");
      if (keyValues.length > 1 && keyValues[0] === 'prodCd') {
        prodCd = keyValues[1];
      }
    });
    return prodCd;
  };

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
  var cell, prodCd;
  for (var i = 0; i < childCount; i++) {
    cell = model.getChildAt(layer, i);
    prodCd = getProdCd(cell.getStyle());
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
      try {
        // graph.insertVertex(parent,
        //   null,
        //   item.prodNm,
        //   pos.x, pos.y,
        //   graph.tKeySize.width, graph.tKeySize.height,
        //   'prodCd=' + item.prodCd + ';saleUprc=' + item.saleUprc + ";");

        // 버튼
        var btn = graph.insertVertex(parent, null,
          '',
          pos.x, pos.y,
          graph.tKeySize.width, graph.tKeySize.height,
          "prodCd=" + item.prodCd + ";saleUprc=" + item.saleUprc + ";");

        // 버튼에 품목명 추가
        var prodTag = graph.insertVertex(btn, 'prodCd',
          item.prodNm,
          5, 5,
          graph.tKeySize.width - 10, graph.tKeySize.height / 2 - 5,
          "prodCd=" + item.prodCd + ";align=left;verticalAlign=top;strokeColor=none;resizable=0;autosize=1;"
        );

        // // 버튼에 금액 추가
        var priceTag = graph.insertVertex(btn, 'saleUprc',
          item.saleUprc,
          5, graph.tKeySize.height / 2,
          graph.tKeySize.width - 10, graph.tKeySize.height / 2 - 5,
          "prodCd=" + item.prodCd + ";align=right;strokeColor=none;resizable=0;autosize=1;"
        );

      }
      finally {
        model.endUpdate();
        sidebar.initUsed();
        // 좌측 그리드 선택 Clear
        rows.isSelected = false;
      }
    }
  }
  //--dropEvent

  //드래그할 항목 생성
  var previewElt = document.createElement('div');
  previewElt.style.border = 'dashed black 1px';
  previewElt.style.width = graph.tKeySize.width + 'px';
  previewElt.style.height = graph.tKeySize.height + 'px';

  //DnD 처리
  var ds = mxUtils.makeDraggable(grid.cells.hostElement, graph, dropEvent, previewElt, -(graph.tKeySize.width / 2), -(graph.tKeySize.height / 2));
  ds.highlightDropTargets = true;

};


/**
 * 그래픽 영역 - 상품그룹, 상품영역
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
//그룹키 사이즈 (Custom 변수)
Graph.prototype.gKeySize = {width: 99, height: 60};
//터치키 사이즈 (Custom 변수)
Graph.prototype.tKeySize = {width: 99, height: 74};
//최대 페이지 갯수
Graph.prototype.MAX_PAGE = 5;
//한페이지에 컬럼 갯수
Graph.prototype.COL_PER_PAGE = 5;
//페이지당 줄 수
Graph.prototype.ROW_PER_PAGE = 2;
Graph.prototype.textEditing = false;
Graph.prototype.defaultThemeName = 'touchKey';
//상품 그룹 영역 셀의 prefix
Graph.prototype.groupPrefix = 'g';
//상품 영역 셀의 prefix
Graph.prototype.prodPrefix = 'p';
//상품그룹 영역에 index 변수
//그룹, 상품영역의 셀과 레이어의 아이디를 맞추기 위해 사용
Graph.prototype.nextGrpId = 1;
//현재 페이지 번호
Graph.prototype.pageNo = 1;

/**
 * 그룹/상품 영역 초기화
 */
Graph.prototype.init = function () {

  mxGraph.prototype.init.apply(this, arguments);

  var graph = this;
  //마우스를 영역 밖으로 드래그 했을 때 패닝이 되지 않도록 처리
  graph.setPanning(false);
  //Enables HTML labels
  graph.setHtmlLabels(true);
  //셀을 이동했을 때 스크롤 금지
  graph.graphHandler.scrollOnMove = false;
  graph.autoScroll = false;

  //그룹/상품 이동 시 처리
  //대상 셀에 이미 상품이 있을 경우 이동 금지
  var mxGraphHandlerMoveCells = mxGraphHandler.prototype.moveCells;
  graph.graphHandler.moveCells = function (cells, dx, dy, clone, target, evt) {
    var pt = this.graph.getPointForEvent(evt);

    //박스 크기에 맞게 사이즈 조정
    var newSize = this.graph.adjstCellSize(dx, dy);
    dx = newSize.x;
    dy = newSize.y;

    //vertex 이동 시 이동될 위치에 vertex가 있는 경우 이동 금지
    var checkCollision = function (bounds, dx, dy, selectedCells) {
      var startX = bounds.x + dx + 1;
      var startY = bounds.y + dy + 1;

      //이동될 위치에 있는 Object가 자신인지 체크
      //자신일 경우에는 이동 가능
      var isMyself = function (cell) {
        var isMy = false;
        for (var i = 0; i < selectedCells.length; i++) {
          if (selectedCells[i] == cell) {
            isMy = true;
            break;
          }
        }
        return isMy;
      };

      //한칸씩 이동 하면서 해당 위치에 셀이 있는지 체크
      var isColl = false;
      for (var x = 0; x < bounds.width; (x += graph.tKeySize.width)) {
        for (var y = 0; y < bounds.height; (y += graph.tKeySize.height)) {
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
    var lastX = graph.tKeySize.width * graph.COL_PER_PAGE * graph.pageNo - graph.tKeySize.width;
    var lastY = graph.tKeySize.height * graph.ROW_PER_PAGE - graph.tKeySize.height;
    if (x > lastX && y > lastY) {
      isPagingArea = true;
    }
    return isPagingArea;
  };

  //셀의 사이즈가 변경되었을 때 배경 크기에 맞게 보정
  graph.resizeCell = function (cell, bounds, recurse) {
    var newPoint = this.adjstCellSize(bounds.x, bounds.y);
    var newSize = this.adjstCellSize(bounds.width, bounds.height);
    bounds = new mxRectangle(newPoint.x, newPoint.y, newSize.x, newSize.y);

    //vertex 리사이즈 시 다른 vertex를 덮는 경우 리턴
    var checkCollision = function () {
      var isColl = false;
      for (var x = 0; x < bounds.width; (x += graph.tKeySize.width)) {
        for (var y = 0; y < bounds.height; (y += graph.tKeySize.height)) {
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

    var resizeChild = function (child) {
      for (var r = 0; r < child.length; r++) {
        var cell = child[r];
        if (cell.id === "prodCd") {
          console.log("cell.geometry.x ", cell.geometry.x);
          console.log("bounds.width ", bounds.width);
          console.log("bounds.x ", bounds.x);

          cell.geometry.x = parseInt(bounds.x);
        } else if (cell.id === "saleUprc") {
          if (bounds.height > graph.tKeySize.height) {
            var division = bounds.height / graph.tKeySize.height - 1;
            cell.geometry.y = bounds.height / 2 + division * (graph.tKeySize.height / 2);
          } else {
            cell.geometry.y = bounds.height / 2;
          }
        }
        cell.geometry.width = bounds.width - 10;
      }
    }
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
 * 터치키의 사이즈에 따라 위치이동, 셀크기 변경 시 사이즈 보정
 */
Graph.prototype.adjstCellSize = function (w, h) {
  var kw = this.tKeySize.width;
  var kh = this.tKeySize.height;

  var mw = w % kw;
  var mh = h % kh;
  var dw = Math.round(w / kw);
  var dh = Math.round(h / kh);
  //터치키 크기(가로/세로) 절반을 넘으면 터치키(가로/세로) 만큼으로 크기 보정
  var dx = Math.abs(mw) <= (kw / 2) ? (kw * dw) : (kw * dw);
  var dy = Math.abs(mh) <= (kh / 2) ? (kh * dh) : (kh * dh);

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
  };

  function idX(id) {
    return id
  };
  return html_sanitize(value, urlX, idX);
};


/**
 * 그룹 변경 시 상품영역 스크롤 초기화
 */
Graph.prototype.initProdPaging = function () {
  var graph = this;
  //좌우 이동 버튼 위치 초기화
  graph.container.scrollLeft = 0;
  graph.pageNo = 1;
  //var elt = document.getElementById('prodNav');
  //elt.style.left = '320px';
};

/**
 * 엘리먼트 선택 표시
 */
Graph.prototype.setSelected = function (elt, name, selected) {

  if (selected) {
    var arr = elt.className.split(' ');
    if (arr.indexOf('on') == -1) {
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

  graph.minimumGraphSize = new mxRectangle(0, 0, graph.tKeySize.width * graph.COL_PER_PAGE * graph.MAX_PAGE, graph.tKeySize.height * graph.ROW_PER_PAGE);
  graph.maximumGraphBounds = new mxRectangle(0, 0, graph.tKeySize.width * graph.COL_PER_PAGE * graph.MAX_PAGE, graph.tKeySize.height * graph.ROW_PER_PAGE);

  //그룹 영역이 2줄 or 3줄 인지에 따라 영역 크기 지정
  if (graph.isGroup) {
    var removeClass = function (elt, name) {
      elt.className = elt.className.replace(new RegExp(name, 'g'), '').trim();
    }
    var addClass = function (elt, name) {
      elt.className += ' ' + name;
    }
    var groupWrap = document.getElementById('groupWrap');
    removeClass(graph.container, 'h120');
    removeClass(graph.container, 'h180');
    removeClass(groupWrap, 'h120');
    removeClass(groupWrap, 'h180');
    if (parseInt(rowPerPage) == 2) {
      addClass(graph.container, 'h120');
      addClass(groupWrap, 'h120');
    }
    else if (parseInt(rowPerPage) == 3) {
      addClass(graph.container, 'h180');
      addClass(groupWrap, 'h180');
    }
  }

  //좌우 이동 버튼 위치 초기화
  graph.container.scrollLeft = 0;
  graph.pageNo = 1;

  graph.refresh();
};

/**
 * UNDO/REDO 이벤트 생성 - 상품영역만 적용
 * - 상품그룹 생성 시 여러 동작을 하여 불필요한 history가 생성되었음
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

  //TODO 상품그룹에서 상품레이어 처리 시 2개의 트랜잭션 처리되는 문제. 일단 주석 처리
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

  //var cx = (graph.container.clientWidth) * graph.MAX_PAGE;
  //var cy = graph.container.clientHeight;
  var cx = (graph.tKeySize.width * graph.COL_PER_PAGE * graph.pageNo);
  var cy = (graph.tKeySize.height * graph.ROW_PER_PAGE);
  var px = parseInt(pt.x / graph.tKeySize.width) * graph.tKeySize.width;
  var py = parseInt(pt.y / graph.tKeySize.height) * graph.tKeySize.height;
  var lastX = cx - graph.tKeySize.width;
  var lastY = cy - graph.tKeySize.height;

  for (var posY = py; posY < cy; posY += graph.tKeySize.height) {
    for (var posX = px; posX < cx; posX += graph.tKeySize.width) {
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
 * 오른쪽 설정 기능 패널
 */
function Format(touchkey) {

  this.touchkey = touchkey;
  this.container = document.getElementById('format');
  this.graph = touchkey.group;

  this.init();
}

Format.prototype.fontColor = null;
Format.prototype.fontSize = null;
Format.prototype.fillColor = null;

/**
 * 기능 패널 초기화
 */
Format.prototype.init = function () {

  //그룹, 상품 선택 변경 시 설정 패널 초기화
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
  document.getElementById('keyStyle').style.display = 'none';
  if (cells.length > 0) {
    //설정 값 초기화
    this.setElementsValue();
    document.getElementById('keyStyle').style.display = 'block';
  }

};

/**
 * 폰트/정렬 설정 초기화
 */
Format.prototype.initElements = function () {

  var graph = this.graph;
  var format = this;

  //초기화 버튼
  addClickHandler(document.getElementById('btnInit'), function () {
    format.open(false);
  });

  //저장 버튼
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
      s.graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, s.value, null);
    }
  });

  /**
   * 폰트 색상 설정 시작
   */
  this.fontColor = new wijmo.input.InputColor('#fontColor', {
    placeholder: 'Select the color',
    value: '#000000',
    valueChanged: function (s, e) {
      s.graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, s.value, null);
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
      s.graph.setCellStyles(mxConstants.STYLE_FONTSIZE, s.value, null);
    }
  });

};


/**
 * 폰트/정렬 설정값 Set
 */
Format.prototype.setElementsValue = function () {
  var graph = this.graph;
  var format = this;

  //선택된 셀에서 스타일 정보 읽기
  var cells = graph.getSelectionCells();
  var initFontSize = 10;
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
  var group = this.touchkey.group;
  var prod = this.touchkey.prod;

  //open
  var reqGroup = mxUtils.post(TOUCHKEY_OPEN_URL, '',
    mxUtils.bind(this, function (req) {
      //var enabled = req.getStatus() != 404;
      if (req.getStatus() == 200) {
        try {
          var jsonStr = JSON.parse(req.getText());
          var xmlStr = jsonStr.data;

          if (xmlStr != null) {
            var xmlArr = xmlStr.split("|");

            //그룹 영역 추가
            var groupXml = mxUtils.parseXml(xmlArr[0]);
            //console.log(groupXml);
            this.setGraphXml(group, groupXml.documentElement);

            //상품 영역 추가
            var prodXml = mxUtils.parseXml(xmlArr[1]);
            //console.log(prodXml);
            this.setGraphXml(prod, prodXml.documentElement);

            //로드된 그룹에 오버레이 삭제 버튼 추가
            var model = group.getModel();
            var parent = group.getDefaultParent();
            var childCount = model.getChildCount(parent);
            for (var i = 0; i < childCount; i++) {
              cell = model.getChildAt(parent, i);
              group.addCellOverlay(cell, group.createOverlay(prod, cell));
            }

            //그룹 영역에서 첫번째(무엇이될지는모름) 셀을 선택하고 상품영역에서도 해당 레이어 활성화
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

  //console.log(node);
  if (node != null) {
    var dec = new mxCodec(node.ownerDocument);
    //console.log(dec);
    if (node.nodeName == 'mxGraphModel') {
      graph.model.beginUpdate();
      try {
        graph.model.clear();
        dec.decode(node, graph.getModel());
      }
      finally {
        graph.model.endUpdate();

        //console.log(graph);
        //로드 후 변수 초기화
        graph.initValue(graph.ROW_PER_PAGE);
        //console.log(graph);
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

  //console.log(group);
  //console.log(prod);
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

