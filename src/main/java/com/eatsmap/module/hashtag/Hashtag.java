package com.eatsmap.module.hashtag;

import com.eatsmap.module.hashtag.dto.CreateHashtagRequest;
import com.eatsmap.module.hashtag.dto.UpdateHashtagRequest;
import com.eatsmap.module.reviewHashtagHistory.ReviewHashtagHistory;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "hashtag_seq", sequenceName = "hashtag_seq", initialValue = 1001, allocationSize = 30)
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hashtag_seq")
    @Column(name = "hashtag_id")
    private Long id;

    private String hashtagCode;

    private String hashtagName;

    private boolean deleted;

    @OneToMany(mappedBy = "hashtag")
    private List<ReviewHashtagHistory> reviewHashtagHistories = new ArrayList<>();

    public static Hashtag createHashtag(CreateHashtagRequest request) {
        return Hashtag.builder()
                .hashtagCode(request.getHashtagCode())
                .hashtagName(request.getHashtagName())
                .deleted(false)
                .build();
    }

    public void updateHashtag(UpdateHashtagRequest request) {
        this.hashtagCode = request.getHashtagCode();
        this.hashtagName = request.getHashtagName();
    }

    public void deleteHashtag() {
        this.deleted = true;
    }
}
