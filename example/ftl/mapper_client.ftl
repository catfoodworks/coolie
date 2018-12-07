<@context>
    <@import>
        com.catfoodworks.coolie.example.common.CmmonCrudMapper
        ${model_package}.${classNameFcUc}
        ${model_package}.${classNameFcUc}Example
    </@import>
    <@class type="interface" suffix="Mapper" base="CommonCrudMapper<${classNameFcUc}, ${classNameFcUc}Example>">
    </@class>
</@context>