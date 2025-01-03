<#assign indent = ""?left_pad(indent * 4)>
${indent}<SearchBar
${indent}   searchQuery={${component.id}${resource.urlParameters[0]?cap_first}}
${indent}   onSearch={${component.id}HandleSubmit}
${indent}   onQueryChange={${component.id}${resource.urlParameters[0]?cap_first}HandleChange}
${indent}/>
