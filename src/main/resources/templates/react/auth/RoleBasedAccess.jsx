import { useAuth } from "./AuthProvider.jsx";

export default function RoleBasedAccess({ roles = [], children }) {
    const { user } = useAuth();

    if (roles.length > 0 && (!user || !roles.includes(user.role))) {
        return null;
    }

    return children;
}
