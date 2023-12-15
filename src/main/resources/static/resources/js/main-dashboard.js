function doAction() {
  showModal();
}

function showModal() {
  let $addQuestionModalBtn = $("#show-add-question-modal-btn");
  $addQuestionModalBtn.on('click', () => {
    let $modalAddQuestion = $("#modal-add-question");
    $modalAddQuestion.modal();
  });

  let $addQuestionBtn = $("#add-question-btn");
  $addQuestionBtn.on('click', () => {
    var form = document.getElementById("saveQuestionForm");
    var $formData = new FormData(form);
    var jsonData = {};
    $formData.forEach(function (value, key) {
      jsonData[key] = value;
    });
    submitForm(jsonData);
  });
}

function submitForm(jsonData) {
  $.ajax({
    url: '/question',
    type: 'POST',
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(jsonData),
    success: function (response) {
      console.log(response);
    },
    error: function (error) {
      console.error(error);
    }
  });
}

