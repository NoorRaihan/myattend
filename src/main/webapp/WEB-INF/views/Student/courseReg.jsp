<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Course Registeration - myAttend+</title>
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/theme-change@2.5.0/index.js"></script>
    <script src="${contextPath}/resources/courseReg.js"></script>
  </head>
  <body class="bg-neutral min-h-screen">
    <div class="fixed inset-x-0 w-full bg-primary min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <jsp:include page="../Home/header.jsp">
          <jsp:param
            name="menu"
            value="<li class='sm:hidden'>Student</li><li>Course Registeration</li>"
          />
          <jsp:param name="title" value="Course Registeration" />
        </jsp:include>
        <div class="w-auto my-10">
          <div class="flex flex-wrap mx-4 gap-5 items-start transition-all">
            <div class="basis-4/5 grow">
              <div class="card card-compact bg-neutral shadow-xl">
                <div class="card-body">
                  <h2 class="card-title">Registered Courses</h2>
                  <div class="-mx-4 sm:-mx-0">
                    <table class="min-w-full divide-y divide-gray-300">
                      <thead>
                        <tr>
                          <th
                            scope="col"
                            class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-0"
                          >
                            Course Name
                          </th>
                          <th
                            scope="col"
                            class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                          >
                            Course Code
                          </th>
                          <th
                            scope="col"
                            class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                          >
                            Assigned Lecturer
                          </th>
                          <th
                            scope="col"
                            class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                          >
                            Credit Hours
                          </th>
                          <th
                            scope="col"
                            class="relative py-3.5 pl-3 pr-4 sm:pr-0"
                          >
                            <span class="sr-only">Edit</span>
                          </th>
                        </tr>
                      </thead>
                      <tbody class="divide-y divide-gray-200 bg-neutral">
                      <c:forEach var="course" items="${registeredCourses}">
                        <tr>
                          <td
                            class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
                          >
                            ${course.getCourse_name()}
                            <dl class="font-normal lg:hidden">
                              <dd class="mt-1 truncate text-gray-700">
                                ${course.getCourse_code()}
                              </dd>
                              <dd class="mt-1 truncate text-gray-500 sm:hidden">
                                ${course.getCredit_hour()}
                              </dd>
                            </dl>
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            ${course.getCourse_code()}
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            ${course.getUser().getFullname()}
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            ${course.getCredit_hour()}
                          </td>
                          <td
                            class="py-4 pl-3 pr-4 text-center text-sm font-medium sm:pr-0"
                          >
                            <div
                              class="btn btn-sm rounded-full btn-error text-white unreg"
                              onclick="unregCourse.showModal();"
                              data-id="${course.getId()}"
                            >
                              Unregister
                            </div>
                          </td>
                        </tr>
                        <!-- More people... -->
                      </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
            <div class="basis-4/5 grow">
              <div class="card card-compact bg-base-100 shadow-xl">
                <div class="card-body">
                  <h2 class="card-title">Courses List</h2>
                  <div class="-mx-4 sm:-mx-0">
                    <table class="min-w-full divide-y divide-gray-300">
                      <thead>
                        <tr>
                          <th
                            scope="col"
                            class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-0"
                          >
                            Course Name
                          </th>
                          <th
                            scope="col"
                            class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                          >
                            Course Code
                          </th>
                          <th
                            scope="col"
                            class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                          >
                            Assigned Lecturer
                          </th>
                          <th
                            scope="col"
                            class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                          >
                            Credit Hours
                          </th>
                          <th
                            scope="col"
                            class="relative py-3.5 pl-3 pr-4 sm:pr-0"
                          >
                            <span class="sr-only">Edit</span>
                          </th>
                        </tr>
                      </thead>
                      <tbody class="divide-y divide-gray-200 bg-white">
                      <c:forEach var="course2" items="${availableCourses}">
                        <tr>
                          <td
                            class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
                          >
                            ${course2.getCourse_name()}
                            <dl class="font-normal lg:hidden">
                              <dd class="mt-1 truncate text-gray-700">
                                ${course2.getCourse_code()}
                              </dd>
                              <dd class="mt-1 truncate text-gray-500 sm:hidden">
                                ${course2.getCredit_hour()}
                              </dd>
                            </dl>
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            ${course2.getCourse_code()}
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            ${course2.getUser().getFullname()}
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            ${course2.getCredit_hour()}
                          </td>
                          <td
                            class="py-4 pl-3 pr-4 text-center text-sm font-medium sm:pr-0"
                          >
                            <div
                              class="btn btn-sm rounded-full btn-primary reg"
                              onclick="regCourse.showModal();"
                              data-id="${course2.getId()}"
                            >
                              Register
                            </div>
                          </td>
                        </tr>
                        <!-- More people... -->
                      </c:forEach>
                      </tbody>
                    </table>
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

    <dialog id="unregCourse" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Unregister Course</h3>
        <p class="py-4">Do you really want to unregister this course ?</p>
        <div class="modal-action">
          <form action="/student/register/course" method="post">
            <input type="hidden" name="cid" id="unreg_id" value="1" />
            <input type="hidden" name="ind" id="ind" value="unregister" />
            <button type="submit" class="btn btn-sm btn-error text-white">
              Yes
            </button>
          </form>
        </div>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>
    <dialog id="regCourse" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Register Course</h3>
        <p class="py-4">Do you really want to Register this course ?</p>
        <form action="/student/register/course" method="post">
          <input type="hidden" name="cid" id="reg_id" value="1" />
          <input type="hidden" name="ind" id="ind" value="register" />
          <div class="modal-action">
            <button type="submit" class="btn btn-sm btn-success text-white">
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
