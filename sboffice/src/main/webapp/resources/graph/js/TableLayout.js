
//그리드의 크기
mxGraph.prototype.gridSize = 10;
mxGraph.prototype.foldingEnabled = false;
mxGraphView.prototype.gridColor = '#e0e0e0';

//지정된 영역에만 구성요소를 넣을 수 있도록 처리 
mxGraph.prototype.allowNegativeCoordinates = false;

//셀 가이드 사용 여부
mxGraphHandler.prototype.guidesEnabled = true;


/**
 * 메인 Class
 */
TableLayout = function(container, themes) {
  mxEventSource.call(this);
  this.container = container || document.body;
  
  //그래프 객체 생성
  this.graph = new Graph(this.container, null, null, null, themes);
  
  //왼쪽 구성요소
  this.comp = new Sidebar(this.graph);

  //상단 층설정 영역 생성
  this.floor = new Floor(this.graph);
  
  //오른쪽 설정 영역 생성
  this.format = new Format(this);
  
  this.init();
  
  //화면 오픈 스플래시 이미지(부가)
  var splash = document.getElementById('splash');
  if (splash != null) {
    try {
      mxEvent.release(splash);
      mxEffects.fadeOut(splash, 100, true);
    }
    catch (e) {
      // mxUtils is not available (library not loaded)
      splash.parentNode.removeChild(splash);
    }
  }
  
};

//Extends mxEditor
mxUtils.extend(TableLayout, mxEventSource);

//변수 생성
TableLayout.prototype.graph = null;
TableLayout.prototype.comp = null;
TableLayout.prototype.layer = null;
TableLayout.prototype.format = null;

//배경색
TableLayout.prototype.defaultBackgroundColor = '#ffffff';

//배경이미지
TableLayout.prototype.containerBackgroundImage = 'url(' + IMAGE_PATH + '/grid.gif' + ')';



/**
 * 메인 - 초기화
 */
TableLayout.prototype.init = function() {
  

  //그래프 영역의 배경 이미지 설정
  this.container.style.backgroundImage = this.containerBackgroundImage;
  this.container.style.backgroundColor = this.defaultBackgroundColor; 

  //마우스 오른쪽 클릭 - 컨텍스트 메뉴
  //mxEvent.disableContextMenu(this.container);

  //매장영역에서 이벤트 발생 시 설정영역을 새로 그려주기 위한 이벤트 핸들러
  this.graph.getSelectionModel().addListener(mxEvent.CHANGE, this.format.update);
  this.graph.addListener(mxEvent.EDITING_STARTED, this.format.update);
  this.graph.addListener(mxEvent.EDITING_STOPPED, this.format.update);
  this.graph.getModel().addListener(mxEvent.CHANGE, this.format.update);
  this.graph.addListener(mxEvent.ROOT, this.format.update);

  //서버의 초기 설정 로드
  var openResult = this.format.open(true);
  
  //TODO 오픈 실패 or 기존 설정 정보가 없을 경우 기본 구성 로딩
  //if(!openResult) {
    //console.log('')
  //}
  
};
/**
 * 메인 - 기존 설정 opn 시 값 초기화 
 */
TableLayout.prototype.initValue = function() {
  //층 설정 툴바 초기화
  this.floor.refresh();
  
  //그래프 변수값 초기화
  this.graph.initValue();
  
};

/**
* 왼쪽 매장구성요소 처리
*/
Sidebar = function(graph) {
  
  this.container = document.getElementById('component');
  this.graph = graph;
  
  this.init();
}
/**
 * 매장구성요소 영역 초기화
 */
Sidebar.prototype.init = function() {
  
  this.toolbar = new mxToolbar(this.container);
  this.toolbar.enabled = false;

  toolbar = this.toolbar;
  
  var tableSize = this.graph.defaultTableSize;
  
  //ts2는 xml과 message 파일에 정의되어야 함
  //tblTypeFg:1:사각,2:원탁,3:포장,4:배달,5:최소
  this.addToolbarIcon('ts2','tblTypeFg=1;tblSeatCnt=2;', STENCIL_PATH + '/ts2.png', tableSize.width, tableSize.height);
  toolbar.addLine();
  //this.addSidebarIcon('ts2', '/tableSquare2.jpg');
};

/**
 * 왼쪽 매장구성요소 아이콘 추가
 */
Sidebar.prototype.addToolbarIcon = function(type, style, icon, w, h) {
  var graph = this.graph;
  var toolbar = this.toolbar;

  var vertex = new mxCell(null, new mxGeometry(0, 0, w, h), type + ';' + style);
  vertex.setVertex(true);

  //Drop 이벤트 처리
  var funct = function(graph, evt, cell) {
    graph.stopEditing(false);
    
    var parent = graph.getDefaultParent();
    var pt = graph.getPointForEvent(evt);
    var v1 = graph.getModel().cloneCell(vertex);
    //v1.value = graph.getModel().getChildCount(parent) + 1;
    //console.log(pt.x + ',' + pt.y);
    //미리보기 위치와 맞추기 위해 미리보기 포인트 만큼 마이너스 처리 
    v1.geometry.x = pt.x - (w/2);
    v1.geometry.y = pt.y - (h/2);
    
    //라벨 추가
    //TODO 테이블속성 기능에서 테이블명 위치를 가져와 동일 위치로 지정할 때 사용(relative, 0~1, 0~1)
    var label = graph.insertVertex(v1, null, graph.getModel().getChildCount(parent) + 1, 0.1, 0.1, 30, 10, 'label', true);
    
    graph.setSelectionCells(graph.importCells([v1], 0, 0));
    
  }

  // Creates the image which is used as the drag icon (preview)
  var img = toolbar.addMode(null, icon, funct);
  img.title = mxResources.get(type);
  
  //DnD 활성화
  var ds = mxUtils.makeDraggable(img, graph, funct, img, -(w/2), -(h/2));
  ds.setGuidesEnabled(true);
};

/**
* 상단 층설정
*/
Floor = function(graph) {
  
  this.container = document.getElementById('floor');
  this.graph = graph;
  
  this.toolbar = new mxToolbar(this.container);
  this.toolbar.enabled = false;
  
  this.refresh();
}
/**  툴바 구분자 */
Floor.prototype.SEPARATOR_IMG = mxClient.imageBasePath + '/separator.gif';

/**
 * 툴바 층설정 버튼 생성
 */
Floor.prototype.clear = function() {
  
  var floor = this;
  var graph = this.graph;
  var toolbar = this.toolbar;
  
  //팝업 변수
  var wnd = null;
  
  this.container.innerHTML = '';
  
  var model = graph.getModel();
  var layerCount = model.getChildCount(model.root);
  
  //그리드 row 수에 따라 레이어 높이 변경
  var getLayerHeight = function(count) {
    return ((count+3)*30)+50;
  };
  
  //TODO 그룹구분 설정
  //그룹 등록 시 선택한 값을 레이어에 등록해 두었다가 팝업 재 생성 시 사용
  var tblGrpFg = [
    {id:'1', name:mxResources.get('tblGrpFgNormal')},
    {id:'2', name:mxResources.get('tblGrpFgTogo')},
    {id:'3', name:mxResources.get('tblGrpFgDelivery')}
  ];
  var tblGrpFgMap = new wijmo.grid.DataMap(tblGrpFg, 'id', 'name');
  
  //층설정 레이어 생성 - Wijmo 그리드 포함
  var makePopup = function(btn) {
    var data = [];
    for (var i = 0; i < layerCount; i++) {
      (mxUtils.bind(this, function(layer) {
        data.push({
            id: layer.id,
            name: layer.value||mxResources.get('background'),
            tblGrpFg: graph.getCellStyle(layer)['tblGrpFg'] || '1',
            layer: layer //graph Cell 정보 추가, add/remove/rename에 사용!
          });
      }))(model.getChildAt(model.root, i));
    }
    
    var div = document.createElement('div'); 
    div.id = 'floorGrid';
    graph.container.appendChild(div);
    
    var floorGrid = new wijmo.grid.FlexGrid('#floorGrid', {
      itemsSource: data,
      allowAddNew: true,
      allowDelete: true,
      //newRowAtTop: true,
      selectionMode: 'Row',
      columns: [
        {binding: 'id', isReadOnly: true, visible: false},
        {binding: 'name', header: mxResources.get('floorName'), isRequired: true},
        {binding: 'tblGrpFg', header: mxResources.get('tblGrpFg'), dataMap:tblGrpFgMap},
        {binding: 'layer', visible: false}
      ],
      rowAdded: function(s, e) {
        //레이어 높이 조정
        wnd.setSize(300, getLayerHeight(s.rows.length-1));
        
        if (graph.isEnabled()) {
          graph.model.beginUpdate();
          try {
            var layer = graph.addCell(new mxCell(), graph.model.root);
            //graph.setDefaultParent(layer);
            s.rows[e.row].dataItem.tblGrpFg = '1';
            graph.setCellStyles('tblGrpFg', s.rows[e.row].dataItem.tblGrpFg, [layer]);
            s.rows[e.row].dataItem.layer = layer;
          }
          finally {
            graph.model.endUpdate();
          }
        }
      },
      rowEditEnded: function(s, e) {
        //console.log(s.rows[e.row]);
        var layer = s.rows[e.row].dataItem.layer;
        var layerName = s.rows[e.row].dataItem.name;
        var tblGrpFg = s.rows[e.row].dataItem.tblGrpFg;
        var model = graph.getModel();
        if (graph.isEnabled() && layerName != null) {
          model.beginUpdate();
          try {
            model.setValue(layer, layerName);
            graph.setCellStyles('tblGrpFg', tblGrpFg, [layer]);
          }
          finally {
            model.endUpdate();
          }
        }
      },
      deletingRow: function(s, e) {
        //레이어 높이 조정
        wnd.setSize(300, getLayerHeight(s.rows.length-1));
        var layer = s.rows[e.row].dataItem.layer;
        //console.log(layer);
        if (graph.isEnabled()) {
          graph.model.beginUpdate();
          try {
            var index = graph.model.root.getIndex(layer);
            graph.removeCells([layer], false);
            
            // Creates default layer if no layer exists
            if (graph.model.getChildCount(graph.model.root) == 0) {
              graph.model.add(graph.model.root, new mxCell());
              graph.setDefaultParent(null);
            }
            else if (index > 0 && index <= graph.model.getChildCount(graph.model.root)) {
              graph.setDefaultParent(graph.model.getChildAt(graph.model.root, index - 1));
            }
            else {
              graph.setDefaultParent(null);
            }
          }
          finally {
            graph.model.endUpdate();
          }
        }
      }

    });

    //TODO 수정/삭제 버튼 추가
    //http://demos.wijmo.com/5/PureJS/LearnWijmo/LearnWijmo/#hkxco8kb
    
    return floorGrid;
  };
  
  //기어 버튼 클릭 시 층설정 팝업 오픈
  toolbar.addItem(mxResources.get('floorConfig'), IMAGE_PATH + '/gear.png', function(evt){

    //레이어 생성
    var floorGrid = makePopup(this);
    
    //팝업 기능
    var div = document.createElement('div');
    div.appendChild(floorGrid.hostElement);
    div.style.padding = '4px';
    
    var divBtn = document.createElement('div');
    //취소 버튼
    var cancelBtn = mxUtils.button(mxResources.get('cancelBtn'), function() {
      wnd.hide();
    });
    cancelBtn.className = 'geBtn';
    //TODO CSS 우선순위 확인 후 삭제 필요
    cancelBtn.style.background = '#f5f5f5';
    divBtn.appendChild(cancelBtn);
    
    //적용 버튼
    var applyBtn = mxUtils.button(mxResources.get('applyBtn'), function() {
      
      //그리드에 있는 층 설정으로 레이어 생성
      //층버튼 다시 그리기
      floor.refresh();
      wnd.hide();
    });
    applyBtn.className = 'geBtn gePrimaryBtn';
    //TODO CSS 우선순위 확인 후 삭제 필요(다자인)
    applyBtn.style.backgroundColor = '#4d90fe';
    applyBtn.style.backgroundImage = 'linear-gradient(#4d90fe 0px,#4787ed 100%)';
    divBtn.appendChild(applyBtn);
    
    div.appendChild(divBtn);
    //div.appendChild(cancelBtn);
    //div.appendChild(applyBtn);
    
    //팝업 설정 및 show
    var cx = graph.container.clientWidth / 2;
    var cy = graph.container.clientHeight / 2;
    wnd = new mxWindow(mxResources.get('floorConfig'), div, cx, cy, 300, getLayerHeight(layerCount), false);
    wnd.setMaximizable(false);
    wnd.setScrollable(false);
    wnd.setResizable(false);
    wnd.setClosable(true);
    wnd.setVisible(true);
    
    
  });
  
  //툴바에 분리 이미지 추가
  toolbar.addSeparator(this.SEPARATOR_IMG);
  
};
/**
 * 화면 새로 그리기 - 층설정이 변경되었을 때 마다
 */
Floor.prototype.refresh = function() {

  var floor = this;
  var graph = this.graph;
  var toolbar = this.toolbar;
  
  //초기화, 층설정 버튼
  this.clear();
  
  //층버튼 생성
  var model = graph.getModel();
  var layerCount = model.getChildCount(model.root);
  
  //모든 층을 visible = false로 하고 선택한 층만 true로 변경
  var visibleLayer = function(layer) {
    //클릭이벤트 - 모든 레이어 visible false
    for(var layerIdx = 0; layerIdx < layerCount; layerIdx++) {
      (mxUtils.bind(this, function(layerCell) {
        //console.log(layerCell);
        model.setVisible(layerCell, false);
      }))(model.getChildAt(model.root, layerIdx));
    }

    //선택된 레이어를 기본값으로 설정
    var index = model.root.getIndex(layer);
    if (index > 0 && index <= model.getChildCount(model.root)) {
      graph.setDefaultParent(layer);
    }
    else {
      graph.setDefaultParent(null);
    }

    //해당레이어의 style을 가져와서 화면 컨테이너에 적용
    var styles = graph.getCellStyle(layer);
    //백그라운드컬러
    graph.container.style.backgroundColor = styles[mxConstants.STYLE_FILLCOLOR];
    
    //백그라운드이미지
    if(styles[mxConstants.STYLE_IMAGE] != null) {
      var img = new mxImage(decodeURIComponent(styles[mxConstants.STYLE_IMAGE]),
          styles[mxConstants.STYLE_IMAGE_WIDTH],
          styles[mxConstants.STYLE_IMAGE_HEIGHT]);
      graph.setBackgroundImage(img);
      graph.view.validateBackgroundImage();
    }
    else {
      graph.setBackgroundImage();
      graph.view.validateBackgroundImage();
    }

    //클릭이벤트 - 선택된 레이어 visible true
    model.setVisible(layer, true);
  };

  //레이어 수 만큼 버튼 생성
  for (var i = 0; i < layerCount; i++) {
    
    (mxUtils.bind(this, function(layer) {
      //레이어 이름으로 버튼 생성
      var layerBtn = mxUtils.button(layer.value||mxResources.get('background'), function() {

        //클릭된 레이어 view 처리
        visibleLayer(layer);
        
        //툴바 초기화로 refresh
        floor.refresh();
      });
      //선택된 레이어 버튼 색상 적용
      if( layer == graph.getDefaultParent() ) {
        layerBtn.className = 'geBtn gePrimaryBtn';
        visibleLayer(layer);
      }
      else {
        layerBtn.className = 'geBtn'; 
      }
        
      this.container.appendChild(layerBtn);
    }))(model.getChildAt(model.root, i));
  }
  
};


/**
* 그래픽 영역
*/
function Graph(container, model, renderHint, stylesheet, themes) {

  mxGraph.call(this, container, model, renderHint, stylesheet);
  this.themes = themes || this.defaultThemes;
  
  var loadStylesheet = function(graph) {
    var node = (graph.themes != null) ? graph.themes[graph.defaultThemeName] :
      (!mxStyleRegistry.dynamicLoading) ? null :
      mxUtils.load(STYLE_PATH + '/tableattr.xml').getDocumentElement();

    if (node != null) {
      var dec = new mxCodec(node.ownerDocument);
      dec.decode(node, graph.getStylesheet());
    }
  };
  this.currentVertexStyle = mxUtils.clone(this.defaultVertexStyle);
  if (stylesheet == null) {
    loadStylesheet(this);
  }
  
}
/**
 * Graph inherits from mxGraph.
 */
mxUtils.extend(Graph, mxGraph);

Graph.prototype.defaultThemes = {};
Graph.prototype.defaultVertexStyle = {};

Graph.prototype.defaultThemeName = 'tablelayout';

//DnD에서 생성되는 크기
Graph.prototype.defaultTableSize = {width:40,height:40};

//배경이미지 - 기본설정
Graph.prototype.graphBackgroundImage = IMAGE_PATH + '/gradient_background.jpg';


/**
 * 영역 초기화
 */
Graph.prototype.init = function() {

  mxGraph.prototype.init.apply(this, arguments);
  
  var graph = this;
  
  //그래프 영역의 배경 색 설정 - 기본설정
  //graph.setBackgroundImage(new mxImage(this.graphBackgroundImage, 600, 640));
  
  //마우스를 영역 밖으로 드래그 했을 때 패닝이 되지 않도록 처리
  graph.setPanning(false);
  
  //Enables HTML labels
  graph.setHtmlLabels(true);
  
  //셀을 이동했을 때 스크롤 금지
  graph.graphHandler.scrollOnMove = false;
  graph.autoScroll = false;

  //UNDO, 키 핸들러 추가
  graph.undoManager = graph.createUndoManager(graph);
  var rubberband = new mxRubberband(graph);
  graph.keyHandler = graph.createKeyHandler(graph);
  //console.log(this);
  
  //구성요소 이동 시 처리
  var mxGraphHandlerMoveCells = mxGraphHandler.prototype.moveCells;
  graph.graphHandler.moveCells = function(cells, dx, dy, clone, target, evt) {
    //대상 셀에 이미 상품이 있을 경우 이동 금지
    //getCellAt에서 gridSize 범위를 가지고 해당 위치에 셀을 가져온다.
    var pt = this.graph.getPointForEvent(evt);
    var cellAt = this.graph.getCellAt(pt.x, pt.y);
    if (cellAt != null) {
      mxEvent.consume(evt);
      return;
    }
    mxGraphHandlerMoveCells.apply(this, arguments);
  };
  
  /* 이하 라벨 텍스트 선택 및 변경에 사용 */
  //graph.vertexLabelsMovable = false;
  //graph.setCellsMovable(false);
  //console.log(graph);
  
  //라벨 삭제 금지 - Del 키는 이벤트 핸들러 때문에 삭제가 됨.
  graph.graphHandler.removeCellsFromParent = false;
  //Autosize labels on insert where autosize=1
  graph.autoSizeCellsOnAdd = true;
  
  //라벨일 때만 이동 금지
  graph.isCellMovable = function(cell) {
    if(cell.style == 'label') {
      return false;
    }
    return this.isCellsMovable();
  };

  //Allows moving of relative cells
  graph.isCellLocked = function(cell) {
    return this.isCellsLocked();
  };
  
  graph.isCellResizable = function(cell) {
    var geo = this.model.getGeometry(cell);
    return geo == null || !geo.relative;
  };

  //Truncates the label to the size of the vertex
  graph.getLabel = function(cell) {
    var label = (this.labelsVisible) ? this.convertValueToString(cell) : '';
    var geometry = this.model.getGeometry(cell);
    
    if (!this.model.isCollapsed(cell) && geometry != null && (geometry.offset == null ||
      (geometry.offset.x == 0 && geometry.offset.y == 0)) && this.model.isVertex(cell) &&
      geometry.width >= 2) {
      var style = this.getCellStyle(cell);
      var fontSize = style[mxConstants.STYLE_FONTSIZE] || mxConstants.DEFAULT_FONTSIZE;
      var max = geometry.width / (fontSize * 0.625);
      if (max < label.length) {
        return label.substring(0, max) + '...';
      }
    }
    return label;
  };

  graph.isWrapping = function(cell) {
    return this.model.isCollapsed(cell);
  };
  //Enables clipping of vertex labels if no offset is defined
  graph.isLabelClipped = function(cell){
    var geometry = this.model.getGeometry(cell);
    return geometry != null && !geometry.relative && (geometry.offset == null ||
      (geometry.offset.x == 0 && geometry.offset.y == 0));
  };
  /* 이상 라벨 텍스트 선택 및 변경에 사용 */

  //그래프가 생성될 때 첫번째 child의 이름 초기값 지정
  graph.getModel().getChildAt(graph.getModel().root, 0).setValue(mxResources.get('background'));

};

/**
 * 초기 데이터 조회 시 변수 초기화
 */
Graph.prototype.initValue = function() {
  var graph = this;

  var model = graph.getModel();
  var parent = graph.getDefaultParent();


};
  
/**
 * Sanitizes the given HTML markup.
 */
Graph.prototype.sanitizeHtml = function(value, editing) {
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
  function idX(id) { return id };
  return html_sanitize(value, urlX, idX);
};

/**
 * 그래프 영역 레이어 활성화 처리
 */
Graph.prototype.switchLayer = function(layer) {
  var graph = this;
  var model = graph.getModel();
  var layerCount = model.getChildCount(model.root);
  //모든 레이어 visible false
  for(var layerIdx = 0; layerIdx < layerCount; layerIdx++) {
    (mxUtils.bind(this, function(visibleLayer) {
      model.setVisible(visibleLayer, false);
    }))(model.getChildAt(model.root, layerIdx));
  }
  
  graph.setDefaultParent(layer);
  //클릭이벤트 - 선택된 레이어 visible true
  model.setVisible(layer, true);

};

/**
 * UNDO/REDO 이벤트 생성 
 */
Graph.prototype.createUndoManager = function(graph) {
  
  var undoManager = new mxUndoManager();
  var listener = function(sender, evt) {
    undoManager.undoableEditHappened(evt.getProperty('edit'));
  };
  graph.getModel().addListener(mxEvent.UNDO, listener);
  graph.getView().addListener(mxEvent.UNDO, listener);
  
  return undoManager;
};
/**
 * 키보드 이벤트 생성
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
  
  //Ctrl + z
  keyHandler.bindControlKey(90, function(evt) { graph.undoManager.undo() });
  //Ctrl + Shift + z
  keyHandler.bindControlShiftKey(90, function(evt) { graph.undoManager.redo() });
  
  return keyHandler;
};
/**
 * 키보드 이벤트 생성
 */
Graph.prototype.removeStyle = function(key) {
};



/**
* 오른쪽 설정 기능 패널
*/
function Format(tablelayout) {
  
  this.container = document.getElementById('format');

  this.main = tablelayout;
  this.graph = tablelayout.graph;
  
  this.init();
}
/**
 * 기능 패널 초기화
 */
Format.prototype.init = function() {

  this.update = mxUtils.bind(this, function(sender, evt) {
    this.refresh();
  });
  
  this.clear();
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
  
  //배경색
  this.backgroundColor();

  //배경이미지
  this.backgroundImage();
};
/**
 * 화면 새로 그리기
 */
Format.prototype.refresh = function() {

  var graph = this.graph;
  
  //상단 라벨, 초기화/저장 버튼
  this.clear();
  
  //셀을 2개 이상 선택 했을 때 정렬 기능 활성화
  var cells = graph.getSelectionCells();
  var model = graph.getModel();
  var vertexCnt = 0;
  for (var i = 0; i < cells.length; i++) {
    if(model.isVertex(cells[i])) {
      vertexCnt++;
    }
  }
  if(vertexCnt > 1) {
    //테이블 정렬
    this.addAlign();
  }
};


/**
 * 공통 제목 생성 함수
 */
Format.prototype.createTitle = function(title) {
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
Format.prototype.createPanel = function() {
  var div = document.createElement('div');
  div.style.padding = '12px 0px 12px 18px';
  div.style.borderBottom = '1px solid #c0c0c0';
  return div;
};

/**
 * 공통 패널 생성 함수
 */
Format.prototype.createWijmoContainer = function(id) {
  var div = document.createElement('div');
  div.id = id;
  div.style.width = '150px';
  div.style.margin = '0px 0px 0px 10px';
  return div;
};

/**
 * 초기화/저장 버튼
 */
Format.prototype.addSaveBtn = function() {
  var div = this.createPanel();
  
  var btn = mxUtils.button(mxResources.get('initBtn'), mxUtils.bind(this, function(evt) {
    this.open(false);
  }));
  
  btn.setAttribute('title', mxResources.get('initBtn'));
  btn.className = 'geBtn';
  btn.style.width = '98px';
  btn.style.marginRight = '5px';
  btn.style.marginBottom = '2px';
  div.appendChild(btn);

  btn = mxUtils.button(mxResources.get('saveBtn'), mxUtils.bind(this, function(evt) {
    this.save();
  }));
      
  btn.setAttribute('title', mxResources.get('saveBtn'));
  btn.className = 'geBtn';
  btn.style.width = '98px';
  btn.style.marginRight = '0px';
  btn.style.marginBottom = '2px';
  div.appendChild(btn);

  return div;
};

/**
 * 그래픽 영역 배경색 설정
 */
Format.prototype.backgroundColor = function() {
  
  var graphContainer = this.main.container;
  var graph = this.graph;
  
  //라벨 설정
  var div = this.createPanel();
  div.appendChild(this.createTitle(mxResources.get('backgroundColor')));
  mxUtils.br(div);
  
  //wijmo 컴포넌트 추가
  div.appendChild(this.createWijmoContainer('backgroundColor'));
  //format 컨테이너에 추가
  this.container.appendChild(div);
  
  var theInputColor = new wijmo.input.InputColor('#backgroundColor', {
    value: graphContainer.style.backgroundColor,
    valueChanged: function(s, e) {
      var layer = graph.getDefaultParent();
      graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, s.value, [layer]);
      graphContainer.style.backgroundColor = s.value; 
    }
  });
};

/**
 * 그래픽 영역 배경 이미지 설정
 */
Format.prototype.backgroundImage = function() {
  
  var graph = this.graph;
  var formatContainer =   this.container;

  //라벨 설정
  var div = this.createPanel();
  div.appendChild(this.createTitle(mxResources.get('backgroundImage')));
  mxUtils.br(div);
  
  //이미지 삭제 버튼
  var btn = mxUtils.button(mxResources.get('initBackgroundImage'), mxUtils.bind(this, function(evt) {
    graph.setBackgroundImage();
    graph.view.validateBackgroundImage();
    //해당 레이어에 이미지 정보 저장
    var layer = graph.getDefaultParent();
    graph.setCellStyles(mxConstants.STYLE_IMAGE, '', [layer]);
  }));
  btn.setAttribute('title', mxResources.get('initBackgroundImage'));
  btn.className = 'geBtn';
  btn.style.width = '98px';
  btn.style.marginRight = '5px';
  btn.style.marginBottom = '2px';
  div.appendChild(btn);

  
  //input image
  var input = document.createElement('input');
  input.setAttribute('type', 'file');
  input.setAttribute('accept', 'image/x-png, image/gif, image/jpeg');
  
  var apply = mxUtils.bind(this, function(evt){
    var canvas = graph.view.canvas; 
    if (canvas.ownerSVGElement != null) {
      canvas = canvas.ownerSVGElement;
    }
    var readURL = function(input) {
      if (input.files && input.files[0]) {
          var reader = new FileReader();
          reader.onload = function (e) {
            var img = new mxImage(e.target.result, graph.container.offsetWidth, graph.container.offsetHeight);
            graph.setBackgroundImage(img);
            graph.view.validateBackgroundImage();
            
            //해당 레이어에 이미지 정보 저장
            var layer = graph.getDefaultParent();
            graph.setCellStyles(mxConstants.STYLE_IMAGE, encodeURIComponent(img.src), [layer]);
            graph.setCellStyles(mxConstants.STYLE_IMAGE_WIDTH, img.width, [layer]);
            graph.setCellStyles(mxConstants.STYLE_IMAGE_HEIGHT, img.height, [layer]);
          }
          reader.readAsDataURL(input.files[0]);
      }
    }
    readURL(evt.target);
    
  });
  mxEvent.addListener(input, 'change', apply); 
  div.appendChild(input);
  
  //format 컨테이너에 추가
  formatContainer.appendChild(div);
  
};
/**
 * 정렬 설정
 */
Format.prototype.addAlign = function() {
  var graph = this.graph;

  //라벨 설정
  var div = this.createPanel();
  div.appendChild(this.createTitle(mxResources.get('cellAlign')));
  
  var stylePanel = this.stylePanel();
  
  var left = this.addButton('geSprite-alignleft', mxResources.get('left'),
    function() { graph.alignCells(mxConstants.ALIGN_LEFT); }, stylePanel);
  var center = this.addButton('geSprite-aligncenter', mxResources.get('center'),
    function() { graph.alignCells(mxConstants.ALIGN_CENTER); }, stylePanel);
  var right = this.addButton('geSprite-alignright', mxResources.get('right'),
    function() { graph.alignCells(mxConstants.ALIGN_RIGHT); }, stylePanel);

  var top = this.addButton('geSprite-aligntop', mxResources.get('top'),
    function() { graph.alignCells(mxConstants.ALIGN_TOP); }, stylePanel);
  var middle = this.addButton('geSprite-alignmiddle', mxResources.get('middle'),
    function() { graph.alignCells(mxConstants.ALIGN_MIDDLE); }, stylePanel);
  var bottom = this.addButton('geSprite-alignbottom', mxResources.get('bottom'),
    function() { graph.alignCells(mxConstants.ALIGN_BOTTOM); }, stylePanel);

  this.styleButtons([left, center, right, top, middle, bottom]);
  right.style.marginRight = '6px';
  div.appendChild(stylePanel);
  
  //format 컨테이너에 추가
  this.container.appendChild(div);
};
/**
 * 포맷 패널에 들어갈 sprite 아이콘 holder
 */
Format.prototype.stylePanel = function() {
  var stylePanel = document.createElement('div');
  stylePanel.style.position = 'relative';
  stylePanel.style.paddingLeft = '0px';
  stylePanel.style.borderWidth = '0px';
  stylePanel.className = 'geToolbarContainer';
  
  return stylePanel;
};
/**
 * 포맷 패널에 들어갈 sprite 아이콘 버튼 스타일 지정
 */
Format.prototype.styleButtons = function(elts) {
//var styleButtons = function(elts) {
  for (var i = 0; i < elts.length; i++) {
    mxUtils.setPrefixedStyle(elts[i].style, 'borderRadius', '3px');
    mxUtils.setOpacity(elts[i], 100);
    elts[i].className += ' geColorBtn';
  }
};
/**
 * 포맷 패널에 들어갈 sprite 아이콘 버튼 생성
 */
Format.prototype.addButton = function(classname, tooltip, funct, c) {
//var addButton = function(classname, tooltip, funct, c) {
  
  var createButton = function(classname) {
    var elt = document.createElement('a');
    elt.setAttribute('href', 'javascript:void(0);');
    elt.className = 'geButton';
    var inner = document.createElement('div');
    if (classname != null) {
      inner.className = 'geSprite ' + classname;
    }
    elt.appendChild(inner);
    return elt;
  };
  
  var addClickHandler = function(elt, funct) {
    if (funct != null) {
      mxEvent.addListener(elt, 'click', function(evt) {
        funct(evt);
        mxEvent.consume(evt);
      });
      
      if (document.documentMode != null && document.documentMode >= 9) {
        // Prevents focus
        mxEvent.addListener(elt, 'mousedown', function(evt) {
          evt.preventDefault();
        });
      }
    }
  };
  
  var elt = createButton(classname);
  if (tooltip != null) {
    elt.setAttribute('title', tooltip);
  }
  addClickHandler(elt, funct);
  c.appendChild(elt);
  return elt;
};


/**
 * 기존 구성 조회
 */
Format.prototype.open = function(isLoad) {
  var graph = this.graph;
  var main = this.main;
  //open
  var reqGroup = mxUtils.post(TABLELAYOUT_OPEN_URL, '',
      mxUtils.bind(this, function(req) {
        //var enabled = req.getStatus() != 404;
        if( req.getStatus() == 200 ) {
          var jsonStr = JSON.parse(req.getText());
          var xmlStr = jsonStr.data;
          try {
            var xml = mxUtils.parseXml(xmlStr); 
            this.setGraphXml(graph, xml.documentElement);
            main.initValue();
            if(!isLoad) {
              mxUtils.alert(mxResources.get('opened'));
            }
          }
          catch (e) {
            mxUtils.alert(mxResources.get('invalidOrMissingFile') + ': ' + e.message);
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
Format.prototype.save = function() {
  var graph = this.graph;
  
  if (graph.isEditing()) {
    graph.stopEditing();
  }
  
  var node = null;
  var enc = new mxCodec(mxUtils.createXmlDocument());
  node = enc.encode(graph.getModel());
  
  var xmlPretty = mxUtils.getPrettyXml(node);
  mxLog.show();
  mxLog.write(xmlPretty);
  
  var xml = encodeURIComponent(mxUtils.getXml(node));
  try {
    if (xml.length < MAX_REQUEST_SIZE) {
      var onload = function(req) {
        mxUtils.alert(mxResources.get('saved'));
      }
      var onerror = function(req) {
        mxUtils.alert('Error');
      }
      new mxXmlRequest(TABLELAYOUT_SAVE_URL, 'xml=' + xml).send(onload, onerror);
    }
    else {
      mxUtils.alert(mxResources.get('drawingTooLarge'));
      //mxUtils.popup(xml);
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

