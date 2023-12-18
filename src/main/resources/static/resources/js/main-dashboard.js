function doAction() {
  showAddModalEvent();
  addQuestionEvent();
  showGetOneModalEvent();
}

function showAddModalEvent() {
  let $addQuestionModalBtn = $("#show-add-question-modal-btn");
  $addQuestionModalBtn.on('click', () => {
    clearForm('saveQuestionForm');
    let $modalAddQuestion = $("#modal-add-question");
    $modalAddQuestion.modal();
  });
}

function showGetOneModalEvent() {
  let $td = $("#main-table tr > td:first-child");
  $td.on('click', () => {
    alert("SUCCESS");
  });
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

