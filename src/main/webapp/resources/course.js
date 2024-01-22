$(document).ready(function () {
  $(document).on("click", ".edit", function () {
    var id = $(this).data("id");
    usrDetails(id);
  });

  $(document).on("click", ".disable", function () {
    var id = $(this).data("id");
    $("#c_id").val(id);
  });
  $(document).on("click", ".enable", function () {
    var id = $(this).data("id");
    $("#c_id").val(id);
  });
});

function usrDetails(id) {
  $.ajax({
    method: "GET",
    url: "/course/detail",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        $("#c_id").val(response.data.id);
        $("#c_code").val(response.data.course_code);
        $("#c_name").val(response.data.course_name);
        $("#c_lectedt option[value='" + response.data.user_id + "']").prop(
          "selected",
          true
        );
        $("#c_credit").val(response.data.credit_hour);
        $("#c_coloredt option[value='" + response.data.color + "']").prop(
          "selected",
          true
        );
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
