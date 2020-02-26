<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ufn" uri="solbipos/function" %>

<style>
.sam-tbl {padding:40px}
.sam-tbl table {width:100%;}
.sam-tbl table thead th,.sam-tbl table tbody th,.sam-tbl table tbody td  {position:relative; font-size:13px; text-align:center; padding:3px; line-height:20px; border:1px solid #ddd}
.sam-tbl table thead th,.sam-tbl table tbody th {background-color:#f6f6f6; color:#222}
.sam-tbl table thead th {padding:5px}


</style>

<div class="sam-tbl">

	<p class="bk mb10">default button</p>
	<table>
		<colgroup>
			<col style="width:20%">
			<col style="width:30%">
			<col style="width:50%">
		</colgroup>
		<thead>
			<tr>
				<th>버튼명</th>
				<th>버튼</th>
				<th>사용 클래스명</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th>blue</th>
				<td><button type="button" class="btn_blue" id="" ng-click="">blue</button></td>
				<td>class="btn_blue"</td>
			</tr>
			<tr>
				<th>skyblue</th>
				<td><button type="button" class="btn_skyblue">skyblue</button></td>
				<td>class="btn_skyblue"</td>
			</tr>			
			<tr>
				<th>black</th>
				<td><button type="button" class="btn_blk">black</button></td>
				<td>class="btn_blk"</td>
			</tr>							
			<tr>
				<th>gray</th>
				<td><button type="button" class="btn_gray">gray</button></td>
				<td>class="btn_gray"</td>
			</tr>							
			<tr>
				<th>gray style 1</th>
				<td><a href="#" class="btn_grayS">gray</a></td>
				<td>class="btn_grayS"</td>
			</tr>							
			<tr>
				<th>gray style 2</th>
				<td><a href="#" class="btn_grayS2">gray2</button></td>
				<td>class="btn_grayS2"</td>
			</tr>							
			<tr>
				<th>yellow</th>
				<td><a href="#" class="btn_yellow">yellow</a></td>
				<td>class="btn_yellow"</td>
			</tr>							
			<tr>
				<th>green</th>
				<td><a href="#" class="btn_green">green</button></td>
				<td>class="btn_green"</td>
			</tr>												
		</tbody>
	</table>

	<p class="bk mb10 mt30">contents button</p>
	<table>
		<colgroup>
			<col style="width:20%">
			<col style="width:30%">
			<col style="width:50%">
		</colgroup>
		<thead>
			<tr>
				<th>버튼명</th>
				<th>버튼</th>
				<th>사용 클래스명</th>
			</tr>
		</thead>		
		<tbody>
			<tr>
				<th>search</th>
				<td><button class="btn_blue fr" ng-click="_broadcast('')"><s:message code="cmm.search" /></button></td>
				<td>class="btn_blue"</td>
			</tr>												
			<tr>
				<th>그리드 타이틀 및 페이지스케일 버튼</th>
				<td>
					<div class="oh sb-select">
						<span class="fr">
							<button class="btn_skyblue ml5" id="" ng-click="">버튼 1</button>
							<button class="btn_skyblue ml5" id="" ng-click="">버튼 2</button>	    
						</span>    
					 <div class="clearfix"></div>
					</div>				
				</td>
				<td>class="btn_skyblue"</td>
			</tr>												
			<tr>
				<th>updownSet</th>
				<td>
					<div class="updownSet">
						<button type="button" class="btn_up">UP</button>
						<button type="button" class="btn_down">Down</button>
						<button type="button" class="btn_skyblue">추가</button>
						<button type="button" class="btn_skyblue">삭제</button>
						<button type="button" class="btn_skyblue">저장</button>
					</div>
					</td>
				<td>class="updownSet" >> <br/>class="btn_up", class="btn_down", class="btn_skyblue"</td>
			</tr>
			<tr>
				<th>초기화/저장</th>
				<td>
            <div class="btn_int">
              <button class="btn_skyblue" id="btnInit"><s:message code="cmm.init"/></button>
              <button class="btn_skyblue" id="btnSave"><s:message code="cmm.save"/></button>
            </div>
				</td>
				<td>class="btn_int" >> <br/>class="btn_skyblue"</td>
			</tr>		
			<tr>
				<th>inner/layer button</th>
				<td>
            <a href="#" class="btn_grayS">주소검색</a>
            <a href="#" class="btn_grayS">예외관리</a>
				</td>
				<td>class="btn_grayS"</td>
			</tr>		
			<tr>
				<th>layer bottom button</th>
				<td>
					<div class="btnSet">
						<span><a href="#" class="btn_blue" id="btnReg">신규 등록</a></span>
						<span><a href="#" class="btn_blue" id="btnSave">저장</a></span>
						<span><a href="#" class="btn_blue" id="btnEdit">수정</a></span>
						<span><a href="#" class="btn_gray" id="btnClose">닫기</a></span>
					</div>
				</td>
				<td>class="btnSet" >> <br/>class="btn_blue", class="btn_gray"</td>
			</tr>		
			<tr>
				<th>paging</th>
				<td>
					<div class="pageNum">
						<ul>
							<li class="btn_previous"><a href="#"></a></li>
							<li><a href="#" class="pagenav on">1</a></li>
							<li><a href="#" class="pagenav">2</a></li>							
							<li class="btn_next"><a href="#"></a></li>
						</ul>
					</div>
					<br/>
					<div class="pageNum2">
						<ul>
							<li class="btn_previous"><a href="#"></a></li>
							<li><a href="#" class="pagenav on">1</a></li>
							<li><a href="#" class="pagenav">2</a></li>							
							<li class="btn_next"><a href="#"></a></li>
						</ul>
					</div>
					<br/>
					<div class="pageNum3">
						<ul>
							<li class="btn_previous"><a href="#"></a></li>
							<li><a href="#" class="pagenav on">1</a></li>
							<li><a href="#" class="pagenav">2</a></li>							
							<li class="btn_next"><a href="#"></a></li>
						</ul>
					</div>									
				</td>
				<td>class="pageNum",class="pageNum2",class="pageNum3" >> <br/>class="btn_previous", class="btn_next", class="pagenav"</td>
			</tr>
			<tr>
				<th>즐겨찾기관리</th>
				<td>
			    <div id="_faMenu" class="faMenu"">
			      <p class="btn_faManage"><a href="#">즐겨찾기 관리</a></p>
			    </div>
				</td>
				<td>class="faMenu" >> <br/>class="btn_faManage"</td>
			</tr>
			<tr>
				<th>메뉴이동</th>
				<td>
			    <div class="moveBtn">
			      <a href="#" class="mL" title="왼쪽으로 메뉴 이동"></a>
			      <a href="#" class="mR" title="오른쪽으로 메뉴 이동"></a>
			    </div>
				</td>
				<td>class="moveBtn" >> <br/>class="mL", class="mR"</td>
			</tr>											
			<tr>
				<th>btn_login</th>
				<td><button type="button" class="btn_login">login</button></td>
				<td>class="btn_login"</td>
			</tr>													
			<tr>
				<th>blue width 100%</th>
				<td><button type="button" class="btn_bluew100">blue width 100%</button></td>
				<td>class="btn_bluew100"</td>
			</tr>									
			<tr>
				<th>인증번호받기</th>
				<td>
      		<div class="Area_crtNum">
        		<button type="button" class="btn_crtNum" onClick="javascript:authNum();"><s:message code="login.pw.find.authnum.btn" /></button>
        	</div>
				</td>
				<td>class="Area_crtNum" >> <br/>class="btn_crtNum"</td>
			</tr>																
			<tr>
				<th>Layer close</th>
				<td>
            <div class="title">
              <a href="#" class="btn_close"></a>
            </div>
				</td>
				<td>class=""title"" >> <br/>class="btn_close"</td>
			</tr>
		</tbody>
	</table>
</div>

