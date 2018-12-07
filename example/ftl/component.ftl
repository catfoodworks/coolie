<@context>
    <@import>
        com.catfoodworks.coolie.example.common.util.IdUtil
        org.springframework.beans.factory.annotation.Autowired
        org.springframework.stereotype.Component
        java.io.Serializable
        java.util.Date
        java.util.List
        java.util.function.Consumer
        ${mapper_package}.${classNameFcUc}Mapper
        ${model_package}.${classNameFcUc}Example
        ${model_package}.${classNameFcUc}
    </@import>
    <@definition for="class" param="annotation" >
    Component
    </@definition>
    <@class suffix="Component">
        <@property>
    @Autowired
    private ${classNameFcUc}Mapper ${classNameFcLc}Mapper;
        </@property>
        <@method>
    public int create${classNameFcUc}(${classNameFcUc} ${classNameFcLc}) {
            <@provider provider="componentMethodProvider" method="create"></@provider>
        return this.${classNameFcLc}Mapper.insertSelective(${classNameFcLc});
    }

    public ${classNameFcUc} retrieve${classNameFcUc}(Serializable ${classNameFcLc}Id) {
        return this.${classNameFcLc}Mapper.selectByPrimaryKey(${classNameFcLc}Id.toString());
    }

    public List<${classNameFcUc}> queryAll${classNameFcUc}(${classNameFcUc} ${classNameFcLc}) {
        return this.queryAll${classNameFcUc}(buildSimpleExample(${classNameFcLc}));
    }

    public List<${classNameFcUc}> queryAll${classNameFcUc}(Consumer<${classNameFcUc}Example> exampleBuilder) {
        ${classNameFcUc}Example example = new ${classNameFcUc}Example();
        exampleBuilder.accept(example);
        return this.queryAll${classNameFcUc}(example);
    }

    public List<${classNameFcUc}> queryAll${classNameFcUc}(${classNameFcUc}Example ${classNameFcLc}Example) {
        return this.${classNameFcLc}Mapper.selectByExample(${classNameFcLc}Example);
    }

    public List<${classNameFcUc}> query${classNameFcUc}(${classNameFcUc} ${classNameFcLc}) {
        return this.query${classNameFcUc}(buildSimpleExample(${classNameFcLc}));
    }

    public List<${classNameFcUc}> query${classNameFcUc}(Consumer<${classNameFcUc}Example> exampleBuilder) {
            ${classNameFcUc}Example example = new ${classNameFcUc}Example();
        exampleBuilder.accept(example);
        return this.query${classNameFcUc}(example);
    }

    public List<${classNameFcUc}> query${classNameFcUc}(${classNameFcUc}Example ${classNameFcLc}Example) {
            <@provider provider="componentMethodProvider" method="queryActive"></@provider>
        return this.${classNameFcLc}Mapper.selectByExample(${classNameFcLc}Example);
    }

    public int update${classNameFcUc}(${classNameFcUc} ${classNameFcLc}, ${classNameFcUc}Example ${classNameFcLc}Example, boolean isSelective) {
            <@provider provider="componentMethodProvider" method="update"></@provider>

        if(isSelective)
            if(${classNameFcLc}Example == null)
                return this.${classNameFcLc}Mapper.updateByPrimaryKeySelective(${classNameFcLc});
            else
                return this.${classNameFcLc}Mapper.updateByExampleSelective(${classNameFcLc}, ${classNameFcLc}Example);
        else
            if(${classNameFcLc}Example == null)
                return this.${classNameFcLc}Mapper.updateByPrimaryKey(${classNameFcLc});
            else
                return this.${classNameFcLc}Mapper.updateByExample(${classNameFcLc}, ${classNameFcLc}Example);
    }

    public int update${classNameFcUc}(Consumer<${classNameFcUc}> modelBuilder, Consumer<${classNameFcUc}Example> exampleBuilder) {
        ${classNameFcUc} model = new ${classNameFcUc}();
        modelBuilder.accept(model);

        ${classNameFcUc}Example example = new ${classNameFcUc}Example();
        exampleBuilder.accept(example);

        return update${classNameFcUc}(model, example, true);
    }

    public int update${classNameFcUc}(${classNameFcUc} ${classNameFcLc}) {
        return this.update${classNameFcUc}(${classNameFcLc}, null, true);
    }

    public int update${classNameFcUc}(${classNameFcUc} ${classNameFcLc}, ${classNameFcUc}Example ${classNameFcLc}Example) {
        return this.update${classNameFcUc}(${classNameFcLc}, ${classNameFcLc}Example, true);
    }

    public int delete${classNameFcUc}(Serializable ${classNameFcLc}Id) {
        return this.${classNameFcLc}Mapper.deleteByPrimaryKey(${classNameFcLc}Id.toString());
    }

    public int countAll${classNameFcUc}(${classNameFcUc} ${classNameFcLc}) {
        return this.countAll${classNameFcUc}(buildSimpleExample(${classNameFcLc}));
    }

    public int countAll${classNameFcUc}(Consumer<${classNameFcUc}Example> exampleBuilder) {
        ${classNameFcUc}Example example = new ${classNameFcUc}Example();
        exampleBuilder.accept(example);
        return countAll${classNameFcUc}(example);
    }

    public int countAll${classNameFcUc}(${classNameFcUc}Example ${classNameFcLc}Example) {
        return this.${classNameFcLc}Mapper.countByExample(${classNameFcLc}Example);
    }

    public int count${classNameFcUc}(${classNameFcUc} ${classNameFcLc}) {
        return this.count${classNameFcUc}(buildSimpleExample(${classNameFcLc}));
    }

    public int count${classNameFcUc}(Consumer<${classNameFcUc}Example> exampleBuilder) {
        ${classNameFcUc}Example example = new ${classNameFcUc}Example();
        exampleBuilder.accept(example);
        return count${classNameFcUc}(example);
    }

    public int count${classNameFcUc}(${classNameFcUc}Example ${classNameFcLc}Example) {
            <@provider provider="componentMethodProvider" method="queryActive"></@provider>
        return this.${classNameFcLc}Mapper.countByExample(${classNameFcLc}Example);
    }

    protected ${classNameFcUc}Example buildSimpleExample(${classNameFcUc} record) {
        ${classNameFcUc}Example ${classNameFcLc}Example = new ${classNameFcUc}Example();
        if(record != null) {
            ${classNameFcUc}Example.Criteria criteria = ${classNameFcLc}Example.createCriteria();
            <@columns>
                if(record.${getter}() != null) {
                    criteria.and${propertyNameFcUc}EqualTo(record.${getter}());
                }
            </@columns>
        }
        return ${classNameFcLc}Example;
    }
        </@method>
    </@class>
</@context>
