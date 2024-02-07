$(document).ready(function () {
  $(document).on("click", ".edit", function () {
    var id = $(this).data("id");
    studDetails(id);
  });

  $(document).on("click", ".delete", function () {
    var id = $(this).data("id");
    console.log(id);
    $("#deleteId").val(id);
  });

  $("#studentDT").DataTable();
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
        $("#id").val(response.data.user.id);
        $("#stud_id").val(response.data.stud_id);
        $("#fullname").val(response.data.user.fullname);
        $("#program").val(response.data.program);
        $("#intake").val(response.data.intake);
        $("#semester").val(response.data.semester);
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
