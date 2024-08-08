export const getStyle = (customStyles, property) => {
    return customStyles && customStyles[property] ? customStyles[property] : {};
};

export const getDisplayName = (displayNames, name) => {
    return displayNames && displayNames[name] ? displayNames[name] : name;
};