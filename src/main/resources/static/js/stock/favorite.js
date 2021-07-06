import urlModule from './../util/url_util.js';

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

function portfolioSync() {
  $.ajax({
    url: '/stock/portfolioSync',
    method: 'get',
    context: this,

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

window.addEventListener("load", function () {
  var current_url = window.location.href
  var url;
  if (current_url.indexOf("stock/favorite/k") != -1) {
    url = '/stock/favorite/list?location=코스피'
  } else {
    url = '/stock/favorite/list'
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
          { column: "previousRate", dir: "asc" },
        ],
        columns: [                 //define the table columns
          {
            title: "이름", field: "url", width: 180, formatter: function (cell, formatterParams, onRendered) {
              //cell - the cell component
              //formatterParams - parameters set for the column
              //onRendered - function to call when the formatter has been rendered

              return cell.getValue(); //return the contents of the cell;
            },
          },
          { title: "현재가격", field: "price", width: 90, editor: "select" },
          {
            title: "전일대비", field: "previousRate", width: 80, editor: "input", formatter: function (cell, formatterParams, onRendered) {
              //3퍼 하락
              if (cell.getValue() <= -3) {
                return "<span class = 'bold_blue'>" + cell.getValue() + "</span>";
              } else if (cell.getValue() >= 3) {
                return "<span class = 'bold_red'>" + cell.getValue() + "</span>";
              } else if (cell.getValue() > 0) {
                return "<span class = 'red'>" + cell.getValue() + "</span>";
              } else if (cell.getValue() < 0) {
                return "<span class = 'blue'>" + cell.getValue() + "</span>";
              } else {
                return cell.getValue();
              }
            }
          },
          { title: "삭제", field: "car", width: 50, hozAlign: "center", formatter: "tickCross", sorter: "boolean", editor: true },
        ],
      });
    }
  });
});




function stockSync() {
  $.ajax({
    url: '/stock/stockSync',
    method: 'get',
    context: this,

    success: function (data, status, xhr) {
      location.reload()
    }
  });
}
