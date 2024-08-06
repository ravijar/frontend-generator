export const getStyle = (customStyles, property) => {
    return customStyles && customStyles[property] ? customStyles[property] : {};
};