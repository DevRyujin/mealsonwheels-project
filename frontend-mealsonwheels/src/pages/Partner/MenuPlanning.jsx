import React, { useState, useEffect } from 'react';

export default function MenuPlanningPage() {
  const [dishes, setDishes] = useState([
    { name: '', price: '', quantity: '', ingredients: '', ingredientsAvailable: '' },
  ]);

  const [ingredients, setIngredients] = useState([
    { name: '', stock: '', lowStock: false, checkAvailability: false },
  ]);

  const [isRemoving, setIsRemoving] = useState(false);
  const [selectedToRemove, setSelectedToRemove] = useState([]);

  const [isDishRemoving, setIsDishRemoving] = useState(false);
  const [selectedDishToRemove, setSelectedDishToRemove] = useState(null);

  // Dish handlers
  const handleAddDish = () => {
    setDishes([...dishes, { name: '', price: '', quantity: '', ingredients: '', ingredientsAvailable: '' }]);
  };

  const handleRemoveDish = (index) => {
    const updated = [...dishes];
    updated.splice(index, 1);
    setDishes(updated);
  };

useEffect(() => {
  if (!isDishRemoving && selectedDishToRemove !== null) {
    handleRemoveDish(selectedDishToRemove);
    setSelectedDishToRemove(null);
  }
  // eslint-disable-next-line react-hooks/exhaustive-deps
}, [isDishRemoving, selectedDishToRemove]);

  // Ingredient handlers
  const handleAddIngredient = () => {
    setIngredients([...ingredients, { name: '', stock: '', lowStock: false, checkAvailability: false }]);
  };

  const toggleRemoveMode = () => {
    if (isRemoving) {
      const remaining = ingredients.filter((_, idx) => !selectedToRemove.includes(idx));
      setIngredients(remaining);
      setSelectedToRemove([]);
      setIsRemoving(false);
    } else {
      setIsRemoving(true);
    }
  };

  const toggleSelected = (index) => {
    if (selectedToRemove.includes(index)) {
      setSelectedToRemove(selectedToRemove.filter((i) => i !== index));
    } else {
      setSelectedToRemove([...selectedToRemove, index]);
    }
  };

  // Save function
  const handleSave = () => {
    console.log("=== Dishes ===", dishes);
    console.log("=== Ingredients ===", ingredients);
    alert("Changes saved successfully!");
  };

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      <h1 className="text-2xl font-bold mb-4 text-center">Menu Planning & Preparation</h1>

      {/* Dish Buttons */}
      <div className="flex justify-end gap-2 mb-4">
        <button onClick={handleAddDish} className="bg-green-500 text-white px-3 py-1 rounded text-sm">+ Add Dish</button>
        <button onClick={() => setIsDishRemoving(!isDishRemoving)} className="bg-red-500 text-white px-3 py-1 rounded text-sm">
          {isDishRemoving ? 'Confirm Remove' : 'Remove'}
        </button>
      </div>

      {/* Dish Inputs */}
      {dishes.map((dish, index) => (
        <div key={index} className="grid grid-cols-6 gap-2 mb-3 items-center">
          <input
            type="text"
            placeholder="Dish Name"
            className="border p-2 rounded h-10"
            value={dish.name}
            onChange={(e) => {
              const updated = [...dishes];
              updated[index].name = e.target.value;
              setDishes(updated);
            }}
          />
          <input
            type="text"
            placeholder="Price"
            className="border p-2 rounded h-10"
            value={dish.price}
            onChange={(e) => {
              const updated = [...dishes];
              updated[index].price = e.target.value;
              setDishes(updated);
            }}
          />
          <input
            type="text"
            placeholder="Quantity"
            className="border p-2 rounded h-10"
            value={dish.quantity}
            onChange={(e) => {
              const updated = [...dishes];
              updated[index].quantity = e.target.value;
              setDishes(updated);
            }}
          />
          <input
            type="text"
            placeholder="Ingredients"
            className="border p-2 rounded h-10"
            value={dish.ingredients}
            onChange={(e) => {
              const updated = [...dishes];
              updated[index].ingredients = e.target.value;
              setDishes(updated);
            }}
          />
          <input
            type="text"
            placeholder="Ingredients Available"
            className="border p-2 rounded h-10"
            value={dish.ingredientsAvailable}
            onChange={(e) => {
              const updated = [...dishes];
              updated[index].ingredientsAvailable = e.target.value;
              setDishes(updated);
            }}
          />
          {isDishRemoving && (
            <input
              type="radio"
              name="dishToRemove"
              className="h-4 w-4"
              onChange={() => setSelectedDishToRemove(index)}
            />
          )}
        </div>
      ))}

      {/* Ingredient Tracker */}
      <h2 className="text-xl font-bold mt-8 mb-4">Ingredient Tracker</h2>

      <div className="flex justify-end gap-2 mb-4">
        <button onClick={handleAddIngredient} className="bg-green-500 text-white px-3 py-1 rounded text-sm">+ Add Ingredient</button>
        <button onClick={toggleRemoveMode} className="bg-red-500 text-white px-3 py-1 rounded text-sm">
          {isRemoving ? 'Confirm Remove' : 'Remove'}
        </button>
      </div>

      {ingredients.map((ingredient, index) => (
        <div key={index} className="grid grid-cols-6 gap-2 mb-2 items-center">
          <input
            type="text"
            placeholder="Ingredient"
            className="border p-2 rounded h-10"
            value={ingredient.name}
            onChange={(e) => {
              const updated = [...ingredients];
              updated[index].name = e.target.value;
              setIngredients(updated);
            }}
          />
          <input
            type="text"
            placeholder="Stock"
            className="border p-2 rounded h-10"
            value={ingredient.stock}
            onChange={(e) => {
              const updated = [...ingredients];
              updated[index].stock = e.target.value;
              setIngredients(updated);
            }}
          />
          <label className="flex items-center space-x-2">
            <input
              type="checkbox"
              checked={ingredient.lowStock}
              onChange={(e) => {
                const updated = [...ingredients];
                updated[index].lowStock = e.target.checked;
                setIngredients(updated);
              }}
            />
            <span className="text-sm">Low Stock</span>
          </label>
          <label className="flex items-center space-x-2">
            <input
              type="checkbox"
              checked={ingredient.checkAvailability}
              onChange={(e) => {
                const updated = [...ingredients];
                updated[index].checkAvailability = e.target.checked;
                setIngredients(updated);
              }}
            />
            <span className="text-sm">Check Availability</span>
          </label>
          {isRemoving && (
            <input
              type="checkbox"
              className="h-4 w-4"
              checked={selectedToRemove.includes(index)}
              onChange={() => toggleSelected(index)}
            />
          )}
        </div>
      ))}

      {/* Save Button */}
      <div className="flex justify-end mt-6">
        <button onClick={handleSave} className="bg-blue-600 text-white px-6 py-2 rounded">
          Save
        </button>
      </div>
    </div>
  );
}