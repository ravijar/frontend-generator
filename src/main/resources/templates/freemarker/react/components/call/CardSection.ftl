<#assign indent = ""?left_pad(indent * 4)>
${indent}<div className="${component.styleId}-result-container">
${indent}    {${component.id}Fetched && (
${indent}       <CardSection
${indent}           responseData={${component.id}FetchResponse?.data}
${indent}           responseSchema={${component.id}Responses[${component.id}FetchResponse?.httpStatusCode]?.responseSchema}
${indent}           displayNames={${component.id}Responses[${component.id}FetchResponse?.httpStatusCode]?.displayNames}
${indent}           styles={styles.${component.id}.cardSection}
${indent}       />
${indent}    )}
${indent}</div>

