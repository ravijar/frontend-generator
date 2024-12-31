import React from "react";
import "./Button.css";

const Button = ({ text, onClick, type = "button" }) => {
    return (
        <button className="custom-button" type={type} onClick={onClick}>
            {text}
        </button>
    );
}

export default Button