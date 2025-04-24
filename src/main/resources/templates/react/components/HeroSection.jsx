import './HeroSection.css';

const HeroSection = ({ backgroundImage, text, subText, styles = {}, children }) => {
    return (
        <div
            className="hero-section"
            style={{
                backgroundImage: `url(${backgroundImage})`,
                ...styles.heroSection,
            }}
        >
            <div className="hero-overlay" style={styles.heroOverlay}></div>
            <div className="hero-content" style={styles.heroContent}>
                <h1>{text}</h1>
                <h2>{subText}</h2>
            </div>
            <div className="children-container" style={styles.childrenContainer}>
                {children}
            </div>
        </div>
    );
};

export default HeroSection;
