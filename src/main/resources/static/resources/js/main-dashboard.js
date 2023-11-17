function doAction() {
  showModal();
}

function showModal() {
  let $addQuestionBtn = $("#show-add-question-modal-btn");
  $addQuestionBtn.on('click', () => {
    let $modalAddQuestion = $("#modal-add-question");
    $modalAddQuestion.modal();
  });
}
