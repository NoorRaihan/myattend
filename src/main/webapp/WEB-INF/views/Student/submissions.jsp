<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
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
                              Pending
                              <!-- <c:if test="${not empty assignment.getSubmissions()}">
                                  First Submission Status: ${assignment.getSubmissions()[0].getStatus()}
                              </c:if>
                              <c:if test="${empty assignment.getSubmissions()}">
                                  No submissions available for this assignment.
                              </c:if> -->
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
                              <div class="flex mb-5">
                                ${assignment.getAssignment_desc()}
                              </div>
                              <div class="flex gap-2">
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
                              </div>
                              <div class="flex justify-end mt-2 gap-2">
                                <!-- if ada submission, (buang hidden) -->
                                <button type="button" class="btn btn-sm btn-accent editSub hidden" data-id="1">View / Edit Submission</button>
                                <!-- else -->
                                <button type="button" class="btn btn-sm btn-primary submission" data-id="1">Add Submission</button>
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
                    <div class="stat-value text-primary">10</div>
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
            $('#ass_id').val(id);
            $('#addSub')[0].showModal();
            hugerte.init({
              selector: '#sub_desc',
              ui_mode: 'split'
            });
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
        <form id="subAdd" action="" method="post">
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
              <input type="file" name="sub_attach" class="file-input file-input-bordered file-input-primary file-input-sm w-full max-w-xs"/>
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
