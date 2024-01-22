$(document).ready(function () {
  $(document).on("click", ".edit", function () {
    var id = $(this).data("id");
    studDetails(id);
  });

  $(document).on("click", ".delete", function () {
    var id = $(this).data("id");
    $("#uid").val(id);
  });
});

function studDetails(id) {
  $.ajax({
    method: "GET",
    url: "/lecturer/detail",
    data: { uid: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        $("#id").val(response.data.user.id);
        $("#lect_id").val(response.data.lect_id);
        $("#fullname").val(response.data.user.fullname);
        $("#startDate").val(response.data.formStartDate);
        $("#qualify").val(response.data.qualification);
        $("#salary").val(response.data.salary);
        $("#sv").val(response.data.supervisor_id);
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
