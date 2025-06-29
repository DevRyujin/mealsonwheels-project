import React, { useState } from 'react';
import { FaStar } from 'react-icons/fa';

export default function MemberFeedback() {
  const [rating, setRating] = useState(0);
  const [comments, setComments] = useState('');

  const handleSubmit = () => {
    if (!rating) {
      alert('Please select a star rating!');
      return;
    }
    console.log({ rating, comments });
    alert(`Thanks for your ${rating}-star feedback!`);
    setRating(0);
    setComments('');
  };

  return (
    <div className="bg-white min-h-screen px-4 py-10 md:px-16">
      <div className="max-w-3xl mx-auto">
        {/* Header */}
        <div className="bg-gray-100 p-6 rounded text-center shadow mb-10">
          <h1 className="text-3xl font-bold text-gray-800">Got feedback? We're listening!</h1>
          <p className="text-gray-600 mt-2">Every thought counts—be a part of our evolution.</p>
        </div>

        <p className="text-lg mb-4 font-medium text-gray-800">
          Let us know how we did — your input matters
        </p>

        {/* Grouped Star Ratings */}
        <div className="flex flex-wrap gap-16 mb-6">
          {[1, 2, 3, 4, 5].map((group) => (
            <button
              key={group}
              type="button"
              onClick={() => setRating(group)}
              className="flex gap-1 group"
            >
              {[...Array(group)].map((_, i) => (
                <FaStar
                  key={i}
                  className={`text-3xl transition ${
                    rating === group ? 'text-yellow-400' : 'text-gray-300 group-hover:text-yellow-300'
                  }`}
                />
              ))}
            </button>
          ))}
        </div>

        {/* Comment Input */}
        <label htmlFor="comments" className="block font-semibold text-gray-700 mb-1">
          Your comments
        </label>
        <textarea
          id="comments"
          rows="4"
          placeholder="Tell us your experience..."
          value={comments}
          onChange={(e) => setComments(e.target.value)}
          className="w-full border-b border-gray-400 focus:outline-none focus:ring-1 focus:ring-indigo-400 py-2 px-1 mb-6"
        />

        {/* Submit Buttons */}
        <div className="flex items-center justify-between">
          <button
            onClick={handleSubmit}
            className="px-4 py-2 border border-black rounded hover:bg-gray-100 transition"
          >
            Submit Feedback
          </button>
          <button 
          className="text-sm text-blue-600 hover:underline"
          onClick={() => navigate('/member/memberdashboard')}
          >
            Maybe Later
          </button>
        </div>
      </div>
    </div>
  );
}
