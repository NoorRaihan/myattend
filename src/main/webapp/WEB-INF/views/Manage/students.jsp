<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html data-theme="light">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Student Management - myAttend+</title>
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="${contextPath}/resources/student.js"></script>
  </head>
  <body class="bg-slate-200 min-h-screen">
    <div class="fixed inset-x-0 w-full bg-blue-500 min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <jsp:include page="../Home/header.jsp">
          <jsp:param
            name="menu"
            value="<li>Admin Menu</li><li>Student Management</li>"
          />
          <jsp:param name="title" value="Student Management" />
        </jsp:include>
        <div class="w-auto my-10">
          <div
            class="flex flex-wrap-reverse mx-4 gap-5 items-start transition-all"
          >
            <div class="basis-3/5 grow">
              <div class="card card-compact bg-base-100 shadow-xl">
                <div class="card-body">
                  <h2 class="card-title">Students</h2>
                  <div class="-mx-4 sm:-mx-0">
                    <table class="min-w-full divide-y divide-gray-300">
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
                            Username
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
                        <tr>
                          <td
                            class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
                          >
                            myAttend+ Admin
                            <dl class="font-normal lg:hidden">
                              <dd class="mt-1 truncate text-gray-700">
                                0000000001
                              </dd>
                              <dd class="mt-1 truncate text-gray-500 sm:hidden">
                                CDCS230
                              </dd>
                            </dl>
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            0000000001
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            CDCS230
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            2023/4
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 sm:table-cell"
                          >
                            3
                          </td>
                          <td class="px-3 py-4 text-sm text-gray-500"></td>
                          <td
                            class="py-4 pl-3 pr-4 text-right text-sm font-medium sm:pr-0"
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
                                    onclick="editStud.showModal()"
                                    data-id="1"
                                    class="edit"
                                    >Edit</a
                                  >
                                </li>
                                <li>
                                  <a
                                    onclick="deleteStud.showModal()"
                                    class="text-red-600"
                                    data-id="1"
                                    class="delete"
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
                <div class="card-body">
                  <div class="stat place-items-center">
                    <div class="stat-title">Total Students</div>
                    <div class="stat-value text-blue-500">100</div>
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
      </div>

      <%@ include file="../Home/drawer.jsp" %>
    </div>

    <dialog id="editStud" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Edit Student</h3>
        <form action="#" method="post">
          <input type="hidden" name="id" id="id" value="1" />
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-full">
              <div class="label">
                <span class="label-text">Full Name</span>
              </div>
              <input
                type="text"
                name="fullname"
                id="fullname"
                class="input input-sm input-bordered"
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
                class="input input-sm input-bordered"
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
                class="input input-sm input-bordered"
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
                class="input input-sm input-bordered"
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
        <p class="py-4">Do you really want to delete this student ?</p>
        <form action="" method="post">
          <input type="hidden" name="id" id="id" value="1" />
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
  </body>
</html>
