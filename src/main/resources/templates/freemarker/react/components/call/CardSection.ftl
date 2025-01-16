<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "parent">
${indent}<div className="${component.resultComponent.styleId}-container">
${indent}    {${component.id}Fetched && (
${indent}       <CardSection
${indent}           responseData={${component.id}FetchResponse?.data}
${indent}           responseSchema={${component.id}Responses[${component.id}FetchResponse?.httpStatusCode]?.responseSchema}
${indent}           displayNames={${component.id}Responses[${component.id}FetchResponse?.httpStatusCode]?.displayNames}
${indent}           styles={styles.${component.id}.cardSection}
${indent}       >
                <#assign component = component.resultComponent>
                <#include nestCall>
${indent}       </CardSection>
${indent}    )}
${indent}</div>
        <#break>
</#switch>
