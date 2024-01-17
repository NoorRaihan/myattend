<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html data-theme="light">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Course Management - myAttend+</title>
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="${contextPath}/resources/course.js"></script>
  </head>
  <body class="bg-slate-200 min-h-screen">
    <div class="fixed inset-x-0 w-full bg-blue-500 min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <jsp:include page="../Home/header.jsp">
          <jsp:param
            name="menu"
            value="<li class='sm:hidden'>Admin Menu</li><li>Course Management</li>"
          />
          <jsp:param name="title" value="Course Management" />
        </jsp:include>
        <div class="w-auto my-10">
          <div
            class="flex flex-wrap-reverse mx-4 gap-5 items-baseline transition-all"
          >
            <div class="basis-3/5 grow">
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
                            class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 sm:table-cell"
                          >
                            Color
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
                        <tr>
                          <td
                            class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
                          >
                            Enterprise Programming
                            <dl class="font-normal lg:hidden">
                              <dd class="mt-1 truncate text-gray-700">
                                CSC584
                              </dd>
                              <dd class="mt-1 truncate text-gray-500 sm:hidden">
                                3
                              </dd>
                            </dl>
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            CSC584
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            Lecturer 1
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            3
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 sm:table-cell"
                          >
                            <div class="avatar placeholder">
                              <div
                                class="bg-gradient-to-br from-green-300 to-emerald-500 rounded-full w-6"
                              ></div>
                            </div>
                          </td>
                          <td
                            class="px-3 py-4 text-right text-sm text-gray-500"
                          >
                            <input
                              type="checkbox"
                              class="toggle toggle-primary"
                              checked
                            />
                          </td>
                          <td
                            class="py-4 pl-3 pr-4 text-center text-sm font-medium sm:pr-0"
                          >
                            <div class="dropdown dropdown-left dropdown-hover">
                              <div
                                tabindex="0"
                                role="button"
                                class="btn btn-ghost btn-xs"
                              >
                                <svg
                                  xmlns="http://www.w3.org/2000/svg"
                                  viewBox="0 0 24 24"
                                  fill="currentColor"
                                  class="size-6"
                                >
                                  <path
                                    fill-rule="evenodd"
                                    d="M10.5 6a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0Zm0 6a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0Zm0 6a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0Z"
                                    clip-rule="evenodd"
                                  />
                                </svg>
                              </div>
                              <ul
                                tabindex="0"
                                class="dropdown-content z-[1] menu p-2 shadow-lg bg-slate-100 rounded-box w-fit"
                              >
                                <li>
                                  <a
                                    onclick="editCourse.showModal()"
                                    data-id="1"
                                    class="edit"
                                    >Edit</a
                                  >
                                </li>
                                <li>
                                  <a
                                    onclick="deleteCourse.showModal()"
                                    class="text-red-600 delete"
                                    data-id="1"
                                    >Delete</a
                                  >
                                </li>
                              </ul>
                            </div>
                          </td>
                        </tr>
                        <!-- More people... -->
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
                    <div class="stat-title">Total Courses</div>
                    <div class="stat-value text-blue-500">100</div>
                    <div class="stat-desc">myAttend+</div>
                  </div>
                  <div class="flex justify-center">
                    <button
                      class="btn btn-primary rounded-full my-auto"
                      onclick="newCourse.showModal(); courseAdd.reset();"
                    >
                      Add New Course
                    </button>
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

    <dialog id="newCourse" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Add New Course</h3>
        <form id="courseAdd" action="#" method="post">
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-full">
              <div class="label">
                <span class="label-text">Course Name</span>
              </div>
              <input
                type="text"
                name="c_name"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Course Code</span>
              </div>
              <input
                type="text"
                name="c_code"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Assigned Lecturer</span>
              </div>
              <select
                name="c_lect"
                id="c_lect"
                class="select select-sm select-bordered"
              >
                <option disabled selected>Pick one</option>
                <option value="20234001">Lecturer 1</option>
                <option value="20234002">Lecturer 2</option>
                <option value="20234003">Lecturer 3</option>
              </select>
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Credit Hours</span>
              </div>
              <input
                type="number"
                name="c_credit"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Color</span>
              </div>
              <select
                name="c_color"
                id="c_color"
                class="select select-sm select-bordered"
              >
                <option disabled selected>Pick one</option>
                <option value="GREEN1">Green</option>
                <option value="LIME1">Light Green</option>
                <option value="PURPLE1">Purple</option>
                <option value="BLUE1">Blue</option>
                <option value="CYAN1">Light Blue</option>
                <option value="FUCHSIA1">Fuchsia</option>
                <option value="PINK1">Pink</option>
                <option value="YELLOW1">Yellow</option>
                <option value="ORANGE1">Orange</option>
                <option value="RED1">Red</option>
              </select>
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
    <dialog id="editCourse" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Edit Course</h3>
        <form id="courseAdd" action="#" method="post">
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-full">
              <div class="label">
                <span class="label-text">Course Name</span>
              </div>
              <input
                type="text"
                name="c_name"
                id="c_name"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Course Code</span>
              </div>
              <input
                type="text"
                name="c_code"
                id="c_code"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Assigned Lecturer</span>
              </div>
              <select
                name="c_lect"
                id="c_lectedt"
                class="select select-sm select-bordered"
              >
                <option disabled selected>Pick one</option>
                <option value="20234001">Lecturer 1</option>
                <option value="20234002">Lecturer 2</option>
                <option value="20234003">Lecturer 3</option>
              </select>
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Credit Hours</span>
              </div>
              <input
                type="number"
                name="c_credit"
                id="c_credit"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Color</span>
              </div>
              <select
                name="c_color"
                id="c_coloredt"
                class="select select-sm select-bordered"
              >
                <option disabled selected>Pick one</option>
                <option value="GREEN1">Green</option>
                <option value="LIME1">Light Green</option>
                <option value="PURPLE1">Purple</option>
                <option value="BLUE1">Blue</option>
                <option value="CYAN1">Light Blue</option>
                <option value="FUCHSIA1">Fuchsia</option>
                <option value="PINK1">Pink</option>
                <option value="YELLOW1">Yellow</option>
                <option value="ORANGE1">Orange</option>
                <option value="RED1">Red</option>
              </select>
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
    <dialog id="deleteCourse" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Delete Course</h3>
        <p class="py-4">Do you really want to delete this course ?</p>
        <form action="#" method="post">
          <input type="hidden" name="c_id" id="c_id" value="1" />
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
    <c:remove var="success" scope="session" />
  </body>
</html>
