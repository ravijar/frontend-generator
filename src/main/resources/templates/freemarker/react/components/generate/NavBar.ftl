import { Link, useLocation } from "react-router-dom";
import "./NavBar.css";

export default function NavBar() {
    const location = useLocation();

    return (
        <nav className="navbar">
            <ul className="navbar-links">
<#list data as page>
                <li className={location.pathname === "${page.route}" ? "active" : ""}>
                    <Link to="${page.route}">${page.name}</Link>
                </li>
</#list>
            </ul>
        </nav>
    );
}