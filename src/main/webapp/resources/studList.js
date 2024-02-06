function loadStudList(id) {
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
        var studList;
        if (response.data.students.length == 0) {
          $(".ttlStud").html(response.data.students.length);
          studList = `<div class="w-full text-center justify-center py-10">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="mx-auto size-12 text-gray-400"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="m3 3 1.664 1.664M21 21l-1.5-1.5m-5.485-1.242L12 17.25 4.5 21V8.742m.164-4.078a2.15 2.15 0 0 1 1.743-1.342 48.507 48.507 0 0 1 11.186 0c1.1.128 1.907 1.077 1.907 2.185V19.5M4.664 4.664 19.5 19.5"
              />
            </svg>
            <h3 class="mt-2 text-sm font-semibold text-gray-400">
              No student registered for this course.
            </h3>
          </div>`;
        } else {
          $(".ttlStud").html(response.data.students.length);
          studList = "<table class='min-w-full divide-y divide-gray-300'>";
          studList += `<thead>
            <tr>
              <th
                scope="col"
                class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-0"
              >
                Name
              </th>
              <th
                scope="col"
                class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
              >
                Student ID
              </th>
              <th
                scope="col"
                class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
              >
                Programme
              </th>
              <th
                scope="col"
                class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
              >
                Intake
              </th>
              <th
                scope="col"
                class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 sm:table-cell"
              >
                Semester
              </th>
              <th
                scope="col"
                class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
              ></th>
            </tr>
          </thead>`;
          studList += `<tbody class="divide-y divide-gray-200 bg-white">`;
          for (var dataItem of response.data.students) {
            studList +=
              `<tr>
            <td
              class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
            >
              ` +
              dataItem.user.fullname +
              `
              <dl class="font-normal lg:hidden">
                <dd class="mt-1 truncate text-gray-700">
                  ` +
              dataItem.stud_id +
              `
                </dd>
                <dd class="mt-1 truncate text-gray-500 sm:hidden">
                  ` +
              dataItem.program +
              `
                </dd>
              </dl>
            </td>
            <td
              class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
            >
              ` +
              dataItem.stud_id +
              `
            </td>
            <td
              class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
            >
              ` +
              dataItem.program +
              `
            </td>
            <td
              class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
            >
              ` +
              dataItem.intake +
              `
            </td>
            <td
              class="hidden px-3 py-4 text-sm text-gray-500 sm:table-cell"
            >
              ` +
              dataItem.semester +
              `
            </td>
          </tr>`;
          }
          studList += "</tbody></table>";
        }

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
