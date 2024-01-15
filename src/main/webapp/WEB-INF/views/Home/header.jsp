<div class="navbar bg-gradient-to-b from-blue-500 from-30% sticky top-0 z-20">
  <div class="navbar-start">
    <label
      for="my-drawer"
      class="btn btn-square btn-ghost drawer-button xl:hidden text-white"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        class="inline-block w-5 h-5 stroke-current fill-white"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M4 6h16M4 12h16M4 18h16"
        ></path>
      </svg>
    </label>
    <div class="mx-2 text-white lg:mx-6">
      <div class="text-sm breadcrumbs">
        <ul>
          <li><a href="/">Home</a></li>
          ${param.menu}
        </ul>
      </div>
      <h3 class="text-xl font-bold -mt-2">${param.title}</h3>
    </div>
  </div>
  <div class="navbar-end mr-4">
    <form action="/logout" method="post">
      <button type="submit" class="btn btn-sm rounded-md btn-ghost text-white">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="currentColor"
          class="w-6 h-6"
        >
          <path
            fill-rule="evenodd"
            d="M7.5 3.75A1.5 1.5 0 0 0 6 5.25v13.5a1.5 1.5 0 0 0 1.5 1.5h6a1.5 1.5 0 0 0 1.5-1.5V15a.75.75 0 0 1 1.5 0v3.75a3 3 0 0 1-3 3h-6a3 3 0 0 1-3-3V5.25a3 3 0 0 1 3-3h6a3 3 0 0 1 3 3V9A.75.75 0 0 1 15 9V5.25a1.5 1.5 0 0 0-1.5-1.5h-6Zm10.72 4.72a.75.75 0 0 1 1.06 0l3 3a.75.75 0 0 1 0 1.06l-3 3a.75.75 0 1 1-1.06-1.06l1.72-1.72H9a.75.75 0 0 1 0-1.5h10.94l-1.72-1.72a.75.75 0 0 1 0-1.06Z"
            clip-rule="evenodd"
          />
        </svg>
        Logout
      </button>
    </form>
  </div>
</div>
