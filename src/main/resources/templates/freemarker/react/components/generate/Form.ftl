import InputField from "./InputField";
import Button from "./Button";
import "./Form.css";

export default function ${component.id?cap_first}({
        ${component.id}HandleSubmit,
<#list component.resource.urlParameters as parameter>
        ${component.id}${parameter.name?cap_first}, ${component.id}${parameter.name?cap_first}HandleChange, ${component.id}${parameter.name?cap_first}Error,
</#list>
<#list component.resource.requestProperties as property>
        ${component.id}${property.name?cap_first}, ${component.id}${property.name?cap_first}HandleChange, ${component.id}${property.name?cap_first}Error,
</#list>
        styles = {},
    })
{
    return (
        <div className="${component.styleId}-container">
            <form onSubmit={${component.id}HandleSubmit} className="form-container" style={styles.formContainer}>
                <div className="form-inputs" style={styles.formInputs}>
                <#list component.resource.urlParameters as parameter>
                    <InputField
                        label="${parameter.displayName}"
                        value={${component.id}${parameter.name?cap_first}}
                        onChange={${component.id}${parameter.name?cap_first}HandleChange}
                        placeholder={"Enter " + "${parameter.displayName}"}
                        error={${component.id}${parameter.name?cap_first}Error}
                        styles={styles.inputField}
                    />
                </#list>
                <#list component.resource.requestProperties as property>
                    <InputField
                        label="${property.displayName}"
                        value={${component.id}${property.name?cap_first}}
                        onChange={${component.id}${property.name?cap_first}HandleChange}
                        placeholder={"Enter " + "${property.displayName}"}
                        error={${component.id}${property.name?cap_first}Error}
                        styles={styles.inputField}
                    />
                </#list>
                </div>
                <Button
                    text="${component.submitText}"
                    type="submit"
                    styles={styles.${component.id}}
                />
            </form>
        </div>
    );
}



