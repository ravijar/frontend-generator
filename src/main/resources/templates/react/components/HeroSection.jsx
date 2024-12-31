import './HeroSection.css';

const HeroSection = ({ backgroundImage, textContent }) => {
    return (
        <div
            className="hero-section"
            style={{
                backgroundImage: `url(${backgroundImage})`,
            }}
        >
            <div className="hero-overlay"></div>
            <div className="hero-content">
                <h1>{textContent}</h1>
            </div>
        </div>
    );
};

export default HeroSection;
