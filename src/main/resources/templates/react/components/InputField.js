import "./InputField.css";

export default function InputField ({ label, type = 'text', value, onChange, placeholder = '', error, styles = {} }) {
    const handleInputChange = (event) => {
        const { value } = event.target;
        onChange(value);
    };

    return (
        <div className="input-field-container" style={styles.container}>
            {label && <label className="input-field-label" style={styles.label}>{label}</label>}
            <input
                type={type}
                value={value}
                onChange={handleInputChange}
                placeholder={placeholder}
                className="input-field"
                style={styles.input}
            />
            {error && <span className="input-field-error" style={styles.error}>{error}</span>}
        </div>
    );
};

