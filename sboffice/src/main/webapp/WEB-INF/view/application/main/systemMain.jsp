<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--right-->
<div class="contents">
    <!--메인컨텐츠-->
    <div class="mainCon">
        <!--총 매장수-->
        <div class="w50 fl br bb stat_purple1">
            <h2>총 매장수 <span>16,325</span></h2>
            <div>
                <p class="w33 fl tc"><span>오픈</span><span>8,126</span></p>
                <p class="w33 fl tc"><span>폐점</span><span>7,978</span></p>
                <p class="w33 fl tc"><span>데모</span><span>221</span></p>
            </div>    
        </div>
        <!--//총 매장수-->
        
        <!--총 포스수-->
        <div class="w50 fl bb stat_sky1">
            <h2>총 포스수 <span>5,517</span></h2>
            <div>
                <p class="w33 fl tc"><span>오픈</span><span>8,126</span></p>
                <p class="w33 fl tc"><span>폐점</span><span>7,978</span></p>
                <p class="w33 fl tc"><span>데모</span><span>221</span></p>
            </div>    
        </div>
        <!--//총 포스수-->
        
        <!--주간 매출-->
        <div class="w70 fl br bb graph">
            <h2>주간 매출 (매장수/포스수)<div class="circle"><span class="blue">매장수</span><span class="sky">포스수</span></div></h2>
            <div class="wizWrap" id="chart1" style="width:100%; height:255px;"></div>
        </div>
        <!--//주간 매출-->
        
        <!--날씨-->
        <div class="w30 fl bb weather">
           <div class="today"> 
               <h2 class="hidden">날씨</h2>
               <p class="date">
                   <span>서울시</span>
                   <span><em>11월 26일</em> <em>일요일</em></span>
               </p>
               <a href="#">날씨더보기</a>
               <div class="temperatures">
                   <p>
                       <!--파란색 날씨아이콘 : weIc01~14까지-->
                       <em class="weIc02"></em>
                       <span>
                           최고 <em>13°C</em><br />
                           최저 <em>5°C</em>
                       </span>
                   </p> 
               </div>
           </div>
           <ul>
               <li>
                   <!--검은색 날씨아이콘 : weIc01~14까지-->
                   <span class="weIc11"></span>
                   <span class="day">월</span>
                   <span><em>5°C</em> / <em>13°C</em></span>
               </li>
               <li>
                   <!--검은색 날씨아이콘 : weIc01~14까지-->
                   <span class="weIc08"></span>
                   <span class="day">화</span>
                   <span><em>-3°C</em> / <em>10°C</em></span>
               </li>
           </ul> 
        </div>
        <!--//날씨-->
        
        <!--주간 POS 설치현황-->
        <div class="w70 fl br bb graph">
            <h2>주간 POS 설치현황<div class="square"><span class="blue">신규</span><span class="sky">재설치</span></div></h2>
            <div class="wizWrap" id="chart2" style="width:100%; height:255px;"></div>
        </div>
        <!--//주간 POS 설치현황-->
        
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
        
        <!--순위테이블-->
        <div class="w100 fl mainTbl">
            <h2>주간 POS 설치 상위 대리점</h2>  
            <table>
                <colgroup>
                    <col class="w10" />
                    <col class="w20" />
                    <col class="w30" />
                    <col class="w20" />
                    <col class="w20" />    
                </colgroup>
                <thead>
                    <tr>
                        <th>순위</th>
                        <th>구분</th>
                        <th>총판 / 대리점명</th>
                        <th>신규설치 매장수</th>
                        <th>폐점수</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>1</th>
                        <td>총판</td>
                        <td class="bk">서울정보통신</td>
                        <td>369</td>
                        <td>10</td>
                    </tr>
                    <tr>
                        <th>2</th>
                        <td>대리점</td>
                        <td class="bk">이노베이션</td>
                        <td>248</td>
                        <td>2</td>
                    </tr>
                    <tr>
                        <th>3</th>
                        <td>총판</td>
                        <td class="bk">구로정보통신</td>
                        <td>123</td>
                        <td>2</td>
                    </tr>
                    <tr>
                        <th>4</th>
                        <td>대리점</td>
                        <td class="bk">대림정보통신</td>
                        <td>85</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <th>5</th>
                        <td>총판</td>
                        <td class="bk">삼성정보통신</td>
                        <td>42</td>
                        <td>1</td>
                    </tr>
                </tbody>
            </table> 
        </div>
        <!--//순위테이블-->
    </div>
    <!--//메인컨텐츠-->
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

var flexChartPoints = 7;

$(document).ready(function(){
  
  <%-- 주간매출 --%>
  var chart1 = new wijmo.chart.FlexChart('#chart1');
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

  var chartAnimation1 = new wijmo.chart.animation.ChartAnimation(chart1, {
      animationMode: wijmo.chart.animation.AnimationMode.All,
      easing: wijmo.chart.animation.Easing.Swing,
      duration: 400
  });

  <%-- 주간 POS 설치현황--%>
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


</script>



