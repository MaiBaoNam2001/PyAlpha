$(function () {
  var userPrincipal = $('#user_principal').text();
  var scriptList = $('#script_list');
  var pagination = $('#pagination');
  var addScriptForm = $('#addscriptform')[0];
  var addScriptName = $('#add_scriptname');
  var addScriptDesc = $('#add_scriptdesc');
  var addScriptFile = $('#add_scriptfile');

  function loadScriptList() {
    $.ajax({
      type: 'GET',
      url: `${endpoint}/api/scripts/fetch-all`,
      contentType: 'application/json',
      dataType: 'json',
      success: function (data) {
        console.log(data);
        if (data.length === 0) {
          let message = document.createElement('p');
          message.innerText = 'Không có script nào để hiển thị';
          message.style.marginLeft = '20px';
          scriptList.empty();
          scriptList.append(message);
        } else {
          scriptList.empty();
          pagination.pagination({
            dataSource: data,
            pageSize: 10,
            callback: function (response, pagination) {
              scriptList.empty();
              for (let i = 0; i < response.length; i++) {
                scriptList.append(`
                <tr class="main__content--table-row">
                  <td class="main__content--table-data table-script-title-col"><a href="${endpoint}/admin/scripts/${response[i].id}">${response[i].title}</a></td>
                  <td class="main__content--table-data table-script-desc-col" style="white-space: pre-line">${response[i].description}</td>
                  <td class="main__content--table-data">
                    <a class="main__content--table-data-btn btn actionbtn" href="${endpoint}/admin/scripts/${response[i].id}"><i
                        class="icon fa-solid fa-circle-info" data-toggle="tooltip"
                        title="Xem chi tiết"></i></a>
                    <button class="main__content--table-data-btn btn actionbtn" onclick="document.getElementById('editscriptform_${response[i].id}').style.display='block'"><i
                        class="icon fa-solid fa-edit" data-toggle="tooltip" title="Chỉnh sửa"></i>
                    </button>
                    <!-- edit script modal -->
                    <div id="editscriptform_${response[i].id}" class="modal" style="display: none;">
                      <form action="#" class="modal-content animate" method="post" onsubmit="return false;">
                        <div class="modal__header">
                          <h2 class="modal__header--title">Sửa script</h2>
                          <i class="modal__close icon fa-solid fa-circle-xmark"
                             onclick="document.getElementById('editscriptform_${response[i].id}').style.display='none'"></i>
                        </div>
                        <div class="modal__body">
                          <div class="modal__body--input">
                            <label for="edit_scriptname" class="modal__body--input-label">Tiêu đề</label>
                            <input type="text" name="edit_scriptname" id="edit_scriptname_${response[i].id}"
                                   class="modal__body--input-field"
                                   placeholder="Nhập tiêu đề" value="${response[i].title}" required>
                          </div>
                          <div class="modal__body--input">
                            <label for="edit_scriptdesc" class="modal__body--input-label">Mô tả</label>
                            <textarea rows="4" name="edit_scriptdesc" id="edit_scriptdesc_${response[i].id}"
                                      class="modal__body--input-field"
                                      placeholder="Nhập mô tả" required>${response[i].description}</textarea>
                          </div>
                          <button type="submit" class="modal__footer--btn btn" id="edit_script_button_${response[i].id}">Cập nhật</button>
                        </div>
                      </form>
                    </div>
                    <button class="main__content--table-data-btn btn actionbtn" onclick="document.getElementById('deletescriptform_${response[i].id}').style.display='block'"><i
                        class="icon fa-solid fa-trash" data-toggle="tooltip" title="Xóa"></i></button>
                    <!-- delete script modal -->
                    <div id="deletescriptform_${response[i].id}" class="modal" style="display: none;">
                      <form action="#" class="modal-content animate" method="post" onsubmit="return false;">
                        <div class="modal__header">
                          <h2 class="modal__header--title">Xóa script</h2>
                          <i class="modal__close icon fa-solid fa-circle-xmark"
                             onclick="document.getElementById('deletescriptform_${response[i].id}').style.display='none'"></i>
                        </div>
                        <div class="modal__body">
                          <p class="modal__body--text">Bạn có chắc chắn muốn xóa script này?<br>LƯU Ý : Khi xóa script này thì nhật ký thực thi có liên quan đến script này cũng sẽ bị xóa.</p>
                          <div class="modal__body--confirm-delete-btns">
                            <button type="submit" class="modal__footer--btn btn cancelbtn"
                                    style="background-color: var(--prim) " id="delete_script_button_${response[i].id}">Xóa
                            </button>
                            <button type="button"
                                    onclick="document.getElementById('deletescriptform_${response[i].id}').style.display='none'"
                                    class="cancelbtn btn">Hủy
                            </button>
                          </div>
                        </div>
                      </form>
                    </div>
                  </td>
                </tr>
                `);

                var editScriptFormButton = $(`#edit_script_button_${response[i].id}`);
                editScriptFormButton.on('click', function () {
                  var editScriptName = $(`#edit_scriptname_${response[i].id}`);
                  var editScriptDesc = $(`#edit_scriptdesc_${response[i].id}`);
                  if (editScriptName.val() !== '' && editScriptDesc.val()
                      !== '') {
                    var editScriptForm = $(`#editscriptform_${response[i].id}`)[0];
                    var editScriptId = $(this).attr('id').split('_')[3];
                    $.ajax({
                      type: 'POST',
                      url: `${endpoint}/api/scripts/update`,
                      contentType: 'application/json',
                      data: JSON.stringify({
                        id: editScriptId,
                        title: editScriptName.val(),
                        description: editScriptDesc.val(),
                        user: userPrincipal,
                      }),
                      success: function (data) {
                        editScriptForm.style.display = 'none';
                        Toastinette.init({
                          position: 'top-center',
                          title: 'Cập nhật script thành công',
                          message: 'Script đã được cập nhật vào hệ thống',
                          type: 'success',
                          autoClose: 5000,
                          progress: true,
                        });
                        scriptList.empty();
                        loadScriptList();
                      },
                      error: function (jqXHR, status, error) {
                        console.log(error);
                        if (jqXHR.status === 400) {
                          var errMsg = jqXHR.responseText.includes('title')
                              ? 'Tiêu đề script đã tồn tại!'
                              : jqXHR.responseText;
                          Toastinette.init({
                            position: 'top-center',
                            title: 'Cập nhật script thất bại',
                            message: errMsg,
                            type: 'error',
                            autoClose: 5000,
                            progress: true,
                          });
                        }
                      }
                    });
                  }
                });

                var deleteScriptFormButton = $(`#delete_script_button_${response[i].id}`);
                deleteScriptFormButton.on('click', function () {
                  var deleteScriptForm = $(`#deletescriptform_${response[i].id}`)[0];
                  var deleteScriptId = $(this).attr('id').split('_')[3];
                  $.ajax({
                    type: 'POST',
                    url: `${endpoint}/api/scripts/delete`,
                    contentType: 'application/json',
                    data: JSON.stringify({
                      id: deleteScriptId, user: userPrincipal,
                    }),
                    success: function (data) {
                      deleteScriptForm.style.display = 'none';
                      Toastinette.init({
                        position: 'top-center',
                        title: 'Xóa script thành công',
                        message: 'Script đã được xóa khỏi hệ thống',
                        type: 'success',
                        autoClose: 5000,
                        progress: true,
                      });
                      scriptList.empty();
                      loadScriptList();
                    },
                    error: function (jqXHR, status, error) {
                      console.log(error);
                      if (jqXHR.status === 400) {
                        // var errMsg = jqXHR.responseText.includes('file')
                        //     ? 'Không thể xóa file script!' : jqXHR.responseText;
                        var errMsg = jqXHR.responseText.split(':')[1].trim();
                        if (errMsg.includes('file')) {
                          errMsg = 'Không thể xóa file script!';
                        } else {
                          switch (errMsg) {
                            case 'Script not found!':
                              errMsg = 'Script không tồn tại!';
                              break;
                            case 'User not found!':
                              errMsg = 'User không tồn tại!';
                              break;
                            case 'User is not admin!':
                              errMsg = 'User không có quyền xóa script!';
                              break;
                            default:
                              errMsg = 'Không thể thực thi SQL Statement';
                              break;
                          }
                        }
                        switch (errMsg) {
                          case 'Script not found!':
                            errMsg = 'Script không tồn tại!';
                            break;
                          case 'User not found!':
                            errMsg = 'User không tồn tại!';
                            break;
                          case 'User is not admin!':
                            errMsg = 'User không có quyền xóa script!';
                            break;

                        }
                        Toastinette.init({
                          position: 'top-center',
                          title: 'Xóa script thất bại',
                          message: errMsg,
                          type: 'error',
                          autoClose: 5000,
                          progress: true,
                        });
                      }
                    }
                  });
                });
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

  loadScriptList();

  $('#add_script_btn').on('click', function () {
    if (addScriptName.val() !== '' && addScriptDesc.val() !== ''
        && addScriptFile[0].files[0] !== undefined) {
      var scriptJson = {
        'title': addScriptName.val(),
        'description': addScriptDesc.val(),
        'user': userPrincipal,
      }
      var formData = new FormData();
      formData.append('scriptJsonString', JSON.stringify(scriptJson));
      formData.append('file', addScriptFile[0].files[0]);

      $.ajax({
        type: 'POST',
        url: `${endpoint}/api/scripts/add`,
        contentType: false,
        processData: false,
        data: formData,
        success: function (data) {
          addScriptForm.style.display = 'none';
          Toastinette.init({
            position: 'top-center',
            title: 'Thêm script thành công',
            message: 'Script đã được thêm vào hệ thống',
            type: 'success',
            autoClose: 5000,
            progress: true,
          });
          scriptList.empty();
          loadScriptList();
        },
        error: function (jqXHR, status, error) {
          console.log(error);
          if (jqXHR.status === 400) {
            var errMsg = jqXHR.responseText.includes('title')
                ? 'Tiêu đề script đã tồn tại!' : 'File script đã tồn tại!';
            Toastinette.init({
              position: 'top-center',
              title: 'Thêm script thất bại',
              message: errMsg,
              type: 'error',
              autoClose: 5000,
              progress: true,
            });
          }
        }
      });
    }
  });
});