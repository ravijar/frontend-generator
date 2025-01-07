<#assign indent = ""?left_pad(indent * 4)>
${indent}<div className="${component.styleId}-result-container">
${indent}   <CardSection
${indent}       responseData={${component.id}FetchResponse}
${indent}       responseSchema={${component.id}FetchResponseSchema}
${indent}       styles={styles.${component.id}.cardSection}
${indent}   />
${indent}</div>

