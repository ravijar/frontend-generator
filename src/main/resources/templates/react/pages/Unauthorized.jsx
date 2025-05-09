import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {faExclamationTriangle} from "@fortawesome/free-solid-svg-icons";
import "./Unauthorized.css";
import Button from "../components/Button.jsx";

export default function Unauthorized() {
    const navigate = useNavigate();

    const handleReturn = () => {
        navigate("/");
    };

    return (
        <div className="unauthorized-container">
            <FontAwesomeIcon icon={faExclamationTriangle } className="unauthorized-icon" />
            <h2 className="unauthorized-title">Access Denied</h2>
            <p className="unauthorized-message">
                You are unauthorized to visit this page.
                <br />
                Please contact user support for more information.
            </p>
            <Button
                text="Return"
                onClick={handleReturn}
            />
        </div>
    );
}
