import "./KeyValuePair.css";

export default function KeyValuePair ({ keyName, value, styles = {} }) {
    return (
        <div className="child-container" style={styles.childContainer}>
            <div className="child-key" style={styles.childKey}>{keyName}</div>
            <div className="child-value" style={styles.childValue}>{value}</div>
        </div>
    );
};