import "./KeyValuePair.css";

export default function KeyValuePair ({ keyName, value, styles = {} }) {
    return (
        <div className="key-value-pair-container" style={styles.container}>
            <div className="key" style={styles.key}>{keyName}</div>
            <div className="value" style={styles.value}>{value}</div>
        </div>
    );
};