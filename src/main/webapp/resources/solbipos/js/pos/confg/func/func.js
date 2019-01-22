/****************************************************************
 *
 * 파일명 : func.js
 * 설  명 : 포스기능정의 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.01.14     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


/**********************************************************************
 *  기능 구분 그리드
 **********************************************************************/
app.controller('funcFgCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('funcFgCtrl', $scope, $http, true));

  // 선택 기능
  $scope.selectedFuncFg;
  $scope.setSelectedFuncFg = function(funcFg) {
    $scope.selectedFuncFg = funcFg;
  };
  $scope.getSelectedFuncFg = function(){
    return $scope.selectedFuncFg;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.$apply(function() {
      $scope.data = new wijmo.collections.CollectionView(funcFgList);
      $("button").hide();
    });

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "nmcodeCd") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 기능구분 선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];

        // 기능구분 클릭시 해당 기능 목록 조회
        if ( col.binding === "nmcodeCd" ) {
          $scope.setSelectedFuncFg(s.rows[ht.row].dataItem);

          var scope = agrid.getScope('funcCtrl');
          scope._broadcast('funcCtrl', $scope.getSelectedFuncFg());

          event.preventDefault();
        }
      }
    });
  };

  $scope.$on("funcFgCtrl", function(event, data) {
    // $scope.getStoreList();
    event.preventDefault();
  });
}]);


/**********************************************************************
 *  기능 그리드
 **********************************************************************/
app.controller('funcCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('funcCtrl', $scope, $http, true));

  // 선택 기능 구분
  $scope.selectedFuncFg;
  $scope.setSelectedFuncFg = function(funcFg) {
    $scope.selectedFuncFg = funcFg;
  };
  $scope.getSelectedFuncFg = function(){
    return $scope.selectedFuncFg;
  };

  // 선택 기능
  $scope.selectedFunc;
  $scope.setSelectedFunc = function(func) {
    $scope.selectedFunc = func;
  };
  $scope.getSelectedFunc = function(){
    return $scope.selectedFunc;
  };

  var storeKindData = new wijmo.grid.DataMap(storeKind, 'value', 'name');
  var posFgData = new wijmo.grid.DataMap(posFg, 'value', 'name');

  var dataHeader = [
    {header:messages["func.chk"] ,  binding:"gChk", width:40, align:"center"},
    {header:messages["func.fnkeyNo"], binding:"fnkeyNo",width:80, align:"center", isReadOnly:"true"},
    {header:messages["func.fnkeyNm"],  binding:"fnkeyNm", width:120, align:"left"},
    {header:messages["func.storeFg"],  binding:"storeFg", width:100, dataMap:storeKindData, align:"center"},
    {header:messages["func.posFg"],  binding:"posFg", width:90, dataMap:posFgData, align:"center"},
    {header:messages["func.posiAdjYn"],  binding:"posiAdjYn", width:100, align:"center"},
    {header:messages["func.colPosi"],  binding:"colPosi", width:75, align:"center", visible:false},
    {header:messages["func.rowPosi"],  binding:"rowPosi", width:5, visible:false},
    {header:messages["func.width"],  binding:"width", visible:false},
    {header:messages["func.height"],  binding:"height", width:85, align:"center", visible:false},
    {header:messages["func.comm"],
      columns:[
        {header:messages["func.useYn"],  binding:"fnkeyUseYn0", width:70, align:"center"},
        {header:messages["func.imgFileNm"],  binding:"imgFileNm0", width:100, align:"center"},
      ]
    },
    {header:messages["func.food"],
      columns:[
        {header:messages["func.useYn"],  binding:"fnkeyUseYn1", width:70, align:"center"},
        {header:messages["func.imgFileNm"],  binding:"imgFileNm1", width:100, align:"center"},
      ]
    },
    {header:messages["func.useYn"],  binding:"useYn", width:80, align:"center"},
    {header:messages["func.dispSeq"],  binding:"dispSeq", width:90, align:"center", visible:false}
  ];

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // bindColumnGroup 생성
    bindColumnGroups(s, dataHeader);

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "fnkeyNo") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 기능구분 선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        // 기능구분 클릭시 해당 기능 목록 조회
        if ( col.binding === "fnkeyNo" ) {

          $scope.setSelectedFunc(selectedRow);
          // 레이어팝업 오픈
          $scope.funcStoreLayer.show(true, function(){

            var regScope  = agrid.getScope('regStoreCtrl');
            regScope._gridDataInit();

            var noRegScope  = agrid.getScope('noRegStoreCtrl');
            noRegScope._gridDataInit();
          });

          event.preventDefault();
        }
      }
    });
  };

  // 헤더 컬럼 MERGE
  function bindColumnGroups(flex, columnGroups){

    // create the columns
    flex.autoGenerateColumns = false;
    createColumnGroups(flex, columnGroups, 0);

    // merge the headers
    mergeColumnGroups(flex);

    // center-align headers vertically and horizontally
    flex.formatItem.addHandler(function (s, e) {
      if (e.panel == flex.columnHeaders) {
        wijmo.setCss(e.cell.children[0], {
          verticalAlign: 'middle',
          textAlign: 'center'
        });
        console.log(e.cell.children[0])


      }
    });

    // select column groups by clicking the merged headers
    flex.allowSorting = false;
    flex.allowDragging = wijmo.grid.AllowDragging.None;
    flex.addEventListener(flex.hostElement, 'click', function (e) {
      var ht = flex.hitTest(e);
      if (ht.panel == flex.columnHeaders) {
        var rng = flex.getMergedRange(flex.columnHeaders, ht.row, ht.col, false) || ht.range;
        flex.select(new wijmo.grid.CellRange(0, rng.col, flex.rows.length - 1, rng.col2));
        e.preventDefault();
      }
    });
  }

  // 컬럼 그룹 생성
  function createColumnGroups(flex, columnGroups, level) {

    // prepare to generate columns
    var colHdrs = flex.columnHeaders;

    // add an extra header row if necessary
    if (level >= colHdrs.rows.length) {
      colHdrs.rows.splice(colHdrs.rows.length, 0, new wijmo.grid.Row());
    }

    // loop through the groups adding columns or groups
    for (var i = 0; i < columnGroups.length; i++) {
      var group = columnGroups[i];

      if (!group.columns) {

        // create a single column
        var col = new wijmo.grid.Column();

        // copy properties from group
        for (var prop in group) {
          if (prop in col) {
            col[prop] = group[prop];
          }
        }

        // add the new column to the grid, set the header
        flex.columns.push(col);
        colHdrs.setCellData(level, colHdrs.columns.length - 1, group.header);

      } else {

        // get starting column index for this group
        var colIndex = colHdrs.columns.length;

        // create columns for this group
        createColumnGroups(flex, group.columns, level + 1);

        // set headers for this group
        for (var j = colIndex; j < colHdrs.columns.length; j++) {
          colHdrs.setCellData(level, j, group.header);
        }
      }
    }
  }

  // 컬럼 그룹 머지
  function mergeColumnGroups(flex) {
    // merge headers
    var colHdrs = flex.columnHeaders;
    flex.allowMerging = wijmo.grid.AllowMerging.ColumnHeaders;

    // merge horizontally
    for (var r = 0; r < colHdrs.rows.length; r++) {
      colHdrs.rows[r].allowMerging = true;
    }

    // merge vertically
    for (var c = 0; c < colHdrs.columns.length; c++) {
      colHdrs.columns[c].allowMerging = true;
    }

    // fill empty cells with content from cell above
    for (var c = 0; c < colHdrs.columns.length; c++) {
      for (var r = 1; r < colHdrs.rows.length; r++) {
        var hdr = colHdrs.getCellData(r, c);
        if (!hdr || hdr == colHdrs.columns[c].binding) {
          var hdr = colHdrs.getCellData(r - 1, c);
          colHdrs.setCellData(r, c, hdr);
        }
      }
    }

    // handle top-left panel
    for (var c = 0; c < flex.topLeftCells.columns.length; c++) {
      flex.topLeftCells.columns[c].allowMerging = true;
    }
  }

  $scope.$on("funcCtrl", function(event, data) {
    $scope.setSelectedFuncFg(data);
    $scope.getFuncList();
    event.preventDefault();
  });


  // 기능 목록 조회
  $scope.getFuncList = function(){

    var params = {};
    params.fnkeyFg = $scope.getSelectedFuncFg().nmcodeCd;

    $scope.$broadcast('loadingPopupActive');

    // ajax 통신 설정
    $http({
      method: 'POST', //방식
      url: '/pos/confg/func/func/funcList.sb', /* 통신할 URL */
      params: params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');

      var list = response.data.data.list;
      if (list.length === undefined || list.length === 0) {
        $scope.data = new wijmo.collections.CollectionView([]);
        if (response.data.message) {
          $scope._popMsg(response.data.message);
        }
        return false;
      }

      var data = new wijmo.collections.CollectionView(list);
      data.trackChanges = true;
      $scope.data = data;

      $("button").show();

    }, function errorCallback(response) {
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // 'complete' code here
      if (typeof callback === 'function') {
        setTimeout(function () {
          callback();
        }, 10);
      }
    });
  };

  // 위로 옮기기
  $scope.up = function(){
    var movedRows = 0;
    for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
      var item = $scope.flex.collectionView.items[i];
      if (i > 0 && item.gChk) {
        if (!$scope.flex.collectionView.items[i - 1].gChk) {
          movedRows = i - 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };

  // 아래로 옮기기
  $scope.down = function(){
    var movedRows = 0;
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      var item = $scope.flex.collectionView.items[i];
      if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
        if (!$scope.flex.collectionView.items[i + 1].gChk) {
          movedRows = i + 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };

  // 행 추가
  $scope.add = function(){
    // 파라미터 설정
    var params = {};
    params.fnkeyNo = '자동채번';
    params.posiAdjYn = false;
    params.fnkeyUseYn0 = false;
    params.fnkeyUseYn1 = false;
    params.useYn = true;
    // 추가기능 수행 : 파라미터
    $scope._addRow(params, 2);
  };

  // 행 삭제
  $scope.del = function(){
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
      var item = $scope.flex.collectionView.items[i];
      if(item.gChk){
        $scope.flex.collectionView.removeAt(i);
      }
    }
  };

  // 저장
  $scope.save = function(){
    $scope.flex.collectionView.commitEdit();

    // 파라미터 설정
    var params = [];

    for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
      $scope.flex.collectionView.itemsRemoved[d].status = 'D';
      $scope.flex.collectionView.itemsRemoved[d].fnkeyFg = $scope.getSelectedFuncFg().nmcodeCd;
      params.push($scope.flex.collectionView.itemsRemoved[d]);
    }

    // dispSeq 재설정
    var editItems = [];
    for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
      if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
        editItems.push($scope.flex.collectionView.items[s]);
      }
    }

    for (var s = 0; s < editItems.length; s++) {
      editItems[s].dispSeq = (s + 1);
      $scope.flex.collectionView.editItem(editItems[s]);
      $scope.flex.collectionView.commitEdit();
    }

    for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
      $scope.flex.collectionView.itemsEdited[u].status = 'U';
      $scope.flex.collectionView.itemsEdited[d].fnkeyFg = $scope.getSelectedFuncFg().nmcodeCd;
      params.push($scope.flex.collectionView.itemsEdited[u]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = 'I';
      $scope.flex.collectionView.itemsAdded[d].fnkeyFg = $scope.getSelectedFuncFg().nmcodeCd;
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save('/pos/confg/func/func/save.sb', params, function() {
      // 저장 후 재조회
      $scope._broadcast('funcCtrl', $scope.getSelectedFuncFg());
    });
  };

}]);
