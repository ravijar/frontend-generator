import './SearchBar.css';

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
                Search
            </button>
        </form>
    );
};

export default SearchBar;
