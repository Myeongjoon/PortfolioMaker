import urlModule from './../util/url_util.js';
import graphModule from './../util/graph_util.js';

google.charts.load('current', { 'packages': ['corechart'] });
google.charts.setOnLoadCallback(drawChart);

function saveStock() {
  $.ajax({
    url: '/stock',
    method: 'post',
    context: this,
    data: {
      ticker: $('#ticker').val(),
      count: $('#count').val()
    },

    success: function (data, status, xhr) {
      location.reload()
    }
  });
}

$(document).on('click', '.deleteBtn', function () {
  $.ajax({
    url: '/stock',
    method: 'delete',
    context: this,
    data: { ticker: $(this).attr('data-ticker') },
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

function drawGrid(data, status, xhr) {
  var table_data = []
  for (const element of data) {
    if (element.name == null) {
      element.name = element.ticker;
    }
    element.url = '<a href="http://localhost:8080/stock/detail?ticker=' + element.ticker + '" >' + element.name + '</a>';
    table_data.push(element)
  }
  //이름이 있을 경우 ticker를 이름으로 변경
  var table = new Tabulator("#table", {
    data: table_data,           //load row data from array
    layout: "fitColumns",      //fit columns to width of table
    responsiveLayout: "hide",  //hide columns that dont fit on the table
    tooltips: true,            //show tool tips on cells
    addRowPos: "top",          //when adding a new row, add it to the top of the table
    history: true,             //allow undo and redo actions on the table
    pagination: "local",       //paginate the data
    paginationSize: 20,         //allow 7 rows per page of data
    movableColumns: true,      //allow column order to be changed
    resizableRows: true,       //allow row order to be changed
    initialSort: [             //set the initial sort order of the data
      { column: "rate", dir: "asc" },
    ],
    columns: [                 //define the table columns
      {
        title: "이름", field: "url", width: 100, formatter: function (cell, formatterParams, onRendered) {
          //cell - the cell component
          //formatterParams - parameters set for the column
          //onRendered - function to call when the formatter has been rendered

          return cell.getValue(); //return the contents of the cell;
        },
      },
      {
        title: "갯수", field: "count", width: 100, hozAlign: "left", formatter: "money", formatterParams: {
          precision: 0
        }
      },
      { title: "구매가격", field: "buyPriceSum", width: 110, editor: "select" },
      { title: "현재가격", field: "currentPriceSum", width: 110, editor: "select" },
      {
        title: "수익률", field: "rate", width: 100, editor: "input", formatter: function (cell, formatterParams, onRendered) {
          return graphModule.checkPriceRate(cell.getValue());
        }
      },
      {
        title: "전일대비", field: "previousRate", width: 100, editor: "input", formatter: function (cell, formatterParams, onRendered) {
          return graphModule.checkPriceRate(cell.getValue());
        }
      },
      { title: "삭제", field: "car", width: 90, hozAlign: "center", formatter: "tickCross", sorter: "boolean", editor: true },
    ],
  });
}

function drawChart() {

  var current_url = window.location.href
  var url = '/stock/portfolio/list';
  if (current_url.indexOf("stock/portfolio/k") != -1) {
    url += '?location=KOSPI'
  } else if (current_url.indexOf("stock/portfolio/n") != -1) {
    url += '?location=NASDAQ'
  } else {
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
      drawGrid(data, status, xhr)
      var array = [];
      array.push(['name', 'price'])
      for (const element of data) {
        var temp = []
        temp.push(element.name)
        temp.push(Number(element.currentPriceSum.replace(/,/gi, "")))
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