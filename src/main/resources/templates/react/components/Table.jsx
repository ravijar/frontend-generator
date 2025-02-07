import "./Table.css";
import {Children, cloneElement, isValidElement} from "react";

export default function Table({ columns, data, rowKey, displayNames, children }) {
    const childComponents = Array.isArray(children) ? children : [children];

    return (
        <div className="table-container">
            <table className="custom-table">
                <thead>
                <tr>
                    {columns.map((col) => (
                        <th key={col}>{displayNames[col]}</th>
                    ))}
                    {childComponents.map((_, index) => (
                        <th key={`action-${index}`}></th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {data.length === 0 ? (
                    <tr>
                        <td colSpan={columns.length + childComponents.length} className="no-data">
                            No data available
                        </td>
                    </tr>
                ) : (
                    data.map((row, index) => (
                        <tr key={index}>
                            {columns.map((col) => (
                                <td key={col}>{row[col] !== undefined ? row[col] : "-"}</td>
                            ))}
                            {childComponents.map((child, childIndex) => (
                                <td key={`child-${childIndex}`}>
                                    {isValidElement(child)
                                        ? cloneElement(child, {
                                            children: Children.map(child.props.children, (nestedChild) =>
                                                isValidElement(nestedChild)
                                                    ? cloneElement(nestedChild, {id: row[rowKey]})
                                                    : nestedChild
                                            ),
                                        })
                                        : child}
                                </td>
                            ))}
                        </tr>
                    ))
                )}
                </tbody>
            </table>
        </div>
    );
}
