import { createContext, useContext, useState } from 'react';
import { GoogleOAuthProvider, googleLogout, useGoogleLogin } from '@react-oauth/google';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(null);

    const login = useGoogleLogin({
        onSuccess: (response) => {
            setToken(response.access_token);
            setUser(response);
        },
        onError: (error) => console.log('Login Failed:', error),
        flow: 'implicit',
    });

    const logout = () => {
        googleLogout();
        setUser(null);
        setToken(null);
    };

    return (
        <AuthContext.Provider value={{ user, token, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);
