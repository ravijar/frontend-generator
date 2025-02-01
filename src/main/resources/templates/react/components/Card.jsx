import RecursiveKeyValuePair from "./RecursiveKeyValuePair";
import "./Card.css";
import {Children, cloneElement, isValidElement} from "react";

const Card = ({ item, displayNames, styles = {}, children }) => {
    return (
        <div className="detailed-card" style={styles.detailedCard}>
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
    );
};

export default Card;