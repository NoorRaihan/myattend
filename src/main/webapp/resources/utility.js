$(document).ready(function () {
  $(document).on("click", ".roleEdit", function () {
    var id = $(this).data("id");
    roleDetails(id);
  });

  $(document).on("click", ".roleDelete", function () {
    var id = $(this).data("id");
    $("#roleId").val(id);
  });

  $(document).on("click", ".statusEdit", function () {
    var id = $(this).data("id");
    statusDetails(id);
  });

  $(document).on("click", ".statusDelete", function () {
    var id = $(this).data("id");
    $("#statusId").val(id);
  });
});

function roleDetails(id) {
  $.ajax({
    method: "GET",
    url: "",
    data: { role_id: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        $("#role_id").val(response.data.id);
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

function statusDetails(id) {
  $.ajax({
    method: "GET",
    url: "",
    data: { status_id: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        $("#status_id").val(response.data.id);
        $("#status_name").val(response.data.status_name);
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
