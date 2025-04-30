import { useAuth } from '../auth/AuthProvider';
import './Login.css';
import googleLogo from '../assets/google-logo.png';

export default function Login() {
    const { login } = useAuth();

    return (
        <div className="login-container">
            <p className="login-message">
                You need to sign in to access this content.
            </p>
            <button className="google-login-button" onClick={() => login()}>
                <img src={googleLogo} alt="Google" className="google-icon" />
                Sign in with Google
            </button>
        </div>
    );
}
