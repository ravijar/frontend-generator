import React from "react";
import "./Button.css";

const Button = ({ text, onClick, type = "button", styles = {} }) => {
    return (
        <button className="custom-button" type={type} onClick={onClick} style={styles.customButton}>
            {text}
        </button>
    );
}

export default Button