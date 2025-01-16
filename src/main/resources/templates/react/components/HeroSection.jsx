import './HeroSection.css';

const HeroSection = ({ backgroundImage, textContent, styles = {}, children }) => {
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
                <h1>{textContent}</h1>
            </div>
            {children}
        </div>
    );
};

export default HeroSection;
