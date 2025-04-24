<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.parent.role>
    <#case "root">
    <#case "child">
${indent}<div className="${component.styleId}-container">
${indent}    {${component.parent.id}Fetched && (
${indent}       <Card
${indent}           item={${component.id}Filter()}
${indent}           displayNames={${component.parent.id}Responses[${component.parent.id}FetchResponse?.httpStatusCode]?.displayNames}
${indent}           styles={styles.${component.id}}
${indent}       >
        <#include nestCall>
${indent}       </Card>
${indent}    )}
${indent}</div>
        <#break>
</#switch>