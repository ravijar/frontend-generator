<#assign responses = "/react/constants/Responses.ftl">

<#assign useState = "/react/hooks/UseState.ftl">
<#assign useEffect = "/react/hooks/UseEffect.ftl">

<#assign fetch = "/react/logic/Fetch.ftl">
<#assign navigate = "/react/logic/Navigate.ftl">
<#assign handleChange = "/react/logic/HandleChange.ftl">
<#assign handleSubmit = "/react/logic/HandleSubmit.ftl">

<#assign alertCall = "/react/components/Alert/Call.ftl">
<#assign alertState = "/react/components/Alert/State.ftl">

<#assign buttonCall = "/react/components/Button/Call.ftl">

<#assign cardSectionCall = "/react/components/CardSection/Call.ftl">

<#assign containerCall = "/react/components/Container/Call.ftl">
<#assign containerEffect = "/react/components/Container/Effect.ftl">
<#assign containerLogic = "/react/components/Container/Logic.ftl">
<#assign containerState = "/react/components/Container/State.ftl">

<#assign formCall = "/react/components/Form/Call.ftl">
<#assign formEffect = "/react/components/Form/Effect.ftl">
<#assign formLogic = "/react/components/Form/Logic.ftl">
<#assign formState = "/react/components/Form/State.ftl">

<#assign heroSectionCall = "/react/components/HeroSection/Call.ftl">
<#assign heroSectionEffect = "/react/components/HeroSection/Effect.ftl">
<#assign heroSectionLogic = "/react/components/HeroSection/Logic.ftl">
<#assign heroSectionState = "/react/components/HeroSection/State.ftl">

<#assign searchBarCall = "/react/components/SearchBar/Call.ftl">
<#assign searchBarLogic = "/react/components/SearchBar/Logic.ftl">
<#assign searchBarState = "/react/components/SearchBar/State.ftl">

<#assign resultCall = "/react/components/common/result/Call.ftl">
<#assign resultState = "/react/components/common/result/State.ftl">

<#assign nestCall = "/react/components/common/nest/Call.ftl">
<#assign nestEffect = "/react/components/common/nest/Effect.ftl">
<#assign nestLogic = "/react/components/common/nest/Logic.ftl">
<#assign nestState = "/react/components/common/nest/State.ftl">
import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { createConfiguration, DefaultApi } from "../client_api";
import HeroSection from "../components/HeroSection";
import SearchBar from "../components/SearchBar";
import Button from "../components/Button";
import CardSection from "../components/CardSection";
import Alert from "../components/Alert";
<#list page.components as component>
    <#switch component.type>
        <#case "Form">
import ${component.id?cap_first} from "../components/${component.id?cap_first}";
            <#break>
    </#switch>
</#list>
import "./${page.name?cap_first}.css";
import styles from "../custom_styles/${page.name?cap_first}";

<#-- Creating Constants -->
<#assign indentValue = 0>
<#list page.components as component>
    <#include responses>
</#list>

export default function ${page.name?cap_first}() {
    const navigate = useNavigate();
    <#if page.urlParameter??>const { ${page.urlParameter} } = useParams();</#if>

    const configuration = createConfiguration();
    const clientApi = new DefaultApi(configuration);

    <#-- Creating Component UseStates -->
    <#assign indentValue = 1>
    <#list page.components as parentComponent>
        <#assign component = parentComponent>
        <#switch parentComponent.type>
            <#case "HeroSection">
                <#include heroSectionState>
                <#break>
            <#case "SearchBar">
                <#include searchBarState>
                <#break>
            <#case "Form">
                <#include formState>
                <#break>
            <#case "Container">
                <#include containerState>
                <#break>
        </#switch>
    </#list>

    <#-- Creating Component UseEffects -->
    <#assign indentValue = 1>
    <#list page.components as parentComponent>
    <#assign component = parentComponent>
    <#switch parentComponent.type>
        <#case "HeroSection">
            <#include heroSectionEffect>
            <#break>
        <#case "Container">
            <#include containerEffect>
            <#break>
        <#case "Form">
            <#include formEffect>
            <#break>
    </#switch>
    </#list>

    <#-- Creating Component Logic -->
    <#assign indentValue = 1>
    <#list page.components as parentComponent>
        <#assign component = parentComponent>
        <#switch parentComponent.type>
            <#case "HeroSection">
                <#include heroSectionLogic>
                <#break>
            <#case "SearchBar">
                <#include searchBarLogic>
                <#break>
            <#case "Form">
                <#include formLogic>
                <#break>
            <#case "Container">
                <#include containerLogic>
                <#break>
        </#switch>
    </#list>

    return (
        <div className = "page-container">
            <#-- Calling Components -->
            <#assign indentValue = 3>
            <#list page.components as parentComponent>
                <#assign component = parentComponent>
                <#switch parentComponent.type>
                    <#case "HeroSection">
                        <#include heroSectionCall>
                        <#break>
                    <#case "SearchBar">
                        <#include searchBarCall>
                        <#break>
                    <#case "Button">
                        <#include buttonCall>
                        <#break>
                    <#case "Form">
                        <#include formCall>
                        <#break>
                    <#case "Container">
                        <#include containerCall>
                        <#break>
                </#switch>
            </#list>
        </div>
    )
};