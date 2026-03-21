package com.kevorino.webboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Webboard extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wno;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1500, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer; //다대일 연관관계에서 작성하의 필드는 작성하지않음

    public void changeTitle (String title){
        this.title = title;
    }

    public void changeContent (String content){
        this.content = content;
    }

}
