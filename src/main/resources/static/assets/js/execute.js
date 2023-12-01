$(function () {
  var scriptId = document.location.href.split("/").pop();
  var scriptFile = $('#script_file');
  var title = $('#title');
  var navTitle = $('#nav_title');
  var header = $('#header');
  var description = $('#description');
  var executeBtn = $('#executebtn');
  var paramInput = $('#paraminput');
  var scriptOutput = $('#scriptoutput');
  var status = $('#status');
  var username = $('#username').text();

  $.ajax({
    type: 'GET',
    url: `${endpoint}/api/scripts/${scriptId}`,
    contentType: 'application/json',
    dataType: 'json',
    success: function (data) {
      console.log(data);
      title.text(title.text() + data.title);
      navTitle.html(data.title);
      header.html(data.title);
      description.html(data.description);
      scriptFile.val(data.file);
      executeBtn.on('click', function () {
        $.ajax({
          type: 'POST',
          url: `${endpoint}/api/execute-script`,
          contentType: 'application/json',
          dataType: 'json',
          data: JSON.stringify({
            file: scriptFile.val(), input: paramInput.val(), script: scriptId,
          }),
          success: function (response) {
            console.log(response);
            if (response.status === 200) {
              scriptOutput.html(response.output);
              status.removeClass('fa-circle-xmark')
              status.addClass('fa-circle-check')
            } else {
              scriptOutput.html("Hãy kiểm tra tham số và thử lại!");
              status.removeClass('fa-circle-check')
              status.addClass('fa-circle-xmark')
            }
            $.ajax({
              type: 'POST',
              url: `${endpoint}/api/logs/add`,
              contentType: 'application/json',
              data: JSON.stringify({
                username: username,
                executeTime: response.executeTime,
                cpuTime: response.cpuTime,
                status: response.status,
                input: response.input,
                output: response.output,
                error: response.error,
                script: response.script
              }),
              success: function (subData) {
                console.log(subData);
              },
              error: function (subJqXHR, subStatus, subError) {
                console.log(subError);
              }
            });
          },
          error: function (jqXHR, status, error) {
            console.log(jqXHR);
            console.log(error);
            if (jqXHR.status === 400) {
              var errMsg = jqXHR.responseText;
              switch (errMsg) {
                case 'File is empty!':
                  errMsg = 'File script bị rỗng!';
                  break;
                case 'Input is null!':
                  errMsg = 'Input không được để trống!';
                  break;
                case 'Script requires input but input field is empty!':
                  errMsg = 'Script yêu cầu input nhưng trường input bị rỗng!';
                  break;
                case 'File not found!':
                  errMsg = 'File script không tồn tại!';
                  break;
                default:
                  break;
              }
              Toastinette.init({
                position: 'top-center',
                title: 'Thực thi script thất bại',
                message: errMsg,
                type: 'error',
                autoClose: 5000,
                progress: true,
              });
            }
          }
        });
      });
    },
    error: function (jqXHR, status, error) {
      console.log(error);
      if (jqXHR.status === 400) {
        window.location.href = `${endpoint}/not-found`;
      }
    }
  });
});