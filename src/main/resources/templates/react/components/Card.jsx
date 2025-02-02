import RecursiveKeyValuePair from "./RecursiveKeyValuePair";
import "./Card.css";
import {Children, cloneElement, isValidElement} from "react";

const Card = ({ item, displayNames, styles = {}, children }) => {
    return (
        <div className="detailed-card" style={styles.detailedCard}>
            <div className="detailed-card-img" style={styles.cardImg}>
                <img src={item.image} alt={item.title}></img>
            </div>
            <div className="content-container" style={styles.contentContainer}>
                <div className="detailed-card-info" style={styles.detailedCardInfo}>
                    <p className="detailed-card-title" style={styles.detailedCardTitle}>{item.title}</p>
                    <p className="detailed-card-body" style={styles.detailedCardBody}>{item.description}</p>
                </div>
                <div className="key-value-pairs-container" style={styles.keyValuePairsContainer}>
                    <RecursiveKeyValuePair data={item.data} displayNames={displayNames}/>
                </div>
                <div className="children-container" style={styles.childrenContainer}>
                    {Children.map(children, (child) =>
                        isValidElement(child)
                            ? cloneElement(child, {
                                children: Children.map(child.props.children, (nestedChild) =>
                                    isValidElement(nestedChild)
                                        ? cloneElement(nestedChild, {id: item.key})
                                        : nestedChild
                                ),
                            })
                            : child
                    )}
                </div>
            </div>
        </div>
    );
};

export default Card;