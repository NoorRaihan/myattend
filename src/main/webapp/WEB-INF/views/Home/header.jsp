<div class="navbar bg-gradient-to-b from-primary from-30% sticky top-0 z-20">
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
      <h3 class="text-xl font-bold">${param.title}</h3>
    </div>
  </div>
  <div class="navbar-end mr-4">
    <div class="dropdown dropdown-end">
      <div tabindex="0" role="button" class="btn btn-ghost text-white">
        Theme
        <svg
          width="12px"
          height="12px"
          class="h-2 w-2 fill-current opacity-60 inline-block"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 2048 2048"
        >
          <path
            d="M1799 349l242 241-1017 1017L7 590l242-241 775 775 775-775z"
          ></path>
        </svg>
      </div>
      <ul
        tabindex="0"
        class="menu menu-sm dropdown-content mt-1 z-[1] p-2 shadow-xl bg-neutral rounded-box w-fit"
      >
        <li>
          <input
            type="radio"
            name="theme-dropdown"
            class="theme-controller btn btn-sm btn-block btn-ghost justify-start"
            aria-label="Default"
            value="blue"
            checked
          />
        </li>
        <li>
          <input
            type="radio"
            name="theme-dropdown"
            class="theme-controller btn btn-sm btn-block btn-ghost justify-start"
            aria-label="Pink"
            value="pink"
          />
        </li>
      </ul>
    </div>
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
