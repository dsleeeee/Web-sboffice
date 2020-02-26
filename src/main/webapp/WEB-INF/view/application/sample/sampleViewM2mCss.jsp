<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/> --%>

<style>
.sam-tbl {}
.sam-tbl table {width:100%}
.sam-tbl table thead th,.sam-tbl table tbody th,.sam-tbl table tbody td  {position:relative; font-size:13px; text-align:center; padding:3px 10px; line-height:20px; border:1px solid #ddd}
.sam-tbl table thead th,.sam-tbl table tbody th {background-color:#f6f6f6; color:#222}
.sam-tbl table thead th {padding:5px}
.sam-tbl .txt {margin:30px 0 10px; font-size:16px; font-weight:bold; color:#222}
.s12 {font-size:12px !important}
.s13 {font-size:13px !important}
.s14 {font-size:14px !important}
.s16 {font-size:16px !important}
.s18 {font-size:18px !important}
.sam-ul {}
.sam-ul:after {display:block; clear:both; content:''}
.sam-ul li {text-align:left}
.sam-ul li span {display:inline-block; text-align:center; font-size:12px; padding:5px; margin:5px; background-color:#eee; color:#222}
.sam-ul li.bt {margin-top:10px; border-top:1px solid #222}
.sam-ul li.bb {margin-bottom:10px; border-bottom:1px solid #222}
.sam-ul li.bd {border:1px solid #222}
.box span {display:inline-block; text-align:center; font-size:14px; padding:10px; margin:5px; background-color:#eee; color:#222}
</style>


<div class="subCon">

  
	<div class="sam-tbl">
	
		<p class="txt">font size</p>
	  <table>
	    <colgroup>
	      <col style="width:15%">	      	      	      	      	      	      
	      <col style="width:18%">	      	      	      	      	      	      
	      <col style="width:17%">	      	      	      	      	      	      
	      <col style="width:15%">	      	      	      	      	      	      
	      <col style="width:18%">	      	      	      	      	      	      
	      <col style="width:17%">	      	      	      	      	      	      
	    </colgroup>
	    <thead>
	    <tr>
	      <th>구분</th>
	      <th>클래스</th>
	      <th>사용 예</th>
	      <th>구분</th>
	      <th>클래스</th>
	      <th>사용 예</th>
	    </tr>
	    </thead>
	    <tbody>
	    <tr>
	      <th>12px</th>
	      <td>class="s12"</td>
	      <td class="s12">크기 12px</td>
	      <th>13px</th>
	      <td>class="s13"</td>
	      <td class="s13">크기 13px</td>
	    </tr>
	    <tr>
	      <th>14px</th>
	      <td>class="s14"</td>
	      <td class="s14">크기 14px</td>
	      <th>16px</th>
	      <td>class="s16"</td>
	      <td class="s16">크기 16px</td>
	    </tr>
	    <tr>
	      <th>18px</th>
	      <td>class="s18"</td>
	      <td class="s18">크기 18px</td>
	      <th></th>
	      <td></td>
	      <td></td>
	    </tr>
	    </tbody>
	  </table>		
	
		<p class="txt">text align</p>
	  <table>
	    <colgroup>
	      <col style="width:15%">	      	      	      	      	      	      
	      <col style="width:18%">	      	      	      	      	      	      
	      <col style="width:17%">	      	      	      	      	      	      
	      <col style="width:15%">	      	      	      	      	      	      
	      <col style="width:18%">	      	      	      	      	      	      
	      <col style="width:17%">	      	      	      	      	      	      
	    </colgroup>
	    <thead>
	    <tr>
	      <th>구분</th>
	      <th>클래스</th>
	      <th>사용 예</th>
	      <th>구분</th>
	      <th>클래스</th>
	      <th>사용 예</th>
	    </tr>
	    </thead>
	    <tbody>
	    <tr>
	      <th>left</th>
	      <td>class="tl"</td>
	      <td class="tl">왼쪽정렬</td>
	      <th>center</th>
	      <td>class="tc"</td>
	      <td class="tc">중앙정렬</td>
	    </tr>
	    <tr>
	      <th>right</th>
	      <td>class="tr"</td>
	      <td class="tr">오른쪽정렬</td>
	      <th></th>
	      <td></td>
	      <td></td>
	    </tr>
	    </tbody>
	  </table>
	 
		<p class="txt">text color</p>
	  <table>
	    <colgroup>
	      <col style="width:15%">	      	      	      	      	      	      
	      <col style="width:18%">	      	      	      	      	      	      
	      <col style="width:17%">	      	      	      	      	      	      
	      <col style="width:15%">	      	      	      	      	      	      
	      <col style="width:18%">	      	      	      	      	      	      
	      <col style="width:17%">	      	      	      	      	      	      
	    </colgroup>
	    <thead>
	    <tr>
	      <th>구분</th>
	      <th>클래스</th>
	      <th>사용 예</th>
	      <th>구분</th>
	      <th>클래스</th>
	      <th>사용 예</th>
	    </tr>
	    </thead>
	    <tbody>
	    <tr>
	      <th>black</th>
	      <td>class="bk"</td>
	      <td class="bk">검정색</td>
	      <th>gray</th>
	      <td>class="gr"</td>
	      <td class="gr">회색</td>
	    </tr>
	    <tr>
	      <th>red</th>
	      <td>class="red"</td>
	      <td class="red">빨간색</td>
	      <th>blue</th>
	      <td>class="blue"</td>
	      <td class="blue">파란색</td>
	    </tr>
	    </tbody>
	  </table>
	  	 
		<p class="txt">box position</p>
	  <table>
	    <colgroup>
	      <col style="width:15%">	      	      	      	      	      	      
	      <col style="width:15%">	      	      	      	      	      	      
	      <col style="width:55%">	      	      	      	      	      	          	      	      	      	      	      
	      <col style="width:15%">	      	      	      	      	      	          	      	      	      	      	      
	    </colgroup>
	    <thead>
	    <tr>
	      <th>구분</th>
	      <th>클래스</th>
	      <th>사용 예</th>
	      <th>사용 후</th>
	    </tr>
	    </thead>
	    <tbody>
	    <tr>
	      <th>left</th>
	      <td>class="fl"</td>
	      <td>
					<p class="box"><span class="fl">box position: left</span></p>
					<div class="clearfix"></div>
				</td>
				<td rowspan="4">태그가 끝난 다음 지점에<br/>&lt;div class="clearfix"&gt;&lt;/div&gt;<br/>태그 추가 삽입</td>
	    </tr>
	    <tr>
	      <th>right</th>
	      <td>class="fr"</td>
	      <td>
					<p class="box"><span class="fr">box position: right</span></p>
					<div class="clearfix"></div>
				</td>
	    </tr>
	    <tr>
	      <th>left &amp; right</th>
	      <td>class="fl", class="fr"</td>
	      <td>
					<div class="box">
						<span class="fl">box position: left</span>
						<span class="fr">box position: right</span>
					</div>
					<div class="clearfix"></div>
				</td>
	    </tr>
	    <tr>
	      <th>left &amp; right 2 ~</th>
	      <td>class="fl", class="fr"</td>
	      <td>
					<div class="box txtIn fl">
						<span class="fl">box position: left</span>
					</div>
					<div class="box txtIn fr">
						<span>right 1</span>
						<span>right 2</span>
					</div>					
					<div class="clearfix"></div>
				</td>
	    </tr>
	    </tbody>
	  </table>

		<p class="txt">box width %</p>
	  <table>
	    <colgroup>
	      <col style="width:15%">	      	      	      	      	      	      
	      <col style="width:20%">	      	      	      	      	      	      
	      <col style="width:65%">	      	      	      	      	      	          	      	      	      	      	      
	    </colgroup>
	    <thead>
	    <tr>
	      <th>구분</th>
	      <th>클래스</th>
	      <th>사용 예</th>
	    </tr>
	    </thead>
	    <tbody>
	    <tr>
	      <th>width</th>
	      <td>class="w5" ~ class="w100"<br/>5의 배수 5% ~ 100% 까지<br/>예외 타입: w4, w7, w8, w11, w28, w33</td>
	      <td>
					<ul class="sam-ul">
						<li><span class="w5">5%</span></li>
						<li><span class="w20">20%</span></li>
						<li><span class="w40">40%</span></li>
						<li><span class="w60">60%</span></li>
						<li><span class="w80">80%</span></li>
						<li><span class="w100">100%</span></li>
					</ul>
				</td>
	    </tr>
	    </tbody>
	  </table>

		<p class="txt">box height px</p>
	  <table>
	    <colgroup>
	      <col style="width:15%">	      	      	      	      	      	      
	      <col style="width:20%">	      	      	      	      	      	      
	      <col style="width:65%">	      	      	      	      	      	          	      	      	      	      	      
	    </colgroup>
	    <thead>
	    <tr>
	      <th>구분</th>
	      <th>클래스</th>
	      <th>사용 예</th>
	    </tr>
	    </thead>
	    <tbody>
	    <tr>
	      <th>height</th>
	      <td>class="h100" ~ class="h900"<br/>100px씩 증가 ~ 900px 까지</td>
	      <td>
					<ul class="sam-ul">
						<li class="fl mr30"><span class="h100">100px</span></li>
						<li class="fl mr30"><span class="h200">200px</span></li>
						<li class="fl"><span class="h300">300px</span></li>
					</ul>
				</td>
	    </tr>
	    </tbody>
	  </table>

		<p class="txt">box margin px</p>
	  <table>
	    <colgroup>
	      <col style="width:15%">	      	      	      	      	      	      
	      <col style="width:20%">	      	      	      	      	      	      
	      <col style="width:65%">	      	      	      	      	      	          	      	      	      	      	      
	    </colgroup>
	    <thead>
	    <tr>
	      <th>구분</th>
	      <th>클래스</th>
	      <th>사용 예</th>
	    </tr>
	    </thead>
	    <tbody>
	    <tr>
	      <th>top</th>
	      <td>class="mt10" ~ class="mt40" <br/>10px씩 증가 ~ 40px 까지</td>
	      <td class="tl">
					<ul class="sam-ul">
						<li class="fl mr10 bt"><p class="mt10"><span>top: 10px</span></p></li>
						<li class="fl mr10 bt"><p class="mt20"><span>top: 20px</span></p></li>
						<li class="fl mr10 bt"><p class="mt30"><span>top: 30px</span></p></li>
						<li class="fl bt"><p class="mt40"><span>top: 40px</span></p></li>
					</ul>
				</td>
	    </tr>
	    <tr>
	      <th>bottom</th>
	      <td>class="mb10" ~ class="mb40" <br/>10px씩 증가 ~ 40px 까지</td>
	      <td class="tl">
					<ul class="sam-ul">
						<li class="fl mr10 bb"><p class="mb10"><span>bottom: 10px</span></p></li>
						<li class="fl mr10 bb"><p class="mb20"><span>bottom: 20px</span></p></li>
						<li class="fl mr10 bb"><p class="mb30"><span>bottom: 30px</span></p></li>
						<li class="fl mr10 bb"><p class="mb40"><span>bottom: 40px</span></p></li>
					</ul>
				</td>
	    </tr>
	    <tr>
	      <th>left</th>
	      <td>class="ml10" ~ class="ml40" <br/>10px씩 증가 ~ 40px 까지</td>
	      <td class="tl">
					<ul class="sam-ul">
						<li class="fl"><p class="ml10"><span>left: 10px</span></p></li>
						<li class="fl"><p class="ml20"><span>left: 20px</span></p></li>
						<li class="fl"><p class="ml30"><span>left: 30px</span></p></li>
						<li class="fl"><p class="ml40"><span>left: 40px</span></p></li>
					</ul>
				</td>
	    </tr>	 	     
	    <tr>
	      <th>right</th>
	      <td>class="mr10" ~ class="mr40" <br/>10px씩 증가 ~ 40px 까지</td>
	      <td class="tl">
					<ul class="sam-ul">
						<li class="fl"><p class="mr10"><span>right: 10px</span></p></li>
						<li class="fl"><p class="mr20"><span>right: 20px</span></p></li>
						<li class="fl"><p class="mr30"><span>right: 30px</span></p></li>
						<li class="fl"><p class="mr40"><span>right: 40px</span></p></li>
					</ul>
				</td>
	    </tr>
	    </tbody>
	  </table>

		<p class="txt">box padding px</p>
	  <table>
	    <colgroup>
	      <col style="width:15%">	      	      	      	      	      	      
	      <col style="width:20%">	      	      	      	      	      	      
	      <col style="width:65%">	      	      	      	      	      	          	      	      	      	      	      
	    </colgroup>
	    <thead>
	    <tr>
	      <th>구분</th>
	      <th>클래스</th>
	      <th>사용 예</th>
	    </tr>
	    </thead>
	    <tbody>
	    <tr>
	      <th>all</th>
	      <td>class="pd10" ~ class="pd40" <br/>10px씩 증가 ~ 40px 까지</td>
	      <td class="tl">
					<ul class="sam-ul">
						<li class="fl mr10 bd"><p class="pd10"><span>all: 10px</span></p></li>
						<li class="fl mr10 bd"><p class="pd20"><span>all: 20px</span></p></li>
						<li class="fl mr10 bd"><p class="pd30"><span>all: 30px</span></p></li>
						<li class="fl bd"><p class="pd40"><span>all: 40px</span></p></li>
					</ul>
				</td>
	    </tr>
	    <tr>
	      <th>top</th>
	      <td>class="pdt10" ~ class="pdt40" <br/>10px씩 증가 ~ 40px 까지</td>
	      <td class="tl">
					<ul class="sam-ul">
						<li class="fl mr10 bd"><p class="pdt10"><span>top: 10px</span></p></li>
						<li class="fl mr10 bd"><p class="pdt20"><span>top: 20px</span></p></li>
						<li class="fl mr10 bd"><p class="pdt30"><span>top: 30px</span></p></li>
						<li class="fl bd"><p class="pdt40"><span>top: 40px</span></p></li>
					</ul>
				</td>
	    </tr>
	    <tr>
	      <th>bottom</th>
	      <td>class="pdb10" ~ class="pdb40" <br/>10px씩 증가 ~ 40px 까지</td>
	      <td class="tl">
					<ul class="sam-ul">
						<li class="fl mr10 bd"><p class="pdb10"><span>bottom: 10px</span></p></li>
						<li class="fl mr10 bd"><p class="pdb20"><span>bottom: 20px</span></p></li>
						<li class="fl mr10 bd"><p class="pdb30"><span>bottom: 30px</span></p></li>
						<li class="fl bd"><p class="pdb40"><span>bottom: 40px</span></p></li>
					</ul>
				</td>
	    </tr>
	    <tr>
	      <th>left</th>
	      <td>class="pdl10" ~ class="pdl40" <br/>10px씩 증가 ~ 40px 까지</td>
	      <td class="tl">
					<ul class="sam-ul">
						<li class="fl mr10 bd"><p class="pdl10"><span>left:10px</span></p></li>
						<li class="fl mr10 bd"><p class="pdl20"><span>left:20px</span></p></li>
						<li class="fl mr10 bd"><p class="pdl30"><span>left:30px</span></p></li>
						<li class="fl bd"><p class="pdl40"><span>left:40px</span></p></li>
					</ul>
				</td>
	    </tr>
	    <tr>
	      <th>right</th>
	      <td>class="pdr10" ~ class="pdr40" <br/>10px씩 증가 ~ 40px 까지</td>
	      <td class="tl">
					<ul class="sam-ul">
						<li class="fl mr10 bd"><p class="pdr10"><span>right: 10px</span></p></li>
						<li class="fl mr10 bd"><p class="pdr20"><span>right: 20px</span></p></li>
						<li class="fl mr10 bd"><p class="pdr30"><span>right: 30px</span></p></li>
						<li class="fl bd"><p class="pdr40"><span>right: 40px</span></p></li>
					</ul>
				</td>
	    </tr>	    
	    </tbody>
	  </table>


	</div>

</div>


