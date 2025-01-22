<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Assignment Management - myAttend+</title>
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/theme-change@2.5.0/index.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/hugerte@1.0.4/hugerte.min.js"></script>
  </head>
  <body class="bg-neutral min-h-screen">
    <div class="fixed inset-x-0 w-full bg-primary min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <jsp:include page="../Home/header.jsp">
          <jsp:param
            name="menu"
            value="<li class='sm:hidden'>Lecturer</li><li>Assignment Management</li>"
          />
          <jsp:param name="title" value="Assignment Management" />
        </jsp:include>
        <div class="w-auto my-10">
          <form
            action = "/assignment/course"
            method = "get"
            class="flex ml-2 overflow-x-auto scrollbar-hide"
            aria-labelledby="profile-overview-title"
          >
            <c:forEach var="course" items="${courses}">
              <button type="submit" id="course" name="course" value="${course.getId()}">
                <div class="mx-2 card ${course.getColorConfig()} shadow-lg hover:shadow-2xl overflow-hidden w-72 flex-shrink-0">
                  <div class="card-body">
                    <div class="flex flex-row justify-between z-10">
                      <div class="flex flex-col">
                        <p class="text-2xl font-bold text-left">
                          ${course.getCourse_code()}
                        </p>
                        <p class="text-lg font-semibold text-left">
                          ${course.getCourse_name()}
                        </p>
                        <p class="text-sm text-left">
                          ${course.getCredit_hour()} Credit Hours
                        </p>
                      </div>
                    </div>
                    <div class="absolute -top-7 -right-5">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="currentColor"
                        class="size-28 fill-slate-200"
                      >
                        <path
                          d="M11.7 2.805a.75.75 0 0 1 .6 0A60.65 60.65 0 0 1 22.83 8.72a.75.75 0 0 1-.231 1.337 49.948 49.948 0 0 0-9.902 3.912l-.003.002c-.114.06-.227.119-.34.18a.75.75 0 0 1-.707 0A50.88 50.88 0 0 0 7.5 12.173v-.224c0-.131.067-.248.172-.311a54.615 54.615 0 0 1 4.653-2.52.75.75 0 0 0-.65-1.352 56.123 56.123 0 0 0-4.78 2.589 1.858 1.858 0 0 0-.859 1.228 49.803 49.803 0 0 0-4.634-1.527.75.75 0 0 1-.231-1.337A60.653 60.653 0 0 1 11.7 2.805Z"
                        />
                        <path
                          d="M13.06 15.473a48.45 48.45 0 0 1 7.666-3.282c.134 1.414.22 2.843.255 4.284a.75.75 0 0 1-.46.711 47.87 47.87 0 0 0-8.105 4.342.75.75 0 0 1-.832 0 47.87 47.87 0 0 0-8.104-4.342.75.75 0 0 1-.461-.71c.035-1.442.121-2.87.255-4.286.921.304 1.83.634 2.726.99v1.27a1.5 1.5 0 0 0-.14 2.508c-.09.38-.222.753-.397 1.11.452.213.901.434 1.346.66a6.727 6.727 0 0 0 .551-1.607 1.5 1.5 0 0 0 .14-2.67v-.645a48.549 48.549 0 0 1 3.44 1.667 2.25 2.25 0 0 0 2.12 0Z"
                        />
                        <path
                          d="M4.462 19.462c.42-.419.753-.89 1-1.395.453.214.902.435 1.347.662a6.742 6.742 0 0 1-1.286 1.794.75.75 0 0 1-1.06-1.06Z"
                        />
                      </svg>
                    </div>
                  </div>
                </div>
              </button>
            </c:forEach>
          </form>
          <div
            class="flex flex-wrap mt-4 mx-4 gap-5 items-start transition-all"
          >
            <div
              id="clsList"
              class="card card-compact md:basis-2/4 basis-full bg-white shadow-lg grow transition-all"
            >
              <div class="card-body">
                <% if (request.getParameter("course") != null) { %>
                <div class="card-title justify-between">
                  <h1>Assignments</h1>
                  <button class="btn btn-sm btn-primary" id="newAssBtn">
                    New Assignment
                  </button>
                </div>

                <table class="table-auto min-w-full divide-y divide-gray-300">
                  <thead>
                    <tr>
                      <th scope="col" class="py-3.5 px-3 text-left text-sm font-semibold text-gray-900">Title</th>
                      <th scope="col" class="py-3.5 px-3 text-left text-sm font-semibold text-gray-900 hidden md:table-cell">Start</th>
                      <th scope="col" class="py-3.5 px-3 text-left text-sm font-semibold text-gray-900 hidden md:table-cell">End</th>
                      <th scope="col" class="relative py-3.5 pl-3 pr-4 sm:pr-0"></th>
                    </tr>
                  </thead>
                  <tbody id="assList" class="divide-y divide-gray-200 bg-white">
                    <c:forEach var="assignment" items="${assignments}">
                    <tr>
                      <td class="w-full max-w-0 py-4 px-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none">
                        ${assignment.getAssignment_header()}
                        <p class="text-xs font-normal italic text-gray-400 md:hidden">${assignment.getStarted_at()} - ${assignment.getEnded_at()}</p>
                      </td>
                      <td class="px-3 py-4 text-sm text-gray-500 md:table-cell hidden">${assignment.getStarted_at()}</td>
                      <td class="px-3 py-4 text-sm text-gray-500 md:table-cell hidden">${assignment.getEnded_at()}</td>
                      <td class="py-4 px-3 text-right text-sm font-medium align-middle flex justify-end gap-2">
                        <button class="btn btn-sm btn-ghost text-info px-1 md:px-2 subList" data-id="${assignment.getAssignment_id()}">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-5">
                            <path fill-rule="evenodd" d="M7.502 6h7.128A3.375 3.375 0 0 1 18 9.375v9.375a3 3 0 0 0 3-3V6.108c0-1.505-1.125-2.811-2.664-2.94a48.972 48.972 0 0 0-.673-.05A3 3 0 0 0 15 1.5h-1.5a3 3 0 0 0-2.663 1.618c-.225.015-.45.032-.673.05C8.662 3.295 7.554 4.542 7.502 6ZM13.5 3A1.5 1.5 0 0 0 12 4.5h4.5A1.5 1.5 0 0 0 15 3h-1.5Z" clip-rule="evenodd" />
                            <path fill-rule="evenodd" d="M3 9.375C3 8.339 3.84 7.5 4.875 7.5h9.75c1.036 0 1.875.84 1.875 1.875v11.25c0 1.035-.84 1.875-1.875 1.875h-9.75A1.875 1.875 0 0 1 3 20.625V9.375ZM6 12a.75.75 0 0 1 .75-.75h.008a.75.75 0 0 1 .75.75v.008a.75.75 0 0 1-.75.75H6.75a.75.75 0 0 1-.75-.75V12Zm2.25 0a.75.75 0 0 1 .75-.75h3.75a.75.75 0 0 1 0 1.5H9a.75.75 0 0 1-.75-.75ZM6 15a.75.75 0 0 1 .75-.75h.008a.75.75 0 0 1 .75.75v.008a.75.75 0 0 1-.75.75H6.75a.75.75 0 0 1-.75-.75V15Zm2.25 0a.75.75 0 0 1 .75-.75h3.75a.75.75 0 0 1 0 1.5H9a.75.75 0 0 1-.75-.75ZM6 18a.75.75 0 0 1 .75-.75h.008a.75.75 0 0 1 .75.75v.008a.75.75 0 0 1-.75.75H6.75a.75.75 0 0 1-.75-.75V18Zm2.25 0a.75.75 0 0 1 .75-.75h3.75a.75.75 0 0 1 0 1.5H9a.75.75 0 0 1-.75-.75Z" clip-rule="evenodd" />
                          </svg>
                          <span class="hidden md:inline">Submissions</span>
                        </button>
                        <button class="btn btn-sm btn-ghost text-primary px-1 md:px-2 editAss" data-id="${assignment.getAssignment_id()}" data-title="${assignment.getAssignment_header()}" data-desc="${assignment.getAssignment_desc()}" data-date-start="${assignment.getStarted_at()}" data-date-end="${assignment.getEnded_at()}" data-allow-late="${assignment.isBypass_time_flag()}">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-5">
                            <path d="M21.731 2.269a2.625 2.625 0 0 0-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 0 0 0-3.712ZM19.513 8.199l-3.712-3.712-8.4 8.4a5.25 5.25 0 0 0-1.32 2.214l-.8 2.685a.75.75 0 0 0 .933.933l2.685-.8a5.25 5.25 0 0 0 2.214-1.32l8.4-8.4Z" />
                            <path d="M5.25 5.25a3 3 0 0 0-3 3v10.5a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3V13.5a.75.75 0 0 0-1.5 0v5.25a1.5 1.5 0 0 1-1.5 1.5H5.25a1.5 1.5 0 0 1-1.5-1.5V8.25a1.5 1.5 0 0 1 1.5-1.5h5.25a.75.75 0 0 0 0-1.5H5.25Z" />
                          </svg>
                          <span class="hidden md:inline">Edit</span>
                        </button>
                        <button class="btn btn-sm btn-ghost text-error px-1 md:px-2 deleteAss" data-id="${assignment.getAssignment_id()}">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-5">
                            <path fill-rule="evenodd" d="M16.5 4.478v.227a48.816 48.816 0 0 1 3.878.512.75.75 0 1 1-.256 1.478l-.209-.035-1.005 13.07a3 3 0 0 1-2.991 2.77H8.084a3 3 0 0 1-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 0 1-.256-1.478A48.567 48.567 0 0 1 7.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 0 1 3.369 0c1.603.051 2.815 1.387 2.815 2.951Zm-6.136-1.452a51.196 51.196 0 0 1 3.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 0 0-6 0v-.113c0-.794.609-1.428 1.364-1.452Zm-.355 5.945a.75.75 0 1 0-1.5.058l.347 9a.75.75 0 1 0 1.499-.058l-.346-9Zm5.48.058a.75.75 0 1 0-1.498-.058l-.347 9a.75.75 0 0 0 1.5.058l.345-9Z" clip-rule="evenodd" />
                          </svg>
                          <span class="hidden md:inline">Delete</span>
                        </button>
                      </td>
                    </tr>
                    </c:forEach>
                  </tbody>
                </table>
                <% } else { %>
                <div class="w-full text-center justify-center py-10">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-12 text-gray-400 mx-auto">
                      <path stroke-linecap="round" stroke-linejoin="round" d="M11.35 3.836c-.065.21-.1.433-.1.664 0 .414.336.75.75.75h4.5a.75.75 0 0 0 .75-.75 2.25 2.25 0 0 0-.1-.664m-5.8 0A2.251 2.251 0 0 1 13.5 2.25H15c1.012 0 1.867.668 2.15 1.586m-5.8 0c-.376.023-.75.05-1.124.08C9.095 4.01 8.25 4.973 8.25 6.108V8.25m8.9-4.414c.376.023.75.05 1.124.08 1.131.094 1.976 1.057 1.976 2.192V16.5A2.25 2.25 0 0 1 18 18.75h-2.25m-7.5-10.5H4.875c-.621 0-1.125.504-1.125 1.125v11.25c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125V18.75m-7.5-10.5h6.375c.621 0 1.125.504 1.125 1.125v9.375m-8.25-3 1.5 1.5 3-3.75" />
                    </svg>
                    <h3 class="mt-2 text-sm font-semibold text-gray-400">
                      Please select a course to view assignments
                    </h3>
                </div>
                <% } %>
              </div>
            </div>

            <% if (request.getParameter("course") != null) { %>
            <div id="subCard" class="card card-compact md:basis-1/4 basis-full bg-white shadow-lg grow transition-all hidden">
              <div class="card-body">
                <div class="card-title justify-between">
                  <h1>Submissions</h1>
                </div>
                <ul role="list" class="divide-y divide-gray-100">
                  <li class="flex justify-between gap-x-6 py-5">
                    <div class="flex min-w-0 gap-x-4">
                      <div class="min-w-0 flex-auto">
                        <p class="text-sm/6 font-semibold text-gray-900">Student 1 <span class="text-xs text-gray-400 font-normal italic">(2024123456)</span></p>
                        <p class="truncate text-xs/5 text-gray-500">Submitted at 01:00 am</p>
                      </div>
                    </div>
                    <div class="shrink-0 flex items-center">
                      <button class="btn btn-sm btn-ghost text-primary px-1 md:px-2 viewSub" data-id="">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-5">
                          <path d="M12 15a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z" />
                          <path fill-rule="evenodd" d="M1.323 11.447C2.811 6.976 7.028 3.75 12.001 3.75c4.97 0 9.185 3.223 10.675 7.69.12.362.12.752 0 1.113-1.487 4.471-5.705 7.697-10.677 7.697-4.97 0-9.186-3.223-10.675-7.69a1.762 1.762 0 0 1 0-1.113ZM17.25 12a5.25 5.25 0 1 1-10.5 0 5.25 5.25 0 0 1 10.5 0Z" clip-rule="evenodd" />
                        </svg>
                        <span class="hidden md:inline">View</span>
                      </button>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
            <% } %>
          </div>
        </div>

        <div id="alert" class="toast toast-top toast-end z-50 hidden">
          <div class="alert alert-warning">
            <span id="alertMsg">Error</span>
          </div>
        </div>
        <div id="succ" class="toast toast-top toast-end z-50 hidden">
          <div class="alert alert-success">
            <span id="succMsg">Success</span>
          </div>
        </div>
        <script>
          let error = "${sessionScope.error}";
          if (error != "") {
            $("#alertMsg").html(error);
            $("#alert").show().delay(3000).fadeOut();
          }
          let succ = "${sessionScope.success}";
          if (succ != "") {
            $("#succMsg").html(succ);
            $("#succ").show().delay(3000).fadeOut();
          }

          $(document).ready(function() {

            // add n edit assignment ----------------------------------------------------------------------------
            const modal = document.getElementById('addAss');

            $('#newAssBtn').on('click', function() {
              $('#saveAss').val('add');
              $('#assAttachs').hide();
              modal.showModal();
              hugerte.init({
                selector: '#ass_desc',
                ui_mode: 'split'
              });
            });

            $('.editAss').on('click', function() {
              $('#ass-modal-title').html('Edit Assignment');
              $('#formAction').val('update');
              $('#assAttachs').show();
              let id = $(this).data('id');
              let title = $(this).data('title');
              let desc = $(this).data('desc');
              let start_at = $(this).data('date-start');
              let ended_at = $(this).data('date-end');
              let allow_late = $(this).data('allow-late');
              // Convert the date to "YYYY-MM-DDTHH:MM" for datetime-local input
              let startAtFormatted = start_at.replace(' ', 'T').slice(0, 16); // "2025-01-17T14:30"
              let endAtFormatted = ended_at.replace(' ', 'T').slice(0, 16);   // "2025-01-18T16:00"
              $('#assAdd').attr('action', '/assignment/update/'+id);
              console.log($('#assAdd').attr('action')); // Check the new form action

              $('#ass_id').val(id);
              $('#ass_title').val(title);
              $('#ass_desc').val(desc);
              $('#ass_start').val(startAtFormatted);
              $('#ass_end').val(endAtFormatted);
              $('#ass_late').val(allow_late);
              $('#saveAss').val('Edit');
              // Set the checkbox state
              if (allow_late) {
                $('#ass_late').prop('checked', true); // Check the checkbox
              } else {
                $('#ass_late').prop('checked', false); // Uncheck the checkbox
              }

              $('#is_update').val(1);
              
              
              // $.ajax({
              //   type: 'POST',
              //   url: '',
              //   data: {
              //     id: id
              //   },
              //   success: function(data) {
              //     $('#ass_title').val(data.title);
              //     $('#ass_desc').val(data.desc);
              //     $('#ass_start').val(data.start_at);
              //     $('#ass_end').val(data.ended_at);
              //     $('#ass_late').val(data.allow_late);
              //     $('#ass_id').val(data.id);
              //   }
              // });

              modal.showModal();
              hugerte.init({
                selector: '#ass_desc',
                ui_mode: 'split'
              });
            });

            modal.addEventListener('close', (event) => {
              $('#assAdd')[0].reset();
              hugerte.remove('#ass_desc');
            });

            // delete assignment -------------------------------------------------------------------------------
            $('.deleteAss').on('click', function() {
              let id = $(this).data('id');
              $('#del_id').val(id);
              $('#deleteAss')[0].showModal();
            });

            // view submissions --------------------------------------------------------------------------------
            $('.subList').on('click', function() {
              $('#subCard').toggle('fast');
            })

            $('.viewSub').on('click', function() {
              let id = $(this).data('id');
              $('#viewSub')[0].showModal();
            });
          });
        </script>
      </div>

      <%@ include file="../Home/drawer.jsp" %>
    </div>

    <dialog id="addAss" class="modal">
      <div class="modal-box max-w-4xl">
        <h3 id="ass-modal-title" class="font-bold text-lg mb-2">Add New Assignment</h3>
        <form id="assAdd" action="/assignment/create/${course.getId()}" method="post" enctype="multipart/form-data">
          <input type="hidden" name="action" id="formAction" value="create" />
          <div class="grid grid-cols-5 gap-3 items-end">
            <label class="form-control col-span-5">
              <div class="label">
                <span class="label-text">Assignment Title</span>
              </div>
              <input
                type="text"
                id="ass_title"
                name="ass_title"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control md:col-span-2 col-span-5">
              <div class="label">
                <span class="label-text">Date & Time Start</span>
              </div>
              <input type="datetime-local" id="ass_start" name="ass_start" class="input input-primary input-sm input-bordered"/>
            </label>
            <label class="form-control md:col-span-2 col-span-5">
              <div class="label">
                <span class="label-text">Date & Time End</span>
              </div>
              <input type="datetime-local" id="ass_end" name="ass_end" class="input input-primary input-sm input-bordered"/>
            </label>
            <label class="form-control md:col-span-1 col-span-5">
              <div class="label cursor-pointer">
                <span class="label-text">Allow Late</span>
                <input type="checkbox" id="ass_late" name="ass_late" class="toggle toggle-primary"/>
              </div>
            </label>
            <label class="form-control col-span-5">
              <div class="label">
                <span class="label-text">Description</span>
              </div>
              <textarea name="ass_desc" id="ass_desc" class="textarea textarea-primary textarea-bordered" rows="4"></textarea>
            </label>
            <label class="form-control md:col-span-2 col-span-5">
              <div class="label">
                <span class="label-text">Attachments</span>
              </div>
              <input accept=".png, .pdf, .jpeg, .jpg" type="file" name="ass_attach" class="file-input file-input-bordered file-input-primary file-input-sm w-full max-w-xs"/>
            </label>
            <div id="assAttachs" class="md:col-span-3 col-span-5 ">
              <div class="flex flex-row gap-x-2">
                <!-- if ada attachments -->

                <div class="join rounded-full">
                  <button type="button" class="btn btn-sm btn-primary join-item text-white px-1">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-5">
                      <path fill-rule="evenodd" d="M5.47 5.47a.75.75 0 0 1 1.06 0L12 10.94l5.47-5.47a.75.75 0 1 1 1.06 1.06L13.06 12l5.47 5.47a.75.75 0 1 1-1.06 1.06L12 13.06l-5.47 5.47a.75.75 0 0 1-1.06-1.06L10.94 12 5.47 6.53a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
                    </svg>
                  </button>
                  <button type="button" class="btn btn-sm btn-secondary text-primary join-item">
                    Attachment 1
                  </button>
                </div>
                <div class="join rounded-full">
                  <button type="button" class="btn btn-sm btn-primary join-item text-white px-1">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-5">
                      <path fill-rule="evenodd" d="M5.47 5.47a.75.75 0 0 1 1.06 0L12 10.94l5.47-5.47a.75.75 0 1 1 1.06 1.06L13.06 12l5.47 5.47a.75.75 0 1 1-1.06 1.06L12 13.06l-5.47 5.47a.75.75 0 0 1-1.06-1.06L10.94 12 5.47 6.53a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
                    </svg>
                  </button>
                  <button type="button" class="btn btn-sm btn-secondary text-primary join-item">
                    Attachment 2
                  </button>
                </div>

                <!-- end if ada attachments -->
              </div>
            </div>
          </div>
          <div class="modal-action">
            <input type="hidden" name="ass_id" id="ass_id" />
            <input type="hidden" name="course_id" id="course_id" value="${course.getId()}"/>
            <input type="hidden" name="is_update" id="is_update" value="0">
            <button type="button" class="btn btn-sm btn-ghost text-primary" onclick="addAss.close()">Cancel</button>
            <button type="submit" class="btn btn-sm btn-primary" id="saveAss" name="action" value="add">Save</button>
          </div>
        </form>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>

    <dialog id="deleteAss" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Delete Assignment</h3>
        <p class="py-4">Do you really want to delete this assignment ?</p>
        
        <form action="/assignment/delete" method="post">
          <input type="hidden" name="ass_id" id="del_id" value="${assignment.getAssignment_id()}"/>
          <input type="hidden" name="course_id" id="del_id" value="${course.getId()}"/>
          <div class="modal-action">
            <button type="button" class="btn btn-sm btn-ghost text-error" onclick="deleteAss.close()">No</button>
            <button type="submit" class="btn btn-sm btn-error text-white">Yes</button>
          </div>
        </form>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>

    <dialog id="viewSub" class="modal">
      <div class="modal-box max-w-4xl">
        <h3 class="font-bold text-lg">View Submisssion</h3>
        <p class="font-semibold italic text-sm text-gray-500 mb-2">
          by <span class="font-bold">Student 1</span> at <span class="font-bold">01:00 am</span>
        </p>
        <div class="py-4 px-3 bg-neutral rounded-xl">
          <div class="flex mb-5">
            Assignment description bla bla bla
          </div>
          <div class="flex gap-2">
            <div class="btn btn-sm btn-secondary text-primary rounded-full">
              Attachment 1
            </div>
            <div class="btn btn-sm btn-secondary text-primary rounded-full">
              Attachment 2
            </div>
          </div>
        </div>
        <div class="modal-action">
          <button type="button" class="btn btn-sm btn-primary" onclick="viewSub.close()">Close</button>
        </div>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>

    <c:remove var="error" scope="session" />
    <c:remove var="success" scope="session" />
  </body>
</html>
