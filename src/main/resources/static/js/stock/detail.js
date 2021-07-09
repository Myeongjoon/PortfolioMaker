import urlModule from './../util/url_util.js';

google.charts.load('current', { packages: ['corechart', 'line'] });
google.charts.setOnLoadCallback(drawChart);


function drawChart() {
  var url = '/stock/detail/list'
  var ticker = urlModule.getParam("ticker");
  if (ticker != null && ticker != "") {
    url += "?ticker=" + ticker
  }
  $.ajax({
    url: url,
    method: 'get',
    context: this,
    success: function (data, status, xhr) {
      var array = [];
      for (const element of data) {
        var temp = []
        temp.push(new Date(element.priceDate))
        temp.push(Number(element.price.replace(/,/gi, "")))
        array.push(temp)
      }

      var data = new google.visualization.DataTable();
      data.addColumn('date', 'X');
      data.addColumn('number', $("#name").val());

      data.addRows(array);

      var options = {
        hAxis: {
          title: '시간'
        },
        vAxis: {
          title: '자산 가격'
        },
        colors: ['#a52714', '#097138'],
        crosshair: {
          color: '#000',
          trigger: 'selection'
        }
      };

      var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

      chart.draw(data, options);
      chart.setSelection([{ row: 38, column: 1 }]);
    }
  });

}

$(document).on('click', '.deleteBtn', function () {
  $.ajax({
    url: '/stock/detail',
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