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
          <!-- 
          <div class="wizWrap" ng-app="app" ng-controller="appCtrl">
            this is the chart
            <wj-flex-chart items-source="data" binding-x="country" control="gradientBasicChart"
                           style="width:100%; height:240px;">
              <wj-flex-chart-series binding="sales" ng-attr-style="chartProps.gradientPredefinedColor"></wj-flex-chart-series>
            </wj-flex-chart>
          </div>
           -->
          
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
            <!-- 
            <div ng-app="app" ng-controller="appCtrl">
              this is the chart
              <wj-flex-chart items-source="data2" binding-x="country" control="gradientBasicChart"
                           style="width:100%; height:200px;">
                <wj-flex-chart-series binding="sales" ng-attr-style="chartProps.gradientPredefinedColor"></wj-flex-chart-series>
              </wj-flex-chart>
            </div>
             -->
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

// chart

// 차트1
// 앱 모듈 선언
/* 
var app = angular.module('app', ['wj']);
console.log('app : '+ JSON.stringify(app));

// 앱 컨트롤러
app.controller('appCtrl', function appCtrl($scope) {
  
 // 랜덤 데이터 생성
 var countries = 'US,Germany,UK,Japan,Italy,Greece'.split(','),
     data = [], data2 = [];
 for (var i = 0; i < countries.length; i++) {
     data.push({
         country: countries[i],
         downloads: Math.round(Math.random() * 20000),
         sales: Math.random() * 10000,
         expenses: Math.random() * 5000
     });
     data2.push({
       country: countries[i],
       downloads: Math.round(Math.random() * 20000),
       sales: Math.random() * 10000,
       expenses: Math.random() * 5000
   });
 }

 // scope.data 에 데이터 추가
 $scope.data = data;
 $scope.data2 = data;
 
 console.log('$scope.data : '+ JSON.stringify( $scope.data));
 console.log('$scope.data2 : '+ JSON.stringify( $scope.data));
 
 //add chart properties to scope
 $scope.chartProps = {
     gradientPredefinedColor: {fill:'l(0,0,1,1)#abd800-#5c7e00'}
 };
});
 */
/* 
//차트2
var app2 = angular.module('app', ['wj']);

console.log('app2 : '+ JSON.stringify(app2));

//앱 컨트롤러
app2.controller('appCtrl', function appCtrl($scope) {

//랜덤 데이터 생성
var countries2 = 'US2,Germany2,UK2,Japan2,Ital2y,Greece2'.split(','),
   data2 = [];
for (var i = 0; i < countries2.length; i++) {
 data2.push({
     country: countries2[i],
     downloads: Math.round(Math.random() * 20000),
     sales: Math.random() * 10000,
     expenses: Math.random() * 5000
 });
}
 // scope.data 에 데이터 추가
 $scope.data = data2;
 
 console.log('$scope.data 2 : '+ JSON.stringify( $scope.data));
 
 //add chart properties to scope
 $scope.chartProps = {
   gradientPredefinedColor: {fill:'l(0,0,1,1)#abd800-#5c7e00'}
 };
});
   */
</script>



