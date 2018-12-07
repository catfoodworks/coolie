<@context>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${package}.${className}Mapper" >
    <resultMap id="BaseResultMap" type="${model_package}.${className}" >
        <@columns>
            <#if columnIsPk == true>
        <id column="${columnName}" property="${propertyName}" jdbcType="${columnType}" />
            <#else>
        <result column="${columnName}" property="${propertyName}" jdbcType="${columnType}" />
            </#if>
        </@columns>
    </resultMap>
    <sql id="Example_Where_Clause" >
        <where >
            <foreach collection="oredCriteria" item="criteria" separator="or" >
                <if test="criteria.valid" >
                    <trim prefix="(" suffix=")" prefixOverrides="and" >
                        <foreach collection="criteria.criteria" item="criterion" >
                            <choose >
                                <when test="criterion.noValue" >
                                    ${r'and t.${criterion.condition}'}
                                </when>
                                <when test="criterion.singleValue" >
                                    ${r'and t.${criterion.condition} #{criterion.value}'}
                                </when>
                                <when test="criterion.betweenValue" >
                                    ${r'and t.${criterion.condition} #{criterion.value} and #{criterion.secondValue}'}
                                </when>
                                <when test="criterion.listValue" >
                                    ${r'and t.${criterion.condition}'}
                                    <foreach collection="criterion.value" item="listItem" open="(" close=")" separator="," >
                                        ${r'#{listItem}'}
                                    </foreach>
                                </when>
                            </choose>
                        </foreach>
                    </trim>
                </if>
            </foreach>
        </where>
    </sql>
    <sql id="Update_By_Example_Where_Clause" >
        <where >
            <foreach collection="example.oredCriteria" item="criteria" separator="or" >
                <if test="criteria.valid" >
                    <trim prefix="(" suffix=")" prefixOverrides="and" >
                        <foreach collection="criteria.criteria" item="criterion" >
                            <choose >
                                <when test="criterion.noValue" >
                                    ${r'and ${criterion.condition}'}
                                </when>
                                <when test="criterion.singleValue" >
                                    ${r'and ${criterion.condition} #{criterion.value}'}
                                </when>
                                <when test="criterion.betweenValue" >
                                    ${r'and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}'}
                                </when>
                                <when test="criterion.listValue" >
                                    ${r'and ${criterion.condition}'}
                                    <foreach collection="criterion.value" item="listItem" open="(" close=")" separator="," >
                                        ${r'#{listItem}'}
                                    </foreach>
                                </when>
                            </choose>
                        </foreach>
                    </trim>
                </if>
            </foreach>
        </where>
    </sql>
    <sql id="Base_Column_List" >
        <@columns delimiter = "," newline = 5>t.${columnName}</@columns>
    </sql>
    <select id="selectByExample" resultMap="BaseResultMap" parameterType="${model_package}.${classNameFcUc}Example" >
        select
        <if test="distinct" >
            distinct
        </if>
        <include refid="Base_Column_List" />
        from ${tableName} t
        <if test="_parameter != null" >
            <include refid="Example_Where_Clause" />
        </if>
        <if test="orderByClause != null" >
            ${r'order by ${orderByClause}'}
        </if>
        <if test="offset != null and length != null" >
            ${r'limit ${offset}, ${length}'}
        </if>
    </select>
    <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.String" >
        select
        <include refid="Base_Column_List" />
        from ${tableName} t
        where ${tableName}_id = ${r'#{'}${classNameFcLc}Id,jdbcType=VARCHAR${r'}'}
    </select>
    <delete id="deleteByPrimaryKey" parameterType="java.lang.String" >
        delete from ${tableName} t
        where ${tableName}_id = ${r'#{'}${classNameFcLc}Id,jdbcType=VARCHAR${r'}'}
    </delete>
    <delete id="deleteByExample" parameterType="${model_package}.${classNameFcUc}Example" >
        delete from ${tableName} t
        <if test="_parameter != null" >
            <include refid="Example_Where_Clause" />
        </if>
    </delete>
    <insert id="insert" parameterType="${model_package}.${classNameFcUc}" >
        insert into ${tableName} (<@columns delimiter = "," newline = 5>${columnName}</@columns>)
        values (<@columns delimiter = "," newline = 3>${r'#{'}${propertyName},jdbcType=${columnType}${r'}'}</@columns>)
    </insert>
    <insert id="insertSelective" parameterType="${model_package}.${JavaBeanUtil("Camel", table.name)}" >
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides="," >
            <@columns>
            <if test="${propertyName} != null" >
                ${columnName},
            </if>
            </@columns>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides="," >
            <@columns>
            <if test="${propertyName} != null" >
                ${r'#{'}${propertyName},jdbcType=${columnType}${r'}'},
            </if>
            </@columns>
        </trim>
    </insert>
    <select id="countByExample" parameterType="${model_package}.${classNameFcUc}Example" resultType="java.lang.Integer" >
        select count(*) from ${tableName} t
        <if test="_parameter != null" >
            <include refid="Example_Where_Clause" />
        </if>
    </select>
    <update id="updateByExampleSelective" parameterType="map" >
        update ${tableName} t
        <set >
            <@columns>
            <if test="record.${propertyName} != null" >
                ${columnName} = ${r'#{record.'}${propertyName},jdbcType=${columnType}${r'}'},
            </if>
            </@columns>
        </set>
        <if test="_parameter != null" >
            <include refid="Update_By_Example_Where_Clause" />
        </if>
    </update>
    <update id="updateByExample" parameterType="map" >
        update ${tableName} t
        set <@columns delimiter = "," newline = 1> ${columnName} = ${r'#{record.'}${propertyName},jdbcType=${columnType}${r'}'}</@columns>
        <if test="_parameter != null" >
            <include refid="Update_By_Example_Where_Clause" />
        </if>
    </update>
    <update id="updateByPrimaryKeySelective" parameterType="${model_package}.${JavaBeanUtil("Camel", table.name)}" >
        update ${tableName} t
        <set >
            <@columns>
            <if test="record.${propertyName} != null" >
                ${columnName} = ${r'#{record.'}${propertyName},jdbcType=${columnType}${r'}'},
            </if>
            </@columns>
        </set>
        where ${tableName}_id = ${r'#{'}record.${classNameFcLc}Id,jdbcType=VARCHAR${r'}'}
    </update>
    <update id="updateByPrimaryKey" parameterType="${model_package}.${classNameFcUc}" >
        update ${tableName} t
        set
        <@columns delimiter = "," newline = 1>${columnName} = ${r'#{record.'}${propertyName},jdbcType=${columnType}${r'}'}</@columns>
        where ${tableName}_id = ${r'#{'}record.${classNameFcLc}Id,jdbcType=VARCHAR${r'}'}
    </update>
</mapper>
</@context>
