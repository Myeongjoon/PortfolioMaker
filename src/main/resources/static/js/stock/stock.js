var stockModule = {
  stockSync: function () {
    $.ajax({
      url: '/stock/stockSync',
      method: 'get',
      context: this,

      success: function (data, status, xhr) {
        location.reload()
      }
    });
  }
}
export default stockModule;