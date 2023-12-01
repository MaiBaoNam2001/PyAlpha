$(function () {
  var userList = $('#user-list');
  var pagination = $('#pagination');
  var btnRegisterUser = $('#btn_register_user');
  var registerUsername = $('#register_username');
  var registerPassword = $('#register_password');
  var registerUserForm = $('#register_user_form')[0];

  //eye icon to show password
  var registerEyeIcon = document.getElementById('register_eye_icon');
  var regsiterPassword = document.getElementById('register_password');
  registerEyeIcon.addEventListener('click', function () {
    if (regsiterPassword.type === "password") {
      regsiterPassword.type = "text";
      registerEyeIcon.classList.add('eye-icon--active');
      registerEyeIcon.classList.add('fa-eye-slash');
      registerEyeIcon.classList.remove('fa-eye');
    } else {
      regsiterPassword.type = "password";
      registerEyeIcon.classList.remove('eye-icon--active');
      registerEyeIcon.classList.remove('fa-eye-slash');
      registerEyeIcon.classList.add('fa-eye');
    }
  });

  loadNormalUserList();

  function loadNormalUserList() {
    $.ajax({
      type: 'GET',
      url: `${endpoint}/api/users/fetch-all`,
      contentType: 'application/json',
      dataType: 'json',
      success: function (data) {
        console.log(data);
        if (data.length === 0) {
          let message = document.createElement('p');
          message.innerText = 'Không có người dùng để hiển thị';
          message.style.marginLeft = '20px';
          userList.empty();
          userList.append(message);
        } else {
          userList.empty();
          pagination.pagination({
            dataSource: data,
            pageSize: 10,
            callback: function (response, pagination) {
              userList.empty();
              for (let i = 0; i < response.length; i++) {
                userList.append(`
                <tr class="main__content--table-row">
                  <td class="main__content--table-data table-username-col">${response[i].username}</td>
                  <td class="main__content--table-data">
                    <button class="main__content--table-data-btn btn actionbtn" 
                    onclick="document.getElementById('edit_user_form_${response[i].id}').style.display = 'block'">
                      <i class="icon fa-solid fa-edit" data-toggle="tooltip" title="Chỉnh sửa"></i>
                    </button>
                    <!-- edit user form -->
                    <div id="edit_user_form_${response[i].id}" class="modal">
                      <form class="modal-content animate" action="#" onsubmit="return false;">
                        <h2 class="form-title">Đổi mật khẩu</h2>
                        <div class="container">
                          <div class="txtfield">
                            <p id="edit_username_label">Tên người dùng</p>
                            <p id="edit_username_${response[i].id}">${response[i].username}</p>
                          </div>
                          <div class="txtfield">
                            <input class="form__input password-input" type="password" id="edit_password_${response[i].id}"
                                   name="edit_password">
                            <span class="form__span"></span>
                            <label class="form__label" for="edit_password">Mật khẩu</label>
                            <i class="eye-icon fa fa-eye" aria-hidden="true" id="edit_eye_icon_${response[i].id}"></i>
                          </div>
                          <div class="form-problem">
                            <p class="form-problem-alert" id="edit_form_alert"></p>
                          </div>
                          <button class="btn submit-btn" type="submit" id="btn_edit_user_${response[i].id}">Cập nhật</button>
                        </div>
                        <div class="container container-under">
                          <div></div>
                          <button type="button" onclick="document.getElementById('edit_user_form_${response[i].id}').style.display='none'"
                                  class="cancelbtn btn">Hủy
                          </button>
                        </div>
                      </form>
                    </div>
                    
                    <button class="main__content--table-data-btn btn actionbtn" onclick="document.getElementById('delete_user_form_${response[i].id}').style.display='block'">
                      <i class="icon fa-solid fa-trash" data-toggle="tooltip" title="Xóa"></i>
                    </button>
                    <!-- delete script modal -->
                    <div id="delete_user_form_${response[i].id}" class="modal" style="display: none;">
                      <form action="#" class="modal-content animate" onsubmit="return false;">
                        <div class="modal__header">
                          <h2 class="modal__header--title">Xóa người dùng</h2>
                          <i class="modal__close icon fa-solid fa-circle-xmark"
                             onclick="document.getElementById('delete_user_form_${response[i].id}').style.display='none'"></i>
                        </div>
                        <div class="modal__body">
                          <p class="modal__body--text">Bạn có chắc chắn muốn xóa người dùng này?<br>LƯU Ý : Khi xóa người dùng này thì nhật ký thực thi có liên quan đến người dùng này cũng sẽ bị xóa.</p>
                          <div class="modal__body--confirm-delete-btns">
                            <button type="submit" class="modal__footer--btn btn cancelbtn"
                                    style="background-color: var(--prim)" id="btn_delete_user_${response[i].id}">Xóa
                            </button>
                            <button type="button"
                                    onclick="document.getElementById('delete_user_form_${response[i].id}').style.display='none'"
                                    class="cancelbtn btn">Hủy
                            </button>
                          </div>
                        </div>
                      </form>
                    </div>
                  </td>
                </tr>
                `);

                //eye icon to show password
                let editEyeIcon = document.getElementById(
                    `edit_eye_icon_${response[i].id}`);
                let editPassword = document.getElementById(
                    `edit_password_${response[i].id}`);
                editEyeIcon.addEventListener('click', function () {
                  if (editPassword.type === "password") {
                    editPassword.type = "text";
                    editEyeIcon.classList.add('eye-icon--active');
                    editEyeIcon.classList.add('fa-eye-slash');
                    editEyeIcon.classList.remove('fa-eye');
                  } else {
                    editPassword.type = "password";
                    editEyeIcon.classList.remove('eye-icon--active');
                    editEyeIcon.classList.remove('fa-eye-slash');
                    editEyeIcon.classList.add('fa-eye');
                  }
                });

                let btnEditUser = $(`#btn_edit_user_${response[i].id}`);
                btnEditUser.on('click', function () {
                  let editUsername = $(`#edit_username_${response[i].id}`);
                  let editPassword = $(`#edit_password_${response[i].id}`);
                  let editUserForm = $(`#edit_user_form_${response[i].id}`)[0];
                  $.ajax({
                    type: 'POST',
                    url: `${endpoint}/api/users/change-password`,
                    contentType: 'application/json',
                    data: JSON.stringify({
                      username: editUsername.text(),
                      password: editPassword.val()
                    }),
                    success: function (subData) {
                      editUserForm.style.display = 'none';
                      Toastinette.init({
                        position: 'top-center',
                        title: 'Thay đổi mật khẩu thành công',
                        message: 'Mật khẩu mới đã được cập nhật vào hệ thống',
                        type: 'success',
                        autoClose: 5000,
                        progress: true,
                      });
                      userList.empty();
                      loadNormalUserList();
                    },
                    error: function (subJqXHR, subStatus, subError) {
                      console.log(subError);
                      if (subJqXHR.status === 400) {
                        let errMsg = subJqXHR.responseText.split(':')[1].trim();
                        switch (errMsg) {
                          case 'Username is not exist!':
                            errMsg = 'Tên người dùng không tồn tại!';
                            break;
                          case 'Password is empty!':
                            errMsg = 'Chưa nhập mật khẩu mới!';
                            break;
                          case 'New password is the same as old password!':
                            errMsg = 'Mật khẩu mới trùng với mật khẩu cũ!';
                            break;
                          default:
                            break;
                        }
                        Toastinette.init({
                          position: 'top-center',
                          title: 'Thay đổi mật khẩu thất bại',
                          message: errMsg,
                          type: 'error',
                          autoClose: 5000,
                          progress: true,
                        });
                      }
                    }
                  });
                });

                let btnDeleteUser = $(`#btn_delete_user_${response[i].id}`);
                btnDeleteUser.on('click', function () {
                  let userId = $(this).attr('id').split('_')[3];
                  let deleteUserForm = $(`#delete_user_form_${response[i].id}`)[0];
                  $.ajax({
                    type: 'POST',
                    url: `${endpoint}/api/users/delete`,
                    contentType: 'application/json',
                    data: JSON.stringify({
                      id: userId
                    }),
                    success: function (subData) {
                      deleteUserForm.style.display = 'none';
                      Toastinette.init({
                        position: 'top-center',
                        title: 'Xóa người dùng thành công',
                        message: 'Người dùng đã được xóa khỏi hệ thống',
                        type: 'success',
                        autoClose: 5000,
                        progress: true,
                      });
                      userList.empty();
                      loadNormalUserList();
                    },
                    error: function (subJqXHR, subStatus, subError) {
                      console.log(subError);
                      if (subJqXHR.status === 400) {
                        let errMsg = subJqXHR.responseText.split(':')[1].trim();
                        switch (errMsg) {
                          case 'User is not exist!':
                            errMsg = 'Người dùng không tồn tại!';
                            break;
                          case 'Can not delete admin!':
                            errMsg = 'Không thể xóa admin!';
                            break;
                          default:
                            errMsg = 'Không thể thực thi câu lệnh SQL!';
                            break;
                        }
                        Toastinette.init({
                          position: 'top-center',
                          title: 'Xóa người dùng thất bại',
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

  btnRegisterUser.on('click', function () {
    if (registerUsername.val() !== '' && registerPassword.val() !== '') {
      $.ajax({
        type: 'POST',
        url: `${endpoint}/api/users/register`,
        contentType: 'application/json',
        data: JSON.stringify({
          username: registerUsername.val(),
          password: registerPassword.val(),
          role: 'ROLE_USER',
        }),
        success: function (data) {
          registerUserForm.style.display = 'none';
          Toastinette.init({
            position: 'top-center',
            title: 'Thêm người dùng thành công',
            message: 'Người dùng đã được thêm vào hệ thống',
            type: 'success',
            autoClose: 5000,
            progress: true,
          });
          userList.empty();
          loadNormalUserList();
        },
        error: function (jqXHR, status, error) {
          console.log(error);
          if (jqXHR.status === 400) {
            Toastinette.init({
              position: 'top-center',
              title: 'Thêm người dùng thất bại',
              message: 'Người dùng đã tồn tại!',
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