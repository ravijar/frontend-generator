import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthProvider";
import "./NavBar.css";

export default function NavBar() {
    const location = useLocation();
    const navigate = useNavigate();
    const { user, logout } = useAuth();

    const handleAuthClick = () => {
        if (user) {
            logout();
        } else {
            navigate("/login");
        }
    };

    return (
        <nav className="navbar">
            <div className="logo">LOGO</div>
            <div className="menu">
                <ul className="menu-links">
<#list pages as page>
                    <li className={location.pathname === "${page.route}" ? "active" : ""}>
                        <Link to="${page.route}">${page.name}</Link>
                    </li>
</#list>
                </ul>
                <button className="auth-button" onClick={handleAuthClick}>
                    {user ? "Logout" : "Login"}
                </button>
            </div>
        </nav>
    );
}