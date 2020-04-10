/** 반품마감 추가등록 그리드 controller */
app.controller('rtnDstbCloseStoreAddCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnDstbCloseStoreAddCtrl', $scope, $http, true));

  $scope._setComboData("option1", [
    {"name": messages["rtnDstbCloseStore.add.option1All"], "value": ""},
    {"name": messages["rtnDstbCloseStore.add.option1SafeStock"], "value": "S"}
  ]);

  $scope._setComboData("option2", [
    {"name": messages["rtnDstbCloseStore.add.option2All"], "value": ""},
    {"name": messages["rtnDstbCloseStore.add.option2Order"], "value": "ORD"},
    {"name": messages["rtnDstbCloseStore.add.option2Outstock"], "value": "OUT"},
    {"name": messages["rtnDstbCloseStore.add.option2Sale"], "value": "SALE"}
  ]);

  $scope.srchRegStartDate = wcombo.genDate("#srchRegStartDate");
  $scope.srchRegEndDate   = wcombo.genDate("#srchRegEndDate");

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "097";
    var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // s.allowMerging = wijmo.grid.AllowMerging.AllHeaders;
    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "mgrUnitQty") {
          $scope.calcAmt(item);
        }
        else if (col.binding === "mgrEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
          if (item.poUnitQty === 1) {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용.
            e.cell.outerHTML = e.cell.outerHTML;
          }
          else {
            $scope.calcAmt(item);
          }
        }
      }
    });

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 주문수량 수정시 금액,VAT,합계 계산하여 보여준다.
        if (col.binding === "mgrUnitQty" || col.binding === "mgrEtcQty") {
          var item        = s.rows[e.row].dataItem;
          $scope.calcAmt(item);
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


  // 금액 계산
  $scope.calcAmt = function (item) {
    /** 수량이 없는 경우 계산하지 않음.
     null 또는 undefined 가 나올수 있으므로 확실하게 확인하기 위해 nvl 처리로 null 로 바꿔서 비교 */
    if (nvl(item.mgrUnitQty, null) === null && (item.poUnitQty !== 1 && nvl(item.mgrEtcQty, null) === null)) return false;

    var mgrSplyUprc = parseInt(item.mgrSplyUprc);
    var poUnitQty   = parseInt(item.poUnitQty);
    var vat01       = parseInt(item.vatFg01);
    var envst0011   = parseInt(item.envst0011);

    var unitQty    = parseInt(nvl(item.mgrUnitQty, 0)) * parseInt(item.poUnitQty);
    var etcQty     = parseInt(nvl(item.mgrEtcQty, 0));
    var totQty     = parseInt(unitQty + etcQty);
    var tempMgrAmt = Math.round(totQty * mgrSplyUprc / poUnitQty);
    var mgrAmt     = tempMgrAmt - Math.round(tempMgrAmt * vat01 * envst0011 / 11);
    var mgrVat     = Math.round(tempMgrAmt * vat01 / (10 + envst0011));
    var mgrTot     = parseInt(mgrAmt + mgrVat);

    item.mgrTotQty = totQty; // 총수량
    item.mgrAmt    = mgrAmt; // 금액
    item.mgrVat    = mgrVat; // VAT
    item.mgrTot    = mgrTot; // 합계
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnDstbCloseStoreAddCtrl", function (event, data) {

    // 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    if (!$.isEmptyObject(data)) {
      $scope.reqDate = data.reqDate;
      $scope.slipFg  = data.slipFg;

      // 값 초기화
      $scope.prodClassCdNm = messages["cmm.all"];
      $scope.prodClassCd   = '';

      $scope.wjRtnDstbCloseStoreAddLayer.show(true);
      $("#addProdSubTitle").html(' ('+messages["rtnDstbCloseStore.add.reqDate"]+' : ' + getFormatDate($scope.reqDate, '-') + ')');
    }
    else { // 페이징처리에서 broadcast 호출시
      $scope.searchRtnDstbCloseStoreAddList();
    }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 조회
  $scope.search = function () {
    if ($("#rtnDstbCloseStoreAddSelectStoreCd").val() === "") {
      $scope._popMsg(messages["rtnDstbCloseStore.add.require.selectStore"]); // 매장을 선택해 주세요.
      return false;
    }
    $scope.searchRtnDstbCloseStoreAddList();
  };

  // 반품가능상품 리스트 조회
  $scope.searchRtnDstbCloseStoreAddList = function () {
    $scope.storeCd = $("#rtnDstbCloseStoreAddSelectStoreCd").val();
    // 파라미터
    var params       = {};
    params.reqDate   = $scope.reqDate;
    params.slipFg    = $scope.slipFg;
    params.storeCd   = $scope.storeCd;
    params.startDate = wijmo.Globalize.format($scope.srchRegStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.srchRegEndDate.value, 'yyyyMMdd');
    params.listScale = 50;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStoreAdd/list.sb", params);
  };

  // 반품 상품 저장
  $scope.saveRtnDstbCloseStoreAdd = function () {
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      if (item.mgrTotQty !== null && item.mgrTotQty !== "0" && (parseInt(item.mgrTotQty) < parseInt(item.poMinQty))) {
        $scope._popMsg(messages["rtnDstbCloseStore.add.not.minMgrQty"]); // 반품수량은 최소주문수량 이상 입력하셔야 합니다.
        return false;
      }
      if (item.mgrEtcQty !== null && (parseInt(item.mgrEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["rtnDstbCloseStore.add.not.mgrEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
      if (item.mgrTot !== null && (parseInt(item.mgrTot) > 9999999999)) {
        $scope._popMsg(messages["rtnDstbCloseStore.add.not.overMgrTot"]); // 반품금액이 너무 큽니다.
        return false;
      }

      item.status    = "U";
      item.reqDate   = $scope.reqDate;
      item.slipFg    = $scope.slipFg;
      item.storeCd   = $scope.storeCd;
      item.empNo     = "0000";
      item.storageCd = "999";
      item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      params.push(item);
    }

    $scope._save("/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStoreAdd/save.sb", params, function () {
      $scope.saveAddProdCallback()
    });
  };

  // 저장 후 콜백 서치 함수
  $scope.saveAddProdCallback = function () {
    $scope.searchRtnDstbCloseStoreAddList();

    var rtnDstbCloseStoreScope = agrid.getScope('rtnDstbCloseStoreCtrl');
    rtnDstbCloseStoreScope.searchRtnDstbCloseStoreList();
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.rtnDstbCloseStoreAddSelectStoreShow = function () {
    $scope._broadcast('rtnDstbCloseStoreAddSelectStoreCtrl');
  };

  // 매장 선택시 그리드 초기화
  $scope.fnGridClear = function () {
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;
    cv.refresh();
  };


  // 옵션2 값 변경 이벤트 함수
  $scope.selectedIndexChanged = function (s, e) {
    if (s.selectedValue === "") {
      $scope.option2LayerHide();
    }
    else {
      $scope.option2LayerHide();
      $("#option2DateLayer").show();

      if (s.selectedValue === "ORD") {
        $("#option2OrdLayer").show();
        $("#option2OrdLayer2").show();
      }
      else if (s.selectedValue === "OUT") {
        $("#option2OutLayer").show();
        $("#option2OutLayer2").show();
      }
      else if (s.selectedValue === "SALE") {
        $("#option2SaleLayer").show();
        $("#option2SaleLayer2").show();
      }
    }
  };


  $scope.option2LayerHide = function () {
    $("#option2DateLayer").hide();
    $("#option2OrdLayer").hide();
    $("#option2OrdLayer2").hide();
    $("#option2OutLayer").hide();
    $("#option2OutLayer2").hide();
    $("#option2SaleLayer").hide();
    $("#option2SaleLayer2").hide();
  };


  // 상품분류정보 팝업
  $scope.popUpProdClass = function () {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope          = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd    = scope.getSelectedClass();
        var params         = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
          function (response) {
            $scope.prodClassCd   = prodClassCd;
            $scope.prodClassCdNm = response.data.data;
          }
        );
      }
    });
  };


  // 엑셀 다운로드
  $scope.excelDownload = function () {
    if($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : false,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, 'excel.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };


  /** 엑셀업로드 관련 공통 함수 */
  $scope.excelTextUpload = function (prcsFg) {
    if ($("#dstbCloseStoreAddSelectStoreCd").val() === '' && prcsFg !== 'excelFormDown') {
      $scope._popMsg(messages["dstbCloseStore.add.require.selectStore"]); // 매장을 선택해 주세요.
      return false;
    }

    var excelUploadScope = agrid.getScope('excelUploadCtrl');
    /** 업로드 구분. 해당값에 따라 엑셀 양식이 달라짐 */
    var uploadFg = 'dstbCloseStore';

    // 엑셀 양식다운로드
    if (prcsFg === 'excelFormDown') {
      excelUploadScope.excelFormDownload(uploadFg);
    }
    else{
      var msg = messages["excelUpload.confmMsg"]; // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?
      s_alert.popConf(msg, function () {
        excelUploadScope.uploadFg   = uploadFg;
        excelUploadScope.storeCd    = $("#rtnDstbCloseStoreAddSelectStoreCd").val();
        /** 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
        excelUploadScope.parentCtrl = 'rtnDstbCloseStoreAddCtrl';
        // 엑셀 업로드
        if (prcsFg === 'excelUp') {
          $("#excelUpFile").val('');
          $("#excelUpFile").trigger('click');
        }
        // 텍스트 업로드
        else if (prcsFg === 'textUp') {
          $("#textUpFile").val('');
          $("#textUpFile").trigger('click');
        }
      });
    }
  };


  /** 업로드 완료 후 callback 함수. 업로드 이후 로직 작성. */
  $scope.uploadCallBack = function () {
    var params      = {};
    params.date     = $scope.reqDate;
    params.slipFg   = $scope.slipFg;
    params.hdRemark = $scope.regHdRemark;
    params.addQtyFg = $scope.addQtyFg;

    var excelUploadScope = agrid.getScope('excelUploadCtrl');

    $http({
      method : 'POST', //방식
      url    : '/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStoreAdd/excelUpload.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // excelUploadScope.excelUploadingPopup(false); // 업로딩 팝업 닫기

        // 엑셀 에러내역 팝업 호출
        $scope.excelUploadErrInfo();

        // 등록 그리드 및 여신, 부모 그리드 조회
        $scope.saveRegistCallback();
      }
    }, function errorCallback(response) {
      $scope._popMsg(response.data.message);
      // excelUploadScope.excelUploadingPopup(false); // 업로딩 팝업 닫기
      return false;
    }).then(function () {
      excelUploadScope.excelUploadingPopup(false); // 업로딩 팝업 닫기
    });
  };


  // 에러내역 팝업 호출
  $scope.excelUploadErrInfo = function () {
    var params      = {};
    params.uploadFg = 'dstbCloseStore';
    $scope._broadcast('excelUploadErrInfoCtrl', params);
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
