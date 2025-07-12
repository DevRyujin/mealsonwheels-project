export default function Footer() {
  return (
    <div className="flex flex-col w-full h-fit bg-indigo-950 text-[#e5e7eb] px-14 py-14">
      <div className="flex flex-row flex-wrap md:flex-nowrap gap-6">
        {/* Left side: Logo + Socials */}
        <div className="flex flex-col gap-4 w-full md:w-[35%]">
          <div className="flex items-center gap-4">
            <img alt="Logo" src="/images/logo.png" width="80" />
            <div className="text-3xl font-bold">Meals On Wheels</div>
          </div>
          <div className="grid grid-cols-3 gap-6 w-fit p-4">
            {/* Social icons */}
            <a href="#"><svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" width="24" height="24" viewBox="0 0 24 24"><path d="M24 4.557..."/></svg></a>
            <a href="#"><svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" width="24" height="24" viewBox="0 0 24 24"><path d="M19.615..."/></svg></a>
            <a href="#"><svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" width="24" height="24" viewBox="0 0 24 24"><path d="M9 8h-3v4..."/></svg></a>
          </div>
        </div>

        {/* Right side: Links + Contact */}
        <div className="flex flex-wrap justify-between w-full md:w-[65%] gap-10">
          <div className="grid grid-cols-2 gap-12">
            <div className="flex flex-col gap-2">
              <div className="font-bold uppercase text-[#9ca3af] pb-3">Company</div>
              <a href="/" className="hover:underline">Home</a>
              <a href="/about" className="hover:underline">About Us</a>
              <a href="/services" className="hover:underline">Services</a>
              <a href="/contact" className="hover:underline">Contact</a>
            </div>
            <div className="flex flex-col gap-2">
              <div className="font-bold uppercase text-[#9ca3af] pb-3">Schedule</div>
              <p>Mon - Fri: 8am - 6pm</p>
              <p>Sat: 10am - 4pm</p>
            </div>
          </div>

          <div className="flex flex-col gap-2">
            <div className="font-bold uppercase text-[#9ca3af] pb-3">Contact Us</div>
            <p className="text-[#e5e7eb] mb-2">Get in touch with us.</p>
            <form className="flex items-center">
              <input
                type="email"
                name="email"
                placeholder="Enter your email"
                className="w-full bg-gray-100 text-gray-700 rounded-l-lg py-3 px-4 focus:outline-none"
                autoComplete="off"
                required
              />
              <button
                type="submit"
                className="bg-gradient-to-r from-orange-500 to-red-500 text-white font-semibold py-3 px-6 rounded-r-lg transition-colors duration-300"
              >
                Subscribe
              </button>
            </form>
          </div>
        </div>
      </div>

      <div className="w-full border-t border-gray-500 my-8"></div>
      <div className="text-center">© 2025 Meals on Wheels - All rights reserved.</div>
    </div>
  );
}
