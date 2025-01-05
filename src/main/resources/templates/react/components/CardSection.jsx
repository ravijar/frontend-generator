import RecursiveKeyValuePair from "./RecursiveKeyValuePair";
import "./CardSection.css";

const CardSection = ({ responseData, responseSchema }) => {
    const renderContent = () => {
        switch (responseSchema.type) {
            case "null":
                return (
                    <div className="single-card-containerr">
                        <RecursiveKeyValuePair data={responseData} />
                    </div>
                );
            case "array":
                return (
                    <div className="card-array-container">
                        {responseData.map((item, index) => (
                            <div key={index} className="card-array-item">
                                <RecursiveKeyValuePair data={item} />
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
