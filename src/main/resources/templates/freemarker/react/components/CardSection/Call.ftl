<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.parent.role>
    <#case "root">
    <#case "child">
${indent}<div className="${component.styleId}-container">
${indent}    {${component.parent.id}Fetched && (
${indent}       <CardSection
                <#if component.parent.title??>
${indent}           title="${component.parent.title}"
                </#if>
${indent}           styles={styles.${component.id}}
${indent}           mapModel={${component.id}Filter}
${indent}           onClick={(${component.urlParameter}) => navigate(`${component.templateLiteralRoute}`)}
${indent}           responseSchema={${component.parent.id}Responses[${component.parent.id}FetchResponse?.httpStatusCode]?.responseSchema.type}
${indent}           data={${component.parent.id}FetchResponse?.data}
${indent}           displayNames={${component.parent.id}Responses[${component.parent.id}FetchResponse?.httpStatusCode]?.displayNames}
${indent}       >
${indent}       </CardSection>
${indent}    )}
${indent}</div>
        <#break>
</#switch>
