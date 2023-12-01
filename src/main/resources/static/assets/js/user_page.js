$(function () {
  var programList = $("#program_list");
  var pagination = $("#pagination");

  function loadProgramList() {
    $.ajax({
      type: "GET",
      url: `${endpoint}/api/scripts/fetch-all`,
      contentType: 'application/json',
      dataType: 'json',
      success: function (data) {
        console.log(data);
        if (data.length === 0) {
          let message = document.createElement('p');
          message.innerText = 'Không có chương trình nào để hiển thị';
          message.style.marginLeft = '3px';
          programList.empty();
          programList.append(message);
        } else {
          programList.empty();
          pagination.pagination({
            dataSource: data,
            pageSize: 10,
            callback: function (response, pagination) {
              programList.empty();
              for (let i = 0; i < response.length; i++) {
                programList.append(`
                <a href="${endpoint}/user/execute/${response[i].id}" class="main__program" id="main_program">
                  <div class="main__script">
                    <h2 class="main__list-title">${response[i].title}</h2>
                    <p class="main__list-desc" style="white-space: pre-line">${response[i].description}</p>
                  </div>
                </a>
              `);
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
    });
  }

  loadProgramList();

})