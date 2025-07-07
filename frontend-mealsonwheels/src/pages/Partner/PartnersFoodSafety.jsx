import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function PartnersFoodSafety() {
  const navigate = useNavigate();
  const [backPath, setBackPath] = useState('/');

  useEffect(() => {
    const userType = localStorage.getItem('userType');
    if (userType === 'partner') {
      setBackPath('/partner/MenuPlanning');
    } else if (userType === 'member') {
      setBackPath('/member/meal-order');
    } else {
      setBackPath('/'); // fallback
    }
  }, []);

  const safetyItems = [
    {
      title: 'Hygiene and Sanitation',
      description:
        'We uphold high standards of cleanliness across all stages of meal preparation, from handwashing to sanitized kitchen equipment.',
    },
    {
      title: 'Temperature Control',
      description:
        'Meals are stored and delivered at safe temperatures to reduce the risk of contamination and maintain freshness.',
    },
    {
      title: 'Allergen Management',
      description:
        'Our team is trained to recognize and manage food allergens, following clear protocols to prevent cross-contamination.',
    },
    {
      title: 'Staff Training',
      description:
        'All food handlers complete training in food hygiene, safety protocols, and proper handling techniques before serving our community.',
    },
  ];

  return (
    <div className="bg-white min-h-screen py-10 px-4 md:px-16">
      <div className="text-center">
        <h1 className="text-3xl font-bold">Meals on Wheels</h1>
        <h2 className="text-2xl font-semibold mt-2">Food Safety Management</h2>
        <p className="mt-4 max-w-3xl mx-auto text-gray-700">
          Our dedication to service excellence drives every aspect of our operations. We follow consistent standards
          and implement continuous training to ensure that every meal is prepared with care and delivered with
          professionalism, supporting the dignity and health of those we serve.
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-10 max-w-5xl mx-auto">
        {safetyItems.map((item, index) => (
          <div
            key={index}
            className="bg-gray-200 rounded-md p-6 text-center shadow hover:shadow-md transition"
          >
            <h3 className="text-xl font-semibold mb-2">{item.title}</h3>
            <p className="text-gray-800">{item.description}</p>
          </div>
        ))}
      </div>

      <div className="text-center mt-10">
        <button
          onClick={() => navigate(backPath)}
          className="px-6 py-2 border border-black rounded hover:bg-gray-100 transition"
        >
          ← Back to menu
        </button>
      </div>
    </div>
  );
}
