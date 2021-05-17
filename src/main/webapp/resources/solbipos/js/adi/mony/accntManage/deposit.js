/****************************************************************
 *
 * 파일명 : deposit.js
 * 설  명 : 계정관리 설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.12     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/***************************************************************************
 *  입금계정 그리드 생성
 ***************************************************************************/
app.controller('depositViewCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('depositViewCtrl', $scope, $http, true));

  // comboBox 초기화
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');

    $scope.searchDepositAccntList();

  };

  $scope.$on("depositViewCtrl", function(event, data) {
    event.preventDefault();
  });

  // 입금계정 조회
  $scope.searchDepositAccntList = function(){
    // 파라미터
    var params = {};
    params.accntFg="1"; // 입금

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "deposit/getDepositAccntList.sb", params, function() {
      // 입금계정 조회 완료 후, 출금계정 조회
      var withdrawScope = agrid.getScope('withdrawCtrl');
      withdrawScope.searchWithdrawAccntList();

      $("#depositBtnArea").show();

    }, false);
  };

  // 입금계정 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.accntCd="자동채번";
    params.accntFg="1"; // 입금
    params.useYn = "Y";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 입금계정 그리드 삭제
  $scope.delete = function() {

    // 본사에서 등록한 계정을 삭제하는지 체크
    if (orgnFg === "STORE") {
      if (hqOfficeCd !== "00000") {

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
          var item = $scope.flex.collectionView.items[i];

          if (item.gChk) {
            if (item.accntCd !== "자동채번"){
              if (801 > Number(item.accntCd)) {
                $scope._popMsg(messages["accntManage.chk.del"]); // 본사에서 등록한 계정은 삭제할 수 없습니다.
                return false;
              }
            }
          }
        }
      }
    }

    // 삭제
    var chkCount = 0;
    var params = [];
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
      var item = $scope.flex.collectionView.items[i];

      if(item.gChk){

        chkCount++;

        if (item.accntCd === "자동채번"){
          $scope.flex.collectionView.removeAt(i);
        }else {
          var obj = {};
          obj.status = "D";
          obj.accntCd = item.accntCd;
          obj.accntFg = item.accntFg;
          params.push(obj);
        }
      }
    }

    if(chkCount === 0){
      $scope._popMsg("삭제할 " + messages["accntManage.deposit"] + "의 체크박스" + messages["accntManage.chk.item"]); //  삭제할 입금계정의 체크박스을(를) 선택하세요.
      return false;
    }

    if(params.length > 0){
      $scope._popConfirm(messages["accntManage.deposit"] + messages["accntManage.confirm.del"], function() { //  입금계정을 삭제하시겠습니까?
        $scope._save(baseUrl + "deposit/saveDepositAccntList.sb", params,
            function(){
              $scope.searchDepositAccntList();
            }
        );
      });
    }
  };

  // 입금계정 그리드 저장
  $scope.save = function() {

    $scope.flex.collectionView.commitEdit();

    // 생성, 수정 Validation Check
    var chkCount = 0;
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      var item = $scope.flex.collectionView.items[i];

      if(item.gChk) {

        chkCount++;

        if (item.accntNm === null || item.accntNm === '' || item.accntNm === undefined) {
          $scope._popMsg(messages["accntManage.deposit"] + messages["accntManage.chk.accntNm"]); // 입금계정의 계정명을 반드시 입력하세요.
          return false;
        }

        if (orgnFg === "STORE") {
          if (hqOfficeCd !== "00000") {
            if (item.accntCd !== "자동채번") {
              if (801 > Number(item.accntCd)) {
                $scope._popMsg(messages["accntManage.chk.mod"]); // 본사에서 등록한 계정은 수정할 수 없습니다.
                return false;
              }
            }
          }
        }

      }
    }

    if(chkCount === 0){
      $scope._popMsg("저장할 " + messages["accntManage.deposit"] + "의 체크박스" + messages["accntManage.chk.item"]); //  저장할 입금계정의 체크박스을(를) 선택하세요.
      return false;
    }

    // 파라미터 설정
    var params = [];

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      if($scope.flex.collectionView.itemsEdited[i].gChk) {
        $scope.flex.collectionView.itemsEdited[i].status = "U";
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      if($scope.flex.collectionView.itemsAdded[i].gChk) {
        $scope.flex.collectionView.itemsAdded[i].status = "I";
        params.push($scope.flex.collectionView.itemsAdded[i]);
      }
    }

    $scope._popConfirm(messages["accntManage.deposit"] + messages["accntManage.confirm.save"], function() { //  입금계정을 저장하시겠습니까?
      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save(baseUrl + "deposit/saveDepositAccntList.sb", params,
          function () {
            $scope.searchDepositAccntList();
          }
      );
    });
  };

  // 입금계정 일괄저장
  $scope.batch = function() {

    var msg = "";

    if($scope.flex.collectionView.items.length == 0){
      msg = messages["accntManage.emptyBatchList.msg"]; //적용할 내역이 없습니다.
      s_alert.pop(msg);
      return;
    }

    msg = messages["accntManage.chkDeposit.msg"]; // 입금계정을 전 매장에 적용하시겠습니까?
    s_alert.popConf(msg, function() {

        // 파라미터
        var params = {};
        params.accntFg="1"; // 입금

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save(baseUrl + "deposit/batchDepositAccntList.sb", params,
            function (result) {
              $scope.searchDepositAccntList();
            }
        );

    });
  };

}]);


/**************************************************************************
 *  출금계정 그리드 생성
 **************************************************************************/
app.controller('withdrawCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('withdrawCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');
  };

  $scope.$on("withdrawCtrl", function(event, data) {
    event.preventDefault();
  });

  // 출금계정 조회
  $scope.searchWithdrawAccntList = function(){
    var params = {};
    params.accntFg="2"; // 출금

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "deposit/getDepositAccntList.sb", params, function() {

      $("#withdrawBtnArea").show();

    }, false);
  };

  // 출금계정 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.accntCd="자동채번";
    params.accntFg="2"; // 출금
    params.useYn = "Y";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 출금계정 그리드 삭제
  $scope.delete = function() {

    // 본사에서 등록한 계정을 삭제하는지 체크
    if (orgnFg === "STORE") {
      if (hqOfficeCd !== "00000") {

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
          var item = $scope.flex.collectionView.items[i];

          if (item.gChk) {
            if (item.accntCd !== "자동채번"){
              if (801 > Number(item.accntCd)) {
                $scope._popMsg(messages["accntManage.chk.del"]); // 본사에서 등록한 계정은 삭제할 수 없습니다.
                return false;
              }
            }
          }
        }
      }
    }

    // 삭제
    var chkCount = 0;
    var params = [];
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
      var item = $scope.flex.collectionView.items[i];

      if(item.gChk){

        chkCount++;

        if (item.accntCd === "자동채번"){
          $scope.flex.collectionView.removeAt(i);
        }else {
          var obj = {};
          obj.status = "D";
          obj.accntCd = item.accntCd;
          obj.accntFg = item.accntFg;
          params.push(obj);
        }
      }
    }

    if(chkCount === 0){
      $scope._popMsg("삭제할 " + messages["accntManage.withdraw"] + "의 체크박스" + messages["accntManage.chk.item"]); //  삭제할 출금계정의 체크박스을(를) 선택하세요.
      return false;
    }

    if(params.length > 0){
      $scope._popConfirm(messages["accntManage.withdraw"] + messages["accntManage.confirm.del"], function() { //  출금계정을 삭제하시겠습니까?
        $scope._save(baseUrl + "deposit/saveDepositAccntList.sb", params,
            function () {
              $scope.searchWithdrawAccntList();
            }
        );
      });
    }
  };

  // 출금계정 그리드 저장
  $scope.save = function() {

    $scope.flex.collectionView.commitEdit();

    // 생성, 수정 Validation Check
    var chkCount = 0;
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      var item = $scope.flex.collectionView.items[i];

      if(item.gChk) {

        chkCount++;

        if (item.accntNm === null || item.accntNm === '' || item.accntNm === undefined) {
          $scope._popMsg(messages["accntManage.withdraw"] + messages["accntManage.chk.accntNm"]); // 출금계정의 계정명을 반드시 입력하세요.
          return false;
        }

        if (orgnFg === "STORE") {
          if (hqOfficeCd !== "00000") {
            if (item.accntCd !== "자동채번") {
              if (801 > Number(item.accntCd)) {
                $scope._popMsg(messages["accntManage.chk.mod"]); // 본사에서 등록한 계정은 수정할 수 없습니다.
                return false;
              }
            }
          }
        }

      }
    }

    if(chkCount === 0){
      $scope._popMsg("저장할 " + messages["accntManage.withdraw"] + "의 체크박스" + messages["accntManage.chk.item"]); //  저장할 출금계정의 체크박스을(를) 선택하세요.
      return false;
    }

    // 파라미터 설정
    var params = [];

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      if($scope.flex.collectionView.itemsEdited[i].gChk) {
        $scope.flex.collectionView.itemsEdited[i].status = "U";
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      if($scope.flex.collectionView.itemsAdded[i].gChk) {
        $scope.flex.collectionView.itemsAdded[i].status = "I";
        params.push($scope.flex.collectionView.itemsAdded[i]);
      }
    }

    $scope._popConfirm(messages["accntManage.withdraw"] + messages["accntManage.confirm.save"], function() { //  출금계정을 저장하시겠습니까?
      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save(baseUrl + "deposit/saveDepositAccntList.sb", params,
          function () {
            $scope.searchWithdrawAccntList();
          }
      );
    });
  };

  // 출금계정 일괄저장
  $scope.batch = function() {

    var msg = "";

    if($scope.flex.collectionView.items.length == 0){
      msg = messages["accntManage.emptyBatchList.msg"]; //적용할 내역이 없습니다.
      s_alert.pop(msg);
      return;
    }

    msg = messages["accntManage.chkWidthdraw.msg"]; // 출금계정을 전 매장에 적용하시겠습니까?
    s_alert.popConf(msg, function() {

        // 파라미터
        var params = {};
        params.accntFg="2"; // 출금

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save(baseUrl + "deposit/batchDepositAccntList.sb", params,
            function(){
              $scope.searchWithdrawAccntList();
            }
        );

    });
  };

}]);


