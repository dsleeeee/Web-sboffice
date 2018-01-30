
//터치키 그리드의 크기
//mxGraph.prototype.gridSize = 10;
//mxGraphView.prototype.gridColor = '#e0e0e0';

//지정된 영역에만 터치키를 넣을 수 있도록 처리 
mxGraph.prototype.allowNegativeCoordinates = false;


/**
 * 메인 Class
 */
Touchkey = function(container, themes) {
  mxEventSource.call(this);
  this.container = container || document.body;
  
  //상품그룹 생성, 영역 추가는 나중에
  this.group = new Graph(null, null, null, null, themes);
  
  //상품그룹 생성
  this.prod = new Graph(null, null, null, null, themes);
  
  //왼쪽 Wijmo 그리드 생성
  this.grid = new Grid(this.prod);

  //오른쪽 설정 영역 생성
  this.format = new Format(this);
  
  this.init(themes);

};

//Extends mxEditor
mxUtils.extend(Touchkey, mxEventSource);

//변수 생성
Touchkey.prototype.actions = null;
Touchkey.prototype.group = null;
Touchkey.prototype.prod = null;
Touchkey.prototype.grid = null;

/**
 * 메인 - 초기화
 */
Touchkey.prototype.init = function(themes) {
  
  //div 태그 생성, 백그라운드 터치키 이미지 사용
  var createDiv = function(classname) {
    var elt = document.createElement('div');
    elt.className = classname;
    return elt;
  };

  //상품 그룹 생성
  this.groupContainer = createDiv("geGroupContainer");
  
  //그룹별 상품 생성
  this.prodContainer = createDiv("geProdContainer");
  
  //상품 영역 추가
  this.prod.init(this.prodContainer);

  //상품그룹 영역 추가
  this.group.init(this.groupContainer);
  //상품 그룹 영역 고유 특성 정의
  this.group.initGroupArea(this.prod);

  //상품 영역 고유 특성 정의
  this.prod.initProdArea(this.group);

  //마우스 오른쪽 클릭 - 컨텍스트 메뉴
  //mxEvent.disableContextMenu(this.container);
  
  this.groupContainer.appendChild(this.createPagingShape('Group', this.group, themes));
  this.prodContainer.appendChild(this.createPagingShape('Prod', this.prod, themes));
  
  /*
  var groupWrap = createDiv("wrapGroup");
  groupWrap.appendChild(this.groupContainer)
  var prodWrap = createDiv("wrapProd");
  prodWrap.appendChild(this.prodContainer)
  this.container.appendChild(groupWrap);
  this.container.appendChild(prodWrap);
  */
  this.container.appendChild(this.groupContainer);
  this.container.appendChild(this.prodContainer);
  
  //스크롤바..(테스트)
  //this.container.appendChild(createDiv("hidden"));
  //console.log(this);
  
  //그룹, 상품 영역 변경 시 설정 패널 이벤트 처리
  //this.format.init(this.group);
  //this.format.init(this.prod);
  
  //console.log(this.format);
  this.group.getSelectionModel().addListener(mxEvent.CHANGE, this.format.update);
  //this.group.addListener(mxEvent.EDITING_STARTED, this.format.update);
  //this.group.addListener(mxEvent.EDITING_STOPPED, this.format.update);
  //group.getModel().addListener(mxEvent.CHANGE, this.update);
  //group.addListener(mxEvent.ROOT, this.update);
  
  this.prod.getSelectionModel().addListener(mxEvent.CHANGE, this.format.update);
  //this.prod.addListener(mxEvent.EDITING_STARTED, this.format.update);
  //this.prod.addListener(mxEvent.EDITING_STOPPED, this.format.update);

  //서버의 초기 설정 로드
  this.format.open(true);
};





/**
 * 메모리 삭제
 */
Touchkey.prototype.destroy = function() {
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

function loadStylesheet(graph)
{
  //console.log('call');
  var node = (graph.themes != null) ? graph.themes[graph.defaultThemeName] :
    (!mxStyleRegistry.dynamicLoading) ? null :
    mxUtils.load(STYLE_PATH + '/default.xml').getDocumentElement();
  //console.log(node);
  if (node != null) {
    var dec = new mxCodec(node.ownerDocument);
    dec.decode(node, graph.getStylesheet());
  }
}


/**
 * 그룹/상품 영역 페이지 이동
 */
Touchkey.prototype.createPagingShape = function(type, target, themes) {

  var div = document.createElement('div');
  div.id = 'pagingShape' + type;
  div.style.borderStyle = 'none';
  div.style.borderWidth = '0px';
  div.style.overflow = 'hidden';
  div.style.position = 'absolute';
  div.style.zIndex = '1'; //상품영역 위에 표시
  div.style.width = target.keySize.width + 'px';
  div.style.height = target.keySize.height + 'px';
  div.style.top = (target.keySize.height * (target.ROW_PER_PAGE-1)) + 'px';
  div.style.left = (target.keySize.width * (target.COL_PER_PAGE-1)) + 'px';
  
  //console.log(target);
  //console.log(div);
  
  var graph = new mxGraph(div); 
  
  graph.setEnabled(false);
  graph.themes = themes;
  graph.defaultThemeName = 'default';
  loadStylesheet(graph);

  //페이지 이동 위치 셀의크기 * 페이지에 컬럼 수
  var scrollWidth = target.keySize.width * target.COL_PER_PAGE;
  graph.addMouseListener({
    mouseDown: function(sender, me)
    {
      //console.log(me);
      if(me.state != null ) {
        var cell = me.state.cell;
        if (cell.style == 'prevBtn') {
          if(target.pageNo > 1) {
            target.pageNo -= 1;
            target.container.scrollLeft -= scrollWidth;
            div.style.left = (parseInt(div.style.left.replace('px','')) - scrollWidth) + 'px';
          }
        }
        //다음버튼 클릭 시 처리
        else if (cell.style == 'nextBtn') {
          if(target.pageNo < target.MAX_PAGE) {
            target.pageNo += 1;
            target.container.scrollLeft += scrollWidth;
            div.style.left = (parseInt(div.style.left.replace('px','')) + scrollWidth) + 'px';
          }
        }
      }
    },
    mouseMove: function() { },
    mouseUp: function() { }
  });
  var parent = graph.getDefaultParent();
  var model = graph.getModel();
  model.beginUpdate();
  try {
    //graph.insertVertex(parent, null, div, 320, 80, graph.gridSize, graph.gridSize);
    var prevBtn = graph.insertVertex(parent, null, '이전', 0, 0, parseInt(target.keySize.width/2), target.keySize.height, 'prevBtn');
    var nextBtn = graph.insertVertex(parent, null, '다음', parseInt(target.keySize.width/2), 0, parseInt(target.keySize.width/2), target.keySize.height, 'nextBtn');
  }
  finally
  {
    model.endUpdate();
  }

  return div;
  //console.log(graph);
};



/**
* 그래픽 영역 - 상품그룹, 상품영역
*/
function Graph(container, model, renderHint, stylesheet, themes) {

  mxGraph.call(this, container, model, renderHint, stylesheet);
  this.themes = themes || this.defaultThemes;
  
  this.currentVertexStyle = mxUtils.clone(this.defaultVertexStyle);
  if (stylesheet == null)
  {
    loadStylesheet(this);
  }

}
/**
 * Graph inherits from mxGraph.
 */
mxUtils.extend(Graph, mxGraph);

Graph.prototype.defaultThemes = {};
Graph.prototype.defaultVertexStyle = {};

//터치키 사이즈 (Custom 변수)
Graph.prototype.keySize = {width:80, height:60};

//최대 페이지 갯수
Graph.prototype.MAX_PAGE = 5;
//한페이지에 컬럼 갯수
Graph.prototype.COL_PER_PAGE = 5;
//페이지당 줄 수
Graph.prototype.ROW_PER_PAGE = 2;

Graph.prototype.textEditing = false;

Graph.prototype.defaultThemeName = 'touchkey';

//상품 그룹 영역 셀의 prefix
Graph.prototype.groupPrefix = 'g';
//상품 영역 셀의 prefix
Graph.prototype.prodPrefix = 'p';

// Adds optional caching for the HTML label
Graph.prototype.labelCached = false;

//상품그룹/상품 영역에 index 변수
Graph.prototype.nextId = 1;

//현재 페이지 번호
Graph.prototype.pageNo = 1;

/**
 * 그룹/상품 영역 초기화
 */
Graph.prototype.init = function(container) {

  mxGraph.prototype.init.apply(this, arguments);
  
  //그래프 영역의 배경 이미지 설정
  container.style.backgroundImage = 'url(' + IMAGE_PATH + '/key.png' + ')';

  //마우스를 영역 밖으로 드래그 했을 때 패닝이 되지 않도록 처리
  this.setPanning(false);
  
  //Enables HTML labels
  this.setHtmlLabels(true);
  
  //셀을 이동했을 때 스크롤 금지
  this.graphHandler.scrollOnMove = false;
  this.autoScroll = false;

  //console.log(this);
  
  //그룹/상품 이동 시 처리
  var mxGraphHandlerMoveCells = mxGraphHandler.prototype.moveCells;
  this.graphHandler.moveCells = function(cells, dx, dy, clone, target, evt){
    //대상 셀에 이미 상품이 있을 경우 이동 금지
    var pt = this.graph.getPointForEvent(evt);
    if (this.graph.getCellAt(pt.x+1, pt.y+1) != null) {
      mxEvent.consume(evt);
      return;
    }
    //console.log(this.graph);
    //console.log(this.graph.pageNo);
    //console.log(this.graph.getMaximumGraphBounds());
    //페이지 범위를 넘는 경우 이동 금지
    var bounds = this.graph.getMaximumGraphBounds();
    var startWidthX = (bounds.width / this.graph.MAX_PAGE) * (this.graph.pageNo-1);
    var endWidthX = (bounds.width / this.graph.MAX_PAGE) * this.graph.pageNo;
    if(pt.y > bounds.height || (pt.x < startWidthX || pt.x > endWidthX) ) {
      mxEvent.consume(evt);
      return;
    }
    
    var w = this.graph.keySize.width;
    var h = this.graph.keySize.height;
    
    var mw = dx % w;
    var mh = dy % h;
    var dw = Math.round(dx / w);
    var dh = Math.round(dy / h);
    
    //console.log(dx);
    //console.log(dy);
    //실제 이동 거리 계산
    //터치키 크기(가로/세로) 절반을 넘으면 터치키(가로/세로) 만큼으로 거리 보정하여 이동
    dx = Math.abs(mw) <= (w/2) ? (w*dw) : (w*dw);
    dy = Math.abs(mh) <= (h/2) ? (h*dh) : (h*dh);
    //console.log(dx);
    //console.log(dy);
    
    mxGraphHandlerMoveCells.apply(this, arguments);
  };

  
  // Overrides method to provide a cell label in the display
  this.convertValueToString = function(cell) {

    if (this.labelCached && cell.div != null)
    {
      // Uses cached label
      return cell.div;
    }
    else if (mxUtils.isNode(cell.value) &&cell.value.nodeName.toLowerCase() == 'userobject')
    {
      // Returns a DOM for the label
      var div = document.createElement('div');
      div.innerHTML = cell.getAttribute('label');
      if(cell.getAttribute('price') != null ) {
        mxUtils.br(div, 3);
        var numberWithCommas = function(x) {
          x = x.toString();
          var pattern = /(-?\d+)(\d{3})/;
          while (pattern.test(x))
              x = x.replace(pattern, "$1,$2");
          return x;
        }
        mxUtils.write(div, numberWithCommas(cell.getAttribute('price')));
      }
      
      if (this.labelCached)
      {
        // Caches label
        cell.div = div;
      }
      
      return div;
    }
    return mxGraph.prototype.convertValueToString.apply(this, arguments); 
};

  // Overrides method to store a cell label in the model
  var cellLabelChanged = this.cellLabelChanged;
  this.cellLabelChanged = function(cell, newValue, autoSize) {
    if (mxUtils.isNode(cell.value) && cell.value.nodeName.toLowerCase() == 'userobject') {
      // Clones the value for correct undo/redo
      var elt = cell.value.cloneNode(true);
      elt.setAttribute('label', newValue);
      newValue = elt;
    }
    
    cellLabelChanged.apply(this, arguments);
  };
  
  // Overrides method to create the editing value
  var getEditingValue = this.getEditingValue;
  this.getEditingValue = function(cell) {
    if (mxUtils.isNode(cell.value) && cell.value.nodeName.toLowerCase() == 'userobject')
    {
      return cell.getAttribute('label');
    }
  };

};



/**
 * Sanitizes the given HTML markup.
 */
Graph.prototype.sanitizeHtml = function(value, editing) {
  // Uses https://code.google.com/p/google-caja/wiki/JsHtmlSanitizer
  // NOTE: Original minimized sanitizer was modified to support
  // data URIs for images, and mailto and special data:-links.
  // LATER: Add MathML to whitelisted tags
  function urlX(link)
  {
    if (link != null && link.toString().toLowerCase().substring(0, 11) !== 'javascript:')
    {
      return link;
    }
    
    return null;
  };
    function idX(id) { return id };
  
  return html_sanitize(value, urlX, idX);
};


/**
 * 초기 데이터 조회 시 변수 초기화
 */
Graph.prototype.initValue = function(rowPerPage) {
  var graph = this;
  
  //새로운 셀 생성을 위한 다음 index 값 초기값 계산
  //첫번째 상품 선택을 위한 
  var objCnt = 0;
  var firstCell = null;
  for (var key in graph.model.cells) {
    var cell = graph.model.cells[key]; 
    //console.log(cell);
    if (mxUtils.isNode(cell.value) && cell.value.nodeName.toLowerCase() == 'userobject') {
      objCnt++;
      if(objCnt == 1) {
        firstCell = cell;
      }
    }
  }
  graph.nextId = (objCnt + 1); 
  
  //영역의 첫번째 셀 선택
  if(firstCell != null) {
    graph.switchLayer(firstCell.getParent());
    graph.selectCellForEvent(firstCell);
  }
  
  //xml에 설정했던 페이지당 줄 수 적용
  graph.ROW_PER_PAGE = rowPerPage;

  graph.minimumGraphSize = new mxRectangle(0, 0, graph.keySize.width * graph.COL_PER_PAGE * graph.MAX_PAGE, graph.keySize.height * graph.ROW_PER_PAGE);
  graph.maximumGraphBounds = new mxRectangle(0, 0, graph.keySize.width * graph.COL_PER_PAGE * graph.MAX_PAGE, graph.keySize.height * graph.ROW_PER_PAGE);

  //console.log(graph);
  graph.container.style.height = (graph.keySize.height * graph.ROW_PER_PAGE) + 20 + 'px';
  //상품영역 초기화할 때 위치 지정 - 그룹영역의 크기에 따라
  if(!graph.isGroup && graph.group != null) {
    graph.container.style.top = (graph.group.keySize.height * graph.group.ROW_PER_PAGE) + 'px';
  }
  
  //좌우 이동 버튼 위치 초기화
  graph.container.scrollLeft = 0;
  var elt = null;
  if(graph.isGroup) {
    elt = document.getElementById('pagingShapeGroup');
  }
  else {
    elt = document.getElementById('pagingShapeProd');
  }
  if(elt != null) {
    elt.style.left = '320px';
    elt.style.top = (graph.keySize.height * (graph.ROW_PER_PAGE-1)) + 'px';
  }
  //console.log(graph);
};

//상품 영역 레이어 활성화 처리
Graph.prototype.switchLayer = function(layer) {
  var prod = this;
//var switchLayer = function(layer) {
  var model = prod.getModel();
  //선택된 영역이 셀인 경우 해당 셀에 해당하는 상품 레이어 활성화
  var layerCount = model.getChildCount(model.root);
  //모든 레이어 visible false
  for(var layerIdx = 0; layerIdx < layerCount; layerIdx++) {
    (mxUtils.bind(this, function(visibleLayer) {
      //console.log(visibleLayer);
      model.setVisible(visibleLayer, false);
    }))(model.getChildAt(model.root, layerIdx));
  }
  
  prod.setDefaultParent(layer);
  //console.log(prod);
  //클릭이벤트 - 선택된 레이어 visible true
  model.setVisible(layer, true);

};


/**
 * 상품 그룹 영역 초기화
 */
Graph.prototype.initGroupArea = function(prod) {
  
  var graph = this;

  //그룹영역은 2줄로 초기화
  graph.initValue(2);
  graph.isGroup = true;
  
  //상품 그룹 영역에 새로운 그룹 생성
  var createGroup = function(x, y) {
    var parent = graph.getDefaultParent();
    var nextId = graph.nextId;
    //console.log(nextId);
    if (graph.isEnabled()) {
      graph.model.beginUpdate();
      try {
        graph.insertVertex(parent,
            (graph.groupPrefix + graph.nextId),
            graph.createKeyObject(mxResources.get('groupName')),
            x, y,
            graph.keySize.width, graph.keySize.height); 
      }
      finally {
        graph.model.endUpdate();
      }
    }
    
    graph.nextId++;
    //console.log(graph);
    return nextId;
  };
  
  //상품 영역에 새로운 레이어 생성
  var createLayer = function(index) {
    var layer = null;
    var layerIndex = graph.groupPrefix + index;
    //console.log(layerIndex);
    layer = prod.addCell(new mxCell(layerIndex), prod.model.root, index);
    return layer;
  };
  
  
  //선택된 그룹을 삭제할 수 있도록 overlay 생성
  graph.addListener(mxEvent.CLICK, function(sender, evt) {
    //console.log(graph);
    var cell = evt.getProperty('cell'); 
    if (cell != null) {
      var overlays = graph.getCellOverlays(cell);
      if (overlays == null) {
        // Creates a new overlay with an image and a tooltip
        var overlay = new mxCellOverlay(
            new mxImage(IMAGE_PATH + '/delete.gif', 16, 16),
            'Overlay tooltip',
            mxConstants.ALIGN_RIGHT,
            mxConstants.ALIGN_TOP,
            new mxPoint(-10, 10)
            );

        // Installs a handler for clicks on the overlay
        overlay.addListener(mxEvent.CLICK, function(sender, evt2)
        {
          //상품영역 레이어 삭제
          var nextId = cell.id.substr(1);
          var model = prod.getModel();
          var layer = model.getCell(nextId);
          prod.removeCells([layer]);
          //그룹영역 셀 삭제
          graph.removeCells([cell]);
        });
        
        // Sets the overlay for the cell in the graph
        graph.addCellOverlay(cell, overlay);
      }
      else
      {
        graph.removeCellOverlays(cell);
      }
    }
  });
  //override 마우스 이벤트 - 그룹, 상품 영역
  graph.addMouseListener({
    //상품 그룹 영역 마우스 클릭 시 해당 상품 레이어 보이기
    //상품 레이어가 없을 경우 새로 생성
    //그룹과 상품영역은 id로 연결
    mouseDown: function(sender, me)
    {
      if(me.state == null ) {
        //선택된 상품그룹 영역이 셀이 아닌 경우에는 해당 영역에 새로운 그룹생성
        var x = parseInt(me.getGraphX()/graph.keySize.width) * graph.keySize.width;
        var y = parseInt(me.getGraphY()/graph.keySize.height) * graph.keySize.height;
        var currId = createGroup(x, y);
        var layer = createLayer(currId);
        prod.switchLayer(layer);
      }
      else {
        //선택된 레이어를 기본값으로 설정
        var cell = me.state.cell;
        //console.log(cell);
        //상품그룹 터치키 클릭 시 처리
        if (mxUtils.isNode(cell.value) && cell.value.nodeName.toLowerCase() == 'userobject') {
          var currId = cell.id.substr(1);
          var model = prod.getModel();
          var layer = model.getCell(currId);
          prod.switchLayer(layer);
        }
      }
    },
    mouseMove: function() { },
    mouseUp: function() { }
  });
  
  //기본 상품 그룹 생성
  if (prod.isEnabled() && graph.isEnabled()) {
    prod.model.beginUpdate();
    graph.model.beginUpdate();
    try {
      var currId = createGroup(0, 0);
      var model = prod.getModel();
      var layer = model.getCell(currId);
      layer.value = prod.groupPrefix + '1';
      prod.switchLayer(layer);
    }
    finally {
      graph.model.endUpdate();
      prod.model.endUpdate();
    }
  }
  
  //console.log(graph);
};

/**
 * UNDO/REDO 이벤트 생성 - 상품영역만 적용
 * - 상품그룹 생성 시 여러 동작을 하여 불필요한 history가 생성되었음
 */
Graph.prototype.createUndoManager = function(graph) {
  
  var undoManager = new mxUndoManager();
  //console.log(undoManager);
  var listener = function(sender, evt) {
    undoManager.undoableEditHappened(evt.getProperty('edit'));
    //console.log(undoManager);
  };
  graph.getModel().addListener(mxEvent.UNDO, listener);
  graph.getView().addListener(mxEvent.UNDO, listener);
  
  return undoManager;
};
/**
 * 키보드 이벤트 생성
 * - 키보드 이벤트를 각각 graph에 적용하는 것이 기능 상 불가능 한것으로 확인.
 */
Graph.prototype.createKeyHandler = function(graph) {
  var keyHandler = new mxKeyHandler(graph);
  keyHandler.bindKey(46, function(evt) {
    graph.escape();
    var cells = graph.getDeletableCells(graph.getSelectionCells());
    
    if (cells != null && cells.length > 0) { 
      var parents = graph.model.getParents(cells);
      graph.removeCells(cells);
      
      // Selects parents for easier editing of groups
      if (parents != null) {
        var select = [];
        for (var i = 0; i < parents.length; i++) {
          if ( graph.model.contains(parents[i]) && graph.model.isVertex(parents[i]) ) {
            select.push(parents[i]);
          }
        }
        graph.setSelectionCells(select);
      }
    }
  });

  //TODO 상품그룹에서 상품레이어 처리 시 2개의 트랜잭션 처리되는 문제. 일단 주석 처리
  //Ctrl + z
  keyHandler.bindControlKey(90, function(evt) { graph.undoManager.undo() });
  //Ctrl + Shift + z
  keyHandler.bindControlShiftKey(90, function(evt) { graph.undoManager.redo() });
  
  return keyHandler;
};


/**
 * 상품 영역 고유 특성 처리
 */
Graph.prototype.initProdArea = function(group) {
  
  var graph = this;
  this.group = group;

  //console.log(graph);
  //상품영역은 6줄로 초기화
  graph.initValue(6);
  graph.isGroup = false;
  
  //상품 영역에만 UNDO, 키 핸들러 추가
  this.undoManager = this.createUndoManager(graph);
  var rubberband = new mxRubberband(graph);
  this.keyHandler = this.createKeyHandler(graph);
  //console.log(graph);

};
/**
 * 터치키 박스 및 내용 생성
 */
Graph.prototype.createKeyObject = function(label, price) {

  var graph = this;

  // Creates a user object that stores the state
  var doc = mxUtils.createXmlDocument();
  var obj = doc.createElement('UserObject');
  obj.setAttribute('label', label);
  if(price != null ) {
    obj.setAttribute('price', price);
  }
  return obj;
};


/**
 * 그리드 사이즈 만큼 이동하면서 셀의 위치 찾기
 */
Graph.prototype.findPosition = function(pt) {
  var graph = this;
  var cntFind = 0;

  var cx = (graph.container.clientWidth) * graph.MAX_PAGE;
  var cy = graph.container.clientHeight;
  var px = parseInt(pt.x/graph.keySize.width) * graph.keySize.width;
  var py = parseInt(pt.y/graph.keySize.height) * graph.keySize.height;
  var lastX = (graph.keySize.width * graph.COL_PER_PAGE * graph.pageNo) - graph.keySize.width; 
  var lastY = (graph.keySize.height * graph.ROW_PER_PAGE) - graph.keySize.height; 
  
  for(var posY = py; posY < cy; posY += graph.keySize.height) {
    for(var posX = px; posX < cx; posX += graph.keySize.width) {
      //마지막 셀은 페이지 이동을 위해 미사용 처리 필요
      if(posX >= lastX && posY >= lastY) {
        return null
      }
      if (graph.getCellAt((posX+1), (posY+1)) == null) {
        return new mxPoint(posX, posY);
      }
      //긴급탈출
      if(cntFind++ > 30) return null;
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
/**
 * 기능 패널 초기화
 */
Format.prototype.init = function() {

  this.update = mxUtils.bind(this, function(sender, evt) {
    //console.log(sender);
    this.graph = sender.graph;
    if(sender.container != null) {
      this.graph = sender;
    }
    this.refresh();
  });
  
  this.clear();
  
  //오른쪽 설정 영역을 로딩 시점에 보이게 할 때 활성화
  //this.refresh();
};

/**
 * 설정 패널 생성
 */
Format.prototype.clear = function() {
  
  this.container.innerHTML = '';
  
  //console.log('refresh');
  var div = document.createElement('div');
  div.style.whiteSpace = 'nowrap';
  div.style.color = 'rgb(112, 112, 112)';
  div.style.textAlign = 'left';
  div.style.cursor = 'default';
  
  var label = document.createElement('div');
  label.style.border = '1px solid #c0c0c0';
  label.style.borderWidth = '0px 0px 1px 0px';
  label.style.textAlign = 'center';
  label.style.fontWeight = 'bold';
  label.style.overflow = 'hidden';
  label.style.display = (mxClient.IS_QUIRKS) ? 'inline' : 'inline-block';
  label.style.paddingTop = '4px';
  label.style.height = (mxClient.IS_QUIRKS) ? '34px' : '25px';
  label.style.width = '100%';
  this.container.appendChild(div);

  //console.log(this.container);
  
  //"설정" 라벨
  mxUtils.write(label, mxResources.get('labelFunction'));
  div.appendChild(label);

  //초기화/저장 버튼
  div.appendChild(this.addSaveBtn());
  
};
/**
 * 화면 새로 그리기
 */
Format.prototype.refresh = function() {

  //상단 라벨, 초기화/저장 버튼
  this.clear();
  
  //셀 배경색 채우기
  this.fillCellColor();
  
  //폰트 설정
  this.fontStyle();
  
};


/**
 * 공통 제목 생성 함수
 */
Format.prototype.createTitle = function(title)
{
  var span = document.createElement('span');
  span.style.whiteSpace = 'nowrap';
  span.style.overflow = 'hidden';
  span.style.width = '100px';
  span.style.fontWeight = 'bold';
  mxUtils.write(span, title);
  return span;
};
/**
 * 공통 패널 생성 함수
 */
Format.prototype.createPanel = function()
{
  var div = document.createElement('div');
  div.style.padding = '12px 0px 12px 18px';
  div.style.borderBottom = '1px solid #c0c0c0';
  return div;
};

/**
 * 공통 패널 생성 함수
 */
Format.prototype.createWijmoContainer = function(id)
{
  var div = document.createElement('div');
  div.id = id;
  div.style.width = '150px';
  div.style.margin = '0px 0px 0px 10px';
  return div;
};

/**
 * 초기화/저장 버튼
 */
Format.prototype.addSaveBtn = function()
{
  var div = this.createPanel();
  
  var btn = mxUtils.button(mxResources.get('initBtn'), mxUtils.bind(this, function(evt) {
    //this.editorUi.actions.get('open').funct();
    this.open(false);
  }));
  
  btn.setAttribute('title', mxResources.get('initBtn'));
  btn.style.width = '98px';
  btn.style.marginRight = '5px';
  btn.style.marginBottom = '2px';
  div.appendChild(btn);

  btn = mxUtils.button(mxResources.get('saveBtn'), mxUtils.bind(this, function(evt) {
    //this.editorUi.actions.get('save').funct();
    this.save(this.touchkey.group, this.touchkey.prod);
  }));
      
  btn.setAttribute('title', mxResources.get('saveBtn'));
  btn.style.width = '98px';
  btn.style.marginRight = '0px';
  btn.style.marginBottom = '2px';
  div.appendChild(btn);

  return div;
};


/**
 * 셀 배경색 채우기
 */
Format.prototype.fillCellColor = function() {
  var graph = this.graph;

  //라벨 설정
  var div = this.createPanel();
  div.appendChild(this.createTitle(mxResources.get('fillCellColor')));
  
  //wijmo 컴포넌트 추가
  div.appendChild(this.createWijmoContainer('fillColor'));
  //format 컨테이너에 추가
  this.container.appendChild(div);

  //console.log(graph);
  var cells = graph.getSelectionCells();
  var state = graph.view.getState(cells);
  var init;
  if (state != null) {
    init = mxUtils.getValue(state.style, mxConstants.STYLE_FILLCOLOR, null);
  }
  var theInputColor = new wijmo.input.InputColor('#fillColor', {
    placeholder: 'Select the color',
    value: init,
    valueChanged: function(s, e) {
      graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, s.value, graph.getSelectionCells());
    }
  });
  
};

/**
 * 폰트 설정
 */
Format.prototype.fontStyle = function() {
  var graph = this.graph;

  //"폰트" 라벨 설정
  var div = this.createPanel();
  
  div.appendChild(this.createTitle(mxResources.get('font')));
  mxUtils.br(div);

  /**
   * 폰트 색상 설정 시작
   */
  //wijmo 컴포넌트 추가
  div.appendChild(this.createWijmoContainer('fontColor'));
  //format 컨테이너에 추가
  this.container.appendChild(div);

  var cells = graph.getSelectionCells();
  var state = graph.view.getState(cells);
  var init;
  if (state != null) {
    init = mxUtils.getValue(state.style, mxConstants.STYLE_FONTCOLOR, null);
  }
  var theInputColor = new wijmo.input.InputColor('#fontColor', {
    placeholder: 'Select the color',
    value: init,
    valueChanged: function(s, e) {
      graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, s.value, cells);
    }
  });
  mxUtils.br(div);

  /**
   * 폰트 크기 설정 시작
   */
  //wijmo 컴포넌트 추가
  div.appendChild(this.createWijmoContainer('fontSize'));
  //format 컨테이너에 추가
  this.container.appendChild(div);

  var cells = graph.getSelectionCells();
  var state = graph.view.getState(cells);
  var init = 10;
  if (state != null) {
    init = mxUtils.getValue(state.style, mxConstants.STYLE_FONTSIZE, null);
  }
  
  var theInputNumber = new wijmo.input.InputNumber('#fontSize', {
    format: 'n0',
    step: 1,
    min: 8,
    max: 15,
    value: init,
    valueChanged: function(s, e) {
      graph.setCellStyles(mxConstants.STYLE_FONTSIZE, s.value, cells);
    }
  });

};

/**
 * 기존 구성 조회
 */
Format.prototype.open = function(isLoad)
{
  var group = this.touchkey.group;
  var prod = this.touchkey.prod;
  
  //open
  var reqGroup = mxUtils.post(TOUCHKEY_OPEN_URL, '',
      mxUtils.bind(this, function(req) {
        //var enabled = req.getStatus() != 404;
        if( req.getStatus() == 200 ) {
          var jsonStr = JSON.parse(req.getText());
          var xmlStr = jsonStr.data;
          try {
            var xmlArr = xmlStr.split("|");
            var groupXml = mxUtils.parseXml(xmlArr[0]); 
            //console.log(groupXml);
            this.setGraphXml(group, groupXml.documentElement);
            
            var prodXml = mxUtils.parseXml(xmlArr[1]); 
            //console.log(prodXml);
            this.setGraphXml(prod, prodXml.documentElement);
          }
          catch (e) {
            //mxUtils.alert(mxResources.get('invalidOrMissingFile') + ': ' + e.message);
          }
          if(!isLoad) {
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
Format.prototype.setGraphXml = function(graph, node) {
  
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
        
        //console.log(node);
        //로드 후 변수 셋팅
        graph.initValue(parseInt(node.getAttribute('rowPerPage')));
        //console.log(graph);
      }
    }
    else {
      throw { 
          message: mxResources.get('cannotOpenFile'), 
          node: node,
          toString: function() { return this.message; }
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
Format.prototype.save = function()
{
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
  
  var enc = new mxCodec(mxUtils.createXmlDocument());
  var node = enc.encode(group.getModel());
  var xml = mxUtils.getPrettyXml(node);
  mxLog.show();
  mxLog.write(xml);
  var enc = new mxCodec(mxUtils.createXmlDocument());
  var node = enc.encode(prod.getModel());
  var xml = mxUtils.getPrettyXml(node);
  //mxLog.show();
  mxLog.write(xml);
  
  
  var node = null;
  var enc = new mxCodec(mxUtils.createXmlDocument());
  node = enc.encode(group.getModel());
  //페이지당 줄 수를 XML에 셋팅해 두었다가 로딩 시 사용
  node.setAttribute('rowPerPage', group.ROW_PER_PAGE);
  var xmlGroup = mxUtils.getXml(node);
  //console.log(xmlGroup);
  
  var node = null;
  var enc = new mxCodec(mxUtils.createXmlDocument());
  node = enc.encode(prod.getModel());
  //페이지당 줄 수를 XML에 셋팅해 두었다가 로딩 시 사용
  node.setAttribute('rowPerPage', prod.ROW_PER_PAGE);
  var xmlProd = mxUtils.getXml(node);
  //console.log(xmlProd);

  var xml = encodeURIComponent(xmlGroup) + '|' + encodeURIComponent(xmlProd);
  
  try {
    if (xml.length < MAX_REQUEST_SIZE) {
      var onload = function(req) {
        mxUtils.alert(mxResources.get('saved'));
      }
      var onerror = function(req) {
        mxUtils.alert('Error');
      }
      //console.log(encodeURIComponent(xml));
      //new mxXmlRequest(SAVE_URL, '&xml=' + encodeURIComponent(xml)).simulate(document, '_blank');
      new mxXmlRequest(TOUCHKEY_SAVE_URL, 'xml=' + xml).send(onload, onerror);
    }
    else {
      mxUtils.alert(mxResources.get('drawingTooLarge'));
      //mxUtils.popup(xml);
      return;
    }
    //setModified(false);
    //setFilename(name);
    //this.updateDocumentTitle();
  }
  catch (e) {
    mxUtils.alert(mxResources.get('errorSavingFile'));
  }

};





/**
* 상품 조회 그리드 처리
*/
function Grid(prod) {
  
  this.graph = prod;
  
  this.init();
}
/**
 * 그리드 영역 초기화
 */
Grid.prototype.init = function() {

  this.grid = this.makeGrid();
  this.makeDragSource();

};
/**
 * 그리드 생성
 */
Grid.prototype.makeGrid = function() {
  // create some random data
  function getData() {
    var goods = '상품1,상품2,상품3'.split(','),
        data = [];
    for (var i = 0; i < goods.length; i++) {
        data.push({
            //'chk':false,
            'prodNm': goods[i],
            'saleUprc': Math.round(Math.random() * 20000)
        });
    }
    return data;
  }

  function areAllRowsSelected(flex) {
    for (var i = 0; i < flex.rows.length; i++) {
        if (!flex.rows[i].isSelected) return false;
    }
    return true;
  }

  //그리드 생성
  var flex = new wijmo.grid.FlexGrid('#theGrid', {
    itemsSource: getData(),
    selectionMode: 'ListBox',
    allowDragging: 'None',
    isReadOnly: true
  });
  flex.select(-1, -1);
  
  //커스텀 이벤트 처리
  flex.hostElement.addEventListener('mousedown', function (e) {
    var ht = flex.hitTest(e);

    // allow sorting/resizing/dragging
    if (ht.cellType == wijmo.grid.CellType.ColumnHeader) {
        return;
    }
    
    // toggle row selection when clicking row headers
    if (ht.cellType == wijmo.grid.CellType.RowHeader) {
        flex.rows[ht.row].isSelected = !flex.rows[ht.row].isSelected;
    }

    // toggle all rows selection when clicking top-left cell
    if (ht.cellType == wijmo.grid.CellType.TopLeft) {
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
      if (e.panel == flex.rowHeaders) {
          sel = flex.rows[e.row].isSelected;
          wijmo.toggleClass(e.cell, 'wj-state-multi-selected', sel);
      }
  
      // apply selected state to top-left cell
      if (e.panel == flex.topLeftCells) {
          sel = areAllRowsSelected(flex);
      }
  
      // show checkboxes on row header and top-left cells
      if (sel != null && e.col == 0) {
        e.cell.innerHTML = '<span class="wj-glyph-check" style="opacity:' + (sel ? 1 : .25) + '"></span>';
      }
  });
  
  return flex;
};
/**
 * Cell을 드래그 할수 있도록 처리
 */
Grid.prototype.makeDragSource = function() {

  var graph = this.graph;
  var grid = this.grid;
  
  //드래그 이벤트 생성
  //function makeDragEvent(graph, grid, cell, r) {
  var makeDragEvent = mxUtils.bind(this, function(graph, grid) {

    var dropEvent = function(graph, evt, cell, x, y) {

      var parent = graph.getDefaultParent();
      var model = graph.getModel();
      
      //Drop 할 때 오브젝트 생성
      var pt = graph.getPointForEvent(evt);
      
      
      for(var selected = 0; selected < grid.selectedItems.length; selected++ ) {
        
        var pos = graph.findPosition(pt);
        if( pos == null) {
          mxEvent.consume(evt);
          return;
        }
        
        //Drop 된 포지션과 다음 포지션에 터치키 생성
        var item = grid.selectedItems[selected];
        model.beginUpdate();
        try
        {
          graph.insertVertex(parent,
              graph.prodPrefix + graph.nextId,
              graph.createKeyObject(item.prodNm, item.saleUprc),
              pos.x, pos.y,
              graph.keySize.width, graph.keySize.height);
        }
        finally
        {
          model.endUpdate();
        }
        //console.log(graph);
        graph.nextId++;
      }
    }

    //드래그할 항목 생성
    var previewElt = document.createElement('div');
    previewElt.style.border = 'dashed black 1px';
    previewElt.style.width = graph.keySize.width + 'px';
    previewElt.style.height = graph.keySize.height + 'px';
    //console.log(grid);
    var ds = mxUtils.makeDraggable(grid.cells.hostElement, graph, dropEvent, previewElt, -(graph.keySize.width/2), -(graph.keySize.height/2));
    ds.highlightDropTargets = true;
  })(graph, grid);
  //==makeDragEvent
  //}
  

};
/**
 * 로딩 시점에 초기화
 * @returns
 */
(function() {
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

