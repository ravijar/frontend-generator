<#assign indent = ""?left_pad(indentValue * 4)>
${indent}<div className="${component.styleId}-result-container">
${indent}    {${component.id}Fetched && (
${indent}       <CardSection
${indent}           responseData={${component.id}FetchResponse?.data}
${indent}           responseSchema={${component.id}Responses[${component.id}FetchResponse?.httpStatusCode]?.responseSchema}
${indent}           displayNames={${component.id}Responses[${component.id}FetchResponse?.httpStatusCode]?.displayNames}
${indent}           styles={styles.${component.id}.cardSection}
${indent}       >
    <#if body.result.component.components??>
        <#assign indentValue = indentValue + 3>
        <#list body.result.component.components as subComponent>
            <#switch subComponent.type>
                <#case "Button">
                    <#assign body = subComponent>
                    <#include buttonCall>
                <#break>
            </#switch>
        </#list>
        <#assign indentValue = indentValue - 3>
        <#assign indent = ""?left_pad(indentValue * 4)>
    </#if>
${indent}       </CardSection>
${indent}    )}
${indent}</div>

