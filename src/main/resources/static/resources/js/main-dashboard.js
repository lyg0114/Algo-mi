function doAction() {
  showAddModalEvent();
  addQuestionEvent();
  showGetOneModalEvent();
}

let targetForm = 'saveQuestionForm';

function showAddModalEvent() {
  let $addQuestionModalBtn = $("#show-add-question-modal-btn");
  $addQuestionModalBtn.on('click', () => {
    clearForm(targetForm);
    let $modalAddQuestion = $("#modal-add-question");
    $modalAddQuestion.modal();
  });
}

function showGetOneModalEvent() {
  let $td = $("#main-table tr > td:first-child");
  $td.on('click', function () {
        clearForm(targetForm);
        getQuestion($(this).attr("id"));
        let $modalAddQuestion = $("#modal-add-question");
        $modalAddQuestion.modal();
      }
  )
  ;
}

function clearForm(targetForm) {
  var form = document.getElementById(targetForm);
  var formElements = form.querySelectorAll('input, select');
  formElements.forEach(function (ele) {
    ele.value = '';
  });
}

function addQuestionEvent() {
  let $addQuestionBtn = $("#add-question-btn");
  $addQuestionBtn.on('click', () => {
    var form = document.getElementById("saveQuestionForm");
    var formData = new FormData(form);
    var jsonData = {};
    formData.forEach((value, key) => {
      jsonData[key] = value;
    });
    addQuestion(jsonData);
  });
}

function addQuestion(jsonData) {
  $.ajax({
    url: '/question',
    type: 'POST',
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(jsonData),
    success: function (response) {
      $.modal.close();
      alert("등록이 완료되었습니다.");
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
      console.log(response);
      setFormValues(response);
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

