$(document).ready(function () {
  $(document).on("click", ".course", function () {
    var id = $(this).data("id");
    loadClsList(id);
  });

  $(document).on("click", ".classF", function () {
    var id = $(this).data("cid");
    classAttend(id);
  });

  $(document).on("click", ".edit", function () {
    var id = $(this).data("id");
    classDetails(id);
  });
});

function classDetails(id) {
  $.ajax({
    method: "GET",
    url: "",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        $("#cls_id").val(response.data.id);
        $("#c_id").val(response.data.course_id);
        $("#cls_date").val(response.data.date);
        $("#cls_sTime").val(response.data.start_time);
        $("#cls_eTime").val(response.data.end_time);
        $("#cls_venue").val(response.data.venue);
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}

function loadClsList(id) {
  $.ajax({
    method: "GET",
    url: "",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        $("#c_id").val(response.data.id);
        var count = 1;
        var classList;
        for (var dataItem of response.data[1].classes) {
          classList +=
            "<tr class='classF' data-cid='" +
            dataItem.id +
            "'>" +
            "<td class='w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0'>Seminar " +
            count +
            "<dl class='font-normal lg:hidden'>" +
            "<dd class='mt-1 truncate text-gray-700'>" +
            dataItem.date +
            "</dd>" +
            "<dd class='mt-1 truncate text-gray-500 sm:hidden'>" +
            dataItem.time +
            "</dd>" +
            "</dl>" +
            "</td>" +
            "<td class='hidden px-3 py-4 text-sm text-gray-500 lg:table-cell'>" +
            dataItem.date +
            "</td>" +
            "<td class='hidden px-3 py-4 text-sm text-gray-500 sm:table-cell'>" +
            dataItem.time +
            "</td>" +
            "<td class='px-3 py-4 text-nowrap text-sm text-gray-500'>" +
            dataItem.venue +
            "</td>" +
            "<td class='py-4 pr-4 text-nowrap text-left text-sm text-gray-500 pl-0'>" +
            "<div class='dropdown dropdown-left dropdown-hover'>" +
            "<div tabindex='0' role='button' class='btn btn-ghost btn-xs'>" +
            "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='currentColor' class='size-6'>" +
            "<path fill-rule='evenodd' d='M10.5 6a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0Zm0 6a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0Zm0 6a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0Z' clip-rule='evenodd'/>" +
            "</svg>" +
            "</div>" +
            "<ul tabindex='0' class='dropdown-content z-[1] menu p-2 shadow-lg bg-neutral rounded-box w-fit'>" +
            "<li><label for='detail-drawer' data-id='" +
            dataItem.id +
            "' class='qr'>Attendance QR</label></li>" +
            "<li><a onclick='editCourse.showModal()' data-id='" +
            dataItem.id +
            "' class='edit'>Edit</a></li>" +
            "<li><a onclick='disableCourse.showModal()' class='text-red-600 disable' data-id='" +
            dataItem.id +
            "'>Disable</a></li>" +
            "</ul>" +
            "</div>" +
            "</td>" +
            "</tr>";
          count++;
        }
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

function classAttend(id) {
  $.ajax({
    method: "GET",
    url: "",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        $("#c_id").val(response.data.id);
        var studList =
          "<ul role='list' class='flex-1 divide-y divide-gray-200 overflow-y-auto'>";
        for (var student of response.data.students) {
          studList +=
            `<li>
            <div class='group relative flex items-center px-5 py-6'>
              <div class='-m-1 block flex-1 p-1'>
                <div class='absolute inset-0' aria-hidden='true'></div>
                <div class='relative flex min-w-0 flex-1 items-center'>
                  <div class='truncate'>
                    <p class='truncate text-sm font-medium text-gray-900'>` +
            student.name +
            `</p>
                    <p class='truncate text-sm text-gray-500'>` +
            student.id +
            `</p>
                  </div>
                </div>
              </div>
              `;
          if (student.status == 1) {
            `<div class='relative ml-2 inline-block flex-shrink-0 tooltip' data-tip='Present'>
                <svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='currentColor' class='size-7 fill-green-500'>
                  <path fill-rule='evenodd' d='M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12Zm13.36-1.814a.75.75 0 1 0-1.22-.872l-3.236 4.53L9.53 12.22a.75.75 0 0 0-1.06 1.06l2.25 2.25a.75.75 0 0 0 1.14-.094l3.75-5.25Z' clip-rule='evenodd'>
                </svg>
              </div>`;
          } else if (student.status == 2) {
            `<div
            class="relative ml-2 inline-block flex-shrink-0 tooltip"
            data-tip="Absent"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="currentColor"
              class="size-7 fill-red-500"
            >
              <path
                fill-rule="evenodd"
                d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25Zm-1.72 6.97a.75.75 0 1 0-1.06 1.06L10.94 12l-1.72 1.72a.75.75 0 1 0 1.06 1.06L12 13.06l1.72 1.72a.75.75 0 1 0 1.06-1.06L13.06 12l1.72-1.72a.75.75 0 1 0-1.06-1.06L12 10.94l-1.72-1.72Z"
                clip-rule="evenodd"
              />
            </svg>
          </div>`;
          } else if (student.status == 3) {
            `<div
            class="relative ml-2 inline-block flex-shrink-0 tooltip"
            data-tip="Absent with reason"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="currentColor"
              class="size-7 fill-blue-500"
            >
              <path
                fill-rule="evenodd"
                d="M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12ZM12 8.25a.75.75 0 0 1 .75.75v3.75a.75.75 0 0 1-1.5 0V9a.75.75 0 0 1 .75-.75Zm0 8.25a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5Z"
                clip-rule="evenodd"
              />
            </svg>
          </div>`;
          }
          `</div>
          </li>`;
        }
        studList += "</ul>";
        $("#studentList").html(studList);
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
