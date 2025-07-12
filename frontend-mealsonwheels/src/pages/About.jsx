import React from 'react';

const About = () => {
  return (
    <section id="about-section" className="py-20 bg-gray-50">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 grid md:grid-cols-2 gap-12 items-center">
        
        {/* Text Content */}
        <div className="space-y-6">
          <h2 className="text-3xl sm:text-4xl font-bold text-gray-900 uppercase">
            About Meals on Wheels
          </h2>
          <div className="w-20 h-1 bg-gradient-to-r from-orange-500 to-red-500"></div>
          <p className="text-gray-700 text-base sm:text-lg leading-relaxed">
            For over decades, Meals on Wheels has been at the forefront of community nutrition, delivering not just meals, but hope, independence, and connection to those who need it most.
          </p>
          <p className="text-gray-700 leading-relaxed">
            Our mission extends beyond food delivery. We're building a network of care that ensures seniors, individuals with disabilities, and families facing food insecurity have access to nutritious, chef-prepared meals delivered with compassion and dignity.
          </p>

          {/* Stats */}
          <div className="grid grid-cols-2 gap-6 mt-8">
            <div className="text-center p-4 bg-white rounded-lg shadow-sm">
              <div className="text-3xl font-bold text-orange-600">50K+</div>
              <div className="text-gray-600">Meals Delivered</div>
            </div>
            <div className="text-center p-4 bg-white rounded-lg shadow-sm">
              <div className="text-3xl font-bold text-red-600">1000+</div>
              <div className="text-gray-600">Happy Customers</div>
            </div>
          </div>
        </div>

        {/* Image Card */}
        <div className="relative">
          <div className="relative h-80 sm:h-96 rounded-2xl shadow-xl overflow-hidden">
            <img
              src="/images/arc-vegetables.webp"
              alt="Fresh vegetables"
              className="w-full h-full object-cover"
            />
            <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent flex items-end p-6 sm:p-8">
              <div className="text-white">
                <h3 className="text-xl sm:text-2xl font-bold mb-1">Fresh & Nutritious</h3>
                <p className="text-sm opacity-90">Every meal is prepared with care and delivered fresh to your door</p>
              </div>
            </div>
          </div>

          {/* Decorative circles */}
          <div className="absolute -top-4 -right-4 w-16 h-16 sm:w-20 sm:h-20 bg-yellow-400 rounded-full opacity-20"></div>
          <div className="absolute -bottom-4 -left-4 w-14 h-14 sm:w-16 sm:h-16 bg-red-400 rounded-full opacity-20"></div>
        </div>

      </div>
    </section>
  );
};

export default About;
