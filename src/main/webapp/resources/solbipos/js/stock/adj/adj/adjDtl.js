/** 조정관리 상세 그리드 controller */
app.controller('adjDtlCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('adjDtlCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 조정수량 수정시 금액,VAT,합계 계산하여 보여준다.
        if (col.binding === "adjQty") {
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
    var costUprc = parseInt(item.costUprc);
    var adjQty   = parseInt(nvl(item.adjQty, 0));
    var adjAmt   = parseInt(adjQty) * parseInt(costUprc);

    item.adjQty = adjQty;   // 조정수량
    item.adjAmt = adjAmt; // 조정금액
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("adjDtlCtrl", function (event, data) {
    // 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    if (!$.isEmptyObject(data)) {
      $scope._setPagingInfo('curr', 1); // 페이징처리시 페이지번호 초기화

      $scope.adjDate = data.adjDate;
      $scope.seqNo   = data.seqNo;

      $scope.procFgCheck(); // 조정 진행구분 체크
    }
    else { // 페이징처리에서 broadcast 호출시
      $scope.searchAdjDtlList();
    }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 조정 진행구분 체크 및 조정제목 조회
  $scope.procFgCheck = function () {
    var params     = {};
    params.adjDate = $scope.adjDate;
    params.seqNo   = $scope.seqNo;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/stock/adj/adj/adjRegist/procFgCheck.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 진행구분이 조정등록이 아니면 상품추가/변경 불가
        if (!$.isEmptyObject(response.data.data)) {
          // 조정 등록 상태이면 버튼 show
          if (response.data.data.procFg !== "" && response.data.data.procFg === "0") {
            $scope.btnDtlAddProd = true;
            $scope.btnDtlSave    = true;
            $scope.btnDtlConfirm = true;
          }
          else {
            $scope.btnDtlAddProd = false;
            $scope.btnDtlSave    = false;
            $scope.btnDtlConfirm = false;
          }
          $scope.adjTitle = response.data.data.adjTitle;
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
      $scope.wjAdjDtlLayer.show(true);
      $("#registSubTitle").html(messages["adj.reg.adjDate"] + ' : ' + getFormatDate($scope.adjDate, '-'));
      $scope.searchAdjDtlList();
    });
  };


  // 조정상품 리스트 조회
  $scope.searchAdjDtlList = function () {
    // 파라미터
    var params       = {};
    params.adjDate   = $scope.adjDate;
    params.seqNo     = $scope.seqNo;
    params.listScale = 50;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/adj/adj/adjDtl/list.sb", params);
  };


  // 조정 상품 저장
  $scope.saveAdjDtl = function (confirmFg) {
    if (nvl($scope.adjTitle, '') === '') {
      var msg = messages["adj.reg.adjTitle"] + messages["cmm.require.text"];
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
      if (item.gChk === true && nvl(item.adjQty, '') !== '') {
        item.status = "D";
      }
      else {
        item.status = "U";
      }
      item.adjDate   = $scope.adjDate;
      item.seqNo     = $scope.seqNo;
      item.adjTitle  = $scope.adjTitle;
      item.storageCd = "001";
      item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      item.confirmFg = confirmFg;

      params.push(item);
    }

    $scope._save("/stock/adj/adj/adjDtl/save.sb", params, function () {
      $scope.saveRegistCallback(confirmFg)
    });
  };


  // 저장 후 콜백 서치 함수
  $scope.saveRegistCallback = function (confirmFg) {
    $scope.searchAdjDtlList();

    var adjScope = agrid.getScope('adjCtrl');
    adjScope.searchAdjList();

    if (confirmFg === 'Y') {
      $scope.wjAdjDtlLayer.hide(true);
    }
  };


  // 확정
  $scope.confirm = function () {
    var msg = messages["adj.dtl.confirmMsg"]; // 확정하시겠습니까?
    s_alert.popConf(msg, function () {
      $scope.saveAdjDtl('Y');
      return false;
    });
  };


  // 상품추가
  $scope.addProd = function () {
    var params        = {};
    params.adjDate    = $scope.adjDate;
    params.seqNo      = $scope.seqNo;
    params.callParent = 'adjDtl';
    $scope._broadcast('adjRegistCtrl', params);
  };


}]);
