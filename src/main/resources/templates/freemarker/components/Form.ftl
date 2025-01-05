<#assign indent = ""?left_pad(indent * 4)>
${indent}<div className="${component.styleId}-container">
${indent}   <form onSubmit={${component.id}HandleSubmit} className="form-container">
${indent}       <div className="form-inputs">
                <#list resource.urlParameters as parameter>
${indent}           <InputField
${indent}               label="${parameter}"
${indent}               value={${component.id}${parameter?cap_first}}
${indent}               onChange={handle${component.id?cap_first}${parameter?cap_first}Change}
${indent}               placeholder={"Enter " + "${parameter}"}
${indent}               error={${component.id}${parameter?cap_first}Error}
${indent}           />
                </#list>
                <#list resource.requestParameters as parameter>
${indent}           <InputField
${indent}               label="${parameter}"
${indent}               value={${component.id}${parameter?cap_first}}
${indent}               onChange={${component.id}${parameter?cap_first}HandleChange}
${indent}               placeholder={"Enter " + "${parameter}"}
${indent}               error={${component.id}${parameter?cap_first}Error}
${indent}           />
                </#list>
${indent}       </div>
${indent}       <button type="submit" className="form-submit">${body.submit.name}</button>
${indent}   </form>
${indent}</div>

