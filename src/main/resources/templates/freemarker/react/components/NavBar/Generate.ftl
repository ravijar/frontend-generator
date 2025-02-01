import { Link, useLocation } from "react-router-dom";
import "./NavBar.css";

export default function NavBar() {
    const location = useLocation();

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
            </div>
        </nav>
    );
}