<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--right-->
<div class="contents">
    <!--메인컨텐츠-->
    <div class="mainCon">
        <!--오늘의 매출건수-->
        <div class="w50 fl br bb stat_purple2">
            <h2>오늘의 매출건수 <span>424</span></h2>
            <div>
                <p><span>신용카드</span><span>210</span></p>
                <p><span>현금</span><span>100</span></p>
                <p><span>현금영수증</span><span>60</span></p>
                <p><span>기타</span><span>54</span></p>
            </div>    
        </div>
        <!--//오늘의 매출건수-->
        
        <!--오늘의 매출금액-->
        <div class="w50 fl bb stat_sky2">
            <h2>오늘의 매출금액 <span>30,126,900</span></h2>
            <div>
                <p><span>신용카드</span><span>12,006,000</span></p>
                <p><span>현금</span><span>10,010,400</span></p>
                <p><span>현금영수증</span><span>5,070,500</span></p>
                <p><span>기타</span><span>3,040,000</span></p>
            </div>    
        </div>
        <!--//오늘의 매출금액-->
        
        <!--매출현황-->
        <div class="w70 fl br bb graph">
            <h2>매출현황
                <div class="searchBox">
                    <span id="dateText1"></span>
                    <span>
                        <select id="dateSelect1"></select>
                    </span>
                </div>
            </h2>
            
            <!-- wijmo 차트 -->
            <div class="wizWrap" id="chart1" style="width:100%; height:240px;"></div>
            <!--// wijmo 차트 -->
            
            <!-- chart 에 필요한 요소 -->
            <select id="chartType" style="display:none;"></select>
            <select id="chartAnimationMode" style="display:none;"></select>
            <select id="chartEasing" style="display:none;"></select>
            <input id="chartDuration" value="0" style="display:none"/>
            <select id="chartAddMenu" style="display:none;"></select>
            <select id="chartRemoveMenu" style="display:none;"></select>
            <!--// chart 에 필요한 요소 -->
            
        </div>
        <!--//매출현황-->
        
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
                       <span>최고 <em>13°C</em><br />최저 <em>5°C</em>
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
        
        <!--상품매출 TOP 10-->
        <div class="w70 fl br bb graph">
            <h2>상품매출 TOP 10
                <div class="searchBox">
                    <span id="dateText2"></span>
                    <span>
                        <select id="dateSelect2"></select>
                    </span>
                </div>
            </h2>
            
            <!-- wijmo 차트 -->
            <div class="wizWrap" id="chart2" style="width:100%; height:240px;"></div>
            <!-- // wijmo 차트 -->
        </div>
        <!--//상품매출 TOP 10-->
        
        <!--공지사항-->
        <div class="w30 fl bb notice">
            <h2>공지사항</h2>  
            <ul id="notice"></ul> 
        </div>
        <!--//공지사항-->
    </div>
    <!--//메인컨텐츠-->
</div>
<!--//right-->

<script>

//그래프 날짜 select box
var dateSelList1 = ${dateSelList1};
var dateSelList2 = ${dateSelList2};

var selOptionHtml1 = "";
var selOptionHtml2 = "";

if(dateSelList1.length > 0){
  for(i in dateSelList1){
    if(i==0){
      $("#dateText1").text("기간 : " + dateSelList1[i].date);
    }
    selOptionHtml1 += "<option value=\""+dateSelList1[i].date+"\">"+dateSelList1[i].name+"</option>";
  }
  $("#dateSelect1").html(selOptionHtml1);
}

if(dateSelList2.length > 0){
  for(j in dateSelList2){
    if(j==0){
      $("#dateText2").text("기간 : " + dateSelList2[j].date);
    }
    selOptionHtml2 += "<option value=\""+dateSelList2[j].date+"\">"+dateSelList2[j].name+"</option>";
  }
  $("#dateSelect2").html(selOptionHtml2);
}

$("#dateSelect1").change(function(){
  $("#dateText1").text("기간 : " + $(this).val());
});
$("#dateSelect2").change(function(){
  $("#dateText2").text("기간 : " + $(this).val());
});

//공지사항
var noticeList = ${noticeList};
var noticeHtml = "";
if(noticeList.length > 0){
  for(i in noticeList){
    noticeHtml += "<li>";
    noticeHtml += "<a href=\"#\">"+ noticeList[i].content + "</a>";
    noticeHtml += "<span>" + dateUtilObj.formatDate(noticeList[i].regDt, "yyyy.MM.dd")+ "</span>";
    noticeHtml += "</li>";    
  }
  $("#notice").append(noticeHtml);
}

//랜덤 데이터 생성
function getData(numCount) {
 var data = new wijmo.collections.ObservableArray();
 //var data = [];

 for (var i = 0; i < numCount; i++) {
     data.push(getRandomData('M' + getRandomValue(1000)));
 }
// console.log('data : '+JSON.stringify(data));
 return data;
}

function getRandomData(idx) {
 return {
     //x: getRandomValue(100),
     x: idx,
     y0: getRandomValue(200)
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
  var chart2 = new wijmo.chart.FlexChart('#chart2');
  
  // 위 차트
  chart1.beginUpdate();
  chart1.chartType = wijmo.chart.ChartType.Column;
  chart1.itemsSource = getData(flexChartPoints); // 여기에 받아온 데이터 넣기
  chart1.bindingX = 'x';
  chart1.palette = wijmo.chart.Palettes['organic'];

  for (var i = 0; i < 1; i++) {
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
  
  // 아래 차트
  chart2.beginUpdate();
  chart2.chartType = wijmo.chart.ChartType.Column;
  chart2.itemsSource = getData(flexChartPoints); // 여기에 받아온 데이터 넣기
  chart2.bindingX = 'x';
  chart2.palette = wijmo.chart.Palettes['superhero'];
  
  //create data series
  for (var i = 0; i < 1; i++) {
      var series = new wijmo.chart.Series();
      series.binding = 'y' + i;
      chart2.series.push(series);
  }
  chart2.endUpdate();
  
  var chartAnimation2 = new wijmo.chart.animation.ChartAnimation(chart2, {
    animationMode: wijmo.chart.animation.AnimationMode.All,
    easing: wijmo.chart.animation.Easing.Swing,
    duration: 400
  });

  chartType.selectedValue = 'Column';
  chartAnimationMode.selectedValue = 'All';
  chartEasing.selectedValue = 'Swing';
  chartDuration.value = 400;
  chartDuration.min = 200;
  chartDuration.max = 5000;
  chartDuration.step = 200;
  chartDuration.format = 'n0';
  
  
});



</script>
