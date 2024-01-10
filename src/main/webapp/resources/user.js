$(document).ready(function () {
  $(document).on("click", ".edit", function () {
    var id = $(this).data("id");
    console.log("test")

  });

  $(document).on("click", ".delete", function () {
    var id = $(this).data("id");
    $("#id").val(id);
    console.log("test2");
    $("#uid").val(id);
  });
});

function usrDetails(id) {
  $.ajax({
    method: "POST",
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
        $("#gender").val(response.data.gender);
        $("#birthdate").val(response.data.birth_date);
        $("#email").val(response.data.email);
        $("#role").val(response.data.role_id);
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
