import './SearchBar.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";

const SearchBar = ({ placeholder = "Search...", onSearch, onQueryChange, searchQuery, styles={} }) => {
    const handleInputChange = (event) => {
        const { value } = event.target;
        onQueryChange(value);
    };

    return (
        <form className="search-bar" onSubmit={onSearch} style={styles.searchBar}>
            <input
                type="text"
                value={searchQuery}
                onChange={handleInputChange}
                placeholder={placeholder}
                className="search-input"
                style={styles.searchInput}
            />
            <button type="submit" className="search-button" style={styles.searchButton}>
                <FontAwesomeIcon icon={faMagnifyingGlass} />
            </button>
        </form>
    );
};

export default SearchBar;
