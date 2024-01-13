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
function usrDetails(id) {
  $.ajax({
    method: "POST",
    url: "#",
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
        $("#startDate").val(response.data.start_date);
        $("#qualify").val(response.data.qualification);
        $("#salary").val(response.data.salary);
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
