import KeyValuePair from "./KeyValuePair";
import "./KeyValuePair.css";
import { getDisplayName } from "../common/Utils";

export default function RecursiveKeyValuePair ({ data, displayNames, parentKey = "", styles = {} }) {
    return (
        <>
            {Object.entries(data).map(([key, value]) => {
                const newKey = parentKey ? `${parentKey}.${key}` : key;
                if (value && typeof value === 'object' && !Array.isArray(value)) {
                    return (
                        <div key={newKey} className="parent-container" style={styles.parentContainer}>
                            <div className="parent-key" style={styles.parentKey}>{getDisplayName(displayNames, key)}</div>
                            <RecursiveKeyValuePair data={value} displayNames={displayNames} parentKey={newKey} styles={styles}/>
                        </div>
                    );
                } else {
                    return (
                        <KeyValuePair
                            key={newKey}
                            keyName={getDisplayName(displayNames, key)}
                            value={value}
                            styles={styles}
                        />
                    );
                }
            })}
        </>
    );
};