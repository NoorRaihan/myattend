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
            value='<li class="sm:text-ellipsis">Admin Menu</li><li class="sm:text-ellipsis">Utility Management</li>'
          />
          <jsp:param name="title" value="Utility Management" />
        </jsp:include>

          <div
            class="flex flex-wrap my-10 mx-4 gap-5 transition-all"
          >
            <div class="md:basis-2/5 basis-full flex-grow">
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
                            class="py-3.5 px-3 md:text-left text-center text-sm font-semibold text-gray-900"
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
                              class="py-4 px-3 text-sm font-medium text-gray-900 w-auto md:text-left text-center"
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
            <div class="md:basis-2/5 basis-full flex-grow">
              <div class="card card-compact bg-base-100 shadow-xl">
                <div class="card-body">
                  <div class="card-title justify-between">
                    <h1>Session Management</h1>
                    <button
                      class="btn btn-sm btn-primary"
                      onclick="newSession.showModal(); sessionAdd.reset();"
                    >
                      New Session
                    </button>
                  </div>
                  <div class="-mx-4 sm:-mx-0">
                    <table class="min-w-full divide-y divide-gray-300">
                      <thead>
                        <tr>
                          <th
                            scope="col"
                            class="py-3.5 px-3 text-left text-sm font-semibold text-gray-900"
                          >
                            Session
                          </th>
                          <th
                            scope="col"
                            class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
                          >
                            Status
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
                        <c:forEach var="session" items="${sessions}">
                          <tr>
                            <td
                              class="w-full max-w-0 py-4 px-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none"
                            >
                              ${session.getSessionName()}
                              <p class="text-xs font-normal italic text-gray-400">${session.getId()}</p>
                            </td>
                              <c:choose>
                                <c:when test="${session.isUsed()}">
                                  <td class="px-3 py-4 text-sm text-gray-500">
                                    <div class="badge badge-success text-white">Active</div>
                                  </td>
                                </c:when>
                                <c:otherwise>
                                  <td class="px-3 py-4 text-sm text-gray-500">
                                    <div class="badge badge-neutral">Disabled</div>
                                  </td>
                                </c:otherwise>
                                </c:choose>
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
                                  class="dropdown-content z-[1] menu p-2 shadow-lg bg-slate-100 rounded-box w-max"
                                >
                                  <li>
                                    <c:choose>
                                      <c:when test="${session.isUsed()}">
                                        <a
                                            onclick="setSessionDisable.showModal()"
                                            data-id="${session.getId()}"
                                            class="sessionSetDisable"
                                            >Set Disable</a
                                          >
                                      </c:when>
                                      <c:otherwise>
                                        <a
                                            onclick="setSession.showModal()"
                                            data-id="${session.getId()}"
                                            class="sessionSet"
                                            >Set Active</a
                                          >
                                      </c:otherwise>
                                    </c:choose>
                                    
                                  </li>
                                  <li>
                                    <a
                                      onclick="editSession.showModal()"
                                      data-id="${session.getId()}"
                                      data-name="${session.getSessionName()}"
                                      class="sessionEdit"
                                      >Edit</a
                                    >
                                  </li>
                                  <li>
                                    <a
                                      onclick="deleteSession.showModal()"
                                      class="text-red-600 sessionDelete"
                                      data-id="${session.getId()}"
                                      >Delete</a
                                    >
                                  </li>
                                </ul>
                              </div>
                            </td>
                          </tr>
                        </c:forEach>
                        <!-- More session... -->
                      </tbody>
                    </table>
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
            <button type="button" class="btn btn-sm btn-ghost text-red-600" onclick="deleteRole.close()">
              No
            </button>
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


    <dialog id="newSession" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Add New Session</h3>
        <form
          id="sessionAdd"
          action="/session/create"
          method="post"
        >
          <div class="grid grid-cols-2 gap-3">
            <label class="form-control col-span-2">
              <div class="label">
                <span class="label-text">Session Name</span>
              </div>
              <input
                type="text"
                name="session_name"
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

    <dialog id="editSession" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Edit Session</h3>
        <form
          id="sessionEdit"
          action="/session/update"
          method="post"
        >
          <div class="grid grid-cols-2 gap-3">
            <label class="form-control col-span-2">
              <div class="label">
                <span class="label-text">Session Name</span>
              </div>
              <input
                type="text"
                name="session_name"
                id="session_name"
                class="input input-primary input-sm input-bordered"
              />
            </label>
          </div>
          <input type="hidden" name="session_id" id="session_id" value="" />
          <div class="modal-action">
            <button type="submit" class="btn btn-sm btn-primary">Save</button>
          </div>
        </form>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>

    <dialog id="setSession" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Set Session</h3>
        <p class="py-4">Do you really want to set this session as active ?</p>
        <form action="/session/activate" method="post">
          <input type="hidden" name="session_id" id="session_id2" value="" />
          <div class="modal-action">
            <button type="button" class="btn btn-sm btn-ghost text-primary" onclick="setSession.close()">
              No
            </button>
            <button type="submit" class="btn btn-sm btn-primary">
              Yes
            </button>
          </div>
        </form>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>

    <dialog id="setSessionDisable" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Set Session</h3>
        <p class="py-4">Do you really want to deactivate this session ?</p>
        <form action="/session/disable" method="post">
          <input type="hidden" name="session_id" id="session_id3" value="" />
          <div class="modal-action">
            <button type="button" class="btn btn-sm btn-ghost text-primary" onclick="setSession.close()">
              No
            </button>
            <button type="submit" class="btn btn-sm btn-primary">
              Yes
            </button>
          </div>
        </form>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>

    <dialog id="deleteSession" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Delete Session</h3>
        <p class="py-4">Do you really want to delete this session ?</p>
        <form action="/session/delete" method="post">
          <input type="hidden" name="sessionid" id="sessionid" value="" />
          <div class="modal-action">
            <button type="button" class="btn btn-sm btn-ghost text-red-600" onclick="deleteSession.close()">
              No
            </button>
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
