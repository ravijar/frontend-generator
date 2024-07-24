import KeyValuePair from "../components/KeyValuePair";
import "./RecursiveKeyValuePair.css";

export default function RecursiveKeyValuePair ({ data, parentKey = '' }) {
    return (
        <>
            {Object.entries(data).map(([key, value]) => {
                const newKey = parentKey ? `${parentKey}.${key}` : key;
                if (value && typeof value === 'object' && !Array.isArray(value)) {
                    return (
                        <div key={newKey} className="nested-container">
                            <div className="nested-key">{newKey}</div>
                            <RecursiveKeyValuePair data={value} parentKey={newKey} />
                        </div>
                    );
                } else {
                    return (
                        <KeyValuePair
                            key={newKey}
                            keyName={newKey}
                            value={value}
                        />
                    );
                }
            })}
        </>
    );
};