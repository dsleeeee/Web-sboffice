/** 물량오류 상세 그리드 controller */
app.controller('hqMoveDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('hqMoveDtlCtrl', $scope, $http, true));

  $scope.moveDate            = wcombo.genDate("#dtlHqMoveMoveDate");
  $scope.moveDate.isReadOnly = true;

  $scope._setComboData("srchHqMoveDtlDlvrFg", [
    {"name": messages["hqStoreMove.dlvrFg0"], "value": "0"},
    {"name": messages["hqStoreMove.dlvrFg1"], "value": "1"},
    {"name": messages["hqStoreMove.dlvrFg2"], "value": "2"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "097";
    var url                 = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "outEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
          if (item.poUnitQty === 1) {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용.
            var html         = e.cell.outerHTML;
            e.cell.outerHTML = html;
          }
        }
      }
    });

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 주문수량 수정시 금액,VAT,합계 계산하여 보여준다.
        if (col.binding === "outUnitQty" || col.binding === "outEtcQty" || col.binding === "outSplyUprc" || col.binding === "inSplyUprc") {
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
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    s.columnHeaders.rows[0].dataItem = {
      prodCd     : messages["hqStoreMove.dtl.prodCd"],
      prodNm     : messages["hqStoreMove.dtl.prodNm"],
      poUnitFgNm : messages["hqStoreMove.dtl.poUnitFg"],
      poUnitQty  : messages["hqStoreMove.dtl.poUnitQty"],
      outUnitQty : messages["hqStoreMove.dtl.qty"],
      outEtcQty  : messages["hqStoreMove.dtl.qty"],
      outTotQty  : messages["hqStoreMove.dtl.totQty"],
      outSplyUprc: messages["hqStoreMove.dtl.out"],
      outCurrQty : messages["hqStoreMove.reg.out"],
      outAmt     : messages["hqStoreMove.dtl.out"],
      outVat     : messages["hqStoreMove.dtl.out"],
      outTot     : messages["hqStoreMove.dtl.out"],
      inSplyUprc : messages["hqStoreMove.dtl.in"],
      inCurrQty  : messages["hqStoreMove.reg.in"],
      inAmt      : messages["hqStoreMove.dtl.in"],
      inVat      : messages["hqStoreMove.dtl.in"],
      inTot      : messages["hqStoreMove.dtl.in"],
      vatFg      : messages["hqStoreMove.dtl.vatFg"],
      envst0011  : messages["hqStoreMove.dtl.envst0011"],
      envst0011  : messages["hqStoreMove.dtl.envst0011"],
      poUnitFg   : messages["hqStoreMove.dtl.poUnitFg"],
    };

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


  $scope.calcAmt = function (item) {
    /** 수량이 없는 경우 계산하지 않음.
        null 또는 undefined 가 나올수 있으므로 확실하게 확인하기 위해 nvl 처리로 null 로 바꿔서 비교 */
    if (nvl(item.outUnitQty, null) === null && (item.poUnitQty !== 1 && nvl(item.outEtcQty, null) === null)) return false;

    var outSplyUprc  = parseInt(nvl(item.outSplyUprc, 0));
    var inSplyUprc   = parseInt(nvl(item.inSplyUprc, 0));
    var poUnitQty    = parseInt(item.poUnitQty);
    var vat01        = parseInt(item.vatFg01);
    var outEnvst0011 = parseInt(item.outEnvst0011);
    var inEnvst0011  = parseInt(item.inEnvst0011);

    var unitQty    = parseInt(nvl(item.outUnitQty, 0)) * parseInt(item.poUnitQty);
    var etcQty     = parseInt(nvl(item.outEtcQty, 0));
    var totQty     = parseInt(unitQty + etcQty);
    var tempOutAmt = Math.round(totQty * outSplyUprc / poUnitQty);
    var tempInAmt  = Math.round(totQty * inSplyUprc / poUnitQty);
    var outAmt     = tempOutAmt - Math.round(tempOutAmt * vat01 * outEnvst0011 / 11);
    var outVat     = Math.round(tempOutAmt * vat01 / (10 + outEnvst0011));
    var outTot     = parseInt(outAmt + outVat);
    var inAmt      = tempInAmt - Math.round(tempInAmt * vat01 * inEnvst0011 / 11);
    var inVat      = Math.round(tempInAmt * vat01 / (10 + inEnvst0011));
    var inTot      = parseInt(inAmt + inVat);

    item.outTotQty = totQty; // 총수량
    item.outAmt    = outAmt; // 이출금액
    item.outVat    = outVat; // 이출VAT
    item.outTot    = outTot; // 이출합계
    item.inAmt     = inAmt; // 이입금액
    item.inVat     = inVat; // 이입VAT
    item.inTot     = inTot; // 이입합계
    
	if (nvl(item.outTotQty, null) > nvl(item.outCurrQty, null)){
		alert("이동할 수량이 현재 수량보다 많습니다.");
		item.outUnitQty = null;
	    item.outTotQty  = null; // 총수량
	    item.outAmt     = null; // 이출금액
	    item.outVat     = null; // 이출VAT
	    item.outTot     = null; // 이출합계
	    item.inAmt      = null; // 이입금액
	    item.inVat      = null; // 이입VAT
	    item.inTot      = null; // 이입합계
		return false;  
	}
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("hqMoveDtlCtrl", function (event, data) {
    $scope.slipNo = data.slipNo;
    $scope.wjHqMoveDtlLayer.show(true);

    $scope.getSlipNoInfo();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 전표상세 조회
  $scope.getSlipNoInfo = function () {
    var params    = {};
    params.slipNo = $scope.slipNo;
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
       params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/move/hqMove/hqMoveDtl/getSlipNoInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {

          $scope.moveDate.value = new Date(getFormatDate(response.data.data.moveDate, "-"));
          $scope.procFg         = response.data.data.procFg;
          $scope.dlvrFg         = response.data.data.dlvrFg;
          $scope.dtlHdRemark    = response.data.data.remark;
          $scope.outStorageCd     = response.data.data.outStorageCd;
          $scope.outStorageNm     = response.data.data.outStorageNm;
          $scope.inStorageCd      = response.data.data.inStorageCd;
          $scope.inStorageNm      = response.data.data.inStorageNm;

          $("#hqMoveDtlOutSelectStorageCd").val($scope.outStorageCd);
          $("#hqMoveDtlOutSelectStorageNm").val($scope.outStorageNm);
          $("#hqMoveDtlInSelectStorageCd").val($scope.inStorageCd);
          $("#hqMoveDtlInSelectStorageNm").val($scope.inStorageNm);

          var regDt      = response.data.data.regDt;
          var hqConfmDt  = response.data.data.hqConfmDt;

          $("#regHqDt").html(regDt !== null ? getFormatDateTime(regDt) : "");
          $("#hqConfmHqDt").html(hqConfmDt !== null ? getFormatDateTime(hqConfmDt) : "");

          if ($scope.procFg === "3") {
            $scope.hqMoveDtlBtnLayer = false;
            $scope.flex.isReadOnly        = true;
          } else {
            $scope.hqMoveDtlBtnLayer = true;
            $scope.flex.isReadOnly        = false;
          }

          $scope.searchHqMoveDtlList();
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


  // 매장이동관리 상세내역 리스트 조회
  $scope.searchHqMoveDtlList = function () {
    // 파라미터
    var params    = {};
    params.slipNo = $scope.slipNo;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/move/hqMove/hqMoveDtl/list.sb", params, function () {
    });
  };


  // 저장
  $scope.save = function (confirmFg) {
    var params = [];

    // 본사확정이면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
    if (confirmFg === "Y" && $scope.flex.collectionView.itemsEdited.length <= 0) {
    	var size = $scope.flex.collectionView.items.length;
    	for(var i=0; i<size; i++) {
	      var item = $scope.flex.collectionView.items[i];
	      if (item === null) return false;
	
	      $scope.flex.collectionView.editItem(item);
	      item.status = "U";
	      $scope.flex.collectionView.commitEdit();
    	}
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      if (item.etcQty !== null && (parseInt(item.etcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["hqStoreMove.dtl.not.etcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
      if (item.outTot !== null && (parseInt(item.outTot) > 9999999999)) {
        $scope._popMsg(messages["hqStoreMove.dtl.not.overOutTot"]); // 이출금액이 너무 큽니다.
        return false;
      }
      if (item.inTot !== null && (parseInt(item.inTot) > 9999999999)) {
        $scope._popMsg(messages["hqStoreMove.dtl.not.overInTot"]); // 이입금액이 너무 큽니다.
        return false;
      }

      item.status    = "U";
      item.outStorageCd = $scope.outStorageCd;
      item.inStorageCd  = $scope.inStorageCd;
      item.slipNo    = $scope.slipNo;
      item.dlvrFg    = $scope.dlvrFg;
      item.remark    = $scope.dtlHdRemark;
      item.storageCd = "001";
      item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      item.confirmFg = confirmFg;

      params.push(item);
    }

    $scope._save("/iostock/move/hqMove/hqMoveDtl/save.sb", params, function () {
      $scope.saveHqMoveDtlCallback()
    });
  };


  $scope.confirm = function () {
    var msg = messages["hqStoreMove.dtl.confirmMsg"]; // 현전표를 확정하시겠습니까?
    s_alert.popConf(msg, function () {
      $scope.save('Y');
    });
  };


  $scope.saveHqMoveDtlCallback = function () {
    $scope.wjHqMoveDtlLayer.hide(true);

    var hqStoreMoveScope = agrid.getScope('hqMoveCtrl');
    hqStoreMoveScope.searchHqMoveList();
  };


  $scope.addProd = function () {
    var params        	= {};
    params.slipNo     	= $scope.slipNo;
    params.outStorageCd = $scope.outStorageCd;
    params.inStorageCd  = $scope.inStorageCd;

    $scope._broadcast('hqMoveAddProdCtrl', params);
  };


  $scope.delete = function () {
    var msg = messages["hqStoreMove.dtl.delMsg"]; // 현전표를 삭제하시겠습니까?
    s_alert.popConf(msg, function () {
      var params    = {};
      params.slipNo = $scope.slipNo;
      //가상로그인 session 설정
      if(document.getElementsByName('sessionId')[0]){
         params['sid'] = document.getElementsByName('sessionId')[0].value;
      }

      /** 로딩바 show */
      $scope.$broadcast('loadingPopupActive', messages["cmm.saving"]);

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/iostock/move/hqMove/hqMoveDtl/delete.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
          $scope._popMsg(messages["cmm.saveSucc"]);
          $scope.saveHqMoveDtlCallback();
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        $scope._popMsg(messages["cmm.saveFail"]);
        /** 로딩바 hide */
        $scope.$broadcast('loadingPopupInactive');
        return false;
      }).then(function () {
        // "complete" code here
        /** 로딩바 hide */
        $scope.$broadcast('loadingPopupInactive');
      });
    });
  };


  // 창고선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.hqMoveDtlOutSelectStorageShow = function () {
    $scope._broadcast('hqMoveDtlOutSelectStorageCtrl');
  };

  // 창고선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.hqMoveDtlInSelectStorageShow = function () {
    $scope._broadcast('hqMoveDtlInSelectStorageCtrl');
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
