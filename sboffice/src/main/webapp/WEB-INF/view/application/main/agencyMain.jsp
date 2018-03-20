<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="contents">
    <!--메인컨텐츠-->
    <div class="mainCon">
        <!--총 매장수-->
        <div class="w50 fl br bb stat_purple2">
            <h2>총 매장수 <span>16,325</span></h2>
            <div>
                <p><span>오픈</span><span>8,126</span></p>
                <p><span>폐점</span><span>7,978</span></p>
            </div>    
        </div>
        <!--//총 매장수-->
        
        <!--총 포스수-->
        <div class="w50 fl bb stat_sky2">
            <h2>총 포스수 <span>5,517</span></h2>
            <div>
                <p><span>오픈</span><span>4,526</span></p>
                <p><span>폐점</span><span>517</span></p>
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
        
        <!--주간 설치현황-->
        <div class="w70 fl br bb graph">
            <h2>주간 설치현황<div class="square"><span class="blue">신규</span><span class="sky">재설치</span></div></h2>
            <div class="wizWrap" id="chart2" style="width:100%; height:255px;"></div>
        </div>
        <!--//주간 설치현황-->
        
        <!--공지사항-->
        <div class="w30 fl bb notice">
            <h2>공지사항</h2>  
            <ul>
                <li><a href="#">KCP NetPOS 셋업파일 설치관련 공지사항</a><span>2017.12.04</span></li>
                <li><a href="#">여신금융협회 보안인증 거친 IC카드 단말기 설치건</a><span>2017.12.01</span></li>
                <li><a href="#">가맹점 내 필요 보험 권유 및 유의 안내 공문</a><span>2017.11.26</span></li>
            </ul> 
        </div>
        <!--//공지사항-->
        
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
                        <td>외식</td>
                        <td class="bk">거북솥삼겹살</td>
                        <td>5,056</td>
                    </tr>
                    <tr>
                        <th>2</th>
                        <td>커피저문점</td>
                        <td class="bk">스타벅스 코리아</td>
                        <td>3,572</td>
                    </tr>
                    <tr>
                        <th>3</th>
                        <td>실내스크린골프점</td>
                        <td class="bk">골프존파크 논현 토너먼트센터</td>
                        <td>1,200</td>
                    </tr>
                    <tr>
                        <th>4</th>
                        <td>펜션.게스트하우스</td>
                        <td class="bk">강원도 따뜻하고 행복한 HOUSE</td>
                        <td>960</td>
                    </tr>
                    <tr>
                        <th>5</th>
                        <td>스포츠시설</td>
                        <td class="bk">최고급 휘트니스 극동스포츠</td>
                        <td>876</td>
                    </tr>
                </tbody>
            </table> 
        </div>
        <!--//순위테이블-->
    </div>
    <!--//메인컨텐츠-->
</div>

<script>
//랜덤 데이터 생성
function getData(numCount) {
  var data = new wijmo.collections.ObservableArray();
  //var data = [];

  for (var i = 0; i < numCount; i++) {
      data.push(getRandomData('M' + getRandomValue(200)));
  }
  
  //console.log(JSON.stringify(data));
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

//flexChart
var flexChartPoints = 10;

$(document).ready(function(){
  var chart1 = new wijmo.chart.FlexChart('#chart1');
  
  //flex chart
  chart1.beginUpdate();
  chart1.chartType = wijmo.chart.ChartType.Line;
  chart1.itemsSource = getData(flexChartPoints); // 여기에 받아온 데이터 넣기
  chart1.bindingX = 'x';
  
  //create data series
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

  // 아래 차트
  var chart2 = new wijmo.chart.FlexChart('#chart2');
  chart2.beginUpdate();
  chart2.chartType = wijmo.chart.ChartType.Column;
  chart2.itemsSource = getData(flexChartPoints); // 여기에 받아온 데이터 넣기

  chart2.chartType = parseInt(0);
  chart2.stacking = parseInt(1);
  chart2.rotated = false;

  for (var i = 0; i < 2; i++) {
      var series = new wijmo.chart.Series();
      series.binding = 'y' + i;
      chart2.series.push(series);
  }
  chart2.endUpdate();
  
});


</script>



