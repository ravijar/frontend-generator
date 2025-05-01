import { useAuth } from '../auth/AuthProvider';
import { useLocation, useNavigate } from 'react-router-dom';
import './Login.css';
import googleLogo from '../assets/google-logo.png';

export default function Login() {
    const { login } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();

    const from = location.state?.from?.pathname || "/";

    const handleLogin = () => {
        login({
            onSuccess: () => navigate(from, { replace: true }),
        });
    };

    return (
        <div className="login-container">
            <form className="login-form" onSubmit={(e) => { e.preventDefault(); handleLogin(); }}>
                <h2 className="login-title">Sign In</h2>
                <button type="submit" className="google-login-button">
                    <img src={googleLogo} alt="Google" className="google-icon" />
                    Sign in with Google
                </button>
            </form>
        </div>
    );
}
