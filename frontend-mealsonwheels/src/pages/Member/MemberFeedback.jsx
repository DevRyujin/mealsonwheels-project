// src/pages/MemberFeedback.jsx
import React, { useState } from 'react';

export default function MemberFeedback() {
  const [rating, setRating] = useState(0);
  const [hover, setHover] = useState(0);
  const [comments, setComments] = useState('');

  const handleSubmit = () => {
    console.log({ rating, comments });
    alert('Feedback submitted!');
    // submit to API here if needed
  };

  return (
    <div className="bg-white min-h-screen px-4 py-10 md:px-16">
      <div className="bg-gray-200 p-6 rounded text-center max-w-4xl mx-auto">
        <h1 className="text-2xl font-bold">Got feedback? We're listening!</h1>
        <p className="mt-2 text-gray-700">Every thought counts—be a part of our evolution.</p>
      </div>

      <div className="mt-10 max-w-2xl mx-auto">
        <p className="mb-4 text-lg">Let us know how we did—your input matters</p>

        <div className="flex gap-2 mb-6">
          {[...Array(10)].map((_, i) => {
            const index = i + 1;
            return (
              <button
                key={index}
                type="button"
                onClick={() => setRating(index)}
                onMouseEnter={() => setHover(index)}
                onMouseLeave={() => setHover(0)}
              >
                <span className={`text-3xl ${index <= (hover || rating) ? 'text-yellow-400' : 'text-gray-300'}`}>
                  ★
                </span>
              </button>
            );
          })}
        </div>

        <label htmlFor="comments" className="block font-semibold mb-1">
          Your comments
        </label>
        <textarea
          id="comments"
          rows="4"
          placeholder="Tell us your experience..."
          value={comments}
          onChange={(e) => setComments(e.target.value)}
          className="w-full border-b border-gray-400 focus:outline-none py-2 px-1 mb-6"
        />

        <div className="flex items-center justify-between">
          <button
            onClick={handleSubmit}
            className="px-4 py-2 border border-black rounded hover:bg-gray-100"
          >
            Submit Feedback
          </button>
          <button className="text-sm text-blue-600 hover:underline">Maybe Later</button>
        </div>
      </div>
    </div>
  );
}
