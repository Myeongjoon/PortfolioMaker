$(document).on('click', '.deleteBtn', function () {
  $.ajax({
    url: '/stock/meta',
    method: 'delete',
    context: this,
    data: { id: $(this).attr('data-id') },
    success: function (data, status, xhr) {
      $(this).hide();
      alert('해당 데이터가 제거되었습니다.');
      location.reload();
    },
    error: function (jqXhr, textStatus, errorMessage) {
      alert('연결 실패되었습니다. 추후에 다시 진행해주세요.');
    }
  });

});