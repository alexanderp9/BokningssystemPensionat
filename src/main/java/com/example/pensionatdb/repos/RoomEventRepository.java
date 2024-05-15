package com.example.pensionatdb.repos;

import com.example.pensionatdb.models.RoomEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomEventRepository extends JpaRepository<RoomEvent, Long> {

    List<RoomEvent> findByRoomId(Long roomId);
}
