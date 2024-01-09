$(document).ready(function () {
  $(document).on("click", ".edit", function () {
    var id = $(this).data("id");
    usrDetails(id);
  });

  $(document).on("click", ".delete", function () {
    var id = $(this).data("id");
    usrDetails(id);
  });
});
function usrDetails(id) {
  $.ajax({
    method: "POST",
    url: "#",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      $("#id").val(response.id);
      $("#fullname").val(response.fullname);
      $("#gender").val(response.gender);
      $("#birthdate").val(response.birth_date);
      $("#email").val(response.email);
      $("#role").val(response.role_id);
    },
    error: function (response) {
      $("#alertMsg").html(response.message);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
