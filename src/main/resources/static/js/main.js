google.charts.load('current', { 'packages': ['corechart'] });
google.charts.setOnLoadCallback(drawChart);

function drawChart() {

  $.ajax({
    url: '/portfolio',
    method: 'get',
    context: this,
    success: function (data, status, xhr) {
      array = [];
      array.push(['name', 'price'])
      for (const element of data) {
        temp = []
        temp.push(element.name)
        temp.push(element.price)
        array.push(temp)
      }
      var data = google.visualization.arrayToDataTable(array);

      var options = {
        title: '나의 포트폴리오'
      };

      var chart = new google.visualization.PieChart(document.getElementById('piechart'));

      chart.draw(data, options);
    }
  });
}

function uploadPortfolio() {
  price = $('#price').val().replace(/,/gi, "")
  $.ajax({
    url: '/portfolio',
    method: 'post',
    context: this,
    data: {
      name: $('#name').val(),
      price: price
    },

    success: function (data, status, xhr) {
      location.reload()
    }
  });
}