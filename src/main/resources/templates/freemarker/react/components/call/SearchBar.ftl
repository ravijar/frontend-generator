<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "parent">
${indent}<div className="${component.styleId}-container">
${indent}   <SearchBar
${indent}       searchQuery={${component.id}${component.resource.urlParameters[0].name?cap_first}}
${indent}       onSearch={${component.id}HandleSubmit}
${indent}       onQueryChange={${component.id}${component.resource.urlParameters[0].name?cap_first}HandleChange}
${indent}       styles={styles.${component.id}}
${indent}   />
${indent}</div>
<#include resultCall>
        <#break>
</#switch>
