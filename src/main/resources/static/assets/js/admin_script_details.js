$(function () {
  var scriptId = document.location.pathname.split("/")[3];
  var logList = $("#log_list");
  var pagination = $("#pagination");
  var title = $("#title");
  var navTitle = $("#nav_title");
  var mainHeader = $("#main_header");
  var description = $("#description");

  function loadLogList() {
    $.ajax({
      type: 'GET',
      url: `${endpoint}/api/logs/fetch/${scriptId}`,
      contentType: 'application/json',
      dataType: 'json',
      success: function (data) {
        console.log(data);
        if (data.length === 0) {
          let message = document.createElement('p');
          message.innerText = 'Không có nhật ký để hiển thị';
          message.style.marginLeft = '20px';
          logList.empty();
          logList.append(message);
        } else {
          logList.empty();
          pagination.pagination({
            dataSource: data,
            pageSize: 10,
            callback: function (response, pagination) {
              logList.empty();
              for (let i = 0; i < response.length; i++) {
                logList.append(`
                 <tr class="main__content--table-row">
                  <td class="main__content--table-data table-log-user">${response[i].username}</td>
                  <td class="main__content--table-data table-log-execute-time">
                    ${moment(response[i].executeTime).format(
                    'HH:mm:ss DD/MM/YY')}
                  </td>
                  <td class="main__content--table-data table-log-cpu-time">${response[i].cpuTime}ms</td>
                  <td class="main__content--table-data table-log-status" id="log-status-${response[i].id}"></td>
                  <td class="main__content--table-data table-log-input" style="white-space: pre-line">${response[i].input}</td>
                  <td class="main__content--table-data table-log-output">${response[i].output}</td>
                  <td class="main__content--table-data table-log-error">
                    ${response[i].error === null ? '' : response[i].error}
                  </td>
                 </tr>
              `);
                let statusIcon = document.createElement('i');
                statusIcon.classList.add('icon', 'fa-regular');
                statusIcon.classList.add(
                    response[i].status === 200
                        ? 'fa-circle-check'
                        : 'fa-circle-xmark');
                let logStatus = $(`#log-status-${response[i].id}`);
                logStatus.append(statusIcon);
              }
              $('.paginationjs-pages > ul').addClass('pagination');
              $('.paginationjs-pages > ul > li').addClass('page-item');
            }
          });
        }
      },
      error: function (jqXHR, status, error) {
        console.log(error);
      }
    })
  }

  $.ajax({
    type: 'GET',
    url: `${endpoint}/api/scripts/${scriptId}`,
    contentType: 'application/json',
    dataType: 'json',
    success: function (data) {
      title.text(title.text() + data.title);
      navTitle.html(data.title);
      mainHeader.html(data.title);
      description.html(data.description);
      loadLogList();
    },
    error: function (jqXHR, status, error) {
      console.log(error);
      if (jqXHR.status === 400) {
        window.location.href = `${endpoint}/not-found`;
      }
    }
  });
});