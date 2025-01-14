import RecursiveKeyValuePair from "./RecursiveKeyValuePair";
import "./CardSection.css";

const CardSection = ({ responseData, responseSchema, displayNames, styles = {}, children }) => {
    const renderContent = () => {
        switch (responseSchema.type) {
            case "null":
                return (
                    <div className="single-card-container" style={styles.keyValuePair}>
                        <RecursiveKeyValuePair data={responseData} displayNames={displayNames}/>
                        {children}
                    </div>
                );
            case "array":
                return (
                    <div className="card-array-container" style={styles.cardArrayContainer}>
                        {responseData.map((item, index) => (
                            <div key={index} className="card-array-item" style={styles.cardArrayItem}>
                                <RecursiveKeyValuePair data={item} displayNames={displayNames}/>
                                {children}
                            </div>
                        ))}
                    </div>
                );
            default:
                return null;
        }
    };

    return <>{renderContent()}</>;
};

export default CardSection;
