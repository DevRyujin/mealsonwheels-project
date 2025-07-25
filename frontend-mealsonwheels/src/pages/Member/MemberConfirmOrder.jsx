import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { orderService } from '../../services/orderService';

const ConfirmOrderPage = () => {
  const navigate = useNavigate();
  const [pendingOrder, setPendingOrder] = useState(null);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    const stored = JSON.parse(localStorage.getItem("pendingOrder"));
    if (!stored) {
      const fallbackRole = localStorage.getItem("userRole")?.toLowerCase(); // or use auth context
      if (fallbackRole === "caregiver") return navigate("/caregiver/menus");
      else return navigate("/member/menus");
    }
    setPendingOrder(stored);
  }, [navigate]);

  if (!pendingOrder) return null;

  const { user, meal, quantity } = pendingOrder;

  const handleSubmit = async (e) => {
    e.preventDefault();
     if (submitting) return;
     setSubmitting(true);
    try {
      const orderPayload = {
        ...(user.role === "MEMBER" ? { memberId: user.id } : { caregiverId: user.id }),
        partnerId: meal.partnerId,
        totalAmount: 0,
        riderId: null,
        mealIds: [meal.id],
        meals: [
          {
            mealId: meal.id,
            mealName: meal.mealName,
            mealDesc: meal.mealDesc,
            mealPhoto: meal.photoData,
            mealType: meal.mealType,
            mealDietary: meal.mealDietary,
            mealCreatedDate: meal.mealCreatedDate,
            quantity
          }
        ]
      };
      console.log("Meal Type sent to backend:", meal.mealType);

      await orderService.placeOrder(orderPayload);
      localStorage.removeItem("pendingOrder");
      if (user.role === "MEMBER") {
        navigate("/member/order-success");
      } else if (user.role === "CAREGIVER") {
        navigate("/caregiver/order-success");
      }
    } catch (err) {
      console.error("Failed to confirm order:", err);
      alert("Failed to submit the order. Please try again.");
    }
  };

  return (
    <div className="min-h-screen flex flex-col">
      <main className="flex-grow p-6">
        <h2 className="text-left text-3xl font-semibold py-6 rounded mb-4">Confirm your order here!</h2>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-10 items-start">
          {/* 📸 Meal Image Card */}
          <div className="w-full flex justify-center">
            <div className="bg-white border rounded-xl shadow overflow-hidden p-0 w-full max-w-[800px] h-[500px] flex items-center justify-center mt-3">
              {meal?.photoData ? (
                <img
                  src={`data:${meal.mealPhotoType};base64,${meal.photoData}`}
                  alt={meal.mealName}
                  className="w-full h-full object-cover"
                />
              ) : (
                <p className="text-gray-400 text-center">No Image Available</p>
              )}
            </div>
          </div>


          {/* 📋 Info Card Styled Like a Form (but Read-only) */}
          <form onSubmit={handleSubmit} className="space-y-6">
            <fieldset className="border border-gray-300 p-4 rounded">
              <legend className="text-lg font-semibold">Your Details</legend>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
                <ReadOnlyField label="Full Name" value={user.name} />
                <ReadOnlyField label="Email" value={user.email} />
                <ReadOnlyField label="Address" value={user.address} />
                <ReadOnlyField label="Phone" value={user.phone} />

                {user.role === "CAREGIVER" && (
                  <>
                    <ReadOnlyField label="Caregiver Name" value={user.caregiverName || "N/A"} />
                    <ReadOnlyField label="Relation" value={user.caregiverRelation || "N/A"} />
                  </>
                )}
              </div>
            </fieldset>

            <fieldset className="border border-gray-300 p-4 rounded">
              <legend className="text-lg font-semibold">Menu to order</legend>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
                <ReadOnlyField label="Menu Name" value={meal.mealName} />
                <ReadOnlyField label="Meal Type" value={meal.mealType} />
                <ReadOnlyField label="Quantity" value={quantity} />
                <ReadOnlyField label="Created Date" value={meal.mealCreatedDate?.slice(0, 10)} />
              </div>
            </fieldset>

            <div className="text-center">
              <button
                type="submit"
                disabled={submitting}
                className="inline-block mt-4 px-6 py-2 border border-black rounded hover:bg-gray-200 transition disabled:opacity-50"
              >
                {submitting ? "Placing Order..." : "Confirm Order"}
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

const ReadOnlyField = ({ label, value }) => (
  <div className="flex flex-col">
    <label className="text-sm text-gray-500 mb-1">{label}</label>
    <div className="border p-2 rounded bg-gray-100 text-gray-800">{value}</div>
  </div>
);

export default ConfirmOrderPage;
