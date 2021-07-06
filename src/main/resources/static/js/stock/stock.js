var stockModule = {
  stockSync: function () {
    var current_url = window.location.href
    var url;
    if (current_url.indexOf("/k") != -1) {
      url = '/stock/stockSync?location=코스피'
    } else if (current_url.indexOf("/n") != -1) {
      url = '/stock/stockSync?location=NASDAQ'
    } else {
      url = '/stock/stockSync'
      loc = getParam("location");
      if (loc != null && loc != "") {
        url += "?location=" + loc
      }
    }
    $.ajax({
      url: url,
      method: 'get',
      context: this,

      success: function (data, status, xhr) {
        location.reload()
      }
    });
  },
  portfolioSync: function () {
    $.ajax({
      url: '/stock/portfolioSync',
      method: 'get',
      context: this,

      success: function (data, status, xhr) {
        location.reload()
      }
    });
  }
}
export default stockModule;