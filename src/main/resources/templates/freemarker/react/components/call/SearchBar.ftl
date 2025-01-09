<#assign indent = ""?left_pad(indent * 4)>
${indent}<div className="${component.styleId}-container">
${indent}   <SearchBar
${indent}       searchQuery={${component.id}${resource.urlParameters[0]?cap_first}}
${indent}       onSearch={${component.id}HandleSubmit}
${indent}       onQueryChange={${component.id}${resource.urlParameters[0]?cap_first}HandleChange}
${indent}       styles={styles.${component.id}}
${indent}   />
${indent}</div>

