package com.kernel360.infra.mapper;

import com.kernel360.entity.commoncode.CommonCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommonCodeMapper {
    /** 애노테이션으로 맵핑이 보기 싫다면 xml로 따로 빼면 됩니다. **/
    @Results(id = "CommonCodeVO", value={
            @Result(property = "codeNo", column = "code_no"),
            @Result(property = "codeName", column = "code_name"),
            @Result(property = "upperNo", column = "upper_no"),
            @Result(property = "upperName", column = "upper_name"),
            @Result(property = "sortOrder", column = "sort_order"),
            @Result(property = "isUsed", column = "is_used"),
            @Result(property = "description", column = "description"),
            @Result(property = "subDescription", column = "sub_description"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "modifiedAt", column = "modified_at"),
            @Result(property = "modifiedBy", column = "modified_by")
    })
    @Select("SELECT * FROM common_code WHERE upper_name = #{codeName} and is_used = #{isUsed}")
    List<CommonCode> findAllByUpperNameAndIsUsed(String codeName, boolean isUsed);
}

