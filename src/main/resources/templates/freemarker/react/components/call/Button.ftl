<#assign indent = ""?left_pad(indent * 4)>
${indent}<div className="${component.styleId}-container">
${indent}   <Button
${indent}       text="${body.text.body}"
${indent}       onClick={${component.id}Navigate}
${indent}   />
${indent}</div>

