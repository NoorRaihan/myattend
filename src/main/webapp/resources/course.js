// Wait for the document to be fully loaded
$(document).ready(function () {
  // Attach a click event to elements with class "edit"
  $(document).on("click", ".edit", function () {
    // Get the data-id attribute of the clicked element
    var id = $(this).data("id");
    // Call the usrDetails function with the retrieved id
    usrDetails(id);
  });

  // Attach a click event to elements with class "disable"
  $(document).on("click", ".disable", function () {
    // Get the data-id attribute of the clicked element
    var id = $(this).data("id");
    // Set the value of the elements with ids "disable_id" and "delete_id" to the retrieved id
    $("#disable_id").val(id);
    $("#delete_id").val(id);
  });

  // Attach a click event to elements with class "enable"
  $(document).on("click", ".enable", function () {
    // Get the data-id attribute of the clicked element
    var id = $(this).data("id");
    // Set the value of the element with id "enable_id" to the retrieved id
    $("#enable_id").val(id);
  });

  // Attach a click event to elements with class "dtl"
  $(document).on("click", ".dtl", function () {
    // Get the data-id attribute of the clicked element
    var id = $(this).data("id");
    // Call the courseDetails function with the retrieved id
    courseDetails(id);
  });

  // Attach a click event to elements with class "rmStud"
  $(document).on("click", ".rmStud", function () {
    // Get the data-id and data-cid attributes of the clicked element
    var id = $(this).data("id");
    var cid = $(this).data("cid");
    // Set the value of the elements with ids "s_id" and "co_id" to the retrieved ids
    $("#s_id").val(id);
    $("#co_id").val(cid);
  });

  // Initialize the DataTable for the element with id "courseDT"
  $("#courseDT").DataTable();
});

// This function makes an AJAX request to retrieve user details based on the provided 'id' parameter
function usrDetails(id) {
  // Send a GET request to the "/course/detail" endpoint with the 'id' parameter
  $.ajax({
    method: "GET",
    url: "/course/detail",
    data: { id: id },
    dataType: "json",
    // If the request is successful, execute the following function
    success: function (response) {
      // Check if the response status is "error"
      if (response.respStatus == "error") {
        // Construct an error message using the response code and message
        let msg = "(" + response.respCode + ") " + response.respMessage;
        // Display the error message in the designated element, show it, and then hide it after 5 seconds
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        // If the response status is not "error", populate the form fields with the retrieved data
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
    // If the request encounters an error, execute the following function
    error: function (response) {
      // Construct an error message using the response code and message
      let msg = "(" + response.respCode + ") " + response.respMessage;
      // Display the error message in the designated element, show it, and then hide it after 5 seconds
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}

// a function to fetch course details using an AJAX GET request and view it on the course details sidebar
function courseDetails(id) {
  $.ajax({
    method: "GET",
    url: "/student/course",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        $("#detailBG").removeClass(
          "bg-gradient-to-br from-green-300 to-emerald-500 from-lime-300 to-lime-500 from-purple-300 to-purple-500 from-blue-300 to-blue-500 from-cyan-300 to-cyan-500 from-fuchsia-300 to-fuchsia-500 from-pink-300 to-pink-500 from-yellow-300 to-yellow-500 from-orange-300 to-orange-500 from-red-300 to-red-500"
        );
        $("#detailBG").addClass(response.data.course.colorConfig);
        $("#courseName").html(response.data.course.course_name);
        $("#courseCode").html(response.data.course.course_code);
        $("#courseLecturer").html(response.data.course.course_lecturer);
        var list = '<ul role="list" class="divide-y divide-gray-200">';
        for (const dataItem of response.data.students) {
          list +=
            "<li>" +
            "<div class='group relative flex items-center px-5 py-6'>" +
            "<div class='-m-1 block flex-1 p-1'>" +
            "<div class='absolute inset-0 group-hover:bg-neutral' aria-hidden='true'></div>" +
            "<div class='relative flex min-w-0 flex-1 items-center'>" +
            "<div class='ml-4 truncate'>" +
            "<p class='truncate text-sm font-medium text-gray-900'>" +
            dataItem.user.fullname +
            "</p>" +
            "<p class='truncate text-sm text-gray-500'>Student ID: " +
            dataItem.stud_id +
            "</p>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='relative ml-2 inline-block flex-shrink-0 text-left'>" +
            "<button type='button' class='group relative inline-flex h-8 w-8 items-center justify-center rounded-full bg-white focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 rmStud' onclick='removeStudent.showModal()' data-id='" +
            dataItem.user.id +
            "' data-cid='" +
            response.data.course.id +
            "'>" +
            "<span class='absolute -inset-1.5'></span>" +
            "<span class='flex h-full w-full items-center justify-center rounded-full'>" +
            "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='currentColor' class='w-6 h-6 text-red-500'>" +
            "<path fill-rule='evenodd' d='M16.5 4.478v.227a48.816 48.816 0 0 1 3.878.512.75.75 0 1 1-.256 1.478l-.209-.035-1.005 13.07a3 3 0 0 1-2.991 2.77H8.084a3 3 0 0 1-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 0 1-.256-1.478A48.567 48.567 0 0 1 7.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 0 1 3.369 0c1.603.051 2.815 1.387 2.815 2.951Zm-6.136-1.452a51.196 51.196 0 0 1 3.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 0 0-6 0v-.113c0-.794.609-1.428 1.364-1.452Zm-.355 5.945a.75.75 0 1 0-1.5.058l.347 9a.75.75 0 1 0 1.499-.058l-.346-9Zm5.48.058a.75.75 0 1 0-1.498-.058l-.347 9a.75.75 0 0 0 1.5.058l.345-9Z' clip-rule='evenodd'/>" +
            "</svg>" +
            "</span>" +
            "</button>" +
            "</div>" +
            "</div>" +
            "</li>";
        }
        list += "</ul>";
        $("#studentList").html(list);
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });

  $.ajax({
    method: "GET",
    url: "/class/course",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        var classList =
          "<ul role='list' class='flex-1 divide-y divide-gray-200 overflow-y-auto'>";
        for (var dataItem of response.data.classes) {
          classList +=
            "<li>" +
            "<div class='group relative flex items-center px-5 py-6'>" +
            "<div class='-m-1 block flex-1 p-1'>" +
            "<div class='absolute inset-0 group-hover:bg-neutral' aria-hidden='true'></div>" +
            "<div class='relative flex min-w-0 flex-1 items-center'>" +
            "<div class='ml-4 truncate'>" +
            "<p class='truncate text-sm font-medium text-gray-900'>" +
            dataItem.class_desc +
            "</p>" +
            "<p class='truncate text-sm text-gray-500'>" +
            dataItem.formattedClassDate +
            "</p>" +
            "<p class='truncate text-sm text-gray-500'>" +
            dataItem.formStartTime +
            " - " +
            dataItem.formEndTime;
          "</p>" + "</div>" + "</div>" + "</div>" + "</div>" + "</li>";
        }
        classList += "</ul>";
        $("#classList").html(classList);
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
