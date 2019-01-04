/** 세트상품 그리드 controller */
app.controller('setProdAdjRegistCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('setProdAdjRegistCtrl', $scope, $http, true));

  $scope.setDate = wcombo.genDate("#setDate");

  $scope._setComboData("regListScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "prodCd") { // 상품코드
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
        else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "prodCd") { // 상품코드 클릭
          var params    = {};
          params.prodCd = selectedRow.prodCd;
          $scope._broadcast('setProdAdjRegistCompstCtrl', params);
        }
      }
    });

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 조정수량 수정시 금액,VAT,합계 계산하여 보여준다.
        if (col.binding === "setProdQty") {
          var item = s.rows[e.row].dataItem;
          $scope.calcAmt(item);
        }
      }

      s.collectionView.commitEdit();
    });

  };


  $scope.calcAmt = function (item) {
    var costUprc   = parseInt(item.costUprc);
    var setProdQty = parseInt(nvl(item.setProdQty, 0));
    var setProdAmt = parseInt(setProdQty) * parseInt(costUprc);

    item.setProdQty = setProdQty; // 등록수량
    item.setProdAmt = setProdAmt; // 원가금액
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("setProdAdjRegistCtrl", function (event, data) {

    // 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    $scope.wjSetProdAdjRegistLayer.show(true);
    $scope.searchSetProdAdjRegistList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 세트상품 리스트 조회
  $scope.searchSetProdAdjRegistList = function () {
    // 파라미터
    var params = {};

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/setProdAdj/setProdAdj/setProdAdjRegist/list.sb", params);
  };


  // 세트 구성/해체 저장
  $scope.saveSetProdAdjRegist = function (setMakeFg) {
    var params = [];

    // 수정된 상품 가져오기
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      // 체크박스가 체크되어 있으면서 기존에 등록되어 있던 상품은 삭제한다.
      if (item.setProdQty !== null && parseInt(item.setProdQty) > 0) {
        item.status    = "U";
        item.setDate   = wijmo.Globalize.format($scope.setDate.value, 'yyyyMMdd');
        item.setMakeFg = setMakeFg;
        item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관

        params.push(item);
      }
    }

    $scope._save("/stock/setProdAdj/setProdAdj/setProdAdjRegist/save.sb", params, function () {
      $scope.saveRegistCallback()
    });
  };


  // 저장 후 콜백 서치 함수
  $scope.saveRegistCallback = function () {
    var setProdAdjScope = agrid.getScope('setProdAdjCtrl');
    setProdAdjScope.searchSetProdAdjList();

    $scope.wjSetProdAdjRegistLayer.hide(true);
  };

}]);


/** 세트구성상품 그리드 controller */
app.controller('setProdAdjRegistCompstCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('setProdAdjRegistCompstCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("setProdAdjRegistCompstCtrl", function (event, data) {

    // 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    $scope.prodCd = data.prodCd;
    $scope.searchSetProdAdjRegistCompstList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 세트구성상품 리스트 조회
  $scope.searchSetProdAdjRegistCompstList = function () {
    // 파라미터
    var params    = {};
    params.prodCd = $scope.prodCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/stock/setProdAdj/setProdAdj/setProdAdjRegistCompst/list.sb", params);
  };

}]);
