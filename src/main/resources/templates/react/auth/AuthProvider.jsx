import { createContext, useContext, useState } from 'react';
import { googleLogout, useGoogleLogin } from '@react-oauth/google';
import { createApiClient } from "../common/ClientAPIWrapper.js";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(null);

    let loginCallback = null;

    const loginGoogle = useGoogleLogin({
        onSuccess: async (response) => {
            const accessToken = response.access_token;

            setToken(accessToken);
            setUser(response);

            try {
                const clientApi = createApiClient(accessToken);
                await clientApi.login(accessToken);
                console.log("User authenticated with backend.");
            } catch (err) {
                console.error("Backend login failed:", err);
            }

            if (loginCallback) loginCallback();
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
    };

    return (
        <AuthContext.Provider value={{ user, token, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);
