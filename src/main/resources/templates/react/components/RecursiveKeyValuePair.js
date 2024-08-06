import KeyValuePair from "../components/KeyValuePair";
import "./KeyValuePair.css";

export default function RecursiveKeyValuePair ({ data, parentKey = '', styles = {} }) {
    return (
        <>
            {Object.entries(data).map(([key, value]) => {
                const newKey = parentKey ? `${parentKey}.${key}` : key;
                if (value && typeof value === 'object' && !Array.isArray(value)) {
                    return (
                        <div key={newKey} className="parent-container" style={styles.parentContainer}>
                            <div className="parent-key" style={styles.parentKey}>{newKey}</div>
                            <RecursiveKeyValuePair data={value} parentKey={newKey} styles={styles}/>
                        </div>
                    );
                } else {
                    return (
                        <KeyValuePair
                            key={newKey}
                            keyName={newKey}
                            value={value}
                            styles={styles}
                        />
                    );
                }
            })}
        </>
    );
};