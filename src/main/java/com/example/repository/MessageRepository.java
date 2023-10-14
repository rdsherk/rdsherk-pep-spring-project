package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer>{

    /**
     * 
     * @param posted_by
     * @return List of messages with given posted_by
     */
    @Query(value = "SELECT * FROM message WHERE posted_by=?1", nativeQuery = true)
    List<Message> findByPostedBy(int posted_by);
}
