google.charts.load('current', { 'packages': ['corechart'] });
google.charts.setOnLoadCallback(drawChart);

function drawChart() {

  $.ajax({
    url: '/portfolio',
    method: 'get',
    context: this,
    success: function (data, status, xhr) {
        array = [];
        array.push(['type','price'])
        for (const element of data) {
          temp = []
          temp.push(element.name)
          temp.push(element.price)
          array.push(temp)
        }
      var data = google.visualization.arrayToDataTable(array);

      var options = {
        title: 'My Daily Activities'
      };

      var chart = new google.visualization.PieChart(document.getElementById('piechart'));

      chart.draw(data, options);
    }
  });
}