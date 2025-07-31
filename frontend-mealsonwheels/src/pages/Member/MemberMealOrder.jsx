import React, { useEffect, useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import axiosInstance from "../../api/axiosInstance";

const MealDetailsPage = () => {
  const [meals, setMeals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [quantities, setQuantities] = useState({});
  const [quantityErrors, setQuantityErrors] = useState({});
  const [isSubmittingId, setIsSubmittingId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchMeals = async () => {
      const delay = new Promise((resolve) => setTimeout(resolve, 3000)); // ⏳ Minimum 3 seconds
      try {
        const storedUser = JSON.parse(localStorage.getItem("user"));
        const role = storedUser?.role;
        const userId = storedUser?.id;

        if (!role || !userId) {
          throw new Error("User not found in localStorage.");
        }

        let endpoint;
        if (role === "MEMBER") {
          endpoint = `/meals/member/${userId}/filtered`;
        } else if (role === "CAREGIVER") {
          endpoint = `/meals/caregiver/${userId}/filtered`;
        } else {
          throw new Error("Unsupported user role");
        }

        const mealRes = await axiosInstance.get(endpoint);
        await delay; // ⏳ Wait even if data is already available
        setMeals(mealRes.data);
      } catch (err) {
        console.error("Failed to fetch meals:", err);
        await delay; // Ensure the bowing gif is shown
        setError(
          "No current meal available. Please come back again later. Thank you!"
        );
      } finally {
        setLoading(false);
      }
    };

    fetchMeals();
  }, []);

  const handleQuantityChange = (mealId, value) => {
    setQuantities((prev) => ({
      ...prev,
      [mealId]: value,
    }));
  };

  const handleQuantityInput = (e, mealId) => {
    const value = Number(e.target.value);

    if (value < 1) {
      setQuantityErrors((prev) => ({
        ...prev,
        [mealId]: "Minimum quantity is 1",
      }));
      handleQuantityChange(mealId, 1);
    } else {
      setQuantityErrors((prev) => ({
        ...prev,
        [mealId]: null,
      }));
      handleQuantityChange(mealId, value);
    }
  };

  const handleOrder = async (meal) => {
    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user"));

    if (
      !token ||
      !user ||
      (user.role !== "MEMBER" && user.role !== "CAREGIVER")
    ) {
      alert("Please log in as a member or caregiver.");
      return;
    }

    if (quantityErrors[meal.id]) {
      alert("Please fix quantity before ordering.");
      return;
    }

    const quantity = quantities[meal.id] || 1;

    const pendingOrder = {
      quantity,
      user,
      meal: {
        id: meal.id,
        mealName: meal.mealName,
        mealDesc: meal.mealDesc,
        mealPhotoType: meal.mealPhotoType,
        photoData: meal.photoData,
        mealCreatedDate: meal.mealCreatedDate,
        mealType: meal.mealType,
        mealDietary: meal.mealDietary,
        partnerId: meal.partnerId,
      },
    };

    try {
      setIsSubmittingId(meal.id);
      localStorage.setItem("pendingOrder", JSON.stringify(pendingOrder));
      localStorage.setItem("userRole", user.role);

      if (user.role === "MEMBER") {
        navigate("/member/confirm-order");
      } else if (user.role === "CAREGIVER") {
        navigate("/caregiver/confirm-order");
      }
    } finally {
      setIsSubmittingId(null);
    }
  };

  return (
    <div className="min-h-screen flex flex-col bg-white">
      <main className="flex-grow p-6">
        <h1 className="text-2xl font-bold text-center mb-6">ORDER HERE</h1>

        <p className="bg-yellow-50 border-l-4 border-yellow-400 p-4 rounded text-sm text-gray-800 mb-8">
          Meals shown here are available today. Whether you receive them as{" "}
          <strong>hot</strong> or <strong>frozen</strong> will depend on your
          location.
        </p>

        {/* Loading (Always 3s minimum) */}
        {loading && (
          <div className="text-center">
            <p className="text-gray-900">
              Fetching today's meals... Please wait.
            </p>
            <img
              src="/images/bow.gif"
              alt="bow"
              className="w-full max-w-xs mx-auto my-4"
            />
          </div>
        )}

        {/* Error */}
        {!loading && error && (
          <div className="text-center">
            <p className="text-gray-900">{error}</p>
            <img
              src="/images/bow.gif"
              alt="bow"
              className="w-full max-w-xs mx-auto my-4"
            />
          </div>
        )}

        {/* Meals List */}
        {!loading && !error && (
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-6 max-w-6xl mx-auto">
            {meals.map((meal) => (
              <div
                key={meal.id}
                className="bg-white border rounded-xl shadow hover:shadow-md transition overflow-hidden"
              >
                <img
                  src={`data:${meal.mealPhotoType};base64,${meal.photoData}`}
                  alt={meal.mealName}
                  className="w-full h-52 object-cover"
                />
                <div className="p-5">
                  <h2 className="text-xl font-bold mb-1">{meal.mealName}</h2>
                  <p className="text-sm text-gray-600 mb-4">{meal.mealDesc}</p>

                  <div className="flex flex-col sm:flex-row justify-between gap-4 mb-4">
                    <div className="text-sm text-gray-700">
                      <strong>Created:</strong>{" "}
                      {meal.mealCreatedDate?.slice(0, 10)}
                    </div>
                    <div className="text-sm text-gray-500 italic">
                      *Delivery type depends on your distance.
                    </div>
                  </div>

                  <div className="mb-3">
                    <label
                      htmlFor={`qty-${meal.id}`}
                      className="text-sm block mb-1"
                    >
                      Quantity:
                    </label>
                    <input
                      id={`qty-${meal.id}`}
                      type="number"
                      min="1"
                      value={quantities[meal.id] || 1}
                      onChange={(e) => handleQuantityInput(e, meal.id)}
                      className="w-20 border rounded px-2 py-1 text-sm"
                    />
                    {quantityErrors[meal.id] && (
                      <p className="text-red-500 text-sm mt-1">
                        {quantityErrors[meal.id]}
                      </p>
                    )}
                  </div>

                  <div className="flex gap-4">
                    <NavLink
                      to="/food-safety"
                      className="w-full text-center border border-gray-400 px-4 py-2 rounded hover:bg-gray-100 text-sm"
                    >
                      Food Safety
                    </NavLink>
                    <button
                      onClick={() => handleOrder(meal)}
                      disabled={isSubmittingId === meal.id}
                      className={`w-full text-center px-4 py-2 rounded text-white ${
                        isSubmittingId === meal.id
                          ? "bg-blue-300 cursor-not-allowed"
                          : "bg-blue-500 hover:bg-blue-600"
                      }`}
                    >
                      {isSubmittingId === meal.id ? "Placing..." : "Order Now"}
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
};

export default MealDetailsPage;
