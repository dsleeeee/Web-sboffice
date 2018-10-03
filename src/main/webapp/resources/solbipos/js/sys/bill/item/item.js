/****************************************************************
 *
 * 파일명 : item.js
 * 설  명 : 출력코드구성 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.10     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 출력코드구성 그리드 생성
 */
app.controller('printCodeCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('printCodeCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var contentColumn = s.columns.getColumn("content");
    contentColumn.multiLine = true;
    contentColumn.wordWrap = true;
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "prtCd") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        } else if ( col.binding === "content") {
          wijmo.addClass(e.cell, 'wj-grid-multi-editor');
        }
      }
    });
    // 출력코드구성 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      var dataItem = s.rows[e.row].dataItem;
      if (col.binding === "prtCd") {
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          e.cancel = true;
        }
      }
    });
    // alt + Enter 이벤트. 예제 필드에만.
    s.addEventListener(s.hostElement, 'keydown', function(e) {
      var rowNum = s.selection.row;
      var colNum = s.selection.col;
      var col = s.columns[colNum];
      if (col.binding === "content") {
        if (e.altKey) {
          if (e.keyCode === wijmo.Key.Enter) {
            s.finishEditing(false);
            setTimeout(function () {
              s.startEditing(true, rowNum, colNum, true);
            },10);
          }
        }
      }
    });
    // cell 에디트 종료시 42글자 포멧팅. 예제 필드에만.
    s.cellEditEnded.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if(col.binding === "content") {
        var val = s.getCellData(e.row, e.col);
        var lines = val.split("\n");
        if ( lines != null ) {
          var newValues = new Array();
          var newLine = 0;
          var splitStr = "";
          for (var i = 0; i < lines.length; i++) {
            if (lines[i].getByteLength() <= 42) {
              newValues[newLine++] = lines[i];
            } else {
              splitStr = lines[i].splitByteLen(42);
              for (var n = 0; n < splitStr.length; n++) {
                newValues[newLine++] = splitStr[n];
              }
            }
          }
          val = newValues.join("\n");
          s.setCellData(e.row, e.col, val);
          s.rows[e.row].height = 20 + ( newValues.length * 14 );
        }
      }
    });
  };
  // 그리드 Row 사이즈 정렬
  $scope.autoSizeVisibleRows = function(flex) {
    for ( var r = 0; r < flex.itemsSource.itemCount; r++ ) {
      if(flex.rows[r]._data.content && flex.rows[r]._data.content.split("\n").length > 1 ) {
        flex.autoSizeRow(r, false);
      }
    }
  };
  // 출력코드구성 그리드 조회
  $scope.$on("printCodeCtrl", function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/sys/bill/item/item/list.sb", params, function() {
      // 버튼 Show
      $("#btnAdd").show();
      $("#btnDel").show();
      $("#btnSave").show();

      $scope.autoSizeVisibleRows($scope.flex);
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 출력코드구성 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.samplYn = false;
    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };
  // 출력코드구성 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/sys/bill/item/item/save.sb", params, function() {
      // 저장기능 수행 후 재조회
      $scope._broadcast("printCodeCtrl");
    });
  }
}]);

