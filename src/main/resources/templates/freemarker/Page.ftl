import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { DefaultApi } from "../client_api";
<#if httpMethod == "POST" || httpMethod == "PUT">
import ${requestSchema} from "../client_api/src/model/${requestSchema}";
</#if>
import InputField from "../components/InputField";
import RecursiveKeyValuePair from "../components/RecursiveKeyValuePair";
import "./Page.css";

export default function ${pageName?cap_first}() {
    const navigate = useNavigate();
    const clientApi = new DefaultApi();

<#list fields as field>
    const [${field.name}, set${field.name?cap_first}] = useState("");
    const [${field.name}Error, set${field.name?cap_first}Error] = useState("");
</#list>

<#if responseSchema.type == "null">
    const [responseData, setResponseData] = useState({});
<#elseif responseSchema.type == "array">
    const [responseData, setResponseData] = useState([]);
</#if>

<#list fields as field>
    const handle${field.name?cap_first}Change = (value) => {
        set${field.name?cap_first}(value);
        if (value.trim() === '') {
            set${field.name?cap_first}Error('This field is required');
        } else {
            set${field.name?cap_first}Error('');
        }
    };

</#list>
<#list nextPages as nextPage>
    const onClick${nextPage.name} = () => {
        navigate("/${nextPage.name?uncap_first}");
    }

</#list>
    const handleSubmit = (event) => {
        event.preventDefault();
    <#if httpMethod == "POST" || httpMethod == "PUT">
        const body = new ${requestSchema}({
        <#list requestParams as param>
            ${param.name} : ${param.name},
        </#list>
        });
    </#if>

    <#if httpMethod == "GET">
        clientApi.${apiMethod}(<#list fields as field>${field.name}, </#list>(error, data, response) => {
            if (error) {
                console.log(error);
            }
            setResponseData(response.body);
        });
    <#elseif httpMethod == "POST">
        clientApi.${apiMethod}(body, (error, data, response) => {
            if (error) {
                console.log(error);
            }
            setResponseData(response.body);
        });
    <#elseif httpMethod == "DELETE">
        clientApi.${apiMethod}(${fields[0].name}, (error, data, response) => {
            if (error) {
                console.log(error);
            }
            setResponseData(response.body);
        });
    <#elseif httpMethod == "PUT">
        clientApi.${apiMethod}(body, ${fields[0].name}, (error, data, response) => {
            if (error) {
                console.log(error);
            }
            setResponseData(response.body);
        });
    </#if>

    };

    return (
        <div className="page-container">
            <form onSubmit={handleSubmit} className="form-container">
                <div className="form-inputs">
                <#list fields as field>
                    <InputField
                        label={"${field.name?cap_first}"}
                        value={${field.name}}
                        onChange={handle${field.name?cap_first}Change}
                        placeholder={"Enter ${field.name?cap_first}"}
                        error={${field.name}Error}
                    />
                </#list>
                </div>
                <button type="submit" className="form-submit">Submit</button>
            </form>

        <#if responseSchema.type == "null">
            <div className="key-value-pairs-container">
                <RecursiveKeyValuePair data={responseData} />
            </div>
        <#elseif responseSchema.type == "array">
            <div className="array-container">
                {responseData.map((item, index) => (
                    <div key={index} className="array-item">
                        <RecursiveKeyValuePair data={item} />
                    </div>
                ))}
            </div>
        </#if>

            <div className="navigation-buttons-container">
            <#list nextPages as nextPage>
                <button className="navigation-button" onClick={onClick${nextPage.name}}>${nextPage.name}</button>
            </#list>
            </div>
        </div>
    );
}
