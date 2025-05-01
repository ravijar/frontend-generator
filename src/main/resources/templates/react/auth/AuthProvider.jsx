import { createContext, useContext, useState } from 'react';
import { googleLogout, useGoogleLogin } from '@react-oauth/google';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(null);

    const loginGoogle = useGoogleLogin({
        onSuccess: (response) => {
            setToken(response.access_token);
            setUser(response);
            if (loginCallback) loginCallback();
        },
        onError: (error) => console.error('Login Failed:', error),
        flow: 'implicit',
    });

    let loginCallback = null;
    const login = ({ onSuccess } = {}) => {
        loginCallback = onSuccess;
        loginGoogle();
    };

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
