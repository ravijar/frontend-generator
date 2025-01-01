<#assign heroSectionTemplatePath = "/components/HeroSection.ftl">
<#assign searchBarTemplatePath = "/components/SearchBar.ftl">
<#assign buttonTemplatePath = "/components/Button.ftl">

<#assign fetchTemplatePath = "/logic/Fetch.ftl">
<#assign navigateTemplatePath = "/logic/Navigate.ftl">

<#assign useStateTemplatePath = "/hooks/UseState.ftl">

import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { createConfiguration, DefaultApi } from "../client_api";
import HeroSection from "../components/HeroSection";
import SearchBar from "../components/SearchBar";
import Button from "../components/Button";

export default function ${data.name?cap_first}() {
    const navigate = useNavigate();

    const configuration = createConfiguration();
    const clientApi = new DefaultApi(configuration);

    <#-- Creating Component Hooks -->
    <#list data.components as component>
        <#assign body = component.body>
        <#assign indent = 1>
        <#switch body.type>
            <#case "SearchBar">
                <#assign state = "${component.id}FetchResponse">
                <#include useStateTemplatePath>
            <#break>
        </#switch>
    </#list>

    <#-- Creating Component Logic -->
    <#list data.components as component>
        <#assign body = component.body>
        <#assign resource = (component.resource)!>
        <#assign indent = 1>
        <#switch body.type>
            <#case "SearchBar">
                <#include fetchTemplatePath>
            <#break>
            <#case "Button">
                <#include navigateTemplatePath>
            <#break>
        </#switch>

    </#list>

    return (
        <div className = "page-container">
            <#-- Calling Components -->
            <#list data.components as component>
                <#assign body = component.body>
                <#assign indent = 3>
                <#switch body.type>
                    <#case "HeroSection">
                        <#include heroSectionTemplatePath>
                        <#break>
                    <#case "SearchBar">
                        <#include searchBarTemplatePath>
                        <#break>
                    <#case "Button">
                        <#include buttonTemplatePath>
                        <#break>
                </#switch>
            </#list>
        </div>
    )
};