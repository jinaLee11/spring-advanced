package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    /** Lv2. 분석
     * Todo와 User는 다대일 관계로 매핑되어있는 상태고,
     * Todo 전체 목록을 가져오기 위해서는 우선 Todo 목록을 조회하는 쿼리 1번 + Todo와 이어진 User도 가져오기 위해 N번의 쿼리 돌림
     *  => N+1 문제 발생 (성능이 떨어진다고 한다)
     * 해당 Repository에서는 fetch join을 사용해 N+1문제를 해결했다.
     * fetch join은 join과 비슷하지만 연관된 엔티티나 컬렉션을 한 번의 쿼리로 가져올 수 있다!
     */

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN FETCH t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    int countById(Long todoId);
}
