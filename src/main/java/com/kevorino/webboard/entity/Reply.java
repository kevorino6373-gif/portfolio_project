package com.kevorino.webboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long rno;

    private String text;

    private String replyer;

    //회원이 아닌사람도 댓글을 남길 수 있다고 가정하여 Board와의 연관관계는 설정하지않음

}
