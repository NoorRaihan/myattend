$(document).ready(function () {
  $(document).on("click", ".roleEdit", function () {
    var id = $(this).data("id");
    roleDetails(id);
  });

  $(document).on("click", ".roleDelete", function () {
    var id = $(this).data("id");
    $("#roleid").val(id);
  });
});

function roleDetails(id) {
  $.ajax({
    method: "GET",
    url: "/utility/detail",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        $("#role_id").val(response.data.id);
        $("#oriId").val(response.data.id);
        $("#role_name").val(response.data.role_name);
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
