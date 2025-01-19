import RecursiveKeyValuePair from "./RecursiveKeyValuePair";
import "./CardSection.css";
import {Children, cloneElement, isValidElement} from "react";

const CardSection = ({ responseData, responseSchema, displayNames, styles = {}, children }) => {
    const renderContent = () => {
        switch (responseSchema.type) {
            case "null":
                return (
                    <div className="single-card-container" style={styles.keyValuePair}>
                        <RecursiveKeyValuePair data={responseData} displayNames={displayNames}/>
                        <div className="children-container" style={styles.childrenContainer}>
                            {children}
                        </div>
                    </div>
                );
            case "array":
                return (
                    <div className="card-array-container" style={styles.cardArrayContainer}>
                        {responseData.map((item, index) => (
                            <div key={item.id} className="card-array-item" style={styles.cardArrayItem}>
                                <RecursiveKeyValuePair data={item} displayNames={displayNames}/>
                                <div className="children-container" style={styles.childrenContainer}>
                                    {Children.map(children, (child) =>
                                        isValidElement(child)
                                            ? cloneElement(child, {
                                                children: Children.map(child.props.children, (nestedChild) =>
                                                    isValidElement(nestedChild)
                                                        ? cloneElement(nestedChild, { id: item.id })
                                                        : nestedChild
                                                ),
                                            })
                                            : child
                                    )}
                                </div>
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
