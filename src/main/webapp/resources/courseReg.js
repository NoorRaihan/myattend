$(document).ready(function () {
  $(document).on("click", ".unreg", function () {
    var id = $(this).data("id");
    $("#unreg_id").val(id);
  });
  $(document).on("click", ".reg", function () {
    var id = $(this).data("id");
    $("#reg_id").val(id);
  });
});
