google.charts.load('current', { packages: ['corechart', 'line'] });
google.charts.setOnLoadCallback(drawChart);

function getParam(sname) {
  var params = location.search.substr(location.search.indexOf("?") + 1);
  var sval = "";
  params = params.split("&");
  for (var i = 0; i < params.length; i++) {
    temp = params[i].split("=");
    if ([temp[0]] == sname) { sval = temp[1]; }

  }
  return sval;
}


function drawChart() {
  url = '/stock/detail/list'
  ticker = getParam("ticker");
  if (ticker != null && ticker != "") {
    url += "?ticker=" + ticker
  }
  $.ajax({
    url: url,
    method: 'get',
    context: this,
    success: function (data, status, xhr) {
      array = [];
      for (const element of data) {
        temp = []
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