import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { mealService } from '../../services/mealService';
import { Star } from 'lucide-react';

export default function MealHistory() {
  const [deliveredMealHistory, setDeliveredMealHistory] = useState([]);
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem('user'));

  useEffect(() => {
    const fetchHistory = async () => {
      try {
        const data = await mealService.getDeliveredMealHistory(); // Each item must include `rating`
        setDeliveredMealHistory(data);
      } catch (error) {
        console.error('Failed to fetch meal history:', error);
      }
    };

    fetchHistory();
  }, []);

  const handleRedirect = (orderId) => {
    if (user.role === 'MEMBER') {
      navigate(`/member/feedback/${orderId}`);
    } else if (user.role === 'CAREGIVER') {
      navigate(`/caregiver/feedback/${orderId}`);
    }
  };

  const renderStars = (rating) => {
    const stars = [];
    for (let i = 0; i < 5; i++) {
      stars.push(
        <Star
          key={i}
          size={16}
          className={i < rating ? 'text-yellow-500 fill-yellow-500' : 'text-gray-300'}
        />
      );
    }
    return <div className="flex justify-center">{stars}</div>;
  };

  return (
    <div className="px-4 py-12 sm:px-6 lg:px-8 bg-gray-50 min-h-screen">
      <div className="max-w-6xl mx-auto p-6 bg-white rounded-xl shadow-md">
        <h2 className="text-2xl font-bold text-gray-800 mb-6">Your Meal History</h2>

        <div className="overflow-x-auto rounded-lg border border-gray-200">
          <table className="min-w-full divide-y divide-gray-200 text-sm text-left">
            <thead className="bg-gray-100 text-gray-700">
              <tr>
                <th className="px-4 py-3">No.</th>
                <th className="px-4 py-3">Meal Name</th>
                <th className="px-4 py-3">Meal Type</th>
                <th className="px-4 py-3">Restaurant</th>
                <th className="px-4 py-3 text-center">Action</th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-100">
              {deliveredMealHistory.length === 0 ? (
                <tr>
                  <td colSpan="5" className="text-center text-gray-500 py-6">
                    No meals ordered yet.
                  </td>
                </tr>
              ) : (
                deliveredMealHistory.map((meal, index) => (
                  <tr key={meal.id} className="hover:bg-gray-50 transition align-middle">
                    <td className="px-4 py-2">{index + 1}</td>
                    <td className="px-4 py-2">{meal.mealName}</td>
                    <td className="px-4 py-2 capitalize">{meal.mealType}</td>
                    <td className="px-4 py-2">{meal.partnerName}</td>
                    <td className="px-4 py-2 text-center">
                      {meal.rating ? (
                        renderStars(meal.rating)
                      ) : (
                        <button
                          onClick={() => handleRedirect(meal.id)}
                          className="inline-flex items-center justify-center gap-1 bg-blue-600 text-white text-sm h-9 px-4 rounded-md hover:bg-blue-700 transition"
                        >
                          Rate <Star size={16} />
                        </button>
                      )}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
