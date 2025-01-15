import InputField from "./InputField";
import Button from "./Button";
import "./Form.css";

export default function ${data.id?cap_first}({
        ${data.id}HandleSubmit,
<#list data.resource.urlParameters as parameter>
        ${data.id}${parameter.name?cap_first}, ${data.id}${parameter.name?cap_first}HandleChange, ${data.id}${parameter.name?cap_first}Error,
</#list>
<#list data.resource.requestProperties as property>
        ${data.id}${property.name?cap_first}, ${data.id}${property.name?cap_first}HandleChange, ${data.id}${property.name?cap_first}Error,
</#list>
        styles = {},
    })
{
    return (
        <div className="${data.styleId}-container">
            <form onSubmit={${data.id}HandleSubmit} className="form-container" style={styles.formContainer}>
                <div className="form-inputs" style={styles.formInputs}>
                <#list data.resource.urlParameters as parameter>
                    <InputField
                        label="${parameter.displayName}"
                        value={${data.id}${parameter.name?cap_first}}
                        onChange={${data.id}${parameter.name?cap_first}HandleChange}
                        placeholder={"Enter " + "${parameter.displayName}"}
                        error={${data.id}${parameter.name?cap_first}Error}
                        styles={styles.inputField}
                    />
                </#list>
                <#list data.resource.requestProperties as property>
                    <InputField
                        label="${property.displayName}"
                        value={${data.id}${property.name?cap_first}}
                        onChange={${data.id}${property.name?cap_first}HandleChange}
                        placeholder={"Enter " + "${property.displayName}"}
                        error={${data.id}${property.name?cap_first}Error}
                        styles={styles.inputField}
                    />
                </#list>
                </div>
                <Button
                    text="${data.body.submit.name}"
                    type="submit"
                    styles={styles.${data.id}}
                />
            </form>
        </div>
    );
}



