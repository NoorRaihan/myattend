$(document).ready(function () {
  $(document).on("click", ".edit", function () {
    var id = $(this).data("id");
    studDetails(id);
  });

  $(document).on("click", ".delete", function () {
    var id = $(this).data("id");
    $("#id").val(id);
  });
});
function studDetails(id) {
  $.ajax({
    method: "GET",
    url: "/student/detail",
    data: { uid: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        $("#id").val(response.data.id);
        $("#fullname").val(response.data.user.fullname);
        $("#username").val(response.data.user.username);
        $("#program").val(response.data.program);
        $("#intake").val(response.data.intake);
        $("#semseter").val(response.data.semester);
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
