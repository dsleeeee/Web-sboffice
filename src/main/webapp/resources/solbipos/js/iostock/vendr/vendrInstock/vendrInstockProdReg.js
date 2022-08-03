/** 입고/반출 상품 추가/변경 그리드 controller */
app.controller('vendrInstockProdRegCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrInstockProdRegCtrl', $scope, $http, true));

  $scope._setComboData("safeStockFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["vendrInstock.reg.safeStockY"], "value": "Y"}
  ]);

  $scope._setComboData("addQtyFg", [
    {"name": messages["vendrInstock.reg.addQtyFgApply"], "value": "apply"},
    {"name": messages["vendrInstock.reg.addQtyFgAdd"], "value": "add"}
  ]);
  
  var global_storage_cnt = 0;	//매장의 창고 갯수
  
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    
//    // 출고창고
//    var url = '/iostock/order/outstockConfm/outstockConfm/getOutStorageCombo.sb';    
//    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
//    $scope._queryCombo("combo", "saveDtlOutStorageCd", null, url, comboParams, null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
//    
//    comboParams         = {};
    comboParams.nmcodeGrpCd = "093";
    var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "inEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
          if (item.poUnitQty === 1) {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용
            e.cell.outerHTML = e.cell.outerHTML;
          }
        }
      }
    });

    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "inEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
        var dataItem = s.rows[elements.row].dataItem;
        if (dataItem.poUnitQty === 1) {
          elements.cancel = true;
        }
      }
    });
    
    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 원가 및 수량 수정시 합계 계산하여 보여준다.
        if (col.binding === "costUprc" || col.binding === "inUnitQty" || col.binding === "inEtcQty") {
          var item = s.rows[e.row].dataItem;
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
       if (col.isReadOnly || panel.grid.isReadOnly) {
         wijmo.addClass(cell, 'wj-custom-readonly');
       }
     }
   }
    
    
  //Grid Header 2줄 - START	----------------------------------------------------------------
      s.allowMerging  = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());
      
      
      //첫째줄 Header 생성
      var dataItem = {};
          dataItem.prodCd         = messages["vendrInstock.reg.prodCd"         ];	//상품코드
          dataItem.prodNm         = messages["vendrInstock.reg.prodNm"         ];	//상품명
          dataItem.lastCostUprc   = messages["vendrInstock.reg.lastCostUprc"   ];	//최종원가
          dataItem.poUnitFg       = messages["vendrInstock.reg.poUnitFg"       ];   //주문단위 
          dataItem.poUnitQty      = messages["vendrInstock.reg.poUnitQty"      ];   //입수
          dataItem.prevInUnitQty  = messages["vendrInstock.reg.prevInUnitQty"  ];   //기수량
          dataItem.prevInEtcQty   = messages["vendrInstock.reg.prevInUnitQty"  ];   //기수량          
          dataItem.costUprc       = messages["vendrInstock.reg.costUprc"       ];	//원가단가(VAT포함)
          dataItem.inUnitQty      = messages["vendrInstock.reg.inUnitQty"      ];	//수량   
          dataItem.inEtcQty       = messages["vendrInstock.reg.inUnitQty"      ];	//수량   
          dataItem.prevInTotQty   = messages["vendrInstock.reg.prevInTotQty"   ];	//이전총수량
          dataItem.inTotQty       = messages["vendrInstock.reg.inTotQty"       ];	//총수량
          dataItem.inAmt          = messages["vendrInstock.reg.inAmt"          ];	//금액
          dataItem.inVat          = messages["vendrInstock.reg.inVat"          ];	//VAT
          dataItem.inTot          = messages["vendrInstock.reg.inTot"          ];	//합계(VAT포함)
          dataItem.vatFg          = messages["vendrInstock.reg.vatFg"          ];	//상품부가세구분
          dataItem.vendrVatFg01   = messages["vendrInstock.reg.vendrVatFg01"   ];	//부가세포함여부
//          dataItem.storeSplyUprc  = messages["vendrInstock.reg.storeSplyUprc"  ];	//매장공급가

      s.columnHeaders.rows[0].dataItem = dataItem;
  //Grid Header 2줄 - END		----------------------------------------------------------------

  };

  $scope.calcAmt = function (item) {
    /** 수량이 없는 경우 계산하지 않음.
        null 또는 undefined 가 나올수 있으므로 확실하게 확인하기 위해 nvl 처리로 null 로 바꿔서 비교 */
    if (nvl(item.inUnitQty, null) === null && (item.poUnitQty !== 1 && nvl(item.inEtcQty, null) === null)) return false;

    var costUprc     = parseFloat(item.costUprc);
    var poUnitQty    = parseInt(item.poUnitQty);
    var vat01        = parseInt(item.vatFg01);
    var vendrVatFg01 = parseInt(item.vendrVatFg01);

    var unitQty = (parseInt(nvl(item.prevOrderUnitQty, 0)) + parseInt(nvl(item.inUnitQty, 0))) * parseInt(item.poUnitQty);
    var etcQty  = parseInt(nvl(item.prevOrderEtcQty, 0)) + parseInt(nvl(item.inEtcQty, 0));
    var totQty  = parseInt(unitQty + etcQty);
    var tempAmt = Math.round(totQty * costUprc / poUnitQty);
    var inAmt   = tempAmt - Math.round(tempAmt * vat01 * vendrVatFg01 / 11);
    var inVat   = Math.round(tempAmt * vat01 / (10 + vendrVatFg01));
    var inTot   = parseInt(inAmt + inVat);

    item.inTotQty = totQty;   // 총주문수량
    item.inAmt    = inAmt; // 금액
    item.inVat    = inVat; // VAT
    item.inTot    = inTot; // 합계
  };
  //$scope.calcAmt		--------------------------------------------------------------------------------------------------------------------------

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("vendrInstockProdRegCtrl", function (event, data) {

    if (!$.isEmptyObject(data)) {
      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      $scope.slipNo  = data.slipNo;
      $scope.slipFg  = data.slipFg;
      $scope.vendrCd = data.vendrCd;
      
      // 값 초기화
      $scope.prodClassCdNm = messages["cmm.all"];
      $scope.prodClassCd   = '';
      $scope.storeSplyChk  = false;

      $scope.wjVendrInstockProdRegLayer.show(true);
      
    }
    // 페이징처리에서 broadcast 호출시
    else {
      $scope.searchVendrInstockRegList();
    }
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 조회버튼 클릭으로 조회시
  $scope.fnSearch = function () {
    //$scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
    $scope.searchVendrInstockRegList();
  };


  // 상품 리스트 조회
  $scope.searchVendrInstockRegList = function () {
    // 파라미터
    var params       = {};
    params.slipNo    = $scope.slipNo;
    params.slipFg    = $scope.slipFg;
    params.vendrCd   = $scope.vendrCd;
    params.listScale = 50;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/vendr/vendrInstock/vendrInstockProdReg/list.sb", params, function () {
    	
  	});
  };


  // 상품 저장
  $scope.save = function () {
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      // 이전 주문수량이 없으면서 주문수량 0인 경우 저장하지 않는다.
      if (item.prevInTotQty === null && item.inTotQty === 0) {
        continue;
      }
      if (item.inUnitQty !== null && (0 > parseInt(item.inUnitQty))) {
          $scope._popMsg(messages['vendrInstock.reg.above.zero.inUnitQty']); // 주문단위수량은 0이상 입력합니다.
          return false;
       }
      if (item.inEtcQty !== null && (0 > parseInt(item.inEtcQty))) {
          $scope._popMsg(messages['vendrInstock.reg.above.zero.inEtcQty']); //낱개수량은 0이상 입력합니다.
          return false;
      }
      if (item.inEtcQty !== null && (parseInt(item.inEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages['vendrInstock.reg.not.inEtcQty']); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
      if (item.inTot !== null && (parseInt(item.inTot) > 9999999999)) {
        $scope._popMsg(messages['vendrInstock.reg.not.overInTot']); // 주문금액이 너무 큽니다.
        return false;
      }

      item.status      = "U";
      item.slipNo      = $scope.slipNo;
      item.slipFg      = $scope.slipFg;
      item.storageCd   = "999";			//001 -> 999
      item.hqBrandCd   = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      item.storeSplyFg = ($scope.storeSplyChk === true ? 'Y' : 'N');
      item.prodRegFg = "2"; // 상품추가/변경에서 상품등록
//      item.outStorageCd	= $scope.save.dtl.outStorageCd;
      
    
      params.push(item);
    }

    $scope._save("/iostock/vendr/vendrInstock/vendrInstockProdReg/save.sb", params, function () {
      $scope.saveRegistCallback()
    });
  };


  // 저장 후 콜백 서치 함수
  $scope.saveRegistCallback = function () {
    $scope.searchVendrInstockRegList();

    var vendrInstockProdScope = agrid.getScope('vendrInstockProdCtrl');
    vendrInstockProdScope.procFgCheck();
  };


  $scope.setLastCostToCostUprc = function () {
    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
    // 데이터 처리중 팝업 띄우기위해 $timeout 사용.
    $timeout(function () {
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        if (item.lastCostUprc !== null) {
          $scope.flex.collectionView.editItem(item);

          if (nvl(item.lastCostUprc, 0) > 0) {
            item.costUprc = parseInt(item.lastCostUprc);
          }
          $scope.calcAmt(item);
          $scope.flex.collectionView.commitEdit();
        }
      }
      $scope.$broadcast('loadingPopupInactive');
    }, 100);
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
      $scope._popMsg(messages["excelUploadMPS.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
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
    var excelUploadScope = agrid.getScope('excelUploadMPSCtrl');
    /** 업로드 구분. 해당값에 따라 엑셀 양식이 달라짐. */
    var uploadFg = 'vendr';

    // 엑셀 양식다운로드
    if (prcsFg === 'excelFormDown') {
      excelUploadScope.excelFormDownload(uploadFg);
    }
    else{
      var msg = messages["excelUploadMPS.confmMsg"]; // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?
      s_alert.popConf(msg, function () {
        excelUploadScope.uploadFg   = uploadFg;
        /** 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
        excelUploadScope.parentCtrl = 'vendrInstockProdRegCtrl';
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
    params.slipNo   = $scope.slipNo;
    params.slipFg   = $scope.slipFg;
    params.vendrCd  = $scope.vendrCd;
    params.addQtyFg = $scope.addQtyFg;

    var excelUploadScope = agrid.getScope('excelUploadMPSCtrl');
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params.sid = document.getElementsByName('sessionId')[0].value;
    }
    
    $http({
      method : 'POST', //방식
      url    : '/iostock/vendr/vendrInstock/vendrInstockProdReg/excelUpload.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 엑셀 에러내역 팝업 호출
        $scope.excelUploadErrInfo();

        // 등록 그리드, 부모 그리드 조회
        $scope.saveRegistCallback();
      }
    }, function errorCallback(response) {
      $scope._popMsg(response.data.message);
      return false;
    }).then(function () {
      excelUploadScope.excelUploadingPopup(false); // 업로딩 팝업 닫기
    });
  };


  // 에러내역 팝업 호출
  $scope.excelUploadErrInfo = function () {
    var params      = {};
    params.uploadFg = 'vendr';
    params.vendrCd  = $scope.vendrCd;
    $scope._broadcast('excelUploadMPSErrInfoCtrl', params);
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
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params.sid = document.getElementsByName('sessionId')[0].value;
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
