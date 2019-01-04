<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstmn/taxReport/"/>

<style type="text/css">
</style>

<wj-popup id="wjTaxReportLayer" control="wjTaxReportLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="taxReportLayer" class="wj-dialog wj-dialog-columns" ng-controller="taxReportCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <div class="mt5 tr">
        <%-- 인쇄 --%>
        <button type="button" class="btn_skyblue ml5" id="btnPrint" ng-click="print()">
          <s:message code="vendrOrder.report.print"/></button>
      </div>

      <div class="w100 mt10 taxReport" id="taxReport">
      </div>

    </div>
  </div>
</wj-popup>


<script type="text/javascript">
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

      $scope.wjTaxReportLayer.show(true);

      // taxReport html 내용 초기화
      $("#taxReport").html('');

      $scope.supplierInfo();

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 공급자 정보 조회
    $scope.supplierInfo = function () {
      var params = {};

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

            var taxReportHtml   = '';
            var nextPageHtml = '<p style="page-break-before:always; margin-top: 5px;"></p>'; // 프린트 출력시 다음 페이지로 넘기기 위한 html
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
              var subTxt  = messages["dstmn.report.supplier"];
              // 공급자 보관용과 공급받는자 보관용을 만들기 위해 for 문 2번 돈다.
              for (var k = 0; k < 2; k++) {
                if (k > 0) {
                  brColor = 'brBlue';
                  subTxt  = messages["dstmn.report.supplied"];
                  taxReportHtml += '<div class="w100 mt10 mb10 tc">'
                    + '- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -'
                    + '</div>';
                }

                taxReportHtml += '<table class="w100 ' + brColor + '">'
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
                  + '<tr>'
                  + '<td rowspan="2" colspan="14" class="tc"><p class="txtIn" style="font-size: 25px; color: black;">' + messages["dstmn.report.taxReport"] + '</p> (' + subTxt + ')</td>'
                  + '<td colspan="6" class="tc">권</td>'
                  + '<td colspan="8" class="tc">호</td>'
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
                  + '<td rowspan="4" class="tc">공<br><br><br>급<br><br><br>자</td>'
                  + '<td colspan="2" class="tc">등록<br>번호</td>'
                  + '<td colspan="9" class="tc">' + supplierBizNo + '</td>'
                  + '<td rowspan="4" class="tc">공<br><br>급<br><br>받<br><br>는<br><br>자</td>'
                  + '<td colspan="3" class="tc">등록<br>번호</td>'
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
                  + '<td colspan="2" class="tc">상호<br>(법인명)</td>'
                  + '<td colspan="4" class="tl">' + $scope.supplierNm + '</td>'
                  + '<td class="tc">성명</td>'
                  + '<td colspan="4" class="tl">' + $scope.supplierOwnerNm + '</td>'
                  + '<td colspan="3" class="tc">상호<br>(법인명)</td>'
                  + '<td colspan="5" class="tl">' + $scope.suppliedNm + '</td>'
                  + '<td class="tc">성명</td>'
                  + '<td colspan="6" class="tl">' + $scope.suppliedOwnerNm + '</td>'
                  + '</tr>'
                  + '<tr>'
                  + '<td colspan="2" class="tc">사업장<br>주소</td>'
                  + '<td colspan="9" class="tl">' + $scope.supplierAddr + '</td>'
                  + '<td colspan="3" class="tc">사업장<br>주소</td>'
                  + '<td colspan="12" class="tl">' + $scope.suppliedAddr + '</td>'
                  + '</tr>'
                  + '<tr>'
                  + '<td colspan="2" class="tc">업태</td>'
                  + '<td colspan="4" class="tl">' + $scope.supplierBizType + '</td>'
                  + '<td class="tc">종목</td>'
                  + '<td colspan="4" class="tl">' + $scope.supplierBizItem + '</td>'
                  + '<td colspan="3" class="tc">업태</td>'
                  + '<td colspan="5" class="tl">' + $scope.suppliedBizType + '</td>'
                  + '<td class="tc">종목</td>'
                  + '<td colspan="6" class="tl">' + $scope.suppliedBizItem + '</td>'
                  + '</tr>'
                  + '<tr>'
                  + '<td colspan="3" class="tc">작성</td>'
                  + '<td colspan="12" class="tc">공급가액</td>'
                  + '<td colspan="10" class="tc">세액</td>'
                  + '<td colspan="3" class="tc">비고</td>'
                  + '</tr>'
                  + '<tr>'
                  + '<td class="tc">년</td>'
                  + '<td class="tc">월</td>'
                  + '<td class="tc">일</td>'
                  + '<td style="text-align: center; width:35px;">공란<br>수</td>'
                  + '<td class="tc">백</td>'
                  + '<td class="tc">십</td>'
                  + '<td class="tc">억</td>'
                  + '<td class="tc">천</td>'
                  + '<td class="tc">백</td>'
                  + '<td class="tc">십</td>'
                  + '<td class="tc">만</td>'
                  + '<td class="tc">천</td>'
                  + '<td class="tc">백</td>'
                  + '<td class="tc">십</td>'
                  + '<td class="tc">일</td>'
                  + '<td class="tc">십</td>'
                  + '<td class="tc">억</td>'
                  + '<td class="tc">천</td>'
                  + '<td class="tc">백</td>'
                  + '<td class="tc">십</td>'
                  + '<td class="tc">만</td>'
                  + '<td class="tc">천</td>'
                  + '<td class="tc">백</td>'
                  + '<td class="tc">십</td>'
                  + '<td class="tc">일</td>'
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
                  + '<tr>'
                  + '<td colspan="2" class="tc">월 일</td>'
                  + '<td colspan="6" class="tc">품목</td>'
                  + '<td colspan="3" class="tc">규격</td>'
                  + '<td colspan="3" class="tc">수량</td>'
                  + '<td colspan="3" class="tc">단가</td>'
                  + '<td colspan="4" class="tc">공급가액</td>'
                  + '<td colspan="4" class="tc">세액</td>'
                  + '<td colspan="3" class="tc">비고</td>'
                  + '</tr>'
                  + '<tr>'
                  + '<td class="tc">' + $scope.outMonth + '</td>'
                  + '<td class="tc">' + $scope.outDay + '</td>'
                  + '<td colspan="6" class="tc">' + ($scope.taxFg === '0' ? '물품대' : '물품대-과세') + '</td>'
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
                  + '<td colspan="6" class="tc">' + ($scope.taxFg === '0' ? '전표번호 : ' + $scope.slipNo : '물품대-면세') + '</td>'
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
                  + '<td colspan="6" class="tc">' + ($scope.taxFg === '1' ? '전표번호 : ' + $scope.slipNo : '') + '</td>'
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
                  + '<tr>'
                  + '<td colspan="3" class="tc">물품대</td>'
                  + '<td colspan="4" class="tc">미수금</td>'
                  + '<td colspan="5" class="tc">입금 및 기타</td>'
                  + '<td colspan="5" class="tc">청구금액</td>'
                  + '<td rowspan="2" colspan="11" class="tc">이 금액을 <b>' + ($scope.billFg === '0' ? '청구' : '영수') + '</b> 함</td>'
                  + '</tr>'
                  + '<tr>'
                  + '<td colspan="3" class="tr">' + addComma($scope.baseTot) + '</td>'
                  + '<td colspan="4" class="tr">0</td>'
                  + '<td colspan="5" class="tr">0</td>'
                  + '<td colspan="5" class="tr">' + addComma($scope.baseTot) + '</td>'
                  + '</tr>'
                  + '</table>';
              }
              // console.log(taxReportHtml);
              $('#taxReport').append(taxReportHtml);
            }

            // $('#taxReport').append(taxReportHtml);
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
      });
    }


    // 인쇄
    $scope.print = function () {
      // create document
      var doc  = new wijmo.PrintDocument({
        title: '세금계산서'
      });
      // add content to it
      var view = document.querySelector('#taxReport');
      doc.append(view);

      // and print it
      doc.print();
    };

  }]);

</script>
<%--<script type="text/javascript" src="/resource/solbipos/js/iostock/order/dstmn/dstmnDtl.js?ver=20181224.01" charset="utf-8"></script>--%>
