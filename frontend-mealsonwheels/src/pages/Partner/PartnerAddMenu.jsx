import React, { useState } from 'react';

export default function AddMenuPage() {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [image, setImage] = useState(null);

  const handleImageUpload = (e) => {
    setImage(e.target.files[0]);
  };

  const handleCreateMenu = () => {
    // Logic for creating a menu goes here
    console.log({ title, description, image });
  };

  return (
    <div className="min-h-screen bg-white flex flex-col justify-between">

      {/* Main Content */}
      <main className="flex flex-col md:flex-row px-8 py-10 gap-8">
        <div className="flex-1 bg-gray-200 flex items-center justify-center text-2xl font-semibold text-center p-10 rounded">
          Create your ideal menu with us!
        </div>
        <div className="flex-1 space-y-6">
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
              <input type="file" onChange={handleImageUpload} />
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
            className="border px-4 py-2 rounded hover:bg-gray-100"
          >
            Create Menu
          </button>
        </div>
      </main>
    </div>
  );
}
