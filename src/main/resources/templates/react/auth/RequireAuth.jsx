import { Navigate, useLocation } from 'react-router-dom';
import { useAuth } from './AuthProvider';

export default function RequireAuth({ children, roles = [] }) {
    const { token, user } = useAuth();
    const location = useLocation();

    if (!token) {
        return <Navigate to="/login" state={{ from: location }} replace />;
    }

    if (roles.length > 0 && (!user || !roles.includes(user.role))) {
        return <Navigate to="/unauthorized" replace />;
    }

    return children;
}
