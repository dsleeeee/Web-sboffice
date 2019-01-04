/** 요일별 그리드 controller */
app.controller('daysCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('daysCtrl', $scope, $http, true));

  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("daysCtrl");

    // 그리드 DataMap 설정
    $scope.sysStatFgMap    = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.orderCloseYnMap = new wijmo.grid.DataMap([
      {id: "Y", name: messages["outstockReqDate.orderCloseYnY"]},
      {id: "N", name: messages["outstockReqDate.orderCloseYnN"]},
    ], 'id', 'name');

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        // if (col.binding === "storeCd") {
        //   wijmo.addClass(e.cell, 'wijLink');
        //   wijmo.addClass(e.cell, 'wj-custom-readonly');
        // }
      }
    });

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    s.columnHeaders.rows[0].dataItem = {
      storeCd     : messages["outstockReqDate.storeCd"],
      storeNm     : messages["outstockReqDate.storeNm"],
      ownerNm     : messages["cmm.owner.nm"],
      sysStatFg   : messages["outstockReqDate.sysStatFg"],
      orderCloseYn: messages["outstockReqDate.orderCloseYn"],
      sun         : messages["outstockReqDate.outstockReqDate"],
      mon         : messages["outstockReqDate.outstockReqDate"],
      tue         : messages["outstockReqDate.outstockReqDate"],
      wed         : messages["outstockReqDate.outstockReqDate"],
      thu         : messages["outstockReqDate.outstockReqDate"],
      fri         : messages["outstockReqDate.outstockReqDate"],
      sat         : messages["outstockReqDate.outstockReqDate"],
      daysRemark  : messages["outstockReqDate.remark"],
    };
  };


  // 체크박스가 있는 헤더머지 때문에 itemFormatter 를 재정의함.
  $scope.itemFormatter = function (panel, r, c, cell) {
    if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
      //align in center horizontally and vertically
      panel.rows[r].allowMerging    = true;
      panel.columns[c].allowMerging = true;

      wijmo.setCss(cell, {
        display    : 'table',
        tableLayout: 'fixed'
      });
      cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
      wijmo.setCss(cell.children[0], {
        display      : 'table-cell',
        verticalAlign: 'middle',
        textAlign    : 'center'
      });

      if ((panel.grid.columnHeaders.rows.length - 1) === r) {
        // 헤더의 전체선택 클릭 로직
        var flex   = panel.grid;
        var column = flex.columns[c];
        // check that this is a boolean column
        if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
          // prevent sorting on click
          column.allowSorting = false;
          // count true values to initialize checkbox
          var cnt             = 0;
          for (var i = 0; i < flex.rows.length; i++) {
            if (flex.getCellData(i, c) === true) {
              cnt++;
            }
          }
          // create and initialize checkbox
          if (column.format === 'checkBoxText') {
            cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />'
              + '<label for=\"' + column.binding + '\" class=\"wj-header-label\">' + cell.innerHTML + '</label>';
          } else {
            cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
          }
          var cb           = cell.firstChild;
          cb.checked       = cnt > 0;
          cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
          // apply checkbox value to cells
          cb.addEventListener('click', function (e) {
            flex.beginUpdate();
            for (var i = 0; i < flex.rows.length; i++) {
              var cell = flex.cells.getCellElement(i, c);

              // 활성화 및 readOnly 아닌 경우에만 체크되도록
              // if (!cell.children[0].disabled) {
              if (!cell.children[0].disabled) {
                flex.setCellData(i, c, cb.checked);
              }
            }
            flex.endUpdate();
          });
        }
      }
    }
    // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
    else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
      // GroupRow 인 경우에는 표시하지 않는다.
      if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
        cell.textContent = '';
      } else {
        if (!isEmpty(panel._rows[r]._data.rnum)) {
          cell.textContent = (panel._rows[r]._data.rnum).toString();
        } else {
          cell.textContent = (r + 1).toString();
        }
      }
    }
    // readOnly 배경색 표시
    else if (panel.cellType === wijmo.grid.CellType.Cell) {
      var col = panel.columns[c];
      if (col.isReadOnly) {
        wijmo.addClass(cell, 'wj-custom-readonly');
      }
    }
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("daysCtrl", function (event, data) {
    $scope.searchDaysList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.searchDaysList = function () {
    // 파라미터
    var params     = {};
    params.storeCd = $("#daysSelectStoreCd").val();
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/order/outstockReqDate/days/list.sb", params);
  };

  // 요청일 저장
  $scope.saveDays = function () {
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    $scope._save("/iostock/order/outstockReqDate/days/save.sb", params, function () {
      $scope.searchDaysList()
    });
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.daysSelectStoreShow = function () {
    $scope._broadcast('daysSelectStoreCtrl');
  };

}]);
