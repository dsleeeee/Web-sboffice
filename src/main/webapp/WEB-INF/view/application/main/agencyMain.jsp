<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--right-->
<div class="contents" ng-controller="agencyMainCtrl">
    <!--메인컨텐츠-->
    <div class="mainCon">
        <!--총 매장수-->
        <div class="w50 fl br bb stat_purple2">
            <h2>총 매장수 <span>0</span></h2>
            <div>
                <p><span>오픈</span><span>0</span></p>
                <p><span>폐점</span><span>0</span></p>
            </div>
        </div>
        <!--//총 매장수-->
        
        <!--총 포스수-->
        <div class="w50 fl bb stat_sky2">
            <h2>총 포스수 <span>0</span></h2>
            <div>
                <p><span>오픈</span><span>0</span></p>
                <p><span>폐점</span><span>0</span></p>
            </div>
        </div>
        <!--//총 포스수-->
        
        <!--주간 매출-->
        <div class="w70 fl br bb graph">
            <h2>주간 매출 (매장수/포스수)<div class="circle"><span class="blue">매장수</span><span class="sky">포스수</span></div></h2>
            <div class="wizWrap" id="chart1" style="width:100%; height:255px;"></div>
        </div>
        <!--//주간 매출-->

        <!--공지사항-->
        <div class="w30 fl bb notice">
            <h2>공지사항</h2>
            <ul>
                <c:forEach var="item" items="${noticeList}">
                    <li><a href="#">${item.content}</a><span>${item.regDt}</span></li>
                </c:forEach>
            </ul>
        </div>
        <!--//공지사항-->

        <!--주간 설치현황-->
        <div class="w70 fl br bb graph">
            <h2>주간 설치현황<div class="square"><span class="blue">신규</span><span class="sky">재설치</span></div></h2>
            <div class="wizWrap" id="chart2" style="width:100%; height:255px;"></div>
        </div>
        <!--//주간 설치현황-->

        <!--날씨-->
        <div class="w30 fl bb weather">
            <%--<div class="today">--%>
                <%--<h2 class="hidden">날씨</h2>--%>
                <%--<p class="date">--%>
                    <%--<span>서울시</span>--%>
                    <%--<span><em>11월 26일</em> <em>일요일</em></span>--%>
                <%--</p>--%>
                <%--<a href="#">날씨더보기</a>--%>
                <%--<div class="temperatures">--%>
                    <%--<p>--%>
                        <%--<!--파란색 날씨아이콘 : weIc01~14까지-->--%>
                        <%--<em class="weIc02"></em>--%>
                        <%--<span>--%>
                           <%--최고 <em>13°C</em><br />--%>
                           <%--최저 <em>5°C</em>--%>
                       <%--</span>--%>
                    <%--</p>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<ul>--%>
                <%--<li>--%>
                    <%--<!--검은색 날씨아이콘 : weIc01~14까지-->--%>
                    <%--<span class="weIc11"></span>--%>
                    <%--<span class="day">월</span>--%>
                    <%--<span><em>5°C</em> / <em>13°C</em></span>--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<!--검은색 날씨아이콘 : weIc01~14까지-->--%>
                    <%--<span class="weIc08"></span>--%>
                    <%--<span class="day">화</span>--%>
                    <%--<span><em>-3°C</em> / <em>10°C</em></span>--%>
                <%--</li>--%>
            <%--</ul>--%>
        </div>
        <!--//날씨-->
        
        <!--순위테이블-->
        <div class="w100 fl mainTbl">
            <h2>주간 매출 상위 가맹점</h2>  
            <table>
                <colgroup>
                    <col class="w10" />
                    <col class="w30" />
                    <col class="w35" />
                    <col class="w25" />
                </colgroup>
                <thead>
                    <tr>
                        <th>순위</th>
                        <th>업종</th>
                        <th>가맹점명</th>
                        <th>매출건수</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>1</th>
                        <td>-</td>
                        <td class="bk">-</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <th>2</th>
                        <td>-</td>
                        <td class="bk">-</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <th>3</th>
                        <td>-</td>
                        <td class="bk">-</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <th>4</th>
                        <td>-</td>
                        <td class="bk">-</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <th>5</th>
                        <td>-</td>
                        <td class="bk">-</td>
                        <td>0</td>
                    </tr>
                </tbody>
            </table> 
        </div>
        <!--//순위테이블-->
    </div>
    <!--//메인컨텐츠-->
    <!--팝업-->
    <div id="divDimmed" class="fullDimmed" style="display: none;"></div>
    <div id="divPopup" class="layer" style="display: none;">
        <div class="layer_inner" style="position:absolute; left:50%; top:50%;  transform: translate(-50%, -50%); text-align: center;">
            <!--layerContent-->
            <div class="title" style="width:560px;">
                <a href="#" class="btn_close" ng-click="close()"></a>
                <div class="con">
                    <img src="/resource/solbipos/css/img/popUp/20210127.jpg" style="width:100%" alt="2021 상반기 프린터 할인행사" />
                </div>
                <div class="btnSet">
                    <span><a href="#" class="btn_blue" id="btnCloseToday" ng-click="closeToday()">오늘하루 열지않기</a></span>
                    <span><a href="#" class="btn_blue" id="btnClose" ng-click="close()"><s:message code="cmm.close" /></a></span>
                </div>
            </div>
        </div>
    </div>
    <!--팝업-->
</div>
<!--//right-->

<script>

<%-- 랜덤 데이터 생성 (추후 데이터 받아오면서 변경)--%>
function getData(numCount) {
  var data = new wijmo.collections.ObservableArray();
  //var data = [];
  for (var i = 0; i < numCount; i++) {
    //data.push(getRandomData('M' + getRandomValue(200)));
    data.push(getRandomData('11월 28일'));
  }
  return data;
}
function getRandomData(idx) {
  return {
      //x: getRandomValue(100),
      x: idx,
      y0: getRandomValue(200),
      y1: getRandomValue(400)
  };
}
function getRandomValue(max) {
  return Math.round(Math.random() * max);
}
function updateMenuHeader(menu, prefix, text) {
  menu.header = prefix + text;
}

<%-- wijmo flexChart --%>
// var flexChartPoints = 7;
var flexChartPoints = 0;

$(document).ready(function(){
  var chart1 = new wijmo.chart.FlexChart('#chart1');
  
  <%-- 주간매출 --%>
  chart1.beginUpdate();
  chart1.chartType = wijmo.chart.ChartType.Line;
  chart1.itemsSource = getData(flexChartPoints); // 여기에 받아온 데이터 넣기
  chart1.bindingX = 'x';
  chart1.palette = ['#93cbfc', '#90f0fc'];
  
  for (var i = 0; i < 2; i++) {
      var series = new wijmo.chart.Series();
      series.binding = 'y' + i;
      chart1.series.push(series);
  }
  chart1.endUpdate();

  var chartAnimation = new wijmo.chart.animation.ChartAnimation(chart1, {
      animationMode: wijmo.chart.animation.AnimationMode.All,
      easing: wijmo.chart.animation.Easing.Swing,
      duration: 400
  });

  <%--주간 설치현황--%>
  var chart2 = new wijmo.chart.FlexChart('#chart2');
  chart2.beginUpdate();
  chart2.chartType = wijmo.chart.ChartType.Column;
  chart2.itemsSource = getData(flexChartPoints); // 여기에 받아온 데이터 넣기
  chart2.bindingX = 'x';
  
  chart2.chartType = parseInt(0);
  chart2.stacking = parseInt(1);
  chart2.rotated = false;
  chart2.palette = ['#93cbfc', '#90f0fc'];

  for (var i = 0; i < 2; i++) {
      var series = new wijmo.chart.Series();
      series.binding = 'y' + i;
      chart2.series.push(series);
  }
  chart2.endUpdate();
});

<%-- angularJs --%>
app.controller('agencyMainCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('agencyMainCtrl', $scope, $http, true));

    // 팝업 게시 기간
    var startDate = '20210201';
    var endDate = '20210331';

    // 오늘 날짜
    var date = new Date();
    var year = new String(date.getFullYear());
    var month = new String(date.getMonth()+1);
    var day = new String(date.getDate());

    // 한자리수일 경우 0을 채워준다.
    if(month.length == 1){
        month = "0" + month;
    }
    if(day.length == 1){
        day = "0" + day;
    }
    var now = year + "" + month + "" + day;

    // 1. 쿠키 만들기
    function setCookie(name, value, expiredays) {
        var today = new Date();
        today.setDate(today.getDate() + expiredays);
        document.cookie = name + '=' + escape(value) + '; path=/; expires=' + today.toGMTString() + ';'
    }

    //2. 쿠키 가져오기
    function getCookie(name){
        var cName = name + "=";
        var x = 0;
        while ( x <= document.cookie.length )
        {
            var y = (x+cName.length);
            if ( document.cookie.substring( x, y ) == cName )
            {
                if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                    endOfCookie = document.cookie.length;
                return unescape( document.cookie.substring( y, endOfCookie ) );
            }
            x = document.cookie.indexOf( " ", x ) + 1;
            if ( x == 0 )
                break;
        }
        return "";
    }

    //alert(getCookie("notToday"));

    // 날짜 비교하여 팝업 띄우기
    if(Number(now) >= Number(startDate)){
        if(Number(endDate) >= Number(now)){
            if(getCookie("notToday")!="Y") {
                $("#divDimmed").css('display', 'block');
                $("#divPopup").css('display', 'block');
            }
        }
    }

    // 오늘하루 열지않기
    $scope.closeToday = function(){
        setCookie('notToday','Y', 1);
        $("#divDimmed").css('display', 'none');
        $("#divPopup").css('display', 'none');
    }

    // 닫기
    $scope.close = function(){
     $("#divDimmed").css('display', 'none');
     $("#divPopup").css('display', 'none');
    }

}]);


</script>