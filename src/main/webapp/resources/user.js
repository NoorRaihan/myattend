$(document).ready(function () {
  $(document).on("click", ".edit", function () {
    var id = $(this).data("id");
    usrDetails(id);
  });

  $(document).on("click", ".delete", function () {
    var id = $(this).data("id");
    $("#deleteId").val(id);
  });
});

function usrDetails(id) {
  $.ajax({
    method: "GET",
    url: "/user/detail",
    data: { uid: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        $("#editId").val(response.data.id);
        $("#fullname").val(response.data.fullname);
        $("#username").val(response.data.username);
        $("#gender option[value='" + response.data.gender + "']").prop(
          "selected",
          true
        );
        $("#birthdate").val(response.data.formBirthDate);
        $("#email").val(response.data.email);
        $("#role option[value='" + response.data.role_id + "']").prop(
          "selected",
          true
        );
        $("#dpImage").val(response.data.profile_pic);
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
