<#--
  @Deprecated since January 10, 2025.
-->

import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { createConfiguration, DefaultApi } from "../client_api";
import InputField from "../components/InputField";
import RecursiveKeyValuePair from "../components/RecursiveKeyValuePair";
import Alert from "../components/Alert";
import { getStyle, getDisplayName } from "../common/Utils"
<#if pageDTO.customStyled>
import styles from "../customStyles/${pageDTO.pageName?cap_first}Styles";
</#if>
import "./Page.css";

const displayNames = {
<#list displayNames as displayNameMap>
<#list displayNameMap?keys as key>
    ${key} : "${displayNameMap[key]}",
</#list>
</#list>
}

const responses = {
<#list responses as response>
    "${response.code}": {
        description: <#if response.description??>"${response.description}"<#else>null</#if>,
        responseSchema: {
            name: <#if response.schema??>"${response.schema}"<#else>null</#if>,
            type: <#if response.type??>"${response.type}"<#else>null</#if>
        },
        nextPages: [
        <#list response.nextPages as nextPage>
            {name: "${nextPage}"}<#if nextPage_has_next>,</#if>
        </#list>
        ]
    }<#if response_has_next>,</#if>
</#list>
};

export default function ${pageDTO.pageName?cap_first}() {
    const navigate = useNavigate();

    const configuration = createConfiguration();
    const clientApi = new DefaultApi(configuration);

    let customStyles = {};
<#if pageDTO.customStyled>
    customStyles = styles;
</#if>

<#list fields as field>
    const [${field.name}, set${field.name?cap_first}] = useState("");
    const [${field.name}Error, set${field.name?cap_first}Error] = useState("");
</#list>

    const [responseData, setResponseData] = useState({});

    const [responseSchema, setResponseSchema] = useState({});
    const [nextPages, setNextPages] = useState([])
    const [alert, setAlert] = useState({
        type: '',
        statusCode: null,
        message: '',
        visible: false,
    });

    const showAlert = (type, statusCode, message) => {
        setAlert({
            type: type,
            statusCode: statusCode,
            message: message,
            visible: true,
        });
    };

    const closeAlert = () => {
        setAlert({ ...alert, visible: false });
    };

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
    const onNextPageButtonClick = (pageName) => {
        navigate("/" + pageName.charAt(0).toLowerCase() + pageName.slice(1));
    }

    const fetchData = async () => {
        try {
        <#if pageDTO.resourceMethod == "POST" || pageDTO.resourceMethod == "PUT">
            const body = {
            <#list requestParams as param>
                ${param.name} : ${param.name},
            </#list>
            };

        </#if>
        <#if pageDTO.resourceMethod == "GET">
            const response = await clientApi.${apiMethod}WithHttpInfo(<#list fields as field>${field.name}, </#list>);
        <#elseif pageDTO.resourceMethod == "POST">
            const response = await clientApi.${apiMethod}WithHttpInfo(body);
        <#elseif pageDTO.resourceMethod == "DELETE">
            const response = await clientApi.${apiMethod}WithHttpInfo(${fields[0].name});
        <#elseif pageDTO.resourceMethod == "PUT" || pageDTO.resourceMethod == "PATCH">
            const response = await clientApi.${apiMethod}WithHttpInfo(${fields[0].name}, body);
        </#if>
            console.log(response);
            showAlert("success", response.httpStatusCode, responses[response.httpStatusCode]?.description);
            setResponseSchema(responses[response.httpStatusCode]?.responseSchema);
            setNextPages(responses[response.httpStatusCode]?.nextPages);
            setResponseData(response.data);
        } catch (error) {
            console.log(error.message);
            showAlert("error", error.code, responses[error.code]?.description);
            setResponseSchema(responses[error.code]?.responseSchema);
            setNextPages(responses[error.code]?.nextPages);
            setResponseData({});
        }
    };

<#if !fields?has_content>
    useEffect(() => {
<#else>
    const handleSubmit = (event) => {
        event.preventDefault();
</#if>
        closeAlert();
        fetchData();
    }<#if !fields?has_content>, [])</#if>;

    return (
        <div className="page-container" style={getStyle(customStyles,"pageContainer")}>
        <#if pageDTO.pageTitle?? && pageDTO.pageTitle?has_content>
            <div className="title-bar" style={getStyle(customStyles,"titleBar")}>
                <div className="title" style={getStyle(customStyles,"title")}>${pageDTO.pageTitle}</div>
            </div>
        </#if>
        <#if fields?has_content>
            <form onSubmit={handleSubmit} className="form-container" style={getStyle(customStyles,"formContainer")}>
                <div className="form-inputs" style={getStyle(customStyles,"formInputs")}>
                <#list fields as field>
                    <InputField
                        label={getDisplayName(displayNames, "${field.name}")}
                        value={${field.name}}
                        onChange={handle${field.name?cap_first}Change}
                        placeholder={"Enter " + getDisplayName(displayNames, "${field.name}")}
                        error={${field.name}Error}
                        styles={getStyle(customStyles,"inputField")}
                    />
                </#list>
                </div>
                <button type="submit" className="form-submit" style={getStyle(customStyles,"formSubmit")}>Submit</button>
            </form>
        </#if>

            {alert.visible && (
                <Alert
                    type={alert.type}
                    statusCode={alert.statusCode}
                    message={alert.message}
                    onClose={closeAlert}
                />
            )}

            {responseSchema && (
                <>
                    {responseSchema.type === "null" && (
                        <div className="key-value-pairs-container" style={getStyle(customStyles,"keyValuePairsContainer")}>
                            <RecursiveKeyValuePair
                                data={responseData}
                                displayNames={displayNames}
                                styles={getStyle(customStyles,"keyValuePair")}
                            />
                        </div>
                    )}

                    {responseSchema.type === "array" && (
                        <div className="array-container" style={getStyle(customStyles,"arrayContainer")}>
                            {responseData.map((item, index) => (
                                <div key={index} className="array-item" style={getStyle(customStyles,"arrayItem")}>
                                    <RecursiveKeyValuePair
                                        data={item}
                                        displayNames={displayNames}
                                        styles={getStyle(customStyles,"keyValuePair")}
                                    />
                                </div>
                            ))}
                        </div>
                    )}
                </>
            )}

            {nextPages.length > 0 && (
                <div className="navigation-buttons-container" style={getStyle(customStyles,"navigationButtonsContainer")}>
                    {nextPages.map((nextPage, index) => (
                        <button
                            key={index}
                            className="navigation-button"
                            style={getStyle(customStyles, "navigationButton")}
                            onClick={() => onNextPageButtonClick(nextPage.name)}
                        >
                        {nextPage.name}
                        </button>
                    ))}
                </div>
            )}
        </div>
    );
}
