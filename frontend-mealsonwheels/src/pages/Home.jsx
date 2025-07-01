import { Link } from 'react-router-dom';

export default function Home() {
    const scrollToAbout = () => {
        document.getElementById('about-section').scrollIntoView({ behavior: 'smooth' });
    };

    return (
        <div className="min-h-screen">
            {/* Hero Section */}
            <section className="relative bg-cover bg-center h-screen" style={{ backgroundImage: "url('/images/vegetables.webp')" }}>
                <div className="absolute inset-0 bg-gradient-to-r from-black/50 to-transparent">
                    <div className="absolute inset-0 opacity-10" style={{
                        backgroundImage: `url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%23ffffff' fill-opacity='0.1'%3E%3Ccircle cx='30' cy='30' r='4'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E")`,
                    }}></div>
                </div>

                <div className="relative z-10 flex flex-col justify-center h-full max-w-7xl px-6 sm:px-10 lg:px-20 xl:px-40">
                    <h1 className="text-4xl sm:text-5xl md:text-6xl lg:text-7xl font-bold text-white uppercase leading-tight" style={{
                        textShadow: '2px 2px 0 #000, -2px -2px 0 #000, 2px -2px 0 #000, -2px 2px 0 #000, 0 2px 0 #000, 2px 0 0 #000, 0 -2px 0 #000, -2px 0 0 #000'
                    }}>
                        Welcome to<br />
                        <span className="text-yellow-300">Meals on Wheels</span>
                    </h1>
                    <p className="mt-6 text-white text-lg sm:text-xl max-w-3xl leading-relaxed text-justify">
                        Delivering nutritious, delicious meals directly to your doorstep. We connect communities through food, ensuring no one goes hungry.
                    </p>
                    <div className="mt-6 flex flex-col sm:flex-row gap-4 items-start sm:items-center">
                        <Link
                            to="/register"
                            className="bg-red-600 hover:bg-red-700 text-white
                                    px-5 py-2.5 sm:px-8 sm:py-4
                                    rounded-xl font-semibold
                                    text-base sm:text-lg
                                    transition-all duration-200 transform
                                    hover:scale-105 shadow-lg hover:shadow-xl"
                        >
                            Get Started
                        </Link>
                    </div>
                </div>

                <div className="absolute bottom-8 w-full text-center z-10">
                    <button onClick={scrollToAbout} className="text-white text-base hover:text-yellow-300 transition-colors duration-200 flex flex-col items-center mx-auto">
                        <span className="mb-3">Learn More About Us</span>
                        <svg className="w-8 h-8 animate-bounce" fill="currentColor" viewBox="0 0 20 20">
                            <path fillRule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clipRule="evenodd" />
                        </svg>
                    </button>
                </div>
            </section>

            {/* About Section */}
            <section id="about-section" className="py-20 bg-gray-50">
                <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 grid md:grid-cols-2 gap-12 items-center">
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

                    <div className="relative">
                        <div className="relative h-80 sm:h-96 rounded-2xl shadow-xl overflow-hidden">
                            <img src="/images/arc-vegetables.webp" alt="Fresh vegetables" className="w-full h-full object-cover" />
                            <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent flex items-end p-6 sm:p-8">
                                <div className="text-white">
                                    <h3 className="text-xl sm:text-2xl font-bold mb-1">Fresh & Nutritious</h3>
                                    <p className="text-sm opacity-90">Every meal is prepared with care and delivered fresh to your door</p>
                                </div>
                            </div>
                        </div>
                        <div className="absolute -top-4 -right-4 w-16 h-16 sm:w-20 sm:h-20 bg-yellow-400 rounded-full opacity-20"></div>
                        <div className="absolute -bottom-4 -left-4 w-14 h-14 sm:w-16 sm:h-16 bg-red-400 rounded-full opacity-20"></div>
                    </div>
                </div>
            </section>

            {/* Services Section */}
            <section className="py-20 bg-white">
                <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div className="text-center mb-16">
                        <h2 className="text-3xl sm:text-4xl font-bold text-gray-900 mb-4">Our Services</h2>
                        <div className="w-20 h-1 bg-gradient-to-r from-orange-500 to-red-500 mx-auto mb-6"></div>
                        <p className="text-gray-600 max-w-2xl mx-auto">
                            We offer comprehensive meal delivery services tailored to meet diverse community needs
                        </p>
                    </div>

                    <div className="grid sm:grid-cols-2 md:grid-cols-3 gap-8">
                        {/* Repeatable Service Card */}
                        {[
                            {
                                title: 'Regular Meal Delivery',
                                icon: (
                                    <path d="M3 4a1 1 0 011-1h12a1 1 0 011 1v2a1 1 0 01-1 1H4a1 1 0 01-1-1V4zM3 10a1 1 0 011-1h6a1 1 0 011 1v6a1 1 0 01-1 1H4a1 1 0 01-1-1v-6zM14 9a1 1 0 00-1 1v6a1 1 0 001 1h2a1 1 0 001-1v-6a1 1 0 00-1-1h-2z" />
                                ),
                                text: 'Scheduled daily or weekly meal deliveries for seniors and individuals with mobility challenges'
                            },
                            {
                                title: 'Special Diet Plans',
                                icon: (
                                    <path fillRule="evenodd" d="M6 2a1 1 0 00-1 1v1H4a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V6a2 2 0 00-2-2h-1V3a1 1 0 10-2 0v1H7V3a1 1 0 00-1-1zm0 5a1 1 0 000 2h8a1 1 0 100-2H6z" clipRule="evenodd" />
                                ),
                                text: 'Customized meal plans for dietary restrictions, medical conditions, and personal preferences'
                            },
                            {
                                title: 'Emergency Meals',
                                icon: (
                                    <path d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                                ),
                                text: 'Quick response meal delivery during emergencies, natural disasters, or urgent situations'
                            }
                        ].map((service, idx) => (
                            <div key={idx} className="text-center p-8 bg-gray-50 rounded-2xl hover:shadow-lg transition-shadow duration-200">
                                <div className="w-16 h-16 bg-gradient-to-r from-orange-500 to-red-500 rounded-full flex items-center justify-center mx-auto mb-6">
                                    <svg className="w-8 h-8 text-white" fill="currentColor" viewBox="0 0 20 20">{service.icon}</svg>
                                </div>
                                <h3 className="text-xl font-bold text-gray-900 mb-4">{service.title}</h3>
                                <p className="text-gray-600">{service.text}</p>
                            </div>
                        ))}
                    </div>
                </div>
            </section>

            {/* CTA */}
            <section className="py-20 bg-gradient-to-r from-orange-600 to-red-600">
                <div className="max-w-4xl mx-auto text-center px-4 sm:px-6">
                    <h2 className="text-3xl sm:text-4xl font-bold text-white mb-6">Ready to Get Started?</h2>
                    <p className="text-lg sm:text-xl text-white opacity-90 mb-8 max-w-2xl mx-auto">
                        Join thousands of satisfied customers who trust Meals on Wheels for their daily nutrition needs
                    </p>
                    <div className="flex flex-col sm:flex-row gap-4 justify-center">
                        <Link to="/register" className="bg-white text-red-600 hover:bg-gray-100 px-8 py-4 rounded-xl font-semibold transition-all duration-200 transform hover:scale-105 shadow-lg">
                            Sign Up Now
                        </Link>
                        <Link to="/test" className="bg-transparent border-2 border-white text-white hover:bg-white hover:text-red-600 px-8 py-4 rounded-xl font-semibold transition-all duration-200">
                            Test Our Service
                        </Link>
                    </div>
                </div>
            </section>
        </div>
    );
}
