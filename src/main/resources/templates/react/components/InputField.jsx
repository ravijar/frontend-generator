import "./InputField.css";

export default function InputField ({ label, type = 'text', value, onChange, placeholder = '', error, styles = {} }) {
    const handleInputChange = (event) => {
        const { value } = event.target;
        onChange(value);
    };

    return (
        <div className="input-group" style={styles.inputGroup}>
            {label && <label className="label" style={styles.label}>{label}</label>}
            <input
                type={type}
                value={value}
                onChange={handleInputChange}
                placeholder={placeholder}
                className="input"
                style={styles.input}
            />
            {error && <span className="error" style={styles.error}>{error}</span>}
        </div>
    );
};

