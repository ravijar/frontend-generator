import React from 'react';

export default function InputField ({ label, type = 'text', value, onChange, placeholder = '', required = false, error }) {
    const handleInputChange = (event) => {
        const { value } = event.target;
        onChange(value);
    };

    return (
        <div className="input-field">
            {label && <label>{label}</label>}
            <input
                type={type}
                value={value}
                onChange={handleInputChange}
                placeholder={placeholder}
            />
            {required && value.trim() === '' && <span className="error">This field is required</span>}
            {error && <span className="error">{error}</span>}
        </div>
    );
};

