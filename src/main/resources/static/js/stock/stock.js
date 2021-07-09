import urlModule from './../util/url_util.js';

var stockModule = {
  stockSync: function () {
    var current_url = window.location.href
    var url;
    if (current_url.indexOf("/k") != -1) {
      url = '/stock/stockSync?location=KOSPI'
    } else if (current_url.indexOf("/n") != -1) {
      url = '/stock/stockSync?location=NASDAQ'
    } else {
      url = '/stock/stockSync'
      var loc = urlModule.getParam("location");
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
      }, error: function (error) {
        alert("주식 싱크 실패!");
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
      }, error: function (error) {
        alert("포트폴리오 싱크 실패!");
      }
    });
  }
}
export default stockModule;