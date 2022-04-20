/** 거래명세표 controller */
app.controller('transReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('transReportCtrl', $scope, $http, true));

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("transReportCtrl", function (event, data) {
    $scope.slipFg     = data.slipFg;
    $scope.stmtAcctFg = data.stmtAcctFg;
    $scope.strSlipNo  = data.strSlipNo;
    $scope.startDate = data.startDate;
    $scope.endDate = data.endDate;

    $scope.wjTransReportLayer.show(true);

    // transReport html 내용 초기화
    $("#transReport").html('');

    $scope.supplierInfo();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 공급자 정보 조회
  $scope.supplierInfo = function () {
    // 로딩바 show
    $scope.$broadcast('loadingPopupActive');
    var params    = {};
    params.slipNo = $scope.strSlipNo;
    params.slipFg = $scope.slipFg;

    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/dstmn/taxReport/supplierInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var data = response.data.data;
          // console.log(response.data.data);

          $scope.supplierNm      = nvl(data.supplierNm, '');
          $scope.supplierOwnerNm = nvl(data.supplierOwnerNm, '');
          $scope.supplierBizNo   = nvl(data.supplierBizNo, '');
          $scope.supplierAddr    = nvl(data.supplierAddr, '');
          $scope.supplierBizType = nvl(data.supplierBizType, '');
          $scope.supplierBizItem = nvl(data.supplierBizItem, '');

          // 전표 내역 조회
          $scope.transReportInfoList();
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


  // 전표 내역 조회
  $scope.transReportInfoList = function () {
    var params    = {};
    params.slipNo = $scope.strSlipNo;
    params.slipFg = $scope.slipFg;

    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/dstmn/transReport/transReportInfoList.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var dataList        = response.data.data.list;
          $scope.slipInfoList = dataList;
          $scope.prodList();
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


  // 전표의 상품 리스트 조회
  $scope.prodList = function () {
    var params    = {};
    params.slipNo = $scope.strSlipNo;
    params.startDate	=	$scope.startDate;
    params.endDate		=	$scope.endDate;

    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/dstmn/dstmnDtl/list.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var data    = response.data.data.list;
          var loopCnt = data.length;

          var slipProdList = {};
          var prodList     = [];
          var slipNo       = '';
          var bSlipNo      = '';
          for (var i = 0; i < loopCnt; i++) {
            slipNo = data[i].slipNo;

            // 이전 전표번호와 현재 전표가 다르면 이전 전표번호로 상품리스트를 담는다.
            if (bSlipNo !== '' && slipNo !== bSlipNo) {
              slipProdList[bSlipNo] = prodList;
              prodList              = [];
            }

            bSlipNo = slipNo;
            prodList.push(data[i]);
          }

          // for 문 돌면서 이전 전표번호와 현재 전표가 다르지 않게 끝나 담지 못한 데이터 담기.
          if (nvl(slipProdList[slipNo], '') === '') {
            slipProdList[slipNo] = prodList;
          }

          $scope.slipProdList = slipProdList;
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
    var slipCnt         = $scope.slipInfoList.length;
    var transReportHtml = '';
    var nextPageHtml    = '<p class="nextPage mt5"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html

    for (var i = 0; i < slipCnt; i++) { // 전표수만큼 loop
      var slipInfo = $scope.slipInfoList[i];
      var dataList = $scope.slipProdList[slipInfo.slipNo];
      var loopCnt  = dataList.length;

      var arrProdList = [];
      var arrList     = [];
      var pageRowCnt  = 20; // 한 페이지에 표시할 row 수 변수
      var rowCnt      = 0;
      var totPageCnt  = Math.ceil(parseInt(loopCnt) / parseInt(pageRowCnt));
      // 한 페이지에 표시할 row 수에 따라 배열에 담기 위해 for 문 돔. 해당 배열만큼 페이지수 생성
      for (var j = 0; j < loopCnt; j++) {
        if (rowCnt >= pageRowCnt) {
          rowCnt = 0;
          arrProdList.push(arrList);
          arrList = [];
        }

        var item = dataList[j];
        arrList.push(item);
        rowCnt++;
      }
      arrProdList.push(arrList);

      slipInfo.totPageCnt = totPageCnt; // 전표내역에 총페이지 JSON 형태로 추가
      transReportHtml += (i > 0 ? nextPageHtml : ''); // 프린트 출력시 다음 페이지로 넘기기 위한 html 을 붙여준다.

      var titleHtml  = ''; // 최상단 html
      var infoHtml   = $scope.getInfoHtml(slipInfo); // 공급자, 공급받는자 정보 html
      var footerHtml = ''; // 최하단 정보 html

      var subTxt   = '공급자 보관용';
      var stmtLoop = ($scope.stmtAcctFg === '' ? 2 : 1); // 거래명세표 인쇄 구분에 따라 loop 돌 횟수를 정한다.
      for (var k = 0; k < stmtLoop; k++) { // 거래명세표 인쇄 구분에 따라 공급자 보관용과 공급받는자 보관용을 만들기 위해 loop 돈다.
        // 상품 리스트 HTML 생성
        transReportHtml += (k > 0 ? nextPageHtml : ''); // 프린트 출력시 다음 페이지로 넘기기 위한 html 을 붙여준다.
        var prodListHtml       = '';
        var prodListHeaderHtml = '<table class="w100">' // 상품리스트 header html
          + '<colgroup>'
          + '<col style="width:5%;">'
          + '<col style="width:25%;">'
          + '<col style="width:10%;">'
          + '<col style="width:10%;">'
          + '<col style="width:10%;">'
          + '<col style="width:7%;">'
          + '<col style="width:13%;">'
          + '<col style="width:10%;">'
          + '<col style="width:10%;">'
          + '</colgroup>'
          + '<tr class="h30">'
          + '<th class="tc">No.</th>'
          + '<th class="tc">품목 및 규격</th>'
          + '<th class="tc">원산지</th>'
          + '<th class="tc">단위</th>'
          + '<th class="tc">단가</th>'
          + '<th class="tc">수량</th>'
          + '<th class="tc">금액<br>(부가세포함)</th>'
          + '<th class="tc">세액</th>'
          + '<th class="tc">비고</th>'
          + '</tr>';

        // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 loop 돈다.
        for (var m = 0; m < arrProdList.length; m++) {
          var pageTotAmt = 0;
          var pageTotVat = 0;
          var pageTotTot = 0;
          var pageTotQty = 0;
          var prodList   = arrProdList[m];
          for (var n = 0; n < pageRowCnt; n++) { // 한 페이지에 표시할 row 수만큼 loop
            // 상품에 대한 데이터가 없더라도 row 는 만들어줌
            if (nvl(prodList[n], '') === '') {
              prodListHtml += '<tr class="h25">'
                + '<td class="tc">' + (n + 1) + '</td>'
                + '<td></td>'
                + '<td></td>'
                + '<td></td>'
                + '<td></td>'
                + '<td></td>'
                + '<td></td>'
                + '<td></td>'
                + '<td></td>'
                + '</tr>';
            } else {
              var item   = prodList[n];
              var prodNm = '[' + item.prodCd + '] ' + item.prodNm;
              // var nmLimit = 30; // 상품명이 해당 변수의 길이보다 긴 경우 변수의 길이만큼 자른 후 뒤에 .. 을 붙여 표시한다.
              // if (prodNm.length > nmLimit) {
              //   prodNm = prodNm.substr(0, nmLimit) + '..';
              // }
              // console.log(prodList[n]);
              var outUnitQty = (item.outUnitQty === undefined || item.outUnitQty == null || item.outUnitQty.length <= 0) ? nvl(item.outUnitQty,0) : addComma(item.outUnitQty);
              var outEtcQty = (item.outEtcQty === undefined || item.outEtcQty == null || item.outEtcQty.length <= 0) ? nvl(item.outEtcQty,0) : addComma(item.outEtcQty);
              var outTotQty = (item.outTotQty === undefined || item.outTotQty == null || item.outTotQty.length <= 0) ? item.outTotQty : addComma(item.outTotQty);
              var outAmt = (item.outAmt === undefined || item.outAmt == null || item.outAmt.length <= 0) ? item.outAmt : addComma(item.outAmt);
              var outVat = (item.outVat === undefined || item.outVat == null || item.outVat.length <= 0) ? item.outVat : addComma(item.outVat);
              var outQty = 0;
              if(outEtcQty == 0){
            	  outQty	=	outUnitQty;
              } else {
            	  outQty	=	outUnitQty+'('+ outEtcQty+')';
              }
//              console.log("outUnitQty ::"+outUnitQty)
              console.log("outQty ::"+outQty)
//              console.log("outTotQty ::"+outTotQty)
              prodListHtml += '<tr class="h25">'
                + '<td class="tc">' + (n + 1) + '</td>'
                + '<td class="tl"><input type="text" value="' + prodNm + '" class="w100" readonly></td>'
                + '<td class="tc">' + '</td>' // 박정은 20.03.12 (수정 예정)
                + '<td class="tc">' + item.poUnitFgNm + '</td>'
                + '<td class="tr">' + addComma(item.outSplyUprc) + '</td>'
//                + '<td class="tr">' + addComma(item.outTotQty) + '</td>'
//                + '<td class="tr">' + addComma(item.outAmt) + '</td>'
//                + '<td class="tr">' + addComma(item.outVat) + '</td>'
//                + '<td class="tr">' + outTotQty + '</td>'
            	+ '<td class="tr">' + outQty + '</td>'
                + '<td class="tr">' + outAmt + '</td>'
                + '<td class="tr">' + outVat + '</td>'
                 + '<td class="tl"><input type="text" value="' + nvl(item.remark,'') + '" class="w100" readonly></td>'
//                + '<td class="tl"></td>'
                + '</tr>';
              pageTotAmt = parseInt(pageTotAmt) + parseInt(item.outAmt);
              pageTotVat = parseInt(pageTotVat) + parseInt(item.outVat);
              pageTotTot = parseInt(pageTotTot) + parseInt(item.outTot);
              pageTotQty = parseInt(pageTotQty) + parseInt(item.outTotQty);
            }
          } // 한 페이지에 표시할 row 수만큼 loop END
          prodListHtml += '</table>';
          slipInfo.pageTotAmt = pageTotAmt;
          slipInfo.pageTotVat = pageTotVat;
          slipInfo.pageTotTot = pageTotTot;
          slipInfo.pageTotQty = pageTotQty;
          slipInfo.pageNo     = (m + 1);

          if (k === 0) { // 공급자 보관용인 경우
            if ($scope.stmtAcctFg === '1') {
              subTxt = '공급자 보관용';
            } else if ($scope.stmtAcctFg === '2') {
              subTxt = '공급받는자 보관용';
            }
            titleHtml = $scope.getTitleHtml(subTxt, slipInfo);
          } else if (k > 0) { // 공급받는자 보관용인 경우
            subTxt    = '공급받는자 보관용';
            titleHtml = $scope.getTitleHtml(subTxt, slipInfo);
          }
          footerHtml = $scope.getFooterHtml(slipInfo); // 최하단 정보 html

          transReportHtml += (m > 0 ? nextPageHtml : '') + titleHtml + infoHtml + prodListHeaderHtml + prodListHtml + footerHtml;
          prodListHtml = '';
        } // 데이터 조회 후 한 페이지에 표시할 row 수에 따라 배열에 담아놓은 수만큼 페이지 생성하기 위해 loop END
      } // 거래명세표 인쇄 구분에 따라 공급자 보관용과 공급받는자 보관용을 만들기 위해 loop END
    } // 전표수만큼 loop END

    // console.log(transReportHtml);
    $('#transReport').append(transReportHtml);

    // 로딩바 hide
    $scope.$broadcast('loadingPopupInactive');
  };


  // 타이틀 정보 html 생성
  $scope.getTitleHtml = function (subTxt, slipInfo) {
    // 현재 월, 일 세팅
    var month = getCurDate('-').split('-')[1];
    var day   = getCurDate('-').split('-')[2];

    var titleHtml = '<table class="w100">'
      + '<colgroup>'
      + '<col style="width: 45%">'
      + '<col style="width: 3%">'
      + '<col style="width: 9%">'
      + '<col style="width: 9%">'
      + '<col style="width: 9%">'
      + '<col style="width: 1%">'
      + '<col style="width: 10%">'
      + '<col style="width: 14%">'
      + '</colgroup>'
      + '<tr class="h25">'
      + '<td rowspan="4" class="tc br0"><p class="s35 bk">거래명세표 ['+($scope.slipFg === 1 ? '출고' : '반품')+']</p><br>( ' + subTxt + ' )</td>'
      + '<td rowspan="4" class="tc">확<br><br>인</td>'
      + '<td class="tc">배송자</td>'
      + '<td class="tc">인수자</td>'
      + '<td class="tc">담당자</td>'
      + '<td rowspan="4" class="tc br0"></td>'
      + '<td rowspan="2" class="tc">조회기간</td>'
      + '<td class="tc">' + $scope.startDate + '</td>'
      + '</tr>'
      + '<tr class="h25">'
      + '<td rowspan="3" class="tc">' + nvl(slipInfo.dlvrNm, '') + '</td>'
      + '<td rowspan="3" class="tc"></td>'
      + '<td rowspan="3" class="tc"></td>'
      + '<td class="tc">' + $scope.endDate + '</td>'
      + '</tr>'
      + '<tr class="h25">'
      + '<td class="tc">발행일시</td>'
      + '<td class="tc">' + month + '-' + day + ' ' + getCurTime(':') + '</td>'
      + '</tr>'
      + '<tr class="h25">'
      + '<td class="tc">페이지</td>'
      + '<td class="tc">' + slipInfo.pageNo + ' / ' + slipInfo.totPageCnt + '</td>'
      + '</tr>'
      + '</table>';
    return titleHtml;
  };


  // 공급자, 공급받는자 정보 html 생성
  $scope.getInfoHtml = function (slipInfo) {
    $scope.suppliedNm      = nvl(slipInfo.suppliedNm, '');
    $scope.suppliedOwnerNm = nvl(slipInfo.suppliedOwnerNm, '');
    $scope.suppliedBizNo   = nvl(slipInfo.suppliedBizNo, '');
    $scope.suppliedAddr    = nvl(slipInfo.suppliedAddr, '');
    $scope.suppliedBizType = nvl(slipInfo.suppliedBizType, '');
    $scope.suppliedBizItem = nvl(slipInfo.suppliedBizItem, '');

    // 사업자번호가 혹시나 10자리가 안되면 그대로 보여주기
    var supplierBizNo = ($scope.supplierBizNo.length !== 10 ? $scope.supplierBizNo : $scope.supplierBizNo.substr(0, 3) + '-' + $scope.supplierBizNo.substr(3, 2) + '-' + $scope.supplierBizNo.substr(5));
    var suppliedBizNo = ($scope.suppliedBizNo.length !== 10 ? $scope.suppliedBizNo : $scope.suppliedBizNo.substr(0, 3) + '-' + $scope.suppliedBizNo.substr(3, 2) + '-' + $scope.suppliedBizNo.substr(5));

    var slipInfoHtml = '<table class="w100 mt5">'
      + '<colgroup>'
      + '<col style="width: 3%">'
      + '<col style="width: 11%">'
      + '<col style="width: 18%">'
      + '<col style="width: 8%">'
      + '<col style="width: 10%">'
      + '<col style="width: 3%">'
      + '<col style="width: 11%">'
      + '<col style="width: 18%">'
      + '<col style="width: 8%">'
      + '<col style="width: 10%">'
      + '</colgroup>'
      + '<tr>'
      + '<td rowspan="4" class="tc">공<br><br><br>급<br><br><br>자</td>'
      + '<td class="tc">사업자번호</td>'
      + '<td colspan="3" class="tc">' + supplierBizNo + '</td>'
      + '<td rowspan="4" class="tc">공<br><br>급<br><br>받<br><br>는<br><br>자</td>'
      + '<td class="tc">사업자번호</td>'
      + '<td colspan="3" class="tc">' + suppliedBizNo + '</td>'
      + '</tr>'
      + '<tr>'
      + '<td class="tc">상호(법인명)</td>'
      + '<td class="tc">' + $scope.supplierNm + '</td>'
      + '<td class="tc">성명</td>'
      + '<td class="tc">' + $scope.supplierOwnerNm + '</td>'
      + '<td class="tc">상호(법인명)</td>'
      + '<td class="tc">' + $scope.suppliedNm + '</td>'
      + '<td class="tc">성명</td>'
      + '<td class="tc">' + $scope.suppliedOwnerNm + '</td>'
      + '</tr>'
      + '<tr>'
      + '<td class="tc">사업장주소</td>'
      + '<td colspan="3" class="tl">' + $scope.supplierAddr + '</td>'
      + '<td class="tc">사업장주소</td>'
      + '<td colspan="3" class="tl">' + $scope.suppliedAddr + '</td>'
      + '</tr>'
      + '<tr>'
      + '<td class="tc">업태</td>'
      + '<td class="tc">' + $scope.supplierBizItem + '</td>'
      + '<td class="tc">종목</td>'
      + '<td class="tc">' + $scope.supplierBizType + '</td>'
      + '<td class="tc">업태</td>'
      + '<td class="tc">' + $scope.suppliedBizItem + '</td>'
      + '<td class="tc">종목</td>'
      + '<td class="tc">' + $scope.suppliedBizType + '</td>'
      + '</tr>'
      + '</table>';
    return slipInfoHtml;
  };


  // 최하단 정보 html 생성
  $scope.getFooterHtml = function (slipInfo) {
    // 비고가 5줄 이상 넘어가는 경우 5줄까지만 보여주고 나머지 줄은 ... 으로 표시.
    var remark      = nvl(slipInfo.remark, '').replace(/\n/gi, '<br>');
    var remarkLimit = 5;
    if (remark.split('<br>').length > remarkLimit) {
      remark = remark.split('<br>').slice(0, remarkLimit).join('<br>');
      remark += '...';
    }

    var footerHtml = '<table class="w100">'
      + '<colgroup>'
      + '<col style="width: 11%">' // 공급가액
      + '<col style="width: 14%">' // 공급가액값
      + '<col style="width:  9%">' // 세액
      + '<col style="width: 12%">' // 세액값
      + '<col style="width: 15%">' // 합계수량
      + '<col style="width:  9%">' // 합계수량값
      + '<col style="width: 15%">' // 합계금액
      + '<col style="width: 15%">' // 합계금액값
      + '</colgroup>'
      + '<tr>'
      + '<td class="tc">공급가액</td>'
      + '<td class="tr">' + addComma(slipInfo.pageTotAmt) + '</td>'
      + '<td class="tc">세 액</td>'
      + '<td class="tr">' + addComma(slipInfo.pageTotVat) + '</td>'
      + '<td class="tc">페이지 합계수량</td>'
      + '<td class="tr">' + addComma(slipInfo.pageTotQty) + '</td>'
      + '<td class="tc">페이지 합계금액</td>'
      + '<td class="tr">' + addComma(slipInfo.pageTotTot) + '</td>'
      + '</tr>'
      + '<tr>'
      + '<td class="tc">총 공급가액</td>'
      + '<td class="tr">' + addComma(slipInfo.totAmt) + '</td>'
      + '<td class="tc">총 세액</td>'
      + '<td class="tr">' + addComma(slipInfo.totVat) + '</td>'
      + '<td class="tc">총 합계수량</td>'
      + '<td class="tr">' + addComma(slipInfo.totQty) + '</td>'
      + '<td class="tc">총 합계금액</td>'
      + '<td class="tr">' + addComma(slipInfo.totTot) + '</td>'
      + '</tr>'
      + '<tr style="height:90px;" valign="top">'
      + '<td class="tc" valign="middle">비 고</td>'
      + '<td class="tl" colspan="7">' + remark + '</td>'
      + '</tr>'
      + '</table>';
    return footerHtml;
  };


  // 인쇄
  $scope.print = function () {
    // create document
    var doc = new wijmo.PrintDocument({
      title: '거래명세표'
    });

    // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
    var browser = navigator.userAgent.toLowerCase();
    if (-1 != browser.indexOf('chrome')) {
      // doc.append('<div style="height: 9mm;"></div>');
    }

    // add content to it
    var view = document.querySelector('#transReport');
    doc.append(view);

    // and print it
    doc.print();
  };
}]);
