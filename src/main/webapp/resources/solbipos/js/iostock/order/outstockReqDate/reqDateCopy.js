app.controller('reqDateCopyCtrl', ['$scope', '$http', function ($scope, $http) {
  // angular.extend(this, new RootController('reqDateCopyCtrl', $scope, $http, true));

  // $scope.storeCd = "DS00001";
  $scope.search      = function () {
    // 매장을 선택해주세요.
    if ($("#targetSelectStoreCd").val() == "") {
      $scope._popMsg(messages["outstockReqDate.require.selectStore"]);
      return false;
    }
    // 요일별 그리드 조회
    $scope._broadcast('reqDateCopyDaysCtrl', {proc: "search"});
    // 특정일 그리드 조회
    $scope._broadcast('reqDateCopySpecificCtrl', {proc: "search"});
  };
  // 복사를 실행하기 위해 저장버튼 클릭. 우선 특정일 먼저 복사하도록 broadcast 날림.
  $scope.reqDateCopy = function () {
    // 매장을 선택해주세요.
    if ($("#targetSelectStoreCd").val() == "") {
      $scope._popMsg(messages["outstockReqDate.require.selectStore"]);
      return false;
    }
    // 복사할 매장을 선택해주세요.
    if ($("#copySelectStoreCd").val() == "") {
      $scope._popMsg(messages["outstockReqDate.require.selectCopyStore"]);
      return false;
    }

    // 특정일 복사 요청
    var msg = messages["outstockReqDate.confirm.copyMsg"]; //출고가능요일과 선택한 특정일이 복사됩니다. 진행하시겠습니까?
    s_alert.popConf(msg, function () {
      $scope._broadcast('reqDateCopySpecificCtrl', {proc: "copy"});
    });
  };
  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("reqDateCopyCtrl", function (event, data) {
    $scope._broadcast('reqDateCopyDaysCtrl', {proc: "copy"});
  });

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.targetSelectStoreShow = function () {
    $scope._broadcast('targetSelectStoreCtrl');
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.copySelectStoreShow = function () {
    $scope._broadcast('copySelectStoreCtrl');
  };
}]);

/** 요일별 그리드 controller */
app.controller('reqDateCopyDaysCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('reqDateCopyDaysCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("reqDateCopyDaysCtrl");

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
        //   var item = s.rows[e.row].dataItem;
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
  $scope.$on("reqDateCopyDaysCtrl", function (event, data) {
    if (data.proc == "search") {
      $scope.searchReqDateDaysList();
    }
    else if (data.proc == "copy") {
      $scope.daysCopy();
    }
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 요일별 그리드 조회
  $scope.searchReqDateDaysList = function () {
    // 파라미터
    var params       = {};
    params.storeCd   = $("#targetSelectStoreCd").val();
    params.listScale = 5000; // 조회 쿼리를 요일별 리스트 조회와 공통으로 사용하기 때문에 해당 페이지에선 페이징처리가 필요없어 listScale을 크게 줌.
    params.curr      = 1;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/order/outstockReqDate/days/list.sb", params);
  };

  // 요일별 복사
  $scope.daysCopy = function () {

    var params         = {};
    params.storeCd     = $("#targetSelectStoreCd").val();
    params.copyStoreCd = $("#copySelectStoreCd").val();
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params.sid = document.getElementsByName('sessionId')[0].value;
    }
    
    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/outstockReqDate/days/copy.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        $scope._popMsg(messages["cmm.saveSucc"]);
      }
    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
    });
  };
}]);

/** 특정일 그리드 controller */
app.controller('reqDateCopySpecificCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('reqDateCopySpecificCtrl', $scope, $http, true));
  
  $scope.hqOfficeCd = gvHqOfficeCd;
  
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("reqDateCopySpecificCtrl");

    // 그리드 DataMap 설정
    $scope.sysStatFgMap     = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.outstockReqYnMap = new wijmo.grid.DataMap([
      {id: "Y", name: messages["outstockReqDate.outstockReqYnY"]},
      {id: "N", name: messages["outstockReqDate.outstockReqYnN"]},
    ], 'id', 'name');

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
        else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("reqDateCopySpecificCtrl", function (event, data) {
    if (data.proc == "search") {
      $scope.searchReqDateSpecificList();
    }
    else if (data.proc == "copy") {
      $scope.specificCopy();
    }
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.searchReqDateSpecificList = function () {
    // 파라미터
    var params       = {};
    params.storeCd   = $("#targetSelectStoreCd").val();
    params.listScale = 5000; // 조회 쿼리를 요일별 리스트 조회와 공통으로 사용하기 때문에 해당 페이지에선 페이징처리가 필요없어 listScale을 크게 줌.
    params.curr      = 1;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/order/outstockReqDate/specificDate/list.sb", params, function () {
      $scope.searchEnd()
    });
  };

  // 특정일 그리드 조회 완료 후 호출 함수. gChk 를 모두 체크상태로 변경한다.
  $scope.searchEnd = function () {
    var flex = $scope.flex;
    if (flex) {
      for (var i = 0; i < flex.rows.length; i++) {
        flex.setCellData(i, 0, true);
      }
      flex.collectionView.commitEdit();
    }
  };

  // 특정일 복사
  $scope.specificCopy = function () {
    var params = [];
    var flex   = $scope.flex;
    for (var i = 0; i < flex.rows.length; i++) {
      if (flex.getCellData(i, 0)) {
        // 타겟매장과 복사할 매장이 동일합니다.
        if (flex.rows[i]._data.storeCd == $("#copySelectStoreCd").val()) {
          $scope._popMsg(messages["outstockReqDate.duplicate.targetSelectStore"]);
          return false;
        }
        flex.rows[i]._data.copyStoreCd = $("#copySelectStoreCd").val();
        params.push(flex.rows[i]._data);
      }
    }

    // 길이체크
    if (params.length <= 0) {
      // 특정일은 복사할 내용이 없습니다. 출고가능요일을 복사하시겠습니까?
      var msg = messages["outstockReqDate.not.copySpecificDate"] + " " + messages["outstockReqDate.copyDays"];
      s_alert.popConf(msg, function () {
        $scope._broadcast('reqDateCopyDaysCtrl', {proc: "copy"});
      });
      return false;
    }
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    params.hqOfficeCd	=	$scope.hqOfficeCd;
    $scope._save("/iostock/order/outstockReqDate/specificDate/copy.sb", params, function () {
    	$scope._broadcast('reqDateCopyDaysCtrl', {proc: "copy"});
//        $scope.searchspecificDateList()
      });
    // ajax 통신 설정
//    $http({
//      method : 'POST', //방식
//      url    : '/iostock/order/outstockReqDate/specificDate/copy.sb', /* 통신할 URL */
//      data   : params, /* 파라메터로 보낼 데이터 */
//      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
//    }).then(function successCallback(response) {
//      if ($scope._httpStatusCheck(response, true)) {
//        $scope._broadcast('reqDateCopyDaysCtrl', {proc: "copy"});
//      }
//    }, function errorCallback(response) {
//      // called asynchronously if an error occurs
//      // or server returns response with an error status.
//      $scope._popMsg(messages["cmm.saveFail"]);
//      return false;
//    }).then(function () {
//    });
  };

}]);
