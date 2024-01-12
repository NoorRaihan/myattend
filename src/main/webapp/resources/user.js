$(document).ready(function () {
  $(document).on("click", ".edit", function () {
    var id = $(this).data("id");
    usrDetails(id);
  });

  $(document).on("click", ".delete", function () {
    var id = $(this).data("id");
    $("#uid").val(id);
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
        $("#id").val(response.data.id);
        $("#fullname").val(response.data.fullname);
        $("#username").val(response.data.username);
        $("#gender").val(response.data.gender).trigger("change");
        $("#birthdate").val(response.data.formBirthDate);
        $("#email").val(response.data.email);
        $("#role").val(response.data.role_id).trigger("change");
        $("#dpImage").val(response.data.profile_pic);
      }
      $("#id").val(response.data.id);
      $("#fullname").val(response.data.fullname);
      $("#gender").val(response.data.gender).trigger("change");
      $("#birthdate").val(response.data.birth_date);
      $("#email").val(response.data.email);
      $("#role").val(response.data.role_id).trigger("change");
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
