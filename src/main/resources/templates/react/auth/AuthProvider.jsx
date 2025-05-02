import { createContext, useContext, useState } from 'react';
import { googleLogout, useGoogleLogin } from '@react-oauth/google';
import { createApiClient } from "../common/ClientAPIWrapper.js";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [user, setUser] = useState(() => {
        const storedUser = localStorage.getItem('user');
        return storedUser ? JSON.parse(storedUser) : null;
    });

    const [token, setToken] = useState(() => {
        return localStorage.getItem('token') || null;
    });

    let loginCallback = null;

    const loginGoogle = useGoogleLogin({
        onSuccess: async (response) => {
            const accessToken = response.access_token;

            try {
                const clientApi = createApiClient(accessToken);
                const backendUser = await clientApi.login(accessToken);

                setToken(accessToken);
                setUser(backendUser);

                localStorage.setItem('token', accessToken);
                localStorage.setItem('user', JSON.stringify(backendUser));

                console.log("User authenticated with backend.");
                if (loginCallback) loginCallback();

            } catch (err) {
                console.error("Backend login failed:", err);
                googleLogout();
            }
        },
        onError: (error) => console.error('Login Failed:', error),
        flow: 'implicit',
    });

    const login = ({ onSuccess } = {}) => {
        loginCallback = onSuccess;
        loginGoogle();
    };

    const logout = () => {
        googleLogout();
        setUser(null);
        setToken(null);
        localStorage.removeItem('user');
        localStorage.removeItem('token');
    };

    return (
        <AuthContext.Provider value={{ user, token, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);
