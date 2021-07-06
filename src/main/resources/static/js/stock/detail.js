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