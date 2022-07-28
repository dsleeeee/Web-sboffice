/** 세금계산서 controller */
app.controller('taxReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('taxReportCtrl', $scope, $http, true));

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("taxReportCtrl", function (event, data) {
    $scope.writtenDate  = data.writtenDate;
    $scope.billFg       = data.billFg;
    $scope.taxFg        = data.taxFg;
    $scope.strSlipNo    = data.strSlipNo;
    $scope.writtenYear  = getFormatDate($scope.writtenDate).split('-')[0].substr(2, 2);
    $scope.writtenMonth = getFormatDate($scope.writtenDate).split('-')[1];
    $scope.writtenDay   = getFormatDate($scope.writtenDate).split('-')[2];
    $scope.vendrCd      = data.vendrCd;

    $scope.wjTaxReportLayer.show(true);

    // taxReport html 내용 초기화
    $("#taxReport").html('');

    $scope.supplierInfo();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 공급자 정보 조회
  $scope.supplierInfo = function () {
    // 로딩바 show
    $scope.$broadcast('loadingPopupActive');

    var params = {};
    
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
          $scope.taxReportInfoList();
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
  $scope.taxReportInfoList = function () {
    var params    = {};
    params.slipNo = $scope.strSlipNo;
    params.slipFg = $scope.slipFg;
    params.vendrCd = $scope.vendrCd;

    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }	
    
    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/dstmn/taxReport/taxReportInfoList.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var dataList = response.data.data.list;
          var loopCnt  = dataList.length;
          // console.log(response.data.data);

          var taxReportHtml = '';
          var nextPageHtml  = '<p class="nextPage mt5"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
          for (var i = 0; i < loopCnt; i++) {
            taxReportHtml = '';
            // 첫번째 페이지가 아닌 경우 세금계산서를 그리기 전에 프린트 출력시 다음 페이지로 넘기기 위한 html 을 붙여준다.
            if (i > 0) {
              taxReportHtml += nextPageHtml;
            }
            var item               = dataList[i];
            // console.log(item);
            $scope.slipNo          = nvl(item.slipNo, '');
            $scope.outDate         = nvl(item.outDate, '');
            $scope.outMonth        = getFormatDate(item.outDate).split('-')[1];
            $scope.outDay          = getFormatDate(item.outDate).split('-')[2];
            $scope.suppliedNm      = nvl(item.suppliedNm, '');
            $scope.suppliedOwnerNm = nvl(item.suppliedOwnerNm, '');
            $scope.suppliedBizNo   = nvl(item.suppliedBizNo, '');
            $scope.suppliedAddr    = nvl(item.suppliedAddr, '');
            $scope.suppliedBizType = nvl(item.suppliedBizType, '');
            $scope.suppliedBizItem = nvl(item.suppliedBizItem, '');
            $scope.baseAmt0        = nvl(item.baseAmt0, 0); // 면세 금액
            $scope.baseVat0        = nvl(item.baseVat0, 0); // 면세 VAT
            $scope.baseTot0        = nvl(item.baseTot0, 0); // 면세 합계금액
            $scope.baseAmt1        = nvl(item.baseAmt1, 0); // 과세 금액
            $scope.baseVat1        = nvl(item.baseVat1, 0); // 과세 VAT
            $scope.baseTot1        = nvl(item.baseTot1, 0); // 과세 합계금액
            $scope.baseAmt         = parseInt($scope.baseAmt0) + parseInt($scope.baseAmt1);
            $scope.baseVat         = parseInt($scope.baseVat0) + parseInt($scope.baseVat1);
            $scope.baseTot         = parseInt($scope.baseTot0) + parseInt($scope.baseTot1);

            var supplierBizNo = ($scope.supplierBizNo.length !== 10 ? $scope.supplierBizNo : $scope.supplierBizNo.substr(0, 3) + '-' + $scope.supplierBizNo.substr(3, 2) + '-' + $scope.supplierBizNo.substr(5));

            var strBaseAmt = $scope.baseAmt + '';
            var strBaseVat = $scope.baseVat + '';
            var arrBaseAmt = [];
            var arrBaseVat = [];
            for (var j = 11 - strBaseAmt.length; j < 11; j++) {
              arrBaseAmt[j] = strBaseAmt.substr(j - (11 - strBaseAmt.length), 1);
            }
            for (var j = 10 - strBaseVat.length; j < 10; j++) {
              arrBaseVat[j] = strBaseVat.substr(j - (10 - strBaseVat.length), 1);
            }

            var brColor = 'brRed'; // border color 를 위한 class. 공급자는 red, 공급받는자는 blue
            var subTxt  = messages["dstmn.taxReport.supplierStorage"];
            // 공급자 보관용과 공급받는자 보관용을 만들기 위해 for 문 2번 돈다.
            for (var k = 0; k < 2; k++) {
              if (k > 0) {
                brColor = 'brBlue';
                subTxt  = messages["dstmn.taxReport.suppliedStorage"];
                taxReportHtml += '<div class="w100 mt5 mb5 tc s12">'
                  + '- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -'
                  + '</div>';
              }

              taxReportHtml += '<table class="w100 ' + brColor + '">'
                + '<colgroup>'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '<col style="width: 30px;">'
                + '</colgroup>'
                + '<tr class="h20">'
                + '<td rowspan="2" colspan="14" class="tc"><p class="txtIn s25 bk">' + messages["dstmn.taxReport.taxReport"] + '</p> (' + subTxt + ')</td>'
                + '<td colspan="6" class="tc">' + messages["dstmn.taxReport.gwon"] + '</td>'
                + '<td colspan="8" class="tc">' + messages["dstmn.taxReport.ho"] + '</td>'
                + '</tr>'
                + '<tr>'
                + '<td colspan="2" class="tc"></td>'
                + '<td colspan="2" class="tc"></td>'
                + '<td colspan="2" class="tc">-</td>'
                + '<td colspan="2" class="tc"></td>'
                + '<td colspan="2" class="tc"></td>'
                + '<td colspan="2" class="tc"></td>'
                + '<td colspan="2" class="tc"></td>'
                + '</tr>'
                + '<tr>'
                + '<td rowspan="4" class="tc">' + messages["dstmn.taxReport.supplier"] + '</td>'
                + '<td colspan="2" class="tc">' + messages["dstmn.taxReport.regNum"] + '</td>'
                + '<td colspan="9" class="tc">' + supplierBizNo + '</td>'
                + '<td rowspan="4" class="tc">' + messages["dstmn.taxReport.supplied"] + '</td>'
                + '<td colspan="3" class="tc">' + messages["dstmn.taxReport.regNum"] + '</td>'
                + '<td class="tc">' + nvl($scope.suppliedBizNo.substr(0, 1), '') + '</td>'
                + '<td class="tc">' + nvl($scope.suppliedBizNo.substr(1, 1), '') + '</td>'
                + '<td class="tc">' + nvl($scope.suppliedBizNo.substr(2, 1), '') + '</td>'
                + '<td class="tc">-</td>'
                + '<td class="tc">' + nvl($scope.suppliedBizNo.substr(3, 1), '') + '</td>'
                + '<td class="tc">' + nvl($scope.suppliedBizNo.substr(4, 1), '') + '</td>'
                + '<td class="tc">-</td>'
                + '<td class="tc">' + nvl($scope.suppliedBizNo.substr(5, 1), '') + '</td>'
                + '<td class="tc">' + nvl($scope.suppliedBizNo.substr(6, 1), '') + '</td>'
                + '<td class="tc">' + nvl($scope.suppliedBizNo.substr(7, 1), '') + '</td>'
                + '<td class="tc">' + nvl($scope.suppliedBizNo.substr(8, 1), '') + '</td>'
                + '<td class="tc">' + nvl($scope.suppliedBizNo.substr(9, 1), '') + '</td>'
                + '</tr>'
                + '<tr>'
                + '<td colspan="2" class="tc">' + messages["dstmn.taxReport.storeNm"] + '</td>'
                + '<td colspan="4" class="tl">' + $scope.supplierNm + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.ownerNm"] + '</td>'
                + '<td colspan="4" class="tl">' + $scope.supplierOwnerNm + '</td>'
                + '<td colspan="3" class="tc">' + messages["dstmn.taxReport.storeNm"] + '</td>'
                + '<td colspan="5" class="tl">' + $scope.suppliedNm + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.ownerNm"] + '</td>'
                + '<td colspan="6" class="tl">' + $scope.suppliedOwnerNm + '</td>'
                + '</tr>'
                + '<tr>'
                + '<td colspan="2" class="tc">' + messages["dstmn.taxReport.storeAddr"] + '</td>'
                + '<td colspan="9" class="tl">' + $scope.supplierAddr + '</td>'
                + '<td colspan="3" class="tc">' + messages["dstmn.taxReport.storeAddr"] + '</td>'
                + '<td colspan="12" class="tl">' + $scope.suppliedAddr + '</td>'
                + '</tr>'
                + '<tr>'
                + '<td colspan="2" class="tc">' + messages["dstmn.taxReport.bizType"] + '</td>'
                + '<td colspan="4" class="tl">' + $scope.supplierBizType + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.bizItem"] + '</td>'
                + '<td colspan="4" class="tl">' + $scope.supplierBizItem + '</td>'
                + '<td colspan="3" class="tc">' + messages["dstmn.taxReport.bizType"] + '</td>'
                + '<td colspan="5" class="tl">' + $scope.suppliedBizType + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.bizItem"] + '</td>'
                + '<td colspan="6" class="tl">' + $scope.suppliedBizItem + '</td>'
                + '</tr>'
                + '<tr class="h20">'
                + '<td colspan="3" class="tc">' + messages["dstmn.taxReport.write"] + '</td>'
                + '<td colspan="12" class="tc">' + messages["dstmn.taxReport.splyUprc"] + '</td>'
                + '<td colspan="10" class="tc">' + messages["dstmn.taxReport.taxAmt"] + '</td>'
                + '<td colspan="3" class="tc">' + messages["dstmn.taxReport.remark"] + '</td>'
                + '</tr>'
                + '<tr>'
                + '<td class="tc">' + messages["dstmn.taxReport.year"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.month"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.day"] + '</td>'
                + '<td style="text-align: center; width:40px;">' + messages["dstmn.taxReport.blankCount"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.hundred"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.ten"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.billion"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.thousand"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.hundred"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.ten"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.tenThousand"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.thousand"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.hundred"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.ten"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.one"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.ten"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.billion"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.thousand"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.hundred"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.ten"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.tenThousand"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.thousand"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.hundred"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.ten"] + '</td>'
                + '<td class="tc">' + messages["dstmn.taxReport.one"] + '</td>'
                + '<td rowspan="2" colspan="3" class="tc"></td>'
                + '</tr>'
                + '<tr>'
                + '<td class="tc">' + $scope.writtenYear + '</td>'
                + '<td class="tc">' + $scope.writtenMonth + '</td>'
                + '<td class="tc">' + $scope.writtenDay + '</td>'
                + '<td class="tc">' + (11 - strBaseAmt.length) + '</td>'
                + '<td class="tc">' + nvl(arrBaseAmt[0], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseAmt[1], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseAmt[2], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseAmt[3], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseAmt[4], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseAmt[5], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseAmt[6], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseAmt[7], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseAmt[8], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseAmt[9], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseAmt[10], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseVat[0], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseVat[1], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseVat[2], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseVat[3], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseVat[4], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseVat[5], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseVat[6], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseVat[7], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseVat[8], '') + '</td>'
                + '<td class="tc">' + nvl(arrBaseVat[9], '') + '</td>'
                + '</tr>'
                + '<tr  class="h20">'
                + '<td colspan="2" class="tc">' + messages["dstmn.taxReport.month"] + ' ' + messages["dstmn.taxReport.day"] + '</td>'
                + '<td colspan="6" class="tc">' + messages["dstmn.taxReport.prodNm"] + '</td>'
                + '<td colspan="3" class="tc">' + messages["dstmn.taxReport.poUnitFg"] + '</td>'
                + '<td colspan="3" class="tc">' + messages["dstmn.taxReport.qty"] + '</td>'
                + '<td colspan="3" class="tc">' + messages["dstmn.taxReport.uprc"] + '</td>'
                + '<td colspan="4" class="tc">' + messages["dstmn.taxReport.splyUprc"] + '</td>'
                + '<td colspan="4" class="tc">' + messages["dstmn.taxReport.taxAmt"] + '</td>'
                + '<td colspan="3" class="tc">' + messages["dstmn.taxReport.remark"] + '</td>'
                + '</tr>'
                + '<tr>'
                + '<td class="tc">' + $scope.outMonth + '</td>'
                + '<td class="tc">' + $scope.outDay + '</td>'
                + '<td colspan="6" class="tc">' + ($scope.taxFg === '0' ? messages["dstmn.taxReport.prodPay"] : messages["dstmn.taxReport.prodPay"] + '-' + messages["dstmn.taxReport.taxation"]) + '</td>'
                + '<td colspan="3" class="tc"></td>'
                + '<td colspan="3" class="tr"></td>'
                + '<td colspan="3" class="tr"></td>'
                + '<td colspan="4" class="tr">' + ($scope.taxFg === '0' ? addComma($scope.baseAmt) : addComma($scope.baseAmt1)) + '</td>'
                + '<td colspan="4" class="tr">' + ($scope.taxFg === '0' ? addComma($scope.baseVat) : addComma($scope.baseVat1)) + '</td>'
                + '<td colspan="3" class="tc"></td>'
                + '</tr>'
                + '<tr>'
                + '<td class="tc">' + ($scope.taxFg === '0' ? '' : $scope.outMonth) + '</td>'
                + '<td class="tc">' + ($scope.taxFg === '0' ? '' : $scope.outDay) + '</td>'
                + '<td colspan="6" class="tc">' + ($scope.taxFg === '0' ? messages["dstmn.taxReport.slipNo"] + ' : ' + $scope.slipNo : messages["dstmn.taxReport.prodPay"] + '-' + messages["dstmn.taxReport.taxFree"]) + '</td>'
                + '<td colspan="3" class="tc"></td>'
                + '<td colspan="3" class="tr"></td>'
                + '<td colspan="3" class="tr"></td>'
                + '<td colspan="4" class="tr">' + ($scope.taxFg === '0' ? '' : addComma($scope.baseAmt0)) + '</td>'
                + '<td colspan="4" class="tr">' + ($scope.taxFg === '0' ? '' : addComma($scope.baseVat0)) + '</td>'
                + '<td colspan="3" class="tc"></td>'
                + '</tr>'
                + '<tr>'
                + '<td class="tc"></td>'
                + '<td class="tc"></td>'
                + '<td colspan="6" class="tc">' + ($scope.taxFg === '1' ? messages["dstmn.taxReport.slipNo"] + ' : ' + $scope.slipNo : '') + '</td>'
                + '<td colspan="3" class="tc"></td>'
                + '<td colspan="3" class="tr"></td>'
                + '<td colspan="3" class="tr"></td>'
                + '<td colspan="4" class="tr"></td>'
                + '<td colspan="4" class="tr"></td>'
                + '<td colspan="3" class="tc"></td>'
                + '</tr>'
                + '<tr>'
                + '<td class="tc"></td>'
                + '<td class="tc"></td>'
                + '<td colspan="6" class="tc"></td>'
                + '<td colspan="3" class="tc"></td>'
                + '<td colspan="3" class="tr"></td>'
                + '<td colspan="3" class="tr"></td>'
                + '<td colspan="4" class="tr">0</td>'
                + '<td colspan="4" class="tr">0</td>'
                + '<td colspan="3" class="tc"></td>'
                + '</tr>'
                + '<tr  class="h20">'
                + '<td colspan="3" class="tc">' + messages["dstmn.taxReport.prodPay"] + '</td>'
                + '<td colspan="4" class="tc">' + messages["dstmn.taxReport.unpaidAmt"] + '</td>'
                + '<td colspan="5" class="tc">' + messages["dstmn.taxReport.depositAndEtc"] + '</td>'
                + '<td colspan="5" class="tc">' + messages["dstmn.taxReport.billAmt"] + '</td>'
                + '<td rowspan="2" colspan="11" class="tc">' + messages["dstmn.taxReport.txt1"] + ' <b>' + ($scope.billFg === '0' ? messages["dstmn.taxReport.bill"] : messages["dstmn.taxReport.receipt"]) + '</b> ' + messages["dstmn.taxReport.txt2"] + '</td>'
                + '</tr>'
                + '<tr>'
                + '<td colspan="3" class="tr">' + addComma($scope.baseTot) + '</td>'
                + '<td colspan="4" class="tr">0</td>'
                + '<td colspan="5" class="tr">0</td>'
                + '<td colspan="5" class="tr">' + addComma($scope.baseTot) + '</td>'
                + '</tr>'
                + '</table>';
            }
            $('#taxReport').append(taxReportHtml);
          }

          // console.log($('#taxReport').html());

          // 로딩바 hide
          $scope.$broadcast('loadingPopupInactive');
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


  // 인쇄
  $scope.print = function () {
    // create document
    var doc = new wijmo.PrintDocument({
      title: messages["dstmn.taxReport.taxReport"]
    });

    // 브라우저 체크하여 크롬인 경우 위에 빈칸 9mm 를 둔다. ie와 비슷하게 맞추기 위함...
    var browser = navigator.userAgent.toLowerCase();
    if (-1 != browser.indexOf('chrome')) {
      // doc.append('<div style="height: 9mm;"></div>');
    }

    // add content to it
    var view = document.querySelector('#taxReport');
    doc.append(view);
    // console.log(view);

    // and print it
    doc.print();
  };


}]);
