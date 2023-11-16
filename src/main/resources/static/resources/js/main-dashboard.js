function doAction() {
  test();
}

function test(){
  $("#search").on('click', function() {
    $("#modal-test").modal();
  });
}