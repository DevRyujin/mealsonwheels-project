import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function AddMenuPage() {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [image, setImage] = useState(null);
  const [submitted, setSubmitted] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleImageUpload = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImage(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleCreateMenu = () => {
    if (!title || !description || !image) {
      setError("Please fill out all fields and upload an image.");
      return;
    }
    setError('');
    setSubmitted(true);
  };

  const handleConfirm = () => {
    navigate('/member/meal-order');
  };

  return (
    <div className="min-h-screen bg-white flex flex-col justify-between px-8 py-10">
      {submitted ? (
        <div className="flex items-center justify-center">
          <div className="max-w-md w-full bg-white border rounded-xl shadow-md p-6 space-y-4 text-center">
            <img src={image} alt="Menu Preview" className="w-full h-64 object-cover rounded-md border" />
            <h1 className="text-2xl font-bold text-gray-800">{title}</h1>
            <p className="text-gray-600 text-sm">{description}</p>
            <button
              onClick={handleConfirm}
              className="w-full bg-gradient-to-r from-orange-300 to-red-600 text-white font-semibold py-2 rounded hover:brightness-90 transform transition-transform active:scale-95"
            >
              Confirm 
            </button>
          </div>
        </div>
      ) : (
        <main className="flex flex-col md:flex-row gap-8">
          <div className="flex-1 bg-gray-200 flex items-center justify-center text-2xl font-semibold text-center p-10 rounded">
            {image ? (
              <img src={image} alt="Menu Preview" className="max-h-64 rounded object-contain" />
            ) : (
              "Create your ideal menu with us!"
            )}
          </div>

          <div className="flex-1 space-y-6">
            {error && <p className="text-red-500 text-sm font-medium">{error}</p>}

            <div>
              <label className="block mb-1 font-semibold">Menu Title</label>
              <input
                type="text"
                className="w-full border rounded px-3 py-2"
                placeholder="Enter menu title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
              />
            </div>

            <div>
              <label className="block mb-1 font-semibold">Menu Picture</label>
              <div className="border rounded p-4 text-center bg-gray-100">
                <input type="file" accept="image/*" onChange={handleImageUpload} />
                <p className="text-sm mt-2">Click to upload or drag and drop</p>
              </div>
            </div>

            <div>
              <label className="block mb-1 font-semibold">Menu Description</label>
              <textarea
                className="w-full border rounded px-3 py-2"
                placeholder="Describe your menu"
                rows="4"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              ></textarea>
            </div>

            <button
              onClick={handleCreateMenu}
              className="border px-4 py-2 rounded hover:bg-gray-200"
            >
              Create Menu
            </button>
          </div>
        </main>
      )}
    </div>
  );
}
