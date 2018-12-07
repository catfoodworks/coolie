<@context>
    <@import>
        import org.apache.ibatis.annotations.Param;
        import java.util.List;
    </@import>
    <@class suffix='<T, E>'>
    int countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(String id);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExample(E example);

    T selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    int updateByExample(@Param("record") T record, @Param("example") E example);

    int updateByPrimaryKeySelective(@Param("record") T record);

    int updateByPrimaryKey(@Param("record") T record);
    </@class>
</@context>