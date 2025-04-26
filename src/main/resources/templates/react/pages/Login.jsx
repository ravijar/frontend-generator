import { useAuth } from '../auth/AuthProvider';

export default function Login() {
    const { login } = useAuth();

    return (
        <div className="page-container">
            <h1>Welcome to Pet Store Admin</h1>
            <p>Please sign in to continue</p>
            <button onClick={() => login()}>Sign In with Google</button>
        </div>
    );
}
