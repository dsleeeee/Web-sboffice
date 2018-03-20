<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--right-->
<div class="contents">
    <!--메인컨텐츠-->
    <div class="mainCon">
        <!--총 매장수-->
        <div class="w33 fl br bb stat_purple3">
            <h2>총 매장수 <span>16,325</span></h2>    
        </div>
        <!--//총 매장수-->
        
        <!--주간 신규 오픈매장-->
        <div class="w33 fl br bb stat_sky3">
            <h2>총 포스수 <span>16,325</span></h2>    
        </div>
        <!--//주간 신규 오픈매장-->
        
        <!--주간 폐점매장-->
        <div class="w33 fl bb stat_blue3">
            <h2>주간 폐점매장 <span>1</span></h2>    
        </div>
        <!--//주간 폐점매장-->
        
        <!--매출현황-->
        <div class="w100 fl bb graph">
          <h2>매출현황
            <div class="searchBox">
              <span id="dateText1"></span>
              <span>
                <select id="dateSelect1"></select>
              </span>
             </div>
          </h2>
          
          <!-- wijmo 차트 -->
          <!-- binding 컬럼 변경 필요 -->
          <!-- 차트 색상 변경 필요 -->
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
        
        <!--매출 상위 상품-->
        <div class="w100 fl bb graph">
            <h2>매출 상위 상품
                <div class="searchBox">
                    <span id="dateText2"></span>
                    <span>
                        <select id="dateSelect2"></select>
                    </span>
                </div>
            </h2>
            <!-- wijmo 차트 -->
            <div class="wizWrap" id="chart2" style="width:100%; height:240px;">
              <!-- 
              <wj-flex-chart items-source="data2" binding-x="country" control="gradientBasicChart" style="width:100%; height:200px;">
                <wj-flex-chart-series binding="sales" ng-attr-style="chartProps.gradientPredefinedColor"></wj-flex-chart-series>
              </wj-flex-chart>
               -->
            </div>
        </div>
        <!--//매출 상위 상품-->
        
        <!--순위테이블-->
        <div class="w100 fl bb mainTbl">
            <h2>매출 상위 가맹점
                <div class="searchBox">
                    <span id="dateText3"></span>
                    <span>
                        <select id="dateSelect3"></select>
                    </span>
                </div>
            </h2>  
            <table class="mt10">
                <colgroup>
                    <col class="w7" />
                    <col class="w28" />
                    <col class="w15" />
                    <col class="w7" />
                    <col class="w28" />
                    <col class="w15" />   
                </colgroup>
                <thead>
                    <tr>
                        <th>순위</th>
                        <th>매장명</th>
                        <th>매출액</th>
                        <th>순위</th>
                        <th>매장명</th>
                        <th>매출액</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>1</th>
                        <td>고봉김밥(양장점)</td>
                        <td>314,000</td>
                        <th>6</th>
                        <td>고봉김밥(오치점)</td>
                        <td>314,000</td>
                    </tr>
                    <tr>
                        <th>2</th>
                        <td>고봉김밥(양장점)</td>
                        <td>314,000</td>
                        <th>7</th>
                        <td>고봉김밥(오치점)</td>
                        <td>314,000</td>
                    </tr>
                    <tr>
                        <th>3</th>
                        <td>고봉김밥(제니스점)</td>
                        <td>313,000</td>
                        <th>8</th>
                        <td>고봉김밥(광안점)</td>
                        <td>313,000</td>
                    </tr>
                    <tr>
                        <th>4</th>
                        <td>고봉김밥(명륜아이파트점)</td>
                        <td>311,000</td>
                        <th>9</th>
                        <td>고봉김밥(좌동점)</td>
                        <td>311,000</td>
                    </tr>
                    <tr>
                        <th>5</th>
                        <td>고봉김밥(부산대역점)</td>
                        <td>310,000</td>
                        <th>10</th>
                        <td>고봉김밥(부산역점)</td>
                        <td>310,000</td>
                    </tr>
                </tbody>
            </table> 
        </div>
        <!--//순위테이블-->
        
        <!--수발주 정보/회원현황-->
        <div class="w40 fl">
            <!--수발주 정보-->
            <div class="w100 bb bgTxt">
                <h2>수발주 정보</h2>
                <ul>
                    <li>입고미확정<span>100</span></li>
                    <li>반품미확정<span>10</span></li>
                </ul>    
            </div>
            <!--//수발주 정보-->
            <!--회원현황-->
            <div class="w100 bgTxt">
                <h2>회원현황</h2>
                <ul>
                    <li>전체회원수<span>1,670</span></li>
                    <li>정보미입력 회원수<span>52</span></li>
                </ul>    
            </div>
            <!--//회원현황-->
        </div>
        <!--//수발주 정보/회원현황-->
        
        <!--공지사항-->
        <div class="w30 fl bl notice">
            <h2>공지사항</h2>  
            <ul id="notice"></ul> 
        </div>
        <!--//공지사항-->
        
        <!--날씨-->
        <div class="w30 fl bl weather">
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
        
    </div>
    <!--//메인컨텐츠-->
</div>
<!--//right-->

<script>

// 그래프 날짜 select box
var dateSelList1 = ${dateSelList1};
var dateSelList2 = ${dateSelList2};

var selOptionHtml1 = "";
var selOptionHtml2 = "";
var selOptionHtml3 = "";

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
      $("#dateText3").text("기간 : " + dateSelList2[j].date);
    }
    
    selOptionHtml2 += "<option value=\""+dateSelList2[j].date+"\">"+dateSelList2[j].name+"</option>";
    selOptionHtml3 += "<option value=\""+dateSelList2[j].date+"\">"+dateSelList2[j].name+"</option>";
  }
  $("#dateSelect2").html(selOptionHtml2);
  $("#dateSelect3").html(selOptionHtml3);
}

$("#dateSelect1").change(function(){
  $("#dateText1").text("기간 : " + $(this).val());
});
$("#dateSelect2").change(function(){
  $("#dateText2").text("기간 : " + $(this).val());
});
$("#dateSelect3").change(function(){
  $("#dateText3").text("기간 : " + $(this).val());
});

// 공지사항
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
    //data.push(getRandomData('ramdom ' + getRandomValue(1000)));
    data.push(getRandomData('11월 28일'));  // 데이터 넣을때 날짜 데이터 형식 맞춰 넣기
  }
  console.log('data : '+JSON.stringify(data));
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
var flexChartPoints = 7;

$(document).ready(function(){
  var chart1 = new wijmo.chart.FlexChart('#chart1');
  var chart2 = new wijmo.chart.FlexChart('#chart2');
  
  // 위 차트
  chart1.beginUpdate();
  chart1.chartType = wijmo.chart.ChartType.Column;
  chart1.itemsSource = getData(flexChartPoints); // 여기에 받아온 데이터 넣기
  
  //console.log('itemSource : '+ JSON.stringify(getData(flexChartPoints)));
  
  
  chart1.bindingX = 'x';
  chart1.palette = ['#93cbfc'];
  //chart1.dataLabel.content  = "{x}";
  
  chart1.labelAlign = "bottom";
  chart1.labelAngle = 0;
  
  
  
  
  
  //create data series
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
  chart2.fotmat = 'p1';
  chart2.palette = ['#90f0fc'];
  
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



