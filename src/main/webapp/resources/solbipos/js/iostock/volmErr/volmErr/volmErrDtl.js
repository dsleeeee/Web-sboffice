/** 물량오류 상세 그리드 controller */
app.controller('volmErrDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout){
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('volmErrDtlCtrl', $scope, $http, true));

  $scope.orgnFg = gvOrgnFg;
  $scope.outDate = wcombo.genDateVal("#dtlOutDate", getNextDay());
  $scope.errFgMap = new wijmo.grid.DataMap([
    {id: "0", name: messages["volmErr.reg"]},
    {id: "1", name: messages["volmErr.confirm"]},
  ], 'id', 'name');
  
  // 익일 날짜 셋팅 함수
  function getNextDay() {
	  var today = new Date();
	  var dd = today.getDate()+1;
	  var mm = today.getMonth()+1; //January is 0!
	  var yyyy = today.getFullYear();

	  if (dd < 10) {
	    dd= '0' + dd;
	  }

	  if (mm < 10) {
		mm = '0' + mm;
	  }

	  today = yyyy + mm + dd;

	  return today;
	}
  
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');


    //Header column merge (출고수량, 입고수량)
    s.allowMerging                          = 'ColumnHeaders';
    s.columnHeaders.rows[0].allowMerging    = true;
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("volmErrDtlCtrl", function (event, data) {
    $scope.slipNo   = data.slipNo;
    $scope.slipFg   = data.slipFg;
    $scope.procFg   = data.procFg;
    $scope.storeCd  = data.storeCd;
    $scope.storeNm  = data.storeNm;
    $scope.hdRemark = data.hdRemark;
   
    $scope.wjVolmErrDtlLayer.show(true);
    $("#spanDtlTitle").html(messages["volmErr.dtl.slipNo"] + ' : ' + $scope.slipNo + ', ' + messages["volmErr.dtl.store"] + ' : ' + '[' + $scope.storeCd + '] ' + $scope.storeNm);
        /*
        console.log('data.procFg        : ' + data.procFg     );
        console.log('$scope.procFg      : ' + $scope.procFg   );

        console.log('scope.procFg === 0 : ' + $scope.procFg === "0"     );
        console.log('scope.procFg ==  0 : ' + $scope.procFg ==  "0"     );
        console.log('scope.procFg === 0 : ' + $scope.procFg ===  0      );
        console.log('scope.procFg ==  0 : ' + $scope.procFg ==   0      );
        console.log('scope.procFg === 0 : ' + $scope.procFg === '0'     );
        console.log('scope.procFg ==  0 : ' + $scope.procFg ==  '0'     );

        console.log('data.procFg === 0  : ' + data.procFg === "0"     );
        console.log('data.procFg ==  0  : ' + data.procFg ==  "0"     );
        console.log('data.procFg === 0  : ' + data.procFg ===  0      );
        console.log('data.procFg ==  0  : ' + data.procFg ==   0      );
        console.log('data.procFg === 0  : ' + data.procFg === '0'     );
        console.log('data.procFg ==  0  : ' + data.procFg ==  '0'     );

        console.log('data.procFg ==     : ' + data.procFg === $scope.procFg );
        console.log('data.procFg ==     : ' + data.procFg ==  $scope.procFg );
        */
    
    if ($scope.procFg === "0" && gvOrgnFg !=="S") {    //procFgMap(0:입력, 1:확정)
	      //console.log('000 $scope.procFg === "0"');
	      $("#volmErrBtnLayer").show();
	      $scope.volmErrConfirmFg   = true;
	      $scope.btnDtlSave         = true;
	    }else if($scope.procFg === "0" && gvOrgnFg ==="S"){
	    	$("#volmErrBtnExcelLayer").show();
	    	var flex = $scope.flex;
	    	flex.formatItem.addHandler(function (s, e) {
				if (e.panel === s.cells) {
					var col = s.columns[e.col];
					if (col.binding === "errFg") { // 실매출
						wijmo.addClass(e.cell, 'wj-custom-readonly');
						s.rows[e.row].isReadOnly = true;
			        }
				}
			});
	    }
	    else {
	      //console.log('000 else');
	    	var flex = $scope.flex;
	    	flex.formatItem.addHandler(function (s, e) {
				if (e.panel === s.cells) {
					var col = s.columns[e.col];
					if (col.binding === "errFg") { // 실매출
						wijmo.addClass(e.cell, 'wj-custom-readonly');
						s.rows[e.row].isReadOnly = true;
			        }
				}
			});
	    	
	      $("#volmErrBtnLayer").hide();
	      $scope.volmErrConfirmFg   = false;
	      $scope.btnDtlSave         = false;
	      
	      $("#volmErrBtnExcelLayer").hide();
	    }

	    $("#volmErrConfirmFg"   ).prop("checked", false);
	    $("#divDtlOutDate"      ).hide(); //페이지 호출시 출고일자는 일단 무조건 hide 처리.
   
    // 물량오류 처리구분 콤보박스 조회 및 생성. slipFg 가 있어야 하므로 상세페이지를 호출할때 조회하도록 함.
    var comboParams         = {};
        comboParams.nmcodeGrpCd = "089";
        comboParams.nmcodeItem1 = $scope.slipFg;
        
        // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
        $scope._queryCombo("map", null, "errFgMap", "/iostock/cmm/iostockCmm/getCombo.sb", comboParams);
        
    $scope.searchVolmErrDtlList();
    
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 물량오류 상세내역 리스트 조회
  $scope.searchVolmErrDtlList = function () {
    // 파라미터
    var params    = {};
        params.slipNo = $scope.slipNo;
      $scope._inquirySub("/iostock/volmErr/volmErr/volmErrDtl/list.sb", params, function () {});
  };


  // 저장
  $scope.save = function () { // IF : 확정 AND O2, O4 일때 마감 체크 ; // ELSE : 확정이 아니면 바로 SAVE; 확정 AND O1, O3, O5 일때 바로 SAVE; 
	  for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
          var item = $scope.flex.collectionView.itemsEdited[i];
          if(item.errFg === "O2" || item.errFg === "O4"){
        	  $scope.errFgCk = true;
        	  break;
          }else{
        	  $scope.errFgCk = false;
          }
	  }
	  if(($("#volmErrConfirmFg").is(":checked") && $scope.errFgCk)){ // 확정체크 AND 처리구분 (02,04)
    	// 출고요청가능일인지 여부 체크
    	$scope.storeOrderDateCheck();
      }else{ // 확정이 아니거나, 처리구분이 O1,O3,O5
    	  console.log($("#volmErrConfirmFg").is(":checked"));
    	// 물량오류 처리 함수
    	$scope.saveVolmErr();
      }
  };

  // 출고요청가능일인지 여부 체크
  $scope.storeOrderDateCheck = function () {
    var params     = {};
    params.reqDate = wijmo.Globalize.format($scope.outDate.value, 'yyyyMMdd');
    params.slipFg  = $scope.slipFg;
    params.storeCd = $scope.storeCd;

    //가상로그인 session 설정
	    if(document.getElementsByName('sessionId')[0]){
	    	params['sid'] = document.getElementsByName('sessionId')[0].value;
	    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/storeOrder/storeOrderRegist/orderDateCheck.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          if (response.data.data.orderFg > 0) {
            $scope._popMsg(messages["storeOrder.dtl.not.orderDate"]);
            return false;
          }
        }
        $scope.storeCloseCheck();
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      
      return false;
    }).then(function () {
    	
    });
  };
  
  // 매장마감여부 체크
  $scope.storeCloseCheck = function () {
    var params     = {};
    params.reqDate = wijmo.Globalize.format($scope.outDate.value, 'yyyyMMdd');
    params.slipFg  = $scope.slipFg;

    //가상로그인 session 설정
	    if(document.getElementsByName('sessionId')[0]){
	    	params['sid'] = document.getElementsByName('sessionId')[0].value;
	    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/storeOrder/storeOrderRegist/storeCloseCheck.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          if (response.data.data.orderCloseFg === "Y") {
            $scope._popMsg(messages["storeOrder.dtl.orderClose"]);
            
            return false;
          }
        }
        $scope.orderProcFgCheck(); // 주문진행구분 체크
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      
      return false;
    }).then(function () {
      // "complete" code here
    });
  };
  
  // 주문진행구분 체크 및 HD 비고 조회
  $scope.orderProcFgCheck = function () {
    var params     = {};
    params.reqDate = wijmo.Globalize.format($scope.outDate.value, 'yyyyMMdd');
    params.slipFg  = $scope.slipFg;
    params.storeCd = $scope.storeCd;

    //가상로그인 session 설정
	    if(document.getElementsByName('sessionId')[0]){
	    	params['sid'] = document.getElementsByName('sessionId')[0].value;
	    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/storeOrder/storeOrderRegist/orderProcFgCheck.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 진행구분이 주문등록이 아니면 상품추가/변경 불가
        if (!$.isEmptyObject(response.data.data)) {
          if (response.data.data.procFg != "00") {
            $scope._popMsg(messages["storeOrder.dtl.not.orderProcEnd"]);
            
            return false;
          }
          $scope.regHdRemark = response.data.data.remark;
        }
        // 물량오류 처리 함수
        $scope.saveVolmErr();        
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      
      return false;
    }).then(function () {
      // "complete" code here
    });
  };

  
  $scope.saveVolmErrDtlCallback = function () {
    $scope.wjVolmErrDtlLayer.hide(true);

    var volmErrScope = agrid.getScope('volmErrCtrl');
    volmErrScope.searchVolmErrList();

  };

  // 확정체크시 값 체크
  $scope.fnConfirmChk = function () {
    if ($("#volmErrConfirmFg").is(":checked")) {
      var showDate = "N";
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        if (item.errFg === null || item.errFg === "") {
          $scope._popMsg(messages["volmErr.dtl.require.selectErrFg"]); // 처리구분을 선택해 주세요.
          $("#volmErrConfirmFg").prop("checked", false);
          return false;
        }
        if (showDate === "N" && (item.errFg === "O2" || item.errFg === "O4" || item.errFg === "R2")) {
          showDate = "Y";
        }
      }

      if (showDate === "Y") {
        $("#divDtlOutDate").show();
      }
    }
    else {
      $("#divDtlOutDate").hide();
    }
  };
  
  // 물량오류 처리 함수
  $scope.saveVolmErr = function (){
	  var params           = [];
      var newSlipNoFg      = "N";
      var hqNewAdjustFg    = "N";
      var storeNewAdjustFg = "N";
      
      // 확정이 체크 되어있으면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
  	if ($("#volmErrConfirmFg").is(":checked") && $scope.flex.collectionView.itemsEdited.length <= 0) {
        var item = $scope.flex.collectionView.items[0];
        if (item === null) return false;

        $scope.flex.collectionView.editItem(item);
        item.status = "U";
        $scope.flex.collectionView.commitEdit();
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.errFg === null || item.errFg === "") {
          $scope._popMsg(messages["volmErr.dtl.require.selectErrFg"]); // 처리구분을 선택해 주세요.
          return false;
        }
        
        if (newSlipNoFg       === "N" && (item.errFg === "O2" || item.errFg === "O4" || item.errFg === "R2")) {
          newSlipNoFg = "Y";
        }
        if (hqNewAdjustFg     === "N" && (item.errFg === "O4" || item.errFg === "O5" || item.errFg === "R4")) {
          hqNewAdjustFg = "Y";
        }
        if (storeNewAdjustFg  === "N" && item.errFg === "R2") {
          storeNewAdjustFg = "Y";
        }

        if(wijmo.Globalize.format($scope.outDate.value, 'yyyyMMdd') < getToday()){ // 저장 날짜가 이전 날짜인경우.
      	  $scope._popMsg(messages["volmErr.dtl.prevDateOrder"]); // 당일보다 이전일자로 등록 하실 수 없습니다.
      	  return false;
        }else{
	        	item.status           = "U";
//	        	item.errFg;
	            item.slipNo           = $scope.slipNo;
	            item.slipFg           = $scope.slipFg;
	            item.storeCd          = $scope.storeCd;
	            item.hdRemark         = $scope.hdRemark;
	            item.outDate          = wijmo.Globalize.format($scope.outDate.value, 'yyyyMMdd');
	            item.confirmFg        = ($("#volmErrConfirmFg").is(":checked") ? $("#volmErrConfirmFg").val() : "");
	            item.procFg           = $scope.procFg;
	            item.newSlipNoFg      = newSlipNoFg;
	            item.hqNewAdjustFg    = hqNewAdjustFg;
	            item.storeNewAdjustFg = storeNewAdjustFg;
	      }
        params.push(item);
      }
      $scope._save("/iostock/volmErr/volmErr/volmErrDtl/save.sb", params, function () { // 확정 체크값 구분 못함(변경사항이 없습니다 alert 표시)
        $scope.saveVolmErrDtlCallback()
      });
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


	//[엑셀 다운로드] - START	------------------------------------------------------------------------------------------------------------------------------
	$scope.excelDownload = function(){
		if ($scope.flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
		$timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(
                $scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles   : 	true,
                    includeColumns      :   function (column) {
                                                return column.visible;
                                            }
                },
              //'물량오류관리_상세_' + getToday() + '.xlsx',
                '물량오류관리_상세_' + getCurDate('-') + '.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
	};
    //[엑셀 다운로드] - END	------------------------------------------------------------------------------------------------------------------------------


}]);