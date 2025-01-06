import InputField from "./InputField";
import Button from "./Button";
import "./Form.css";

export default function ${data.id?cap_first}({
        ${data.id}HandleSubmit,
<#list data.resource.urlParameters as parameter>
        ${data.id}${parameter?cap_first}, ${data.id}${parameter?cap_first}HandleChange, ${data.id}${parameter?cap_first}Error,
</#list>
<#list data.resource.requestParameters as parameter>
        ${data.id}${parameter?cap_first}, ${data.id}${parameter?cap_first}HandleChange, ${data.id}${parameter?cap_first}Error,
</#list>
    })
{
    return (
        <div className="${data.styleId}-container">
            <form onSubmit={${data.id}HandleSubmit} className="form-container">
                <div className="form-inputs">
                <#list data.resource.urlParameters as parameter>
                    <InputField
                        label="${parameter}"
                        value={${data.id}${parameter?cap_first}}
                        onChange={${data.id}${parameter?cap_first}HandleChange}
                        placeholder={"Enter " + "${parameter}"}
                        error={${data.id}${parameter?cap_first}Error}
                    />
                </#list>
                <#list data.resource.requestParameters as parameter>
                    <InputField
                        label="${parameter}"
                        value={${data.id}${parameter?cap_first}}
                        onChange={${data.id}${parameter?cap_first}HandleChange}
                        placeholder={"Enter " + "${parameter}"}
                        error={${data.id}${parameter?cap_first}Error}
                    />
                </#list>
                </div>
                <Button
                    text="${data.body.submit.name}"
                    type="submit"
                />
            </form>
        </div>
    );
}



