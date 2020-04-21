/** 실사관리 상세 그리드 controller */
app.controller('acinsDtlCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('acinsDtlCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 실사수량 수정시 금액,VAT,합계 계산하여 보여준다.
        if (col.binding === "acinsQty") {
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
  };


  $scope.calcAmt = function (item) {
    var costUprc    = parseInt(item.costUprc);
    var acinsQty    = parseInt(nvl(item.acinsQty, 0));
    var cmptCurrQty = parseInt(nvl(item.cmptCurrQty, 0));
    var adjQty      = parseInt(acinsQty) - parseInt(cmptCurrQty);
    var acinsAmt    = parseInt(acinsQty) * parseInt(costUprc);
    var adjAmt      = parseInt(adjQty) * parseInt(costUprc);

    item.adjQty   = adjQty;   // 조정수량
    item.acinsAmt = acinsAmt; // 실사금액
    item.adjAmt   = adjAmt; // 조정금액
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("acinsDtlCtrl", function (event, data) {
    // 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    if (!$.isEmptyObject(data)) {
      $scope._setPagingInfo('curr', 1); // 페이징처리시 페이지번호 초기화

      $scope.acinsDate = data.acinsDate;
      $scope.seqNo     = data.seqNo;

      $scope.procFgCheck(); // 실사 진행구분 체크
    }
    else { // 페이징처리에서 broadcast 호출시
      $scope.searchAcinsDtlList();
    }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 실사 진행구분 체크 및 실사제목 조회
  $scope.procFgCheck = function () {
    var params       = {};
    params.acinsDate = $scope.acinsDate;
    params.seqNo     = $scope.seqNo;
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }
    
    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/stock/acins/acins/acinsRegist/procFgCheck.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 진행구분이 실사등록이 아니면 상품추가/변경 불가
        if (!$.isEmptyObject(response.data.data)) {
          // 실사 등록 상태이면 버튼 show
          if (response.data.data.procFg != "" && response.data.data.procFg == "0") {
            $scope.btnDtlAddProd = true;
            $scope.btnDtlSave    = true;
            $scope.btnDtlConfirm = true;
          }
          else {
            $scope.btnDtlAddProd = false;
            $scope.btnDtlSave    = false;
            $scope.btnDtlConfirm = false;
          }
          $scope.acinsTitle = response.data.data.acinsTitle;
        }
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
      $scope.wjAcinsDtlLayer.show(true);
      $("#registSubTitle").html(messages["acins.reg.acinsDate"] + ' : ' + getFormatDate($scope.acinsDate, '-'));
      $scope.searchAcinsDtlList();
    });
  };


  // 실사상품 리스트 조회
  $scope.searchAcinsDtlList = function () {
    // 파라미터
    var params       = {};
    params.acinsDate = $scope.acinsDate;
    params.seqNo     = $scope.seqNo;
    params.listScale = 50;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/acins/acins/acinsDtl/list.sb", params);
  };


  // 실사 상품 저장
  $scope.saveAcinsDtl = function (confirmFg) {
    if (nvl($scope.acinsTitle, '') === '') {
      var msg = messages["acins.reg.acinsTitle"] + messages["cmm.require.text"];
      $scope._popMsg(msg);
      return false;
    }

    // 확정처리가 체크 되어있으면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
    if (confirmFg === 'Y' && $scope.flex.collectionView.itemsEdited.length <= 0) {
      var item = $scope.flex.collectionView.items[0];
      if (item === null) return false;

      $scope.flex.collectionView.editItem(item);
      item.status = "U";
      $scope.flex.collectionView.commitEdit();
    }

    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      // 체크박스가 체크되어 있는 상품은 삭제한다.
      if (item.gChk === true && nvl(item.acinsQty, '') !== '') {
        item.status = "D";
      }
      else {
        item.status = "U";
      }
      item.acinsDate  = $scope.acinsDate;
      item.seqNo      = $scope.seqNo;
      item.acinsTitle = $scope.acinsTitle;
      item.storageCd  = "999";	//001	->	999
      item.hqBrandCd  = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      item.confirmFg  = confirmFg;

      params.push(item);
    }

    $scope._save("/stock/acins/acins/acinsDtl/save.sb", params, function () {
      $scope.saveRegistCallback(confirmFg)
    });
  };


  // 저장 후 콜백 서치 함수
  $scope.saveRegistCallback = function (confirmFg) {
    $scope.searchAcinsDtlList();

    var acinsScope = agrid.getScope('acinsCtrl');
    acinsScope.searchAcinsList();

    if (confirmFg === 'Y') {
      $scope.wjAcinsDtlLayer.hide(true);
    }
  };


  // 확정
  $scope.confirm = function () {
    var msg = messages["acins.dtl.confirmMsg"]; // 확정하시겠습니까?
    var checkLenth = 0;
    s_alert.popConf(msg, function () {
    	for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
  	      var item = $scope.flex.collectionView.items[i];
  	      if(item.gChk == true){
  	    	  alert("확정하시려면 상품목록의 체크박스를 해제해주세요.");
  	    	  return false;
  	      }else{
  	    	  checkLenth++;
  	      }
    	}
    	
	    //체크해제된 ROW수가 총 ROW와 같으면 확정
	    if($scope.flex.collectionView.items.length == checkLenth){
	    	$scope.saveAcinsDtl('Y');
	    	return false;
	    }    
    });
  };


  // 상품추가
  $scope.addProd = function () {
    var params        = {};
    params.acinsDate  = $scope.acinsDate;
    params.seqNo      = $scope.seqNo;
    params.callParent = 'acinsDtl';
    $scope._broadcast('acinsRegistCtrl', params);
  };


}]);
