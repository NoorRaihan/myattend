<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Course Assignments - ClassHive</title>
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
            value='<li class="sm:hidden">Student</li><li>Course Assignments</li>'
          />
          <jsp:param name="title" value="CSC201 Assignments" />
        </jsp:include>
        <div class="w-auto my-10">
          <div
            class="flex flex-wrap-reverse mx-4 gap-5 items-end transition-all"
          >
            <div class="basis-3/5 grow">
              <div class="card card-compact bg-base-100 shadow-xl">
                <div class="card-body">
                  <h2 class="card-title">Assignment List</h2>
                  <div class="-mx-4 sm:-mx-0">
                    <table class="min-w-full divide-y divide-gray-300">
                      <thead>
                        <tr>
                          <th
                            scope="col"
                            class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900"
                          >
                            Title
                          </th>
                          <th
                            scope="col"
                            class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                          >
                            Start Date
                          </th>
                          <th
                            scope="col"
                            class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 sm:table-cell"
                          >
                            End Date
                          </th>
                          <th
                            scope="col"
                            class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 sm:table-cell"
                          >
                            Status
                          </th>
                          <th
                            scope="col"
                            colspan="2"
                            class="px-3 py-3.5 text-center text-sm font-semibold text-gray-900"
                          >
                          </th>
                        </tr>
                      </thead>
                      <tbody class="bg-white">
                        <c:forEach var="assignment" items="${assignments}">
                          <tr class="border-t-gray-500">
                            <td class="py-4 pl-4 pr-3 text-sm font-medium text-gray-900">
                              ${assignment.getAssignment_header()}
                            </td>
                            <td class="hidden px-3 py-4 text-sm text-gray-500 sm:table-cell">
                              <!-- 01-04-2022 -->
                              ${assignment.getStarted_at()}
                            </td>
                            <td class="hidden px-3 py-4 text-sm text-gray-500 sm:table-cell">
                              <!-- 01-04-2022 -->
                              ${assignment.getEnded_at()}
                              
                            </td>
                            <td class="px-3 py-4 text-sm text-gray-500">
                              <c:set var="status" value="${empty assignment.submissions ? '' : assignment.submissions[0].status}" />

                              <c:choose>
                                  <c:when test="${status == 'PENDING'}">
                                      <span class="inline-flex items-center rounded-md bg-yellow-50 px-2 py-1 text-xs font-medium text-yellow-800 ring-1 ring-yellow-600/20 ring-inset">${status}</span>
                                  </c:when>
                                  <c:when test="${status == 'SUBMITTED'}">
                                      <span class="inline-flex items-center rounded-md bg-blue-50 px-2 py-1 text-xs font-medium text-blue-700 ring-1 ring-blue-700/10 ring-inset">${status}</span>
                                  </c:when>
                                  <c:when test="${status == 'CHECKED'}">
                                      <span class="inline-flex items-center rounded-md bg-green-50 px-2 py-1 text-xs font-medium text-green-700 ring-1 ring-green-700/10 ring-inset">${status}</span>
                                  </c:when>
                                  <c:when test="${status == 'FAIL'}">
                                      <span class="inline-flex items-center rounded-md bg-red-50 px-2 py-1 text-xs font-medium text-red-700 ring-1 ring-red-700/10 ring-inset">${status}</span>
                                  </c:when>
                                  <c:when test="${status == 'PASS'}">
                                      <span class="inline-flex items-center rounded-md bg-purple-50 px-2 py-1 text-xs font-medium text-purple-700 ring-1 ring-purple-700/10 ring-inset">${status}</span>
                                  </c:when>
                                  <c:otherwise>
                                      <span class="inline-flex items-center rounded-md bg-gray-50 px-2 py-1 text-xs font-medium text-gray-700 ring-1 ring-gray-700/10 ring-inset">NOT SUBMITTED YET</span>
                                  </c:otherwise>
                              </c:choose>
                            </td>
                            <td class="py-4 pr-0 text-nowrap text-right text-sm text-gray-500">
                              <button type="button" class="btn btn-sm btn-ghost desc" data-id="${assignment.getAssignment_id()}">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6 text-primary">
                                  <path stroke-linecap="round" stroke-linejoin="round" d="m19.5 8.25-7.5 7.5-7.5-7.5" />
                                </svg>
                              </button>
                            </td>
                          </tr>
                          <tr class="hidden" id="ass-${assignment.getAssignment_id()}">
                            <td class="py-4 px-3 bg-neutral rounded-xl" colspan="5">
                              <div class="flex">
                                ${assignment.getAssignment_desc()}
                              </div>
                              <div class="flex mb-5">
                                <c:forEach var="filename" items="${fn:split(assignment.getServer_filename(), '|')}">
                                  <a target="_blank" href="${assignment.getFile_path()}/${filename}" class="my-2 py-2">
                                    <button type="button" class="btn btn-sm btn-secondary text-primary join-item">
                                      ${assignment.getAssignment_header()}
                                    </button>
                                  </a>
                                </c:forEach>
                              </div>
                              <div class="flex gap-2">
                                <c:set var="i" value="0" />
                                <c:choose>
                                  <c:when test="${not empty assignment.getSubmissions()}">
                                    <c:forEach var="filename" items="${fn:split(assignment.getSubmissions()[0].getServer_filename(), '|')}">
                                      <div class="join rounded-full">
                                        <c:choose>
                                          <c:when test="${assignment.isActiveAssignment() or !assignment.isActiveAssignment()}">
                                            <form action="/submission/delete" method="POST">
                                              <input type="hidden" name="sub_id" id="sub_id" value="${assignment.submissions[i].getSubmission_id()}"/>
                                              <input type="hidden" name="course_id" id="course_id" value="${assignment.getCourse().getId()}"/>
                                              <input type="hidden" name="submission_filename" id="submission_filename" value="${assignment.submissions[i].server_filename}"/>
                                              <button type="submit" class="btn btn-sm btn-primary join-item text-white px-1">
                                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-5">
                                                  <path fill-rule="evenodd" d="M5.47 5.47a.75.75 0 0 1 1.06 0L12 10.94l5.47-5.47a.75.75 0 1 1 1.06 1.06L13.06 12l5.47 5.47a.75.75 0 1 1-1.06 1.06L12 13.06l-5.47 5.47a.75.75 0 0 1-1.06-1.06L10.94 12 5.47 6.53a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
                                                </svg>
                                              </button>
                                            </form>
                                            <a target="_blank" href="${assignment.submissions[i].file_path}/${assignment.submissions[i].server_filename}">
                                              <button type="button" class="btn btn-sm btn-secondary text-primary join-item">
                                                  ${assignment.submissions[i].server_filename}
                                              </button>
                                            </a>
                                          </c:when>
                                        </c:choose>
                                      </div>
                                      <c:set var="i" value="${i + 1}" />
                                    </c:forEach>
                                  </c:when>
                                </c:choose>
                                <!-- <div class="join rounded-full">
                                  <button type="button" class="btn btn-sm btn-primary join-item text-white px-1">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-5">
                                      <path fill-rule="evenodd" d="M5.47 5.47a.75.75 0 0 1 1.06 0L12 10.94l5.47-5.47a.75.75 0 1 1 1.06 1.06L13.06 12l5.47 5.47a.75.75 0 1 1-1.06 1.06L12 13.06l-5.47 5.47a.75.75 0 0 1-1.06-1.06L10.94 12 5.47 6.53a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
                                    </svg>
                                  </button>
                                  <button type="button" class="btn btn-sm btn-secondary text-primary join-item">
                                    Attachment 1
                                  </button>
                                </div> -->
                                <!-- <div class="join rounded-full">
                                  <button type="button" class="btn btn-sm btn-primary join-item text-white px-1">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-5">
                                      <path fill-rule="evenodd" d="M5.47 5.47a.75.75 0 0 1 1.06 0L12 10.94l5.47-5.47a.75.75 0 1 1 1.06 1.06L13.06 12l5.47 5.47a.75.75 0 1 1-1.06 1.06L12 13.06l-5.47 5.47a.75.75 0 0 1-1.06-1.06L10.94 12 5.47 6.53a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
                                    </svg>
                                  </button>
                                  <button type="button" class="btn btn-sm btn-secondary text-primary join-item">
                                    Attachment 2
                                  </button>
                                </div> -->
                              </div>
                              <div class="flex justify-end mt-2 gap-2">
                                <!-- if ada submission, (buang hidden) -->
                                <button type="button" class="btn btn-sm btn-accent editSub hidden" data-id="1">View / Edit Submission</button>
                                <!-- else -->
                                <c:choose>
                                  <c:when test="${empty assignment.getSubmissions()}">
                                    <c:choose>
                                      <c:when test="${!assignment.isActiveAssignment() or assignment.isActiveAssignment()}">
                                        <button type="button" class="btn btn-sm btn-primary submission" data-id="${assignment.getAssignment_id()}" data-course-id="${assignment.getCourse().getId()}">
                                          Add Submission
                                        </button>
                                      </c:when>
                                    </c:choose>
                                  </c:when>
                                </c:choose>
                              </div>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
            <div class="basis-1/5 grow">
              <div class="card card-compact bg-base-100 shadow-xl">
                <div
                  class="card-body flex md:flex-col justify-between flex-row"
                >
                  <div class="stat place-items-center basis-1/4 sm:basis-full">
                    <div class="stat-title">Total</div>
                    <div class="stat-value text-primary">${totalActiveAssignments}</div>
                    <div class="stat-desc">Active Assignments</div>
                  </div>
                </div>
              </div>
            </div>
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

          $(document).on("click", ".desc", function () {
            let id = $(this).data("id");
            $(this).toggleClass("rotate-180");
            $("#ass-" + id).toggle('fast');
          });

          // Add Submission --------------------------------------------------------------------------------------

          $(document).on("click", ".submission", function () {
            $("#subTitle").html("Add Submission");
            $('#delSub').hide();
            $("#subAttachs").hide();
            let id = $(this).data("id");
            let courseId = $(this).data("course-id");
            $('#ass_id').val(id);
            $('#addSub')[0].showModal();
            hugerte.init({
              selector: '#sub_desc',
              ui_mode: 'split'
            });
            $('#subAdd').attr('action', '/submission/create/' + id + '/' + courseId);
          });

          $('#addSub').on('close', function() {
            $('#subAdd')[0].reset();
            hugerte.remove('#sub_desc');
          });

          // View / Edit Submission --------------------------------------------------------------------------------------
          $(document).on("click", ".editSub", function () {
            $("#subTitle").html("Edit Submission");
            $("#subAttachs").show();
            let id = $(this).data("id");
            let courseId = $(this).data("course-id");
            $('#delSub').show().data("id", id);
            $('#sub_id').val(id);
            $.ajax({
              type: 'POST',
              url: '',
              data: {
                id: id
              },
              success: function(data) {
                $('#sub_desc').val(data.description);
                $('#sub_id').val(data.id);
              }
            });
            $('#addSub')[0].showModal();
            hugerte.init({
              selector: '#sub_desc',
              ui_mode: 'split'
            });
          });

          // Delete Submission --------------------------------------------------------------------------------------
          $(document).on("click", "#delSub", function () {
            let id = $(this).data("id");
            $('#del_id').val(id);
            $('#deleteSub')[0].showModal();
          });

        </script>
      </div>

      <%@ include file="../Home/drawer.jsp" %>
    </div>

    <dialog id="addSub" class="modal">
      <div class="modal-box max-w-4xl">
        <h3 class="font-bold text-lg mb-2" id="subTitle">Add New Submission</h3>
        <form id="subAdd" method="post" enctype="multipart/form-data">
          <div class="grid grid-cols-5 gap-3 items-end">
            <label class="form-control col-span-5">
              <div class="label">
                <span class="label-text">Submisson</span>
              </div>
              <textarea name="sub_desc" id="sub_desc" class="textarea textarea-primary textarea-bordered" rows="4"></textarea>
            </label>
            <label class="form-control md:col-span-2 col-span-5">
              <div class="label">
                <span class="label-text">Attachments</span>
              </div>
              <input type="file" accept=".png , .pdf, .jpeg, .jpg" name="sub_attach" class="file-input file-input-bordered file-input-primary file-input-sm w-full max-w-xs"/>
            </label>
            <div id="subAttachs" class="md:col-span-3 col-span-5 ">
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
          <div class="flex justify-between mt-5">
            <input type="hidden" name="ass_id" id="ass_id" />
            <input type="hidden" name="sub_id" id="sub_id" />
            <div>
              <button type="button" class="btn btn-sm btn-error text-white" id="delSub" data-id="1">Delete Submission</button>
            </div>
            <div>
              <button type="button" class="btn btn-sm btn-ghost text-primary" onclick="addSub.close()">Cancel</button>
              <button type="submit" class="btn btn-sm btn-primary" id="saveSub" name="action" value="add">Save</button>
            </div>
          </div>
        </form>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>

    <dialog id="deleteSub" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Delete Submission</h3>
        <p class="py-4">Do you really want to delete this submission ?</p>
        <form action="" method="post">
          <input type="hidden" name="sub_id" id="del_id"/>
          <div class="modal-action">
            <button type="button" class="btn btn-sm btn-ghost text-error" onclick="deleteSub.close()">No</button>
            <button type="submit" class="btn btn-sm btn-error text-white" name="action" value="del">Yes</button>
          </div>
        </form>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>

    <c:remove var="error" scope="session" />
    <c:remove var="success" scope="session" />
  </body>
</html>
