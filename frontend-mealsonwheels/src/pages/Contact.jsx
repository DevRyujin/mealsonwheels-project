// src/pages/Contact.jsx
import React, { useState } from 'react';

export default function Contact() {
  const [form, setForm] = useState({ contactFullName: '', contactEmail: '', contactMessage: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert('Message sent!');
    console.log(form);
    setForm({ contactFullName: '', contactEmail: '', contactMessage: '' });
  };

  return (
    <section id="contact" className="py-16 bg-white px-4 md:px-16">
      <h2 className="text-3xl font-bold text-center mb-2">Contact Us</h2>
      <p className="text-center text-gray-600 mb-8">We'd love to hear from you! Get in touch with us.</p>

      <div className="bg-gradient-to-r from-orange-100 to-red-400 max-w-xl mx-auto rounded-lg shadow-md p-6">
        <div className="text-center mb-6">
          <h3 className="text-xl font-semibold text-indigo-900">GET IN TOUCH</h3>
          <p className="uppercase text-sm font-semibold text-gray-800">Contact Form</p>
          <p className="text-sm text-gray-600">Reach out for inquiries or feedback.</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="contactFullName" className="block text-sm font-medium mb-1">Full Name</label>
            <input
              type="text"
              id="contactFullName"
              name="contactFullName"
              value={form.contactFullName}
              onChange={handleChange}
              placeholder="Your Full Name"
              required
              className="w-full border border-gray-300 rounded px-4 py-2"
            />
          </div>
          <div>
            <label htmlFor="contactEmail" className="block text-sm font-medium mb-1">Email Address</label>
            <input
              type="email"
              id="contactEmail"
              name="contactEmail"
              value={form.contactEmail}
              onChange={handleChange}
              placeholder="Your Email Address"
              required
              className="w-full border border-gray-300 rounded px-4 py-2"
            />
          </div>
          <div>
            <label htmlFor="contactMessage" className="block text-sm font-medium mb-1">Message</label>
            <textarea
              id="contactMessage"
              name="contactMessage"
              value={form.contactMessage}
              onChange={handleChange}
              rows="4"
              placeholder="Your Message"
              required
              className="w-full border border-gray-300 rounded px-4 py-2"
            />
          </div>

          <button
            type="submit"
            className="w-full bg-gradient-to-r from-orange-600 to-red-600 text-white py-2 rounded hover:bg-gray-800"
          >
            Send Message
          </button>
        </form>
      </div>
    </section>
  );
}
