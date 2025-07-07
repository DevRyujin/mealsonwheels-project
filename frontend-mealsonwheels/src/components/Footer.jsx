

export default function Footer() {
    return (
        <footer className="bg-red-700 text-white p-4 text-sm grid grid-cols-3 gap-4 text-left">
        <div>
          <div className="font-semibold mb-2">ABOUT US</div>
          <p>Price</p>
          <p>Menu</p>
          <p>Home</p>
        </div>
        <div>
          <div className="font-semibold mb-2">SERVICES</div>
          <p>Delivery</p>
          <p>Packaging</p>
        </div>
        <div>
          <div className="font-semibold mb-2">SCHEDULE</div>
          <p>Hot Meals</p>
          <p>Mon–Fri</p>
          <p>9:00 am – 8:00 pm</p>
          <br />
          <p>Frozen Meals</p>
          <p>Sat–Sun</p>
          <p>Delivered within 10km</p>
        </div>
      </footer>
    );
}