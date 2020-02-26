<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.tonyAdd {position:absolute; top:0; left:-220px; bottom:0; right:220px; width:220px; height:100%; z-index:99; background-color:#2e333f}

.tonyMainlogo {position:fixed; top:0; width:200px; height:49px; z-index:100; background-color:#2e333f}
.tonyMainlogo span {display:block; text-align:center}

.tonySnb {position:fixed; top:101px; width:220px; z-index:100; background-color:#2e333f}
.tonySnb ul {padding:20px} 
.tonySnb ul li {}
.tonySnb ul li a {display:block; width:100%; height:45px; font-size:14px; }
.tonySnb ul li a:hover,.tonySnb ul li a:active {background:url(/resource/solbipos/css/img/menu_bullet.png) 170px 12px no-repeat; background-size:6px; color:#fff}
.tonySnb ul li a .ico {display:inline-block; position:relative; top:6px; width:20px; height:22px; margin-right:15px; background:url(/resource/solbipos/css/img/menu.png) 0 0 no-repeat}
.tonySnb ul li a.menu1 .ico {background-position:0 0}
.tonySnb ul li a.menu2 .ico {background-position:-60px 0}
.tonySnb ul li a.menu3 .ico {background-position:-120px 0}
.tonySnb ul li a.menu4 .ico {background-position:-160px 0}
.tonySnb ul li a.menu5 .ico {background-position:-100px 0}
.tonySnb ul li a.menu6 .ico {background-position:-80px 0}
.tonySnb ul li a.menu7 .ico {background-position:-140px 0}
.tonySnb ul li a.menu8 .ico {background-position:-200px 0}

.tonytab {position:fixed; top:50px; width:220px; border-top:1px solid #3e4452; border-bottom:1px solid #3e4452}
.tonytab span {display:block; float:left; width:110px; height:49px; line-height:49px; text-align:center; font-size:14px; color:#888fa4}
.tonytab span:first-child {border-right:1px solid #3e4452; background-color:#212633}
.tonytab span em {display:inline-block; width:40px; padding-bottom:16px; border-bottom:2px solid #fff; font-size:14px; color:#fff}

.menuControl {display:none}
.userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#ff9d39}
.fixedMenu.t2 {position:fixed; top:50px; left:220px; right:0; z-index:97; box-shadow:0 0 0 0 #ff9d39}
.fixedMenu.t2 ul li a {padding:0 35px 0 10px !important}
.fixedMenu.t2 ul li a.btn_close {padding-right:0 !important; top:16px !important; right:10px !important}

.wj-gridWrap.t2 {height:370px; overflow-y: hidden; position:relative;}

.s-bg {position:absolute; top:34px; right:0; left:0; bottom:0; width:100%; height:370px; background-color:#fff; z-index:10}
.s-bg p {text-align:center; margin-top:150px}

</style>

		<div class="subCon" ng-controller="spTonyCtrl">

			<div class="tonyAdd">
				<a href="#" class="tonyMainlogo">
					<span><img src="/resource/solbipos/css/img/test/tony_main_logo.png" alt="" /></span>
				</a>

				<div class="tonytab">
					<span><em>전체</em></span>
					<span>즐겨찾기</span>
				</div>

				<div class="tonySnb">
					<ul>
						<li><a href="#" class="menu1"><i class="ico"></i>운영관리</a></li>
						<li><a href="#" class="menu2"><i class="ico"></i>마스터관리</a></li>
						<li><a href="#" class="menu3"><i class="ico"></i>매출분석</a></li>
						<li><a href="#" class="menu4"><i class="ico"></i>수주관리</a></li>
						<li><a href="#" class="menu5"><i class="ico"></i>매입관리</a></li>
						<li><a href="#" class="menu6"><i class="ico"></i>재고관리</a></li>
						<li><a href="#" class="menu7"><i class="ico"></i>도우미관리</a></li>
						<li><a href="#" class="menu8"><i class="ico"></i>커뮤니케이션</a></li>
					</ul>
				</div>
			</div>

			<div class="userInfo t2">
				<a href="#" class="userId">
					<span>토니모리 관리자</span>
				</a>

				<div class="userLayer userLayer2" style="display:none">
					<p>
						<span>토니모리 관리자</span>
						<span> 
							<em>토니모리</em> 
						</span> 
						<span>개발중</span>
					</p>
					<ul>
						<li><a href="#">내 정보 변경</a></li>
						<li><a href="#" id="pwchg">비밀번호 변경</a></li>
						<li><a href="#">로그아웃</a></li>
					</ul>
				</div>
			</div>
			
			<!-- 고정메뉴 -->
			<div class="fixedMenu t2">
				<nav>
					<ul>
						<li>			
							<a href="/sample/tonymory/sample41.sb">메인화면</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample51.sb">제품등록</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample42.sb">제품별수주현황</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample43.sb">주문등록</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample44.sb">판촉마일리지 관리</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample45.sb">판촉마일리지 현황</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample46.sb">판촉마일리지 내역조회</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample47.sb">당일 매출 현황</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample48.sb">매출 업로드 등록</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample49.sb" class="on">매출 업로드 확정</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample50.sb">일별-제품판매실적조회</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
					</ul>
				</nav>
				<!-- 고정메뉴 있는경우 -->
			</div>
			<!-- //고정메뉴 -->
			
			<div class="searchBar flddUnfld">
				<a href="#" class="open fl">매출 업로드 확정</a>
				<!-- 조회 -->
				<div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
					<button class="btn_gray">확정</button>
					<button class="btn_blue">조회</button>
					<button class="btn_gray">삭제</button>
					<button class="btn_gray">엑셀</button>
					<button class="btn_gray">닫기</button>
				</div>
			</div>

			<div id="grid" class="w100">	
				<div class="wj-gridWrap mt10 t2">
				  <span class="s-bg">
					<p>조회된 데이터가 없습니다.</p>				
				  </span>

				  <div class="row">
					<wj-flex-grid 
						sticky-headers="true"
						items-source="data">

						<wj-flex-grid-column header="No" binding="" width="50"></wj-flex-grid-column>
						<wj-flex-grid-column header="매출일자" binding="" width="*"></wj-flex-grid-column>
						<wj-flex-grid-column header="매출구분" binding="" width="*" align="center"></wj-flex-grid-column>
						<wj-flex-grid-column header="매출유형" binding="" width="*" align="right"></wj-flex-grid-column>
						<wj-flex-grid-column header="등록건수" binding="" width="*" align="right"></wj-flex-grid-column>
						<wj-flex-grid-column header="판매수량" binding="" width="*"></wj-flex-grid-column>
						<wj-flex-grid-column header="매출총액" binding="" width="*"></wj-flex-grid-column>
						<wj-flex-grid-column header="매출금액" binding="" width="*"></wj-flex-grid-column>
						<wj-flex-grid-column header="할인금액" binding="" width="*"></wj-flex-grid-column>
						<wj-flex-grid-column header="업로드유형" binding="" width="*"></wj-flex-grid-column>
						<wj-flex-grid-column header="처리구분" binding="" width="*"></wj-flex-grid-column>
						
					</wj-flex-grid>
				  </div>
				</div>

				<p class="s16 bk lh30 mt30">상세내역</p>
				<div class="wj-gridWrap mt10 t2">
					<span class="s-bg">
					  <p>조회된 데이터가 없습니다.</p>				
					</span>
					<div class="row">
					  <wj-flex-grid 
						  sticky-headers="true"
						  items-source="data">
  
						  <wj-flex-grid-column header="No" binding="id" width="50"></wj-flex-grid-column>
						  <wj-flex-grid-column header="매출일자" binding="" width="*"></wj-flex-grid-column>
						  <wj-flex-grid-column header="매출구분" binding="" width="*" align="center"></wj-flex-grid-column>
						  <wj-flex-grid-column header="매출유형" binding="" width="*" align="right"></wj-flex-grid-column>
						  <wj-flex-grid-column header="등록건수" binding="" width="*" align="right"></wj-flex-grid-column>
						  <wj-flex-grid-column header="판매수량" binding="" width="*"></wj-flex-grid-column>
						  <wj-flex-grid-column header="매출총액" binding="" width="*"></wj-flex-grid-column>
						  <wj-flex-grid-column header="매출금액" binding="" width="*"></wj-flex-grid-column>
						  <wj-flex-grid-column header="할인금액" binding="" width="*"></wj-flex-grid-column>
						  <wj-flex-grid-column header="업로드유형" binding="" width="*"></wj-flex-grid-column>
						  <wj-flex-grid-column header="처리구분" binding="" width="*"></wj-flex-grid-column>
					  </wj-flex-grid>
					</div>
				</div>

			</div>


		</div>
		<!-- //subCon end-->

<script>
$(document).ready(function() {

	$(document).on('click', '.userId', function() {
		$("div.userLayer2").toggle();
	});
});

</script>
    
<script>
  // js 추가
  var app = agrid.getApp();

  /**********************************************************************
   *  토니모리 테스트 페이지
   **********************************************************************/
  app.controller('spTonyCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('spTonyCtrl', $scope, $http, true));

	$scope.combobox1 = ['선택', '구분1', '구분2', '구분3'];

	// generate some random data
    var txt1 = '(2호선당산역2호점),(5호선마포역점),(5호선을지로4가역점),(6호선새절역점),(6호선응암역점)'.split(','),
    	txt2 = '정상,정상,정상,에러'.split(','),
		txt3 = '주문사용,미사용,3월 판촉적립'.split(','),
		txt4 = '버블트리오렌지바이탈바디클렌저,퍼퓸드뮤즈바디클렌저스파클링,바디위드여성청결제,펴퓸드바디그레이스크림샤워3,퍼퓸드바디클래식샤워젤3'.split(','),
		txt5 = 'C0010090,C0010220,C0011050,C0010020,C0010070,C0021090,C0030020'.split(','),

        data = [];
    for (var i = 0; i < 5; i++) {
        data.push({
            id: i,
            str1: txt1[i % txt1.length],
            str2: txt2[i % txt2.length],
            str3: txt3[i % txt3.length],
            str4: txt4[i % txt4.length],
            str5: txt5[i % txt5.length],
            date: new Date(2019, i % 12, i % 28),
            amount: Math.random() * 1000,
            num: Math.random() * 100,
            chk: i % 0 == 0
        });
    }

    // add data array to scope
	$scope.data = data;

  }

  ]);
</script>