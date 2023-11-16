function doAction() {
  showModal();
}

function showModal() {
  let $addQuestionBtn = $("#add-question-btn");
  $addQuestionBtn.on('click', () => {
    let $modalAddQuestion = $("#modal-add-question");
    $modalAddQuestion.modal();
  });
}
