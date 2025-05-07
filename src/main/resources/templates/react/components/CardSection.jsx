import "./CardSection.css";
import NoContent from "./NoContent";

const CardSection = ({ items, onClick, styles = {} }) => {
    if (!Array.isArray(items) || items.length === 0) {
        return (
            <div className="card-array-container" style={styles.cardArrayContainer}>
                <NoContent />
            </div>
        );
    }

    return (
        <div className="card-array-container" style={styles.cardArrayContainer}>
            {items.map((item) => (
                <div
                    key={item.key}
                    className="card"
                    onClick={() => onClick(item.key)}
                    style={styles.card}
                >
                    {item.image && (
                        <div
                            className="card-img"
                            style={{
                                backgroundImage: `url(${item.image})`,
                                ...styles.cardImg,
                            }}
                        ></div>
                    )}
                    <div className="card-info" style={styles.cardInfo}>
                        <p className="text-title" style={styles.textTitle}>{item.title}</p>
                        {item.description && (
                            <p className="text-body" style={styles.textBody}>{item.description}</p>
                        )}
                        {item.highlight && (
                            <div className="highlight-text" style={styles.highlightText}>
                                {item.highlight}
                            </div>
                        )}
                    </div>
                </div>
            ))}
        </div>
    );
};

export default CardSection;
