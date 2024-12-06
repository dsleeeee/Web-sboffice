/****************************************************************
 *
 * 파일명 : kioskKeyMapView.js
 * 설  명 : 키맵미리보기 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.04.28     권지현      1.0
 *
 * **************************************************************/

// 팝업 그리드 생성
app.controller('kioskKeyMapViewCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('kioskKeyMapViewCtrl', $scope, $http, false));
  var tuCls;
  var tuKey;
  var tuClsPage = 1;
  var tuKeyPage = 1;
  var soldOut = "<div style='position: absolute;'><img src='/resource/solbipos/css/img/soldOut.png' style='width: auto; height: 80%; opacity: 0.5;'/></div>";

  $scope._setComboData("tuClsTypeView", kioskTuClsTypeList);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 분류 초기화
  $scope.resetTh = function (){
    for(var th=0; th < 8; th++){
      $("#tuCls" + th).html('');
      $("#th" + th).css('background-color', '#d5d5d5');
    }
  }

  // 키맵 초기화
  $scope.resetTd = function (){
    for(var td=0; td < 12; td++){
      $("#tuKey" + td).html('');
    }
  }

  // 팝업 그리드 조회
  $scope.$on("kioskKeyMapViewCtrl", function(event, data) {
    $scope.getTuClsList();
    event.preventDefault();
  });

  // 키맵그룹 변경
  $scope.changeTuClsType = function (s){
    $scope.resetTh();
    $scope.resetTd();
    $scope.getTuClsList();
  }

  // 분류조회
  $scope.getTuClsList = function (){
    // 파라미터
    var params = {};
    params.tuClsType = $scope.tuClsTypeViewCombo.selectedValue;
    if(orgnFg==='STORE'){
      var scope = agrid.getScope('kioskKeyMapRegistCtrl');
      params.posNo = scope.posNoCombo.selectedValue;
    }

    if(document.getElementsByName('sessionId')[0]){
      params.sid = document.getElementsByName('sessionId')[0].value;
    }

    var url = "/base/prod/kioskKeyMap/kioskKeyMap/getKioskCategory.sb";

    $http({
      method: 'POST', //방식
      url: url, /* 통신할 URL */
      params: params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      if ($scope._httpStatusCheck(response, true)) {
        // this callback will be called asynchronously
        // when the response is available
        var list = response.data.data.list;
        if (list.length === undefined || list.length === 0) {
          return false;
        } else {
          tuCls = list;
          for (var i = 0; i < (8 > list.length ? list.length : 8); i++) {
            $("#tuCls" + i).html("<span id='tuClsCd"+i+"' style='display: none'>"+list[i].tuClsCd+"</span>" + list[i].tuClsNm);
          }
          $("#th0").css('background-color', '#FDD69D');
          $scope.getTuKeyList(list[0].tuClsCd);
        }
      }
    }, function errorCallback(response) {
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // 'complete' code here
      if (typeof callback === 'function') {
        setTimeout(function () {
          callback();
        }, 10);
      }
    });
  };

  // 키맵조회
  $scope.getTuKeyList = function (tuClsCd){

    // 파라미터
    var params = {};
    params.tuClsType = $scope.tuClsTypeViewCombo.selectedValue;
    params.tuClsCd = tuClsCd;
    if(orgnFg==='STORE'){
      var scope = agrid.getScope('kioskKeyMapRegistCtrl');
      params.posNo = scope.posNoCombo.selectedValue;
    }

    if(document.getElementsByName('sessionId')[0]){
      params.sid = document.getElementsByName('sessionId')[0].value;
    }

    var url = "/base/prod/kioskKeyMap/kioskKeyMap/getTuKeyList.sb";
    $http({
      method: 'POST', //방식
      url: url, /* 통신할 URL */
      params: params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      if ($scope._httpStatusCheck(response, true)) {
        // this callback will be called asynchronously
        // when the response is available
        var list = response.data.data.list;
        if (list.length === undefined || list.length === 0) {
          return false;
        } else {
          tuKey = list;
          for (var i = 0; i < (12 > list.length ? list.length : 12); i++) {
            var lineCss = '';
            var momsKioskEdge = "";
            var lineDiv = '';
            if(list[i].groupProdCdFg !== null && list[i].groupProdCdFg !== ""){
              lineCss = "<span class='blue'>["+list[i].prodCd+"]"+list[i].sideProdYn +"</span><br/>";
              $("#tuKey" + i).attr('style', 'cursor:pointer');
            }else{
              list[i].groupProdCdFg = '';
              lineCss = "<span>["+list[i].prodCd+"]"+list[i].sideProdYn +"</span>";
              $("#tuKey" + i).attr('style', '');
            }

            if(list[i].momsKioskEdge !== null) {
              if (list[i].momsKioskEdge !== "0") {
                momsKioskEdge = "<span class='fl red' style='font-size:8px; position:relative; z-index:10;'>" + list[i].momsKioskEdge + "</span></br>";
                lineDiv = "<div style='position:relative; top:-10px;'>";
              } else {
                momsKioskEdge = '';
                lineDiv = "<div>";
              }
            }else{
              momsKioskEdge = '';
              lineDiv = "<div>";
            }

            if(list[i].soldOutYn === 'Y' && orgnFg === "STORE"){
              $("#tuKey" + i).html(momsKioskEdge + soldOut + lineDiv + "<img src='" + list[i].imgUrl + "/" + list[i].imgFileNm + "' onerror='this.src=\"/resource/solbipos/css/img/NoImage.png\"' style='width: auto; height: 65%;'><br/>"+ lineCss + list[i].groupProdCdFg+"<br/>"+list[i].prodNm+"<br/>"+list[i].saleUprc + "</div>");
            } else {
              $("#tuKey" + i).html(momsKioskEdge + lineDiv + "<img src='" + list[i].imgUrl + "/" + list[i].imgFileNm + "' onerror='this.src=\"/resource/solbipos/css/img/NoImage.png\"' style='width: auto; height: 65%;'><br/>"+ lineCss + list[i].groupProdCdFg+"<br/>"+list[i].prodNm+"<br/>"+list[i].saleUprc + "</div>");
            }
          }
        }
      }
    }, function errorCallback(response) {
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // 'complete' code here
      if (typeof callback === 'function') {
        setTimeout(function () {
          callback();
        }, 10);
      }
    });
  };


  $scope.tuKeyClick = function (i){

    var index = parseInt(i)+(12*(tuKeyPage-1));

    if(tuKey[index] !== null && tuKey[index] !== undefined) {
      if (tuKey[index].groupProdCd !== null && tuKey[index].groupProdCd != '') {
        alert("세트: [" + tuKey[index].groupProdCd + "]" + tuKey[index].groupProdNm);
      }
    }
  }

  // 분류클릭
  $scope.thClick = function (index){
    $scope.resetTd();

    tuKeyPage = 1;

    for (var i = 0; i < 8; i++) {
      $("#th"+i).css('background-color', '#d5d5d5');
    }
    $("#th"+index).css('background-color', '#FDD69D');
    $scope.getTuKeyList($("#tuClsCd" + index).text());
  }

  // 분류 좌
  $scope.thPre = function (){
    if(tuClsPage > 1){
      $scope.resetTh();

      tuClsPage--;
      var index
      if(tuClsPage === 1){
        index=0;
      } else {
        index=8*(tuClsPage-1);
      }

      for (var i = 0; i < 8; i++) {
        $("#tuCls" + i).html("<span id='tuClsCd"+i+"' style='display: none'>"+tuCls[index].tuClsCd+"</span>" + tuCls[index].tuClsNm);
        index ++;
      }
    }
  }

  // 분류 우
  $scope.thNext = function (){
    if(tuCls.length > 8*tuClsPage){
      $scope.resetTh();

      var index=8*tuClsPage;
      tuClsPage++;

      for (var i = 0; index < (8*tuClsPage > tuCls.length ? tuCls.length : 8*tuClsPage); i++) {
        $("#tuCls" + i).html("<span id='tuClsCd"+i+"' style='display: none'>"+tuCls[index].tuClsCd+"</span>" + tuCls[index].tuClsNm);
        index ++;
      }
    }
  }

  // 키맵 좌
  $scope.tdPre = function (){
    if(tuKeyPage > 1){
      $scope.resetTd();

      tuKeyPage--;
      var index
      if(tuKeyPage === 1){
        index=0;
      } else {
        index=12*(tuKeyPage-1);
      }

      for (var i = 0; i < 12; i++) {
        var lineCss = '';
        var momsKioskEdge = "";
        var lineDiv = '';
        if(tuKey[index].groupProdCdFg !== null && tuKey[index].groupProdCdFg !== ""){
          lineCss = "<span class='blue'>["+tuKey[index].prodCd+"]"+tuKey[index].sideProdYn +"</span></br>";
          $("#tuKey" + i).attr('style', 'cursor:pointer');
        }else{
          tuKey[index].groupProdCdFg = '';
          lineCss = "<span>["+tuKey[index].prodCd+"]"+tuKey[index].sideProdYn +"</span>";
          $("#tuKey" + i).attr('style', '');
        }
        if(tuKey[index].momsKioskEdge !== null) {
          if (tuKey[index].momsKioskEdge !== "0") {
            momsKioskEdge = "<span class='fl red' style='font-size:8px; position:relative; z-index:10;'>" + tuKey[index].momsKioskEdge + "</span></br>";
            lineDiv = "<div style='position:relative; top:-10px;'>";
          } else {
            momsKioskEdge = '';
            lineDiv = "<div>";
          }
        }else{
          momsKioskEdge = '';
          lineDiv = "<div>";
        }
        if(tuKey[index].soldOutYn === 'Y' && orgnFg === "STORE"){
          $("#tuKey" + i).html(momsKioskEdge + soldOut + lineDiv + "<img src='" + tuKey[index].imgUrl + "/" + tuKey[index].imgFileNm + "' onerror='this.src=\"/resource/solbipos/css/img/NoImage.png\"' style='width: auto; height: 65%;'><br/>"+ lineCss + tuKey[index].groupProdCdFg+"<br/>"+tuKey[index].prodNm+"<br/>"+tuKey[index].saleUprc + "</div>");
        } else {
          $("#tuKey" + i).html(momsKioskEdge + lineDiv + "<img src='" + tuKey[index].imgUrl + "/" + tuKey[index].imgFileNm + "' onerror='this.src=\"/resource/solbipos/css/img/NoImage.png\"' style='width: auto; height: 65%;'><br/>"+ lineCss + tuKey[index].groupProdCdFg+"<br/>"+tuKey[index].prodNm+"<br/>"+tuKey[index].saleUprc + "</div>");
        }
        index ++;
      }
    }
  }

  // 키맵 우
  $scope.tdNext = function (){
    if(tuKey.length > 12*tuKeyPage){
      $scope.resetTd();

      var index=12*tuKeyPage;
      tuKeyPage++;

      for (var i = 0; index < (12*tuKeyPage > tuKey.length ? tuKey.length : 12*tuKeyPage); i++) {
        var lineCss = '';
        var momsKioskEdge = "";
        var lineDiv = '';
        if(tuKey[index].groupProdCdFg !== null && tuKey[index].groupProdCdFg !== ""){
          lineCss = "<span class='blue'>["+tuKey[index].prodCd+"]"+tuKey[index].sideProdYn +"</span></br>";
          $("#tuKey" + i).attr('style', 'cursor:pointer');
        }else{
          tuKey[index].groupProdCdFg = '';
          lineCss = "<span>["+tuKey[index].prodCd+"]"+tuKey[index].sideProdYn +"</span>";
          $("#tuKey" + i).attr('style', '');
        }
        if(tuKey[index].momsKioskEdge !== null) {
          if (tuKey[index].momsKioskEdge !== "0") {
            momsKioskEdge = "<span class='fl red' style='font-size:8px; position:relative; z-index:10;'>" + tuKey[index].momsKioskEdge + "</span></br>";
            lineDiv = "<div style='position:relative; top:-10px;'>";
          } else {
            momsKioskEdge = '';
            lineDiv = "<div>";
          }
        }else{
          momsKioskEdge = '';
          lineDiv = "<div>";
        }
        if(tuKey[index].soldOutYn === 'Y' && orgnFg === "STORE"){
          $("#tuKey" + i).html(momsKioskEdge + soldOut + lineDiv + "<img src='" + tuKey[index].imgUrl + "/" + tuKey[index].imgFileNm + "' onerror='this.src=\"/resource/solbipos/css/img/NoImage.png\"' style='width: auto; height: 65%;'><br/>"+ lineCss + tuKey[index].groupProdCdFg+"<br/>"+tuKey[index].prodNm+"<br/>"+tuKey[index].saleUprc + "</div>");
        } else {
          $("#tuKey" + i).html(momsKioskEdge + lineDiv + "<img src='" + tuKey[index].imgUrl + "/" + tuKey[index].imgFileNm + "' onerror='this.src=\"/resource/solbipos/css/img/NoImage.png\"' style='width: auto; height: 65%;'><br/>"+ lineCss + tuKey[index].groupProdCdFg+"<br/>"+tuKey[index].prodNm+"<br/>"+tuKey[index].saleUprc + "</div>");
        }
        index ++;
      }
    }
  }

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 팝업 핸들러 추가
    $scope.kioskKeyMapViewLayer.hidden.addHandler(function (s) {
      setTimeout(function () {
        $scope.resetTh();
        $scope.resetTd();
        tuClsPage = 1;
        tuKeyPage = 1;
      }, 50)
    });
  });
}]);