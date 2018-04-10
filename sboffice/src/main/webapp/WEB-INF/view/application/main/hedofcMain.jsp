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
            <h2>
                매출현황
                <div class="searchBox">
                    <span id="dateText1"></span>
                    <span>
                        <select id="dateSelect1">
                          <c:forEach var="item" items="${dateSelList1}">
                            <option value="${item.dateStr}">${item.name}</option>
                          </c:forEach>
                        </select>
                    </span>
                </div>
            </h2>
            <div class="wizWrap" id="chart1" style="width:100%; height:240px;"></div>
            
            <%-- chart 에 필요한 요소 --%>
            <select id="chartType" style="display:none;"></select>
            <select id="chartAnimationMode" style="display:none;"></select>
            <select id="chartEasing" style="display:none;"></select>
            <input id="chartDuration" value="0" style="display:none"/>
            <select id="chartAddMenu" style="display:none;"></select>
            <select id="chartRemoveMenu" style="display:none;"></select>
            
        </div>
        <!--//매출현황-->
        
        <!--매출 상위 상품-->
        <div class="w100 fl bb graph">
            <h2>
                매출 상위 상품
                <div class="searchBox">
                    <span id="dateText2"></span>
                    <span>
                        <select id="dateSelect2">
                          <c:forEach var="item" items="${dateSelList2}">
                            <option value="${item.dateStr}">${item.name}</option>
                          </c:forEach>
                        </select>
                    </span>
                </div>
            </h2>
            <div class="wizWrap" id="chart2" style="width:100%; height:240px;"></div>
        </div>
        <!--//매출 상위 상품-->
        
        <!--순위테이블-->
        <div class="w100 fl bb mainTbl">
            <h2>
                매출 상위 가맹점
                <div class="searchBox">
                    <span id="dateText3"></span>
                    <span>
                        <select id="dateSelect3">
                          <c:forEach var="item" items="${dateSelList2}">
                            <option value="${item.dateStr}">${item.name}</option>
                          </c:forEach>
                        </select>
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
            <ul>
              <c:forEach var="item" items="${noticeList}">
                <li><a href="#">${item.content}</a><span>${item.regDt}</span></li>
              </c:forEach>
            </ul> 
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
        
    </div>
    <!--//메인컨텐츠-->
</div>
<!--//right-->

<script>

$(document).ready(function(){
  $("#dateText1").text("기간 : " + $("#dateSelect1").val());
  $("#dateText2").text("기간 : " + $("#dateSelect2").val());
});

<%-- 날짜 선택 변경 --%>
$("#dateSelect1").change(function(){
  $("#dateText1").text("기간 : " + $(this).val());
});
$("#dateSelect2").change(function(){
  $("#dateText2").text("기간 : " + $(this).val());
});
$("#dateSelect3").change(function(){
  $("#dateText3").text("기간 : " + $(this).val());
});

<%-- 랜덤 데이터 생성 (추후 데이터 받아오면서 변경)--%>
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

<%-- wijmo flexChart --%>
var flexChartPoints = 7;

$(document).ready(function(){
  var chart1 = new wijmo.chart.FlexChart('#chart1');
  var chart2 = new wijmo.chart.FlexChart('#chart2');

  <%-- 매출현황 그래프 --%>
  chart1.beginUpdate();
  chart1.chartType = wijmo.chart.ChartType.Column;
  chart1.itemsSource = getData(flexChartPoints); // 여기에 받아온 데이터 넣기
  chart1.bindingX = 'x';
  chart1.palette = ['#93cbfc'];
  chart1.labelAlign = "bottom";
  chart1.labelAngle = 0;

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

  <%-- 매출 상위 상품 그래프 --%>
  chart2.beginUpdate();
  chart2.chartType = wijmo.chart.ChartType.Column;
  chart2.itemsSource = getData(flexChartPoints); // 여기에 받아온 데이터 넣기
  chart2.bindingX = 'x';
  chart2.fotmat = 'p1';
  chart2.palette = ['#90f0fc'];

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

  <%-- 차트 타입 정의--%>
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
