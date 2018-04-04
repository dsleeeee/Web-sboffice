
//그리드의 크기
mxGraph.prototype.gridSize = 10;
mxGraphView.prototype.gridColor = '#e0e0e0';

//지정된 영역에만 구성요소를 넣을 수 있도록 처리 
mxGraph.prototype.allowNegativeCoordinates = false;

//셀 가이드 사용 여부
mxGraphHandler.prototype.guidesEnabled = true;


/**
 * 메인 Class
 */
TableAttr = function(container, themes) {
  mxEventSource.call(this);
  this.container = container || document.body;
  
  //그래프 객체 생성
  this.graph = new Graph(this.container, null, null, null, themes);
  
  //왼쪽 속성명
  this.comp = new Sidebar(this.graph);

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
mxUtils.extend(TableAttr, mxEventSource);

//변수 생성
TableAttr.prototype.graph = null;
TableAttr.prototype.comp = null;
TableAttr.prototype.layer = null;
TableAttr.prototype.format = null;

//배경색
TableAttr.prototype.defaultBackgroundColor = '#ffffff';

//배경이미지
TableAttr.prototype.containerBackgroundImage = 'url(' + IMAGE_PATH + '/grid.gif' + ')';



/**
 * 메인 - 초기화
 */
TableAttr.prototype.init = function() {
  

  //그래프 영역의 배경 이미지 설정
  this.container.style.backgroundImage = this.containerBackgroundImage;
  this.container.style.backgroundColor = this.defaultBackgroundColor; 

  //마우스 오른쪽 클릭 - 컨텍스트 메뉴
  //mxEvent.disableContextMenu(this.container);

  //this.container.appendChild(this.graphContainer);

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
TableAttr.prototype.initValue = function() {
  
  //기존 설정 오픈 시 그리드 초기값 설정(사용여부)
  this.comp.initValue();
};

/**
* 왼쪽 속성명 처리
*/
Sidebar = function(graph) {
  
  this.container = document.getElementById('component');
  this.graph = graph;
  
  this.init();
}

/**
 * 속성명 영역 초기화
 */
Sidebar.prototype.init = function() {
  
  this.grid = this.makeGrid();
  
  var graph = this.graph;
  var theGrid = this.grid;
  
  //셀 삭제 시 그리드에서 사용여부 해제 처리
  var cellsRemoved = graph.cellsRemoved;
  graph.cellsRemoved = function(cells) {
    for(var i=0; i < cells.length; i++) {
      var cell = cells[i];
      theGrid.setCellData((parseInt(cell.getId())-1), 'used', false);
    }
    cellsRemoved.apply(this, arguments);
  }
  //셀 추가 시 그리드에서 사용여부 체크 처리
  var cellsAdded = graph.cellsAdded;
  graph.cellsAdded = function(cells) {
    for(var i=0; i < cells.length; i++) {
      var cell = cells[i];
      theGrid.setCellData((parseInt(cell.getId())-1), 'used', true);
    }
    cellsAdded.apply(this, arguments);
  }
  
  //UNDO 를 했을 때 그리드의 사용여부 업데이트
  var undoHandler = function(sender, evt) {
    var model = graph.getModel();
    var cells = [];
    
    //추가된 경우 사용여부 true
    var cand = graph.getSelectionCellsForChanges(evt.getProperty('edit').changes);
    for (var i = 0; i < cand.length; i++) {
      if ((model.isVertex(cand[i]) || model.isEdge(cand[i])) && graph.view.getState(cand[i]) != null) {
        cells.push(cand[i]);
        theGrid.setCellData(parseInt(cand[i].id)-1, 'used', true);
      }
    }
    graph.setSelectionCells(cells);
    
    //삭제된 경우 사용여부 false
    var removes = graph.getRemovedCellsForChanges(evt.getProperty('edit').changes);
    for (var i = 0; i < removes.length; i++) {
      if ((model.isVertex(removes[i]) || model.isEdge(removes[i]))) {
        theGrid.setCellData(parseInt(removes[i].id)-1, 'used', false);
      }
    }
  }
  graph.undoManager.addListener(mxEvent.UNDO, undoHandler);
  graph.undoManager.addListener(mxEvent.REDO, undoHandler);

};
/**
 * 속성명 영역 값 초기화
 */
Sidebar.prototype.initValue = function() {
  var graph = this.graph;
  var theGrid = this.grid;
  
  //그리드에서 사용여부 초기화
  this.initUsed();
  
};
/**
 * 사용여부 초기화
 */
Sidebar.prototype.initUsed = function() {
  var graph = this.graph;
  var theGrid = this.grid;

  for(i = 0; i < theGrid.rows.length; i++) {
    theGrid.setCellData(theGrid.rows[i].index, 'used', false);
  }

  var parent = graph.getDefaultParent();
  var model = graph.getModel();
  var childCount = model.getChildCount(parent);
  for(var i = 0; i < childCount; i++) {
    var cell = model.getChildAt(parent, i);
    theGrid.setCellData((parseInt(cell.getId())-1), 'used', true);
  }

};

/**
 * 그리드 생성
 */
Sidebar.prototype.makeGrid = function() {

  var sidebar = this;
  var graph = this.graph;
  

  var theGrid = new wijmo.grid.FlexGrid('#theGrid', {
    autoGenerateColumns: false,
    selectionMode: 'ListBox',//'RowRange','ListBox'
    isReadOnly: true,
    itemsSource: getData(),
    columns: [
      { binding: 'idx', visible: false},
      { binding: 'name', header: mxResources.get('attrName'), width: 80, isReadOnly: true },
      { binding: 'tag', header: mxResources.get('preview'), width: 100, isReadOnly: true, visible: false},
      { binding: 'used', header: mxResources.get('alreadyUsed'), width: 80, isReadOnly: true },
      { binding: 'rect', visible: false}
    ],
    showAlternatingRows: false,
    formatItem: function(s, e) {
      if (e.panel == s.cells && s.columns[e.col].binding == 'tag') {
        var item = s.rows[e.row].dataItem;
        e.cell.innerHTML = item.tag;
      }
    }
  });

  //RowHeader 없애기
  theGrid.rowHeaders.columns.splice(0, 1);

  //ListBox 선택모드에서 cell의 hostElement를 가져오는데 문제가 있어
  //아래 2줄을 추가 하였음
  theGrid.select(-1, -1);
  theGrid.select(0, 1);
  
  
  //마우스 text selection 이벤트를 방지하기 위해 사용
  //TODO 기본 이벤트가 문제가 될 경우 아래 소스 고려
  /*
  function pauseEvent(e){
    if(e.stopPropagation) e.stopPropagation();
    if(e.preventDefault) e.preventDefault();
    e.cancelBubble=true;
    e.returnValue=false;
    return false;
  }
  var mouseDown = false;
  theGrid.hostElement.addEventListener('mousedown', function (e) {
    mouseDown = true;
    return pauseEvent(e);
  });
  theGrid.hostElement.addEventListener('mousemove', function (e) {
    //console.log('mousemove');
    pauseEvent(e);
  });
  theGrid.hostElement.addEventListener('mouseup', function (e) {
    mouseDown = false;
  });
  */
  
  //TODO 선택한 ROW가 바뀌었을 때 그래픽 영역에서 활성화
  theGrid.selectionChanged.addHandler(function (s, e) {
    //idx가져오기(0번째 항목)
    var idx = theGrid.getCellData(e.row, 0, true);
    var used = theGrid.getCellData(e.row, 3, true);
    //사용중일 경우만 선택 활성화
    if(used) {
      var cell = graph.getModel().getCell(idx);
      graph.setSelectionCell(cell);
      //console.log(cell);
      //맨앞으로 가져오기
      graph.orderCells(false);
      graph.container.focus();
    }
  });

  //판매금액 포맷팅
  var numberWithCommas = function(x) {
    var isInteger = function(num) {
      return (num ^ 0) === num;
    };
    if(isInteger(x)) {
      x = x.toString();
      var pattern = /(-?\d+)(\d{3})/;
      while (pattern.test(x)) {
        x = x.replace(pattern, "$1,$2");
      }
    }
    return x;
  }

  
  //Drop 이벤트
  var dropEvent = function(graph, evt, cell, x, y) {
    var parent = graph.getDefaultParent();
    var model = graph.getModel();
    
    //Drop 할 때 오브젝트 생성
    var pt = graph.getPointForEvent(evt);
    for(var selected = 0; selected < theGrid.selectedItems.length; selected++ ) {
      var rows = theGrid.selectedRows[selected];
      var item = rows.dataItem;
      //console.log(item.tag);
      //사용중이 아닌 항목만 넣기
      if(!item.used) {
        var lastCell;
        model.beginUpdate();
        try {
          lastCell = graph.insertVertex(parent,
              item.idx,
              numberWithCommas(item.tag),
              item.rect.x, item.rect.y,
              item.rect.width, item.rect.height,
              'tableAttr');
        }
        finally {
          model.endUpdate();
        }
        graph.setSelectionCell(lastCell);
      }
    }
  };
  //Row에 DnD이벤트 생성
  var cells = theGrid.cells.hostElement.childNodes;
  for(var i = 1; i < cells.length; i++) {
    var ds = mxUtils.makeDraggable(cells[i], graph, dropEvent, cells[i], -10, -(theGrid.rows.defaultSize * (i-1)));
    ds.highlightDropTargets = true;
  }
  
  // create some random data
  //서버에서 받은 데이터로 대체
  function getData() {
    
    var findPos = function(cd) {
      var obj;
      for(x = 0; x < TABLE_ATTR_DEFAULTS.length; x++) {
        obj = TABLE_ATTR_DEFAULTS[x];
        if( cd == obj.attrCd) {
          return new mxRectangle(obj.x, obj.y, obj.width, obj.height);
        }
      }
      return new mxRectangle(10, 10, 50, 50);
    };
    
    //그래프의 child node는 2부터 시작할 수 밖에 없어 코드 맞추기 위해 lpad 하였음
    function lpad(s, padLength, padString){
      while(s.length < padLength)
          s = padString + s;
      return s;
    }
    
    var data = [];
    for(i = 0; i < TABLE_ATTR_ITEMS.length; i++) {
      //console.log(TABLE_ATTR_ITEMS[i]);
      var cd = lpad((i+1).toString(), 2, '0');
      data.push({
        name: TABLE_ATTR_ITEMS[i].nmcodeNm,
        tag: TABLE_ATTR_ITEMS[i].nmcodeItem1,
        used: false,
        rect: findPos(cd),
        idx: cd 
      });
    }
    return data;
  }
  
  return theGrid;
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
  
  //this.init();
}
/**
 * Graph inherits from mxGraph.
 */
mxUtils.extend(Graph, mxGraph);

Graph.prototype.defaultThemes = {};
Graph.prototype.defaultVertexStyle = {};

Graph.prototype.defaultThemeName = 'tableattr';

//배경이미지 - 기본설정
Graph.prototype.graphBackgroundImage = IMAGE_PATH + '/gradient_background.jpg';


/**
 * 영역 초기화
 */
Graph.prototype.init = function() {

  mxGraph.prototype.init.apply(this, arguments);
  
  var graph = this;

  //console.log(graph);
  
  //그래프 영역의 배경 색 설정 - 기본설정
  //graph.setBackgroundImage(new mxImage(this.graphBackgroundImage, 600, 640));
  
  graph.minimumGraphSize = new mxRectangle(0, 0, 400, 400);
  graph.maximumGraphBounds = new mxRectangle(0, 0, 400, 400);

  
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
  
  //마우스 클릭 할 때 focus 처리, jsp에서 content에 tabindex -1 로 처리 했으나 추가
  //https://jgraph.github.io/mxgraph/docs/known-issues.html
  if (mxClient.IS_NS) {
    mxEvent.addListener(graph.container, 'mousedown', function() {
      if (!graph.isEditing()) {
        graph.container.setAttribute('tabindex', '-1');
        graph.container.focus();
      }
    });
  }

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
* 오른쪽 설정 기능 패널
*/
function Format(tableattr) {
  
  this.container = document.getElementById('format');

  this.main = tableattr;
  this.graph = tableattr.graph;
  
  this.init();
}

Format.prototype.defaultFonts = [
  {name:'NotoR', value:0},
  {name:'Hanna', value:1},
  {name:'Jeju Gothic', value:2},
  {name:'Jeju Hallasan', value:3},
  {name:'Jeju Myeongjo', value:4},
  {name:'Kopub Batang', value:5},
  {name:'Nanum Brush Script', value:6},
  {name:'Nanum Gothic', value:7},
  {name:'Nanum Gothic Coding', value:8},
  {name:'Nanum Myeongjo', value:9},
  {name:'Nanum Pen Script', value:10},
  {name:'Noto Sans KR', value:11}
  ];


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
  
};
/**
 * 화면 새로 그리기
 */
Format.prototype.refresh = function() {

  var graph = this.graph;
  
  //상단 라벨, 초기화/저장 버튼
  this.clear();

  //선택된 셀이 있을 때만 활성화 되는 부분
  var cells = graph.getSelectionCells();
  if(cells.length > 0 ) {
    //폰트 설정
    this.fontStyle();
    
    //정렬 옵션
    this.align();
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
 * 폰트 설정
 */
Format.prototype.fontStyle = function() {
  var graph = this.graph;
  var format = this;
  
  //"폰트" 라벨 설정
  var div = this.createPanel();
  
  div.appendChild(this.createTitle(mxResources.get('font')));
  mxUtils.br(div);

  //선택된 셀에서 스타일 정보 읽기
  var cells = graph.getSelectionCells();
  var initFontSize = 10;
  var initFontFamily;
  var initFontColor;
  for(var i=0; i < cells.length; i++) {
    var cell = cells[i];
    var state = graph.view.getState(cell);
    if (state != null) {
      initFontSize = Math.max(0, parseInt(mxUtils.getValue(state.style, mxConstants.STYLE_FONTSIZE, null)));
      initFontFamily = mxUtils.getValue(state.style, mxConstants.STYLE_FONTFAMILY, null);
      initFontColor = mxUtils.getValue(state.style, mxConstants.STYLE_FONTCOLOR, null);
    }
  }

  /**
   * 폰트 종류
   */
  //wijmo 컴포넌트 추가
  div.appendChild(this.createWijmoContainer('fontFamily'));
  //format 컨테이너에 추가
  this.container.appendChild(div);
  
  var template = '<div style="font-family:{name}">{name}</div>';
  var theInputColor = new wijmo.input.ComboBox('#fontFamily', {
    itemsSource: format.defaultFonts,
    displayMemberPath: 'name',
    selectedValuePath: 'value',
    formatItem: function(s, e) {
      var html = wijmo.format(template, e.data, function(data, name, fmt, val) {
        return wijmo.isString(data[name]) ? wijmo.escapeHtml(data[name]) : val;
      });
      e.item.innerHTML = html;
    },
    selectedIndexChanged: function(s, e) {
      graph.setCellStyles(mxConstants.STYLE_FONTFAMILY, s.text, cells);
    }
  });
  theInputColor.text = initFontFamily;
  
  mxUtils.br(div);
  //console.log(graph.view);
  
  /**
   * 폰트 색상 설정 시작
   */
  //wijmo 컴포넌트 추가
  div.appendChild(this.createWijmoContainer('fontColor'));
  //format 컨테이너에 추가
  this.container.appendChild(div);

  var theInputColor = new wijmo.input.InputColor('#fontColor', {
    placeholder: 'Select the color',
    value: initFontColor,
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

  var theInputNumber = new wijmo.input.InputNumber('#fontSize', {
    format: 'n0',
    step: 1,
    min: 8,
    max: 20,
    value: initFontSize,
    valueChanged: function(s, e) {
      graph.setCellStyles(mxConstants.STYLE_FONTSIZE, s.value, cells);
    }
  });
  mxUtils.br(div);

  /**
   * 폰트 스타일(굵게, 기울임, 밑줄)
   */
  var stylePanel = this.stylePanel();
  var bold = this.addButton('geSprite-bold', mxResources.get('bold'), function() {
      graph.toggleCellStyleFlags(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
    }, stylePanel);
  var italic = this.addButton('geSprite-italic', mxResources.get('italic'), function() {
      graph.toggleCellStyleFlags(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_ITALIC);
    }, stylePanel);
  var underline = this.addButton('geSprite-underline', mxResources.get('underline'), function() {
    graph.toggleCellStyleFlags(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_UNDERLINE);
    }, stylePanel);
  
  this.styleButtons([bold, italic, underline]);
  underline.style.marginRight = '6px';
  div.appendChild(stylePanel);

  this.container.appendChild(div);
};

/**
 * 폰트 속성/정렬
 */
Format.prototype.align = function() {
  var graph = this.graph;
  var format = this;
  
  //"정렬" 라벨 설정
  var div = this.createPanel();
  
  div.appendChild(this.createTitle(mxResources.get('align')));
  mxUtils.br(div);

  /**
   * 텍스트 위치(왼쪽/중앙/오른쪽, TOP/MIDDLE/BOTTOM)
   */
  var stylePanel = this.stylePanel();
  var left = this.addButton('geSprite-left', mxResources.get('left'),
      function() { graph.setCellStyles(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT); }, stylePanel);
  var center = this.addButton('geSprite-center', mxResources.get('center'),
    function() { graph.setCellStyles(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER); }, stylePanel);
  var right = this.addButton('geSprite-right', mxResources.get('right'),
    function() { graph.setCellStyles(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_RIGHT); }, stylePanel);

  var top = this.addButton('geSprite-top', mxResources.get('top'),
      function() { graph.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP); }, stylePanel);
  var middle = this.addButton('geSprite-middle', mxResources.get('middle'),
    function() { graph.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);}, stylePanel);
  var bottom = this.addButton('geSprite-bottom', mxResources.get('bottom'),
    function() { graph.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);}, stylePanel);
  
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
  var reqGroup = mxUtils.post(TABLEATTR_OPEN_URL, '',
      mxUtils.bind(this, function(req) {
        //var enabled = req.getStatus() != 404;
        if( req.getStatus() == 200 ) {
          var jsonStr = JSON.parse(req.getText());
          var xmlStr = jsonStr.data;
          if(xmlStr != null) {
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
  
  var xml = mxUtils.getXml(node);
  //console.log(xml);

  var xml = encodeURIComponent(xml);
  
  try {
    if (xml.length < MAX_REQUEST_SIZE) {
      var onload = function(req) {
        mxUtils.alert(mxResources.get('saved'));
      }
      var onerror = function(req) {
        mxUtils.alert('Error');
      }
      new mxXmlRequest(TABLEATTR_SAVE_URL, 'xml=' + xml).send(onload, onerror);
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

