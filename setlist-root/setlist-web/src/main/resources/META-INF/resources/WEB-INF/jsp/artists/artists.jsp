<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
<div class="row marketing">

	<div id="idxNmArea" class="btn-group btn-group-sm" role="group" aria-label="INDEX">
		<button type="button" class="btn btn-default ${param.idxNm == 'A' ? 'active' : ''}" value="A">A</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'B' ? 'active' : ''}" value="B">B</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'C' ? 'active' : ''}" value="C">C</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'D' ? 'active' : ''}" value="D">D</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'E' ? 'active' : ''}" value="E">E</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'F' ? 'active' : ''}" value="F">F</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'G' ? 'active' : ''}" value="G">G</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'H' ? 'active' : ''}" value="H">H</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'I' ? 'active' : ''}" value="I">I</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'J' ? 'active' : ''}" value="J">J</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'K' ? 'active' : ''}" value="K">K</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'L' ? 'active' : ''}" value="L">L</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'M' ? 'active' : ''}" value="M">M</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'N' ? 'active' : ''}" value="N">N</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'O' ? 'active' : ''}" value="O">O</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'P' ? 'active' : ''}" value="P">P</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'Q' ? 'active' : ''}" value="Q">Q</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'R' ? 'active' : ''}" value="R">R</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'S' ? 'active' : ''}" value="S">S</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'T' ? 'active' : ''}" value="T">T</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'U' ? 'active' : ''}" value="U">U</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'V' ? 'active' : ''}" value="V">V</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'W' ? 'active' : ''}" value="W">W</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'X' ? 'active' : ''}" value="X">X</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'Y' ? 'active' : ''}" value="Y">Y</button>
		<button type="button" class="btn btn-default ${param.idxNm == 'Z' ? 'active' : ''}" value="Z">Z</button>
	</div>
</div>	

<div class="row marketing">
	<div class="list-group">
	<c:forEach var="item" varStatus="status" items="${artists}">
		<button type="button" name="artistGo" class="list-group-item" value="${item.artistId}">
			<span class="badge">14</span>
			${item.artistName}, ${item.nationality}
		</button>
	</c:forEach>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		"use strict";
		
		$('#idxNmArea > button').on('click', function() {
			location.href = '/artists?idxNm=' + $(this).val();
		});
		
		$('button[name="artistGo"]').on('click', function() {
			location.href = '/artists/' + $(this).val();
		});
	});
</script>