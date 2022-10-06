package org.example.simpletweetsite.repositories;

import org.example.simpletweetsite.domain.Message;
import org.example.simpletweetsite.domain.User;
import org.example.simpletweetsite.domain.dto.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


// This will be AUTO IMPLEMENTED by Spring into a Bean called messageRepository
// CRUD refers Create, Read, Update, Delete

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("select new org.example.simpletweetsite.domain.dto.MessageDto(" +
            "m, count(ml), sum(case when ml = :user then 1 else 0 end) > 0) " +
            "from Message m left join m.likes ml group by m")
    Page<MessageDto> findAll(@Param("user") User user, Pageable pageable);

    @Query("select new org.example.simpletweetsite.domain.dto.MessageDto(" +
            "m, count(ml), sum(case when ml = :user then 1 else 0 end) > 0) " +
            "from Message m left join m.likes ml where m.tag = :tag group by m")
    Page<MessageDto> findByTag(@Param("user") User user, @Param("tag") String tag, Pageable pageable);


    @Query("select new org.example.simpletweetsite.domain.dto.MessageDto(" +
            "m, count(ml), sum(case when ml = :user then 1 else 0 end) > 0) " +
            "from Message m left join m.likes ml where m.author = :author group by m")
    Page<MessageDto> findByUser(@Param("user") User user, @Param("author") User author, Pageable pageable);

}
