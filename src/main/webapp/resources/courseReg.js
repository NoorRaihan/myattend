$(document).ready(function () {
  // Attach a click event to elements with class "unreg"
  $(document).on("click", ".unreg", function () {
    // Get the data-id attribute value of the clicked element and store it in the variable id
    var id = $(this).data("id");
    // Set the value of the element with id "unreg_id" to the value of the variable id
    $("#unreg_id").val(id);
  });
  // Attach a click event to elements with class "reg"
  $(document).on("click", ".reg", function () {
    // Get the data-id attribute value of the clicked element and store it in the variable id
    var id = $(this).data("id");
    // Set the value of the element with id "reg_id" to the value of the variable id
    $("#reg_id").val(id);
  });
});
