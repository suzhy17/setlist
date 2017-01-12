<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<div class="container" id="body">
	<h3>포토 이모티콘 등록</h3>
	<br>
	<div id="categoryType" class="btn-group btn-group-justified" role="group" aria-label="이모티콘 종류 선택">
		<div class="btn-group" role="group">
			<a href="/emoticon/mms/products?write&type=template" class="btn ${param.type == 'template' ? 'btn-info' : 'btn-default'}">템플릿</a>
		</div>
		<div class="btn-group" role="group">
			<a href="/emoticon/mms/products?write&type=icon" class="btn ${param.type == 'icon' ? 'btn-info' : 'btn-default'}">아이콘</a>
		</div>
		<div class="btn-group" role="group">
			<a href="/emoticon/mms/products?write&type=texticon" class="btn ${param.type == 'texticon' ? 'btn-info' : 'btn-default'}">그림문자</a>
		</div>
	</div>
	<br>

	<form id="product-form" method="post">
		<input type="hidden" name="productTypeCd" value="${productTypeCd}" />
		<table class="table table-bordered">
			<tbody>
				<tr>
					<th class="active text-center">템플릿명</th>
					<td>
						<div class="col-md-4">
							<input type="text" name="contentNm" class="form-control" maxlength="20" placeholder="템플릿명" />
						</div>
						<span class="slim">(20byte 이내)</span>
					</td>
				</tr>
				<tr>
					<th class="active text-center">카테고리</th>
					<td>
						<div class="col-md-4">
							<select name="categoryCd" class="form-control">
								<option value="">[카테고리 선택]</option>
								<c:forEach var="item" items="${usedCategoryList}">
									<option value="${item.categoryCd}">${item.categoryNm}</option>
								</c:forEach>
								<c:forEach var="item" items="${unusedCategoryList}">
									<option value="${item.categoryCd}">${item.categoryNm} (미사용)</option>
								</c:forEach>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<th class="active text-center">배경이미지</th>
					<td>
						<div class="col-md-4">
							<input type="file" id="file3" name="file3" class="form-control" />
						</div>
						<span class="slim">(480x720 또는 640x960)</span>
					</td>
				</tr>
				<tr>
					<th class="active text-center">썸네일이미지</th>
					<td>
						<div class="col-md-4">
							<input type="file" id="file6" name="file6" class="form-control" />
						</div>
						<span class="slim">(240x360 , 50kb 이하)</span>
					</td>
				</tr>
				<tr>
					<th class="active text-center">추가이미지</th>
					<td>
						<div class="col-md-3">
							<span class="btn btn-success btn-sm fileinput-button">
								<i class="glyphicon glyphicon-plus"></i>
								<span>Add (png파일)</span>
								<input id="fileupload" type="file" name="file" />
							</span>
						</div>
						<div id="imageFormArea" class="col-md-9">
						</div>
					</td>
				</tr>
				<tr>
					<th class="active text-center">템플릿소스</th>
					<td>
						<div class="col-md-12">
							<textarea name="templateSource" rows="15" class="form-control"></textarea>
						</div>
					</td>
				</tr>
				<tr>
					<th class="active text-center">태그</th>
					<td>
						<div class="col-md-4">
							<input type="text" name="tagNm" class="form-control" maxlength="20" placeholder="태그" />
						</div>
						<span class="slim">(,로 구분)</span>
					</td>
				</tr>
				<tr>
					<th class="active text-center">판매여부</th>
					<td>
						<label class="radio-inline">
							<input type="radio" name="saleYn" value="Y" /> 예
						</label>
						<label class="radio-inline">
							<input type="radio" name="saleYn" value="N" checked="checked" /> 아니오
						</label>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="text-center">
			<button type="submit" class="btn btn-primary">등록하기</button>
			<a href="/emoticon/mms/products?type=${param.type}&categoryCd=ALL" class="btn btn-default">목록으로</a>
		</div>
	</form>
</div>

<div class="row tmpl" id="imageFormTemplate" style="display: none;">
	<div class="col-md-2 text-center files">
	</div>
	<div class="col-md-9">
		<div class="row chosen">
			<label class="radio-inline">
				<input type="radio" name="type" value="icon"> icon
			</label>
			<label class="radio-inline">
				<input type="radio" name="type" value="texticon"> texticon
			</label>
			<label class="radio-inline">
				<input type="radio" name="type" value="frame"> frame
			</label>
			<button name="uploadBtn" class="btn btn-primary btn-sm" disabled="disabled">Processing...</button>
			<button type="button" name="deletePreviewBtn" class="btn btn-default btn-sm">삭제</button>
		</div>
		<div class="row uploaded">
			<span class="type-title"><strong>frame 1</strong></span>
			<ul>
				<li><strong>원본파일</strong> : <span class="org-file">xxxxxx.png</span><br/>
				<li><strong>저장파일</strong> : <span class="saved-file">xxxxxxxxxx.png</span>
			</ul>
			<button type="button" name="viewBtn" class="btn btn-default btn-sm">보기</button>
			<button type="button" name="sourceBtn" data-type="" data-depth="" data-width="" data-height="" class="btn btn-info btn-sm">소스입력</button>
			<button type="button" name="deleteBtn" class="btn btn-danger btn-sm">삭제</button>
			<input type="hidden" name="fileNo" />
			<input type="hidden" name="fileType" />
			<input type="hidden" name="sizeCode" />
		</div>
	</div>
	<hr />
</div>

<script type="text/javascript">
	$(document).ready(function() {
		
		// 폼 전송 (파일 포함)
		var $frm = $('#product-form');
		$frm.ajaxForm({
			dataType: 'json',
			beforeSubmit: function(formData, jqForm, options) {
				if (!confirm('템플릿을 등록하시겠습니까?')) {
					return false;
				}
				
				$.blockUI({
					message: '등록 중 입니다.<br/>잠시만 기다려주세요.'
				});
				
				var $contentNm  = $frm.find('input[name="contentNm"]'),
					$tagNm      = $frm.find('input[name="tagNm"]'),
					$categoryCd = $frm.find('select[name="categoryCd"]'),
					$file3      = $frm.find('input[name="file3"]');
	
				if (!$contentNm.val()) {
					alert('템플릿명을 입력해 주세요.');
					$.unblockUI();
					$contentNm.focus();
					return false;
				}
	
				if (!$categoryCd.val()) {
					alert('카테고리를 입력해 주세요.');
					$.unblockUI();
					$categoryCd.focus();
					return false;
				}
	
				if (!$tagNm.val()) {
					alert('태그를 입력해 주세요.');
					$.unblockUI();
					$tagNm.focus();
					return false;
				}
	
				if (!$file3.val()) {
					alert('배경이미지 파일을 선택해 주세요.');
					$.unblockUI();
					$file3.focus();
					return false;
				}
			},
			success: function(data) {
				alert('등록되었습니다.');
				location.href = '/emoticon/mms/products?type=template&categoryCd=ALL';
			},
			complete: function(xhr) {
				$.unblockUI();
			}
		});
		
		var $xml,
			$templateSource = $('textarea[name="templateSource"]');
		
		$.get("/static/template-source-xml/template.xml", function(data) {
			$xml = $(data);
			//console.log(xmlToString($xml));
			$('textarea[name="templateSource"]').html(xmlToString($xml));
		}, 'XML');
		
		// 파일 업로드 라이브러리 참조
		// https://blueimp.github.io/jQuery-File-Upload/basic-plus.html

		function toggleFileChooseBtn(enabled) {
			if (enabled) {
				$('#fileupload').prop('disabled', false).parent().removeClass('disabled');
			} else {
				$('#fileupload').prop('disabled', true).parent().addClass('disabled');
			}
		};

		// 보기 버튼 클릭
		$('button[name="viewBtn"]').on('click', function() {
			window.open($(this).val(), '_blank');
		});
		
		// 소스추가 버튼 클릭
		$('button[name="sourceBtn"]').on('click', function() {
			var $this = $(this),
				src = $this.val(),
				type = $this.data('type'),
				depth = $this.data('depth'),
				width = $this.data('width'),
				height = $this.data('height');
			
			if (type === 'icon' || type === 'texticon') {
				var htmlTag = '<icon ';
				htmlTag += 'depth="'+depth+'" ';
				htmlTag += 'src="'+src+'" ';
				htmlTag += 'rotation="0" ';
				htmlTag += 'x_position="0" ';
				htmlTag += 'y_position="0" ';
				htmlTag += 'width="'+width+'" ';
				htmlTag += 'height="'+height+'" ';
				htmlTag += 'readonly="N" ';
//				htmlTag += 'scaleX="1" ';
//				htmlTag += 'scaleY="1" ';
//				htmlTag += 'isColorChange="" ';
//				htmlTag += 'isColor="" ';
//				htmlTag += 'isAlpha="1" ';
//				htmlTag += 'isBrightness="" ';
//				htmlTag += 'isContrast="" ';
//				htmlTag += 'isSaturation="" ';
//				htmlTag += 'isHue="" ';
				if (type === 'texticon') {
					htmlTag += 'type_cd="imgText" ';
					htmlTag += 'sub_type_cd="sysText" ';
				}
				htmlTag += '></icon>';
				console.log('htmlTag='+htmlTag);
				
				$xml.find('icons').append(htmlTag);
			}
			else if (type === 'frame') {
				var htmlTag = '<frame ';
				htmlTag += 'src="'+src+'" ';
				htmlTag += 'product_no="0" ';
				htmlTag += '></frame>';
				console.log('htmlTag='+htmlTag);
				
				$xml.find('templete').append(htmlTag);
			}

			console.log('$xml='+xmlToString($xml));
			$templateSource.html(formatXml(xmlToString($xml)));
		});
		
		$('button[name="uploadBtn"]').on('click', function(event) {
			event.preventDefault();

			var $this = $(this),
			data = $this.data();

			if ($this.parent().find('input[name="type"]:checked').length === 0) {
				alert('이미지 종류를 먼저 선택하세요.');
				return false;
			}
			
			$this
				.off('click')
				.text('Abort')
				.on('click', function() {
					$this.remove();
					data.abort();
				});
			data.submit().always(function() {
				$this.remove();
			});
		});
		
		// 업로드 후 삭제 버튼 클릭
		$('button[name="deleteBtn"]').on('click', function() {
			var $this = $(this),
				fileNo = $this.val(),
				depth = $this.data('depth');
			$.ajax({
				method: 'DELETE',
				url: '/file-delete/'+fileNo
			}).done(function(data) {
				alert(data.resMsg);
				$xml.find('icon[depth="'+depth+'"]').remove();
				$templateSource.html(xmlToString($xml));
				$this.parents('.tmpl').remove();
			}).fail(function() {
				alert('알수 없는 오류 입니다. 시스템 관리자에게 문의 바랍니다.');
			});
		});
		
		// 업로드 전 삭제 버튼 클릭
		$('button[name="deletePreviewBtn"]').on('click', function() {
			$(this).parents('.tmpl').remove();
			toggleFileChooseBtn(true);
		});
		
		var depth = 0;
		
		$('input[name="type"]').click(function() {
			depth++;
			var type = $(this).filter(':checked').val();
			$('#fileupload').fileupload({
				formData: {
					type: type,
					depth: depth
				}
			})
		});
	
		$('#fileupload').fileupload({
			url: '/file-upload',
			dataType: 'json',
			autoUpload: false,
			singleFileUploads: true,
			//acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
			acceptFileTypes: /(\.|\/)(png)$/i,
			maxFileSize: 999000,
			disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
			previewMaxWidth: 100,
			previewMaxHeight: 100,
			previewCrop: true
		}).on('fileuploadadd', function(e, data) {
			console.log("== fileuploadadd (파일 추가 중) ==");
	
			$.each(data.files, function(index, file) {

				var $imageFormTemplate = $('#imageFormTemplate').clone(true).attr('id','').show();
				$imageFormTemplate.find('.uploaded').hide();

				if (!index) {
					// 업로드 버튼
					$imageFormTemplate.find('button[name="uploadBtn"]').data(data);
				}
				$('#imageFormArea').append($imageFormTemplate);
			});
		}).on('fileuploadprocessalways', function(e, data) {
			console.log("== fileuploadprocessalways (파일 추가 완료) ==");
	
			var index = data.index,
				file = data.files[index],
				$imageFormTemplate = $('#imageFormArea div.row:last-child');
				console.log('file=' + JSON.stringify(file));
				console.log('data.files=' + JSON.stringify(data.files));
				console.log('data.files[index].preview=' + document.getHTML(data.files[index].preview, true));

			if (file.preview) {
				$imageFormTemplate.find('div.files').append(file.preview);
			}
			
			// 지원하지 않는 확장자 선택 등의 에러 발생시 처리
			if (file.error) {
				alert(file.error);
				$imageFormTemplate.remove();
				return false;
			}
			if (index + 1 === data.files.length) {
				$imageFormTemplate.find('button[name="uploadBtn"]')
					.text('업로드')
					.prop('disabled', !!data.files.error);
			}
			toggleFileChooseBtn(false);

		}).on('fileuploadprogressall', function(e, data) {
			console.log("== fileuploaddone (파일 업로드 중) ==");
			console.log("== progressall ==");
			console.log("data="+JSON.stringify(data));
			toggleFileChooseBtn(true);
		}).on('fileuploaddone', function(e, data) {
			console.log("== fileuploaddone (파일 업로드 완료) ==");
			console.log("data.result="+ JSON.stringify(data.result));
			
			var result = data.result;
			
			if (result.resCd === 'SUCCESS') {
				
				var productFile = result.resObj;
				
				// 업로드 버튼을 지우고 소스입력 버튼을 표시한다.
				var $imageFormTemplate = $('#imageFormArea > div.row:last-child');
				var typeTitle = $imageFormTemplate.find('input[name="type"]:checked').val();
				$imageFormTemplate.find('.chosen').remove();
				$imageFormTemplate.find('.uploaded').show();
				$imageFormTemplate.find('button[name="viewBtn"]').val(productFile.imgPath);
				$imageFormTemplate.find('button[name="sourceBtn"]').val(productFile.imgPath).data('type', typeTitle).data('depth', depth).data('width', productFile.width).data('height', productFile.height);
				$imageFormTemplate.find('button[name="deleteBtn"]').val(productFile.fileNo).data('type', typeTitle).data('depth', depth);
				$imageFormTemplate.find('span.type-title').html('<strong>' + typeTitle + ' ' + (depth++) + '</strong>');
				$imageFormTemplate.find('span.org-file').text(productFile.originFileNm);
				$imageFormTemplate.find('span.saved-file').text(productFile.imgPath);
				$imageFormTemplate.find('input[name="fileNo"]').val(productFile.fileNo);
				$imageFormTemplate.find('input[name="fileType"]').val(typeTitle);
			} else {
				var error = $('<span class="text-danger"/>').text(result.resMsg);
			}
		}).on('fileuploadfail', function(e, data) {
			console.log("== fileuploadfail (파일 업로드 실패) ==");
			console.log("data="+JSON.stringify(data));
			
			$.each(data.files, function(index) {
				var error = $('<span class="text-danger"/>').text('File upload failed.');
				$(data.context.children()[index])
					.append('<br>')
					.append(error);
			});
		}).prop('disabled', !$.support.fileInput)
			.parent().addClass($.support.fileInput ? undefined : 'disabled');

		var maxThumbSize = 51200;	// 50Kb
		
		// 배경 이미지 체크
		$('#file3').change(function() {
			// 파일 확장자 체크
			var fileName = $(this).val();
			var fileExt = fileName.slice(fileName.indexOf('.') + 1).toLowerCase();
			if (fileExt !== 'png' && fileExt !== 'jpg' && fileExt !== 'jpeg' && fileExt !== 'gif') {
				alert('png, jpg, jpeg, gif 파일만 선택 가능합니다.');
				this.value = null;
				return false;
			}
		});

		// 썸네일 이미지 체크
		$('#file6').change(function() {
			// 파일 용량 체크
			var file = this.files[0];
			if (file.size > maxThumbSize || file.fileSize > maxThumbSize) {
				alert('파일 용량이 50Kb를 초과하였습니다.');
				this.value = null;
				return false;
			}
		});
	});
</script>