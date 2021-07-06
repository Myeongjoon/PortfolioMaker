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



window.addEventListener("load", function () {
  url = '/stock/main/list'
  loc = getParam("location");
  if (loc != null && loc != "") {
    url += "?location=" + loc
  }
  $.ajax({
    url: url,
    method: 'get',
    context: this,

    success: function (data, status, xhr) {
      table_data = []
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
          { title: "수익률", field: "rate", width: 100, editor: "input" },
          { title: "전일대비", field: "previousRate", width: 100, editor: "input" },
          { title: "삭제", field: "car", width: 90, hozAlign: "center", formatter: "tickCross", sorter: "boolean", editor: true },
        ],
      });
    }
  });
});

