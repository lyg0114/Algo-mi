function doAction() {
  showAddModalEvent();
  crudQuestionEvent();
  showGetOneModalEvent();
  changeModalBtnStatusEvent();
}

let targetForm = 'saveQuestionForm';

function showAddModalEvent() {
  let $addQuestionModalBtn = $("#show-add-question-modal-btn");

  function setAddBtn() {
    $("#delete-question-btn").hide();
    $("#change-status-btn").hide();
    $("#update-question-btn").hide();
    $("#add-question-btn").show();
    setFormReadOnly(false);
  }

  $addQuestionModalBtn.on('click', () => {
    clearForm(targetForm);
    setAddBtn();
    let $modalAddQuestion = $("#modal-add-question");
    $modalAddQuestion.modal();
  });
}

function showGetOneModalEvent() {
  let $td = $("#main-table tr > td:first-child");

  function setModifyBtn() {
    $("#update-question-btn").hide();
    $("#add-question-btn").hide();
    $("#delete-question-btn").show();
    $("#change-status-btn").show();
  }

  $td.on('click', function () {
        clearForm(targetForm);
        setModifyBtn();
        getQuestion($(this).attr("id"));
        $("#question-id").val($(this).attr("id"));
        let $modalAddQuestion = $("#modal-add-question");
        $modalAddQuestion.modal();
      }
  );
}

function changeModalBtnStatusEvent() {
  let $changeStatusBtn = $("#change-status-btn");
  $changeStatusBtn.on('click', () => {
    function setUpdateBtn() {
      $("#add-question-btn").hide();
      $("#change-status-btn").hide();
      $("#delete-question-btn").hide();
      $("#update-question-btn").show();
    }

    setFormReadOnly(false);
    setUpdateBtn();
  });
}

function clearForm(targetForm) {
  var form = document.getElementById(targetForm);
  var formElements = form.querySelectorAll('input, select');
  formElements.forEach(function (ele) {
    ele.value = '';
  });
}

function crudQuestionEvent() {
  $("#add-question-btn").on('click', () => {
    var form = document.getElementById("saveQuestionForm");
    var formData = new FormData(form);
    var saveJsonData = {};
    formData.forEach((value, key) => {
      saveJsonData[key] = value;
    });
    saveQuestion(saveJsonData, "POST", "/question");
  });

  $("#update-question-btn").on('click', () => {
    var form = document.getElementById("saveQuestionForm");
    var formData = new FormData(form);
    var saveJsonData = {};
    formData.forEach((value, key) => {
      saveJsonData[key] = value;
    });
    saveQuestion(saveJsonData, "PUT", "/question/" + $("#question-id").val());
  });

  $("#delete-question-btn").on('click', () => {
    if(confirm("정말 삭제하시겠습니까?")){
      deleteQuestion($("#question-id").val());
    }
  });
}

function deleteQuestion(questionId) {
  $.ajax({
    url: "/question/" + questionId,
    type: "DELETE",
    success: function (response) {
      $.modal.close();
      alert("삭제가 완료되었습니다.");
      location.reload();
    },
    error: function (error) {
      console.error(error);
    }
  });
}

function saveQuestion(saveJsonData, method, url) {
  $.ajax({
    url: url,
    type: method,
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(saveJsonData),
    success: function (response) {
      $.modal.close();
      alert("작업이 완료되었습니다.");
      location.reload();
    },
    error: function (error) {
      console.error(error);
    }
  });
}

function getQuestion(targetId) {
  $.ajax({
    url: '/question/' + targetId,
    type: 'GET',
    success: function (response) {
      setFormValues(response);
      setFormReadOnly(true);
    },
    error: function (error) {
      console.error(error);
    }
  });
}

function setFormValues(response) {
  for (let key in response) {
    if (response.hasOwnProperty(key)) {
      let value = response[key];
      let $input = $(`#saveQuestionForm [name="${key}"]`);
      if ($input.length > 0) {
        $input.val(value);
      }
    }
  }
}

function setFormReadOnly(isTrue) {
  $("#saveQuestionForm :input").prop("readonly", isTrue);
  $("#saveQuestionForm select").each(function () {
    $(this).prop("disabled", isTrue);
  });
}
