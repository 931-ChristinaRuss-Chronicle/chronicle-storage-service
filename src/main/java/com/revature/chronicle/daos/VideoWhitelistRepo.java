package com.revature.chronicle.daos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.chronicle.models.Video;

@Repository
public interface VideoWhitelistRepo {
	public static final String ADD_USER = "INSERT INTO video_whitelist VALUES ? ?";
	public static final String GET_USERS = "SELECT * FROM video_whitelist WHERE video_id = ?";
	public static final String DELETE_USER = "DELETE FROM note_whitelist WHERE user_id = ?";
	
	@Query(value = ADD_USER, nativeQuery = true)
    List<Video> addUser(int noteId, String userId);
	
	@Query(value = GET_USERS, nativeQuery = true)
    List<Video> getUsers(int noteId);
	
	@Query(value = DELETE_USER, nativeQuery = true)
	void deleteUser(String userId);
}
