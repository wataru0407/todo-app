package com.example.todoapi.repository.task;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskRepository {

    @Select("select id, title from tasks where id = #{taskId}")
    Optional<TaskRecord> select(Long taskId);

    @Select("select id, title from tasks limit #{limit} offset #{offset}")
    List<TaskRecord> selectList(int limit, long offset);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into tasks (title) values (#{title})")
    void insert(TaskRecord record);

    @Update("update tasks set title = #{title} where id = #{id}")
    void update(TaskRecord taskRecord);

    @Delete("delete from tasks where id = #{id}")
    void delete(Long taskId);
}
