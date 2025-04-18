package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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

    /** Lv2. @EntityGraph 사용하기
     * @EntityGraph : fetch join 대신 사용할 수 있는 어노테이션. 연관 엔티티 즉시로딩 가능하게 함!
     *                JPQL에서 fetch join과 페이징을 함께 쓰기 어려울 때, 여러 곳에서 동일한 fetch 패턴이 반복 될 때 사용하면 좋다.
     *                단! @EntityGraph는 단순히 연관된 엔티티를 쪼인해주도록 JPA에게 힌트만 주는 역할을 해서
     *                Where절, Order By절 등은 직접 쿼리로 써주거나 메서드 네이밍 처리 해줘야 한다!
     */

    @EntityGraph(attributePaths = {"user"}) // "user"는 연관관계 맺은 필드명 의미 => Todo 엔티티 가보면 확인 가능 !!
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT t FROM Todo t " +
            "WHERE t.id = :todoId") // where절은 쿼리로 유지
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    int countById(Long todoId);
}
