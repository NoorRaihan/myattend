<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Utility Management - myAttend+</title>
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/theme-change@2.5.0/index.js"></script>
    <script src="${contextPath}/resources/utility.js"></script>
  </head>
  <body class="bg-neutral min-h-screen">
    <div class="fixed inset-x-0 w-full bg-primary min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <jsp:include page="../Home/header.jsp">
          <jsp:param
            name="menu"
            value='<li class="sm:hidden">Admin Menu</li><li>Utility Management</li>'
          />
          <jsp:param name="title" value="Utility Management" />
        </jsp:include>
        <div class="w-auto my-10">
          <div
            class="flex flex-wrap-reverse mx-4 gap-5 items-end transition-all"
          >
            <div class="basis-2/5">
              <div class="card card-compact bg-base-100 shadow-xl">
                <div class="card-body">
                  <div class="card-title justify-between">
                    <h1>User Roles</h1>
                    <button
                      class="btn btn-sm btn-primary"
                      onclick="newRole.showModal(); roleAdd.reset();"
                    >
                      New Role
                    </button>
                  </div>
                  <div class="-mx-4 sm:-mx-0">
                    <table class="min-w-full divide-y divide-gray-300">
                      <thead>
                        <tr>
                          <th
                            scope="col"
                            class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-0"
                          >
                            Role ID
                          </th>
                          <th
                            scope="col"
                            class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
                          >
                            Role Name
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
                        <c:forEach var="role" items="${roles}">
                          <tr>
                            <td
                              class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
                            >
                              ${role.getId()}
                            </td>
                            <td class="px-3 py-4 text-sm text-gray-500">
                              ${role.getRole_name()}
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
                                      onclick="editRole.showModal()"
                                      data-id="${role.getId()}"
                                      class="roleEdit"
                                      >Edit</a
                                    >
                                  </li>
                                  <li>
                                    <a
                                      onclick="deleteRole.showModal()"
                                      class="text-red-600 roleDelete"
                                      data-id="${role.getId()}"
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

    <dialog id="newRole" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Add New User Role</h3>
        <form
          id="roleAdd"
          action="/role/create"
          method="post"
        >
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-full">
              <div class="label">
                <span class="label-text">Role Name</span>
              </div>
              <input
                type="text"
                name="name"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-full">
              <div class="label">
                <span class="label-text">Role ID</span>
              </div>
              <input
                type="number"
                name="id"
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
    <dialog id="editRole" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Edit Role</h3>
        <form action="/role/update" method="post">
          <input type="hidden" name="oriid" id="oriId" value="1" />
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-full">
              <div class="label">
                <span class="label-text">Role Name</span>
              </div>
              <input
                type="text"
                name="name"
                id="role_name"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-full">
              <div class="label">
                <span class="label-text">Role ID</span>
              </div>
              <input
                type="number"
                name="id"
                id="role_id"
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
    <dialog id="deleteRole" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Delete Role</h3>
        <p class="py-4">Do you really want to delete this role ?</p>
        <form action="/role/delete" method="post">
          <input type="hidden" name="id" id="roleid" value="1" />
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
