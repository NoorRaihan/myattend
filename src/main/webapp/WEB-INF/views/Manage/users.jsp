<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html data-theme="light">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>User Management - myAttend+</title>
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="${contextPath}/resources/user.js"></script>
  </head>
  <body class="bg-slate-200 min-h-screen">
    <div class="fixed inset-x-0 w-full bg-blue-500 min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <jsp:include page="../Home/header.jsp">
          <jsp:param
            name="menu"
            value='<li class="sm:hidden">Admin Menu</li><li>User Management</li>'
          />
          <jsp:param name="title" value="User Management" />
        </jsp:include>
        <div class="w-auto my-10">
          <div
            class="flex flex-wrap-reverse mx-4 gap-5 items-baseline transition-all"
          >
            <div class="basis-3/5 grow">
              <div class="card card-compact bg-base-100 shadow-xl">
                <div class="card-body">
                  <h2 class="card-title">Users</h2>
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
                            Gender
                          </th>
                          <th
                            scope="col"
                            class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                          >
                            Birthdate
                          </th>
                          <th
                            scope="col"
                            class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 sm:table-cell"
                          >
                            Email
                          </th>
                          <th
                            scope="col"
                            class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
                          >
                            Role
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
                        <c:forEach var="user" items="${users}">
                          <tr>
                            <td
                              class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
                            >
                              ${user.getFullname()}
                              <dl class="font-normal lg:hidden">
                                <dd class="mt-1 truncate text-gray-700">
                                  ${user.getUsername()}
                                </dd>
                                <dd
                                  class="mt-1 truncate text-gray-500 sm:hidden"
                                >
                                  ${user.getEmail()}
                                </dd>
                              </dl>
                            </td>
                            <td
                              class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                            >
                              ${user.getUsername()}
                            </td>
                            <td
                              class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                            >
                              ${user.getGender()}
                            </td>
                            <td
                              class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                            >
                              ${user.getFormatBirthDate()}
                            </td>
                            <td
                              class="hidden px-3 py-4 text-sm text-gray-500 sm:table-cell"
                            >
                              ${user.getEmail()}
                            </td>
                            <td class="px-3 py-4 text-sm text-gray-500">
                              ${user.getRole().getRole_name()}
                            </td>
                            <td
                              class="py-4 pl-3 pr-4 text-right text-sm font-medium sm:pr-0"
                            >
                              <div
                                class="dropdown dropdown-left dropdown-hover"
                              >
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
                                      onclick="editUser.showModal()"
                                      data-id="${user.getId()}"
                                      class="edit"
                                      >Edit</a
                                    >
                                  </li>
                                  <li>
                                    <a
                                      onclick="deleteUser.showModal()"
                                      class="text-red-600 delete"
                                      data-id="${user.getId()}"
                                      >Delete</a
                                    >
                                  </li>
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
                <div
                  class="card-body flex md:flex-col justify-between flex-row"
                >
                  <div class="stat place-items-center basis-1/4 sm:basis-full">
                    <div class="stat-title">Total Users</div>
                    <div class="stat-value text-blue-500">${totalUser}</div>
                    <div class="stat-desc">users of myAttend+</div>
                  </div>
                  <div class="flex justify-center">
                    <button
                      class="btn btn-primary rounded-full my-auto"
                      onclick="newUser.showModal(); userAdd.reset();"
                    >
                      Add New User
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
          let succ = "${sessionScope.success}";
          if (succ != "") {
            $("#succMsg").html(succ);
            $("#succ").show().delay(3000).fadeOut();
          }
        </script>
      </div>

      <%@ include file="../Home/drawer.jsp" %>
    </div>

    <dialog id="newUser" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Add New User</h3>
        <form id="userAdd" action="/user/create" method="post">
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-full">
              <div class="label">
                <span class="label-text">Full Name</span>
              </div>
              <input
                type="text"
                name="fullname"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Username</span>
              </div>
              <input
                type="text"
                name="username"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Password</span>
              </div>
              <input
                type="password"
                name="password"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Gender</span>
              </div>
              <select
                name="gender"
                id="gender"
                class="select select-sm select-bordered"
              >
                <option disabled selected>Pick one</option>
                <option value="M">Male</option>
                <option value="F">Female</option>
              </select>
            </label>
            <label class="form-control basis-2/4 grow">
              <div class="label">
                <span class="label-text">Birthdate</span>
              </div>
              <input
                type="date"
                name="birthdate"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-2/4 grow">
              <div class="label">
                <span class="label-text">Email</span>
              </div>
              <input
                type="email"
                name="email"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Role</span>
              </div>
              <select
                name="role"
                id="role"
                class="select select-sm select-bordered"
              >
                <option disabled selected>Pick one</option>
                <option value="1">Admin</option>
                <option value="2">Lecturer</option>
                <option value="3">Student</option>
              </select>
            </label>
            <label class="form-control basis-full grow">
              <div class="label">
                <span class="label-text">Profile Picture</span>
              </div>
              <input
                type="file"
                name="dpImage"
                class="file-input file-input-sm file-input-bordered file-input-primary"
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
    <dialog id="editUser" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Edit User</h3>
        <form action="/user/update" method="post">
          <input type="hidden" name="uid" id="editId" value="1" />
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
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Username</span>
              </div>
              <input
                type="text"
                name="username"
                id="username"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Gender</span>
              </div>
              <select
                name="gender"
                id="gender"
                class="select select-sm select-bordered"
              >
                <option value="M">Male</option>
                <option value="F">Female</option>
              </select>
            </label>
            <label class="form-control basis-2/4 grow">
              <div class="label">
                <span class="label-text">Birthdate</span>
              </div>
              <input
                type="date"
                name="birthdate"
                id="birthdate"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-2/4 grow">
              <div class="label">
                <span class="label-text">Email</span>
              </div>
              <input
                type="email"
                name="email"
                id="email"
                class="input input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Role</span>
              </div>
              <select
                name="role"
                id="role"
                class="select select-sm select-bordered"
              >
                <option value="1">Admin</option>
                <option value="2">Lecturer</option>
                <option value="3">Student</option>
              </select>
            </label>
            <label class="form-control basis-full grow">
              <div class="label">
                <span class="label-text">Profile Picture</span>
              </div>
              <input
                type="file"
                name="dpImage"
                id="dpImage"
                class="file-input file-input-sm file-input-bordered file-input-primary"
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
    <dialog id="deleteUser" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Delete User</h3>
        <p class="py-4">Do you really want to delete this user ?</p>
        <form action="/user/delete" method="post">
          <input type="hidden" name="uid" id="uid" value="1" />
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
