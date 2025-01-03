import './SearchBar.css';

const SearchBar = ({ placeholder = "Search...", onSearch, onQueryChange, searchQuery }) => {
    const handleInputChange = (event) => {
        const { value } = event.target;
        onQueryChange(value);
    };

    return (
        <form className="search-bar" onSubmit={onSearch}>
            <input
                type="text"
                value={searchQuery}
                onChange={handleInputChange}
                placeholder={placeholder}
                className="search-input"
            />
            <button type="submit" className="search-button">
                Search
            </button>
        </form>
    );
};

export default SearchBar;
