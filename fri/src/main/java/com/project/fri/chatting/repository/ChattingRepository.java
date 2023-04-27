package com.project.fri.chatting.repository;


import com.project.fri.chatting.entity.ChattingMessage;
import org.springframework.data.jpa.repository.JpaRepository;


// spring data jpa는 인터페이스를 상속 받는 것 만으로 바로 사용이 가능하지만
// qeury dsl를 사용하기 위해서 queryFactory가 필요함
// 따라서 query dsl 구현체를 만들어 해당 인터페이스를 상속받아야함
// queryFactory를 bean으로 등록해서 사용해도 되는데 인터페이스 레포 말고 클래스 레포가 필요한거 같음
public interface ChattingRepository extends JpaRepository<ChattingMessage,Long>,
    ChattingRepositoryCustom {

}
