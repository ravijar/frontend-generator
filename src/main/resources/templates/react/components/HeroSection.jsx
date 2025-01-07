import './HeroSection.css';

const HeroSection = ({ backgroundImage, textContent, styles = {} }) => {
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
        </div>
    );
};

export default HeroSection;
