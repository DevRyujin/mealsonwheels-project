import { Navigate } from 'react-router-dom';
import { useEffect, useState } from 'react';

const RedirectFoodSafety = () => {
  const [redirectTo, setRedirectTo] = useState(null);

  useEffect(() => {
    const role = localStorage.getItem('userType');

    if (role === 'partner') {
      setRedirectTo('/partner/partnersfoodsafety');
    } else if (role === 'member') {
      setRedirectTo('/member/foodsafety'); // You can change this path if needed
    } else {
      setRedirectTo('/unauthorized'); // fallback
    }
  }, []);

  if (!redirectTo) return null;

  return <Navigate to={redirectTo} replace />;
};

export default RedirectFoodSafety;
