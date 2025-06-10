export default function AdminDashboard() {
  return (
    <div className="max-w-8xl mx-auto">
      {/* Hero Section */}
      <div className="relative h-[530px] overflow-hidden">
        <img
          src="/images/vegetables.webp"
          alt="vegetables"
          className="absolute w-full bottom-0 h-[530px] object-cover object-bottom"
        />
        <h1 className="absolute z-10 text-white text-3xl sm:text-4xl md:text-5xl lg:text-6xl 
                        font-bold top-1/2 transform -translate-y-1/2 
                        text-center px-4 w-full max-w-5xl mx-auto tracking-widest">
          BEHIND EVERY MEAL, A <br className="hidden sm:block" />
          MISSION OF CARE
        </h1>
      </div>

      {/* Command Center Title */}
      <div className="bg-gray-200 mt-6 mx-2 rounded-xl p-4 text-center shadow-md">
        <h2 className="font-semibold text-black text-3xl">Dashboard Command Center</h2>
        <p className="font-medium text-black">
          Your main control center for managing and monitoring the website
        </p>
      </div>

      {/* Grid of Stats */}
      <div className="max-w-6xl mx-auto grid grid-cols-1 sm:grid-cols-2 gap-6 my-10 px-4">
        <div className="bg-gray-200 rounded-md p-6 text-center shadow">
          <h3 className="font-semibold text-lg">Members</h3>
          <p className="text-2xl mt-2 font-bold">0</p>
        </div>

        <div className="bg-gray-200 rounded-md p-6 text-center shadow">
          <h3 className="font-semibold text-lg">Meal Programs</h3>
          <p className="text-2xl mt-2 font-bold">0</p>
        </div>

        <div className="bg-gray-200 rounded-md p-6 text-center shadow">
          <h3 className="font-semibold text-lg">Total Orders</h3>
          <p className="text-2xl mt-2 font-bold">0</p>
        </div>

        <div className="bg-gray-200 rounded-md p-6 text-center shadow">
          <h3 className="font-semibold text-lg">Total Donations</h3>
          <p className="text-2xl mt-2 font-bold">0</p>
        </div>
      </div>
    </div>
  );
}
