import React from "react";
import "./Button.css";

const Button = ({ text, onClick = () => {}, type = "button", styles = {}, id }) => {
    return (
        <button
            className="custom-button"
            type={type}
            onClick={() => onClick(id)}
            style={styles.customButton}
        >
            {text}
        </button>
    );
};

export default Button;