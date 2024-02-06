$(document).ready(function () {
  $(document).on("click", "#profileBtn", function () {
    $("#profileBtn").addClass("hidden");
    $("#dashboardBtn").removeClass("hidden");
    $("#dashboard").addClass("hidden");
    $("#profile").removeClass("hidden");
  });

  $(document).on("click", "#dashboardBtn", function () {
    $("#dashboardBtn").addClass("hidden");
    $("#profileBtn").removeClass("hidden");
    $("#profile").addClass("hidden");
    $("#dashboard").removeClass("hidden");
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
