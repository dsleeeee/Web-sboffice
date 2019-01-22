/** 반품등록 상세 그리드 controller */
app.controller('rtnStoreOrderDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnStoreOrderDtlCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "097";
    var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "orderEtcQty") { // 입수에 따라 반품수량 컬럼 readonly 컨트롤
          if (item.poUnitQty === 1) {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용
            e.cell.outerHTML = e.cell.outerHTML;
          }
        }
      }
    });

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "orderUnitQty" || col.binding === "orderEtcQty") { // 반품수량 수정시
          var item          = s.rows[e.row].dataItem;
          var orderSplyUprc = parseInt(item.orderSplyUprc);
          var poUnitQty     = parseInt(item.poUnitQty);
          var vat01         = parseInt(item.vatFg01);
          var envst0011     = parseInt(item.envst0011);

          var unitQty  = parseInt(nvl(item.orderUnitQty, 0)) * parseInt(item.poUnitQty);
          var etcQty   = parseInt(nvl(item.orderEtcQty, 0));
          var totQty   = parseInt(unitQty + etcQty);
          var tempAmt  = Math.round(totQty * orderSplyUprc / poUnitQty);
          var orderAmt = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
          var orderVat = Math.round(tempAmt * vat01 / (10 + envst0011));
          var orderTot = parseInt(orderAmt + orderVat);

          item.orderTotQty = totQty;   // 총반품수량
          item.orderAmt    = orderAmt; // 금액
          item.orderVat    = orderVat; // VAT
          item.orderTot    = orderTot; // 합계
        }
      }

      s.collectionView.commitEdit();
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging  = 2;
    s.itemFormatter = function (panel, r, c, cell) {
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
    }
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnStoreOrderDtlCtrl", function (event, data) {
    $scope.reqDate     = data.reqDate;
    $scope.slipFg      = data.slipFg;
    $scope.procFg      = data.procFg;
    $scope.dtlHdRemark = data.hdRemark;
    $scope.storeCd     = data.storeCd;

    $scope.wjRtnStoreOrderDtlLayer.show(true);
    if ($scope.procFg === "00") {
      $scope.btnAddProd = true;
      $scope.btnDtlSave = true;
      $scope.btnConfirm = true;
      $scope.flex.isReadOnly = false;
    }
    else {
      $scope.btnAddProd = false;
      $scope.btnDtlSave = false;
      $scope.btnConfirm = false;
      $scope.flex.isReadOnly = true;
    }

    $("#spanDtlTitle").html(messages["rtnStoreOrder.reqDate"]+' : ' + getFormatDate($scope.reqDate, '-'));
    $scope.wjRtnStoreOrderDtlLayer.show(true);
    $scope.searchRtnStoreOrderDtlList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 반품등록 상세내역 리스트 조회
  $scope.searchRtnStoreOrderDtlList = function () {
    // 파라미터
    var params     = {};
    params.reqDate = $scope.reqDate;
    params.slipFg  = $scope.slipFg;
    params.storeCd = $scope.storeCd;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderDtl/list.sb", params);
  };


  // 반품 상세 저장
  $scope.saveRtnStoreOrderDtl = function (saveFg) {
    var params   = [];
    var orderTot = 0;
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item       = $scope.flex.collectionView.itemsEdited[i];
      item.status    = "U";
      item.reqDate   = $scope.reqDate;
      item.slipFg    = $scope.slipFg;
      item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리.
      item.hdRemark  = $scope.dtlHdRemark;
      item.storeCd   = $scope.storeCd;
      orderTot += parseInt(item.orderTot);
      params.push(item);
    }

    // 파라미터 길이체크
    if (params.length <= 0) {
      // 수정된 파라미터가 없더라도 확정은 진행되어야함.
      if (saveFg === "confirm") {
        $scope.confirm();
      }
      else {
        $scope._popMsg(messages["cmm.not.modify"]);
      }
      return false;
    } else {
      params = JSON.stringify(params);
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderRegist/save.sb', /* 통신할 URL */
      data   : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.flex.collectionView.clearChanges();

        // 확정버튼 클릭시에도 변경사항 저장 후에 확정 로직을 진행해야하므로 저장 후에 확정로직 다시 실행.
        if (saveFg === "confirm") {
          $scope.confirm();
        }
        else if (saveFg === "save") {
          $scope.saveOrderDtlCallback();
        }
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
    });
  };


  // 반품확정
  $scope.confirm = function () {
    var params     = {};
    params.reqDate = $scope.reqDate;
    params.slipFg  = $scope.slipFg;
    params.remark  = $scope.dtlHdRemark;
    params.storeCd = $scope.storeCd;

    $scope._save("/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderDtl/confirm.sb", params, function () {
      $scope.saveOrderDtlCallback()
    });
  };


  // 저장 후 콜백 함수
  $scope.saveOrderDtlCallback = function () {
    $scope.searchRtnStoreOrderDtlList();

    var rtnStoreOrderScope = agrid.getScope('rtnStoreOrderCtrl');
    rtnStoreOrderScope.searchRtnStoreOrderList();
  };


  // 상품추가/변경
  $scope.addProd = function () {
    var params        = {};
    params.callParent = "rtnStoreOrderDtl";
    params.reqDate    = $scope.reqDate;
    params.slipFg     = $scope.slipFg;
    params.hdRemark   = $scope.dtlHdRemark;
    params.storeCd    = $scope.storeCd;
    $scope._broadcast("rtnStoreOrderRegistCtrl", params);
  };


  // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
  // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
  // comboId : combo 생성할 ID
  // gridMapId : grid 에서 사용할 Map ID
  // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
  // params : 데이터 조회할 url에 보낼 파라미터
  // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
  // callback : queryCombo 후 callback 할 함수
  $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
    var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
    if (url) {
      comboUrl = url;
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : comboUrl, /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data.list)) {
          var list       = response.data.data.list;
          var comboArray = [];
          var comboData  = {};

          if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
            comboArray = [];
            if (option === "A") {
              comboData.name  = messages["cmm.all"];
              comboData.value = "";
              comboArray.push(comboData);
            } else if (option === "S") {
              comboData.name  = messages["cmm.select"];
              comboData.value = "";
              comboArray.push(comboData);
            }

            for (var i = 0; i < list.length; i++) {
              comboData       = {};
              comboData.name  = list[i].nmcodeNm;
              comboData.value = list[i].nmcodeCd;
              comboArray.push(comboData);
            }
            $scope._setComboData(comboId, comboArray);
          }

          if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
            comboArray = [];
            for (var i = 0; i < list.length; i++) {
              comboData      = {};
              comboData.id   = list[i].nmcodeCd;
              comboData.name = list[i].nmcodeNm;
              comboArray.push(comboData);
            }
            $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
          }
        }
      }
    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.error"]);
      return false;
    }).then(function () {
      if (typeof callback === 'function') {
        $timeout(function () {
          callback();
        }, 10);
      }
    });
  };

}]);
