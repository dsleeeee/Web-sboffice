/** 분배지시서(기사) 상품-매장 controller */
app.controller('dstbDlvrProdStoreReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dstbDlvrProdStoreReportCtrl', $scope, $http, true));

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dstbDlvrProdStoreReportCtrl", function (event, data) {
    $scope.strSlipNo = data.strSlipNo;
    $scope.strDlvrCd = data.strDlvrCd;

    $scope.wjDstbDlvrProdStoreReportLayer.show(true);

    // dstbDlvrProdStoreReport html 내용 초기화
    $("#dstbDlvrProdStoreReport").html('');

    $scope.searchDlvrProdList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 배송기사 상품 리스트 조회
  $scope.searchDlvrProdList = function () {
    // 로딩바 show
    $scope.$broadcast('loadingPopupActive');

    var params    = {};
    params.slipNo = $scope.strSlipNo;
    params.dlvrCd = $scope.strDlvrCd;
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }	
    
    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/dstmn/dstbDlvrReport/list.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var data    = response.data.data.list;
          var loopCnt = data.length;

          var dlvrProdList = {};
          var prodList     = [];
          var dlvrCd       = '';
          var bDlvrCd      = '';
          for (var i = 0; i < loopCnt; i++) {
            dlvrCd = data[i].dlvrCd;

            // 이전 배송기사와 현재 배송기사가 다르면 이전 배송기사로 상품리스트를 담는다.
            if (bDlvrCd !== '' && dlvrCd !== bDlvrCd) {
              dlvrProdList[bDlvrCd] = prodList;
              prodList              = [];
            }

            bDlvrCd = dlvrCd;
            prodList.push(data[i]);
          }

          // for 문 돌면서 이전 배송기사와 현재 배송기사가 다르지 않게 끝나 담지 못한 데이터 담기.
          if (nvl(dlvrProdList[dlvrCd], '') === '') {
            dlvrProdList[dlvrCd] = prodList;
          }

          $scope.dlvrProdList = dlvrProdList;
          $scope.reportRender();
        }
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
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


  // report html 생성
  $scope.reportRender = function () {
    var arrDlvr = $scope.strDlvrCd.split(',');
    var dlvrCnt = arrDlvr.length;

    var dstbDlvrProdStoreReportHtml = ''; // 화면에 뿌려질 최종 html 을 담기위한 변수
    var nextPageHtml                = '<p class="nextPage mt5"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
    // 현재 월, 일, 시간 세팅
    var month = getCurDate('-').split('-')[1];
    var day   = getCurDate('-').split('-')[2];
    var time  = getCurTime(':');

    for (var dCnt = 0; dCnt < dlvrCnt; dCnt++) { // 배송기사수만큼 loop
      dstbDlvrProdStoreReportHtml += (dCnt > 0 ? nextPageHtml : ''); // 배송기사가 변경되면 다음 페이지로 넘기기 위한 html 을 붙여준다.

      var dlvrCd   = arrDlvr[dCnt];
      var dataList = $scope.dlvrProdList[dlvrCd];
      var loopCnt  = dataList.length;

      var arrProdList = [];
      var arrList     = [];
      var subSum      = {};
      var pageRowCnt  = 35; // 한 페이지에 표시할 row수 변수
      var rowCnt      = 0;
      var prodCd      = '';
      var bProdCd     = '';
      var prodCnt     = 1;
      var subUnitQty  = 0;
      var subEtcQty   = 0;
      // 한 페이지에 표시할 row 수에 따라 배열에 담기 위해 for 문 돔. 해당 배열만큼 페이지수 생성
      for (var i = 0; i < loopCnt; i++) {
        if (rowCnt >= pageRowCnt) {
          rowCnt = 0;
          arrProdList.push(arrList);
          arrList = [];
        }

        var item = dataList[i];
        prodCd   = item.prodCd;
        // 이전 상품과 현재 상품이 다른 경우 소계추가
        if (bProdCd !== '' && prodCd !== bProdCd) {
          subSum            = {};
          subSum.dlvrNm     = item.dlvrNm;
          subSum.subUnitQty = subUnitQty;
          subSum.subEtcQty  = subEtcQty;
          arrList.push(subSum);
          subUnitQty = 0;
          subEtcQty  = 0;
          rowCnt++;
          prodCnt++;
        }

        subUnitQty = parseInt(subUnitQty) + parseInt(item.outUnitQty); // 상품별 소계 단위수량
        subEtcQty  = parseInt(subEtcQty) + parseInt(item.outEtcQty);   // 상품별 소계 낱개수량
        arrList.push(item);
        bProdCd = prodCd;
        rowCnt++;
      }
      subSum            = {};
      subSum.subUnitQty = subUnitQty;
      subSum.subEtcQty  = subEtcQty;
      arrList.push(subSum);
      arrProdList.push(arrList);

      var totPageCnt         = Math.ceil((parseInt(loopCnt) + parseInt(prodCnt)) / parseInt(pageRowCnt)); // 전체 페이지 수
      var rowNo              = 0; // row 의 No. 를 위한 변수
      var titleHtml          = ''; // 분배지시서 최상단 html
      var prodListHtml       = ''; // 상품리스트 html
      var prodListHeaderHtml = '<table class="w100 mt5">' // 상품리스트 header html
        + '<colgroup>'
        + '<col style="width:5%;">'
        + '<col style="width:37%;">'
        + '<col style="width:8%;">'
        + '<col style="width:30%;">'
        + '<col style="width:5%;">'
        + '<col style="width:5%;">'
        + '<col style="width:10%;">'
        + '</colgroup>'
        + '<tr class="tc">'
        + '<th>No.</th>'
        + '<th>상품</th>'
        + '<th>단위</th>'
        + '<th>매장</th>'
        + '<th colspan="2">수량</th>'
        + '<th>비고</th>'
        + '</tr>';
 
      // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 for문을 돈다
      for (var i = 0; i < arrProdList.length; i++) {
        var prodList = arrProdList[i];
        bProdCd      = '';
        // 상품수만큼 for문 돈다
        for (var j = 0; j < prodList.length; j++) {
          var item = prodList[j];
          if (nvl(item.subUnitQty, '') === '') {
            var storeNm    = '[' + item.storeCd + '] ' + item.storeNm;
            var prodNm     = '';
            var poUnitFgNm = '';
            prodCd         = item.prodCd;

            // 상품이 바뀌는 경우 상품명, 단위 변경
            if (prodCd !== bProdCd) {
              prodNm     = '[' + item.prodCd + '] ' + item.prodNm;
              poUnitFgNm = item.poUnitFgNm;
            }
            bProdCd = prodCd;

            prodListHtml += '<tr class="h25">'
              + '<td class="tc">' + (++rowNo) + '</td>'
              + '<td class="tl"><input type="text" value="' + prodNm + '" class="w100" readonly></td>'
              + '<td class="tc">' + poUnitFgNm + '</td>'
              + '<td class="tl"><input type="text" value="' + storeNm + '" class="w100" readonly></td>'
              + '<td class="tr">' + (item.outUnitQty === 0 ? '' : addComma(item.outUnitQty)) + '</td>'
              + '<td class="tr">' + (item.outEtcQty === 0 ? '' : addComma(item.outEtcQty)) + '</td>'
              // + '<td class="tl"><input type="text" value="' + nvl(item.remark,'') + '" class="w100" readonly></td>'
              + '<td class="tl"></td>'
              + '</tr>';
          } else {
            prodListHtml += '<tr class="h25">'
              + '<td class="tr" colspan="4">소계</td>'
              + '<td class="tr">' + (item.subUnitQty === 0 ? '' : addComma(item.subUnitQty)) + '</td>'
              + '<td class="tr">' + (item.subEtcQty === 0 ? '' : addComma(item.subEtcQty)) + '</td>'
              + '<td class="tl"></td>'
              + '</tr>';
          }
        }
        prodListHtml += '</table>';
        titleHtml    = '<table class="w100">'
          + '<colgroup>'
          + '<col style="width: 45%">'
          + '<col style="width: 20%">'
          + '<col style="width: 20%">'
          + '<col style="width: 15%">'
          + '</colgroup>'
          + '<tr>'
          + '<td class="tl br0"><p class="bk s20">분배지시서 (기사별)</p></td>'
          + '<td class="tl br0" valign="bottom">배송기사 : ' + prodList[0].dlvrNm + '</td>'
          + '<td class="tl br0" valign="bottom">출력 : ' + month + '-' + day + ' ' + time + '</td>'
          + '<td class="tl br0" valign="bottom">페이지 : ' + (i + 1) + ' / ' + totPageCnt + '</td>'
          + '</tr>'
          + '</table>';
        dstbDlvrProdStoreReportHtml += (i > 0 ? nextPageHtml : '') + titleHtml + prodListHeaderHtml + prodListHtml;
        prodListHtml = '';
      } // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 loop END
    } // 배송기사수만큼 loop End

    // console.log(dstbDlvrProdStoreReportHtml);
    $('#dstbDlvrProdStoreReport').append(dstbDlvrProdStoreReportHtml);

    // 로딩바 hide
    $scope.$broadcast('loadingPopupInactive');
  };


  // 인쇄
  $scope.print = function () {
    // create document
    var doc = new wijmo.PrintDocument({
      title: '분배지시서(기사별) 상품-매장'
    });

    // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
    var browser = navigator.userAgent.toLowerCase();
    if (-1 != browser.indexOf('chrome')) {
      // doc.append('<div style="height: 9mm;"></div>');
    }

    // add content to it
    var view = document.querySelector('#dstbDlvrProdStoreReport');
    doc.append(view);

    // and print it
    doc.print();
  };

}]);
