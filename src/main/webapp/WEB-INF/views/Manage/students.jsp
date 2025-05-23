<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Student Management - myAttend+</title>
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/theme-change@2.5.0/index.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.7/css/dataTables.tailwindcss.min.css" />
    <script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.js"></script>
    <script src="${contextPath}/resources/dataTables.tailwindcss.js"></script>
    <script src="${contextPath}/resources/student.js"></script>
  </head>
  <body class="bg-neutral min-h-screen">
    <div class="fixed inset-x-0 w-full bg-primary min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <jsp:include page="../Home/header.jsp">
          <jsp:param
            name="menu"
            value="<li class='sm:hidden'>Admin Menu</li><li>Student Management</li>"
          />
          <jsp:param name="title" value="Student Management" />
        </jsp:include>
        <div class="w-auto my-10">
          <div
            class="flex flex-wrap-reverse mx-4 gap-5 items-end transition-all"
          >
            <div class="basis-3/5 grow">
              <div class="card card-compact bg-base-100 shadow-xl">
                <div class="card-body">
                  <h2 class="card-title">Students</h2>
                  <div class="-mx-4 sm:-mx-0">
                    <table id="studentDT" class="min-w-full divide-y divide-gray-300">
                      <thead>
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
                          <th
                            scope="col"
                            class="relative py-3.5 pl-3 pr-4 sm:pr-0"
                          >
                            <span class="sr-only">Edit</span>
                          </th>
                        </tr>
                      </thead>
                      <tbody class="divide-y divide-gray-200 bg-white">
                      <c:forEach var="student" items="${students}">
                        <tr>
                          <td
                            class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
                          >
                            ${student.getUser().getFullname()}
                            <dl class="font-normal lg:hidden">
                              <dd class="mt-1 truncate text-gray-700">
                                <c:choose>
                                  <c:when test="${student.getStud_id() == -1}">
                                    No data yet
                                  </c:when>
                                  <c:otherwise>
                                    ${student.getStud_id()}
                                  </c:otherwise>
                                </c:choose>
                              </dd>
                              <dd class="mt-1 truncate text-gray-500 sm:hidden">
                                ${student.getProgram()}
                              </dd>
                            </dl>
                          </td>
                          <c:choose>
                            <c:when test="${student.getStud_id() == -1}">
                              <td
                                class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell text-center" colspan="4"
                              >
                                No data yet
                              </td>
                              <td class="hidden"></td>
                              <td class="hidden"></td>
                              <td class="hidden"></td>
                              <td class="hidden"></td>
                            </c:when>
                            <c:otherwise>
                              <td
                                class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                              >
                                ${student.getStud_id()}
                              </td>
                              <td
                                class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                              >
                                ${student.getProgram()}
                              </td>
                              <td
                                class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                              >
                                ${student.getIntake()}
                              </td>
                              <td
                                class="hidden px-3 py-4 text-sm text-gray-500 sm:table-cell"
                              >
                                ${student.getSemester()}
                              </td>
                            </c:otherwise>
                          </c:choose>
                          <td
                            class="px-3 py-4 text-right text-sm text-gray-500"
                          >
                            <button
                              onclick="editStud.showModal()"
                              data-id="${student.getUser().getId()}"
                              class="btn btn-ghost btn-xs edit"
                            >
                              <svg
                                xmlns="http://www.w3.org/2000/svg"
                                viewBox="0 0 24 24"
                                fill="currentColor"
                                class="size-5 fill-green-700"
                              >
                                <path
                                  d="M21.731 2.269a2.625 2.625 0 0 0-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 0 0 0-3.712ZM19.513 8.199l-3.712-3.712-8.4 8.4a5.25 5.25 0 0 0-1.32 2.214l-.8 2.685a.75.75 0 0 0 .933.933l2.685-.8a5.25 5.25 0 0 0 2.214-1.32l8.4-8.4Z"
                                />
                                <path
                                  d="M5.25 5.25a3 3 0 0 0-3 3v10.5a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3V13.5a.75.75 0 0 0-1.5 0v5.25a1.5 1.5 0 0 1-1.5 1.5H5.25a1.5 1.5 0 0 1-1.5-1.5V8.25a1.5 1.5 0 0 1 1.5-1.5h5.25a.75.75 0 0 0 0-1.5H5.25Z"
                                />
                              </svg>
                            </button>
                          </td>
                          <td
                            class="py-4 pl-3 pr-4 text-center text-sm font-medium sm:pr-0"
                          >
                            <button
                              onclick="deleteStud.showModal()"
                              data-id="${student.getUser().getId()}"
                              class="btn btn-ghost btn-xs delete"
                            >
                              <svg
                                xmlns="http://www.w3.org/2000/svg"
                                viewBox="0 0 24 24"
                                fill="currentColor"
                                class="size-5 fill-red-700"
                              >
                                <path
                                  fill-rule="evenodd"
                                  d="M16.5 4.478v.227a48.816 48.816 0 0 1 3.878.512.75.75 0 1 1-.256 1.478l-.209-.035-1.005 13.07a3 3 0 0 1-2.991 2.77H8.084a3 3 0 0 1-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 0 1-.256-1.478A48.567 48.567 0 0 1 7.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 0 1 3.369 0c1.603.051 2.815 1.387 2.815 2.951Zm-6.136-1.452a51.196 51.196 0 0 1 3.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 0 0-6 0v-.113c0-.794.609-1.428 1.364-1.452Zm-.355 5.945a.75.75 0 1 0-1.5.058l.347 9a.75.75 0 1 0 1.499-.058l-.346-9Zm5.48.058a.75.75 0 1 0-1.498-.058l-.347 9a.75.75 0 0 0 1.5.058l.345-9Z"
                                  clip-rule="evenodd"
                                />
                              </svg>
                            </button>
                              </ul>
                            </div>
                          </td>
                        </tr>
                        </c:forEach>
                        <!-- More people... -->
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
            <div class="basis-1/5 grow">
              <div class="card card-compact bg-base-100 shadow-xl">
                <div class="card-body">
                  <div class="stat place-items-center">
                    <div class="stat-title">Total Students</div>
                    <div class="stat-value text-primary">${totalStudent}</div>
                    <div class="stat-desc">myAttend+</div>
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
        </script>
      </div>

      <%@ include file="../Home/drawer.jsp" %>
    </div>

    <dialog id="editStud" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Edit Student</h3>
        <form action="/student/update" method="post">
          <input type="hidden" name="uid" id="id" value="1" />
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-full">
              <div class="label">
                <span class="label-text">Full Name</span>
              </div>
              <input
                type="text"
                name="fullname"
                id="fullname"
                class="input input-primary input-sm input-bordered" disabled
              />
            </label>
            <label class="form-control basis-2/4 grow">
              <div class="label">
                <span class="label-text">Student ID</span>
              </div>
              <input
                type="text"
                id="stud_id"
                class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                disabled
              />
            </label>
            <label class="form-control basis-2/4 grow">
              <div class="label">
                <span class="label-text">Programme</span>
              </div>
              <input
                type="text"
                name="program"
                id="program"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Intake</span>
              </div>
              <input
                type="text"
                name="intake"
                id="intake"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-2/4 grow">
              <div class="label">
                <span class="label-text">Semester</span>
              </div>
              <input
                type="number"
                name="semester"
                id="semester"
                class="input input-primary input-sm input-bordered"
              />
            </label>
          </div>
          <div class="modal-action">
            <button type="submit" class="btn btn-sm btn-primary">Save</button>
          </div>
        </form>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>
    <dialog id="deleteStud" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Delete Student</h3>
        <p class="py-4">Do you really want to delete this student's information ?</p>
        <form action="/student/delete" method="post">
          <input type="hidden" name="uid" id="deleteId" value="1" />
          <div class="modal-action">
            <button type="submit" class="btn btn-sm btn-error text-white">
              Yes
            </button>
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
